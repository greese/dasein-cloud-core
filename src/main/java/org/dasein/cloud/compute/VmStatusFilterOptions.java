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
import java.util.Set;

/**
 * Options for filtering virtual machines statuses {@link VirtualMachineStatus} when querying the cloud provider.
 * You may optionally filter on any or all set criteria. If a value has not been set for a specific criterion,
 * it is not included in the filtering process.
 *
 * @author igoonich
 * @since 28.03.2014
 */
public class VmStatusFilterOptions {

    private boolean matchesAny;
    private String[] vmIds;
    private Set<VmStatus> vmStatuses;

    private VmStatusFilterOptions(boolean matchesAny) {
        this.matchesAny = matchesAny;
    }

    /**
     * @return the VM ids, if any, on which filtering should be done ({@code null} means don't filter on VM ids)
     */
    public String[] getVmIds() {
        return vmIds;
    }

    /**
     * @return the VM statuses, if any, on which filtering should be done ({@code null} means don't filter on VM statuses)
     */
    public Set<VmStatus> getVmStatuses() {
        return vmStatuses;
    }

    public static VmStatusFilterOptions getInstance() {
        return new VmStatusFilterOptions(false);
    }

    /**
     * Indicates whether there are any criteria associated with these options.
     *
     * @return <code>true</code> if this filter options object has any criteria associated with it
     */
    public boolean hasCriteria() {
        return ((vmIds != null && vmIds.length > 0) || (vmStatuses != null && !vmStatuses.isEmpty()));
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
    public boolean matches(@Nonnull VirtualMachineStatus vm) {
        if (vmStatuses != null && !vmStatuses.isEmpty()) {
            if (vmStatuses.contains(vm.getProviderHostStatus()) || vmStatuses.contains(vm.getProviderVmStatus())) {
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
    public @Nonnull VmStatusFilterOptions matchingAll() {
        this.matchesAny = false;
        return this;
    }

    /**
     * Indicates that the criteria associated with this filter must match just one single criterion.
     *
     * @return this
     */
    public @Nonnull VmStatusFilterOptions matchingAny() {
        this.matchesAny = true;
        return this;
    }

    /**
     * Builds filtering options that will force filtering on the specified VM identifiers.
     *
     * @param vmIds the VM identifiers on which to filter
     * @return this
     */
    public @Nonnull VmStatusFilterOptions withVmIds(@Nonnull String... vmIds) {
        this.vmIds = vmIds;
        return this;
    }

    /**
     * Builds filtering options that will force filtering on the specified VM statuses.
     *
     * @param vmStatuses the VM statuses on which to filter
     * @return this
     */
    public @Nonnull VmStatusFilterOptions withVmStatuses(@Nonnull Set<VmStatus> vmStatuses) {
        this.vmStatuses = vmStatuses;
        return this;
    }

}
