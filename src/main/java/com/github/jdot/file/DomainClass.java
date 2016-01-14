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

package com.github.jdot.file;

import com.github.jdot.file.builder.EqualityBuilder;
import com.github.jdot.annotation.Domain;
import com.github.jdot.file.builder.BeanBuilder;
import com.github.jdot.file.builder.BuilderBuilder;
import com.github.jdot.file.builder.TypeBuilder;
import com.github.jdot.type.Type;

/**
 * Java file for concrete domain class which implements the {@link Domain}-annotated interface that drives the code
 * generation.
 * 
 * @author Christian Gleissner
 */
public class DomainClass extends JavaFile {

    public DomainClass(Type originalType) {
        super.originalType = originalType;
        if (originalType.isInterface()) {
            super.type = new Type.Mapper(originalType).withName("(.*?)Intf", "$1", true).map();
        } else {
            throw new RuntimeException("Cannot map class " + originalType.getQualifiedName()
                    + " to domain class. Expected interface.");
        }
    }

    @Override
    protected String prepareWrite() {
        new TypeBuilder(this).implementInterface().build();
        new BeanBuilder(this).withGetters().build();
        new BuilderBuilder(this).build();
        new EqualityBuilder(this).build();
        return type.getQualifiedName();

    }
}
