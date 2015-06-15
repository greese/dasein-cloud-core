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

import org.dasein.cloud.Capabilities;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.util.NamingConstraints;

import javax.annotation.Nonnull;
import java.util.Locale;

/**
 * Created by Drew Lyall: 01/08/14 12:29
 * @author Drew Lyall
 */
public interface RelationalDatabaseCapabilities extends Capabilities{
    /**
     * The term the provider uses to describe the DB Backup.
     * @param locale the language in which the term should be presented
     * @return the provider term for DB Snapshot
     */
    public @Nonnull String getProviderTermForBackup( Locale locale );

    /**
     * The term the provider uses to describe the Database.
     * @param locale the language in which the term should be presented
     * @return the provider term for Databases
     */
    public @Nonnull String getProviderTermForDatabase(Locale locale);

    /**
     * The term the provider uses to describe the DB Snapshot.
     * @param locale the language in which the term should be presented
     * @return the provider term for DB Snapshot
     */
    public @Nonnull String getProviderTermForSnapshot(Locale locale);

    /**
     * Indicates whether the RDS Support includes firewall rules
     * @return true if support includes firewall rules
     * @throws CloudException an error occurred in the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public boolean supportsFirewallRules() throws CloudException, InternalException;

    /**
     * Indicates whether the provider supports HA RDS support
     * @return true if HA support is available
     * @throws CloudException an error occurred in the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public boolean supportsHighAvailability() throws CloudException, InternalException;

    /**
     * Indicates whether the provider supports Low Availability RDS support
     * @return true if Low Availability support is available
     * @throws CloudException an error occurred in the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public boolean supportsLowAvailability() throws CloudException, InternalException;

    /**
     * Indicates whether the cloud provides support for RDS maintenance windows
     * @return true if maintenance windows are supported
     * @throws CloudException an error occurred in the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public boolean supportsMaintenanceWindows() throws CloudException, InternalException;

    /**
     * Indicates whether the provider allows the modification of running databases
     * @return true if modification is allowed
     * @throws CloudException an error occurred in the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public boolean supportsAlterDatabase() throws CloudException, InternalException;

    /**
     * Indicates whether the provider supports the snapshotting of databases
     * @return true if snapshots are supported
     * @throws CloudException an error occurred in the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public boolean supportsSnapshots() throws CloudException, InternalException;

    /**
     * Indicates whether the cloud provides support for RDS backups
     * @return boolean
     * @throws CloudException an error occurred in the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public boolean supportsDatabaseBackups() throws CloudException, InternalException;

    public boolean supportsScheduledDatabaseBackups() throws CloudException, InternalException;

    public boolean supportsDemandBackups() throws CloudException, InternalException;

    public boolean supportsRestoreBackup() throws CloudException, InternalException;

    public boolean supportsDeleteBackup() throws CloudException, InternalException;

    public boolean supportsBackupConfigurations() throws CloudException, InternalException;

    /**
     * Identifies the naming conventions that constrain how databases may be named (friendly name) in this cloud.
     * @return naming conventions that constrain database naming
     * @throws CloudException an error occurred querying the cloud for naming constraints
     * @throws InternalException an error occurred assembling the naming constraints object
     */
    public @Nonnull NamingConstraints getRelationalDatabaseNamingConstraints() throws CloudException, InternalException;
}
