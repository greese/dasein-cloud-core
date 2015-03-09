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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nullable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test cases for the basic plumbing supporting cloud object store in Dasein Cloud.
 * <p>Created by George Reese: 2/8/14 6:26 PM</p>
 * @author George Reese
 * @since 2014.03 (about time)
 * @version 2014.03 initial version
 */
public class ObjectStoreTestCase {
    private String bucket;
    private String prefix;

    @Before
    public void setUp() {
        bucket = null;
        prefix = null;
    }

    @After
    public void tearDown() {

    }

    private void checkCloudStorageLoggingContent(@Nullable CloudStorageLogging status, long successTime, long errorTime, @Nullable String errorMessage) {
        assertNotNull("The logging status returned from the constructor was illegally a null value", status);
        assertEquals("The bucket does not match the test value", bucket, status.getBucket());
        assertEquals("The prefix does not match the test value", prefix, status.getPrefix());
        assertEquals("The last success timestamp does not match the test value", successTime, status.getLastSuccessfulDeliveryTimestamp());
        assertEquals("The last failure timestamp does not match the test value", errorTime, status.getLastFailureTimestamp());
        assertEquals("The last failure message does not match the test value", errorMessage, status.getLastFailureMessage());
    }

    @Test
    public void verifyCloudStorageLoggingConstructor() {
        long success = System.currentTimeMillis();
        String message = null;
        long failure = -1L;

        bucket = "root";
        prefix = "super";
        CloudStorageLogging logging = CloudStorageLogging.getInstance(bucket, prefix, success, failure, message);

        checkCloudStorageLoggingContent(logging, success, failure, message);
    }

    @Test
    public void verifyCloudStorageLoggingConstructorWithError() {
        long success = System.currentTimeMillis();
        String message = "Bad log";
        long failure = 123456789L;

        bucket = "root";
        prefix = "super";
        CloudStorageLogging logging = CloudStorageLogging.getInstance(bucket, prefix, success, failure, message);

        checkCloudStorageLoggingContent(logging, success, failure, message);
    }
}
