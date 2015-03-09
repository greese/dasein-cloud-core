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
 * @version 2013.04 added support for proper sorting of -1 rules to the end (issue greese/dasein-cloud-aws/#8)
 * @since 2010.08
 */
@SuppressWarnings("UnusedDeclaration")
public class FirewallRule implements Comparable<FirewallRule> {
    static private @Nonnull RuleTarget toSourceDestination(@Nonnull String source) {
        String[] parts = source.split("\\.");

        if( parts.length == 4 ) {
            int idx = parts[3].indexOf("/");
            boolean num = true;

            if( idx > 0 ) {
                parts[3] = parts[3].substring(0,idx);
            }
            for( String p : parts ) {
                try {
                    //noinspection ResultOfMethodCallIgnored
                    Integer.parseInt(p);
                }
                catch( NumberFormatException ignore ) {
                    num = false;
                    break;
                }
            }
            if( num ) {
                return RuleTarget.getCIDR(source);
            }
        }
        return RuleTarget.getGlobal(source);
    }

    /**
     * Constructs an INGRESS/ALLOW firewall rule from the specified source to all instances associated with this firewall.
     * @param firewallRuleId the provider ID for this rule (or null if the provider does not identify its rules)
     * @param providerFirewallId the unique ID of the firewall with which this rule is associated
     * @param source a CIDR indicating the set of IP addresses from which the traffic originates
     * @param protocol the protocol over which the communication is allowed
     * @param port the port for the communication
     * @return a firewall rule matching the parameters specified
     * @deprecated Use {@link #getInstance(String, String, RuleTarget, Direction, Protocol, Permission, RuleTarget, int, int)}
     */
    static public @Nonnull FirewallRule getInstance(@Nullable String firewallRuleId, @Nonnull String providerFirewallId, @Nonnull String source, @Nonnull Protocol protocol, int port) {
        FirewallRule rule = new FirewallRule();

        rule.sourceEndpoint = toSourceDestination(source);
        rule.destinationEndpoint = RuleTarget.getGlobal(providerFirewallId);
        rule.direction = Direction.INGRESS;
        rule.endPort = port;
        rule.firewallId = providerFirewallId;
        rule.permission = Permission.ALLOW;
        rule.protocol = protocol;
        rule.startPort = port;
        rule.precedence = 0;
        rule.providerRuleId = (firewallRuleId == null ? getRuleId(rule.firewallId, rule.sourceEndpoint, rule.direction, rule.protocol, rule.permission, rule.destinationEndpoint, rule.startPort, rule.endPort): firewallRuleId);
        return rule;
    }

    /**
     * Constructs an INGRESS/ALLOW firewall rule from the specified source to the instances matching the specified target.
     * @param firewallRuleId the provider ID for this rule (or null if the provider does not identify its rules)
     * @param providerFirewallId the unique ID of the firewall with which this rule is associated
     * @param source a CIDR indicating the set of IP addresses from which the traffic originates
     * @param protocol the protocol over which the communication is allowed
     * @param target the type of resources that may be targeted by the inbound traffic
     * @param port the port for the communication
     * @return a firewall rule matching the parameters specified
     * @deprecated Use {@link #getInstance(String, String, RuleTarget, Direction, Protocol, Permission, RuleTarget, int, int)}
     */
    static public @Nonnull FirewallRule getInstance(@Nullable String firewallRuleId, @Nonnull String providerFirewallId, @Nonnull String source, @Nonnull Protocol protocol, @Nonnull RuleTarget target, int port) {
        FirewallRule rule = new FirewallRule();

        rule.sourceEndpoint = toSourceDestination(source);
        rule.destinationEndpoint = target;
        rule.direction = Direction.INGRESS;
        rule.endPort = port;
        rule.firewallId = providerFirewallId;
        rule.permission = Permission.ALLOW;
        rule.protocol = protocol;
        rule.startPort = port;
        rule.precedence = 0;
        rule.providerRuleId = (firewallRuleId == null ? getRuleId(rule.firewallId, rule.sourceEndpoint, rule.direction, rule.protocol, rule.permission, rule.destinationEndpoint, rule.startPort, rule.endPort): firewallRuleId);
        return rule;
    }

    /**
     * Constructs a firewall rule originating from/to the specified source CIDR and traveling in the specified direction to/from the specified target resources.
     * Due to some historical oddities, what "source" and "target" mean are dependent on the direction of travel. The source
     * is ALWAYS outside the firewall, and the target is ALWAYS behind the firewall. Therefore, under an EGRESS rule, the
     * source is the target and the target is the source. This reversal is not true for non-deprecated methods.
     * @param firewallRuleId the provider ID for this rule (or null if the provider does not identify its rules)
     * @param providerFirewallId the unique ID of the firewall with which this rule is associated
     * @param source a CIDR indicating the set of IP addresses from which the traffic originates
     * @param direction the direction of the network traffic being allowed/disallowed
     * @param protocol the protocol over which the communication is allowed
     * @param permission whether or not the traffic is being allowed/denied
     * @param target the type of resources that may be targeted by the inbound traffic
     * @param port the port for the communication
     * @return a firewall rule matching the parameters specified
     * @deprecated Use {@link #getInstance(String, String, RuleTarget, Direction, Protocol, Permission, RuleTarget, int, int)}
     */
    static public @Nonnull FirewallRule getInstance(@Nullable String firewallRuleId, @Nonnull String providerFirewallId, @Nonnull String source, @Nonnull Direction direction, @Nonnull Protocol protocol, @Nonnull Permission permission, @Nonnull RuleTarget target, int port) {
        FirewallRule rule = new FirewallRule();

        if( direction.equals(Direction.INGRESS) ) {
            rule.sourceEndpoint = toSourceDestination(source);
            rule.destinationEndpoint = target;
        }
        else {
            rule.sourceEndpoint = target;
            rule.destinationEndpoint = toSourceDestination(source);
        }
        rule.direction = direction;
        rule.endPort = port;
        rule.firewallId = providerFirewallId;
        rule.permission = permission;
        rule.protocol = protocol;
        rule.startPort = port;
        rule.precedence = 0;
        rule.providerRuleId = (firewallRuleId == null ? getRuleId(rule.firewallId, rule.sourceEndpoint, rule.direction, rule.protocol, rule.permission, rule.destinationEndpoint, rule.startPort, rule.endPort): firewallRuleId);
        return rule;
    }

    /**
     * Constructs a firewall rule originating from/to the specified source CIDR and traveling in the specified direction to/from the specified target resources.
     * Due to some historical oddities, what "source" and "target" mean are dependent on the direction of travel. The source
     * is ALWAYS outside the firewall, and the target is ALWAYS behind the firewall. Therefore, under an EGRESS rule, the
     * source is the target and the target is the source. This reversal is not true for non-deprecated methods.
     * @param firewallRuleId the provider ID for this rule (or null if the provider does not identify its rules)
     * @param providerFirewallId the unique ID of the firewall with which this rule is associated
     * @param source a CIDR indicating the set of IP addresses from which the traffic originates
     * @param direction the direction of the network traffic being allowed/disallowed
     * @param protocol the protocol over which the communication is allowed
     * @param permission whether or not the traffic is being allowed/denied
     * @param target the type of resources that may be targeted by the inbound traffic
     * @param startPort the end of the port range for the communication
     * @param endPort the end of the port range for the communication
     * @return a firewall rule matching the parameters specified
     * @deprecated Use {@link #getInstance(String, String, RuleTarget, Direction, Protocol, Permission, RuleTarget, int, int)}
     */
    static public @Nonnull FirewallRule getInstance(@Nullable String firewallRuleId, @Nonnull String providerFirewallId, @Nonnull String source, @Nonnull Direction direction, @Nonnull Protocol protocol, @Nonnull Permission permission, @Nonnull RuleTarget target, int startPort, int endPort) {
        FirewallRule rule = new FirewallRule();

        if( direction.equals(Direction.INGRESS) ) {
            rule.sourceEndpoint = toSourceDestination(source);
            rule.destinationEndpoint = target;
        }
        else {
            rule.sourceEndpoint = target;
            rule.destinationEndpoint = toSourceDestination(source);
        }
        rule.direction = direction;
        rule.endPort = endPort;
        rule.firewallId = providerFirewallId;
        rule.permission = permission;
        rule.protocol = protocol;
        rule.precedence = 0;
        rule.startPort = startPort;
        rule.providerRuleId = (firewallRuleId == null ? getRuleId(rule.firewallId, rule.sourceEndpoint, rule.direction, rule.protocol, rule.permission, rule.destinationEndpoint, rule.startPort, rule.endPort): firewallRuleId);
        return rule;
    }

    /**
     * Constructs a firewall rule matching traffic that originates from the specified source endpoint going to the
     * specified destination endpoint. For an INGRESS rule, the source is outside the network and the destination is
     * protected by this firewall. For an EGRESS rule, the source is a resource protected by this firewall and the
     * destination is some resources external to this. This straightforward approach differs from the way the deprecated
     * versions of this method treat source/target.
     * @param firewallRuleId the provider ID for this rule (or null if the provider does not identify its rules)
     * @param providerFirewallId the unique ID of the firewall with which this rule is associated
     * @param sourceEndpoint a rule target identifying the source of traffic associated with this rule
     * @param direction the direction of the network traffic being allowed/disallowed
     * @param protocol the protocol over which the communication is allowed
     * @param permission whether or not the traffic is being allowed/denied
     * @param destinationEndpoint a rule target identifying the destination of traffic associated with this rule
     * @param startPort the end of the port range for the communication
     * @param endPort the end of the port range for the communication
     * @return a firewall rule matching the parameters specified
     */
    static public @Nonnull FirewallRule getInstance(@Nullable String firewallRuleId, @Nonnull String providerFirewallId, @Nonnull RuleTarget sourceEndpoint, @Nonnull Direction direction, @Nonnull Protocol protocol, @Nonnull Permission permission, @Nonnull RuleTarget destinationEndpoint, int startPort, int endPort) {
        FirewallRule rule = new FirewallRule();

        rule.sourceEndpoint = sourceEndpoint;
        rule.destinationEndpoint = destinationEndpoint;
        rule.direction = direction;
        rule.endPort = endPort;
        rule.firewallId = providerFirewallId;
        rule.permission = permission;
        rule.protocol = protocol;
        rule.precedence = 0;
        rule.startPort = startPort;
        rule.providerRuleId = (firewallRuleId == null ? getRuleId(rule.firewallId, rule.sourceEndpoint, rule.direction, rule.protocol, rule.permission, rule.destinationEndpoint, rule.startPort, rule.endPort): firewallRuleId);
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

    static public @Nonnull String getRuleId(@Nonnull String providerFirewallId, @Nonnull RuleTarget sourceEndpoint, @Nonnull Direction direction, @Nonnull Protocol protocol, @Nonnull Permission permission, @Nullable RuleTarget destinationEndpoint, int startPort, int endPort) {
        if( destinationEndpoint == null ) {
            if( Permission.ALLOW.equals(permission) ) {
                return providerFirewallId + ":" + sourceEndpoint + ":" + direction + ":" + protocol + ":" + startPort + ":" + endPort;
            }
            else {
                return Permission.DENY + ":" + providerFirewallId + ":" + sourceEndpoint + ":" + direction + ":" + protocol + ":" + startPort + ":" + endPort;
            }
        }
        else {
            return permission + ":" + providerFirewallId + ":" + sourceEndpoint + ":" + direction + ":" + protocol + ":" + startPort + ":" + endPort + ":" + destinationEndpoint;
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


        Direction direction = null;
        RuleTarget source;


        String tname = parts[i++];

        if( parts.length < i+1 ) {
            return null;
        }
        else {
            try {
                RuleTargetType t = RuleTargetType.valueOf(tname);
                String tmp = parts[i++];

                direction = Direction.valueOf(tmp.toUpperCase());

                switch( t ) {
                    case GLOBAL: source = RuleTarget.getGlobal(tmp); break;
                    case VM: source = RuleTarget.getVirtualMachine(tmp); break;
                    case VLAN: source = RuleTarget.getVlan(tmp); break;
                    case CIDR: source = RuleTarget.getCIDR(tmp); break;
                    default: return null;
                }
            }
            catch( Throwable ignore ) {
                source = RuleTarget.getGlobal(providerFirewallId);
            }
        }

        if( direction == null ) {
            try {
                direction = Direction.valueOf(parts[i++].toUpperCase());
            }
            catch( Throwable ignore ) {
                return null;
            }
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
            target = RuleTarget.getGlobal(providerFirewallId);
        }
        else {
            tname = parts[i++];
            if( parts.length < i+1 ) {
                target = RuleTarget.getGlobal(providerFirewallId);
            }
            else {
                try {
                    RuleTargetType t = RuleTargetType.valueOf(tname);

                    switch( t ) {
                        case GLOBAL: target = RuleTarget.getGlobal(parts[i]); break;
                        case VM: target = RuleTarget.getVirtualMachine(parts[i]); break;
                        case VLAN: target = RuleTarget.getVlan(parts[i]); break;
                        case CIDR: target = RuleTarget.getCIDR(parts[i]); break;
                        default: return null;
                    }
                }
                catch( Throwable ignore ) {
                    target = RuleTarget.getGlobal(providerFirewallId);
                }
            }
        }
        return FirewallRule.getInstance(null, providerFirewallId, source, direction, protocol, permission, target, startPort, endPort);
    }

    private RuleTarget destinationEndpoint;
    private Direction  direction;
    private int        endPort;
    private String     firewallId;
    private Permission permission;
    private int        precedence;
    private Protocol   protocol;
    private String     providerRuleId;
    private RuleTarget sourceEndpoint;
    private int        startPort;

    private FirewallRule() { }

    @Override
    public int compareTo(@Nonnull FirewallRule other) {
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
            if( precedence == -1 ) {
                return 1;
            }
            else if( other.precedence == -1 ) {
                return -1;
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
     * @deprecated Use {@link #getSourceEndpoint()}
     */
    public @Nullable String getCidr() {
        //noinspection deprecation
        return getSource();
    }

    /**
     * @return the destination rule target for traffic matching this rule
     */
    public @Nonnull RuleTarget getDestinationEndpoint() {
        return destinationEndpoint;
    }

    /**
     * @return the sub-target behind this firewall that limits routing of traffic.
     * @deprecated Use {@link #getDestinationEndpoint()}
     */
    public @Nonnull  RuleTarget getTarget() {
        if( destinationEndpoint == null ) {
            return RuleTarget.getGlobal(firewallId);
        }
        return destinationEndpoint;
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
     * @return a string representing the source of this traffic
     * @deprecated Use {@link #getSourceEndpoint()}
     */
    public @Nonnull String getSource() {
        String source;

        switch( sourceEndpoint.getRuleTargetType() ) {
            case GLOBAL:
                source = sourceEndpoint.getProviderFirewallId();
                if( source == null ) {
                    source = firewallId;
                }
                return source;
            case CIDR:
                //noinspection ConstantConditions
                return sourceEndpoint.getCidr();
            case VM:
                //noinspection ConstantConditions
                return sourceEndpoint.getProviderVirtualMachineId();
            case VLAN:
                //noinspection ConstantConditions
                return sourceEndpoint.getProviderVlanId();
        }
        throw new RuntimeException("Invalid rule target type: " + sourceEndpoint.getRuleTargetType());
    }

    /**
     * @return the source of the traffic associated with the rule
     */
    public @Nonnull RuleTarget getSourceEndpoint() {
        return sourceEndpoint;
    }

    /**
     * @return the first port in the range of ports allowed by this rule
     */
    public int getStartPort() {
        return startPort;
    }

    @Override
    public @Nonnull String toString() {
        return (direction + " (" + precedence + ")/" + permission + ": " + sourceEndpoint + "->" + protocol + ":" + destinationEndpoint + " [" + startPort + "-" + endPort + "]");
    }

    /**
     * Indicates a precedence order for this rule
     * @param precedence the numeric precedence
     * @return this
     */
    public @Nonnull FirewallRule withPrecedence(@Nonnegative int precedence) {
        this.precedence = precedence;
        return this;
    }
}
