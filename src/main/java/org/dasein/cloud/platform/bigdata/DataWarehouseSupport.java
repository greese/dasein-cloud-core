/**
 * Copyright (C) 2014 Dell, Inc.
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

package org.dasein.cloud.platform.bigdata;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.Requirement;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Implements support for bigdata/data warehousing services like Amazon's Redshift.
 * <p>Created by George Reese: 2/7/14 12:06 PM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 initial version (issue #100)
 */
public interface DataWarehouseSupport {
    /**
     * Provisions a new data cluster in the current region of this cloud using the provided provisioning options. This
     * method should block until an ID is available and {@link #getCluster(String)} and {@link #listClusters()} will
     * return the new data cluster when queried.
     * @param options the state information to be used in creating the data cluster
     * @return the unique ID of the newly created data cluster
     * @throws CloudException an error occurred in the cloud provider while provisioning the data cluster
     * @throws InternalException an error occurred in the Dasein Cloud implementation while preparing or processing the call
     */
    public @Nonnull String createCluster(@Nonnull DataClusterCreateOptions options) throws CloudException, InternalException;

    public @Nullable DataCluster getCluster(@Nonnull String clusterId) throws CloudException, InternalException;

    public @Nonnull Requirement isDataCenterConstrained() throws CloudException, InternalException;

    public @Nonnull Iterable<DataCluster> listClusters() throws CloudException, InternalException;

    // TODO: list products

    public void removeCluster(@Nonnull String clusterId) throws CloudException, InternalException;

    public boolean supportsEncryptionAtRest() throws CloudException, InternalException;
}
