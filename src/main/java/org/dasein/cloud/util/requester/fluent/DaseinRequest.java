package org.dasein.cloud.util.requester.fluent;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.util.requester.*;
import org.dasein.cloud.util.requester.streamprocessors.JsonStreamToObjectProcessor;
import org.dasein.cloud.util.requester.streamprocessors.StreamToDocumentProcessor;
import org.dasein.cloud.util.requester.streamprocessors.StreamToStringProcessor;
import org.dasein.cloud.util.requester.streamprocessors.XmlStreamToObjectProcessor;
import org.w3c.dom.Document;

/**
 * Created by Vlad_Munthiu on 10/20/2014.
 */

public class DaseinRequest implements CompositeRequester {

    private CloudProvider provider;
    private HttpClientBuilder httpClientBuilder;
    private HttpUriRequest httpUriRequestBuilder;

    public DaseinRequest(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequestBuilder){
        this.provider = provider;
        this.httpClientBuilder = httpClientBuilder;
        this.httpUriRequestBuilder = httpUriRequestBuilder;
    }

    @Override
    public <T> Requester<T> withXmlProcessor(Class<T> classType) {
        return new DaseinRequestExecutor<T>(this.provider, this.httpClientBuilder, this.httpUriRequestBuilder,
                new DaseinResponseHandler<T>(new XmlStreamToObjectProcessor(), classType));
    }

    @Override
    public <T, V> Requester<V> withXmlProcessor(DriverToCoreMapper<T, V> mapper, Class<T> classType) {
        return new DaseinRequestExecutor<V>(this.provider, this.httpClientBuilder, this.httpUriRequestBuilder,
                new DaseinResponseHandlerWithMapper<T, V>(new XmlStreamToObjectProcessor(), mapper, classType));
    }

    @Override
    public <T> Requester<T> withJsonProcessor(Class<T> classType) {
        return new DaseinRequestExecutor<T>(this.provider, this.httpClientBuilder, this.httpUriRequestBuilder,
                new DaseinResponseHandler<T>(new JsonStreamToObjectProcessor(), classType));
    }

    @Override
    public <T, V> Requester<V> withJsonProcessor(DriverToCoreMapper<T, V> mapper, Class<T> classType) {
        return new DaseinRequestExecutor<V>(this.provider, this.httpClientBuilder, this.httpUriRequestBuilder,
                new DaseinResponseHandlerWithMapper<T, V>(new JsonStreamToObjectProcessor(), mapper, classType));
    }

    @Override
    public <T> DaseinRequestExecutor<Document> withDocumentProcessor() {
        return new DaseinRequestExecutor<Document>(this.provider, this.httpClientBuilder, this.httpUriRequestBuilder,
                new DaseinResponseHandler<Document>(new StreamToDocumentProcessor(), Document.class));
    }

    @Override
    public String execute() throws CloudException {
        return new DaseinRequestExecutor<String>(this.provider, this.httpClientBuilder, this.httpUriRequestBuilder,
                new DaseinResponseHandler<String>(new StreamToStringProcessor(), String.class)).execute();
    }
}
