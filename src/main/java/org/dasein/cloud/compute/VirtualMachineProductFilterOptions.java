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

import org.dasein.util.uom.storage.Megabyte;
import org.dasein.util.uom.storage.Storage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * Options for filtering virtual machine products when querying the cloud provider. You may optionally filter on
 * any or all set criteria. If a value has not been set for a specific criterion, it is not included in the filtering
 * process.
 * <p>Created by Drew Lyall: 04/07/14 15:00 AM</p>
 * @author Drew Lyall
 * @version 2014.08 initial version
 * @since 2014.08
 */
public class VirtualMachineProductFilterOptions{
    private boolean matchesAny;
    private String  regex;
    private int cpuCount = 0;
    private Storage<Megabyte> ramSize;
    private String            dataCenterId;
    private Architecture      architecture;

    /**
     * Constructs an empty set of filtering options that will force match against any VM Product by default.
     * @return an empty filtering options objects
     */
    public static @Nonnull VirtualMachineProductFilterOptions getInstance() {
        return new VirtualMachineProductFilterOptions(false);
    }

    /**
     * Constructs filter options that will match either any criteria or all criteria, but has no actual criteria
     * associated with it.
     * @param matchesAny <code>true</code> if it is sufficient that just one of the criteria are matched, false if all are needed to be matched
     * @return a newly constructed set of VM Product filtering options
     */
    public static @Nonnull VirtualMachineProductFilterOptions getInstance(boolean matchesAny) {
        return new VirtualMachineProductFilterOptions(matchesAny);
    }

    /**
     * Constructs a filter against a Java regular expression that must match all criteria.
     * @param regex the regular expression to match against the VM Product name or description
     * @return a VM Product filter options object
     */
    public static @Nonnull VirtualMachineProductFilterOptions getInstance(@Nonnull String regex) {
        VirtualMachineProductFilterOptions options = new VirtualMachineProductFilterOptions(false);

        options.regex = regex;
        return options;
    }

    /**
     * Constructs a filter against a Java regular expression that must match criteria as specified
     * @param matchesAny <code>true</code> if it is sufficient that just one of the criteria are matched, false if all are needed to be matched
     * @param regex      the regular expression to match against the VM Product name or description
     * @return a VM Product filter options object
     */
    public static @Nonnull VirtualMachineProductFilterOptions getInstance(boolean matchesAny, @Nonnull String regex){
        VirtualMachineProductFilterOptions options = new VirtualMachineProductFilterOptions(matchesAny);

        options.regex = regex;
        return options;
    }

    private VirtualMachineProductFilterOptions(boolean matchesAny) {
        this.matchesAny = matchesAny;
    }

    /**
     * @return the architecture on which filtering should be done, or <code>null</code> to not filter on architecture
     */
    public @Nullable Architecture getArchitecture() {
        return architecture;
    }

    public @Nullable String getRegex(){
        return regex;
    }

    public int getCpuCount(){
        return cpuCount;
    }

    public @Nullable Storage<Megabyte> getRamSize(){
        return ramSize;
    }

    public @Nullable String getDataCenterId(){
        return dataCenterId;
    }

    /**
     * Indicates whether there are any criteria associated with these options.
     * @return true if this filter options object has any criteria associated with it
     */
    public boolean hasCriteria() {
        return (cpuCount > 0 || (ramSize != null && ramSize.intValue() > 0) || regex != null || dataCenterId != null || architecture != null);
    }

    /**
     * Indicates whether these options can match a single criterion (true) or if all criteria must be
     * matched in order for the VM Product to pass the filter (false).
     * @return whether matching any single criterion is sufficient to consider a VM Product a match
     */
    public boolean isMatchesAny() {
        return matchesAny;
    }

    /**
     * Indicates that the criteria associated with this filter must match all set criteria.
     * @return this
     */
    public @Nonnull VirtualMachineProductFilterOptions matchingAll(){
        this.matchesAny = false;
        return this;
    }

    /**
     * Indicates that the criteria associated with this filter must match just one single criterion.
     * @return this
     */
    public @Nonnull VirtualMachineProductFilterOptions matchingAny(){
        this.matchesAny = true;
        return this;
    }

    /**
     * Adds a regex to the set of filtering options. This regular expression is a standard Java regular expression
     * matches against the VM Product name or description
     * @param regex the Java regular expression string to match against
     * @return this
     */
    public @Nonnull VirtualMachineProductFilterOptions matchingRegex(@Nonnull String regex){
        this.regex = regex;
        return this;
    }

    public @Nonnull VirtualMachineProductFilterOptions withCpuCount(int cpuCount){
        this.cpuCount = cpuCount;
        return this;
    }

    public @Nonnull VirtualMachineProductFilterOptions withRamSize(@Nonnull Storage<Megabyte> ramSize){
        this.ramSize = ramSize;
        return this;
    }

    public @Nonnull VirtualMachineProductFilterOptions withDataCenterId(@Nonnull String dataCenterId){
        this.dataCenterId = dataCenterId;
        return this;
    }

    public @Nonnull VirtualMachineProductFilterOptions withArchitecture(@Nonnull Architecture architecture) {
        this.architecture = architecture;
        return this;
    }

    /**
     * Matches a VM Product against the criteria in this set of filter options.
     * @param product the VM Product to test
     * @return true if the VM Product matches all criteria
     */
    public boolean matches(@Nonnull VirtualMachineProduct product) {
        if( regex != null ) {
            boolean matches = (product.getName().matches(regex) || product.getDescription().matches(regex));
            if( !matches && !matchesAny ) {
                return false;
            }
            else if( matches && matchesAny ) {
                return true;
            }
        }
        else if(cpuCount > 0){
            boolean matches = product.getCpuCount() == cpuCount;
            if( !matches && !matchesAny ) {
                return false;
            }
            else if( matches && matchesAny ) {
                return true;
            }
        }
        else if(ramSize != null){
            boolean matches = product.getRamSize() == ramSize;
            if( !matches && !matchesAny ) {
                return false;
            }
            else if( matches && matchesAny ) {
                return true;
            }
        }
        else if(dataCenterId != null && product.getDataCenterId() != null){
            boolean matches = product.getDataCenterId().equals(dataCenterId);
            if( !matches && !matchesAny ) {
                return false;
            }
            else if( matches && matchesAny ) {
                return true;
            }
        }
        else if(architecture != null && product.getArchitectures() != null) {
            boolean matches = Arrays.binarySearch(product.getArchitectures(), architecture) >= 0;
            if( !matches && !matchesAny ) {
                return false;
            }
            else if( matches && matchesAny ) {
                return true;
            }
        }
        return !matchesAny;
    }
}
