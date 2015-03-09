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

package org.dasein.cloud.network;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HealthCheckFilterOptions {
    /**
     * Constructs an empty set of filtering options that will force match against any Health Check by default.
     * @return an empty filtering options objects
     */
    static public @Nonnull HealthCheckFilterOptions getInstance() {
        return new HealthCheckFilterOptions(false);
    }

    /**
     * Constructs filter options that will match either any criteria or all criteria, but has no actual criteria
     * associated with it.
     * @param matchesAny <code>true</code> if it is sufficient that just one of the criteria are matched, false if all are needed to be matched
     * @return a newly constructed set of Health Check filtering options
     */
    static public @Nonnull HealthCheckFilterOptions getInstance(boolean matchesAny) {
        return new HealthCheckFilterOptions(matchesAny);
    }

    /**
     * Constructs a filter against a Java regular expression that must match all criteria.
     * @param regex the regular expression to match against the Health Check name, description or path
     * @return a VM filter options object
     */
    static public @Nonnull HealthCheckFilterOptions getInstance(@Nonnull String regex) {
        HealthCheckFilterOptions options = new HealthCheckFilterOptions(false);

        options.regex = regex;
        return options;
    }

    /**
     * Constructs a filter against a Java regular expression that must match criteria as specified
     * @param matchesAny <code>true</code> if it is sufficient that just one of the criteria are matched, false if all are needed to be matched
     * @param regex the regular expression to match against the VM name, description, or tag values
     * @return a VM filter options object
     */
    static public @Nonnull HealthCheckFilterOptions getInstance(boolean matchesAny, @Nonnull String regex) {
        HealthCheckFilterOptions options = new HealthCheckFilterOptions(matchesAny);

        options.regex = regex;
        return options;
    }

    private boolean  matchesAny;
    private String   regex;
    private LoadBalancerHealthCheck.HCProtocol protocol;
    private int      port = 0;

    private HealthCheckFilterOptions(boolean matchesAny) {
        this.matchesAny = matchesAny;
    }

    /**
     * @return a regular expression to match against a Health Check name, description or path values.
     */
    public @Nullable String getRegex() {
        return this.regex;
    }

    /**
     * Indicates whether there are any criteria associated with these options.
     * @return <code>true</code> if this filter options object has any criteria associated with it
     */
    public boolean hasCriteria() {
        return (protocol != null || port != 0 || regex != null);
    }

    /**
     * Indicates whether these options can match a single criterion (<code>true</code>) or if all criteria must be
     * matched in order for the Health Check to pass the filter (<code>false</code>).
     * @return whether matching any single criterion is sufficient to consider a Health Check a match
     */
    public boolean isMatchAny(){
        return this.matchesAny;
    }

    /**
     * Indicates that the criteria associated with this filter must match all set criteria.
     * @return this
     */
    public @Nonnull HealthCheckFilterOptions matchingAll() {
        this.matchesAny = false;
        return this;
    }

    /**
     * Indicates that the criteria associated with this filter must match just one single criterion.
     * @return this
     */
    public @Nonnull HealthCheckFilterOptions matchingAny() {
        this.matchesAny = true;
        return this;
    }

    /**
     * Adds a regex to the set of filtering options. This regular expression is a standard Java regular expression
     * matches against the Health Check name, description, and path.
     * @param regex the Java regular expression string to match against
     * @return this
     */
    public @Nonnull HealthCheckFilterOptions matchingRegex(@Nonnull String regex) {
        this.regex = regex;
        return this;
    }

    public @Nonnull HealthCheckFilterOptions matchingProtocol(@Nonnull LoadBalancerHealthCheck.HCProtocol protocol){
        this.protocol = protocol;
        return this;
    }

    public @Nonnull HealthCheckFilterOptions matchingPort(int port){
        this.port = port;
        return this;
    }

    /**
     * Matches a Health Check against the criteria in this set of filter options.
     * @param lbhc the Health Check to test
     * @return true if the Health Check matches all criteria
     */
    public boolean matches(@Nonnull LoadBalancerHealthCheck lbhc) {
        if( regex != null ){
            if( !((lbhc.getName() != null && lbhc.getName().matches(regex))
                    || (lbhc.getDescription() != null && lbhc.getDescription().matches(regex))
                    || (lbhc.getPath() != null && lbhc.getPath().matches(regex))) ) {
                if( !matchesAny ) {
                    return false;
                }
            }
            else if( matchesAny ) {
                return true;
            }
        }
        if( protocol != null ) {
            if( !protocol.equals(lbhc.getProtocol()) ) {
                if( !matchesAny ) {
                    return false;
                }
            }
            else if( matchesAny ) {
                return true;
            }
        }
        if( port > 0 ) {
            if( lbhc.getPort() != port ) {
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
}
