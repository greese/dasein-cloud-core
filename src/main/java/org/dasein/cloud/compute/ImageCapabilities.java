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

import javax.annotation.Nonnull;
import java.util.Locale;

/**
 * Describes the capabilities of a region within a cloud for a specific account.
 * <p>Created by George Reese: 2/27/14 3:01 PM</p>
 * @author George Reese
 * @version 2014.03 initial version
 * @since 2014.03
 */
public interface ImageCapabilities extends Capabilities{
    /**
     * Indicates whether or not a virtual machine in the specified state can be the target of
     * an {@link MachineImageSupport#bundleVirtualMachine(String, MachineImageFormat, String, String)} call.
     * @param fromState the state in which the theoretical virtual machine exists
     * @return true if such a virtual machine may be bundled, false otherwise
     * @throws CloudException the cloud provider encountered an error while processing the request
     * @throws InternalException a Dasein Cloud error occurred while processing the request
     */
    public boolean canBundle(@Nonnull VmState fromState) throws CloudException, InternalException;

    /**
     * Indicates whether or not a virtual machine in the specified state can be the target of
     * an {@link MachineImageSupport#captureImage(ImageCreateOptions)} call.
     * @param fromState the state in which the theoretical virtual machine exists
     * @return true if such a virtual machine may be imaged, false otherwise
     * @throws CloudException the cloud provider encountered an error while processing the request
     * @throws InternalException a Dasein Cloud error occurred while processing the request
     */
    public boolean canImage(@Nonnull VmState fromState) throws CloudException, InternalException;

    /**
     * Provides the cloud provider specific term for a public image of the specified image class.
     * @param cls the image class for the desired type
     * @return the term used by the provider to describe a public image
     */
    public abstract @Nonnull String getProviderTermForImage(@Nonnull Locale locale, @Nonnull ImageClass cls);

    /**
     * Provides the cloud provider specific term for a custom image of the specified image class.
     * @param locale the locale for which the term should be translated
     * @param cls the image class for the desired type
     * @return the term used by the provider to describe a custom image
     */
    public abstract @Nonnull String getProviderTermForCustomImage(@Nonnull Locale locale, @Nonnull ImageClass cls);

    /**
     * Identifies if you can bundle a virtual machine to cloud storage from within the VM. If you must bundle local to the
     * virtual machine (as with AWS), this should return {@link org.dasein.cloud.Requirement#REQUIRED}. If you must be external, this
     * should return {@link org.dasein.cloud.Requirement#NONE}. If both external and local are supported, this method
     * should return {@link org.dasein.cloud.Requirement#OPTIONAL}.
     * @return how local bundling is supported
     * @throws org.dasein.cloud.CloudException an error occurred with the cloud provider
     * @throws org.dasein.cloud.InternalException an error occurred within the Dasein cloud implementation
     */
    public @Nonnull Requirement identifyLocalBundlingRequirement() throws CloudException, InternalException;

    /**
     * Lists all machine image formats for any uploading/registering of machine images that might be supported.
     * If uploading/registering is not supported, this method will return any empty set.
     * @return the list of supported formats you can upload to the cloud
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     */
    public abstract @Nonnull Iterable<MachineImageFormat> listSupportedFormats() throws CloudException, InternalException;

    /**
     * Lists all machine image formats that can be used in bundling a virtual machine. This should be a sub-set
     * of formats specified in {@link #listSupportedFormats()} as you need to be able to register images of this format.
     * If bundling is not supported, this method will return an empty list.
     * @return the list of supported formats in which you can bundle a virtual machine
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     */
    public abstract @Nonnull Iterable<MachineImageFormat> listSupportedFormatsForBundling() throws CloudException, InternalException;

    /**
     * Lists the image classes supported in this cloud.
     * @return the supported image classes
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     */
    public abstract @Nonnull Iterable<ImageClass> listSupportedImageClasses() throws CloudException, InternalException;

    /**
     * Enumerates the types of images supported in this cloud.
     * @return the list of supported image types
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     */
    public abstract @Nonnull Iterable<MachineImageType> listSupportedImageTypes() throws CloudException, InternalException;

    /**
     * Supports the ability to directly upload an image into the cloud and have it registered as a new image. When
     * doing this, you construct your create options using {@link ImageCreateOptions#getInstance(MachineImageFormat, java.io.InputStream, Platform, String, String)}.
     * @return true if you can do direct uploads into the cloud
     * @throws CloudException an error occurred with the cloud provider when checking this capability
     * @throws InternalException an error occurred within the Dasein cloud implementation while check this capability
     */
    public abstract boolean supportsDirectImageUpload() throws CloudException, InternalException;

    /**
     * Indicates whether capturing a virtual machine as a custom image of type {@link ImageClass#MACHINE} is supported in
     * this cloud.
     * @param type the type of image you are checking for capture capabilities
     * @return true if you can capture custom images in this cloud
     * @throws CloudException an error occurred with the cloud provider when checking this capability
     * @throws InternalException an error occurred within the Dasein cloud implementation while check this capability
     */
    public abstract boolean supportsImageCapture(@Nonnull MachineImageType type) throws CloudException, InternalException;

    /**
     * Indicates whether or not this cloud supports sharing images with specific accounts.
     * @return true if you can share your images with another account
     * @throws CloudException an error occurred with the cloud provider when checking this capability
     * @throws InternalException an error occurred within the Dasein cloud implementation while check this capability
     */
    public abstract boolean supportsImageSharing() throws CloudException, InternalException;

    /**
     * Indicates whether or not this cloud supports making images publicly available to all other accounts.
     * @return true if you can share your images publicly
     * @throws CloudException an error occurred with the cloud provider when checking this capability
     * @throws InternalException an error occurred within the Dasein cloud implementation while check this capability
     */
    public abstract boolean supportsImageSharingWithPublic() throws CloudException, InternalException;

    /**
     * Indicates whether a library of public images of the specified class should be expected. If true,
     * {@link MachineImageSupport#listImages(ImageClass)} should provide a non-empty list of that type.
     * @param cls the image class of the images being checked
     * @return true if a public image library exists
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException an error occurred within the Dasein cloud implementation
     */
    public abstract boolean supportsPublicLibrary(@Nonnull ImageClass cls) throws CloudException, InternalException;
}
