package org.dasein.cloud.ci;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

public class TopologyProvisionOptions {

    public enum MaintenenceOption {
        MIGRATE_VM_INSTANCE,
        TERMINATE_VM_INSTANCE;

        public String toString() {
            switch( this ) {
                case MIGRATE_VM_INSTANCE: return "MIGRATE";
                case TERMINATE_VM_INSTANCE: return "TERMINATE";
            }
            return null;
        }
    }

    public enum DiskType {
        STANDARD_PERSISTENT_DISK,
        SSD_PERSISTENT_DISK;

        public String toString() {
            switch( this ) {
                case STANDARD_PERSISTENT_DISK: return "pd-standard";
                case SSD_PERSISTENT_DISK: return "pd-ssd";
            }
            return null;
        }
    }

    public class Disk {
        private String deviceName;
        private DiskType deviceType;
        private String deviceSource;
        private boolean bootable;
        private boolean autoDelete;

        private Disk(@Nonnull String deviceName, @Nonnull DiskType diskType, @Nonnull String deviceSource, @Nonnull boolean bootable, @Nonnull boolean autoDelete) {
            this.deviceName = deviceName;
            this.deviceType = diskType;
            this.deviceSource = deviceSource;
            this.bootable = bootable;
            this.autoDelete = autoDelete;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public DiskType getDeviceType() {
            return deviceType;
        }

        public String getDeviceSource() {
            return deviceSource;
        }

        public boolean getBootable() {
            return bootable;
        }

        public boolean getAutoDelete() {
            return autoDelete;
        }
    }

    public class Network {
        private String networkName;
        private String networkSelfUrl;
        private List<AccessConfig> accessConfig;

        private Network(@Nonnull String networkName, @Nonnull String networkSelfUrl, @Nonnull List<AccessConfig> accessConfig) {
            this.networkName = networkName;
            this.networkSelfUrl = networkSelfUrl;
            this.accessConfig = accessConfig;
        }

        public String getNetworkName() {
            return networkName;
        }

        public String getNetworkSelfUrl() {
            return networkSelfUrl;
        }

        public List<AccessConfig> getAccessConfig() {
            return accessConfig;
        }
    }

    public class AccessConfig {
        private String name;
        private String kind;
        private String type;

        public AccessConfig(@Nonnull String kind, @Nonnull String type, @Nonnull String name) {
            this.name = name;
            this.kind = kind;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public String getKind() {
            return kind;
        }

        public String getType() {
            return type;
        }
    }

    private List<Disk> diskArray = new ArrayList<Disk>();
    private List<Network> networkArray = new ArrayList<Network>();
    private String productName;
    private String productDescription;
    private String machineType;
    private boolean canIpForward;
    private String[] sshKeys = new String[0];
    private List<String> tags = new ArrayList<String>();
    private Map<String, String> metadata = new HashMap<String, String>();
    private String[] roDisks = new String[0];
    private boolean automaticRestart;
    private MaintenenceOption maintenenceAction;
    private DiskType bootDiskType; 

    private TopologyProvisionOptions() { }

    private TopologyProvisionOptions(@Nonnull String productName, @Nonnull String productDescription, @Nonnull String machineType, @Nonnull boolean canIpForward) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.machineType = machineType;
        this.canIpForward = canIpForward;
    }

    static public @Nonnull TopologyProvisionOptions getInstance(@Nonnull String productName, @Nonnull String productDescription, @Nonnull String machineType, @Nonnull boolean canIpForward) {
        return new TopologyProvisionOptions(productName, productDescription, machineType, canIpForward);
    }

    public @Nonnull TopologyProvisionOptions withAttachedDisk(@Nonnull String deviceName, @Nonnull DiskType diskType, @Nonnull String deviceSource, @Nonnull boolean bootable, @Nonnull boolean autoDelete) {
        diskArray.add(new Disk(deviceName, diskType, deviceSource, bootable, autoDelete));
        return this;
    }

    public @Nonnull TopologyProvisionOptions withNetworkInterface(@Nonnull String networkName, @Nonnull String networkSelfUrl, @Nonnull boolean assignEphemeralIp) {
        if (false == assignEphemeralIp) {
            this.networkArray.add(new Network(networkName, networkSelfUrl, new ArrayList<AccessConfig>()));
        } else {
            List<AccessConfig> accessConfig = new ArrayList<AccessConfig>();
            accessConfig.add(new AccessConfig("compute#accessConfig", "ONE_TO_ONE_NAT", "External NAT"));
            this.networkArray.add(new Network(networkName, networkSelfUrl, accessConfig));
        }

        return this;
    }

/*
    public TopologyProvisionOptions withNetworkInterface(@Nonnull String networkName, @Nonnull String networkSelfUrl) {
        this.networkArray.add(new Network(networkName, networkSelfUrl, new ArrayList<AccessConfig>()));
        return this;
    }
*/

    public TopologyProvisionOptions withSshKeys(@Nonnull String[] sshKeys) {
        this.sshKeys  = sshKeys;
        return this;
    }

    public TopologyProvisionOptions withTags(@Nonnull List<String> tags) {
        this.tags = tags;
        return this;
    }

    public TopologyProvisionOptions withReadOnlyDisks(@Nonnull String[] roDisks) {
        this.roDisks = roDisks;
        return this;
    }

    public TopologyProvisionOptions withAutomaticRestart(@Nonnull boolean automaticRestart) {
        this.automaticRestart = automaticRestart;
        return this;
    }

    public TopologyProvisionOptions withMaintenceOption(@Nonnull MaintenenceOption maintenenceAction) {
        this.maintenenceAction = maintenenceAction;
        return this;
    }

    public TopologyProvisionOptions withBootDiskType(@Nonnull DiskType bootDiskType) {
        this.bootDiskType = bootDiskType;
        return this;
    }

    public TopologyProvisionOptions withMetadata(@Nonnull Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }


    public AccessConfig getAccessConfigInstance(@Nonnull String name, @Nonnull String natIP, @Nonnull String type) {
        return new AccessConfig(name, natIP, type);
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public boolean getCanIpForward() {
        return canIpForward;
    }

    public String getMachineType() {
        return machineType;
    }

    public List<Network> getNetworkArray() {
        return networkArray;
    }

    public List<Disk> getDiskArray() {
        return diskArray;
    }

    public String[] getSshKeys() {
        return sshKeys;
    }

    public List<String> getTags() {
        return tags;
    }

    public String[] getRoDisks() {
        return roDisks;
    }

    public boolean getAutomaticRestart() {
        return automaticRestart;
    }

    public MaintenenceOption getMaintenenceAction() {
        return maintenenceAction;
    }

    public DiskType getBootDiskType() {
        return bootDiskType;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }
}
