package org.dasein.cloud.compute;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * @author Eduard Bakaev
 */
public class AutoScalingGroupFilterOptions {

    /**
     * Constructs an empty set of filtering options that will force match against any AutoScalingGroup by default.
     *
     * @return an empty filtering options objects
     */
    static public @Nonnull AutoScalingGroupFilterOptions getInstance() {
        return new AutoScalingGroupFilterOptions();
    }

    private Map<String, String> tags;

    /**
     * @return the tags, if any, on which filtering should be done (<code>null</code> means don't filter on tags)
     */
    public Map<String, String> getTags() {
        return tags;
    }

    /**
     * Indicates whether there are any criteria associated with these options.
     *
     * @return <code>true</code> if this filter options object has any criteria associated with it
     */
    public boolean hasCriteria() {
        return ( ( tags != null && !tags.isEmpty() ) );
    }

    /**
     * Matches a scaling group against the criteria in this set of filter options. Currently on tags
     * if at least one tag is found it returns 'true'
     *
     * @param sg the scaling group to test
     * @return true if the AutoScalingGroup matches at least one criteria
     */
    public boolean matches( @Nonnull ScalingGroup sg ) {
        if( tags != null && !tags.isEmpty() && sg.getTagsAsMap().size() > 0 ) {
            Map<String, String> currentTags = sg.getTagsAsMap();
            for( String tagToCheck : tags.keySet() ) {
                if( !( currentTags.containsKey(tagToCheck) && currentTags.get(tagToCheck).equals(tags.get(tagToCheck)) ) ) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Builds filtering options that will force filtering on the specified meta-data tags.
     *
     * @param tags the meta-data tags on which to filter
     * @return this
     */
    public @Nonnull AutoScalingGroupFilterOptions withTags( @Nonnull Map<String, String> tags ) {
        this.tags = tags;
        return this;
    }
}
