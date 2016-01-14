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
import com.github.jdot.type.State;
import org.apache.commons.lang.StringUtils;

/**
 * Builder for a static inner builder class which is placed inside an immutable domain class.
 * 
 * @author Christian Gleissner
 */
public class BuilderBuilder extends JavaFileBuilder<BuilderBuilder, JavaFile> {

    private boolean forOriginalType;

    public BuilderBuilder(JavaFile javaFile) {
        super(javaFile);
    }

    @Override
    public JavaFile build() {
        String typeName = type.getQualifiedName();
        if (forOriginalType) {
            typeName = originalType.getQualifiedName();
        }
        statics.line("    public final static class Builder {");
        statics.line("        private " + typeName + " template;");

        statics.line("        public Builder() {");
        statics.line("            this.template = new " + typeName + "();");
        statics.line("        }");

        // Clone-based constructor to modify existing instance
        statics.line("        public Builder(" + typeName + " o) {");
        statics.line("            try {");
        statics.line("                this.template = (" + typeName + ") o.clone();");
        statics.line("            } catch (Exception e) {");
        statics.line("                throw new RuntimeException(\"Cloning failed: \" + o, e);");
        statics.line("            }");
        statics.line("        }");

        for (State state : type.getStates()) {
            statics.nl();
            statics.line("        public Builder with" + StringUtils.capitalize(state.getName()) + "("
                    + state.getType() + " " + state.getName() + ") {");
            statics.line("            template." + state.getName() + " = " + state.getName() + ";");
            statics.line("            return this;");
            statics.line("        }");
        }
        statics.nl();
        statics.line("        public " + typeName + " build() {");
        statics.line("            return this.template;");
        statics.line("        }");
        statics.line("    }");
        statics.nl();
        return javaFile;
    }

    public BuilderBuilder forOriginalType() {
        forOriginalType = true;
        return this;
    }
}
