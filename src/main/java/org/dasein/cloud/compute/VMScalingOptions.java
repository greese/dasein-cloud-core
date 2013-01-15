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
    private VolumeCreateOptions[]         volumes;

    private VMScalingOptions() { }

    /**
     * @return the new product ID or <code>null</code> if the product is to remain the same
     */
    public @Nullable String getProviderProductId() {
        return providerProductId;
    }

    /**
     * Provides a list of volumes to be scaled. The list may contain null entries and may be shorter or longer than
     * the current list of volumes. If longer, it means new volumes should be added. If shorter, it means volumes beyond
     * the end of the list should be left alone. If there's a null entry, it means the corresponding current volume
     * should be left alone. There is no mechanism for removing volumes.
     * @return a list of volumes to be scaled
     */
    public @Nonnull VolumeCreateOptions[] getVolumes() {
        return (volumes == null ? new VolumeCreateOptions[0] : volumes);
    }
    /**
     * Identifies that this scaling operation will create/modify volumes
     * @param options the list of options for creating the new volumes
     * @return this
     */
    public @Nonnull VMScalingOptions withVolumes(@Nonnull VolumeCreateOptions ... options) {
        volumes = options;
        return this;
    }
}
