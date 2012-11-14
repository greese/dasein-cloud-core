/**
 * Copyright (C) 2009-2012 enStratus Networks Inc.
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
import java.io.OutputStream;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.dasein.cloud.AccessControlledService;
import org.dasein.cloud.AsynchronousTask;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.identity.ServiceAction;

/**
 * Defines operations on the images/templates from which virtual machines are created. In general, any cloud
 * that is supporting virtual machines or machine images supports both. Machine images can be true machine images,
 * kernel images, or ramdisk images. Some clouds refer to machine images using words like template. Crudely, a machine
 * image is an image laid on to the root disk in creating a virtual machine.
 * @author George Reese
 * @version 2013.01 George Reese Issue #7 Added support for ramdisk and kernel images
 * @since unknown
 */
public interface MachineImageSupport extends AccessControlledService {
    static public final ServiceAction ANY               = new ServiceAction("IMAGE:ANY");

    static public final ServiceAction DOWNLOAD_IMAGE    = new ServiceAction("IMAGE:DOWNLOAD_IMAGE");
    static public final ServiceAction GET_IMAGE         = new ServiceAction("IMAGE:GET_IMAGE");
    static public final ServiceAction IMAGE_VM          = new ServiceAction("IMAGE:IMAGE_VM");
    static public final ServiceAction LIST_IMAGE        = new ServiceAction("IMAGE:LIST_IMAGE");
    static public final ServiceAction MAKE_PUBLIC       = new ServiceAction("IMAGE:MAKE_PUBLIC");
    static public final ServiceAction REGISTER_IMAGE    = new ServiceAction("IMAGE:REGISTER_IMAGE");
    static public final ServiceAction REMOVE_IMAGE      = new ServiceAction("IMAGE:REMOVE_IMAGE");
    static public final ServiceAction SHARE_IMAGE       = new ServiceAction("IMAGE:SHARE_IMAGE");
    static public final ServiceAction UPLOAD_IMAGE      = new ServiceAction("IMAGE:UPLOAD_IMAGE");

    public abstract void downloadImage(@Nonnull String machineImageId, @Nonnull OutputStream toOutput) throws CloudException, InternalException;
    
    public abstract @Nullable MachineImage getMachineImage(@Nonnull String machineImageId) throws CloudException, InternalException;
    
    public abstract @Nonnull String getProviderTermForImage(@Nonnull Locale locale);

    /**
     * Indicates whether or not a public image library of {@link ImageClass#MACHINE} is supported.
     * @return true if there is a public library
     * @deprecated Use {@link #supportsPublicLibrary(ImageClass)}
     */
    public abstract boolean hasPublicLibrary();

    public abstract @Nonnull AsynchronousTask<String> imageVirtualMachine(String vmId, String name, String description) throws CloudException, InternalException;
    
    public abstract @Nonnull AsynchronousTask<String> imageVirtualMachineToStorage(String vmId, String name, String description, String directory) throws CloudException, InternalException;
    
    public abstract @Nonnull String installImageFromUpload(@Nonnull MachineImageFormat format, @Nonnull InputStream imageStream) throws CloudException, InternalException;
    
    public abstract boolean isImageSharedWithPublic(@Nonnull String machineImageId) throws CloudException, InternalException;
    
    public abstract boolean isSubscribed() throws CloudException, InternalException;

    /**
     * Lists all images in my library. This generally includes all images belonging to me as well any explicitly shared
     * with me. In clouds without a public library, it's all images I can see.
     * @param cls the class of image being listed
     * @return the list of images in my image library of the specified image class
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     */
    public abstract @Nonnull Iterable<MachineImage> listImages(@Nonnull ImageClass cls) throws CloudException, InternalException;

    /**
     * Lists all images that I can see belonging to the specified account owner. These images may either be public or
     * explicitly shared with me.
     * @param cls the class of the image being listed
     * @param ownedBy the account number of the owner of the image
     * @return the list of images I can see belonging to the specified account owner of the specified image class
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     */
    public abstract @Nonnull Iterable<MachineImage> listImages(@Nonnull ImageClass cls, @Nonnull String ownedBy) throws CloudException, InternalException;

    /**
     * Lists all publicly available images of the specified image class. {@link #supportsPublicLibrary(ImageClass)} must be true for this
     * class of images if this method is to return a non-empty list.
     * @param cls the class of image being listed
     * @return a list of all publicly available images of the specified image class
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     */
    public abstract @Nonnull Iterable<MachineImage> listPublicImages(@Nonnull ImageClass cls) throws CloudException, InternalException;

    /**
     * Lists all images of class {@link ImageClass#MACHINE} in my library.
     * @return the list of machine machine images
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     * @deprecated Use {@link #listImages(ImageClass)} with {@link ImageClass#MACHINE}
     */
    public abstract @Nonnull Iterable<MachineImage> listMachineImages() throws CloudException, InternalException;

    /**
     * Lists all images of class {@link ImageClass#MACHINE} that I can see belonging to the specified account owner.
     * These images may either be public or explicitly shared with me. You may specify no accountId to indicate you
     * are looking for the public library.
     * @param accountId the accountId of the owner of the target images or <code>null</code> indicating you want the public library
     * @return the list of machine machine images belonging to the specified account owner
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException a local error occurred in the Dasein Cloud implementation
     * @deprecated Use {@link #listImages(ImageClass,String)} or {@link #listPublicImages(ImageClass)} with {@link ImageClass#MACHINE}
     */
    public abstract @Nonnull Iterable<MachineImage> listMachineImagesOwnedBy(String accountId) throws CloudException, InternalException;
    
    public abstract @Nonnull Iterable<MachineImageFormat> listSupportedFormats() throws CloudException, InternalException;
    
    public abstract @Nonnull Iterable<String> listShares(@Nonnull String forMachineImageId) throws CloudException, InternalException;

    public abstract @Nonnull String registerMachineImage(String atStorageLocation) throws CloudException, InternalException;
    
    public abstract void remove(@Nonnull String machineImageId) throws CloudException, InternalException;
    
    public abstract @Nonnull Iterable<MachineImage> searchMachineImages(@Nullable String keyword, @Nullable Platform platform, @Nullable Architecture architecture) throws CloudException, InternalException;

    public abstract void shareMachineImage(@Nonnull String machineImageId, @Nonnull String withAccountId, boolean allow) throws CloudException, InternalException;
    
    public abstract boolean supportsCustomImages();

    /**
     * Identifies whether this cloud supports the specified kind of image.
     * @param cls the image class being checked
     * @return true if the cloud supports (at least) listing these kinds of images
     * @throws CloudException an error occurred with the cloud provider when checking this capability
     * @throws InternalException an error occurred within the Dasein cloud implementation while check this capability
     */
    public abstract boolean supportsImageClass(ImageClass cls) throws CloudException, InternalException;

    public abstract boolean supportsImageSharing();
    
    public abstract boolean supportsImageSharingWithPublic();

    /**
     * Indicates whether a library of public images of the specified class should be expected. If true,
     * {@link #listImages(ImageClass)} should provide a non-empty list of that type.
     * @param cls the image class of the images being checked
     * @return true if a public image library exists
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException an error occurred within the Dasein cloud implementation
     */
    public abstract boolean supportsPublicLibrary(@Nonnull ImageClass cls) throws CloudException, InternalException;

    public abstract @Nonnull String transfer(@Nonnull CloudProvider fromCloud, @Nonnull String machineImageId) throws CloudException, InternalException;
}
