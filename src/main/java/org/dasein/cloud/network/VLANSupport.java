/**
 * Copyright (C) 2009-2012 enStratus Networks Inc.
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

import java.util.Collection;
import java.util.Locale;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.dasein.cloud.AccessControlledService;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.identity.ServiceAction;

public interface VLANSupport extends AccessControlledService {
    static public final ServiceAction ANY               = new ServiceAction("NET:ANY");

    static public final ServiceAction ATTACH_NIC        = new ServiceAction("NET:ATTACH_NIC");
    static public final ServiceAction CREATE_NIC        = new ServiceAction("NET:CREATE_NIC");
    static public final ServiceAction DETACH_NIC        = new ServiceAction("NET:DETACH_NIC");
    static public final ServiceAction GET_NIC           = new ServiceAction("NET:GET_NIC");
    static public final ServiceAction LIST_NIC          = new ServiceAction("NET:LIST_NIC");
    static public final ServiceAction REMOVE_NIC        = new ServiceAction("NET:REMOVE_NIC");
    
    static public final ServiceAction CREATE_SUBNET     = new ServiceAction("NET:CREATE_SUBNET");
    static public final ServiceAction CREATE_VLAN       = new ServiceAction("NET:CREATE_VLAN");
    static public final ServiceAction GET_SUBNET        = new ServiceAction("NET:GET_SUBNET");
    static public final ServiceAction GET_VLAN          = new ServiceAction("NET:GET_VLAN");
    static public final ServiceAction LIST_SUBNET       = new ServiceAction("NET:LIST_SUBNET");
    static public final ServiceAction LIST_VLAN         = new ServiceAction("NET:LIST_VLAN");
    static public final ServiceAction REMOVE_SUBNET     = new ServiceAction("NET:REMOVE_SUBNET");
    static public final ServiceAction REMOVE_VLAN       = new ServiceAction("NET:REMOVE_VLAN");

    /**
     * Indicates that users may self-provision network interfaces. If false, either network interfaces are not supported
     * or they cannot be self-provisioned
     * @return true if users can self-provision network interfaces
     * @throws CloudException an error occurred checking with the cloud if network interfaces may be self provisioned
     * @throws InternalException an error occurred in the Dasein Cloud implementation determining support
     */
    public abstract boolean allowsNewNetworkInterfaceCreation() throws CloudException, InternalException;

    public abstract boolean allowsNewVlanCreation() throws CloudException, InternalException;
    
    public abstract boolean allowsNewSubnetCreation() throws CloudException, InternalException;

    /**
     * Attaches a network interface to an existing virtual machine.
     * @param nicId the unique ID of the network interface to attach
     * @param vmId the virtual machine to which the network interface should be attached
     * @param index the 1-based index (-1 meaning at the end) for the attached interface 
     * @throws CloudException an error occurred with the cloud provider attaching the interface                                                           
     * @throws InternalException an error occurred within the Dasein Cloud implementation attaching the interface
     */
    public abstract void attachNetworkInterface(@Nonnull String nicId, @Nonnull String vmId, int index) throws CloudException, InternalException;

    /**
     * Provisions a new network interface in accordance with the specified create options. 
     * @param options the options to be used in creating the network interface
     * @return the newly provisioned network interface
     * @throws CloudException an error occurred in the cloud while provisioning the interface
     * @throws InternalException a local error occurred during the provisoning of the interface
     * @throws OperationNotSupportedException if {@link #allowsNewNetworkInterfaceCreation()} is false
     */
    public abstract @Nonnull NetworkInterface createNetworkInterface(@Nonnull NICCreateOptions options) throws CloudException, InternalException;

    public abstract @Nonnull Subnet createSubnet(@Nonnull String cidr, @Nonnull String inProviderVlanId, @Nonnull String name, @Nonnull String description) throws CloudException, InternalException;
    
    public abstract @Nonnull VLAN createVlan(@Nonnull String cidr, @Nonnull String name, @Nonnull String description, @Nonnull String domainName, @Nonnull String[] dnsServers, @Nonnull String[] ntpServers) throws CloudException, InternalException;

    /**
     * Detaches the specified network interface from any virtual machine it might be attached to.
     * @param nicId the unique ID of the network interface to be detached
     * @throws CloudException an error occurred with the cloud provider while detaching the network interface
     * @throws InternalException a local error occurred while detaching the network interface
     */
    public abstract void detachNetworkInterface(@Nonnull String nicId) throws CloudException, InternalException;

    /**
     * Specifies the maximum number of network interfaces that may be provisioned.
     * @return the maximum number of network interfaces that may be provisioned or -1 for no limit or -2 for unknown
     * @throws CloudException an error occurred requesting the limit from the cloud provider
     * @throws InternalException a local error occurred figuring out the limit
     */
    public abstract int getMaxNetworkInterfaceCount() throws CloudException, InternalException;
    
    public abstract int getMaxVlanCount() throws CloudException, InternalException;

    /**
     * Identifies the provider term for a network interface.
     * @param locale the locale in which the term should be provided
     * @return a localized term for "network interface" specific to this cloud provider
     */
    public abstract @Nonnull String getProviderTermForNetworkInterface(@Nonnull Locale locale);
    
    public abstract @Nonnull String getProviderTermForSubnet(@Nonnull Locale locale);
    
    public abstract @Nonnull String getProviderTermForVlan(@Nonnull Locale locale);

    /**
     * Fetches the network interfaced specified by the unique network interface ID.
     * @param nicId the unique ID of the desired network interface
     * @return the network interface that matches the specified ID
     * @throws CloudException an error occurred in the cloud provider fetching the desired network interface
     * @throws InternalException a local error occurred while fetching the desired network interface
     */
    public abstract @Nullable NetworkInterface getNetworkInterface(@Nonnull String nicId) throws CloudException, InternalException;
    
    public abstract @Nullable Subnet getSubnet(@Nonnull String subnetId) throws CloudException, InternalException;
    
    public abstract @Nullable VLAN getVlan(@Nonnull String vlanId) throws CloudException, InternalException;

    /**
     * Indicates whether or not this cloud included the concept of network interfaces in its networking support.
     * @return true if this cloud supports network interfaces as part of its networking concepts
     * @throws CloudException an error occurred with the cloud provider determining support for network interfaces
     * @throws InternalException a local error occurred determining support for network interfaces
     */
    public abstract boolean isNetworkInterfaceSupportEnabled() throws CloudException, InternalException;

    public abstract boolean isSubscribed() throws CloudException, InternalException;
    
    public abstract boolean isSubnetDataCenterConstrained() throws CloudException, InternalException;

    public abstract boolean isVlanDataCenterConstrained() throws CloudException, InternalException;

    /**
     * Lists the IDs of the firewalls protecting the specified network interface.
     * @param nicId the network interface ID of the desired network interface
     * @return the firewall/security group IDs of all firewalls supporting this network interface
     * @throws CloudException an error occurred with the cloud providing fetching the firewall IDs
     * @throws InternalException a local error occurred while attempting to communicate with the cloud
     */
    public abstract Collection<String> listFirewallIdsForNIC(@Nonnull String nicId) throws CloudException, InternalException;

    /**
     * Lists all network interfaces currently provisioned in the current region.
     * @return a list of all provisioned network interfaces in the current region
     * @throws CloudException an error occurred with the cloud provider fetching the network interfaces
     * @throws InternalException a local error occurred fetching the network interfaces
     */
    public abstract @Nonnull Iterable<NetworkInterface> listNetworkInterfaces() throws CloudException, InternalException;

    /**
     * Lists all network interfaces attached to a specific virtual machine.
     * @param forVmId the virtual machine whose network interfaces you want listed
     * @return the network interfaces attached to the specified virtual machine
     * @throws CloudException an error occurred with the cloud provider determining the attached network interfaces
     * @throws InternalException a local error occurred listing the network interfaces attached to the specified virtual machine
     */
    public abstract @Nonnull Iterable<NetworkInterface> listNetworkInterfacesForVM(@Nonnull String forVmId) throws CloudException, InternalException;

    /**
     * Lists all network interfaces connected to a specific subnet. Valid only if the cloud provider supports subnets.
     * @param subnetId the subnet ID for the subnet in which you are searching
     * @return all interfaces within the specified subnet
     * @throws CloudException an error occurred in the cloud identifying the matching network interfaces
     * @throws InternalException a local error occurred constructing the cloud query
     */
    public abstract @Nonnull Iterable<NetworkInterface> listNetworkInterfacesInSubnet(@Nonnull String subnetId) throws CloudException, InternalException;

    /**
     * Lists all network interfaces connected to a specific VLAN. Valid only if the cloud provider supports VLANs.
     * @param vlanId the VLAN ID for the VLAN in which you are searching
     * @return all interfaces within the specified VLAN
     * @throws CloudException an error occurred in the cloud identifying the matching network interfaces
     * @throws InternalException a local error occurred constructing the cloud query
     */
    public abstract @Nonnull Iterable<NetworkInterface> listNetworkInterfacesInVLAN(@Nonnull String vlanId) throws CloudException, InternalException;

    public abstract @Nonnull Iterable<Subnet> listSubnets(@Nonnull String inVlanId) throws CloudException, InternalException;
    
    public abstract @Nonnull Iterable<VLAN> listVlans() throws CloudException, InternalException;

    /**
     * De-provisions the specified network interface.
     * @param nicId the network interface to be de-provisioned
     * @throws CloudException an error occurred with the cloud provider while de-provisioning the network interface
     * @throws InternalException a local error occurred while de-provisioning the network interface
     */
    public abstract void removeNetworkInterface(@Nonnull String nicId) throws CloudException, InternalException;
    
    public abstract void removeSubnet(String providerSubnetId) throws CloudException, InternalException;
    
    public abstract void removeVlan(String vlanId) throws CloudException, InternalException; 
    
    public abstract boolean supportsVlansWithSubnets() throws CloudException, InternalException;
}
