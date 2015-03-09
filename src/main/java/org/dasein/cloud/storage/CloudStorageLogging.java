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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A specific log for logging being done into a cloud storage endpoint.
 * <p>Created by George Reese: 2/8/14 2:06 PM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 initial version in support of data cluster logging (issue #100)
 */
public class CloudStorageLogging {
    /**
     * Constructs a new logging status for the specified values.
     * @param bucket the bucket into which logging is occurring
     * @param prefix the prefix of the logs
     * @param lastSuccessfulDeliveryTimestamp the last time when logs were delivered
     * @param lastFailureTimestamp the message indicating that the logs failed to be delivered
     * @param lastFailureMessage the last time whe the logs were failed to be delivered
     * @return a logging status object representing the specified values
     */
    static public CloudStorageLogging getInstance(@Nonnull String bucket, @Nonnull String prefix, long lastSuccessfulDeliveryTimestamp, long lastFailureTimestamp, @Nullable String lastFailureMessage) {
        CloudStorageLogging logging = new CloudStorageLogging();

        logging.bucket = bucket;
        logging.prefix = prefix;
        logging.lastSuccessfulDeliveryTimestamp = lastSuccessfulDeliveryTimestamp;
        logging.lastFailureTimestamp = lastFailureTimestamp;
        logging.lastFailureMessage = lastFailureMessage;
        return logging;
    }

    private String bucket;
    private String lastFailureMessage;
    private long   lastFailureTimestamp;
    private long   lastSuccessfulDeliveryTimestamp;
    private String prefix;

    private CloudStorageLogging() { }

    /**
     * @return the bucket into which logging is being done
     */
    public @Nonnull String getBucket() {
        return bucket;
    }

    /**
     * @return a message indicating the last failure of the logs to be delivered
     */
    public @Nullable String getLastFailureMessage() {
        return lastFailureMessage;
    }

    /**
     * @return the timestamp when logs were last failed to be delivered
     */
    public long getLastFailureTimestamp() {
        return lastFailureTimestamp;
    }

    /**
     * @return the timestamp when the logs were last successfully delivered
     */
    public long getLastSuccessfulDeliveryTimestamp() {
        return lastSuccessfulDeliveryTimestamp;
    }

    /**
     * @return the prefix for the log files
     */
    public @Nonnull String getPrefix() {
        return prefix;
    }

    @Override
    public @Nonnull String toString() {
        return (bucket + "/" + prefix);
    }
}
