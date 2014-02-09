/*
 * Copyright (C) 2014 Dell, Inc.
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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Snapshots of data clusters that may be used to create/restore data clusters.
 * <p>Created by George Reese: 2/8/14 7:33 PM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 initial version (issue #100)
 */
public class DataClusterSnapshot {
    private String                   adminUserName;
    private boolean                  automated;
    private String                   clusterVersion;
    private long                     creationTimestamp;
    private DataClusterSnapshotState currentState;
    private String                   databaseName;
    private int                      databasePort;
    private String                   description;
    private String                   name;
    private int                      nodeCount;
    private String                   providerClusterId;
    private String                   providerDataCenterId;
    private String                   providerOwnerId;
    private String                   providerProductId;
    private String                   providerRegionId;
    private String                   providerSnapshotId;

    private DataClusterSnapshot() { }

    @Override
    public boolean equals(@Nullable Object other) {
        if( other == null ) {
            return false;
        }
        if( other == this ) {
            return true;
        }
        if( !getClass().getName().equals(other.getClass().getName()) ) {
            return false;
        }
        DataClusterSnapshot s = (DataClusterSnapshot)other;

        return (providerOwnerId.equals(s.providerOwnerId) && providerRegionId.equals(s.providerRegionId) && providerSnapshotId.equals(s.providerSnapshotId));
    }

    public @Nonnull String getAdminUserName() {
        return adminUserName;
    }

    public @Nonnull String getClusterVersion() {
        return clusterVersion;
    }

    public @Nonnegative long getCreationTimestamp() {
        return creationTimestamp;
    }

    public @Nonnull DataClusterSnapshotState getCurrentState() {
        return currentState;
    }

    public @Nonnull String getDatabaseName() {
        return databaseName;
    }

    public @Nonnegative int getDatabasePort() {
        return databasePort;
    }

    public @Nonnull String getDescription() {
        return description;
    }

    public @Nonnull String getName() {
        return name;
    }

    public @Nonnegative int getNodeCount() {
        return nodeCount;
    }

    public @Nullable String getProviderClusterId() {
        return providerClusterId;
    }

    public @Nullable String getProviderDataCenterId() {
        return providerDataCenterId;
    }

    public @Nonnull String getProviderOwnerId() {
        return providerOwnerId;
    }

    public @Nonnull String getProviderProductId() {
        return providerProductId;
    }

    public @Nonnull String getProviderRegionId() {
        return providerRegionId;
    }

    public @Nonnull String getProviderSnapshotId() {
        return providerSnapshotId;
    }

    @Override
    public int hashCode() {
        return (providerOwnerId + "/" + providerRegionId + "/" + providerSnapshotId).hashCode();
    }

    @Override
    public @Nonnull String toString() {
        return providerSnapshotId;
    }
}
