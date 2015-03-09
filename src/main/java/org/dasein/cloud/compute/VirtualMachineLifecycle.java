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

package org.dasein.cloud.compute;

/**
 * Describes the types of VM lifecycle
 * <p>Created by Stas Maksimov: 20/06/2014 22:56</p>
 *
 * @author Stas Maksimov
 * @version 2014.08 initial version
 * @since 2014.08
 */
public enum VirtualMachineLifecycle {

    /**
     * VM started via launch or launchMany methods
     */
    NORMAL,

    /**
     * VM started via a spot VM request
     */
    SPOT,

}
