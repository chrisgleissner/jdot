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

package com.googlecode.jdot.util;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic.Kind;

public class Logger {

    private final static boolean USE_STDOUT = true;

    public final static void error(ProcessingEnvironment processingEnv, String msg) {
        if (USE_STDOUT) {
            System.out.println(msg);
        } else {
            processingEnv.getMessager().printMessage(Kind.ERROR, msg);
        }
    }

    public final static void info(ProcessingEnvironment processingEnv, String msg) {
        if (USE_STDOUT) {
            System.out.println(msg);
        } else {
            processingEnv.getMessager().printMessage(Kind.NOTE, msg);
        }
    }

    public final static void logPhaseDuration(ProcessingEnvironment processingEnv, String phaseName,
            int numberOfElements, long duration) {
        info(processingEnv, String.format("%s for %d element(s) took %dms", phaseName, numberOfElements,
                duration / 1000000));
    }

    public final static void warn(ProcessingEnvironment processingEnv, String msg) {
        if (USE_STDOUT) {
            System.out.println(msg);
        } else {
            processingEnv.getMessager().printMessage(Kind.WARNING, msg);
        }
    }
}
