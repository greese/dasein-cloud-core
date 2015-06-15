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

import org.dasein.cloud.Taggable;
import org.dasein.cloud.network.Networkable;
import org.dasein.util.uom.storage.Gigabyte;
import org.dasein.util.uom.storage.Storage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a block storage volume in the cloud.
 *
 * @author George Reese (george.reese@imaginary.com)
 * @version 2012-07 updated to match new volume enhancements, including UoM, type, and root volume awareness
 * @since unknown
 */
public class Volume implements Networkable, Taggable {
    private long                creationTimestamp;
    private VolumeState         currentState;
    private String              providerDataCenterId;
    private String              description;
    private String              deviceId;
    private VolumeFormat        format;
    private Platform            guestOperatingSystem;
    private int                 iops;
    private String              mediaLink;
    private String              name;
    private String              providerProductId;
    private String              providerVolumeId;
    private String              providerRegionId;
    private String              providerVirtualMachineId;
    private String              providerVlanId;
    private boolean             rootVolume;
    private Storage<Gigabyte>   size;
    private String              providerSnapshotId;
    private Map<String, String> tags;
    private VolumeType          type;

    /**
     * deleteOnVirtualMachineTermination is needed for listing volumes on a virtualmachine, but set to null
     * for when listing volumes through VolumeSupport as some providers (AWS) don't provide this info there.
     */
    private Boolean deleteOnVirtualMachineTermination = null;

    public Volume() {
    }

    @Override
    public boolean equals( Object ob ) {
        if( ob == null ) {
            return false;
        }
        if( ob == this ) {
            return true;
        }
        if( !getClass().getName().equals(ob.getClass().getName()) ) {
            return false;
        }
        Volume other = ( Volume ) ob;

        if( !providerRegionId.equals(other.providerRegionId) ) {
            return false;
        }
        return providerVolumeId.equals(other.providerVolumeId);
    }

    /**
     * @return provider snapshot identifier
     * @deprecated Use {@link #getProviderSnapshotId()}
     */
    public String getSnapshotId() {
        return getProviderSnapshotId();
    }

    public String getProviderSnapshotId() {
        return providerSnapshotId;
    }

    /**
     * @param snapshotId provider snapshot identifier
     * @deprecated use {@link #setProviderSnapshotId(String)}
     */
    public void setSnapshotId( String snapshotId ) {
        this.providerSnapshotId = snapshotId;
    }

    public void setProviderSnapshotId( String snapshotId ) {
        this.providerSnapshotId = snapshotId;
    }

    public VolumeState getCurrentState() {
        return currentState;
    }

    /**
     * @return provider data center identifier
     * @deprecated use {@link #getProviderDataCenterId()}
     */
    public String getDataCenterId() {
        return getProviderDataCenterId();
    }

    public String getProviderDataCenterId() {
        return providerDataCenterId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getName() {
        return name;
    }

    public String getProviderVolumeId() {
        return providerVolumeId;
    }

    /**
     * @return provider region identifier
     * @deprecated use {@link #getProviderRegionId()}
     */
    public String getRegionId() {
        return getProviderRegionId();
    }

    public String getProviderRegionId() {
        return providerRegionId;
    }

    /**
     * @return provider virtual machine identifier
     * @deprecated use {@link #getProviderVirtualMachineId()}
     */
    public String getServerId() {
        return getProviderVirtualMachineId();
    }

    public String getProviderVirtualMachineId() {
        return providerVirtualMachineId;
    }

    public Storage<Gigabyte> getSize() {
        return size;
    }

    public void setSize( Storage<?> size ) {
        this.size = ( Storage<Gigabyte> ) size.convertTo(Storage.GIGABYTE);
    }

    public int getSizeInGigabytes() {
        return ( size == null ? 0 : size.getQuantity().intValue() );
    }

    public void setCurrentState( VolumeState currentState ) {
        this.currentState = currentState;
    }

    /**
     * @param dataCenterId provider data center identifier
     * @deprecated use {@link #setProviderDataCenterId(String)}
     */
    public void setDataCenterId( String dataCenterId ) {
        setProviderDataCenterId(dataCenterId);
    }

    public void setProviderDataCenterId( String dataCenterId ) {
        this.providerDataCenterId = dataCenterId;
    }

    public void setDeviceId( String deviceId ) {
        this.deviceId = deviceId;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public void setProviderVolumeId( String providerVolumeId ) {
        this.providerVolumeId = providerVolumeId;
    }

    /**
     * @param regionId provider region identifier
     * @deprecated use {@link #setProviderRegionId(String)}
     */
    public void setRegionId( String regionId ) {
        setProviderRegionId(regionId);
    }

    public void setProviderRegionId( String regionId ) {
        this.providerRegionId = regionId;
    }

    /**
     * @param serverId provider virtual machine identifier
     * @deprecated use {@link #setProviderVirtualMachineId(String)}
     */
    public void setServerId( String serverId ) {
        setProviderVirtualMachineId(serverId);
    }

    public void setProviderVirtualMachineId( String serverId ) {
        this.providerVirtualMachineId = serverId;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp( long creationTimestamp ) {
        this.creationTimestamp = creationTimestamp;
    }

    public VolumeType getType() {
        return type;
    }

    public void setType( VolumeType t ) {
        type = t;
    }

    public String toString() {
        return ( name + " [" + providerVolumeId + "]" );
    }

    public String getProviderProductId() {
        return providerProductId;
    }

    public void setProviderProductId( String providerProductId ) {
        this.providerProductId = providerProductId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public boolean isRootVolume() {
        return rootVolume;
    }

    public void setRootVolume( boolean rootVolume ) {
        this.rootVolume = rootVolume;
    }

    public String getMediaLink() {
        return mediaLink;
    }

    public void setMediaLink( String mediaLink ) {
        this.mediaLink = mediaLink;
    }

    public Platform getGuestOperatingSystem() {
        return guestOperatingSystem;
    }

    public void setGuestOperatingSystem( Platform guestOperatingSystem ) {
        this.guestOperatingSystem = guestOperatingSystem;
    }

    public int getIops() {
        return iops;
    }

    public void setIops( int iops ) {
        this.iops = iops;
    }

    public boolean isAttached() {
        return ( providerVirtualMachineId != null );
    }

    public @Nonnull VolumeFormat getFormat() {
        return ( format == null ? VolumeFormat.BLOCK : format );
    }

    public void setFormat( @Nonnull VolumeFormat format ) {
        this.format = format;
    }

    public String getProviderVlanId() {
        return providerVlanId;
    }

    public void setProviderVlanId( String providerVlanId ) {
        this.providerVlanId = providerVlanId;
    }

    public synchronized void setTags( Map<String, String> properties ) {
        getTags().clear();
        getTags().putAll(properties);
    }

    public @Nullable String getTag( @Nonnull String key ) {
        return getTags().get(key);
    }

    @Override
    public @Nonnull Map<String, String> getTags() {
        if( tags == null ) {
            tags = new HashMap<String, String>();
        }
        return tags;
    }

    @Override
    public void setTag( @Nonnull String key, @Nonnull String value ) {
        getTags().put(key, value);
    }

    public @Nullable Boolean isDeleteOnVirtualMachineTermination() {
        return deleteOnVirtualMachineTermination;
    }

    public void setDeleteOnVirtualMachineTermination( @Nullable Boolean deleteOnVirtualMachineTermination ) {
        this.deleteOnVirtualMachineTermination = deleteOnVirtualMachineTermination;
    }

}
