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

import com.googlecode.jdot.annotation.TypeMapping;

/**
 * Mapper for types of {@link Field}s and {@link Property}s on domain and view classes. Can be declared via the
 * {@link TypeMapping} annotation on classes/interface or packages.
 * 
 * @author Christian Gleissner
 * @param <DOMAIN>
 * @param <VIEW>
 */
public interface TypeMapper<DOMAIN, VIEW> {

    Class<DOMAIN> getDomainType();

    Class<VIEW> getViewType();

    DOMAIN toDomain(VIEW view);

    VIEW toView(DOMAIN domain);

}
