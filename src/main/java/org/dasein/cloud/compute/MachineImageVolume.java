package org.dasein.cloud.compute;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Eugene Yaroslavtsev
 * @since 18.07.2014
 */
public class MachineImageVolume {

    static public
    @Nonnull
    MachineImageVolume getSnapshotVolumeInstance(@Nonnull String deviceName, @Nonnull String snapshotId, @Nonnull Integer volumeSize, @Nonnull String volumeType, @Nullable Integer iops) {
        @SuppressWarnings("deprecation") MachineImageVolume machineImageVolume = new MachineImageVolume();

        machineImageVolume.deviceName = deviceName;
        machineImageVolume.iops = iops;
        machineImageVolume.snapshotId = snapshotId;
        machineImageVolume.volumeSize = volumeSize;
        machineImageVolume.volumeType = volumeType;

        return machineImageVolume;
    }

    public MachineImageVolume() {}

    private String deviceName;
    private String snapshotId;
    private Integer volumeSize;
    private String volumeType;
    private Integer iops;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId;
    }

    public Integer getVolumeSize() {
        return volumeSize;
    }

    public void setVolumeSize(Integer volumeSize) {
        this.volumeSize = volumeSize;
    }

    public String getVolumeType() {
        return volumeType;
    }

    public void setVolumeType(String volumeType) {
        this.volumeType = volumeType;
    }

    public Integer getIops() {
        return iops;
    }

    public void setIops(Integer iops) {
        this.iops = iops;
    }
}
