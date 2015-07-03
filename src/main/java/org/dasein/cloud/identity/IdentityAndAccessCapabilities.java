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

import org.dasein.cloud.Capabilities;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.Requirement;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;

/**
 * Describes the capabilities of the identity and access service within a cloud for a specific account.
 * Created by Stas Maksimov: 18/06/2015 11:52
 * @author Stas Maksimov
 * @since 2015.09
 * @version 2015.09
 */
public interface IdentityAndAccessCapabilities extends Capabilities {

    /**
     * @return true if the cloud API supports the management of access control through its IdM APIs
     * @throws CloudException an error occurred within the cloud provider determining access control support
     * @throws InternalException an error occurred within the Dasein Cloud implementation determining access control support
     */
    boolean supportsAccessControls() throws CloudException, InternalException;

    /**
     * @return true if the cloud API supports managing access to the cloud console (also false if the cloud has no console)
     * @throws CloudException an error occurred within the cloud provider determining console access support
     * @throws InternalException an error occurred within the Dasein Cloud implementation determining console access support
     */
    boolean supportsConsoleAccess() throws CloudException, InternalException;

    /**
     * @return true if the cloud API supports managing API access
     * @throws CloudException an error occurred within the cloud provider determining API access management support
     * @throws InternalException an error occurred within the Dasein Cloud implementation determining API access management support
     */
    boolean supportsAPIAccess() throws CloudException, InternalException;

    /**
     * Provides the cloud console URL where cloud user may sign-in to using their credentials.
     * @see IdentityAndAccessSupport#enableConsoleAccess(String, byte[])
     * @see IdentityAndAccessCapabilities#supportsConsoleAccess()
     * @return the URL of the cloud console
     * @throws CloudException an error occurred with the cloud provider in determining support
     * @throws InternalException a local error occurred while determining support
     */
    @Nullable String getConsoleUrl() throws CloudException, InternalException;

    /**
     * Provides the provider term for a user entity.
     * @param locale the locale into which the term should be translated
     * @return the provider term for the user entity, ideally translated for the specified locale
     */
    @Nonnull String getProviderTermForUser(@Nonnull Locale locale);

    /**
     * Provides the provider term for a group entity.
     * @param locale the locale into which the term should be translated
     * @return the provider term for the group entity, ideally translated for the specified locale
     */
    @Nonnull String getProviderTermForGroup(@Nonnull Locale locale);
}
