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
 * Defines what kind of image (machine, ramdisk, kernel) a "MachineImage" represents.
 * <p>Created by George Reese: 11/14/12 3:03 PM</p>
 * @author George Reese
 * @version 2013.01 initial version (Issue #7)
 * @since 2013.01
 */
public enum ImageClass {
    MACHINE, RAMDISK, KERNEL
}
