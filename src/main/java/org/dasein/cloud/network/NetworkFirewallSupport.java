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

import org.dasein.cloud.AccessControlledService;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.Requirement;
import org.dasein.cloud.ResourceStatus;
import org.dasein.cloud.Tag;
import org.dasein.cloud.identity.ServiceAction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

/**
 * Prescribes support network firewalls. Network firewalls are like regular firewalls, except they are associated with
 * networks or subnets instead of virtual machines.
 * <p>Created by George Reese: 2/4/13 8:40 AM</p>
 * @author George Reese
 * @since 2013.04
 * @version 2013.04 initial version (issue greese/dasein-cloud-aws/#8)
 * @version 2014.03 added support for constraints (issue #99)
 */
public interface NetworkFirewallSupport extends AccessControlledService {
    static public final ServiceAction ANY                  = new ServiceAction("NFW:ANY");

    static public final ServiceAction ASSOCIATE            = new ServiceAction("NFW:ASSOCIATE");
    static public final ServiceAction AUTHORIZE            = new ServiceAction("NFW:AUTHORIZE");
    static public final ServiceAction CREATE_FIREWALL      = new ServiceAction("NFW:CREATE_FIREWALL");
    static public final ServiceAction GET_FIREWALL         = new ServiceAction("NFW:GET_FIREWALL");
    static public final ServiceAction LIST_FIREWALL        = new ServiceAction("NFW:LIST_FIREWALL");
    static public final ServiceAction REMOVE_FIREWALL      = new ServiceAction("NFW:REMOVE_FIREWALL");
    static public final ServiceAction REVOKE               = new ServiceAction("NFW:REVOKE");

    /**
     * Associates the specified firewall with the specified subnet.
     * @param firewallId the firewall to be associated
     * @param withSubnetId the subnet with which the firewall is to be associated
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws OperationNotSupportedException you cannot associate network firewalls at the subnet level
     */
    public void associateWithSubnet(@Nonnull String firewallId, @Nonnull String withSubnetId) throws CloudException, InternalException;

    /**
     * Provides positive authorization for the specified firewall rule with the specified precedence. Any call to this method should
     * result in an override of any previous authorizations. For this method, the source endpoint is the source for the traffic and
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
     * @param precedence the precedence of this rule with respect to others (-1 means the default rule)
     * @return the provider ID of the new rule
     * @throws CloudException an error occurred with the cloud provider establishing the rule
     * @throws InternalException an error occurred locally trying to establish the rule
     * @throws OperationNotSupportedException the specified direction, target, or permission are not supported
     */
    public @Nonnull String authorize(@Nonnull String firewallId, @Nonnull Direction direction, @Nonnull Permission permission, @Nonnull RuleTarget sourceEndpoint, @Nonnull Protocol protocol, @Nonnull RuleTarget destinationEndpoint, int beginPort, int endPort, int precedence) throws CloudException, InternalException;

    /**
     * Provides positive authorization for the specified firewall rule with the specified precedence. Any call to this method should
     * result in an override of any previous authorizations. For this method, the source endpoint is the source for the traffic and
     * the destination endpoint is where the traffic terminates. For INGRESS rules, the destination endpoint will thus be
     * resources protected by this firewall and for EGRESS rules the destination endpoint is one or more external resources.
     * @param firewallId the unique, cloud-specific ID for the firewall being targeted by the new rule
     * @param options the firewall create options that define how the rule should be created
     * @return the provider ID of the new rule
     * @throws CloudException an error occurred with the cloud provider establishing the rule
     * @throws InternalException an error occurred locally trying to establish the rule
     * @throws OperationNotSupportedException the specified direction, target, or permission are not supported
     */
    public @Nonnull String authorize(@Nonnull String firewallId, @Nonnull FirewallRuleCreateOptions options) throws CloudException, InternalException;

    /**
     * Creates a new firewall based on the specified creation options.
     * @param options the options to be used in creating the firewall
     * @return the unique provider ID identifying the newly created firewall
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws UnsupportedOperationException this cloud doesn't support firewall creation using the specified options
     */
    public @Nonnull String createFirewall(@Nonnull FirewallCreateOptions options) throws InternalException, CloudException;

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
     * Provides access to meta-data about load balancer capabilities in the current region of this cloud.
     * @return a description of the features supported by this region of this cloud
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     */
    public @Nonnull NetworkFirewallCapabilities getCapabilities() throws CloudException, InternalException;

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
     * @deprecated use {@link NetworkFirewallCapabilities#getFirewallConstraintsForCloud()}
     */
    @Deprecated
    public @Nonnull FirewallConstraints getFirewallConstraintsForCloud() throws InternalException, CloudException;

    /**
     * Provides the cloud-specific terminology for the concept of a network firewall. For example, AWS calls a
     * network firewall a "network ACL".
     * @param locale the locale for which you should translate the firewall term
     * @return the translated term for network firewall with the target cloud provider
     * @deprecated use {@link NetworkFirewallCapabilities#getProviderTermForNetworkFirewall(java.util.Locale)}
     */
    @Deprecated
    public @Nonnull String getProviderTermForNetworkFirewall(@Nonnull Locale locale);

    /**
     * Indicates the degree to which authorizations expect precedence of rules to be established.
     * @return the degree to which precedence is required
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     * @deprecated use {@link NetworkFirewallCapabilities#identifyPrecedenceRequirement()}
     */
    @Deprecated
    public @Nonnull Requirement identifyPrecedenceRequirement() throws InternalException, CloudException;

    /**
     * Identifies whether or not the current account is subscribed to network firewall services in the current region.
     * @return true if the current account is subscribed to network firewall services for the current region
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
     * @deprecated use {@link NetworkFirewallCapabilities#isZeroPrecedenceHighest()}
     */
    @Deprecated
    public boolean isZeroPrecedenceHighest() throws InternalException, CloudException;

    /**
     * Lists the status for all network firewalls in the current provider context.
     * @return the status for all network firewalls in the current account
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public @Nonnull Iterable<ResourceStatus> listFirewallStatus() throws InternalException, CloudException;

    /**
     * Lists all network firewalls in the current provider context.
     * @return a list of all network firewalls in the current provider context
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public @Nonnull Iterable<Firewall> listFirewalls() throws InternalException, CloudException;

    /**
     * Provides the rules supported by the named firewall ordered in order of precedence with the most
     * important rule first. <em>Implementation note: natural sorting order for {@link FirewallRule} is low to
     * high. If this cloud has 0 as a low priority, you should reverse the natural sort!</em>
     * @param firewallId the unique ID of the firewall being queried
     * @return all rules supported by the target firewall
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public @Nonnull Iterable<FirewallRule> listRules(@Nonnull String firewallId) throws InternalException, CloudException;

    /**
     * Describes what kinds of destinations may be named. A cloud must support at least one, but may support more
     * than one.
     * @return a list of supported destinations
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     * @deprecated use {@link NetworkFirewallCapabilities#listSupportedDestinationTypes()}
     */
    @Deprecated
    public @Nonnull Iterable<RuleTargetType> listSupportedDestinationTypes() throws InternalException, CloudException;

    /**
     * Lists the supported traffic directions for rules for network firewalls.
     * @return a list of supported directions
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     * @deprecated use {@link NetworkFirewallCapabilities#listSupportedDirections()}
     */
    @Deprecated
    public @Nonnull Iterable<Direction> listSupportedDirections() throws InternalException, CloudException;

    /**
     * Lists the types of permissions that one may authorize for a network firewall rule.
     * @return a list of supported permissions
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     * @deprecated use {@link org.dasein.cloud.network.NetworkFirewallCapabilities#listSupportedPermissions()}
     */
    @Deprecated
    public @Nonnull Iterable<Permission> listSupportedPermissions() throws InternalException, CloudException;

    /**
     * Describes what kinds of source endpoints may be named. A cloud must support at least one, but may support more
     * than one.
     * @return a list of supported source endpoints
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     * @deprecated use {@link NetworkFirewallCapabilities#listSupportedSourceTypes()}
     */
    @Deprecated
    public @Nonnull Iterable<RuleTargetType> listSupportedSourceTypes() throws InternalException, CloudException;

    /**
     * Removes one or more network firewalls from the system.
     * @param firewallIds the unique IDs of the firewalls to be deleted
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public void removeFirewall(@Nonnull String ... firewallIds) throws InternalException, CloudException;

    /**
     * Removes meta-data from a network firewall. If tag values are set, their removal is dependent on underlying cloud
     * provider behavior. They may be removed only if the tag value matches or they may be removed regardless of the
     * value.
     * @param firewallId the firewall to update
     * @param tags the meta-data tags to remove
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void removeTags(@Nonnull String firewallId, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Removes meta-data from multiple network firewalls. If tag values are set, their removal is dependent on underlying cloud
     * provider behavior. They may be removed only if the tag value matches or they may be removed regardless of the
     * value.
     * @param firewallIds the network firewalls to update
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
     * Indicates whether or not you can create new network firewalls or whether you just have to live with what the cloud provider gave you.
     * @return <code>true</code> if you can call {@link #createFirewall(FirewallCreateOptions)}
     * @throws CloudException an error occurred with the cloud provider while checking for support
     * @throws InternalException a local error occurred while checking for support
     * @deprecated use {@link NetworkFirewallCapabilities#supportsNetworkFirewallCreation()}
     */
    @Deprecated
    public boolean supportsNetworkFirewallCreation() throws CloudException, InternalException;

    /**
     * Updates meta-data for a network firewall with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     * @param firewallId the network firewall to update
     * @param tags the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void updateTags(@Nonnull String firewallId, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Updates meta-data for multiple network firewalls with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     * @param firewallIds the network firewalls to update
     * @param tags  the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void updateTags(@Nonnull String[] firewallIds, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Set meta-data for a network firewall. Remove any tags that were not provided by the incoming tags, and add or
     * overwrite any new or pre-existing tags.
     *
     * @param firewallId the network firewalls to set
     * @param tags       the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void setTags( @Nonnull String firewallId, @Nonnull Tag... tags ) throws CloudException, InternalException;

    /**
     * Set meta-data for multiple network firewalls. Remove any tags that were not provided by the incoming tags, and add or
     * overwrite any new or pre-existing tags.
     *
     * @param firewallIds the networks firewalls to set
     * @param tags        the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void setTags( @Nonnull String[] firewallIds, @Nonnull Tag... tags ) throws CloudException, InternalException;

}
