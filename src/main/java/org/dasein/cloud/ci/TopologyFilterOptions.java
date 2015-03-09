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

package org.dasein.cloud.ci;

import org.dasein.cloud.CloudProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * Options for filtering topology objects when performing searches.
 * <p>Created by George Reese: 5/31/13 9:22 AM</p>
 * @author George Reese
 * @version 2013.07 initial version
 * @since 2013.07
 */
public class TopologyFilterOptions {

    /**
     * Constructs a filter for any kind of topologies.
     * @return a simple filter for topologies that does no filtering unless other options are added
     */
    static public @Nonnull TopologyFilterOptions getInstance() {
        return new TopologyFilterOptions(null, false);
    }

    /**
     * Constructs a filter for any kind of topologies.
     * @param matchesAny <code>true</code> if it is sufficient that just one of the criteria are matched, false if all are needed to be matched
     * @return a simple filter for topologies that does no filtering unless other options are added
     */
    static public @Nonnull TopologyFilterOptions getInstance(boolean matchesAny) {
        return new TopologyFilterOptions(null, matchesAny);
    }

    /**
     * Constructs a regex filter on the specified regular expression.
     * @param regex the regular expression on which to filter
     * @return a filter for topologies that match the specified regular expression
     */
    static public @Nonnull TopologyFilterOptions getInstance(@Nonnull String regex) {
        TopologyFilterOptions options = new TopologyFilterOptions(null, false);

        options.regex = regex;
        return options;
    }

    /**
     * Constructs a regex filter on the specified regular expression.
     * @param matchesAny <code>true</code> if it is sufficient that just one of the criteria are matched, false if all are needed to be matched
     * @param regex the regular expression on which to filter
     * @return a filter for topologies that match the specified regular expression
     */
    static public @Nonnull TopologyFilterOptions getInstance(boolean matchesAny, @Nonnull String regex) {
        TopologyFilterOptions options = new TopologyFilterOptions(null, matchesAny);

        options.regex = regex;
        return options;
    }

    private String             accountNumber;
    private boolean            matchesAny;
    private String             regex;
    private Map<String,String> tags;

    private TopologyFilterOptions(@Nullable String account, boolean matchesAny) {
        this.accountNumber = account;
        this.matchesAny = matchesAny;
    }

    /**
     * Matches a topology against the criteria in this set of filter options.
     * @param topology the topology to test
     * @return true if the topology matches all criteria
     */
    public boolean matches(@Nonnull Topology topology) {
        if( accountNumber != null ) {
            if( !accountNumber.equals(topology.getProviderOwnerId()) ) {
                if( !matchesAny ) {
                    return false;
                }
            }
            else if( matchesAny ) {
                return true;
            }
        }
        if( regex != null ) {
            boolean matches = (topology.getName().matches(regex) || topology.getDescription().matches(regex));

            if( !matches ) {
                for( Map.Entry<String,String> tag : topology.getTags().entrySet() ) {
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
            if( !CloudProvider.matchesTags(topology.getTags(), topology.getName(), topology.getDescription(), tags) ) {
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
     * Sets the tags to filter against.
     * @param tags the tags to filter against
     * @return this
     */
    public @Nonnull TopologyFilterOptions withTags(@Nonnull Map<String, String> tags) {
        this.tags = tags;
        return this;
    }

    @Override
    public @Nonnull String toString() {
        return ("[" + (matchesAny ? "Match ANY: " : "Match ALL: ") + "accountNumber=" + accountNumber + ",regex=" + regex + "]");
    }
}
