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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * A fixed group of parameters associated with a data cluster parameter family for a version that may be used when creating
 * data clusters.
 * <p>Created by George Reese: 2/8/14 1:20 PM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 (issue #100)
 */
public class DataClusterParameterGroup {
    /**
     * Constructs a parameter group from the specified values.
     * @param providerGroupId the unique ID of the parameter group
     * @param family the family to which the parameter group belongs
     * @param name the name of the parameter group
     * @param description a description of the parameter group
     * @param parameters the parameter names and their associated values
     * @return a newly constructed parameter group based on the specified values
     */
    static public @Nonnull DataClusterParameterGroup getInstance(@Nonnull String providerGroupId, @Nonnull String family, @Nonnull String name, @Nonnull String description, Map<String,Object> parameters) {
        DataClusterParameterGroup group = new DataClusterParameterGroup();

        group.providerGroupId = providerGroupId;
        group.family = family;
        group.name = name;
        group.description = description;
        group.parameters = parameters;
        return group;
    }

    private String             description;
    private String             family;
    private String             name;
    private Map<String,Object> parameters;
    private String             providerGroupId;

    private DataClusterParameterGroup() { }

    @Override
    public boolean equals(@Nullable Object other) {
        if( other == null ) {
            return false;
        }
        if( other == this ) {
            return true;
        }
        return getClass().getName().equals(other.getClass().getName()) && providerGroupId.equals(((DataClusterParameterGroup) other).providerGroupId) && family.equals(((DataClusterParameterGroup) other).family);
    }

    /**
     * @return a description of the parameter group
     */
    public @Nonnull String getDescription() {
        return description;
    }

    /**
     * @return the family from the data cluster version to which the parameters from this parameter group are tied
     */
    public @Nonnull String getFamily() {
        return family;
    }

    /**
     * @return the name of the parameter group
     */
    public @Nonnull String getName() {
        return name;
    }

    /**
     * @return the parameters and their values associated with this parameter group
     */
    public @Nonnull Map<String,Object> getParameters() {
        return parameters;
    }

    /**
     * @return the unique identifier for the parameter group
     */
    public @Nonnull String getProviderGroupId() {
        return providerGroupId;
    }

    @Override
    public @Nonnull String toString() {
        return (providerGroupId + " (" + family + ")");
    }
}
