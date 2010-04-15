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

@TypeMapping(values = { "com.googlecode.jdot.mapper.DateTimeMapper" })
@DomainSuperclass("com.googlecode.jdot.test.domain.DomainObject")
@ViewSuperclass("com.googlecode.jdot.test.view.ViewObject")
package com.googlecode.jdot.test.domain;

import com.googlecode.jdot.annotation.DomainSuperclass;
import com.googlecode.jdot.annotation.TypeMapping;
import com.googlecode.jdot.annotation.ViewSuperclass;

