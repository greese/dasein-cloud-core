package org.dasein.cloud.util.requester;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.dasein.cloud.CloudErrorType;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.util.requester.streamprocessors.StreamProcessor;

import java.io.IOException;

 /**
 * @author Vlad Munthiu
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
            throw new CloudResponseException(CloudErrorType.GENERAL, httpResponse.getStatusLine().getStatusCode(),
                    httpResponse.getStatusLine().getReasonPhrase(), EntityUtils.toString(httpResponse.getEntity()));
        }
        else {
            return (T) processor.read(httpResponse.getEntity().getContent(), classType);
        }
    }
 }
