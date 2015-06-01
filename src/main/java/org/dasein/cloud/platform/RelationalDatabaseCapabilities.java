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
     */
    public boolean supportsFirewallRules() throws CloudException, InternalException;

    /**
     * Indicates whether the provider supports HA RDS support
     * @return true if HA support is available
     * @throws CloudException
     * @throws InternalException
     */
    public boolean supportsHighAvailability() throws CloudException, InternalException;

    /**
     * Indicates whether the provider supports Low Availability RDS support
     * @return true if Low Availability support is available
     * @throws CloudException
     * @throws InternalException
     */
    public boolean supportsLowAvailability() throws CloudException, InternalException;

    /**
     * Indicates whether the cloud provides support for RDS maintenance windows
     * @return true if maintenance windows are supported
     */
    public boolean supportsMaintenanceWindows() throws CloudException, InternalException;

    /**
     * Indicates whether the provider allows the modification of running databases
     * @return true if modification is allowed
     */
    public boolean supportsAlterDatabase() throws CloudException, InternalException;

    /**
     * Indicates whether the provider supports the snapshotting of databases
     * @return true if snapshots are supported
     */
    public boolean supportsSnapshots() throws CloudException, InternalException;

    /**
     * Indicates whether the cloud provides support for RDS backups
     * @return boolean
     * @throws CloudException
     * @throws InternalException
     */
    public boolean supportsDatabaseBackups() throws CloudException, InternalException;

    public boolean supportsScheduledDatabaseBackups() throws CloudException, InternalException;

    public boolean supportsDemandBackups() throws CloudException, InternalException;

    public boolean supportsRestoreBackup() throws CloudException, InternalException;

    public boolean supportsDeleteBackup() throws CloudException, InternalException;

    public boolean supportsBackupConfigurations() throws CloudException, InternalException;


    /*******************************************************************************
     * isSupportsXXX methods are deprecated for naming consistency with other APIs *
     *******************************************************************************/

    /**
     * Indicates whether the RDS Support includes firewall rules
     * @deprecated
     * @see org.dasein.cloud.platform.RelationalDatabaseCapabilities#supportsFirewallRules()
     * @return true if support includes firewall rules
     */
    public boolean isSupportsFirewallRules() throws CloudException, InternalException;

    /**
     * Indicates whether the provider supports HA RDS support
     * @deprecated
     * @return true if HA support is available
     * @see RelationalDatabaseCapabilities#supportsHighAvailability()
     * @throws CloudException
     * @throws InternalException
     */
    public boolean isSupportsHighAvailability() throws CloudException, InternalException;

    /**
     * Indicates whether the provider supports Low Availability RDS support
     * @deprecated
     * @see RelationalDatabaseCapabilities#supportsLowAvailability()
     * @return true if Low Availability support is available
     * @throws CloudException
     * @throws InternalException
     */
    public boolean isSupportsLowAvailability() throws CloudException, InternalException;

    /**
     * Indicates whether the cloud provides support for RDS maintenance windows
     * @deprecated
     * @see RelationalDatabaseCapabilities#supportsMaintenanceWindows()
     * @return true if maintenance windows are supported
     */
    public boolean isSupportsMaintenanceWindows() throws CloudException, InternalException;

    /**
     * Indicates whether the provider allows the modification of running databases
     * @deprecated
     * @see RelationalDatabaseCapabilities#supportsAlterDatabase()
     * @return true if modification is allowed
     */
    public boolean isSupportsAlterDatabase() throws CloudException, InternalException;

    /**
     * Indicates whether the provider supports the snapshot-ing of databases
     * @deprecated
     * @see RelationalDatabaseCapabilities#supportsSnapshots()
     * @return true if snapshots are supported
     */
    public boolean isSupportsSnapshots() throws CloudException, InternalException;

    public NamingConstraints getRelationalDatabaseNamingConstraints();
}
