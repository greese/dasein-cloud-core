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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A reference to a compute firewall represented by {@link Firewall}. Because a compute firewall is often
 * referenced by an account number/firewall ID pair, this class helps maintain the reference without the need
 * to fully load firewall instances.
 * <p>Created by George Reese: 2/9/14 10:16 AM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 initial version in support of data cluster firewalls
 */
public class FirewallReference implements Comparable<FirewallReference> {
    /**
     * Constructs a firewall reference from the specified owner ID/firewall ID pair
     * @param ownerId the owner of the target firewall
     * @param firewallId the unique ID of the target firewall
     * @return a firewall reference that refers to the specified owner ID/firewall ID pair
     */
    static public @Nonnull FirewallReference getInstance(@Nonnull String ownerId, @Nonnull String firewallId) {
        FirewallReference r = new FirewallReference();

        r.providerOwnerId = ownerId;
        r.providerFirewallId = firewallId;
        return r;
    }

    private String providerOwnerId;
    private String providerFirewallId;

    private FirewallReference() { }

    @Override
    public int compareTo(@Nullable FirewallReference other) {
        if( other == null ) {
            return -1;
        }
        return toString().compareTo(other.toString());
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
        FirewallReference r = (FirewallReference)other;

        return (providerOwnerId.equals(r.providerOwnerId) && providerFirewallId.equals(r.providerFirewallId));
    }

    /**
     * @return the supposedly unique ID of the firewall being referenced
     */
    public @Nonnull String getProviderFirewallId() {
        return providerFirewallId;
    }

    /**
     * @return the account owner of the firewall being referenced
     */
    public @Nonnull String getProviderOwnerId() {
        return providerOwnerId;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public @Nonnull String toString() {
        return (providerOwnerId + "/" + providerFirewallId);
    }
}
