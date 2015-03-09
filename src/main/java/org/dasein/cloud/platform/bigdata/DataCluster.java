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

import org.dasein.cloud.Taggable;
import org.dasein.cloud.TimeWindow;
import org.dasein.cloud.network.VLAN;
import org.dasein.util.uom.time.Day;
import org.dasein.util.uom.time.TimePeriod;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Models a data warehousing cluster that consists of a number of database nodes and a shared
 * database endpoint to support big data processing.
 * <p>Created by George Reese: 2/7/14 12:08 PM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 added model (issue #100)
 */
public class DataCluster implements Taggable {
    /**
     * Constructs a new data cluster object with the most basic data required in order to be considered a valid Dasein
     * object. Make sure to honor the nullability of the specified parameters.
     * @param providerOwnerId the owning account of the data cluster
     * @param providerRegionId the region in which the cluster operates
     * @param providerDataCenterId the data center affinity of the cluster, if any
     * @param providerDataClusterId the unique ID for the cluster
     * @param currentState the current state of the cluster
     * @param name the user-friendly name of the cluster
     * @param description a user-friendly description of the cluster
     * @param providerProductId the unique ID of the product with which the cluster is associated
     * @param databaseName the name of the database supported by the cluster
     * @param databasePort the port to which you can connect for SQL (or other protocol) queries
     * @param protocols the supported query protocols
     * @return a newly constructed data cluster
     */
    static public @Nonnull DataCluster getInstance(@Nonnull String providerOwnerId, @Nonnull String providerRegionId, @Nullable String providerDataCenterId, @Nonnull String providerDataClusterId, @Nonnull DataClusterState currentState, @Nonnull String name, @Nonnull String description, @Nonnull String providerProductId, @Nonnull String databaseName, @Nonnegative int databasePort, @Nullable ClusterQueryProtocol ... protocols) {
        return getInstance(null, providerOwnerId, providerRegionId, providerDataCenterId, providerDataClusterId, currentState, name, description, providerProductId, "0", databaseName, databasePort, null, null, 1, false, 0L, protocols);
    }

    /**
     * Constructs a new data cluster object in a VLAN with the most basic data required in order to be considered a valid Dasein
     * object. Make sure to honor the nullability of the specified parameters.
     * @param inVlanId the unique ID of the VLAN in which the data cluster operates
     * @param providerOwnerId the owning account of the data cluster
     * @param providerRegionId the region in which the cluster operates
     * @param providerDataCenterId the data center affinity of the cluster, if any
     * @param providerDataClusterId the unique ID for the cluster
     * @param currentState the current state of the cluster
     * @param name the user-friendly name of the cluster
     * @param description a user-friendly description of the cluster
     * @param providerProductId the unique ID of the product with which the cluster is associated
     * @param databaseName the name of the database supported by the cluster
     * @param databasePort the port to which you can connect for SQL (or other protocol) queries
     * @param protocols the supported query protocols
     * @return a newly constructed data cluster
     */
    static public @Nonnull DataCluster getInstance(@Nonnull String inVlanId, @Nonnull String providerOwnerId, @Nonnull String providerRegionId, @Nullable String providerDataCenterId, @Nonnull String providerDataClusterId, @Nonnull DataClusterState currentState, @Nonnull String name, @Nonnull String description, @Nonnull String providerProductId, @Nonnull String databaseName, @Nonnegative int databasePort, @Nullable ClusterQueryProtocol ... protocols) {
        return getInstance(inVlanId, providerOwnerId, providerRegionId, providerDataCenterId, providerDataClusterId, currentState, name, description, providerProductId, "0", databaseName, databasePort, null, null, 1, false, 0L, protocols);
    }

    /**
     * Constructs a new data cluster object with extended attributes.
     * Make sure to honor the nullability of the specified parameters.
     * @param providerOwnerId the owning account of the data cluster
     * @param providerRegionId the region in which the cluster operates
     * @param providerDataCenterId the data center affinity of the cluster, if any
     * @param providerDataClusterId the unique ID for the cluster
     * @param currentState the current state of the cluster
     * @param name the user-friendly name of the cluster
     * @param description a user-friendly description of the cluster
     * @param providerProductId the unique ID of the product with which the cluster is associated
     * @param databaseName the name of the database supported by the cluster
     * @param databasePort the port to which you can connect for SQL (or other protocol) queries
     * @param adminUser the administrative user for the database
     * @param adminPassword the password for the administrative user for the database
     * @param nodeCount the number of nodes supporting the database
     * @param protocols the supported query protocols
     * @return a newly constructed data cluster
     */
    static public @Nonnull DataCluster getInstance(@Nonnull String providerOwnerId, @Nonnull String providerRegionId, @Nullable String providerDataCenterId, @Nonnull String providerDataClusterId, @Nonnull DataClusterState currentState, @Nonnull String name, @Nonnull String description, @Nonnull String providerProductId, @Nonnull String databaseName, @Nonnegative int databasePort, @Nullable String adminUser, @Nullable String adminPassword, @Nonnegative int nodeCount, @Nullable ClusterQueryProtocol ... protocols) {
        return getInstance(null, providerOwnerId, providerRegionId, providerDataCenterId, providerDataClusterId, currentState, name, description, providerProductId, "0", databaseName, databasePort, adminUser, adminPassword, nodeCount, false, 0L, protocols);
    }

    /**
     * Constructs a new data cluster object in a specific VLAN with extended attributes.
     * Make sure to honor the nullability of the specified parameters.
     * @param inVlanId the unique ID of the VLAN in which the data cluster operates
     * @param providerOwnerId the owning account of the data cluster
     * @param providerRegionId the region in which the cluster operates
     * @param providerDataCenterId the data center affinity of the cluster, if any
     * @param providerDataClusterId the unique ID for the cluster
     * @param currentState the current state of the cluster
     * @param name the user-friendly name of the cluster
     * @param description a user-friendly description of the cluster
     * @param providerProductId the unique ID of the product with which the cluster is associated
     * @param clusterVersion the version of the cluster
     * @param databaseName the name of the database supported by the cluster
     * @param databasePort the port to which you can connect for SQL (or other protocol) queries
     * @param adminUser the administrative user for the database
     * @param adminPassword the password for the administrative user for the database
     * @param nodeCount the number of nodes supporting the database
     * @param encrypted indicates whether the database supported by the cluster is encrypted
     * @param creationTimestamp the time at which the data cluster was initial provisioned
     * @param protocols the supported query protocols
     * @return a newly constructed data cluster
     */
    static public @Nonnull DataCluster getInstance(@Nullable String inVlanId, @Nonnull String providerOwnerId, @Nonnull String providerRegionId, @Nullable String providerDataCenterId, @Nonnull String providerDataClusterId, @Nonnull DataClusterState currentState, @Nonnull String name, @Nonnull String description, @Nonnull String providerProductId, @Nonnull String clusterVersion, @Nonnull String databaseName, @Nonnegative int databasePort, @Nullable String adminUser, @Nullable String adminPassword, @Nonnegative int nodeCount, boolean encrypted, long creationTimestamp, ClusterQueryProtocol ... protocols) {
        DataCluster c = new DataCluster();

        c.providerVlanId = inVlanId;
        c.providerOwnerId = providerOwnerId;
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
        c.creationTimestamp = creationTimestamp;
        c.protocols = protocols;
        return c;
    }

    private String                 adminPassword;
    private String                 adminUserName;
    private String                 clusterVersion;
    private long                   creationTimestamp;
    private DataClusterState       currentState;
    private String                 databaseName;
    private int                    databasePort;
    private String                 description;
    private boolean                encrypted;
    private TimeWindow             maintenanceWindow;
    private ClusterQueryProtocol[] protocols;
    private String                 providerDataCenterId;
    private String                 providerDataClusterId;
    private String                 providerOwnerId;
    private String                 providerParameterGroupId;
    private String                 providerProductId;
    private String                 providerRegionId;
    private String                 providerVlanId;
    private String                 name;
    private int                    nodeCount;
    private TimePeriod<Day>        snapshotRetention;
    private Map<String,String>     tags;

    private DataCluster() { }

    /**
     * Alters the time at which this data cluster object indicates the underlying cloud resource was provisioned. This
     * method will not actually affect a change in the underlying cloud resource.
     * @param creationTimestamp the unix timestamp reflected the provision time of this data cluster
     * @return this
     */
    public @Nonnull DataCluster createdAt(@Nonnegative long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
        return this;
    }

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
        return providerOwnerId.equals(c.providerOwnerId) && providerRegionId.equals(c.providerRegionId) && !((providerDataCenterId == null && c.providerDataCenterId != null) || (providerDataCenterId != null && c.providerDataCenterId != null)) && !(providerDataCenterId != null && !providerDataCenterId.equals(c.providerDataCenterId)) && providerDataClusterId.equals(providerDataClusterId);
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
     * @return the unix timestamp when this data cluster was initially provisioned (0L for unknown)
     */
    public @Nonnegative long getCreationTimestamp() {
        return creationTimestamp;
    }

    /**
     * @return the current state of the data cluster
     */
    public @Nonnull DataClusterState getCurrentState() {
        return currentState;
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
     * @return the preferred time window in which the cloud should perform any automated maintenance operations
     */
    public @Nullable TimeWindow getMaintenanceWindow() {
        return maintenanceWindow;
    }

    /**
     * @return a list of all protocols that may be used to query this database on the {@link #getDatabasePort()}.
     */
    public @Nonnull ClusterQueryProtocol[] getProtocols() {
        ClusterQueryProtocol[] p = new ClusterQueryProtocol[protocols == null ? 0 : protocols.length];

        if( protocols != null && protocols.length > 0 ) {
            System.arraycopy(protocols, 0, p, 0, protocols.length);
        }
        return p;
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
     * @return the owner of this data cluster
     */
    public @Nonnull String getProviderOwnerId() {
        return providerOwnerId;
    }

    /**
     * @return the parameter group, if any, associated with this data cluster
     */
    public @Nullable String getProviderParameterGroupId() {
        return providerParameterGroupId;
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

    /**
     * Indicates the period for which an automated snapshot will be retained. A <code>null</code> value indicates
     * that snapshots are retained for an indetermined length of time (or forever).
     * @return the time period for which automated snapshots get retained
     */
    public @Nullable TimePeriod<Day> getSnapshotRetentionPeriod() {
        return snapshotRetention;
    }

    /**
     * @param key the key of the tag value you wish to fetch
     * @return the tag value for the specified tag key
     */
    public @Nullable String getTag(@Nonnull String key) {
        return getTags().get(key);
    }

    @Override
    public @Nonnull Map<String, String> getTags() {
        return (tags == null ? new HashMap<String, String>() : tags);
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
     * Alters this data cluster object to reflect the the time window during which this data cluster is maintained
     * It does not actually cause a change to the underlying cloud resource.
     * @param window the preferred maintenance window
     * @return this
     */
    public @Nonnull DataCluster havingMaintenanceWindow(@Nonnull TimeWindow window) {
        maintenanceWindow = window;
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
    public void setTag(@Nonnull String key, @Nonnull String value) {
        if( tags == null ) {
            tags = new HashMap<String,String>();
        }
        tags.put(key, value);
    }

    /**
     * Adds additional protocols to the list of protocols associated with this data cluster.
     * @param protocols the new protocols (in addition to the current ones) to be associated with this cluster
     * @return this
     */
    public @Nonnull DataCluster supportingProtocols(@Nonnull ClusterQueryProtocol ... protocols) {
        if( protocols.length > 0 ) {
            ArrayList<ClusterQueryProtocol> p = new ArrayList<ClusterQueryProtocol>();

            if( this.protocols != null ) {
                Collections.addAll(p, this.protocols);
            }
            Collections.addAll(p, protocols);
            this.protocols = new ClusterQueryProtocol[p.size()];
            this.protocols = p.toArray(this.protocols);
        }
        return this;
    }

    @Override
    public @Nonnull String toString() {
        return providerDataClusterId;
    }

    /**
     * Alters this data cluster object to reflect the version associated with this cluster. It does not
     * actually cause a change to the underlying cloud resource.
     * @param version the cluster version
     * @return this
     */
    public @Nonnull DataCluster usingVersion(@Nonnull String version) {
        this.clusterVersion = version;
        return this;
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
     * Alters the data cluster to reflect the fact that the underlying cloud resource is assigned to the specified
     * parameter group. It does not actually cause a change to the underlying cloud resource.
     * @param providerParameterGroupId the parameter group with which this cluster is associated
     * @return this
     */
    public @Nonnull DataCluster withParameterGroup(@Nonnull String providerParameterGroupId) {
        this.providerParameterGroupId = providerParameterGroupId;
        return this;
    }

    /**
     * Alters the data cluster to reflect the fact that the underlying cloud cluster has a snapshot retention period
     * of the specified length. It does not actually cause a change in the cloud.
     * @param period the period for which automated snapshots are retained against this cluster
     * @return this
     */
    public @Nonnull DataCluster withSnapshotsRetainedFor(@Nonnull TimePeriod<?> period) {
        snapshotRetention = (TimePeriod<Day>)period.convertTo(TimePeriod.DAY);
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
