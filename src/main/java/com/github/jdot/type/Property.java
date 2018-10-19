/*
 * Copyright (C) 2010-2018 Christian Gleissner
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

import com.github.jdot.mapper.NameMapper;
import com.google.common.base.Joiner;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;
import java.lang.annotation.Annotation;

/**
 * JavaBean property declared by a {@link Type}.
 * 
 * @author Christian Gleissner
 */
public class Property extends State {

    /**
     * Returns new BeanProperty instance if the specified element is a JavaBean accessor method, otherwise null.
     * 
     * @param element
     * @return
     */
    public static Property build(Type owner, ExecutableElement element) {
        Property beanProperty = null;
        String name = null;
        boolean propertyFound = true;
        boolean writeable = false;
        String type = null;

        // Check modifiers
        boolean publicFound = false;
        boolean staticFound = false;
        for (Modifier modifier : element.getModifiers()) {
            if (Modifier.PUBLIC.equals(modifier)) {
                publicFound = true;
            }
            if (Modifier.STATIC.equals(modifier)) {
                staticFound = true;
            }
        }
        if (!publicFound || staticFound) {
            propertyFound = false;
        }

        // Check method name
        if (propertyFound) {
            String methodName = element.getSimpleName().toString();
            if (methodName.startsWith("set") && Character.isUpperCase(methodName.charAt(3))) {
                name = uncapitalize(methodName.substring(3));
                writeable = true;
            } else if (methodName.startsWith("get") && Character.isUpperCase(methodName.charAt(3))) {
                name = uncapitalize(methodName.substring(3));
            } else if (methodName.startsWith("is") && Character.isUpperCase(methodName.charAt(2))) {
                name = uncapitalize(methodName.substring(2));
            } else {
                propertyFound = false;
            }
        }

        // Check arguments / return type
        if (propertyFound) {
            if (writeable) {
                if (element.getParameters().size() == 1 && TypeKind.VOID.equals(element.getReturnType().getKind())) {
                    type = element.getParameters().get(0).asType().toString();
                } else {
                    propertyFound = false;
                }
            } else {
                if (TypeKind.VOID.equals(element.getReturnType().getKind())) {
                    propertyFound = false;
                } else {
                    type = element.getReturnType().toString();
                }
            }
        }

        if (propertyFound) {
            beanProperty = new Property(owner, element, name, type, writeable);
        }
        return beanProperty;
    }

    private static String uncapitalize(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append(Character.toLowerCase(s.charAt(0)));
        sb.append(s.substring(1));
        return sb.toString();
    }

    public static String getPropertyName(String methodName) {
        String propertyName = "";
        if (methodName.startsWith("set")) {
            propertyName = uncapitalize(methodName.substring(3));
        } else if (methodName.startsWith("get")) {
            propertyName = uncapitalize(methodName.substring(3));
        } else if (methodName.startsWith("is")) {
            propertyName = uncapitalize(methodName.substring(2));
        } else {
            throw new RuntimeException("Method does not declare JavaBean property: " + methodName);
        }
        return propertyName;
    }

    private ExecutableElement getterElement;

    private ExecutableElement setterElement;
    private boolean writeable;

    private Property(Type owner, ExecutableElement element, String name, String type, boolean writeable) {
        this.owner = owner;
        if (writeable) {
            setterElement = element;
        } else {
            getterElement = element;
        }
        this.name = name;
        this.type = type;
        this.writeable = writeable;
    }

    @Override
    public <ANNO extends Annotation> ANNO getAnnotation(Class<? extends ANNO> annotationClass) {
        ANNO annotation = null;
        if (getterElement != null) {
            annotation = getterElement.getAnnotation(annotationClass);
        }
        if (annotation == null && setterElement != null) {
            annotation = setterElement.getAnnotation(annotationClass);
        }
        return annotation;
    }

    public ExecutableElement getGetterElement() {
        return getterElement;
    }

    public ExecutableElement getSetterElement() {
        return setterElement;
    }

    public boolean isWriteable() {
        return writeable;
    }

    public void mapName(NameMapper mapping) {
        name = mapping.applyTo(name);
    }

    public void mapType(NameMapper mapping) {
        type = mapping.applyTo(type);
    }

    /**
     * Merges this property with another property of the same name. Useful for consolidating separate
     * <code>Property</code> instances for the getter and setter methods, respectively.
     * 
     * @param property
     */
    public void mergeWith(Property property) {
        if (property.name.equals(name)) {
            writeable = writeable | property.writeable;
            setterElement = setterElement == null ? property.setterElement : setterElement;
            getterElement = getterElement == null ? property.getterElement : getterElement;
        }
    }

    public void setWriteable(boolean writeable) {
        this.writeable = writeable;
    }

    @Override
    public String toString() {
        return Joiner.on(" ").join(type, name);
    }
}
