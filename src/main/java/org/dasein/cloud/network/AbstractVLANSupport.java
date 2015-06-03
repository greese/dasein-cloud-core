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

import org.dasein.cloud.*;
import org.dasein.cloud.compute.ComputeServices;
import org.dasein.cloud.compute.VirtualMachine;
import org.dasein.cloud.compute.VirtualMachineSupport;
import org.dasein.cloud.identity.ServiceAction;
import org.dasein.cloud.util.TagUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Provides baseline support for functionality that is common among implementations, in particular for deprecated methods.
 * <p>Created by George Reese: 1/29/13 9:56 AM</p>
 * @author George Reese
 * @version 2013.04
 * @since 2013.04
 */
public abstract class AbstractVLANSupport<T extends CloudProvider> extends AbstractProviderService<T> implements VLANSupport {

    protected AbstractVLANSupport(T provider) {
        super(provider);
    }

    @Override
    public Route addRouteToAddress(@Nonnull String routingTableId, @Nonnull IPVersion version, @Nullable String destinationCidr, @Nonnull String address) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Routing tables are not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public Route addRouteToGateway(@Nonnull String routingTableId, @Nonnull IPVersion version, @Nullable String destinationCidr, @Nonnull String gatewayId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Routing tables are not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public Route addRouteToNetworkInterface(@Nonnull String routingTableId, @Nonnull IPVersion version, @Nullable String destinationCidr, @Nonnull String nicId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Routing tables are not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public Route addRouteToVirtualMachine(@Nonnull String routingTableId, @Nonnull IPVersion version, @Nullable String destinationCidr, @Nonnull String vmId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Routing tables are not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    @Deprecated
    public boolean allowsMultipleTrafficTypesOverSubnet() throws CloudException, InternalException {
        return getCapabilities().allowsMultipleTrafficTypesOverSubnet();
    }

    @Override
    @Deprecated
    public boolean allowsMultipleTrafficTypesOverVlan() throws CloudException, InternalException {
        return getCapabilities().allowsMultipleTrafficTypesOverVlan();
    }


    @Override
    @Deprecated
    public boolean allowsNewNetworkInterfaceCreation() throws CloudException, InternalException {
        return getCapabilities().allowsNewNetworkInterfaceCreation();
    }

    @Override
    @Deprecated
    public boolean allowsNewVlanCreation() throws CloudException, InternalException {
        return getCapabilities().allowsNewVlanCreation();
    }

    @Override
    @Deprecated
    public boolean allowsNewRoutingTableCreation() throws CloudException, InternalException {
      return getCapabilities().allowsNewRoutingTableCreation();
    }

    @Override
    @Deprecated
    public boolean allowsNewSubnetCreation() throws CloudException, InternalException {
        return getCapabilities().allowsNewSubnetCreation();
    }

    @Override
    public void assignRoutingTableToSubnet(@Nonnull String subnetId, @Nonnull String routingTableId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Routing tables are not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public void disassociateRoutingTableFromSubnet(@Nonnull String subnetId, @Nonnull String routingTableId) throws CloudException, InternalException {
      throw new OperationNotSupportedException("Routing tables are not currently implemented for " + getProvider().getCloudName());
    }
    @Override
    public void assignRoutingTableToVlan(@Nonnull String vlanId, @Nonnull String routingTableId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Routing tables are not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public void attachNetworkInterface(@Nonnull String nicId, @Nonnull String vmId, int index) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Network interfaces are not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public String createInternetGateway(@Nonnull String vlanId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Internet gateways are not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public @Nonnull String createRoutingTable(@Nonnull String vlanId, @Nonnull String name, @Nonnull String description) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Routing tables are not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public @Nonnull NetworkInterface createNetworkInterface(@Nonnull NICCreateOptions options) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Network interfaces are not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    @Deprecated
    public @Nonnull Subnet createSubnet(@Nonnull String cidr, @Nonnull String inProviderVlanId, @Nonnull String name, @Nonnull String description) throws CloudException, InternalException {
        return createSubnet(SubnetCreateOptions.getInstance(inProviderVlanId, cidr, name, description));
    }

    @Override
    public @Nonnull Subnet createSubnet(@Nonnull SubnetCreateOptions options) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Subnets are not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public @Nonnull VLAN createVlan(@Nonnull String cidr, @Nonnull String name, @Nonnull String description, @Nonnull String domainName, @Nonnull String[] dnsServers, @Nonnull String[] ntpServers) throws CloudException, InternalException {
        throw new OperationNotSupportedException("VLANs are not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public @Nonnull VLAN createVlan(@Nonnull VlanCreateOptions vco) throws CloudException, InternalException {
      throw new OperationNotSupportedException("VLANs are not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public void detachNetworkInterface(@Nonnull String nicId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Network interfaces are not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    @Deprecated
    public int getMaxNetworkInterfaceCount() throws CloudException, InternalException {
        return getCapabilities().getMaxNetworkInterfaceCount();
    }

    @Override
    @Deprecated
    public int getMaxVlanCount() throws CloudException, InternalException {
        return getCapabilities().getMaxVlanCount();
    }

    @Override
    public NetworkInterface getNetworkInterface(@Nonnull String nicId) throws CloudException, InternalException {
        for( NetworkInterface nic : listNetworkInterfaces() ) {
            if( nicId.equals(nic.getProviderNetworkInterfaceId()) ) {
                return nic;
            }
        }
        return null;
    }

    @Override
    public RoutingTable getRoutingTableForSubnet(@Nonnull String subnetId) throws CloudException, InternalException {
        return null;
    }

    @Override
    @Deprecated
    public @Nonnull Requirement getRoutingTableSupport() throws CloudException, InternalException {
        return getCapabilities().getRoutingTableSupport();
    }

    @Override
    public RoutingTable getRoutingTableForVlan(@Nonnull String vlanId) throws CloudException, InternalException {
        return null;
    }

    @Override
    public RoutingTable getRoutingTable(@Nonnull String id) throws CloudException, InternalException {
      return null;
    }

    @Override
    public Subnet getSubnet(@Nonnull String subnetId) throws CloudException, InternalException {
        for( VLAN vlan : listVlans() ) {
            for( Subnet subnet : listSubnets(vlan.getProviderVlanId()) ) {
                if( subnet.getProviderSubnetId().equals(subnetId) ) {
                    return subnet;
                }
            }
        }
        return null;
    }

    @Override
    @Deprecated
    public @Nonnull Requirement getSubnetSupport() throws CloudException, InternalException {
        return getCapabilities().getSubnetSupport();
    }

    @Override
    public VLAN getVlan(@Nonnull String vlanId) throws CloudException, InternalException {
        for( VLAN vlan : listVlans() ) {
            if( vlan.getProviderVlanId().equals(vlanId) ) {
                return vlan;
            }
        }
        return null;
    }

    @Override
    @Deprecated
    public @Nonnull Requirement identifySubnetDCRequirement() {
        try {
            return getCapabilities().identifySubnetDCRequirement();
        } catch( CloudException e ) {
        } catch( InternalException e ) {
        }
        throw new RuntimeException("Unable to identify subnet DC requirement.");
    }

    @Override
    public boolean isConnectedViaInternetGateway(@Nonnull String vlanId) throws CloudException, InternalException {
        return false;
    }

    @Override
    @Deprecated
    public boolean isNetworkInterfaceSupportEnabled() throws CloudException, InternalException {
        return getCapabilities().isNetworkInterfaceSupportEnabled();
    }

    @Override
    @Deprecated
    public boolean isSubnetDataCenterConstrained() throws CloudException, InternalException {
        return getCapabilities().isSubnetDataCenterConstrained();
    }

    @Override
    @Deprecated
    public boolean isVlanDataCenterConstrained() throws CloudException, InternalException {
        return getCapabilities().isVlanDataCenterConstrained();
    }

    @Override
    public @Nonnull Iterable<String> listFirewallIdsForNIC(@Nonnull String nicId) throws CloudException, InternalException {
        return Collections.emptyList();
    }

    @Override
    public @Nonnull Iterable<ResourceStatus> listNetworkInterfaceStatus() throws CloudException, InternalException {
        List<ResourceStatus> status = new ArrayList<ResourceStatus>();

        for( NetworkInterface nic : listNetworkInterfaces() ) {
            status.add(new ResourceStatus(nic.getProviderNetworkInterfaceId(), nic.getCurrentState()));
        }
        return status;
    }

    @Override
    public @Nonnull Iterable<NetworkInterface> listNetworkInterfaces() throws CloudException, InternalException {
        return Collections.emptyList();
    }

    @Override
    public @Nonnull Iterable<NetworkInterface> listNetworkInterfacesForVM(@Nonnull String forVmId) throws CloudException, InternalException {
        List<NetworkInterface> nics = new ArrayList<NetworkInterface>();

        for( NetworkInterface nic : listNetworkInterfaces() ) {
            if( forVmId.equals(nic.getProviderVirtualMachineId()) ) {
                nics.add(nic);
            }
        }
        return nics;
    }

    @Override
    public @Nonnull Iterable<NetworkInterface> listNetworkInterfacesInSubnet(@Nonnull String subnetId) throws CloudException, InternalException {
        List<NetworkInterface> nics = new ArrayList<NetworkInterface>();

        for( NetworkInterface nic : listNetworkInterfaces() ) {
            if( subnetId.equals(nic.getProviderSubnetId()) ) {
                nics.add(nic);
            }
        }
        return nics;
    }

    @Override
    public @Nonnull Iterable<NetworkInterface> listNetworkInterfacesInVLAN(@Nonnull String vlanId) throws CloudException, InternalException {
        List<NetworkInterface> nics = new ArrayList<NetworkInterface>();

        for( NetworkInterface nic : listNetworkInterfaces() ) {
            if( vlanId.equals(nic.getProviderVlanId()) ) {
                nics.add(nic);
            }
        }
        return nics;
    }

    @Override
    public @Nonnull Iterable<Networkable> listResources(@Nonnull String inVlanId) throws CloudException, InternalException {
        List<Networkable> resources = new ArrayList<Networkable>();
        NetworkServices network = getProvider().getNetworkServices();

        if( network != null ) {
            FirewallSupport fwSupport = network.getFirewallSupport();

            if( fwSupport != null ) {
                for( Firewall fw : fwSupport.list() ) {
                    if( inVlanId.equals(fw.getProviderVlanId()) ) {
                        resources.add(fw);
                    }
                }
            }

            IpAddressSupport ipSupport = network.getIpAddressSupport();

            if( ipSupport != null ) {
                for( IPVersion version : ipSupport.getCapabilities().listSupportedIPVersions() ) {
                    for( IpAddress addr : ipSupport.listIpPool(version, false) ) {
                        if( inVlanId.equals(addr.getProviderVlanId()) ) {
                            resources.add(addr);
                        }
                    }

                }
            }
            for( RoutingTable table : listRoutingTablesForVlan(inVlanId) ) {
                resources.add(table);
            }
            ComputeServices compute = getProvider().getComputeServices();
            VirtualMachineSupport vmSupport = compute == null ? null : compute.getVirtualMachineSupport();
            Iterable<VirtualMachine> vms;

            if( vmSupport == null ) {
                vms = Collections.emptyList();
            }
            else {
                vms = vmSupport.listVirtualMachines();
            }
            for( Subnet subnet : listSubnets(inVlanId) ) {
                resources.add(subnet);
                for( VirtualMachine vm : vms ) {
                    if( subnet.getProviderSubnetId().equals(vm.getProviderVlanId()) ) {
                        resources.add(vm);
                    }
                }
            }
        }
        return resources;
    }

    @Override
    public @Nonnull Iterable<RoutingTable> listRoutingTablesForSubnet(@Nonnull String subnetId) throws CloudException, InternalException {
      return Collections.emptyList();
    }

    @Override
    @Deprecated
    public @Nonnull Iterable<RoutingTable> listRoutingTables(@Nonnull String vlanId) throws CloudException, InternalException {
        return listRoutingTablesForVlan(vlanId);
    }

    @Override
    public @Nonnull Iterable<RoutingTable> listRoutingTablesForVlan(@Nonnull String vlanId) throws CloudException, InternalException {
      return Collections.emptyList();
    }

    @Override
    public @Nonnull Iterable<Subnet> listSubnets(@Nullable String vlanId) throws CloudException, InternalException {
        return Collections.emptyList();
    }

    @Override
    @Deprecated
    public @Nonnull Iterable<IPVersion> listSupportedIPVersions() throws CloudException, InternalException {
        return getCapabilities().listSupportedIPVersions();
    }

    @Override
    public @Nonnull Iterable<ResourceStatus> listVlanStatus() throws CloudException, InternalException {
        List<ResourceStatus> status = new ArrayList<ResourceStatus>();
        for( VLAN vlan : listVlans() ) {
            status.add(new ResourceStatus(vlan.getProviderVlanId(), vlan.getCurrentState()));
        }
        return status;
    }

    @Override
    public @Nonnull Iterable<VLAN> listVlans() throws CloudException, InternalException {
        return Collections.emptyList();
    }

    @Override
    public @Nonnull String[] mapServiceAction(@Nonnull ServiceAction action) {
        return new String[0];
    }

    @Override
    public void removeInternetGateway(@Nonnull String vlanId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Internet gateways are not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public void removeNetworkInterface(@Nonnull String nicId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Network interfaces are not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public void removeRoute(@Nonnull String routingTableId, @Nonnull String destinationCidr) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Routing tables are not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public void removeRoutingTable(@Nonnull String routingTableId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Routing tables are not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public void removeSubnet(String providerSubnetId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Subnets are not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public void removeVlan(String vlanId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("VLANs are not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public void removeSubnetTags(@Nonnull String subnetId, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void removeSubnetTags(@Nonnull String[] subnetIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        for( String id : subnetIds ) {
            removeSubnetTags(id, tags);
        }
    }

    @Override
    public void removeVLANTags(@Nonnull String vlanId, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void removeVLANTags(@Nonnull String[] vlanIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        for( String id : vlanIds ) {
            removeVLANTags(id, tags);
        }
    }

    @Override
    @Deprecated
    public boolean supportsInternetGatewayCreation() throws CloudException, InternalException {
        return getCapabilities().supportsInternetGatewayCreation();
    }

    @Override
    @Deprecated
    public boolean supportsRawAddressRouting() throws CloudException, InternalException {
        return getCapabilities().supportsRawAddressRouting();
    }

    @Override
    public void updateRoutingTableTags(@Nonnull String routingTableId, @Nonnull Tag... tags) throws CloudException, InternalException{
        throw new OperationNotSupportedException("Routing table tags are not supported in " + getProvider().getCloudName());
    }

    @Override
    public void updateSubnetTags(@Nonnull String subnetId, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void updateSubnetTags(@Nonnull String[] subnetIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        for( String id : subnetIds ) {
            updateSubnetTags(id, tags);
        }
    }

    @Override
    public void updateVLANTags(@Nonnull String vlanId, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void updateVLANTags(@Nonnull String[] vlanIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        for( String id : vlanIds ) {
            updateVLANTags(id, tags);
        }
    }

    @Override
    public void updateInternetGatewayTags( @Nonnull String internetGatewayId, @Nonnull Tag... tags ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Internet gateway tags are not supported in " + getProvider().getCloudName());
    }

    @Override
    public void updateInternetGatewayTags(@Nonnull String[] internetGatewayIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        for (String internetGatewayId : internetGatewayIds) {
            updateInternetGatewayTags(internetGatewayId, tags);
        }
    }

    @Override
    public void updateRoutingTableTags(@Nonnull String[] routingTableIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        for (String routingTableId : routingTableIds) {
            updateRoutingTableTags(routingTableId, tags);
        }
    }

    @Override
    public void removeRoutingTableTags(@Nonnull String[] routingTableIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        for (String routingTableId : routingTableIds) {
            removeRoutingTableTags(routingTableId, tags);
        }
    }

    @Override
    public void removeInternetGatewayTags(@Nonnull String internetGatewayId, @Nonnull Tag... tags) throws CloudException, InternalException{
        throw new OperationNotSupportedException("Internet Gateway tags are not supported in " + getProvider().getCloudName());
    }

    @Override
    public void removeInternetGatewayTags(@Nonnull String[] internetGatewayIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        for (String internetGatewayId : internetGatewayIds) {
            removeInternetGatewayTags(internetGatewayId, tags);
        }
    }

    @Override
    public void setSubnetTags( @Nonnull String[] subnetIds, @Nonnull Tag... tags ) throws CloudException, InternalException {
        for( String id : subnetIds ) {

            Tag[] collectionForDelete = TagUtils.getTagsForDelete(getSubnet(id).getTags(), tags);

            if( collectionForDelete.length != 0 ) {
                removeSubnetTags(id, collectionForDelete);
            }

            updateSubnetTags(id, tags);
        }
    }

    @Override
    public void setRoutingTableTags( @Nonnull String[] routingTableIds, @Nonnull Tag... tags ) throws CloudException, InternalException {
        for( String id : routingTableIds ) {
            Tag[] collectionForDelete = TagUtils.getTagsForDelete(getRoutingTable(id).getTags(), tags);

            if( collectionForDelete.length != 0 ) {
                removeRoutingTableTags(id, collectionForDelete);
            }

            updateRoutingTableTags(id, tags);
        }
    }

    @Override
    public void setInternetGatewayTags( @Nonnull String[] internetGatewayIds, @Nonnull Tag... tags ) throws CloudException, InternalException {
        for( String id : internetGatewayIds ) {
            Tag[] collectionForDelete = TagUtils.getTagsForDelete(getInternetGatewayById(id).getTags(), tags);

            if( collectionForDelete.length != 0 ) {
                removeInternetGatewayTags(id, collectionForDelete);
            }

            updateInternetGatewayTags(id, tags);
        }
    }
    
    @Override
    public void setVLANTags( @Nonnull String[] vlanIds, @Nonnull Tag... tags ) throws CloudException, InternalException {
        for( String id : vlanIds ) {

            Tag[] collectionForDelete = TagUtils.getTagsForDelete(getVlan(id).getTags(), tags);

            if( collectionForDelete.length != 0 ) {
                removeVLANTags(id, collectionForDelete);
            }

            updateVLANTags(id, tags);
        }
    }
    
    @Override
    public void setVLANTags( @Nonnull String vlanId, @Nonnull Tag... tags ) throws CloudException, InternalException {
        setVLANTags(new String[]{vlanId}, tags);
    }

    @Override
    public void setSubnetTags( @Nonnull String subnetId, @Nonnull Tag... tags ) throws CloudException, InternalException {
        setSubnetTags(new String[]{subnetId}, tags);
    }

    @Override
    public void setRoutingTableTags( @Nonnull String routingTableId, @Nonnull Tag... tags ) throws CloudException, InternalException {
        setRoutingTableTags(new String[]{routingTableId}, tags);
    }

    @Override
    public void setInternetGatewayTags( @Nonnull String internetGatewayId, @Nonnull Tag... tags ) throws CloudException, InternalException {
        setInternetGatewayTags(new String[]{internetGatewayId}, tags);
    }

    public void removeRoutingTableTags(@Nonnull String routingTableId, @Nonnull Tag... tags) throws CloudException, InternalException{
        throw new OperationNotSupportedException("Routing table tags are not supported in " + getProvider().getCloudName());
    }

    @Override
    public void removeInternetGatewayById( @Nonnull String id ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Internet gateways are not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public @Nonnull Iterable<InternetGateway> listInternetGateways( @Nullable String vlanId ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Internet gateways are not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public @Nullable InternetGateway getInternetGatewayById( @Nonnull String gatewayId ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Internet gateways are not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public @Nullable String getAttachedInternetGatewayId( @Nonnull String vlanId ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Internet gateways are not currently implemented for " + getProvider().getCloudName());
    }
}
