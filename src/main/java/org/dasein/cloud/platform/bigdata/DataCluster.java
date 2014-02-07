/*
 * Copyright (C) 2014 enStratus Networks Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dasein.cloud.platform.bigdata;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Models a data warehousing cluster that consists of a number of database nodes and a shared
 * database endpoint to support big data processing.
 * <p>Created by George Reese: 2/7/14 12:08 PM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 added model (issue #100)
 */
public class DataCluster {
    private String  adminPassword;
    private String  adminUserName;
    private String  databaseName;
    private int     databasePort;
    private String  description;
    private boolean encrypted;
    private String  providerDataCenterId;
    private String  providerDataClusterId;
    private String  providerRegionId;
    private String  providerVlanId;
    private String  name;

    public @Nonnull String getDescription() {
        return description;
    }

    public @Nonnull String getProviderDataClusterId() {
        return providerDataClusterId;
    }

    public @Nullable String getProviderVlanId() {
        return providerVlanId;
    }

    public @Nonnull String getName() {
        return name;
    }

    @Override
    public @Nonnull String toString() {
        return providerDataClusterId;
    }
}
