/**
 * Copyright (C) 2009-2014 Dell, Inc.
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
import org.dasein.cloud.identity.ServiceAction;
import org.dasein.cloud.util.NamingConventions;
import org.dasein.util.uom.storage.Storage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.Locale;

/**
 * <p>
 *   Interface into the cloud storage services supported by this cloud provider.
 * </p>
 * @author George Reese @ enstratius (http://www.enstratius.com)
 */
public interface BlobStoreSupport extends AccessControlledService {
    static public final ServiceAction ANY                     = new ServiceAction("BLOB:ANY");

    static public final ServiceAction CREATE_BUCKET           = new ServiceAction("BLOB:CREATE_BUCKET");
    static public final ServiceAction DOWNLOAD                = new ServiceAction("BLOB:DOWNLOAD");
    static public final ServiceAction GET_BUCKET              = new ServiceAction("BLOB:GET_BUCKET");
    static public final ServiceAction LIST_BUCKET             = new ServiceAction("BLOB:LIST_BUCKET");
    static public final ServiceAction LIST_BUCKET_CONTENTS    = new ServiceAction("BLOB:LIST_BUCKET_CONTENTS");
    static public final ServiceAction MAKE_PUBLIC             = new ServiceAction("BLOB:MAKE_PUBLIC");
    static public final ServiceAction REMOVE_BUCKET           = new ServiceAction("BLOB:REMOVE_BUCKET");
    static public final ServiceAction UPLOAD                  = new ServiceAction("BLOB:UPLOAD");

    public boolean allowsNestedBuckets() throws CloudException, InternalException;

    public boolean allowsRootObjects() throws CloudException, InternalException;

    public boolean allowsPublicSharing() throws CloudException, InternalException;

    public void clearBucket(@Nonnull String bucket) throws CloudException, InternalException;

    public @Nonnull Blob createBucket(@Nonnull String bucket, boolean findFreeName) throws InternalException, CloudException;
    
    public FileTransfer download(@Nullable String bucket, @Nonnull String objectName, @Nonnull File toFile) throws InternalException, CloudException;
    
    public boolean exists(@Nonnull String bucket) throws InternalException, CloudException;

    public Blob getBucket(@Nonnull String bucketName) throws InternalException, CloudException;

    public Blob getObject(@Nullable String bucketName, @Nonnull String objectName) throws InternalException, CloudException;

    public @Nullable String getSignedObjectUrl(@Nonnull String bucket,@Nonnull String object, @Nonnull String expiresEpochInSeconds) throws InternalException, CloudException;

    public @Nullable Storage<org.dasein.util.uom.storage.Byte> getObjectSize(@Nullable String bucketName, @Nullable String objectName) throws InternalException, CloudException;

    public int getMaxBuckets() throws CloudException, InternalException;

    public Storage<org.dasein.util.uom.storage.Byte> getMaxObjectSize() throws InternalException, CloudException;

    public int getMaxObjectsPerBucket() throws CloudException, InternalException;

    public @Nonnull NamingConventions getBucketNameRules() throws CloudException, InternalException;

    public @Nonnull NamingConventions getObjectNameRules() throws CloudException, InternalException;

    public @Nonnull String getProviderTermForBucket(@Nonnull Locale locale);
    
    public @Nonnull String getProviderTermForObject(@Nonnull Locale locale);
    
    public boolean isPublic(@Nullable String bucket, @Nullable String object) throws CloudException, InternalException;
    
    public boolean isSubscribed() throws CloudException, InternalException;

    public @Nonnull Iterable<Blob> list(@Nullable String bucket) throws CloudException, InternalException;
    
    public void makePublic(@Nonnull String bucket) throws InternalException, CloudException;
    
    public void makePublic(@Nullable String bucket, @Nonnull String object) throws InternalException, CloudException;

    public void move(@Nullable String fromBucket, @Nullable String objectName, @Nullable String toBucket) throws InternalException, CloudException;
    
    public void removeBucket(@Nonnull String bucket) throws CloudException, InternalException;

    public void removeObject(@Nullable String bucket, @Nonnull String object) throws CloudException, InternalException;

    public @Nonnull String renameBucket(@Nonnull String oldName, @Nonnull String newName, boolean findFreeName) throws CloudException, InternalException;
        
    public void renameObject(@Nullable String bucket, @Nonnull String oldName, @Nonnull String newName) throws CloudException, InternalException;
        
    public @Nonnull Blob upload(@Nonnull File sourceFile, @Nullable String bucket, @Nonnull String objectName) throws CloudException, InternalException;
}
