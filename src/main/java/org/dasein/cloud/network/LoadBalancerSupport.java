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
import org.dasein.cloud.identity.ServiceAction;
import org.dasein.cloud.Tag;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Locale;

/**
 * Implements support for cloud load balancing services.
 * @author George Reese
 * @author Cameron Stokes
 * @version 2014.05 overhaul of health checks support
 * @version 2013.04 added Javadoc and did some refactoring
 * @version 2013.02 added support for health checks
 * @since unknown
 */
public interface LoadBalancerSupport extends AccessControlledService {
    static public final ServiceAction ANY                       = new ServiceAction("LB:ANY");

    static public final ServiceAction ADD_DATA_CENTERS          = new ServiceAction("LB:ADD_DC");
    static public final ServiceAction ADD_VMS                   = new ServiceAction("LB:ADD_VM");
    static public final ServiceAction CREATE_LOAD_BALANCER      = new ServiceAction("LB:CREATE_LOAD_BALANCER");
    static public final ServiceAction GET_LOAD_BALANCER         = new ServiceAction("LB:GET_LOAD_BALANCER");
    static public final ServiceAction LIST_LOAD_BALANCER        = new ServiceAction("LB:LIST_LOAD_BALANCER");
    static public final ServiceAction GET_LOAD_BALANCER_SERVER_HEALTH   = new ServiceAction("LB:GET_LOAD_BALANCER_SERVER_HEALTH");
    static public final ServiceAction REMOVE_DATA_CENTERS       = new ServiceAction("LB:REMOVE_DC");
    static public final ServiceAction REMOVE_VMS                = new ServiceAction("LB:REMOVE_VM");
    static public final ServiceAction REMOVE_LOAD_BALANCER      = new ServiceAction("LB:REMOVE_LOAD_BALANCER");
    static public final ServiceAction CONFIGURE_HEALTH_CHECK    = new ServiceAction("LB:CONFIGURE_HEALTH_CHECK");
    static public final ServiceAction LIST_SSL_CERTIFICATES     = new ServiceAction("LB:LIST_SSL_CERTIFICATES");
    static public final ServiceAction GET_SSL_CERTIFICATE       = new ServiceAction("LB:GET_SSL_CERTIFICATE");
    static public final ServiceAction CREATE_SSL_CERTIFICATE    = new ServiceAction("LB:CREATE_SSL_CERTIFICATE");
    static public final ServiceAction DELETE_SSL_CERTIFICATE    = new ServiceAction("LB:DELETE_SSL_CERTIFICATE");
    static public final ServiceAction SET_LB_SSL_CERTIFICATE    = new ServiceAction("LB:SET_SSL_CERTIFICATE");
    static public final ServiceAction CREATE_LOAD_BALANCER_LISTENERS    = new ServiceAction("LB:CREATE_LOAD_BALANCER_LISTENERS");
    static public final ServiceAction DELETE_LOAD_BALANCER_LISTENERS    = new ServiceAction("LB:DELETE_LOAD_BALANCER_LISTENERS");
    static public final ServiceAction SET_FIREWALLS        = new ServiceAction("LB:SET_FIREWALLS");
    static public final ServiceAction ATTACH_LB_TO_SUBNETS    = new ServiceAction("LB:ATTACH_LB_TO_SUBNETS");
    static public final ServiceAction DETACH_LB_FROM_SUBNETS    = new ServiceAction("LB:DETACH_LB_FROM_SUBNETS");
    static public final ServiceAction MODIFY_LB_ATTRIBUTES    = new ServiceAction("LB:MODIFY_LB_ATTRIBUTES");
    static public final ServiceAction DESCRIBE_LOADBALANCER_ATTRIBUTES    = new ServiceAction("LB:DESCRIBE_LOADBALANCER_ATTRIBUTES");

    /**
     * Adds one or more new listeners to your load balancer.
     *
     * @param toLoadBalancerId the load balancer to which data centers are being added
     * @param listeners        the listeners to be established on create
     * @throws CloudException                 an error occurred with the cloud provider while performing this action
     * @throws InternalException              an error occurred within the Dasein Cloud implementation while performing this action
     * @throws OperationNotSupportedException this load balancer is not data-center aware
     */
    public void addListeners( @Nonnull String toLoadBalancerId, @Nullable LbListener[] listeners ) throws CloudException, InternalException;

    /**
     * Removes one or more new listeners from load balancer.
     *
     * @param toLoadBalancerId the load balancer to which data centers are being added
     * @param listeners        the listeners to be deleted
     * @throws CloudException                 an error occurred with the cloud provider while performing this action
     * @throws InternalException              an error occurred within the Dasein Cloud implementation while performing this action
     * @throws OperationNotSupportedException this load balancer is not data-center aware
     */
    public void removeListeners( @Nonnull String toLoadBalancerId, @Nullable LbListener[] listeners ) throws CloudException, InternalException;

    /**
     * Adds one or more data centers to the list of data centers associated with the specified load balancer. This method
     * makes sense only if load balancers are not data center aware
     * @param toLoadBalancerId the load balancer to which data centers are being added
     * @param dataCenterIdsToAdd one or more data centers to add
     * @throws CloudException an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     * @throws OperationNotSupportedException this load balancer is not data-center aware
     */
    public void addDataCenters(@Nonnull String toLoadBalancerId, @Nonnull String ... dataCenterIdsToAdd) throws CloudException, InternalException;

    /**
     * Adds one or more IP address endpoints to the load balancer resource pool.
     * @param toLoadBalancerId the load balancer to which the endpoints are being added
     * @param ipAddresses the addresses to be added
     * @throws CloudException an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     * @throws OperationNotSupportedException this load balancer does not support IP endpoints, or does not support adding them post-create
     */
    public void addIPEndpoints(@Nonnull String toLoadBalancerId, @Nonnull String ... ipAddresses) throws CloudException, InternalException;

    /**
     * Adds one or more virtual machine endpoints to the load balancer resource pool.
     * @param toLoadBalancerId the load balancer to which the endpoints are being added
     * @param serverIdsToAdd the IDs of the virtual machines to be added
     * @throws CloudException an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     * @throws OperationNotSupportedException this load balancer does not support VM endpoints, or does not support adding them post-create
     */
    public void addServers(@Nonnull String toLoadBalancerId, @Nonnull String ... serverIdsToAdd) throws CloudException, InternalException;

    /**
     * Provisions a new cloud load balancer in the target region based on the specified creation options.
     * @param options the options for creating the new load balancer
     * @return the unique ID of the new load balancer
     * @throws CloudException an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     */
    public @Nonnull String createLoadBalancer(@Nonnull LoadBalancerCreateOptions options) throws CloudException, InternalException;

    /**
     * Provides access to meta-data about load balancer capabilities in the current region of this cloud.
     * @return a description of the features supported by this region of this cloud
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     */
    public @Nonnull LoadBalancerCapabilities getCapabilities() throws CloudException, InternalException;

    /**
     * Fetches the details for the load balancer associated with the specified load balancer ID from the cloud.
     * @param loadBalancerId the unique ID of the desired load balancer
     * @return the matching load balancer details if there is a matching load balancer in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     */
    public @Nullable LoadBalancer getLoadBalancer(@Nonnull String loadBalancerId) throws CloudException, InternalException;

    /**
     * Indicates whether the current account has access to load balancer services in the current region.
     * @return true if the current account has access to load balancer services in the current region
     * @throws CloudException an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     */
    public boolean isSubscribed() throws CloudException, InternalException;

    /**
     * Lists the endpoints associated with the specified load balancer.
     * @param forLoadBalancerId the load balancer for which you are listing the balanced endpoints
     * @return a list of balanced endpoints
     * @throws CloudException an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nonnull Iterable<LoadBalancerEndpoint> listEndpoints(@Nonnull String forLoadBalancerId) throws CloudException, InternalException;

    /**
     * Lists the endpoints associated with the specified load balancer that match the desired values.
     * @param forLoadBalancerId the load balancer for which you are listing the balanced endpoints
     * @param type the type of endpoint being sought
     * @param endpoints the VM ID or addresses of the endpoints being sought
     * @return a list of balanced endpoints matching the desired criteria
     * @throws CloudException an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nonnull Iterable<LoadBalancerEndpoint> listEndpoints(@Nonnull String forLoadBalancerId, @Nonnull LbEndpointType type, @Nonnull String ... endpoints) throws CloudException, InternalException;

    /**
     * Lists the load balancers in the current region.
     * @return a list of load balancers in the current region
     * @throws CloudException an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nonnull Iterable<LoadBalancer> listLoadBalancers() throws CloudException, InternalException;

    /**
     * Lists the current status of all load balancers associated with the account in the current region.
     * @return the status of all load balancers associated with the account in the current region
     * @throws CloudException an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nonnull Iterable<ResourceStatus> listLoadBalancerStatus() throws CloudException, InternalException;

    /**
     * Removes one or more data centers from the rotation behind this load balancer
     * @param fromLoadBalancerId the load balancer to remove data centers from
     * @param dataCenterIdsToRemove the data centers to be removed
     * @throws CloudException an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     * @throws OperationNotSupportedException this load balancer is not data-center aware
     */
    public void removeDataCenters(@Nonnull String fromLoadBalancerId, @Nonnull String ... dataCenterIdsToRemove) throws CloudException, InternalException;

    /**
     * Removes one or more IP endpoints from the load balancer resource pool.
     * @param fromLoadBalancerId the load balancer from which the endpoints are being removed
     * @param addresses the IP addresses to be removed
     * @throws CloudException an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     * @throws OperationNotSupportedException this load balancer does not support IP endpoints, or does not support removing them post-create
     */
    public void removeIPEndpoints(@Nonnull String fromLoadBalancerId, @Nonnull String ... addresses) throws CloudException, InternalException;

    /**
     * Removes the specified load balancer from the cloud.
     * @param loadBalancerId the load balancer to be removed
     * @throws CloudException an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     */
    public void removeLoadBalancer(@Nonnull String loadBalancerId) throws CloudException, InternalException;

    /**
     * Removes one or more virtual machine endpoints from the load balancer resource pool.
     * @param fromLoadBalancerId the load balancer from which the endpoints are being removed
     * @param serverIdsToRemove the IDs of the virtual machines to be removed
     * @throws CloudException an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     * @throws OperationNotSupportedException this load balancer does not support VM endpoints, or does not support removing them post-create
     */
    public void removeServers(@Nonnull String fromLoadBalancerId, @Nonnull String ... serverIdsToRemove) throws CloudException, InternalException;

    /**
     * Creates a standalone LoadBalancerHealthCheck that can be attached to a LoadBalancer either at a later time
     * or on creation of the LB.
     * @param name the name of the Health Check if required
     * @param description a friendly name for the Health Check
     * @param host an optional hostname that can be set as the target for the health check monitoring
     * @param protocol the protocol to be used for the health check monitoring
     * @param port the port to be used for the health check monitoring
     * @param path the path which is the target for the health check monitoring
     * @param interval how often to perform the health check, in seconds
     * @param timeout timeout after which the health check request is considered a failure, in seconds
     * @param healthyCount the number of consecutive successful requests before an unhealthy instance is marked as healthy
     * @param unhealthyCount the number of consecutive failed requests before a healthy instance is marked as unhealthy
     * @return the unique ID of the health check
     * @throws CloudException an error occurred in the cloud provider while processing the request
     * @throws InternalException an error occurred within Dasein Cloud while processing the request
     */
    public LoadBalancerHealthCheck createLoadBalancerHealthCheck(@Nullable String name, @Nullable String description, @Nullable String host, @Nullable LoadBalancerHealthCheck.HCProtocol protocol, int port, @Nullable String path, int interval, int timeout, int healthyCount, int unhealthyCount) throws CloudException, InternalException;

    /**
     * Creates a LoadBalancerHealthCheck object. For some clouds the Health Checks can exist as standalone objects but for others
     * (indicated by LoadBalancerCapabilities.healthCheckRequiresLoadBalancer()) they must only exist connected to a Load Balancer.
     * In those cases the HealthCheckOptions dialog must have a valid providerLoadBalancerId
     * @param options the options for creating the health check
     * @return the unique ID of the health check
     * @throws CloudException an error occurred in the cloud provider while processing the request
     * @throws InternalException an error occurred within Dasein Cloud while processing the request
     */
    public LoadBalancerHealthCheck createLoadBalancerHealthCheck(@Nonnull HealthCheckOptions options) throws CloudException, InternalException;

    /**
     * Gets the specified Health Check from the cloud
     * @param providerLBHealthCheckId the unique ID of the LB Health Check
     * @param providerLoadBalancerId optionally can provide the ID of a load balancer to with the Health Check is attached
     * @return the specified LoadBalancerHealthCheck
     * @throws CloudException an error occurred in the cloud provider while processing the request
     * @throws InternalException an error occurred within Dasein Cloud while processing the request
     */
    public LoadBalancerHealthCheck getLoadBalancerHealthCheck(@Nonnull String providerLBHealthCheckId, @Nullable String providerLoadBalancerId) throws CloudException, InternalException;

    /**
     * Lists all health checks matching the given HealthCheckFilterOptions belonging to the account owner currently in
     * the cloud. The filtering functionality is delegated to the cloud provider.
     * @param options the filter options
     * @return all health checks belonging to the account owner
     * @throws CloudException an error occurred in the cloud provider while processing the request
     * @throws InternalException an error occurred within Dasein Cloud while processing the request
     */
    public Iterable<LoadBalancerHealthCheck> listLBHealthChecks(@Nullable HealthCheckFilterOptions options) throws CloudException, InternalException;

    /**
     * Attaches an existing Health Check to an existing Load Balancer
     * @param providerLoadBalancerId the load balancer ID
     * @param providerLBHealthCheckId the health check ID
     * @throws CloudException an error occurred in the cloud provider while processing the request
     * @throws InternalException an error occurred within Dasein Cloud while processing the request
     */
    public void attachHealthCheckToLoadBalancer(@Nonnull String providerLoadBalancerId, @Nonnull String providerLBHealthCheckId)throws CloudException, InternalException;

    /**
     * Detach named healthCheck from named loadBalancer without deleting either.
     * @param providerLoadBalancerId the load balancer id
     * @param providerLBHeathCheckId the health check id
     * @throws CloudException an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     */
    public void detachHealthCheckFromLoadBalancer(@Nonnull String providerLoadBalancerId, @Nonnull String providerLBHeathCheckId) throws CloudException, InternalException;


    /**
     * Allows an existing LB Health Check to be modified
     * @param providerLBHealthCheckId the ID of the Health Check being adjusted
     * @param options the new options to which the Health Check will be modified to meet
     * @return the modified LoadBalancerHealthCheck object
     * @throws CloudException an error occurred in the cloud provider while processing the request
     * @throws InternalException an error occurred within Dasein Cloud while processing the request
     */
    public LoadBalancerHealthCheck modifyHealthCheck(@Nonnull String providerLBHealthCheckId, @Nonnull HealthCheckOptions options) throws InternalException, CloudException;

    /**
     * Removes a health check associated with a particular Load Balancer. Only certain clouds allow this operation
     * @param providerLoadBalancerId the ID of the Load Balancer that has the health check being removed
     * @throws CloudException an error occurred in the cloud provider while processing the request
     * @throws InternalException an error occurred within Dasein Cloud while processing the request
     */
    public void removeLoadBalancerHealthCheck(@Nonnull String providerLoadBalancerId) throws CloudException, InternalException;

    /**
     * Uploads a new server certificate associated with the account and current region.
     * @param options the details of the certificate to upload
     * @return details of created server certificate
     * @throws CloudException an error occurred with the cloud provider or request parameters were incorrect
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     */
    public SSLCertificate createSSLCertificate(@Nonnull SSLCertificateCreateOptions options) throws CloudException, InternalException;

    /**
     * Lists all available server certificates associated with the account in the current region.
     * @return all server certificates associated with the account in the current region. Certificates may not contain
     * all fields, e.g. a body. To get all information use {@link #getSSLCertificate(String)}.
     * @throws CloudException an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nonnull Iterable<SSLCertificate> listSSLCertificates() throws CloudException, InternalException;

    /**
     * Removes a given server certificate from the account in current region.
     * <strong>Note:</strong> be sure to first unset this certificate from any load balancer it is used by.
     * @param certificateName name of the certificate to remove
     * @throws CloudException an error occurred with the cloud provider, certificate does not exist by given name etc
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     */
    public void removeSSLCertificate(@Nonnull String certificateName) throws CloudException, InternalException;

    /**
     * Assigns an SSL certificate to specified port of a load balancer.
     * @param options request options: load balancer name, port number and certificate ID.
     * @throws CloudException thrown if load balancer or certificate do not exist or other error occurs in the cloud.
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     */
    public void setSSLCertificate(@Nonnull SetLoadBalancerSSLCertificateOptions options) throws CloudException, InternalException;

    /**
     * Fetched the details of an SSL certificate associated with the given name.
     * @param certificateName the certificate name to search for.
     * @return server certificate name or null if no certificate exists with the given name.
     * @throws CloudException an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nullable SSLCertificate getSSLCertificate(@Nonnull String certificateName) throws CloudException, InternalException;

    /**
     * Attaches an existing Load Balancer to an existing firewalls
     * @param providerLoadBalancerId the load balancer ID
     * @param firewallIds the firewalls
     * @throws CloudException an error occurred in the cloud provider while processing the request
     * @throws InternalException an error occurred within Dasein Cloud while processing the request
     */
    public void setFirewalls(@Nonnull String providerLoadBalancerId, @Nonnull String... firewallIds) throws CloudException, InternalException;

    /**
     * Adds subnets to the loadbalancer
     *
     * @param toLoadBalancerId the ID of the loadbalancer the subnets need to be attached
     * @param subnetIdsToAdd subnets IDs to be attached to the specified loadbalancer
     * @throws CloudException an error occurred in the cloud provider while processing the request
     * @throws InternalException an error occurred within Dasein Cloud while processing the request
     */
    public void attachLoadBalancerToSubnets(@Nonnull String toLoadBalancerId, @Nonnull String ... subnetIdsToAdd) throws CloudException, InternalException;

    /**
     * Removes subnet from the loadbalancer
     *
     * @param fromLoadBalancerId the ID of loadbalancer the subnets need to be detached
     * @param subnetIdsToDelete subnets IDs to be detached from the specified loadbalancer
     * @throws CloudException an error occurred in the cloud provider while processing the request
     * @throws InternalException an error occurred within Dasein Cloud while processing the request
     */
    public void detachLoadBalancerFromSubnets(@Nonnull String fromLoadBalancerId, @Nonnull String ... subnetIdsToDelete) throws CloudException, InternalException;

    /**
     * Modifies the attributes of a specified load balancer
     *
     * @param id      firewall id
     * @param options attributes options
     * @throws CloudException an error occurred in the cloud provider while processing the request
     * @throws InternalException an error occurred within Dasein Cloud while processing the request
     */
    public void modifyLoadBalancerAttributes( @Nonnull String id, @Nonnull LbAttributesOptions options ) throws CloudException, InternalException;

    /**
     * Get load balancer attributes
     *
     * @param id - load balancer id
     * @return load balancer attributes
     * @throws CloudException an error occurred in the cloud provider while processing the request
     * @throws InternalException an error occurred within Dasein Cloud while processing the request
     */
    public LbAttributesOptions getLoadBalancerAttributes( @Nonnull String id ) throws CloudException, InternalException;

    /**
     * Removes meta-data from a loadBalancer. If tag values are set, their removal is dependent on underlying cloud
     * provider behavior. They may be removed only if the tag value matches or they may be removed regardless of the
     * value.
     * @param loadBalancerId the loadBalancer to update
     * @param tags the meta-data tags to remove
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void removeTags(@Nonnull String loadBalancerId, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Removes meta-data from multiple loadBalancers. If tag values are set, their removal is dependent on underlying cloud
     * provider behavior. They may be removed only if the tag value matches or they may be removed regardless of the
     * value.
     * @param loadBalancerIds the loadBalancers to update
     * @param tags  the meta-data tags to remove
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void removeTags(@Nonnull String[] loadBalancerIds, @Nonnull Tag ... tags) throws CloudException, InternalException;
    
    /**
     * Updates meta-data for a loadBalancer with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     * @param loadBalancerId the loadBalancer to update
     * @param tags the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void updateTags(@Nonnull String loadBalancerId, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Updates meta-data for multiple loadBalancers with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     * @param loadBalancerIds the loadBalancers to update
     * @param tags  the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void updateTags(@Nonnull String[] loadBalancerIds, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Set meta-data for a loadBalancer. Remove any tags that were not provided by the incoming tags, and add or
     * overwrite any new or pre-existing tags.
     *
     * @param loadBalancerId the loadBalancer to set
     * @param tags     the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void setTags( @Nonnull String loadBalancerId, @Nonnull Tag... tags ) throws CloudException, InternalException;

    /**
     * Set meta-data for multiple loadBalancers. Remove any tags that were not provided by the incoming tags, and add or
     * overwrite any new or pre-existing tags.
     *
     * @param loadBalancerIds the loadBalancers to set
     * @param tags      the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void setTags( @Nonnull String[] loadBalancerIds, @Nonnull Tag... tags ) throws CloudException, InternalException;

}
