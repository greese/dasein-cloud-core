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
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Options for filtering virtual machines when querying the cloud provider. You may optionally filter on
 * any or all set criteria. If a value has not been set for a specific criterion, it is not included in the filtering
 * process.
 * <p>Created by Cameron Stokes: 01/27/13 09:41 AM</p>
 *
 * @author Cameron Stokes
 * @version 2013.04 initial version
 * @since 2013.04
 */
public class VMFilterOptions {
    private String[]                  labels;
    private boolean                   matchesAny;
    private String                    regex;
    private Map<String, String>       tags;
    private Set<VmState>              vmStates;
    private VirtualMachineLifecycle[] lifecycles;
    private String                    spotRequestId;

    /**
     * Constructs an empty set of filtering options that will force match against any VM by default.
     *
     * @return an empty filtering options objects
     */
    static public @Nonnull VMFilterOptions getInstance() {
        return new VMFilterOptions(false);
    }

    /**
     * Constructs filter options that will match either any criteria or all criteria, but has no actual criteria
     * associated with it.
     *
     * @param matchesAny <code>true</code> if it is sufficient that just one of the criteria are matched, false if all are needed to be matched
     * @return a newly constructed set of VM filtering options
     */
    static public @Nonnull VMFilterOptions getInstance( boolean matchesAny ) {
        return new VMFilterOptions(matchesAny);
    }

    /**
     * Constructs a filter against a Java regular expression that must match all criteria.
     *
     * @param regex the regular expression to match against the VM name, description, or tag values
     * @return a VM filter options object
     */
    static public @Nonnull VMFilterOptions getInstance( @Nonnull String regex ) {
        VMFilterOptions options = new VMFilterOptions(false);

        options.regex = regex;
        return options;
    }

    /**
     * Constructs a filter against a Java regular expression that must match criteria as specified
     *
     * @param matchesAny <code>true</code> if it is sufficient that just one of the criteria are matched, false if all are needed to be matched
     * @param regex      the regular expression to match against the VM name, description, or tag values
     * @return a VM filter options object
     */
    static public @Nonnull VMFilterOptions getInstance( boolean matchesAny, @Nonnull String regex ) {
        VMFilterOptions options = new VMFilterOptions(matchesAny);

        options.regex = regex;
        return options;
    }

    private VMFilterOptions( boolean matchesAny ) {
        this.matchesAny = matchesAny;
    }

    /**
     * @return a regular expression to match against a VM name, description, or tag values.
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
     * @return the labels, if any, on which filtering should be done (<code>null</code> means don't filter on labels)
     */
    public @Nullable String[] getLabels() {
        return labels;
    }

    /**
     * @return the VM states, if any, on which filtering should be done (<code>null</code> means don't filter on VM states)
     */
    public @Nullable Set<VmState> getVmStates() {
        return vmStates;
    }

    /**
     * @return the VM lifecycles, if any, on which filtering should be done (<code>null</code> means don't filter on VM lifecycles)
     */
    public @Nullable VirtualMachineLifecycle[] getLifecycles() {
        return lifecycles;
    }

    /**
     * @return the Spot VM request Id, if any, on which filtering should be done (<code>null</code> means don't filter on it)
     */
    public @Nullable String getSpotRequestId() {
        return spotRequestId;
    }

    /**
     * Indicates whether there are any criteria associated with these options.
     *
     * @return <code>true</code> if this filter options object has any criteria associated with it
     */
    public boolean hasCriteria() {
        return ( ( tags != null && !tags.isEmpty() ) || ( labels != null && labels.length > 0 ) || ( vmStates != null && !vmStates.isEmpty() ) || ( lifecycles != null && lifecycles.length > 0 ) || regex != null );
    }

    /**
     * Indicates whether these options can match a single criterion (<code>true</code>) or if all criteria must be
     * matched in order for the VM to pass the filter (<code>false</code>).
     *
     * @return whether matching any single criterion is sufficient to consider a VM a match
     */
    public boolean isMatchesAny() {
        return matchesAny;
    }

    /**
     * Matches a virtual machine against the criteria in this set of filter options.
     *
     * @param vm the virtual machine to test
     * @return true if the VM matches all criteria
     */
    public boolean matches( @Nonnull VirtualMachine vm ) {
        if( regex != null ) {
            boolean matches = ( vm.getName().matches(regex) || vm.getDescription().matches(regex) );

            if( !matches ) {
                for( Map.Entry<String, String> tag : vm.getTags().entrySet() ) {
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
            if( !CloudProvider.matchesTags(vm.getTags(), vm.getName(), vm.getDescription(), tags) ) {
                if( !matchesAny ) {
                    return false;
                }
            }
            else if( matchesAny ) {
                return true;
            }
        }
        if( lifecycles != null && lifecycles.length > 0 ) {
            boolean matches = false;
            for( VirtualMachineLifecycle lc : lifecycles ) {
                if( lc.equals(vm.getLifecycle()) ) {
                    matches = true;
                    break;
                }
            }
            if( !matches && !matchesAny ) {
                return false;
            }
            else if( matches && matchesAny ) {
                return true;
            }
        }
        if( spotRequestId != null ) {
            boolean matches = spotRequestId.equals(vm.getSpotRequestId());
            if( !matches && !matchesAny ) {
                return false;
            }
            else if( matches && matchesAny ) {
                return true;
            }
        }
        return !matchesAny;
    }

    /**
     * Indicates that the criteria associated with this filter must match all set criteria.
     *
     * @return this
     */
    public @Nonnull VMFilterOptions matchingAll() {
        this.matchesAny = false;
        return this;
    }

    /**
     * Indicates that the criteria associated with this filter must match just one single criterion.
     *
     * @return this
     */
    public @Nonnull VMFilterOptions matchingAny() {
        this.matchesAny = true;
        return this;
    }

    /**
     * Adds a regex to the set of filtering options. This regular expression is a standard Java regular expression
     * matches against the VM name, description, and meta-data tags.
     *
     * @param regex the Java regular expression string to match against
     * @return this
     */
    public @Nonnull VMFilterOptions matchingRegex( @Nonnull String regex ) {
        this.regex = regex;
        return this;
    }

    /**
     * Builds filtering options that will force filtering on the specified meta-data tags.
     *
     * @param tags the meta-data tags on which to filter
     * @return this
     */
    public @Nonnull VMFilterOptions withTags( @Nonnull Map<String, String> tags ) {
        this.tags = tags;
        return this;
    }

    /**
     * Builds filtering options that will force filtering on the specified labels.
     *
     * @param labels the labels on which to filter
     * @return this
     */
    public @Nonnull VMFilterOptions withLabels( @Nonnull String... labels ) {
        this.labels = labels;
        return this;
    }

    /**
     * Builds filtering options that will force filtering on the specified VM states.
     *
     * @param vmStates the VM states on which to filter
     * @return this
     */
    public @Nonnull VMFilterOptions withVmStates( @Nonnull Set<VmState> vmStates ) {
        this.vmStates = vmStates;
        return this;
    }

    /**
     * Builds filtering options that will force filtering on the specified VM lifecycles.
     *
     * @param lifecycles the VM lifecycles on which to filter
     * @return this
     */
    public @Nonnull VMFilterOptions withLifecycles( @Nonnull VirtualMachineLifecycle... lifecycles ) {
        this.lifecycles = lifecycles;
        return this;
    }

    /**
     * Builds filtering options that will force filtering on the specified Spot VM request Id only
     * @param spotRequestId spot request identifier on which to filter
     * @return this
     */
    public @Nonnull VMFilterOptions withSpotRequestId( @Nonnull String spotRequestId ) {
        this.spotRequestId = spotRequestId;
        return this;
    }

}
