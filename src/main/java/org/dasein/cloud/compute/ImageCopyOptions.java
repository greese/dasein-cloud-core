package org.dasein.cloud.compute;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Options to copy a machine image from one region to another.
 *
 * @author Bulat Badretdinov
 * @since 2014.06
 */
public class ImageCopyOptions {

    public static @Nonnull ImageCopyOptions getInstance( @Nonnull String targetRegionId, @Nonnull String providerImageId, @Nullable String name, @Nullable String description ) {
        ImageCopyOptions options = new ImageCopyOptions();
        options.targetRegionId = targetRegionId;
        options.providerImageId = providerImageId;
        options.name = name;
        options.description = description;
        return options;
    }

    private String targetRegionId;
    private String providerImageId;
    private String name;
    private String description;

    private ImageCopyOptions() {
    }

    /**
     * The name of the region where the image will be copied to.
     *
     * @return source region ID
     */
    public @Nonnull String getTargetRegionId() {
        return targetRegionId;
    }

    /**
     * the cloud provider ID uniquely identifying the image to copy.
     *
     * @return ID of the image to copy
     */
    public @Nonnull String getProviderImageId() {
        return providerImageId;
    }

    /**
     * The desired name of the new image in the destination region.
     *
     * @return desired name of copied image
     */
    public @Nullable String getName() {
        return name;
    }

    /**
     * The desired description of the new image in the destination region.
     *
     * @return desired description of copied image
     */
    public @Nullable String getDescription() {
        return description;
    }
}
