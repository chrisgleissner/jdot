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

package com.github.jdot.file.builder;

import com.github.jdot.file.JavaFile;
import com.google.common.base.Joiner;

public class TypeBuilder extends JavaFileBuilder<TypeBuilder, JavaFile> {

    private boolean abstractType;
    private boolean implementInterface;

    public TypeBuilder(JavaFile javaFile) {
        super(javaFile);
    }

    @Override
    public JavaFile build() {
        prefix.line("package " + type.getPackageName() + ";");
        prefix.add("public ");
        if (abstractType) {
            prefix.add("abstract ");
        }
        prefix.add("class " + type.getName());
        if (!type.getParameters().isEmpty()) {
            prefix.add("<");
            prefix.add(Joiner.on(", ").join(type.getParameters()));
            prefix.add(">");
        }
        if (useView) {
            if (type.getViewSuperclassName() != null) {
                prefix.add(" extends " + type.getViewSuperclassName());
            }
        } else {
            if (type.getDomainSuperclassName() != null) {
                prefix.add(" extends " + type.getDomainSuperclassName());
            }
        }
        if (implementInterface) {
            prefix.add(" implements " + originalType.getQualifiedName());
        }
        prefix.line(" {");
        postfix.line("}");
        return javaFile;
    }

    public TypeBuilder implementInterface() {
        implementInterface = true;
        return this;
    }

    public TypeBuilder makeAbstract() {
        abstractType = true;
        return this;
    }
}
