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

package org.dasein.cloud;

/**
 * Defines what level data gets cached at.
 * <p>Created by AndyLyall: 02/25/14 13:35 PM</p>
 * @author Andy Lyall
 * @version 2014.03 initial version
 * @since 2014.03
 */
public enum VisibleScope {

    /**
     * Resource is visibile across the entire account
     */
    ACCOUNT_GLOBAL,

    /**
     * Resource is visible across one whole region
     */
    ACCOUNT_REGION,

    /**
     * Resource is visible across one whole datacenter
     */
    ACCOUNT_DATACENTER
}
