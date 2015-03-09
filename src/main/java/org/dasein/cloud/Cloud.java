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

package org.dasein.cloud;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * Represents a core cloud independent of account-specific connectivity information. You can pre-register cloud objects
 * at system start up time and use the {@link #getInstance(String)} method to fetch the clouds during the system
 * life cycle. Clouds are uniquely identified by their API endpoints.
 * <p>Created by George Reese: 2/27/14 8:40 PM</p>
 * @author George Reese
 * @version 2014.03 initial version (issue #123)
 * @since 2014.03
 */
public class Cloud {
    static private HashMap<String,Cloud> clouds = new HashMap<String, Cloud>();

    /**
     * Fetches a cloud object using its {@link #getEndpoint()} as its identifying value. If no cloud has been registered
     * under that endpoint, this method will return <code>null</code>.
     * @param endpoint the API endpoint of the desired cloud
     * @return any cloud matching the specified endpoint
     */
    static public @Nullable Cloud getInstance(@Nonnull String endpoint) {
        return clouds.get(endpoint);
    }

    /**
     * Registers a cloud with the specified state information under the named endpoint. If a cloud is already registered
     * under that endpoint, this method will simply return the original as if it were a call to {@link #getInstance(String)}.
     * @param providerName the name of the organization providing the cloud service (e.g. Amazon)
     * @param cloudName the name of the cloud service being provided (e.g. AWS)
     * @param endpoint the bootstrap endpoint for any API requests
     * @param providerClass the Dasein Cloud implementation of the {@link org.dasein.cloud.CloudProvider} abstract class supporting connectivity to this cloud
     * @return a cloud matching the specified state information with that cloud cached for future reference
     */
    static public @Nonnull Cloud register(@Nonnull String providerName, @Nonnull String cloudName, @Nonnull String endpoint, @Nonnull Class<? extends CloudProvider> providerClass) {
        if( clouds.containsKey(endpoint) ) {
            return clouds.get(endpoint);
        }
        Cloud cloud = new Cloud();

        cloud.endpoint = endpoint;
        cloud.cloudName = cloudName;
        cloud.providerClass = providerClass;
        cloud.providerName = providerName;
        clouds.put(endpoint, cloud);
        return cloud;
    }

    private String cloudName;
    private String endpoint;
    private Class<? extends CloudProvider> providerClass;
    private String providerName;

    private Cloud() { }

    /**
     * Constructs an instance of the Dasein Cloud {@link org.dasein.cloud.CloudProvider} abstract class supporting
     * connectivity to this cloud. The resulting provider is NOT connected to the cloud. This method exists
     * solely to support meta-data queries independent of any particular connection.
     * @return an unconnected Dasein Cloud provider supporting this cloud
     * @throws IllegalAccessException there is an error in the Dasein Cloud {@link org.dasein.cloud.CloudProvider} implementation for this cloud that prevents it from loading (likely private constructor)
     * @throws InstantiationException there is an error in the Dasein Cloud {@link org.dasein.cloud.CloudProvider} implementation for this cloud that prevents it from loading (likely an error in the constructor)
     */
    public @Nonnull CloudProvider buildProvider() throws IllegalAccessException, InstantiationException {
        return providerClass.newInstance();
    }

    /**
     * Creates a context object based on values specified in a Java {@link Properties} file.
     * Creates a context against this cloud with the specified configuration information. This context can then be used
     * to connect to the cloud for API requests
     * @param forAccountNumber the account number of the account with the cloud provider
     * @param inRegionId the region to which a connection should be made
     * @param configurationProperties a properties object containing values for the required fields for connecting to this cloud
     * @return a configured context ready to be connected to the cloud via {@link ProviderContext#connect(CloudProvider)}
     * @throws InternalException an error occurred loading the {@link CloudProvider} implementation for this cloud or UTF-8 is not supported
     */
    public @Nonnull ProviderContext createContext(@Nonnull String forAccountNumber, @Nonnull String inRegionId, @Nonnull Properties configurationProperties) throws InternalException {
        try {
            List<ContextRequirements.Field> fields = buildProvider().getContextRequirements().getConfigurableValues();
            ArrayList<ProviderContext.Value> values = new ArrayList<ProviderContext.Value>();

            for( ContextRequirements.Field f : fields ) {
                if( f.type.equals(ContextRequirements.FieldType.KEYPAIR) ) {
                    String[] parts = new String[2];

                    parts[0] = configurationProperties.getProperty(f.name + "_public." + forAccountNumber);
                    parts[1] = configurationProperties.getProperty(f.name + "_private." + forAccountNumber);
                    if( parts[0] != null && parts[1] != null ) {
                        values.add(ProviderContext.Value.parseValue(f, parts));
                    }
                    else if( f.required ) {
                        throw new InternalException("Unable to find configuration value " + f.name + " in the properties");
                    }
                }
                else {
                    String value = configurationProperties.getProperty(f.name + "." + forAccountNumber);

                    if( value != null ) {
                        values.add(ProviderContext.Value.parseValue(f, value));
                    }
                    else if( f.required ) {
                        throw new InternalException("Unable to find configuration value " + f.name + " in the properties");
                    }
                }
            }
            return createContext(forAccountNumber, inRegionId, values.toArray(new ProviderContext.Value<?>[values.size()]));
        }
        catch( UnsupportedEncodingException e ) {
            throw new InternalException(e);
        }
        catch( InstantiationException e ) {
            throw new InternalException(e);
        }
        catch( IllegalAccessException e ) {
            throw new InternalException(e);
        }
    }

    /**
     * Creates a context against this cloud with the specified configuration information. This context can then be used
     * to connect to the cloud for API requests
     * @param forAccountNumber the account number of the account with the cloud provider
     * @param inRegionId the region to which a connection should be made
     * @param values any configuration values to provide (to see what values are expected, query {@link org.dasein.cloud.CloudProvider#getContextRequirements()})
     * @return a configured context ready to be connected to the cloud via {@link ProviderContext#connect(CloudProvider)}
     */
    public @Nonnull ProviderContext createContext(@Nonnull String forAccountNumber, @Nonnull String inRegionId, ProviderContext.Value<?> ... values) {
        return ProviderContext.getContext(this, forAccountNumber, inRegionId, values);
    }

    @Override
    public boolean equals(Object other) {
        return other != null && (other == this || getClass().getName().equals(other.getClass().getName()) && endpoint.equals(((Cloud) other).endpoint));
    }

    /**
     * @return the name of the cloud
     */
    public @Nonnull String getCloudName() {
        return cloudName;
    }

    /**
     * @return the endpoint to which API connections are initially made
     */
    public @Nonnull String getEndpoint() {
        return endpoint;
    }

    /**
     * @return the class for the {@link org.dasein.cloud.CloudProvider} implementation for this cloud
     */
    public @Nonnull Class<? extends CloudProvider> getProviderClass() {
        return providerClass;
    }

    /**
     * @return the name of the organization providing the cloud services behind this cloud
     */
    public @Nonnull String getProviderName() {
        return providerName;
    }

    @Override
    public int hashCode() {
        return endpoint.hashCode();
    }

    @Override
    public @Nonnull String toString() {
        return endpoint;
    }
}
