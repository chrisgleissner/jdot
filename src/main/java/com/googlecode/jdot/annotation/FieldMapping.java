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

package com.googlecode.jdot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.googlecode.jdot.mapper.TypeMapper;

/**
 * Declares the type mapping of a field by specifying either source/target types or by specifying a {@link TypeMapper}.
 * 
 * @author Christian Gleissner
 */
@Retention(RetentionPolicy.SOURCE)
@Target(value = { ElementType.METHOD, ElementType.FIELD })
public @interface FieldMapping {

    boolean domain() default false;

    String mapper() default "";

    String source() default "";

    String target() default "";

    boolean view() default false;
}
