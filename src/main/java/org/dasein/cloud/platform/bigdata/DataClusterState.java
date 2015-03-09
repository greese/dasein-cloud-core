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

/**
 * Represents the operational state for a {@link DataCluster}.
 * <p>Created by George Reese: 2/7/14 12:24 PM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 initial version
 */
public enum DataClusterState {
    /**
     * The cluster is available
     */
    AVAILABLE,
    /**
     * The system is in the process of creating the cluster
     */
    CREATING,
    /**
     * The system is in the process of deleting the cluster
     */
    DELETING,
    /**
     * The cloud is in the process of rebooting the cluster
     */
    REBOOTING,
    /**
     * The cloud is in the process of resizing the cluster
     */
    RESIZING
}
