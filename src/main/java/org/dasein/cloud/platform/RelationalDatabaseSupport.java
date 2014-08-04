/**
 * Copyright (C) 2009-2014 Dell, Inc.
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

import java.util.Collection;
import java.util.Locale;

import org.dasein.cloud.AccessControlledService;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.ResourceStatus;
import org.dasein.cloud.TimeWindow;
import org.dasein.cloud.identity.ServiceAction;

import javax.annotation.Nonnull;

/**
 * Relational Database Support methods
 *
 * @author Stas Maksimov
 * @version 2014.07 renamed getDatabaseProducts to listDatabaseProducts for API consistency
 * @since ?
 */
public interface RelationalDatabaseSupport extends AccessControlledService {
    static public final ServiceAction ANY                    = new ServiceAction("RDBMS:ANY");

    static public final ServiceAction ALTER_DB               = new ServiceAction("RDBMS:ALTER_DB");
    static public final ServiceAction CREATE_RDBMS           = new ServiceAction("RDBMS:CREATE_RDBMS");
    static public final ServiceAction CREATE_RDBMS_SNAP      = new ServiceAction("RDBMS:CREATE_RDBMS_SNAP");
    static public final ServiceAction CREATE_RDBMS_FROM_SNAP = new ServiceAction("RDBMS:CREATE_RDBMS_FROM_SNAP");
    static public final ServiceAction GET_RDBMS              = new ServiceAction("RDBMS:GET_RDBMS");
    static public final ServiceAction GET_RDBMS_SNAP         = new ServiceAction("RDBMS:GET_RDBMS_SNAP");
    static public final ServiceAction LIST_RDBMS             = new ServiceAction("RDBMS:LIST_RDBMS");
    static public final ServiceAction LIST_RDBMS_SNAP        = new ServiceAction("RDBMS:LIST_RDBMS_SNAP");
    static public final ServiceAction REMOVE_RDBMS           = new ServiceAction("RDBMS:REMOVE_RDBMS");
    static public final ServiceAction REMOVE_RDBMS_SNAP      = new ServiceAction("RDBMS:REMOVE_RDBMS_SNAP");
    static public final ServiceAction RESTART                = new ServiceAction("RDBMS:RESTART");
    static public final ServiceAction UPDATE_RDBMS_FIREWALL  = new ServiceAction("RDBMS:UPDATE_FIREWALL");

    public void addAccess(String providerDatabaseId, String sourceCidr) throws CloudException, InternalException;
    
    public void alterDatabase(String providerDatabaseId, boolean applyImmediately, String productSize, int storageInGigabytes, String configurationId, String newAdminUser, String newAdminPassword, int newPort, int snapshotRetentionInDays, TimeWindow preferredMaintenanceWindow, TimeWindow preferredBackupWindow) throws CloudException, InternalException;
    
    public String createFromScratch(String dataSourceName, DatabaseProduct product, String databaseVersion, String withAdminUser, String withAdminPassword, int hostPort) throws CloudException, InternalException;
    
    public String createFromLatest(String dataSourceName, String providerDatabaseId, String productSize, String providerDataCenterId, int hostPort) throws InternalException, CloudException;

    public String createFromSnapshot(String dataSourceName, String providerDatabaseId, String providerDbSnapshotId, String productSize, String providerDataCenterId, int hostPort) throws CloudException, InternalException;

    public String createFromTimestamp(String dataSourceName, String providerDatabaseId, long beforeTimestamp, String productSize, String providerDataCenterId, int hostPort) throws InternalException, CloudException;

    /**
     * Provides access to meta-data about RDS capabilities in the current region of this cloud.
     *
     * @return a description of the features supported by this region of this cloud
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException    an error occurred within the cloud provider
     */
    public @Nonnull RelationalDatabaseCapabilities getCapabilities() throws InternalException, CloudException;

    public DatabaseConfiguration getConfiguration(String providerConfigurationId) throws CloudException, InternalException;

    public Database getDatabase(String providerDatabaseId) throws CloudException, InternalException;

    public Iterable<DatabaseEngine> getDatabaseEngines() throws CloudException, InternalException;
    
    public String getDefaultVersion(DatabaseEngine forEngine) throws CloudException, InternalException;
    
    public Iterable<String> getSupportedVersions(DatabaseEngine forEngine) throws CloudException, InternalException;

    /**
     * List supported database products
     * @param forEngine database engine, e.g. MySQL, SQL Server EE, etc.
     * @return iteration of the database products supported by the engine
     * @throws CloudException
     * @throws InternalException
     * @deprecated since 2014.07 for consistency
     * @see org.dasein.cloud.platform.RelationalDatabaseSupport#listDatabaseProducts(DatabaseEngine)
     *
     */

    @Deprecated
    public Iterable<DatabaseProduct> getDatabaseProducts(DatabaseEngine forEngine) throws CloudException, InternalException;
    /**
     * List supported database products
     * @param forEngine database engine, e.g. MySQL, SQL Server EE, etc.
     * @return iteration of the database products supported by the engine
     * @throws CloudException
     * @throws InternalException
     * @since 2014.07 for consistency
     */
    public Iterable<DatabaseProduct> listDatabaseProducts(DatabaseEngine forEngine) throws CloudException, InternalException;

    @Deprecated
    public String getProviderTermForDatabase(Locale locale);

    @Deprecated
    public String getProviderTermForSnapshot(Locale locale);

    public DatabaseSnapshot getSnapshot(String providerDbSnapshotId) throws CloudException, InternalException;
    
    public boolean isSubscribed() throws CloudException, InternalException;

    @Deprecated
    public boolean isSupportsFirewallRules();

    @Deprecated
    public boolean isSupportsHighAvailability() throws CloudException, InternalException;

    @Deprecated
    public boolean isSupportsLowAvailability() throws CloudException, InternalException;

    @Deprecated
    public boolean isSupportsMaintenanceWindows();

    @Deprecated
    public boolean isSupportsSnapshots();
    
    public Iterable<String> listAccess(String toProviderDatabaseId) throws CloudException, InternalException;
    
    public Iterable<DatabaseConfiguration> listConfigurations() throws CloudException, InternalException;

    public @Nonnull Iterable<ResourceStatus> listDatabaseStatus() throws CloudException, InternalException;

    public Iterable<Database> listDatabases() throws CloudException, InternalException;
    
    public Collection<ConfigurationParameter> listParameters(String forProviderConfigurationId) throws CloudException, InternalException;
    
    public Iterable<DatabaseSnapshot> listSnapshots(String forOptionalProviderDatabaseId) throws CloudException, InternalException;

    public void removeConfiguration(String providerConfigurationId) throws CloudException, InternalException;
    
    public void removeDatabase(String providerDatabaseId) throws CloudException, InternalException;
    
    public void removeSnapshot(String providerSnapshotId) throws CloudException, InternalException;
    
    public void resetConfiguration(String providerConfigurationId, String ... parameters) throws CloudException, InternalException;
    
    public void restart(String providerDatabaseId, boolean blockUntilDone) throws CloudException, InternalException;
    
    public void revokeAccess(String providerDatabaseId, String sourceCide) throws CloudException, InternalException;
    
    public void updateConfiguration(String providerConfigurationId, ConfigurationParameter ... parameters) throws CloudException, InternalException;
    
    public DatabaseSnapshot snapshot(String providerDatabaseId, String name) throws CloudException, InternalException;
}
