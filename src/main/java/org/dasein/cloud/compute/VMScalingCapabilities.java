/**
 * ========= CONFIDENTIAL =========
 *
 * Copyright (C) 2012 enStratus Networks Inc - ALL RIGHTS RESERVED
 *
 * ====================================================================
 *  NOTICE: All information contained herein is, and remains the
 *  property of enStratus Networks Inc. The intellectual and technical
 *  concepts contained herein are proprietary to enStratus Networks Inc
 *  and may be covered by U.S. and Foreign Patents, patents in process,
 *  and are protected by trade secret or copyright law. Dissemination
 *  of this information or reproduction of this material is strictly
 *  forbidden unless prior written permission is obtained from
 *  enStratus Networks Inc.
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
    static public VMScalingCapabilities getInstance(boolean newVm, boolean product, @Nonnull Requirement alterVmForNewVolume, @Nonnull Requirement alterVmForVolumeChange) {
        VMScalingCapabilities capabilities = new VMScalingCapabilities();

        capabilities.createsNewVirtualMachine = newVm;
        capabilities.supportsProductChanges = product;
        capabilities.alterVmForNewVolume = alterVmForNewVolume;
        capabilities.alterVmForVolumeChange = alterVmForVolumeChange;
        return capabilities;
    }

    private Requirement alterVmForNewVolume;
    private Requirement alterVmForVolumeChange;
    private boolean     createsNewVirtualMachine;
    private boolean     supportsProductChanges;

    private VMScalingCapabilities() { }

    /**
     * Indicates whether or not you must alter a virtual machine in order to attach new volumes to virtual machines.
     * If this is not required and the cloud supports volume attachment, then you simply use the normal
     * {@link VolumeSupport#attach(String, String, String)} method. If it is required, however, you use
     * {@link VirtualMachineSupport#alterVirtualMachine(String, VMScalingOptions)}.
     * @return the requirement for handling new volumes as an alter VM operation
     */
    public @Nonnull Requirement getAlterVmForNewVolume() {
        return alterVmForNewVolume;
    }

    /**
     * Indicates whether or not you must alter a virtual machine in order to resize attached volumes. If
     * {@link Requirement#NONE}, then either you cannot resize volumes or you must do that through mechanisms other
     * than {@link VirtualMachineSupport#alterVirtualMachine(String, VMScalingOptions)}.
     * @return the requirement for handling volume resizing as an alter VM operation
     */
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
     * @return true if you can scale up/down product
     */
    public boolean isSupportsProductChanges() {
        return supportsProductChanges;
    }
}
