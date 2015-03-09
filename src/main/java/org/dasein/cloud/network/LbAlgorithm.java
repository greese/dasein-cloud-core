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

package org.dasein.cloud.network;

/**
 * Represents the different algorithms that might be used in balancing loads.
 * @author George Reese
 * @version 2013.04 added javadoc
 * @since unknown
 */
public enum LbAlgorithm {
    /**
     * Balances traffic across data centers or endpoints in a round-robin fashion.
     */
    ROUND_ROBIN,
    /**
     * Balances traffic across data centers or endpoints based on who has the fewest open connections.
     */
    LEAST_CONN,
    /**
     * Balances traffic across data centers or endpoints based on the source IP address of the client.
     */
    SOURCE
}
