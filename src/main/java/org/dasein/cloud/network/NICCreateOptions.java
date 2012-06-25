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
 * Options used in creating network interfaces.
 * <p>Created by George Reese: 6/23/12 8:50 AM</p>
 * @author George Reese (george.reese@imaginary.com)
 * @since 2012-07
 * @version 2012-07 Introduced network interface configuration options
 */
@SuppressWarnings("UnusedDeclaration")
public class NICCreateOptions {
    static public NICCreateOptions getInstanceForSubnet(@Nonnull String subnetId, @Nonnull String name, @Nonnull String description) {
        NICCreateOptions options = new NICCreateOptions();
        
        options.subnetId = subnetId;
        options.name = name;
        options.description = description;
        return options;
    }

    static public NICCreateOptions getInstanceForVlan(@Nonnull String vlanId, @Nonnull String name, @Nonnull String description) {
        NICCreateOptions options = new NICCreateOptions();

        options.vlanId = vlanId;
        options.name = name;
        options.description = description;
        return options;
    }

    private String   description;
    private String[] firewallIds;
    private String   ipAddress;
    private String   name;
    private String   subnetId;
    private String   vlanId;

    private NICCreateOptions() { }

    public @Nonnull String getDescription() {
        return description;
    }
    
    public @Nonnull String[] getFirewallIds() {
        return (firewallIds == null ? new String[0] : firewallIds);
    }
    
    public @Nullable String getIpAddress() {
        return ipAddress;
    }
    
    public @Nonnull String getName() {
        return name;
    }
    
    public @Nullable String getSubnetId() {
        return subnetId;
    }
            
    public @Nullable String getVlanId() {
        return vlanId;
    }
    
    public @Nonnull NICCreateOptions behindFirewalls(@Nonnull String ... firewallIds) {
        if( this.firewallIds == null || this.firewallIds.length < 1 ) {
            this.firewallIds = firewallIds;
        }
        else if( firewallIds.length > 0 ) {
            String[] tmp = new String[this.firewallIds.length + firewallIds.length];
            int i = 0;

            for( String id : this.firewallIds ) {
                tmp[i++] = id;
            }
            for( String id : firewallIds ) {
                tmp[i++] = id;
            }
            this.firewallIds = tmp;
        }
        return this;
    }
    
    public @Nonnull NICCreateOptions withIpAddress(@Nonnull String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }
}
