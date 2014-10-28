package org.dasein.cloud.util.requester.streamprocessors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dasein.cloud.util.requester.streamprocessors.StreamProcessor;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Vlad_Munthiu on 10/17/2014.
 */
public class JsonStreamToObjectProcessor<T> implements StreamProcessor<T> {
    public @Nullable T read(InputStream inputStream, Class<T> classType){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(inputStream, classType);
        } catch (Exception ex) {
            throw new RuntimeException("Error deserializing response input stream into dasein object", ex);
        }
    }

    public @Nullable String write(T object){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception ex) {
            throw new RuntimeException("Error serializing dasein object into string", ex);
        }
    }
}
