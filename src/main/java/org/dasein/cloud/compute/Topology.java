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
    static public @Nonnull Topology getInstance(@Nonnull String ownerId, @Nonnull String regionId, @Nonnull String topologyId, @Nonnull String name, @Nonnull String description) {
        Topology t = new Topology();

        t.providerOwnerId = ownerId;
        t.providerRegionId = regionId;
        t.providerTopologyId = topologyId;
        t.name = name;
        t.description = description;
        t.creationTimestamp = 0L;
        return t;
    }

    private long   creationTimestamp;
    private String description;
    private String name;
    private String providerOwnerId;
    private String providerRegionId;
    private String providerTopologyId;
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

    public @Nonnegative long getCreationTimestamp() {
        return creationTimestamp;
    }

    public @Nonnull String getDescription() {
        return description;
    }

    public @Nonnull String getName() {
        return name;
    }

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
