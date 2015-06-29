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

import org.dasein.cloud.Capabilities;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;

import javax.annotation.Nonnull;
import java.util.Locale;

/**
 * Describes the capabilities of a Push Notification Service within a cloud for a specific account.
 * @author Drew Lyall
 * @version 2014.08
 * @since 2014.08
 */
public interface PushNotificationCapabilities extends Capabilities{
    /**
     * Indicates whether the cloud supports topic creation
     * @return true if topics can be created
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException    an error occurred within the cloud provider
     */
    public boolean canCreateTopic() throws CloudException, InternalException;

    /**
     * Indicates whether the cloud allows the removal of topics
     * @return true if topics can be removed
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException    an error occurred within the cloud provider
     */
    public boolean canRemoveTopic() throws CloudException, InternalException;

    /**
     * The term the provider uses to describe the push subscription.
     * @param locale the language in which the term should be presented
     * @return the provider term for push subscriptions
     */
    public @Nonnull String getProviderTermForSubscription(Locale locale);

    /**
     * The term the provider uses to describe the notification topic.
     * @param locale the language in which the term should be presented
     * @return the provider term for the topic
     */
    public @Nonnull String getProviderTermForTopic(Locale locale);
}
