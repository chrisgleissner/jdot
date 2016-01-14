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

/**
 * Abstraction of instance {@link Field} and JavaBean {@link Property}.
 * 
 * @author Christian Gleissner
 */
public abstract class State {

    protected String name;
    protected Type owner;
    protected String type;

    public abstract <ANNO extends Annotation> ANNO getAnnotation(Class<? extends ANNO> annotationClass);

    public String getName() {
        return name;
    }

    public Type getOwner() {
        return owner;
    }

    public String getType() {
        return type;
    }

    public boolean hasAnnotation(Class<? extends Annotation> annotationClass) {
        return getAnnotation(annotationClass) != null;
    }
}
