package org.dasein.cloud.util.requester.streamprocessors;

import org.dasein.cloud.util.requester.streamprocessors.StreamProcessor;

import javax.annotation.Nullable;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * Created by Vlad_Munthiu on 10/17/2014.
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
