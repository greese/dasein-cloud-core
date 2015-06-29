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

package org.dasein.cloud.compute;

import java.util.Collection;
import java.util.Locale;

import org.dasein.cloud.AccessControlledService;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.Requirement;
import org.dasein.cloud.ResourceStatus;
import org.dasein.cloud.Tag;
import org.dasein.cloud.identity.ServiceAction;
import org.dasein.util.uom.storage.Gigabyte;
import org.dasein.util.uom.storage.Storage;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the contract for managing block storage volumes within a cloud provider that supports
 * block storage volumes.
 * @author George Reese (george.reese@imaginary.com
 * @since unknown
 * @version 2012-07 Added extended meta-data, included support for new {@link VolumeCreateOptions}
 * @version 2013.01 Added status listing (Issue #4)
 * @version 2013.04 Added support for volume filtering (Issue greese/dasein-cloud/#6)
 */
@SuppressWarnings("UnusedDeclaration")
public interface VolumeSupport extends AccessControlledService {
    static public final ServiceAction ANY               = new ServiceAction("VOLUME:ANY");

    static public final ServiceAction ATTACH            = new ServiceAction("VOLUME:ATTACH");
    static public final ServiceAction CREATE_VOLUME     = new ServiceAction("VOLUME:CREATE_VOLUME");
    static public final ServiceAction DETACH            = new ServiceAction("VOLUME:DETACH");
    static public final ServiceAction GET_VOLUME        = new ServiceAction("VOLUME:GET_VOLUME");
    static public final ServiceAction LIST_VOLUME       = new ServiceAction("VOLUME:LIST_VOLUME");
    static public final ServiceAction REMOVE_VOLUME     = new ServiceAction("VOLUME:REMOVE_VOLUME");

    /**
     * Attaches the specified volume to the target server using the specified device ID.
     * @param volumeId the unique ID of the volume to attach
     * @param toServer the unique ID of the virtual machine to which the volume should be attached
     * @param deviceId the operating system device ID that will identify the volume to the guest operating system
     * @throws InternalException an error occurred in the Dasein Cloud implementation while performing the attachment
     * @throws CloudException the attachment failed with the cloud provider
     * @see VolumeCapabilities#getDeviceIdOnAttachRequirement() 
     */
    public void attach(@Nonnull String volumeId, @Nonnull String toServer, @Nullable String deviceId) throws InternalException,
            CloudException;

    /**
     * Creates a snapshot with the specified bare-bones options.
     * @param fromSnapshot the snapshot (if any) from which the volume should be created
     * @param sizeInGb the size of the volume to be created
     * @param inZone the data center into which the volume should be provisioned
     * @return the provider volume ID for the newly created volume
     * @throws InternalException an error occurred within the Dasein Cloud implementation creating the volume
     * @throws CloudException an error occurred within the service provider creating the volume
     * @deprecated Use {@link #createVolume(VolumeCreateOptions)}
     */
    public @Nonnull String create(@Nullable String fromSnapshot, @Nonnegative int sizeInGb, @Nonnull String inZone) throws InternalException, CloudException;

    /**
     * Creates a new volume based on the specified options for creating a disk/block storage device.
     * @param options the options to be used in creating the volume
     * @return the provider volume ID that will identify the newly created volume
     * @throws InternalException an error occurred within the Dasein Cloud implementation creating the volume
     * @throws CloudException an error occurred within the service provider creating the volume
     */
    public @Nonnull String createVolume(@Nonnull VolumeCreateOptions options) throws InternalException, CloudException;

    /**
     * Detaches the specified volume from any virtual machines to which it might be attached.
     * @param volumeId the unique ID of the volume to be detached
     * @throws InternalException an error occurred in the Dasein Cloud implementation while performing the detachment
     * @throws CloudException the detachment failed with the cloud provider
     */
    public void detach(@Nonnull String volumeId) throws InternalException, CloudException;

    /**
     * Detaches the specified volume from any virtual machines to which it might be attached with the option to
     * force the detachment when some cloud state is preventing it.
     * @param volumeId the unique ID of the volume to be detached
     * @param force indicate whether or not the detach should be forced even if the VM is not releasing it
     * @throws InternalException an error occurred in the Dasein Cloud implementation while performing the detachment
     * @throws CloudException the detachment failed with the cloud provider
     */
    public void detach(@Nonnull String volumeId, boolean force) throws InternalException, CloudException;

    /**
     * Provides access to meta-data about volume capabilities in the current region of this cloud.
     * @return a description of the features supported by this region of this cloud
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     */
    public abstract VolumeCapabilities getCapabilities() throws CloudException, InternalException;

    /**
     * Indicates the maximum number of volumes that may be provisioned in this account.
     * @return the maximum number of volumes that may be provisioned, -1 for unlimited, or -2 for unknown
     * @throws InternalException an error occurred within the Dasein Cloud implementation determining the limit
     * @throws CloudException an error occurred retrieving the limit from the cloud
     * @deprecated use {@link VolumeCapabilities#getMaximumVolumeCount()}
     */
    @Deprecated
    public int getMaximumVolumeCount() throws InternalException, CloudException;

    /**
     * Indicates the largest provisionable volume.
     * @return the largest provisionable volume or null if a limit is not known
     * @throws InternalException an error occurred within the Dasein Cloud implementation determining the limit
     * @throws CloudException an error occurred retrieving the limit from the cloud
     * @deprecated use {@link VolumeCapabilities#getMaximumVolumeSize()}
     */
    @Deprecated
    public @Nullable Storage<Gigabyte> getMaximumVolumeSize() throws InternalException, CloudException;

    /**
     * Indicates the smallest provisionable volume.
     * @return the size of the smallest provisionable volume
     * @throws InternalException an error occurred within the Dasein Cloud implementation determining the limit
     * @throws CloudException an error occurred retrieving the limit from the cloud
     * @deprecated use {@link VolumeCapabilities#getMinimumVolumeSize()}
     */
    @Deprecated
    public @Nonnull Storage<Gigabyte> getMinimumVolumeSize() throws InternalException, CloudException;

    /**
     * Specifies the provider term for a volume.
     * @param locale the locale into which the term should be translated
     * @return a localized term for a volume using the cloud provider's terminology
     * @deprecated use {@link VolumeCapabilities#getProviderTermForVolume(java.util.Locale)}
     */
    @Deprecated
    public @Nonnull String getProviderTermForVolume(@Nonnull Locale locale);

    /**
     * Fetches the volume with the specified volume ID.
     * @param volumeId the ID of the desired volume
     * @return the matching volume or <code>null</code> if no such volume exists
     * @throws InternalException an error in the Dasein Cloud implementation while fetching the volume
     * @throws CloudException an error occurred with the cloud provider while fetching the volume
     */
    public @Nullable Volume getVolume(@Nonnull String volumeId) throws InternalException, CloudException;

    /**
     * Identifies to what degree volume products are supported/required in this cloud. If the support
     * level is {@link Requirement#NONE}, then {@link #listVolumeProducts()} should return an empty list.
     * @return whether or not specifying a volume product is required to create a volume
     * @throws InternalException an error occurred in the Dasein Cloud implementation determining the support level
     * @throws CloudException an error occurred with the cloud provider determining the support level
     * @deprecated use {@link VolumeCapabilities#getVolumeProductRequirement()}
     */
    @Deprecated
    public @Nonnull Requirement getVolumeProductRequirement() throws InternalException, CloudException;

    /**
     * Indicates that a volume size is not necessary (and ultimately ignored) during the volume creation process
     * because the volume size is determined by the selected volume product.
     * @return true if the volume size is determined by the product choice
     * @throws InternalException an error occurred within Dasein Cloud determining this feature
     * @throws CloudException an error occurred identifying this requirement from the cloud provider
     * @deprecated use {@link VolumeCapabilities#isVolumeSizeDeterminedByProduct()}
     */
    @Deprecated
    public boolean isVolumeSizeDeterminedByProduct() throws InternalException, CloudException;

    /**
     * Lists the possible device IDs supported in this cloud for block devices for different guest operating systems.
     * @param platform the guest operating system
     * @return a list of device IDs that may be used in attaching the volume to a virtual machine
     * @throws InternalException an error occurred in the Dasein Cloud implementation while assembling the list
     * @throws CloudException an error occurred fetching a list from the cloud provider
     * @deprecated use {@link VolumeCapabilities#listPossibleDeviceIds(Platform)}
     */
    @Deprecated
    public @Nonnull Iterable<String> listPossibleDeviceIds(@Nonnull Platform platform) throws InternalException, CloudException;

    /**
     * Describes the formats supported in this cloud.
     * @return a list of supported formats
     * @throws InternalException an error occurred in the Dasein Cloud implementation while assembling the list
     * @throws CloudException an error occurred fetching a list from the cloud provider
     * @deprecated use {@link VolumeCapabilities#listSupportedFormats()}
     */
    @Deprecated
    public @Nonnull Iterable<VolumeFormat> listSupportedFormats() throws InternalException, CloudException;

    /**
     * Lists the set of volume products that may be used in provisioning a block storage volume. Because not all clouds
     * support the concept of volume products (as indicated by {@link #getVolumeProductRequirement()}, this method should
     * return an empty list in such clouds.
     * @return the list of products that may be used to provision volumes
     * @throws InternalException an error occurred within the Dasein Cloud implementation assembling the list
     * @throws CloudException an error occurred fetching the product list from the cloud provider
     */
    public @Nonnull Iterable<VolumeProduct> listVolumeProducts() throws InternalException, CloudException;

    /**
     * Lists the status for all volumes in the current region.
     * @return the status for all volumes in the current region
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     * @throws CloudException an error occurred with the cloud provider
     */
    public @Nonnull Iterable<ResourceStatus> listVolumeStatus() throws InternalException, CloudException;

    /**
     * Lists all volumes in the current region with the cloud provider.
     * @return the volumes in the current region for this cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     * @throws CloudException an error occurred with the cloud provider
     */
    public @Nonnull Iterable<Volume> listVolumes() throws InternalException, CloudException;

    /**
     * Lists all volumes in the current region with the cloud provider matching the given
     * VolumeFilterOptions belonging to the account owner currently in the cloud. The filtering
     * functionality is delegated to the cloud provider.
     * @param options filter options
     * @return the volumes in the current region for this cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     * @throws CloudException an error occurred with the cloud provider
     */
    public @Nonnull Iterable<Volume> listVolumes(@Nullable VolumeFilterOptions options) throws InternalException, CloudException;

    /**
     * Identifies whether or not the current account has access to volumes in the current region.
     * @return true if the current account has access to volumes in the current region
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public boolean isSubscribed() throws CloudException, InternalException;

    /**
     * De-provisions the specified volume. This method should block until the volume is in a {@link VolumeState#DELETED} state.
     * @param volumeId the unique ID of the volume to be de-provisioned
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     * @throws CloudException an error occurred with the cloud provider
     */
    public void remove(@Nonnull String volumeId) throws InternalException, CloudException;

    /**
     * Removes meta-data from a volume. If tag values are set, their removal is dependent on underlying cloud
     * provider behavior. They may be removed only if the tag value matches or they may be removed regardless of the
     * value.
     * @param volumeId the volume to update
     * @param tags the meta-data tags to remove
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void removeTags(@Nonnull String volumeId, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Removes meta-data from multiple volumes. If tag values are set, their removal is dependent on underlying cloud
     * provider behavior. They may be removed only if the tag value matches or they may be removed regardless of the
     * value.
     * @param volumeIds the volume to update
     * @param tags  the meta-data tags to remove
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void removeTags(@Nonnull String[] volumeIds, @Nonnull Tag ... tags) throws CloudException, InternalException;

    /**
     * Updates meta-data for a volume with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     * @param volumeId the volume to update
     * @param tags the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void updateTags(@Nonnull String volumeId, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Updates meta-data for multiple volumes with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     * @param volumeIds the volumes to update
     * @param tags  the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void updateTags(@Nonnull String[] volumeIds, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Set meta-data for a volume. Remove any tags that were not provided by the incoming tags, and add or
     * overwrite any new or pre-existing tags.
     *
     * @param volumeId the volume to set
     * @param tags     the meta-data tags to set
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     */
    public void setTags( @Nonnull String volumeId, @Nonnull Tag... tags ) throws CloudException, InternalException;

    /**
     * Set meta-data for multiple volumes. Remove any tags that were not provided by the incoming tags, and add or
     * overwrite any new or pre-existing tags.
     *
     * @param volumeIds the volumes to set
     * @param tags      the meta-data tags to set
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     */
    public void setTags( @Nonnull String[] volumeIds, @Nonnull Tag... tags ) throws CloudException, InternalException;

}
