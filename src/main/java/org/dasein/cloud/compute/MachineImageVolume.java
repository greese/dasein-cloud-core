package org.dasein.cloud.compute;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Eugene Yaroslavtsev
 * @since 18.07.2014
 */
public class MachineImageVolume {

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
