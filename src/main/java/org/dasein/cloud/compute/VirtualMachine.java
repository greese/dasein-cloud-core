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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.dasein.cloud.Tag;
import org.dasein.cloud.Taggable;
import org.dasein.cloud.network.Networkable;
import org.dasein.cloud.network.RawAddress;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * <p>
 * A virtual machine running within a cloud. This class contains the current state at the time
 * of any cloud API call for the target VM.
 * </p>
 * @author George Reese @ enstratius (http://www.enstratius.com)
 * @version 2012-07 Altered product -> productId to minimize chattiness of any polling using Dasein Cloud
 * @version 2013.02 added Networkable interface (issue #24)
 * @version 2013.04 added access to shell key IDs
 */
public class VirtualMachine implements Networkable, Taggable {
    private Architecture          architecture;
    private boolean               clonable;
    private long                  creationTimestamp;
    private VmState               currentState;
    private Map<String,String>    tags;    
    private String                description;
    private boolean               imagable;
    private long                  lastBootTimestamp;
    private long                  lastPauseTimestamp;
    private String                name;
    private boolean               pausable;    
    private boolean               persistent;
    private Platform              platform;
    private String                privateDnsAddress;
    private RawAddress[]          privateIpAddresses;
    private String                productId;
    private String                providerAssignedIpAddressId;
    private String                providerDataCenterId;
    private String                providerKernelImageId;
    private String                providerMachineImageId;
    private String                providerOwnerId;
    private String                providerRamdiskImageId;
    private String                providerRegionId;
    private String[]              providerShellKeyIds;
    private String                providerSubnetId;
    private String                providerVirtualMachineId;
    private String                providerVlanId;
    private String                providerKeypairId;
    private String[]              providerFirewallIds;
    private String                publicDnsAddress;
    private RawAddress[]          publicIpAddresses;
    private boolean               rebootable;
    private String                rootPassword;
    private String                rootUser;
    private long                  terminationTimestamp;
    
    public VirtualMachine() { }
    
    public boolean equals(Object ob) {
        if( ob == null ) {
            return false;
        }
        if( ob == this ) {
            return true;
        }
        if( !getClass().getName().equals(ob.getClass().getName()) ) {
            return false;
        }
        VirtualMachine other = (VirtualMachine)ob;
        
        if( !getProviderRegionId().equals(other.getProviderRegionId()) ) {
            return false;
        }
        return getProviderVirtualMachineId().equals(other.getProviderVirtualMachineId());
    }
    
    public void addTag(Tag t) {
        addTag(t.getKey(), t.getValue());
    }
    
    public void addTag(String key, String value) {
        getTags().put(key, value);
    }
    
    private transient volatile Callable<String> passwordCallback = null;
    
    public void setPasswordCallback(Callable<String> callback) {
        this.passwordCallback = callback;
    }

    public void setRootPassword(String rootPassword) {
        this.rootPassword = rootPassword;
    }

    public String getRootPassword() {
        String pw;
        
        synchronized( this ) {
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

    public String getRootPassword(long timeoutInMilliseconds) throws InterruptedException {
        long timeout = System.currentTimeMillis() + timeoutInMilliseconds;
        String pw = getRootPassword();
        boolean hasCallback;

        synchronized( this ) {
            hasCallback = (passwordCallback != null);
        }
        if( hasCallback ) {
            while( pw == null ) {
                if( timeout <= System.currentTimeMillis() ) {
                    throw new InterruptedException("System timed out waiting for a password to become available.");
                }
                try { Thread.sleep(15000L); }
                catch( InterruptedException ignore ) { }
                pw = getRootPassword();
            }
        }
        return pw;
    }
    
    public String fetchPassword() {
        String pw;
        
        synchronized( this ) {
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
                synchronized( this ) {
                    rootPassword = pw;
                }
            }
            return rootPassword;
        }
        catch( Exception e ) {
            return null;
        }
    }
    
    
    public String toString() {
        return name + " [" + providerVirtualMachineId + "]";
    }

    public Architecture getArchitecture() {
        return architecture;
    }

    public void setArchitecture(Architecture architecture) {
        this.architecture = architecture;
    }

    public boolean isClonable() {
        return clonable;
    }

    public void setClonable(boolean clonable) {
        this.clonable = clonable;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public VmState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(VmState currentState) {
        this.currentState = currentState;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isImagable() {
        return imagable;
    }

    public void setImagable(boolean imagable) {
        this.imagable = imagable;
    }

    public long getLastBootTimestamp() {
        return lastBootTimestamp;
    }

    public void setLastBootTimestamp(long lastBootTimestamp) {
        this.lastBootTimestamp = lastBootTimestamp;
    }

    public long getLastPauseTimestamp() {
        return lastPauseTimestamp;
    }

    public void setLastPauseTimestamp(long lastPauseTimestamp) {
        this.lastPauseTimestamp = lastPauseTimestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPausable() {
        return pausable;
    }

    public void setPausable(boolean pausable) {
        this.pausable = pausable;
    }

    public boolean isPersistent() {
        return persistent;
    }

    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String getPrivateDnsAddress() {
        return privateDnsAddress;
    }

    public void setPrivateDnsAddress(String privateDnsAddress) {
        this.privateDnsAddress = privateDnsAddress;
    }

    public @Nonnull RawAddress[] getPrivateAddresses() {
        return (privateIpAddresses == null ? new RawAddress[0] : privateIpAddresses);
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

    public void setPrivateAddresses(@Nonnull RawAddress ... addresses) {
        privateIpAddresses = addresses;
    }

    /**
     * @deprecated Use {@link #setPrivateAddresses(RawAddress...)}
     */
    @Deprecated
    public void setPrivateIpAddresses(String[] privateIpAddresses) {
        this.privateIpAddresses = new RawAddress[privateIpAddresses == null ? 0 : privateIpAddresses.length];
        if( privateIpAddresses != null ) {
            for( int i=0; i<this.privateIpAddresses.length; i++ ) {
                this.privateIpAddresses[i] = new RawAddress(privateIpAddresses[i]);
            }
        }
    }

    public String getProviderAssignedIpAddressId() {
        return providerAssignedIpAddressId;
    }

    public void setProviderAssignedIpAddressId(String providerAssignedIpAddressId) {
        this.providerAssignedIpAddressId = providerAssignedIpAddressId;
    }

    public String getProviderDataCenterId() {
        return providerDataCenterId;
    }

    public void setProviderDataCenterId(String providerDataCenterId) {
        this.providerDataCenterId = providerDataCenterId;
    }

    public String getProviderMachineImageId() {
        return providerMachineImageId;
    }

    public void setProviderMachineImageId(String providerMachineImageId) {
        this.providerMachineImageId = providerMachineImageId;
    }

    public String getProviderOwnerId() {
        return providerOwnerId;
    }

    public void setProviderOwnerId(String providerOwnerId) {
        this.providerOwnerId = providerOwnerId;
    }

    public String getProviderRegionId() {
        return providerRegionId;
    }

    public void setProviderRegionId(String providerRegionId) {
        this.providerRegionId = providerRegionId;
    }

    public String getProviderVirtualMachineId() {
        return providerVirtualMachineId;
    }

    public void setProviderVirtualMachineId(String providerVirtualMachineId) {
        this.providerVirtualMachineId = providerVirtualMachineId;
    }

    public String getPublicDnsAddress() {
        return publicDnsAddress;
    }

    public void setPublicDnsAddress(String publicDnsAddress) {
        this.publicDnsAddress = publicDnsAddress;
    }

    public @Nonnull RawAddress[] getPublicAddresses() {
        return (publicIpAddresses == null ? new RawAddress[0] : publicIpAddresses);
    }

    /**
     * @deprecated Use {@link #getPublicAddresses()}
     */
    @Deprecated
    public String[] getPublicIpAddresses() {
        String[] addrs = new String[publicIpAddresses == null ? 0 : publicIpAddresses.length];

        if( publicIpAddresses != null ) {
            for( int i=0; i<addrs.length; i++ ) {
                addrs[i] = publicIpAddresses[i].getIpAddress();
            }
        }
        return addrs;
    }

    public void setPublicAddresses(@Nonnull RawAddress ... addresses) {
        publicIpAddresses = addresses;
    }

    /**
     * @deprecated Use {@link #setPublicAddresses(RawAddress...)}
     */
    @Deprecated
    public void setPublicIpAddresses(String[] publicIpAddresses) {
        this.publicIpAddresses = new RawAddress[publicIpAddresses == null ? 0 : publicIpAddresses.length];
        if( publicIpAddresses != null ) {
            for( int i=0; i<this.publicIpAddresses.length; i++ ) {
                this.publicIpAddresses[i] = new RawAddress(publicIpAddresses[i]);
            }
        }
    }

    public boolean isRebootable() {
        return rebootable;
    }

    public void setRebootable(boolean rebootable) {
        this.rebootable = rebootable;
    }

    public String getRootUser() {
        return rootUser;
    }

    public void setRootUser(String rootUser) {
        this.rootUser = rootUser;
    }

    public long getTerminationTimestamp() {
        return terminationTimestamp;
    }

    public void setTerminationTimestamp(long terminationTimestamp) {
        this.terminationTimestamp = terminationTimestamp;
    }

    public Callable<String> getPasswordCallback() {
        return passwordCallback;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public Object getTag(String tag) {
        return getTags().get(tag);
    }
    
    public synchronized Map<String,String> getTags() {
        if( tags == null ) {
            tags = new HashMap<String,String>();
        }
        return tags;
    }

    public void setTag(@Nonnull String key, @Nonnull String value) {
        if( tags == null ) {
            tags = new HashMap<String, String>();
        }
        tags.put(key, value);
    }

    public synchronized void setTags(Map<String,String> properties) {
        getTags().clear();
        getTags().putAll(properties);
    }

    public void setProviderSubnetId(String providerSubnetId) {
        this.providerSubnetId = providerSubnetId;
    }

    public String getProviderSubnetId() {
        return providerSubnetId;
    }

    public void setProviderVlanId(String providerVlanId) {
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
      return (providerFirewallIds == null ? new String[0] : providerFirewallIds);
    }

    public void setProviderFirewallIds( String[] providerFirewallIds ) {
      this.providerFirewallIds = providerFirewallIds;
    }

    public @Nullable String getProviderKernelImageId() {
        return providerKernelImageId;
    }

    public void setProviderKernelImageId(@Nullable String providerKernelImageId) {
        this.providerKernelImageId = providerKernelImageId;
    }

    public @Nullable String getProviderRamdiskImageId() {
        return providerRamdiskImageId;
    }

    public void setProviderRamdiskImageId(@Nullable String providerRamdiskImageId) {
        this.providerRamdiskImageId = providerRamdiskImageId;
    }

    public void setProviderShellKeyIds(@Nonnull String ... keyIds) {
        this.providerShellKeyIds = keyIds;
    }

    public @Nonnull String[] getProviderShellKeyIds() {
        return (providerShellKeyIds == null ? new String[0] : providerShellKeyIds);
    }
}
