/*
 * Copyright (C) 2014 Dell, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dasein.cloud.examples;

import org.dasein.cloud.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Demonstrates how to set up a connection to a cloud using system properties. You can obviously use other mechanisms
 * loading the configuration data.
 * <p>Created by George Reese: 3/1/14 4:38 PM</p>
 * @author George Reese
 * @version 2014.03 initial version (issue #123)
 * @since 2014.03
 */
public class CloudConnection201403 {
    static public void main(String ... args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, CloudException, InternalException, UnsupportedEncodingException {
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
        CloudProvider provider = ctx.connect();

        System.out.println("Configured provider: " + provider);
        // Now you can begin working with the provider
    }

}
