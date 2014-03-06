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

package org.dasein.cloud.compute;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.ProviderContext;
import org.dasein.cloud.Requirement;
import org.dasein.cloud.ResourceStatus;
import org.dasein.cloud.Tag;
import org.dasein.cloud.identity.ServiceAction;
import org.dasein.util.uom.storage.Gigabyte;
import org.dasein.util.uom.storage.Storage;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

/**
 * Implements the basic functionality of volume support so that it is easier to rapidly craft a support class for
 * each cloud.
 * <p>Created by George Reese: 1/31/13 11:04 AM</p>
 * @author George Reese
 * @version 2013.04
 * @since 2013.04
 */
public abstract class AbstractVolumeSupport implements VolumeSupport {
    private CloudProvider provider;

    public AbstractVolumeSupport(@Nonnull CloudProvider provider) {
        this.provider = provider;
    }

    @Override
    public void attach(@Nonnull String volumeId, @Nonnull String toServer, @Nonnull String deviceId) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Support for attaching volumes is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public final @Nonnull String create(@Nullable String fromSnapshot, @Nonnegative int sizeInGb, @Nonnull String inZone) throws InternalException, CloudException {
        Storage<Gigabyte> storage = new Storage<Gigabyte>(sizeInGb, Storage.GIGABYTE);

        if( getVolumeProductRequirement().equals(Requirement.REQUIRED) ) {
            VolumeProduct lastChance = null;
            VolumeProduct closest = null;

            for( VolumeProduct product : listVolumeProducts() ) {
                if( lastChance == null ) {
                    lastChance = product;
                }
                else {
                    Float l = lastChance.getMonthlyGigabyteCost();
                    Float t = product.getMonthlyGigabyteCost();

                    if( l != null && t != null && t < l ) {
                        lastChance = product;
                    }
                }
                if( isVolumeSizeDeterminedByProduct() ) {
                    Storage<Gigabyte> size = product.getVolumeSize();
                    int sz = (size == null ? 0 : size.intValue());

                    if( sz >= sizeInGb ) {
                        if( closest == null ) {
                            closest = product;
                        }
                        else {
                            size = closest.getVolumeSize();
                            if( size == null || size.intValue() > sz ) {
                                closest = product;
                            }
                        }
                    }
                }
                else {
                    if( closest == null ) {
                        closest = product;
                    }
                    else {
                        Float c = closest.getMonthlyGigabyteCost();
                        Float t = product.getMonthlyGigabyteCost();

                        if( c != null && t != null && t < c ) {
                            closest = product;
                        }
                    }
                }
            }
            if( closest == null ) {
                closest = lastChance;
            }
            if( closest != null ) {
                if( fromSnapshot != null ) {
                    String name = "Volume from Snapshot " + fromSnapshot;
                    String description = "Volume created from snapshot #" + fromSnapshot + " on " + (new Date());
                    VolumeCreateOptions options = VolumeCreateOptions.getInstanceForSnapshot(closest.getProviderProductId(), fromSnapshot, storage, name, description, 0);

                    if( inZone != null ) {
                        options = options.inDataCenter(inZone);
                    }
                    return createVolume(options);
                }
                else {
                    String name = "New Volume " + System.currentTimeMillis();
                    String description = "New Volume (created " + (new Date()) + ")";
                    VolumeCreateOptions options = VolumeCreateOptions.getInstance(closest.getProviderProductId(), storage, name, description, 0);

                    if( inZone != null ) {
                        options = options.inDataCenter(inZone);
                    }
                    return createVolume(options);
                }
            }
        }
        if( fromSnapshot != null ) {
            String name = "Volume from Snapshot " + fromSnapshot;
            String description = "Volume created from snapshot #" + fromSnapshot + " on " + (new Date());
            VolumeCreateOptions options = VolumeCreateOptions.getInstanceForSnapshot(fromSnapshot, storage, name, description);

            if( inZone != null ) {
                options = options.inDataCenter(inZone);
            }
            return createVolume(options);
        }
        else {
            String name = "New Volume " + System.currentTimeMillis();
            String description = "New Volume (created " + (new Date()) + ")";
            VolumeCreateOptions options = VolumeCreateOptions.getInstance(storage, name, description);

            if( inZone != null ) {
                options = options.inDataCenter(inZone);
            }
            return createVolume(options);
        }
    }

    @Override
    public @Nonnull String createVolume(@Nonnull VolumeCreateOptions options) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Volume creation is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public final void detach(@Nonnull String volumeId) throws InternalException, CloudException {
        detach(volumeId, false);
    }

    @Override
    public void detach(@Nonnull String volumeId, boolean force) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Detaching volumes is not currently implemented for " + getProvider().getCloudName());
    }

    /**
     * @return the provider context under which the instance is operating
     * @throws CloudException no context was set for this request
     */
    protected @Nonnull ProviderContext getContext() throws CloudException {
        ProviderContext ctx = getProvider().getContext();

        if( ctx == null ) {
            throw new CloudException("No context was specified for this request");
        }
        return ctx;
    }

    /**
     * @return the cloud provider under which this support instance is operating
     */
    protected final @Nonnull CloudProvider getProvider() {
        return provider;
    }

    @Override
    public int getMaximumVolumeCount() throws InternalException, CloudException {
        return -2;
    }

    @Override
    public @Nullable Storage<Gigabyte> getMaximumVolumeSize() throws InternalException, CloudException {
        return null;
    }

    @Override
    public Volume getVolume(@Nonnull String volumeId) throws InternalException, CloudException {
        for( Volume volume : listVolumes() ) {
            if( volume.getProviderVolumeId().equals(volumeId) ) {
                return volume;
            }
        }
        return null;
    }

    @Override
    public @Nonnull Requirement getVolumeProductRequirement() throws InternalException, CloudException {
        return Requirement.NONE;
    }

    @Override
    public boolean isVolumeSizeDeterminedByProduct() throws InternalException, CloudException {
        return false;
    }

    @Override
    public @Nonnull Iterable<VolumeFormat> listSupportedFormats() throws InternalException, CloudException {
        return Collections.singletonList(VolumeFormat.BLOCK);
    }

    @Override
    public @Nonnull Iterable<VolumeProduct> listVolumeProducts() throws InternalException, CloudException {
        return Collections.emptyList();
    }

    @Override
    public @Nonnull Iterable<ResourceStatus> listVolumeStatus() throws InternalException, CloudException {
        ArrayList<ResourceStatus> status = new ArrayList<ResourceStatus>();

        for( Volume volume : listVolumes() ) {
            status.add(new ResourceStatus(volume.getProviderVolumeId(), volume.getCurrentState()));
        }
        return status;
    }

    @Override
    public @Nonnull Iterable<Volume> listVolumes(@Nullable VolumeFilterOptions options) throws InternalException, CloudException {
        if( options == null || !options.hasCriteria() ) {
            return listVolumes();
        }
        ArrayList<Volume> volumes = new ArrayList<Volume>();

        for( Volume v : listVolumes() ) {
            if( options.matches(v) ) {
                volumes.add(v);
            }
        }
        return volumes;
    }

    @Override
    public @Nonnull String[] mapServiceAction(@Nonnull ServiceAction action) {
        return new String[0];
    }

    @Override
    public void removeTags(@Nonnull String volumeId, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void removeTags(@Nonnull String[] volumeIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        for( String id : volumeIds ) {
            removeTags(id, tags);
        }
    }

    @Override
    public void updateTags(@Nonnull String volumeId, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void updateTags(@Nonnull String[] volumeIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        for( String id : volumeIds ) {
            updateTags(id, tags);
        }
    }

    @Override
    public @Nonnull String toString() {
        return (getProvider().getProviderName() + "/" + getProvider().getCloudName() + "/Compute/Volumes");
    }
}
