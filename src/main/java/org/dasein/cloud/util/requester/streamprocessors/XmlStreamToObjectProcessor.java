/**
 * Copyright (C) 2009-2014 Dell, Inc.
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

import org.dasein.cloud.util.requester.streamprocessors.StreamProcessor;

import javax.annotation.Nullable;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.StringWriter;

 /**
 * @author Vlad Munthiu
 */
public class XmlStreamToObjectProcessor<T> implements StreamProcessor<T> {
    public @Nullable T read(InputStream inputStream, Class<T> classType){
        try {
            JAXBContext context = JAXBContext.newInstance(classType);
            Unmarshaller u = context.createUnmarshaller();
            return (T)u.unmarshal(inputStream);
        } catch (Exception ex) {
            throw new RuntimeException("Error deserializing response input stream into dasein object", ex);
        }
    }

    public @Nullable String write(T object) {
        try {
            StringWriter stringWriter = new StringWriter();
            JAXBContext jc = JAXBContext.newInstance(object.getClass());
            Marshaller m = jc.createMarshaller();
            m.marshal(object, stringWriter);

            return stringWriter.toString();
        }
        catch (Exception ex){
            throw new RuntimeException("Error serializing dasein object into string", ex);
        }
    }
}
