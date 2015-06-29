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

package org.dasein.cloud.platform.bigdata;

import org.dasein.cloud.network.Firewall;
import org.dasein.cloud.network.FirewallReference;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

/**
 * Indicates a set of IP addresses and compute firewalls that are able to send traffic into a data cluster associated
 * with this data cluster firewall.
 * <p>Created by George Reese: 2/9/14 9:39 AM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 initial version (issue #100)
 */
public class DataClusterFirewall {
    static public @Nonnull DataClusterFirewall getInstance(@Nonnull String providerOwnerId, @Nonnull String providerRegionId, @Nonnull String providerFirewallId, @Nonnull String name, @Nonnull String description) {
        DataClusterFirewall fw = new DataClusterFirewall();

        fw.providerFirewallId = providerFirewallId;
        fw.providerOwnerId = providerOwnerId;
        fw.providerRegionId = providerRegionId;
        fw.name = name;
        fw.description = description;
        return fw;
    }

    private FirewallReference[] authorizedFirewalls;
    private String[]            authorizedIps;
    private String              description;
    private String              name;
    private String              providerFirewallId;
    private String              providerOwnerId;
    private String              providerRegionId;

    private DataClusterFirewall() { }

    /**
     * Alters the content of this data cluster firewall object to reflect the fact that the named CIDR IPs are
     * included in the list of authorized IPs. This method does not trigger any action in the cloud itself.
     * @param ips a list of CIDR IPs that are authorized to communicate with data clusters protected by this firewall
     * @return this
     */
    public @Nonnull DataClusterFirewall authorizingIps(@Nonnull String ... ips) {
        TreeSet<String> auth = new TreeSet<String>();

        if( authorizedIps != null ) {
            Collections.addAll(auth, authorizedIps);
        }
        Collections.addAll(auth, ips);
        authorizedIps = auth.toArray(new String[auth.size()]);
        return this;
    }

    /**
     * Alters the content of this data cluster firewall object to reflect the fact that the named compute firewalls are
     * included in the list of authorized compute firewalls. This method does not trigger any action in the cloud itself.
     * @param firewalls a list of compute firewall {@link Firewall} references in the form of { owner ID, firewallId } pairs
     * @return this
     */
    public @Nonnull DataClusterFirewall authorizingComputeFirewalls(@Nonnull FirewallReference ... firewalls) {
        TreeSet<FirewallReference> fws = new TreeSet<FirewallReference>();

        if( authorizedFirewalls != null ) {
            Collections.addAll(fws, authorizedFirewalls);
        }
        Collections.addAll(fws, firewalls);
        authorizedFirewalls = fws.toArray(new FirewallReference[fws.size()]);
        return this;
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if( other == null ) {
            return false;
        }
        if( other == this ) {
            return true;
        }
        if( !getClass().getName().equals(other.getClass().getName()) ) {
            return false;
        }
        DataClusterFirewall fw = (DataClusterFirewall)other;

        return (providerRegionId.equals(fw.providerRegionId) && providerOwnerId.equals(fw.providerOwnerId) && providerFirewallId.equals(fw.providerFirewallId));
    }

    /**
     * Lists the compute firewalls {@link Firewall} that are allowed to interact with data clusters protected by this
     * data cluster firewall. An instance in a cloud may be associated with one or more compute firewalls. By virtue
     * of this association, they may therefore be granted access to a data cluster associated with this data cluster
     * firewall if one of the compute firewalls is listed here. Because compute firewalls are uniquely identified through
     * owner/ID pairs, this method returns a list of string arrays. The first item in each entry is owner ID/account number
     * of the cloud account who owns the compute firewall. The second item in each entry is the compute firewall ID.
     * @return a list
     */
    public @Nonnull FirewallReference[] getAuthorizedComputeFirewalls() {
        if( authorizedFirewalls == null || authorizedFirewalls.length < 1 ) {
            return new FirewallReference[0];
        }
        FirewallReference[] copy = new FirewallReference[authorizedFirewalls.length];

        System.arraycopy(authorizedFirewalls, 0, copy, 0, authorizedFirewalls.length);
        return copy;
    }

    /**
     * @return a list of zero or more CIDR IPs authorized to communicate with data clusters associated with this firewall
     */
    public @Nonnull String[] getAuthorizedIps() {
        if( authorizedIps == null || authorizedIps.length < 1 ) {
            return new String[0];
        }
        String[] copy = new String[authorizedIps.length];

        System.arraycopy(authorizedIps, 0, copy, 0, copy.length);
        return copy;
    }

    /**
     * @return a description of the firewall
     */
    public @Nonnull String getDescription() {
        return description;
    }

    /**
     * @return a name for the firewall
     */
    public @Nonnull String getName() {
        return name;
    }

    /**
     * @return the unique ID for this firewall
     */
    public @Nonnull String getProviderFirewallId() {
        return providerFirewallId;
    }

    /**
     * @return the cloud account that owns this firewall
     */
    public @Nonnull String getProviderOwnerId() {
        return providerOwnerId;
    }

    /**
     * @return the region in which this firewall operates
     */
    public @Nonnull String getProviderRegionId() {
        return providerRegionId;
    }

    @Override
    public int hashCode() {
        return (providerOwnerId + "/" + providerRegionId + "/" + providerFirewallId).hashCode();
    }

    @Override
    public @Nonnull String toString() {
        return providerFirewallId;
    }
}
