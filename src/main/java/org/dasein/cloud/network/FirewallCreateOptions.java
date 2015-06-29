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
import java.util.*;

/**
 * Encapsulates values to be used in the creation of {@link Firewall} instances either for virtual machine or
 * network firewalls.
 * <p>Created by George Reese: 2/4/13 8:43 AM</p>
 *
 * @author George Reese
 * @version 2014.04 support for rules on create and constraints
 * @since 2013.04
 */
public class FirewallCreateOptions {
    /**
     * Constructs options for creating a firewall having the specified name and description.
     *
     * @param name        the name of the firewall to be created
     * @param description a description of the purpose of the firewall to be created
     * @return options for creating the firewall based on the specified parameters
     */
    static public FirewallCreateOptions getInstance( @Nonnull String name, @Nonnull String description ) {
        FirewallCreateOptions options = new FirewallCreateOptions();

        options.name = name;
        options.description = description;
        return options;
    }

    /**
     * Constructs options for creating a firewall having the specified name and description with a defined set of initial rules.
     *
     * @param name        the name of the firewall to be created
     * @param description a description of the purpose of the firewall to be created
     * @param ruleOptions a list of one or more options to be created as the same time as the firewall
     * @return options for creating the firewall based on the specified parameters
     */
    static public FirewallCreateOptions getInstance( @Nonnull String name, @Nonnull String description, @Nonnull FirewallRuleCreateOptions... ruleOptions ) {
        FirewallCreateOptions options = new FirewallCreateOptions();

        options.name = name;
        options.description = description;
        options.initialRules = new ArrayList<FirewallRuleCreateOptions>();
        Collections.addAll(options.initialRules, ruleOptions);
        return options;
    }

    /**
     * Constructs options for creating a firewall tied to a specific VLAN having the specified name and description.
     *
     * @param inVlanId    the VLAN ID with which the firewall is associated
     * @param name        the name of the firewall to be created
     * @param description a description of the purpose of the firewall to be created
     * @return options for creating the firewall based on the specified parameters
     */
    static public FirewallCreateOptions getInstance( @Nonnull String inVlanId, @Nonnull String name, @Nonnull String description ) {
        FirewallCreateOptions options = new FirewallCreateOptions();

        options.name = name;
        options.description = description;
        options.providerVlanId = inVlanId;
        options.initialRules = new ArrayList<FirewallRuleCreateOptions>();
        return options;
    }

    /**
     * Constructs options for creating a firewall in the specified VLAN having the specified name and description with a defined set of initial rules.
     *
     * @param inVlanId    the VLAN ID with which the firewall is associated
     * @param name        the name of the firewall to be created
     * @param description a description of the purpose of the firewall to be created
     * @param ruleOptions a list of one or more options to be created as the same time as the firewall
     * @return options for creating the firewall based on the specified parameters
     */
    static public FirewallCreateOptions getInstance( @Nonnull String inVlanId, @Nonnull String name, @Nonnull String description, @Nonnull FirewallRuleCreateOptions... ruleOptions ) {
        FirewallCreateOptions options = new FirewallCreateOptions();

        options.name = name;
        options.description = description;
        options.providerVlanId = inVlanId;
        options.initialRules = new ArrayList<FirewallRuleCreateOptions>();
        Collections.addAll(options.initialRules, ruleOptions);
        return options;
    }

    private Map<FirewallConstraints.Constraint, Object> constraints;
    private String                                      description;
    private ArrayList<FirewallRuleCreateOptions>        initialRules;
    private Map<String, String>                         metaData;
    private Collection<String>                          sourceLabels;
    private Collection<String>                          targetLabels;
    private String                                      name;
    private String                                      providerVlanId;
    private Collection<FirewallRule>                    authorizeRules;

    private FirewallCreateOptions() {
    }

    /**
     * Provisions a firewall in the specified cloud based on the options described in this object. Note that a
     * {@link Firewall} object may represents many different kinds of firewalls, and this class can be used to create
     * many of them as well. This build method, however, is limited to building firewalls for compute resources.
     *
     * @param provider          the cloud provider in which the firewall will be created
     * @param asNetworkFirewall if the provisioned firewall should be provisioned as a network firewall
     * @return the unique ID of the firewall that is provisioned
     * @throws CloudException                 an error occurred with the cloud provider while provisioning the firewall
     * @throws InternalException              an internal error occurred within Dasein Cloud while preparing or handling the API call
     * @throws OperationNotSupportedException this cloud does not support firewalls
     */
    public @Nonnull String build( @Nonnull CloudProvider provider, boolean asNetworkFirewall ) throws CloudException, InternalException {
        NetworkServices services = provider.getNetworkServices();

        if( services == null ) {
            throw new OperationNotSupportedException(provider.getCloudName() + " does not support network services");
        }
        if( asNetworkFirewall ) {
            NetworkFirewallSupport support = services.getNetworkFirewallSupport();

            if( support == null ) {
                throw new OperationNotSupportedException(provider.getCloudName() + " does not have support for network firewalls");
            }
            return support.createFirewall(this);
        }
        else {
            FirewallSupport support = services.getFirewallSupport();

            if( support == null ) {
                throw new OperationNotSupportedException(provider.getCloudName() + " does not have support for firewalls");
            }
            return support.create(this);
        }
    }

    /**
     * @param constraint the constraint being checked
     * @return the value to which rules in this firewall will be constrained for the specified constraint
     */
    public @Nullable Object getConstraintValue( @Nonnull FirewallConstraints.Constraint constraint ) {
        return ( constraints == null ? null : constraints.get(constraint) );
    }

    /**
     * @return a list of all constraints
     */
    public Iterable<FirewallConstraints.Constraint> getConstraints() {
        return ( constraints == null ? new ArrayList<FirewallConstraints.Constraint>() : constraints.keySet() );
    }

    /**
     * @return text describing the purpose of the firewall
     */
    public @Nonnull String getDescription() {
        return description;
    }

    /**
     * @return the initial set of rules with which the firewall should be created
     */
    public @Nonnull FirewallRuleCreateOptions[] getInitialRules() {
        FirewallRuleCreateOptions[] options = new FirewallRuleCreateOptions[initialRules == null ? 0 : initialRules.size()];

        if( initialRules != null ) {
            int idx = 0;

            for( FirewallRuleCreateOptions option : initialRules ) {
                options[idx++] = option;
            }
        }
        return options;
    }

    /**
     * @return any extra meta-data to assign to the firewall
     */
    public @Nonnull Map<String, String> getMetaData() {
        return ( metaData == null ? new HashMap<String, String>() : metaData );
    }

    /**
     * @return the friendly name for the firewall
     */
    public @Nonnull String getName() {
        return name;
    }

    /**
     * @return the VLAN, if any, with which the newly created firewall will be associated
     */
    public @Nullable String getProviderVlanId() {
        return providerVlanId;
    }

    /**
     * @return source labels to assign to firewall
     */
    public Collection<String> getSourceLabels() {
        return sourceLabels;
    }

    /**
     * @return target labels to assign to firewall
     */
    public @Nonnull Collection<String> getTargetLabels() {
        return targetLabels;
    }

    /**
     * Identifies which source labels will be attached to this firewall
     *
     * @param sourceLabelsToAdd source labels to assign to the firewall
     * @return this
     */
    public @Nonnull FirewallCreateOptions withSourceLabels( @Nonnull Collection<String> sourceLabelsToAdd ) {
        if( sourceLabels == null ) {
            sourceLabels = new ArrayList<String>();
        }
        sourceLabels.addAll(sourceLabelsToAdd);
        return this;
    }

    /**
     * Identifies which target labels will be attached to this firewall
     *
     * @param targetLabelsToAdd labels to assign to the firewall
     * @return this
     */
    public @Nonnull FirewallCreateOptions withTargetLabels( @Nonnull Collection<String> targetLabelsToAdd ) {
        if( targetLabels == null ) {
            targetLabels = new ArrayList<String>();
        }
        targetLabels.addAll(targetLabelsToAdd);
        return this;
    }

    /**
     * Provides a Collection of initial authorize FirewallRules
     *
     * @return a Collection of FirewallRule
     */
    public @Nonnull Collection<FirewallRule> getAuthorizeRules() {
        return authorizeRules == null ? Collections.<FirewallRule>emptyList() : authorizeRules;
    }

    /**
     * Defines the initial authorize firewall rules.
     *
     * @param rulesToAdd a Collection of type FirewallRule
     * @return this
     */
    public @Nonnull FirewallCreateOptions withAuthorizeRules( @Nonnull Collection<FirewallRule> rulesToAdd ) {
        if( authorizeRules == null ) {
            authorizeRules = new ArrayList<FirewallRule>();
        }
        authorizeRules.addAll(rulesToAdd);
        return this;
    }

    /**
     * Adds one or more firewall rule creation options to the list of rules that should be created at the
     * same time as the firewall. This method is additive, meaning it will add to any rule options set in
     * prior calls.
     *
     * @param options one or more firewall rule options to be used in authorizing rules during the firewall create process
     * @return this
     */
    public @Nonnull FirewallCreateOptions havingInitialRules( @Nonnull FirewallRuleCreateOptions... options ) {
        if( initialRules == null ) {
            initialRules = new ArrayList<FirewallRuleCreateOptions>();
        }
        Collections.addAll(initialRules, options);
        return this;
    }

    /**
     * Adds a VLAN onto the set of parameters with which the firewall will be created.
     *
     * @param vlanId the unique ID of the VLAN with which the firewall will be associated on creation
     * @return this
     */
    public @Nonnull FirewallCreateOptions inVlanId( @Nonnull String vlanId ) {
        providerVlanId = vlanId;
        return this;
    }

    /**
     * Adds a constraint value to a constrained firewall. All rules must match the specified value when added.
     *
     * @param constraint the rule field being constrained
     * @param value      the value to which it is constrained
     * @return this
     */
    public @Nonnull FirewallCreateOptions withConstraint( @Nonnull FirewallConstraints.Constraint constraint, @Nonnull Object value ) {
        if( constraints == null ) {
            constraints = new HashMap<FirewallConstraints.Constraint, Object>();
        }
        constraints.put(constraint, value);
        return this;
    }

    /**
     * Specifies any meta-data to be associated with the firewall when created. This method is
     * accretive, meaning that it adds to any existing meta-data (or replaces an existing key).
     *
     * @param key   the key of the meta-data entry
     * @param value the value for the meta-data entry
     * @return this
     */
    public @Nonnull FirewallCreateOptions withMetaData( @Nonnull String key, @Nonnull String value ) {
        if( metaData == null ) {
            metaData = new HashMap<String, String>();
        }
        metaData.put(key, value);
        return this;
    }

    /**
     * Specifies meta-data to add onto any existing meta-data being associated with this firewall when created.
     * This method is accretive, meaning that it adds to any existing meta-data (or replaces an existing keys).
     *
     * @param metaData the meta-data to be set for the new firewall
     * @return this
     */
    public @Nonnull FirewallCreateOptions withMetaData( @Nonnull Map<String, String> metaData ) {
        if( this.metaData == null ) {
            this.metaData = new HashMap<String, String>();
        }
        this.metaData.putAll(metaData);
        return this;
    }
}
