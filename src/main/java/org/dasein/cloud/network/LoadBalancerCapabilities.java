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
import org.dasein.cloud.util.NamingConstraints;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;

/**
 * Describes the capabilities of a region within a cloud for a specific account.
 * <p>Created by George Reese: 2/27/14 3:01 PM</p>
 * @author George Reese
 * @version 2014.03 initial version
 * @since 2014.03
 */
public interface LoadBalancerCapabilities extends Capabilities{
    /**
     * Indicates the type of load balancer supported by this cloud.
     * @return the load balancer type
     * @throws CloudException
     *          an error occurred with the cloud provider while performing this action
     * @throws InternalException
     *          an error occurred within the Dasein Cloud implementation while performing this action
     */
    @Nonnull LoadBalancerAddressType getAddressType() throws CloudException, InternalException;

    /**
     * @return the maximum number of public ports on which the load balancer can listen
     * @throws CloudException    an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    @Nonnegative int getMaxPublicPorts() throws CloudException, InternalException;

    /**
     * Gives the cloud provider's term for a load balancer (for example, "ELB" in AWS).
     * @param locale the locale for which the term should be translated
     * @return the provider term for a load balancer
     */
    @Nonnull String getProviderTermForLoadBalancer(@Nonnull Locale locale);

    /**
     * Returns the visible scope of the load balancer or null if not applicable for the specific cloud
     * @return The Visible Scope for the load balancer
     */
    @Nullable VisibleScope getLoadBalancerVisibleScope();

    /**
     * Indicates whether a health check can be created independently of a load balancer
     * @return false if a health check can exist without having been assigned to a load balancer
     * @throws InternalException
     *             an error occurred within the Dasein Cloud API implementation
     * @throws CloudException
     *             an error occurred within the cloud provider
     */
    boolean healthCheckRequiresLoadBalancer() throws CloudException, InternalException;

    /**
     * Indicates whether a name is required when creating a health check
     * @return Requirement for health check name
     * @throws InternalException
     *             an error occurred within the Dasein Cloud API implementation
     * @throws CloudException
     *             an error occurred within the cloud provider
     */
    Requirement healthCheckRequiresName() throws CloudException, InternalException;

    /**
     * @return the degree to which endpoints should or must be part of the load balancer creation process
     * @throws CloudException    an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    @Nonnull Requirement identifyEndpointsOnCreateRequirement() throws CloudException, InternalException;

    /**
     * Indicates the degree to which listeners should or must be specified when creating a load balancer.
     * @return the degree to which listeners must be specified during load balancer creation
     * @throws CloudException    an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    @Nonnull Requirement identifyListenersOnCreateRequirement() throws CloudException, InternalException;

    /**
     * Indicates whether a load balancer requires a vlan to be specified when it is created.
     * @return the degree to which a vlan should or must be part of the creation process
     * @throws CloudException an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    @Nonnull Requirement identifyVlanOnCreateRequirement() throws CloudException, InternalException;

    /**
     * Indicates whether a load balancer requires a health check to be specified when it is created.
     * @return the degree to which a health check should or must be part of the creation process
     * @throws CloudException an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    @Nonnull Requirement identifyHealthCheckOnCreateRequirement() throws CloudException, InternalException;

    /**
     * @return whether or not you are expected to provide an address as part of the create process or one gets assigned
     *         by the provider
     * @throws CloudException    an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    boolean isAddressAssignedByProvider() throws CloudException, InternalException;

    /**
     * Indicates whether or not VM endpoints for this load balancer should be constrained to specific data centers in
     * its region. It should be false for load balancers handling non-VM endpoints or load balancers that are free
     * to balance across any data center. When a load balancer is data-center limited, the load balancer tries to
     * balance
     * traffic equally across the data centers. It is therefore up to you to try to keep the data centers configured
     * with equal capacity.
     * @return whether or not VM endpoints are constrained to specific data centers associated with the load balancer
     * @throws CloudException    an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     */
    boolean isDataCenterLimited() throws CloudException, InternalException;

    /**
     * Lists the load balancing algorithms from which you can choose when setting up a load balancer listener.
     * @return a list of one or more supported load balancing algorithms
     * @throws CloudException    an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    @Nonnull Iterable<LbAlgorithm> listSupportedAlgorithms() throws CloudException, InternalException;

    /**
     * Describes what kind of endpoints may be added to a load balancer.
     * @return a list of one or more supported endpoint types
     * @throws CloudException    an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    @Nonnull Iterable<LbEndpointType> listSupportedEndpointTypes() throws CloudException, InternalException;

    /**
     * Lists all IP protocol versions supported for load balancers in this cloud.
     * @return a list of supported versions
     * @throws CloudException    an error occurred checking support for IP versions with the cloud provider
     * @throws InternalException a local error occurred preparing the supported version
     */
    @Nonnull Iterable<IPVersion> listSupportedIPVersions() throws CloudException, InternalException;

    /**
     * Lists the various options for session stickiness with load balancers in this cloud.
     * @return a list of one or more load balancer persistence options for session stickiness
     * @throws CloudException    an error occurred checking support for IP versions with the cloud provider
     * @throws InternalException a local error occurred preparing the supported version
     */
    @Nonnull Iterable<LbPersistence> listSupportedPersistenceOptions() throws CloudException, InternalException;

    /**
     * Lists the network protocols supported for load balancer listeners.
     * @return a list of one or more supported network protocols for load balancing
     * @throws CloudException    an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    @Nonnull Iterable<LbProtocol> listSupportedProtocols() throws CloudException, InternalException;

    /**
     * Indicates whether or not endpoints may be added to or removed from a load balancer once the load balancer has
     * been created.
     * @return true if you can modify the endpoints post-create
     * @throws CloudException    an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     */
    boolean supportsAddingEndpoints() throws CloudException, InternalException;

    /**
     * Indicates whether or not the underlying cloud monitors the balanced endpoints and provides health status
     * information.
     * @return true if monitoring is supported
     * @throws CloudException    an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     */
    boolean supportsMonitoring() throws CloudException, InternalException;

    /**
     * Indicates whether a single load balancer is limited to either IPv4 or IPv6 (false) or can support both IPv4 and
     * IPv6 traffic (true)
     * @return true if a load balancer can be configured to support simultaneous IPv4 and IPv6 traffic
     * @throws CloudException    an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     */
    boolean supportsMultipleTrafficTypes() throws CloudException, InternalException;

    /**
     * Indicates whether certificates may be uploaded to and managed by this cloud. Consulting this method will only
     * make sense if {@link #listSupportedProtocols()} returns {@link LbProtocol#HTTPS} as part of the response.
     * @return <code>true</code> if cloud will manage the certificates;
     * <code>false</code> if certificate needs to be uploaded upon load balancer creation, SSL management API will
     * likely throw exceptions in this case (e.g. {@link LoadBalancerSupport#createSSLCertificate(org.dasein.cloud.network.SSLCertificateCreateOptions)}).
     * @throws InternalException
     *             an error occurred within the Dasein Cloud API implementation
     * @throws CloudException
     *             an error occurred within the cloud provider
     */
    boolean supportsSslCertificateStore() throws CloudException, InternalException;

    /**
     * Identifies the naming conventions that constrain how load balancers may be named (friendly name) in this cloud.
     * @return naming conventions that constrain load balancer naming
     * @throws CloudException an error occurred querying the cloud for naming constraints
     * @throws InternalException an error occurred assembling the naming constraints object
     */
    @Nonnull NamingConstraints getLoadBalancerNamingConstraints() throws CloudException, InternalException;
}
