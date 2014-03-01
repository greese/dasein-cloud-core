/**
 * Copyright (C) 2009-2014 Dell, Inc.
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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * <p>
 * The contextual information necessary for making calls to a cloud provider. Each provider requires
 * a provider context in order to connect to the target cloud account and perform operations within 
 * the cloud. This context includes the account number, operational region, and any number of
 * authentication keys. 
 * </p>
 * @author George Reese
 * @version 2014.03 refactored for discoverability of configuration values and better model enforcement (issue #123)
 * @since 2010.08
 */
public class ProviderContext extends ProviderContextCompat implements Serializable {
    static private final Random random = new Random();

    static public class Value {
        public String name;
        public Object value;

        public Value(@Nonnull String name, @Nonnull Object value) {
            this.name = name;
            this.value = value;
        }
    }

    /**
     * Helper method for clearing out credentials with random data.
     * @param keys a list of credentials to fill with random data
     */
    static public void clear(byte[] ... keys) {
        if( keys != null ) {
            for( byte[] key : keys ) {
                if( key != null ) {
                    random.nextBytes(key);
                }
            }
        }
    }

    /**
     * Constructs a provider context from configuration values provided by a client. The preferred mechanism to access
     * this constructor is via {@link Cloud#createContext(String, String, org.dasein.cloud.ProviderContext.Value...)}.
     * @param cloud the cloud configuration object to build against
     * @param accountNumber the account number within the cloud to use for the connection context
     * @param regionId the ID of the region in which the client will be operating
     * @param configurationValues one or more configuration values that match the configuration requirements for the cloud
     * @return an instance of a provider context ready to connect to the target cloud using the specified configuration values
     */
    static ProviderContext getContext(@Nonnull Cloud cloud, @Nonnull String accountNumber, @Nullable String regionId, @Nonnull Value ... configurationValues) {
        ProviderContext ctx = new ProviderContext(cloud, accountNumber, regionId);

        ctx.configurationValues = new HashMap<String,Object>();
        for( Value v : configurationValues ) {
            ctx.configurationValues.put(v.name, v.value);
        }
        return ctx;
    }

    /**
     * @return a pseudo-random number using the context random number generator (not a secure random)
     */
    public static Random getRandom() {
        return random;
    }

    private String             accountNumber;
    private Cloud              cloud;
    private Map<String,Object> configurationValues;
    private String             regionId;

    /**
     * Constructs a provider context from the provided values
     * @param cloud the cloud configuration object to build against
     * @param accountNumber the account number within the cloud to use for the connection context
     * @param regionId the ID of the region in which the client will be operating
     */
    private ProviderContext(@Nonnull Cloud cloud, @Nonnull String accountNumber, @Nullable String regionId) {
        this.cloud = cloud;
        this.accountNumber = accountNumber;
        this.regionId = regionId;
    }

    /**
     * @return the account number that identifies the account to the cloud provider independent of context
     */
    public @Nonnull String getAccountNumber() {


        return accountNumber;
    }

    /**
     * Connects to the cloud associated with this connection context. The result will be a connected implementation
     * of the {@link CloudProvider} abstract class specific to the cloud in question.
     * @return a connected cloud provider instance
     * @throws CloudException an error occurred with any handshake that might have been necessary to perform a connection (generally not needed)
     * @throws InternalException an error occurred loading the {@link org.dasein.cloud.CloudProvider} implementation
     */
    public @Nonnull CloudProvider connect() throws CloudException, InternalException {
        try {
            CloudProvider p = cloud.buildProvider();

            p.connect(this);
            return p;
        }
        catch( InstantiationException e ) {
            throw new InternalException(e);
        }
        catch( IllegalAccessException e ) {
            throw new InternalException(e);
        }
    }

    /**
     * Connects to the cloud associated with this connection context. The result will be a connected implementation
     * of the {@link CloudProvider} abstract class specific to the cloud in question. This variation exists specifically
     * for clients trying to use a compute provider together with a storage provider to create an apparently unified cloud.
     * The approach is to first connect to the compute provider, and then connect to the storage provider using this method.
     * @param computeProvider the compute provider with which the connected storage provider will be associated
     * @return a connected cloud provider instance
     * @throws CloudException an error occurred with any handshake that might have been necessary to perform a connection (generally not needed)
     * @throws InternalException an error occurred loading the {@link org.dasein.cloud.CloudProvider} implementation
     */
    public @Nonnull CloudProvider connect(@Nonnull CloudProvider computeProvider) throws CloudException, InternalException {
        try {
            CloudProvider p = cloud.buildProvider();

            p.connect(this, computeProvider);
            return p;
        }
        catch( InstantiationException e ) {
            throw new InternalException(e);
        }
        catch( IllegalAccessException e ) {
            throw new InternalException(e);
        }
    }

    /**
     * @return the cloud for which this context is configured
     */
    public @Nonnull Cloud getCloud() {
        return cloud;
    }

    /**
     * Looks through the values provided for configuring this context and returns the named value, if set
     * @param field the name of the field whose configuration value is sought
     * @return the value matching the named field or <code>null</code> if no value is set
     */
    public @Nullable Object getConfigurationValue(@Nonnull String field) {
        return configurationValues.get(field);
    }

    /**
     * Looks through the values provided for configuring this context and returns the matching value, if set
     * @param field the field from the context requirements whose configuration value is sought
     * @return the value matching the specified field or <code>null</code> if no value is set
     */
    public @Nullable Object getConfigurationValue(@Nonnull ContextRequirements.Field field) {
        return configurationValues.get(field.name);
    }

    /**
     * @return the cloud's unique identified for the region in which this context is operating
     */
    public @Nullable String getRegionId() {
        return regionId;
    }

    /******************************** DEPRECATED METHODS ********************************/

    /**
     * Constructs a new, empty provider context for managing the provider context information.
     * @deprecated use {@link ProviderContext#getContext(Cloud, String, String, Value...)}
     */
    @Deprecated
    public ProviderContext() { }

    /**
     * Constructs a new provider context for the specified account number in the specified region.
     * @param accountNumber the account number for the account in the cloud
     * @param inRegionId the region to be referenced by this provider context
     * @deprecated use {@link ProviderContext#getContext(Cloud, String, String, Value...)}
     */
    @Deprecated
    public ProviderContext(@Nonnull String accountNumber, @Nonnull String inRegionId) {
        this.accountNumber = accountNumber;
        regionId = inRegionId;
    }

    @Override
    @Deprecated
    public void setAccountNumber(@Nonnull String accountNumber) {
        if( this.accountNumber == null ) {
            this.accountNumber = accountNumber;
        }
        else {
            throw new RuntimeException("Cannot double-set the account number. Tried " + accountNumber + ", was already " + this.accountNumber);
        }
    }

    @Override
    @Deprecated
    public void setRegionId(@Nullable String regionId) {
        if( this.regionId == null ) {
            this.regionId = regionId;
        }
        else {
            throw new RuntimeException("Cannot double-set the region ID. Tried " + regionId + ", was already " + this.regionId);
        }
    }
}
