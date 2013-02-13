package org.dasein.cloud.compute;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * Options for filtering volumes when querying the cloud provider.
 * <p>Created by Cameron Stokes: 02/12/13</p>
 * @author Cameron Stokes
 * @version 2013.04 initial version
 * @since 2013.04
 */
public class VolumeFilterOptions {

    /**
     * Constructs an empty set of filtering options that will force match against any volume by default.
     * @return an empty filtering options objects
     */
    static public @Nonnull VolumeFilterOptions getInstance() {
        return new VolumeFilterOptions();
    }

    private Map<String,String> tags;

    /**
     * @return the tags, if any, on which filtering should be done (<code>null</code> means don't filter on tags)
     */
    public @Nullable Map<String, String> getTags() {
        return tags;
    }

    /**
     * Builds filtering options that will force filtering on the specified meta-data tags.
     * @param tags the meta-data tags on which to filter
     * @return this
     */
    public @Nonnull VolumeFilterOptions withTags(@Nonnull Map<String, String> tags) {
        this.tags = tags;
        return this;
    }

}
