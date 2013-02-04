package org.dasein.cloud.network;

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
