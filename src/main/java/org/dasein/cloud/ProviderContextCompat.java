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

package org.dasein.cloud;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Properties;

/**
 * Provides backwards compatibility with the pre-2014.03 way of configuring a Dasein Cloud implementation to talk
 * to a specific cloud. Backwards compatibility has two challenges. First, some drivers may not be updated to
 * expecting a client to use the new connection methods. Second, some clients may depend on the old methods. Thus,
 * you can end up with the following combinations:
 * <ol>
 *     <li>new client + new implementation</li>
 *     <li>new client + old implementation</li>
 *     <li>old client + new implementation</li>
 *     <li>old client + old implementation</li>
 * </ol>
 * <p>
 *     All of these scenarios have test cases to prove them out in dasein-cloud-core. The focus of this class
 *     is to handle the stuff clients should not be doing.
 * </p>
 * <p>Created by George Reese: 2/28/14 9:13 AM</p>
 * @author George Reese
 * @version 2014.03 initial version (issue #123)
 * @since 2014.03
 */
public abstract class ProviderContextCompat implements Serializable {
    private byte[]     accessPrivate;
    private byte[]     accessPublic;
    private String     cloudName;
    private Properties customProperties;
    private String     effectiveAccountNumber;
    private String     endpoint;
    private String     providerName;
    private String     storage;
    private String     storageAccountNumber;
    private Properties storageCustomProperties;
    private String     storageEndpoint;
    private byte[]     storagePrivate;
    private byte[]     storagePublic;
    private byte[]     storageX509Cert;
    private byte[]     storageX509Key;
    private byte[]     x509Cert;
    private byte[]     x509Key;

    /**
     * Clears out all keys being stored by this provider. The keys are overwritten with random data.
     * If these keys were set from instance variables in other objects, this method will result in
     * some peculiar side-effects.
     */
    public void clear() {
        ProviderContext.clear(accessPublic, accessPrivate, storagePublic, storagePrivate, x509Cert, x509Key, storageX509Cert, storageX509Key);
    }

    public void setEffectiveAccountNumber(String effectiveAccountNumber) {
        this.effectiveAccountNumber = effectiveAccountNumber;
    }

    public @Nullable String getEffectiveAccountNumber() {
        if( effectiveAccountNumber == null ) {
            return getAccountNumber();
        }
        return effectiveAccountNumber;
    }

    /************************************** HELPER METHODS ************************************/
    public abstract String getAccountNumber();

    public abstract Cloud getCloud();

    public abstract @Nullable Object getConfigurationValue(@Nonnull String field);

    public abstract String getRegionId();

    /******************************** DEPRECATED HELPER METHODS ********************************/

    /**
     * Sets the account number for this provider context.
     * @param accountNumber the account number associated with the provider account
     * @deprecated use {@link Cloud#createContext(String, String, org.dasein.cloud.ProviderContext.Value...)}
     */
    @Deprecated
    public abstract void setAccountNumber(@Nonnull String accountNumber);

    /**
     * Sets the region context for this provider context.
     * @param regionId the unique cloud ID for the region supporting this context
     * @deprecated use {@link Cloud#createContext(String, String, org.dasein.cloud.ProviderContext.Value...)}
     */
    @Deprecated
    public abstract void setRegionId(@Nullable String regionId);

    /******************************** DEPRECATED METHODS ********************************/

    /**
     * The private access key is the primary authentication password for web services calls.
     * In this AWS world, this is your access secret key.
     * @return the private access key for the cloud provider
     */
    public @Nullable byte[] getAccessPrivate() {
        if( accessPrivate != null ) {
            return accessPrivate;
        }
        Object val = getConfigurationValue(ContextRequirements.Field.ACCESS_KEYS);

        if( val == null ) {
            return null;
        }
        accessPrivate = ((byte[][])val)[1];
        return accessPrivate;
    }

    /**
     * The public access key is the primary authentication user ID for web services calls. In the
     * AWS world, this is your access public key.
     * @return the public access key for the cloud provider
     */
    public @Nullable byte[] getAccessPublic() {
        if( accessPublic != null ) {
            return accessPublic;
        }
        Object val = getConfigurationValue(ContextRequirements.Field.ACCESS_KEYS);

        if( val == null ) {
            return null;
        }
        accessPublic = ((byte[][])val)[0];
        return accessPublic;
    }

    /**
     * Provides the name of the cloud being accessed through this API.
     * For a multi-cloud provider or a multi-cloud implementation of Dasein Cloud, it is useful
     * to have the cloud name configurable.
     * @return the name of the underlying cloud
     * @deprecated use {@link #getCloud()}.{@link Cloud#getCloudName()}
     */
    @Deprecated
    public @Nullable String getCloudName() {
        if( cloudName != null ) {
            return cloudName;
        }
        Cloud cloud = getCloud();

        if( cloud == null ) {
            return null;
        }
        cloudName = cloud.getCloudName();
        return cloudName;
    }

    /**
     * Provides any properties specific to the underlying Dasein Cloud implementation that will
     * help it interact with thge underlying cloud.
     * @return any custom properties supporting cloud connectivity
     */
    public @Nullable Properties getCustomProperties() {
        if( customProperties == null ) {
            Cloud cloud = getCloud();
            if( cloud == null ) {
                return null;
            }
            Properties p = new Properties();

            try {
                for( ContextRequirements.Field f : cloud.buildProvider().getContextRequirements().getConfigurableValues() ) {
                    if( !f.type.equals(ContextRequirements.FieldType.KEYPAIR) ) {
                        Object val = getConfigurationValue(f.name);

                        if( val != null ) {
                            p.setProperty(f.name, val.toString());
                        }
                    }
                }
                customProperties = p;
            }
            catch( Exception ignore ) {
                return p;
            }
        }
        return customProperties;
    }

    /**
     * Provides the endpoint through which cloud API calls are routed. This is typically configured for
     * private clouds or cloud APIs that support more than one cloud.
     * @return the cloud API host
     * @deprecated use {@link #getCloud()}.{@link Cloud#getEndpoint()}
     */
    @Deprecated
    public @Nullable String getEndpoint() {
        if( endpoint != null ) {
            return endpoint;
        }
        Cloud cloud = getCloud();

        if( cloud == null ) {
            return null;
        }
        endpoint = cloud.getEndpoint();
        return endpoint;
    }

    /**
     * Provides the name of the organization providing cloud services. This value is configurable
     * for multi-provider APIs like the AWS/Eucalyptus APIs.
     * @return the name of the organization providing the underlying cloud
     * @deprecated use {@link #getCloud()}.{@link Cloud#getProviderName()}
     */
    @Deprecated
    public @Nullable String getProviderName() {
        if( providerName != null ) {
            return providerName;
        }
        Cloud cloud = getCloud();

        if( cloud == null ) {
            return null;
        }
        providerName = cloud.getProviderName();
        return providerName;
    }

    /**
     * @return the contents of the X509 authentication certificate
     * @deprecated use {@link Cloud#createContext(String, String, org.dasein.cloud.ProviderContext.Value...)}
     */
    @Deprecated
    public byte[] getX509Cert() {
        if( x509Cert != null ) {
            return x509Cert;
        }
        Object val = getConfigurationValue(ContextRequirements.Field.X509);

        if( val == null ) {
            return null;
        }
        x509Cert = ((byte[][])val)[0];
        return x509Cert;
    }

    /**
     * @return the contents of the X509 authentication key
     * @deprecated use {@link Cloud#createContext(String, String, org.dasein.cloud.ProviderContext.Value...)}
     */
    @Deprecated
    public byte[] getX509Key() {
        if( x509Key != null ) {
            return x509Key;
        }
        Object val = getConfigurationValue(ContextRequirements.Field.X509);

        if( val == null ) {
            return null;
        }
        x509Key = ((byte[][])val)[1];
        return x509Key;
    }

    /**
     * Establishes the access key values.
     * @param publicKey the public key part of the access key
     * @param privateKey the private key part of the access key
     * @deprecated use {@link Cloud#createContext(String, String, org.dasein.cloud.ProviderContext.Value...)}
     */
    @Deprecated
    public void setAccessKeys(@Nullable byte[] publicKey, @Nullable byte[] privateKey) {
        accessPublic = publicKey;
        accessPrivate = privateKey;
    }

    /**
     * Sets the private access key.
     * @param accessPrivate the proper private access key value.
     * @deprecated use {@link Cloud#createContext(String, String, org.dasein.cloud.ProviderContext.Value...)}
     */
    @Deprecated
    public void setAccessPrivate(@Nullable byte[] accessPrivate) {
        this.accessPrivate = accessPrivate;
    }

    /**
     * Sets the public access key.
     * @param accessPublic the proper public access key value
     * @deprecated use {@link Cloud#createContext(String, String, org.dasein.cloud.ProviderContext.Value...)}
     */
    @Deprecated
    public void setAccessPublic(@Nullable byte[] accessPublic) {
        this.accessPublic = accessPublic;
    }

    /**
     * Sets the name of the cloud supported by this context.
     * @param name the name of the underlying cloud
     * @deprecated use {@link Cloud#createContext(String, String, org.dasein.cloud.ProviderContext.Value...)}
     */
    @Deprecated
    public void setCloudName(@Nullable String name) {
        cloudName = name;
    }

    /**
     * Establishes any custom properties for use by the Dasein Cloud implementation.
     * @param properties custom properties for the Dasein Cloud implementation
     * @deprecated use {@link Cloud#createContext(String, String, org.dasein.cloud.ProviderContext.Value...)}
     */
    @Deprecated
    public void setCustomProperties(@Nonnull Properties properties) {
        customProperties = properties;
    }

    /**
     * Sets the cloud endpoint for the provider context.
     * @param endpoint the host to which cloud API calls are routed
     * @deprecated use {@link Cloud#createContext(String, String, org.dasein.cloud.ProviderContext.Value...)}
     */
    @Deprecated
    public void setEndpoint(@Nullable String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * Sets the name of the organization providing cloud services.
     * @param name the name of the organization providing cloud services
     * @deprecated use {@link Cloud#createContext(String, String, org.dasein.cloud.ProviderContext.Value...)}
     */
    @Deprecated
    public void setProviderName(@Nullable String name) {
        providerName = name;
    }

    /**
     * Some clouds use X509 certificates to authenticate API calls instead of user name/password or API keys. This
     * sets the X509 certificate for API calls made through this context.
     * @param x509Cert the X509 certificate to be used in API calls
     * @deprecated use {@link Cloud#createContext(String, String, org.dasein.cloud.ProviderContext.Value...)}
     */
    @Deprecated
    public void setX509Cert(byte[] x509Cert) {
        this.x509Cert = x509Cert;
    }

    /**
     * Some clouds use X509 certificates to authenticate API calls instead of user name/password or API keys. This
     * sets the X509 key (the private part) for API calls made through this context.
     * @param x509Key the X509 key to be used in API calls
     * @deprecated use {@link Cloud#createContext(String, String, org.dasein.cloud.ProviderContext.Value...)}
     */
    @Deprecated
    public void setX509Key(byte[] x509Key) {
        this.x509Key = x509Key;
    }









    /**
     * The storage private key is the private key used for cloud storage access. The AWS world has
     * no direct analog to this value, but it is used to represent the AWS private key.
     * @return the private key for cloud storage services
     */
    public @Nullable byte[] getStoragePrivate() {
        return storagePrivate;
    }

    /**
     * The storage public key is the public key used for cloud storage access. The AWS world has
     * no direct analog to this value, but it is used to represent the AWS certificate.
     * @return the public key for storafe
     */
    public @Nullable byte[] getStoragePublic() {
        return storagePublic;
    }

    /**
     * Sets the storage keypair for this provider context.
     * @param publicKey the storage key public key
     * @param privateKey the storage key private key
     */
    public void setStorageKeys(@Nullable byte[] publicKey, @Nullable byte[] privateKey) {
        storagePublic = publicKey;
        storagePrivate = privateKey;
    }

    /**
     * Sets the storage private key.
     * @param storagePrivate the storage private key
     */
    public void setStoragePrivate(@Nullable byte[] storagePrivate) {
        this.storagePrivate = storagePrivate;
    }

    /**
     * Sets the storage public key value.
     * @param storagePublic the storage public key
     */
    public void setStoragePublic(@Nullable byte[] storagePublic) {
        this.storagePublic = storagePublic;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getStorageAccountNumber() {
        return storageAccountNumber;
    }

    public void setStorageAccountNumber(String storageAccountNumber) {
        this.storageAccountNumber = storageAccountNumber;
    }

    public byte[] getStorageX509Cert() {
        return storageX509Cert;
    }

    public void setStorageX509Cert(byte[] storageX509Cert) {
        this.storageX509Cert = storageX509Cert;
    }

    public byte[] getStorageX509Key() {
        return storageX509Key;
    }

    public void setStorageX509Key(byte[] storageX509Key) {
        this.storageX509Key = storageX509Key;
    }

    public void setStorageEndpoint(String storageEndpoint) {
        this.storageEndpoint = storageEndpoint;
    }

    public String getStorageEndpoint() {
        return storageEndpoint;
    }

    public void setStorageCustomProperties(Properties storageCustomProperties) {
        this.storageCustomProperties = storageCustomProperties;
    }

    public Properties getStorageCustomProperties() {
        return storageCustomProperties;
    }
}
