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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Identifies a subnet of a VLAN in clouds that allow VLAN subnetting.
 * @version 2013.04 Simplified the construction of subnets and annoted methods
 * @version 2013.02 added Networkable interface
 * @since unknown
 */
public class Subnet implements Networkable, Taggable {
    static public @Nonnull Subnet getInstance(@Nonnull String ownerId, @Nonnull String regionId, @Nonnull String vlanId, @Nonnull String subnetId, @Nonnull SubnetState currentState, @Nonnull String name, @Nonnull String description, @Nonnull String cidr) {
        Subnet subnet = new Subnet();

        subnet.providerOwnerId = ownerId;
        subnet.providerRegionId = regionId;
        subnet.providerVlanId = vlanId;
        subnet.providerSubnetId = subnetId;
        subnet.currentState = currentState;
        subnet.name = name;
        subnet.description = description;
        subnet.cidr = cidr;
        subnet.supportedTraffic = new IPVersion[] { IPVersion.IPV4 };
        return subnet;
    }

    private AllocationPool[]   allocationPools;
    private int                availableIpAddresses;
    private String             cidr;
    private SubnetState        currentState;
    private String             description;
    private RawAddress         gateway;
    private String             name;
    private String             providerDataCenterId;
    private String             providerOwnerId;
    private String             providerRegionId;
    private String             providerSubnetId;
    private String             providerVlanId;
    private IPVersion[]        supportedTraffic;
    private Map<String,String> tags;
    
    public Subnet() { }

    /**
     * In clouds where the subnet is constrained to a data center, this enables you to specify the data center to
     * which this subnet is constrained
     * @param dataCenterId the data center to which this subnet is constrained
     * @return this
     */
    public @Nonnull Subnet constrainedToDataCenter(@Nonnull String dataCenterId) {
        this.providerDataCenterId = dataCenterId;
        return this;
    }

    @Override
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
        Subnet other = (Subnet)ob;
        if( !providerSubnetId.equals(other.providerSubnetId) ) {
            return false;
        }
        if( !providerVlanId.equals(other.providerVlanId) ) {
            return false;
        }
        //noinspection SimplifiableIfStatement
        if( !providerRegionId.equals(other.providerRegionId) ) {
            return false;
        }
        return providerOwnerId.equals(other.providerOwnerId);
    }

    /**
     * @return a list of IP address ranges from which IP addresses may be allocated
     */
    public @Nonnull AllocationPool[] getAllocationPools() {
        if( allocationPools == null ) {
            allocationPools = new AllocationPool[0];
        }
        return allocationPools;
    }

    /**
     * @return the count of available IP addresses
     */
    public @Nonnegative int getAvailableIpAddresses() {
        return availableIpAddresses;
    }

    /**
     * @return the CIDR block associated with this subnet
     */
    public String getCidr() {
        return cidr;
    }

    /**
     * @return the current state for the subnet
     */
    public SubnetState getCurrentState() {
        return currentState;
    }

    /**
     * @return a user-friendly description for the subnet
     */
    public @Nonnull String getDescription() {
        return description;
    }

    /**
     * @return the gateway IP address to be used in routing out of the subnet
     */
    public @Nullable RawAddress getGateway() {
        return gateway;
    }

    /**
     * @return the name of the subnet
     */
    public @Nonnull String getName() {
        return name;
    }

    /**
     * @return the data center, if any, to which this subnet is constrained
     */
    public @Nullable String getProviderDataCenterId() {
        return providerDataCenterId;
    }

    /**
     * @return the account that owns this subnet or an empty string if it is a publicly shared subnet
     */
    public @Nonnull String getProviderOwnerId() {
        return providerOwnerId;
    }

    /**
     * @return the region to which this subnet is constrained
     */
    public @Nonnull String getProviderRegionId() {
        return providerRegionId;
    }

    /**
     * @return the unique ID for this subnet
     */
    public @Nonnull String getProviderSubnetId() {
        return providerSubnetId;
    }

    /**
     * @return the ID for the VLAN of which this subnet is part
     */
    public @Nonnull String getProviderVlanId() {
        return providerVlanId;
    }

    /**
     * @return a list of IP versions supported on this subnet
     */
    public @Nonnull IPVersion[] getSupportedTraffic() {
        return ((supportedTraffic == null || supportedTraffic.length < 1) ? new IPVersion[] { IPVersion.IPV4 } : supportedTraffic);
    }

    /**
     * Specifies the allocation pools associated with the subnet.
     * @param pools one or more IP address ranges from which IPs may be allocated
     * @return this
     */
    public @Nonnull Subnet havingAllocationPools(@Nonnull AllocationPool ... pools) {
        this.allocationPools = pools;
        return this;
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
     * Sets the meta-data tags and overwrites any existing values.
     * @param tags the tags to be set
     */
    public void setTags(@Nonnull Map<String,String> tags) {
        this.tags = tags;
    }

    /**
     * Indicates that this subnet will support the specified kind of traffic.
     * @param traffic the traffic supported in this subnet
     * @return this
     */
    public Subnet supportingTraffic(@Nonnull IPVersion ... traffic) {
        supportedTraffic = traffic;
        return this;
    }

    @Override
    public String toString() {
        return (cidr + " [" + providerOwnerId + "/" + providerSubnetId + "]");
    }

    /**
     * Specifies the IP address of the gateway for this subnet.
     * @param gatewayIp the gateway IP address for the subnet
     * @return this
     */
    public @Nonnull Subnet usingGateway(@Nonnull RawAddress gatewayIp) {
        this.gateway = gatewayIp;
        return this;
    }

    /**
     * Specifies the number of IP addresses currently available in this subnet.
     * @param count the number of IP addresses available in this subnet
     * @return this
     */
    public @Nonnull Subnet withAvailableIpAddresses(int count) {
        this.availableIpAddresses = count;
        return this;
    }

    /******************************* DEPRECATED METHODS ***********************************/

    /**
     * Sets the count of available IP addresses.
     * @param availableIpAddresses the number of available IP addresses
     * @deprecated Use the static factory methods
     */
    public void setAvailableIpAddresses(@Nonnegative int availableIpAddresses) {
        this.availableIpAddresses = availableIpAddresses;
    }

    /**
     * Sets the CIDR associated with this subnet.
     * @param cidr the CIDR block for the subnet
     * @deprecated  Use the static factory methods
     */
    public void setCidr(@Nonnull String cidr) {
        this.cidr = cidr;
    }

    /**
     * Sets the current state of the subnet.
     * @param currentState the subnet's current state
     * @deprecated Use the static factory methods
     */
    public void setCurrentState(@Nonnull SubnetState currentState) {
        this.currentState = currentState;
    }

    /**
     * Sets a description for the subnet.
     * @param description the subnet's description
     * @deprecated Use the static factory methods
     */
    public void setDescription(@Nonnull String description) {
        this.description = description;
    }

    /**
     * Sets the name of the subnet.
     * @param name the name of the subnet
     * @deprecated Use the static factory methods
     */
    public void setName(@Nonnull String name) {
        this.name = name;
    }

    /**
     * Sets the data center to which this subnet is constrained
     * @param providerDataCenterId the data center to which the subnet is constrained
     * @deprecated Use the static factory methods
     */
    public void setProviderDataCenterId(@Nullable String providerDataCenterId) {
        this.providerDataCenterId = providerDataCenterId;
    }

    /**
     * Sets the account number that owns this subnet or an empty string for a shared subnet.
     * @param providerOwnerId the account number owning this subnet
     * @deprecated Use the static factory methods
     */
    public void setProviderOwnerId(String providerOwnerId) {
        this.providerOwnerId = providerOwnerId;
    }

    /**
     * Sets the region to which this subnet is constrained.
     * @param providerRegionId the region to which the subnet is constrained
     * @deprecated  Use the static factory methods
     */
    public void setProviderRegionId(String providerRegionId) {
        this.providerRegionId = providerRegionId;
    }

    /**
     * Sets the unique ID for identifying this subnet with the cloud provider.
     * @param providerSubnetId the unique ID of the subnet
     * @deprecated Use the static factory methods
     */
    public void setProviderSubnetId(@Nonnull String providerSubnetId) {
        this.providerSubnetId = providerSubnetId;
    }

    /**
     * Sets the ID of the VLAN in which this subnet sits.
     * @param providerVlanId the ID of the VLAN for the subnet
     * @deprecated Use the static factory methods
     */
    public void setProviderVlanId(@Nonnull String providerVlanId) {
        this.providerVlanId = providerVlanId;
    }
}
