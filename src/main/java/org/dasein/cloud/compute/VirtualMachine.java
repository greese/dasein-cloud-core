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

import org.dasein.cloud.*;
import org.dasein.cloud.network.Networkable;
import org.dasein.cloud.network.RawAddress;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.Callable;

/**
 * <p>
 * A virtual machine running within a cloud. This class contains the current state at the time
 * of any cloud API call for the target VM.
 * </p>
 *
 * @author George Reese @ enstratius (http://www.enstratius.com)
 * @version 2013.04 added access to shell key IDs
 */
public class VirtualMachine implements Networkable, Taggable {
    private String                  affinityGroupId;
    private Architecture            architecture;
    private boolean                 clonable;
    private long                    creationTimestamp;
    private VmState                 currentState;
    private Map<String, String>     tags;
    private String[]                labels;
    private String                  description;
    private boolean                 imagable;
    private long                    lastBootTimestamp;
    private long                    lastPauseTimestamp;
    private String                  name;
    private boolean                 pausable;
    private boolean                 persistent;
    private Platform                platform;
    private String                  privateDnsAddress;
    private RawAddress[]            privateIpAddresses;
    private String                  productId;
    private String                  providerAssignedIpAddressId;
    private String                  providerDataCenterId;
    private String                  providerKernelImageId;
    private String                  providerMachineImageId;
    private String                  providerOwnerId;
    private String                  providerRamdiskImageId;
    private String                  providerRegionId;
    private String[]                providerShellKeyIds;
    private String                  providerSubnetId;
    private String                  providerVirtualMachineId;
    private String[]                providerNetworkInterfaceIds;
    private String                  providerVlanId;
    private String                  providerKeypairId;
    private String[]                providerFirewallIds;
    private String[]                providerVolumeIds;
    private String                  publicDnsAddress;
    private RawAddress[]            publicIpAddresses;
    private boolean                 rebootable;
    private String                  rootPassword;
    private String                  rootUser;
    private String                  stateReasonMessage;
    private long                    terminationTimestamp;
    private Volume[]                volumes;
    private boolean                 ioOptimized;
    private boolean                 ipForwardingAllowed;
    private String                  providerRoleId;
    private VmStatus                providerHostStatus;
    private VmStatus                providerVmStatus;
    private String                  virtualMachineGroup;
    private VisibleScope            visibleScope;
    private VirtualMachineLifecycle lifecycle;
    private String                  spotRequestId; // TODO - add filtering by, add setter/getter
    private String                  resourcePoolId;
    private String                  clientRequestToken;

    public VirtualMachine() {
    }

    public boolean equals( Object ob ) {
        if( ob == null ) {
            return false;
        }
        if( ob == this ) {
            return true;
        }
        if( !getClass().getName().equals(ob.getClass().getName()) ) {
            return false;
        }
        VirtualMachine other = ( VirtualMachine ) ob;

        if( !getProviderRegionId().equals(other.getProviderRegionId()) ) {
            return false;
        }
        return getProviderVirtualMachineId().equals(other.getProviderVirtualMachineId());
    }

    public void addTag( Tag t ) {
        addTag(t.getKey(), t.getValue());
    }

    public void addTag( String key, String value ) {
        getTags().put(key, value);
    }

    private transient volatile Callable<String> passwordCallback = null;

    public void setPasswordCallback( Callable<String> callback ) {
        this.passwordCallback = callback;
    }

    public void setRootPassword( String rootPassword ) {
        this.rootPassword = rootPassword;
    }

    public String getRootPassword() {
        String pw;

        synchronized ( this ) {
            pw = rootPassword;
        }
        if( pw != null ) {
            return pw;
        }
        if( passwordCallback != null ) {
            pw = fetchPassword();
        }
        return pw;
    }

    public String getRootPassword( long timeoutInMilliseconds ) throws InterruptedException {
        long timeout = System.currentTimeMillis() + timeoutInMilliseconds;
        String pw = getRootPassword();
        boolean hasCallback;

        synchronized ( this ) {
            hasCallback = ( passwordCallback != null );
        }
        if( hasCallback ) {
            while( pw == null ) {
                if( timeout <= System.currentTimeMillis() ) {
                    throw new InterruptedException("System timed out waiting for a password to become available.");
                }
                try {
                    Thread.sleep(15000L);
                } catch( InterruptedException ignore ) {
                }
                pw = getRootPassword();
            }
        }
        return pw;
    }

    public String fetchPassword() {
        String pw;

        synchronized ( this ) {
            pw = rootPassword;
        }
        if( pw != null ) {
            return pw;
        }
        if( passwordCallback == null ) {
            return null;
        }
        try {
            pw = passwordCallback.call();
            if( pw != null ) {
                synchronized ( this ) {
                    rootPassword = pw;
                }
            }
            return rootPassword;
        } catch( Exception e ) {
            return null;
        }
    }

    public String toString() {
        return name + " [" + providerVirtualMachineId + "]";
    }

    public String getAffinityGroupId(){
        return affinityGroupId;
    }

    public void setAffinityGroupId(String affinityGroupId){
        this.affinityGroupId = affinityGroupId;
    }

    public Architecture getArchitecture() {
        return architecture;
    }

    public void setArchitecture( Architecture architecture ) {
        this.architecture = architecture;
    }

    public boolean isClonable() {
        return clonable;
    }

    public void setClonable( boolean clonable ) {
        this.clonable = clonable;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp( long creationTimestamp ) {
        this.creationTimestamp = creationTimestamp;
    }

    public VmState getCurrentState() {
        return currentState;
    }

    public void setCurrentState( VmState currentState ) {
        this.currentState = currentState;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public boolean isImagable() {
        return imagable;
    }

    public void setImagable( boolean imagable ) {
        this.imagable = imagable;
    }

    public long getLastBootTimestamp() {
        return lastBootTimestamp;
    }

    public void setLastBootTimestamp( long lastBootTimestamp ) {
        this.lastBootTimestamp = lastBootTimestamp;
    }

    public long getLastPauseTimestamp() {
        return lastPauseTimestamp;
    }

    public void setLastPauseTimestamp( long lastPauseTimestamp ) {
        this.lastPauseTimestamp = lastPauseTimestamp;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public boolean isPausable() {
        return pausable;
    }

    public void setPausable( boolean pausable ) {
        this.pausable = pausable;
    }

    public boolean isPersistent() {
        return persistent;
    }

    public void setPersistent( boolean persistent ) {
        this.persistent = persistent;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform( Platform platform ) {
        this.platform = platform;
    }

    public String getPrivateDnsAddress() {
        return privateDnsAddress;
    }

    public void setPrivateDnsAddress( String privateDnsAddress ) {
        this.privateDnsAddress = privateDnsAddress;
    }

    public @Nonnull RawAddress[] getPrivateAddresses() {
        return ( privateIpAddresses == null ? new RawAddress[0] : privateIpAddresses );
    }

    /**
     * @return a list of private IP address strings
     * @deprecated Use {@link #getPrivateAddresses()}
     */
    @Deprecated
    public String[] getPrivateIpAddresses() {
        String[] addrs = new String[privateIpAddresses == null ? 0 : privateIpAddresses.length];

        if( privateIpAddresses != null ) {
            int i = 0;

            for( RawAddress addr : privateIpAddresses ) {
                addrs[i++] = addr.getIpAddress();
            }
        }
        return addrs;
    }

    public void setPrivateAddresses( @Nonnull RawAddress... addresses ) {
        privateIpAddresses = addresses;
    }

    /**
     * @param privateIpAddresses array of private ip addresses associated with this VM
     * @deprecated Use {@link #setPrivateAddresses(RawAddress...)}
     */
    @Deprecated
    public void setPrivateIpAddresses( String[] privateIpAddresses ) {
        this.privateIpAddresses = new RawAddress[privateIpAddresses == null ? 0 : privateIpAddresses.length];
        if( privateIpAddresses != null ) {
            for( int i = 0; i < this.privateIpAddresses.length; i++ ) {
                this.privateIpAddresses[i] = new RawAddress(privateIpAddresses[i]);
            }
        }
    }

    public String getProviderAssignedIpAddressId() {
        return providerAssignedIpAddressId;
    }

    public void setProviderAssignedIpAddressId( String providerAssignedIpAddressId ) {
        this.providerAssignedIpAddressId = providerAssignedIpAddressId;
    }

    public String getProviderDataCenterId() {
        return providerDataCenterId;
    }

    public void setProviderDataCenterId( String providerDataCenterId ) {
        this.providerDataCenterId = providerDataCenterId;
    }

    public String getProviderMachineImageId() {
        return providerMachineImageId;
    }

    public void setProviderMachineImageId( String providerMachineImageId ) {
        this.providerMachineImageId = providerMachineImageId;
    }

    public String getProviderOwnerId() {
        return providerOwnerId;
    }

    public void setProviderOwnerId( String providerOwnerId ) {
        this.providerOwnerId = providerOwnerId;
    }

    public String getProviderRegionId() {
        return providerRegionId;
    }

    public void setProviderRegionId( String providerRegionId ) {
        this.providerRegionId = providerRegionId;
    }

    public String getProviderVirtualMachineId() {
        return providerVirtualMachineId;
    }

    public void setProviderVirtualMachineId( String providerVirtualMachineId ) {
        this.providerVirtualMachineId = providerVirtualMachineId;
    }

    public String getPublicDnsAddress() {
        return publicDnsAddress;
    }

    public void setPublicDnsAddress( String publicDnsAddress ) {
        this.publicDnsAddress = publicDnsAddress;
    }

    public @Nonnull RawAddress[] getPublicAddresses() {
        return ( publicIpAddresses == null ? new RawAddress[0] : publicIpAddresses );
    }

    /**
     * @return array of public ip addresses associated with this VM
     * @deprecated Use {@link #getPublicAddresses()}
     */
    @Deprecated
    public String[] getPublicIpAddresses() {
        if( publicIpAddresses == null || publicIpAddresses.length < 0 ) {
            if( publicDnsAddress == null ) {
                return new String[0];
            }
            String ip = resolve(publicDnsAddress);

            if( ip != null ) {
                publicIpAddresses = new RawAddress[]{new RawAddress(ip)};
            }
            else {
                return new String[0];
            }
        }
        String[] addrs = new String[publicIpAddresses.length];

        if( publicIpAddresses != null ) {
            for( int i = 0; i < addrs.length; i++ ) {
                addrs[i] = publicIpAddresses[i].getIpAddress();
            }
        }
        return addrs;
    }

    /**
     * Creates an informal association under a group name for the launched VM with other virtual machines in
     * the system. The underlying cloud may interpret this in any number of ways.
     *
     * @return the virtual machine group association
     */
    public @Nullable String getVirtualMachineGroup() {
        return virtualMachineGroup;
    }

    private String resolve( String dnsName ) {
        if( dnsName != null && dnsName.length() > 0 ) {
            InetAddress[] addresses;

            try {
                addresses = InetAddress.getAllByName(dnsName);
            } catch( UnknownHostException e ) {
                addresses = null;
            }
            if( addresses != null && addresses.length > 0 ) {
                dnsName = addresses[0].getHostAddress();
            }
            else {
                dnsName = dnsName.split("\\.")[0];
                dnsName = dnsName.replaceAll("-", "\\.");
                dnsName = dnsName.substring(4);
            }
        }
        return dnsName;
    }

    public void setPublicAddresses( @Nonnull RawAddress... addresses ) {
        publicIpAddresses = addresses;
    }

    /**
     * @param publicIpAddresses array of public IP addresses associated with this VM
     * @deprecated Use {@link #setPublicAddresses(RawAddress...)}
     */
    @Deprecated
    public void setPublicIpAddresses( String[] publicIpAddresses ) {
        this.publicIpAddresses = new RawAddress[publicIpAddresses == null ? 0 : publicIpAddresses.length];
        if( publicIpAddresses != null ) {
            for( int i = 0; i < this.publicIpAddresses.length; i++ ) {
                this.publicIpAddresses[i] = new RawAddress(publicIpAddresses[i]);
            }
        }
    }

    public boolean isRebootable() {
        return rebootable;
    }

    public void setRebootable( boolean rebootable ) {
        this.rebootable = rebootable;
    }

    public String getRootUser() {
        return rootUser;
    }

    public void setRootUser( String rootUser ) {
        this.rootUser = rootUser;
    }

    public String getStateReasonMessage() {
        return stateReasonMessage;
    }

    public void setStateReasonMessage( String stateReasonMessage ) {
        this.stateReasonMessage = stateReasonMessage;
    }

    public long getTerminationTimestamp() {
        return terminationTimestamp;
    }

    public void setTerminationTimestamp( long terminationTimestamp ) {
        this.terminationTimestamp = terminationTimestamp;
    }

    public Callable<String> getPasswordCallback() {
        return passwordCallback;
    }

    public void setProductId( String productId ) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setLabels( String[] labels ) {
        this.labels = labels;
    }

    public String[] getLabels() {
        return ( labels == null ? new String[0] : labels );
    }

    public Object getTag( String tag ) {
        return getTags().get(tag);
    }

    public synchronized @Nonnull Map<String, String> getTags() {
        if( tags == null ) {
            tags = new HashMap<String, String>();
        }
        return tags;
    }

    public void setTag( @Nonnull String key, @Nonnull String value ) {
        if( tags == null ) {
            tags = new HashMap<String, String>();
        }
        tags.put(key, value);
    }

    public synchronized void setTags( Map<String, String> properties ) {
        getTags().clear();
        getTags().putAll(properties);
    }

    public void setProviderSubnetId( String providerSubnetId ) {
        this.providerSubnetId = providerSubnetId;
    }

    public String getProviderSubnetId() {
        return providerSubnetId;
    }

    public void setProviderVlanId( String providerVlanId ) {
        this.providerVlanId = providerVlanId;
    }

    public String getProviderVlanId() {
        return providerVlanId;
    }

    public String getProviderKeypairId() {
        return providerKeypairId;
    }

    public void setProviderKeypairId( String providerKeypairId ) {
        this.providerKeypairId = providerKeypairId;
    }

    public String[] getProviderFirewallIds() {
        return ( providerFirewallIds == null ? new String[0] : providerFirewallIds );
    }

    public void setProviderFirewallIds( String[] providerFirewallIds ) {
        this.providerFirewallIds = providerFirewallIds;
    }

    public String[] getProviderNetworkInterfaceIds() {
        return ( providerNetworkInterfaceIds == null ? new String[0] : providerNetworkInterfaceIds );
    }

    public void setProviderNetworkInterfaceIds( String[] providerNetworkInterfaceIds ) {
        this.providerNetworkInterfaceIds = providerNetworkInterfaceIds;
    }

    public @Nullable String getProviderKernelImageId() {
        return providerKernelImageId;
    }

    public void setProviderKernelImageId( @Nullable String providerKernelImageId ) {
        this.providerKernelImageId = providerKernelImageId;
    }

    public @Nullable String getProviderRamdiskImageId() {
        return providerRamdiskImageId;
    }

    public void setProviderRamdiskImageId( @Nullable String providerRamdiskImageId ) {
        this.providerRamdiskImageId = providerRamdiskImageId;
    }

    public void setProviderShellKeyIds( @Nonnull String... keyIds ) {
        this.providerShellKeyIds = keyIds;
    }

    public @Nonnull String[] getProviderShellKeyIds() {
        return ( providerShellKeyIds == null ? new String[0] : providerShellKeyIds );
    }

    public @Nonnull String[] getProviderVolumeIds( @Nonnull CloudProvider provider ) throws CloudException, InternalException {
        if( providerVolumeIds == null ) {
            ComputeServices services = provider.getComputeServices();

            if( services == null ) {
                throw new OperationNotSupportedException("No compute services are defined");
            }
            VolumeSupport support = services.getVolumeSupport();

            if( support == null ) {
                providerVolumeIds = new String[0];
            }
            else {
                TreeSet<String> ids = new TreeSet<String>();

                for( Volume v : support.listVolumes(VolumeFilterOptions.getInstance().attachedTo(providerVirtualMachineId)) ) {
                    ids.add(v.getProviderVolumeId());
                }
                providerVolumeIds = ids.toArray(new String[ids.size()]);
            }
        }
        return providerVolumeIds;
    }

    public void setProviderVolumeIds( @Nonnull String... ids ) {
        providerVolumeIds = ids;
    }

    public @Nullable Volume[] getVolumes() {
        return volumes;
    }

    public void setVolumes( @Nullable Volume[] volumes ) {
        this.volumes = volumes;
    }

    public boolean isIoOptimized() {
        return ioOptimized;
    }

    public void setIoOptimized( boolean ioOptimized ) {
        this.ioOptimized = ioOptimized;
    }

    public boolean isIpForwardingAllowed() {
        return ipForwardingAllowed;
    }

    public void setIpForwardingAllowed( boolean ipForwardingAllowed ) {
        this.ipForwardingAllowed = ipForwardingAllowed;
    }

    public String getProviderRoleId() {
        return providerRoleId;
    }

    public void setProviderRoleId( String roleId ) {
        this.providerRoleId = roleId;
    }

    public VmStatus getProviderVmStatus() {
        return providerVmStatus;
    }

    public void setProviderVmStatus( VmStatus vmStatus ) {
        this.providerVmStatus = vmStatus;
    }

    public VmStatus getProviderHostStatus() {
        return providerHostStatus;
    }

    public void setProviderHostStatus( VmStatus vmStatus ) {
        this.providerHostStatus = vmStatus;
    }

    public void setVisibleScope( VisibleScope visibleScope ) {
        this.visibleScope = visibleScope;
    }

    public VisibleScope getVisibleScope() {
        return this.visibleScope;
    }

    public VirtualMachineLifecycle getLifecycle() {
        if( lifecycle == null ) {
            lifecycle = VirtualMachineLifecycle.NORMAL;
        }
        return lifecycle;
    }

    public void setLifecycle( VirtualMachineLifecycle lifecycle ) {
        this.lifecycle = lifecycle;
    }

    public String getSpotRequestId() {
        return spotRequestId;
    }

    public void setSpotRequestId( String spotRequestId ) {
        this.spotRequestId = spotRequestId;
    }

    public String getResourcePoolId() {
        return resourcePoolId;
    }

    public void setResourcePoolId(String resourcePoolId) {
        this.resourcePoolId = resourcePoolId;
    }

    public String getClientRequestToken() {return clientRequestToken;}

    public void setClientRequestToken(String clientRequestToken) {this.clientRequestToken = clientRequestToken;}
}
