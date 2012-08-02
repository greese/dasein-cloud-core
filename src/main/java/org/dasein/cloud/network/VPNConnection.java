/**
 * Copyright (C) 2009-2012 enStratus Networks Inc.
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

/**
 * Represents an established connection between a cloud VPN and a customer gateway.
 * <p>Created by George Reese: 6/26/12 1:29 PM</p>
 * @author George Reese (george.reese@imaginary.com)
 * @since 2012-07
 * @version 2012-07 initial version
 */
@SuppressWarnings("UnusedDeclaration")
public class VPNConnection {
    private String      configurationXml;
    private VPNConnectionState currentState;
    private VPNProtocol protocol;
    private String      providerGatewayId;
    private String      providerVpnId;
    private String      providerVpnConnectionId;

    public VPNConnection() { }
    
    public String getConfigurationXml() {
        return configurationXml;
    }

    public void setConfigurationXml(String configurationXml) {
        this.configurationXml = configurationXml;
    }

    public VPNConnectionState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(VPNConnectionState currentState) {
        this.currentState = currentState;
    }

    public VPNProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(VPNProtocol protocol) {
        this.protocol = protocol;
    }

    public String getProviderGatewayId() {
        return providerGatewayId;
    }

    public void setProviderGatewayId(String providerGatewayId) {
        this.providerGatewayId = providerGatewayId;
    }

    public String getProviderVpnId() {
        return providerVpnId;
    }

    public void setProviderVpnId(String providerVpnId) {
        this.providerVpnId = providerVpnId;
    }

    public String getProviderVpnConnectionId() {
        return providerVpnConnectionId;
    }

    public void setProviderVpnConnectionId(String vpnConnectionId) {
        this.providerVpnConnectionId = vpnConnectionId;
    }
    
    public String toString() {
        return (providerVpnId + " -> " + providerGatewayId);
    }
}
