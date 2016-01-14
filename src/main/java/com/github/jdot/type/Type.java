/*
 * Copyright (C) 2010-2016 Christian Gleissner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.jdot.type;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import com.github.jdot.annotation.TypeMapping;
import com.google.common.base.Joiner;
import com.github.jdot.annotation.DomainSuperclass;
import com.github.jdot.annotation.ViewSuperclass;
import com.github.jdot.mapper.NameMapper;
import com.github.jdot.mapper.RegexpNameMapper;
import com.github.jdot.mapper.TypeMapper;
import com.github.jdot.util.ElementUtil;

/**
 * Type (class or interface) to be processed. Provides simplified access to the mirror API declared in the {@code
 * javax.lang.model} package for the purpose of domain mapping.
 * 
 * @author Christian Gleissner
 */
public class Type implements Cloneable {

    /**
     * Type mapper which transforms a specified type into a target type. The original type is typically the type that
     * contains the annotations which drive the code generation whilst the mapped type describes the exact structure of
     * the type to be generated.
     * 
     * @author Christian Gleissner
     */
    public final static class Mapper {

        private boolean abstractType;
        private final List<RegexpNameMapper> fieldNameMappings = new ArrayList<RegexpNameMapper>();
        private final List<RegexpNameMapper> fieldTypeMappings = new ArrayList<RegexpNameMapper>();
        private NameMapper nameMapping;
        private NameMapper packageNameMapping;
        private final Type type;

        public Mapper(Type type) {
            this.type = type;
        }

        public Type map() {
            Type mappedType;
            try {
                mappedType = (Type) type.clone();
                if (nameMapping != null) {
                    mappedType.name = nameMapping.applyTo(type.name);
                }
                if (packageNameMapping != null) {
                    mappedType.packageName = packageNameMapping.applyTo(type.packageName);
                }
                if (abstractType) {
                    mappedType.abstractType = true;
                }
                mappedType.updateQualifiedName();

                for (NameMapper mapping : fieldNameMappings) {
                    for (Field field : mappedType.getFields()) {
                        field.mapName(mapping);
                    }
                    for (Property property : mappedType.getProperties()) {
                        property.mapName(mapping);
                    }
                }

                for (NameMapper mapping : fieldTypeMappings) {
                    for (Field field : mappedType.getFields()) {
                        field.mapType(mapping);
                    }
                    for (Property property : mappedType.getProperties()) {
                        property.mapType(mapping);
                    }
                }

                return mappedType;
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException("Cloning not supported", e);
            }
        }

        public Mapper toAbstractType() {
            abstractType = true;
            return this;
        }

        public Mapper withFieldName(String source, String target, boolean regexp) {
            fieldNameMappings.add(new RegexpNameMapper(source, target, regexp));
            return this;
        }

        public Mapper withFieldType(String source, String target, boolean regexp) {
            fieldTypeMappings.add(new RegexpNameMapper(source, target, regexp));
            return this;
        }

        public Mapper withName(String source, String target, boolean regexp) {
            nameMapping = new RegexpNameMapper(source, target, regexp);
            return this;
        }

        public Mapper withPackageName(String source, String target, boolean regexp) {
            packageNameMapping = new RegexpNameMapper(source, target, regexp);
            return this;
        }
    }

    private boolean abstractType;
    private String domainSuperclassName;
    private final Map<String, String> domainTypeToTypeMapperNameMap = new HashMap<String, String>();
    private final Map<String, String> domainTypeToViewTypeMap = new HashMap<String, String>();
    private final TypeElement element;
    private final Map<String, Field> fields = new HashMap<String, Field>();
    private final Collection<String> interfaceNames = new ArrayList<String>();
    private final Collection<Type> interfaces = new ArrayList<Type>();
    private boolean interfaceType;
    private String name;
    private String packageName;
    private final List<String> parameters = new ArrayList<String>();
    private Collection<Property> properties;
    private String qualifiedName;
    private final Collection<Type> superClasses = new ArrayList<Type>();
    private final Collection<String> superClassNames = new ArrayList<String>();
    private String viewSuperclassName;
    private String visibility;

    public Type(ProcessingEnvironment processingEnv, TypeElement element) {
        name = element.getSimpleName().toString();
        if (!element.getTypeParameters().isEmpty()) {
            for (TypeParameterElement typeParameterElement : element.getTypeParameters()) {
                StringBuilder parameter = new StringBuilder(256);
                parameter.append(typeParameterElement.toString());
                if (!typeParameterElement.getBounds().isEmpty()) {
                    String boundary = typeParameterElement.getBounds().get(0).toString();
                    if (!"java.lang.Object".equals(boundary)) {
                        parameter.append(" extends ");
                        // TODO handle more than one boundary
                        parameter.append(typeParameterElement.getBounds().get(0).toString());
                    }
                }
                parameters.add(parameter.toString());
            }
        }
        packageName = element.getEnclosingElement().toString();
        updateQualifiedName();
        interfaceType = ElementKind.INTERFACE.equals(element.getKind());
        for (Modifier modifier : element.getModifiers()) {
            if (Modifier.ABSTRACT.equals(modifier)) {
                abstractType = true;
            }
        }
        visibility = ElementUtil.getVisibility(element.getModifiers());
        for (TypeMirror typeMirror : processingEnv.getTypeUtils().directSupertypes(element.asType())) {
            String superType = typeMirror.toString();
            if (!"java.lang.Object".equals(superType)) {
                boolean interfaceFound = true;
                if (typeMirror instanceof DeclaredType) {
                    DeclaredType declaredType = (DeclaredType) typeMirror;
                    if (ElementKind.CLASS.equals(declaredType.asElement().getKind())) {
                        interfaceFound = false;
                    }
                }
                if (interfaceFound) {
                    interfaceNames.add(superType);
                } else {
                    superClassNames.add(superType);
                }
            }
        }
        this.element = element;
        populateTypeMappers(element);
        populateDomainSuperclassName(element);
        populateViewSuperclassName(element);
    }

    public Type(String name, TypeElement element) {
        int lastDotIndex = name.lastIndexOf(".");
        if (lastDotIndex != -1) {
            packageName = name.substring(0, lastDotIndex);
            this.name = name.substring(lastDotIndex + 1);
        } else {
            this.name = name;
        }
        qualifiedName = name;
        this.element = element;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Type other = (Type) obj;
        if (qualifiedName == null) {
            if (other.qualifiedName != null) {
                return false;
            }
        } else if (!qualifiedName.equals(other.qualifiedName)) {
            return false;
        }
        return true;
    }

    public String getDomainSuperclassName() {
        return domainSuperclassName;
    }

    public Map<String, String> getDomainTypeToTypeMapperNameMap() {
        return domainTypeToTypeMapperNameMap;
    }

    public TypeElement getElement() {
        return element;
    }

    public Field getField(String fieldName) {
        return fields.get(fieldName);
    }

    public Collection<Field> getFields() {
        return fields.values();
    }

    /**
     * Returns name with types and inheritance, if any.
     * 
     * @return
     */
    public String getFullName() {
        StringBuilder sb = new StringBuilder(128);
        sb.append(this);
        if (!parameters.isEmpty()) {
            sb.append("<");
            sb.append(Joiner.on(", ").join(parameters));
            sb.append(">");
        }
        if (!superClassNames.isEmpty()) {
            sb.append(" extends " + Joiner.on(", ").join(superClassNames));
        }
        if (!interfaceNames.isEmpty()) {
            sb.append((isInterface() ? " extends " : " implements ") + Joiner.on(", ").join(interfaceNames));
        }
        return sb.toString();
    }

    public String getImports() {
        Set<String> imports = new TreeSet<String>();
        for (Field field : fields.values()) {
            imports.add("import " + field.getType() + ";");
        }
        for (Property property : properties) {
            imports.add("import " + property.getType() + ";");
        }
        return Joiner.on("\n").join(imports);
    }

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public Collection<String> getParameters() {
        return parameters;
    }

    public Collection<Property> getProperties() {
        return properties;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    // TODO This currently returns either properties or fields. Merge fields and properties.
    public Collection<? extends State> getStates() {
        if (properties.isEmpty()) {
            return fields.values();
        } else {
            return properties;
        }
    }

    public String getTypeMapperName(String domainType) {
        return domainTypeToTypeMapperNameMap.get(domainType);
    }

    public String getViewSuperclassName() {
        return viewSuperclassName;
    }

    public String getViewType(String domainType) {
        String viewType = domainTypeToViewTypeMap.get(domainType);
        if (viewType == null) {
            return domainType;
        } else {
            return viewType;
        }
    }

    public String getVisibility() {
        return visibility;
    }

    public boolean hasAnnotation(Class<? extends Annotation> annotationClass) {
        return element.getAnnotation(annotationClass) != null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (qualifiedName == null ? 0 : qualifiedName.hashCode());
        return result;
    }

    public boolean isAbstract() {
        return abstractType;
    }

    public boolean isInterface() {
        return interfaceType;
    }

    public void setFields(Collection<Field> fields) {
        for (Field field : fields) {
            this.fields.put(field.getName(), field);
        }
    }

    public void setProperties(Collection<Property> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return Joiner.on(" ").skipNulls().join(visibility, abstractType && !interfaceType ? "abstract" : null,
                interfaceType ? "interface" : "class", name);
    }

    private Set<String> getTypeMapperClassNames(TypeMapping mapperAnnotation) {
        Set<String> typeMapperClassNames = new HashSet<String>();
        if (mapperAnnotation != null) {
            for (String typeMapperClassName : mapperAnnotation.values()) {
                typeMapperClassNames.add(typeMapperClassName);
            }
        }
        return typeMapperClassNames;
    }

    private void populateDomainSuperclassName(TypeElement element) {
        DomainSuperclass domainSuperclass = element.getAnnotation(DomainSuperclass.class);
        if (domainSuperclass == null) {
            domainSuperclass = element.getEnclosingElement().getAnnotation(DomainSuperclass.class);
        }
        if (domainSuperclass != null) {
            domainSuperclassName = domainSuperclass.value();
        }
    }

    private void populateTypeMappers(TypeElement typeElement) {
        Set<String> typeMapperClassNames = new HashSet<String>();
        typeMapperClassNames.addAll(getTypeMapperClassNames(typeElement
                .getAnnotation(TypeMapping.class)));
        typeMapperClassNames.addAll(getTypeMapperClassNames(typeElement.getEnclosingElement().getAnnotation(
                TypeMapping.class)));
        for (String typeMapperClassName : typeMapperClassNames) {
            TypeMapper<?, ?> typeMapper;
            try {
                Class<? extends TypeMapper<?, ?>> clazz = (Class<? extends TypeMapper<?, ?>>) Class
                        .forName(typeMapperClassName);
                typeMapper = clazz.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Could not instantiate type mapper class " + typeMapperClassName, e);
            }
            domainTypeToViewTypeMap.put(typeMapper.getDomainType().getName(), typeMapper.getViewType().getName());
            domainTypeToTypeMapperNameMap.put(typeMapper.getDomainType().getName(), typeMapper.getClass().getName());
        }
    }

    private void populateViewSuperclassName(TypeElement element) {
        ViewSuperclass viewSuperclass = element.getAnnotation(ViewSuperclass.class);
        if (viewSuperclass == null) {
            viewSuperclass = element.getEnclosingElement().getAnnotation(ViewSuperclass.class);
        }
        if (viewSuperclass != null) {
            viewSuperclassName = viewSuperclass.value();
        }
    }

    private void updateQualifiedName() {
        qualifiedName = packageName + "." + name;
    }

    void resolveTypeReferences(TypeRegistry typeRegistry) {
        for (String interfaceName : interfaceNames) {
            interfaces.add(typeRegistry.getType(interfaceName));
        }
        for (String superClassName : superClassNames) {
            superClasses.add(typeRegistry.getType(superClassName));
        }
    }
}
