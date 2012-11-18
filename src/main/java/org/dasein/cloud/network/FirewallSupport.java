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

import org.dasein.cloud.AccessControlledService;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.ResourceStatus;
import org.dasein.cloud.identity.ServiceAction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * <p>
 * Operations on whatever concept the underlying cloud uses to regulate network traffic into a
 * server or group of servers.
 * </p>
 * @author George Reese @ enStratus (http://www.enstratus.com)
 * @version 2013.01 Added better permission support and firewall rule id support (Issue #14)
 * @since unknown
 */
public interface FirewallSupport extends AccessControlledService {
    static public final ServiceAction ANY                  = new ServiceAction("FW:ANY");

    static public final ServiceAction AUTHORIZE            = new ServiceAction("FW:AUTHORIZE");
    static public final ServiceAction CREATE_FIREWALL      = new ServiceAction("FW:CREATE_FIREWALL");
    static public final ServiceAction GET_FIREWALL         = new ServiceAction("FW:GET_FIREWALL");
    static public final ServiceAction LIST_FIREWALL        = new ServiceAction("FW:LIST_FIREWALL");
    static public final ServiceAction REMOVE_FIREWALL      = new ServiceAction("FW:REMOVE_FIREWALL");
    static public final ServiceAction REVOKE               = new ServiceAction("FW:REVOKE");

    /**
     * Provides ALLOW/INGRESS authorization to all destinations behing this firewall for the specified rule.
     * Any call to this method should result in an override of any previous revocations.
     * @param firewallId the unique, cloud-specific ID for the firewall being targeted by the new rule
     * @param source the source CIDR (http://en.wikipedia.org/wiki/CIDR) or provider firewall ID for the CIDR or other firewall being set
     * @param protocol the protocol (tcp/udp/icmp) supported by this rule
     * @param beginPort the beginning of the port range to be allowed, inclusive
     * @param endPort the end of the port range to be allowed, inclusive
     * @return the provider ID of the new rule
     * @throws CloudException an error occurred with the cloud provider establishing the rule
     * @throws InternalException an error occurred locally trying to establish the rule
     * @throws OperationNotSupportedException ALLOW, INGRESS, or global destination rules are not allowed
     */
    public @Nonnull String authorize(@Nonnull String firewallId, @Nonnull String source, @Nonnull Protocol protocol, int beginPort, int endPort) throws CloudException, InternalException;

    /**
     * Provides positive authorization to all destinations behind this firewall for the specified rule.
     * Any call to this method should result in an override of any previous revocations.
     * @param firewallId the unique, cloud-specific ID for the firewall being targeted by the new rule
     * @param direction the direction of the traffic governing the rule                  
     * @param source the source CIDR (http://en.wikipedia.org/wiki/CIDR) or provider firewall ID for the CIDR or other firewall being set
     * @param protocol the protocol (tcp/udp/icmp) supported by this rule
     * @param beginPort the beginning of the port range to be allowed, inclusive
     * @param endPort the end of the port range to be allowed, inclusive
     * @return the provider ID of the new rule
     * @throws CloudException an error occurred with the cloud provider establishing the rule
     * @throws InternalException an error occurred locally trying to establish the rule
     * @throws OperationNotSupportedException the specified direction, ALLOW rules, or global destinations are not supported
     */
    public @Nonnull String authorize(@Nonnull String firewallId, @Nonnull Direction direction, @Nonnull String source, @Nonnull Protocol protocol, int beginPort, int endPort) throws CloudException, InternalException;

    /**
     * Provides positive authorization for the specified firewall rule to global destinations. Any call to this method should
     * result in an override of any previous revocations.
     * @param firewallId the unique, cloud-specific ID for the firewall being targeted by the new rule
     * @param direction the direction of the traffic governing the rule
     * @param permission ALLOW or DENY
     * @param source the source CIDR (http://en.wikipedia.org/wiki/CIDR) or provider firewall ID for the CIDR or other firewall being set
     * @param protocol the protocol (tcp/udp/icmp) supported by this rule
     * @param beginPort the beginning of the port range to be allowed, inclusive
     * @param endPort the end of the port range to be allowed, inclusive
     * @return the provider ID of the new rule
     * @throws CloudException an error occurred with the cloud provider establishing the rule
     * @throws InternalException an error occurred locally trying to establish the rule
     * @throws OperationNotSupportedException the specified direction or permission are not supported or global destinations are not supported
     */
    public @Nonnull String authorize(@Nonnull String firewallId, @Nonnull Direction direction, @Nonnull Permission permission, @Nonnull String source, @Nonnull Protocol protocol, int beginPort, int endPort) throws CloudException, InternalException;

    /**
     * Provides positive authorization for the specified firewall rule. Any call to this method should
     * result in an override of any previous revocations.
     * @param firewallId the unique, cloud-specific ID for the firewall being targeted by the new rule
     * @param direction the direction of the traffic governing the rule
     * @param permission ALLOW or DENY
     * @param source the source CIDR (http://en.wikipedia.org/wiki/CIDR) or provider firewall ID for the CIDR or other firewall being set
     * @param protocol the protocol (tcp/udp/icmp) supported by this rule
     * @param destination the destination to specify for this rule
     * @param beginPort the beginning of the port range to be allowed, inclusive
     * @param endPort the end of the port range to be allowed, inclusive
     * @return the provider ID of the new rule
     * @throws CloudException an error occurred with the cloud provider establishing the rule
     * @throws InternalException an error occurred locally trying to establish the rule
     * @throws OperationNotSupportedException the specified direction, destination, or permission are not supported
     */
    public @Nonnull String authorize(@Nonnull String firewallId, @Nonnull Direction direction, @Nonnull Permission permission, @Nonnull String source, @Nonnull Protocol protocol, @Nonnull RuleDestination destination, int beginPort, int endPort) throws CloudException, InternalException;

    /**
     * Creates a new firewall with the specified name.
     * @param name the user-friendly name for the new firewall
     * @param description a description of the purpose of the firewall
     * @return the unique ID for the newly created firewall
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     * @throws InternalException an error occurred locally independent of any events in the cloud
     */
    public @Nonnull String create(@Nonnull String name, @Nonnull String description) throws InternalException, CloudException;
    
    /**
     * Creates a new firewall with the specified name governing the target VLAN. If the underlying cloud 
     * doesn't support VLANs, then this method will throw an UnsupportedOperationException.
     * @param name the user-friendly name for the new firewall
     * @param description a description of the purpose of the firewall
     * @param providerVlanId the ID of the VLAN with which this firewall will be associated
     * @return the unique ID for the newly created firewall
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws UnsupportedOperationException this cloud doesn't support VLANs with firewalls
     */
    @SuppressWarnings("unused")
    public @Nonnull String createInVLAN(@Nonnull String name, @Nonnull String description, @Nonnull String providerVlanId) throws InternalException, CloudException;
    
    
    /**
     * Deletes the specified firewall from the system.
     * @param firewallId the unique ID of the firewall to be deleted
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public void delete(@Nonnull String firewallId) throws InternalException, CloudException;
    
    /**
     * Provides the full firewall data for the specified firewall.
     * @param firewallId the unique ID of the desired firewall
     * @return the firewall state for the specified firewall instance
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public @Nullable Firewall getFirewall(@Nonnull String firewallId) throws InternalException, CloudException;
    
    /**
     * Provides the firewall terminology for the concept of a firewall. For example, AWS calls a 
     * firewall a "security group".
     * @param locale the locale for which you should translate the firewall term
     * @return the translated term for firewall with the target cloud provider
     */
    public @Nonnull String getProviderTermForFirewall(@Nonnull Locale locale);
    
    /**
     * Provides the affirmative rules supported by the named firewall.
     * @param firewallId the unique ID of the firewall being queried
     * @return all rules supported by the target firewall
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public @Nonnull Collection<FirewallRule> getRules(@Nonnull String firewallId) throws InternalException, CloudException;

    /**
     * Identifies whether or not the current account is subscribed to firewall services in the current region.
     * @return true if the current account is subscribed to firewall services for the current region
     * @throws CloudException an error occurred with the cloud provider while determining subscription status
     * @throws InternalException an error occurred in the Dasein Cloud implementation while determining subscription status
     */
    public boolean isSubscribed() throws CloudException, InternalException;

    /**
     * Lists all firewalls in the current provider context.
     * @return a list of all firewalls in the current provider context
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public @Nonnull Collection<Firewall> list() throws InternalException, CloudException;

    /**
     * Lists the status for all firewalls in the current provider context.
     * @return the status for all firewalls in the current account
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public @Nonnull Iterable<ResourceStatus> listFirewallStatus() throws InternalException, CloudException;

    /**
     * Describes what kinds of destinations may be named. A cloud must support at least one, but may support more
     * than one.
     * @param inVlan whether or not you are testing capabilities for VLAN firewalls
     * @return a list of supported destinations
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public @Nonnull Iterable<DestinationType> listSupportedDestinationTypes(boolean inVlan) throws InternalException, CloudException;

    /**
     * Revokes the uniquely identified firewall rule.
     * @param providerFirewallRuleId the unique ID of the firewall.
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public abstract void revoke(@Nonnull String providerFirewallRuleId) throws InternalException, CloudException;

    /**
     * Revokes the specified INGRESS + ALLOW access from the named firewall.
     * @param firewallId the firewall from which the rule is being revoked
     * @param source the source CIDR (http://en.wikipedia.org/wiki/CIDR) or provider firewall ID for the CIDR or other firewall being set
     * @param protocol the protocol (tcp/icmp/udp) of the rule being removed
     * @param beginPort the initial port of the rule being removed
     * @param endPort the end port of the rule being removed
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public void revoke(@Nonnull String firewallId, @Nonnull String source, @Nonnull Protocol protocol, int beginPort, int endPort) throws CloudException, InternalException;

    /**
     * Revokes the specified ALLOW access from the named firewall.
     * @param firewallId the firewall from which the rule is being revoked
     * @param direction the direction of the traffic being revoked                  
     * @param source the source CIDR (http://en.wikipedia.org/wiki/CIDR) or provider firewall ID for the CIDR or other firewall being set
     * @param protocol the protocol (tcp/icmp/udp) of the rule being removed
     * @param beginPort the initial port of the rule being removed
     * @param endPort the end port of the rule being removed
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public void revoke(@Nonnull String firewallId, @Nonnull Direction direction, @Nonnull String source, @Nonnull Protocol protocol, int beginPort, int endPort) throws CloudException, InternalException;

    /**
     * Revokes the specified access from the named firewall.
     * @param firewallId the firewall from which the rule is being revoked
     * @param direction the direction of the traffic being revoked
     * @param permission ALLOW or DENY
     * @param source the source CIDR (http://en.wikipedia.org/wiki/CIDR) or provider firewall ID for the CIDR or other firewall being set
     * @param protocol the protocol (tcp/icmp/udp) of the rule being removed
     * @param beginPort the initial port of the rule being removed
     * @param endPort the end port of the rule being removed
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public void revoke(@Nonnull String firewallId, @Nonnull Direction direction, @Nonnull Permission permission, @Nonnull String source, @Nonnull Protocol protocol, int beginPort, int endPort) throws CloudException, InternalException;

    /**
     * Revokes the specified access from the named firewall.
     * @param firewallId the firewall from which the rule is being revoked
     * @param direction the direction of the traffic being revoked
     * @param permission ALLOW or DENY
     * @param source the source CIDR (http://en.wikipedia.org/wiki/CIDR) or provider firewall ID for the CIDR or other firewall being set
     * @param protocol the protocol (tcp/icmp/udp) of the rule being removed
     * @param destination the target for traffic matching this rule
     * @param beginPort the initial port of the rule being removed
     * @param endPort the end port of the rule being removed
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public void revoke(@Nonnull String firewallId, @Nonnull Direction direction, @Nonnull Permission permission, @Nonnull String source, @Nonnull Protocol protocol, @Nonnull RuleDestination destination, int beginPort, int endPort) throws CloudException, InternalException;

    /**
     * Indicates whether firewalls of the specified type (VLAN or flat network) support rules over the direction specified.
     * @param direction the direction of the traffic
     * @param permission the type of permission
     * @param inVlan whether or not you are looking for support for VLAN or flat network traffic
     * @return true if the cloud supports the creation of firewall rules in the direction specfied for the type of network specified
     * @throws CloudException an error occurred with the cloud provider while checking for support
     * @throws InternalException a local error occurred while checking for support
     */
    public boolean supportsRules(@Nonnull Direction direction, @Nonnull Permission permission, boolean inVlan) throws CloudException, InternalException;

    /**
     * Indicates whether or the sources you specify in your rules may be other firewalls (security group behavior).
     * @return true if the sources may be other firewalls
     * @throws CloudException an error occurred with the cloud provider while checking for support
     * @throws InternalException a local error occurred while checking for support
     */
    public boolean supportsFirewallSources() throws CloudException, InternalException;
}
