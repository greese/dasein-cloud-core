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

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.Requirement;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Options for provisioning a load balancer in the cloud. Different clouds have very different requirements on
 * what is minimally necessary when provisioning a load balancer. Verify that the information you are providing is
 * proper by checking the meta-data in {@link LoadBalancerSupport}.
 * <p>Created by George Reese: 3/7/13 9:02 PM</p>
 * @author George Reese
 * @version 2013.04 initial version
 * @since 2013.04
 */
public class LoadBalancerCreateOptions {
    /**
     * Constructs a minimally acceptable set of options for provisioning a load balancer in a cloud. It is nearly
     * certain that this will not be sufficient for actually building the load balancer, but it will provide the
     * minimum data required by Dasein Cloud.
     * @param name the name of the load balancer to provision
     * @param description a user-friendly description for the load balancer to provision
     * @return a set of creation options for building a load balancer
     */
    static public LoadBalancerCreateOptions getInstance(@Nonnull String name, @Nonnull String description) {
        LoadBalancerCreateOptions options = new LoadBalancerCreateOptions();

        options.name = name;
        options.description = description;
        return options;
    }

    /**
     * Constructs a minimally acceptable set of options for provisioning a load balancer in a cloud that requires you
     * to specify a static IP address ID. It is nearly certain that this will not be sufficient for actually building
     * the load balancer, but it will provide the minimum data required by Dasein Cloud.
     * @param name the name of the load balancer to provision
     * @param description a user-friendly description for the load balancer to provision
     * @param atIpAddressId the unique ID of the static IP address to use in creating the load balancer
     * @return a set of creation options for building a load balancer
     */
    static public LoadBalancerCreateOptions getInstance(@Nonnull String name, @Nonnull String description, @Nullable String atIpAddressId) {
        LoadBalancerCreateOptions options = new LoadBalancerCreateOptions();

        options.name = name;
        options.description = description;
        options.providerIpAddressId = atIpAddressId;
        return options;
    }

    private List<LoadBalancerEndpoint>      endpoints;
    private List<String>                    providerDataCenterIds;
    private List<String>                    providerSubnetIds;
    private List<String>                    firewallIds;
    private String                          providerIpAddressId;
    private String                          description;
    private List<LbListener>                listeners;
    private Map<String,Object>              metaData;
    private String                          name;
    private String                          providerVlanId;
    private LbType                          type;
    private HealthCheckOptions              healthCheckOptions;
    private LbAttributesOptions             lbAttributesOptions;

    private LoadBalancerCreateOptions() { }

    /**
     * Builds a load balancer in the cloud using the options specified in this object. It will examine provider meta-data
     * and validate the creation options prior to making a call to the cloud provider.
     * @param provider the provider in which the load balancer will be built
     * @return the ID of the newly created load balancer
     * @throws CloudException an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     * @throws OperationNotSupportedException this cloud does not support load balancer creation
     */
    public @Nonnull String build( @Nonnull CloudProvider provider ) throws CloudException, InternalException {
        NetworkServices services = provider.getNetworkServices();

        if( services == null ) {
            throw new OperationNotSupportedException("Network services are not supported in " + provider.getCloudName());
        }
        LoadBalancerSupport support = services.getLoadBalancerSupport();

        if( support == null ) {
            throw new OperationNotSupportedException("Load balancers are not supported in " + provider.getCloudName());
        }
        if( support.getCapabilities().identifyListenersOnCreateRequirement().equals(Requirement.REQUIRED) && ( listeners == null || listeners.isEmpty() ) ) {
            throw new CloudException("You must specify at least one listener when creating a load balancer in " + provider.getCloudName());
        }
        if( support.getCapabilities().identifyEndpointsOnCreateRequirement().equals(Requirement.REQUIRED) && ( endpoints == null || endpoints.isEmpty() ) ) {
            throw new CloudException("You must specify at least one endpoint when creating a load balancer in " + provider.getCloudName());
        }
        if( support.getCapabilities().isDataCenterLimited() && ( providerDataCenterIds == null || providerDataCenterIds.isEmpty() ) ) {
            throw new CloudException("You must specify at least one data center when creating a load balancer in " + provider.getCloudName());
        }
        if( support.getCapabilities().identifyVlanOnCreateRequirement().equals(Requirement.REQUIRED) && providerVlanId == null){
            throw new CloudException("You must specify the vlan into which the load balancer will be created in " + provider.getCloudName());
        }
        if( !support.getCapabilities().isAddressAssignedByProvider() && providerIpAddressId == null ) {
            // attempt to find an address
            IpAddressSupport as = services.getIpAddressSupport();

            if( as != null ) {
                for( IPVersion version : support.getCapabilities().listSupportedIPVersions() ) {
                    Iterator<IpAddress> addresses = as.listIpPool(version, true).iterator();

                    if( addresses.hasNext() ) {
                        providerIpAddressId = addresses.next().getProviderIpAddressId();
                        break;
                    }
                }
                if( providerIpAddressId == null ) {
                    for( IPVersion version : support.getCapabilities().listSupportedIPVersions() ) {
                        if( as.getCapabilities().isRequestable(version) ) {
                            providerIpAddressId = as.request(version);
                        }
                    }
                }
            }
        }
        return support.createLoadBalancer(this);
    }

    /**
     * @return a description of the purpose of the load balancer to be created
     */
    public @Nonnull String getDescription() {
        return description;
    }

    /**
     * @return the endpoints that should be established as part of the create operation
     */
    public @Nonnull LoadBalancerEndpoint[] getEndpoints() {
        return (endpoints == null ? new LoadBalancerEndpoint[0] : endpoints.toArray(new LoadBalancerEndpoint[endpoints.size()]));
    }

    /**
     * @return the listeners to set up as part of the load balancer creation process
     */
    public @Nonnull LbListener[] getListeners() {
        return (listeners == null ? new LbListener[0] : listeners.toArray(new LbListener[listeners.size()]));
    }

    /**
     * @return any custom meta-data associated with the load balancer to be created
     */
    public @Nonnull Map<String,Object> getMetaData() {
        return (metaData == null ? new HashMap<String, Object>() : metaData);
    }

    /**
     * @return the name of the load balancer to be created
     */
    public @Nonnull String getName() {
        return name;
    }

    public @Nullable String getProviderVlanId() {return providerVlanId;}

    /**
     * @return the data centers to which this load balancer will be limited
     */
    public @Nonnull String[] getProviderDataCenterIds() {
        if( providerDataCenterIds == null ) {
            return new String[0];
        }
        return providerDataCenterIds.toArray(new String[providerDataCenterIds.size()]);
    }

    /**
     * @return the subnets to which this load balancer will be added
     */
    public @Nonnull String[] getProviderSubnetIds() {
      if( providerSubnetIds == null ) {
        return new String[0];
      }
      return providerSubnetIds.toArray(new String[providerSubnetIds.size()]);
    }

    /**
     * @return the security groups to which this load balancer will be added
     */
    public String[] getFirewallIds() {
        if( firewallIds == null ) {
            return new String[0];
        }
        return firewallIds.toArray(new String[firewallIds.size()]);
    }

    /**
     * @return the IP address you are assigning to this load balancer if the address is required
     */
    public @Nullable String getProviderIpAddressId() {
        return providerIpAddressId;
    }

    /**
     * @return the load balancer type
     */
    public @Nullable LbType getType() {
      return type;
    }

    public @Nullable HealthCheckOptions getHealthCheckOptions(){
        return this.healthCheckOptions;
    }

    /**
     * @return the load balancer attributes
     */
    public @Nullable LbAttributesOptions getLbAttributesOptions() {
        return lbAttributesOptions;
    }

    /**
     * Adds the specified listeners into the list of listeners that will be part of the load balancer creation.
     * @param listeners the listeners to include in the creation
     * @return this
     */
    public @Nonnull LoadBalancerCreateOptions havingListeners(@Nonnull LbListener ... listeners) {
        if( this.listeners == null ) {
            this.listeners = new ArrayList<LbListener>();
        }
        Collections.addAll(this.listeners, listeners);
        return this;
    }

    /**
     * Adds the specified data centers into this list of data centers to which this load balancer rotation will be limited.
     * @param dataCenterIds the IDs of the data centers to limit the load balancer to
     * @return this
     */
    public @Nonnull LoadBalancerCreateOptions limitedTo(@Nonnull String ... dataCenterIds) {
        if( providerDataCenterIds == null ) {
            providerDataCenterIds = new ArrayList<String>();
        }
        Collections.addAll(providerDataCenterIds, dataCenterIds);
        return this;
    }

    /**
     * Adds the specified subnets into this list of subnets to which this load balancer rotation will be added.
     * @param providerSubnetIds the IDs of the subnets to add the load balancer to
     * @return this
     */
    public @Nonnull LoadBalancerCreateOptions withProviderSubnetIds(@Nonnull String ... providerSubnetIds) {
      if( this.providerSubnetIds == null ) {
        this.providerSubnetIds = new ArrayList<String>();
      }
      Collections.addAll(this.providerSubnetIds, providerSubnetIds);
      return this;
    }

    /**
     * Adds the specified firewalls into this list of firewalls to which this load balancer rotation will be added.
     *
     * @param firewallIds the IDs of the firewalls to add the load balancer to
     * @return this
     */
    public @Nonnull LoadBalancerCreateOptions withFirewalls( @Nonnull String... firewallIds ) {
        if( this.firewallIds == null ) {
            this.firewallIds = new ArrayList<String>();
        }
        Collections.addAll(this.firewallIds, firewallIds);
        return this;
    }

    @Override
    public @Nonnull String toString() {
        return ("[" + name + " - " + providerIpAddressId + " - " + listeners + "-" + endpoints + "]");
    }

    /**
     * Adds the specified IP addresses as endpoints that will be established as part of the load balancer creation
     * process.
     * @param ipAddresses the IP address to add to the pool
     * @return this
     */
    public @Nonnull LoadBalancerCreateOptions withIpAddresses(@Nonnull String ... ipAddresses) {
        if( endpoints == null ) {
            endpoints = new ArrayList<LoadBalancerEndpoint>();
        }
        for( String ipAddress : ipAddresses ) {
            endpoints.add(LoadBalancerEndpoint.getInstance(LbEndpointType.IP, ipAddress, LbEndpointState.ACTIVE));
        }
        return this;
    }

    /**
     * Specifies a vlanId into which the load balancer should be created if required by the cloud
     * @param providerVlanId the Id of the vlan into which the load balancer will be created
     * @return this
     */
    public @Nonnull LoadBalancerCreateOptions withVlanId(@Nullable String providerVlanId){
        this.providerVlanId = providerVlanId;
        return this;
    }

    /**
     * Adds the specified virtual machines as endpoints that will be established as part of the load balancer creation
     * process.
     * @param virtualMachineIds the IDs of the virtual machines to include in the load balancer rotation
     * @return this
     */
    public @Nonnull LoadBalancerCreateOptions withVirtualMachines(@Nonnull String ... virtualMachineIds) {
        if( endpoints == null ) {
            endpoints = new ArrayList<LoadBalancerEndpoint>();
        }
        for( String virtualMachineId : virtualMachineIds ) {
            endpoints.add(LoadBalancerEndpoint.getInstance(LbEndpointType.VM, virtualMachineId, LbEndpointState.ACTIVE));
        }
        return this;
    }

    /**
     * Adds meta-data to associate with the load balancer to be created. This method is additive; it will not wipe existing
     * meta-data.
     * @param metaData the meta-data to add
     * @return this
     */
    public @Nonnull LoadBalancerCreateOptions withMetaData(@Nonnull Map<String,Object> metaData) {
        if( this.metaData == null ) {
            this.metaData = new HashMap<String, Object>();
        }
        this.metaData.putAll(metaData);
        return this;
    }

    /**
     * Sets the load balancer type.
     * @param type the load balancer type
     * @return this
     */
    public @Nonnull LoadBalancerCreateOptions asType( @Nullable LbType type ) {
      this.type = type;
      return this;
    }

    /**
     * Adds the specified health check options to be associated with the load balancer on creation.
     * When provided Dasein will attempt to create a health check along side the load balancer
     * @param options the Health Check Options
     * @return this
     */
    public @Nonnull LoadBalancerCreateOptions withHealthCheckOptions(@Nullable HealthCheckOptions options){
        this.healthCheckOptions = options;
        return this;
    }

    /**
     * Adds the specified health check options to be associated with the load balancer on creation.
     * @param lbAttributesOptions the attributes
     * @return this
     */
    public @Nonnull LoadBalancerCreateOptions withLbAttributeOptions(@Nullable LbAttributesOptions lbAttributesOptions ) {
        this.lbAttributesOptions = lbAttributesOptions;
        return this;
    }

}
