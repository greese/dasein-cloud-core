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

package org.dasein.cloud.ci;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TopologyProvisionOptions {

    public enum MaintenanceOption {
        MIGRATE_VM_INSTANCE,
        TERMINATE_VM_INSTANCE;
    }

    public enum DiskType {
        STANDARD_PERSISTENT_DISK,
        SSD_PERSISTENT_DISK;
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
        private List<AccessConfig> accessConfig;

        private Network(@Nonnull String networkName, @Nonnull List<AccessConfig> accessConfig) {
            this.networkName = networkName;
            this.accessConfig = accessConfig;
        }

        public String getNetworkName() {
            return networkName;
        }

        public List<AccessConfig> getAccessConfig() {
            return accessConfig;
        }
    }

    public class AccessConfig {
        private String name;
        private String type;

        public AccessConfig(@Nonnull String type, @Nonnull String name) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
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
    private MaintenanceOption maintenanceAction;
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
            this.networkArray.add(new Network(networkName, new ArrayList<AccessConfig>()));
        } else {
            List<AccessConfig> accessConfig = new ArrayList<AccessConfig>();
            accessConfig.add(new AccessConfig("ONE_TO_ONE_NAT", "External NAT"));
            this.networkArray.add(new Network(networkName, accessConfig));
        }

        return this;
    }

    public @Nonnull TopologyProvisionOptions withSshKeys(@Nonnull String[] sshKeys) {
        this.sshKeys  = sshKeys;
        return this;
    }

    public @Nonnull TopologyProvisionOptions withTags(@Nonnull List<String> tags) {
        this.tags = tags;
        return this;
    }

    public @Nonnull TopologyProvisionOptions withReadOnlyDisks(@Nonnull String[] roDisks) {
        this.roDisks = roDisks;
        return this;
    }

    public @Nonnull TopologyProvisionOptions withAutomaticRestart(@Nonnull boolean automaticRestart) {
        this.automaticRestart = automaticRestart;
        return this;
    }

    public @Nonnull TopologyProvisionOptions withMaintenanceOption(@Nonnull MaintenanceOption maintenanceAction) {
        this.maintenanceAction = maintenanceAction;
        return this;
    }

    public @Nonnull TopologyProvisionOptions withBootDiskType(@Nonnull DiskType bootDiskType) {
        this.bootDiskType = bootDiskType;
        return this;
    }

    public @Nonnull TopologyProvisionOptions withMetadata(@Nonnull Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

    public @Nullable AccessConfig getAccessConfigInstance(@Nonnull String natIP, @Nonnull String type) {
        return new AccessConfig(natIP, type);
    }

    public @Nonnull String getProductName() {
        return productName;
    }

    public @Nonnull String getProductDescription() {
        return productDescription;
    }

    public boolean getCanIpForward() {
        return canIpForward;
    }

    public @Nonnull String getMachineType() {
        return machineType;
    }

    public @Nonnull List<Network> getNetworkArray() {
        return networkArray;
    }

    public @Nonnull List<Disk> getDiskArray() {
        return diskArray;
    }

    public @Nonnull String[] getSshKeys() {
        return sshKeys;
    }

    public @Nonnull List<String> getTags() {
        return tags;
    }

    public @Nonnull String[] getRoDisks() {
        return roDisks;
    }

    public boolean getAutomaticRestart() {
        return automaticRestart;
    }

    public @Nullable MaintenanceOption getMaintenenceAction() {
        return maintenanceAction;
    }

    public @Nullable DiskType getBootDiskType() {
        return bootDiskType;
    }

    public @Nonnull Map<String, String> getMetadata() {
        return metadata;
    }
}
