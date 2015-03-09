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

package org.dasein.cloud.util.requester.fluent;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.util.requester.DaseinRequestExecutor;
import org.dasein.cloud.util.requester.DriverToCoreMapper;
import org.json.JSONObject;
import org.w3c.dom.Document;

 /**
 * @author Vlad Munthiu
 */
public interface CompositeRequester extends Requester<String> {
    <T> Requester<T> withXmlProcessor(Class<T> classType);
    <T, V> Requester<V> withXmlProcessor(DriverToCoreMapper<T, V> mapper, Class<T> classType);
    <T> Requester<T> withJsonProcessor(Class<T> classType);
    <T, V> Requester<V> withJsonProcessor(DriverToCoreMapper<T, V> mapper, Class<T> classType);
    <T> DaseinRequestExecutor<Document> withDocumentProcessor();
    <T> DaseinRequestExecutor<JSONObject> withJSONObjectProcessor();
    String execute() throws CloudException;
}
