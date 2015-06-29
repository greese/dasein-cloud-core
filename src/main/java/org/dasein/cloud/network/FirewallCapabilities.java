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
import org.dasein.cloud.util.NamingConstraints;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;

/**
 * Describes the capabilities of a region within a cloud for a specific account.
 * <p>Created by George Reese: 2/27/14 3:01 PM</p>
 * @author George Reese
 * @version 2014.03 initial version
 * @since 2014.03
 */
public interface FirewallCapabilities extends Capabilities{
    /**
     * Fetches the constraints for firewalls in this cloud. A constraint is a field that all rules
     * associated with a firewall must share. For example, a firewall constrained on
     * {@link FirewallConstraints.Constraint#PROTOCOL} requires all rules associated with it to share
     * the same protocol.
     * @return the firewall constraints for this cloud
     * @throws InternalException
     *          an internal error occurred assembling the cloud firewall constraints
     * @throws CloudException
     *          an error occurred fetching constraint data from the cloud
     */
    public @Nonnull FirewallConstraints getFirewallConstraintsForCloud() throws InternalException, CloudException;

    /**
     * Provides the firewall terminology for the concept of a firewall. For example, AWS calls a
     * firewall a "security group".
     * @param locale the locale for which you should translate the firewall term
     * @return the translated term for firewall with the target cloud provider
     */
    public @Nonnull String getProviderTermForFirewall(@Nonnull Locale locale);

    /**
     * Returns the visible scope of the Firewall or null if not applicable for the specific cloud
     * @return the Visible Scope of the Firewall
     */
    public @Nullable VisibleScope getFirewallVisibleScope();

    /**
     * Indicates the degree to which authorizations expect precedence of rules to be established.
     * @param inVlan whether or not you are checking for VLAN firewalls or regular ones
     * @return the degree to which precedence is required
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException    an error occurred with the cloud provider while performing the operation
     */
    public @Nonnull Requirement identifyPrecedenceRequirement(boolean inVlan) throws InternalException, CloudException;

    /**
     * Indicates whether the highest precedence comes from low numbers. If true, 0 is the highest precedence a rule
     * can have. If false, 0 is the lowest precedence.
     * @return true if 0 is the highest precedence for a rule
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException    an error occurred with the cloud provider while performing the operation
     */
    public boolean isZeroPrecedenceHighest() throws InternalException, CloudException;

    /**
     * Describes what kinds of destinations may be named. A cloud must support at least one, but may support more
     * than one.
     * @param inVlan whether or not you are testing capabilities for VLAN firewalls
     * @return a list of supported destinations
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException    an error occurred with the cloud provider while performing the operation
     * @deprecated use {@link FirewallCapabilities#listSupportedDestinationTypes(boolean, Direction)}
     */
    @Deprecated
    public @Nonnull Iterable<RuleTargetType> listSupportedDestinationTypes(boolean inVlan) throws InternalException, CloudException;

    /**
     * Describes what kinds of destinations may be named. A cloud must support at least one, but may support more
     * than one.
     * @param inVlan whether or not you are testing capabilities for VLAN firewalls
     * @param direction indicates whether the rule is ingress or egress
     * @return a list of supported destinations
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException    an error occurred with the cloud provider while performing the operation
     */
    public @Nonnull Iterable<RuleTargetType> listSupportedDestinationTypes(boolean inVlan, Direction direction) throws InternalException, CloudException;

    /**
     * Lists the supported traffic directions for rules behind this kind of firewall.
     * @param inVlan whether or not you are interested in VLAN firewalls
     * @return a list of supported directions
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException    an error occurred with the cloud provider while performing the operation
     */
    public @Nonnull Iterable<Direction> listSupportedDirections(boolean inVlan) throws InternalException, CloudException;

    /**
     * Lists the types of permissions that one may authorize for a firewall rule.
     * @param inVlan whether or not you are interested in VLAN firewalls or general ones
     * @return a list of supported permissions
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException    an error occurred with the cloud provider while performing the operation
     */
    public @Nonnull Iterable<Permission> listSupportedPermissions(boolean inVlan) throws InternalException, CloudException;

    /**
     * Lists the protocols that any firewall rules are allowed to use.
     * @param inVlan whether or not you are testing capabilities for VLAN firewalls
     * @return a list of supported protocols
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException    an error occurred with the cloud provider while performing the operation
     */
    public @Nonnull Iterable<Protocol> listSupportedProtocols(boolean inVlan) throws InternalException, CloudException;

    /**
     * Describes what kinds of source endpoints may be named. A cloud must support at least one, but may support more
     * than one.
     * @param inVlan whether or not you are testing capabilities for VLAN firewalls
     * @return a list of supported source endpoints
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException    an error occurred with the cloud provider while performing the operation
     * @deprecated use {@link FirewallCapabilities#listSupportedDestinationTypes(boolean, Direction)}
     */
    @Deprecated
    public @Nonnull Iterable<RuleTargetType> listSupportedSourceTypes(boolean inVlan) throws InternalException, CloudException;

    /**
     * Describes what kinds of source endpoints may be named. A cloud must support at least one, but may support more
     * than one.
     * @param inVlan whether or not you are testing capabilities for VLAN firewalls
     * @param direction indicates whether the rule is ingress or egress
     * @return a list of supported source endpoints
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException    an error occurred with the cloud provider while performing the operation
     */
    public @Nonnull Iterable<RuleTargetType> listSupportedSourceTypes(boolean inVlan, Direction direction) throws InternalException, CloudException;

    /**
     * @return true if the cloud requires a new firewall to be created with an initial set of rules
     * @throws CloudException    an error occurred with the cloud provider while checking for support
     * @throws InternalException a local error occurred while checking for support
     */
    public boolean requiresRulesOnCreation() throws CloudException, InternalException;

    /**
     * Indicates whether a firewall can exist independantly of a vlan or whether a vlan is required for it to exist
     * @return Requirement.REQUIRED if the firewall is dependant upon a vlan, Requirement.NONE if it is independant
     * @throws CloudException an error occurred processing the request in the cloud
     * @throws InternalException an internal error occurred inside the Dasein Cloud implementation
     */
    public @Nonnull Requirement requiresVLAN() throws CloudException, InternalException;

    /**
     * Indicates whether firewalls of the specified type (VLAN or flat network) support rules over the direction
     * specified.
     * @param direction  the direction of the traffic
     * @param permission the type of permission
     * @param inVlan     whether or not you are looking for support for VLAN or flat network traffic
     * @return true if the cloud supports the creation of firewall rules in the direction specfied for the type of
     *         network specified
     * @throws CloudException    an error occurred with the cloud provider while checking for support
     * @throws InternalException a local error occurred while checking for support
     */
    public boolean supportsRules(@Nonnull Direction direction, @Nonnull Permission permission, boolean inVlan) throws CloudException, InternalException;

    /**
     * Indicates whether or not you can create new firewalls or whether you just have to live with what the cloud
     * provider gave you.
     * @param inVlan whether this is for VLAN-specific firewalls
     * @return <code>true</code> if you can call {@link FirewallSupport#create(String, String)} or {@link
     *         FirewallSupport#createInVLAN(String, String, String)} to create a firewall
     * @throws CloudException    an error occurred with the cloud provider while checking for support
     * @throws InternalException a local error occurred while checking for support
     */
    public boolean supportsFirewallCreation(boolean inVlan) throws CloudException, InternalException;

    /**
     * Indicates whether or not you can delete firewalls.
     * @return <code>true</code> if you can call {@link FirewallSupport#delete(String)} to delete a firewall
     * @throws CloudException    an error occurred with the cloud provider while checking for support
     * @throws InternalException a local error occurred while checking for support
     */
    public boolean supportsFirewallDeletion() throws CloudException, InternalException;

    /**
     * Identifies the naming conventions that constrain how firewalls may be named (friendly name) in this cloud.
     * @return naming conventions that constrain firewall naming
     * @throws CloudException an error occurred querying the cloud for naming constraints
     * @throws InternalException an error occurred assembling the naming constraints object
     */
    public @Nonnull NamingConstraints getFirewallNamingConstraints() throws CloudException, InternalException;
}
