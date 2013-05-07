/**
 * Copyright (C) 2009-2013 Dell, Inc.
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
import java.util.Map;

/**
 * Encapsulates values to be used in the creation of {@link Firewall} instances either for virtual machine or
 * network firewalls.
 * <p>Created by George Reese: 2/4/13 8:43 AM</p>
 * @author George Reese
 * @since 2013.04
 * @version 2013.04 initial version (issue greese/dasein-cloud-aws/#8)
 */
public class FirewallCreateOptions {
    /**
     * Constructs options for creating a firewall having the specified name and description.
     * @param name the name of the firewall to be created
     * @param description a description of the purpose of the firewall to be created
     * @return options for creating the firewall based on the specified parameters
     */
    static public FirewallCreateOptions getInstance(@Nonnull String name, @Nonnull String description) {
        FirewallCreateOptions options = new FirewallCreateOptions();

        options.name = name;
        options.description = description;
        return options;
    }

    /**
     * Constructs options for creating a firewall tied to a specific VLAN having the specified name and description.
     * @param inVlanId the VLAN ID with which the firewall is associated
     * @param name the name of the firewall to be created
     * @param description a description of the purpose of the firewall to be created
     * @return options for creating the firewall based on the specified parameters
     */
    static public FirewallCreateOptions getInstance(@Nonnull String inVlanId, @Nonnull String name, @Nonnull String description) {
        FirewallCreateOptions options = new FirewallCreateOptions();

        options.name = name;
        options.description = description;
        options.providerVlanId = inVlanId;
        return options;
    }

    private String             description;
    private Map<String,String> metaData;
    private String             name;
    private String             providerVlanId;

    private FirewallCreateOptions() { }

    /**
     * Provisions a firewall in the specified cloud based on the options described in this object.
     * @param provider the cloud provider in which the firewall will be created
     * @param asNetworkFirewall if the provisioned firewall should be provisioned as a network firewall
     * @return the unique ID of the firewall that is provisioned
     * @throws CloudException an error occurred with the cloud provider while provisioning the firewall
     * @throws InternalException an internal error occurred within Dasein Cloud while preparing or handling the API call
     * @throws OperationNotSupportedException this cloud does not support firewalls
     */
    public @Nonnull String build(@Nonnull CloudProvider provider, boolean asNetworkFirewall) throws CloudException, InternalException {
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
     * @return text describing the purpose of the firewall
     */
    public @Nonnull String getDescription() {
        return description;
    }

    /**
     * @return any extra meta-data to assign to the firewall
     */
    public @Nonnull Map<String,String> getMetaData() {
        return (metaData == null ? new HashMap<String, String>() : metaData);
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
     * Adds a VLAN onto the set of parameters with which the firewall will be created.
     * @param vlanId the unique ID of the VLAN with which the firewall will be associated on creation
     * @return this
     */
    public @Nonnull FirewallCreateOptions inVlanId(@Nonnull String vlanId) {
        providerVlanId = vlanId;
        return this;
    }

    /**
     * Specifies any meta-data to be associated with the firewall when created. This method is
     * accretive, meaning that it adds to any existing meta-data (or replaces an existing key).
     * @param key the key of the meta-data entry
     * @param value the value for the meta-data entry
     * @return this
     */
    public @Nonnull FirewallCreateOptions withMetaData(@Nonnull String key, @Nonnull String value) {
        if( metaData == null ) {
            metaData = new HashMap<String, String>();
        }
        metaData.put(key, value);
        return this;
    }

    /**
     * Specifies meta-data to add onto any existing meta-data being associated with this firewall when created.
     * This method is accretive, meaning that it adds to any existing meta-data (or replaces an existing keys).
     * @param metaData the meta-data to be set for the new firewall
     * @return this
     */
    public @Nonnull FirewallCreateOptions withMetaData(@Nonnull Map<String,String> metaData) {
        if( this.metaData == null ) {
            this.metaData = new HashMap<String, String>();
        }
        this.metaData.putAll(metaData);
        return this;
    }
}
