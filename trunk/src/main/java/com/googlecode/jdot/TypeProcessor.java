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

package com.googlecode.jdot;

import javax.annotation.processing.ProcessingEnvironment;

import com.googlecode.jdot.type.Type;

/**
 * Annotation processor which understands {@link Type}s, a simplified facade for the javax.lang.model package especially
 * geared towards generating code for domain classes.
 * 
 * @author Christian Gleissner
 */
public interface TypeProcessor {

    /**
     * Processes the specified {@link Type} in a {@link ProcessingEnvironment}.
     * 
     * @param processingEnv
     * @param type
     * @return whether or not the set of annotations are claimed by this processor
     */
    boolean processType(ProcessingEnvironment processingEnv, Type type);
}
