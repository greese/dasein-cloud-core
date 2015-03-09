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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Describes a specific source or destination target for an IP address rule and formats it for display.
 * <p>Created by George Reese: 11/18/12 9:57 AM</p>
 * @author George Reese
 * @version 2013.01 initial verson (Issue #11)
 * @since 2013.01
 */
@SuppressWarnings("UnusedDeclaration")
public class RuleTarget {
    /**
     * @param providerFirewallId the firewall ID for the global rule
     * @return a rule target that reflects global routing for all resources protected by the named firewall
     */
    static public @Nonnull RuleTarget getGlobal(@Nonnull String providerFirewallId) {
        RuleTarget d = new RuleTarget();

        d.providerFirewallId = providerFirewallId;
        d.ruleTargetType = RuleTargetType.GLOBAL;
        return d;
    }

    /**
     * @param cidr the CIDR for the sub-destination
     * @return a rule sub-destination reflecting the IPs that match the specified CIDR
     */
    static public @Nonnull RuleTarget getCIDR(@Nonnull String cidr) {
        RuleTarget d = new RuleTarget();

        d.ruleTargetType = RuleTargetType.CIDR;
        d.cidr = cidr;
        return d;
    }

    /**
     * @param virtualMachineId a virtual machine behind this firewall
     * @return a sub-destination for just the specified virtual machine
     */
    static public @Nonnull
    RuleTarget getVirtualMachine(@Nonnull String virtualMachineId) {
        RuleTarget d = new RuleTarget();

        d.ruleTargetType = RuleTargetType.VM;
        d.providerVirtualMachineId = virtualMachineId;
        return d;
    }

    /**
     * @param vlanId a VLAN behind the firewall
     * @return a sub-destination matching only IPs within a specific VLAN behind this firewall
     */
    static public @Nonnull  RuleTarget getVlan(@Nonnull String vlanId) {
        RuleTarget d = new RuleTarget();

        d.ruleTargetType = RuleTargetType.VLAN;
        d.providerVlanId = vlanId;
        return d;
    }

    private RuleTargetType  ruleTargetType;
    private String          cidr;
    private String          providerFirewallId;
    private String          providerVirtualMachineId;
    private String          providerVlanId;

    private RuleTarget() { }

    /**
     * @return the type of destination this represents
     */
    public @Nonnull
    RuleTargetType getRuleTargetType() {
        return ruleTargetType;
    }

    /**
     * @return the CIDR behind the firewall for this destination
     */
    public @Nullable String getCidr() {
        return cidr;
    }

    /**
     * @return the unique ID of the provider firewall associated with resources for this target
     */
    public @Nullable String getProviderFirewallId() {
        return providerFirewallId;
    }

    /**
     * @return the unique ID of a virtual machine behind the firewall for this destination
     */
    public @Nullable String getProviderVirtualMachineId() {
        return providerVirtualMachineId;
    }

    /**
     * @return the unique ID of a VLAN behind the firewall for this destination
     */
    public @Nullable String getProviderVlanId() {
        return providerVlanId;
    }

    @Override
    public @Nonnull String toString() {
        if( cidr != null ) {
            return ruleTargetType + ":" + cidr;
        }
        else if( providerVirtualMachineId != null ) {
            return ruleTargetType + ":" + providerVirtualMachineId;
        }
        else if( providerVlanId != null ) {
            return ruleTargetType + ":" + providerVlanId;
        }
        else if( providerFirewallId != null ) {
            return ruleTargetType + ":" + providerFirewallId;
        }
        return ruleTargetType.toString();
    }
}
