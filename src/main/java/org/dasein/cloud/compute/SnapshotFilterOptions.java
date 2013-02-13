package org.dasein.cloud.compute;

import org.dasein.cloud.CloudProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * Options for filtering snapshots when querying the cloud provider.
 * <p>Created by Cameron Stokes: 02/12/13</p>
 * @author Cameron Stokes
 * @version 2013.04 initial version
 * @since 2013.04
 */
public class SnapshotFilterOptions {

    /**
     * Constructs an empty set of filtering options.
     * @return an empty filtering options objects
     */
    static public @Nonnull SnapshotFilterOptions getInstance() {
        return new SnapshotFilterOptions();
    }

    private Map<String,String> tags;

    /**
     * @return the tags, if any, on which filtering should be done (<code>null</code> means don't filter on tags)
     */
    public @Nullable Map<String, String> getTags() {
        return tags;
    }

    /**
     * Compares a snapshot against these filter options to see if it matches.
     * @param snapshot the snapshot to be compared
     * @return <code>true</code> if the snapshot matches the filter criteria
     */
    public boolean matches(@Nonnull Snapshot snapshot) {
        if( tags != null && !tags.isEmpty() ) {
            if( !CloudProvider.matchesTags(snapshot.getTags(), snapshot.getName(), snapshot.getDescription(), tags) ) {
                return false;
            }
        }
        return true;
    }

    /**
     * Builds filtering options that will force filtering on the specified meta-data tags.
     * @param tags the meta-data tags on which to filter
     * @return this
     */
    public @Nonnull SnapshotFilterOptions withTags(@Nonnull Map<String, String> tags) {
        this.tags = tags;
        return this;
    }

}
