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

package com.googlecode.jdot.test.domain;

import java.util.Collection;

import org.joda.time.DateTime;

import com.googlecode.jdot.annotation.Domain;
import com.googlecode.jdot.annotation.Id;
import com.googlecode.jdot.annotation.TypeMapping;

@Domain
@TypeMapping(values = { "com.googlecode.jdot.mapper.DateTimeMapper" })
public interface PersonIntf {

    @Id
    DateTime getBirthday();

    Collection<? extends PersonIntf> getChildren();

    Collection<Plant> getGardenContents();

    @Id
    String getName();

    Collection<? extends PersonIntf> getParents();

}
