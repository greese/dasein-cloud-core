package org.dasein.cloud.compute;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * Options for filtering virtual machines when querying the cloud provider.
 * <p>Created by Cameron Stokes: 01/27/13 09:41 AM</p>
 *
 * @author Cameron Stokes
 */
public class VMFilterOptions {

    private VMFilterOptions() {}

    static public @Nonnull VMFilterOptions getInstance() {
        return new VMFilterOptions();
    }

    private Map<String,String> tags;

    public Map<String, String> getTags() {
        return tags;
    }

    public @Nonnull VMFilterOptions withTags(@Nonnull Map<String, String> tags) {
        this.tags = tags;
        return this;
    }

}
