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

package org.dasein.cloud.storage;

import org.dasein.cloud.AbstractProviderService;
import org.dasein.cloud.CloudProvider;

import javax.annotation.Nullable;

/**
 * Abstract base class for implementing storage services for any generic cloud. Using this base class, you need override
 * only the methods that provide actual functionality in the target cloud.
 * @author George Reese
 * @version 2013.07 added support for online and offline storage (issue #66)
 * @since unknown
 */
public abstract class AbstractStorageServices<T extends CloudProvider> extends AbstractProviderService<T> implements StorageServices {

    protected AbstractStorageServices(T provider) {
        super(provider);
    }

    @Override
    @Deprecated
    public final @Nullable BlobStoreSupport getBlobStoreSupport() {
        BlobStoreSupport s = getOnlineStorageSupport();

        if( s == null ) {
            return getOfflineStorageSupport();
        }
        return s;
    }

    @Override
    @Deprecated
    public final boolean hasBlobStoreSupport() {
        return (hasOnlineStorageSupport() || hasOfflineStorageSupport());
    }

    @Override
    public @Nullable OfflineStoreSupport getOfflineStorageSupport() {
        return null;
    }

    @Override
    public boolean hasOfflineStorageSupport() {
        return (getOfflineStorageSupport() != null);
    }

    @Override
    public @Nullable BlobStoreSupport getOnlineStorageSupport() {
        return null;
    }

    @Override
    public boolean hasOnlineStorageSupport() {
        return (getOnlineStorageSupport() != null);
    }
}
