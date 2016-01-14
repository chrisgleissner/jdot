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

package com.github.jdot.mapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * NameMapper which supports simple or regular expression mapping. Simple mapping replaces a string with the defined
 * target string if it exactly matches the source string. Regular expression mapping permits a standard Java regular
 * expression pattern for both source and target.
 * 
 * @author Christian Gleissner
 */
public class RegexpNameMapper implements NameMapper {
    private Pattern pattern;
    private final String source;
    private final String target;

    public RegexpNameMapper(String source, String target) {
        this(source, target, false);
    }

    public RegexpNameMapper(String source, String target, boolean regexp) {
        this.source = source;
        this.target = target;
        if (regexp) {
            pattern = Pattern.compile(source);
        }
    }

    public String applyTo(String source) {
        String result = source;
        if (pattern != null) {
            Matcher matcher = pattern.matcher(source);
            result = matcher.replaceFirst(target);
        } else {
            if (source.equals(this.source)) {
                result = target;
            }
        }
        return result;
    }
}
