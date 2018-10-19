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

import com.github.jdot.file.builder.EqualityBuilder;
import com.github.jdot.type.Type;
import com.github.jdot.file.builder.BeanBuilder;
import com.github.jdot.file.builder.TypeBuilder;

/**
 * Java file for view class.
 * 
 * @author Christian Gleissner
 */
public class ViewClass extends JavaFile {

    public ViewClass(Type originalType) {
        super.originalType = originalType;
        if (originalType.isInterface()) {
            super.type = new Type.Mapper(originalType).withName("(.*?)Intf", "$1View", true).withPackageName(
                    "(.*?)\\.domain", "$1\\.view", true).map();
        } else {
            super.type = new Type.Mapper(originalType).withName("(^.*?$)", "$1View", true).withPackageName(
                    "(.*?)\\.domain", "$1\\.view", true).map();
        }
    }

    @Override
    protected String prepareWrite() {
        new TypeBuilder(this).useView().build();
        new BeanBuilder(this).useView().exposeAllPropertiesAsWriteable().withGetters().withSetters().build();
        new EqualityBuilder(this).useView().build();
        return type.getQualifiedName();
    }
}
