/*
 * Copyright (C) 2010 Christian Gleissner
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

package com.googlecode.jdot.type;

import static com.googlecode.jdot.util.Logger.logPhaseDuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;

import com.google.common.base.Joiner;
import com.googlecode.jdot.util.ElementUtil;

/**
 * Registry of all known {@link Type}s.
 */
public class TypeRegistry {

    private final ConcurrentNavigableMap<String, Type> types = new ConcurrentSkipListMap<String, Type>();

    public TypeRegistry(ProcessingEnvironment processingEnv, Set<? extends Element> elements) {
        long startTime = System.nanoTime();
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            Type type = new Type(processingEnv, typeElement);
            enrichTypeWithFields(type);
            enrichTypeWithProperties(type);
            addType(type);
        }
        resolveTypeReferences();
        logPhaseDuration(processingEnv, "Type registration", elements.size(), System.nanoTime() - startTime);
    }

    public boolean exists(String qualifiedName) {
        return types.containsKey(qualifiedName);
    }

    /**
     * Returns the {@link Type} with the specified name. If the type is unknown to the registry, a new {@link Type}
     * instance is created with only its <code>name</code> field initialized.
     * 
     * @param name
     * @return
     */
    public Type getType(String qualifiedName) {
        return getType(qualifiedName, null);
    }

    /**
     * Returns the {@link Type} for the specified element. If the type is unknown to the registry, a new {@link Type}
     * instance is created with only its <code>name</code> and <code>element</code> fields initialized.
     * 
     * @param name
     * @return
     */
    public Type getType(TypeElement element) {
        return getType(ElementUtil.getQualifiedName(element));
    }

    public Collection<Type> getTypes() {
        return types.values();
    }

    @Override
    public String toString() {
        return Joiner.on(", ").join(types.keySet());
    }

    private void addType(Type type) {
        types.put(type.getQualifiedName(), type);
    }

    private void enrichTypeWithFields(Type type) {
        List<Field> fields = new ArrayList<Field>();
        for (VariableElement variableElement : ElementFilter.fieldsIn(type.getElement().getEnclosedElements())) {
            for (Modifier modifier : variableElement.getModifiers()) {
                if (Modifier.STATIC.equals(modifier)) {
                    continue;
                }
            }
            fields.add(new Field(type, variableElement));
        }
        type.setFields(fields);
    }

    private void enrichTypeWithProperties(Type type) {
        Map<String, Property> properties = new LinkedHashMap<String, Property>();
        for (ExecutableElement executableElement : ElementFilter.methodsIn(type.getElement().getEnclosedElements())) {
            Property property = Property.build(type, executableElement);
            if (property != null) {
                Property existingProperty = properties.get(property.getName());
                if (existingProperty == null) {
                    properties.put(property.getName(), property);
                } else {
                    existingProperty.setWriteable(existingProperty.isWriteable() | property.isWriteable());
                }
            }
        }
        type.setProperties(properties.values());
    }

    private Type getType(String qualifiedName, TypeElement element) {
        Type type = types.get(qualifiedName);
        if (type == null) {
            type = types.putIfAbsent(qualifiedName, new Type(qualifiedName, element));
        }
        return type;
    }

    /**
     * Resolves any type references. Should be invoked after all types were added.
     */
    private void resolveTypeReferences() {
        for (Type type : types.values()) {
            type.resolveTypeReferences(this);
        }
    }
}
