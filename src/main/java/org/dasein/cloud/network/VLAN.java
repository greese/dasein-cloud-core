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

import org.dasein.cloud.Taggable;
import org.dasein.cloud.VisibleScope;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 2013.02 Added networkType field (issue #25)
 */
public class VLAN implements Taggable {
    private String    cidr;
    private VLANState currentState;
    private String    description;
    private String[]  dnsServers;
    private String    domainName;
    private String    name;
    private String[]  ntpServers;
    private String    providerDataCenterId;
    private String    providerOwnerId;
    private String    providerRegionId;
    private String    providerVlanId;
    private String    networkType;
    private IPVersion[]        supportedTraffic;
    private Map<String,String> tags;
    private VisibleScope visibleScope;
    
    public VLAN() { }

    public boolean equals(Object ob) {
        if( ob == null ) {
            return false;
        }
        if( ob == this ) {
            return true;
        }
        if( ob instanceof VLAN ) {
            VLAN v = (VLAN)ob;
            
            if( !providerOwnerId.equals(v.providerOwnerId) ) {
                return false;
            }
            return providerVlanId.equals(v.providerVlanId);
        }
        return false;
    }
    
    public int hashCode() {
        return (providerOwnerId + providerVlanId).hashCode();
    }
    
    public String getCidr() {
        return cidr;
    }

    /**
     * Sets the CIDR associated with this network.
     * @param cidr the CIDR that governs the address space for the network
     */
    public void setCidr(String cidr) {
        this.cidr = cidr;
    }

    /**
     * Sets the network CIDR based on a netmask and an address within the network (typically, your gateway).
     * @param netmask the netmask for the network
     * @param anAddress an address within the network
     */
    public void setCidr(@Nonnull String netmask, @Nonnull String anAddress) {
        String[] dots = netmask.split("\\.");
        int cidr = 0;

        for( String item : dots ) {
            int x = Integer.parseInt(item);

            for( ; x > 0 ; x = (x<<1)%256 ) {
                cidr++;
            }
        }
        StringBuilder network = new StringBuilder();

        dots = anAddress.split("\\.");
        int start = 0;

        for( String item : dots ) {
            if( ((start+8) < cidr) || cidr == 0 ) {
                network.append(item);
            }
            else {
                int addresses = (int)Math.pow(2, (start+8)-cidr);
                int subnets = 256/addresses;
                int gw = Integer.parseInt(item);

                for( int i=0; i<subnets; i++ ) {
                    int base = i*addresses;
                    int top = ((i+1)*addresses);

                    if( gw >= base && gw < top ) {
                        network.append(String.valueOf(base));
                        break;
                    }
                }
            }
            start += 8;
            if( start < 32 ) {
                network.append(".");
            }
        }
        network.append("/");
        network.append(String.valueOf(cidr));
        setCidr(network.toString());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProviderDataCenterId() {
        return providerDataCenterId;
    }

    public void setProviderDataCenterId(String providerDataCenterId) {
        this.providerDataCenterId = providerDataCenterId;
    }

    public String getProviderOwnerId() {
        return providerOwnerId;
    }

    public void setProviderOwnerId(String providerOwnerId) {
        this.providerOwnerId = providerOwnerId;
    }
    
    public String getProviderRegionId() {
        return providerRegionId;
    }

    public void setProviderRegionId(String providerRegionId) {
        this.providerRegionId = providerRegionId;
    }

    public String getProviderVlanId() {
        return providerVlanId;
    }

    public void setProviderVlanId(String providerVlanId) {
        this.providerVlanId = providerVlanId;
    }
    
    public String toString() {
        return cidr + " [" + providerOwnerId + "/" + providerVlanId + "]";
    }

    public void setDnsServers(String[] dnsServers) {
        this.dnsServers = dnsServers;
    }

    public String[] getDnsServers() {
        return (dnsServers == null ? new String[0] : dnsServers);
    }

    public void setTags(Map<String,String> tags) {
        this.tags = tags;
    }

    public @Nonnull Map<String,String> getTags() {
        return (tags == null ? new HashMap<String, String>() : tags);
    }

    public void setVisibleScope(VisibleScope visibleScope){
        this.visibleScope = visibleScope;
    }

    public VisibleScope getVisibleScope(){
        return this.visibleScope;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setNtpServers(String[] ntpServers) {
        this.ntpServers = ntpServers;
    }

    public String[] getNtpServers() {
        return (ntpServers == null ? new String[0] : ntpServers);
    }

    public void setCurrentState(VLANState currentState) {
        this.currentState = currentState;
    }

    public VLANState getCurrentState() {
        return currentState;
    }

    public IPVersion[] getSupportedTraffic() {
        return (supportedTraffic == null ? new IPVersion[0] : supportedTraffic);
    }

    public void setSupportedTraffic(IPVersion ... supportedTraffic) {
        this.supportedTraffic = supportedTraffic;
    }

    public @Nullable String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(@Nonnull String t) {
        networkType = t;
    }

    @Override
    public void setTag(@Nonnull String key, @Nonnull String value) {
        if( tags == null ) {
            tags = new HashMap<String,String>();
        }
        tags.put(key, value);
    }

    public @Nullable String getTag(@Nonnull String key) {
        return getTags().get(key);
    }
}
