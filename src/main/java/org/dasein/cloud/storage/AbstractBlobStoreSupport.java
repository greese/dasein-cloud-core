/**
 * Copyright (C) 2009-2013 enstratius, Inc.
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Callable;

import org.apache.commons.codec.binary.Base64;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.util.Retry;
import org.dasein.util.uom.storage.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractBlobStoreSupport implements BlobStoreSupport {
    private byte[] computeMD5Hash(InputStream is) throws NoSuchAlgorithmException, IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[16384];
            int bytesRead;

            while ((bytesRead = bis.read(buffer, 0, buffer.length)) != -1) {
                messageDigest.update(buffer, 0, bytesRead);
            }
            return messageDigest.digest();
        } 
        finally {
            try {
                bis.close();
            } catch (Exception e) {
                System.err.println("Unable to close input stream of hash candidate: " + e);
            }
        }
    }

    @Override
    public void clearBucket(@Nonnull String bucket) throws CloudException, InternalException {
        for( Blob file : list(bucket) ) {
            if( file.isContainer() ) {
                String name = file.getBucketName();

                if( name != null ) {
                    clearBucket(name);
                }
            }
            else {
                String name = file.getObjectName();

                if( name != null ) {
                    removeObject(file.getBucketName(), name);
                }
            }
        }
        removeBucket(bucket);
    }

    protected void copy(@Nullable String sourceBucket, @Nullable String sourceObject, @Nullable String targetBucket, @Nullable String targetObject) throws InternalException, CloudException {
        if( sourceObject == null ) {
            if( targetBucket == null && !allowsRootObjects() ) {
                throw new CloudException("Cannot place objects in the root");
            }
            if( targetBucket != null && targetBucket.equalsIgnoreCase(sourceBucket) ) {
                throw new CloudException("Cannot copy in place");
            }
            if( targetBucket != null && !exists(targetBucket) ) {
                createBucket(targetBucket, false);
            }
            for( Blob blob : list(sourceBucket) ) {
                copy(blob.getBucketName(), blob.getObjectName(), targetBucket, null);
            }
        }
        else {
            if( targetObject == null ) {
                targetObject = sourceObject;
            }
            copyFile(sourceBucket, sourceObject, targetBucket, targetObject);
        }
    }
    
    protected void copy(@Nonnull InputStream input, @Nonnull OutputStream output, @Nullable FileTransfer xfer) throws IOException {
        try {
            byte[] bytes = new byte[10240];
            long total = 0L;
            int count;

            if( xfer != null ) {
                xfer.setBytesTransferred(0L);
            }
            while( (count = input.read(bytes, 0, 10240)) != -1 ) {
                if( count > 0 ) {
                    output.write(bytes, 0, count);
                    total = total + count;
                    if( xfer != null ) {
                        xfer.setBytesTransferred(total);
                    }
                }
            }
            output.flush();
        }
        finally {
            input.close();
            output.close();
        }
    }
    
    protected void copyFile(@Nullable String sourceBucket, @Nonnull String sourceObject, @Nullable String targetBucket, @Nonnull String targetObject) throws InternalException, CloudException {
        File tmp = null;
        
        try {
            try {
                tmp = File.createTempFile("file", ".tmp");
            }
            catch( IOException e ) {
                e.printStackTrace();
                throw new InternalException(e);
            }
            get(sourceBucket, sourceObject, tmp, null);
            put(targetBucket, targetObject, tmp);
        }
        finally {
            if( tmp != null ) {
                //noinspection ResultOfMethodCallIgnored
                tmp.delete();
            }
        }
    }
    
    @Override
    public FileTransfer download(final @Nullable String bucketName, final @Nonnull String objectName, final @Nonnull File diskFile) throws CloudException, InternalException {
        final FileTransfer transfer = new FileTransfer();

        Storage<org.dasein.util.uom.storage.Byte> bytes = getObjectSize(bucketName, objectName);

        if( bytes == null ) {
            throw new CloudException("File does not exist");
        }
        transfer.setBytesToTransfer(bytes.getQuantity().intValue());
        if( transfer.getBytesToTransfer() == -1L ) {
            throw new CloudException("No such file: " + ((bucketName == null ? "/" : "/" + bucketName) + "/" + objectName));
        }
        Thread t = new Thread() {
            public void run() {
                Callable<Object> operation = new Callable<Object>() {
                    public Object call() throws Exception {
                        boolean success = false;
                        
                        try {
                            get(bucketName, objectName, diskFile, transfer);
                            success = true;
                            return null;
                        }
                        finally {
                            if( !success ) {
                                if( diskFile.exists() ) {
                                    //noinspection ResultOfMethodCallIgnored
                                    diskFile.delete();
                                }
                            }
                        }
                    }
                };
                try {
                    (new Retry<Object>()).retry(5, operation);
                    transfer.complete(null);
                }
                catch( CloudException e ) {
                    transfer.complete(e);
                }
                catch( InternalException e ) {
                    transfer.complete(e);
                }
                catch( Throwable t ) {
                    t.printStackTrace();
                    transfer.complete(t);
                }
            }
        };
        
        t.setDaemon(true);
        t.start();
        return transfer;
    }

    protected abstract void get(@Nullable String bucket, @Nonnull String object, @Nonnull File toFile, @Nullable FileTransfer transfer) throws InternalException, CloudException;
        
        
    protected @Nonnull String getChecksum(@Nonnull InputStream input) throws NoSuchAlgorithmException, IOException {
        return toBase64(computeMD5Hash(input));
    }
    
    protected abstract void put(@Nullable String bucket, @Nonnull String objectName, @Nonnull File file) throws InternalException, CloudException;
        
    protected abstract void put(@Nullable String bucketName, @Nonnull String objectName, @Nonnull String content) throws InternalException, CloudException;

    private @Nonnull String toBase64(@Nonnull byte[] data) {
        byte[] b64 = Base64.encodeBase64(data);
        
        return new String(b64);
    }
}
