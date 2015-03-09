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
public interface NetworkFirewallCapabilities extends Capabilities{
    /**
     * Fetches the constraints for firewalls in this cloud. A constraint is a field that all rules
     * associated with a firewall must share. For example, a firewall constrained on
     * {@link FirewallConstraints.Constraint#PROTOCOL} requires all rules associated with it to share
     * the same protocol.
     * @return the firewall constraints for this cloud
     * @throws org.dasein.cloud.InternalException an internal error occurred assembling the cloud firewall constraints
     * @throws org.dasein.cloud.CloudException an error occurred fetching constraint data from the cloud
     */
    public @Nonnull FirewallConstraints getFirewallConstraintsForCloud() throws InternalException, CloudException;

    /**
     * Provides the cloud-specific terminology for the concept of a network firewall. For example, AWS calls a
     * network firewall a "network ACL".
     * @param locale the locale for which you should translate the firewall term
     * @return the translated term for network firewall with the target cloud provider
     */
    public @Nonnull String getProviderTermForNetworkFirewall(@Nonnull Locale locale);

    /**
     * Indicates the degree to which authorizations expect precedence of rules to be established.
     * @return the degree to which precedence is required
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public @Nonnull Requirement identifyPrecedenceRequirement() throws InternalException, CloudException;

    /**
     * Indicates whether the highest precedence comes from low numbers. If true, 0 is the highest precedence a rule
     * can have. If false, 0 is the lowest precedence.
     * @return true if 0 is the highest precedence for a rule
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public boolean isZeroPrecedenceHighest() throws InternalException, CloudException;

    /**
     * Describes what kinds of destinations may be named. A cloud must support at least one, but may support more
     * than one.
     * @return a list of supported destinations
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public @Nonnull Iterable<RuleTargetType> listSupportedDestinationTypes() throws InternalException, CloudException;

    /**
     * Lists the supported traffic directions for rules for network firewalls.
     * @return a list of supported directions
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public @Nonnull Iterable<Direction> listSupportedDirections() throws InternalException, CloudException;

    /**
     * Lists the types of permissions that one may authorize for a network firewall rule.
     * @return a list of supported permissions
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public @Nonnull Iterable<Permission> listSupportedPermissions() throws InternalException, CloudException;

    /**
     * Describes what kinds of source endpoints may be named. A cloud must support at least one, but may support more
     * than one.
     * @return a list of supported source endpoints
     * @throws InternalException an error occurred locally independent of any events in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing the operation
     */
    public @Nonnull Iterable<RuleTargetType> listSupportedSourceTypes() throws InternalException, CloudException;

    /**
     * Indicates whether or not you can create new network firewalls or whether you just have to live with what the cloud provider gave you.
     * @return <code>true</code> if you can call {@link NetworkFirewallSupport#createFirewall(FirewallCreateOptions)}
     * @throws CloudException an error occurred with the cloud provider while checking for support
     * @throws InternalException a local error occurred while checking for support
     */
    public boolean supportsNetworkFirewallCreation() throws CloudException, InternalException;
}
