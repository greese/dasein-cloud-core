/**
 * ========= CONFIDENTIAL =========
 *
 * Copyright (C) 2012 enStratus Networks Inc - ALL RIGHTS RESERVED
 *
 * ====================================================================
 *  NOTICE: All information contained herein is, and remains the
 *  property of enStratus Networks Inc. The intellectual and technical
 *  concepts contained herein are proprietary to enStratus Networks Inc
 *  and may be covered by U.S. and Foreign Patents, patents in process,
 *  and are protected by trade secret or copyright law. Dissemination
 *  of this information or reproduction of this material is strictly
 *  forbidden unless prior written permission is obtained from
 *  enStratus Networks Inc.
 * ====================================================================
 */
package org.dasein.cloud.compute;

import org.dasein.util.uom.storage.Gigabyte;
import org.dasein.util.uom.storage.Storage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Configuration options for creating volumes.
 * <p>Created by George Reese: 6/22/12 8:41 PM</p>
 * @author George Reese (george.reese@imaginary.com)
 * @since 2012-07
 * @version 2012-07 introduced volume create options
 */
public class VolumeCreateOptions {
    static public VolumeCreateOptions getInstance(@Nonnull Storage<?> size, @Nonnull String name, @Nonnull String description) {
        return new VolumeCreateOptions(null, null, size, name, description);
    }

    static public VolumeCreateOptions getInstance(@Nonnull String volumeProductId, @Nonnull Storage<?> size, @Nonnull String name, @Nonnull String description) {
        return new VolumeCreateOptions(volumeProductId, null, size, name, description);
    }

    static public VolumeCreateOptions getInstanceForSnapshot(@Nonnull String snapshotId, @Nonnull Storage<?> size, @Nonnull String name, @Nonnull String description) {
        return new VolumeCreateOptions(null, snapshotId, size, name, description);
    }

    static public VolumeCreateOptions getInstanceForSnapshot(@Nonnull String volumeProductId, @Nonnull String snapshotId, @Nonnull Storage<?> size, @Nonnull String name, @Nonnull String description) {
        return new VolumeCreateOptions(volumeProductId, snapshotId, size, name, description);
    }
    
    private String            dataCenterId;
    private String            description;
    private String            deviceId;
    private String            name;
    private String            snapshotId;
    private String            virtualMachineId;
    private String            volumeProductId;
    private Storage<Gigabyte> volumeSize;
    
    @SuppressWarnings("UnusedDeclaration")
    private VolumeCreateOptions() { }

    private VolumeCreateOptions(@Nullable String volumeProductId, @Nullable String snapshotId, @Nonnull Storage<?> size, @Nonnull String name, @Nonnull String description) {
        this.volumeProductId = volumeProductId;
        this.snapshotId = snapshotId;
        volumeSize = (Storage<Gigabyte>)size.convertTo(Storage.GIGABYTE);
        this.name = name;
        this.description = description;
    }
    
    public @Nullable String getDataCenterId() {
        return dataCenterId;
    }
    
    public @Nonnull String getDescription() {
        return description;
    }

    public @Nullable String getDeviceId() {
        return deviceId;
    }
    
    public @Nonnull String getName() {
        return name;
    }
    
    public @Nullable String getSnapshotId() {
        return snapshotId;
    }
    
    public @Nullable String getVirtualMachineId() {
        return virtualMachineId;
    }
    
    public @Nullable String getVolumeProductId() {
        return volumeProductId;
    }
    
    public @Nonnull Storage<Gigabyte> getVolumeSize() {
        return volumeSize;
    }

    public @Nonnull VolumeCreateOptions inDataCenter(@Nonnull String dataCenterId) {
        this.dataCenterId = dataCenterId;
        return this;
    }

    public @Nonnull VolumeCreateOptions withAttachment(@Nonnull String vmId, @Nonnull String asDeviceId) {
        this.virtualMachineId = vmId;
        this.deviceId = asDeviceId;
        return this;
    }
}
