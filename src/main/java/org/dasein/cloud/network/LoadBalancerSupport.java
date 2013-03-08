/**
 * Copyright (C) 2009-2013 enstratius, Inc.
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

import java.util.Locale;

import org.dasein.cloud.AccessControlledService;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.Requirement;
import org.dasein.cloud.ResourceStatus;
import org.dasein.cloud.identity.ServiceAction;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Implements support for cloud load balancing services.
 * @author George Reese
 * @author Cameron Stokes
 * @version 2013.04 added Javadoc and did some refactoring
 * @version 2013.02 added support for health checks
 * @since unknown
 */
public interface LoadBalancerSupport extends AccessControlledService {
    static public final ServiceAction ANY                  = new ServiceAction("LB:ANY");

    static public final ServiceAction ADD_DATA_CENTERS     = new ServiceAction("LB:ADD_DC");
    static public final ServiceAction ADD_VMS              = new ServiceAction("LB:ADD_VM");
    static public final ServiceAction CREATE_LOAD_BALANCER = new ServiceAction("LB:CREATE_LOAD_BALANCER");
    static public final ServiceAction GET_LOAD_BALANCER    = new ServiceAction("LB:GET_LOAD_BALANCER");
    static public final ServiceAction LIST_LOAD_BALANCER   = new ServiceAction("LB:LIST_LOAD_BALANCER");
    static public final ServiceAction GET_LOAD_BALANCER_SERVER_HEALTH   = new ServiceAction("LB:GET_LOAD_BALANCER_SERVER_HEALTH");
    static public final ServiceAction REMOVE_DATA_CENTERS  = new ServiceAction("LB:REMOVE_DC");
    static public final ServiceAction REMOVE_VMS           = new ServiceAction("LB:REMOVE_VM");
    static public final ServiceAction REMOVE_LOAD_BALANCER = new ServiceAction("LB:REMOVE_LOAD_BALANCER");

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
     * Indicates the type of load balancer supported by this cloud.
     * @return the load balancer type
     * @throws CloudException an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     */
    public @Nonnull LoadBalancerAddressType getAddressType() throws CloudException, InternalException;


    /**
     * Fetches the details for the load balancer associated with the specified load balancer ID from the cloud.
     * @param loadBalancerId the unique ID of the desired load balancer
     * @return the matching load balancer details if there is a matching load balancer in the cloud
     * @throws CloudException an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     */
    public @Nullable LoadBalancer getLoadBalancer(@Nonnull String loadBalancerId) throws CloudException, InternalException;

    /**
     * Lists all servers along with their load balancer health status for the specified load balancer.
     * @param loadBalancerId the unique ID of the load balancer being checked
     * @return all servers with their current health status
     * @throws CloudException an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     * @deprecated Use {@link #listEndpoints(String)}
     */
    public @Nonnull Iterable<LoadBalancerServer> getLoadBalancerServerHealth(@Nonnull String loadBalancerId) throws CloudException, InternalException;

    /**
     * Lists the desired servers along with their load balancer health status for the specified load balancer.
     * @param loadBalancerId the unique ID of the load balancer being checked
     * @param serverIdsToCheck a list of server IDs for which the current health is being sought
     * @return all servers matching the specified server IDs with their current health status
     * @throws CloudException an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     * @deprecated Use {@link #listEndpoints(String, LbEndpointType, String...)}
     */
    public @Nonnull Iterable<LoadBalancerServer> getLoadBalancerServerHealth(@Nonnull String loadBalancerId, @Nonnull String ... serverIdsToCheck) throws CloudException, InternalException;

    /**
     * @return the maximum number of public ports on which the load balancer can listen
     * @throws CloudException an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nonnegative int getMaxPublicPorts() throws CloudException, InternalException;

    /**
     * Gives the cloud provider's term for a load balancer (for example, "ELB" in AWS).
     * @param locale the locale for which the term should be translated
     * @return the provider term for a load balancer
     */
    public @Nonnull String getProviderTermForLoadBalancer(@Nonnull Locale locale);

    /**
     * @return the degree to which endpoints should or must be part of the load balancer creation process
     * @throws CloudException an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nonnull Requirement identifyEndpointsOnCreateRequirement() throws CloudException, InternalException;

    /**
     * Indicates the degree to which listeners should or must be specified when creating a load balancer.
     * @return the degree to which listeners must be specified during load balancer creation
     * @throws CloudException an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nonnull Requirement identifyListenersOnCreateRequirement() throws CloudException, InternalException;

    /**
     * @return whether or not you are expected to provide an address as part of the create process or one gets assigned by the provider
     * @throws CloudException an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public boolean isAddressAssignedByProvider() throws CloudException, InternalException;

    /**
     * Indicates whether or not VM endpoints for this load balancer should be constrained to specific data centers in
     * its region. It should be false for load balancers handling non-VM endpoints or load balancers that are free
     * to balance across any data center. When a load balancer is data-center limited, the load balancer tries to balance
     * traffic equally across the data centers. It is therefore up to you to try to keep the data centers configured
     * with equal capacity.
     * @return whether or not VM endpoints are constrained to specific data centers associated with the load balancer
     * @throws CloudException an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     */
    public boolean isDataCenterLimited() throws CloudException, InternalException;

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
     * Lists the load balancing algorithms from which you can choose when setting up a load balancer listener.
     * @return a list of one or more supported load balancing algorithms
     * @throws CloudException an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nonnull Iterable<LbAlgorithm> listSupportedAlgorithms() throws CloudException, InternalException;

    /**
     * Describes what kind of endpoints may be added to a load balancer.
     * @return a list of one or more supported endpoint types
     * @throws CloudException an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nonnull Iterable<LbEndpointType> listSupportedEndpointTypes() throws CloudException, InternalException;

    /**
     * Lists all IP protocol versions supported for load balancers in this cloud.
     * @return a list of supported versions
     * @throws CloudException an error occurred checking support for IP versions with the cloud provider
     * @throws InternalException a local error occurred preparing the supported version
     */
    public @Nonnull Iterable<IPVersion> listSupportedIPVersions() throws CloudException, InternalException;

    /**
     * Lists the various options for session stickiness with load balancers in this cloud.
     * @return a list of one or more load balancer persistence options for session stickiness
     * @throws CloudException an error occurred checking support for IP versions with the cloud provider
     * @throws InternalException a local error occurred preparing the supported version
     */
    public @Nonnull Iterable<LbPersistence> listSupportedPersistenceOptions() throws CloudException, InternalException;

    /**
     * Lists the network protocols supported for load balancer listeners.
     * @return a list of one or more supported network protocols for load balancing
     * @throws CloudException an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nonnull Iterable<LbProtocol> listSupportedProtocols() throws CloudException, InternalException;

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
     * Indicates whether or not endpoints may be added to or removed from a load balancer once the load balancer has been created.
     * @return true if you can modify the endpoints post-create
     * @throws CloudException an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     */
    public boolean supportsAddingEndpoints() throws CloudException, InternalException;

    /**
     * Indicates whether or not the underlying cloud monitors the balanced endpoints and provides health status information.
     * @return true if monitoring is supported
     * @throws CloudException an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     */
    public boolean supportsMonitoring() throws CloudException, InternalException;

    /**
     * Indicates whether a single load balancer is limited to either IPv4 or IPv6 (false) or can support both IPv4 and
     * IPv6 traffic (true)
     * @return true if a load balancer can be configured to support simultaneous IPv4 and IPv6 traffic
     * @throws CloudException an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     */
    public boolean supportsMultipleTrafficTypes() throws CloudException, InternalException;

    /********************************** DEPRECATED METHODS *************************************/

    /**
     * Creates a new load balancer matching the specified characteristics in the cloud.
     * @param name the name of the new load balancer
     * @param description a description for the new load balancer
     * @param addressId the address ID of the IP address, if any, for the new load balancer
     * @param dataCenterIds a list of data center IDs across which the load balancer will manage traffic
     * @param listeners the listeners to be established on create
     * @param serverIds a list of servers to be attached to the load balancer on create
     * @return the unique ID for the new load balancer
     * @throws CloudException an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     * @deprecated Use {@link #createLoadBalancer(LoadBalancerCreateOptions)}
     */
    public @Nonnull String create(@Nonnull String name, @Nonnull String description, @Nullable String addressId, @Nullable String[] dataCenterIds, @Nullable LbListener[] listeners, @Nullable String[] serverIds) throws CloudException, InternalException;

    /**
     * Removes a load balancer.
     * @param loadBalancerId  the load balancer to be removed
     * @throws CloudException an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     * @deprecated Use {@link #removeLoadBalancer(String)}
     */
    public void remove(@Nonnull String loadBalancerId) throws CloudException, InternalException;

    /**
     * @return <code>true</code> if you must provide at least one listener when creating a load balancer
     * @throws CloudException an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     * @deprecated Use {@link #identifyListenersOnCreateRequirement()}
     */
    public boolean requiresListenerOnCreate() throws CloudException, InternalException;

    /**
     * @return <code>true</code> if at least one endpoint must be part of the load balancer creation process
     * @throws CloudException an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     * @deprecated Use {@link #identifyEndpointsOnCreateRequirement()}
     */
    public boolean requiresServerOnCreate() throws CloudException, InternalException;
}
