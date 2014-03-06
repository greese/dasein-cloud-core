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


package org.dasein.cloud.compute;

import org.dasein.cloud.Capabilities;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.Requirement;
import org.dasein.util.uom.storage.Gigabyte;
import org.dasein.util.uom.storage.Storage;

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
public interface VolumeCapabilities extends Capabilities{
    /**
     * Indicates whether the Volume can be attached to a hypothetical virtual machine in the given state
     * @param vmState the state of the hypothetical virtual machine
     * @return true if the volume can be attached in the given state, otherwise false
     * @throws InternalException
     * @throws CloudException
     */
    public abstract boolean canAttach(VmState vmState)throws InternalException, CloudException;

    /**
     * Indicates whether the Volume can be detached from a hypothetical virtual machine in the given state
     * @param vmState the state of the hypothetical virtual machine
     * @return true if the volume can be detached in the given state, otherwise false
     * @throws InternalException
     * @throws CloudException
     */
    public abstract boolean canDetach(VmState vmState)throws InternalException, CloudException;

    /**
     * Indicates the maximum number of volumes that may be provisioned in this account.
     * @return the maximum number of volumes that may be provisioned, -1 for unlimited, or -2 for unknown
     * @throws org.dasein.cloud.InternalException an error occurred within the Dasein Cloud implementation determining the limit
     * @throws org.dasein.cloud.CloudException an error occurred retrieving the limit from the cloud
     */
    public int getMaximumVolumeCount() throws InternalException, CloudException;

    /**
     * Indicates the largest provisionable volume.
     * @return the largest provisionable volume or null if a limit is not known
     * @throws InternalException an error occurred within the Dasein Cloud implementation determining the limit
     * @throws CloudException an error occurred retrieving the limit from the cloud
     */
    public @Nullable Storage<Gigabyte> getMaximumVolumeSize() throws InternalException, CloudException;

    /**
     * Indicates the smallest provisionable volume.
     * @return the size of the smallest provisionable volume
     * @throws InternalException an error occurred within the Dasein Cloud implementation determining the limit
     * @throws CloudException an error occurred retrieving the limit from the cloud
     */
    public @Nonnull Storage<Gigabyte> getMinimumVolumeSize() throws InternalException, CloudException;

    /**
     * Specifies the provider term for a volume.
     * @param locale the locale into which the term should be translated
     * @return a localized term for a volume using the cloud provider's terminology
     */
    public @Nonnull String getProviderTermForVolume(@Nonnull Locale locale);

    /**
     * Identifies to what degree volume products are supported/required in this cloud. If the support
     * level is {@link org.dasein.cloud.Requirement#NONE}, then {@link VolumeSupport#listVolumeProducts()} should return an empty list.
     * @return whether or not specifying a volume product is required to create a volume
     * @throws InternalException an error occurred in the Dasein Cloud implementation determining the support level
     * @throws CloudException an error occurred with the cloud provider determining the support level
     */
    public @Nonnull Requirement getVolumeProductRequirement() throws InternalException, CloudException;

    /**
     * Indicates that a volume size is not necessary (and ultimately ignored) during the volume creation process
     * because the volume size is determined by the selected volume product.
     * @return true if the volume size is determined by the product choice
     * @throws InternalException an error occurred within Dasein Cloud determining this feature
     * @throws CloudException an error occurred identifying this requirement from the cloud provider
     */
    public boolean isVolumeSizeDeterminedByProduct() throws InternalException, CloudException;

    /**
     * Lists the possible device IDs supported in this cloud for block devices for different guest operating systems.
     * @param platform the guest operating system
     * @return a list of device IDs that may be used in attaching the volume to a virtual machine
     * @throws InternalException an error occurred in the Dasein Cloud implementation while assembling the list
     * @throws CloudException an error occurred fetching a list from the cloud provider
     */
    public @Nonnull Iterable<String> listPossibleDeviceIds(@Nonnull Platform platform) throws InternalException, CloudException;

    /**
     * Describes the formats supported in this cloud.
     * @return a list of supported formats
     * @throws InternalException an error occurred in the Dasein Cloud implementation while assembling the list
     * @throws CloudException an error occurred fetching a list from the cloud provider
     */
    public @Nonnull Iterable<VolumeFormat> listSupportedFormats() throws InternalException, CloudException;

    /**
     * Indicates whether the volume is required to be created attached to a virtual machine or it it can be created
     * independantly.
     * @return requirement level for a VM on create
     * @throws InternalException an error occurred in the Dasein Cloud implementation while assembling the list
     * @throws CloudException an error occurred fetching a list from the cloud provider
     */
    public @Nonnull Requirement requiresVMOnCreate() throws InternalException, CloudException;
}
