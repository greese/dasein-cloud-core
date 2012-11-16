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
package org.dasein.cloud;

import javax.annotation.Nonnull;

/**
 * Represents the current status of a specific kind of resource. This is used by listXXXStatus() methods in different
 * support objects so you can quickly fetch the status of a set of cloud resources without having to fetch all
 * associated data. Fetching the status is a good way to check for large-scale state changes which may need to be
 * more frequent than detailed state changes.
 * <p>Created by George Reese: 11/16/12 12:11 PM</p>
 * @author George Reese
 * @version 2013.01 initial version (Issue #4)
 * @since 2013.01
 */
public class ResourceStatus {
    private String providerResourceId;
    private Object resourceStatus;

    public ResourceStatus(@Nonnull String id, @Nonnull Object status) {
        providerResourceId = id;
        resourceStatus = status;
    }

    public String getProviderResourceId() {
        return providerResourceId;
    }

    public Object getResourceStatus() {
        return resourceStatus;
    }

    public String toString() {
        return (providerResourceId + " [" + resourceStatus + "]");
    }
}
