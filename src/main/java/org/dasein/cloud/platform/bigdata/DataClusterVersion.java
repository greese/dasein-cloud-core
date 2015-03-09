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

/**
 * A specific version of the data clustering technology used by the cloud provider.
 * <p>Created by George Reese: 2/8/14 12:53 PM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 initial version (issue #100)
 */
public class DataClusterVersion {
    /**
     * Constructs a new data cluster version object.
     * @param versionNumber the version number represented by this object
     * @param parameterFamily the family that defines what parameters should get values in clusters based on this version
     * @param name the name of the version
     * @param description a description of the version
     * @return a new data cluster version object with the specified values
     */
    static public @Nonnull DataClusterVersion getInstance(@Nonnull String versionNumber, @Nonnull String parameterFamily, @Nonnull String name, @Nonnull String description) {
        DataClusterVersion version = new DataClusterVersion();

        version.versionNumber = versionNumber;
        version.parameterFamily = parameterFamily;
        version.name = name;
        version.description = description;
        return version;
    }

    private String description;
    private String name;
    private String parameterFamily;
    private String versionNumber;

    private DataClusterVersion() { }

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
        DataClusterVersion v = (DataClusterVersion)other;

        return versionNumber.equals(v.versionNumber);
    }

    /**
     * @return a description of the version
     */
    public @Nonnull String getDescription() {
        return description;
    }

    /**
     * @return a user-friendly name for the version
     */
    public @Nonnull String getName() {
        return name;
    }

    /**
     * @return the family with which parameters for a data cluster in this version are associated
     */
    public @Nonnull String getParameterFamily() {
        return parameterFamily;
    }

    /**
     * @return the version number of the version (identifies the version)
     */
    public @Nonnull String getVersionNumber() {
        return versionNumber;
    }

    @Override
    public @Nonnull String toString() {
        return versionNumber;
    }
}
