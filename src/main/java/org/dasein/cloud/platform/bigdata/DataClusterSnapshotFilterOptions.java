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

package org.dasein.cloud.platform.bigdata;

import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.compute.Snapshot;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * Options for filtering snapshots when querying the cloud provider.
 * <p>Created by George Reese: 2/9/14 1:26 PM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 initial version (issue #103)
 */
public class DataClusterSnapshotFilterOptions {
    /**
     * Constructs an empty set of filtering options.
     * @return an empty filtering options objects
     */
    static public @Nonnull DataClusterSnapshotFilterOptions getInstance() {
        return new DataClusterSnapshotFilterOptions();
    }

    /**
     * Constructs a filter for any kind of data cluster snapshots.
     * @param matchesAny <code>true</code> if it is sufficient that just one of the criteria are matched, false if all are needed to be matched
     * @return a simple filter for snapshots that does no filtering unless other options are added
     */
    static public @Nonnull DataClusterSnapshotFilterOptions getInstance(boolean matchesAny) {
        DataClusterSnapshotFilterOptions options = new DataClusterSnapshotFilterOptions();

        options.matchesAny = matchesAny;
        return options;
    }

    /**
     * Constructs a regex filter on the specified regular expression.
     * @param regex the regular expression on which to filter
     * @return a filter for snapshots that match the specified regular expression
     */
    static public @Nonnull DataClusterSnapshotFilterOptions getInstance(@Nonnull String regex) {
        DataClusterSnapshotFilterOptions options = new DataClusterSnapshotFilterOptions();

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
    static public @Nonnull DataClusterSnapshotFilterOptions getInstance(boolean matchesAny, @Nonnull String regex) {
        DataClusterSnapshotFilterOptions options = new DataClusterSnapshotFilterOptions();

        options.matchesAny = matchesAny;
        options.regex = regex;
        return options;
    }

    private String             accountNumber;
    private boolean            matchesAny;
    private String             regex;
    private Map<String,String> tags;

    private DataClusterSnapshotFilterOptions() { }

    /**
     * @return an account number on which filtering should be done, or <code>null</code> to not filter on account number
     */
    public @Nullable String getAccountNumber() {
        return accountNumber;
    }

    /**
     * @return a regular expression to match against an image name, description, or tag values.
     */
    public @Nullable String getRegex() {
        return regex;
    }

    /**
     * @return the tags, if any, on which filtering should be done (<code>null</code> means don't filter on tags)
     */
    public @Nullable Map<String, String> getTags() {
        return tags;
    }

    /**
     * Indicates whether there are any criteria associated with these options.
     * @return <code>true</code> if this filter options object has any criteria associated with it
     */
    public boolean hasCriteria() {
        return ((tags != null && !tags.isEmpty()) || regex != null || accountNumber != null);
    }

    /**
     * Indicates whether these options can match a single criterion (<code>true</code>) or if all criteria must be
     * matched in order for the snapshot to pass the filter (<code>false</code>).
     * @return whether matching any single criterion is sufficient to consider a snapshot a match
     */
    public boolean isMatchesAny() {
        return matchesAny;
    }

    /**
     * Compares a snapshot against these filter options to see if it matches.
     * @param snapshot the snapshot to be compared
     * @param currentAccount <code>null</code> if in the context of a <code>searchXXX</code> method, or the
     *                       account number for the current user if in a <code>listXXX</code> method.
     * @return <code>true</code> if the snapshot matches the filter criteria
     */
    public boolean matches(@Nonnull DataClusterSnapshot snapshot, @Nullable String currentAccount) {
        if( accountNumber == null ) {
            if( currentAccount != null && !currentAccount.equals(snapshot.getProviderOwnerId()) ) {
                if( !matchesAny ) {
                    return false;
                }
            }
        }
        else {
            if( !accountNumber.equals(snapshot.getProviderOwnerId()) ) {
                if( !matchesAny ) {
                    return false;
                }
            }
            else if( matchesAny ) {
                return true;
            }
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
    public @Nonnull DataClusterSnapshotFilterOptions matchingAll() {
        this.matchesAny = false;
        return this;
    }

    /**
     * Indicates that the criteria associated with this filter must match just one single criterion.
     * @return this
     */
    public @Nonnull DataClusterSnapshotFilterOptions matchingAny() {
        this.matchesAny = true;
        return this;
    }

    /**
     * Adds a regex to the set of filtering options. This regular expression is a standard Java regular expression
     * matches against the snapshot name, description, and meta-data tags.
     * @param regex the Java regular expression string to match against
     * @return this
     */
    public @Nonnull DataClusterSnapshotFilterOptions matchingRegex(@Nonnull String regex) {
        this.regex = regex;
        return this;
    }

    /**
     * Sets an account number to the options on which snapshot filtering should be done.
     * @param accountNumber the account number to filter against
     * @return this
     */
    public @Nonnull DataClusterSnapshotFilterOptions withAccountNumber(@Nonnull String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    /**
     * Builds filtering options that will force filtering on the specified meta-data tags.
     * @param tags the meta-data tags on which to filter
     * @return this
     */
    public @Nonnull DataClusterSnapshotFilterOptions withTags(@Nonnull Map<String, String> tags) {
        this.tags = tags;
        return this;
    }

}
