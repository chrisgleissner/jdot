/*
 * Copyright (C) 2010-2016 Christian Gleissner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not uCopyright (C) 2010-2016se this file except in compliance with the License.
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

package com.github.jdot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class or interface as driver of the code generation.
 * 
 * <p>
 * The effect of this annotation depends on where it is placed:
 * <ul>
 * <li>Domain class: Generate abstract superclass for domain class and concrete view class.
 * <li>Interface (needs to end with <code>Intf</code>: Generate concrete domain class and concrete view class
 * implementing the interface.
 * </ul>
 * </p>
 * 
 * @author Christian Gleissner
 */
@Retention(RetentionPolicy.SOURCE)
@Target(value = { ElementType.TYPE })
public @interface Domain {
}
