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

import org.dasein.util.uom.storage.Gigabyte;
import org.dasein.util.uom.storage.Storage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Map;

/**
 * Options for vertical scaling of a running virtual machine.
 * <p>Created by George Reese: 11/18/12 7:52 PM</p>
 * @author George Reese
 * @version 2013.01 initial version (Issue #9)
 * @version 2013.02 added volume scaling options (issue #30)
 * @since 2013.01
 */
public class VMScalingOptions {
    /**
     * Provides scaling options for scaling based purely on product
     * @param newProductId the new product ID
     * @return a VM scaling options object configured for the desired options
     */
    static public VMScalingOptions getInstance(@Nonnull String newProductId) {
        VMScalingOptions options = new VMScalingOptions();

        options.providerProductId = newProductId;
        return options;
    }

    private String                        providerProductId;
    private VolumeCreateOptions[]         volumesToCreate;
    private Storage<Gigabyte>[]           volumesToResize;

    private VMScalingOptions() { }

    /**
     * @return the new product ID or <code>null</code> if the product is to remain the same
     */
    public @Nullable String getProviderProductId() {
        return providerProductId;
    }

    /**
     * @return the create options for the volumes to create an attach as part of this alteration process
     */
    public @Nonnull VolumeCreateOptions[] getVolumesToCreate() {
        return (volumesToCreate == null ? new VolumeCreateOptions[0] : volumesToCreate);
    }

    /**
     * @return the volumes to resize as part of the alteration process
     */
    public @Nonnull Storage<Gigabyte>[] getVolumesToResize() {
        if( volumesToResize == null ) {
            return (Storage<Gigabyte>[])(new Storage[0]);
        }
        return volumesToResize;
    }

    /**
     * Identifies that this scaling operation will create volumes to be attached during the scale.
     * @param options the list of options for creating the new volumes
     * @return this
     */
    public @Nonnull VMScalingOptions withVolumesToCreate(@Nonnull VolumeCreateOptions ... options) {
        volumesToCreate = options;
        return this;
    }

    /**
     * Indicates that one or more of the volumes currently attached to the VM will be resized. The match is done by
     * index. If a volume is not to be resized, it should have a null entry in this call. The number of volumes in
     * this call may be less than the number of volumes attached to the VM. In that case, the extra volumes are to
     * be unchanged during the alter VM process.
     * @param volumes the new sizes of the volumes attached to the VM with null entries indicating no change
     * @return this
     */
    public @Nonnull VMScalingOptions withVolumeToResize(@Nonnull Storage<Gigabyte> ... volumes) {
        volumesToResize = volumes;
        return this;
    }
}
