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

import static com.googlecode.jdot.util.NameUtil.toBuilderMethod;
import static com.googlecode.jdot.util.NameUtil.toGetter;
import static com.googlecode.jdot.util.NameUtil.toSetter;

import java.util.HashMap;
import java.util.Map;

import com.googlecode.jdot.file.DomainMapperClass;
import com.googlecode.jdot.mapper.DomainMapper;
import com.googlecode.jdot.type.State;

public class DomainMapperBuilder extends JavaFileBuilder<DomainMapperBuilder, DomainMapperClass> {

    public static final String NAME_SUFFIX = "Mapper";

    public DomainMapperBuilder(DomainMapperClass domainMapperClass) {
        super(domainMapperClass);
    }

    @Override
    public DomainMapperClass build() {
        String domainTypeName = javaFile.getDomainType().getQualifiedName();
        String viewTypeName = javaFile.getViewType().getQualifiedName();
        prefix.line("package " + javaFile.getMapperType().getPackageName() + ";");
        prefix.nl();
        prefix.line("public class " + javaFile.getMapperType().getName() + " implements "
                + DomainMapper.class.getName() + "<" + domainTypeName + ", " + viewTypeName + "> {");
        prefix.nl();

        // TypeMapper fields
        Map<String, String> typeMapperMap = javaFile.getDomainType().getDomainTypeToTypeMapperNameMap();
        Map<String, String> typeMapperFieldMap = new HashMap<String, String>();
        if (!typeMapperMap.isEmpty()) {
            int i = 0;
            fields.line("    // TypeMappers");
            for (Map.Entry<String, String> entry : typeMapperMap.entrySet()) {
                String typeMapperField = "mapper" + i++;
                typeMapperFieldMap.put(entry.getKey(), typeMapperField);
                fields.line("    private final " + entry.getValue() + " " + typeMapperField + " = new "
                        + entry.getValue() + "();");
            }
            fields.nl();
        }

        // Methods
        methods.line("    public " + viewTypeName + " toView(" + domainTypeName + " domain) {");
        methods.line("        " + viewTypeName + " view = new " + viewTypeName + "();");
        for (State state : javaFile.getDomainType().getStates()) {
            String typeMapperField = typeMapperFieldMap.get(state.getType());
            if (typeMapperField != null) {
                methods.line("        view." + toSetter(state) + "(" + typeMapperField + ".toView(domain."
                        + toGetter(state) + "()));");
            } else {
                methods.line("        view." + toSetter(state) + "(domain." + toGetter(state) + "());");
            }
        }
        methods.line("        return view;");
        methods.line("    }");
        methods.nl();

        methods.line("    public " + domainTypeName + " toDomain(" + viewTypeName + " view) {");
        methods.line("        " + domainTypeName + ".Builder builder = new " + domainTypeName + ".Builder();");
        for (State state : javaFile.getDomainType().getStates()) {
            String typeMapperField = typeMapperFieldMap.get(state.getType());
            if (typeMapperField != null) {
                methods.line("        builder." + toBuilderMethod(state) + "(" + typeMapperField + ".toDomain(view."
                        + toGetter(state) + "()));");
            } else {
                methods.line("        builder." + toBuilderMethod(state) + "(view." + toGetter(state) + "());");
            }
        }
        methods.line("        return builder.build();");
        methods.line("    }");
        methods.nl();

        postfix.add("}");
        return javaFile;
    }
}
