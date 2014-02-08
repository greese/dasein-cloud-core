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

    /**
     * Fetches the state of the cluster identified with the specified cluster ID from the cloud. If no such cloud exists,
     * no value will be returned.
     * @param clusterId the unique ID of the data cluster being fetched
     * @return an object with the current data cluster state or <code>null</code> if no such data cluster exists
     * @throws CloudException an error occurred in the cloud provider while fetching the target data cluster
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public @Nullable DataCluster getCluster(@Nonnull String clusterId) throws CloudException, InternalException;

    /**
     * Fetches a specific product offering for the provisioning of data clusters. If no such product exists,
     * a <code>null</code> value is returned.
     * @param productId the product ID of the desired product
     * @return any matching product or <code>null</code> if no such product exists
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public @Nullable DataClusterProduct getClusterProduct(@Nonnull String productId) throws CloudException, InternalException;

    /**
     * Indicates whether or not data clusters may be (or are required to be) constrained to a specific data center.
     * @return the requirement for data clusters to be constrained to a data center
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public @Nonnull Requirement getDataCenterConstraintRequirement() throws CloudException, InternalException;

    /**
     * Lists all known data clusters for the current account in the current region of the cloud.
     * @return all known data clusters for this region
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public @Nonnull Iterable<DataCluster> listClusters() throws CloudException, InternalException;

    /**
     * Lists all products through which data clusters may be provisioned.
     * @return the list of available data cluster products
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public @Nonnull Iterable<DataClusterProduct> listClusterProducts() throws CloudException, InternalException;

    /**
     * Removes the specified data cluster from the cloud.
     * @param clusterId the data cluster to be removed
     * @param snapshotFirst snapshot the database before removing the cluster
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public void removeCluster(@Nonnull String clusterId, boolean snapshotFirst) throws CloudException, InternalException;

    /**
     * Indicates whether or not this cloud supports encryption of your database at rest.
     * @return true if encryption at rest is supported
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public boolean supportsEncryptionAtRest() throws CloudException, InternalException;
}
