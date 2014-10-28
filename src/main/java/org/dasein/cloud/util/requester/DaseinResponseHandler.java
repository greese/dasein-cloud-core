package org.dasein.cloud.util.requester;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.dasein.cloud.util.requester.streamprocessors.StreamProcessor;

import java.io.IOException;

/**
 * Created by Vlad_Munthiu on 10/20/2014.
 */
public class DaseinResponseHandler<T> implements ResponseHandler<T> {

    private Class<T> classType;
    private StreamProcessor<T> processor;

    public DaseinResponseHandler(StreamProcessor processor, Class<T> classType){
        this.processor = processor;
        this.classType = classType;
    }

    @Override
    public T handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
        if( httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK
                && httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_CREATED
                && httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_ACCEPTED ) {
            throw new ClientProtocolException();
        }
        else {
            return (T) processor.read(httpResponse.getEntity().getContent(), classType);
        }
    }
}
