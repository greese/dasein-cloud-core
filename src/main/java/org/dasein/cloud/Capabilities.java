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

package org.dasein.cloud;

import javax.annotation.Nonnull;

/**
 * Base interface for all capabilities objects.
 * <p>Created by George Reese: 2/27/14 3:42 PM</p>
 * @author George Reese
 * @version 2014.03 initial version
 * @since 2014.03
 */
public interface Capabilities {
    /**
     * No limit (used for capabilities methods that return a numeric limit)
     */
    static public final int LIMIT_UNLIMITED = -1;
    /**
     * The limit is unknown (used for capabilities methods that return a numeric limit)
     */
    static public final int LIMIT_UNKNOWN   = -2;

    /**
     * @return the account number under which these capabilities were tested
     */
    public @Nonnull String getAccountNumber();

    /**
     * @return the region ID under which these capabilities were tested
     */
    public @Nonnull String getRegionId();
}
