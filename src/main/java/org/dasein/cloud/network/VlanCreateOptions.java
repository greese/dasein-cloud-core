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
 * Encapsulates values to be used in the creation of {@link org.dasein.cloud.network.VLAN} instances.
 * <p>Created by George Reese: 2/4/13 8:43 AM</p>
 * @author Chris Kelner chris.kelner@weather.com - github.com/ckelner
 * @since 2013.03
 * @version 2013.04 initial
 */
public class VlanCreateOptions {
    /**
     * Constructs options for creating a vlan having the specified name, description and cidr.
     * @param name the name of the vlan to be created
     * @param description a description of the purpose of the vlan to be created
     * @param cidr a subnet for the vlan to be created
     * @return options for creating the vlan based on the specified parameters
     */
    static public VlanCreateOptions getInstance(@Nonnull String name, @Nonnull String description, @Nonnull String cidr,
                                                @Nullable String domain, @Nullable String[] dnsServers,
                                                @Nullable String[] ntpServers) {
        VlanCreateOptions options = new VlanCreateOptions();

        options.name = name;
        options.description = description;
        options.cidr = cidr;
        options.domain = domain;
        options.dnsServers = dnsServers;
        options.ntpServers = ntpServers;
        return options;
    }

    private String             description;
    private String             cidr;
    private String             name;
    private String           domain;
    private String[]           dnsServers;
    private String[]           ntpServers;

    private VlanCreateOptions() { }

    /**
     * Provisions a firewall in the specified cloud based on the options described in this object.
     * @param provider the cloud provider in which the firewall will be created
     * @return the unique ID of the vlan that is provisioned
     * @throws org.dasein.cloud.CloudException an error occurred with the cloud provider while provisioning the vlan
     * @throws org.dasein.cloud.InternalException an internal error occurred within Dasein Cloud while preparing or handling the API call
     * @throws org.dasein.cloud.OperationNotSupportedException this cloud does not support vlan
     */
    public @Nonnull String build(@Nonnull CloudProvider provider) throws CloudException, InternalException {
        NetworkServices services = provider.getNetworkServices();

        if( services == null ) {
            throw new OperationNotSupportedException(provider.getCloudName() + " does not support network services");
        }
        else {
            VLANSupport support = services.getVlanSupport();

            if( support == null ) {
                throw new OperationNotSupportedException(provider.getCloudName() + " does not have support for vlans");
            }
            return support.createVlan(this).getProviderVlanId();
        }
    }

    /**
     * @return text describing the purpose of the vlan
     */
    public @Nonnull String getDescription() {
        return description;
    }

    /**
     * @return the friendly name for the vlan
     */
    public @Nonnull String getName() {
        return name;
    }

    /**
     * @return the cidr for the vlan
     */
    public @Nonnull String getCidr() {
      return cidr;
    }

    /**
     * @return the domain, if any, for the vlan
     */
    public @Nullable String getDomain() {
      return domain;
    }

    /**
     * @return the NTP servers, if any, for the vlan
     */
    public @Nullable String[] getNtpServers() {
      return ntpServers;
    }

    /**
     * @return the DNS servers, if any, for the vlan
     */
    public @Nullable String[] getDnsServers() {
      return dnsServers;
    }

}
