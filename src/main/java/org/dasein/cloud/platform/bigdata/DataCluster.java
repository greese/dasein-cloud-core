/*
 * Copyright (C) 2014 enStratus Networks Inc.
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

package org.dasein.cloud.platform.bigdata;

import org.dasein.cloud.network.VLAN;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Models a data warehousing cluster that consists of a number of database nodes and a shared
 * database endpoint to support big data processing.
 * <p>Created by George Reese: 2/7/14 12:08 PM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 added model (issue #100)
 */
public class DataCluster {
    static public DataCluster getInstance(@Nonnull String providerRegionId, @Nullable String providerDataCenterId, @Nonnull String providerDataClusterId, @Nonnull DataClusterState currentState, @Nonnull String name, @Nonnull String description, @Nonnull String providerProductId, @Nonnull String databaseName, @Nonnegative int databasePort) {
        return getInstance(null, providerRegionId, providerDataCenterId, providerDataClusterId, currentState, name, description, providerProductId, "0", databaseName, databasePort, null, null, 1, false);
    }

    static public DataCluster getInstance(@Nonnull String inVlanId, @Nonnull String providerRegionId, @Nullable String providerDataCenterId, @Nonnull String providerDataClusterId, @Nonnull DataClusterState currentState, @Nonnull String name, @Nonnull String description, @Nonnull String providerProductId, @Nonnull String databaseName, @Nonnegative int databasePort) {
        return getInstance(inVlanId, providerRegionId, providerDataCenterId, providerDataClusterId, currentState, name, description, providerProductId, "0", databaseName, databasePort, null, null, 1, false);
    }

    static public DataCluster getInstance(@Nonnull String providerRegionId, @Nullable String providerDataCenterId, @Nonnull String providerDataClusterId, @Nonnull DataClusterState currentState, @Nonnull String name, @Nonnull String description, @Nonnull String providerProductId, @Nonnull String databaseName, @Nonnegative int databasePort, @Nullable String adminUser, @Nullable String adminPassword, @Nonnegative int nodeCount) {
        return getInstance(null, providerRegionId, providerDataCenterId, providerDataClusterId, currentState, name, description, providerProductId, "0", databaseName, databasePort, adminUser, adminPassword, nodeCount, false);
    }

    static public DataCluster getInstance(@Nullable String inVlanId, @Nonnull String providerRegionId, @Nullable String providerDataCenterId, @Nonnull String providerDataClusterId, @Nonnull DataClusterState currentState, @Nonnull String name, @Nonnull String description, @Nonnull String providerProductId, @Nonnull String clusterVersion, @Nonnull String databaseName, @Nonnegative int databasePort, @Nullable String adminUser, @Nullable String adminPassword, @Nonnegative int nodeCount, boolean encrypted) {
        DataCluster c = new DataCluster();

        c.providerVlanId = inVlanId;
        c.providerRegionId = providerRegionId;
        c.providerDataCenterId = providerDataCenterId;
        c.currentState = currentState;
        c.providerDataClusterId = providerDataClusterId;
        c.providerProductId = providerProductId;
        c.databaseName = databaseName;
        c.databasePort = databasePort;
        c.name = name;
        c.description = description;
        c.nodeCount = nodeCount;
        c.encrypted = encrypted;
        c.clusterVersion = clusterVersion;
        c.adminUserName = adminUser;
        c.adminPassword = adminPassword;
        return c;
    }

    private String           adminPassword;
    private String           adminUserName;
    private String           clusterVersion;
    private DataClusterState currentState;
    private String           databaseName;
    private int              databasePort;
    private String           description;
    private boolean          encrypted;
    private String           providerDataCenterId;
    private String           providerDataClusterId;
    private String           providerProductId;
    private String           providerRegionId;
    private String           providerVlanId;
    private String           name;
    private int              nodeCount;

    private DataCluster() { }

    @Override
    public boolean equals(@Nullable Object other) {
        if( other == null ) {
            return false;
        }
        if( other == this ) {
            return true;
        }
        if( !getClass().getName().equals(other.getClass().getName()) ) {
            return false;
        }
        DataCluster c = (DataCluster)other;
        return providerRegionId.equals(c.providerRegionId) && !((providerDataCenterId == null && c.providerDataCenterId != null) || (providerDataCenterId != null && c.providerDataCenterId != null)) && !(providerDataCenterId != null && !providerDataCenterId.equals(c.providerDataCenterId)) && providerDataClusterId.equals(providerDataClusterId);
    }

    /**
     * @return the administrative password to use in SQL or other data access APIs
     */
    public @Nullable String getAdminPassword() {
        return adminPassword;
    }

    /**
     * @return the administrative user name to use in SQL or other data access APIs
     */
    public @Nullable String getAdminUserName() {
        return adminUserName;
    }

    /**
     * @return the version string for the version of this data cluster
     */
    public @Nonnull String getClusterVersion() {
        return clusterVersion;
    }

    /**
     * @return the current state of the data cluster
     */
    public @Nonnull DataClusterState getCurrentState() {
        return currentState;
    }

    /**
     * @return the data center in which the cluster operates
     */
    public @Nullable String getProviderDataCenterId() {
        return providerDataCenterId;
    }

    /**
     * @return the unique identifier for identifying this data cluster with the cloud provider
     */
    public @Nonnull String getProviderDataClusterId() {
        return providerDataClusterId;
    }

    /**
     * @return the database name to use in accessing the database behind this cluster
     */
    public @Nonnull String getDatabaseName() {
        return databaseName;
    }

    /**
     * @return the port for accessing the database on this cluster for SQL or other data access APIs
     */
    public @Nonnegative int getDatabasePort() {
        return databasePort;
    }

    /**
     * @return a user-friendly description of this data cluster
     */
    public @Nonnull String getDescription() {
        return description;
    }

    /**
     * @return the unique identifier of the product associated with the cluster
     */
    public @Nonnull String getProviderProductId() {
        return providerProductId;
    }

    /**
     * @return the region in which the cluster operates
     */
    public @Nonnull String getProviderRegionId() {
        return providerRegionId;
    }

    /**
     * @return the unique identifier of the {@link VLAN} to which this cluster is attached
     */
    public @Nullable String getProviderVlanId() {
        return providerVlanId;
    }

    /**
     * @return a user-friendly name for the data cluster
     */
    public @Nonnull String getName() {
        return name;
    }

    /**
     * @return the number of nodes supporting this data cluster
     */
    public @Nonnegative int getNodeCount() {
        return nodeCount;
    }

    @Override
    public int hashCode() {
        return (providerRegionId + "/" + providerDataCenterId + "/" + providerDataClusterId).hashCode();
    }

    /**
     * Alters this data cluster object to reflect the administrative credentials associated with this data cluster in
     * the cloud. This method does not affect an actual change in the cloud data cluster.
     * @param adminUser the administrative user name to use in SQL or other data access APIs
     * @param adminPassword the administrative password to use in SQL or other data access APIs
     * @return this
     */
    public @Nonnull DataCluster havingAdminCredentials(@Nonnull String adminUser, @Nonnull String adminPassword) {
        this.adminUserName = adminUser;
        this.adminPassword = adminPassword;
        return this;
    }

    /**
     * Alters this data cluster object to reflect the the number of nodes behind this data cluster.
     * It does not actually cause a change to the underlying cloud resource.
     * @param nodeCount the node count for the data cluster
     * @return this
     */
    public @Nonnull DataCluster havingNodeCount(@Nonnegative int nodeCount) {
        this.nodeCount = nodeCount;
        return this;
    }

    /**
     * Alters this data cluster object to reflect the version associated with this cluster. It does not
     * actually cause a change to the underlying cloud resource.
     * @param version the cluster version
     * @return this
     */
    public @Nonnull DataCluster havingVersion(@Nonnull String version) {
        this.clusterVersion = version;
        return this;
    }

    /**
     * Alters this data cluster object to reflect the {@link VLAN} to which this data cluster is attached.
     * It does not actually cause a change to the underlying cloud resource.
     * @param providerVlanId the unique ID of the {@link VLAN} to which this data cluster is attached
     * @return this
     */
    public @Nonnull DataCluster inVlan(@Nonnull String providerVlanId) {
        this.providerVlanId = providerVlanId;
        return this;
    }

    /**
     * @return true if the database for this cluster is encrypted
     */
    public boolean isEncrypted() {
        return encrypted;
    }

    @Override
    public @Nonnull String toString() {
        return providerDataClusterId;
    }

    /**
     * Alters this data cluster object to reflect the fact that the underlying cloud resource is encrypted. It does not
     * actually cause a change to the underlying cloud resource.
     * @return this
     */
    public @Nonnull DataCluster withEncryption() {
        encrypted = true;
        return this;
    }

    /**
     * Alters this data cluster object to reflect the fact that the underlying cloud resource is NOT encrypted. It does not
     * actually cause a change to the underlying cloud resource.
     * @return this
     */
    public @Nonnull DataCluster withoutEncryption() {
        encrypted = false;
        return this;
    }
}
