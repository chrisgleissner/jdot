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

package com.github.jdot.util;

import org.apache.commons.lang3.StringUtils;

import com.github.jdot.type.State;

public class NameUtil {

    public final static String classNameToFieldName(String className) {
        return StringUtils.uncapitalize(className);
    }

    public final static String toBuilderMethod(State state) {
        return "with" + StringUtils.capitalize(state.getName());
    }

    public final static String toGetter(State state) {
        if ("boolean".equals(state.getType())) {
            return "is" + StringUtils.capitalize(state.getName());
        } else {
            return "get" + StringUtils.capitalize(state.getName());
        }
    }

    public final static String toSetter(State state) {
        return "set" + StringUtils.capitalize(state.getName());
    }
}
