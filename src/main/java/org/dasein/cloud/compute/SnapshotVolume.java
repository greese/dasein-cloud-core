package org.dasein.cloud.compute;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Eugene Yaroslavtsev
 * @since 18.07.2014
 */
public class SnapshotVolume {

    static public @Nonnull
    SnapshotVolume getSnapshotVolumeInstance(@Nonnull String deviceName, @Nonnull String snapshotId, @Nonnull Integer volumeSize, @Nonnull String volumeType, @Nullable String iops) {
        @SuppressWarnings("deprecation") SnapshotVolume snapshotVolume = new SnapshotVolume();

        snapshotVolume.deviceName = deviceName;
        snapshotVolume.iops = iops;
        snapshotVolume.snapshotId = snapshotId;
        snapshotVolume.volumeSize = volumeSize;
        snapshotVolume.volumeType = volumeType;

        return snapshotVolume;
    }

    public SnapshotVolume() { }

    private String             deviceName;
    private String             snapshotId;
    private Integer            volumeSize;
    private String             volumeType;
    private String             iops;

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

    public String getIops() {
        return iops;
    }

    public void setIops(String iops) {
        this.iops = iops;
    }
}
