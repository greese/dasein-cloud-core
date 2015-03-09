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

import java.util.HashMap;
import java.util.Map;
import org.dasein.cloud.Taggable;
import org.dasein.util.uom.storage.*;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * <p>
 *   Represents a raw file or object stored in cloud storage.
 * </p>
 * @author George Reese @ enstratius (http://www.enstratius.com)
 */
public class Blob implements Comparable<Blob> , Taggable {
    static public @Nonnull Blob getInstance(@Nonnull String providerRegionId, @Nonnull String location, @Nonnull String bucketName, @Nonnegative long creationTimestamp) {
        Blob blob = new Blob();

        blob.creationTimestamp = creationTimestamp;
        blob.providerRegionId = providerRegionId;
        blob.bucketName = bucketName;
        blob.objectName = null;
        blob.location = location;
        blob.size = null;
        return blob;
    }

    static public @Nonnull Blob getInstance(@Nonnull String providerRegionId, @Nonnull String location, @Nullable String bucketName, String objectName, @Nonnegative long creationTimestamp, @Nonnull Storage<?> size) {
        Blob blob = new Blob();

        blob.creationTimestamp = creationTimestamp;
        blob.providerRegionId = providerRegionId;
        blob.bucketName = bucketName;
        blob.objectName = objectName;
        blob.location = location;
        blob.size = (Storage<org.dasein.util.uom.storage.Byte>)size.convertTo(Storage.BYTE);
        return blob;
    }

    private long                                      creationTimestamp;
    private String                                    bucketName;
    private String                                    location;
    private String                                    objectName;
    private String                                    providerRegionId;
    private Storage<org.dasein.util.uom.storage.Byte> size;
    private Map<String, String>                       tags;
    
    private Blob() { }

    @Override
    public int compareTo(@Nullable Blob other) {
        if( other == null ) {
            return 1;
        }
        if( other == this ) {
            return 0;
        }
        if( bucketName != null && other.bucketName == null ) {
            return 1;
        }
        else if( bucketName == null && other.bucketName != null ) {
            return -1;
        }
        return toString().compareTo(other.toString());
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if( other == null ) {
            return false;
        }
        if( other == this ) {
            return true;
        }
        if( !other.getClass().getName().equals(getClass().getName()) ) {
            return false;
        }
        Blob file = (Blob)other;
        if( bucketName == null ) {
            if( file.bucketName != null ) {
                return false;
            }
            if( objectName == null ) { // technically not possible
                return (file.objectName == null);
            }
            return objectName.equals(file.objectName);
        }
        return (bucketName.equals(file.bucketName) && ((objectName == null && file.objectName == null) || (objectName != null && objectName.equals(file.objectName))));
    }

    public @Nullable String getBucketName() {
        return bucketName;
    }

    public boolean isContainer() {
        return (objectName == null);
    }

    public @Nonnegative long getCreationTimestamp() {
        return creationTimestamp;
    }

    public @Nonnull String getLocation() {
        return location;
    }

    public @Nullable String getObjectName() {
        return objectName;
    }

    public @Nonnull String getProviderRegionId() {
        return providerRegionId;
    }

    public @Nullable Storage<org.dasein.util.uom.storage.Byte> getSize() {
        return size;
    }

    @Override
    public @Nonnull String toString() {
        return ((bucketName == null ? "/" : (bucketName.startsWith("/") ? bucketName : "/" + bucketName)) + (objectName == null ? "" : ("/" + objectName)));
    }

    @Override
    public @Nonnull Map<String, String> getTags() {
        if( tags == null ) {
            tags = new HashMap<String, String>();
        }
        return tags;
    }

    @Override
    public void setTag(@Nonnull String key, @Nonnull String value) {
        getTags().put(key, value);
    }
}
