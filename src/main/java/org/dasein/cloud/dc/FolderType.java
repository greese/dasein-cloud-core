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
