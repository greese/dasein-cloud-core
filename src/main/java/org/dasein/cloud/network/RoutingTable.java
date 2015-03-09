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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Identifies a routing table associated with a VLAN.
 * <p>Created by George Reese: 6/30/12 5:16 PM</p>
 * @author George Reese (george.reese@imaginary.com)
 * @since 2012.07
 * @version 2012.07 initial version
 */
public class RoutingTable implements Networkable, Taggable {
    private String  description;
    private String  name;
    private boolean main;
    private String  providerOwnerId;
    private String  providerRegionId;
    private String  providerRoutingTableId;
    private String  providerVlanId;
    private Route[] routes;
    private Map<String,String> tags;
    private String[] providerSubnetIds;
    
    public RoutingTable() { }

    @Override
    public boolean equals(@Nullable Object other) {
        if( other == null ) {
            return false;
        }
        if( other == this ) {
            return true;
        }
        if( other instanceof RoutingTable ) {
            RoutingTable t = (RoutingTable)other;

            return (providerOwnerId.equals(t.providerOwnerId) && providerRegionId.equals(t.providerRegionId) && providerRoutingTableId.equals(t.providerRoutingTableId));
        }
        return false;
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

    public String getProviderRoutingTableId() {
        return providerRoutingTableId;
    }

    public void setProviderRoutingTableId(String providerRoutingTableId) {
        this.providerRoutingTableId = providerRoutingTableId;
    }

    public String getProviderVlanId() {
        return providerVlanId;
    }

    public void setProviderVlanId(String providerVlanId) {
        this.providerVlanId = providerVlanId;
    }

    public String[] getProviderSubnetIds() {
      return providerSubnetIds;
    }

    public void setProviderSubnetIds(String[] providerSubnetIds) {
      this.providerSubnetIds = providerSubnetIds;
    }

    public Route[] getRoutes() {
        return routes;
    }

    public void setRoutes(Route[] routes) {
        this.routes = routes;
    }
    
    public String toString() {
        return providerRoutingTableId;
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

    public boolean isMain() {
        return main;
    }

    public void setMain( boolean main ) {
        this.main = main;
    }

    @Override
    public @Nonnull Map<String, String> getTags() {
        if( tags == null ) {
            tags = new HashMap<String, String>();
        }
        return tags;
    }

    @Override
    public void setTag(@Nonnull String key, @Nonnull String value) {
        getTags().put(key, value);
    }
}
