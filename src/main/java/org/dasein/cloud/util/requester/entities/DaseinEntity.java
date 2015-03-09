/**
 * Copyright (C) 2009-2015 Dell, Inc.
 * See annotations for authorship information
 *
 * ====================================================================
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
 * ====================================================================
 */

package org.dasein.cloud.util.requester.entities;

import org.apache.http.entity.AbstractHttpEntity;
import org.dasein.cloud.util.requester.streamprocessors.StreamProcessor;
import org.dasein.cloud.util.requester.streamprocessors.XmlStreamToObjectProcessor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

 /**
 * @author Vlad Munthiu
 */
public abstract class DaseinEntity<T> extends AbstractHttpEntity implements Cloneable {
    protected final byte[] content;
    protected final int length;

    protected DaseinEntity(T object, StreamProcessor<T> processor){
        final String stringToWrite = processor.write(object);
        content = stringToWrite.getBytes();
        length = content.length;
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }

    @Override
    public long getContentLength() {
        return length;
    }

    @Override
    public InputStream getContent() throws IOException, IllegalStateException {
        return new ByteArrayInputStream(content, 0, length);
    }

    @Override
    public void writeTo(OutputStream outstream) throws IOException {
        if(outstream == null){
            throw new IllegalArgumentException("Output stream may not be null");
        }

        outstream.write(content);
        outstream.flush();
    }

    @Override
    public boolean isStreaming() {
        return false;
    }
}
