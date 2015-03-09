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

import javax.annotation.Nullable;

/**
 * Services representing various kinds of object storage in the cloud.
 * <p>Created by George Reese: 13:48 PM</p>
 * @author George Reese
 * @version 2013.07 added support for online (S3) and offline (glacier) storage options (issue #66)
 * @since unknown
 */
public interface StorageServices {
    /**
     * Provides access to the online blob storage for the cloud provider, if it exists. Otherwise, will provide
     * access to the offline blob storage or <code>null</code> if no blob storage of any kind exists.
     * @return the highest level blob storage available
     * @deprecated Use {@link #getOnlineStorageSupport()} or {@link #getOfflineStorageSupport()}
     */
    public @Nullable BlobStoreSupport getBlobStoreSupport();

    /**
     * @return true if this cloud is configered with any kind of blob storage support
     * @deprecated Use {@link #hasOnlineStorageSupport()} or {@link #hasOfflineStorageSupport()}
     */
    public boolean hasBlobStoreSupport();

    /**
     * Offline storage is slower storage that is generally less expensive than online storage, but may take hours
     * to fetch.
     * @return blob store support for the offline storage, if it exists
     */
    public @Nullable OfflineStoreSupport getOfflineStorageSupport();

    /**
     * @return true if the cloud supports offline storage
     */
    public boolean hasOfflineStorageSupport();

    /**
     * Online storage is a &quot;real-time&quot; object storage engine such as Riak CS or Amazon S3.
     * @return blob store support for the online storage, if it exists
     */
    public @Nullable BlobStoreSupport getOnlineStorageSupport();

    /**
     * @return true if the cloud supports online storage
     */
    public boolean hasOnlineStorageSupport();
}
