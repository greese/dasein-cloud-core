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

import org.dasein.cloud.AccessControlledService;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.Tag;
import org.dasein.cloud.identity.ServiceAction;
import org.dasein.cloud.util.NamingConstraints;
import org.dasein.util.uom.storage.Storage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.Locale;

/**
 * <p>
 * Interface into the cloud storage services supported by this cloud provider.
 * </p>
 *
 * @author George Reese @ enstratius (http://www.enstratius.com)
 */
public interface BlobStoreSupport extends AccessControlledService {
    static public final ServiceAction ANY = new ServiceAction("BLOB:ANY");

    static public final ServiceAction CREATE_BUCKET        = new ServiceAction("BLOB:CREATE_BUCKET");
    static public final ServiceAction DOWNLOAD             = new ServiceAction("BLOB:DOWNLOAD");
    static public final ServiceAction GET_BUCKET           = new ServiceAction("BLOB:GET_BUCKET");
    static public final ServiceAction LIST_BUCKET          = new ServiceAction("BLOB:LIST_BUCKET");
    static public final ServiceAction LIST_BUCKET_CONTENTS = new ServiceAction("BLOB:LIST_BUCKET_CONTENTS");
    static public final ServiceAction MAKE_PUBLIC          = new ServiceAction("BLOB:MAKE_PUBLIC");
    static public final ServiceAction REMOVE_BUCKET        = new ServiceAction("BLOB:REMOVE_BUCKET");
    static public final ServiceAction UPLOAD               = new ServiceAction("BLOB:UPLOAD");

    @Nonnull BlobStoreCapabilities getCapabilities() throws CloudException, InternalException;

    @Deprecated
    boolean allowsNestedBuckets() throws CloudException, InternalException;

    @Deprecated
    boolean allowsRootObjects() throws CloudException, InternalException;

    @Deprecated
    boolean allowsPublicSharing() throws CloudException, InternalException;

    void clearBucket(@Nonnull String bucket) throws CloudException, InternalException;

    @Nonnull Blob createBucket(@Nonnull String bucket, boolean findFreeName) throws InternalException, CloudException;

    FileTransfer download(@Nullable String bucket, @Nonnull String objectName, @Nonnull File toFile) throws InternalException, CloudException;

    boolean exists(@Nonnull String bucket) throws InternalException, CloudException;

    Blob getBucket(@Nonnull String bucketName) throws InternalException, CloudException;

    Blob getObject(@Nullable String bucketName, @Nonnull String objectName) throws InternalException, CloudException;

    @Nullable String getSignedObjectUrl(@Nonnull String bucket, @Nonnull String object, @Nonnull String expiresEpochInSeconds) throws InternalException, CloudException;

    @Nullable Storage<org.dasein.util.uom.storage.Byte> getObjectSize(@Nullable String bucketName, @Nullable String objectName) throws InternalException, CloudException;

    @Deprecated
    int getMaxBuckets() throws CloudException, InternalException;

    @Deprecated
    Storage<org.dasein.util.uom.storage.Byte> getMaxObjectSize() throws InternalException, CloudException;

    @Deprecated
    int getMaxObjectsPerBucket() throws CloudException, InternalException;

    @Deprecated
    @Nonnull NamingConstraints getBucketNameRules() throws CloudException, InternalException;

    @Deprecated
    @Nonnull NamingConstraints getObjectNameRules() throws CloudException, InternalException;

    @Deprecated
    @Nonnull String getProviderTermForBucket(@Nonnull Locale locale);

    @Deprecated
    @Nonnull String getProviderTermForObject(@Nonnull Locale locale);

    boolean isPublic(@Nullable String bucket, @Nullable String object) throws CloudException, InternalException;

    boolean isSubscribed() throws CloudException, InternalException;

    @Nonnull Iterable<Blob> list(@Nullable String bucket) throws CloudException, InternalException;

    void makePublic(@Nonnull String bucket) throws InternalException, CloudException;

    void makePublic(@Nullable String bucket, @Nonnull String object) throws InternalException, CloudException;

    void move(@Nullable String fromBucket, @Nullable String objectName, @Nullable String toBucket) throws InternalException, CloudException;

    void removeBucket(@Nonnull String bucket) throws CloudException, InternalException;

    void removeObject(@Nullable String bucket, @Nonnull String object) throws CloudException, InternalException;

    @Nonnull String renameBucket(@Nonnull String oldName, @Nonnull String newName, boolean findFreeName) throws CloudException, InternalException;

    void renameObject(@Nullable String bucket, @Nonnull String oldName, @Nonnull String newName) throws CloudException, InternalException;

    @Nonnull Blob upload(@Nonnull File sourceFile, @Nullable String bucket, @Nonnull String objectName) throws CloudException, InternalException;

    /**
     * Updates meta-data for a bucket with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     *
     * @param bucket the bucket to update
     * @param tags   the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    void updateTags(@Nonnull String bucket, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Updates meta-data for multiple buckets with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     *
     * @param buckets the buckets to update
     * @param tags    the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    void updateTags(@Nonnull String[] buckets, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Removes meta-data from an bucket. If tag values are set, their removal is dependent on underlying cloud
     * provider behavior. They may be removed only if the tag value matches or they may be removed regardless of the
     * value.
     *
     * @param bucket the bucket to update
     * @param tags   the meta-data tags to remove
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    void removeTags(@Nonnull String bucket, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Removes meta-data from multiple buckets. If tag values are set, their removal is dependent on underlying cloud
     * provider behavior. They may be removed only if the tag value matches or they may be removed regardless of the
     * value.
     *
     * @param buckets the bucket to update
     * @param tags    the meta-data tags to remove
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    void removeTags(@Nonnull String[] buckets, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Set meta-data for an bucket. Remove any tags that were not provided by the incoming tags, and add or
     * overwrite any new or pre-existing tags.
     *
     * @param bucket the bucket to update
     * @param tags   the meta-data tags to remove
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    void setTags(@Nonnull String bucket, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Set meta-data for multiple buckets. Remove any tags that were not provided by the incoming tags, and add or
     * overwrite any new or pre-existing tags.
     *
     * @param buckets the buckets to update
     * @param tags    the meta-data tags to remove
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    void setTags(@Nonnull String[] buckets, @Nonnull Tag... tags) throws CloudException, InternalException;

}
