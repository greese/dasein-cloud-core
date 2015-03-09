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

package org.dasein.cloud.util.requester.streamprocessors;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * Created by Vlad_Munthiu on 11/14/2014.
 */
public class StreamToJSONObjectProcessor implements StreamProcessor<JSONObject> {
    @Nullable
    @Override
    public JSONObject read(InputStream inputStream, Class<JSONObject> classType) throws IOException {
        try {
            StringWriter stringWriter = new StringWriter();
            IOUtils.copy(inputStream, stringWriter);
            return new JSONObject(stringWriter.toString());
        }
        catch (JSONException ex){
            return null;
        }
    }

    @Nullable
    @Override
    public String write(JSONObject object) {
        return object.toString();
    }
}
