package org.dasein.cloud.dc;

import org.dasein.util.uom.storage.Megabyte;
import org.dasein.util.uom.storage.Storage;

/**
 * User: daniellemayne
 * Date: 01/08/2014
 * Time: 08:28
 */
public class StoragePool {

    private String storagePoolId;
    private String storagePoolName;
    private String regionId;
    private String dataCenterId;
    private String affinityGroupId;
    private Storage<Megabyte> capacity;
    private Storage<Megabyte> provisioned;
    private Storage<Megabyte> freeSpace;

    public String getAffinityGroupId() {
        return affinityGroupId;
    }

    public void setAffinityGroupId(String affinityGroupId) {
        this.affinityGroupId = affinityGroupId;
    }

    public String getDataCenterId() {
        return dataCenterId;
    }

    public void setDataCenterId(String dataCenterId) {
        this.dataCenterId = dataCenterId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getStoragePoolName() {
        return storagePoolName;
    }

    public void setStoragePoolName(String storagePoolName) {
        this.storagePoolName = storagePoolName;
    }

    public String getStoragePoolId() {
        return storagePoolId;
    }

    public void setStoragePoolId(String storagePoolId) {
        this.storagePoolId = storagePoolId;
    }

    public Storage<Megabyte> getCapacity() {
        return capacity;
    }

    public void setCapacity(Storage<Megabyte> capacity) {
        this.capacity = capacity;
    }

    public Storage<Megabyte> getProvisioned() {
        return provisioned;
    }

    public void setProvisioned(Storage<Megabyte> provisioned) {
        this.provisioned = provisioned;
    }

    public Storage<Megabyte> getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(Storage<Megabyte> freeSpace) {
        this.freeSpace = freeSpace;
    }

}
