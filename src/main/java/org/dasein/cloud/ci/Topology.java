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

import org.dasein.cloud.Taggable;
import org.dasein.cloud.compute.Architecture;
import org.dasein.cloud.compute.Platform;
import org.dasein.util.uom.storage.Megabyte;
import org.dasein.util.uom.storage.Storage;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A topology is a grouping of configurable resources that may be provisioned together to create a complex grouping of
 * interoperating cloud resources. A simple topology might be one with two images that get provisioned into two virtual
 * machines. A more complex topology might include network provisioning with load balancers.
 * <p>Created by George Reese: 5/30/13 11:38 AM</p>
 * @author George Reese
 * @version 2013.07 initial version
 * @since 2013.07
 */
public class Topology implements Taggable {
    /**
     * A VLAN attached to a topology.
     */
    static public class VLANDevice {
        /**
         * Constructs a VLAN device instance from the specified parameters.
         * @param deviceId the ID (unique within the topology) of the VLAN
         * @param name the name of the VLAN
         * @return a VLAN device with the specified parameters as attributes
         */
        static public VLANDevice getInstance(@Nonnull String deviceId, @Nonnull String name) {
            VLANDevice d = new VLANDevice();

            d.deviceId = deviceId;
            d.name = name;
            return d;
        }

        private String deviceId;
        private String name;

        private VLANDevice() { }

        /**
         * @return an ID that is unique within the topology
         */
        public @Nonnull String getDeviceId() {
            return deviceId;
        }

        /**
         * @return a name for the VLAN
         */
        public @Nonnull String getName() {
            return name;
        }

        @Override
        public @Nonnull String toString() {
            return (name + " [#" + deviceId + "]");
        }
    }

    /**
     * A logical virtual machine attached to a topology.
     */
    static public class VMDevice {
        static public VMDevice getInstance(@Nonnull String deviceId, @Nonnull String name, int cpuCount, Storage<?> memory, String ... interfaces) {
            VMDevice d = new VMDevice();

            d.deviceId = deviceId;
            d.capacity = 1;
            d.platform = Platform.UNKNOWN;
            d.architecture = Architecture.I64;
            d.name = name;
            d.cpuCount = cpuCount;
            d.memory = (Storage<Megabyte>)memory.convertTo(Storage.MEGABYTE);
            d.interfaces = interfaces;
            return d;
        }

        static public VMDevice getInstance(@Nonnull String deviceId, int capacity, @Nonnull String name, int cpuCount, Storage<?> memory, Architecture architecture, Platform platform, String ... interfaces) {
            VMDevice d = new VMDevice();

            d.deviceId = deviceId;
            d.capacity = capacity;
            d.platform = platform;
            d.architecture = architecture;
            d.name = name;
            d.cpuCount = cpuCount;
            d.memory = (Storage<Megabyte>)memory.convertTo(Storage.MEGABYTE);
            d.interfaces = interfaces;
            return d;
        }

        private Architecture      architecture;
        private int               capacity;
        private int               cpuCount;
        private String            deviceId;
        private String[]          interfaces;
        private Storage<Megabyte> memory;
        private String            name;
        private Platform          platform;

        private VMDevice() { }

        public @Nonnull Architecture getArchitecture() {
            return architecture;
        }

        public @Nonnegative int getCapacity() {
            return capacity;
        }

        public @Nonnegative int getCpuCount() {
            return cpuCount;
        }

        public @Nonnull String getDeviceId() {
            return deviceId;
        }

        public @Nonnull String[] getInterfaces() {
            if( interfaces == null ) {
                return new String[0];
            }
            return interfaces;
        }

        public @Nonnull Storage<Megabyte> getMemory() {
            return memory;
        }

        public @Nonnull String getName() {
            return name;
        }

        public @Nonnull Platform getPlatform() {
            return platform;
        }

        public @Nonnull VMDevice withPlatform(@Nonnull Platform p) {
            platform = p;
            return this;
        }

        public String toString() {
            return (name + " [#" + deviceId + "]");
        }
    }

    static public @Nonnull Topology getInstance(@Nonnull String ownerId, @Nonnull String regionId, @Nonnull String topologyId, @Nonnull TopologyState state, @Nonnull String name, @Nonnull String description) {
        Topology t = new Topology();

        t.providerOwnerId = ownerId;
        t.providerRegionId = regionId;
        t.providerTopologyId = topologyId;
        t.currentState = state;
        t.name = name;
        t.description = description;
        t.creationTimestamp = 0L;
        return t;
    }

    private long               creationTimestamp;
    private TopologyState      currentState;
    private String             description;
    private String             name;
    private String             providerDataCenterId;
    private String             providerOwnerId;
    private String             providerRegionId;
    private String             providerTopologyId;
    private Map<String,String> tags;
    private List<VMDevice>     virtualMachines;
    private List<VLANDevice>   vlans;

    private Topology() { }

    /**
     * Indicates the Unix timestamp when this topology was created.
     * @param timestamp the Unix timestamp in milliseconds when this topology was created
     * @return this
     */
    public @Nonnull Topology createdAt(@Nonnegative long timestamp) {
        creationTimestamp = timestamp;
        return this;
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if( other == null ) {
            return false;
        }
        if( other == this ) {
            return true;
        }
        if( !getClass().getName().equals(other.getClass().getName()) ) {
            return false;
        }
        Topology t = (Topology)other;

        return (t.providerOwnerId.equals(providerOwnerId) && t.providerRegionId.equals(providerRegionId) && t.providerTopologyId.equals(providerRegionId));
    }

    /**
     * @return the UNIX timestamp representing the time at which this toplogy was initially created
     */
    public @Nonnegative long getCreationTimestamp() {
        return creationTimestamp;
    }

    /**
     * @return the current state of the topology
     */
    public @Nonnull TopologyState getCurrentState() {
        return currentState;
    }

    /**
     * @return a user-friendly description of the topology
     */
    public @Nonnull String getDescription() {
        return description;
    }

    /**
     * @return a user-friendly name for the topology
     */
    public @Nonnull String getName() {
        return name;
    }

    /**
     * @return the data center, if any, to which the topology is constrained
     */
    public @Nullable String getProviderDataCenterId() {
        return providerDataCenterId;
    }

    /**
     * @return the account number of the account that owns this topology
     */
    public @Nonnull String getProviderOwnerId() {
        return providerOwnerId;
    }

    /**
     * @return the region to which this topology is constrained
     */
    public @Nonnull String getProviderRegionId() {
        return providerRegionId;
    }

    /**
     * @return the unique ID used by the cloud provider to identify this topology
     */
    public @Nonnull String getProviderTopologyId() {
        return providerTopologyId;
    }

    /**
     * Fetches the value of the specified tag key.
     * @param tag the key of the tag to be fetched
     * @return the value associated with the specified key, if any
     */
    public @Nullable Object getTag(@Nonnull String tag) {
        return getTags().get(tag);
    }

    @Override
    public @Nonnull Map<String,String> getTags() {
        if( tags == null ) {
            tags = new HashMap<String,String>();
        }
        return tags;
    }

    /**
     * @return a list of the logical virtual machines to be provisioned when the topology is provisioned into a composite infrastructure
     */
    public @Nonnull Iterable<VMDevice> getVirtualMachines() {
        if( virtualMachines == null ) {
            return Collections.emptyList();
        }
        return virtualMachines;
    }

    /**
     * @return a list of VLANs associated with this topology
     */
    public @Nonnull Iterable<VLANDevice> getVLANs() {
        if( vlans == null ) {
            return Collections.emptyList();
        }
        return vlans;
    }

    @Override
    public int hashCode() {
        return (providerOwnerId + providerRegionId + providerTopologyId).hashCode();
    }

    /**
     * Indicates that the topology is constrained to the specified data center.
     * @param dcId the unique ID of the data center to which this topology is constrained
     * @return this
     */
    public @Nonnull Topology inDataCenter(@Nonnull String dcId) {
        providerDataCenterId = dcId;
        return this;
    }

    @Override
    public void setTag(@Nonnull String key, @Nonnull String value) {
        getTags().put(key, value);
    }

    /**
     * Clears out any currently set tags and replaces them with the specified list.
     * @param properties the list of tag values to be set
     */
    public void setTags(Map<String,String> properties) {
        getTags().clear();
        getTags().putAll(properties);
    }

    /**
     * Indicates the virtual machines to be associated with this topology. Calls to this method are additive.
     * @param devices one or more VM devices to be added to the topology
     * @return this
     */
    public @Nonnull Topology withVirtualMachines(@Nonnull VMDevice ... devices) {
        if( virtualMachines == null ) {
            virtualMachines = new ArrayList<VMDevice>();
        }
        Collections.addAll(virtualMachines, devices);
        return this;
    }

    /**
     * Indicates the VLANs to be associated with this topology. Calls to this method are additive.
     * @param devices one or more VLAN devices to be added to the topology
     * @return this
     */
    public @Nonnull Topology withVLANs(@Nonnull VLANDevice ... devices) {
        if( vlans == null ) {
            vlans = new ArrayList<VLANDevice>();
        }
        Collections.addAll(vlans, devices);
        return this;
    }

    @Override
    public @Nonnull String toString() {
        return (name + " [#" + providerTopologyId + "]");
    }
}
