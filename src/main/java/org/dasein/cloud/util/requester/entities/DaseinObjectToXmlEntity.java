package org.dasein.cloud.util.requester.entities;

import org.apache.http.entity.AbstractHttpEntity;
import org.dasein.cloud.util.requester.streamprocessors.XmlStreamToObjectProcessor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Vlad_Munthiu on 10/21/2014.
 */
public class DaseinObjectToXmlEntity<T> extends DaseinEntity<T> {
    public DaseinObjectToXmlEntity(T daseinObject){
        super(daseinObject, new XmlStreamToObjectProcessor<T>());
        setContentType("application/xml");
    }
}
