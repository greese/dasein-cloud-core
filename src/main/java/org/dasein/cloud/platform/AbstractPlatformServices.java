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

import org.dasein.cloud.AbstractProviderService;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.platform.bigdata.DataWarehouseSupport;

import javax.annotation.Nullable;

/**
 * Default implementation of platform services for a cloud with support for no platform services functionality.
 * Implementations will override this class to manage support for the features relevant to that cloud.
 * @since unknown
 * @version 2014.03 added support for data warehouse functionality (issue #100)
 */
public abstract class AbstractPlatformServices<T extends CloudProvider> extends AbstractProviderService<T> implements PlatformServices {

    protected AbstractPlatformServices(T provider) {
        super(provider);
    }

    @Override
    public @Nullable CDNSupport getCDNSupport() {
        return null;
    }

    @Override
    public @Nullable DataWarehouseSupport getDataWarehouseSupport() {
        return null;
    }

    @Override
    public @Nullable KeyValueDatabaseSupport getKeyValueDatabaseSupport() {
        return null;
    }

    @Override
    public @Nullable MQSupport getMessageQueueSupport() {
        return null;
    }

    @Override
    public @Nullable PushNotificationSupport getPushNotificationSupport() {
        return null;
    }

    @Override
    public @Nullable RelationalDatabaseSupport getRelationalDatabaseSupport() {
        return null;
    }

    @Override
    public @Nullable MonitoringSupport getMonitoringSupport() {
        return null;
    }

    @Override
    public boolean hasCDNSupport() {
        return (getCDNSupport() != null);
    }

    @Override
    public boolean hasDataWarehouseSupport() {
        return (getDataWarehouseSupport() != null);
    }

    @Override
    public boolean hasKeyValueDatabaseSupport() {
        return (getKeyValueDatabaseSupport() != null);
    }

    @Override
    public boolean hasMessageQueueSupport() {
        return (getMessageQueueSupport() != null);
    }

    @Override
    public boolean hasPushNotificationSupport() {
        return (getPushNotificationSupport() != null);
    }

    @Override
    public boolean hasRelationalDatabaseSupport() {
        return (getRelationalDatabaseSupport() != null);
    }

    @Override
    public boolean hasMonitoringSupport() {
       return getMonitoringSupport()!= null;
    }

}
