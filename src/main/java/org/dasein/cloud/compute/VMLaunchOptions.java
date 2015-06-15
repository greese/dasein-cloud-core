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
import org.dasein.cloud.network.NICCreateOptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Configuration of a virtual machine to be launched prior to launch. Dasein Cloud uses this to create a complete
 * virtual machine to the caller's specifications. Implementations may be able to realize a launch configuration in
 * a single API call, or they may require many underlying API calls. All of this will be hidden to the caller.
 * <p>Created by George Reese: 6/22/12 5:35 PM</p>
 * @author George Reese
 * @version 2012.07 Initial introduction
 * @version 2013.01 George Reese Issue #2 Added static IP on launch support
 * @version 2013.01 George Reese Issue #7 Added ramdisk ID and kernel ID options
 * @since 2012.07
 */
@SuppressWarnings("UnusedDeclaration")
public class VMLaunchOptions {
    // NOTE: ADDING/REMOVING/CHANGING AN ATTRIBUTE? MAKE SURE YOU REFLECT THE CHANGE IN THE copy() METHOD
    private String             bootstrapKey;
    private String             bootstrapPassword;
    private String             bootstrapUser;
    private String             dataCenterId;
    private String             description;
    private boolean            extendedAnalytics;
    private String[]           firewallIds;
    private String             friendlyName;
    private String             hostName;
    private String             kernelId;
    private String             machineImageId;
    private Map<String,Object> metaData;
    private String[]           labels;
    private String             networkProductId;
    private NICConfig[]        networkInterfaces;
    private boolean            preventApiTermination;
    private String             privateIp;
    private boolean            provisionPublicIp;
    private String             ramdiskId;
    private String             rootVolumeProductId;
    private String             standardProductId;
    private String[]           staticIpIds;
    private String             userData;
    private String             vlanId;
    private String             subnetId;
    private VolumeAttachment[] volumes;
    private boolean            ioOptimized;
    private boolean            ipForwardingAllowed;
    private String             roleId;
    private Boolean            associatePublicIpAddress;
    private String             affinityGroupId;
    private String             virtualMachineGroup;
    private String             resourcePoolId;
    private String             storagePoolId;
    private String             vmFolderId;
    private String             dnsDomain;
    private String[]           dnsServerList;
    private String[]           dnsSuffixList;
    private String[]           gatewayList;
    private String             netMask;
    private String             winWorkgroupName;
    private String             winOwnerName;
    private String             winOrgName;
    private String             winProductSerialNum;
    private String             clientRequestToken;
    // NOTE: SEE NOTE AT TOP OF ATTRIBUTE LIST WHEN ADDING/REMOVING/CHANGING AN ATTRIBUTE

    static public class NICConfig {
        public String           nicId;
        public NICCreateOptions nicToCreate;
    }

    /**
     * Constructs a new set of launch options from a minimum required configuration.
     * @param withStandardProductId the flavor/size/product ID to be used in the launching the VM
     * @param usingMachineImageId the machine image/template from which the VM will be created
     * @param havingFriendlyName a friendly name for the VM (this may be altered to fit cloud provider rules or entirely disregarded)
     * @param withDescription a long description identifying the function of the virtual machine 
     * @return a set of VM launch options to be used in launching a VM
     */
    static public @Nonnull VMLaunchOptions getInstance(@Nonnull String withStandardProductId, @Nonnull String usingMachineImageId, @Nonnull String havingFriendlyName, @Nonnull String withDescription) {
        return new VMLaunchOptions(withStandardProductId, usingMachineImageId, havingFriendlyName, havingFriendlyName, withDescription);
    }

    /**
     * Constructs a new set of launch options from a minimum required configuration.
     * @param withStandardProductId the flavor/size/product ID to be used in the launching the VM
     * @param usingMachineImageId the machine image/template from which the VM will be created
     * @param withHostName a DNS-friendly name that can be used on the local host, local network, or even in DNS to reference the new VM 
     * @param havingFriendlyName a friendly name for the VM (this may be altered to fit cloud provider rules or entirely disregarded)
     * @param withDescription a long description identifying the function of the virtual machine 
     * @return a set of VM launch options to be used in launching a VM
     */
    static public @Nonnull VMLaunchOptions getInstance(@Nonnull String withStandardProductId, @Nonnull String usingMachineImageId, @Nonnull String withHostName, @Nonnull String havingFriendlyName, @Nonnull String withDescription) {
        return new VMLaunchOptions(withStandardProductId, usingMachineImageId,withHostName, havingFriendlyName, withDescription);
    }


    private VMLaunchOptions() { }
    
    private VMLaunchOptions(@Nonnull String standardProductId, @Nonnull String machineImageId, @Nonnull String hostName, @Nonnull String friendlyName, @Nonnull String description) {
        this.standardProductId = standardProductId;
        this.machineImageId = machineImageId;
        this.description = description;
        this.hostName = hostName;
        this.friendlyName = friendlyName;
        extendedAnalytics = false;
    }

    /**
     * Launches a virtual machine based on the current contents of this set of VM launch options.
     * @param provider the cloud provider in which the VM should be provisioned
     * @return the unique ID of the provisioned virtual machine
     * @throws CloudException an error occurred within the cloud provider while building the VM
     * @throws InternalException an error occurred within Dasein Cloud in preparing the API call
     * @throws OperationNotSupportedException the cloud does not support virtual machines
     */
    public @Nonnull String build(@Nonnull CloudProvider provider) throws CloudException, InternalException {
        ComputeServices services = provider.getComputeServices();

        if( services == null ) {
            throw new OperationNotSupportedException(provider.getCloudName() + " does not support compute services.");
        }
        VirtualMachineSupport support = services.getVirtualMachineSupport();

        if( support == null ) {
            throw new OperationNotSupportedException(provider.getCloudName() + " does not have virtual machine support");
        }
        return support.launch(this).getProviderVirtualMachineId();
    }

    /**
     * Launches multiple virtual machines based on the current contents of this set of launch options. The method is a success
     * if any one virtual machine is provisioned even if any errors occurred provisioning others.
     * @param provider the cloud provider in which the VM should be provisioned
     * @param count the number of virtual machines to provision
     * @return the IDs of the virtual machines that were provisioned
     * @throws CloudException an error occurred within the cloud provider that prevented the provisioning of any VMs
     * @throws InternalException an error occurred within Dasein Cloud in preparing the API call (can happen even if a VM gets provisioned)
     * @throws OperationNotSupportedException the cloud does not support virtual machines
     */
    public @Nonnull Iterable<String> buildMany(@Nonnull CloudProvider provider, int count) throws CloudException, InternalException {
        ComputeServices services = provider.getComputeServices();

        if( services == null ) {
            throw new OperationNotSupportedException(provider.getCloudName() + " does not support compute services.");
        }
        VirtualMachineSupport support = services.getVirtualMachineSupport();

        if( support == null ) {
            throw new OperationNotSupportedException(provider.getCloudName() + " does not have virtual machine support");
        }
        return support.launchMany(this, count);
    }

    /**
     * Copies the meaningful aspects of this set of VM launch options to be used in a second set of VM launch options. The only
     * things NOT copied are attributes that are inherently instance specific such as IP address, volume attachments,
     * private IP, and NICs. Any volumes and/or NICs to be created, however, are preserved.
     * @param havingHostName a host name special for this new set of VM create options
     * @param havingFriendlyName a friendly name special for this new set of VM create options
     * @return a copy of this set of VM launch options minus any instance-specific elements
     */
    public @Nonnull VMLaunchOptions copy(@Nonnull String havingHostName, @Nonnull String havingFriendlyName) {
        VMLaunchOptions options = new VMLaunchOptions(standardProductId, machineImageId, havingHostName, havingFriendlyName, description);

        options.bootstrapKey = bootstrapKey;
        options.bootstrapPassword = bootstrapPassword;
        options.bootstrapUser = bootstrapUser;
        options.dataCenterId = dataCenterId;
        options.extendedAnalytics = extendedAnalytics;
        options.firewallIds = (firewallIds == null ? new String[0] : Arrays.copyOf(options.firewallIds, options.firewallIds.length));
        options.ioOptimized = ioOptimized;
        options.ipForwardingAllowed = ipForwardingAllowed;
        options.kernelId = kernelId;
        options.virtualMachineGroup = virtualMachineGroup;
        options.resourcePoolId = resourcePoolId;
        if( metaData != null ) {
            options.metaData = new HashMap<String, Object>();
            options.metaData.putAll(metaData);
        }
        if( networkInterfaces != null && networkInterfaces.length > 0 ) {
            ArrayList<NICConfig> cfgs = new ArrayList<NICConfig>();

            for( NICConfig cfg : networkInterfaces ) {
                if( cfg.nicToCreate != null ) {
                    NICConfig c = new NICConfig();

                    c.nicToCreate = cfg.nicToCreate.copy(c.nicToCreate.getName() + " - " + hostName);
                    cfgs.add(c);
                }
            }
            options.networkInterfaces = cfgs.toArray(new NICConfig[cfgs.size()]);
        }
        else {
            options.networkInterfaces = new NICConfig[0];
        }
        options.networkProductId = networkProductId;
        options.preventApiTermination = preventApiTermination;
        options.privateIp = null;
        options.provisionPublicIp = provisionPublicIp;
        options.ramdiskId = ramdiskId;
        options.rootVolumeProductId = rootVolumeProductId;
        options.staticIpIds = new String[0];
        options.userData = userData;
        options.vlanId = vlanId;
        options.subnetId = subnetId;
        options.volumes = new VolumeAttachment[0];
        options.ioOptimized = ioOptimized;
        options.ipForwardingAllowed = ipForwardingAllowed;
        options.roleId = roleId;
        if( volumes != null && volumes.length > 0 ) {
            ArrayList<VolumeAttachment> copy = new ArrayList<VolumeAttachment>();

            for( VolumeAttachment a : volumes ) {
                if( a.volumeToCreate != null ) {
                    VolumeAttachment nv = new VolumeAttachment();

                    nv.volumeToCreate = a.volumeToCreate.copy(a.volumeToCreate.getName() + "-" + hostName);
                    copy.add(nv);
                }
            }
            options.volumes = copy.toArray(new VolumeAttachment[copy.size()]);
        }
        options.affinityGroupId = affinityGroupId;
        options.storagePoolId = storagePoolId;
        options.vmFolderId = vmFolderId;
        options.dnsDomain = dnsDomain;
        options.dnsServerList = (dnsServerList == null ? new String[0] : Arrays.copyOf(options.dnsServerList, options.dnsServerList.length));
        options.dnsSuffixList = (dnsSuffixList == null ? new String[0] : Arrays.copyOf(options.dnsSuffixList, options.dnsSuffixList.length));
        options.gatewayList = (gatewayList == null ? new String[0] : Arrays.copyOf(options.gatewayList, options.gatewayList.length));
        options.netMask = netMask;
        options.winWorkgroupName = winWorkgroupName;
        options.winOwnerName = winOwnerName;
        options.winOrgName = winOrgName;
        options.winProductSerialNum = winProductSerialNum;
        options.clientRequestToken = clientRequestToken;
        return options;
    }

    /**
     * @return the identifier for the keypair that will be used to bootstrap a root or other user account.
     */
    public @Nullable String getBootstrapKey() {
        return bootstrapKey;
    }

    /**
     * @return the user-specified password to use in boostrapping the {@link #getBootstrapUser()} account
     */
    public @Nullable String getBootstrapPassword() {
        return bootstrapPassword;
    }

    /**
     * In some cases, this value will be ignored as the user may be hard coded into a machine image or dictated by
     * the cloud provider
     * @return the user-specified user name to use for bootstrapping a user account to the new VM
     */
    public @Nullable String getBootstrapUser() {
        return bootstrapUser;
    }

    /**
     * If none is specified, Dasein Cloud or the cloud provider will pick one.
     * @return the data center into which the VM will be launched
     */
    public @Nullable String getDataCenterId() {
        return dataCenterId;
    }

    /**
     * @return a lengthy description of the role of this virtual machine
     */
    public @Nonnull String getDescription() {
        return description;
    }

    /**
     * @return true if the VM is to be launched with extended analytics enabled
     */
    public boolean isExtendedAnalytics() {
        return extendedAnalytics;
    }

    /**
     * @return the firewall IDs of the firewalls/security groups to launch the virtual machine under
     */
    public @Nonnull String[] getFirewallIds() {
        return (firewallIds == null ? new String[0] : firewallIds);
    }

    /**
     * @return any labels to assign to this virtual machine
     */
    public @Nonnull String[] getLabels() {
        return (labels == null ? new String[0] : labels);
    }

    /**
     * @return the friendly name of the virtual machine
     */
    public @Nonnull String getFriendlyName() {
        return friendlyName;
    }

    /**
     * @return a DNS-friendly host name that may or may not be used by the underlying cloud
     */
    public @Nonnull String getHostName() {
        return hostName;
    }

    /**
     * @return the provider ID of the kernel image to use in launching the virtual machine
     */
    public String getKernelId() {
        return kernelId;
    }

    /**
     * @return the unique cloud ID for the machine image to use in launching the virtual machine
     */
    public @Nonnull String getMachineImageId() {
        return machineImageId;
    }

    /**
     * @return any extra meta-data to assign to the virtual machine
     */
    public @Nonnull Map<String,Object> getMetaData() {
        return (metaData == null ? new HashMap<String, Object>() : metaData);
    }

    /**
     * Some clouds have quality of service offerings in the form of network products that allow you to pick different
     * levels of services within the assigned network.
     * @return the product ID of the network/subnet in which this virtual machine will operate
     */
    public @Nullable String getNetworkProductId() {
        return networkProductId;
    }

    /**
     * @return true if the VM should not be capable of being terminated through the API
     */
    public boolean isPreventApiTermination() {
        return preventApiTermination;
    }

    /**
     * @return all configuration data for networking interfaces to which the VM will be attached
     */
    public NICConfig[] getNetworkInterfaces() {
        return networkInterfaces;
    }

    /**
     * @return the provider ID of the ramdisk image to use in launching the virtual machine
     */
    public String getRamdiskId() {
        return ramdiskId;
    }

    /**
     * @return the resource pool id to use in launching the vm
     */
    public String getResourcePoolId() {
        return resourcePoolId;
    }

    /**
     * Some clouds have quality of service offerings in the form of volume products that allow you to pick
     * different levels of service for a given root volume. The product may also impact disk size.
     * @return the produce ID for the root volume to which the virtual machine will be attached
     */
    public @Nullable String getRootVolumeProductId() {
        return rootVolumeProductId;
    }

    /**
     * @return the flavor/size/product under which this virtual machine will be launched
     */
    public @Nonnull String getStandardProductId() {
        return standardProductId;
    }

    /**
     * @return the list of static IPs to assign to this VM when provisioned
     */
    public @Nonnull String[] getStaticIpIds() {
        if( staticIpIds == null ) {
            return new String[0];
        }
        return Arrays.copyOf(staticIpIds, staticIpIds.length);
    }

    /**
     * @return the private ip when VM is launched in VLAN
     */
    public @Nullable String getPrivateIp() {
      return privateIp;
    }

    /**
     * @return the user data that will be provided to the guest OS post-launch
     */
    public @Nullable String getUserData() {
        return userData;
    }

    /**
     * @return the subnet which the launch VM is going to be assigned to
     */
    public @Nullable String getSubnetId() {
        return subnetId;
    }

    /**
     * @return an optional group association with other virtual machines
     */
    public @Nullable String getVirtualMachineGroup() {
        return virtualMachineGroup;
    }

    /**
     * @return the VLAN or subnet into which the virtual machine will be launched
     */
    public @Nullable String getVlanId() {
        return vlanId;
    }

    /**
     * @return the configuration of non-root volumes to which this machine should be attached after launch
     */
    public @Nonnull VolumeAttachment[] getVolumes() {
        return (volumes == null ? new VolumeAttachment[0] : volumes);
    }

    /**
     * @return the vmFolder that this machine should be launched into
     */
    public @Nullable String getVmFolderId() {
        return vmFolderId;
    }

    /**
     * @return true/false for i/o optimized
     */
    public boolean isIoOptimized() {
      return ioOptimized;
    }
    
    /**
     * Indicates which firewalls this configuration will support. This call adds to any configured firewall IDs.
     * You can therefore call it multiple times if it makes sense to your code logic.
     * @param firewallIds one or more firewall IDs to protect the new VM
     * @return this
     */
    public @Nonnull VMLaunchOptions behindFirewalls(@Nonnull String ... firewallIds) {
        if( this.firewallIds == null || this.firewallIds.length < 1 ) {
            this.firewallIds = firewallIds;
        }
        else if( firewallIds.length > 0 ) {
            String[] tmp = new String[this.firewallIds.length + firewallIds.length];
            int i = 0;
            
            for( String id : this.firewallIds ) {
                tmp[i++] = id;
            }
            for( String id : firewallIds ) {
                tmp[i++] = id;
            }
            this.firewallIds = tmp;
        }
        return this;
    }

    /**
     * Specifies any labels to be added to the virtual machine at launch time
     * @param labels one or more labels to be added to new VM
     * @return this
     */
    public @Nonnull VMLaunchOptions withLabels(String... labels) {
        if (labels != null) {
            this.labels = Arrays.copyOf(labels, labels.length);
        }
        return this;
    }

    /**
     * Indicates the data center into which the VM should be launched.
     * @param dataCenterId the data center into which the VM should be launched
     * @return this
     */
    public @Nonnull VMLaunchOptions inDataCenter(@Nonnull String dataCenterId) {
        this.dataCenterId = dataCenterId;
        return this;
    }

    /**
     * Creates an informal association under a group name for the launched VM with other virtual machines in
     * the system. The underlying cloud may interpret this in any number of ways.
     * @param group the name of the group to which the virtual machine belongs
     * @return this
     */
    public @Nonnull VMLaunchOptions inVirtualMachineGroup(@Nonnull String group) {
        this.virtualMachineGroup = group;
        return this;
    }

    /**
     * Indicates that the virtual machine should be launched with API termination disabled.
     * @return this
     */
    public @Nonnull VMLaunchOptions preventAPITermination() {
        this.preventApiTermination = true;
        return this;
    }

    /**
     * Indicates the VLAN/subnet into which the virtual machine should be launched
     * @param networkProductId the network product, if any, for the VLAN/subnet into which the VM is being launched
     * @param dataCenterId the data center into which the VM is being launched
     * @param vlanId the VLAN/subnet into which the virtual machine is being launched
     * @return this
     */
    public @Nonnull VMLaunchOptions inVlan(@Nullable String networkProductId, @Nonnull String dataCenterId, @Nonnull String vlanId) {
        this.networkProductId = networkProductId;
        this.dataCenterId = dataCenterId;
        this.vlanId = vlanId;
        return this;
    }

    /**
     * Indicates specifically the subnet into which the virtual machine should be launched
     * @param networkProductId the network product, if any, for the VLAN/subnet into which the VM is being launched
     * @param dataCenterId the data center into which the VM is being launched
     * @param vlanId optionally provide the vlanId containing the subnet if the cloud requires it
     * @param subnetId the subnet into which the virtual machine is being launched
     * @return this
     */
    public @Nonnull VMLaunchOptions inSubnet(@Nullable String networkProductId, @Nonnull String dataCenterId, @Nullable String vlanId, @Nonnull String subnetId) {
        this.networkProductId = networkProductId;
        this.dataCenterId = dataCenterId;
        this.vlanId = vlanId;
        this.subnetId = subnetId;
        return this;
    }

    /**
     * Identifies which volumes which will be attached to this virtual machine post-launch. The volumes may
     * already exist or specify new volumes to be created. The order of the volumes matters. This call is
     * accretive, meaning you can call it again to add more attachments.
     * @param attachments the attachments to add
     * @return this
     */
    public @Nonnull VMLaunchOptions withAttachments(@Nonnull VolumeAttachment ... attachments) {
        if( volumes == null || volumes.length < 1 ) {
            volumes = attachments;
        }
        else if( attachments.length > 0 ) {
            VolumeAttachment[] tmp = new VolumeAttachment[volumes.length + attachments.length];
            int i=0;

            for( VolumeAttachment a : volumes ) {
                tmp[i++] = a;
            }
            for( VolumeAttachment a : attachments ) {
                tmp[i++] = a;
            }
            volumes = tmp;
        }
        return this;
    }

    /**
     * Identifies which volumes which will be attached to this virtual machine post-launch. 
     * These are all volumes that will be created. The order of the volumes matters. This call is
     * accretive, meaning you can call it again to add more attachments.
     * @param toBeCreated the options to be used in creating new volumes
     * @return this
     */
    public @Nonnull VMLaunchOptions withAttachments(@Nonnull VolumeCreateOptions ... toBeCreated) {
        int i = 0;
        
        if( volumes != null && volumes.length > 0 ) {
            VolumeAttachment[] tmp = new VolumeAttachment[volumes.length + toBeCreated.length];
            
            for( VolumeAttachment a : volumes ) {
                tmp[i++] = a;
            }
            volumes = tmp;
        }
        else {
            volumes = new VolumeAttachment[toBeCreated.length];
        }
        for( VolumeCreateOptions options : toBeCreated ) {
            VolumeAttachment a = new VolumeAttachment();
            
            a.deviceId = options.getDeviceId();
            a.volumeToCreate = options;
            volumes[i++] = a;
        }
        return this;
    }

    /**
     * Identifies a specific, existing volume to attach post-launch. This call is accretive, meaning that you can
     * call it multiple times to add additional volumes (order matters)
     * @param existingVolumeId the cloud provider volume ID for the volume to attach
     * @param withDeviceId the device ID to use in making the attachment
     * @return this
     */
    public @Nonnull VMLaunchOptions withAttachment(@Nonnull String existingVolumeId, @Nonnull String withDeviceId) {
        VolumeAttachment a = new VolumeAttachment();
        
        a.deviceId = withDeviceId;
        a.existingVolumeId = existingVolumeId;
        if( volumes == null || volumes.length < 1 ) {
            volumes = new VolumeAttachment[] { a };
        }
        else {
            VolumeAttachment[] tmp = new VolumeAttachment[volumes.length + 1];
            int i = 0;
            
            for( VolumeAttachment current : volumes ) {
                tmp[i++] = current;
            }
            tmp[i] = a;
            volumes = tmp;
        }
        return this;
    }

    /**
     * Identifies the SSH key to use in bootstrapping the virtual machine.
     * @param key the SSH key to be used in bootstrapping the VM
     * @return this
     * @deprecated since 2014.08
     *
     */
    public @Nonnull VMLaunchOptions withBoostrapKey(@Nonnull String key) {
        return withBootstrapKey(key);
    }

    /**
     * Identifies the SSH key to use in bootstrapping the virtual machine.
     * @param key the SSH key to be used in bootstrapping the VM
     * @return this
     */
    public @Nonnull VMLaunchOptions withBootstrapKey(@Nonnull String key) {
        this.bootstrapKey = key;
        return this;
    }


    /**
     * Identifies the user and password to use in bootstrapping this virtual machine.
     * @param user the user name of the user to add to the VM
     * @param password the password to be used in accessing the VM
     * @return this
     */
    public @Nonnull VMLaunchOptions withBootstrapUser(@Nonnull String user, @Nonnull String password) {
        this.bootstrapUser = user;
        this.bootstrapPassword = password;
        return this;
    }

    /**
     * Indicates that extended analytics should be enabled for this virtual machine.
     * @return this
     */
    public @Nonnull VMLaunchOptions withExtendedAnalytics() {
        extendedAnalytics = true;
        return this;
    }

    /**
     * Specifies that a kernel and/or ramdisk image should be used in provisioning this virtual machine.
     * @param providerKernelId the kernel ID, if any, to use during the launch
     * @param providerRamdiskId the ramdisk ID, if any, to use during the launch
     * @return this
     */
    public @Nonnull VMLaunchOptions withExtendedImages(@Nullable String providerKernelId, @Nullable String providerRamdiskId) {
        kernelId = providerKernelId;
        ramdiskId = providerRamdiskId;
        return this;
    }

    /**
     * Specifies any meta-data to be associated with the virtual machine at launch time. This method is
     * accretive, meaning that it adds to any existing meta-data (or replaces an existing key). Though Dasein Cloud
     * allows the ability to retain type in meta-data, the reality is that most clouds will convert values to strings.
     * @param key the key of the meta-data entry 
     * @param value the value for the meta-data entry (this will probably become a {@link java.lang.String} in most clouds)
     * @return this
     */
    public @Nonnull VMLaunchOptions withMetaData(@Nonnull String key, @Nonnull Object value) {
        if( metaData == null ) {
            metaData = new HashMap<String, Object>();
        }
        metaData.put(key, value);
        return this;
    }

    /**
     * Specifies meta-data to add onto any existing meta-data being associated with this virtual machine at launch time.
     * This method is accretive, meaning that it adds to any existing meta-data (or replaces an existing keys).
     * Though Dasein Cloud allows the ability to retain type in meta-data, the reality is that most clouds will convert
     * values to strings.
     * @param metaData the meta-data to be set for the new VM
     * @return this
     */
    public @Nonnull VMLaunchOptions withMetaData(@Nonnull Map<String,Object> metaData) {
        if( this.metaData == null ) {
            this.metaData = new HashMap<String, Object>();
        }
        this.metaData.putAll(metaData);
        return this;
    }

    /**
     * Indicates the network interfaces to be attached to this virtual machine at launch time. This method is accretive,
     * meaning it will add to any existing network interface configurations.
     * @param nicIds the unique cloud provider IDs for the network interfaces to be attached
     * @return this
     */
    public @Nonnull VMLaunchOptions withNetworkInterfaces(String ... nicIds) {
        if( networkInterfaces == null || networkInterfaces.length < 1 ) {
            int i = 0;
            
            networkInterfaces = new NICConfig[nicIds.length];
            for( String id : nicIds ) {
                NICConfig cfg = new NICConfig();
                
                cfg.nicId = id;
                networkInterfaces[i++] = cfg;
            }
        }
        else if( nicIds.length > 0 ) {
            NICConfig[] tmp = new NICConfig[networkInterfaces.length + nicIds.length];
            int i=0;

            for( NICConfig cfg : networkInterfaces ) {
                tmp[i++] = cfg;
            }
            for( String id : nicIds ) {
                NICConfig cfg = new NICConfig();

                cfg.nicId = id;
                tmp[i++] = cfg;
            }
            networkInterfaces = tmp;
        }
        return this;        
    }

    /**
     * Indicates configuration options for network interfaces to be created and added onto the virtual machine
     * when it is launched. This method is accretive, meaning it will add to any existing network interface configurations.
     * @param options the options for creating the new network interfaces
     * @return this
     */
    public @Nonnull VMLaunchOptions withNetworkInterfaces(NICCreateOptions ... options) {
        if( networkInterfaces == null || networkInterfaces.length < 1 ) {
            int i = 0;

            networkInterfaces = new NICConfig[options.length];
            for( NICCreateOptions opt : options ) {
                NICConfig cfg = new NICConfig();

                cfg.nicToCreate = opt;
                networkInterfaces[i++] = cfg;
            }
        }
        else if( options.length > 0 ) {
            NICConfig[] tmp = new NICConfig[networkInterfaces.length + options.length];
            int i=0;

            for( NICConfig cfg : networkInterfaces ) {
                tmp[i++] = cfg;
            }
            for( NICCreateOptions opt : options ) {
                NICConfig cfg = new NICConfig();

                cfg.nicToCreate = opt;
                tmp[i++] = cfg;
            }
            networkInterfaces = tmp;
        }
        return this;
    }

    /**
     * Indicates that the virtual machine is to be launched with the specified resource pool.
     * @param resourcePoolId the resource pool ID
     * @return this
     */
    public @Nonnull VMLaunchOptions withResourcePoolId(@Nonnull String resourcePoolId) {
        this.resourcePoolId = resourcePoolId;
        return this;
    }

    /**
     * Indicates that the virtual machine is to be launched with the specified root volume product.
     * @param volumeProductId the product ID of the root volume
     * @return this
     */
    public @Nonnull VMLaunchOptions withRootVolumeProduct(@Nonnull String volumeProductId) {
        this.rootVolumeProductId = volumeProductId;
        return this;
    }

    /**
     * Identifies user data to be made available to the guest OS. This could be properties data, JSON, XML, or
     * whatever other kind of data you like.
     * @param userData the user data to be made available to the guest OS
     * @return this
     */
    public @Nonnull VMLaunchOptions withUserData(@Nonnull String userData) {
        this.userData = userData;
        return this;
    }

    /**
     * Identifies the vm folder that the machine should be launched into
     * @param vmFolderId the vm folder the machine should be launched into
     * @return this
     */
    public @Nonnull VMLaunchOptions withVMFolderId(@Nonnull String vmFolderId) {
        this.vmFolderId = vmFolderId;
        return this;
    }

    public @Nonnull VMLaunchOptions withStaticIps(@Nonnull String ... ipIds) {
        this.staticIpIds = ipIds;
        return this;
    }

    public @Nonnull VMLaunchOptions withIoOptimized(boolean ioOptimized) {
      this.ioOptimized = ioOptimized;
      return this;
    }

    /**
     * Identifies the private ip address to assign to the VM when launched in a VLAN
     * @param ipAddr an ip address within the subnet cidr range which the VM is being launched into
     * @return this
     */
    public @Nonnull VMLaunchOptions withPrivateIp(@Nonnull String ipAddr) {
      this.privateIp = ipAddr;
      return this;
    }

    /**
     * @return true if the VM was provisioned with a public IP address at launch time
     */
    public boolean isProvisionPublicIp() {
        return provisionPublicIp;
    }

    /**
     * Tell the cloud provider to automatically provision a public IP for this VM on launch.
     * @param provisionPublicIp true indicates that a public IP address should be provisioned at launch time
     * @return this
     */
    public @Nonnull VMLaunchOptions withProvisionPublicIp(boolean provisionPublicIp) {
        this.provisionPublicIp = provisionPublicIp;
        return this;
    }

    /**
     * Specifies if the VM can forward IP packets that don't match the IP address of the
     * VM sending the packet. This allows VMs to act as NAT instances.
     *
     * @return if IP forwarding is enabled
     */
    public boolean isIpForwardingAllowed() {
      return ipForwardingAllowed;
    }

    /**
     * See {@link #isIpForwardingAllowed()}
     * @param ipForwardingAllowed {@code true} to allow IP forwarding
     * @return this
     */
    public @Nonnull VMLaunchOptions withIpForwardingAllowed( boolean ipForwardingAllowed ) {
      this.ipForwardingAllowed = ipForwardingAllowed;
      return this;
    }

    /**
     * Identifies a service account/role that the virtual machine will be able to use
     * @param roleId the role Id from the provider
     * @return this
     */
    public @Nonnull VMLaunchOptions withRoleId(@Nonnull String roleId) {
      this.roleId = roleId;
      return this;
    }

    /**
     * @return the role id
     */
    public @Nullable String getRoleId() {
      return roleId;
    }

    /**
     * Associate a public IP address at launch, use cloud defaults if null
     *
     * @param publicIpAddress associate with public IP address if {@code true}
     * @return this
     */
    public @Nonnull VMLaunchOptions withAssociatePublicIpAddress( final @Nullable Boolean publicIpAddress ) {
      this.associatePublicIpAddress = publicIpAddress;
      return this;
    }

    /**
     * @return {@code true} if VM should be associated with a public IP address, use cloud provider defaults if {@code null}
     * @see #withAssociatePublicIpAddress(Boolean)
     */
    public @Nullable Boolean isAssociatePublicIpAddress() {
      return associatePublicIpAddress;
    }

    /**
     * @return provider affinity group id
     * @see #withAffinityGroupId(String)
     */
    public String getAffinityGroupId() {
        return affinityGroupId;
    }

    /**
     * Specifies the affinity group to launch the instance within. Affinity groups are a logical grouping of instances
     * meant for low-latency clusters. Affinity groups are not supported by all providers.
     * @param affinityGroupId the affinity group id
     * @return this
     */
    public @Nonnull VMLaunchOptions withAffinityGroupId( @Nonnull String affinityGroupId ) {
        this.affinityGroupId = affinityGroupId;
        return this;
    }

    /**
     * @return provider storage pool id
     * @see #withStoragePoolId(String)
     */
    public @Nullable String getStoragePoolId() {
        return storagePoolId;
    }

    /**
     * Specifies the stroage pool to launch the instance within. Storage pools are the physical location
     * of the vm disk files. Storage pools are not supported by all providers.
     * @param storagePoolId the storage pool id
     * @return this
     */
    public @Nonnull VMLaunchOptions withStoragePoolId( @Nonnull String storagePoolId ) {
        this.storagePoolId = storagePoolId;
        return this;
    }

    /** The following attributes are used when manually specifying a private ip address

    /**
     * @return the dns servers, for a virtual network adapter with a static IP address to launch the virtual machine under
     */
    public @Nonnull String[] getDnsServerList() {
        return (dnsServerList == null ? new String[0] : dnsServerList);
    }

    /**
     * Specifies the dns servers to be used in the virtual machine at launch time
     * @param dnsServers one or more dns servers to be used for the  new VM
     * @return this
     */
    public @Nonnull VMLaunchOptions withDnsServerList(String... dnsServers) {
        if (dnsServers != null) {
            this.dnsServerList = Arrays.copyOf(dnsServers, dnsServers.length);
        }
        return this;
    }

    /**
     * @return the name resolution suffixes for a virtual network adapter to launch the virtual machine under
     */
    public @Nonnull String[] getDnsSuffixList() {
        return (dnsSuffixList == null ? new String[0] : dnsSuffixList);
    }

    /**
     * Specifies the dns suffixes to be used for the virtual machine at launch time
     * @param dnsSuffix one or more dns suffixes to be used for the new VM
     * @return this
     */
    public @Nonnull VMLaunchOptions withDnsSuffixList(@Nullable String... dnsSuffix) {
        if (dnsSuffix != null) {
            this.dnsSuffixList = Arrays.copyOf(dnsSuffix, dnsSuffix.length);
        }
        return this;
    }

    /**
     * @return a list of gateways, in order of preference
     */
    public @Nonnull String[] getGatewayList() {
        return (gatewayList == null ? new String[0] : gatewayList);
    }

    /**
     * Specifies the gateways to be used for the virtual machine at launch time
     * @param gateway one or more gateways to be used for the new VM
     * @return this
     */
    public @Nonnull VMLaunchOptions withGatewayList(@Nullable String... gateway) {
        if (gateway != null) {
            this.gatewayList = Arrays.copyOf(gateway, gateway.length);
        }
        return this;
    }

    /**
     * @return a network mask for configuring the VM's virtual network adapter
     */
    public @Nullable String getNetMask() {
        return netMask;
    }

    /**
     * Specifies the network mask to be used for the virtual machine at launch time. This may be necessary
     * when launching with a fixed IP address.
     * @param netMask network mask to be used
     * @return this
     */
    public @Nonnull VMLaunchOptions withNetMask(@Nullable String netMask) {
        this.netMask = netMask;
        return this;
    }

    /**
     * @return a DNS domain suffix of this virtual machine
     */
    public @Nullable String getDnsDomain() {
        return dnsDomain;
    }

    /**
     * Indicates the dnsDomain into which the VM should be launched.
     * @param dnsDomain the dns domain into which the VM should be launched
     * @return this
     */
    public @Nonnull VMLaunchOptions withDnsDomain(@Nonnull String dnsDomain) {
        this.dnsDomain = dnsDomain;
        return this;
    }

    /**
     * @return the owner of this virtual machine (Windows only)
     */
    public @Nullable String getWinOwnerName() {
        return winOwnerName;
    }

    /**
     * Indicates the windows owner of the VM to be launched.
     * @param winOwnerName the windows owner of the VM to be launched
     * @return this
     */
    public @Nonnull VMLaunchOptions withWinOwnerName(@Nonnull String winOwnerName) {
        this.winOwnerName = winOwnerName;
        return this;
    }

    /**
     * @return the organisation name of this virtual machine (Windows only)
     */
    public @Nullable String getWinOrgName() {
        return winOrgName;
    }

    /**
     * Indicates the windows organisation name of the VM to be launched.
     * @param winOrgName the windows organisation name of the VM to be launched
     * @return this
     */
    public @Nonnull VMLaunchOptions withWinOrgName(@Nonnull String winOrgName) {
        this.winOrgName = winOrgName;
        return this;
    }

    /**
     * @return the windows workgroup name of this virtual machine
     */
    public @Nullable String getWinWorkgroupName() {
        return winWorkgroupName;
    }

    /**
     * Indicates the windows workgroup into which the VM should be launched.
     * @param winWorkgroupName the windows workgroup into which the VM should be launched
     * @return this
     */
    public @Nonnull VMLaunchOptions withWinWorkgroupName(@Nullable String winWorkgroupName) {
        this.winWorkgroupName = winWorkgroupName;
        return this;
    }

    /**
     * @return the windows serial number of this virtual machine
     */
    public @Nullable String getWinProductSerialNum() {
        return winProductSerialNum;
    }

    /**
     * Indicates the windows serial number of the VM to be launched.
     * @param winProductSerialNum the windows serial number of the VM to be launched
     * @return this
     */
    public @Nonnull VMLaunchOptions withWinProductSerialNum(@Nullable String winProductSerialNum) {
        this.winProductSerialNum = winProductSerialNum;
        return this;
    }

    /**
     * @return the client generated request token
     */
    public @Nullable String getClientRequestToken() {return clientRequestToken;}

    /**
     * Provides a request token used by the client to provide idempotency for certain API requests
     * @param clientRequestToken the token
     * @return this
     */
    public @Nonnull VMLaunchOptions withClientRequestToken(@Nullable String clientRequestToken){
        this.clientRequestToken = clientRequestToken;
        return this;
    }
}
