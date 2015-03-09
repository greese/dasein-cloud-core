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

package org.dasein.cloud.ci;

import org.dasein.cloud.AccessControlledService;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.ResourceStatus;
import org.dasein.cloud.Tag;
import org.dasein.cloud.compute.VirtualMachineSupport;
import org.dasein.cloud.network.VLANSupport;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Provides access to functionality around complex topologies represented by the Dasein Cloud
 * {@link Topology} concept and the resulting {@link ConvergedInfrastructure} infrastructures.
 * <p>Created by George Reese: 5/30/13 11:37 AM</p>
 * @author George Reese
 * @version 2013.07 initial version
 * @since 2013.07
 */
public interface ConvergedInfrastructureSupport extends AccessControlledService {
    /**
     * Provides the specified converged infrastructure if it exists.
     * @param ciId the unique ID for the desired converged infrastructure
     * @return the specified converged infrastructure if it exists, otherwise <code>null</code>
     * @throws CloudException an error occurred in the cloud provider while processing the request
     * @throws InternalException an error occurred within Dasein Cloud while processing the request
     */
    public @Nullable ConvergedInfrastructure getConvergedInfrastructure(@Nonnull String ciId) throws CloudException, InternalException;

    /**
     * Verifies that the current account context is subscribed for access to topology support in this cloud and region.
     * @return true if the account is subscribed in the current region for topology support
     * @throws CloudException an error occurred with the cloud provider while checking the subscription
     * @throws InternalException an error occurred within Dasein Cloud while processing the request
     */
    public boolean isSubscribed() throws CloudException, InternalException;

    /**
     * Lists all provisioned converged infrastructures matching the optional filtering options. If no options are provided,
     * all provisioned converged infrastructures are returned.
     * @param options the options for filtering the list of converged infrastructures
     * @return a list of matching converged infrastructures
     * @throws CloudException an error occurred in the cloud provider while processing the request
     * @throws InternalException an error occurred within Dasein Cloud while processing the request
     */
    public @Nonnull Iterable<ConvergedInfrastructure> listConvergedInfrastructures(@Nullable CIFilterOptions options) throws CloudException, InternalException;

    /**
     * Lists the status for all converged infrastructures in the current region.
     * @return the status for all converged infrastructures in the current region
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     * @throws CloudException an error occurred with the cloud provider
     */
    public @Nonnull Iterable<ResourceStatus> listConvergedInfrastructureStatus() throws CloudException, InternalException;

    /**
     * Lists all virtual machines currently part of the specified converged infrastructure. The result is a list of
     * IDs that may be queried using {@link VirtualMachineSupport#getVirtualMachine(String)}.
     * @param inCIId the unique ID of the {@link ConvergedInfrastructure} for which VMs are being looked up
     * @return the list of virtual machine IDs supporting the specified converged infrastructure
     * @throws InternalException an error occurred within Dasein Cloud while processing the request
     * @throws CloudException an error occurred in the cloud provider while processing the request
     */
    public @Nonnull Iterable<String> listVirtualMachines(@Nonnull String inCIId) throws InternalException, CloudException;

    /**
     * Lists all VLANs currently part of the specified converged infrastructure. The result is a list of IDs
     * that may be queried using {@link VLANSupport#getVlan(String)}.
     * @param inCIId the unique ID of the {@link ConvergedInfrastructure} for which VLANs are being queried
     * @return a list of VLAN IDs supporting the specified converged infrastructure
     * @throws CloudException an error occurred in the cloud provider while processing the request
     * @throws InternalException an error occurred within Dasein Cloud while processing the request
     */
    public @Nonnull Iterable<String> listVLANs(@Nonnull String inCIId) throws CloudException, InternalException;

    /**
     * Provisions a cloud infrastructure based on the specified topology provision options.
     * @param options the options for provisioning a topology-based infrastructure
     * @return the converged infrastructure that results from this provisioning operation
     * @throws CloudException an error occurred in the cloud provider while processing the request
     * @throws InternalException an error occurred within Dasein Cloud while processing the request
     */
    public @Nonnull ConvergedInfrastructure provision(@Nonnull CIProvisionOptions options) throws CloudException, InternalException;

    /**
     * Terminates the specified converged infrastructure, terminating or deleting all resources provisioned during
     * the provisioning process for the converged infrastructure. The operation is destructive and not recoverable.
     * @param ciId the unique ID of the converged infrastructure to terminate
     * @param explanation an optional explanation to submit for audit purposes to the cloud provider
     * @throws CloudException an error occurred in the cloud provider while processing the request
     * @throws InternalException an error occurred within Dasein Cloud while processing the request
     */
    public void terminate(@Nonnull String ciId, @Nullable String explanation) throws CloudException, InternalException;

    /**
     * Updates meta-data for a converged infrastructure with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     * @param ciId the converged infrastructure to update
     * @param tags the meta-data tags to set
     * @throws CloudException an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void updateTags(@Nonnull String ciId, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Updates meta-data for multiple converged infrastructures with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     * @param ciIds the converged infrastructures to update
     * @param tags the meta-data tags to set
     * @throws CloudException an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void updateTags(@Nonnull String[] ciIds, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Removes meta-data from a converged infrastructure. If tag values are set, their removal is dependent on underlying cloud
     * provider behavior. They may be removed only if the tag value matches or they may be removed regardless of the
     * value.
     * @param ciId the converged infrastructure to update
     * @param tags the meta-data tags to remove
     * @throws CloudException an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void removeTags(@Nonnull String ciId, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Removes meta-data from multiple converged infrastructures. If tag values are set, their removal is dependent on underlying cloud
     * provider behavior. They may be removed only if the tag value matches or they may be removed regardless of the
     * value.
     * @param ciIds the converged infrastructures to update
     * @param tags the meta-data tags to remove
     * @throws CloudException an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void removeTags(@Nonnull String[] ciIds, @Nonnull Tag... tags) throws CloudException, InternalException;
}
