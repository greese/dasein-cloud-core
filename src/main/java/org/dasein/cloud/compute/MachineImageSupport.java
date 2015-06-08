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

import java.io.InputStream;
import java.util.Collection;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.dasein.cloud.AccessControlledService;
import org.dasein.cloud.AsynchronousTask;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.Requirement;
import org.dasein.cloud.ResourceStatus;
import org.dasein.cloud.Tag;
import org.dasein.cloud.identity.ServiceAction;

/**
 * Defines operations on the images/templates from which virtual machines are created. In general, any cloud
 * that is supporting virtual machines or machine images supports both. Machine images can be true machine images,
 * kernel images, or ramdisk images. Some clouds refer to machine images using words like template. Crudely, a machine
 * image is an image laid on to the root disk in creating a virtual machine.
 * @author George Reese
 * @version 2013.01 Added support for ramdisk and kernel images (Issue #7)
 * @version 2013.01 Added synchronous bundling methods (Issue #12)
 * @version 2013.01 Added a resource lister (Issue #4)
 * @version 2013.02 Added method to identify term for custom images (issue #34)
 * @since unknown
 */
@SuppressWarnings("UnusedDeclaration")
public interface MachineImageSupport extends AccessControlledService {
    static public final ServiceAction ANY               = new ServiceAction("IMAGE:ANY");

    static public final ServiceAction DOWNLOAD_IMAGE    = new ServiceAction("IMAGE:DOWNLOAD_IMAGE");
    static public final ServiceAction GET_IMAGE         = new ServiceAction("IMAGE:GET_IMAGE");
    static public final ServiceAction IMAGE_VM          = new ServiceAction("IMAGE:IMAGE_VM");
    static public final ServiceAction COPY_IMAGE        = new ServiceAction("IMAGE:COPY_IMAGE");
    static public final ServiceAction LIST_IMAGE        = new ServiceAction("IMAGE:LIST_IMAGE");
    static public final ServiceAction MAKE_PUBLIC       = new ServiceAction("IMAGE:MAKE_PUBLIC");
    static public final ServiceAction REGISTER_IMAGE    = new ServiceAction("IMAGE:REGISTER_IMAGE");
    static public final ServiceAction REMOVE_IMAGE      = new ServiceAction("IMAGE:REMOVE_IMAGE");
    static public final ServiceAction SHARE_IMAGE       = new ServiceAction("IMAGE:SHARE_IMAGE");
    static public final ServiceAction UPLOAD_IMAGE      = new ServiceAction("IMAGE:UPLOAD_IMAGE");

    /**
     * Adds the specified account number to the list of accounts with which this image is shared.
     * @param providerImageId the unique ID of the image to be shared
     * @param accountNumber the account number with which the image will be shared
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     * @throws OperationNotSupportedException the cloud does not support sharing images with other accounts
     */
    public void addImageShare(@Nonnull String providerImageId, @Nonnull String accountNumber) throws CloudException, InternalException;

    /**
     * Shares the specified image with the public.
     * @param providerImageId the unique ID of the image to be made public
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     * @throws OperationNotSupportedException the cloud does not support sharing images with the public
     */
    public void addPublicShare(@Nonnull String providerImageId) throws CloudException, InternalException;

    /**
     * Bundles the specified virtual machine to cloud storage so it may be registered as a machine image. Upon completion
     * of this task, there should be a file or set of files that capture the target virtual machine in a file format
     * that can later be registered into a machine image.
     * @param virtualMachineId the virtual machine to be bundled
     * @param format the format in which the VM should be bundled
     * @param bucket the bucket to which the VM should be bundled
     * @param name the name of the object to be created or the prefix for the name
     * @return the location of the bundle file or a manifest to the bundle file
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     */
    public @Nonnull String bundleVirtualMachine(@Nonnull String virtualMachineId, @Nonnull MachineImageFormat format, @Nonnull String bucket, @Nonnull String name) throws CloudException, InternalException;

    /**
     * Bundles the specified virtual machine to cloud storage so it may be registered as a machine image. Upon completion
     * of this task, there should be a file or set of files that capture the target virtual machine in a file format
     * that can later be registered into a machine image.
     * @param virtualMachineId the virtual machine to be bundled
     * @param format the format in which the VM should be bundled
     * @param bucket the bucket to which the VM should be bundled
     * @param name the name of the object to be created or the prefix for the name
     * @param trackingTask the task against which progress for bundling will be tracked
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     */
    public void bundleVirtualMachineAsync(@Nonnull String virtualMachineId, @Nonnull MachineImageFormat format, @Nonnull String bucket, @Nonnull String name, @Nonnull AsynchronousTask<String> trackingTask) throws CloudException, InternalException;

    /**
     * Captures a virtual machine as a machine image. If the underlying cloud requires the virtual machine to change state
     * (a common example is that the VM must be {@link VmState#STOPPED}), then this method will make sure the VM is in
     * that state. This method blocks until the cloud API has provided a reference to the machine image, regardless of
     * what state it is in.
     * @param options the options used in capturing the virtual machine
     * @return a newly created machine image
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     * @throws OperationNotSupportedException the cloud does not support custom image creation
     */
    public @Nonnull MachineImage captureImage(@Nonnull ImageCreateOptions options) throws CloudException, InternalException;

    /**
     * Executes the process of {@link #captureImage(ImageCreateOptions)} as an asynchronous process tracked using an
     * asynchronous task object. This method is expected to return immediately and provide feedback to a client on the
     * progress of the machine image capture process.
     * @param options the options to be used in capturing the virtual machine
     * @param taskTracker the asynchronous task for tracking the progress of this operation
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     * @throws OperationNotSupportedException the cloud does not support custom image creation
     */
    public void captureImageAsync(@Nonnull ImageCreateOptions options, @Nonnull AsynchronousTask<MachineImage> taskTracker) throws CloudException, InternalException;

    /**
     * Copies a machine image from current region of the user to another region.
     *
     * @param options the options used to copy the machine image.
     * @return ID of the new machine image created as a result of the copying.
     * @throws CloudException    an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     */
    public @Nonnull String copyImage( @Nonnull ImageCopyOptions options ) throws CloudException, InternalException;

    /**
     * Provides access to meta-data about virtual machine capabilities in the current region of this cloud.
     * @return a description of the features supported by this region of this cloud
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     */
    public ImageCapabilities getCapabilities() throws CloudException, InternalException;

    /**
     * Provides access to the current state of the specified image.
     * @param providerImageId the cloud provider ID uniquely identifying the desired image
     * @return the image matching the desired ID if it exists
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     */
    public @Nullable MachineImage getImage(@Nonnull String providerImageId) throws CloudException, InternalException;

    /**
     * Provides access to the current state of the specified image.
     * @param providerImageId the cloud provider ID uniquely identifying the desired image
     * @return the image matching the desired ID if it exists
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     * @deprecated Use {@link #getImage(String)}
     */
    public @Nullable MachineImage getMachineImage(@Nonnull String providerImageId) throws CloudException, InternalException;

    /**
     * Provides the cloud provider specific term for a machine image.
     * @param locale the locale for which the term should be translated
     * @return the term used by the provider to describe a machine image
     * @deprecated Use {@link #getProviderTermForImage(Locale, ImageClass)}
     */
    @Deprecated
    public @Nonnull String getProviderTermForImage(@Nonnull Locale locale);

    /**
     * Provides the cloud provider specific term for a public image of the specified image class.
     * @param locale the language in which the term should be presented
     * @param cls the image class for the desired type
     * @return the term used by the provider to describe a public image
     * @deprecated use {@link ImageCapabilities#getProviderTermForImage(java.util.Locale, ImageClass)}
     */
    @Deprecated
    public @Nonnull String getProviderTermForImage(@Nonnull Locale locale, @Nonnull ImageClass cls);

    /**
     * Provides the cloud provider specific term for a custom image of the specified image class.
     * @param locale the locale for which the term should be translated
     * @param cls the image class for the desired type
     * @return the term used by the provider to describe a custom image
     * @deprecated use {@link ImageCapabilities#getProviderTermForCustomImage(java.util.Locale, ImageClass)}
     */
    @Deprecated
    public @Nonnull String getProviderTermForCustomImage(@Nonnull Locale locale, @Nonnull ImageClass cls);

    /**
     * Indicates whether or not a public image library of {@link ImageClass#MACHINE} is supported.
     * @return true if there is a public library
     * @deprecated Use {@link #supportsPublicLibrary(ImageClass)}
     */
    public boolean hasPublicLibrary();

    /**
     * Identifies if you can bundle a virtual machine to cloud storage from within the VM. If you must bundle local to the
     * virtual machine (as with AWS), this should return {@link Requirement#REQUIRED}. If you must be external, this
     * should return {@link Requirement#NONE}. If both external and local are supported, this method
     * should return {@link Requirement#OPTIONAL}.
     * @return how local bundling is supported
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException an error occurred within the Dasein cloud implementation
     * @deprecated use {@link ImageCapabilities#identifyLocalBundlingRequirement()}
     */
    @Deprecated
    public @Nonnull Requirement identifyLocalBundlingRequirement() throws CloudException, InternalException;

    /**
     * Creates a machine image from a virtual machine. This method simply calls {@link #captureImageAsync(ImageCreateOptions, AsynchronousTask)}
     * using the task it returns to you.
     * @param vmId the unique ID of the virtual machine to be imaged
     * @param name the name to give the new image
     * @param description the description to give the new image
     * @return an asynchronous task for tracking the progress of the imaging
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException an error occurred within the Dasein cloud implementation
     * @throws OperationNotSupportedException the cloud does not support custom image creation
     * @deprecated Use {@link #captureImage(ImageCreateOptions)} or {@link #captureImageAsync(ImageCreateOptions, AsynchronousTask)}
     */
    public @Nonnull AsynchronousTask<String> imageVirtualMachine(String vmId, String name, String description) throws CloudException, InternalException;

    /**
     * Indicates whether or not the specified image is shared publicly. It should return false when public image sharing
     * simply isn't supported by the underlying cloud.
     * @param providerImageId the machine image being checked for public status
     * @return true if the target machine image is shared with the general public
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException an error occurred within the Dasein cloud implementation
     */
    public boolean isImageSharedWithPublic(@Nonnull String providerImageId) throws CloudException, InternalException;

    /**
     * Indicates whether or not this account has access to any image services that might exist in this cloud.
     * @return true if the account is subscribed
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException an error occurred within the Dasein cloud implementation
     */
    public boolean isSubscribed() throws CloudException, InternalException;

    /**
     * Lists the current status for all images in my library. The images returned should be the same list provided by
     * {@link #listImages(ImageClass)}, except that this method returns a list of {@link ResourceStatus} objects.
     * @param cls the image class of the target images
     * @return a list of status objects for the images in the library
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     */
    public @Nonnull Iterable<ResourceStatus> listImageStatus(@Nonnull ImageClass cls) throws CloudException, InternalException;

    /**
     * Lists all images in a specific library based on the given filter options. With no filter options specified, this
     * generally includes all images belonging to the current account as well any explicitly shared with me. In clouds without a public
     * library, it's all images I can see. The filtering functionality may be wholly or partially delegated to the cloud
     * provider for efficiency.
     * @param options filter options
     * @return the list of images in my image library of the specified image class
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     */
    public @Nonnull Iterable<MachineImage> listImages(@Nullable ImageFilterOptions options) throws CloudException, InternalException;

    /**
     * Lists all images in my library. This generally includes all images belonging to me as well any explicitly shared
     * with me. In clouds without a public library, it's all images I can see.
     * @param cls the class of image being listed
     * @return the list of images in my image library of the specified image class
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     * @deprecated Use {@link #listImages(ImageFilterOptions)}
     */
    public @Nonnull Iterable<MachineImage> listImages(@Nonnull ImageClass cls) throws CloudException, InternalException;

    /**
     * Lists all images that I can see belonging to the specified account owner. These images may either be public or
     * explicitly shared with me.
     * @param cls the class of the image being listed
     * @param ownedBy the account number of the owner of the image
     * @return the list of images I can see belonging to the specified account owner of the specified image class
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     * @deprecated Use {@link #listImages(ImageFilterOptions)}
     */
    public @Nonnull Iterable<MachineImage> listImages(@Nonnull ImageClass cls, @Nonnull String ownedBy) throws CloudException, InternalException;

    /**
     * Lists all machine image formats for any uploading/registering of machine images that might be supported.
     * If uploading/registering is not supported, this method will return any empty set.
     * @return the list of supported formats you can upload to the cloud
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     * @deprecated use {@link ImageCapabilities#listSupportedFormats()}
     */
    @Deprecated
    public @Nonnull Iterable<MachineImageFormat> listSupportedFormats() throws CloudException, InternalException;

    /**
     * Lists all machine image formats that can be used in bundling a virtual machine. This should be a sub-set
     * of formats specified in {@link #listSupportedFormats()} as you need to be able to register images of this format.
     * If bundling is not supported, this method will return an empty list.
     * @return the list of supported formats in which you can bundle a virtual machine
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     * @deprecated use {@link ImageCapabilities#listSupportedFormatsForBundling()}
     */
    @Deprecated
    public @Nonnull Iterable<MachineImageFormat> listSupportedFormatsForBundling() throws CloudException, InternalException;


    /**
     * Lists all images of class {@link ImageClass#MACHINE} in my library.
     * @return the list of machine machine images
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     * @deprecated Use {@link #listImages(ImageClass)} with {@link ImageClass#MACHINE}
     */
    public @Nonnull Iterable<MachineImage> listMachineImages() throws CloudException, InternalException;

    /**
     * Lists all images of class {@link ImageClass#MACHINE} that I can see belonging to the specified account owner.
     * These images may either be public or explicitly shared with me. You may specify no accountId to indicate you
     * are looking for the public library.
     * @param accountId the accountId of the owner of the target images or <code>null</code> indicating you want the public library
     * @return the list of machine machine images belonging to the specified account owner
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     * @deprecated Use {@link #listImages(ImageClass,String)}
     */
    public @Nonnull Iterable<MachineImage> listMachineImagesOwnedBy(String accountId) throws CloudException, InternalException;

    /**
     * Provides the account numbers for all accounts which which the specified machine image has been shared. This method
     * should return an empty list when sharing is unsupported.
     * @param providerImageId the unique ID of the image being checked
     * @return a list of account numbers with which the target image has been shared
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     */
    public @Nonnull Iterable<String> listShares(@Nonnull String providerImageId) throws CloudException, InternalException;

    /**
     * Lists the image classes supported in this cloud.
     * @return the supported image classes
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     * @deprecated use {@link ImageCapabilities#listSupportedImageClasses()}
     */
    @Deprecated
    public @Nonnull Iterable<ImageClass> listSupportedImageClasses() throws CloudException, InternalException;

    /**
     * Enumerates the types of images supported in this cloud.
     * @return the list of supported image types
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     * @deprecated use {@link ImageCapabilities#listSupportedImageTypes()}
     */
    @Deprecated
    public @Nonnull Iterable<MachineImageType> listSupportedImageTypes() throws CloudException, InternalException;

    /**
     * Registers the bundled virtual machine stored in object storage as a machine image in the cloud.
     * @param options the options used in registering the machine image
     * @return a newly created machine image
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     * @throws OperationNotSupportedException the cloud does not support registering image from object store bundles
     */
    public @Nonnull MachineImage registerImageBundle(@Nonnull ImageCreateOptions options) throws CloudException, InternalException;

  /**
   * Permanently removes all traces of the target image. This method should remove both the image record in the cloud
   * and any cloud storage location in which the image resides for staging.
   * @param providerImageId the unique ID of the image to be removed
   * @throws CloudException an error occurred with the cloud provider
   * @throws InternalException a local error occurred in the Dasein Cloud implementation
   */
  public void remove(@Nonnull String providerImageId) throws CloudException, InternalException;

  /**
   * Permanently removes all traces of the target image. This method should remove both the image record in the cloud
   * and any cloud storage location in which the image resides for staging.
   * @param providerImageId the unique ID of the image to be removed
   * @param checkState if the state of the machine image should be checked first
   * @throws CloudException an error occurred with the cloud provider
   * @throws InternalException a local error occurred in the Dasein Cloud implementation
   */
  public void remove(@Nonnull String providerImageId, boolean checkState) throws CloudException, InternalException;

  /**
     * Removes ALL specific account shares for the specified image. NOTE THAT THIS METHOD WILL NOT THROW AN EXCEPTION
     * WHEN IMAGE SHARING IS NOT SUPPORTED. IT IS A NO-OP IN THAT SCENARIO.
     * @param providerImageId the unique ID of the image to be unshared
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     */
    public void removeAllImageShares(@Nonnull String providerImageId) throws CloudException, InternalException;

    /**
     * Removes the specified account number from the list of accounts with which this image is shared.
     * @param providerImageId the unique ID of the image to be unshared
     * @param accountNumber the account number to be removed
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     * @throws OperationNotSupportedException the cloud does not support sharing images with other accounts
     */
    public void removeImageShare(@Nonnull String providerImageId, @Nonnull String accountNumber) throws CloudException, InternalException;

    /**
     * Removes the public share (if shared) for this image. Safe to call even if the image is not shared or sharing
     * is not supported.
     * @param providerImageId the unique ID of the image to be unshared
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     */
    public void removePublicShare(@Nonnull String providerImageId) throws CloudException, InternalException;

    /**
     * Searches images owned by the specified account number (if null, all visible images are searched). It will match against
     * the specified parameters. Any null parameter does not constrain the search.
     * @param accountNumber the account number to search against or null for searching all visible images
     * @param keyword a keyword on which to search
     * @param platform the platform to match
     * @param architecture the architecture to match
     * @param imageClasses the image classes to search for (null or empty list for all)
     * @return all matching machine images
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     */
    public @Nonnull Iterable<MachineImage> searchImages(@Nullable String accountNumber, @Nullable String keyword, @Nullable Platform platform, @Nullable Architecture architecture, @Nullable ImageClass ... imageClasses) throws CloudException, InternalException;

    /**
     * Searches all machine images visible, public or otherwise, to this account for ones that match the specified values.
     * If a search parameter is null, it doesn't constrain on that parameter.
     * @param keyword a keyword on which to search
     * @param platform the platform to be matched
     * @param architecture the architecture to be matched
     * @return any matching machine images (images of class {@link ImageClass#MACHINE})
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     * @deprecated Use {@link #searchImages(String, String, Platform, Architecture, ImageClass...)} and/or {@link #searchPublicImages(String, Platform, Architecture, ImageClass...)}
     */
    @Deprecated
    public @Nonnull Iterable<MachineImage> searchMachineImages(@Nullable String keyword, @Nullable Platform platform, @Nullable Architecture architecture) throws CloudException, InternalException;

    /**
     * Searches all snapshots visible to the current account owner (whether owned by the account owner or someone else)
     * for all images matching the specified image filter options. This differs from the {@link #listImages(ImageFilterOptions)}
     * method in that it covers all images, not just ones belonging to a specific account.
     * @param options filter criteria
     * @return all images in the current region matching the specified filter options
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     * @throws CloudException an error occurred with the cloud provider
     */
    public @Nonnull Iterable<MachineImage> searchPublicImages(@Nonnull ImageFilterOptions options) throws InternalException, CloudException;

    /**
     * Searches the public machine image library. It will match against the specified parameters. Any null parameter does
     * not constrain the search.
     * @param keyword a keyword on which to search
     * @param platform the platform to match
     * @param architecture the architecture to match
     * @param imageClasses the image classes to search for (null or empty list for all)
     * @return all matching machine images
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     */
    public @Nonnull Iterable<MachineImage> searchPublicImages(@Nullable String keyword, @Nullable Platform platform, @Nullable Architecture architecture, @Nullable ImageClass ... imageClasses) throws CloudException, InternalException;

    /**
     * Adds or removes sharing for the specified image with the specified account or the public. This method simply delegates to the
     * newer {@link #addImageShare(String, String)}, {@link #removeImageShare(String, String)},
     * {@link #addPublicShare(String)}, or {@link #removePublicShare(String)} methods.
     * @param providerImageId the image to be shared/unshared
     * @param withAccountId the account with which the image is to be shared/unshared (null if the operation is for a public share)
     * @param allow true if the image is to be shared, false if it is to be unshared
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     * @throws OperationNotSupportedException the cloud does not support sharing images with other accounts
     * @deprecated Use {@link #addImageShare(String, String)}, {@link #removeImageShare(String, String)}, {@link #addPublicShare(String)}, or {@link #removePublicShare(String)}
     */
    public void shareMachineImage(@Nonnull String providerImageId, @Nullable String withAccountId, boolean allow) throws CloudException, InternalException;

    /**
     * Indicates whether or not the cloud supports the ability to capture custom images.
     * @return true if you can capture custom images in this cloud
     * @throws CloudException an error occurred with the cloud provider when checking this capability
     * @throws InternalException an error occurred within the Dasein cloud implementation while check this capability
     * @deprecated Use {@link #supportsImageCapture(MachineImageType)}
     */
    public boolean supportsCustomImages() throws CloudException, InternalException;

    /**
     * Supports the ability to directly upload an image into the cloud and have it registered as a new image. When
     * doing this, you construct your create options using {@link ImageCreateOptions#getInstance(MachineImageFormat, InputStream, Platform, String, String)}.
     * @return true if you can do direct uploads into the cloud
     * @throws CloudException an error occurred with the cloud provider when checking this capability
     * @throws InternalException an error occurred within the Dasein cloud implementation while check this capability
     * @deprecated use {@link ImageCapabilities#supportsDirectImageUpload()}
     */
    @Deprecated
    public boolean supportsDirectImageUpload() throws CloudException, InternalException;

    /**
     * Indicates whether capturing a virtual machine as a custom image of type {@link ImageClass#MACHINE} is supported in
     * this cloud.
     * @param type the type of image you are checking for capture capabilities
     * @return true if you can capture custom images in this cloud
     * @throws CloudException an error occurred with the cloud provider when checking this capability
     * @throws InternalException an error occurred within the Dasein cloud implementation while check this capability
     * @deprecated use {@link ImageCapabilities#supportsImageCapture(MachineImageType)}
     */
    @Deprecated
    public boolean supportsImageCapture(@Nonnull MachineImageType type) throws CloudException, InternalException;

    /**
     * Indicates whether or not this cloud supports sharing images with specific accounts.
     * @return true if you can share your images with another account
     * @throws CloudException an error occurred with the cloud provider when checking this capability
     * @throws InternalException an error occurred within the Dasein cloud implementation while check this capability
     * @deprecated use {@link ImageCapabilities#supportsImageSharing()}
     */
    public boolean supportsImageSharing() throws CloudException, InternalException;

    /**
     * Indicates whether or not this cloud supports making images publicly available to all other accounts.
     * @return true if you can share your images publicly
     * @throws CloudException an error occurred with the cloud provider when checking this capability
     * @throws InternalException an error occurred within the Dasein cloud implementation while check this capability
     * @deprecated use {@link ImageCapabilities#supportsImageSharingWithPublic()}
     */
    @Deprecated
    public boolean supportsImageSharingWithPublic() throws CloudException, InternalException;

    /**
     * Indicates whether a library of public images of the specified class should be expected. If true,
     * {@link #listImages(ImageClass)} should provide a non-empty list of that type.
     * @param cls the image class of the images being checked
     * @return true if a public image library exists
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException an error occurred within the Dasein cloud implementation
     * @deprecated use {@link ImageCapabilities#supportsPublicLibrary(ImageClass)}
     */
    @Deprecated
    public boolean supportsPublicLibrary(@Nonnull ImageClass cls) throws CloudException, InternalException;

    /**
     * Updates meta-data for a image with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     *
     * @param imageId the image to update
     * @param tags    the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void updateTags(@Nonnull String imageId, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Updates meta-data for multiple images with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     *
     * @param imageIds the images to update
     * @param tags     the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void updateTags(@Nonnull String[] imageIds, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Removes meta-data from an image. If tag values are set, their removal is dependent on underlying cloud
     * provider behavior. They may be removed only if the tag value matches or they may be removed regardless of the
     * value.
     *
     * @param imageId the image to update
     * @param tags    the meta-data tags to remove
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void removeTags(@Nonnull String imageId, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Removes meta-data from multiple images. If tag values are set, their removal is dependent on underlying cloud
     * provider behavior. They may be removed only if the tag value matches or they may be removed regardless of the
     * value.
     *
     * @param imageIds the image to update
     * @param tags     the meta-data tags to remove
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void removeTags(@Nonnull String[] imageIds, @Nonnull Tag... tags) throws CloudException, InternalException;

    /**
     * Set meta-data for an image. Remove any tags that were not provided by the incoming tags, and add or
     * overwrite any new or pre-existing tags.
     *
     * @param imageId the image to update
     * @param tags    the meta-data tags to remove
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void setTags( @Nonnull String imageId, @Nonnull Tag... tags ) throws CloudException, InternalException;

    /**
     * Set meta-data for multiple images. Remove any tags that were not provided by the incoming tags, and add or
     * overwrite any new or pre-existing tags.
     *
     * @param imageIds the images to update
     * @param tags     the meta-data tags to remove
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void setTags( @Nonnull String[] imageIds, @Nonnull Tag... tags ) throws CloudException, InternalException;

}
