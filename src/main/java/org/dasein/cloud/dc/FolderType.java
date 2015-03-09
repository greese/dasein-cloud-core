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

package org.dasein.cloud.dc;

/**
 * <p>
 * The various possible types of folder which can exist. Implementors should do their best to
 * map cloud-specific folder types to these types.
 * </p>
 * User: daniellemayne
 * Date: 18/08/2014
 * Time: 12:19
 */
public enum FolderType {
    /**
     * The folder can contain child compute resource folders, and compute resource hierarchies.
     */
    COMPUTE_RESOURCE,
    /**
     * The folder can contain child datacenter folders, and datacenter objects.
     * Datacenter objects contain virtual machine, compute resource, network entity, and datastore folders
     */
    DATACENTER,
    /**
     * The folder can contain child datastore folders, and datastore objects.
     */
    DATASTORE,
    /**
     * The folder can contain child network folders, and network, distributed virtual switch,
     * and distributed virtual port group objects.
     */
    NETWORK,
    /**
     * The folder can contain child vm folders, and VM, template and virtual application objects.
     */
    VM
}
