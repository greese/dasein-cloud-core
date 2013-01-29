package org.dasein.cloud.compute;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * Options for filtering machine images when querying the cloud provider.
 * <p>Created by Cameron Stokes: 01/28/13 08:41 AM</p>
 * @author Cameron Stokes
 * @version 2013.04 initial version
 * @version 2013.04 renamed to ImageFilterOptions to be consistent with multi-image type naming
 * @since 2013.04
 */
public class ImageFilterOptions {

    /**
     * Constructs a filter for any kind of images
     * @return a simple filter for images that does no filtering unless other options are added
     */
    static public @Nonnull ImageFilterOptions getInstance() {
        return new ImageFilterOptions(null);
    }

    /**
     * Constructs a filter for images of the specified image class
     * @param cls the desired image class
     * @return a filter for images that match the specified image class
     */
    static public @Nonnull ImageFilterOptions getInstance(ImageClass cls) {
        return new ImageFilterOptions(cls);
    }

    private String accountNumber;
    private ImageClass imageClass;
    private Map<String,String> tags;

    private ImageFilterOptions(@Nullable ImageClass cls) {
        imageClass = cls;
    }

    /**
     * @return an account number on which filtering should be done, or <code>null</code> to not filter on account number
     */
    public @Nullable String getAccountNumber() {
        return accountNumber;
    }

    /**
     * @return the image class on which filtering should be done, or <code>null</code> to not filter on image class
     */
    public @Nullable ImageClass getImageClass() {
        return imageClass;
    }

    /**
     * @return the tags on which filtering should be done, or <code>null</code> if no filtering on tags is to be done
     */
    public @Nullable Map<String, String> getTags() {
        return tags;
    }

    /**
     * Sets an account number to the options on which image filtering should be done.
     * @param accountNumber the account number to filter against
     * @return this
     */
    public @Nonnull ImageFilterOptions withAccountNumber(@Nonnull String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    /**
     * Sets an image class on which filtering should be done.
     * @param imageClass the image class to filter against
     * @return this
     */
    public @Nonnull ImageFilterOptions withImageClass(@Nonnull ImageClass imageClass) {
        this.imageClass = imageClass;
        return this;
    }

    /**
     * Sets the tags to filter against.
     * @param tags the tags to filter against
     * @return this
     */
    public @Nonnull ImageFilterOptions withTags(@Nonnull Map<String, String> tags) {
        this.tags = tags;
        return this;
    }

}
