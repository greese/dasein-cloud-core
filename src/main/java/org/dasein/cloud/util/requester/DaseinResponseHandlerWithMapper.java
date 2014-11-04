package org.dasein.cloud.util.requester;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.dasein.cloud.util.requester.streamprocessors.StreamProcessor;

import java.io.IOException;

 /**
 * @author Vlad Munthiu
 */
public class DaseinResponseHandlerWithMapper<T, V> implements ResponseHandler<V> {

    private Class<T> classType;
    private StreamProcessor<T> processor;
    private DriverToCoreMapper<T,V> mapper;

    public DaseinResponseHandlerWithMapper(StreamProcessor processor, DriverToCoreMapper<T, V> mapper, Class<T> classType) {
        this.processor = processor;
        this.mapper = mapper;
        this.classType = classType;
    }

    @Override
    public V handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
        if( httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK
                && httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_CREATED
                && httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_ACCEPTED ) {
            throw new ClientProtocolException();
        }
        else {
            T responseObject = (T) processor.read(httpResponse.getEntity().getContent(), classType);
            if (responseObject == null)
                return null;

            return mapper.mapFrom(responseObject);
        }
    }
}
