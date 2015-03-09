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
 * Represents the current state of an endpoint as it is seen from the load balancer.
 * @author Cameron Stokes
 * @version 2013.04 changed from server state to endpoint state
 * @version 2013.02 initial version
 * @since 2013.02
 */
public enum LbEndpointState {
    /**
     * The endpoint is healthy and responding
     */
    ACTIVE,
    /**
     * The endpoint is not responding
     */
    INACTIVE
}
