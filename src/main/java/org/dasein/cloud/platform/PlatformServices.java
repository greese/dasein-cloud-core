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

import org.dasein.cloud.platform.bigdata.DataWarehouseSupport;

import javax.annotation.Nullable;

/**
 * Provides access to services that support platform as a service (PaaS) operations.
 * @since unknown
 * @version 2014.03 added data warehousing support access (issue #100)
 */
public interface PlatformServices {
    public @Nullable CDNSupport getCDNSupport();

    /**
     * @return access to any support for data warehousing functionality, or <code>null</code> if no support exists
     */
    public @Nullable DataWarehouseSupport getDataWarehouseSupport();

    public @Nullable KeyValueDatabaseSupport getKeyValueDatabaseSupport();
    
    public @Nullable MQSupport getMessageQueueSupport();
    
    public @Nullable PushNotificationSupport getPushNotificationSupport();
    
    public @Nullable RelationalDatabaseSupport getRelationalDatabaseSupport();

    public @Nullable MonitoringSupport getMonitoringSupport();
    
    public boolean hasCDNSupport();

    /**
     * @return true if this cloud supports data warehousing functionality in this region
     */
    public boolean hasDataWarehouseSupport();

    public boolean hasKeyValueDatabaseSupport();
    
    public boolean hasMessageQueueSupport();
    
    public boolean hasPushNotificationSupport();
    
    public boolean hasRelationalDatabaseSupport();

   public boolean hasMonitoringSupport();

}
