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
 * The various states in which a {@link DataClusterSnapshot} can find itself.
 * <p>Created by George Reese: 2/8/14 7:36 PM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 initial version (issue #100)
 */
public enum DataClusterSnapshotState {
    /**
     * The snapshot is available for use
     */
    AVAILABLE,
    /**
     * The snapshot is in the middle of an operation and you can't do anything to it right now
     */
    PENDING,
    /**
     * The snapshot has been deleted, but it still is registered in the system
     */
    DELETED,
    /**
     * The snapshot failed and can never be used to any useful ends
     */
    FAILED
}
