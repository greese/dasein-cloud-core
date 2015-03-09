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

package org.dasein.cloud.network;

import javax.annotation.Nonnull;

/**
 * User: mgulimonov
 * Date: 20.06.2014
 */
public class LbAttributesOptions {

    boolean modifyCrossDataCenter = false;
    boolean modifyConnectionDraining = false;
    boolean modifyConnectionTimeout = false;

    private boolean crossDataCenter;
    private boolean connectionDraining;
    private int connectionDrainingTimeout;
    private int idleConnectionTimeout;

    private LbAttributesOptions() {
    }

    public static LbAttributesOptions getInstance( boolean crossDataCenter, boolean connectionDraining, int connectionDrainingTimeout, int idleConnectionTimeout ) {
        return new LbAttributesOptions()
                .withCrossDataCenter(crossDataCenter)
                .withConnectionDraining(connectionDraining)
                .withConnectionDrainingTimeout(connectionDrainingTimeout)
                .withIdleConnectionTimeout(idleConnectionTimeout);
    }

    public static LbAttributesOptions getInstance() {
        return new LbAttributesOptions();
    }

    public boolean isModifyCrossDataCenter() {
        return modifyCrossDataCenter;
    }

    public boolean isModifyConnectionDraining() {
        return modifyConnectionDraining;
    }

    public boolean isModifyConnectionTimeout() {
        return modifyConnectionTimeout;
    }

    public boolean isCrossDataCenter() {
        return crossDataCenter;
    }

    public boolean isConnectionDraining() {
        return connectionDraining;
    }

    public int getConnectionDrainingTimeout() {
        return connectionDrainingTimeout;
    }

    public int getIdleConnectionTimeout() {
        return idleConnectionTimeout;
    }

    public @Nonnull LbAttributesOptions withCrossDataCenter( boolean crossDataCenter ) {
        this.crossDataCenter = crossDataCenter;
        this.modifyCrossDataCenter = true;
        return this;
    }

    public @Nonnull LbAttributesOptions withConnectionDraining( boolean connectionDraining ) {
        this.connectionDraining = connectionDraining;
        this.modifyConnectionDraining = true;
        return this;
    }

    public @Nonnull LbAttributesOptions withConnectionDrainingTimeout( int connectionDrainingTimeout ) {
        this.connectionDrainingTimeout = connectionDrainingTimeout;
        this.modifyConnectionDraining = true;
        return this;
    }

    public @Nonnull LbAttributesOptions withIdleConnectionTimeout( int idleConnectionTimeout ) {
        this.idleConnectionTimeout = idleConnectionTimeout;
        this.modifyConnectionTimeout = true;
        return this;
    }

    public boolean isEmptyOptions() {
        return !modifyCrossDataCenter && !modifyConnectionDraining && !modifyConnectionTimeout;
    }
}
