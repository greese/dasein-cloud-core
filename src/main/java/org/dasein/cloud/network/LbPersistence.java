/**
 * Copyright (C) 2009-2013 enstratius, Inc.
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
 * Identifies the different mechanisms for creating client stickiness with specific endpoints in a load
 * balanced pool.
 * <p>Created by George Reese: 3/8/13 12:37 PM</p>
 * @author George Reese
 * @version 2013.04 initial version
 * @since 2013.04
 */
public enum LbPersistence {
    /**
     * No persistence is supported
     */
    NONE,
    /**
     * Requests coming from the same subnet will be routed to the same virtual machine/address
     */
    SUBNET,
    /**
     * A server cookie determines endpoint stickiness
     */
    COOKIE
}
