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
package org.dasein.cloud.dc;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Locale;

/**
 * Description
 * <p>Created by stas: 11/10/2014 19:00</p>
 *
 * @author Stas Maksimov
 * @version 2015.01 initial version
 * @since 2015.01
 */
public abstract class AbstractDataCenterServices<T extends CloudProvider> implements DataCenterServices {
    private T provider;

    public AbstractDataCenterServices(T provider) {
        this.provider = provider;
    }

    /**
     * @return the provider object associated with any calls through this support object
     */
    protected final @Nonnull T getProvider() {
        return provider;
    }

    @Override
    public DataCenter getDataCenter( String providerDataCenterId ) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Getting a data center by id is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public Region getRegion( String providerRegionId ) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Getting a region by id is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    @Deprecated
    public String getProviderTermForDataCenter( Locale locale ) {
        try {
            return getCapabilities().getProviderTermForDataCenter(locale);
        }
        catch( Exception e ) {
            throw new RuntimeException("Unable to access data center capabilities of " + provider.getCloudName(), e);
        }
    }

    @Override
    @Deprecated
    public String getProviderTermForRegion( Locale locale ) {
        try {
            return getCapabilities().getProviderTermForRegion(locale);
        }
        catch( Exception e ) {
            throw new RuntimeException("Unable to access data center capabilities of " + provider.getCloudName(), e);
        }
    }

    @Override
    public Collection<DataCenter> listDataCenters( String providerRegionId ) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Listing data centers by region is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public Collection<Region> listRegions() throws InternalException, CloudException {
        throw new OperationNotSupportedException("Listing regions is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public @Nonnull Collection<ResourcePool> listResourcePools( String providerDataCenterId ) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Listing resource pools by data center id is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public @Nullable ResourcePool getResourcePool( String providerResourcePoolId ) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Getting a resource pool by id is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public @Nonnull Collection<StoragePool> listStoragePools() throws InternalException, CloudException {
        throw new OperationNotSupportedException("Listing storage pools is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public @Nonnull StoragePool getStoragePool( String providerStoragePoolId ) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Getting a storage pool by id is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public @Nonnull Collection<Folder> listVMFolders() throws InternalException, CloudException {
        throw new OperationNotSupportedException("Listing VM folders is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public @Nonnull Folder getVMFolder( String providerVMFolderId ) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Getting a VM folder by id is not currently implemented for " + getProvider().getCloudName());
    }
}
