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

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.ProviderContext;
import org.dasein.cloud.Requirement;
import org.dasein.cloud.ResourceStatus;
import org.dasein.cloud.identity.ServiceAction;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;

/**
 * Provides a basic implementation of load balancer support that you can extend and customize to support your cloud.
 * <p>Created by George Reese: 3/7/13 9:48 PM</p>
 * @author George Reese
 * @version 2013.04 initial version
 * @since 2013.04
 */
public abstract class AbstractLoadBalancerSupport<T extends CloudProvider> implements LoadBalancerSupport {
    private T provider;

    public AbstractLoadBalancerSupport(@Nonnull T provider) {
        this.provider = provider;
    }

    @Override
    public void addDataCenters(@Nonnull String toLoadBalancerId, @Nonnull String ... dataCenterIdsToAdd) throws CloudException, InternalException {
        if( getCapabilities().isDataCenterLimited() ) {
            throw new OperationNotSupportedException("Adding data centers has not been implemented for " + getContext().getRegionId() + " of " + getProvider().getCloudName());
        }
        else {
            throw new OperationNotSupportedException("Load balancers are not data-center constrained in " + getContext().getRegionId() + " of " + getProvider().getCloudName());
        }
    }

    @Override
    public void addIPEndpoints(@Nonnull String toLoadBalancerId, @Nonnull String ... ipAddresses) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Adding IP endpoints to an existing load balancer is not currently implemented for " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

    @Override
    public void addServers(@Nonnull String toLoadBalancerId, @Nonnull String ... serverIdsToAdd) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Adding VM endpoints to an existing load balancer is not currently implemented for " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

    @Override
    @Deprecated
    public @Nonnull String create(@Nonnull String name, @Nonnull String description, @Nullable String addressId, @Nullable String[] dataCenterIds, @Nullable LbListener[] listeners, @Nullable String[] serverIds, @Nullable String[] subnetIds, @Nullable LbType type) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Load balancer creation is not implemented in " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

    @Override
    public @Nonnull String createLoadBalancer(@Nonnull LoadBalancerCreateOptions options) throws CloudException, InternalException {
        ArrayList<String> serverIds = new ArrayList<String>();

        for( LoadBalancerEndpoint endpoint : options.getEndpoints() ) {
            if( endpoint.getEndpointType().equals(LbEndpointType.VM) ) {
                serverIds.add(endpoint.getEndpointValue());
            }
        }
        //noinspection deprecation
        return create(options.getName(), options.getDescription(), options.getProviderIpAddressId(), options.getProviderDataCenterIds(), options.getListeners(), serverIds.toArray(new String[serverIds.size()]), options.getProviderSubnetIds(), options.getType());
    }

    @Override
    public SSLCertificate createSSLCertificate(@Nonnull SSLCertificateCreateOptions options) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Creating a server certificate is not implemented in " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

    @Override
    public @Nonnull LoadBalancerAddressType getAddressType() throws CloudException, InternalException {
        return LoadBalancerAddressType.DNS;
    }

    /**
     * @return the current authentication context for any calls through this support object
     * @throws CloudException no context was set
     */
    protected @Nonnull ProviderContext getContext() throws CloudException {
        ProviderContext ctx = getProvider().getContext();

        if( ctx == null ) {
            throw new CloudException("No context was specified for this request");
        }
        return ctx;
    }

    @Override
    public LoadBalancer getLoadBalancer(@Nonnull String loadBalancerId) throws CloudException, InternalException {
        for( LoadBalancer lb : listLoadBalancers() ) {
            if( loadBalancerId.equals(lb.getProviderLoadBalancerId()) ) {
                return lb;
            }
        }
        return null;
    }

    @Override
    @Deprecated
    public @Nonnull Iterable<LoadBalancerServer> getLoadBalancerServerHealth(@Nonnull String loadBalancerId) throws CloudException, InternalException {
        ArrayList<LoadBalancerServer> servers = new ArrayList<LoadBalancerServer>();

        for( LoadBalancerEndpoint endpoint : listEndpoints(loadBalancerId) ) {
            if( endpoint.getEndpointType().equals(LbEndpointType.VM) ) {
                LoadBalancerServer server = new LoadBalancerServer();

                server.setCurrentState(LoadBalancerServerState.valueOf(endpoint.getCurrentState().name()));
                server.setCurrentStateDescription(endpoint.getStateDescription());
                server.setCurrentStateReason(endpoint.getStateReason());
                servers.add(server);
            }
        }
        return servers;
    }

    @Override
    @Deprecated
    public @Nonnull Iterable<LoadBalancerServer> getLoadBalancerServerHealth(@Nonnull String loadBalancerId, @Nonnull String... serverIdsToCheck) throws CloudException, InternalException {
        ArrayList<LoadBalancerServer> servers = new ArrayList<LoadBalancerServer>();

        for( LoadBalancerEndpoint endpoint : listEndpoints(loadBalancerId) ) {
            if( endpoint.getEndpointType().equals(LbEndpointType.VM) ) {
                boolean included = false;

                for( String id : serverIdsToCheck ) {
                    if( id.equals(endpoint.getEndpointValue()) ) {
                        included = true;
                        break;
                    }
                }
                if( included ) {
                    LoadBalancerServer server = new LoadBalancerServer();

                    server.setCurrentState(LoadBalancerServerState.valueOf(endpoint.getCurrentState().name()));
                    server.setCurrentStateDescription(endpoint.getStateDescription());
                    server.setCurrentStateReason(endpoint.getStateReason());
                    servers.add(server);
                }
            }
        }
        return servers;
    }

    @Override
    public @Nonnegative int getMaxPublicPorts() throws CloudException, InternalException {
        return 1;
    }

    /**
     * @return the provider object associated with any calls through this support object
     */
    protected final @Nonnull T getProvider() {
        return provider;
    }

    @Override
    public @Nullable SSLCertificate getSSLCertificate(@Nonnull String certificateName) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Getting server certificates is not implemented in " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

    @Deprecated
    public @Nonnull Requirement identifyEndpointsOnCreateRequirement() throws CloudException, InternalException {
        return getCapabilities().identifyEndpointsOnCreateRequirement();
    }

    @Override
    @Deprecated
    public @Nonnull Requirement identifyListenersOnCreateRequirement() throws CloudException, InternalException {
        return getCapabilities().identifyListenersOnCreateRequirement();
    }

    @Override
    @Deprecated
    public boolean isAddressAssignedByProvider() throws CloudException, InternalException {
        return getCapabilities().isAddressAssignedByProvider();
    }

    @Override
    @Deprecated
    public boolean isDataCenterLimited() throws CloudException, InternalException {
        return getCapabilities().isDataCenterLimited();
    }

    @Override
    public @Nonnull Iterable<LoadBalancer> listLoadBalancers() throws CloudException, InternalException {
        return Collections.emptyList();
    }

    @Override
    public @Nonnull Iterable<ResourceStatus> listLoadBalancerStatus() throws CloudException, InternalException {
        ArrayList<ResourceStatus> status = new ArrayList<ResourceStatus>();

        for( LoadBalancer lb : listLoadBalancers() ) {
            status.add(new ResourceStatus(lb.getProviderLoadBalancerId(), lb.getCurrentState()));
        }
        return status;
    }

    @Override
    public @Nonnull Iterable<LoadBalancerEndpoint> listEndpoints(@Nonnull String forLoadBalancerId) throws CloudException, InternalException {
        ArrayList<LoadBalancerEndpoint> endpoints = new ArrayList<LoadBalancerEndpoint>();
        LoadBalancer lb = getLoadBalancer(forLoadBalancerId);

        if( lb == null ) {
            throw new CloudException("No such load balancer: " + forLoadBalancerId);
        }
        @SuppressWarnings("deprecation") String[] ids = lb.getProviderServerIds();

        //noinspection ConstantConditions
        if( ids != null ) {
            for( String id : ids ) {
                endpoints.add(LoadBalancerEndpoint.getInstance(LbEndpointType.VM, id, LbEndpointState.ACTIVE));
            }
        }
        return endpoints;
    }

    @Override
    public @Nonnull Iterable<LoadBalancerEndpoint> listEndpoints(@Nonnull String forLoadBalancerId, @Nonnull LbEndpointType type, @Nonnull String ... endpoints) throws CloudException, InternalException {
        ArrayList<LoadBalancerEndpoint> matches = new ArrayList<LoadBalancerEndpoint>();

        for( LoadBalancerEndpoint endpoint : listEndpoints(forLoadBalancerId) ) {
            if( endpoint.getEndpointType().equals(type) ) {
                boolean included = false;

                for( String value : endpoints ) {
                    if( value.equals(endpoint.getEndpointValue()) ) {
                        included = true;
                        break;
                    }
                }
                if( included ) {
                    matches.add(endpoint);
                }
            }
        }
        return matches;
    }

    @Override
    public @Nonnull Iterable<SSLCertificate> listSSLCertificates() throws CloudException, InternalException {
        throw new OperationNotSupportedException("Listing server certificates is not implemented in " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

    @Override
    @Deprecated
    public @Nonnull Iterable<LbAlgorithm> listSupportedAlgorithms() throws CloudException, InternalException {
        return getCapabilities().listSupportedAlgorithms();
    }

    @Override
    @Deprecated
    public @Nonnull Iterable<LbEndpointType> listSupportedEndpointTypes() throws CloudException, InternalException {
        return getCapabilities().listSupportedEndpointTypes();
    }

    @Override
    @Deprecated
    public @Nonnull Iterable<LbPersistence> listSupportedPersistenceOptions() throws CloudException, InternalException {
        return getCapabilities().listSupportedPersistenceOptions();
    }

    @Override
    @Deprecated
    public @Nonnull Iterable<LbProtocol> listSupportedProtocols() throws CloudException, InternalException {
        return getCapabilities().listSupportedProtocols();
    }

    @Override
    @Deprecated
    public @Nonnull Iterable<IPVersion> listSupportedIPVersions() throws CloudException, InternalException {
        return getCapabilities().listSupportedIPVersions();
    }

    @Override
    public @Nonnull String[] mapServiceAction(@Nonnull ServiceAction action) {
        return new String[0];
    }

    @Override
    @Deprecated
    public void remove(@Nonnull String loadBalancerId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Load balancer removal is not implemented in " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

    @Override
    public void removeDataCenters(@Nonnull String fromLoadBalancerId, @Nonnull String... dataCenterIdsToRemove) throws CloudException, InternalException {
        if( getCapabilities().isDataCenterLimited() ) {
            throw new OperationNotSupportedException("Removing data centers has not been implemented for " + getContext().getRegionId() + " of " + getProvider().getCloudName());
        }
        else {
            throw new OperationNotSupportedException("Load balancers are not data-center constrained in " + getContext().getRegionId() + " of " + getProvider().getCloudName());
        }
    }

    @Override
    public void removeIPEndpoints(@Nonnull String fromLoadBalancerId, @Nonnull String ... addresses) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Removing IP endpoints from an existing load balancer is not currently implemented for " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

    @Override
    public void removeLoadBalancer(@Nonnull String loadBalancerId) throws CloudException, InternalException {
        //noinspection deprecation
        remove(loadBalancerId);
    }

    @Override
    public void removeSSLCertificate(@Nonnull String certificateName) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Removing server certificate is not implemented in " +
                                                 getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

    @Override
    public void removeServers(@Nonnull String fromLoadBalancerId, @Nonnull String... serverIdsToRemove) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Removing VM endpoints from an existing load balancer is not currently implemented for " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

    @Override
    @Deprecated
    public final boolean requiresListenerOnCreate() throws CloudException, InternalException {
        return getCapabilities().identifyListenersOnCreateRequirement().equals(Requirement.REQUIRED);
    }

    @Override
    @Deprecated
    public final boolean requiresServerOnCreate() throws CloudException, InternalException {
        return getCapabilities().identifyEndpointsOnCreateRequirement().equals(Requirement.REQUIRED);
    }

    @Override
    public void setSSLCertificate( @Nonnull SetLoadBalancerSSLCertificateOptions options ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Setting SSL certificate is not implemented in " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

    @Override
    @Deprecated
    public boolean supportsAddingEndpoints() throws CloudException, InternalException {
        return getCapabilities().supportsAddingEndpoints();
    }

    @Override
    @Deprecated
    public boolean supportsMonitoring() throws CloudException, InternalException {
        return getCapabilities().supportsMonitoring();
    }

    @Override
    @Deprecated
    public boolean supportsMultipleTrafficTypes() throws CloudException, InternalException {
        return getCapabilities().supportsMultipleTrafficTypes();
    }

    @Override
    public LoadBalancerHealthCheck createLoadBalancerHealthCheck(@Nullable String name, @Nullable String description, @Nullable String host, @Nullable LoadBalancerHealthCheck.HCProtocol protocol, int port, @Nullable String path, int interval, int timeout, int healthyCount, int unhealthyCount) throws CloudException, InternalException{
        throw new OperationNotSupportedException("Health Checks have not been implemented for " + getProvider().getCloudName());
    }

    @Override
    public LoadBalancerHealthCheck createLoadBalancerHealthCheck(@Nonnull HealthCheckOptions options) throws CloudException, InternalException{
        throw new OperationNotSupportedException("Health Checks have not been implemented for " + getProvider().getCloudName());
    }

    @Override
    public void attachHealthCheckToLoadBalancer(@Nonnull String providerLoadBalancerId, @Nonnull String providerLBHealthCheckId)throws CloudException, InternalException{
        throw new OperationNotSupportedException("Health Checks have not been implemented for " + getProvider().getCloudName());
    }

    @Override
    public Iterable<LoadBalancerHealthCheck> listLBHealthChecks(@Nullable HealthCheckFilterOptions opts) throws CloudException, InternalException{
        throw new OperationNotSupportedException("Health Checks have not been implemented for " + getProvider().getCloudName());
    }

    @Override
    public LoadBalancerHealthCheck getLoadBalancerHealthCheck(@Nonnull String providerLBHealthCheckId, @Nullable String providerLoadBalancerId)throws CloudException, InternalException{
        throw new OperationNotSupportedException("Health Checks have not been implemented for " + getProvider().getCloudName());
    }

    @Override
    @Deprecated
    public HashMap<String, String> getInstanceHealth(@Nonnull String providerLoadBalancerId, @Nullable String providerVirtualMachineId) throws CloudException, InternalException{
        throw new OperationNotSupportedException("Health Checks have not been implemented for " + getProvider().getCloudName());
    }

    @Override
    public LoadBalancerHealthCheck modifyHealthCheck(@Nonnull String providerLBHealthCheckId, @Nonnull HealthCheckOptions options) throws InternalException, CloudException{
        throw new OperationNotSupportedException("Health Checks have not been implemented for " + getProvider().getCloudName());
    }

    @Override
    public void removeLoadBalancerHealthCheck(@Nonnull String providerLoadBalancerId) throws CloudException, InternalException{
        throw new OperationNotSupportedException("Health Checks have not been implemented for " + getProvider().getCloudName());
    }

    @Override
    public void detatchHealthCheck(String loadBalancerId, String heathcheckId) throws CloudException, InternalException{
        throw new OperationNotSupportedException("Health Checks have not been implemented for " + getProvider().getCloudName());
    }

    @Override
    @Deprecated
    public boolean healthCheckRequiresLoadBalancer() throws CloudException, InternalException{
        return getCapabilities().healthCheckRequiresLoadBalancer();
    }

    @Override
    @Deprecated
    public @Nonnull String getProviderTermForLoadBalancer( @Nonnull Locale locale ) {
        try {
            return getCapabilities().getProviderTermForLoadBalancer(locale);
        } catch( CloudException e ) {
        } catch( InternalException e ) {
        }
        throw new RuntimeException("Unable to get a provider term for load balancer.");
    }

    @Override
    public void setFirewalls(@Nonnull String providerLoadBalancerId, @Nonnull String... firewallIds) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Setting firewalls have not been implemented for " + getProvider().getCloudName());
    }
}
