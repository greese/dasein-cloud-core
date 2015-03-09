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

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.util.uom.time.Second;
import org.dasein.util.uom.time.TimePeriod;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Creation options for creating message queues.
 * <p>Created by George Reese: 7/24/13 4:50 AM</p>
 * @author George Reese
 * @version 2013.07 initial version (issue #76)
 * @since 2013.07
 */
public class MQCreateOptions {
    /**
     * Provides a basic set of options for creating a message queue with the specified name and description. The options
     * will support a default visibility timeout of 15 seconds.
     * @param name the name for the message queue to be created
     * @param description the description for the message queue to be created
     * @return options for creating the desired message queue
     */
    static public @Nonnull MQCreateOptions getInstance(@Nonnull String name, @Nonnull String description) {
        MQCreateOptions options = new MQCreateOptions();

        options.name= name;
        options.description = description;
        options.visibilityTimeout = new TimePeriod<Second>(15, TimePeriod.SECOND);
        return options;
    }

    private String             description;
    private Map<String,String> metaData;
    private String             name;
    private TimePeriod<Second> visibilityTimeout;

    private MQCreateOptions() { }

    /**
     * Creates a message queue in the specified cloud based on the contents of this options object.
     * @param provider the provider object for the cloud in which the message queue should be created
     * @return the ID of the newly created message queue
     * @throws CloudException an error occurred with the cloud provider while making the snapshot
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     * @throws OperationNotSupportedException snapshots are not supported in this cloud
     */
    public @Nonnull String build(@Nonnull CloudProvider provider) throws CloudException, InternalException {
        PlatformServices services = provider.getPlatformServices();

        if( services == null ) {
            throw new OperationNotSupportedException(provider.getCloudName() + " does not support platform services");
        }
        MQSupport support = services.getMessageQueueSupport();

        if( support == null ) {
            throw new OperationNotSupportedException(provider.getCloudName() + " does not support message queues");
        }
        return support.createMessageQueue(this);
    }

    /**
     * @return a description of the message queue
     */
    public @Nonnull String getDescription() {
        return description;
    }

    /**
     * @return any extra meta-data to assign to the message queue
     */
    public @Nonnull Map<String,String> getMetaData() {
        return (metaData == null ? new HashMap<String, String>() : metaData);
    }

    /**
     * @return a name for the message queue
     */
    public @Nonnull String getName() {
        return name;
    }

    /**
     * @return the length of time that a message received from a queue will be invisible to other receiving components when they ask to receive messages
     */
    public @Nonnull TimePeriod<Second> getVisibilityTimeout() {
        return visibilityTimeout;
    }

    /**
     * Specifies any meta-data to be associated with the message queue when created. This method is
     * accretive, meaning that it adds to any existing meta-data (or replaces an existing key).
     * @param key the key of the meta-data entry
     * @param value the value for the meta-data entry
     * @return this
     */
    public @Nonnull MQCreateOptions withMetaData(@Nonnull String key, @Nonnull String value) {
        if( metaData == null ) {
            metaData = new HashMap<String, String>();
        }
        metaData.put(key, value);
        return this;
    }

    /**
     * Specifies meta-data to add onto any existing meta-data being associated with this message queue when created.
     * This method is accretive, meaning that it adds to any existing meta-data (or replaces an existing keys).
     * @param metaData the meta-data to be set for the new firewall
     * @return this
     */
    public @Nonnull MQCreateOptions withMetaData(@Nonnull Map<String,String> metaData) {
        if( this.metaData == null ) {
            this.metaData = new HashMap<String, String>();
        }
        this.metaData.putAll(metaData);
        return this;
    }

    @Override
    public @Nonnull String toString() {
        return ("[create -> " + name + " with timeout=" + visibilityTimeout + "]");
    }
}
