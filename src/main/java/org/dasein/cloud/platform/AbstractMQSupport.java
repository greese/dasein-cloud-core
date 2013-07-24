/**
 * Copyright (C) 2009-2013 Dell, Inc.
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

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.ProviderContext;
import org.dasein.cloud.ResourceStatus;
import org.dasein.cloud.identity.ServiceAction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Provides default method implementations for various message queue operations.
 * <p>Created by George Reese: 7/24/13 6:21 AM</p>
 * @author George Reese
 * @version 2013.07 initial version
 * @since 2013.07
 */
public abstract class AbstractMQSupport<T extends CloudProvider> implements MQSupport {
    private T provider;

    public AbstractMQSupport(@Nonnull T provider) { this.provider = provider; }
    /**
     * Provides the current provider context for the request in progress.
     * @return the current provider context
     * @throws CloudException no context was defined before making this call
     */
    protected final @Nonnull ProviderContext getContext() throws CloudException {
        ProviderContext ctx = getProvider().getContext();

        if( ctx == null ) {
            throw new CloudException("No context was defined for this request");
        }
        return ctx;
    }

    /**
     * @return the current provider governing any operations against this cloud in this support instance
     */
    protected final @Nonnull T getProvider() {
        return provider;
    }


    @Override
    public @Nonnull String[] mapServiceAction( @Nonnull ServiceAction action ) {
        return new String[0];
    }

    @Override
    public @Nonnull String createMessageQueue(@Nonnull MQCreateOptions options) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Message queue creation is not supported in " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

    @Override
    public @Nonnull Iterable<ResourceStatus> listMessageQueueStatus() throws CloudException, InternalException {
        ArrayList<ResourceStatus> status = new ArrayList<ResourceStatus>();

        for( MessageQueue q : listMessageQueues() ) {
            status.add(new ResourceStatus(q.getProviderMessageQueueId(), q.getCurrentState()));
        }
        return status;
    }

    @Override
    public @Nullable MQMessageReceipt receiveMessage(@Nonnull String mqId) throws CloudException, InternalException {
        Iterator<MQMessageReceipt> matches = receiveMessages(mqId, null, 1, null).iterator();

        if( matches.hasNext() ) {
            return matches.next();
        }
        return null;
    }

    @Override
    public void removeMessageQueue(@Nonnull String mqId, @Nullable String reason) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Message queue deletion is not supported in " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }
}
