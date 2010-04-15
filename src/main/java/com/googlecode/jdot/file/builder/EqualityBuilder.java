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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.googlecode.jdot.annotation.Id;
import com.googlecode.jdot.file.JavaFile;
import com.googlecode.jdot.type.Field;
import com.googlecode.jdot.type.Property;
import com.googlecode.jdot.type.State;

public class EqualityBuilder extends JavaFileBuilder<EqualityBuilder, JavaFile> {

    public EqualityBuilder(JavaFile javaFile) {
        super(javaFile);
    }

    @Override
    public JavaFile build() {
        Collection<State> ids = getIds();
        String typeName = javaFile.getType().getName();

        // ===========================================================
        // Equals
        // ===========================================================
        addJavaDoc(ids);
        methods.line("    @Override");
        methods.line("    public boolean equals(Object o) {");
        methods.line("        if (this == o)");
        methods.line("            return true;");
        methods.line("        if (o == null)");
        methods.line("            return false;");
        methods.line("        if (getClass() != o.getClass())");
        methods.line("            return false;");
        methods.line("        " + typeName + " other = (" + typeName + ") o;");
        for (State id : ids) {
            addEqualsFor(id.getName(), super.getStateType(id));
        }
        methods.line("        return true;");
        methods.line("    }");
        methods.nl();

        // ===========================================================
        // HashCode
        // ===========================================================
        addJavaDoc(ids);
        methods.line("    @Override");
        methods.line("    public int hashCode() {");
        methods.line("        final int prime = 31;");
        methods.line("        int result = prime;");
        for (State id : ids) {
            addHashCodeFor(id.getName(), super.getStateType(id));
        }
        methods.line("        return result;");
        methods.line("    }");
        methods.nl();
        return javaFile;
    }

    private void addEqualsFor(String name, String type) {
        // Primitive types
        if ("char".equals(type) || "int".equals(type) || "long".equals(type) || "boolean".equals(type)) {
            methods.line("        if (" + name + " != other." + name + ")");
            methods.line("            return false;");
        } else if ("float".equals(type)) {
            methods.line("        if (Float.floatToIntBits(" + name + ") != Float.floatToIntBits(other." + name + "))");
            methods.line("            return false;");
        } else if ("double".equals(type)) {
            methods.line("        if (Double.doubleToLongBits(" + name + ") != Double.doubleToLongBits(other." + name
                    + "))");
            methods.line("            return false;");
        }
        // TODO gleissc Verify whether this array type identifier is correct
        else if (type.endsWith("[]")) {
            methods.line("        if (!java.util.Arrays.equals(" + name + ", other." + name + "))");
            methods.line("            return false;");
        }
        // Complex types
        else {
            methods.line("        if (" + name + " == null) {");
            methods.line("            if (other." + name + " != null)");
            methods.line("                return false;");
            methods.line("        } else if (!" + name + ".equals(other." + name + "))");
            methods.line("            return false;");
        }
    }

    private void addHashCodeFor(String name, String type) {
        // Primitive types
        if ("char".equals(type)) {
            methods.line("        result = prime * result + " + name + ";");
        } else if ("boolean".equals(type)) {
            methods.line("        result = prime * result + (" + name + " ? 1231 : 1237);");
        } else if ("int".equals(type)) {
            methods.line("        result = prime * result + " + name + ";");
        } else if ("long".equals(type)) {
            methods.line("        prime * result + (int) (" + name + " ^ (" + name + " >>> 32));");
        } else if ("float".equals(type)) {
            methods.line("            result = prime * result + Float.floatToIntBits(" + name + ");");
        } else if ("double".equals(type)) {
            methods.line("        {");
            methods.line("            long temp = Double.doubleToLongBits(" + name + ");");
            methods.line("            result = prime * result + (int) (temp ^ (temp >>> 32));");
            methods.line("        }");
        }
        // TODO gleissc Verify whether this array type identifier is correct
        else if (type.endsWith("[]")) {
            methods.line("        result = prime * result + java.util.Arrays.hashCode(" + name + ");");
        }
        // Complex types
        else {
            methods.line("        result = prime * result + ((" + name + " == null) ? 0 : " + name + ".hashCode());");
        }
    }

    private void addJavaDoc(Collection<State> ids) {
        methods.line("    /**");
        methods.add("     * Identifying fields: ");
        boolean first = true;
        for (State id : ids) {
            if (first) {
                first = false;
            } else {
                methods.add(", ");
            }
            methods.add(id.getName());
        }
        methods.nl();
        methods.line("    */");
    }

    private Collection<State> getIds() {
        List<State> identifiers = new ArrayList<State>();
        if (javaFile.getType().isInterface()) {
            for (Property property : javaFile.getType().getProperties()) {
                if (property.hasAnnotation(Id.class)) {
                    identifiers.add(property);
                }
            }
        } else {
            for (Field field : javaFile.getType().getFields()) {
                if (field.hasAnnotation(Id.class)) {
                    identifiers.add(field);
                }
            }
        }
        return identifiers;
    }
}
