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

package com.github.jdot.test.domain;

import com.github.jdot.annotation.Domain;
import com.github.jdot.annotation.Id;
import com.github.jdot.annotation.TypeMapping;

import java.time.LocalDate;
import java.util.Collection;

@Domain
@TypeMapping(values = { "com.github.jdot.mapper.LocalDateMapper" })
public interface PersonIntf {

    @Id
    LocalDate getBirthday();

    Collection<? extends PersonIntf> getChildren();

    Collection<Plant> getGardenContents();

    @Id
    String getName();

    Collection<? extends PersonIntf> getParents();

}
