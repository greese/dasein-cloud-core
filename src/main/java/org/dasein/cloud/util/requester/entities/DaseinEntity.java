package org.dasein.cloud.util.requester.entities;

import org.apache.http.entity.AbstractHttpEntity;
import org.dasein.cloud.util.requester.streamprocessors.StreamProcessor;
import org.dasein.cloud.util.requester.streamprocessors.XmlStreamToObjectProcessor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Vlad_Munthiu on 10/21/2014.
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
