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

/**
 * Indicates the level of support for a given feature when that support isn't binary.
 * <p>Created by George Reese: 6/22/12 10:17 PM</p>
 * @author George Reese (george.reese@imaginary.com)
 * @since 2012-07
 * @version 2012-07 initial version
 */
public enum Requirement {
    /**
     * This feature is not supported at all in the current context
     */
    NONE,
    /**
     * Use of this feature is optional in the current context
     */
    OPTIONAL,
    /**
     * Use of this feature is required in the current context
     */
    REQUIRED;
}
