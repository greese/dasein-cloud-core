/**
 * Copyright (C) 2009-2012 enStratus Networks Inc.
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

import org.dasein.util.uom.storage.Gigabyte;
import org.dasein.util.uom.storage.Storage;

import javax.annotation.Nonnegative;
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
        return new VolumeCreateOptions(null, null, size, name, description, 0);
    }

    static public VolumeCreateOptions getInstance(@Nonnull String volumeProductId, @Nonnull Storage<?> size, @Nonnull String name, @Nonnull String description, @Nonnegative int iops) {
        return new VolumeCreateOptions(volumeProductId, null, size, name, description, iops);
    }

    static public VolumeCreateOptions getInstanceForSnapshot(@Nonnull String snapshotId, @Nonnull Storage<?> size, @Nonnull String name, @Nonnull String description) {
        return new VolumeCreateOptions(null, snapshotId, size, name, description, 0);
    }

    static public VolumeCreateOptions getInstanceForSnapshot(@Nonnull String volumeProductId, @Nonnull String snapshotId, @Nonnull Storage<?> size, @Nonnull String name, @Nonnull String description, @Nonnegative int iops) {
        return new VolumeCreateOptions(volumeProductId, snapshotId, size, name, description, iops);
    }
    
    private String            dataCenterId;
    private String            description;
    private String            deviceId;
    private VolumeFormat      format;
    private int               iops;
    private String            name;
    private String            snapshotId;
    private String            virtualMachineId;
    private String            volumeProductId;
    private Storage<Gigabyte> volumeSize;
    
    @SuppressWarnings("UnusedDeclaration")
    private VolumeCreateOptions() { }

    private VolumeCreateOptions(@Nullable String volumeProductId, @Nullable String snapshotId, @Nonnull Storage<?> size, @Nonnull String name, @Nonnull String description, @Nonnegative int iops) {
        this.volumeProductId = volumeProductId;
        this.snapshotId = snapshotId;
        volumeSize = (Storage<Gigabyte>)size.convertTo(Storage.GIGABYTE);
        this.name = name;
        this.description = description;
        this.iops = iops;
    }

    public @Nonnull VolumeCreateOptions asFormat(@Nonnull VolumeFormat format) {
        this.format = format;
        return this;
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

    public @Nonnegative int getIops() {
        return iops;
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
