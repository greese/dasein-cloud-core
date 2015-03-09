/**
 * Copyright (C) 2009-2015 Dell, Inc.
 * See annotations for authorship information
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
