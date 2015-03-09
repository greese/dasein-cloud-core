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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * [Class Documentation]
 * <p>Created by George Reese: 5/14/13 10:11 AM</p>
 *
 * @author George Reese
 */
public interface CacheMBean {
    public void clear(@Nonnull String cacheName);

    public @Nullable CacheLevel getCacheLevel(@Nonnull String cacheName);

    public @Nonnull String[] getCaches();

    public @Nonnegative long getNextTimeout(@Nonnull String cacheName);

    public @Nonnegative long getTimeoutInSeconds(@Nonnull String cacheName);

    public void setTimeoutInSeconds(@Nonnull String cacheName, @Nonnegative long timeoutInSeconds);
}
