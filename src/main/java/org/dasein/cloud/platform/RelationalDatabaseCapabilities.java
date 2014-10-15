package org.dasein.cloud.platform;

import org.dasein.cloud.Capabilities;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;

import javax.annotation.Nonnull;
import java.util.Locale;

/**
 * Created by Drew Lyall: 01/08/14 12:29
 * @author Drew Lyall
 */
public interface RelationalDatabaseCapabilities extends Capabilities{
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
    public boolean isSupportsFirewallRules() throws CloudException, InternalException;

    /**
     * Indicates whether the provider supports HA RDS support
     * @return true if HA support is available
     * @throws CloudException
     * @throws InternalException
     */
    public boolean isSupportsHighAvailability() throws CloudException, InternalException;

    /**
     * Indicates whether the provider supports Low Availability RDS support
     * @return true if Low Availability support is available
     * @throws CloudException
     * @throws InternalException
     */
    public boolean isSupportsLowAvailability() throws CloudException, InternalException;

    /**
     * Indicates whether the cloud provides support for RDS maintenance windows
     * @return true if maintenance windows are supported
     */
    public boolean isSupportsMaintenanceWindows() throws CloudException, InternalException;

    /**
     * Indicates whether the provider allows the modification of running databases
     * @return true if modification is allowed
     */
    public boolean isSupportsAlterDatabase() throws CloudException, InternalException;

    /**
     * Indicates whether the provider supports the snapshotting of databases
     * @return true if snapshots are supported
     */
    public boolean isSupportsSnapshots() throws CloudException, InternalException;

    /**
     * The term the provider uses to describe the DB Backup.
     * @param locale the language in which the term should be presented
     * @return the provider term for DB Snapshot
     */
    public @Nonnull String getProviderTermForBackup( Locale locale );

    /** 
     * Indicates whether the cloud provides support for RDS backups
     * @return boolean
     * @throws CloudException
     * @throws InternalException
     */
    public boolean isSupportsDatabaseBackups() throws CloudException, InternalException;

    public boolean isSupportsScheduledDatabaseBackups() throws CloudException, InternalException;

    public boolean isSupportsDemandBackups() throws CloudException, InternalException;

    public boolean isSupportsRestoreBackup() throws CloudException, InternalException;

    public boolean isSupportsDeleteBackup() throws CloudException, InternalException;
}
