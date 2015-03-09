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

import org.dasein.cloud.Taggable;
import org.dasein.util.uom.storage.Storage;
import org.dasein.util.uom.time.Second;
import org.dasein.util.uom.time.TimePeriod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a cloud-based message queue such as a message queue in Amazon SQS.
 * <p>Created by George Reese: 7/24/13 5:03 AM</p>
 * @author George Reese
 * @version 2013.07 initial version (issue #76)
 * @since 2013.07
 */
public class MessageQueue implements Taggable {
    /**
     * Constructs a message queue instance from wire data.
     * @param ownerId the account who owns this message queue
     * @param regionId the ID of the region in which the message queue operates
     * @param mqId the unique ID of the message queue with the provider
     * @param name the name of the message queue
     * @param state the current state of the message queue
     * @param description a user-friendly description of the message queue
     * @param endpoint the endpoint clients can use for posting and fetching messages
     * @param delay the amount of time that the delivery of all messages in the queue will be delayed
     * @param retention the amount of time a message will be retained in the queue
     * @param visibilityTimeout the length of time that a message received from a queue will be invisible to other receiving components when they ask to receive messages
     * @param maxMessageSize the maximum amount of data that a message posted to the queue may contain
     * @return a Dasein Cloud message queue object with the specified wire state
     */
    static public MessageQueue getInstance(@Nonnull String ownerId, @Nonnull String regionId, @Nonnull String mqId, @Nonnull String name, @Nonnull MQState state, @Nonnull String description,
                                           @Nonnull String endpoint, @Nonnull TimePeriod<?> delay, @Nullable TimePeriod<?> retention, @Nullable TimePeriod<?> visibilityTimeout,
                                           @Nullable Storage<?> maxMessageSize) {
        MessageQueue mq = new MessageQueue();

        mq.currentState = state;
        mq.delay = (TimePeriod<Second>)delay.convertTo(TimePeriod.SECOND);
        mq.description = description;
        mq.endpoint = endpoint;
        if( maxMessageSize != null ) {
            mq.maximumMessageSize = (Storage<org.dasein.util.uom.storage.Byte>)maxMessageSize.convertTo(Storage.BYTE);
        }
        mq.name = name;
        mq.providerMessageQueueId = mqId;
        mq.providerOwnerId = ownerId;
        mq.providerRegionId = regionId;
        if( retention != null ) {
            mq.retentionPeriod = (TimePeriod<Second>)retention.convertTo(TimePeriod.SECOND);
        }
        if( visibilityTimeout != null ) {
            mq.visibilityTimeout = (TimePeriod<Second>)visibilityTimeout.convertTo(TimePeriod.SECOND);
        }
        return mq;
    }

    private MQState                                   currentState;
    private TimePeriod<Second>                        delay;
    private String                                    description;
    private String                                    endpoint;
    private Storage<org.dasein.util.uom.storage.Byte> maximumMessageSize;
    private String                                    name;
    private String                                    providerMessageQueueId;
    private String                                    providerOwnerId;
    private String                                    providerRegionId;
    private TimePeriod<Second>                        retentionPeriod;
    private Map<String,String>                        tags;
    private TimePeriod<Second>                        visibilityTimeout;

    private MessageQueue() { }

    /**
     * @return the current state of the message queue with respect to functional operations
     */
    public @Nonnull MQState getCurrentState() {
        return currentState;
    }

    /**
     * @return the amount of time that the delivery of all messages in the queue will be delayed
     */
    public @Nonnull TimePeriod<Second> getDelay() {
        return delay;
    }

    /**
     * @return a user-friendly description of the message queue
     */
    public @Nonnull String getDescription() {
        return description;
    }

    /**
     * @return a URL describing an endpoint that enables clients to interact with the queue
     */
    public @Nonnull String getEndpoint() {
        return endpoint;
    }

    /**
     * A null value means that the maximum size is either unknown or ostensibly not bounded
     * @return the maximum size of messages that may be posted to this queue
     */
    public @Nullable Storage<org.dasein.util.uom.storage.Byte> getMaximumMessageSize() {
        return maximumMessageSize;
    }

    /**
     * @return the name of the message queue
     */
    public @Nonnull String getName() {
        return name;
    }

    /**
     * @return the unique ID of the message queue with the cloud provider
     */
    public @Nonnull String getProviderMessageQueueId() {
        return providerMessageQueueId;
    }

    /**
     * @return the account number of the owner of this message queue
     */
    public @Nonnull String getProviderOwnerId() {
        return providerOwnerId;
    }

    /**
     * @return the ID of the region in which this queue operates
     */
    public @Nonnull String getProviderRegionId() {
        return providerRegionId;
    }

    /**
     * A null value indicates the message will be retained indefinitely.
     * @return the amount of time a message will be retained in the queue
     */
    public @Nullable TimePeriod<Second> getRetentionPeriod() {
        return retentionPeriod;
    }

    /**
     * Fetches the value of the specified tag key.
     * @param tag the key of the tag to be fetched
     * @return the value associated with the specified key, if any
     */
    public @Nullable Object getTag(@Nonnull String tag) {
        return getTags().get(tag);
    }

    @Override
    public @Nonnull Map<String,String> getTags() {
        if( tags == null ) {
            tags = new HashMap<String,String>();
        }
        return tags;
    }

    /**
     * A null value means that the visibility timeout is unspecified or does not exist.
     * @return the length of time that a message received from a queue will be invisible to other receiving components when they ask to receive messages
     */
    public @Nullable TimePeriod<Second> getVisibilityTimeout() {
        return visibilityTimeout;
    }

    @Override
    public int hashCode() {
        return (providerOwnerId + providerRegionId + providerMessageQueueId).hashCode();
    }

    @Override
    public void setTag(@Nonnull String key, @Nonnull String value) {
        getTags().put(key, value);
    }

    /**
     * Clears out any currently set tags and replaces them with the specified list.
     * @param properties the list of tag values to be set
     */
    public void setTags(Map<String,String> properties) {
        getTags().clear();
        getTags().putAll(properties);
    }

    @Override
    public String toString() {
        return (name + " [" + providerMessageQueueId + "]");
    }
}
