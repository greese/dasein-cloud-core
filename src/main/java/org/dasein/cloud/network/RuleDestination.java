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

/**
 * Describes a specific destination for an IP address rule and formats it for display.
 * <p>Created by George Reese: 11/18/12 9:57 AM</p>
 * @author George Reese
 * @version 2013.01 initial verson (Issue #11)
 * @since 2013.01
 */
public class RuleDestination {
    static public @Nonnull RuleDestination getGlobal() {
        RuleDestination d = new RuleDestination();

        d.destinationType = DestinationType.GLOBAL;
        return d;
    }

    static public @Nonnull RuleDestination getIPAddress(@Nonnull String address) {
        RuleDestination d = new RuleDestination();

        d.destinationType = DestinationType.IP;
        d.ipAddress = address;
        return d;
    }

    static public @Nonnull RuleDestination getVirtualMachine(@Nonnull String virtualMachineId) {
        RuleDestination d = new RuleDestination();

        d.destinationType = DestinationType.VM;
        d.providerVirtualMachineId = virtualMachineId;
        return d;
    }

    static public @Nonnull RuleDestination getVlan(@Nonnull String vlanId) {
        RuleDestination d = new RuleDestination();

        d.destinationType = DestinationType.VM;
        d.providerVlanId = vlanId;
        return d;
    }

    private DestinationType destinationType;
    private String          ipAddress;
    private String          providerVirtualMachineId;
    private String          providerVlanId;

    private RuleDestination() { }

    public DestinationType getDestinationType() {
        return destinationType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getProviderVirtualMachineId() {
        return providerVirtualMachineId;
    }

    public String getProviderVlanId() {
        return providerVlanId;
    }

    public String toString() {
        if( ipAddress != null ) {
            return destinationType + ":" + ipAddress;
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
