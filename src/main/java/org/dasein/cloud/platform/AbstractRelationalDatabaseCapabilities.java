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

import org.dasein.cloud.AbstractCapabilities;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.util.NamingConstraints;

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

    @Override
    public NamingConstraints getRelationalDatabaseNamingConstraints() {
        return this.getProvider().getNamingRules().getRelationalDatabaseNameRules();
    }
}
