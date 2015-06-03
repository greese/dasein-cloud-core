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

package org.dasein.cloud.compute;

import org.dasein.cloud.AbstractProviderService;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.ProviderContext;
import org.dasein.cloud.Requirement;
import org.dasein.cloud.ResourceStatus;
import org.dasein.cloud.Tag;
import org.dasein.cloud.identity.ServiceAction;
import org.dasein.cloud.util.TagUtils;
import org.dasein.util.uom.storage.Gigabyte;
import org.dasein.util.uom.storage.Storage;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Implements the basic functionality of volume support so that it is easier to rapidly craft a support class for
 * each cloud.
 * <p>Created by George Reese: 1/31/13 11:04 AM</p>
 * @author George Reese
 * @version 2013.04
 * @since 2013.04
 */
public abstract class AbstractVolumeSupport<T extends CloudProvider> extends AbstractProviderService<T> implements VolumeSupport {

    protected AbstractVolumeSupport(T provider) {
        super(provider);
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

    @Override
    @Deprecated
    public @Nonnull String getProviderTermForVolume(@Nonnull Locale locale) {
        try {
            return getCapabilities().getProviderTermForVolume(locale);
        } catch (CloudException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Deprecated
    public @Nonnull Iterable<String> listPossibleDeviceIds(@Nonnull Platform platform)
            throws InternalException, CloudException {
        return getCapabilities().listPossibleDeviceIds(platform);
    }

    @Override
    @Deprecated
    public int getMaximumVolumeCount() throws InternalException, CloudException {
        return getCapabilities().getMaximumVolumeCount();
    }

    @Override
    @Deprecated
    public @Nullable Storage<Gigabyte> getMaximumVolumeSize() throws InternalException, CloudException {
        return getCapabilities().getMaximumVolumeSize();
    }

    @Override
    @Deprecated
    public @Nonnull Storage<Gigabyte> getMinimumVolumeSize() throws InternalException, CloudException {
        return getCapabilities().getMinimumVolumeSize();
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
    @Deprecated
    public @Nonnull Requirement getVolumeProductRequirement() throws InternalException, CloudException {
        return getCapabilities().getVolumeProductRequirement();
    }

    @Override
    @Deprecated
    public boolean isVolumeSizeDeterminedByProduct() throws InternalException, CloudException {
        return getCapabilities().isVolumeSizeDeterminedByProduct();
    }

    @Override
    @Deprecated
    public @Nonnull Iterable<VolumeFormat> listSupportedFormats() throws InternalException, CloudException {
        return getCapabilities().listSupportedFormats();
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
    public void setTags( @Nonnull String[] volumeIds, @Nonnull Tag... tags ) throws CloudException, InternalException {
        for( String id : volumeIds ) {

            Tag[] collectionForDelete = TagUtils.getTagsForDelete(getVolume(id).getTags(), tags);

            if( collectionForDelete.length != 0 ) {
                removeTags(id, collectionForDelete);
            }

            updateTags(id, tags);
        }
    }

    @Override
    public void setTags( @Nonnull String volumeId, @Nonnull Tag... tags ) throws CloudException, InternalException {
        setTags(new String[]{volumeId}, tags);
    }

    @Override
    public @Nonnull String toString() {
        return (getProvider().getProviderName() + "/" + getProvider().getCloudName() + "/Compute/Volumes");
    }
}
