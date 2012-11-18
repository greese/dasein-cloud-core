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
package org.dasein.cloud.network;

/**
 * Describes a destination for a firewall rule.
 * <p>Created by George Reese: 11/18/12 9:55 AM</p>
 * @author George Reese
 * @version 2013.01 initial version (Issue #11)
 * @since 2013.01
 */
public enum DestinationType {
    GLOBAL, VLAN, VM, IP
}
