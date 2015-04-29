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

package org.dasein.cloud.platform.bigdata;

import org.dasein.cloud.AbstractProviderService;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.ProviderContext;
import org.dasein.cloud.Requirement;
import org.dasein.cloud.Tag;
import org.dasein.cloud.network.FirewallReference;
import org.dasein.cloud.storage.CloudStorageLogging;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * Abstract foundation class for people implementing data warehouse support for specific clouds. This class provides
 * basic helper methods and default implementations of many methods.
 * <p>Created by George Reese: 2/9/14 4:41 PM</p>
 *
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 initial version (issue #100)
 */
public abstract class AbstractDataWarehouseSupport<T extends CloudProvider> extends AbstractProviderService<T> implements
        DataWarehouseSupport {

    protected AbstractDataWarehouseSupport(T provider) {
        super(provider);
    }

    @Override
    public void addSnapshotShare(@Nonnull String snapshotId, @Nonnull String accountNumber) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Snapshot sharing is not supported in " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

    @Override
    public void authorizeComputeFirewalls(@Nonnull String dataClusterFirewallId, @Nonnull FirewallReference... firewalls) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Compute firewall endpoints are not supported in " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

    @Override
    public void authorizeIPs(@Nonnull String dataClusterFirewallId, @Nonnull String... cidrs) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Data cluster firewalls are not supported in " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

    @Override
    public void disableLogging(@Nonnull String clusterId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Logging is not supported for data clusters in " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

    @Override
    public void enableLogging(@Nonnull String clusterId, @Nonnull String bucket, @Nonnull String prefix) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Logging is not supported for data clusters in " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

    @Override
    public @Nullable DataCluster getCluster(@Nonnull String clusterId) throws CloudException, InternalException {
        for( DataCluster cluster : listClusters() ) {
            if( cluster.getProviderDataClusterId().equals(clusterId) ) {
                return cluster;
            }
        }
        return null;
    }

    @Override
    public @Nullable DataClusterFirewall getClusterFirewall(@Nonnull String firewallId) throws CloudException, InternalException {
        for( DataClusterFirewall firewall : listClusterFirewalls() ) {
            if( firewall.getProviderFirewallId().equals(firewallId) ) {
                return firewall;
            }
        }
        return null;
    }

    @Override
    public @Nullable CloudStorageLogging getClusterLoggingStatus(@Nonnull String clusterId) throws CloudException, InternalException {
        return null;
    }

    @Override
    public @Nullable DataClusterProduct getClusterProduct(@Nonnull String productId) throws CloudException, InternalException {
        for( DataClusterProduct product : listClusterProducts() ) {
            if( product.getProviderProductId().equals(productId) ) {
                return product;
            }
        }
        return null;
    }

    @Override
    public @Nullable DataClusterSnapshot getClusterSnapshot(@Nonnull String snapshotId) throws CloudException, InternalException {
        for( DataClusterSnapshot snapshot : listClusterSnapshots() ) {
            if( snapshot.getProviderSnapshotId().equals(snapshotId) ) {
                return snapshot;
            }
        }
        return null;
    }

    @Override
    public void removeAllSnapshotShares(@Nonnull String snapshotId) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void revokeComputeFirewalls(@Nonnull String dataClusterFirewallId, @Nonnull FirewallReference... firewalls) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Compute firewall endpoints are not supported in " + getContext().getRegionId() + " of " + getProvider().getCloudName());

    }

    @Override
    public void revokeIPs(@Nonnull String dataClusterFirewallId, @Nonnull String... cidrs) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Data cluster firewalls are not supported in " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

    @Override
    public void rotateEncryptionKeys(@Nonnull String clusterId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Encryption is not supported in " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

    @Override
    public void updateClusterTags(@Nonnull String[] clusterIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        for( String id : clusterIds ) {
            updateSnapshotTags(id, tags);
        }
    }

    @Override
    public void updateSnapshotTags(@Nonnull String[] snapshotIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        for( String id : snapshotIds ) {
            updateSnapshotTags(id, tags);
        }
    }
}
