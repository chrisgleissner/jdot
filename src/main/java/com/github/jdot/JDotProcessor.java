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

package com.github.jdot;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;

import com.github.jdot.annotation.Domain;
import com.github.jdot.file.DomainClass;
import com.github.jdot.file.DomainSuperClass;
import com.github.jdot.file.ViewClass;
import com.github.jdot.type.Type;
import com.github.jdot.file.DomainMapperClass;

/**
 * JDot annotation processor for immutable business objects with builders, as well as matching JavaBean-compliant domain
 * transfer objects. Main entrance point to JDot.
 */
@SupportedAnnotationTypes("com.github.jdot.annotation.Domain")
public class JDotProcessor extends AbstractTypeProcessor {

    public boolean processType(ProcessingEnvironment processingEnv, Type type) {
        if (type.hasAnnotation(Domain.class)) {
            if (type.isInterface()) {
                new DomainClass(type).write(processingEnv);
            } else {
                new DomainSuperClass(type).write(processingEnv);
            }
            new ViewClass(type).write(processingEnv);
            new DomainMapperClass(type).write(processingEnv);
        }
        return false;
    }
}
