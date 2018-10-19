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

package com.github.jdot;

import static com.github.jdot.util.Logger.logPhaseDuration;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import com.github.jdot.type.TypeRegistry;
import com.github.jdot.annotation.Domain;

/**
 * Adapter which makes a {@link TypeProcessor} accessible as a Java annotation {@link Processor}.
 */
public abstract class AbstractTypeProcessor extends AbstractProcessor implements TypeProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Domain.class);
        if (!roundEnv.processingOver() && !elements.isEmpty()) {
            TypeRegistry typeRegistry = new TypeRegistry(super.processingEnv, elements);
            return processElements(typeRegistry, elements);
        } else {
            return true;
        }
    }

    private boolean processElements(TypeRegistry typeRegistry, Set<? extends Element> elements) {
        long startTime = System.nanoTime();
        boolean annotationsClaimed = true;
        for (Element element : elements) {
            annotationsClaimed &= processType(processingEnv, typeRegistry.getType((TypeElement) element));
        }
        logPhaseDuration(processingEnv, "Processing", elements.size(), System.nanoTime() - startTime);
        return annotationsClaimed;
    }
}
