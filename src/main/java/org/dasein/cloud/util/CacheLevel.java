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
package org.dasein.cloud.util;

/**
 * Defines what level data gets cached at.
 * <p>Created by George Reese: 11/16/12 4:58 PM</p>
 * @author George Reese
 * @version 2013.01 initial version
 * @since 2013.01
 */
public enum CacheLevel {
    /**
     * Cached data should be shared across all accounts and regions in the same cloud
     */
    CLOUD,
    /**
     * Cached data should be shared across all accounts in the same region
     */
    REGION,
    /**
     * Cached data should be shared across all regions for a specific account (not shared with other accounts)
     */
    CLOUD_ACCOUNT,
    /**
     * Cached data should not be shared with other accounts or regions
     */
    REGION_ACCOUNT
}
