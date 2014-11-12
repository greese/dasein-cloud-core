package org.dasein.cloud.platform;

import org.dasein.cloud.AbstractCapabilities;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;

import javax.annotation.Nonnull;

/**
 * Default implementation of RelationalDatabaseCapabilities interface to help with the deprecations
 * Created by stas on 12/11/2014.
 */
public abstract class AbstractRelationalDatabaseCapabilities<T extends CloudProvider> extends AbstractCapabilities<T> implements RelationalDatabaseCapabilities {

    public AbstractRelationalDatabaseCapabilities( @Nonnull T provider ) {
        super(provider);
    }

    @Override
    public boolean isSupportsFirewallRules() throws CloudException, InternalException {
        return supportsFirewallRules();
    }

    @Override
    public boolean isSupportsHighAvailability() throws CloudException, InternalException {
        return supportsHighAvailability();
    }

    @Override
    public boolean isSupportsLowAvailability() throws CloudException, InternalException {
        return supportsLowAvailability();
    }

    @Override
    public boolean isSupportsMaintenanceWindows() throws CloudException, InternalException {
        return supportsMaintenanceWindows();
    }

    @Override
    public boolean isSupportsAlterDatabase() throws CloudException, InternalException {
        return supportsAlterDatabase();
    }

    @Override
    public boolean isSupportsSnapshots() throws CloudException, InternalException {
        return supportsSnapshots();
    }
}
