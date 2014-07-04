package org.dasein.cloud.dc;

/**
 * User: daniellemayne
 * Date: 01/07/2014
 * Time: 11:41
 */
public class ResourcePool {
    private String name;
    private String dataCenterId;
    private String provideResourcePoolId;
    private boolean available;

    public ResourcePool() {
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getProvideResourcePoolId() {
        return provideResourcePoolId;
    }

    public void setProvideResourcePoolId(String provideResourcePoolId) {
        this.provideResourcePoolId = provideResourcePoolId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataCenterId() {
        return dataCenterId;
    }

    public void setDataCenterId(String dataCenterId) {
        this.dataCenterId = dataCenterId;
    }
}