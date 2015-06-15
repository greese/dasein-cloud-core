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

package org.dasein.cloud.dc;

import org.dasein.cloud.Capabilities;

import java.util.Locale;

/**
 * Describes the capabilities of a region within a cloud for a specific account.
 * User: daniellemayne
 * Date: 04/07/2014
 * Time: 16:21
 */
public interface DataCenterCapabilities extends Capabilities {

    /**
     * Provides the cloud-specific term for a data center (e.g. "availability zone").
     * @param locale the locale into which the term should be translated
     * @return the term for a data center
     */
    public String getProviderTermForDataCenter(Locale locale);

    /**
     * Provides the cloud-specific term for a region.
     * @param locale the locale into which the term should be translated
     * @return the term for a region
     */
    public String getProviderTermForRegion(Locale locale);

    /**
     * Inficates whether the underlying cloud supports affinity groups
     * @return {@code true} indicating support for affinity groups
     */
    public boolean supportsAffinityGroups();

    /**
     * Specifies whether the given cloud supports the concept of resource pools
     * @return {@code true} indicating support for resource pools
     */
    public boolean supportsResourcePools();

    /**
     * Specifies whether the given cloud supports the concept of storage pools
     * @return {@code true} indicating support for storage pools
     */
    public boolean supportsStoragePools();

    /**
     * Specifies whether the given cloud supports the concept of folders
     * @return {@code true} indicating support for folders
     */
    public boolean supportsFolders();
}
