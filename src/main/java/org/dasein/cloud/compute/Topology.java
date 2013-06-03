/**
 * Copyright (C) 2009-2013 Dell, Inc.
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

package org.dasein.cloud.compute;

import org.dasein.cloud.Taggable;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * A topology is a grouping of configurable resources that may be provisioned together to create a complex grouping of
 * interoperating cloud resources. A simple topology might be one with two images that get provisioned into two virtual
 * machines. A more complex topology might include network provisioning with load balancers.
 * <p>Created by George Reese: 5/30/13 11:38 AM</p>
 * @author George Reese
 * @version 2013.07 initial version
 * @since 2013.07
 */
public class Topology implements Taggable {
    static public @Nonnull Topology getInstance(@Nonnull String ownerId, @Nonnull String regionId, @Nonnull String topologyId, @Nonnull TopologyState state, @Nonnull String name, @Nonnull String description) {
        Topology t = new Topology();

        t.providerOwnerId = ownerId;
        t.providerRegionId = regionId;
        t.providerTopologyId = topologyId;
        t.currentState = state;
        t.name = name;
        t.description = description;
        t.creationTimestamp = 0L;
        return t;
    }

    private long               creationTimestamp;
    private TopologyState      currentState;
    private String             description;
    private String             name;
    private String             providerDataCenterId;
    private String             providerOwnerId;
    private String             providerRegionId;
    private String             providerTopologyId;
    private Map<String,String> tags;

    private Topology() { }

    /**
     * Indicates the Unix timestamp when this topology was created.
     * @param timestamp the Unix timestamp in milliseconds when this topology was created
     * @return this
     */
    public @Nonnull Topology createdAt(@Nonnegative long timestamp) {
        creationTimestamp = timestamp;
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
        Topology t = (Topology)other;

        return (t.providerOwnerId.equals(providerOwnerId) && t.providerRegionId.equals(providerRegionId) && t.providerTopologyId.equals(providerRegionId));
    }

    /**
     * @return the UNIX timestamp representing the time at which this toplogy was initially created
     */
    public @Nonnegative long getCreationTimestamp() {
        return creationTimestamp;
    }

    /**
     * @return the current state of the topology
     */
    public @Nonnull TopologyState getCurrentState() {
        return currentState;
    }

    /**
     * @return a user-friendly description of the topology
     */
    public @Nonnull String getDescription() {
        return description;
    }

    /**
     * @return a user-friendly name for the topology
     */
    public @Nonnull String getName() {
        return name;
    }

    /**
     * @return the data center, if any, to which the topology is constrained
     */
    public @Nullable String getProviderDataCenterId() {
        return providerDataCenterId;
    }

    /**
     * @return the account number of the account that owns this topology
     */
    public @Nonnull String getProviderOwnerId() {
        return providerOwnerId;
    }

    /**
     * @return the region to which this topology is constrained
     */
    public @Nonnull String getProviderRegionId() {
        return providerRegionId;
    }

    /**
     * @return the unique ID used by the cloud provider to identify this topology
     */
    public @Nonnull String getProviderTopologyId() {
        return providerTopologyId;
    }

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

    @Override
    public int hashCode() {
        return (providerOwnerId + providerRegionId + providerTopologyId).hashCode();
    }

    /**
     * Indicates that the topology is constrained to the specified data center.
     * @param dcId the unique ID of the data center to which this topology is constrained
     * @return this
     */
    public @Nonnull Topology inDataCenter(@Nonnull String dcId) {
        providerDataCenterId = dcId;
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

    @Override
    public @Nonnull String toString() {
        return (name + " [#" + providerTopologyId + "]");
    }
}
