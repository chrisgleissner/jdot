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

import java.lang.annotation.Annotation;

import javax.lang.model.element.VariableElement;

import com.google.common.base.Joiner;
import com.googlecode.jdot.mapper.NameMapper;
import com.googlecode.jdot.util.ElementUtil;

/**
 * Instance field of a {@link Type}.
 * 
 * @author Christian Gleissner
 */
public class Field extends State {

    private final Object constantValue;
    private final VariableElement element;
    private String visibility = "";

    public Field(Type owner, VariableElement element) {
        this.element = element;
        this.owner = owner;
        name = element.getSimpleName().toString();
        visibility = ElementUtil.getVisibility(element.getModifiers());
        type = element.asType().toString();
        constantValue = element.getConstantValue();
    }

    @Override
    public <ANNO extends Annotation> ANNO getAnnotation(Class<? extends ANNO> annotationClass) {
        return element.getAnnotation(annotationClass);
    }

    public Object getConstantValue() {
        return constantValue;
    }

    public VariableElement getElement() {
        return element;
    }

    public String getVisibility() {
        return visibility;
    }

    public void mapName(NameMapper mapping) {
        name = mapping.applyTo(name);
    }

    public void mapType(NameMapper mapping) {
        type = mapping.applyTo(type);
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        return Joiner.on(" ").skipNulls().join(visibility, type, name);
    }
}
