/**
 * Copyright (C) 2009-2013 Dell, Inc.
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
 * Provides JMX access into cache management functionality. Through this bean, you can fetch a list of active caches, get
 * the meta-data around them, and clear them out.
 * <p>Created by George Reese: 5/14/13 2:54 PM</p>
 * @author George Reese
 * @version 2013.07 (issue #58)
 * @since 2013.07
 */
public class CacheManager implements CacheMBean {
    private Cache.CacheDelegate delegate = new Cache.CacheDelegate();

    @Override
    public void clear(@Nonnull String cacheName) {
        delegate.clear(cacheName);
    }

    @Override
    public @Nonnull String[] getCaches() {
        return delegate.getCaches();
    }

    @Override
    public @Nullable CacheLevel getCacheLevel(@Nonnull String cacheName) {
        return delegate.getCacheLevel(cacheName);
    }

    @Override
    public long getNextTimeout(@Nonnull String cacheName) {
        return delegate.getNextTimeout(cacheName);
    }

    @Override
    public long getTimeoutInSeconds(@Nonnull String cacheName) {
        return delegate.getTimeoutInSeconds(cacheName);
    }

    @Override
    public void setTimeoutInSeconds(@Nonnull String cacheName, @Nonnegative long timeoutInSeconds) {
        delegate.setTimeoutInSeconds(cacheName, timeoutInSeconds);
    }
}
