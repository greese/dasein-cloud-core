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
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.TimeWindow;
import org.dasein.cloud.platform.PlatformServices;
import org.dasein.cloud.util.Security;
import org.dasein.util.uom.time.Day;
import org.dasein.util.uom.time.TimePeriod;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

/**
 * Configuration options for provisioning new data clusters.
 * <p>Created by George Reese: 2/7/14 5:04 PM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 initial version (issue #100)
 */
public class DataClusterCreateOptions {
    /**
     * Constructs the provisioning options for a very basic data cluster. By default, the resulting options support a
     * single node cluster with auto-generated
     * @param providerProductId the product to use in the provisioning process
     * @param name the name of the data cluster to be created
     * @param description a user-friendly description for the data cluster
     * @param databaseName the name of the database to be placed in the cluster
     * @return options for creating a data cluster
     */
    static public @Nonnull DataClusterCreateOptions getInstance(@Nonnull String providerProductId, @Nonnull String name, @Nonnull String description, @Nonnull String databaseName) {
        return getInstance(providerProductId, null, name, description, null, databaseName, 0, null, null, 1, true);
    }

    /**
     * Constructs provisioning options for a data cluster with a data center affinity.
     * @param providerProductId the product to use in the provisioning process
     * @param providerDataCenterId the data center into which the data cluster will be provisioned
     * @param name the name of the data cluster to be created
     * @param description a user-friendly description for the data cluster
     * @param databaseName the name of the database to be placed in the cluster
     * @return options for creating a data cluster based on the specified parameters
     */
    static public @Nonnull DataClusterCreateOptions getInstance(@Nonnull String providerProductId, @Nonnull String providerDataCenterId, @Nonnull String name, @Nonnull String description, @Nonnull String databaseName) {
        return getInstance(providerProductId, providerDataCenterId, name, description, null, databaseName, 0, null, null, 1, true);
    }

    /**
     * Constructs provisioning options for a significantly configured data cluster.
     * @param providerProductId the product to use in the provisioning prociess
     * @param providerDataCenterId  the data center into which the data cluster will be provisioned
     * @param name the name of the data cluster to be created
     * @param description a user-friendly description for the data cluster
     * @param clusterVersion the clustering version to be targeted
     * @param databaseName the name of the database to be placed in the cluster
     * @param databasePort the port on which queries will be run
     * @param adminUser the administrative user for querying the database
     * @param adminPassword the password for the administrative user
     * @param nodeCount the number of nodes to be provisioned
     * @param encrypted whether or not the data should be encrypted at rest
     * @return options for creating a data cluster based on the specified parameters
     */
    static public @Nonnull DataClusterCreateOptions getInstance(@Nonnull String providerProductId, @Nullable String providerDataCenterId, @Nonnull String name, @Nonnull String description, @Nullable String clusterVersion, @Nonnull String databaseName, @Nonnegative int databasePort, @Nullable String adminUser, @Nullable String adminPassword, @Nonnegative int nodeCount, boolean encrypted) {
        DataClusterCreateOptions options = new DataClusterCreateOptions();

        options.providerProductId = providerProductId;
        options.providerDataCenterId = providerDataCenterId;
        options.name = name;
        options.description = description;
        options.adminUserName = (adminUser == null ? "admin" : adminUser);
        options.adminPassword = (adminPassword == null ? Security.generateRandomPassword(10, 20) : adminPassword);
        options.clusterVersion = clusterVersion;
        options.databaseName = databaseName;
        options.databasePort = databasePort;
        options.nodeCount = (nodeCount < 1 ? 1 : nodeCount);
        options.encrypted = encrypted;
        return options;
    }

    private String          adminPassword;
    private String          adminUserName;
    private String          clusterVersion;
    private String          databaseName;
    private int             databasePort;
    private String          description;
    private boolean         encrypted;
    private TimeWindow      maintenanceWindow;
    private String          name;
    private int             nodeCount;
    private String          providerDataCenterId;
    private String[]        providerFirewallIds;
    private String          providerParameterGroupId;
    private String          providerProductId;
    private TimePeriod<Day> snapshotRetentionPeriod;

    private DataClusterCreateOptions() { }

    /**
     * Executes a request to create a data cluster in the target region of the target cloud using the
     * options described in this object.
     * @param provider the provider object representing the cloud and region in which the data cluster should be created
     * @return the ID of the newly created data cluster
     * @throws CloudException an error occurred with the cloud provider when creating the data cluster
     * @throws InternalException an error occurred within the Dasein Cloud implementation while creating the data cluster
     */
    public @Nonnull String build(@Nonnull CloudProvider provider) throws CloudException, InternalException {
        PlatformServices services = provider.getPlatformServices();

        if( services == null ) {
            throw new OperationNotSupportedException("No platform services in " + provider.getCloudName());
        }
        DataWarehouseSupport support = services.getDataWarehouseSupport();

        if( support == null ) {
            throw new OperationNotSupportedException("No data warehouse support in " + provider.getCloudName());
        }
        return support.createCluster(this);
    }

    /**
     * @return the admin password to use in accessing the database on the cluster
     */
    public @Nonnull String getAdminPassword() {
        return adminPassword;
    }

    /**
     * @return the admin user to use in accessing the database on the cluster
     */
    public @Nonnull String getAdminUserName() {
        return adminUserName;
    }

    /**
     * @return the version under which the cluster will be created if a specific version is desired
     */
    public @Nullable String getClusterVersion() {
        return clusterVersion;
    }

    /**
     * @return the name of the database to create on the cluster
     */
    public @Nonnull String getDatabaseName() {
        return databaseName;
    }

    /**
     * @return the port on which the cluster will listen for database queries
     */
    public @Nonnegative int getDatabasePort() {
        return databasePort;
    }

    /**
     * @return a user-friendly description of the database
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
     * @return a user-friendly name for the database
     */
    public @Nonnull String getName() {
        return name;
    }

    /**
     * @return the number of nodes that will make up the cluster
     */
    public @Nonnegative int getNodeCount() {
        return nodeCount;
    }

    /**
     * @return the data center to which the cluster will be attached
     */
    public @Nullable String getProviderDataCenterId() {
        return providerDataCenterId;
    }

    /**
     * @return a list of data cluster firewalls to associate the data cluster with upon creation
     */
    public @Nonnull String[] getProviderFirewallIds() {
        String[] ids = new String[providerFirewallIds == null ? 0 : providerFirewallIds.length];

        if( providerFirewallIds != null ) {
            for( int i=0; i<ids.length; i++ ) {
                ids[i] = providerFirewallIds[i];
            }
        }
        return ids;
    }

    /**
     * @return the parameter group to associate with this cluster
     */
    public @Nullable String getProviderParameterGroupId() {
        return providerParameterGroupId;
    }

    /**
     * @return the product to use in provisioning the cluster
     */
    public @Nonnull String getProviderProductId() {
        return providerProductId;
    }

    /**
     * Indicates the period for which an automated snapshot will be retained. A <code>null</code> value indicates
     * that snapshots are retained for an indetermined length of time (or forever).
     * @return the time period for which automated snapshots get retained
     */
    public @Nullable TimePeriod<Day> getSnapshotRetentionPeriod() {
        return snapshotRetentionPeriod;
    }

    /**
     * Alters the creation state to support the specified administrative credentials.
     * @param adminUser the administrative user name to use in SQL or other data access APIs
     * @param adminPassword the administrative password to use in SQL or other data access APIs
     * @return this
     */
    public @Nonnull DataClusterCreateOptions havingAdminCredentials(@Nonnull String adminUser, @Nonnull String adminPassword) {
        this.adminUserName = adminUser;
        this.adminPassword = adminPassword;
        return this;
    }

    /**
     * Alters this data cluster object to reflect the the time window during which this data cluster is maintained
     * @param window the preferred maintenance window
     * @return this
     */
    public @Nonnull DataClusterCreateOptions havingMaintenanceWindow(@Nonnull TimeWindow window) {
        maintenanceWindow = window;
        return this;
    }

    /**
     * Alters the node count of the data cluster to be created
     * @param nodeCount the node count for the new data cluster
     * @return this
     */
    public @Nonnull DataClusterCreateOptions havingNodeCount(@Nonnegative int nodeCount) {
        this.nodeCount = nodeCount;
        return this;
    }

    /**
     * Alters the creation options to support creating the data cluster in the specified data center.
     * @param providerDataCenterId the target data center for the cluster
     * @return this
     */
    public @Nonnull DataClusterCreateOptions inDataCenter(@Nonnull String providerDataCenterId) {
        this.providerDataCenterId = providerDataCenterId;
        return this;
    }

    /**
     * @return the data in the cluster will be encrypted at rest
     */
    public boolean isEncrypted() {
        return encrypted;
    }

    /**
     * Alters the creation options to associate the newly created data cluster with the specified data cluster
     * firewalls. These firewalls must specifically exist for data clusters (e.g. appear in
     * {@link DataWarehouseSupport#listClusterFirewalls()}).
     * @param firewallIds a list of firewalls to associate with the data cluster
     * @return this
     */
    public @Nonnull DataClusterCreateOptions behindFirewalls(@Nonnull String ... firewallIds) {
        TreeSet<String> ids = new TreeSet<String>();

        if( providerFirewallIds != null ) {
            Collections.addAll(ids, providerFirewallIds);
        }
        Collections.addAll(ids, firewallIds);
        providerFirewallIds = ids.toArray(new String[ids.size()]);
        return this;
    }

    /**
     * Alters the creation options to support creating the data cluster with data at rest encrypted.
     * @return this
     */
    public @Nonnull DataClusterCreateOptions withEncryption() {
        this.encrypted = false;
        return this;
    }

    /**
     * Alters the creation options to support creating a data cluster with the specified parameter group assigned.
     * @param providerParameterGroupId the parameter group to assign to the new cluster
     * @return this
     */
    public @Nonnull DataClusterCreateOptions withParameterGroup(@Nonnull String providerParameterGroupId) {
        this.providerParameterGroupId = providerParameterGroupId;
        return this;
    }

    /**
     * Alters the data cluster to reflect the fact that the underlying cloud cluster has a snapshot retention period
     * of the specified length.
     * @param period the period for which automated snapshots are retained against the new cluster
     * @return this
     */
    public @Nonnull DataClusterCreateOptions withSnapshotsRetainedFor(@Nonnull TimePeriod<?> period) {
        snapshotRetentionPeriod = (TimePeriod<Day>)period.convertTo(TimePeriod.DAY);
        return this;
    }

    /**
     * Alters the creation options to support creating the data cluster WITHOUT data at rest encrypted.
     * @return this
     */
    public @Nonnull DataClusterCreateOptions withoutEncryption() {
        this.encrypted = false;
        return this;
    }
}
