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

import org.dasein.cloud.Requirement;

import javax.annotation.Nonnull;

/**
 * Describes the ways in which a cloud can support vertical scaling.
 * <p>Created by George Reese: 11/18/12 7:57 PM</p>
 * @author George Reese
 * @version 2013.01 initial version (Issue #9)
 * @version 2013.02 added support for new volumes/volume resizing as a VM alteration (issue #30)
 * @since 2013.01
 */
public class VMScalingCapabilities {
    @Deprecated
    static public VMScalingCapabilities getInstance(boolean newVm, boolean product, @Nonnull Requirement alterVmForNewVolume, @Nonnull Requirement alterVmForVolumeChange) {
        VMScalingCapabilities capabilities = new VMScalingCapabilities();

        capabilities.createsNewVirtualMachine = newVm;
        capabilities.supportsProductChanges = product;
        capabilities.alterVmForNewVolume = alterVmForNewVolume;
        capabilities.alterVmForVolumeChange = alterVmForVolumeChange;
        return capabilities;
    }

    static public VMScalingCapabilities getInstance(boolean newVm, boolean productChange, boolean productSizeChange) {
        VMScalingCapabilities capabilities = new VMScalingCapabilities();

        capabilities.createsNewVirtualMachine = newVm;
        capabilities.supportsProductChanges = productChange;
        capabilities.supportsProductSizeChanges = productSizeChange;
        return capabilities;
    }

    @Deprecated private Requirement alterVmForNewVolume;
    @Deprecated private Requirement alterVmForVolumeChange;
    private boolean     createsNewVirtualMachine;
    private boolean     supportsProductChanges;
    private boolean     supportsProductSizeChanges;

    private VMScalingCapabilities() { }

    /**
     * Indicates whether or not you must alter a virtual machine in order to attach new volumes to virtual machines.
     * If this is not required and the cloud supports volume attachment, then you simply use the normal
     * {@link VolumeSupport#attach(String, String, String)} method. If it is required, however, you use
     * {@link VirtualMachineSupport#alterVirtualMachine(String, VMScalingOptions)}.
     * @return the requirement for handling new volumes as an alter VM operation
     */
    @Deprecated
    public @Nonnull Requirement getAlterVmForNewVolume() {
        return alterVmForNewVolume;
    }

    /**
     * Indicates whether or not you must alter a virtual machine in order to resize attached volumes. If
     * {@link Requirement#NONE}, then either you cannot resize volumes or you must do that through mechanisms other
     * than {@link VirtualMachineSupport#alterVirtualMachine(String, VMScalingOptions)}.
     * @return the requirement for handling volume resizing as an alter VM operation
     */
    @Deprecated
    public @Nonnull Requirement getAlterVmForVolumeChange() {
        return alterVmForVolumeChange;
    }

    /**
     * Indicates that vertical scaling will result in a new virtual machine with a new unique ID to replace the
     * old virtual machine. Otherwise known as, not really vertical scaling.
     * @return true if a new virtual machine gets created
     */
    public boolean isCreatesNewVirtualMachine() {
        return createsNewVirtualMachine;
    }

    /**
     * This indicates the scalability of distinct, named products.
     * @return true if you can scale up/down product
     */
    public boolean isSupportsProductChanges() {
        return supportsProductChanges;
    }

    /**
     * This indicates the scalability of non-distinct, non-named product sizes
     * @return true if you can scale the product size up/down
     */
    public boolean isSupportsProductSizeChanges(){
        return supportsProductSizeChanges;
    }

    public @Nonnull VMScalingCapabilities withSupportsProductSizeScaling(boolean supportsProductSizeChanges){
        this.supportsProductSizeChanges = supportsProductSizeChanges;
        return this;
    }
}
