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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents a received message along with identifying information and an optional receipt.
 * <p>Created by George Reese: 7/24/13 5:57 AM</p>
 * @author George Reese
 * @version 2013.07 initial version (issue #76)
 * @since 2013.07
 */
public class MQMessageReceipt {
    static public MQMessageReceipt getInstance(@Nonnull MQMessageIdentifier id, @Nonnull String message, @Nonnegative long sentTimestamp) {
        MQMessageReceipt receipt = new MQMessageReceipt();

        receipt.firstReceivedTimestamp = -1L;
        receipt.identifier = id;
        receipt.message = message;
        receipt.receipt = null;
        receipt.receiptCount = 0;
        receipt.senderId = null;
        receipt.sentTimestamp = sentTimestamp;
        return receipt;
    }

    static public MQMessageReceipt getInstance(@Nonnull MQMessageIdentifier id, @Nonnull String receiptId, @Nonnull String message, @Nonnegative long sentTimestamp) {
        MQMessageReceipt receipt = new MQMessageReceipt();

        receipt.firstReceivedTimestamp = -1L;
        receipt.identifier = id;
        receipt.message = message;
        receipt.receipt = receiptId;
        receipt.receiptCount = 0;
        receipt.senderId = null;
        receipt.sentTimestamp = sentTimestamp;
        return receipt;
    }

    static public MQMessageReceipt getInstance(@Nonnull MQMessageIdentifier id, @Nonnull String receiptId, @Nonnull String message, @Nonnegative long sentTimestamp, @Nonnegative int count, long firstReceivedTimestamp) {
        MQMessageReceipt receipt = new MQMessageReceipt();

        receipt.firstReceivedTimestamp = firstReceivedTimestamp;
        receipt.identifier = id;
        receipt.message = message;
        receipt.receipt = receiptId;
        receipt.receiptCount = count;
        receipt.senderId = null;
        receipt.sentTimestamp = sentTimestamp;
        return receipt;
    }

    static public MQMessageReceipt getInstance(@Nonnull MQMessageIdentifier id, @Nonnull String receiptId, @Nonnull String message, @Nonnull String senderId, @Nonnegative long sentTimestamp, @Nonnegative int count, long firstReceivedTimestamp) {
        MQMessageReceipt receipt = new MQMessageReceipt();

        receipt.firstReceivedTimestamp = firstReceivedTimestamp;
        receipt.identifier = id;
        receipt.message = message;
        receipt.receipt = receiptId;
        receipt.receiptCount = count;
        receipt.senderId = senderId;
        receipt.sentTimestamp = sentTimestamp;
        return receipt;
    }

    private long                firstReceivedTimestamp;
    private MQMessageIdentifier identifier;
    private String              message;
    private String              receipt;
    private int                 receiptCount;
    private String              senderId;
    private long                sentTimestamp;

    private MQMessageReceipt() { }

    public long getFirstReceivedTimestamp() {
        return firstReceivedTimestamp;
    }

    public @Nonnull MQMessageIdentifier getIdentifier() {
        return identifier;
    }

    public @Nonnull String getMessage() {
        return message;
    }

    public @Nullable String getReceipt() {
        return receipt;
    }

    public @Nonnegative int getReceiptCount() {
        return receiptCount;
    }

    @Override
    public @Nonnull String toString() {
        return message;
    }
}
