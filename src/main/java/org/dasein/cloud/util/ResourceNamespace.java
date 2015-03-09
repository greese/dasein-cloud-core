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

package org.dasein.cloud.util;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;

import javax.annotation.Nonnull;

/**
 * An interface that prescribes the ability to search within a namespace to find the uniqueness of a name.
 * <p>Created by George Reese: 3/4/14 9:13 AM</p>
 * @author George Reese
 * @version 2014.03 initial version (issue #134)
 * @since 2014.03
 */
public interface ResourceNamespace {
    /**
     * Searches through the relevant namespace to identify if the target name is already in use.
     * @param withName the name being checked
     * @return true if an item already exists with that name, false otherwise
     * @throws CloudException an error occurred querying the cloud against the name
     * @throws InternalException a Dasein Cloud error occurred performing the search
     */
    public boolean hasNamedItem(@Nonnull String withName) throws CloudException, InternalException;
}
