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

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Options for capturing new machine images from a running virtual machine.
 * <p>Created by George Reese: 11/14/12 3:48 PM</p>
 * @author George Reese
 * @version 2013.01 initial version
 * @since 2013.01
 */
public class ImageCreateOptions {
    static public @Nonnull ImageCreateOptions getInstance(@Nonnull VirtualMachine fromVirtualMachine, @Nonnull String name, @Nonnull String description, @Nullable Boolean reboot) {
        ImageCreateOptions options = new ImageCreateOptions();

        options.virtualMachineId = fromVirtualMachine.getProviderVirtualMachineId();
        options.platform = fromVirtualMachine.getPlatform();
        options.name = name;
        options.description = description;
        options.reboot = reboot;
        return options;
    }

    static public @Nonnull ImageCreateOptions getInstance(@Nonnull VirtualMachine fromVirtualMachine, @Nonnull String name, @Nonnull String description) {
      ImageCreateOptions options = new ImageCreateOptions();

      options.virtualMachineId = fromVirtualMachine.getProviderVirtualMachineId();
      options.platform = fromVirtualMachine.getPlatform();
      options.name = name;
      options.description = description;
      return options;
    }

    static public @Nonnull ImageCreateOptions getInstance(@Nonnull MachineImageFormat bundleFormat, @Nonnull String bundleLocation, @Nonnull Platform platform, @Nonnull String name, @Nonnull String description) {
        ImageCreateOptions options = new ImageCreateOptions();

        options.bundleFormat = bundleFormat;
        options.bundleLocation = bundleLocation;
        options.name = name;
        options.description = description;
        options.platform = platform;
        return options;
    }

    static public @Nonnull ImageCreateOptions getInstance(@Nonnull MachineImageFormat bundleFormat, @Nonnull InputStream bundleSource, @Nonnull Platform platform, @Nonnull String name, @Nonnull String description) {
        ImageCreateOptions options = new ImageCreateOptions();

        options.bundleFormat = bundleFormat;
        options.bundleSource = bundleSource;
        options.name = name;
        options.description = description;
        options.platform = platform;
        return options;
    }

    private MachineImageFormat bundleFormat;
    private String             bundleLocation;
    private InputStream        bundleSource;
    private String             description;
    private Map<String,Object> metaData;
    private String             name;
    private Platform           platform;
    private String             software;
    private String             virtualMachineId;
    private Boolean            reboot; // to reboot the machine or not while taking the image

    private ImageCreateOptions() { }

    /**
     * Captures a machine image from a virtual machine or registers one from a bundle with the specified cloud
     * based on the options in this object.
     * @param provider the cloud provider object for the cloud in which the image is going to be created
     * @return the unique ID of the newly created image
     * @throws CloudException an error occurred with the cloud provider while creating the image
     * @throws InternalException an error occurred within Dasein Cloud while preparing the API call
     * @throws OperationNotSupportedException images are not supported in the target cloud
     */
    public @Nonnull String build(@Nonnull CloudProvider provider) throws CloudException, InternalException {
        ComputeServices services = provider.getComputeServices();

        if( services == null ) {
            throw new OperationNotSupportedException(provider.getCloudName() + " does not support compute services");
        }
        MachineImageSupport support = services.getImageSupport();

        if( support == null ) {
            throw new OperationNotSupportedException(provider.getCloudName() + " does not support images");
        }
        if( virtualMachineId != null ) {
            return support.captureImage(this).getProviderMachineImageId();
        }
        else {
            return support.registerImageBundle(this).getProviderMachineImageId();
        }
    }
    /**
     * @return the format of the file(s) found at {@link #getBundleLocation()}.
     */
    public @Nullable MachineImageFormat getBundleFormat() {
        return bundleFormat;
    }

    /**
     * @return the location of the image bundle or its manifest, depending on the format
     */
    public @Nullable String getBundleLocation() {
        return bundleLocation;
    }

    /**
     * @return the source of the image for direct upload
     */
    public InputStream getBundleSource() {
        return bundleSource;
    }

    /**
     * @return a free-form string describing any software bundled into this image
     */
    public @Nullable String getBundledSoftware() {
        return software;
    }

    /**
     * @return a description of the function of the machine image
     */
    public @Nonnull String getDescription() {
        return description;
    }

    /**
     * @return any extra meta-data to assign to the new image
     */
    public @Nonnull Map<String,Object> getMetaData() {
        return (metaData == null ? new HashMap<String, Object>() : metaData);
    }

    /**
     * @return the name of the newly created image
     */
    public @Nonnull String getName() {
        return name;
    }

    /**
     * @return the platform to associate with the new image (by default, the platform of the source VM)
     */
    public @Nonnull Platform getPlatform() {
        return platform;
    }

    /**
     * @return the virtual machine from which the new image should be captured
     */
    public @Nullable String getVirtualMachineId() {
        return virtualMachineId;
    }

    /**
     * @return the whether the instance should reboot while taking the image or not (true/false)
     */
    public @Nullable Boolean getReboot() {
      return reboot;
    }

    /**
     * Specifies an alternate platform to store with the new image. Very few clouds will actually honor this value.
     * @param platform the alternate platform to associate with the image
     * @return this
     */
    public @Nonnull ImageCreateOptions withAlternatePlatform(@Nonnull Platform platform) {
        this.platform = platform;
        return this;
    }

    /**
     * Specifies the software, if any, bundled into this image.
     * @param software the software bundled into the image
     * @return this
     */
    public @Nonnull ImageCreateOptions withBundledSoftware(@Nonnull String software) {
        this.software = software;
        return this;
    }

    /**
     * Specifies any meta-data to be associated with the image when it is created. This method is
     * accretive, meaning that it adds to any existing meta-data (or replaces an existing key). Though Dasein Cloud
     * allows the ability to retain type in meta-data, the reality is that most clouds will convert values to strings.
     * @param key the key of the meta-data entry
     * @param value the value for the meta-data entry (this will probably become a {@link java.lang.String} in most clouds)
     * @return this
     */
    public @Nonnull ImageCreateOptions withMetaData(@Nonnull String key, @Nonnull Object value) {
        if( metaData == null ) {
            metaData = new HashMap<String, Object>();
        }
        metaData.put(key, value);
        return this;
    }
}
