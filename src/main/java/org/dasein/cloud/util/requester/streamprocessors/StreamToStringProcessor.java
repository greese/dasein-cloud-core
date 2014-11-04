package org.dasein.cloud.util.requester.streamprocessors;

import org.apache.commons.io.IOUtils;
import org.dasein.cloud.util.requester.streamprocessors.StreamProcessor;

import javax.annotation.Nullable;
import java.io.*;

 /**
 * @author Vlad Munthiu
 */
public class StreamToStringProcessor implements StreamProcessor<String> {
    @Nullable
    @Override
    public String read(InputStream inputStream, Class<String> classType) throws IOException {
        StringWriter stringWriter = new StringWriter();
        IOUtils.copy(inputStream, stringWriter);
        return stringWriter.toString();
    }

    @Nullable
    @Override
    public String write(String object) {
        return object;
    }
}
