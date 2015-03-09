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

/**
 * Represents the current status of a specific kind of resource. This is used by listXXXStatus() methods in different
 * support objects so you can quickly fetch the status of a set of cloud resources without having to fetch all
 * associated data. Fetching the status is a good way to check for large-scale state changes which may need to be
 * more frequent than detailed state changes.
 * <p>Created by George Reese: 11/16/12 12:11 PM</p>
 * @author George Reese
 * @version 2013.01 initial version (Issue #4)
 * @since 2013.01
 */
public class ResourceStatus {
    private String providerResourceId;
    private Object resourceStatus;

    public ResourceStatus(@Nonnull String id, @Nonnull Object status) {
        providerResourceId = id;
        resourceStatus = status;
    }

    /**
     * @return the cloud provider's unique identifier for this resource
     */
    public @Nonnull String getProviderResourceId() {
        return providerResourceId;
    }

    /**
     * @return the current status of the target resource
     */
    public @Nonnull Object getResourceStatus() {
        return resourceStatus;
    }

    @Override
    public @Nonnull String toString() {
        return (providerResourceId + " [" + resourceStatus + "]");
    }
}
