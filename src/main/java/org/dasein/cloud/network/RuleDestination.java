/**
 * ========= CONFIDENTIAL =========
 *
 * Copyright (C) 2012 enStratus Networks Inc - ALL RIGHTS RESERVED
 *
 * ====================================================================
 *  NOTICE: All information contained herein is, and remains the
 *  property of enStratus Networks Inc. The intellectual and technical
 *  concepts contained herein are proprietary to enStratus Networks Inc
 *  and may be covered by U.S. and Foreign Patents, patents in process,
 *  and are protected by trade secret or copyright law. Dissemination
 *  of this information or reproduction of this material is strictly
 *  forbidden unless prior written permission is obtained from
 *  enStratus Networks Inc.
 * ====================================================================
 */
package org.dasein.cloud.network;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Describes a specific destination for an IP address rule and formats it for display.
 * <p>Created by George Reese: 11/18/12 9:57 AM</p>
 * @author George Reese
 * @version 2013.01 initial verson (Issue #11)
 * @since 2013.01
 */
@SuppressWarnings("UnusedDeclaration")
public class RuleDestination {
    /**
     * @return a rule destination that reflects global routing to all addresses protected by the firewall
     */
    static public @Nonnull RuleDestination getGlobal() {
        RuleDestination d = new RuleDestination();

        d.destinationType = DestinationType.GLOBAL;
        return d;
    }

    /**
     * @param cidr the CIDR for the sub-destination
     * @return a rule sub-destination reflecting the IPs that match the specified CIDR
     */
    static public @Nonnull RuleDestination getCIDR(@Nonnull String cidr) {
        RuleDestination d = new RuleDestination();

        d.destinationType = DestinationType.CIDR;
        d.cidr = cidr;
        return d;
    }

    /**
     * @param virtualMachineId a virtual machine behind this firewall
     * @return a sub-destination for just the specified virtual machine
     */
    static public @Nonnull RuleDestination getVirtualMachine(@Nonnull String virtualMachineId) {
        RuleDestination d = new RuleDestination();

        d.destinationType = DestinationType.VM;
        d.providerVirtualMachineId = virtualMachineId;
        return d;
    }

    /**
     * @param vlanId a VLAN behind the firewall
     * @return a sub-destination matching only IPs within a specific VLAN behind this firewall
     */
    static public @Nonnull RuleDestination getVlan(@Nonnull String vlanId) {
        RuleDestination d = new RuleDestination();

        d.destinationType = DestinationType.VM;
        d.providerVlanId = vlanId;
        return d;
    }

    private DestinationType destinationType;
    private String          cidr;
    private String          providerVirtualMachineId;
    private String          providerVlanId;

    private RuleDestination() { }

    /**
     * @return the type of destination this represents
     */
    public @Nonnull DestinationType getDestinationType() {
        return destinationType;
    }

    /**
     * @return the CIDR behind the firewall for this destination
     */
    public @Nullable String getCidr() {
        return cidr;
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
            return destinationType + ":" + cidr;
        }
        else if( providerVirtualMachineId != null ) {
            return destinationType + ":" + providerVirtualMachineId;
        }
        else if( providerVlanId != null ) {
            return destinationType + ":" + providerVlanId;
        }
        return destinationType.toString();
    }
}
