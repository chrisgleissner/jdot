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

package com.github.jdot.file;

import java.io.IOException;
import java.io.Writer;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;
import javax.tools.Diagnostic.Kind;

import com.github.jdot.file.builder.JavaFileBuilder;
import com.github.jdot.type.Type;
import com.github.jdot.util.SourceBuilder;

/**
 * Abstract superclass for Java source code file. Gets built by {@link JavaFileBuilder}s.
 * 
 * @author Christian Gleissner
 */
public abstract class JavaFile {

    protected final SourceBuilder fieldBuilder = new SourceBuilder();
    protected final SourceBuilder methodBuilder = new SourceBuilder();
    protected Type originalType;
    protected final SourceBuilder postfixBuilder = new SourceBuilder();
    protected final SourceBuilder prefixBuilder = new SourceBuilder();
    protected final SourceBuilder staticBuilder = new SourceBuilder();
    protected Type type;

    public SourceBuilder getFieldBuilder() {
        return fieldBuilder;
    }

    public SourceBuilder getMethodBuilder() {
        return methodBuilder;
    }

    public Type getOriginalType() {
        return originalType;
    }

    public SourceBuilder getPostfixBuilder() {
        return postfixBuilder;
    }

    public SourceBuilder getPrefixBuilder() {
        return prefixBuilder;
    }

    public SourceBuilder getStaticBuilder() {
        return staticBuilder;
    }

    public Type getType() {
        return type;
    }

    public void write(ProcessingEnvironment processingEnv) {
        String filename = prepareWrite();
        Writer writer = null;
        try {
            JavaFileObject jfo = processingEnv.getFiler().createSourceFile(filename);
            writer = jfo.openWriter();
            String content = getContent();
            writer.append(content);
        } catch (Exception e) {
            processingEnv.getMessager().printMessage(Kind.ERROR, "Could not write to " + filename);
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                    processingEnv.getMessager().printMessage(Kind.NOTE, "Created " + filename);
                }
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Kind.ERROR, "Could not close " + filename);
                e.printStackTrace();
            }
        }
    }

    private String getContent() {
        return prefixBuilder.toString() + staticBuilder.toString() + fieldBuilder.toString() + methodBuilder.toString()
                + postfixBuilder.toString();
    }

    /**
     * Prepares the content of this file by appending to the various {@link SourceBuilder} fields of this class.
     * 
     * @return name of file to be written
     */
    protected abstract String prepareWrite();
}
