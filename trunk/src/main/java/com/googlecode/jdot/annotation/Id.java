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

/**
 * Marks an instance field or JavaBean property method as being part of the unique identifier of a class. Such fields /
 * JavaBean properties are used to create the {@code equals} and {@code hashCode} methods. Can be either placed on an
 * instance field or the getter method of a JavaBean property.
 * 
 * @author Christian Gleissner
 */
@Retention(RetentionPolicy.SOURCE)
@Target(value = { ElementType.FIELD, ElementType.METHOD })
public @interface Id {
}
