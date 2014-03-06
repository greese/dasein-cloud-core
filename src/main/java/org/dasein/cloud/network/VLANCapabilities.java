/**
 * Copyright (C) 2009-2014 Dell, Inc.
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

import org.dasein.cloud.Capabilities;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.Requirement;

import javax.annotation.Nonnull;
import java.util.Locale;

/**
 * Describes the capabilities of a region within a cloud for a specific account.
 * <p>Created by George Reese: 2/27/14 3:01 PM</p>
 * @author George Reese
 * @version 2014.03 initial version
 * @since 2014.03
 */
public interface VLANCapabilities extends Capabilities{

    /**
     * Indicates whether or not a NIC can be self-provisioned.
     * @return false if either not supported or cannot be self-provisioned
     * @throws CloudException
     * @throws InternalException
     */
    public boolean allowsNewNetworkInterfaceCreation() throws CloudException, InternalException;

    /**
     * Indicates whether new VLANs can be self-provisioned
     * @return false if VLANs cannot be manually self-provisioned
     * @throws CloudException
     * @throws InternalException
     */
    public boolean allowsNewVlanCreation() throws CloudException, InternalException;

    /**
     * Indicates whether or not Routing Tables can be provisioned
     * @return false if either Routing Tables are not supported or cannot be provisioned
     * @throws CloudException
     * @throws InternalException
     */
    public boolean allowsNewRoutingTableCreation() throws CloudException, InternalException;

    /**
     * Indicates whether or not the cloud allows the creation of subnets
     * @return false if subnets are not supported
     * @throws CloudException
     * @throws InternalException
     */
    public boolean allowsNewSubnetCreation() throws CloudException, InternalException;

    /**
     * Indicates whether or not you can run both IPv4 and IPv6 over a subnet.
     * @return true if you can run both types of traffic over the same subnet
     * @throws CloudException    an error occurred checking with the cloud for support
     * @throws InternalException an error occurred in the Dasein Cloud implementation determining support
     */
    public boolean allowsMultipleTrafficTypesOverSubnet() throws CloudException, InternalException;

    /**
     * Indicates whether or not you can run both IPv4 and IPv6 over a VLAN.
     * @return true if you can run both types of traffic over the same VLAN
     * @throws CloudException    an error occurred checking with the cloud for support
     * @throws InternalException an error occurred in the Dasein Cloud implementation determining support
     */
    public boolean allowsMultipleTrafficTypesOverVlan() throws CloudException, InternalException;

    /**
     * Specifies the maximum number of network interfaces that may be provisioned.
     * @return the maximum number of network interfaces that may be provisioned or -1 for no limit or -2 for unknown
     * @throws CloudException    an error occurred requesting the limit from the cloud provider
     * @throws InternalException a local error occurred figuring out the limit
     */
    public int getMaxNetworkInterfaceCount() throws CloudException, InternalException;

    /**
     * Specifies the maximum number of VLANs that may be provisioned
     * @return the maximum number of VLANs that may be provisioned or -1 for no limit or -2 for unknown
     * @throws CloudException
     * @throws InternalException
     */
    public int getMaxVlanCount() throws CloudException, InternalException;

    /**
     * Identifies the provider term for a network interface.
     * @param locale the locale in which the term should be provided
     * @return a localized term for "network interface" specific to this cloud provider
     */
    public @Nonnull String getProviderTermForNetworkInterface(@Nonnull Locale locale);

    /**
     * Identifies the provider term for a subnet
     * @param locale
     * @return a loalized term for "Subnet" specific to this cloud provider
     */
    public @Nonnull String getProviderTermForSubnet(@Nonnull Locale locale);

    /**
     * Identifies the provider term for a VLAN
     * @param locale
     * @return a localized term for "VLAN" specific to this cloud provider
     */
    public @Nonnull String getProviderTermForVlan(@Nonnull Locale locale);

    /**
     * Indicates whether or not you may or must manage routing tables for your VLANs/subnets.
     * @return the level of routing table management that is required
     * @throws CloudException    an error occurred in the cloud provider determining support
     * @throws InternalException a local error occurred processing the request
     */
    public @Nonnull Requirement getRoutingTableSupport() throws CloudException, InternalException;

    /**
     * Indicates whether subnets in VLANs are required, optional, or not supported.
     * @return the level of support for subnets in this cloud
     * @throws CloudException    an error occurred in the cloud while determining the support level
     * @throws InternalException a local error occurred determining subnet support level
     */
    public @Nonnull Requirement getSubnetSupport() throws CloudException, InternalException;

    /**
     * Indicates whether or not you must specify a data center when provisioning your subnet. If {@link
     * Requirement#NONE},
     * then the cloud has no support for data centers and/or subnets or it lacks the ability to provision subnets in
     * specific data centers. {@link Requirement#OPTIONAL} means that the cloud supports both and you may or may not
     * specify a data center. No cloud should ever return {@link Requirement#REQUIRED}. Even if the cloud requires it,
     * the Dasein Cloud implementation should pick on the client's request when none is specified.
     * @return the requirements for specifying a data center when provisioning a subnet
     * @throws CloudException    an error occurred with the cloud provider determining support for this functionality
     * @throws InternalException a local error occurred determining support for this functionality
     */
    public @Nonnull Requirement identifySubnetDCRequirement() throws CloudException, InternalException;

    /**
     * Indicates whether or not this cloud included the concept of network interfaces in its networking support.
     * @return true if this cloud supports network interfaces as part of its networking concepts
     * @throws CloudException    an error occurred with the cloud provider determining support for network interfaces
     * @throws InternalException a local error occurred determining support for network interfaces
     */
    public boolean isNetworkInterfaceSupportEnabled() throws CloudException, InternalException;

    /**
     * Indicates whether or not the subnet is capable of spanning the datacenter in which it is provisioned
     * @return true if the subnet is constrained to a given datacenter
     * @throws CloudException
     * @throws InternalException
     */
    public boolean isSubnetDataCenterConstrained() throws CloudException, InternalException;

    /**
     * Indicates whether or not the VLAN is capable of spanning the datacenter in which it is provisioned
     * @return true if the VLAN is constrained to a given datacenter
     * @throws CloudException
     * @throws InternalException
     */
    public boolean isVlanDataCenterConstrained() throws CloudException, InternalException;

    /**
     * Lists all IP protocol versions supported for VLANs in this cloud.
     * @return a list of supported versions
     * @throws CloudException    an error occurred checking support for IP versions with the cloud provider
     * @throws InternalException a local error occurred preparing the supported version
     */
    public @Nonnull Iterable<IPVersion> listSupportedIPVersions() throws CloudException, InternalException;

    /**
     * Indicates whether or not this cloud allows enabling of internet gateways for VLANs. This is not relevant if all
     * VLANs are Internet
     * routable or if they simply cannot be made routable.
     * @return true if this cloud supports the optional enablement of Internet gateways for VLANS, false if all VLANs
     *         are either always or never Internet routable
     * @throws CloudException    an error occurred determining this capability from the cloud provider
     * @throws InternalException a local error occurred determining this capability
     */
    public boolean supportsInternetGatewayCreation() throws CloudException, InternalException;

    /**
     * Indicates whether you can specify a raw IP address as a target for your routing table.
     * @return true if you can specify raw addresses, false if you need to specify other resources
     * @throws CloudException    an error occurred identifying support
     * @throws InternalException a local error occurred identifying support
     */
    public boolean supportsRawAddressRouting() throws CloudException, InternalException;
}
