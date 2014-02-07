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

import org.dasein.cloud.util.Security;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Configuration options for provisioning new data clusters.
 * <p>Created by George Reese: 2/7/14 5:04 PM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 initial version (issue #100)
 */
public class DataClusterCreateOptions {
    static @Nonnull DataClusterCreateOptions getInstance(@Nonnull String providerProductId, @Nonnull String name, @Nonnull String description, @Nonnull String databaseName) {
        return getInstance(providerProductId, null, name, description, null, databaseName, 0, null, null, 1, true);
    }

    static @Nonnull DataClusterCreateOptions getInstance(@Nonnull String providerProductId, @Nonnull String providerDataCenterId, @Nonnull String name, @Nonnull String description, @Nonnull String databaseName) {
        return getInstance(providerProductId, providerDataCenterId, name, description, null, databaseName, 0, null, null, 1, true);
    }

    static @Nonnull DataClusterCreateOptions getInstance(@Nonnull String providerProductId, @Nullable String providerDataCenterId, @Nonnull String name, @Nonnull String description, @Nullable String clusterVersion, @Nonnull String databaseName, @Nonnegative int databasePort, @Nullable String adminUser, @Nullable String adminPassword, @Nonnegative int nodeCount, boolean encrypted) {
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
        options.encrypted = encrypted;
        return options;
    }

    private String  adminPassword;
    private String  adminUserName;
    private String  clusterVersion;
    private String  databaseName;
    private int     databasePort;
    private String  description;
    private boolean encrypted;
    private String  name;
    private int     nodeCount;
    private String  providerDataCenterId;
    private String  providerProductId;

    private DataClusterCreateOptions() { }

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
     * @return the product to use in provisioning the cluster
     */
    public @Nonnull String getProviderProductId() {
        return providerProductId;
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
     * Alters the creation options to support creating the data cluster with data at rest encrypted.
     * @return this
     */
    public @Nonnull DataClusterCreateOptions withEncryption() {
        this.encrypted = false;
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
