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

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Iterator;

/**
 * In some clouds, the rules that can be tied to a firewall are constrained based on some common value
 * defined at the firewall level. For example, one cloud might allow you to associate only rules
 * of the same protocol with a given firewall. In Dasein terms, that firewall is considered constrained by
 * protocol. When you attempt to add a rule of a protocol different from the other protocols associated with
 * the firewall, it will cause an error.
 * <p>Created by George Reese: 2/3/14 1:56 PM</p>
 * @author George Reese
 * @version 2014.03 initial version (issue #99)
 */
public class FirewallConstraints {
    /**
     * A constraint is a {@link FirewallRule} field that all rules associated with a firewall constrained
     * on that field must share.
     */
    static public enum Constraint {
        /**
         * Constrained by {@link Direction}
         */
        DIRECTION,
        /**
         * Constrained by destination {@link RuleTarget}
         */
        DESTINATION,
        /**
         * Constrained by source {@link RuleTarget}
         */
        SOURCE,
        /**
         * Constrained by protocol
         */
        PROTOCOL,
        /**
         * Constrained by port range
         */
        PORTS,
        /**
         * Constrained by {@link Permission}
         */
        PERMISSION;

        public @Nullable Object getValue(@Nonnull CloudProvider provider, @Nonnull String firewallId) throws CloudException, InternalException {
            NetworkServices services = provider.getNetworkServices();

            if( services == null ) {
                throw new OperationNotSupportedException(provider.getCloudName() + " does not support network services");
            }
            FirewallSupport support = services.getFirewallSupport();

            if( support == null ) {
                throw new OperationNotSupportedException(provider.getCloudName() + " does not have support for firewalls");
            }
            Iterator<FirewallRule> rules = support.getRules(firewallId).iterator();

            if( !rules.hasNext() ) {
                return null;
            }
            FirewallRule rule = rules.next();

            switch( this ) {
                case DIRECTION:
                    return rule.getDirection();
                case DESTINATION:
                    return rule.getDestinationEndpoint();
                case SOURCE:
                    return rule.getSourceEndpoint();
                case PROTOCOL:
                    return rule.getProtocol();
                case PERMISSION:
                    return rule.getPermission();
                case PORTS:
                    return new int[] { rule.getStartPort(), rule.getEndPort() };
            }
            return null;
        }
    }

    /**
     * The level to which a constraint operates.
     */
    static public enum Level {
        /**
         * A value for this field must be specified for all firewalls in the cloud and all rules must match.
         */
        REQUIRED,
        /**
         * Firewall rules must match only if the firewall has a value for this constraint.
         */
        IF_DEFINED,
        /**
         * Firewall rules do not need to match on this constraint.
         */
        NOT_CONSTRAINED
    }

    /**
     * Fetches an instance of empty firewall constraints. Add constraints to the list by calling
     * {@link #withConstraint(Constraint, Level)}.
     * @return an empty set of firewall constraints
     */
    static public FirewallConstraints getInstance() {
        return new FirewallConstraints();
    }

    private HashMap<Constraint,Level> constraints = new HashMap<Constraint, Level>();

    private FirewallConstraints() { }

    /**
     * Checks the degree to which a given firewall rule field is constrained.
     * @param forConstraint the constraint being checked
     * @return the level to which this constraint must be supported by rules
     */
    public @Nonnull Level getConstraintLevel(@Nonnull Constraint forConstraint) {
        Level l = constraints.get(forConstraint);

        return (l == null ? Level.NOT_CONSTRAINED : l);
    }

    /**
     * @return a list of all constraint fields associated with this set of constraints.
     */
    public @Nonnull Iterable<Constraint> getConstraints() {
        return constraints.keySet();
    }

    /**
     * @return true if this is an empty set of constraints (an open firewall)
     */
    public boolean isOpen() {
        return constraints.isEmpty();
    }

    /**
     * Adds the specified constraint to the list of constraints associated with this firewall.
     * @param constraint the constraint in question
     * @param level the level to which it is constrained
     * @return this
     */
    public @Nonnull FirewallConstraints withConstraint(@Nonnull Constraint constraint, @Nonnull Level level) {
        constraints.put(constraint, level);
        return this;
    }

    @Override
    public @Nonnull String toString() {
        return constraints.toString();
    }
}
