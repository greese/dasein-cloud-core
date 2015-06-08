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

package org.dasein.cloud.platform;

import org.dasein.cloud.Capabilities;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;

import javax.annotation.Nonnull;
import java.util.Locale;

/**
 * Describes the capabilities of a CDN Service within a cloud for a specific account.
 * @author Drew Lyall
 * @version 2014.08
 * @since 2014.08
 */
public interface CDNCapabilities extends Capabilities{
    /**
     * Indicates whether the cloud supports the creation of a new CDN distribution
     * @return true if creation is allowed
     * @throws CloudException an error occurred in the cloud identifying this capability
     * @throws InternalException an error occurred within the Dasein Cloud implementation identifying this capability
     */
    public boolean canCreateCDN() throws CloudException, InternalException;

    /**
     * Indicates whether the cloud supports the deletion of an existing CDN
     * @return true if deletion allowed
     * @throws CloudException an error occurred in the cloud identifying this capability
     * @throws InternalException an error occurred within the Dasein Cloud implementation identifying this capability
     */
    public boolean canDeleteCDN() throws CloudException, InternalException;

    /**
     * Indicates whether an existing CDN can be modified
     * @return true if modification is allowed
     * @throws CloudException an error occurred in the cloud identifying this capability
     * @throws InternalException an error occurred within the Dasein Cloud implementation identifying this capability
     */
    public boolean canModifyCDN() throws CloudException, InternalException;

    /**
     * The term the provider uses to describe distributions.
     * @param locale the language in which the term should be presented
     * @return the provider term for distributions
     */
    public @Nonnull String getProviderTermForDistribution(@Nonnull Locale locale);
}
