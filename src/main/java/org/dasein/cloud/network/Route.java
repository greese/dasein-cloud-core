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
 * Represents a single route in a routing table. The route includes a destination CIDR and a gateway ID or address.
 * <p>Created by George Reese: 6/30/12 5:16 PM</p>
 * @author George Reese (george.reese@imaginary.com)
 * @since 2012.07
 * @version 2012.07 initial version
 */
public class Route {
    static public Route getRouteToAddress(@Nonnull IPVersion version, @Nonnull String destination, @Nonnull String gatewayAddress) {
        Route r = new Route();
        
        r.version = version;
        r.destinationCidr = destination;
        r.gatewayAddress = gatewayAddress;
        return r;
    }

    static public Route getRouteToGateway(@Nonnull IPVersion version, @Nonnull String destination, @Nonnull String gatewayId) {
        Route r = new Route();

        r.version = version;
        r.destinationCidr = destination;
        r.gatewayId = gatewayId;
        return r;
    }

    static public Route getRouteToNetworkInterface(@Nonnull IPVersion version, @Nonnull String destination, @Nonnull String nicId) {
        Route r = new Route();

        r.version = version;
        r.destinationCidr = destination;
        r.gatewayNetworkInterfaceId = nicId;
        return r;
    }
    
    static public Route getRouteToVirtualMachine(@Nonnull IPVersion version, @Nonnull String destination, @Nonnull String ownerId, @Nonnull String vmId) {
        Route r = new Route();

        r.version = version;
        r.destinationCidr = destination;
        r.gatewayOwnerId = ownerId;
        r.gatewayVirtualMachineId = vmId;
        return r;
    }

    static public Route getRouteToVirtualMachineAndNetworkInterface(@Nonnull IPVersion version, @Nonnull String destination, @Nonnull String ownerId, @Nonnull String vmId, @Nonnull String nicId) {
        Route r = new Route();

        r.version = version;
        r.destinationCidr = destination;
        r.gatewayOwnerId = ownerId;
        r.gatewayVirtualMachineId = vmId;
        r.gatewayNetworkInterfaceId = nicId;
        return r;
    }

    private String    destinationCidr;
    private String    gatewayAddress;
    private String    gatewayId;
    private String    gatewayOwnerId;
    private String    gatewayNetworkInterfaceId;
    private String    gatewayVirtualMachineId;
    private IPVersion version;
    
    private Route() { }

    public @Nonnull String getDestinationCidr() {
        return destinationCidr;
    }

    public void setDestinationCidr(String destinationCidr) {
      this.destinationCidr = destinationCidr;
    }

    public @Nullable String getGatewayAddress() {
        return gatewayAddress;
    }

    public void setGatewayAddress(String gatewayAddress) {
      this.gatewayAddress = gatewayAddress;
    }

    public @Nullable String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
      this.gatewayId = gatewayId;
    }

    public @Nullable String getGatewayNetworkInterfaceId() {
        return gatewayNetworkInterfaceId;
    }

    public void setGatewayNetworkInterfaceId(String gatewayNetworkInterfaceId) {
      this.gatewayNetworkInterfaceId = gatewayNetworkInterfaceId;
    }
    
    public @Nullable String getGatewayOwnerId() {
        return gatewayOwnerId;
    }

    public void setGatewayOwnerId(String gatewayOwnerId) {
      this.gatewayOwnerId = gatewayOwnerId;
    }
    
    public @Nullable String getGatewayVirtualMachineId() {
        return gatewayVirtualMachineId;
    }

    public void setGatewayVirtualMachineId(String gatewayVirtualMachineId) {
      this.gatewayVirtualMachineId = gatewayVirtualMachineId;
    }

    public @Nonnull IPVersion getVersion() {
        return version;
    }

    public void setVersion(IPVersion version) {
      this.version = version;
    }

    public String toString() {
        return (destinationCidr + " [" + version + "] -> " + (gatewayId == null ? gatewayAddress : gatewayId));
    }
}
