/**
 * Copyright (C) 2009-2014 Dell, Inc.
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A logical container for cloud resources. Resources within the same affinity group should trend towards low-latency (but non-HA) placement within the cloud.
 * <p>Created by Drew Lyall: 09/07/14 15:00 AM</p>
 * @author Drew Lyall
 * @version 2014.07 initial version
 * @since 2014.07
 */
public class AffinityGroup{
    private String affinityGroupId;
    private String affinityGroupName;
    private String description;
    private String dataCenterId;
    private Long   creationTimestamp;

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
}
