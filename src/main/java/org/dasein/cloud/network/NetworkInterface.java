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

package org.dasein.cloud.network;

import org.dasein.cloud.Taggable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a network interfaces that controls a device's access to a network.
 * @author George Reese
 * @version 2013.02 Added support for multiple IP addresses (issue #38)
 * @since unknown
 */
public class NetworkInterface implements Taggable {
    private NICState currentState;
    private String    description;
    private String    dnsName;
    private RawAddress[] ipAddresses;
    private String    macAddress;
    private String    name;
    private String    providerDataCenterId;
    private String    providerNetworkInterfaceId;
    private String    providerOwnerId;
    private String    providerRegionId;
    private String    providerSubnetId;
    private String    providerVirtualMachineId;
    private String    providerVlanId;
    private Map<String,String> tags;
    
    public NetworkInterface() { }

    public boolean equals(Object other) {
        if( other == null ) {
            return false;
        }
        if( other == this ) {
            return true;
        }
        if( !getClass().getName().equals(other.getClass().getName()) ) {
            return false;
        }
        NetworkInterface nic = (NetworkInterface)other;
        
        if( providerNetworkInterfaceId.equals(nic.providerNetworkInterfaceId) ) {
            if( providerVlanId.equals(nic.providerVlanId) ) {
                if( providerRegionId.equals(nic.providerRegionId) ) {
                    return providerOwnerId.equals(nic.providerOwnerId);
                }
            }
        }
        return false;   
    }

    public String getDnsName() {
        return dnsName;
    }
    
    public void setDnsName(String dnsName) {
        this.dnsName = dnsName;
    }

    /**
     * @return the first IP address as a string
     * @deprecated Use {@link #getIpAddresses()}
     */
    @Deprecated
    public @Nullable String getIpAddress() {
        return (ipAddresses == null || ipAddresses.length < 1 ? null : ipAddresses[0].getIpAddress());
    }

    public @Nonnull RawAddress[] getIpAddresses() {
        return (ipAddresses == null ? new RawAddress[0] : ipAddresses);
    }

    /**
     * Sets the IP addresses to a single IP address
     * @param ipAddress the one IP address for this NIC
     * @deprecated Use {@link #setIpAddresses(RawAddress...)}
     */
    @Deprecated
    public void setIpAddress(@Nonnull String ipAddress) {
        ipAddresses = new RawAddress[] { new RawAddress(ipAddress) };
    }

    public void setIpAddresses(@Nonnull RawAddress ... addresses) {
        this.ipAddresses = addresses;
    }

    public String getMacAddress() {
        return macAddress;
    }
    
    public void setMacAddress(String ma) {
        macAddress = ma;
    }
    
    public String getProviderNetworkInterfaceId() {
        return providerNetworkInterfaceId;
    }

    public void setProviderNetworkInterfaceId(String providerNetworkInterfaceId) {
        this.providerNetworkInterfaceId = providerNetworkInterfaceId;
    }

    public String getProviderVirtualMachineId() {
        return providerVirtualMachineId;
    }

    public void setProviderVirtualMachineId(String providerVirtualMachineId) {
        this.providerVirtualMachineId = providerVirtualMachineId;
    }

    public String getProviderVlanId() {
        return providerVlanId;
    }

    public void setProviderVlanId(String providerVlanId) {
        this.providerVlanId = providerVlanId;
    }
    
    public String getProviderDataCenterId() {
        return providerDataCenterId;
    }

    public void setProviderDataCenterId(String providerDataCenterId) {
        this.providerDataCenterId = providerDataCenterId;
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

    public String getProviderSubnetId() {
        return providerSubnetId;
    }

    public void setProviderSubnetId(String providerSubnetId) {
        this.providerSubnetId = providerSubnetId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NICState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(NICState currentState) {
        this.currentState = currentState;
    }

    public @Nonnull Map<String,String> getTags() {
        return (tags == null ? new HashMap<String, String>() : tags);
    }
    
    public void setTag(@Nonnull String key, @Nonnull String value) {
        if( tags == null ) {
            tags = new HashMap<String, String>();
        }
        tags.put(key, value);
    }
    
    public void setTags(Map<String,String> tags) {
        this.tags = tags;
    }

    public String toString() {
        //noinspection deprecation
        return (getIpAddress() + " [" + providerNetworkInterfaceId + "]");
    }
}
