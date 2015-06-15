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

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.Requirement;
import org.dasein.cloud.dc.DataCenter;
import org.dasein.util.uom.storage.Gigabyte;
import org.dasein.util.uom.storage.Storage;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuration options for creating volumes. When you create volumes, you provide an instance of this class to
 * indicate the different configuration options for instantiating the volume. Not all clouds or volume types require
 * all values to be specified. Use the meta-data in {@link VolumeSupport} to programmatically determine what is
 * required.
 * <p>Created by George Reese: 6/22/12 8:41 PM</p>
 * @author George Reese (george.reese@imaginary.com)
 * @since 2012-07
 * @version 2012-07 introduced volume create options
 * @version 2013.02 added javadoc
 */
public class VolumeCreateOptions {
    /**
     * Provides options for creating a block volume of a specific size. Useful only when {@link VolumeSupport#getVolumeProductRequirement()}
     * is {@link Requirement#NONE} or {@link Requirement#OPTIONAL}.
     * @param size the size of the volume to be created (or to be resized to)
     * @param name the name of the new volume
     * @param description a friendly description of the purpose of the new volume
     * @return an object representing the options for creating the new volume
     */
    static public VolumeCreateOptions getInstance(@Nonnull Storage<?> size, @Nonnull String name, @Nonnull String description) {
        return new VolumeCreateOptions(null, null, size, name, description, 0);
    }

    /**
     * Provides options for creating a block volume of a specific size based on a specific product. This method makes no sense
     * when {@link VolumeSupport#getVolumeProductRequirement()}  is {@link Requirement#NONE}.
     * @param volumeProductId the ID of the volume product from {@link VolumeSupport#listVolumeProducts()} to use in creating the volume
     * @param size the size of the volume to be created (or to be resized to)
     * @param name the name of the new volume
     * @param description a friendly description of the purpose of the new volume
     * @param iops the minimum guaranteed iops or 0 if no guarantees are sought
     * @return an object representing the options for creating the new volume
     */
    static public VolumeCreateOptions getInstance(@Nonnull String volumeProductId, @Nonnull Storage<?> size, @Nonnull String name, @Nonnull String description, @Nonnegative int iops) {
        return new VolumeCreateOptions(volumeProductId, null, size, name, description, iops);
    }

    /**
     * Provides options for creating a network volume of a specific size. Useful only when {@link VolumeSupport#getVolumeProductRequirement()}
     * is {@link Requirement#NONE} or {@link Requirement#OPTIONAL}.
     * @param inVlanId the ID of the VLAN in which the network volume will be created
     * @param size the size of the volume to be created (or to be resized to)
     * @param name the name of the new volume
     * @param description a friendly description of the purpose of the new volume
     * @return an object representing the options for creating the new volume
     */
    static public VolumeCreateOptions getNetworkInstance(@Nonnull String inVlanId, @Nonnull Storage<?> size, @Nonnull String name, @Nonnull String description) {
        VolumeCreateOptions options = new VolumeCreateOptions(null, null, size, name, description, 0);

        options.vlanId = inVlanId;
        options.format = VolumeFormat.NFS;
        return options;
    }

    /**
     * Provides options for creating a network volume of a specific size based on a specific product. This method makes no sense
     * when {@link VolumeSupport#getVolumeProductRequirement()} is {@link Requirement#NONE}.
     * @param inVlanId the ID of the VLAN in which the network volume will be created
     * @param volumeProductId the ID of the volume product from {@link VolumeSupport#listVolumeProducts()} to use in creating the volume
     * @param size the size of the volume to be created (or to be resized to)
     * @param name the name of the new volume
     * @param description a friendly description of the purpose of the new volume
     * @return an object representing the options for creating the new volume
     */
    static public VolumeCreateOptions getNetworkInstance(@Nonnull String volumeProductId, @Nonnull String inVlanId, @Nonnull Storage<?> size, @Nonnull String name, @Nonnull String description) {
        VolumeCreateOptions options = new VolumeCreateOptions(volumeProductId, null, size, name, description, 0);

        options.vlanId = inVlanId;
        options.format = VolumeFormat.NFS;
        return options;
    }

    /**
     * Provides options for creating a network volume of a specific size based on a specific product. This method makes no sense
     * when {@link VolumeSupport#getVolumeProductRequirement()} is {@link Requirement#NONE}.
     * @param inVlanId the ID of the VLAN in which the network volume will be created
     * @param volumeProductId the ID of the volume product from {@link VolumeSupport#listVolumeProducts()} to use in creating the volume
     * @param size the size of the volume to be created (or to be resized to)
     * @param name the name of the new volume
     * @param description a friendly description of the purpose of the new volume
     * @param iops the minimum guaranteed iops or 0 if no guarantees are sought
     * @return an object representing the options for creating the new volume
     */
    static public VolumeCreateOptions getNetworkInstance(@Nonnull String volumeProductId, @Nonnull String inVlanId, @Nonnull Storage<?> size, @Nonnull String name, @Nonnull String description, @Nonnegative int iops) {
        VolumeCreateOptions options = new VolumeCreateOptions(volumeProductId, null, size, name, description, iops);

        options.vlanId = inVlanId;
        options.format = VolumeFormat.NFS;
        return options;
    }

    /**
     * Provides options for creating a block volume from a snapshot. This method makes no sense when {@link ComputeServices#getSnapshotSupport()}
     * is <code>null</code>. Useful only when {@link VolumeSupport#getVolumeProductRequirement()}
     * is {@link Requirement#NONE} or {@link Requirement#OPTIONAL}.
     * @param snapshotId the ID of the snapshot from which the volume will be created
     * @param size the size of the volume to be created
     * @param name the name of the volume to be created
     * @param description a friendly description of the purpose of the new volume
     * @return an object representing the options for creating the new volume
     */
    static public VolumeCreateOptions getInstanceForSnapshot(@Nonnull String snapshotId, @Nonnull Storage<?> size, @Nonnull String name, @Nonnull String description) {
        return new VolumeCreateOptions(null, snapshotId, size, name, description, 0);
    }

    /**
     * Provides options for creating a block volume from a snapshot. This method makes no sense when {@link ComputeServices#getSnapshotSupport()}
     * is <code>null</code>. Useful only when {@link VolumeSupport#getVolumeProductRequirement()} is <em>NOT</em>
     * {@link Requirement#NONE}.
     * @param volumeProductId the ID of the volume product from which the new volume will be created
     * @param snapshotId the ID of the snapshot from which the volume will be created
     * @param size the size of the volume to be created
     * @param name the name of the volume to be created
     * @param description a friendly description of the purpose of the new volume
     * @param iops the guaranteed iops for the new volume (or 0 for no guarantee)
     * @return an object representing the options for creating the new volume
     */
    static public VolumeCreateOptions getInstanceForSnapshot(@Nonnull String volumeProductId, @Nonnull String snapshotId, @Nonnull Storage<?> size, @Nonnull String name, @Nonnull String description, @Nonnegative int iops) {
        return new VolumeCreateOptions(volumeProductId, snapshotId, size, name, description, iops);
    }

    // NOTE: ADDING/REMOVING/CHANGING AN ATTRIBUTE? MAKE SURE YOU REFLECT THE CHANGE IN THE copy() METHOD
    private String             dataCenterId;
    private String             providerVirtualMachineId;
    private String             description;
    private String             deviceId;
    private VolumeFormat       format;
    private int                iops;
    private Map<String,Object> metaData;
    private String             name;
    private String             snapshotId;
    private String             virtualMachineId;
    private String             vlanId;
    private String             volumeProductId;
    private Storage<Gigabyte>  volumeSize;
    // NOTE: SEE NOTE AT TOP OF ATTRIBUTE LIST WHEN ADDING/REMOVING/CHANGING AN ATTRIBUTE

    public void setDataCenterId(String dataCenterId) {
      this.dataCenterId = dataCenterId;
    }

    public void setProviderVirtualMachineId(String providerVirtualMachineId){
        this.providerVirtualMachineId = providerVirtualMachineId;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public void setDeviceId(String deviceId) {
      this.deviceId = deviceId;
    }

    public void setFormat(VolumeFormat format) {
      this.format = format;
    }

    public void setIops(int iops) {
      this.iops = iops;
    }

    public void setMetaData(Map<String, Object> metaData) {
      this.metaData = metaData;
    }

    public void setName(String name) {
      this.name = name;
    }

    public void setSnapshotId(String snapshotId) {
      this.snapshotId = snapshotId;
    }

    public void setVirtualMachineId(String virtualMachineId) {
      this.virtualMachineId = virtualMachineId;
    }

    public void setVlanId(String vlanId) {
      this.vlanId = vlanId;
    }

    public void setVolumeProductId(String volumeProductId) {
      this.volumeProductId = volumeProductId;
    }

    public void setVolumeSize(Storage<Gigabyte> volumeSize) {
      this.volumeSize = volumeSize;
    }

    @SuppressWarnings("UnusedDeclaration")
    private VolumeCreateOptions() { }

    private VolumeCreateOptions(@Nullable String volumeProductId, @Nullable String snapshotId, @Nonnull Storage<?> size, @Nonnull String name, @Nonnull String description, @Nonnegative int iops) {
        this.volumeProductId = volumeProductId;
        this.snapshotId = snapshotId;
        volumeSize = (Storage<Gigabyte>)size.convertTo(Storage.GIGABYTE);
        this.name = name;
        this.description = description;
        this.iops = iops;
        this.format = VolumeFormat.BLOCK;
    }

    /**
     * Provisions a volume in the specified cloud based on the options defined in this object.
     * @param provider the cloud provider in which the volume should be provisioned
     * @return the unique ID of the newly provisioned volume
     * @throws CloudException an error occurred with the cloud provider while provisioning the volume
     * @throws InternalException an error occurred within Dasein Cloud while preparing the API call
     * @throws OperationNotSupportedException the cloud does not support volumes
     */
    public @Nonnull String build(@Nonnull CloudProvider provider) throws CloudException, InternalException {
        ComputeServices services = provider.getComputeServices();

        if( services == null ) {
            throw new OperationNotSupportedException(provider.getCloudName() + " does not have support for compute services");
        }
        VolumeSupport support = services.getVolumeSupport();

        if( support == null ) {
            throw new OperationNotSupportedException(provider.getCloudName() + " does not have volume support");
        }
        return support.createVolume(this);
    }

    /**
     * Makes a copy of these creation options so that individual values may be altered as needed.
     * @param withName the name of the volume to be created
     * @return a copy of these creation options
     */
    public @Nonnull VolumeCreateOptions copy(@Nonnull String withName) {
        VolumeCreateOptions options = new VolumeCreateOptions(volumeProductId, snapshotId, volumeSize, withName, description, iops);

        options.dataCenterId = dataCenterId;
        options.providerVirtualMachineId = providerVirtualMachineId;
        options.deviceId = deviceId;
        options.format = format;
        options.virtualMachineId = virtualMachineId;
        options.vlanId = vlanId;
        if( metaData != null ) {
            options.metaData = new HashMap<String, Object>();
            options.metaData.putAll(metaData);
        }
        return options;
    }

    /**
     * @return the data center into which the new volume will be provisioned
     */
    public @Nullable String getDataCenterId() {
        return dataCenterId;
    }

    /**
     * @return the Virtual Machine instance on which the new volume will be attached at provision time
     */
    public @Nullable String getProviderVirtualMachineId(){
        return providerVirtualMachineId;
    }

    /**
     * @return a friendly description of the function of the volume to be created
     */
    public @Nonnull String getDescription() {
        return description;
    }

    /**
     * @return the device ID under which the volume will be attached
     */
    public @Nullable String getDeviceId() {
        return deviceId;
    }

    /**
     * @return the format for the requested volume
     */
    public @Nonnull VolumeFormat getFormat() {
        return format;
    }

    /**
     * Provides the requested minimum IOPS. This value will be ignored in clouds that cannot honor it.
     * @return a minimum IOPS guarantee for this volume, or 0 for no guarantees
     */
    public @Nonnegative int getIops() {
        return iops;
    }

    /**
     * @return any extra meta-data to assign to the volume
     */
    public @Nonnull Map<String,Object> getMetaData() {
        return (metaData == null ? new HashMap<String, Object>() : metaData);
    }

    /**
     * @return a friendly (and ideally unique) name for this value for display in different contexts
     */
    public @Nonnull String getName() {
        return name;
    }

    /**
     * @return the unique ID of the {@link Snapshot} from which this volume will be created
     */
    public @Nullable String getSnapshotId() {
        return snapshotId;
    }

    /**
     * @return the virtual machine to which this volume will be automatically attached post-create.
     */
    public @Nullable String getVirtualMachineId() {
        return virtualMachineId;
    }

    /**
     * @return the VLAN in which the volume will be provisioned (typically specified for network volumes)
     */
    public @Nullable String getVlanId() {
        return vlanId;
    }

    /**
     * @return the unique ID of the {@link VolumeProduct} to use in creating a new volume
     */
    public @Nullable String getVolumeProductId() {
        return volumeProductId;
    }

    /**
     * A cloud should honor the size <em>as best as possible</em> when a request is made to create a volume. The
     * Dasein implementation will favor creating a volume over exactly matching the requested size.
     * @return the expected size of the volume to be created
     */
    public @Nonnull Storage<Gigabyte> getVolumeSize() {
        return volumeSize;
    }

    /**
     * Specifies the data center in which the volume will be provisioned
     * @param dataCenterId the unique ID of the {@link DataCenter} in which the volume will be provisioned
     * @return this
     */
    public @Nonnull VolumeCreateOptions inDataCenter(@Nonnull String dataCenterId) {
        this.dataCenterId = dataCenterId;
        return this;
    }

    /**
     * Indicates how the volume will be attached to a specific virtual machine on create.
     * @param vmId the unique ID of the {@link VirtualMachine} to which the new volume will be automatically attached after create
     * @param asDeviceId the device ID (see {@link VolumeSupport#listPossibleDeviceIds(Platform)} for valid device IDs) under which it will be attached
     * @return this
     */
    public @Nonnull VolumeCreateOptions withAttachment(@Nonnull String vmId, @Nonnull String asDeviceId) {
        this.virtualMachineId = vmId;
        this.deviceId = asDeviceId;
        return this;
    }

    /**
     * Specifies any meta-data to be associated with the volume at launch time. This method is
     * accretive, meaning that it adds to any existing meta-data (or replaces an existing key). Though Dasein Cloud
     * allows the ability to retain type in meta-data, the reality is that most clouds will convert values to strings.
     * @param key the key of the meta-data entry
     * @param value the value for the meta-data entry (this will probably become a {@link java.lang.String} in most clouds)
     * @return this
     */
    public @Nonnull VolumeCreateOptions withMetaData(@Nonnull String key, @Nonnull Object value) {
        if( metaData == null ) {
            metaData = new HashMap<String, Object>();
        }
        metaData.put(key, value);
        return this;
    }

    /**
     * Specifies meta-data to add onto any existing meta-data being associated with this volume at launch time.
     * This method is accretive, meaning that it adds to any existing meta-data (or replaces an existing keys).
     * Though Dasein Cloud allows the ability to retain type in meta-data, the reality is that most clouds will convert
     * values to strings.
     * @param metaData the meta-data to be set for the new volume
     * @return this
     */
    public @Nonnull VolumeCreateOptions withMetaData(@Nonnull Map<String,Object> metaData) {
        if( this.metaData == null ) {
            this.metaData = new HashMap<String, Object>();
        }
        this.metaData.putAll(metaData);
        return this;
    }

    public @Nonnull VolumeCreateOptions withVirtualMachineId(@Nonnull String providerVirtualMachineId){
        this.providerVirtualMachineId = providerVirtualMachineId;
        return this;
    }
}
