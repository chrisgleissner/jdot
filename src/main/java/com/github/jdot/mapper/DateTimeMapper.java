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

import java.util.Date;

import org.joda.time.DateTime;

/**
 * Mapper between Joda {@code DateTime} and {@link Date}.
 * 
 * @author Christian Gleissner
 */
public class DateTimeMapper implements TypeMapper<DateTime, Date> {

    @Override
    public Class<DateTime> getDomainType() {
        return DateTime.class;
    }

    @Override
    public Class<Date> getViewType() {
        return Date.class;
    }

    @Override
    public DateTime toDomain(Date date) {
        return new DateTime(date);
    }

    @Override
    public Date toView(DateTime dateTime) {
        return new Date(dateTime.getMillis());
    }

}
