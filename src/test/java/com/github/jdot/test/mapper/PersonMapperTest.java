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

package com.github.jdot.test.mapper;

import com.github.jdot.test.domain.Person;
import com.github.jdot.test.view.PersonView;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonMapperTest {

    private final static LocalDate DAD_BIRTHDAY = LocalDate.of(1975, 1, 1);
    private final static LocalDate DAUGHTER_BIRTHDAY = LocalDate.of(2010, 1, 1);
    private final static LocalDate SON_BIRTHDAY = LocalDate.of(2012, 1, 1);

    @Test
    public void mapPersonToPersonView() {
        Person dad = buildDad();
        Person dadClone = buildDad();
        assertThat(dad.hashCode()).isEqualTo(dadClone.hashCode());
        assertThat(dad).isEqualTo(dadClone);

        PersonView dadView = new PersonMapper().toView(dad);

        assertThat(dad.getName()).isEqualTo(dadView.getName());
        assertThat(Date.from(dad.getBirthday().atStartOfDay(ZoneId.systemDefault()).toInstant())).isEqualTo(dadView.getBirthday());

        assertThat(dadView.getChildren()).hasSize(2);
    }

    private Person buildDad() {
        Person son = new Person.Builder().withBirthday(SON_BIRTHDAY).withName("jack").build();
        Person daughter = new Person.Builder().withBirthday(DAUGHTER_BIRTHDAY).withName("jill").build();
        List<Person> children = Lists.newArrayList(son, daughter);
        return new Person.Builder().withBirthday(DAD_BIRTHDAY).withName("john").withChildren(children).build();
    }
}
