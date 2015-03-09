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
import org.dasein.cloud.Tag;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Options for creating subnets of in a network.
 * <p>Created by George Reese: 1/29/13 9:32 AM</p>
 * @author George Reese
 * @version 2013.04 initial version
 * @since 2013.04
 */
public class SubnetCreateOptions {
    /**
     * Creation options for the most basic approach to creating a subnet. Because no data center is specified, either the
     * cloud or the Dasein Cloud implementation will randomly pick a data center (if appropriate).
     * @param inVlanId the VLAN in which the subnet will be created
     * @param cidr a CIDR that is a subnet the addressing of the parent VLAN indicating the address space for the subnet
     * @param name a user-friendly name for the new subnet
     * @param description a description of the subnet's purpose
     * @return options for creating a subnet with the specified parameters
     */
    static public @Nonnull SubnetCreateOptions getInstance(@Nonnull String inVlanId, @Nonnull String cidr, @Nonnull String name, @Nonnull String description) {
        SubnetCreateOptions options = new SubnetCreateOptions();

        options.providerVlanId = inVlanId;
        options.cidr = cidr;
        options.name = name;
        options.description = description;
        return options;
    }

    /**
     * Creation options for creating a subnet in a specific data center.
     * @param inVlanId the VLAN in which the subnet will be created
     * @param inDcId the data center in which the subnet will be created
     * @param cidr a CIDR that is a subnet the addressing of the parent VLAN indicating the address space for the subnet
     * @param name a user-friendly name for the new subnet
     * @param description a description of the subnet's purpose
     * @return options for creating a subnet with the specified parameters
     */
    static public @Nonnull SubnetCreateOptions getInstance(@Nonnull String inVlanId, @Nonnull String inDcId, @Nonnull String cidr, @Nonnull String name, @Nonnull String description) {
        SubnetCreateOptions options = new SubnetCreateOptions();

        options.providerVlanId = inVlanId;
        options.providerDataCenterId = inDcId;
        options.cidr = cidr;
        options.name = name;
        options.description = description;
        return options;
    }

    /**
     * Creation options for creating a subnet in a specific data center with pre-defined meta-data.
     * @param inVlanId the VLAN in which the subnet will be created
     * @param inDcId the data center in which the subnet will be created
     * @param cidr a CIDR that is a subnet the addressing of the parent VLAN indicating the address space for the subnet
     * @param name a user-friendly name for the new subnet
     * @param description a description of the subnet's purpose
     * @param tags any meta-data to assign to the subnet
     * @return options for creating a subnet with the specified parameters
     */
    static public @Nonnull SubnetCreateOptions getInstance(@Nonnull String inVlanId, @Nonnull String inDcId, @Nonnull String cidr, @Nonnull String name, @Nonnull String description, @Nonnull Tag ... tags) {
        SubnetCreateOptions options = new SubnetCreateOptions();

        options.providerVlanId = inVlanId;
        options.providerDataCenterId = inDcId;
        options.cidr = cidr;
        options.name = name;
        options.description = description;
        options.metaData = new HashMap<String, Object>();
        for( Tag t : tags ) {
            options.metaData.put(t.getKey(), t.getValue());
        }
        return options;
    }

    private String             cidr;
    private String             description;
    private Map<String,Object> metaData;
    private String             name;
    private String             providerDataCenterId;
    private String             providerVlanId;
    private IPVersion[]        supportedTraffic;

    private SubnetCreateOptions() { }

    /**
     * Provisions a subnet in the specified cloud based on the options in this object.
     * @param provider the cloud provider in which the subnet will be provisioned
     * @return the unique ID of the subnet that was provisioned
     * @throws CloudException an error occurred in the cloud while provisioning the subnet
     * @throws InternalException an error occurred in the Dasein Cloud implementation while preparing or handling the API call
     * @throws OperationNotSupportedException subnets are not supported in the specified cloud
     */
    public @Nonnull String build(@Nonnull CloudProvider provider) throws CloudException, InternalException {
        NetworkServices services = provider.getNetworkServices();

        if( services == null ) {
            throw new OperationNotSupportedException(provider.getCloudName() + " does not support network services");
        }
        VLANSupport support = services.getVlanSupport();

        if( support == null ) {
            throw new OperationNotSupportedException(provider.getCloudName() + " does not support subnets");
        }
        return support.createSubnet(this).getProviderSubnetId();
    }

    /**
     * @return the address space into which resources for the subnet to be created will be placed
     */
    public @Nonnull String getCidr() {
        return cidr;
    }

    /**
     * @return a description of the purpose of the subnet to be created
     */
    public @Nonnull String getDescription() {
        return description;
    }

    /**
     * @return any custom meta-data associated with the subnet to be created
     */
    public @Nonnull Map<String,Object> getMetaData() {
        return (metaData == null ? new HashMap<String, Object>() : metaData);
    }

    /**
     * @return the name of the subnet to be created
     */
    public @Nonnull String getName() {
        return name;
    }

    /**
     * @return the data center into which the subnet to be created will be placed (or any if <code>null</code>)
     */
    public @Nullable String getProviderDataCenterId() {
        return providerDataCenterId;
    }

    /**
     * @return the VLAN to which the new subnet will belong
     */
    public @Nonnull String getProviderVlanId() {
        return providerVlanId;
    }

    /**
     * @return the type of traffic supported over this subnet
     */
    public @Nonnull IPVersion[] getSupportedTraffic() {
        if( supportedTraffic == null || supportedTraffic.length < 1 ) {
            return new IPVersion[] { IPVersion.IPV4 };
        }
        return supportedTraffic;
    }

    /**
     * Adds meta-data to associate with the subnet to be created. This method is additive; it will not wipe existing
     * meta-data.
     * @param metaData the meta-data to add
     * @return this
     */
    public @Nonnull SubnetCreateOptions withMetaData(@Nonnull Map<String,Object> metaData) {
        if( this.metaData == null ) {
            this.metaData = new HashMap<String, Object>();
        }
        this.metaData.putAll(metaData);
        return this;
    }

    /**
     * Defines the supported traffic over this subnet.
     * @param versions the version or versions supported
     * @return this
     */
    public @Nonnull SubnetCreateOptions withSupportedTraffic(@Nonnull IPVersion ... versions) {
        supportedTraffic = versions;
        return this;
    }
}
