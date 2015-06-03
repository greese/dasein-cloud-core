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

import org.dasein.cloud.AbstractProviderService;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.ProviderContext;
import org.dasein.cloud.Requirement;
import org.dasein.cloud.ResourceStatus;
import org.dasein.cloud.Tag;
import org.dasein.cloud.identity.ServiceAction;
import org.dasein.cloud.util.TagUtils;

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
public abstract class AbstractLoadBalancerSupport<T extends CloudProvider> extends AbstractProviderService<T> implements
        LoadBalancerSupport {

    protected AbstractLoadBalancerSupport(T provider) {
        super(provider);
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
    public void addListeners( @Nonnull String toLoadBalancerId, @Nullable LbListener[] listeners ) throws CloudException, InternalException{
        throw new OperationNotSupportedException("Adding listeners to an existing load balancer is not currently implemented for " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

    @Override
    public void removeListeners( @Nonnull String toLoadBalancerId, @Nullable LbListener[] listeners ) throws CloudException, InternalException{
        throw new OperationNotSupportedException("Removing listeners from an existing load balancer is not currently implemented for " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

    @Override
    public void addServers(@Nonnull String toLoadBalancerId, @Nonnull String ... serverIdsToAdd) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Adding VM endpoints to an existing load balancer is not currently implemented for " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

    @Override
    public @Nonnull String createLoadBalancer(@Nonnull LoadBalancerCreateOptions options) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Load balancer creation is not implemented in " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

    @Override
    public SSLCertificate createSSLCertificate(@Nonnull SSLCertificateCreateOptions options) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Creating a server certificate is not implemented in " + getContext().getRegionId() + " of " + getProvider().getCloudName());
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
    public @Nullable SSLCertificate getSSLCertificate(@Nonnull String certificateName) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Getting server certificates is not implemented in " + getContext().getRegionId() + " of " + getProvider().getCloudName());
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
    public @Nonnull String[] mapServiceAction(@Nonnull ServiceAction action) {
        return new String[0];
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
        throw new OperationNotSupportedException("Removing a load balancer is not implemented in " +
                getContext().getRegionId() + " of " + getProvider().getCloudName());
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
    public void setSSLCertificate( @Nonnull SetLoadBalancerSSLCertificateOptions options ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Setting SSL certificate is not implemented in " + getContext().getRegionId() + " of " + getProvider().getCloudName());
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
    public void detachHealthCheckFromLoadBalancer(@Nonnull String providerLoadBalancerId, @Nonnull String providerLBHealthCheckId) throws CloudException, InternalException{
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
    public LoadBalancerHealthCheck modifyHealthCheck(@Nonnull String providerLBHealthCheckId, @Nonnull HealthCheckOptions options) throws InternalException, CloudException{
        throw new OperationNotSupportedException("Health Checks have not been implemented for " + getProvider().getCloudName());
    }

    @Override
    public void removeLoadBalancerHealthCheck(@Nonnull String providerLoadBalancerId) throws CloudException, InternalException{
        throw new OperationNotSupportedException("Health Checks have not been implemented for " + getProvider().getCloudName());
    }

    @Override
    public void setFirewalls(@Nonnull String providerLoadBalancerId, @Nonnull String... firewallIds) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Setting firewalls have not been implemented for " + getProvider().getCloudName());
    }

    @Override
    public void modifyLoadBalancerAttributes( @Nonnull String id, @Nonnull LbAttributesOptions options ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Modify attributes have not been implemented for " + getProvider().getCloudName());
    }

    @Override
    public LbAttributesOptions getLoadBalancerAttributes( @Nonnull String id ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Get attributes have not been implemented for " + getProvider().getCloudName());
    }

    @Override
    public void attachLoadBalancerToSubnets(@Nonnull String toLoadBalancerId, @Nonnull String... subnetIdsToAdd) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Attaching load balancer to subnets has not been implemented for " + getProvider().getCloudName());
    }

    @Override
    public void detachLoadBalancerFromSubnets(@Nonnull String fromLoadBalancerId, @Nonnull String... subnetIdsToDelete) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Detaching load balancer to subnets has not been implemented for " + getProvider().getCloudName());
    }
    
    @Override
    public void removeTags(@Nonnull String loadBalancerId, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void removeTags(@Nonnull String[] loadBalancerIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        for( String id : loadBalancerIds ) {
            removeTags(id, tags);
        }
    }
    
    @Override
    public void updateTags(@Nonnull String loadBalancerId, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void updateTags(@Nonnull String[] loadBalancerIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        for( String id : loadBalancerIds ) {
            updateTags(id, tags);
        }
    }

    @Override
    public void setTags( @Nonnull String loadBalancerId, @Nonnull Tag... tags ) throws CloudException, InternalException {
        setTags(new String[]{loadBalancerId}, tags);
    }

    @Override
    public void setTags( @Nonnull String[] loadBalancerIds, @Nonnull Tag... tags ) throws CloudException, InternalException {
        for( String id : loadBalancerIds ) {
            Tag[] collectionForDelete = TagUtils.getTagsForDelete(getLoadBalancer(id).getTags(), tags);

            if( collectionForDelete.length != 0) {
                removeTags(id, collectionForDelete);
            }

            updateTags(id, tags);
        }
    }
}
