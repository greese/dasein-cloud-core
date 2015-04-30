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

import org.dasein.cloud.AbstractProviderService;
import org.dasein.cloud.CloudProvider;

import javax.annotation.Nullable;

public abstract class AbstractNetworkServices<T extends CloudProvider> extends AbstractProviderService<T> implements NetworkServices {
    protected AbstractNetworkServices(T provider) {
        super(provider);
    }

    @Override
    public @Nullable DNSSupport getDnsSupport() {
        return null;
    }
    
    @Override
    public @Nullable FirewallSupport getFirewallSupport() {
        return null;
    }

    @Override
    public @Nullable IpAddressSupport getIpAddressSupport() {
        return null;
    }

    @Override
    public @Nullable LoadBalancerSupport getLoadBalancerSupport() {
        return null;
    }

    @Override
    public @Nullable NetworkFirewallSupport getNetworkFirewallSupport() {
        return null;
    }

    @Override
    public @Nullable VLANSupport getVlanSupport() {
        return null;
    }
    
    @Override
    public @Nullable VPNSupport getVpnSupport() {
        return null;
    }
    
    @Override
    public boolean hasDnsSupport() {
        return (getDnsSupport() != null);
    }
    
    @Override
    public boolean hasFirewallSupport() {
        return (getFirewallSupport() != null);
    }

    @Override
    public boolean hasIpAddressSupport() {
        return (getIpAddressSupport() != null);
    }

    @Override
    public boolean hasLoadBalancerSupport() {
        return (getLoadBalancerSupport() != null);
    }

    @Override
    public boolean hasNetworkFirewallSupport() {
        return (getNetworkFirewallSupport() != null);
    }

    @Override
    public boolean hasVlanSupport() {
        return (getVlanSupport() != null);
    }
    
    @Override
    public boolean hasVpnSupport() {
        return (getVpnSupport() != null);
    }

}
