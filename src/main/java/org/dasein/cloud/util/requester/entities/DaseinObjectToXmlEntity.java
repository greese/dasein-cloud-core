package org.dasein.cloud.util.requester.entities;

import org.apache.http.entity.AbstractHttpEntity;
import org.dasein.cloud.util.requester.streamprocessors.XmlStreamToObjectProcessor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

 /**
 * @author Vlad Munthiu
 */
public class DaseinObjectToXmlEntity<T> extends DaseinEntity<T> {
    public DaseinObjectToXmlEntity(T daseinObject){
        super(daseinObject, new XmlStreamToObjectProcessor<T>());
        setContentType("application/xml");
    }
}
