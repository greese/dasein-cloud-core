package org.dasein.cloud.util.requester.streamprocessors;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;

 /**
 * @author Vlad Munthiu
 */
public interface StreamProcessor<T> {
    @Nullable T read(InputStream inputStream, Class<T> classType) throws IOException;
    @Nullable String write(T object);
}
