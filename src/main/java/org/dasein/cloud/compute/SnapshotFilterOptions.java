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

    /**
     * Constructs a filter for any kind of snapshots.
     * @param matchesAny <code>true</code> if it is sufficient that just one of the criteria are matched, false if all are needed to be matched
     * @return a simple filter for snapshots that does no filtering unless other options are added
     */
    static public @Nonnull SnapshotFilterOptions getInstance(boolean matchesAny) {
        SnapshotFilterOptions options = new SnapshotFilterOptions();

        options.matchesAny = matchesAny;
        return options;
    }

    /**
     * Constructs a regex filter on the specified regular expression.
     * @param regex the regular expression on which to filter
     * @return a filter for snapshots that match the specified regular expression
     */
    static public @Nonnull SnapshotFilterOptions getInstance(@Nonnull String regex) {
        SnapshotFilterOptions options = new SnapshotFilterOptions();

        options.matchesAny = false;
        options.regex = regex;
        return options;
    }

    /**
     * Constructs a regex filter on the specified regular expression.
     * @param matchesAny <code>true</code> if it is sufficient that just one of the criteria are matched, false if all are needed to be matched
     * @param regex the regular expression on which to filter
     * @return a filter for snapshots that match the specified regular expression
     */
    static public @Nonnull SnapshotFilterOptions getInstance(boolean matchesAny, @Nonnull String regex) {
        SnapshotFilterOptions options = new SnapshotFilterOptions();

        options.matchesAny = matchesAny;
        options.regex = regex;
        return options;
    }

    private String             accountNumber;
    private boolean            matchesAny;
    private String             regex;
    private Map<String,String> tags;

    private SnapshotFilterOptions() {}

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
        if( accountNumber != null && !accountNumber.equals(snapshot.getOwner()) ) {
            return false;
        }
        if( regex != null ) {
            boolean matches = (snapshot.getName().matches(regex) || snapshot.getDescription().matches(regex));

            if( !matches ) {
                for( Map.Entry<String,String> tag : snapshot.getTags().entrySet() ) {
                    String value = tag.getValue();

                    if( value != null && value.matches(regex) ) {
                        matches = true;
                        break;
                    }
                }
            }
            if( !matches && !matchesAny ) {
                return false;
            }
            else if( matches && matchesAny ) {
                return true;
            }
        }
        if( tags != null && !tags.isEmpty() ) {
            if( !CloudProvider.matchesTags(snapshot.getTags(), snapshot.getName(), snapshot.getDescription(), tags) ) {
                if( !matchesAny ) {
                    return false;
                }
            }
            else if( matchesAny ) {
                return true;
            }
        }
        return !matchesAny;
    }

    /**
     * Indicates that the criteria associated with this filter must match all set criteria.
     * @return this
     */
    public @Nonnull SnapshotFilterOptions matchingAll() {
        this.matchesAny = false;
        return this;
    }

    /**
     * Indicates that the criteria associated with this filter must match just one single criterion.
     * @return this
     */
    public @Nonnull SnapshotFilterOptions matchingAny() {
        this.matchesAny = true;
        return this;
    }

    /**
     * Adds a regex to the set of filtering options. This regular expression is a standard Java regular expression
     * matches against the snapshot name, description, and meta-data tags.
     * @param regex the Java regular expression string to match against
     * @return this
     */
    public @Nonnull SnapshotFilterOptions matchingRegex(@Nonnull String regex) {
        this.regex = regex;
        return this;
    }

    /**
     * Sets an account number to the options on which snapshot filtering should be done.
     * @param accountNumber the account number to filter against
     * @return this
     */
    public @Nonnull SnapshotFilterOptions withAccountNumber(@Nonnull String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
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
