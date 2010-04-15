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

package com.googlecode.jdot.mapper;

/**
 * Mapper for field, class, and package names between domain and view hierarchies.
 * 
 * @author Christian Gleissner
 */
public interface NameMapper {

    /**
     * Applies the mapping defined by this instance to the specified source string.
     * 
     * @param source
     * @return mapped source
     */
    String applyTo(String source);

}