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

package com.googlecode.jdot.file.builder;

import com.googlecode.jdot.file.JavaFile;
import com.googlecode.jdot.type.State;
import com.googlecode.jdot.type.Type;
import com.googlecode.jdot.util.SourceBuilder;

/**
 * Abstract superclass for all builders of {@link JavaFile}s.
 * 
 * @author Christian Gleissner
 */
public abstract class JavaFileBuilder<BUILDER extends JavaFileBuilder<?, ?>, JAVA_FILE extends JavaFile> {

    protected final SourceBuilder fields;
    protected final JAVA_FILE javaFile;
    protected final SourceBuilder methods;
    protected final Type originalType;
    protected final SourceBuilder postfix;
    protected final SourceBuilder prefix;
    protected final SourceBuilder statics;
    protected final Type type;
    protected boolean useView;

    public JavaFileBuilder(JAVA_FILE javaFile) {
        this.javaFile = javaFile;
        type = javaFile.getType();
        originalType = javaFile.getOriginalType();
        prefix = javaFile.getPrefixBuilder();
        postfix = javaFile.getPostfixBuilder();
        fields = javaFile.getFieldBuilder();
        methods = javaFile.getMethodBuilder();
        statics = javaFile.getStaticBuilder();
    }

    public abstract JAVA_FILE build();

    @SuppressWarnings("unchecked")
    public BUILDER useView() {
        useView = true;
        return (BUILDER) this;
    }

    /**
     * Returns the domain or view type for the specified state, depending on how this builder was configured via the
     * {@link #useViewTypes} method.
     * 
     * @param state
     * @return
     */
    protected String getStateType(State state) {
        String stateType = state.getType();
        Type type = state.getOwner();
        if (useView) {
            stateType = type.getViewType(stateType);
        }
        return stateType;
    }

}
