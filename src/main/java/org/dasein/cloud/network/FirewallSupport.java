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

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import org.dasein.cloud.AccessControlledService;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.Requirement;
import org.dasein.cloud.ResourceStatus;
import org.dasein.cloud.Tag;
import org.dasein.cloud.identity.ServiceAction;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * <p>
 * Operations on whatever concept the underlying cloud uses to regulate network traffic into and out of a
 * server or group of servers.
 * </p>
 * @author George Reese @ enstratius (http://www.enstratius.com)
 * @version 2013.01 Added better permission support and firewall rule id support (Issue #14)
 * @version 2013.02 Added support for rule precedence (issue #33)
 * @version 2013.02 Added specifying both source and destination as {@link RuleTarget} objects (issue #26)
 * @version 2013.02 Added meta-data for source endpoint types (issue #27)
 * @version 2014.03 Added support for creating firewall rules through a create options object
 * @version 2014.03 Added support for firewall constraints (issue #99)
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
     * Provides ALLOW/INGRESS authorization to all destinations behind this firewall for the specified rule.
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
     * The meaning of "source" and "target" is counter-intuitive in this method for EGRESS operations. For the deprecated
     * authorize methods, source ALWAYS means the thing outside of this firewall and target ALWAYS means the resources being
     * protected by this firewall. Consequently, the source is the destination for EGRESS rules (but not INGRESS).
     * To avoid confusion, avoid deprecated versions of this method.
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
     * @deprecated Use {@link #authorize(String, Direction, Permission, RuleTarget, Protocol, RuleTarget, int, int, int)}
     */
    public @Nonnull String authorize(@Nonnull String firewallId, @Nonnull Direction direction, @Nonnull String source, @Nonnull Protocol protocol, int beginPort, int endPort) throws CloudException, InternalException;

    /**
     * Provides positive authorization for the specified firewall rule to global destinations. Any call to this method should
     * The meaning of "source" and "target" is counter-intuitive in this method for EGRESS operations. For the deprecated
     * authorize methods, source ALWAYS means the thing outside of this firewall and target ALWAYS means the resources being
     * protected by this firewall. Consequently, the source is the destination for EGRESS rules (but not INGRESS).
     * To avoid confusion, avoid deprecated versions of this method.
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
     * @deprecated Use {@link #authorize(String, Direction, Permission, RuleTarget, Protocol, RuleTarget, int, int, int)}
     */
    public @Nonnull String authorize(@Nonnull String firewallId, @Nonnull Direction direction, @Nonnull Permission permission, @Nonnull String source, @Nonnull Protocol protocol, int beginPort, int endPort) throws CloudException, InternalException;

    /**
     * Provides positive authorization for the specified firewall rule. Any call to this method should
     * result in an override of any previous revocations.
     * The meaning of "source" and "target" is counter-intuitive in this method for EGRESS operations. For the deprecated
     * authorize methods, source ALWAYS means the thing outside of this firewall and target ALWAYS means the resources being
     * protected by this firewall. Consequently, the source is the destination for EGRESS rules (but not INGRESS).
     * To avoid confusion, avoid deprecated versions of this method.
     * @param firewallId the unique, cloud-specific ID for the firewall being targeted by the new rule
     * @param direction the direction of the traffic governing the rule
     * @param permission ALLOW or DENY
     * @param source the source CIDR (http://en.wikipedia.org/wiki/CIDR) or provider firewall ID for the CIDR or other firewall being set
     * @param protocol the protocol (tcp/udp/icmp) supported by this rule
     * @param target the target to specify for this rule
     * @param beginPort the beginning of the port range to be allowed, inclusive
     * @param endPort the end of the port range to be allowed, inclusive
     * @return the provider ID of the new rule
     * @throws CloudException an error occurred with the cloud provider establishing the rule
     * @throws InternalException an error occurred locally trying to establish the rule
     * @throws OperationNotSupportedException the specified direction, target, or permission are not supported
     * @deprecated Use {@link #authorize(String, Direction, Permission, RuleTarget, Protocol, RuleTarget, int, int, int)}
     */
    public @Nonnull String authorize(@Nonnull String firewallId, @Nonnull Direction direction, @Nonnull Permission permission, @Nonnull String source, @Nonnull Protocol protocol, @Nonnull RuleTarget target, int beginPort, int endPort) throws CloudException, InternalException;

    /**
     * Provides positive authorization for the specified firewall rule with the specified precedence. Any call to this method should
     * result in an override of any previous revocations. For this method, the source endpoint is the source for the traffic and
     * the destination endpoint is where the traffic terminates. For INGRESS rules, the destination endpoint will thus be
     * resources protected by this firewall and for EGRESS rules the destination endpoint is one or more external resources.
     * @param firewallId the unique, cloud-specific ID for the firewall being targeted by the new rule
     * @param direction the direction of the traffic governing the rule
     * @param permission ALLOW or DENY
     * @param sourceEndpoint the source endpoint for this rule
     * @param protocol the protocol (tcp/udp/icmp) supported by this rule
     * @param destinationEndpoint the destination endpoint to specify for this rule
     * @param beginPort the beginning of the port range to be allowed, inclusive
     * @param endPort the end of the port range to be allowed, inclusive
     * @param precedence the precedence of this rule with respect to others
     * @return the provider ID of the new rule
     * @throws CloudException an error occurred with the cloud provider establishing the rule
     * @throws InternalException an error occurred locally trying to establish the rule
     * @throws OperationNotSupportedException the specified direction, target, or permission are not supported
     */
    public @Nonnull String authorize(@Nonnull String firewallId, @Nonnull Direction direction, @Nonnull Permission permission, @Nonnull RuleTarget sourceEndpoint, @Nonnull Protocol protocol, @Nonnull RuleTarget destinationEndpoint, int beginPort, int endPort, @Nonnegative int precedence) throws CloudException, InternalException;

    /**
     * Provides positive authorization matching the specified options for creating a new firewall rule.
     * @param firewallId the unique, cloud-specific ID for the firewall being targeted by the new rule
     * @param ruleOptions the create options that dictate how the rule is added
     * @return the provider ID of the new rule
     * @throws CloudException an error occurred with the cloud provider establishing the rule
     * @throws InternalException an error occurred locally trying to establish the rule
     * @throws OperationNotSupportedException the specified direction, target, or permission are not supported
     */
    public @Nonnull String authorize(@Nonnull String firewallId, @Nonnull FirewallRuleCreateOptions ruleOptions) throws CloudException, InternalException;

    /**
     * Creates a new firewall with the specified name.
     * @param name the user-friendly name for the new firewall
     * @param description a description of the purpose of the firewall
     * @return the unique ID for the newly created firewall
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws UnsupportedOperationException this cloud doesn't support firewall creation
     * @deprecated use {@link #create(FirewallCreateOptions)}
     */
    public @Nonnull String create(@Nonnull String name, @Nonnull String description) throws InternalException, CloudException;

    /**
     * Creates a new firewall based on the specified creation options.
     * @param options the options to be used in creating the firewall
     * @return the unique provider ID identifying the newly created firewall
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws UnsupportedOperationException this cloud doesn't support firewall creation using the specified options
     */
    public @Nonnull String create(@Nonnull FirewallCreateOptions options) throws InternalException, CloudException;

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
     * @deprecated use {@link #create(FirewallCreateOptions)}
     */
    public @Nonnull String createInVLAN(@Nonnull String name, @Nonnull String description, @Nonnull String providerVlanId) throws InternalException, CloudException;

    /**
     * Deletes the specified firewall from the system.
     * @param firewallId the unique ID of the firewall to be deleted
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public void delete(@Nonnull String firewallId) throws InternalException, CloudException;

    /**
     * Identifies the constraints and values currently active for the specified firewall. The constrained fields
     * should match the fields defined as being constrained in {@link #getFirewallConstraintsForCloud()}.
     * @param firewallId the ID for which you are seeking active constraints
     * @return a map of constraints to the value on which a given rule value is constrained
     * @throws InternalException an error occurred inside Dasein Cloud processing the request
     * @throws CloudException an error occurred communicating with the cloud provider in assembling the list
     */
    public @Nullable Map<FirewallConstraints.Constraint, Object> getActiveConstraintsForFirewall(@Nonnull String firewallId) throws InternalException, CloudException;

    /**
     * Provides access to meta-data about Firewall capabilities in the current region of this cloud.
     * @return a description of the features supported by this region of this cloud
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     */
    public @Nonnull FirewallCapabilities getCapabilities()throws CloudException, InternalException;

    /**
     * Provides the full firewall data for the specified firewall.
     * @param firewallId the unique ID of the desired firewall
     * @return the firewall state for the specified firewall instance
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public @Nullable Firewall getFirewall(@Nonnull String firewallId) throws InternalException, CloudException;

    /**
     * Fetches the constraints for firewalls in this cloud. A constraint is a field that all rules
     * associated with a firewall must share. For example, a firewall constrained on
     * {@link FirewallConstraints.Constraint#PROTOCOL} requires all rules associated with it to share
     * the same protocol.
     * @return the firewall constraints for this cloud
     * @throws InternalException an internal error occurred assembling the cloud firewall constraints
     * @throws CloudException an error occurred fetching constraint data from the cloud
     * @deprecated use {@link FirewallCapabilities#getFirewallConstraintsForCloud()}
     */
    @Deprecated
    public @Nonnull FirewallConstraints getFirewallConstraintsForCloud() throws InternalException, CloudException;

    /**
     * Provides the firewall terminology for the concept of a firewall. For example, AWS calls a 
     * firewall a "security group".
     * @param locale the locale for which you should translate the firewall term
     * @return the translated term for firewall with the target cloud provider
     * @deprecated use {@link FirewallCapabilities#getProviderTermForFirewall(java.util.Locale)}
     */
    @Deprecated
    public @Nonnull String getProviderTermForFirewall(@Nonnull Locale locale);
    
    /**
     * Provides the affirmative rules supported by the named firewall ordered in order of precedence with the most
     * important rule first. <em>Implementation note: natural sorting order for {@link FirewallRule} is low to
     * high. If this cloud has 0 as a low priority, you should reverse the natural sort!</em>
     * @param firewallId the unique ID of the firewall being queried
     * @return all rules supported by the target firewall
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public @Nonnull Iterable<FirewallRule> getRules(@Nonnull String firewallId) throws InternalException, CloudException;

    /**
     * Indicates the degree to which authorizations expect precedence of rules to be established.
     * @param inVlan whether or not you are checking for VLAN firewalls or regular ones
     * @return the degree to which precedence is required
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     * @deprecated use {@link FirewallCapabilities#identifyPrecedenceRequirement(boolean)}
     */
    @Deprecated
    public @Nonnull Requirement identifyPrecedenceRequirement(boolean inVlan) throws InternalException, CloudException;

    /**
     * Identifies whether or not the current account is subscribed to firewall services in the current region.
     * @return true if the current account is subscribed to firewall services for the current region
     * @throws CloudException an error occurred with the cloud provider while determining subscription status
     * @throws InternalException an error occurred in the Dasein Cloud implementation while determining subscription status
     */
    public boolean isSubscribed() throws CloudException, InternalException;

    /**
     * Indicates whether the highest precedence comes from low numbers. If true, 0 is the highest precedence a rule
     * can have. If false, 0 is the lowest precedence.
     * @return true if 0 is the highest precedence for a rule
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     * @deprecated use {@link FirewallCapabilities#isZeroPrecedenceHighest()}
     */
    @Deprecated
    public boolean isZeroPrecedenceHighest() throws InternalException, CloudException;

    /**
     * Lists all firewalls in the current provider context.
     * @return a list of all firewalls in the current provider context
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public @Nonnull Iterable<Firewall> list() throws InternalException, CloudException;

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
     * @deprecated use {@link FirewallCapabilities#listSupportedDestinationTypes(boolean)}
     */
    @Deprecated
    public @Nonnull Iterable<RuleTargetType> listSupportedDestinationTypes(boolean inVlan) throws InternalException, CloudException;

    /**
     * Lists the supported traffic directions for rules behind this kind of firewall.
     * @param inVlan whether or not you are interested in VLAN firewalls
     * @return a list of supported directions
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     * @deprecated use {@link FirewallCapabilities#listSupportedDirections(boolean)}
     */
    @Deprecated
    public @Nonnull Iterable<Direction> listSupportedDirections(boolean inVlan) throws InternalException, CloudException;

    /**
     * Lists the types of permissions that one may authorize for a firewall rule.
     * @param inVlan whether or not you are interested in VLAN firewalls or general ones
     * @return a list of supported permissions
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     * @deprecated use {@link FirewallCapabilities#listSupportedPermissions(boolean)}
     */
    @Deprecated
    public @Nonnull Iterable<Permission> listSupportedPermissions(boolean inVlan) throws InternalException, CloudException;

    /**
     * Describes what kinds of source endpoints may be named. A cloud must support at least one, but may support more
     * than one.
     * @param inVlan whether or not you are testing capabilities for VLAN firewalls
     * @return a list of supported source endpoints
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     * @deprecated use {@link FirewallCapabilities#listSupportedSourceTypes(boolean)}
     */
    @Deprecated
    public @Nonnull Iterable<RuleTargetType> listSupportedSourceTypes(boolean inVlan) throws InternalException, CloudException;

    /**
     * Removes meta-data from a firewall. If tag values are set, their removal is dependent on underlying cloud
     * provider behavior. They may be removed only if the tag value matches or they may be removed regardless of the
     * value.
     * @param firewallId the firewall to update
     * @param tags the meta-data tags to remove
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void removeTags(@Nonnull String firewallId, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Removes meta-data from multiple firewalls. If tag values are set, their removal is dependent on underlying cloud
     * provider behavior. They may be removed only if the tag value matches or they may be removed regardless of the
     * value.
     * @param firewallIds the firewalls to update
     * @param tags  the meta-data tags to remove
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void removeTags(@Nonnull String[] firewallIds, @Nonnull Tag ... tags) throws CloudException, InternalException;

    /**
     * Revokes the uniquely identified firewall rule.
     * @param providerFirewallRuleId the unique ID of the firewall rule
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public void revoke(@Nonnull String providerFirewallRuleId) throws InternalException, CloudException;

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
     * @param target the target for traffic matching this rule
     * @param beginPort the initial port of the rule being removed
     * @param endPort the end port of the rule being removed
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public void revoke(@Nonnull String firewallId, @Nonnull Direction direction, @Nonnull Permission permission, @Nonnull String source, @Nonnull Protocol protocol, @Nonnull RuleTarget target, int beginPort, int endPort) throws CloudException, InternalException;

    /**
     * Indicates whether firewalls of the specified type (VLAN or flat network) support rules over the direction specified.
     * @param direction the direction of the traffic
     * @param permission the type of permission
     * @param inVlan whether or not you are looking for support for VLAN or flat network traffic
     * @return true if the cloud supports the creation of firewall rules in the direction specfied for the type of network specified
     * @throws CloudException an error occurred with the cloud provider while checking for support
     * @throws InternalException a local error occurred while checking for support
     * @deprecated use {@link FirewallCapabilities#supportsRules(Direction, Permission, boolean)}
     */
    @Deprecated
    public boolean supportsRules(@Nonnull Direction direction, @Nonnull Permission permission, boolean inVlan) throws CloudException, InternalException;

    /**
     * Indicates whether or not you can create new firewalls or whether you just have to live with what the cloud provider gave you.
     * @param inVlan whether this is for VLAN-specific firewalls
     * @return <code>true</code> if you can call {@link #create(String, String)} or {@link #createInVLAN(String, String, String)} to create a firewall
     * @throws CloudException an error occurred with the cloud provider while checking for support
     * @throws InternalException a local error occurred while checking for support
     * @deprecated use {@link FirewallCapabilities#supportsFirewallCreation(boolean)}
     */
    @Deprecated
    public boolean supportsFirewallCreation(boolean inVlan) throws CloudException, InternalException;

    /**
     *
     * @return true if the cloud requires a new firewall to be created with an initial set of rules
     * @throws CloudException an error occurred with the cloud provider while checking for support
     * @throws InternalException a local error occurred while checking for support
     * @deprecated use {@link FirewallCapabilities#requiresRulesOnCreation()}
     */
    public boolean requiresRulesOnCreation() throws CloudException, InternalException;

    /**
     * Indicates whether or not you can delete firewalls.
     * @return <code>true</code> if you can call {@link #delete(String)} to delete a firewall
     * @throws CloudException an error occurred with the cloud provider while checking for support
     * @throws InternalException a local error occurred while checking for support
     * @deprecated use {@link FirewallCapabilities#supportsFirewallDeletion()}
     */
    public boolean supportsFirewallDeletion() throws CloudException, InternalException;

    /**
     * Indicates whether or the sources you specify in your rules may be other firewalls (security group behavior).
     * @return true if the sources may be other firewalls
     * @throws CloudException an error occurred with the cloud provider while checking for support
     * @throws InternalException a local error occurred while checking for support
     * @deprecated Use {@link #listSupportedSourceTypes(boolean)}
     */
    public boolean supportsFirewallSources() throws CloudException, InternalException;

    /**
     * Updates meta-data for a firewall with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     * @param firewallId the firewall to update
     * @param tags the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void updateTags(@Nonnull String firewallId, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Updates meta-data for multiple firewalls with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     * @param firewallIds the firewalls to update
     * @param tags  the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void updateTags(@Nonnull String[] firewallIds, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Set meta-data for a firewall. Remove any tags that were not provided by the incoming tags, and add or
     * overwrite any new or pre-existing tags.
     *
     * @param firewallId the firewall to set
     * @param tags       the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void setTags( @Nonnull String firewallId, @Nonnull Tag... tags ) throws CloudException, InternalException;

    /**
     * Set meta-data for multiple firewalls. Remove any tags that were not provided by the incoming tags, and add or
     * overwrite any new or pre-existing tags.
     *
     * @param firewallIds the firewalls to set
     * @param tags        the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void setTags( @Nonnull String[] firewallIds, @Nonnull Tag... tags ) throws CloudException, InternalException;

}
