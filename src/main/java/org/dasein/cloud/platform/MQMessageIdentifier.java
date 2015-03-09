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

package org.dasein.cloud.platform;

import javax.annotation.Nonnull;

/**
 * Information supporting a specific message that has been posted to a message queue. This representation does not
 * include the message itself.
 * <p>Created by George Reese: 7/24/13 5:49 AM</p>
 * @author George Reese
 * @version 2013.07 initial version (issue #76)
 * @since 2013.07
 */
public class MQMessageIdentifier {
    private String md5;
    private String messageId;

    /**
     * Constructs a message having the specified ID and MD5.
     * @param id the ID of the message
     * @param md5 the MD5 of the message
     */
    public MQMessageIdentifier(@Nonnull String id, @Nonnull String md5) {
        this.messageId = id;
        this.md5 = md5;
    }

    /**
     * @return the MD5 of the message
     */
    public @Nonnull String getMd5() {
        return md5;
    }

    /**
     * @return the message ID
     */
    public @Nonnull String getProviderMessageId() {
        return messageId;
    }

    @Override
    public @Nonnull String toString() {
        return (messageId + " [" + md5 + "]");
    }
}
