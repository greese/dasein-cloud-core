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

import org.dasein.cloud.*;
import org.dasein.cloud.identity.ServiceAction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Locale;

/**
 * @version 2013.02 added listResources(String) (issue #24)
 * @version 2013.04 added support for specifying data centers when provisioning subnets
 */
public interface VLANSupport extends AccessControlledService {
    static public final ServiceAction ANY               = new ServiceAction("NET:ANY");

    static public final ServiceAction ADD_ROUTE               = new ServiceAction("NET:ADD_ROUTE");
    static public final ServiceAction ASSIGN_ROUTE_TO_SUBNET  = new ServiceAction("NET:ASSIGN_ROUTE_TO_SUBNET");
    static public final ServiceAction ASSIGN_ROUTE_TO_VLAN    = new ServiceAction("NET:ASSIGN_ROUTE_TO_VLAN");
    static public final ServiceAction ATTACH_INTERNET_GATEWAY = new ServiceAction("NET:ATTACH_INTERNET_GATEWAY");
    static public final ServiceAction CREATE_ROUTING_TABLE    = new ServiceAction("NET:CREATE_ROUTING_TABLE");
    static public final ServiceAction CREATE_INTERNET_GATEWAY = new ServiceAction("NET:CREATE_INTERNET_GATEWAY");
    static public final ServiceAction GET_ROUTING_TABLE       = new ServiceAction("NET:GET_ROUTING_TABLE");
    static public final ServiceAction LIST_ROUTING_TABLE      = new ServiceAction("NET:LIST_ROUTING_TABLE");
    static public final ServiceAction REMOVE_INTERNET_GATEWAY = new ServiceAction("NET:REMOVE_INTERNET_GATEWAY");
    static public final ServiceAction REMOVE_ROUTE            = new ServiceAction("NET:REMOVE_ROUTE");
    static public final ServiceAction REMOVE_ROUTING_TABLE    = new ServiceAction("NET:REMOVE_ROUTING_TABLE");

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
     * Adds the specified route to the specified routing table.
     * @param routingTableId the routing table to which the route will be added
     * @param version ipv4 or ipv6
     * @param destinationCidr the destination IP address or CIDR, or null if setting the default route
     * @param address the IP address to which the traffic is being routed
     * @return route object
     * @throws CloudException an error occurred in the cloud while adding the route to the routing table
     * @throws InternalException a local error occurred processing the request to add the route
     */
    public Route addRouteToAddress(@Nonnull String routingTableId, @Nonnull IPVersion version, @Nullable String destinationCidr, @Nonnull String address) throws CloudException, InternalException;

    /**
     * Adds the specified route to the specified routing table.
     * @param routingTableId the routing table to which the route will be added
     * @param version ipv4 or ipv6
     * @param destinationCidr the destination IP address or CIDR, or null if setting the default route
     * @param gatewayId the ID of a known gateway
     * @return route object
     * @throws CloudException an error occurred in the cloud while adding the route to the routing table
     * @throws InternalException a local error occurred processing the request to add the route
     */
    public Route addRouteToGateway(@Nonnull String routingTableId, @Nonnull IPVersion version, @Nullable String destinationCidr, @Nonnull String gatewayId) throws CloudException, InternalException;

    /**
     * Adds the specified route to the specified routing table.
     * @param routingTableId the routing table to which the route will be added
     * @param version ipv4 or ipv6
     * @param destinationCidr the destination IP address or CIDR, or null if setting the default route
     * @param nicId the ID of a known network interface
     * @return route object
     * @throws CloudException an error occurred in the cloud while adding the route to the routing table
     * @throws InternalException a local error occurred processing the request to add the route
     */
    public Route addRouteToNetworkInterface(@Nonnull String routingTableId, @Nonnull IPVersion version, @Nullable String destinationCidr, @Nonnull String nicId) throws CloudException, InternalException;

    /**
     * Adds the specified route to the specified routing table.
     * @param routingTableId the routing table to which the route will be added
     * @param version ipv4 or ipv6
     * @param destinationCidr the destination IP address or CIDR, or null if setting the default route
     * @param vmId the unique ID of the virtual machine to which traffic is being routed
     * @return route object
     * @throws CloudException an error occurred in the cloud while adding the route to the routing table
     * @throws InternalException a local error occurred processing the request to add the route
     */
    public Route addRouteToVirtualMachine(@Nonnull String routingTableId, @Nonnull IPVersion version, @Nullable String destinationCidr, @Nonnull String vmId) throws CloudException, InternalException;

    /**
     * Indicates that users may self-provision network interfaces. If false, either network interfaces are not supported
     * or they cannot be self-provisioned
     * @return true if users can self-provision network interfaces
     * @throws CloudException an error occurred checking with the cloud if network interfaces may be self provisioned
     * @throws InternalException an error occurred in the Dasein Cloud implementation determining support
     * @deprecated use {@link VLANCapabilities#allowsNewNetworkInterfaceCreation()}
     */
    @Deprecated
    public boolean allowsNewNetworkInterfaceCreation() throws CloudException, InternalException;

    @Deprecated
    public boolean allowsNewVlanCreation() throws CloudException, InternalException;

    @Deprecated
    public boolean allowsNewRoutingTableCreation() throws CloudException, InternalException;

    @Deprecated
    public boolean allowsNewSubnetCreation() throws CloudException, InternalException;

    /**
     * Indicates whether or not you can run both IPv4 and IPv6 over a subnet.
     * @return true if you can run both types of traffic over the same subnet
     * @throws CloudException an error occurred checking with the cloud for support
     * @throws InternalException an error occurred in the Dasein Cloud implementation determining support
     * @deprecated use {@link VLANCapabilities#allowsMultipleTrafficTypesOverSubnet()}
     */
    @Deprecated
    public boolean allowsMultipleTrafficTypesOverSubnet() throws CloudException, InternalException;

    /**
     * Indicates whether or not you can run both IPv4 and IPv6 over a VLAN.
     * @return true if you can run both types of traffic over the same VLAN
     * @throws CloudException an error occurred checking with the cloud for support
     * @throws InternalException an error occurred in the Dasein Cloud implementation determining support
     * @deprecated use {@link VLANCapabilities#allowsMultipleTrafficTypesOverVlan()}
     */
    @Deprecated
    public boolean allowsMultipleTrafficTypesOverVlan() throws CloudException, InternalException;


    /**
     * Assigns the specified routing table to the target subnet.
     * @param subnetId the unique ID of the subnet being assigned the routing table
     * @param routingTableId the routing table to which the subnet is being assigned
     * @throws CloudException an error occurred with the cloud provider assigning the routing table
     * @throws InternalException a local error occurred while processing the request
     */
    public void assignRoutingTableToSubnet(@Nonnull String subnetId, @Nonnull String routingTableId) throws CloudException, InternalException;

    /**
     * Disassociates the specified routing table from the target subnet.
     * @param subnetId the unique ID of the subnet being disassociated from the routing table
     * @param routingTableId the routing table from which the subnet is being disassociated
     * @throws CloudException an error occurred with the cloud provider disassociating the routing table
     * @throws InternalException a local error occurred while processing the request
     */
    public void disassociateRoutingTableFromSubnet(@Nonnull String subnetId, @Nonnull String routingTableId) throws CloudException, InternalException;

    /**
     * Assigns the specified routing table to the target VLAN (or makes it the main routing table among the routing tables)
     * @param vlanId the VLAN to which the routing table is being assigned
     * @param routingTableId the unique ID of the routing table being assigned
     * @throws CloudException an error occurred with the cloud provider assigning the routing table
     * @throws InternalException a local error occurred while processing the request
     */
    public void assignRoutingTableToVlan(@Nonnull String vlanId, @Nonnull String routingTableId) throws CloudException, InternalException;

    /**
     * Attaches a network interface to an existing virtual machine.
     * @param nicId the unique ID of the network interface to attach
     * @param vmId the virtual machine to which the network interface should be attached
     * @param index the 1-based index (-1 meaning at the end) for the attached interface 
     * @throws CloudException an error occurred with the cloud provider attaching the interface                                                           
     * @throws InternalException an error occurred within the Dasein Cloud implementation attaching the interface
     */
    public void attachNetworkInterface(@Nonnull String nicId, @Nonnull String vmId, int index) throws CloudException, InternalException;

    /**
     * Creates an Internet gateway for the specified VLAN. This method makes sense only if the cloud supports enabling Internet routing from VLANs.
     * @param vlanId the unique ID of the VLAN to create an Internet gateway for
     * @return an ID of the newly created gateway in clouds that allow gateway tracking, or null if gateways are not tracked
     * @throws CloudException an error occurred in the cloud while setting up the Internet gateway
     * @throws InternalException a local error occurred while setting up the Internet gateway
     * @throws OperationNotSupportedException this cloud does not allow enabling Internet routing from VLANs either because all VLANs are automatically routed or are never routed
     */
    public @Nullable String createInternetGateway(@Nonnull String vlanId) throws CloudException, InternalException;

    /**
     * Creates a new routing table for the target VLAN.
     * @param vlanId the VLAN for which a routing table is being created
     * @param name the name of the new routing table
     * @param description a description for the new routing table
     * @return a unique ID within the cloud for the specified routing table
     * @throws CloudException an error occurred with the cloud provider while creating the routing table
     * @throws InternalException a local error occurred creating the routing table
     */
    public @Nonnull String createRoutingTable(@Nonnull String vlanId, @Nonnull String name, @Nonnull String description) throws CloudException, InternalException;
    
    /**
     * Provisions a new network interface in accordance with the specified create options. 
     * @param options the options to be used in creating the network interface
     * @return the newly provisioned network interface
     * @throws CloudException an error occurred in the cloud while provisioning the interface
     * @throws InternalException a local error occurred during the provisoning of the interface
     * @throws OperationNotSupportedException if {@link #allowsNewNetworkInterfaceCreation()} is false
     */
    public @Nonnull NetworkInterface createNetworkInterface(@Nonnull NICCreateOptions options) throws CloudException, InternalException;

    /**
     * Provisions a subnet in the specified VLAN using the specified address space.
     * @param cidr the CIDR of the address space within the target VLAN that will be used for the subnet
     * @param inProviderVlanId the provider ID for the VLAN being subnetted
     * @param name the name of the subnet
     * @param description a description of the purpose of the subnet
     * @return a newly created subnet
     * @throws CloudException an error occurred in the cloud while provisioning the subnet
     * @throws InternalException a local error occurred during the provisoning of the subnet
     * @deprecated Use {@link #createSubnet(SubnetCreateOptions)}
     */
    public @Nonnull Subnet createSubnet(@Nonnull String cidr, @Nonnull String inProviderVlanId, @Nonnull String name, @Nonnull String description) throws CloudException, InternalException;

    /**
     * Provisions a subnet with the specified options for subnet creation.
     * @param options the options to be used in provisioning the subnet
     * @return a newly provisioned subnet
     * @throws CloudException an error occurred in the cloud while provisioning the subnet
     * @throws InternalException a local error occurred during the provisoning of the subnet
     */
    public @Nonnull Subnet createSubnet(@Nonnull SubnetCreateOptions options) throws CloudException, InternalException;

    public @Nonnull VLAN createVlan(@Nonnull String cidr, @Nonnull String name, @Nonnull String description, @Nonnull String domainName, @Nonnull String[] dnsServers, @Nonnull String[] ntpServers) throws CloudException, InternalException;

    /**
     * Creates a new vlan based on the specified creation options.
     * @param options the options to be used in creating the vlan
     * @return the unique provider ID identifying the newly created vlan
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws UnsupportedOperationException this cloud doesn't support vlan creation using the specified options
     */
    public @Nonnull VLAN createVlan(@Nonnull VlanCreateOptions options) throws InternalException, CloudException;

    /**
     * Detaches the specified network interface from any virtual machine it might be attached to.
     * @param nicId the unique ID of the network interface to be detached
     * @throws CloudException an error occurred with the cloud provider while detaching the network interface
     * @throws InternalException a local error occurred while detaching the network interface
     */
    public void detachNetworkInterface(@Nonnull String nicId) throws CloudException, InternalException;

    /**
     * Provides access to meta-data about VLAN capabilities in the current region of this cloud.
     * @return a description of the features supported by this region of this cloud
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     */
    public VLANCapabilities getCapabilities() throws CloudException, InternalException;

    /**
     * Specifies the maximum number of network interfaces that may be provisioned.
     * @return the maximum number of network interfaces that may be provisioned or -1 for no limit or -2 for unknown
     * @throws CloudException an error occurred requesting the limit from the cloud provider
     * @throws InternalException a local error occurred figuring out the limit
     * @deprecated use {@link VLANCapabilities#getMaxNetworkInterfaceCount()}
     */
    @Deprecated
    public int getMaxNetworkInterfaceCount() throws CloudException, InternalException;

    @Deprecated
    public int getMaxVlanCount() throws CloudException, InternalException;

    /**
     * Identifies the provider term for a network interface.
     * @param locale the locale in which the term should be provided
     * @return a localized term for "network interface" specific to this cloud provider
     * @deprecated use {@link VLANCapabilities#getProviderTermForNetworkInterface(java.util.Locale)}
     */
    @Deprecated
    public @Nonnull String getProviderTermForNetworkInterface(@Nonnull Locale locale);

    @Deprecated
    public @Nonnull String getProviderTermForSubnet(@Nonnull Locale locale);

    @Deprecated
    public @Nonnull String getProviderTermForVlan(@Nonnull Locale locale);

    /**
     * Fetches the network interfaced specified by the unique network interface ID.
     * @param nicId the unique ID of the desired network interface
     * @return the network interface that matches the specified ID
     * @throws CloudException an error occurred in the cloud provider fetching the desired network interface
     * @throws InternalException a local error occurred while fetching the desired network interface
     */
    public @Nullable NetworkInterface getNetworkInterface(@Nonnull String nicId) throws CloudException, InternalException;

    /**
     * Identifies the routing table that contains the routes for the subnet.
     * @param subnetId the unique ID of the subnet for which you are attempting to identify a routing table
     * @return the matching routing table or <code>null</code> if the cloud doesn't support routing tables
     * @throws CloudException an error occurred loading the routing table for the specified subnet
     * @throws InternalException a local error occurred identifying the routing table
     * @throws OperationNotSupportedException the cloud does not support subnets
     */
    public @Nullable RoutingTable getRoutingTableForSubnet(@Nonnull String subnetId) throws CloudException, InternalException;

    /**
     * Indicates whether or not you may or must manage routing tables for your VLANs/subnets.
     * @return the level of routing table management that is required
     * @throws CloudException an error occurred in the cloud provider determining support
     * @throws InternalException a local error occurred processing the request
     * @deprecated use {@link VLANCapabilities#getRoutingTableSupport()}
     */
    @Deprecated
    public @Nonnull Requirement getRoutingTableSupport() throws CloudException, InternalException;

    /**
     * Identifies the routing table that supports the routes for the VLAN (when subnets are not supported) or the
     * main/default routing table for subnets within the VLAN (when subnets are supported).
     * @param vlanId the VLAN ID of the VLAN whose routing table is being sought
     * @return the matching routing table or <code>null</code> if the cloud doesn't support routing tables
     * @throws CloudException an error occurred loading the routing table for the specified VLAN
     * @throws InternalException a local error occurred identifying the routing table
     */
    public @Nullable RoutingTable getRoutingTableForVlan(@Nonnull String vlanId) throws CloudException, InternalException;

    /**
     * Identifies the routing table that matches the provided id.
     * @param id the id of the route table
     * @return the matching routing table or <code>null</code> if the cloud doesn't support routing tables or no routing table is found
     * @throws CloudException an error occurred loading the routing table
     * @throws InternalException a local error occurred identifying the routing table
     */
    public @Nullable RoutingTable getRoutingTable(@Nonnull String id) throws CloudException, InternalException;

    public @Nullable Subnet getSubnet(@Nonnull String subnetId) throws CloudException, InternalException;

    /**
     * Indicates whether subnets in VLANs are required, optional, or not supported.
     * @return the level of support for subnets in this cloud
     * @throws CloudException an error occurred in the cloud while determining the support level
     * @throws InternalException a local error occurred determining subnet support level
     * @deprecated use {@link VLANCapabilities#getSubnetSupport()}
     */
    @Deprecated
    public @Nonnull Requirement getSubnetSupport() throws CloudException, InternalException;

    public @Nullable VLAN getVlan(@Nonnull String vlanId) throws CloudException, InternalException;

    /**
     * Indicates whether or not you must specify a data center when provisioning your subnet. If {@link Requirement#NONE},
     * then the cloud has no support for data centers and/or subnets or it lacks the ability to provision subnets in
     * specific data centers. {@link Requirement#OPTIONAL} means that the cloud supports both and you may or may not
     * specify a data center. No cloud should ever return {@link Requirement#REQUIRED}. Even if the cloud requires it,
     * the Dasein Cloud implementation should pick on the client's request when none is specified.
     * @return the requirements for specifying a data center when provisioning a subnet
     * @throws CloudException an error occurred with the cloud provider determining support for this functionality
     * @throws InternalException a local error occurred determining support for this functionality
     * @deprecated use {@link VLANCapabilities#identifySubnetDCRequirement()}
     */
    @Deprecated
    public @Nonnull Requirement identifySubnetDCRequirement() throws CloudException, InternalException;

    /**
     * Indicates whether or not the specified VLAN is connected to the public Internet via an Internet Gateway. A false
     * response does not mean that the VLAN is not connected to the public Internet, it just means that, if it is connected,
     * it's not connected via an Internet Gateway.
     * @param vlanId the VLAN you are checking
     * @return <code>true</code> if the VLAN is connected to the public Internet via an Internet Gateway
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred
     */
    public boolean isConnectedViaInternetGateway(@Nonnull String vlanId) throws CloudException, InternalException;

    /**
     * Gets an attached Internet Gateway ID for specified VLAN.  If null then no gateway attached to VLAN.
     * @param vlanId the VLAN to check for an attached internet gateway
     * @return Id for the Internet Gateway
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred
     */
    public @Nullable String getAttachedInternetGatewayId(@Nonnull String vlanId) throws CloudException, InternalException;

    /**
     * Gets the Internet Gateway by ID
     * @param gatewayId the id of the gateway
     * @return Internet Gateway object
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred
     */
    public @Nullable InternetGateway getInternetGatewayById(@Nonnull String gatewayId) throws CloudException, InternalException;

    /**
     * Indicates whether or not this cloud included the concept of network interfaces in its networking support.
     * @return true if this cloud supports network interfaces as part of its networking concepts
     * @throws CloudException an error occurred with the cloud provider determining support for network interfaces
     * @throws InternalException a local error occurred determining support for network interfaces
     */
    public boolean isNetworkInterfaceSupportEnabled() throws CloudException, InternalException;

    public boolean isSubscribed() throws CloudException, InternalException;

    @Deprecated
    public boolean isSubnetDataCenterConstrained() throws CloudException, InternalException;

    @Deprecated
    public boolean isVlanDataCenterConstrained() throws CloudException, InternalException;

    /**
     * Lists the IDs of the firewalls protecting the specified network interface.
     * @param nicId the network interface ID of the desired network interface
     * @return the firewall/security group IDs of all firewalls supporting this network interface
     * @throws CloudException an error occurred with the cloud providing fetching the firewall IDs
     * @throws InternalException a local error occurred while attempting to communicate with the cloud
     */
    public @Nonnull Iterable<String> listFirewallIdsForNIC(@Nonnull String nicId) throws CloudException, InternalException;

    /**
     * Lists all Internet Gateways for an account or optionally all Internet Gateways for a VLAN.
     * @param vlanId the VLAN ID to search for internet gateways
     * @return a list of internet gateways
     * @throws CloudException an error occurred fetching the internet gatewayss from the cloud provider
     * @throws InternalException a local error occurred processing the internet gateways
     */
    public @Nonnull Iterable<InternetGateway> listInternetGateways(@Nullable String vlanId) throws CloudException, InternalException;

    /**
     * Lists the status of all network interfaces currently provisioned in the current region.
     * @return a list of status for all provisioned network interfaces in the current region
     * @throws CloudException an error occurred with the cloud provider fetching the network interfaces
     * @throws InternalException a local error occurred fetching the network interfaces
     */
    public @Nonnull Iterable<ResourceStatus> listNetworkInterfaceStatus() throws CloudException, InternalException;

    /**
     * Lists all network interfaces currently provisioned in the current region.
     * @return a list of all provisioned network interfaces in the current region
     * @throws CloudException an error occurred with the cloud provider fetching the network interfaces
     * @throws InternalException a local error occurred fetching the network interfaces
     */
    public @Nonnull Iterable<NetworkInterface> listNetworkInterfaces() throws CloudException, InternalException;

    /**
     * Lists all network interfaces attached to a specific virtual machine.
     * @param forVmId the virtual machine whose network interfaces you want listed
     * @return the network interfaces attached to the specified virtual machine
     * @throws CloudException an error occurred with the cloud provider determining the attached network interfaces
     * @throws InternalException a local error occurred listing the network interfaces attached to the specified virtual machine
     */
    public @Nonnull Iterable<NetworkInterface> listNetworkInterfacesForVM(@Nonnull String forVmId) throws CloudException, InternalException;

    /**
     * Lists all network interfaces connected to a specific subnet. Valid only if the cloud provider supports subnets.
     * @param subnetId the subnet ID for the subnet in which you are searching
     * @return all interfaces within the specified subnet
     * @throws CloudException an error occurred in the cloud identifying the matching network interfaces
     * @throws InternalException a local error occurred constructing the cloud query
     */
    public @Nonnull Iterable<NetworkInterface> listNetworkInterfacesInSubnet(@Nonnull String subnetId) throws CloudException, InternalException;

    /**
     * Lists all network interfaces connected to a specific VLAN. Valid only if the cloud provider supports VLANs.
     * @param vlanId the VLAN ID for the VLAN in which you are searching
     * @return all interfaces within the specified VLAN
     * @throws CloudException an error occurred in the cloud identifying the matching network interfaces
     * @throws InternalException a local error occurred constructing the cloud query
     */
    public @Nonnull Iterable<NetworkInterface> listNetworkInterfacesInVLAN(@Nonnull String vlanId) throws CloudException, InternalException;

    /**
     * Lists all resources associated with the specified VLAN. In many clouds, this is a very expensive operation. So
     * call this method with care.
     * @param vlanId the VLAN for whom you are seeking the resource list
     * @return a list of resources associated with the specified VLAN
     * @throws CloudException an error occurred in the cloud identifying the matching resources
     * @throws InternalException a local error occurred constructing the cloud query
     */
    public @Nonnull Iterable<Networkable> listResources(@Nonnull String vlanId) throws CloudException, InternalException;

    /**
     * Lists all routing tables associated with the specified Subnet.
     * @param subnetId the subnet ID whose routing tables are being sought
     * @return a list of routing tables for the specified Subnet
     * @throws CloudException an error occurred fetching the routing tables from the cloud provider
     * @throws InternalException a local error occurred processing the routing tables
     */
    public @Nonnull Iterable<RoutingTable> listRoutingTablesForSubnet(@Nonnull String subnetId) throws CloudException, InternalException;

    /**
     * Lists all routing tables associated with the specified VLAN. 
     * @param vlanId the VLAN ID whose routing tables are being sought
     * @return a list of routing tables for the specified VLAN
     * @throws CloudException an error occurred fetching the routing tables from the cloud provider
     * @throws InternalException a local error occurred processing the routing tables
     * @deprecated use {@link #listRoutingTablesForVlan(String)}
     */
    @Deprecated
    public @Nonnull Iterable<RoutingTable> listRoutingTables(@Nonnull String vlanId) throws CloudException, InternalException;

    /**
     * Lists all routing tables associated with the specified VLAN.
     * @param vlanId the VLAN ID whose routing tables are being sought
     * @return a list of routing tables for the specified VLAN
     * @throws CloudException an error occurred fetching the routing tables from the cloud provider
     * @throws InternalException a local error occurred processing the routing tables
     */
    public @Nonnull Iterable<RoutingTable> listRoutingTablesForVlan(@Nonnull String vlanId) throws CloudException, InternalException;

    /**
     * Lists all subnets associated with the specified VLAN.
     * @param vlanId the VLAN ID whose subnets are being sought
     * @return a list of subnets for the specified VLAN
     * @throws CloudException an error occurred fetching the subnets from the cloud provider
     * @throws InternalException a local error occurred processing the subnets
     */
    public @Nonnull Iterable<Subnet> listSubnets(@Nullable String vlanId) throws CloudException, InternalException;

    /**
     * Lists all IP protocol versions supported for VLANs in this cloud.
     * @return a list of supported versions
     * @throws CloudException an error occurred checking support for IP versions with the cloud provider
     * @throws InternalException a local error occurred preparing the supported version
     * @deprecated use {@link VLANCapabilities#listSupportedIPVersions()}
     */
    @Deprecated
    public @Nonnull Iterable<IPVersion> listSupportedIPVersions() throws CloudException, InternalException;

    /**
     * Lists the status of all VLANs in the current region.
     * @return the status of all VLANs in the current region
     * @throws CloudException an error occurred communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nonnull Iterable<ResourceStatus> listVlanStatus() throws CloudException, InternalException;

    /**
     * Lists all VLANs in the current region.
     * @return all VLANs in the current region
     * @throws CloudException an error occurred communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nonnull Iterable<VLAN> listVlans() throws CloudException, InternalException;

    /**
     * Disconnects the specified VLAN from it's gateway and deletes it if no other VLANs are attached to it.
     * @param forVlanId the VLAN to disconnect
     * @throws CloudException an error occurred with the cloud provider while removing the gateway
     * @throws InternalException a local error occurred while removing the gateway
     * @throws OperationNotSupportedException internet gateway creation/removal is not supported in this cloud
     */
    public void removeInternetGateway(@Nonnull String forVlanId) throws CloudException, InternalException;

    /**
     * Disconnects the specified gateway from it's VLAN and deletes it.
     * @param id the gateway id to delete
     * @throws CloudException an error occurred with the cloud provider while removing the gateway
     * @throws InternalException a local error occurred while removing the gateway
     * @throws OperationNotSupportedException internet gateway creation/removal is not supported in this cloud
     */
    public void removeInternetGatewayById(@Nonnull String id) throws CloudException, InternalException;

    /**
     * Removes meta-data from a internet gateway. If tag values are set, their removal is dependent on underlying cloud
     * provider behavior. They may be removed only if the tag value matches or they may be removed regardless of the
     * value.
     * @param internetGatewayId the unique ID of the internet gateway to update
     * @param tags the meta-data tags to remove
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public abstract void removeInternetGatewayTags(@Nonnull String internetGatewayId, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Removes meta-data from multiply internet gateways. If tag values are set, their removal is dependent on underlying cloud
     * provider behavior. They may be removed only if the tag value matches or they may be removed regardless of the
     * value.
     * @param internetGatewayIds the internet gateways to update
     * @param tags the meta-data tags to remove
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public abstract void removeInternetGatewayTags(@Nonnull String[] internetGatewayIds, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * De-provisions the specified network interface.
     * @param nicId the network interface to be de-provisioned
     * @throws CloudException an error occurred with the cloud provider while de-provisioning the network interface
     * @throws InternalException a local error occurred while de-provisioning the network interface
     * @throws OperationNotSupportedException NIC creation/removal is not supported in this cloud
     */
    public void removeNetworkInterface(@Nonnull String nicId) throws CloudException, InternalException;

    /**
     * Removes any routing to the specified destination from the specified routing table.
     * @param routingTableId the routing table from which the route is being removed
     * @param destinationCidr the destination CIDR for the traffic being routed
     * @throws CloudException an error occurred in the cloud while removing the route
     * @throws InternalException a local error occurred processing the request to remove the route
     * @throws OperationNotSupportedException route creation/removal is not supported in this cloud
     */
    public void removeRoute(@Nonnull String routingTableId, @Nonnull String destinationCidr) throws CloudException, InternalException;

    /**
     * Removes the specified routing table from the cloud.
     * @param routingTableId the unique ID of the routing table to be removed
     * @throws CloudException an error occurred in the cloud removing the routing table
     * @throws InternalException a local error occurred while processing the request to remove the routing table
     * @throws OperationNotSupportedException routing table creation/removal is not supported in this cloud
     */
    public void removeRoutingTable(@Nonnull String routingTableId) throws CloudException, InternalException;

    /**
     * Removes meta-data from a routing table. If tag values are set, their removal is dependent on underlying cloud
     * provider behavior. They may be removed only if the tag value matches or they may be removed regardless of the
     * value.
     * @param routingTableId the unique ID of the routing table to update
     * @param tags the meta-data tags to remove
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public abstract void removeRoutingTableTags(@Nonnull String routingTableId, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Removes meta-data from multiple routing table. If tag values are set, their removal is dependent on underlying cloud
     * provider behavior. They may be removed only if the tag value matches or they may be removed regardless of the
     * value.
     * @param routingTableIds the routing tables to update
     * @param tags the meta-data tags to remove
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public abstract void removeRoutingTableTags(@Nonnull String[] routingTableIds, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Removes the specified subnet from the cloud.
     * @param providerSubnetId the subnet to be removed
     * @throws CloudException an error occurred with the cloud provider while removing the subnet
     * @throws InternalException a local error occurred while processing the request
     * @throws OperationNotSupportedException subnet creation/removal is not supported in this cloud
     */
    public void removeSubnet(String providerSubnetId) throws CloudException, InternalException;

    /**
     * Removes meta-data from a subnet. If tag values are set, their removal is dependent on underlying cloud
     * provider behavior. They may be removed only if the tag value matches or they may be removed regardless of the
     * value.
     * @param subnetId the subnet to update
     * @param tags the meta-data tags to remove
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void removeSubnetTags(@Nonnull String subnetId, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Removes meta-data from multiple subnets. If tag values are set, their removal is dependent on underlying cloud
     * provider behavior. They may be removed only if the tag value matches or they may be removed regardless of the
     * value.
     * @param subnetIds the subnets to update
     * @param tags  the meta-data tags to remove
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void removeSubnetTags(@Nonnull String[] subnetIds, @Nonnull Tag ... tags) throws CloudException, InternalException;

    /**
     * Set meta-data for a subnet. Remove any tags that were not provided by the incoming tags, and add or
     * overwrite any new or pre-existing tags.
     *
     * @param subnetId the subnet to set
     * @param tags     the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void setSubnetTags( @Nonnull String subnetId, @Nonnull Tag... tags ) throws CloudException, InternalException;

    /**
     * Set meta-data for multiple subnet. Remove any tags that were not provided by the incoming tags, and add or
     * overwrite any new or pre-existing tags.
     *
     * @param subnetIds the subnets to set
     * @param tags      the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void setSubnetTags( @Nonnull String[] subnetIds, @Nonnull Tag... tags ) throws CloudException, InternalException;

    /**
     * Removes the specified VLAN from the cloud.
     * @param vlanId the unique ID of the VLAN to be removed
     * @throws CloudException an error occurred removing the target VLAN
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws OperationNotSupportedException VLAN removal is not supported
     */
    public void removeVlan(String vlanId) throws CloudException, InternalException;

    /**
     * Removes meta-data from a VLAN. If tag values are set, their removal is dependent on underlying cloud
     * provider behavior. They may be removed only if the tag value matches or they may be removed regardless of the
     * value.
     * @param vlanId the VLAN to update
     * @param tags the meta-data tags to remove
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void removeVLANTags(@Nonnull String vlanId, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Removes meta-data from multiple VLANs. If tag values are set, their removal is dependent on underlying cloud
     * provider behavior. They may be removed only if the tag value matches or they may be removed regardless of the
     * value.
     * @param vlanIds the VLANs to update
     * @param tags  the meta-data tags to remove
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void removeVLANTags(@Nonnull String[] vlanIds, @Nonnull Tag ... tags) throws CloudException, InternalException;

    /**
     * Indicates whether or not this cloud allows enabling of internet gateways for VLANs. This is not relevant if all VLANs are Internet
     * routable or if they simply cannot be made routable.
     * @return true if this cloud supports the optional enablement of Internet gateways for VLANS, false if all VLANs are either always or never Internet routable
     * @throws CloudException an error occurred determining this capability from the cloud provider
     * @throws InternalException a local error occurred determining this capability
     * @deprecated use {@link VLANCapabilities#supportsInternetGatewayCreation()}
     */
    @Deprecated
    public boolean supportsInternetGatewayCreation() throws CloudException, InternalException;

    /**
     * Indicates whether you can specify a raw IP address as a target for your routing table.
     * @return true if you can specify raw addresses, false if you need to specify other resources
     * @throws CloudException an error occurred identifying support
     * @throws InternalException a local error occurred identifying support
     * @deprecated use {@link VLANCapabilities#supportsRawAddressRouting()}
     */
    @Deprecated
    public boolean supportsRawAddressRouting() throws CloudException, InternalException;

    /**
     * Updates meta-data for a subnet with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     * @param subnetId the subnet to update
     * @param tags the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void updateSubnetTags(@Nonnull String subnetId, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Updates meta-data for multiple subnets with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     * @param subnetIds the subnets to update
     * @param tags  the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void updateSubnetTags(@Nonnull String[] subnetIds, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Updates meta-data for a routing table with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     * @param routingTableId the routing table to update
     * @param tags the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public abstract void updateRoutingTableTags(@Nonnull String routingTableId, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Updates meta-data for multiple routing tables with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     * @param routingTableIds the routing tables to update
     * @param tags the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public abstract void updateRoutingTableTags(@Nonnull String[] routingTableIds, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Set meta-data for a routing table. Remove any tags that were not provided by the incoming tags, and add or
     * overwrite any new or pre-existing tags.
     *
     * @param routingTableId the routing table to update
     * @param tags           the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void setRoutingTableTags( @Nonnull String routingTableId, @Nonnull Tag... tags ) throws CloudException, InternalException;

    /**
     * Set meta-data for multiple routing tables. Remove any tags that were not provided by the incoming tags, and add or
     * overwrite any new or pre-existing tags.
     *
     * @param routingTableIds the routing tables to update
     * @param tags            the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void setRoutingTableTags( @Nonnull String[] routingTableIds, @Nonnull Tag... tags ) throws CloudException, InternalException;

    /**
     * Set meta-data for a VLAN. Remove any tags that were not provided by the incoming tags, and add or
     * overwrite any new or pre-existing tags.
     *
     * @param vlanId the VLAN to update
     * @param tags     the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void setVLANTags( @Nonnull String vlanId, @Nonnull Tag... tags ) throws CloudException, InternalException;

    /**
     * Set meta-data for multiple VLAN. Remove any tags that were not provided by the incoming tags, and add or
     * overwrite any new or pre-existing tags.
     *
     * @param vlanIds the VLAN to update
     * @param tags      the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void setVLANTags( @Nonnull String[] vlanIds, @Nonnull Tag... tags ) throws CloudException, InternalException;
    
    /**
     * Updates meta-data for a VLAN with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     * @param vlanId the VLAN to update
     * @param tags the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void updateVLANTags(@Nonnull String vlanId, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Updates meta-data for multiple VLANs with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     * @param vlanIds the VLANs to update
     * @param tags  the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public abstract void updateVLANTags(@Nonnull String[] vlanIds, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Updates meta-data for an internet gateway with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     * @param internetGatewayId the internet gateway to update
     * @param tags  the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public abstract void updateInternetGatewayTags(@Nonnull String internetGatewayId, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Updates meta-data for multiple internet gateways with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     * @param internetGatewayIds the internet gateways to update
     * @param tags  the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public abstract void updateInternetGatewayTags(@Nonnull String[] internetGatewayIds, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Set meta-data for a internet gateway. Remove any tags that were not provided by the incoming tags, and add or
     * overwrite any new or pre-existing tags.
     *
     * @param internetGatewayId the internet gateway to update
     * @param tags              the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void setInternetGatewayTags( @Nonnull String internetGatewayId, @Nonnull Tag... tags ) throws CloudException, InternalException;

    /**
     * Set meta-data for multiple internet gateways. Remove any tags that were not provided by the incoming tags, and add or
     * overwrite any new or pre-existing tags.
     *
     * @param internetGatewayIds the internet gateways to update
     * @param tags               the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void setInternetGatewayTags( @Nonnull String[] internetGatewayIds, @Nonnull Tag... tags ) throws CloudException, InternalException;

}
