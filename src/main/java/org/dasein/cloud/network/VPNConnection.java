/**
 * ========= CONFIDENTIAL =========
 *
 * Copyright (C) 2012 enStratus Networks Inc - ALL RIGHTS RESERVED
 *
 * ====================================================================
 *  NOTICE: All information contained herein is, and remains the
 *  property of enStratus Networks Inc. The intellectual and technical
 *  concepts contained herein are proprietary to enStratus Networks Inc
 *  and may be covered by U.S. and Foreign Patents, patents in process,
 *  and are protected by trade secret or copyright law. Dissemination
 *  of this information or reproduction of this material is strictly
 *  forbidden unless prior written permission is obtained from
 *  enStratus Networks Inc.
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
