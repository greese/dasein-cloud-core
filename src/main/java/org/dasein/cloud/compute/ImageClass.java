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
 * Defines what kind of image (machine, ramdisk, kernel) a "MachineImage" represents.
 * <p>Created by George Reese: 11/14/12 3:03 PM</p>
 * @author George Reese
 * @version 2013.01 initial version (Issue #7)
 * @since 2013.01
 */
public enum ImageClass {
    MACHINE, RAMDISK, KERNEL
}
