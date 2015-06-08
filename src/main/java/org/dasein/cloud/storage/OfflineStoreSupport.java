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

import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.identity.ServiceAction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;

/**
 * Extends BlobStoreSupport with methods for potentially long-running offline storage requests
 */
public interface OfflineStoreSupport extends BlobStoreSupport {

    static public final ServiceAction CREATE_REQUEST      = new ServiceAction("OFFLINESTORE:CREATE_REQUEST");
    static public final ServiceAction GET_REQUEST         = new ServiceAction("OFFLINESTORE:GET_REQUEST");
    static public final ServiceAction LIST_REQUEST        = new ServiceAction("OFFLINESTORE:LIST_REQUEST");
    static public final ServiceAction GET_REQUEST_RESULT  = new ServiceAction("OFFLINESTORE:GET_REQUEST_RESULT");


    /**
     * List current offline storage requests. Completed jobs may remain in this list
     * for a provider-specific amount of time.
     * @param bucket name of bucket to list requests for
     * @return iterable of current known requests
     * @throws CloudException an error occurred in the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nonnull Iterable<OfflineStoreRequest> listRequests(@Nonnull String bucket) throws CloudException, InternalException;

    /**
     * Get a specific offline storage request
     * @param bucket name of bucket for request
     * @param requestId provider-specific identifier of request
     * @return a representation of the request, or null if it is not found
     * @throws CloudException an error occurred in the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nullable OfflineStoreRequest getRequest(@Nonnull String bucket, @Nonnull String requestId) throws CloudException, InternalException;

    /**
     * Create a new bucket list request
     * @param bucket name of bucket to list
     * @return representation of the request
     * @throws CloudException an error occurred in the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nonnull OfflineStoreRequest createListRequest(@Nonnull String bucket) throws CloudException, InternalException;

    /**
     * Create a new object download request
     * @param bucket name of bucket containing object
     * @param object name of object to download
     * @return representation of the request
     * @throws CloudException an error occurred in the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nonnull OfflineStoreRequest createDownloadRequest(@Nonnull String bucket, @Nonnull String object) throws CloudException, InternalException;

    /**
     * Retrieve the results of a completed list request. Will fail if the request is not complete.
     * @param bucket name of bucket for request
     * @param requestId provider-specific identifier of request
     * @return iterable of found objects
     * @throws CloudException an error occurred in the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nonnull Iterable<Blob> getListRequestResult(@Nonnull String bucket, @Nonnull String requestId) throws InternalException, CloudException;

    /**
     * Initiate the download for a request. Will fail if the request is not complete.
     * @param bucket name of bucket for request
     * @param requestId provider-specific identifier of request
     * @param toFile destination file for download results
     * @return FileTransfer asynchronous object to track the progress of the download
     * @throws CloudException an error occurred in the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nonnull FileTransfer getDownloadRequestResult(@Nonnull String bucket, @Nonnull String requestId, @Nonnull File toFile) throws InternalException, CloudException;

}
