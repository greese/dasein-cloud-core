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

package org.dasein.cloud.identity;

import java.util.Collection;
import java.util.Locale;

import org.dasein.cloud.AccessControlledService;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.Requirement;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Methods for managing SSH keys for remote access to a virtual machine.
 * @author George Reese (george.reese@imaginary.com)
 * @since 2010.11
 * @version 2010.11
 */
public interface ShellKeySupport extends AccessControlledService {
    static public final ServiceAction ANY            = new ServiceAction("SSH:ANY");

    static public final ServiceAction CREATE_KEYPAIR = new ServiceAction("SSH:CREATE_KEYPAIR");
    static public final ServiceAction GET_KEYPAIR    = new ServiceAction("SSH:GET_KEYPAIR");
    static public final ServiceAction LIST_KEYPAIR   = new ServiceAction("SSH:LIST_KEYPAIR");
    static public final ServiceAction REMOVE_KEYPAIR = new ServiceAction("SSH:REMOVE_KEYPAIR");
    
    /**
     * Creates an SSH keypair having the specified name.
     * @param name the name of the SSH keypair
     * @return a new SSH keypair
     * @throws InternalException an error occurred within Dasein Cloud while processing the request
     * @throws CloudException an error occurred in the cloud provider executing the keypair creation
     */
    public @Nonnull SSHKeypair createKeypair(@Nonnull String name) throws InternalException, CloudException;

    /**
     * Deletes the specified keypair having the specified name.
     * @param providerId the provider ID of the keypair to be deleted
     * @throws InternalException an error occurred within Dasein Cloud while performing the deletion
     * @throws CloudException an error occurred with the cloud provider while performing the deletion
     */
    public void deleteKeypair(@Nonnull String providerId) throws InternalException, CloudException;

    /**
     * Fetches the fingerprint of the specified key so you can validate it against the one you have.
     * @param providerId the provider ID of the keypair
     * @return the fingerprint for the specified keypair
     * @throws InternalException an error occurred within Dasein Cloud while fetching the fingerprint
     * @throws CloudException an error occurred with the cloud provider while fetching the fingerprint
     */
    public @Nullable String getFingerprint(@Nonnull String providerId) throws InternalException, CloudException;

    /**
     * Indicates to what degree key importing vs. creation is supported. If importing is not supported, then that
     * means all keys must be created through the cloud provider via {@link #createKeypair(String)}. If importing is
     * required, it means all key creation must be done by importing your keys through {@link #importKeypair(String, String)}.
     * If optional, then you can either import or create keys. When you have the choice, it is always recommended that
     * you import keys.
     * @return the requirement state of importing key pairs
     * @throws CloudException an error occurred with the cloud provider in determining support
     * @throws InternalException a local error occurred while determining support
     * @deprecated use {@link org.dasein.cloud.identity.ShellKeyCapabilities#identifyKeyImportRequirement()}}
     */
    @Deprecated
    public Requirement getKeyImportSupport() throws CloudException, InternalException;
    
    /**
     * Fetches the specified keypair from the cloud provider. The cloud provider may or may not know
     * about the public key at this time, so be prepared for a null {@link SSHKeypair#getPublicKey()}.
     * @param providerId the unique ID of the target keypair
     * @return the keypair matching the specified provider ID or <code>null</code> if no such key exists
     * @throws InternalException an error occurred in the Dasein Cloud implementation while fetching the keypair
     * @throws CloudException an error occurred with the cloud provider while fetching the keypair
     */
    public @Nullable SSHKeypair getKeypair(@Nonnull String providerId) throws InternalException, CloudException;
    
    /**
     * Provides the provider term for an SSH keypair.
     * @param locale the locale into which the term should be translated
     * @return the provider term for the SSH keypair, ideally translated for the specified locale
     * @deprecated use {@link ShellKeyCapabilities#getProviderTermForKeypair(java.util.Locale)}
     */
    @Deprecated
    public @Nonnull String getProviderTermForKeypair(@Nonnull Locale locale);

    /**
     * Provides access to meta-data about shell key capabilities in the current region of this cloud.
     * @return a description of the features supported by this region of this cloud
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     */
    public @Nonnull ShellKeyCapabilities getCapabilities() throws CloudException, InternalException;

    /**
     * Imports the specified public key into your store of keys with the cloud provider under the specified name.
     * @param name the name of the key to be imported
     * @param publicKey the MD5 public key fingerprint as specified in section 4 of RFC4716
     * @return the unique ID of the key as it is stored with the cloud provider
     * @throws InternalException a local error occurred assembling the request
     * @throws CloudException an error occurred with the cloud provider while importing the keys
     */
    public @Nonnull SSHKeypair importKeypair(@Nonnull String name, @Nonnull String publicKey) throws InternalException, CloudException;

    /**
     * @return true if the cloud provider supports shell keypairs in the current region and the current account can use them
     * @throws CloudException an error occurred with the cloud provider while determining subscription status
     * @throws InternalException an error occurred within the Dasein Cloud implementation determining subscription status
     */
    public boolean isSubscribed() throws CloudException, InternalException;

    /**
     * @return a list of all available SSH keypairs (private key is null)
     * @throws InternalException an error occurred within Dasein Cloud listing the keypairs
     * @throws CloudException an error occurred with the cloud provider listing the keyspairs
     */
    public @Nonnull Iterable<SSHKeypair> list() throws InternalException, CloudException;
}
