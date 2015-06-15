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
import javax.annotation.Nullable;

/**
 * Describes an endpoint being managed by a load balancer. An endpoint may be of the types specified by
 * {@link LbEndpointType}.
 * <p>Created by George Reese: 3/7/13 9:31 PM</p>
 * @author George Reese
 * @version 2013.04 initial version
 * @since 2013.04
 */
public class LoadBalancerEndpoint {
    /**
     * Constructs an endpoint matching the specified type and value.
     * @param type the type of the newly constructed endpoint
     * @param value the ID or other value that indicates what the endpoint is
     * @param state the current known state of the endpoint as seen from the load balancer
     * @return the new load balancer endpoint
     */
    static public LoadBalancerEndpoint getInstance(@Nonnull LbEndpointType type, @Nonnull String value, @Nonnull LbEndpointState state) {
        LoadBalancerEndpoint endpoint = new LoadBalancerEndpoint();

        endpoint.endpointType = type;
        endpoint.endpointValue = value;
        endpoint.currentState = state;
        return endpoint;
    }

    /**
     * Constructs an endpoint matching the specified type and value.
     * @param type the type of the newly constructed endpoint
     * @param value the ID or other value that indicates what the endpoint is
     * @param state the state of the endpoint
     * @param stateReason the reason for the current state if any
     * @param stateDescription the description of the current state if any
     * @return the new load balancer endpoint
     */
    static public LoadBalancerEndpoint getInstance(@Nonnull LbEndpointType type, @Nonnull String value, @Nonnull LbEndpointState state, @Nullable String stateReason, @Nullable String stateDescription) {
        LoadBalancerEndpoint endpoint = new LoadBalancerEndpoint();

        endpoint.endpointType = type;
        endpoint.endpointValue = value;
        endpoint.currentState = state;
        endpoint.stateReason = stateReason;
        endpoint.stateDescription = stateDescription;
        return endpoint;
    }

    private LbEndpointState currentState;
    private LbEndpointType  endpointType;
    private String          endpointValue;
    private String          stateDescription;
    private String          stateReason;

    private LoadBalancerEndpoint() {}

    @Override
    public boolean equals(@Nullable Object other) {
        if( other == null ) {
            return false;
        }
        if( other == this ) {
            return true;
        }
        if( !other.getClass().getName().equals(getClass().getName()) ) {
            return false;
        }
        LoadBalancerEndpoint endpoint = (LoadBalancerEndpoint)other;

        return (endpointType.equals(endpoint.endpointType) && endpointValue.equals(endpoint.endpointValue));
    }

    /**
     * @return the current state of the endpoint as seen from the load balancer
     */
    public @Nonnull LbEndpointState getCurrentState() {
        return currentState;
    }

    /**
     * @return the type associated with this endpoint
     */
    public @Nonnull LbEndpointType getEndpointType() {
        return endpointType;
    }

    /**
     * @return a value identifying the endpoint such as a VM ID or an IP address
     */
    public @Nonnull String getEndpointValue() {
        return endpointValue;
    }

    /**
     * @return the current endpoint state as described by the cloud provider
     */
    public @Nonnull String getStateDescription() {
        return (stateDescription == null ? currentState.toString() : stateDescription);
    }

    /**
     * @return the reason for the endpoint being in its current state as described by the cloud provider
     */
    public @Nullable String getStateReason() {
        return stateReason;
    }

    @Override
    public int hashCode() {
        return (endpointType.name() + ":" + endpointValue).hashCode();
    }

    @Override
    public @Nonnull String toString() {
        return (endpointValue + " (" + endpointType + ")");
    }
}
