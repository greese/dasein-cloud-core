/**
 * Copyright (C) 2009-2014 Dell, Inc.
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

import org.dasein.cloud.VisibleScope;
import org.dasein.util.uom.storage.Gigabyte;
import org.dasein.util.uom.storage.Megabyte;
import org.dasein.util.uom.storage.Storage;

import javax.annotation.Nonnull;
import java.io.Serializable;

@SuppressWarnings("UnusedDeclaration")
public class VirtualMachineProduct implements Serializable {
    private static final long serialVersionUID = -6761551014614219494L;

    private int               cpuCount;
    private String            description;
    private Storage<Gigabyte> rootVolumeSize;
    private String            name;
    private String            providerProductId;
    private Storage<Megabyte> ramSize;
    private float             standardHourlyRate;
    private VisibleScope      visibleScope;
    private String            dataCenterId;

    public enum Status        { CURRENT, DEPRECATED; }

    private Status status = Status.CURRENT;

    public VirtualMachineProduct() { }
    
    public boolean equals(Object ob) {
        return (ob != null && (ob == this || getClass().getName().equals(ob.getClass().getName()) && getProviderProductId().equals(((VirtualMachineProduct) ob).getProviderProductId())));
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCpuCount() {
        return cpuCount;
    }

    public void setCpuCount(int cpuCount) {
        this.cpuCount = cpuCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getProviderProductId() {
        return providerProductId;
    }
    
    public void setProviderProductId(@Nonnull String providerProductId) {
        this.providerProductId = providerProductId;
    }

    public Storage<Gigabyte> getRootVolumeSize() {
        return rootVolumeSize;
    }

    public void setRootVolumeSize(Storage<?> rootVolumeSize) {
        this.rootVolumeSize = (Storage<Gigabyte>)rootVolumeSize.convertTo(Storage.GIGABYTE);
    }

    public Storage<Megabyte> getRamSize() {
        return ramSize;
    }

    public void setRamSize(Storage<?> ramSize) {
        this.ramSize = (Storage<Megabyte>)ramSize.convertTo(Storage.MEGABYTE);
    }

    public float getStandardHourlyRate() {
        return standardHourlyRate;
    }

    public void setStandardHourlyRate(float standardHourlyRate) {
        this.standardHourlyRate = standardHourlyRate;
    }

    public void setVisibleScope(VisibleScope visibleScope){
        this.visibleScope = visibleScope;
    }

    public VisibleScope getVisibleScope(){
        return this.visibleScope;
    }

    public String getDataCenterId(){
        return this.dataCenterId;
    }

    public void setDataCenterId(String dataCenterId){
        this.dataCenterId = dataCenterId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatusDeprecated() {
        this.status = Status.DEPRECATED;
    }

    public String toString() {
        return (name + " [" + providerProductId + "]");
    }
}
