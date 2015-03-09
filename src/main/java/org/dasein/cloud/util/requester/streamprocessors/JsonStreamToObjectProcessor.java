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

package org.dasein.cloud.util.requester.streamprocessors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dasein.cloud.util.requester.streamprocessors.StreamProcessor;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;

 /**
 * @author Vlad Munthiu
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
