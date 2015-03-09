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

package org.dasein.cloud.examples;

import org.dasein.cloud.*;

import javax.annotation.Nonnull;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

/**
 * Loads a properly configured Dasein Cloud {@link org.dasein.cloud.CloudProvider}. This class looks for the following
 * system properties:
 * <ul>
 *     <li>DSN_PROVIDER_CLASS</li>
 *     <li>DSN_ENDPOINT</li>
 *     <li>DSN_REGION</li>
 *     <li>DSN_ACCOUNT</li>
 *     <li>DSN_CLOUD_NAME</li>
 *     <li>DSN_CLOUD_PROVIDER</li>
 * </ul>
 * <p>
 *     In addition, it looks for fields needed for authenticating with the target provider (as specified in your
 *     provider class). If you don't know, this loader will first print out the expected fields so you can set
 *     them.
 * </p>
 * The core required values are DSN_PROVIDER_CLASS, DSN_ENDPOINT, DSN_PROVIDER_REGION, and DSN_ACCOUNT, but you also
 * need to set the provider-specific values.
 * <p>Created by George Reese: 10/3/12 12:18 PM</p>
 * @author George Reese
 * @version 2012.09 initial version
 * @version 2014.03 updated for changes to the connection process (issue #123)
 * @since 2012.09
 */
public class ProviderLoader {
    private CloudProvider configuredProvider;

    public ProviderLoader() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedEncodingException, CloudException, InternalException {
        configure();
    }

    public @Nonnull CloudProvider getConfiguredProvider() {
        return configuredProvider;
    }

    private void configure() throws ClassNotFoundException, IllegalAccessException, InstantiationException, UnsupportedEncodingException, CloudException, InternalException {
        // First, read the basic configuration data from system properties
        String cname = System.getProperty("DSN_PROVIDER_CLASS");
        String endpoint = System.getProperty("DSN_ENDPOINT");
        String regionId = System.getProperty("DSN_REGION");
        String cloudName = System.getProperty("DSN_CLOUD_NAME", "Unkown");
        String providerName = System.getProperty("DSN_PROVIDER_NAME", "Unknown");
        String account = System.getProperty("DSN_ACCOUNT");

        // Use that information to register the cloud
        @SuppressWarnings("unchecked") Cloud cloud = Cloud.register(providerName, cloudName, endpoint, (Class<? extends CloudProvider>)Class.forName(cname));

        // Find what additional fields are necessary to connect to the cloud
        ContextRequirements requirements = cloud.buildProvider().getContextRequirements();
        List<ContextRequirements.Field> fields = requirements.getConfigurableValues();

        // Load the values for the required fields from the system properties
        ProviderContext.Value[] values = new ProviderContext.Value[fields.size()];
        int i = 0;

        for(ContextRequirements.Field f : fields ) {
            System.out.print("Loading '" + f.name + "' from ");
            if( f.type.equals(ContextRequirements.FieldType.KEYPAIR) ) {
                System.out.println("'DSN_" + f.name + "_SHARED' and 'DSN_" + f.name + "_SECRET'");
                String shared = System.getProperty("DSN_" + f.name + "_SHARED");
                String secret = System.getProperty("DSN_" + f.name + "_SECRET");

                values[i] = ProviderContext.Value.parseValue(f, shared, secret);
            }
            else {
                System.out.println("'DSN_" + f.name + "'");
                String value = System.getProperty("DSN_" + f.name);

                values[i] = ProviderContext.Value.parseValue(f, value);
            }
            i++;
        }

        ProviderContext ctx = cloud.createContext(account, regionId, values);
        configuredProvider = ctx.connect();
    }
}
