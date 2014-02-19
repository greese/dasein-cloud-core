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

import org.dasein.cloud.*;
import org.dasein.cloud.compute.VirtualMachineSupport;
import org.dasein.cloud.identity.ServiceAction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;
import java.util.concurrent.Future;

/**
 * Services for retrieving IP addresses managed independently from virtual machines and 
 * load balancers. Dasein Cloud supports two kinds of address management:
 * <ul>
 * <li>Assigned</li>
 * <li>Forwarded</li>
 * </ul>
 * <p>
 * Assigned addresses are those that are assigned to a virtual machine. The mechanism (NAT vs.
 * direct NIC assignment) is not relevant. With assigned addresses like AWS Elastic IPs, all
 * traffic for that IP addresses on any port is routed to the assigned server. Clouds may
 * supported assigned public IP addresses, private IPs, both, or neither.
 * </p>
 * <p>
 * Forwarded IP addresses enable you to specify IP forwarding rules on an IP address that may vary
 * based on the port in question. On the same IP address, you could forward traffic for the SMTP
 * port to one server and traffic for the HTTP port to another server. An example of a forwarding
 * cloud is ReliaCloud. Only public IP addresses may do forwarding.
 * </p>
 * <p>
 * Clouds may also support client requests for new IP addresses.
 * </p>
 * <p>
 * <i>Note: In many clouds, the {@link IpAddress#getProviderIpAddressId()} has the same value
 * as the {@link IpAddress#getAddress()}. Do not make the mistake of treating them as 
 * interchangeable values as in some clouds the unique ID is in fact different. Where Dasein
 * Cloud asks for an address ID, use the ID. Where it asks for the numeric IP address, provide
 * the numeric IP address.</i>
 * </p>
 * @author George Reese (george.reese@imaginary.com)
 * @since 2009.01
 * @version 2009.01
 * @version 2012.02 - Added support for service actions
 * @version 2012.07 - Began adding support for IPv6
 * @version 2013.02 - Added isAssignablePostLaunch() (issue #35)
 */
@SuppressWarnings("UnusedDeclaration")
public interface IpAddressSupport extends AccessControlledService {
    static public final ServiceAction ANY                 = new ServiceAction("IPADDR:ANY");

    static public final ServiceAction ASSIGN              = new ServiceAction("IPADDR:ASSIGN");
    static public final ServiceAction CREATE_IP_ADDRESS   = new ServiceAction("IPADDR:CREATE_IP_ADDRESS");
    static public final ServiceAction FORWARD             = new ServiceAction("IPADDR:FORWARD");
    static public final ServiceAction GET_IP_ADDRESS      = new ServiceAction("IPADDR:GET_IP_ADDRESS");
    static public final ServiceAction LIST_IP_ADDRESS     = new ServiceAction("IPADDR:LIST_IP_ADDRESS");
    static public final ServiceAction RELEASE             = new ServiceAction("IPADDR:RELEASE");
    static public final ServiceAction REMOVE_IP_ADDRESS   = new ServiceAction("IPADDR:REMOVE_IP_ADDRESS");
    static public final ServiceAction STOP_FORWARD        = new ServiceAction("IPADDR:STOP_FORWARD");

    /**
     * Assigns the specified address to the target server. This method should be called only if
     * {@link #isAssigned(AddressType)} for the specified address's address type is <code>true</code>.
     * If it is not, you will see the {@link RuntimeException} {@link org.dasein.cloud.OperationNotSupportedException}
     * thrown.
     * @param addressId the unique identifier of the address to be assigned
     * @param serverId the unique ID of the server to which the address is being assigned
     * @throws InternalException an internal error occurred inside the Dasein Cloud implementation
     * @throws CloudException an error occurred processing the request in the cloud
     * @throws org.dasein.cloud.OperationNotSupportedException this cloud provider does not support address assignment of the specified address type
     */
    public void assign(@Nonnull String addressId, @Nonnull String serverId) throws InternalException, CloudException;

    /**
     * Assigns the specified address to the specified network interface.
     * @param addressId the unique ID of the IP address to assign
     * @param nicId the unique ID of the network interface to which the address is being assigned
     * @throws InternalException an error occurred locally while performing the assignment
     * @throws CloudException an error occurred in the cloud provider while performing the assignment
     */
    public void assignToNetworkInterface(@Nonnull String addressId, @Nonnull String nicId) throws InternalException, CloudException;

    /**
     * Forwards the specified public IP address traffic on the specified public port over to the
     * specified private port on the specified server. If the server goes away, you will generally 
     * still have traffic being forwarded to the private IP formally associated with the server, so
     * it is best to stop forwarding before terminating a server.
     * <p>
     * You should check {@link #isForwarding()} before calling this method. The implementation should
     * throw a {@link org.dasein.cloud.OperationNotSupportedException} {@link RuntimeException} if the underlying
     * cloud does not support IP address forwarding.
     * </p>
     * @param addressId the unique ID of the public IP address to be forwarded
     * @param publicPort the public port of traffic to be forwarded
     * @param protocol the network protocol being forwarded (not all clouds support ICMP)
     * @param privatePort the private port on the server to which traffic should be forwarded
     * @param onServerId the unique ID of the server to which traffic is to be forwarded
     * @return the rule ID for the forwarding rule that is created
     * @throws InternalException an internal error occurred inside the Dasein Cloud implementation
     * @throws CloudException an error occurred processing the request in the cloud
     * @throws org.dasein.cloud.OperationNotSupportedException this cloud provider does not support address forwarding
     */
    public @Nonnull String forward(@Nonnull String addressId, int publicPort, @Nonnull Protocol protocol, int privatePort, @Nonnull String onServerId) throws InternalException, CloudException;
        
    /**
     * Provides the {@link IpAddress} identified by the specified unique address ID.
     * @param addressId the unique ID of the IP address being requested
     * @return the matching {@link IpAddress}
     * @throws InternalException an internal error occurred inside the Dasein Cloud implementation
     * @throws CloudException an error occurred processing the request in the cloud
     */
    public @Nullable IpAddress getIpAddress(@Nonnull String addressId) throws InternalException, CloudException;
    
    /**
     * The cloud provider-specific term for an IP address. It's hard to fathom what other
     * than "IP address" anyone could use.
     * @param locale the locale into which the term should be translated
     * @return the cloud provider-specific term for an IP address
     */
    public @Nonnull String getProviderTermForIpAddress(@Nonnull Locale locale);

    /**
     * Indicates whether you need to specify which VLAN you are tying a static IP address to when creating an
     * IP address for use in a VLAN. REQUIRED means you must specify the VLAN, OPTIONAL means you may, and NONE
     * means you do not specify a VLAN.
     * @return the level of requirement for specifying a VLAN when creating a VLAN IP address
     * @throws CloudException an error occurred processing the request in the cloud
     * @throws InternalException an internal error occurred inside the Dasein Cloud implementation
     */
    public @Nonnull Requirement identifyVlanForVlanIPRequirement() throws CloudException, InternalException;

    /**
     * Indicates whether the underlying cloud supports the assignment of addresses of the specified
     * type.
     * @param type the type of address being checked (public or private)
     * @return <code>true</code> if addresses of the specified type are assignable to servers
     * @deprecated use {@link #isAssigned(IPVersion)}
     */
    public boolean isAssigned(@Nonnull AddressType type);

    /**
     * Indicates whether the underlying cloud supports the assignment of addresses of the specified version
     * @param version the IP version being checked
     * @return true if the addresses of the specified version are assignable to cloud resources for public routing
     * @throws CloudException an error occurred with the cloud provider determining support
     * @throws InternalException a local error occurred determining support
     */
    public boolean isAssigned(@Nonnull IPVersion version) throws CloudException, InternalException;

    /**
     * When addresses are assignable, they may be assigned at launch, post-launch, or both.
     * {@link VirtualMachineSupport#identifyStaticIPRequirement()} will tell you what must be done
     * at launch time. This method indicates whether or not assignable IPs may be assigned after launch. This
     * method should never return true when {@link #isAssigned(IPVersion)} returns false.
     * @param version the IP version being checked
     * @return true if IP addresses of the specified version can be assigned post launch
     * @throws CloudException an error occurred with the cloud provider determining support
     * @throws InternalException a local error occurred determining support
     */
    public boolean isAssignablePostLaunch(@Nonnull IPVersion version) throws CloudException, InternalException;

    /**
     * Indicates whether the underlying cloud supports the forwarding individual port traffic on 
     * public IP addresses to hosts private IPs. These addresses may also be used for load
     * balancers in some clouds as well.
     * @return <code>true</code> if public IPs may be forwarded on to private IPs
     * @deprecated use {@link #isForwarding(IPVersion)}
     */
    public boolean isForwarding();

    /**
     * Indicates whether the underlying cloud supports the forwarding of traffic on individual ports
     * targeted to addresses of the specified version on to resources in the cloud.
     * @param version the IP version being checked
     * @return true if forwarding is supported
     * @throws CloudException an error occurred with the cloud provider determining support
     * @throws InternalException a local error occurred determining support
     */
    public abstract boolean isForwarding(IPVersion version) throws CloudException, InternalException;
    
    /**
     * Indicates whether the underlying cloud allows you to make programmatic requests for
     * new IP addresses of the specified type
     * @param type the type of address being checked (public or private)
     * @return <code>true</code> if there are programmatic mechanisms for allocating new IPs of the specified type
     * @deprecated use {@link #isRequestable(IPVersion)}
     */
    public boolean isRequestable(@Nonnull AddressType type);

    /**
     * Indicates whether or not you can request static IP addresses of the specified Internet Protocol version.
     * @param version the IP version you may want to request
     * @return true if you can make requests from the cloud provider to add addresses of this version to your pool
     * @throws CloudException an error occurred with the cloud provider while determining if your account has support
     * @throws InternalException a local exception occurred while determining support
     */
    public boolean isRequestable(@Nonnull IPVersion version) throws CloudException, InternalException;
    
    /**
     * Indicates whether this account is subscribed to leverage IP address services in the
     * target cloud.
     * @return <code>true</code> if the account holder is subscribed
     * @throws InternalException an internal error occurred inside the Dasein Cloud implementation
     * @throws CloudException an error occurred processing the request in the cloud
     */
    public boolean isSubscribed() throws CloudException, InternalException;
    
    /**
     * Lists all (or unassigned) private IP addresses from the account holder's private IP address
     * pool. This method is safe to call even if private IP forwarding is not supported. It will
     * simply return {@link java.util.Collections#emptyList()}.
     * @param unassignedOnly indicates that only unassigned addresses are being sought
     * @return all private IP addresses or the unassigned ones from the pool 
     * @throws InternalException an internal error occurred inside the Dasein Cloud implementation
     * @throws CloudException an error occurred processing the request in the cloud
     * @throws OperationNotSupportedException the requested version is not supported
     * @deprecated private IP pools no longer make sense, use the {@link VLANSupport} class
     */
    public @Nonnull Iterable<IpAddress> listPrivateIpPool(boolean unassignedOnly) throws InternalException, CloudException;
    
    /**
     * Lists all (or unassigned) public IP addresses from the account holder's public IP address
     * pool. This method is safe to call even if public IP forwarding is not supported. It will
     * simply return {@link java.util.Collections#emptyList()}.
     * @param unassignedOnly indicates that only unassigned addresses are being sought
     * @return all public IP addresses or the unassigned ones from the pool 
     * @throws InternalException an internal error occurred inside the Dasein Cloud implementation
     * @throws CloudException an error occurred processing the request in the cloud
     * @throws OperationNotSupportedException the requested version is not supported
     * @deprecated use {@link #listIpPool(IPVersion, boolean)}
     */
    public @Nonnull Iterable<IpAddress> listPublicIpPool(boolean unassignedOnly) throws InternalException, CloudException;

    /**
     * Lists all IP addresses of the specified IP version that are allocated to the account holder's IP address pool. If
     * the specified version is not supported, an empty list should be returned.
     * @param version the version of the IP protocol for which you are looking for IP addresses
     * @param unassignedOnly show only IP addresses that have yet to be assigned to cloud resources
     * @return all matching IP addresses from the IP address pool
     * @throws InternalException a local error occurred loading the IP addresses
     * @throws CloudException an error occurred with the cloud provider while requesting the IP addresses
     */
    public abstract @Nonnull Iterable<IpAddress> listIpPool(@Nonnull IPVersion version, boolean unassignedOnly) throws InternalException, CloudException;

    /**
     * Lists all IP addresses of the specified IP version that are allocated to the account holder's IP address pool. If
     * the specified version is not supported, an empty list should be returned.  This method implements a callable so
     * it can be called concurrently.
     * @param version the version of the IP protocol for which you are looking for IP addresses
     * @param unassignedOnly show only IP addresses that have yet to be assigned to cloud resources
     * @return all matching IP addresses from the IP address pool
     * @throws InternalException a local error occurred loading the IP addresses
     * @throws CloudException an error occurred with the cloud provider while requesting the IP addresses
     */
    public abstract @Nonnull
    Future<Iterable<IpAddress>> listIpPoolConcurrently(@Nonnull IPVersion version, boolean unassignedOnly) throws InternalException, CloudException;

    /**
     * Lists the status of all IP addresses of the specified IP version that are allocated to the account holder's IP
     * address pool. If the specified version is not supported, an empty list should be returned.
     * @param version the version of the IP protocol for which you are looking for IP addresses
     * @return the status of all matching IP addresses from the IP address pool
     * @throws InternalException a local error occurred loading the IP addresses
     * @throws CloudException an error occurred with the cloud provider while requesting the IP addresses
     */
    public abstract @Nonnull Iterable<ResourceStatus> listIpPoolStatus(@Nonnull IPVersion version) throws InternalException, CloudException;

    /**
     * Lists the IP forwarding rules associated with the specified public IP address. This method
     * is safe to call even when requested on a private IP address or when IP forwarding is not supported.
     * In those situations, {@link java.util.Collections#emptyList()} will be returned.
     * @param addressId the unique ID of the public address whose forwarding rules will be sought
     * @return all IP forwarding rules for the specified IP address
     * @throws InternalException an internal error occurred inside the Dasein Cloud implementation
     * @throws CloudException an error occurred processing the request in the cloud
     */
    public @Nonnull Iterable<IpForwardingRule> listRules(@Nonnull String addressId) throws InternalException, CloudException;

    /**
     * Lists all IP protocol versions supported for static IP addresses in this cloud.
     * @return a list of supported versions
     * @throws CloudException an error occurred checking support for IP versions with the cloud provider
     * @throws InternalException a local error occurred preparing the supported version
     */
    public abstract @Nonnull Iterable<IPVersion> listSupportedIPVersions() throws CloudException, InternalException;

    /**
     * When a cloud allows for programmatic requesting of new IP addresses, you may also programmaticall
     * release them ({@link #isRequestable(AddressType)}). This method will release the specified IP
     * address from your pool and you will no longer be able to use it for assignment or forwarding.
     * @param addressId the unique ID of the address to be release
     * @throws InternalException an internal error occurred inside the Dasein Cloud implementation
     * @throws CloudException an error occurred processing the request in the cloud
     * @throws org.dasein.cloud.OperationNotSupportedException this cloud provider does not support address requests
     */
    public void releaseFromPool(@Nonnull String addressId) throws InternalException, CloudException;
    
    /**
     * Releases an IP address assigned to a server so that it is unassigned in the address pool. 
     * You should call this method only when {@link #isAssigned(AddressType)} is <code>true</code>
     * for addresses of the target address's type.
     * @param addressId the address ID to release from its server assignment
     * @throws InternalException an internal error occurred inside the Dasein Cloud implementation
     * @throws CloudException an error occurred processing the request in the cloud
     * @throws org.dasein.cloud.OperationNotSupportedException this cloud provider does not support address assignment for addresses of the specified type
     */
    public void releaseFromServer(@Nonnull String addressId) throws InternalException, CloudException;
    
    /**
     * When requests for new IP addresses may be handled programmatically, this method allocates
     * a new IP address of the specified type. You should call it only if
     * {@link #isRequestable(AddressType)} is <code>true</code> for the address's type.
     * @param typeOfAddress the type of address being requested
     * @return the newly allocated IP address
     * @throws InternalException an internal error occurred inside the Dasein Cloud implementation
     * @throws CloudException an error occurred processing the request in the cloud
     * @throws org.dasein.cloud.OperationNotSupportedException this cloud provider does not support address requests
     * @deprecated use {@link #request(IPVersion)}
     */
    public @Nonnull String request(@Nonnull AddressType typeOfAddress) throws InternalException, CloudException;

    /**
     * Requests an IP address of the specified version for the flat (non-VLAN) network space.
     * @param version the IP version of the address to be requested
     * @return the unique ID of the newly provisioned static IP address
     * @throws InternalException a local error occurred while preparing the request
     * @throws CloudException an error occurred with the cloud while provisioning the new address
     */
    public @Nonnull String request(@Nonnull IPVersion version) throws InternalException, CloudException;
    
    /**
     * Requests a public IP address that may be used with a VLAN. This version may be used only when
     * {@link #identifyVlanForVlanIPRequirement()} is not {@link Requirement#REQUIRED}.
     * @param version the IP version of the address to be requested
     * @return the unique ID of a newly provisioned public IP address
     * @throws InternalException an error occurred locally while attempting to provision the IP address
     * @throws CloudException an error occurred in the cloud provider while provisioning the IP address
     * @throws OperationNotSupportedException either VLAN IPs are not supported or they must be explicitly associated with a VLAN
     */
    public @Nonnull String requestForVLAN(@Nonnull IPVersion version) throws InternalException, CloudException;

    /**
     * Requests a public IP address that must be used with a specific VLAN. This version may be used only when
     * {@link #identifyVlanForVlanIPRequirement()} is not {@link Requirement#NONE}.
     * @param version the IP version of the address to be requested
     * @param vlanId the unique ID of the VLAN to which the IP address will be assigned
     * @return the unique ID of a newly provisioned public IP address
     * @throws InternalException an error occurred locally while attempting to provision the IP address
     * @throws CloudException an error occurred in the cloud provider while provisioning the IP address
     * @throws OperationNotSupportedException either VLAN IPs are not supported or they cannot be explicitly associated with a VLAN
     */
    public @Nonnull String requestForVLAN(@Nonnull IPVersion version, @Nonnull String vlanId) throws InternalException, CloudException;

    /**
     * Removes the specified forwarding rule from the address with which it is associated.
     * @param ruleId the rule to be removed
     * @throws InternalException an internal error occurred inside the Dasein Cloud implementation
     * @throws CloudException an error occurred processing the request in the cloud
     * @throws org.dasein.cloud.OperationNotSupportedException this cloud provider does not support address forwarding
     */
    public void stopForward(@Nonnull String ruleId) throws InternalException, CloudException;

    /**
     * Indicates whether or not IP addresses can be allocated for VLAN use. Only makes sense when the cloud
     * actually supports VLANS.
     * @param ofVersion the version of public IP address that might be routed to a VLAN resource
     * @return true if an IP address may be allocated for use by VLANs
     * @throws InternalException a local error occurred determining support
     * @throws CloudException an error occurred with the cloud provider in determining support
     */
    public boolean supportsVLANAddresses(@Nonnull IPVersion ofVersion) throws InternalException, CloudException;
}
