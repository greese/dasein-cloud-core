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

import org.dasein.util.uom.storage.*;
import org.dasein.util.uom.storage.Byte;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * An abstracted offline storage request. This request may take hours to complete and
 * the status may be queried using its request ID.
 */
public class OfflineStoreRequest {

    private String                                    requestId;
    private String                                    bucketName;
    private String                                    objectName;
    private OfflineStoreRequestAction                 action;
    private String                                    actionDescription;
    private Storage<org.dasein.util.uom.storage.Byte> size;
    private String                                    description;
    private OfflineStoreRequestStatus                 status;
    private String                                    statusDescription;
    private long                                      creationTimestamp;
    private long                                      completionTimestamp;

    public OfflineStoreRequest(String requestId, String bucketName, String objectName,
                               OfflineStoreRequestAction action, String actionDescription,
                               Storage<Byte> size, String description, OfflineStoreRequestStatus status,
                               String statusDescription, long creationTimestamp, long completionTimestamp) {
        this.requestId = requestId;
        this.bucketName = bucketName;
        this.objectName = objectName;
        this.action = action;
        this.actionDescription = actionDescription;
        this.size = size;
        this.description = description;
        this.status = status;
        this.statusDescription = statusDescription;
        this.creationTimestamp = creationTimestamp;
        this.completionTimestamp = completionTimestamp;
    }

    /**
     * The provider-specific identifier for the request.
     * @return the provider-specific request identifier
     */
    public @Nonnull String getRequestId() {
        return requestId;
    }

    /**
     * The name of the storage bucket being requested. This may be null for
     * requests not pertaining to a specific bucket.
     * @return storage bucket name
     */
    public @Nullable String getBucketName() {
        return bucketName;
    }

    /**
     * The name of the storage object being requested. This may be null for
     * requests not pertaining to a specific object.
     * @return storage object name
     */
    public @Nullable String getObjectName() {
        return objectName;
    }

    /**
     * The provider action for this request. Action types unknown to dasein
     * will return as UNKNOWN
     * @return request action
     */
    public @Nonnull OfflineStoreRequestAction getAction() {
        return action;
    }

    /**
     * The provider action description string
     * @return request action description, specific to provider
     */
    public @Nullable String getActionDescription() {
        return actionDescription;
    }

    /**
     * The expected size of the request result. This may be null when this information
     * is unavailable.
     * @return size of request result, if available
     */
    public @Nullable Storage<org.dasein.util.uom.storage.Byte> getSize() {
        return size;
    }

    /**
     * The user or provider description of the request
     * @return user or provider description of the request
     */
    public @Nullable String getDescription() {
        return description;
    }

    /**
     * Current status of the request
     * @return status of request
     */
    public @Nonnull OfflineStoreRequestStatus getStatus() {
        return status;
    }

    /**
     * Provider description of the request status
     * @return provider description of request status
     */
    public @Nonnull String getStatusDescription() {
        return statusDescription;
    }

    /**
     * Timestamp of request creation
     * @return timestamp of request creation
     */
    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    /**
     * Timestamp of request completion. Will be -1 if request is not completed.
     * @return timestamp of request completion, or -1
     */
    public long getCompletionTimestamp() {
        return completionTimestamp;
    }
}
