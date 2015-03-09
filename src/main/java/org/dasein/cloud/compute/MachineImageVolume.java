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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Eugene Yaroslavtsev
 * @since 18.07.2014
 */
public class MachineImageVolume {

    /**
     * Provides minimum viable machine image.
     * @param deviceName the name of the machine image
     * @param snapshotId the ID of the snapshot
     * @param volumeSize the size of the volume
     * @param volumeType the type of the volume
     * @param iops the minimum guaranteed iops or 0 if no guarantees are sought
     * @return the machine image volume
     */
    static public @Nonnull MachineImageVolume getInstance( @Nonnull String deviceName, @Nonnull String snapshotId, @Nonnull Integer volumeSize, @Nonnull String volumeType, @Nullable Integer iops ) {
        @SuppressWarnings( "deprecation" ) MachineImageVolume machineImageVolume = new MachineImageVolume();

        machineImageVolume.deviceName = deviceName;
        machineImageVolume.iops = iops;
        machineImageVolume.snapshotId = snapshotId;
        machineImageVolume.volumeSize = volumeSize;
        machineImageVolume.volumeType = volumeType;

        return machineImageVolume;
    }

    private MachineImageVolume() {
    }

    private String deviceName;
    private String snapshotId;
    private Integer volumeSize;
    private String volumeType;
    private Integer iops;

    public String getDeviceName() {
        return deviceName;
    }

    public String getSnapshotId() {
        return snapshotId;
    }

    public Integer getVolumeSize() {
        return volumeSize;
    }

    public String getVolumeType() {
        return volumeType;
    }

    public Integer getIops() {
        return iops;
    }
}
