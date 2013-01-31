package org.dasein.cloud.compute;

import org.dasein.cloud.CloudProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * Filtering options for filtering listings of volumes based on specific criteria. You can match any single criterion or
 * all criteria.
 * <p>Created by George Reese: 1/31/13 11:05 AM</p>
 * @author George Reese
 * @version 2013.04 initial version
 * @since 2013.04
 */
public class VolumeFilterOptions {
    private String             attachedTo;
    private boolean            matchesAny;
    private String             regex;
    private Map<String,String> tags;

    private VolumeFilterOptions(boolean matchesAny) {
        this.matchesAny = matchesAny;
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
}
