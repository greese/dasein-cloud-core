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

import org.dasein.cloud.AbstractProviderService;
import org.dasein.cloud.AsynchronousTask;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Locale;

/**
 * Basic implementations of deprecated methods, helper functions, and default approaches to implementing methods for
 * image support.
 * <p>Created by George Reese: 1/29/13 1:55 PM</p>
 * @author George Reese
 * @version 2013.04
 * @since 2013.04
 */
public abstract class AbstractImageSupport<T extends CloudProvider> extends AbstractProviderService<T> implements MachineImageSupport {
    protected AbstractImageSupport(T provider) {
        super(provider);
    }

    @Override
    public void addImageShare(@Nonnull String providerImageId, @Nonnull String accountNumber) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Support for image sharing is not currently implemented in " + getProvider().getCloudName());
    }

    @Override
    public void addPublicShare(@Nonnull String providerImageId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Support for image sharing is not currently implemented in " + getProvider().getCloudName());
    }

    @Override
    public @Nonnull String bundleVirtualMachine(@Nonnull String virtualMachineId, @Nonnull MachineImageFormat format, @Nonnull String bucket, @Nonnull String name) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Image bundling is not currently implemented in " + getProvider().getCloudName());
    }

    @Override
    public void bundleVirtualMachineAsync(@Nonnull String virtualMachineId, @Nonnull MachineImageFormat format, @Nonnull String bucket, @Nonnull String name, @Nonnull AsynchronousTask<String> trackingTask) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Image bundling is not currently implemented in " + getProvider().getCloudName());
    }

    /**
     * Executes an actual image capture of a virtual machine. The default implementation throws an {@link OperationNotSupportedException}
     * indicating this is not supported.
     * @param options the options used for creating the virtual machine
     * @param task the task for tracking image creation (or null if not interested/not asynchronous)
     * @return a newly created image
     * @throws CloudException an error occurred with the cloud provider while capturing the virtual machine as an image
     * @throws InternalException an error occurred locally while attempting to capture the virtual machine as an image
     */
    protected MachineImage capture(@Nonnull ImageCreateOptions options, @Nullable AsynchronousTask<MachineImage> task) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Image capture is not currently implemented in " + getProvider().getCloudName());
    }

    @Override
    public final @Nonnull MachineImage captureImage(@Nonnull ImageCreateOptions options) throws CloudException, InternalException {
        boolean supported = false;

        for( MachineImageType type : MachineImageType.values() ) {
            if( getCapabilities().supportsImageCapture(type) ) {
                supported = true;
            }
        }
        if( !supported ) {
            throw new OperationNotSupportedException("Image capture is not supported in " + getProvider().getCloudName());
        }
        return capture(options, null);
    }

    @Override
    public final void captureImageAsync(final @Nonnull ImageCreateOptions options, final @Nonnull AsynchronousTask<MachineImage> taskTracker) throws CloudException, InternalException {
        boolean supported = false;

        for( MachineImageType type : MachineImageType.values() ) {
            if( getCapabilities().supportsImageCapture(type) ) {
                supported = true;
            }
        }
        if( !supported ) {
            throw new OperationNotSupportedException("Image capture is not supported in " + getProvider().getCloudName());
        }
        getProvider().hold();
        Thread t = new Thread() {
            public void run() {
                try {
                    MachineImage img = capture(options, taskTracker);

                    if( !taskTracker.isComplete() ) {
                        taskTracker.completeWithResult(img);
                    }
                }
                catch( Throwable t ) {
                    taskTracker.complete(t);
                }
                finally {
                    getProvider().release();
                }
            }
        };

        t.setName("Capture of " + options.getVirtualMachineId() + " in " + getProvider().getCloudName());
        t.setDaemon(true);
        t.start();
    }

    @Override
    public @Nonnull String copyImage( @Nonnull ImageCopyOptions options ) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Image copying is not currently implemented in " + getProvider().getCloudName());
    }

    @Override
    public final @Nullable MachineImage getMachineImage(@Nonnull String providerImageId) throws CloudException, InternalException {
        return getImage(providerImageId);
    }

    @Override
    @Deprecated
    public @Nonnull String getProviderTermForImage(@Nonnull Locale locale) {
        try {
            return getCapabilities().getProviderTermForImage(locale, ImageClass.MACHINE);
        }
        catch ( Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    @Deprecated
    public @Nonnull String getProviderTermForImage(@Nonnull Locale locale, @Nonnull ImageClass cls) {
        try {
            return getCapabilities().getProviderTermForImage(locale, cls);
        }
        catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    @Deprecated
    public @Nonnull String getProviderTermForCustomImage(@Nonnull Locale locale, @Nonnull ImageClass cls) {
        try {
            return getCapabilities().getProviderTermForImage(locale, cls);
        }
        catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    @Deprecated
    public boolean hasPublicLibrary() {
        try {
            return getCapabilities().supportsPublicLibrary(ImageClass.MACHINE);
        }
        catch( Throwable t ) {
            throw new RuntimeException(t);
        }
    }

    @Override
    @Deprecated
    public @Nonnull Requirement identifyLocalBundlingRequirement() throws CloudException, InternalException {
        return getCapabilities().identifyLocalBundlingRequirement();
    }

    @Override
    public final @Nonnull AsynchronousTask<String> imageVirtualMachine(@Nonnull String vmId, @Nonnull String name, @Nonnull String description) throws CloudException, InternalException {
        ComputeServices services = getProvider().getComputeServices();

        if( services == null ) {
            throw new CloudException("No virtual machine " + vmId + " exists to image in this cloud");
        }
        VirtualMachineSupport support = services.getVirtualMachineSupport();

        if( support == null ) {
            throw new CloudException("No virtual machine " + vmId + " exists to image in this cloud");
        }
        VirtualMachine vm = support.getVirtualMachine(vmId);

        if( vm == null ) {
            throw new CloudException("No virtual machine " + vmId + " exists to image in this cloud");
        }

        final ImageCreateOptions options = ImageCreateOptions.getInstance(vm, name, description);
        final AsynchronousTask<String> task = new AsynchronousTask<String>();

        getProvider().hold();
        Thread t = new Thread() {
            public void run() {
                try {
                    task.completeWithResult(capture(options, null).getProviderMachineImageId());
                }
                catch( Throwable t ) {
                    task.complete(t);
                }
                finally {
                    getProvider().release();
                }
            }
        };

        t.setName("Capture Image from " + vm.getProviderVirtualMachineId() + " in " + getProvider().getCloudName());
        t.setDaemon(true);
        t.start();

        return task;
    }

    @Override
    public boolean isImageSharedWithPublic(@Nonnull String providerImageId) throws CloudException, InternalException {
        return false;
    }

    @Override
    public @Nonnull Iterable<ResourceStatus> listImageStatus(@Nonnull ImageClass cls) throws CloudException, InternalException {
        ArrayList<ResourceStatus> status = new ArrayList<ResourceStatus>();

        for( MachineImage img : listImages(ImageFilterOptions.getInstance(cls)) ) {
            status.add(new ResourceStatus(img.getProviderMachineImageId(), img.getCurrentState()));
        }
        return status;
    }

    @Override
    public @Nonnull Iterable<MachineImage> listImages(@Nonnull ImageClass cls) throws CloudException, InternalException {
        return listImages(ImageFilterOptions.getInstance(cls));
    }

    @Override
    public @Nonnull Iterable<MachineImage> listImages(@Nonnull ImageClass cls, @Nonnull String ownedBy) throws CloudException, InternalException {
        return listImages(ImageFilterOptions.getInstance(cls).withAccountNumber(ownedBy));
    }

    @Override
    @Deprecated
    public @Nonnull Iterable<MachineImageFormat> listSupportedFormats() throws CloudException, InternalException {
        return getCapabilities().listSupportedFormats();
    }

    @Override
    @Deprecated
    public @Nonnull Iterable<MachineImageFormat> listSupportedFormatsForBundling() throws CloudException, InternalException {
        return getCapabilities().listSupportedFormatsForBundling();
    }

    @Override
    public @Nonnull Iterable<MachineImage> listMachineImages() throws CloudException, InternalException {
        return listImages(ImageFilterOptions.getInstance(ImageClass.MACHINE));
    }

    @Override
    public @Nonnull Iterable<MachineImage> listMachineImagesOwnedBy(@Nullable String accountId) throws CloudException, InternalException {
        if( accountId == null ) {
            return listImages(ImageFilterOptions.getInstance(ImageClass.MACHINE));
        }
        else {
            return listImages(ImageFilterOptions.getInstance(ImageClass.MACHINE).withAccountNumber(accountId));
        }
    }

    @Override
    public @Nonnull Iterable<String> listShares(@Nonnull String providerImageId) throws CloudException, InternalException {
        return Collections.emptyList();
    }

    @Override
    @Deprecated
    public @Nonnull Iterable<ImageClass> listSupportedImageClasses() throws CloudException, InternalException {
        return getCapabilities().listSupportedImageClasses();
    }

    @Override
    @Deprecated
    public @Nonnull Iterable<MachineImageType> listSupportedImageTypes() throws CloudException, InternalException {
        return getCapabilities().listSupportedImageTypes();
    }

    @Override
    public @Nonnull String[] mapServiceAction(@Nonnull ServiceAction action) {
        return new String[0];
    }

    /**
     * Compares a machine image to the specified set of criteria to see if there is a match.
     * @param image the image to be checked
     * @param keyword a keyword on which to compare against name, description, or meta-data (or <code>null</code> if not part of the search criteria)
     * @param platform the platform to match against or <code>null</code> if any platform is acceptable
     * @param architecture the architecture to match against or <code>null</code> if any architecture is acceptable
     * @param classes the image classes to filter against or <code>null</code> if any image class is acceptable
     * @return true if the image matches the specified criteria
     */
    protected boolean matches(@Nonnull MachineImage image, @Nullable String keyword, @Nullable Platform platform, @Nullable Architecture architecture, @Nullable ImageClass ... classes) {
        if( architecture != null && !architecture.equals(image.getArchitecture()) ) {
            return false;
        }
        if( classes != null && classes.length > 0 ) {
            boolean matches = false;

            for( ImageClass cls : classes ) {
                if( cls.equals(image.getImageClass()) ) {
                    matches = true;
                    break;
                }
            }
            if( !matches ) {
                return false;
            }
        }
        if( platform != null && !platform.equals(Platform.UNKNOWN) ) {
            Platform mine = image.getPlatform();

            if( platform.isWindows() && !mine.isWindows() ) {
                return false;
            }
            if( platform.isUnix() && !mine.isUnix() ) {
                return false;
            }
            if( platform.isBsd() && !mine.isBsd() ) {
                return false;
            }
            if( platform.isLinux() && !mine.isLinux() ) {
                return false;
            }
            if( platform.equals(Platform.UNIX) ) {
                if( !mine.isUnix() ) {
                    return false;
                }
            }
            else if( !platform.equals(mine) ) {
                return false;
            }
        }
        if( keyword != null ) {
            keyword = keyword.toLowerCase();
            if( !image.getDescription().toLowerCase().contains(keyword) ) {
                if( !image.getName().toLowerCase().contains(keyword) ) {
                    if( !image.getProviderMachineImageId().toLowerCase().contains(keyword) ) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    @Override
    public @Nonnull MachineImage registerImageBundle(@Nonnull ImageCreateOptions options) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Image bundling is not currently implemented in " + getProvider().getCloudName());
    }

    @Override
    public void remove(@Nonnull String providerImageId) throws CloudException, InternalException {
        remove(providerImageId, false);
    }

    @Override
    public void removeAllImageShares(@Nonnull String providerImageId) throws CloudException, InternalException {
        // NO-OP (does not error even when not supported)
    }

    @Override
    public void removeImageShare(@Nonnull String providerImageId, @Nonnull String accountNumber) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Image sharing is not currently implemented in " + getProvider().getCloudName());
    }

    @Override
    public void removePublicShare(@Nonnull String providerImageId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Image sharing is not currently supported in " + getProvider().getCloudName());
    }

    @Override
    public @Nonnull Iterable<MachineImage> searchImages(@Nullable String accountNumber, @Nullable String keyword, @Nullable Platform platform, @Nullable Architecture architecture, @Nullable ImageClass ... imageClasses) throws CloudException, InternalException {
        ImageFilterOptions options = ImageFilterOptions.getInstance();

        if (accountNumber != null) {
            options.withAccountNumber(accountNumber);
        }
        if( keyword != null ) {
            options.matchingRegex(keyword);
        }
        if( architecture != null ) {
            options.withArchitecture(architecture);
        }
        if( platform != null ) {
            options.onPlatform(platform);
        }
        if( imageClasses == null || imageClasses.length < 1 ) {
            return listImages(options);
        }
        else if( imageClasses.length == 1 ) {
            options.withImageClass(imageClasses[0]);
            return listImages(options);
        }
        else {
            ArrayList<MachineImage> images = new ArrayList<MachineImage>();

            for( MachineImage img : listImages(options) ) {
                boolean matches = false;

                for( ImageClass cls : imageClasses ) {
                    if( img.getImageClass().equals(cls) ) {
                        matches = true;
                        break;
                    }
                }
                if( matches && options.matches(img) ) {
                    images.add(img);
                }
            }
            return images;
        }
    }

    @Override
    public @Nonnull Iterable<MachineImage> searchMachineImages(@Nullable String keyword, @Nullable Platform platform, @Nullable Architecture architecture) throws CloudException, InternalException {
        ArrayList<MachineImage> matches = new ArrayList<MachineImage>();
        for( MachineImage img : searchImages(null, keyword, platform, architecture, ImageClass.MACHINE) ) {
            matches.add(img);
        }
        for( MachineImage img : searchPublicImages(keyword, platform, architecture, ImageClass.MACHINE) ) {
            if( !matches.contains(img) ) {
                matches.add(img);
            }
        }
        return matches;
    }

    @Override
    public @Nonnull Iterable<MachineImage> searchPublicImages(@Nonnull ImageFilterOptions options) throws CloudException, InternalException {
        return Collections.emptyList();
    }

    @Override
    public @Nonnull Iterable<MachineImage> searchPublicImages(@Nullable String keyword, @Nullable Platform platform, @Nullable Architecture architecture, @Nullable ImageClass ... imageClasses) throws CloudException, InternalException {
        ImageFilterOptions options = ImageFilterOptions.getInstance();

        if( keyword != null ) {
            options.matchingRegex(keyword);
        }
        if( architecture != null ) {
            options.withArchitecture(architecture);
        }
        if( platform != null ) {
            options.onPlatform(platform);
        }
        if( imageClasses == null || imageClasses.length < 1 ) {
            return searchPublicImages(options);
        }
        else if( imageClasses.length == 1 ) {
            options.withImageClass(imageClasses[0]);
            return searchPublicImages(options);
        }
        else {
            ArrayList<MachineImage> images = new ArrayList<MachineImage>();

            for( MachineImage img : searchPublicImages(options) ) {
                boolean matches = false;

                for( ImageClass cls : imageClasses ) {
                    if( img.getImageClass().equals(cls) ) {
                        matches = true;
                        break;
                    }
                }
                if( matches && options.matches(img) ) {
                    images.add(img);
                }
            }
            return images;
        }
    }

    @Override
    public final void shareMachineImage(@Nonnull String providerImageId, @Nullable String withAccountId, boolean allow) throws CloudException, InternalException {
        if( withAccountId == null ) {
            if( allow ) {
                addPublicShare(providerImageId);
            }
            else {
                removePublicShare(providerImageId);
            }
        }
        else if( allow ) {
            addImageShare(providerImageId, withAccountId);
        }
        else {
            removeImageShare(providerImageId, withAccountId);
        }
    }

    @Override
    @Deprecated
    public boolean supportsCustomImages() throws CloudException, InternalException {
        if( getCapabilities().supportsDirectImageUpload() ) {
            return true;
        }
        for( MachineImageType type : MachineImageType.values() ) {
            if( getCapabilities().supportsImageCapture(type) ) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Deprecated
    public boolean supportsDirectImageUpload() throws CloudException, InternalException {
        return getCapabilities().supportsDirectImageUpload();
    }

    @Override
    @Deprecated
    public boolean supportsImageCapture(@Nonnull MachineImageType type) throws CloudException, InternalException {
        return getCapabilities().supportsImageCapture(type);
    }

    @Override
    @Deprecated
    public boolean supportsImageSharing() throws CloudException, InternalException {
        return getCapabilities().supportsImageSharing();
    }

    @Override
    @Deprecated
    public boolean supportsImageSharingWithPublic() throws CloudException, InternalException {
        return getCapabilities().supportsImageSharingWithPublic();
    }

    @Override
    @Deprecated
    public boolean supportsPublicLibrary(@Nonnull ImageClass cls) throws CloudException, InternalException {
        return getCapabilities().supportsPublicLibrary(cls);
    }

    @Override
    public void updateTags(@Nonnull String imageId, @Nonnull Tag ... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void updateTags(@Nonnull String[] imageIds, @Nonnull Tag ... tags) throws CloudException, InternalException {
        for( String id : imageIds ) {
            updateTags(id, tags);
        }
    }

    @Override
    public void removeTags(@Nonnull String imageId, @Nonnull Tag ... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void removeTags(@Nonnull String[] imageIds, @Nonnull Tag ... tags) throws CloudException, InternalException {
        for( String id : imageIds ) {
            removeTags(id, tags);
        }
    }

    @Override
    public void setTags( @Nonnull String imageId, @Nonnull Tag... tags ) throws CloudException, InternalException {
        setTags(new String[]{imageId}, tags);
    }

    @Override
    public void setTags( @Nonnull String[] imageIds, @Nonnull Tag... tags ) throws CloudException, InternalException {
        for( String id : imageIds ) {

            Tag[] collectionForDelete = TagUtils.getTagsForDelete(getImage(id).getTags(), tags);

            if( collectionForDelete.length != 0 ) {
                removeTags(id, collectionForDelete);
            }

            updateTags(id, tags);
        }
    }
}
