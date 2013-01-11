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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * <p>
 * Describes a specific firewall rule allowing access through the target firewall. Note that for egress rules, the "source"
 * is actually the target and the "target" is the source.
 * </p>
 * @author George Reese (george.reese@imaginary.com)
 * @version 2010.08
 * @version 2012.02 Added annotations
 * @version 2013.01 added full permission support and non-global destinations (Issue #14, Issue #11)
 * @version 2013.02 added precedence
 * @since 2010.08
 */
@SuppressWarnings("UnusedDeclaration")
public class FirewallRule implements Comparable<FirewallRule> {
    static public @Nonnull FirewallRule getInstance(@Nullable String firewallRuleId, @Nonnull String providerFirewallId, @Nonnull String source, @Nonnull Protocol protocol, int port) {
        FirewallRule rule = new FirewallRule();

        rule.source = source;
        rule.target = RuleTarget.getGlobal();
        rule.direction = Direction.INGRESS;
        rule.endPort = port;
        rule.firewallId = providerFirewallId;
        rule.permission = Permission.ALLOW;
        rule.protocol = protocol;
        rule.startPort = port;
        rule.precedence = 0;
        rule.providerRuleId = (firewallRuleId == null ? getRuleId(rule.firewallId, rule.source, rule.direction, rule.protocol, rule.permission, rule.target, rule.startPort, rule.endPort): firewallRuleId);
        return rule;
    }

    static public @Nonnull FirewallRule getInstance(@Nullable String firewallRuleId, @Nonnull String providerFirewallId, @Nonnull String source, @Nonnull Protocol protocol, @Nonnull RuleTarget target, int port) {
        FirewallRule rule = new FirewallRule();

        rule.source = source;
        rule.target = target;
        rule.direction = Direction.INGRESS;
        rule.endPort = port;
        rule.firewallId = providerFirewallId;
        rule.permission = Permission.ALLOW;
        rule.protocol = protocol;
        rule.startPort = port;
        rule.precedence = 0;
        rule.providerRuleId = (firewallRuleId == null ? getRuleId(rule.firewallId, rule.source, rule.direction, rule.protocol, rule.permission, rule.target, rule.startPort, rule.endPort): firewallRuleId);
        return rule;
    }

    static public @Nonnull FirewallRule getInstance(@Nullable String firewallRuleId, @Nonnull String providerFirewallId, @Nonnull String source, @Nonnull Direction direction, @Nonnull Protocol protocol, @Nonnull Permission permission, @Nonnull RuleTarget target, int port) {
        FirewallRule rule = new FirewallRule();

        rule.source = source;
        rule.target = target;
        rule.direction = direction;
        rule.endPort = port;
        rule.firewallId = providerFirewallId;
        rule.permission = permission;
        rule.protocol = protocol;
        rule.startPort = port;
        rule.precedence = 0;
        rule.providerRuleId = (firewallRuleId == null ? getRuleId(rule.firewallId, rule.source, rule.direction, rule.protocol, rule.permission, rule.target, rule.startPort, rule.endPort): firewallRuleId);
        return rule;
    }

    static public @Nonnull FirewallRule getInstance(@Nullable String firewallRuleId, @Nonnull String providerFirewallId, @Nonnull String source, @Nonnull Direction direction, @Nonnull Protocol protocol, @Nonnull Permission permission, @Nonnull RuleTarget target, int startPort, int endPort) {
        FirewallRule rule = new FirewallRule();

        rule.source = source;
        rule.target = target;
        rule.direction = direction;
        rule.endPort = endPort;
        rule.firewallId = providerFirewallId;
        rule.permission = permission;
        rule.protocol = protocol;
        rule.precedence = 0;
        rule.startPort = startPort;
        rule.providerRuleId = (firewallRuleId == null ? getRuleId(rule.firewallId, rule.source, rule.direction, rule.protocol, rule.permission, rule.target, rule.startPort, rule.endPort): firewallRuleId);
        return rule;
    }

    static public @Nonnull String getRuleId(@Nonnull String providerFirewallId, @Nonnull String source, @Nonnull Direction direction, @Nonnull Protocol protocol, @Nonnull Permission permission, @Nullable RuleTarget target, int startPort, int endPort) {
        if( target == null ) {
            if( Permission.ALLOW.equals(permission) ) {
                return providerFirewallId + ":" + source + ":" + direction + ":" + protocol + ":" + startPort + ":" + endPort;
            }
            else {
                return Permission.DENY + ":" + providerFirewallId + ":" + source + ":" + direction + ":" + protocol + ":" + startPort + ":" + endPort;
            }
        }
        else {
            return permission + ":" + providerFirewallId + ":" + source + ":" + direction + ":" + protocol + ":" + startPort + ":" + endPort + ":" + target;
        }
    }

    static public @Nullable FirewallRule parseId(@Nonnull String id) {
        String[] parts = id.split(":");

        if( parts.length < 2 ) {
            return null;
        }
        Permission permission = Permission.ALLOW;
        int i = 0;

        if( parts[i].equalsIgnoreCase("DENY") ) {
            permission = Permission.DENY;
            i++;
        }
        else if( parts[i].equalsIgnoreCase("ALLOW") ) {
            i++;
        }
        if( parts.length < i+1 ) {
            return null;
        }

        String providerFirewallId = parts[i++];

        if( parts.length < i+1 ) {
            return null;
        }

        String source = parts[i++];

        if( parts.length < i+1 ) {
            return null;
        }

        Direction direction;

        try {
            direction = Direction.valueOf(parts[i++].toUpperCase());
        }
        catch( Throwable ignore ) {
            return null;
        }
        if( parts.length < i+1 ) {
            return null;
        }

        Protocol protocol;

        try {
            protocol = Protocol.valueOf(parts[i++].toUpperCase());
        }
        catch( Throwable ignore ) {
            return null;
        }
        if( parts.length < i+1 ) {
            return null;
        }

        int startPort;

        try {
            startPort = Integer.parseInt(parts[i++]);
        }
        catch( NumberFormatException ignore ) {
            return null;
        }
        if( parts.length < i+1 ) {
            return null;
        }

        int endPort;

        try {
            endPort = Integer.parseInt(parts[i++]);
        }
        catch( NumberFormatException ignore ) {
            return null;
        }

        RuleTarget target;

        if( parts.length < i+1 ) {
            target = RuleTarget.getGlobal();
        }
        else {
            String tname = parts[i++];

            if( parts.length < i+1 ) {
                target = RuleTarget.getGlobal();
            }
            else {
                try {
                    RuleTargetType t = RuleTargetType.valueOf(tname);

                    switch( t ) {
                        case GLOBAL: target = RuleTarget.getGlobal(); break;
                        case VM: target = RuleTarget.getVirtualMachine(parts[i]); break;
                        case VLAN: target = RuleTarget.getVlan(parts[i]); break;
                        case CIDR: target = RuleTarget.getCIDR(parts[i]); break;
                        default: return null;
                    }
                }
                catch( Throwable ignore ) {
                    target = RuleTarget.getGlobal();
                }
            }
        }
        return FirewallRule.getInstance(null, providerFirewallId, source, direction, protocol, permission, target, startPort, endPort);
    }

    private RuleTarget target;
    private Direction  direction;
    private int        endPort;
    private String     firewallId;
    private Permission permission;
    private int        precedence;
    private Protocol   protocol;
    private String     providerRuleId;
    private String     source;
    private int        startPort;

    private FirewallRule() { }

    @Override
    public int compareTo(FirewallRule other) {
        if( other == null ) {
            return -1;
        }
        if( other == this ) {
            return 0;
        }
        if( direction.equals(other.direction) ) {
            if( precedence == other.precedence ) {
                return providerRuleId.compareTo(other.providerRuleId);
            }
            return (new Integer(precedence)).compareTo(precedence);
        }
        return direction.compareTo(other.direction);
    }

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
     * @return the sub-target behind this firewall that limits routing of traffic.
     */
    public @Nonnull
    RuleTarget getTarget() {
        if( target == null ) {
            return RuleTarget.getGlobal();
        }
        return target;
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
     * @return the precedence level of this firewall rule
     */
    public @Nonnegative int getPrecedence() {
        return precedence;
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
        return (direction + "/" + permission + ": " + source + "->" + protocol + ":" + target + " [" + startPort + "-" + endPort + "]");
    }

    /**
     * Indicates a precedence order for this rule
     * @param precedence the numeric precedence
     * @return this
     */
    public @Nonnull FirewallRule withPreference(@Nonnegative int precedence) {
        this.precedence = precedence;
        return this;
    }
}
