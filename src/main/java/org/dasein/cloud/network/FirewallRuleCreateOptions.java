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

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The options to be used in authorizing a new firewall rule.
 * <p>Created by George Reese: 2/3/14 10:48 AM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 initial version
 */
public class FirewallRuleCreateOptions {
    /**
     * Constructs the options for creating a new firewall rule with a default precedence of 0.
     * @param direction the direction of the traffic governing the rule
     * @param permission ALLOW or DENY
     * @param sourceEndpoint the source endpoint for this rule (null == global for owner firewall)
     * @param protocol the protocol (tcp/udp/icmp) supported by this rule
     * @param destinationEndpoint the destination endpoint to specify for this rule (null == global for owner firewall)
     * @param portRangeStart the beginning of the port range to be allowed, inclusive
     * @param portRangeEnd the end of the port range to be allowed, inclusive
     * @return a set of options that can be used in the firewall rule authorization process
     */

    static public FirewallRuleCreateOptions getInstance(@Nonnull Direction direction, @Nonnull Permission permission, @Nullable RuleTarget sourceEndpoint, @Nonnull Protocol protocol, @Nullable RuleTarget destinationEndpoint, int portRangeStart, int portRangeEnd)  {
        FirewallRuleCreateOptions options = new FirewallRuleCreateOptions();

        options.direction = direction;
        options.permission = permission;
        options.sourceEndpoint = sourceEndpoint;
        options.protocol = protocol;
        options.destinationEndpoint = destinationEndpoint;
        options.portRangeStart = portRangeStart;
        options.portRangeEnd = portRangeEnd;
        options.precedence = 0;
        return options;
    }

    /**
     * Constructs the options for creating a new firewall rule with a specific precedence.
     * @param direction the direction of the traffic governing the rule
     * @param permission ALLOW or DENY
     * @param sourceEndpoint the source endpoint for this rule (null == global for owner firewall)
     * @param protocol the protocol (tcp/udp/icmp) supported by this rule
     * @param destinationEndpoint the destination endpoint to specify for this rule  (null == global for owner firewall)
     * @param portRangeStart the beginning of the port range to be allowed, inclusive
     * @param portRangeEnd the end of the port range to be allowed, inclusive
     * @param precedence the precedence of this rule with respect to others
     * @return a set of options that can be used in the firewall rule authorization process
     */
    static public FirewallRuleCreateOptions getInstance(@Nonnull Direction direction, @Nonnull Permission permission, @Nullable RuleTarget sourceEndpoint, @Nonnull Protocol protocol, @Nullable RuleTarget destinationEndpoint, int portRangeStart, int portRangeEnd, @Nonnegative int precedence)  {
        FirewallRuleCreateOptions options = new FirewallRuleCreateOptions();

        options.direction = direction;
        options.permission = permission;
        options.sourceEndpoint = sourceEndpoint;
        options.protocol = protocol;
        options.destinationEndpoint = destinationEndpoint;
        options.portRangeStart = portRangeStart;
        options.portRangeEnd = portRangeEnd;
        options.precedence = precedence;
        return options;
    }

    private RuleTarget destinationEndpoint;
    private Direction  direction;
    private Permission permission;
    private int        portRangeEnd;
    private int        portRangeStart;
    private int        precedence;
    private Protocol   protocol;
    private RuleTarget sourceEndpoint;

    /**
     * Creates a firewall rule to be established in the target cloud, associated with the specified firewall.
     * @param provider the cloud provider in which the firewall rule will be created
     * @param forFirewallId the ID of the firewall rule with which this rule will be associated
     * @return the ID for the newly created firewall rule
     * @throws CloudException an error occurred with the cloud provider while authorizing the rule creation
     * @throws InternalException an error occurred within Dasein Cloud while processing the request
     */
    public @Nonnull String build(@Nonnull CloudProvider provider, @Nonnull String forFirewallId) throws CloudException, InternalException {
        NetworkServices services = provider.getNetworkServices();

        if( services == null ) {
            throw new OperationNotSupportedException(provider.getCloudName() + " does not support network services");
        }
        FirewallSupport support = services.getFirewallSupport();

        if( support == null ) {
            throw new OperationNotSupportedException(provider.getCloudName() + " does not have support for firewalls");
        }
        if( sourceEndpoint == null ) {
            sourceEndpoint = RuleTarget.getGlobal(forFirewallId);
        }
        if( destinationEndpoint == null ) {
            destinationEndpoint = RuleTarget.getGlobal(forFirewallId);
        }
        return support.authorize(forFirewallId, this);
    }

    /**
     * If null, this value represents a global rule target associated with the owning firewall
     * @return the destination rule target for traffic matching the rule to be created
     */
    public @Nullable RuleTarget getDestinationEndpoint() {
        return destinationEndpoint;
    }

    /**
     * @return the direction in which the rule to be created operates
     */
    public @Nonnull Direction getDirection() {
        return direction;
    }

    /**
     * @return the permission behind the rule to be created
     */
    public @Nonnull Permission getPermission() {
        return permission;
    }

    /**
     * @return the last port in the range of ports allowed by the rule to be authorized
     */
    public int getPortRangeEnd() {
        return portRangeEnd;
    }

    /**
     * @return the first port in the range of ports allowed by the rule to be authorized
     */
    public int getPortRangeStart() {
        return portRangeStart;
    }

    /**
     * @return the precedence level of the firewall rule to be authorized
     */
    public @Nonnegative int getPrecedence() {
        return precedence;
    }

    /**
     * @return the protocol behind the rule to be authorized
     */
    public @Nonnull Protocol getProtocol() {
        return protocol;
    }

    /**
     * If null, this value represents a global rule target associated with the owning firewall
     * @return the source of the traffic associated with the rule to be authorized
     */
    public @Nullable RuleTarget getSourceEndpoint() {
        return sourceEndpoint;
    }

    @Override
    public @Nonnull String toString() {
        return (direction + " (" + precedence + ")/" + permission + ": " + sourceEndpoint + "->" + protocol + ":" + destinationEndpoint + " [" + portRangeStart + "-" + portRangeEnd + "]");
    }
}
