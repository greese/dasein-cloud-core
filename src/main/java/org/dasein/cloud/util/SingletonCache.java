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

import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.ProviderContext;
import org.dasein.util.CalendarWrapper;
import org.dasein.util.uom.time.Hour;
import org.dasein.util.uom.time.Millisecond;
import org.dasein.util.uom.time.TimePeriod;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implements efficient caching of non-changing resources so that you can minimize the number of API calls being made
 * to a cloud provider. This is similar to the general {@link Cache} class, except it caches only singleton objects.
 * <p>
 * Example:
 * </p>
 * <pre>
 *     private String getToken() {
 *         SingletonCache&lt;String&gt; cache = SingletonCache.getInstance(provider, "token", String.class, CacheLevel.CLOUD_ACCOUNT);
 *         String token = cache.get(provider.getContext());
 *
 *         if( token == null ) {
 *             // make API call to authenticate
 *             cache.put(provider.getContext(), token);
 *         }
 *         return token;
 *     }
 * </pre>
 * <p>Created by George Reese: 6/27/2013 3:52 PM</p>
 * @author George Reese
 * @version 2013.07 initial version
 * @since 2013.07
 */
public final class SingletonCache<T> {
    static private final HashMap<String,SingletonCache<?>> caches = new HashMap<String, SingletonCache<?>>();

    static public class CacheDelegate implements CacheMBean {
        @Override
        public void clear(@Nonnull String cacheName) {
            SingletonCache<?> c;

            synchronized( caches ) {
                if( caches.containsKey(cacheName) ) {
                    //noinspection unchecked
                    c = caches.get(cacheName);
                }
                else {
                    return;
                }
            }
            c.clear();
        }

        @Override
        public @Nonnull String[] getCaches() {
            Set<String> names;

            synchronized( caches ) {
                names = caches.keySet();
            }
            return names.toArray(new String[names.size()]);
        }

        @Override
        public @Nullable CacheLevel getCacheLevel(@Nonnull String cacheName) {
            SingletonCache<?> c;

            synchronized( caches ) {
                if( caches.containsKey(cacheName) ) {
                    //noinspection unchecked
                    c = caches.get(cacheName);
                }
                else {
                    return null;
                }
            }
            if( c.cloudCache != null ) {
                return CacheLevel.CLOUD;
            }
            else if( c.cloudAccountCache != null ) {
                return CacheLevel.CLOUD_ACCOUNT;
            }
            else if( c.regionCache != null ) {
                return CacheLevel.REGION;
            }
            else if( c.regionAccountCache != null ) {
                return CacheLevel.REGION_ACCOUNT;
            }
            return null;
        }

        @Override
        public long getNextTimeout(@Nonnull String cacheName) {
            SingletonCache<?> c;

            synchronized( caches ) {
                if( caches.containsKey(cacheName) ) {
                    //noinspection unchecked
                    c = caches.get(cacheName);
                }
                else {
                    return System.currentTimeMillis();
                }
            }
            return (c.cacheStart + c.cacheTimeout.longValue());
        }

        @Override
        public long getTimeoutInSeconds(@Nonnull String cacheName) {
            SingletonCache<?> c;

            synchronized( caches ) {
                if( caches.containsKey(cacheName) ) {
                    //noinspection unchecked
                    c = caches.get(cacheName);
                }
                else {
                    return 0L;
                }
            }
            return (c.cacheTimeout.longValue()/1000L);
        }

        public void setTimeoutInSeconds(@Nonnull String cacheName, @Nonnegative long timeoutInSeconds) {
            SingletonCache<?> c;

            synchronized( caches ) {
                if( caches.containsKey(cacheName) ) {
                    //noinspection unchecked
                    c = caches.get(cacheName);
                }
                else {
                    return;
                }
            }
            c.cacheTimeout = new TimePeriod<Millisecond>(timeoutInSeconds, TimePeriod.MILLISECOND);
        }
    }

    static private class CacheEntry<T> {
        public long lastCacheClear;
        public SoftReference<T> item;
        public @Nonnull String toString() { return ((item == null) ? "--> empty <--" : item.toString()); }
    }



    /**
     * Provides access to a cache for items under the specified name.
     * @param provider the cloud provider object governing the cache
     * @param name the name of the cache
     * @param level the level at which these objects should be cached
     * @param <X> the type of the object being cached
     * @return a cache containing the context-sensitive cached items
     */
    static public @Nonnull <X> SingletonCache<X> getInstance(@Nonnull CloudProvider provider, @Nonnull String name, @Nonnull CacheLevel level) {
        return getInstance(provider, name, level, new TimePeriod<Hour>(1, TimePeriod.HOUR));
    }

    /**
     * Provides access to a cache for items under the specified name.
     * @param provider the cloud provider object governing the cache
     * @param name the name of the cache
     * @param level the level at which these objects should be cached
     * @param timeout the amount of time before the cache is automatically considered stale and forces you to reload from API
     * @param <X> the type of the object being cached
     * @return a cache containing the context-sensitive cached items
     */
    static public @Nonnull <X> SingletonCache<X> getInstance(@Nonnull CloudProvider provider, @Nonnull String name, @Nonnull CacheLevel level, @Nonnegative TimePeriod<?> timeout) {
        SingletonCache<X> c;

        name = provider.getClass().getName() + "." + name;
        synchronized( caches ) {
            if( caches.containsKey(name) ) {
                //noinspection unchecked
                c = (SingletonCache<X>)caches.get(name);
            }
            else {
                c = new SingletonCache<X>(level, timeout);
                caches.put(name, c);
            }
        }
        return c;
    }

    private HashMap<String,CacheEntry<T>>                         cloudCache;
    private HashMap<String,Map<String,CacheEntry<T>>>             cloudAccountCache;
    private HashMap<String,Map<String,CacheEntry<T>>>             regionCache;
    private HashMap<String,Map<String,Map<String,CacheEntry<T>>>> regionAccountCache;

    private TimePeriod<Millisecond> cacheTimeout;
    private long                    cacheStart;

    private SingletonCache() { }

    private SingletonCache(CacheLevel level, TimePeriod<?> timeout) {
        switch( level ) {
            case CLOUD: cloudCache = new HashMap<String, CacheEntry<T>>(); break;
            case CLOUD_ACCOUNT: cloudAccountCache = new HashMap<String, Map<String, CacheEntry<T>>>(); break;
            case REGION: regionCache = new HashMap<String, Map<String, CacheEntry<T>>>(); break;
            case REGION_ACCOUNT: regionAccountCache = new HashMap<String, Map<String, Map<String, CacheEntry<T>>>>(); break;
        }
        cacheTimeout = (TimePeriod<Millisecond>)timeout.convertTo(TimePeriod.MILLISECOND);
        cacheStart = System.currentTimeMillis();
    }

    /**
     * Clears out the cache across the board, regardless of context.
     */
    public void clear() {
        synchronized( this ) {
            if( cloudCache != null ) {
                cloudCache.clear();
            }
            else if( cloudAccountCache != null ) {
                cloudAccountCache.clear();
            }
            else if( regionCache != null ) {
                regionCache.clear();
            }
            else if( regionAccountCache != null ) {
                regionAccountCache.clear();
            }
            cacheStart = System.currentTimeMillis();
        }
    }

    /**
     * Fetches the item currently cached for the context specified. Depending on the caching level, this
     * method may return different values for different contexts. If the returned value is null, that means
     * nothing is cached and you should refetch and then cache the results.
     * @param ctx the context for the caching
     * @return the item currently in the cache if one is currently cached
     */
    public @Nullable T get(@Nonnull ProviderContext ctx) {
        synchronized( this ) {
            if( System.currentTimeMillis() > (cacheStart + CalendarWrapper.DAY) ) {
                clear();
                return null;
            }
        }
        CacheEntry<T> entry = null;
        String endpoint = ctx.getCloud().getEndpoint();

        if( cloudCache != null ) {
            entry = cloudCache.get(endpoint);
        }
        else if( regionCache != null ) {
            Map<String,CacheEntry<T>> map = regionCache.get(endpoint);

            if( map != null ) {
                entry = map.get(ctx.getRegionId());
            }
        }
        else if( cloudAccountCache != null ) {
            Map<String,CacheEntry<T>> map = cloudAccountCache.get(endpoint);

            if( map != null ) {
                entry = map.get(ctx.getAccountNumber());
            }
        }
        else if( regionAccountCache != null ) {
            Map<String,Map<String,CacheEntry<T>>> map = regionAccountCache.get(endpoint);

            if( map != null ) {
                Map<String,CacheEntry<T>> rmap = map.get(ctx.getRegionId());

                if( rmap != null ) {
                    entry = rmap.get(ctx.getAccountNumber());
                }
            }
        }
        if( entry == null ) {
            return null;
        }
        if( entry.lastCacheClear + cacheTimeout.longValue() < System.currentTimeMillis() ) {
            entry.item = null;
            return null;
        }
        return entry.item.get();
    }

    /**
     * Places a singleton item into the cache for the specified context.
     * @param ctx the context of the cache
     * @param item the item to be cached
     */
    public void put(@Nonnull ProviderContext ctx, @Nonnull T item) {
        CacheEntry<T> entry = new CacheEntry<T>();
        String endpoint = ctx.getCloud().getEndpoint();

        entry.item = new SoftReference<T>(item);
        entry.lastCacheClear = System.currentTimeMillis();
        if( cloudCache != null ) {
            cloudCache.put(endpoint, entry);
        }
        else if( regionCache != null ) {
            Map<String,CacheEntry<T>> map = regionCache.get(endpoint);

            if( map == null ) {
                map = new HashMap<String, CacheEntry<T>>();
                regionCache.put(endpoint, map);
            }
            map.put(ctx.getRegionId(), entry);
        }
        else if( cloudAccountCache != null ) {
            Map<String,CacheEntry<T>> map = cloudAccountCache.get(endpoint);

            if( map == null ) {
                map = new HashMap<String, CacheEntry<T>>();
                cloudAccountCache.put(endpoint, map);
            }
            map.put(ctx.getAccountNumber(), entry);
        }
        else if( regionAccountCache != null ) {
            Map<String,Map<String,CacheEntry<T>>> map = regionAccountCache.get(endpoint);

            if( map == null ) {
                map = new HashMap<String, Map<String, CacheEntry<T>>>();
                regionAccountCache.put(endpoint, map);
            }

            Map<String,CacheEntry<T>> rmap = map.get(ctx.getRegionId());

            if( rmap == null ) {
                rmap = new HashMap<String, CacheEntry<T>>();
                map.put(ctx.getRegionId(), rmap);
            }
            rmap.put(ctx.getAccountNumber(), entry);
        }
    }
}
