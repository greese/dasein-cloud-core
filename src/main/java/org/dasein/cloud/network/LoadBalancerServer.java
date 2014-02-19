/**
 * Copyright (C) 2009-2014 Dell, Inc.
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

import java.io.Serializable;

public class LoadBalancerServer implements Serializable {
    private static final long serialVersionUID = -2396639777723684882L;

    private String                  providerServerId;
    private LoadBalancerServerState currentState;
    private String                  currentStateDescription;
    private String                  currentStateReason;
    private String[]                providerDataCenterIds;
    private String                  providerLoadBalancerId;
    private String                  providerOwnerId;
    private String                  providerRegionId;

    public LoadBalancerServer() {
    }

    public boolean equals(Object ob) {
        if (ob == null) {
            return false;
        }
        if (ob == this) {
            return true;
        }
        if (!getClass().getName().equals(ob.getClass().getName())) {
            return false;
        }
        LoadBalancerServer other = (LoadBalancerServer) ob;
        if (!providerOwnerId.equals(other.providerOwnerId)) {
            return false;
        }
        if (!providerRegionId.equals(other.providerRegionId)) {
            return false;
        }
        return providerServerId.equals(other.providerServerId);
    }

    public String getProviderServerId() {
        return providerServerId;
    }

    public void setProviderServerId(String providerServerId) {
        this.providerServerId = providerServerId;
    }

    public LoadBalancerServerState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(LoadBalancerServerState currentState) {
        this.currentState = currentState;
    }

    public String getCurrentStateDescription() {
        return currentStateDescription;
    }

    public void setCurrentStateDescription(String currentStateDescription) {
        this.currentStateDescription = currentStateDescription;
    }

    public String getCurrentStateReason() {
        return currentStateReason;
    }

    public void setCurrentStateReason(String currentStateReason) {
        this.currentStateReason = currentStateReason;
    }

    public String[] getProviderDataCenterIds() {
        return providerDataCenterIds;
    }

    public void setProviderDataCenterIds(String[] providerDataCenterIds) {
        this.providerDataCenterIds = providerDataCenterIds;
    }

    public String getProviderLoadBalancerId() {
        return providerLoadBalancerId;
    }

    public void setProviderLoadBalancerId(String providerLoadBalancerId) {
        this.providerLoadBalancerId = providerLoadBalancerId;
    }

    public String getProviderOwnerId() {
        return providerOwnerId;
    }

    public void setProviderOwnerId(String providerOwnerId) {
        this.providerOwnerId = providerOwnerId;
    }

    public String getProviderRegionId() {
        return providerRegionId;
    }

    public void setProviderRegionId(String providerRegionId) {
        this.providerRegionId = providerRegionId;
    }

    public String toString() {
        return (providerServerId + " [" + currentState + "]");
    }

}
