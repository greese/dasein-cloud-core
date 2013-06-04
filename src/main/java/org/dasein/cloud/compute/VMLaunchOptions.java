/**
 * Copyright (C) 2009-2013 Dell, Inc.
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
    static public class NICConfig {
        public String nicId;
        public NICCreateOptions nicToCreate;
    }
    
    static public class VolumeAttachment {
        public String deviceId;
        public String existingVolumeId;
        public VolumeCreateOptions volumeToCreate;
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
    private String             networkProductId;
    private NICConfig[]        networkInterfaces;
    private boolean            preventApiTermination;
    private String             ramdiskId;
    private String             rootVolumeProductId;
    private String             standardProductId;
    private String[]           staticIpIds;
    private String             userData;
    private String             vlanId;
    private VolumeAttachment[] volumes;

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
     * @return the user data that will be provided to the guest OS post-launch
     */
    public @Nullable String getUserData() {
        return userData;
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
     * Indicates the data center into which the VM should be launched.
     * @param dataCenterId the data center into which the VM should be launched
     * @return this
     */
    public @Nonnull VMLaunchOptions inDataCenter(@Nonnull String dataCenterId) {
        this.dataCenterId = dataCenterId;
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
     */
    public @Nonnull VMLaunchOptions withBoostrapKey(@Nonnull String key) {
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

    public @Nonnull VMLaunchOptions withStaticIps(@Nonnull String ... ipIds) {
        this.staticIpIds = ipIds;
        return this;
    }
}
