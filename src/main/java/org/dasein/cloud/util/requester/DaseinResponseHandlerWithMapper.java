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
public class DaseinResponseHandlerWithMapper<T, V> implements ResponseHandler<V> {

    private Class<T> classType;
    private StreamProcessor<T> processor;
    private DriverToCoreMapper<T,V> mapper;

    public DaseinResponseHandlerWithMapper(StreamProcessor<T> processor, DriverToCoreMapper<T, V> mapper, Class<T> classType) {
        this.processor = processor;
        this.mapper = mapper;
        this.classType = classType;
    }

    @Override
    public V handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
        if( httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK
                && httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_NO_CONTENT
                && httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_CREATED
                && httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_ACCEPTED ) {
            throw new CloudResponseException(CloudErrorType.GENERAL, httpResponse.getStatusLine().getStatusCode(),
                    httpResponse.getStatusLine().getReasonPhrase(), EntityUtils.toString(httpResponse.getEntity()));
        }
        else {
            if(httpResponse.getEntity() == null)
                return null;

            T responseObject = processor.read(httpResponse.getEntity().getContent(), classType);
            if (responseObject == null)
                return null;

            return mapper.mapFrom(responseObject);
        }
    }
 }
