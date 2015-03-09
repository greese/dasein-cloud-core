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

import org.dasein.cloud.AccessControlledService;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.ResourceStatus;
import org.dasein.cloud.identity.ServiceAction;
import org.dasein.util.uom.time.Second;
import org.dasein.util.uom.time.TimePeriod;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;

/**
 * Implements support for cloud message queue services. This support enables a Dasein Cloud client to interact with
 * message queues and send and receive messages.
 * @author George Reese
 * @version 2013.07 filled out stubbed information and renamed to MQSupport (issue #76)
 * @since unknown
 */
public interface MQSupport extends AccessControlledService {
    static public final ServiceAction ANY                = new ServiceAction("MQ:ANY");

    /**
     * Creates a new message queue in the cloud using the specified creation options.
     * @param options the creation options to be used in creating the message queue
     * @return a newly created message queue
     * @throws CloudException an error occurred with the cloud provider in creating the message queue
     * @throws InternalException an error occurred within the Dasein Cloud implementation while handling the request
     */
    public @Nonnull String createMessageQueue(@Nonnull MQCreateOptions options) throws CloudException, InternalException;

    /**
     * Fetches the specified message queue from the cloud if it exists.
     * @param mqId the unique ID of the target message queue
     * @return the message queue matching the specified ID if it exists
     * @throws CloudException an error occurred with the cloud provider while executing the request
     * @throws InternalException an error occurred within the Dasein Cloud implementation while handling the request
     */
    public @Nullable MessageQueue getMessageQueue(@Nonnull String mqId) throws CloudException, InternalException;

    /**
     * Provides the term the cloud provider uses to describe a message queue.
     * @param locale the locale into which the term should be translated
     * @return the provider term for a message queue
     */
    public @Nonnull String getProviderTermForMessageQueue(@Nonnull Locale locale);

    /**
     * Indicates whether the current account is subscribed in the current cloud region for message queue services.
     * @return true if the account is subscribed for message queue services in the current account
     * @throws CloudException an error occurred in the cloud determining subscription status
     * @throws InternalException an error occurred in the Dasein Cloud implementation determining subscription status
     */
    public boolean isSubscribed() throws CloudException, InternalException;

    /**
     * Lists the message queues established in the current region.
     * @return a list of matching message queues
     * @throws CloudException an error occurred with the cloud provider while executing the request
     * @throws InternalException an error occurred within the Dasein Cloud implementation while handling the request
     */
    public @Nonnull Iterable<MessageQueue> listMessageQueues() throws CloudException, InternalException;

    /**
     * Lists the current status for message queues established in the current region.
     * @return a list of message queue status information
     * @throws CloudException an error occurred with the cloud provider while executing the request
     * @throws InternalException an error occurred within the Dasein Cloud implementation while handling the request
     */
    public @Nonnull Iterable<ResourceStatus> listMessageQueueStatus() throws CloudException, InternalException;

    /**
     * Receives a single message with default parameters for wait time (none) and visibility timeout (queue default).
     * @param mqId the unique ID of the queue to receive from
     * @return the first message available on the queue, if any is available (otherwise <code>null</code>)
     * @throws CloudException an error occurred with the cloud provider while executing the request
     * @throws InternalException an error occurred within the Dasein Cloud implementation while handling the request
     */
    public @Nullable MQMessageReceipt receiveMessage(@Nonnull String mqId) throws CloudException, InternalException;

    /**
     * Receives zero or more messages up to the maximum number specified with customizable receipt attributes.
     * @param mqId the unique ID of the queue to receive from
     * @param waitTime the amount of time to wait for a message to appear on the queue
     * @param count the maximum number of messages to receive
     * @param visibilityTimeout an alternate {@link MessageQueue#getVisibilityTimeout()} for this specific message post-read
     * @return the any messages on the queue up to the specified count (none will be returned if none are available)
     * @throws CloudException an error occurred with the cloud provider while executing the request
     * @throws InternalException an error occurred within the Dasein Cloud implementation while handling the request
     */
    public @Nonnull Iterable<MQMessageReceipt> receiveMessages(@Nonnull String mqId, @Nullable TimePeriod<Second> waitTime, @Nonnegative int count, @Nullable TimePeriod<Second> visibilityTimeout) throws CloudException, InternalException;

    /**
     * Deletes the specified message queue with an optional explanation.
     * @param mqId the unique ID for the message queue to be deleted
     * @param reason the reason for the deletion
     * @throws CloudException an error occurred with the cloud provider while executing the request
     * @throws InternalException an error occurred within the Dasein Cloud implementation while handling the request
     */
    public void removeMessageQueue(@Nonnull String mqId, @Nullable String reason) throws CloudException, InternalException;

    /**
     * Sends the specified message to the message queue.
     * @param mqId the unique ID of the queue to which the message will be sent
     * @param message the message to be sent
     * @return the message ID/MD5 pair representing the message as it was posted to the queue
     * @throws CloudException an error occurred with the cloud provider while executing the request
     * @throws InternalException an error occurred within the Dasein Cloud implementation while handling the request
     */
    public @Nonnull MQMessageIdentifier sendMessage(@Nonnull String mqId, @Nonnull String message) throws CloudException, InternalException;
}
