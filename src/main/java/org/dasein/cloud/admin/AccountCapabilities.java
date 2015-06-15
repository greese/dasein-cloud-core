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

package org.dasein.cloud.admin;

import java.util.Locale;

import javax.annotation.Nonnull;

import org.dasein.cloud.Capabilities;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.Requirement;

/**
 * Describes the capabilities for accounts and account hierarchies within a
 * cloud.
 * 
 * @author David R Young (david.young@centurylink.com)
 */
public interface AccountCapabilities extends Capabilities {

    /**
     * Indicates whether or not sub accounts can be created for this cloud using
     * a {@link AccountSupport#create(Account)} call.
     * 
     * @return true if sub accounts can be created, false if otherwise
     * @throws CloudException
     *             the cloud provider encountered an error while processing the
     *             request
     * @throws InternalException
     *             a Dasein Cloud error occurred while processing the request
     */
    public boolean canCreate() throws InternalException, CloudException;

    /**
     * Indicates whether or not the cloud allows the input of a user-defined
     * account identifier to be used during the account creation process.
     * {@link org.dasein.cloud.Requirement#REQUIRED} means that an account id
     * must be created by the caller and supplied;
     * {@link org.dasein.cloud.Requirement#OPTIONAL} means that it may or may
     * not be provided; {@link org.dasein.cloud.Requirement#NONE} means that no
     * account id may be provided.
     * 
     * @return the requirement associated with a supplied user-defined account
     *         identifier
     * @throws org.dasein.cloud.InternalException
     *             an error occurred within the Dasein Cloud implementation
     * @throws org.dasein.cloud.CloudException
     *             an error occurred with the cloud provide
     */
    public @Nonnull
    Requirement identifyAccountIdRequirement() throws InternalException, CloudException;

    /**
     * Indicates whether or not sub accounts can be deleted for this cloud using
     * a {@link AccountSupport#delete(String)} call.
     * 
     * @return true if sub accounts can be deleted, false if otherwise
     * @throws CloudException
     *             the cloud provider encountered an error while processing the
     *             request
     * @throws InternalException
     *             a Dasein Cloud error occurred while processing the request
     */
    public boolean canDelete() throws InternalException, CloudException;

    /**
     * Indicates whether or not details on an account can be updated for this
     * cloud using an {@link AccountSupport#update(Account)} call.
     * 
     * @return true if account details can be updated, false if otherwise
     * @throws InternalException
     *             an error occurred within the Dasein Cloud API implementation
     * @throws CloudException
     *             an error occurred within the cloud provider
     */
    public boolean canUpdate() throws InternalException, CloudException;

    /**
     * Indicates whether or not an account in the specified state can be the
     * target of an {@link AccountSupport#enable(String)} call.
     * 
     * @param fromState
     *            the state in which the theoretical account exists
     * @return true if an account can be enabled, false if otherwise
     * @throws InternalException
     *             an error occurred within the Dasein Cloud API implementation
     * @throws CloudException
     *             an error occurred within the cloud provider
     */
    public boolean canEnable(AccountState fromState) throws InternalException, CloudException;

    /**
     * Indicates whether or not an account in the specified state can be the
     * target of an {@link AccountSupport#suspend(String)} call.
     * 
     * @param fromState
     *            the state in which the theoretical account exists
     * @return true if an account can be suspended, false if otherwise
     * @throws InternalException
     *             an error occurred within the Dasein Cloud API implementation
     * @throws CloudException
     *             an error occurred within the cloud provider
     */
    public boolean canSuspend(AccountState fromState) throws InternalException, CloudException;

    /**
     * Assists UIs by providing the cloud-specific term for an account in the
     * cloud.
     * 
     * @param locale
     *            the locale for which the term should be translated
     * @return the provider-specific term for an account
     */
    public @Nonnull
    String getProviderTermForAccount(Locale locale);
}
