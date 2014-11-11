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
