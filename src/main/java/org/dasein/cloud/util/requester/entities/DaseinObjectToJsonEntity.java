package org.dasein.cloud.util.requester.entities;

import org.apache.http.entity.AbstractHttpEntity;
import org.dasein.cloud.util.requester.streamprocessors.JsonStreamToObjectProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

 /**
 * @author Vlad Munthiu
 */
public class DaseinObjectToJsonEntity <T> extends DaseinEntity<T> {
    public DaseinObjectToJsonEntity(T daseinObject){
        super(daseinObject, new JsonStreamToObjectProcessor<T>());
        setContentType("application/json");
    }
}
