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

package org.dasein.cloud.ci;

import org.dasein.cloud.Taggable;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * A converged infrastructure is a group of cloud resources provisioned from a {@link Topology} that operate together
 * in service of a common purpose. It can be as simple as a single VM or something more complex like two database VMs
 * and two app server VMs or it may be a very complex multi-tier application.
 * <p>Created by George Reese: 6/2/13 7:19 PM</p>
 * @author George Reese
 * @version 2013.07 initial version
 * @since 2013.07
 */
public class ConvergedInfrastructure implements Taggable {

    static public @Nonnull
    ConvergedInfrastructure getInstance(@Nonnull String ownerId, @Nonnull String regionId, @Nonnull String dataCenterId, @Nonnull String ciId, @Nonnull ConvergedInfrastructureState state, @Nonnull String name, @Nonnull String description, String providerTopologyId) {
        ConvergedInfrastructure ci = new ConvergedInfrastructure();

        ci.providerOwnerId = ownerId;
        ci.providerRegionId = regionId;
        ci.providerDataCenterId = dataCenterId;
        ci.providerConvergedInfrastructureId = ciId;
        ci.currentState = state;
        ci.name = name;
        ci.description = description;
        ci.provisioningTimestamp = System.currentTimeMillis();
        ci.providerTopologyId = providerTopologyId;
        return ci;
    }

    private ConvergedInfrastructureState currentState;
    private String                       description;
    private String                       name;
    private String                       providerConvergedInfrastructureId;
    private String                       providerDataCenterId;
    private String                       providerOwnerId;
    private String                       providerRegionId;
    private String                       providerTopologyId;
    private long                         provisioningTimestamp;
    private Map<String,String>           tags;
    //private String                       selfUrl;

    private ConvergedInfrastructure() { }

    public @Nonnull
    ConvergedInfrastructureState getCurrentState() {
        return currentState;
    }

    public @Nonnull String getDescription() {
        return description;
    }

    public @Nonnull String getName() {
        return name;
    }

    public @Nonnull String getProviderConvergedInfrastructureId() {
        return providerConvergedInfrastructureId;
    }

    public @Nullable String getProviderDataCenterId() {
        return providerDataCenterId;
    }

    public @Nonnull String getProviderOwnerId() {
        return providerOwnerId;
    }

    public @Nonnull String getProviderRegionId() {
        return providerRegionId;
    }

    public @Nullable String getProviderTopologyId() {
        return providerTopologyId;
    }

    public @Nonnegative long getProvisioningTimestamp() {
        return provisioningTimestamp;
    }

//    public String getSelfUrl() {
//        return selfUrl;
//    }
    /**
     * Fetches the value of the specified tag key.
     * @param tag the key of the tag to be fetched
     * @return the value associated with the specified key, if any
     */
    public @Nullable Object getTag(@Nonnull String tag) {
        return getTags().get(tag);
    }

    @Override
    public @Nonnull Map<String,String> getTags() {
        if( tags == null ) {
            tags = new HashMap<String,String>();
        }
        return tags;
    }

    /**
     * Indicates that the converged infrastructure is constrained to the specified data center.
     * @param dcId the unique ID of the data center to which this converged infrastructure is constrained
     * @return this
     */
    public @Nonnull
    ConvergedInfrastructure inDataCenter(@Nonnull String dcId) {
        providerDataCenterId = dcId;
        return this;
    }

    public @Nonnull
    ConvergedInfrastructure provisionedAt(@Nonnegative long ts) {
        provisioningTimestamp = ts;
        return this;
    }

    public @Nonnull
    ConvergedInfrastructure provisionedFrom(@Nonnull String topologyId) {
        providerTopologyId = topologyId;
        return this;
    }

    @Override
    public void setTag(@Nonnull String key, @Nonnull String value) {
        getTags().put(key, value);
    }

    /**
     * Clears out any currently set tags and replaces them with the specified list.
     * @param properties the list of tag values to be set
     */
    public void setTags(Map<String,String> properties) {
        getTags().clear();
        getTags().putAll(properties);
    }
}
