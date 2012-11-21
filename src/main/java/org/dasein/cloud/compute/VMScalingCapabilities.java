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

/**
 * Describes the ways in which a cloud can support vertical scaling.
 * <p>Created by George Reese: 11/18/12 7:57 PM</p>
 * @author George Reese
 * @version 2013.01 initial version (Issue #9)
 * @since 2013.01
 */
public class VMScalingCapabilities {
    static public VMScalingCapabilities getInstance(boolean newVm, boolean product) {
        VMScalingCapabilities capabilities = new VMScalingCapabilities();

        capabilities.createsNewVirtualMachine = newVm;
        capabilities.supportsProductChanges = product;
        return capabilities;
    }

    private boolean createsNewVirtualMachine;
    private boolean supportsProductChanges;

    private VMScalingCapabilities() { }

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
