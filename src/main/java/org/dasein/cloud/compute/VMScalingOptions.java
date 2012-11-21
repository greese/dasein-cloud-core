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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Options for vertical scaling of a running virtual machine.
 * <p>Created by George Reese: 11/18/12 7:52 PM</p>
 * @author George Reese
 * @version 2013.01 initial version (Issue #9)
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

    private String providerProductId;

    private VMScalingOptions() { }

    /**
     * @return the new product ID or <code>null</code> if the product is to remain the same
     */
    public @Nullable String getProviderProductId() {
        return providerProductId;
    }
}
