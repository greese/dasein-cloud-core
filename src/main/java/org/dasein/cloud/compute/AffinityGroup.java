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

package org.dasein.cloud.compute;

import org.dasein.cloud.Tag;
import org.dasein.cloud.Taggable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * A logical container for cloud resources. Resources within the same affinity group should trend towards low-latency (but non-HA) placement within the cloud.
 * <p>Created by Drew Lyall: 09/07/14 15:00 AM</p>
 * @author Drew Lyall
 * @version 2014.08 initial version
 * @since 2014.08
 */
public class AffinityGroup implements Taggable{
    private String              affinityGroupId;
    private String              affinityGroupName;
    private String              description;
    private String              dataCenterId;
    private Long                creationTimestamp;
    private Map<String, String> tags;

    public static @Nonnull AffinityGroup getInstance(@Nonnull String affinityGroupId, @Nonnull String affinityGroupName, @Nullable String description, @Nonnull String dataCenterId, @Nullable Long creationTimestamp){
        AffinityGroup affinityGroup = new AffinityGroup();
        affinityGroup.affinityGroupId = affinityGroupId;
        affinityGroup.affinityGroupName = affinityGroupName;
        affinityGroup.description = description;
        affinityGroup.dataCenterId = dataCenterId;
        affinityGroup.creationTimestamp = creationTimestamp;
        return affinityGroup;
    }

    public @Nonnull String getAffinityGroupId(){
        return affinityGroupId;
    }

    public @Nonnull String getAffinityGroupName(){
        return affinityGroupName;
    }

    public @Nonnull String getDescription(){
        return description;
    }

    public @Nonnull String getDataCenterId(){
        return dataCenterId;
    }

    public @Nullable Long getCreationTimestamp(){
        return creationTimestamp;
    }

    public void addTag(Tag t){
        addTag(t.getKey(), t.getValue());
    }

    public void addTag(String key, String value){
        getTags().put(key, value);
    }

    public Object getTag( String tag ) {
        return getTags().get(tag);
    }

    public synchronized @Nonnull Map<String, String> getTags(){
        if(tags == null){
            tags = new HashMap<String, String>();
        }
        return tags;
    }

    public void setTag(@Nonnull String key, @Nonnull String value){
        if( tags == null ) {
            tags = new HashMap<String, String>();
        }
        tags.put(key, value);
    }

    public synchronized void setTags(Map<String, String> properties){
        getTags().clear();
        getTags().putAll(properties);
    }
}
