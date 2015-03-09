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
 * A load balancer may route to different kinds of endpoints. Depending on the capabilities of the cloud, it may be
 * to just a single kind of endpoint or multiple kinds of endpoints. The type describes a given type of endpoint to
 * which a load balancer may route.
 * <p>Created by George Reese: 3/7/13 9:29 PM</p>
 * @author George Reese
 * @version 2013.04 initial version
 * @since 2013.04
 */
public enum LbEndpointType {
    /**
     * A virtual machine endpoint (the value for a virtual machine endpoint is the ID of the virtual machine)
     */
    VM,
    /**
     * An IP address endpoint (the value for an IP endpoint is an IP address)
     */
    IP
}
