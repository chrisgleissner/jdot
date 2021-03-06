/*
 * Copyright (C) 2010-2018 Christian Gleissner
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

import com.github.jdot.annotation.Domain;
import com.github.jdot.file.builder.BuilderBuilder;
import com.github.jdot.file.builder.EqualityBuilder;
import com.github.jdot.type.Type;
import com.github.jdot.file.builder.BeanBuilder;
import com.github.jdot.file.builder.TypeBuilder;

/**
 * Java file for abstract superclass of {@link Domain}-annotated domain class which drives the code generation.
 * 
 * @author Christian Gleissner
 */
public class DomainSuperClass extends JavaFile {

    public DomainSuperClass(Type type) {
        super.originalType = type;
        if (type.isInterface()) {
            throw new RuntimeException("Cannot map interface " + originalType.getQualifiedName()
                    + " to domain class. Expected class.");
        } else {
            super.type = new Type.Mapper(originalType).withName("(.*?)", "Abstract$1", true).toAbstractType().map();
        }
    }

    @Override
    protected String prepareWrite() {
        new TypeBuilder(this).makeAbstract().build();
        new BeanBuilder(this).withGetters().build();
        new BuilderBuilder(this).forOriginalType().build();
        new EqualityBuilder(this).build();
        return type.getQualifiedName();
    }

}
