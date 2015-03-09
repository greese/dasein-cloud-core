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

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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

    static public class Value<T> {
        public String name;
        public T value;

        public Value(@Nonnull String name, @Nonnull T value) {
            this.name = name;
            this.value = value;
        }

        public byte[][] getKeypair() {
            return (byte[][])value;
        }

        public Float getFloat() {
            if( value instanceof Float ) {
                return (Float)value;
            }
            else if( value instanceof Number ) {
                return ((Number)value).floatValue();
            }
            else if( value instanceof String ) {
                return Float.parseFloat((String) value);
            }
            else throw new ClassCastException("Not a float: " + value);
        }

        public Integer getInt() {
            if( value instanceof Integer ) {
                return (Integer)value;
            }
            else if( value instanceof Number ) {
                return ((Number)value).intValue();
            }
            else if( value instanceof String ) {
                return Integer.parseInt((String) value);
            }
            else throw new ClassCastException("Not an integer: " + value);
        }

        public byte[] getPassword() {
            if( value instanceof String ) {
                try {
                    return ((String)value).getBytes("utf-8");
                }
                catch( UnsupportedEncodingException ignore ) {
                    return (byte[])value;
                }
            }
            return (byte[])value;
        }

        public String getText() {
            if( value instanceof String ) {
                return (String)value;
            }
            else {
                return value.toString();
            }
        }

        static public @Nonnull Value<?> parseValue(@Nonnull ContextRequirements.Field field, @Nonnull String ... fromStrings) throws UnsupportedEncodingException {
            switch( field.type ) {
                case KEYPAIR:
                    if( fromStrings.length != 2 ) {
                        throw new IndexOutOfBoundsException("Should have exactly 2 strings for a keypair value");
                    }
                    if( fromStrings[0] == null || fromStrings[1] == null ) {
                        throw new RuntimeException("Keypair values can not be null");
                    }
                    byte[][] bytes = new byte[2][];

                    bytes[0] = fromStrings[0].getBytes("utf-8");
                    bytes[1] = fromStrings[1].getBytes("utf-8");
                    return new Value<byte[][]>(field.name, bytes);
                case TEXT:
                case TOKEN:
                    return new Value<String>(field.name, fromStrings[0]);
                case INTEGER:
                    return new Value<Integer>(field.name, Integer.parseInt(fromStrings[0]));
                case FLOAT:
                    return new Value<Float>(field.name, Float.parseFloat(fromStrings[0]));
                case PASSWORD:
                    return new Value<byte[]>(field.name, fromStrings[0].getBytes("utf-8"));
                default:
                    throw new RuntimeException("Unsupported type: " + field.type);
            }
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
    static ProviderContext getContext(@Nonnull Cloud cloud, @Nonnull String accountNumber, @Nullable String regionId, @Nonnull Value<?> ... configurationValues) {
        ProviderContext ctx = new ProviderContext(cloud, accountNumber, regionId);
        Properties p = new Properties();

        ctx.configurationValues = new HashMap<String,Object>();
        for( Value<?> v : configurationValues ) {
            ctx.configurationValues.put(v.name, v.value);
            if( v.value instanceof String ) {
                p.setProperty(v.name, (String)v.value);
            }
        }
        //noinspection deprecation
        ctx.setCustomProperties(p);
        return ctx;
    }

    /**
     * @return a pseudo-random number using the context random number generator (not a secure random)
     */
    @SuppressWarnings("UnusedDeclaration")
    public static Random getRandom() {
        return random;
    }

    private String                  accountNumber;
    private Cloud                   cloud;
    private Map<String,Object>      configurationValues;
    private String                  effectiveAccountNumber;
    private String                  regionId;
    private RequestTrackingStrategy strategy;
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

    @SuppressWarnings("deprecation")
    void configureForDeprecatedConnect(@Nonnull CloudProvider p) {
        if( configurationValues == null ) {
            configurationValues = new HashMap<String, Object>();
            ContextRequirements r = p.getContextRequirements();

            ContextRequirements.Field access = r.getCompatAccessKeys();

            if( access != null ) {
                byte[] key = getAccessPublic();

                if( key != null ) {
                    configurationValues.put(access.name, new byte[][] { key, getAccessPrivate() });
                }
            }
            ContextRequirements.Field x509 = r.getCompatAccessX509();
            if( x509 != null ) {
                byte[] cert = getX509Cert();

                if( cert != null ) {
                    configurationValues.put(x509.name, new byte[][] { cert, getX509Key() });
                }
            }
            Properties props = getCustomProperties();

            for( ContextRequirements.Field field : r.getConfigurableValues() ) {
                if( (access == null || !access.name.equals(field.name)) && (x509 == null || !x509.name.equals(field.name)) ) {
                    if( props.containsKey(field.name) ) {
                        configurationValues.put(field.name, props.getProperty(field.name));
                    }
                }
            }
        }
    }

    /**
     * Connects to the cloud associated with this connection context. The result will be a connected implementation
     * of the {@link CloudProvider} abstract class specific to the cloud in question.
     * @return a connected cloud provider instance
     * @throws CloudException an error occurred with any handshake that might have been necessary to perform a connection (generally not needed)
     * @throws InternalException an error occurred loading the {@link org.dasein.cloud.CloudProvider} implementation
     */
    public @Nonnull CloudProvider connect() throws CloudException, InternalException {
        return connect(null);
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
    public @Nonnull CloudProvider connect(@Nullable CloudProvider computeProvider) throws CloudException, InternalException {
        try {
            ProviderContext computeContext = null;

            if( computeProvider != null ) {
                computeContext = computeProvider.getContext();
                if( computeContext == null ) {
                    throw new InternalException("The compute provider has not yet connected to the compute cloud");
                }
            }
            CloudProvider p = cloud.buildProvider();

            p.connect(this, computeProvider, cloud);
            if( computeContext != null ) {
                effectiveAccountNumber = computeContext.getAccountNumber();
            }
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
     * Creates a copy of this context with some parameters replaced by new values.
     *
     * @param havingRegionId the region to set into copied context.
     * @return a new provider context.
     * @throws InternalException an error occurred loading the {@link org.dasein.cloud.CloudProvider} implementation
     */
    public @Nonnull ProviderContext copy( @Nonnull String havingRegionId ) throws InternalException {
        try {
            CloudProvider provider = this.getCloud().buildProvider();
            List<ContextRequirements.Field> fields = provider.getContextRequirements().getConfigurableValues();
            List<Value<Object>> values = new ArrayList<Value<Object>>();
            for( ContextRequirements.Field f : fields ) {
                Object value = this.getConfigurationValue(f);
                if( value != null ) {
                    values.add(new Value<Object>(f.name, value));
                }
            }
            return this.getCloud().createContext(getAccountNumber(), havingRegionId, values.toArray(new Value[values.size()]));
        } catch( IllegalAccessException e ) {
            throw new InternalException(e);
        } catch( InstantiationException e ) {
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
        return getConfigurationValue(field.name);
    }

    /**
     * The effective account number under which this context operates. It is used for defining ownership of
     * storage assets so they align with compute assets while the {@link #getAccountNumber()} for this context
     * is used for interaction with the cloud. This value has meaning only in the scenario in which separate compute
     * and storage clouds are being "glued together" to create a single virtual cloud within Dasein Cloud. Because they
     * are separate clouds, they will have different account numbers used in connecting to them. But to make the
     * storage assets look like they are owned by the compute cloud, the effective account number of the storage
     * cloud {@link org.dasein.cloud.ProviderContext} is set to the real account number of the compute cloud.
     * Ownership of storage assets is then set to the effective account number even though in reality their ownership
     * in the target cloud is the account number.
     * @return the effective account number for this context
     */
    public @Nonnull String getEffectiveAccountNumber() {
        if( effectiveAccountNumber == null ) {
            return getAccountNumber();
        }
        return effectiveAccountNumber;
    }

    /**
     * @return the cloud's unique identified for the region in which this context is operating
     */
    public @Nullable String getRegionId() {
        return regionId;
    }

    /**
     * Sets a strategy for tracking client requests to Dasein. Management of the request ID occurs outside of Dasein so that
     * the client can determine the scope of exactly what it is tracking.
     * @param strategy an object that describes the way in which the ID should be used (sent as a header, logged to file etc).
     * @return the ProviderContext with the tracking strategy set
     */
    public @Nonnull ProviderContext withRequestTracking(@Nonnull RequestTrackingStrategy strategy){
        this.strategy = strategy;
        return this;
    }

    /**
     * @return the strategy object currently being used for tracking requests
     */
    public @Nullable RequestTrackingStrategy getRequestTrackingStrategy(){
        return this.strategy;
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

    @SuppressWarnings("deprecation")
    @Deprecated
    public void setCloud(@Nonnull CloudProvider provider) throws InternalException {
        String endpoint = getEndpoint();

        if( endpoint == null ) {
            throw new InternalException("The context was not properly configured");
        }
        cloud = Cloud.getInstance(endpoint);
        if( cloud == null ) {
            String pname = getProviderName();
            String cname = getCloudName();

            cloud = Cloud.register(pname == null ? provider.getProviderName() : pname, cname == null ? provider.getCloudName() : cname, endpoint, provider.getClass());
        }
    }

    /**
     * Sets the effective account number for the provider context for when this context represents a storage
     * cloud being merged into a compute cloud.
     * @param effectiveAccountNumber the account number of the compute cloud associated with this storage context
     * @deprecated use {@link Cloud#createContext(String, String, org.dasein.cloud.ProviderContext.Value...)} (will automatically set effective account number when connected)
     */
    @Deprecated
    void setEffectiveAccountNumber(@Nonnull String effectiveAccountNumber) {
        if( this.effectiveAccountNumber == null ) {
            this.effectiveAccountNumber = effectiveAccountNumber;
        }
        else {
            throw new RuntimeException("Cannot double-set the effective account number. Tried " + effectiveAccountNumber + ", was already " + this.effectiveAccountNumber);
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
