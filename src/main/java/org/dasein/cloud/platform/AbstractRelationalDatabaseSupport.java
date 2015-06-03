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

package org.dasein.cloud.platform;

import org.dasein.cloud.*;
import org.dasein.cloud.util.TagUtils;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Locale;

/**
 * Description
 * <p>Created by stas: 11/10/2014 21:39</p>
 *
 * @author Stas Maksimov
 * @version 2015.01 initial version
 * @since 2015.01
 */
public abstract class AbstractRelationalDatabaseSupport<T extends CloudProvider> extends AbstractProviderService<T> implements RelationalDatabaseSupport {

    protected AbstractRelationalDatabaseSupport(T provider) {
        super(provider);
    }

    @Override
    public void addAccess( String providerDatabaseId, String sourceCidr ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Adding access is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public void alterDatabase( String providerDatabaseId, boolean applyImmediately, String productSize, int storageInGigabytes, String configurationId, String newAdminUser, String newAdminPassword, int newPort, int snapshotRetentionInDays, TimeWindow preferredMaintenanceWindow, TimeWindow preferredBackupWindow ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Altering database is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public String createFromScratch( String dataSourceName, DatabaseProduct product, String databaseVersion, String withAdminUser, String withAdminPassword, int hostPort ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Creation of new databases is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public String createFromLatest( String dataSourceName, String providerDatabaseId, String productSize, String providerDataCenterId, int hostPort ) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Creation of copy databases is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public String createFromSnapshot( String dataSourceName, String providerDatabaseId, String providerDbSnapshotId, String productSize, String providerDataCenterId, int hostPort ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Creation of databases from snapshots is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public String createFromTimestamp( String dataSourceName, String providerDatabaseId, long beforeTimestamp, String productSize, String providerDataCenterId, int hostPort ) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Creation of databases from a timestamp is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public DatabaseConfiguration getConfiguration( String providerConfigurationId ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Configuration retrieval is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public Database getDatabase( String providerDatabaseId ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Database retrieval is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public Iterable<DatabaseEngine> getDatabaseEngines() throws CloudException, InternalException {
        throw new OperationNotSupportedException("Database engines retrieval is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public String getDefaultVersion( @Nonnull DatabaseEngine forEngine ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Default database version retrieval is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public @Nonnull Iterable<String> getSupportedVersions( @Nonnull DatabaseEngine forEngine ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Supported versions retrieval is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public @Nonnull Iterable<DatabaseProduct> listDatabaseProducts( @Nonnull DatabaseEngine forEngine ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Listing of database products is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public DatabaseSnapshot getSnapshot( String providerDbSnapshotId ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Snapshot retrieval is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public Iterable<String> listAccess( String toProviderDatabaseId ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Database access retrieval is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public Iterable<DatabaseConfiguration> listConfigurations() throws CloudException, InternalException {
        throw new OperationNotSupportedException("Database configuration retrieval is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public @Nonnull Iterable<ResourceStatus> listDatabaseStatus() throws CloudException, InternalException {
        throw new OperationNotSupportedException("Database status retrieval is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public Iterable<Database> listDatabases() throws CloudException, InternalException {
        throw new OperationNotSupportedException("Database retrieval is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public Collection<ConfigurationParameter> listParameters( String forProviderConfigurationId ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Database configuration retrieval is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public Iterable<DatabaseSnapshot> listSnapshots( String forOptionalProviderDatabaseId ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Database snapshots retrieval is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public void removeConfiguration( String providerConfigurationId ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Configuration removal is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public void removeDatabase( String providerDatabaseId ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Database removal is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public void removeSnapshot( String providerSnapshotId ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Snapshot removal is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public void resetConfiguration( String providerConfigurationId, String... parameters ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Configuration reset is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public void restart( String providerDatabaseId, boolean blockUntilDone ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Database restart is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public void revokeAccess( String providerDatabaseId, String sourceCide ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Database access revoking is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public void updateConfiguration( String providerConfigurationId, ConfigurationParameter... parameters ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Configuration update is not currently implemented for "+getProvider().getCloudName());
    }

    @Override
    public DatabaseSnapshot snapshot( String providerDatabaseId, String name ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Snapshot functionality is not currently implemented for "+getProvider().getCloudName());
    }

    //
    // New Backup section
    //

    /*
     * Obtain a valid DatabaseBackup object for the given database instance where the backup was taken prior to the given time.
     *
     * Throws CloudException on failure
     */
    public DatabaseBackup getUsableBackup(String providerDbId, String beforeTimestamp) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Snapshot functionality is not currently implemented for "+getProvider().getCloudName());
    }

    /*
     * Obtain a list of DatabaseBackup objects for a given database, or for all databases if null.
     * 
     * Throws CloudException on failure
     */
    public Iterable<DatabaseBackup> listBackups(String forOptionalProviderDatabaseId) throws CloudException, InternalException{
        throw new OperationNotSupportedException("Snapshot functionality is not currently implemented for "+getProvider().getCloudName());
    }

    /*
     * Create a new database from the passed in backup object
     * 
     * Throws CloudException on failure
     */
    public void createFromBackup(DatabaseBackup backup, String databaseCloneToName) throws CloudException, InternalException{
        throw new OperationNotSupportedException("Snapshot functionality is not currently implemented for "+getProvider().getCloudName());
    }

    /*
     * Remove specified database backup
     * 
     * Throws CloudException on failure
     */
    public void removeBackup(DatabaseBackup backup) throws CloudException, InternalException{
        throw new OperationNotSupportedException("Snapshot functionality is not currently implemented for "+getProvider().getCloudName());
    }

    /*
     * Restore the passed in DatabaseBackup to its current database instance.
     * 
     * Throws CloudException on failure
     */
    public void restoreBackup(DatabaseBackup backup) throws CloudException, InternalException{
        throw new OperationNotSupportedException("Snapshot functionality is not currently implemented for "+getProvider().getCloudName());
    }
    
    @Override
    public void removeTags(@Nonnull String providerDatabaseId, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void removeTags(@Nonnull String[] providerDatabaseIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        for( String id : providerDatabaseIds ) {
            removeTags(id, tags);
        }
    }
    
    @Override
    public void updateTags(@Nonnull String providerDatabaseId, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void updateTags(@Nonnull String[] providerDatabaseIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        for( String id : providerDatabaseIds ) {
            updateTags(id, tags);
        }
    }

    @Override
    public void setTags( @Nonnull String providerDatabaseId, @Nonnull Tag... tags ) throws CloudException, InternalException {
        setTags(new String[]{providerDatabaseId}, tags);
    }

    @Override
    public void setTags( @Nonnull String[] providerDatabaseIds, @Nonnull Tag... tags ) throws CloudException, InternalException {
        for( String id : providerDatabaseIds ) {
            Tag[] collectionForDelete = TagUtils.getTagsForDelete(getDatabase(id).getTags(), tags);

            if( collectionForDelete.length != 0) {
                removeTags(id, collectionForDelete);
            }

            updateTags(id, tags);
        }
    }

}