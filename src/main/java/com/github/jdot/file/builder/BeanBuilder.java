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

package com.github.jdot.file.builder;

import com.github.jdot.file.JavaFile;
import com.github.jdot.type.Field;
import org.apache.commons.lang3.StringUtils;

import com.github.jdot.type.Property;

public class BeanBuilder extends JavaFileBuilder<BeanBuilder, JavaFile> {

    private boolean exposeAllPropertiesAsWriteable;
    private boolean exposeFields;

    public BeanBuilder(JavaFile javaFile) {
        super(javaFile);
    }

    @Override
    public JavaFile build() {
        // Do nothing as already built by withGetters/withSetters methods
        return javaFile;
    }

    public BeanBuilder exposeAllPropertiesAsWriteable() {
        exposeAllPropertiesAsWriteable = true;
        return this;
    }

    public BeanBuilder exposeFields() {
        exposeFields = true;
        return this;
    }

    public BeanBuilder withGetters() {
        if (exposeFields) {
            for (Field field : javaFile.getType().getFields()) {
                createGetter(field.getName(), getStateType(field));
            }
        } else {
            for (Property property : javaFile.getType().getProperties()) {
                createGetter(property.getName(), getStateType(property));
            }
        }
        return this;
    }

    public BeanBuilder withSetters() {
        if (exposeFields) {
            for (Field field : javaFile.getType().getFields()) {
                createSetter(field.getName(), getStateType(field));
            }
        } else {
            for (Property property : javaFile.getType().getProperties()) {
                if (property.isWriteable() || exposeAllPropertiesAsWriteable) {
                    createSetter(property.getName(), getStateType(property));
                }
            }
        }
        return this;
    }

    private final void createGetter(String name, String type) {
        fields.line("    private " + type + " " + name + ";");
        methods.nl();
        methods.line("    public " + type + " " + getGetterMethod(name, type) + "() {");
        methods.line("        return this." + name + ";");
        methods.line("    }");
    }

    private final void createSetter(String name, String type) {
        methods.nl();
        methods.line("    public void " + getSetterMethod(name, type) + "(" + type + " " + name + ") {");
        methods.line("        this." + name + " = " + name + ";");
        methods.line("    }");
    }

    private String getGetterMethod(String propertyName, String type) {
        String capitalizedName = StringUtils.capitalize(propertyName);
        String getterName = null;
        if ("boolean".equals(type) || "java.lang.Boolean".equals(type)) {
            getterName = "is" + capitalizedName;
        } else {
            getterName = "get" + capitalizedName;
        }
        return getterName;
    }

    private String getSetterMethod(String propertyName, String type) {
        return "set" + StringUtils.capitalize(propertyName);
    }
}
