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

package com.googlecode.jdot.test.mapper;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.googlecode.jdot.test.domain.Person;
import com.googlecode.jdot.test.view.PersonView;

public class PersonMapperTest {

    private final static DateTime DAD_BIRTHDAY = new DateTime(1970, 1, 1, 12, 0, 0, 0);
    private final static DateTime DAUGHTER_BIRTHDAY = new DateTime(2020, 1, 1, 12, 0, 0, 0);
    private final static DateTime SON_BIRTHDAY = new DateTime(2020, 1, 1, 12, 0, 0, 0);

    @Before
    public void before() {

    }

    @Test
    public void mapPersonToPersonView() {
        Person dad = buildDad();
        Person dadClone = buildDad();
        assertEquals(dad.hashCode(), dadClone.hashCode());
        assertTrue(dad.equals(dadClone));
        PersonView dadView = new PersonMapper().toView(dad);
        assertEquals(dad.getName(), dadView.getName());
        assertEquals(dad.getBirthday().getMillis(), dadView.getBirthday().getTime());
        assertEquals(2, dadView.getChildren().size());
        dadView.getChildren().iterator().next();

        // TODO add more assertions

    }

    private Person buildDad() {
        Person son = new Person.Builder().withBirthday(new DateTime()).withName("jack").build();
        Person daughter = new Person.Builder().withBirthday(new DateTime()).withName("jill").build();
        List<Person> children = Lists.newArrayList(son, daughter);
        Person dad = new Person.Builder().withBirthday(new DateTime()).withName("john").withChildren(children).build();
        return dad;
    }
}
