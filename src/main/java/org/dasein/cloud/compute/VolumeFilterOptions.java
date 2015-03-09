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

import org.dasein.cloud.CloudProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * Filtering options for filtering listings of volumes based on specific criteria. You can match any single criterion or
 * all criteria.
 * <p>Created simultaneously by George Reese: 1/31/13 11:05 AM and Cameron Stokes: 02/12/13</p>
 * @author George Reese
 * @author Cameron Stokes
 * @version 2013.04 initial version
 * @since 2013.04
 */
public class VolumeFilterOptions {
    /**
     * Constructs an empty filter that will match all criteria assigned to it.
     * @return an empty filter matching all criteria assigned to it
     */
    static public @Nonnull VolumeFilterOptions getInstance() {
        return new VolumeFilterOptions(false);
    }

    /**
     * Constructs an empty filter that will match as specified by the <code>matchAny</code> parameter.
     * @param matchesAny <code>true</code> if any single criterion is sufficient to indicate a match, <code>false</code> if all criteria must be matched
     * @return an empty filter
     */
    static public @Nonnull VolumeFilterOptions getInstance(boolean matchesAny) {
        return new VolumeFilterOptions(matchesAny);
    }

    /**
     * Constructs a filter matching on a Java regular expression that must match all criteria associated with it.
     * @param regex the Java regex to match on
     * @return a filter on a Java regex against the volume name, description, or tags
     */
    static public @Nonnull VolumeFilterOptions getInstance(@Nonnull String regex) {
        VolumeFilterOptions options = new VolumeFilterOptions(false);

        options.regex = regex;
        return options;
    }

    /**
     * Constructs a filter matching on a Java regular expression.
     * @param matchesAny <code>true</code> if any single criterion is sufficient to indicate a match, <code>false</code> if all criteria must be matched
     * @param regex the Java regex to match on
     * @return a filter on a Java regex against the volume name, description, or tags
     */
    static public @Nonnull VolumeFilterOptions getInstance(boolean matchesAny, @Nonnull String regex) {
        VolumeFilterOptions options = new VolumeFilterOptions(matchesAny);

        options.regex = regex;
        return options;
    }

    private String             attachedTo;
    private boolean            matchesAny;
    private String             regex;
    private Map<String,String> tags;

    private VolumeFilterOptions(boolean matchesAny) {
        this.matchesAny = matchesAny;
    }

    /**
     * Filters volumes for those attached to a specific virtual machine
     * @param providerVMId the virtual machine to which the volume must/may be tied
     * @return this
     */
    public @Nonnull VolumeFilterOptions attachedTo(@Nonnull String providerVMId) {
        this.attachedTo = providerVMId;
        return this;
    }

    /**
     * @return the cloud provider ID of a virtual machine whose volumes you are seeking
     */
    public @Nullable String getAttachedTo() {
        return attachedTo;
    }

    /**
     * @return a regular expression to match against a volume name, description, or tag values.
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

     * Indicates whether or not this filter has any criteria on which to filter. If false, then all volumes
     * will match this filter.
     * @return <code>true</code> if there are criteria associated with this filter
     */
    public boolean hasCriteria() {
        return (attachedTo != null || regex != null || (tags != null && !tags.isEmpty()));
    }
    /**
     * Indicates whether these options can match a single criterion (<code>true</code>) or if all criteria must be
     * matched in order for the volume to pass the filter (<code>false</code>).
     * @return whether matching any single criterion is sufficient to consider a volume a match
     */
    public boolean isMatchesAny() {
        return matchesAny;
    }

    /**
     * Matches a volume against the criteria in this set of filter options.
     * @param volume the volume to test
     * @return true if the volume matches all criteria
     */
    public boolean matches(@Nonnull Volume volume) {
        if( attachedTo != null ) {
            if( attachedTo.equals(volume.getProviderVirtualMachineId()) ) {
                if( matchesAny ) {
                    return true;
                }
            }
            else if( !matchesAny ) {
                return false;
            }
        }
        if( regex != null ) {
            boolean matches = (volume.getName().matches(regex) || volume.getDescription().matches(regex));

            if( !matches ) {
                for( Map.Entry<String,String> tag : volume.getTags().entrySet() ) {
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
            if( !CloudProvider.matchesTags(volume.getTags(), volume.getName(), volume.getDescription(), tags) ) {
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
    public @Nonnull VolumeFilterOptions matchingAll() {
        this.matchesAny = false;
        return this;
    }

    /**
     * Indicates that the criteria associated with this filter must match just one single criterion.
     * @return this
     */
    public @Nonnull VolumeFilterOptions matchingAny() {
        this.matchesAny = true;
        return this;
    }

    /**
     * Adds a regex to the set of filtering options. This regular expression is a standard Java regular expression
     * matches against the volume name, description, and meta-data tags.
     * @param regex the Java regular expression string to match against
     * @return this
     */
    public @Nonnull VolumeFilterOptions matchingRegex(@Nonnull String regex) {
        this.regex = regex;
        return this;
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

    @Override
    public @Nonnull String toString() {
        return "[matchesAny=" + matchesAny + ",attachedTo=" + attachedTo + ",regex=" + regex + ",tags=" + tags + "]";
    }
}
