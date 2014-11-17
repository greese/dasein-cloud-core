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
