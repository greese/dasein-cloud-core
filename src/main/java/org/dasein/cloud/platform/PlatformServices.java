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

import org.dasein.cloud.platform.bigdata.DataWarehouseSupport;

import javax.annotation.Nullable;

/**
 * Provides access to services that support platform as a service (PaaS) operations.
 * @since unknown
 * @version 2014.03 added data warehousing support access (issue #100)
 */
public interface PlatformServices {
    public abstract @Nullable CDNSupport getCDNSupport();

    /**
     * @return access to any support for data warehousing functionality, or <code>null</code> if no support exists
     */
    public abstract @Nullable DataWarehouseSupport getDataWarehouseSupport();

    public abstract @Nullable KeyValueDatabaseSupport getKeyValueDatabaseSupport();
    
    public abstract @Nullable MQSupport getMessageQueueSupport();
    
    public abstract @Nullable PushNotificationSupport getPushNotificationSupport();
    
    public abstract @Nullable RelationalDatabaseSupport getRelationalDatabaseSupport();

    public abstract @Nullable MonitoringSupport getMonitoringSupport();
    
    public abstract boolean hasCDNSupport();

    /**
     * @return true if this cloud supports data warehousing functionality in this region
     */
    public abstract boolean hasDataWarehouseSupport();

    public abstract boolean hasKeyValueDatabaseSupport();
    
    public abstract boolean hasMessageQueueSupport();
    
    public abstract boolean hasPushNotificationSupport();
    
    public abstract boolean hasRelationalDatabaseSupport();

   public abstract boolean hasMonitoringSupport();

}
