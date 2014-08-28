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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.dasein.cloud.Tag;
import org.dasein.cloud.Taggable;
import org.dasein.cloud.VisibleScope;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A machine image is an image, template, or similar concept from which virtual machines may be instantiated. The term
 * &quot;machine image&quot; is a throwback to when Dasein Cloud supported only machine images. It now supports three
 * kinds of images ({@link ImageClass}):
 * <ul>
 *     <li>machine</li>
 *     <li>kernal</li>
 *     <li>ramdisk</li>
 * </ul>
 * <p>
 *     A cloud may indicate which kinds of images are necessary for the instantiation of a virtual machine via the
 *     meta-data call {@link VirtualMachineSupport#identifyImageRequirement(ImageClass)}. In practice, just about every
 *     cloud (if not every cloud) requires a &quot;machine&quot; image and a few clouds optionally allow you to specify
 *     &quot;ramdisk&quot; and/or &quot;kernel&quot; images.
 * </p>
 * @author George Reese
 * @version 2013.04 added documentation and the idea of data center constraints
 * @since unknown
 */
public class MachineImage implements Taggable {
    /**
     * Constructs a minimally viable image object of the specified image class. Because no image format is specified,
     * the type for this image will be {@link MachineImageType#VOLUME}.
     * @param ownerId the account number for the account that owns the image
     * @param regionId the region ID with which the image is associated
     * @param imageId the ID for the newly constructed image
     * @param imageClass the image class of the image
     * @param state the current state for the image
     * @param name the name of the image
     * @param description a long description of the function of the image
     * @param architecture the architecture on which this image is based
     * @param platform the platform built into the image
     * @return an image matching the specified parameters
     */
    static public @Nonnull MachineImage getImageInstance(@Nonnull String ownerId, @Nonnull String regionId, @Nonnull String imageId, @Nonnull ImageClass imageClass, @Nonnull MachineImageState state,  @Nonnull String name, @Nonnull String description, @Nonnull Architecture architecture, @Nonnull Platform platform) {
        @SuppressWarnings("deprecation") MachineImage image = new MachineImage();

        image.providerOwnerId = ownerId;
        image.providerRegionId = regionId;
        image.providerMachineImageId = imageId;
        image.name = name;
        image.description = description;
        image.architecture = architecture;
        image.platform = platform;
        image.currentState = state;
        image.imageClass = imageClass;
        image.type = MachineImageType.VOLUME;
        image.creationTimestamp = 0L;
        image.software = "";
        return image;
    }

    /**
     * Constructs a minimally viable image object of the specified image class of the {@link MachineImageType#STORAGE} format.
     * @param ownerId the account number for the account that owns the image
     * @param regionId the region ID with which the image is associated
     * @param imageId the ID for the newly constructed image
     * @param imageClass the image class of the image
     * @param state the current state for the image
     * @param name the name of the image
     * @param description a long description of the function of the image
     * @param architecture the architecture on which this image is based
     * @param platform the platform built into the image
     * @param format the storage format for {@link MachineImageType#STORAGE} images
     * @return an image matching the specified parameters
     */
    static public @Nonnull MachineImage getImageInstance(@Nonnull String ownerId, @Nonnull String regionId, @Nonnull String imageId, @Nonnull ImageClass imageClass, @Nonnull MachineImageState state,  @Nonnull String name, @Nonnull String description, @Nonnull Architecture architecture, @Nonnull Platform platform, @Nonnull MachineImageFormat format) {
        @SuppressWarnings("deprecation") MachineImage image = new MachineImage();

        image.providerOwnerId = ownerId;
        image.providerRegionId = regionId;
        image.providerMachineImageId = imageId;
        image.name = name;
        image.description = description;
        image.architecture = architecture;
        image.platform = platform;
        image.currentState = state;
        image.imageClass = imageClass;
        image.type = MachineImageType.STORAGE;
        image.storageFormat = format;
        image.creationTimestamp = 0L;
        image.software = "";
        return image;
    }

    /**
     * Constructs a minimally viable machine image object of the specified image class. Because no image format is specified,
     * the type for this image will be {@link MachineImageType#VOLUME}.
     * @param ownerId the account number for the account that owns the image
     * @param regionId the region ID with which the image is associated
     * @param imageId the ID for the newly constructed image
     * @param state the current state for the image
     * @param name the name of the image
     * @param description a long description of the function of the image
     * @param architecture the architecture on which this image is based
     * @param platform the platform built into the image
     * @return an image matching the specified parameters
     */
    static public @Nonnull MachineImage getMachineImageInstance(@Nonnull String ownerId, @Nonnull String regionId, @Nonnull String imageId,  @Nonnull MachineImageState state,  @Nonnull String name, @Nonnull String description, @Nonnull Architecture architecture, @Nonnull Platform platform) {
        @SuppressWarnings("deprecation") MachineImage image = new MachineImage();

        image.providerOwnerId = ownerId;
        image.providerRegionId = regionId;
        image.providerMachineImageId = imageId;
        image.name = name;
        image.description = description;
        image.architecture = architecture;
        image.platform = platform;
        image.currentState = state;
        image.imageClass = ImageClass.MACHINE;
        image.type = MachineImageType.VOLUME;
        image.creationTimestamp = 0L;
        image.software = "";
        return image;
    }

    /**
     * Constructs a minimally viable machine image object of the specified image class of the {@link MachineImageType#STORAGE} format.
     * @param ownerId the account number for the account that owns the image
     * @param regionId the region ID with which the image is associated
     * @param imageId the ID for the newly constructed image
     * @param state the current state for the image
     * @param name the name of the image
     * @param description a long description of the function of the image
     * @param architecture the architecture on which this image is based
     * @param platform the platform built into the image
     * @param format the storage format for {@link MachineImageType#STORAGE} images
     * @return an image matching the specified parameters
     */
    static public @Nonnull MachineImage getMachineImageInstance(@Nonnull String ownerId, @Nonnull String regionId, @Nonnull String imageId,  @Nonnull MachineImageState state,  @Nonnull String name, @Nonnull String description, @Nonnull Architecture architecture, @Nonnull Platform platform, @Nonnull MachineImageFormat format) {
        @SuppressWarnings("deprecation") MachineImage image = new MachineImage();

        image.providerOwnerId = ownerId;
        image.providerRegionId = regionId;
        image.providerMachineImageId = imageId;
        image.name = name;
        image.description = description;
        image.architecture = architecture;
        image.platform = platform;
        image.currentState = state;
        image.imageClass = ImageClass.MACHINE;
        image.type = MachineImageType.STORAGE;
        image.storageFormat = format;
        image.creationTimestamp = 0L;
        image.software = "";
        return image;
    }

    /**
     * Constructs a minimally viable image object of the specified image class of the {@link MachineImageType#STORAGE} format.
     * @param ownerId the account number for the account that owns the image
     * @param regionId the region ID with which the image is associated
     * @param imageId the ID for the newly constructed image
     * @param imageClass the image class of the image
     * @param state the current state for the image
     * @param name the name of the image
     * @param description a long description of the function of the image
     * @param architecture the architecture on which this image is based
     * @param platform the platform built into the image
     * @param format the storage format for {@link MachineImageType#STORAGE} images
     * @param visibleScope the scope defining how visible the image is in the cloud
     * @return an image matching the specified parameters
     */
    static public @Nonnull MachineImage getImageInstance(@Nonnull String ownerId, @Nonnull String regionId, @Nonnull String imageId, @Nonnull ImageClass imageClass, @Nonnull MachineImageState state,  @Nonnull String name, @Nonnull String description, @Nonnull Architecture architecture, @Nonnull Platform platform, @Nonnull MachineImageFormat format, @Nullable VisibleScope visibleScope) {
        @SuppressWarnings("deprecation") MachineImage image = new MachineImage();

        image.providerOwnerId = ownerId;
        image.providerRegionId = regionId;
        image.providerMachineImageId = imageId;
        image.name = name;
        image.description = description;
        image.architecture = architecture;
        image.platform = platform;
        image.currentState = state;
        image.imageClass = imageClass;
        image.type = MachineImageType.STORAGE;
        image.storageFormat = format;
        image.creationTimestamp = 0L;
        image.software = "";
        image.visibleScope = visibleScope;
        return image;
    }

    private Architecture       architecture;
    private long               creationTimestamp;
    private MachineImageState  currentState;
    private String             description;
    private ImageClass         imageClass;
    private String             kernelImageId;
    private String             name;
    private Platform           platform;
    private String             providerDataCenterId;
    private String             providerMachineImageId;
    private String             providerOwnerId;
    private String             providerRegionId;
    private String             software;
    private MachineImageFormat storageFormat;
    private Map<String,String> tags;
    private MachineImageType   type;
    private VisibleScope       visibleScope;
    private Collection<MachineImageVolume> volumes;

    /**
     * Constructs an empty machine image.
     * @deprecated Use the static factory methods
     */
    public MachineImage() { }

    /**
     * Indicates the kernel image with which this machine image (only for machine images) is associated.
     * @param kernelImageId the image ID of the kernel image with which this machine image is associated
     * @return this
     */
    public @Nonnull MachineImage associatedWith(@Nonnull String kernelImageId) {
        kernelImageId = kernelImageId;
        return this;
    }

    /**
     * Indicates the data center in which virtual machine instances created from this image must be launched for
     * images that are data center constrained.
     * @param dataCenterId the ID of the data center to which this image is constrained
     * @return this
     */
    public @Nonnull MachineImage constrainedTo(@Nonnull String dataCenterId) {
        this.providerDataCenterId = dataCenterId;
        return this;
    }

    /**
     * Indicates the Unix timestamp when this image was created.
     * @param timestamp the Unix timestamp in milliseconds when this image was created
     * @return this
     */
    public @Nonnull MachineImage createdAt(@Nonnegative long timestamp) {
        creationTimestamp = timestamp;
        return this;
    }

    @Override
    public boolean equals(Object ob) {
        if( ob == null ) {
            return false;
        }
        if( ob == this ) {
            return true;
        }
        if( ob instanceof MachineImage ) {
            MachineImage image = (MachineImage)ob;

            return providerRegionId.equals(image.providerRegionId) && providerMachineImageId.equals(image.providerMachineImageId);
        }
        return false;
    }

    /**
     * @return the architecture associated with this image
     */
    public @Nonnull Architecture getArchitecture() {
        return architecture;
    }

    /**
     * Note: this value will return 0L for clouds that don't track creation times.
     * @return a Unix timestamp in milliseconds indicating when this image was initially created
     */
    public @Nonnegative long getCreationTimestamp() {
        return creationTimestamp;
    }

    /**
     * @return the current state of the image as described by the cloud provider
     */
    public @Nonnull MachineImageState getCurrentState() {
        return currentState;
    }

    /**
     * @return a long description describing this image
     */
    public @Nonnull String getDescription() {
        return description;
    }

    /**
     * @return the image class of this image
     */
    public @Nonnull ImageClass getImageClass() {
        return imageClass;
    }

    /**
     * @return the kernel image associated with this machine image (this is a machine image if it is associated with a kernel image)
     */
    public @Nullable String getKernelImageId() {
        return kernelImageId;
    }

    /**
     * @return the name of the image
     */
    public @Nonnull String getName() {
        return name;
    }

    /**
     * @return the platform embedded in the image
     */
    public @Nonnull Platform getPlatform() {
        return platform;
    }

    /**
     * In most clouds, images/templates are not constrained at the data center/availability zone level. In those cases
     * (for example, AWS), this value will always be <code>null</code>. If, however, the images/templates you use
     * can only be launched into a single data center/availability zone, this will return the ID of that data
     * center/availability zone.
     * @return the data center into which you can launch VMs (<code>null</code> means you can launch into any data center)
     */
    public @Nullable String getProviderDataCenterId() {
        return providerDataCenterId;
    }

    /**
     * @return the unique ID for this image as it is identified with the cloud provider
     */
    public @Nonnull String getProviderMachineImageId() {
        return providerMachineImageId;
    }

    /**
     * @return the account number for the account that owns this image
     */
    public @Nonnull String getProviderOwnerId() {
        return providerOwnerId;
    }

    /**
     * @return the provider ID of the region to which this image is constrained
     */
    public @Nonnull String getProviderRegionId() {
        return providerRegionId;
    }

    /**
     * @return a string indicating the software embedded in this image (an empty string indicates no specific software)
     */
    public @Nonnull String getSoftware() {
        return software;
    }

    /**
     * @return for object store-backed images, the format in which the object(s) are stored
     */
    public @Nullable MachineImageFormat getStorageFormat() {
        return storageFormat;
    }

    /**
     * Fetches the value of the specified tag key.
     * @param tag the key of the tag to be fetched
     * @return the value associated with the specified key, if any
     */
    public @Nullable Object getTag(@Nonnull String tag) {
        return getTags().get(tag);
    }

    @Override
    public @Nonnull Map<String,String> getTags() {
        if( tags == null ) {
            tags = new HashMap<String,String>();
        }
        return tags;
    }

    /**
     * There are two types of images in Dasein Cloud: storage images and volume images. A storage image is one that
     * is accessible via objects in object storage. You can directly download and optionally upload the image into
     * this object storage. Volume images, on the other hand, are sitting on block storage devices in the cloud.
     * @return the type of image represented by this image
     */
    public @Nonnull MachineImageType getType() {
        return type;
    }

    public void setVisibleScope(VisibleScope visibleScope){
        this.visibleScope = visibleScope;
    }

    public VisibleScope getVisibleScope(){
        return this.visibleScope;
    }

    @Override
    public int hashCode() {
        return (providerOwnerId + providerRegionId + providerMachineImageId).hashCode();
    }

    @Override
    public void setTag(@Nonnull String key, @Nonnull String value) {
        getTags().put(key, value);
    }

    /**
     * Clears out any currently set tags and replaces them with the specified list.
     * @param properties the list of tag values to be set
     */
    public void setTags(Map<String,String> properties) {
        getTags().clear();
        getTags().putAll(properties);
    }

    public @Nullable Collection<MachineImageVolume> getVolumes() {
        return volumes;
    }

    public void setVolumes(@Nullable Collection<MachineImageVolume> volumes) {
        this.volumes = volumes;
    }

    @Override
    public String toString() {
        return (name + " [" + providerMachineImageId + "]");
    }

    /**
     * Indicates the software embedded in this image.
     * @param software the software embedded in this image
     * @return this
     */
    public @Nonnull MachineImage withSoftware(@Nonnull String software) {
        this.software = software;
        return this;
    }

    /********************************** DEPRECATED METHODS *******************************************/

    /**
     * Adds a tag to the current list of tags.
     * @param t the tag to be added
     * @deprecated Use {@link #setTag(String, String)}
     */
    public void addTag(Tag t) {
        setTag(t.getKey(), t.getValue());
    }

    /**
     * Adds a tag to the current list of tags.
     * @param key the key of the tag
     * @param value the value of the tag
     * @deprecated Use {@link #setTag(String, String)}
     */
    public void addTag(String key, String value) {
        setTag(key, value);
    }

    /**
     * Sets the architecture associated with this image.
     * @param architecture the architecture associated with this image
     * @deprecated Use the static factory methods
     */
    public void setArchitecture(@Nonnull Architecture architecture) {
        this.architecture = architecture;
    }

    /**
     * Sets the timestamp when this image was created.
     * @param creationTimestamp the Unix timestamp in milliseconds when the image was created
     * @deprecated Use the static factory methods
     */
    public void setCreationTimestamp(@Nonnegative long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    /**
     * Sets the current state of the image.
     * @param currentState the current state of the image
     * @deprecated  Use the static factory methods
     */
    public void setCurrentState(@Nonnull MachineImageState currentState) {
        this.currentState = currentState;
    }

    /**
     * Sets the description for the image.
     * @param description the image description
     * @deprecated Use the static factory methods
     */
    public void setDescription(@Nonnull String description) {
        this.description = description;
    }

    /**
     * Sets the image class for this image.
     * @param imageClass the image class of the image
     * @deprecated Use the static factory methods
     */
    public void setImageClass(@Nonnull ImageClass imageClass) {
        this.imageClass = imageClass;
    }

    /**
     * Sets the kernel image associated with this machine image.
     * @param kernelImageId the kernel image associated with this image
     */
    public void setKernelImageId(@Nonnull String kernelImageId) {
        this.kernelImageId = kernelImageId;
    }

    /**
     * Sets the name of the image.
     * @param name the image name
     * @deprecated  Use the static factory methods
     */
    public void setName(@Nonnull String name) {
        this.name = name;
    }

    /**
     * Sets the platform for the image.
     * @param platform the platform for the image
     * @deprecated Use the static factory methods
     */
    public void setPlatform(@Nonnull Platform platform) {
        this.platform = platform;
    }

    /**
     * Sets the unique ID of this image.
     * @param providerMachineImageId the unique ID of this image
     * @deprecated Use the static factory methods
     */
    public void setProviderMachineImageId(@Nonnull String providerMachineImageId) {
        this.providerMachineImageId = providerMachineImageId;
    }

    /**
     * Sets the account number that owns this image.
     * @param providerOwnerId the owner of this image
     * @deprecated Use the static factory methods
     */
    public void setProviderOwnerId(@Nonnull String providerOwnerId) {
        this.providerOwnerId = providerOwnerId;
    }

    /**
     * Sets the region ID of the region associated with this image.
     * @param providerRegionId the region ID for this image
     * @deprecated Use the static factory methods
     */
    public void setProviderRegionId(@Nonnull String providerRegionId) {
        this.providerRegionId = providerRegionId;
    }

    /**
     * Sets the software embedded in this image.
     * @param software the software embedded in this image
     * @deprecated Use the static factory methods
     */
    public void setSoftware(@Nonnull String software) {
        this.software = software;
    }

    /**
     * Sets the storage format for this storage-backed image.
     * @param storageFormat the storage format for the image
     * @deprecated Use the static factory methods
     */
    public void setStorageFormat(@Nonnull MachineImageFormat storageFormat) {
        this.storageFormat = storageFormat;
    }

    /**
     * Sets the type of image.
     * @param type the type of image
     * @deprecated Use the static factory methods
     */
    public void setType(@Nonnull MachineImageType type) {
        this.type = type;
    }
}
