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

import java.util.HashMap;
import java.util.Map;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.dasein.cloud.Taggable;

/**
 * Represents an IP address that in some way belongs to a cloud account holder.
 * @author George Reese @ enstratius (http://www.enstratius.com)
 * @version 2013.02 added method for fetching raw address (issue #38)
 * @version 2013.02 added providerVlanId field (issue #23)
 * @version 2013.02 added Networkable interface
 * @version 2013.02 added reserved attribute (issue #28)
 * @since unknown
 */
public class IpAddress implements Networkable, Taggable, Comparable<IpAddress> {
    private String      address;
    private AddressType addressType;
    private boolean     forVlan;
    private String      providerNetworkInterfaceId;
    private String      providerIpAddressId;
    private String      providerLoadBalancerId;
    private String      providerVlanId;
    private String      providerAssociationId;
    private String      regionId;
    private boolean     reserved;
    private String      serverId;
    private IPVersion   version;
    private Map<String,String> tags;
    
    public IpAddress() { }
    
    /**
     * Sorts addresses based on their IP address.
     */
    public int compareTo(@Nonnull IpAddress other) {
        int x;
        
        if( other == this ) {
            return 0;
        }
        x = address.compareTo(other.address);
        if( x != 0 ) {
            return x;
        }
        return providerIpAddressId.compareTo(other.providerIpAddressId);
    }
    
    /**
     * Two addresses are equal if they have the same IP address and provider address IDs
     */
    public boolean equals(@CheckForNull Object ob) {
        IpAddress other;
        
        if( ob == null ) {
            return false;
        }
        if( ob == this ) {
            return true;
        }
        if( !getClass().getName().equals(ob.getClass().getName()) ) {
            return false;
        }
        other = (IpAddress)ob;
        return (address.equals(other.address) && providerIpAddressId.equals(other.providerIpAddressId));
    }
    
    /**
     * @return the IP address for this {@link IpAddress}
     * @deprecated Use {@link #getRawAddress()}
     */
    @Deprecated
    public @Nonnull String getAddress() {
        return address;
    }

    /**
     * @return the raw IP address for this static IP
     */
    public @Nonnull RawAddress getRawAddress() {
        return new RawAddress(address, version);
    }
    /**
     * @return the cloud provider's ID for uniquely identifying this address
     */
    public @Nonnull String getProviderIpAddressId() {
        return providerIpAddressId;
    }
    
    /**
     * @return the provider ID for the region in which this address may be assigned
     */
    public @Nonnull String getRegionId() {
        return regionId;
    }
    
    /**
     * @return the provider ID for the server to which this address is assigned (if any)
     */
    public @Nullable String getServerId() {
        return serverId;
    }
    
    /**
     * @return the provider ID for the load balancer to which this address is assigned (if any)
     */
    public @Nullable String getProviderLoadBalancerId() {
        return providerLoadBalancerId;
    }
    
    /**
     * @return the type of address (public or private) this address is
     */
    public @Nonnull AddressType getAddressType() {
        return addressType;
    }
    
    /**
     * @return <code>true</code> if this address is assigned to a server or load balancer
     */
    public boolean isAssigned() {
        return (serverId != null || providerLoadBalancerId != null);
    }

    public void setAddress(@Nonnull String address) {
        this.address = address;
    }

    public void setIpAddressId(@Nonnull String ipAddressId) {
        this.providerIpAddressId = ipAddressId;
    }
    
    public void setRegionId(@Nonnull String regionId) {
        this.regionId = regionId;
    }
    
    public void setServerId(@Nullable String serverId) {
        this.serverId = serverId;
    }
    
    public @Nonnull String toString() {
        return (address + " (" + providerIpAddressId + ")"); 
    }

    public void setProviderLoadBalancerId(@Nullable String providerLoadBalancerId) {
        this.providerLoadBalancerId = providerLoadBalancerId;
    }

    public void setAddressType(@Nonnull AddressType addressType) {
        this.addressType = addressType;
    }
    
    public void setForVlan(boolean f) {
        forVlan = f;
    }

    public boolean isForVlan() {
        return forVlan;
    }

    public String getProviderNetworkInterfaceId() {
        return providerNetworkInterfaceId;
    }

    public void setProviderNetworkInterfaceId(String providerNetworkInterfaceId) {
        this.providerNetworkInterfaceId = providerNetworkInterfaceId;
    }

    public @Nonnull IPVersion getVersion() {
        return (version == null ? IPVersion.IPV4 : version);
    }

    public void setVersion(IPVersion version) {
        this.version = version;
    }

    public String getProviderVlanId() {
        return providerVlanId;
    }

    public void setProviderVlanId(String providerVlanId) {
        this.providerVlanId = providerVlanId;
    }

    public String getProviderAssociationId() {
      return providerAssociationId;
    }

    public void setProviderAssociationId(String providerAssociationId) {
      this.providerAssociationId = providerAssociationId;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    /**
     * Fetches a single tag value.
     * @param key the key of the desired tag
     * @return the value for the desired tag if it exists
     */
    public @Nullable String getTag(@Nonnull String key) {
        return getTags().get(key);
    }

    @Override
    public @Nonnull Map<String,String> getTags() {
        if( tags == null ) {
            tags = new HashMap<String, String>();
        }
        return tags;
    }

    @Override
    public void setTag(@Nonnull String key, @Nonnull String value) {
        if( tags == null ) {
            tags = new HashMap<String,String>();
        }
        tags.put(key, value);
    }

    /**
     * Sets the tag set to the specified values.
     * @param tags the new tag set
     */
    public void setTags(@Nonnull Map<String,String> tags) {
        this.tags = tags;
    }
}
