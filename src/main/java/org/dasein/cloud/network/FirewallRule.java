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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * <p>
 * Describes a specific firewall rule allowing access through the target firewall.
 * </p>
 * @author George Reese (george.reese@imaginary.com)
 * @version 2010.08
 * @version 2012.02 Added annotations
 * @version 2013.01 added full permission support and non-global destinations (Issue #14, Issue #11)
 * @since 2010.08
 */
@SuppressWarnings("UnusedDeclaration")
public class FirewallRule {
    static public @Nonnull FirewallRule getInstance(@Nullable String firewallRuleId, @Nonnull String providerFirewallId, @Nonnull String source, @Nonnull Protocol protocol, int port) {
        FirewallRule rule = new FirewallRule();

        rule.source = source;
        rule.destination = RuleDestination.getGlobal();
        rule.direction = Direction.INGRESS;
        rule.endPort = port;
        rule.firewallId = providerFirewallId;
        rule.permission = Permission.ALLOW;
        rule.protocol = protocol;
        rule.startPort = port;
        rule.providerRuleId = (firewallRuleId == null ? getRuleId(rule.firewallId, rule.source, rule.direction, rule.protocol, rule.permission, rule.destination, rule.startPort, rule.endPort): firewallRuleId);
        return rule;
    }

    static public @Nonnull FirewallRule getInstance(@Nullable String firewallRuleId, @Nonnull String providerFirewallId, @Nonnull String source, @Nonnull Protocol protocol, @Nonnull RuleDestination destination, int port) {
        FirewallRule rule = new FirewallRule();

        rule.source = source;
        rule.destination = destination;
        rule.direction = Direction.INGRESS;
        rule.endPort = port;
        rule.firewallId = providerFirewallId;
        rule.permission = Permission.ALLOW;
        rule.protocol = protocol;
        rule.startPort = port;
        rule.providerRuleId = (firewallRuleId == null ? getRuleId(rule.firewallId, rule.source, rule.direction, rule.protocol, rule.permission, rule.destination, rule.startPort, rule.endPort): firewallRuleId);
        return rule;
    }

    static public @Nonnull FirewallRule getInstance(@Nullable String firewallRuleId, @Nonnull String providerFirewallId, @Nonnull String source, @Nonnull Direction direction, @Nonnull Protocol protocol, @Nonnull Permission permission, @Nonnull RuleDestination destination, int port) {
        FirewallRule rule = new FirewallRule();

        rule.source = source;
        rule.destination = destination;
        rule.direction = direction;
        rule.endPort = port;
        rule.firewallId = providerFirewallId;
        rule.permission = permission;
        rule.protocol = protocol;
        rule.startPort = port;
        rule.providerRuleId = (firewallRuleId == null ? getRuleId(rule.firewallId, rule.source, rule.direction, rule.protocol, rule.permission, rule.destination, rule.startPort, rule.endPort): firewallRuleId);
        return rule;
    }

    static public @Nonnull FirewallRule getInstance(@Nullable String firewallRuleId, @Nonnull String providerFirewallId, @Nonnull String source, @Nonnull Direction direction, @Nonnull Protocol protocol, @Nonnull Permission permission, @Nonnull RuleDestination destination, int startPort, int endPort) {
        FirewallRule rule = new FirewallRule();

        rule.source = source;
        rule.destination = destination;
        rule.direction = direction;
        rule.endPort = endPort;
        rule.firewallId = providerFirewallId;
        rule.permission = permission;
        rule.protocol = protocol;
        rule.startPort = startPort;
        rule.providerRuleId = (firewallRuleId == null ? getRuleId(rule.firewallId, rule.source, rule.direction, rule.protocol, rule.permission, rule.destination, rule.startPort, rule.endPort): firewallRuleId);
        return rule;
    }

    static public @Nonnull String getRuleId(@Nonnull String providerFirewallId, @Nonnull String source, @Nonnull Direction direction, @Nonnull Protocol protocol, @Nonnull Permission permission, @Nullable RuleDestination destination, int startPort, int endPort) {
        if( destination == null ) {
            if( Permission.ALLOW.equals(permission) ) {
                return providerFirewallId + ":" + source + ":" + direction + ":" + protocol + ":" + startPort + ":" + endPort;
            }
            else {
                return Permission.DENY + ":" + providerFirewallId + ":" + source + ":" + direction + ":" + protocol + ":" + startPort + ":" + endPort;
            }
        }
        else {
            return permission + ":" + providerFirewallId + ":" + source + ":" + direction + ":" + protocol + ":" + startPort + ":" + endPort + ":" + destination;
        }
    }


    private RuleDestination destination;
    private Direction  direction;
    private int        endPort;
    private String     firewallId;
    private Permission permission;
    private Protocol   protocol;
    private String     providerRuleId;
    private String     source;
    private int        startPort;

    private FirewallRule() { }

    @Override
    public boolean equals(Object other) {
        if( other == null ) {
            return false;
        }
        if( other == this ) {
            return true;
        }
        //noinspection SimplifiableIfStatement
        if( !getClass().getName().equals(other.getClass().getName()) ) {
            return false;
        }
        return getProviderRuleId().equals(((FirewallRule)other).getProviderRuleId());
    }

    /**
     * The source IP address or range of addresses in CIDR format. IP addresses matching
     * this CIDR have positive access to any server protected by this firewall to the port
     * range specified over the protocol specified.
     * <p>NOTE: Because sources can now be other firewalls, this value may no longer return an actual CIDR</p>
     * @return the source CIDR for this rule
     * @deprecated Use {@link #getSource()}
     */
    public @Nullable String getCidr() {
        return source;
    }

    /**
     * @return the sub-destination behind this firewall that limits routing of traffic.
     */
    public @Nonnull RuleDestination getDestination() {
        if( destination == null ) {
            return RuleDestination.getGlobal();
        }
        return destination;
    }

    /**
     * @return the direction in which this rule operates
     */
    public @Nonnull Direction getDirection() {
        return (direction == null ? Direction.INGRESS : direction);
    }

    /**
     * @return the last port in the range of ports allowed by this rule
     */
    public int getEndPort() {
        return endPort;
    }
    
    /**
     * @return the unique provider ID for the firewall behind this rule
     */
    public @Nonnull String getFirewallId() {
        return firewallId;
    }

    /**
     * @return the permission behind this rule
     */
    public @Nonnull Permission getPermission() {
        return (permission == null ? Permission.ALLOW : permission);
    }

    /**
     * @return the network protocol to allow through to the target ports
     */
    public @Nonnull Protocol getProtocol() {
        return protocol;
    }

    /**
     * @return a unique ID that identifies this rule to the target cloud
     */
    public @Nonnull String getProviderRuleId() {
        return providerRuleId;
    }

    /**
     * @return the source firewall ID or CIDR for who is being allowed to send traffic via this rule
     */
    public @Nonnull String getSource() {
        return source;
    }

    /**
     * @return the first port in the range of ports allowed by this rule
     */
    public int getStartPort() {
        return startPort;
    }

    @Override
    public @Nonnull String toString() {
        return (direction + "/" + permission + ": " + source + "->" + protocol + ":" + destination + " [" + startPort + "-" + endPort + "]");
    }

}
