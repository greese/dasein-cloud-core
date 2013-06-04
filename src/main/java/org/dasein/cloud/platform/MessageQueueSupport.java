/**
 * Copyright (C) 2009-2013 Dell, Inc.
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
import org.dasein.cloud.identity.ServiceAction;

import javax.annotation.Nonnull;
import java.util.Locale;

public interface MessageQueueSupport extends AccessControlledService {
    static public final ServiceAction ANY                = new ServiceAction("MQ:ANY");

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
}
