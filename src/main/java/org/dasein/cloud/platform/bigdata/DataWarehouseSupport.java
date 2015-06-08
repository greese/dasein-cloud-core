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

package org.dasein.cloud.platform.bigdata;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.Requirement;
import org.dasein.cloud.Tag;
import org.dasein.cloud.network.Firewall;
import org.dasein.cloud.network.FirewallReference;
import org.dasein.cloud.storage.CloudStorageLogging;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * Implements support for bigdata/data warehousing services like Amazon's Redshift.
 * <p>Created by George Reese: 2/7/14 12:06 PM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 initial version (issue #100)
 */
public interface DataWarehouseSupport {
    /**
     * Adds the specified account number to the list of accounts with which this snapshot is shared.
     * @param snapshotId the unique ID of the snapshot to be shared
     * @param accountNumber the account number with which the snapshot will be shared
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     * @throws OperationNotSupportedException the cloud does not support sharing snapshots with other accounts
     */
    public void addSnapshotShare(@Nonnull String snapshotId, @Nonnull String accountNumber) throws CloudException, InternalException;

    /**
     * Authorizes access to data clusters associated with a specified data cluster firewall by the compute firewalls
     * ({@link Firewall}) referenced by the firewall references.
     * @param dataClusterFirewallId the data cluster firewall to be altered
     * @param firewalls one or more references to compute firewalls to be authorized
     * @throws CloudException an error occurred in the cloud provider while performing the operation
     * @throws InternalException an error occurred in the Dasein Cloud implementation while preparing or processing the call
     */
    public void authorizeComputeFirewalls(@Nonnull String dataClusterFirewallId, @Nonnull FirewallReference ... firewalls) throws CloudException, InternalException;

    /**
     * Authorizes access to data clusters associated with the specified data cluster firewall by IP addresses that match
     * the specified CIDR notation list.
     * @param dataClusterFirewallId the data cluster firewall to be altered
     * @param cidrs one or more IP addresses in CIDR notation
     * @throws CloudException an error occurred in the cloud provider while performing the operation
     * @throws InternalException an error occurred in the Dasein Cloud implementation while preparing or processing the call
     */
    public void authorizeIPs(@Nonnull String dataClusterFirewallId, @Nonnull String ... cidrs) throws CloudException, InternalException;

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
     * Creates a firewall supporting data clusters in the target cloud if the target cloud supports data cluster firewalls.
     * @param name the name of the new firewall
     * @param description a description of the new firewall
     * @return the unique ID for the newly created firewall
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public @Nonnull String createClusterFirewall(@Nonnull String name, @Nonnull String description) throws CloudException, InternalException;

    /**
     * Creates a new parameter group that defines parameters to be associated with data clusters.
     * @param family the family with which the parameter group is associated (see {@link DataClusterVersion#getParameterFamily()})
     * @param name the name of the parameter group
     * @param description a description of the parameter group
     * @param initialParameters the initial parameter values to associate with the parameter group
     * @return the unique ID for the newly constructed parameter group
     * @throws CloudException an error occurred in the cloud provider while provisioning the parameter group
     * @throws InternalException an error occurred in the Dasein Cloud implementation while preparing or processing the call
     */
    public @Nonnull String createClusterParameterGroup(@Nonnull String family, @Nonnull String name, @Nonnull String description, @Nonnull Map<String,Object> initialParameters) throws CloudException, InternalException;

    /**
     * Enables the creation of manual snapshots of a cluster.
     * @param clusterId the unique ID of the cluster to be snapshotted
     * @param name the name of the snapshot
     * @param description a description of the snapshot purpose
     * @return the unique ID of the pending new snapshot
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public @Nonnull String createClusterSnapshot(@Nonnull String clusterId, @Nonnull String name, @Nonnull String description) throws CloudException, InternalException;

    /**
     * Disables any logging currently happening for the specified data cluster.
     * @param clusterId the unique ID of the cluster for whom logging should be diabled
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public void disableLogging(@Nonnull String clusterId) throws CloudException, InternalException;

    /**
     * Enables logging for the specified cluster to the named bucket with entries having the specified prefix.
     * @param clusterId the unique ID of the cluster on which logging should be enabled
     * @param bucket the cloud object store bucket in which the logs will be stored
     * @param prefix the prefix of all log entries associated with this cluster
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public void enableLogging(@Nonnull String clusterId, @Nonnull String bucket, @Nonnull String prefix) throws CloudException, InternalException;

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
     * Fetches a specific data cluster firewall based on a desired firewall ID. If a matching data cluster firewall
     * does not exist, <code>null</code> is returned.
     * @param firewallId the unique ID of the desired data cluster firewall
     * @return a firewall object matching the specified firewall ID or <code>null</code> if no firewall matches
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public @Nullable DataClusterFirewall getClusterFirewall(@Nonnull String firewallId) throws CloudException, InternalException;

    /**
     * Indicates the current logging status for the specified data cluster. If logging is not enabled or the specified
     * cluster doesn't exist, this method will return <code>null</code>.
     * @param clusterId the ID of the cluster whose logging status should be fetched
     * @return the logging status for the specified data cluster if enabled and it exists
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public @Nullable CloudStorageLogging getClusterLoggingStatus(@Nonnull String clusterId) throws CloudException, InternalException;

    /**
     * Fetches the specified cluster parameter group based on its unique group ID.
     * @param groupId the unique ID of the group being sought
     * @return an object representing the parameter group if it exists, or <code>null</code> if there is no match
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public @Nullable DataClusterParameterGroup getClusterParameterGroup(@Nonnull String groupId) throws CloudException, InternalException;

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
     * Fetches the specified snapshot matching the specified unique snapshot ID. If no such snapshot exists, a
     * <code>null</code> value is returned.
     * @param snapshotId the unique ID of the desired data cluster snapshot
     * @return any matching data cluster snapshot or <code>null</code> if no such snapshot exists
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public @Nullable DataClusterSnapshot getClusterSnapshot(@Nonnull String snapshotId) throws CloudException, InternalException;

    /**
     * Indicates whether or not data clusters may be (or are required to be) constrained to a specific data center.
     * @return the requirement for data clusters to be constrained to a data center
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public @Nonnull Requirement getDataCenterConstraintRequirement() throws CloudException, InternalException;

    /**
     * Lists all data cluster firewalls that support data clusters in this cloud.
     * @return a list of data cluster firewalls
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public @Nonnull Iterable<DataClusterFirewall> listClusterFirewalls() throws CloudException, InternalException;

    /**
     * Lists all parameter groups belonging to this account.
     * @return a list of parameter groups for this account
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public @Nonnull Iterable<DataClusterParameterGroup> listClusterParameterGroups() throws CloudException, InternalException;

    /**
     * Lists all products through which data clusters may be provisioned.
     * @return the list of available data cluster products
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public @Nonnull Iterable<DataClusterProduct> listClusterProducts() throws CloudException, InternalException;

    /**
     * Lists all data cluster snapshots owned by the current user in the current region.
     * @return a list of data cluster snapshots for the current user in the current region
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public @Nonnull Iterable<DataClusterSnapshot> listClusterSnapshots() throws CloudException, InternalException;

    /**
     * Lists data cluster snapshots matching the filter criteria specified. If no filter criteria are specified, this
     * method acts as if it were filtering solely on this user's snapshots (e.g. equal to {@link #listClusterSnapshots()}).
     * @param options filter criteria
     * @return a list of data cluster snapshots matching the specified criteria
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public @Nonnull Iterable<DataClusterSnapshot> listClusterSnapshots(@Nullable DataClusterSnapshotFilterOptions options) throws CloudException, InternalException;

    /**
     * Lists all versions of the clustering technology available for creating new data cluster instances.
     * @return a list of supported versions
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public @Nonnull Iterable<DataClusterVersion> listClusterVersions() throws CloudException, InternalException;

    /**
     * Lists all known data clusters for the current account in the current region of the cloud.
     * @return all known data clusters for this region
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public @Nonnull Iterable<DataCluster> listClusters() throws CloudException, InternalException;

    /**
     * Reboots the cluster (probably results in downtime).
     * @param clusterId the unique ID of the cluster to be rebooted
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public void rebootCluster(@Nonnull String clusterId) throws CloudException, InternalException;

    /**
     * Removes ALL specific account shares for the specified snapshot. NOTE THAT THIS METHOD WILL NOT THROW AN EXCEPTION
     * WHEN SNAPSHOT SHARING IS NOT SUPPORTED. IT IS A NO-OP IN THAT SCENARIO.
     * @param snapshotId the unique ID of the snapshot to be unshared
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     */
    public void removeAllSnapshotShares(@Nonnull String snapshotId) throws CloudException, InternalException;

    /**
     * Removes the specified data cluster from the cloud.
     * @param clusterId the data cluster to be removed
     * @param snapshotFirst snapshot the database before removing the cluster
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public void removeCluster(@Nonnull String clusterId, boolean snapshotFirst) throws CloudException, InternalException;

    /**
     * Deactivates the specified data cluster firewall.
     * @param firewallId the unique ID of the data cluster firewall to be deactivated
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public void removeClusterFirewall(@Nonnull String firewallId) throws CloudException, InternalException;

    /**
     * Removes the specified parameter group from the cloud.
     * @param groupId the unique ID of the target parameter group
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public void removeClusterParameterGroup(@Nonnull String groupId) throws CloudException, InternalException;

    /**
     * Removes the specified cluster snapshot.
     * @param snapshotId the snapshot to be removed
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public void removeClusterSnapshot(@Nonnull String snapshotId) throws CloudException, InternalException;

    /**
     * Removes the specified account number from the list of accounts with which this snapshot is shared.
     * @param snapshotId the unique ID of the snapshot to be unshared
     * @param accountNumber the account number with which the snapshot will be unshared
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     * @throws OperationNotSupportedException the cloud does not support sharing snapshots with other accounts
     */
    public void removeSnapshotShare(@Nonnull String snapshotId, @Nonnull String accountNumber) throws CloudException, InternalException;

    /**
     * Resizes the cluster to the specified number of nodes.
     * @param clusterId the cluster to resize
     * @param nodeCount the number of nodes to which the cluster should be resized
     * @throws CloudException an error occurred in the cloud provider while performing the operation
     * @throws InternalException an error occurred in the Dasein Cloud implementation while preparing or processing the call
     */
    public void resizeCluster(@Nonnull String clusterId, @Nonnegative int nodeCount) throws CloudException, InternalException;

    /**
     * Revokes access to data clusters associated with a specified data cluster firewall from the compute firewalls
     * ({@link Firewall}) referenced by the firewall references.
     * @param dataClusterFirewallId the data cluster firewall to be altered
     * @param firewalls one or more references to compute firewalls to be revoked
     * @throws CloudException an error occurred in the cloud provider while performing the operation
     * @throws InternalException an error occurred in the Dasein Cloud implementation while preparing or processing the call
     */
    public void revokeComputeFirewalls(@Nonnull String dataClusterFirewallId, @Nonnull FirewallReference ... firewalls) throws CloudException, InternalException;

    /**
     * Revokes access to data clusters associated with the specified data cluster firewall from IP addresses that match
     * the specified CIDR notation list.
     * @param dataClusterFirewallId the data cluster firewall to be altered
     * @param cidrs one or more IP addresses in CIDR notation
     * @throws CloudException an error occurred in the cloud provider while performing the operation
     * @throws InternalException an error occurred in the Dasein Cloud implementation while preparing or processing the call
     */
    public void revokeIPs(@Nonnull String dataClusterFirewallId, @Nonnull String ... cidrs) throws CloudException, InternalException;

    /**
     * Rotates the encryption keys used for data at rest encryption inside a data cluster. Encryption at rest
     * must be enabled for the data cluster, otherwise this method will throw an exception.
     * @param clusterId the cluster ID of the cluster for whom key rotation should take place
     * @throws CloudException an error occurred processing the request in the cloud provider or encryption at rest is not enabled
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public void rotateEncryptionKeys(@Nonnull String clusterId) throws CloudException, InternalException;

    /**
     * Indicates whether or not you can authorize compute firewalls for data cluster firewalls. Only makes sense
     * when {@link #supportsClusterFirewalls()} is true.
     * @return true if you can authorize compute firewall access to data cluster firewalls
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public boolean supportsAuthorizingComputeFirewalls() throws CloudException, InternalException;

    /**
     * Indicates whether or not your can enable cluster logging to a bucket in the cloud storage tied to this
     * cloud.
     * @return true if logging can be enabled
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public boolean supportsCloudStorageLogging() throws CloudException, InternalException;

    /**
     * Indicates whether or not this cloud supports protecting access to data clusters with special data cluster firewalls.
     * @return true if supported
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public boolean supportsClusterFirewalls() throws CloudException, InternalException;

    /**
     * Indicates whether or not you can snapshot clusters. Automated snapshots are performed automatically
     * by the cloud provider on a regular basis with a fixed snapshot retention. Manual snapshots are performed through
     * the API or cloud console.
     * @param automated <code>true</code> if you are checking for automated snapshot support, <code>false</code> if for manual
     * @return true if the specified kind of snapshots are supported
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public boolean supportsClusterSnapshots(boolean automated) throws CloudException, InternalException;

    /**
     * Indicates whether or not this cloud supports encryption of your database at rest.
     * @return true if encryption at rest is supported
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public boolean supportsEncryptionAtRest() throws CloudException, InternalException;

    /**
     * Updates the parameters associated with the specified parameter group to new values.
     * @param parameterGroupId the ID of the group whose parameters should be modified
     * @param parameters the parameters to modify
     * @throws CloudException an error occurred processing the request in the cloud provider
     * @throws InternalException an error occurred in the Dasein Cloud implementation while processing the request
     */
    public void updateParameters(@Nonnull String parameterGroupId, @Nonnull Map<String,Object> parameters) throws CloudException, InternalException;

    /**
     * Indicates whether or not a snapshot owner can explicitly share a snapshot with another account.
     * @return true if snapshot sharing is supported
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     * @throws CloudException an error occurred with the cloud provider
     */
    public boolean supportsSnapshotSharing() throws InternalException, CloudException;

    /**
     * Updates meta-data for a data cluster with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     * @param clusterId the data cluster to update
     * @param tags the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void updateClusterTags(@Nonnull String clusterId, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Updates meta-data for multiple data clusters with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     * @param clusterIds the clusters to update
     * @param tags  the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void updateClusterTags(@Nonnull String[] clusterIds, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Updates meta-data for a data cluster snapshot with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     * @param snapshotId the data cluster snapshot to update
     * @param tags the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void updateSnapshotTags(@Nonnull String snapshotId, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Updates meta-data for multiple data cluster snapshots with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     * @param snapshotIds the snapshots to update
     * @param tags  the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void updateSnapshotTags(@Nonnull String[] snapshotIds, @Nonnull Tag... tags) throws CloudException, InternalException;
}
