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

package com.googlecode.jdot.file;

import com.googlecode.jdot.file.builder.DomainMapperBuilder;
import com.googlecode.jdot.mapper.DomainMapper;
import com.googlecode.jdot.type.Type;

/**
 * Java file for {@link DomainMapper} class.
 * 
 * @author Christian Gleissner
 */
public class DomainMapperClass extends JavaFile {

    private Type domainType;
    private Type mapperType;
    private Type viewType;

    public DomainMapperClass(Type originalType) {
        super.originalType = originalType;
        if (originalType.isInterface()) {
            domainType = new Type.Mapper(originalType).withName("(.*?)Intf", "$1", true).map();
            viewType = new Type.Mapper(originalType).withName("(.*?)Intf", "$1View", true).withPackageName(
                    "(.*?)\\.domain$", "$1\\.view", true).map();
            mapperType = new Type.Mapper(originalType).withName("(.*?)Intf", "$1Mapper", true).withPackageName(
                    "(.*?)\\.domain$", "$1\\.mapper", true).map();
        } else {
            domainType = new Type.Mapper(originalType).map();
            viewType = new Type.Mapper(originalType).withName("^(.*?)$", "$1View", true).withPackageName(
                    "(.*?)\\.domain$", "$1\\.view", true).map();
            mapperType = new Type.Mapper(originalType).withName("^(.*?)$", "$1Mapper", true).withPackageName(
                    "(.*?)\\.domain$", "$1\\.mapper", true).map();
        }
    }

    public Type getDomainType() {
        return domainType;
    }

    public Type getMapperType() {
        return mapperType;
    }

    public Type getViewType() {
        return viewType;
    }

    @Override
    protected String prepareWrite() {
        new DomainMapperBuilder(this).build();
        return mapperType.getQualifiedName();
    }
}
