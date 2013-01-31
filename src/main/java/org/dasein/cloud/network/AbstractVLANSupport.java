package org.dasein.cloud.network;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.ProviderContext;
import org.dasein.cloud.Requirement;
import org.dasein.cloud.ResourceStatus;

import javax.annotation.Nonnull;
import java.util.ArrayList;

/**
 * Provides baseline support for functionality that is common among implementations, in particular for deprecated methods.
 * <p>Created by George Reese: 1/29/13 9:56 AM</p>
 * @author George Reese
 * @version 2013.04
 * @since 2013.04
 */
public abstract class AbstractVLANSupport implements VLANSupport {
    private CloudProvider provider;

    public AbstractVLANSupport(@Nonnull CloudProvider provider) {
        this.provider = provider;
    }

    @Override
    @Deprecated
    public @Nonnull Subnet createSubnet(@Nonnull String cidr, @Nonnull String inProviderVlanId, @Nonnull String name, @Nonnull String description) throws CloudException, InternalException {
        return createSubnet(SubnetCreateOptions.getInstance(inProviderVlanId, cidr, name, description));
    }

    protected @Nonnull ProviderContext getContext() throws CloudException {
        ProviderContext ctx = getProvider().getContext();

        if( ctx == null ) {
            throw new CloudException("No context was set for this request");
        }
        return ctx;
    }

    protected final @Nonnull CloudProvider getProvider() {
        return provider;
    }

    @Override
    public @Nonnull Requirement identifySubnetDCRequirement() {
        return Requirement.NONE;
    }

    @Override
    public @Nonnull Iterable<ResourceStatus> listVlanStatus() throws CloudException, InternalException {
        ArrayList<ResourceStatus> status = new ArrayList<ResourceStatus>();

        for( VLAN vlan : listVlans() ) {
            status.add(new ResourceStatus(vlan.getProviderVlanId(), vlan.getCurrentState()));
        }
        return status;
    }
}
