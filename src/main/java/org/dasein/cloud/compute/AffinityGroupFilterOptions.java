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

import javax.annotation.Nonnull;

/**
 * Options for filtering affinity groups when querying the cloud provider. You may optionally filter on
 * any or all set criteria. If a value has not been set for a specific criterion, it is not included in the filtering
 * process.
 * Created by Drew Lyall: 11/07/14 13:45
 * @author Drew Lyall
 */
public class AffinityGroupFilterOptions{
    private boolean matchesAny;
    private String  regex;
    private String  dataCenterId;

    /**
     * Constructs an empty set of filtering options that will force match against any affinity group by default.
     * @return an empty filtering options objects
     */
    public static @Nonnull AffinityGroupFilterOptions getInstance() {
        return new AffinityGroupFilterOptions(false);
    }

    /**
     * Constructs filter options that will match either any criteria or all criteria, but has no actual criteria
     * associated with it.
     * @param matchesAny true if it is sufficient that just one of the criteria are matched, false if all are needed to be matched
     * @return a newly constructed set of affinity group filtering options
     */
    public static @Nonnull AffinityGroupFilterOptions getInstance(boolean matchesAny){
        return new AffinityGroupFilterOptions(matchesAny);
    }

    /**
     * Constructs a filter against a Java regular expression that must match all criteria.
     * @param regex the regular expression to match against the VM name, description, or tag values
     * @return an affinity group filter options object
     */
    public static @Nonnull AffinityGroupFilterOptions getInstance(@Nonnull String regex){
        AffinityGroupFilterOptions options = new AffinityGroupFilterOptions(false);

        options.regex = regex;
        return options;
    }

    /**
     * Constructs a filter against a Java regular expression that must match criteria as specified
     * @param matchesAny true if it is sufficient that just one of the criteria are matched, false if all are needed to be matched
     * @param regex      the regular expression to match against the affinity group name or description
     * @return an affinity group filter options object
     */
    public static @Nonnull AffinityGroupFilterOptions getInstance(boolean matchesAny, @Nonnull String regex){
        AffinityGroupFilterOptions options = new AffinityGroupFilterOptions(matchesAny);

        options.regex = regex;
        return options;
    }

    private AffinityGroupFilterOptions(boolean matchesAny){
        this.matchesAny = matchesAny;
    }

    public String getRegex(){
        return regex;
    }

    public String getDataCenterId(){
        return dataCenterId;
    }

    /**
     * Indicates whether there are any criteria associated with these options.
     * @return true if this filter options object has any criteria associated with it
     */
    public boolean hasCriteria(){
        return (dataCenterId != null || regex != null);
    }

    /**
     * Indicates whether these options can match a single criterion (true) or if all criteria must be
     * matched in order for the Affinity Group to pass the filter (false).
     * @return whether matching any single criterion is sufficient to consider an Affinity Group a match
     */
    public boolean isMatchesAny() {
        return matchesAny;
    }

    /**
     * Indicates that the criteria associated with this filter must match all set criteria.
     * @return this
     */
    public @Nonnull AffinityGroupFilterOptions matchingAll() {
        this.matchesAny = false;
        return this;
    }

    /**
     * Adds a regex to the set of filtering options. This regular expression is a standard Java regular expression
     * matches against the affinity group name or description
     * @param regex the Java regular expression string to match against
     * @return this
     */
    public @Nonnull AffinityGroupFilterOptions matchingRegex(@Nonnull String regex){
        this.regex = regex;
        return this;
    }

    /**
     * Adds a dataCenterId to the set of filtering options.
     * @param dataCenterId the dataCenterId string to match against
     * @return this
     */
    public @Nonnull AffinityGroupFilterOptions withDataCenterId(@Nonnull String dataCenterId){
        this.dataCenterId = dataCenterId;
        return this;
    }

    /**
     * Matches an affinity group against the criteria in this set of filter options.
     * @param affinityGroup the affinity group to test
     * @return true if the affinity group matches all criteria
     */
    public boolean matches(@Nonnull AffinityGroup affinityGroup) {
        if( regex != null ) {
            boolean matches = (affinityGroup.getAffinityGroupName().matches(regex) || affinityGroup.getDescription().matches(regex));
            if(!matches && !matchesAny){
                return false;
            }
            else if(matches && matchesAny){
                return true;
            }
        }
        if(dataCenterId != null){
            boolean matches = dataCenterId.equals(affinityGroup.getDataCenterId());
            if(!matches && !matchesAny){
                return false;
            }
            else if(matches && matchesAny){
                return true;
            }
        }
        return !matchesAny;
    }
}
