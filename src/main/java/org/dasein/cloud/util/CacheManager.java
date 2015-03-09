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
 * Provides JMX access into cache management functionality. Through this bean, you can fetch a list of active caches, get
 * the meta-data around them, and clear them out.
 * <p>Created by George Reese: 5/14/13 2:54 PM</p>
 * @author George Reese
 * @version 2013.07 (issue #58)
 * @since 2013.07
 */
public class CacheManager implements CacheMBean {
    private Cache.CacheDelegate collections         = new Cache.CacheDelegate();
    private SingletonCache.CacheDelegate singletons = new SingletonCache.CacheDelegate();

    @Override
    public void clear(@Nonnull String cacheName) {
        collections.clear(cacheName);
    }

    @Override
    public @Nonnull String[] getCaches() {
        String[] s = singletons.getCaches();
        String[] c = collections.getCaches();
        String[] names = new String[s.length + c.length];
        int i = 0;

        for( String str : s ) {
            names[i++] = str;
        }
        for( String str : c ) {
            names[i++] = str;
        }
        return names;
    }

    @Override
    public @Nullable CacheLevel getCacheLevel(@Nonnull String cacheName) {
        CacheLevel l = collections.getCacheLevel(cacheName);

        if( l == null ) {
            l = singletons.getCacheLevel(cacheName);
        }
        return l;
    }

    @Override
    public long getNextTimeout(@Nonnull String cacheName) {
        CacheLevel l = collections.getCacheLevel(cacheName);

        if( l == null ) {
            return singletons.getNextTimeout(cacheName);
        }
        return collections.getNextTimeout(cacheName);
    }

    @Override
    public long getTimeoutInSeconds(@Nonnull String cacheName) {
        CacheLevel l = collections.getCacheLevel(cacheName);

        if( l == null ) {
            return singletons.getTimeoutInSeconds(cacheName);
        }
        return collections.getTimeoutInSeconds(cacheName);
    }

    @Override
    public void setTimeoutInSeconds(@Nonnull String cacheName, @Nonnegative long timeoutInSeconds) {
        CacheLevel l = collections.getCacheLevel(cacheName);

        if( l == null ) {
            singletons.setTimeoutInSeconds(cacheName, timeoutInSeconds);
        }
        else {
            collections.setTimeoutInSeconds(cacheName, timeoutInSeconds);
        }
    }
}
