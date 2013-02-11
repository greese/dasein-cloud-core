package org.dasein.cloud.network;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.ProviderContext;
import org.dasein.cloud.Requirement;
import org.dasein.cloud.ResourceStatus;
import org.dasein.cloud.Tag;
import org.dasein.cloud.identity.ServiceAction;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Bare-bones implementation of network firewall support with nothing enabled.
 * <p>Created by George Reese: 2/4/13 9:27 AM</p>
 * @author George Reese
 * @since 2013.04
 * @version 2013.04 (issue greese/dasein-cloud-aws/#8)
 */
public abstract class AbstractNetworkFirewallSupport implements NetworkFirewallSupport {
    private CloudProvider provider;

    public AbstractNetworkFirewallSupport(@Nonnull CloudProvider provider)  {
        this.provider = provider;
    }

    @Override
    public void associateWithSubnet(@Nonnull String firewallId, @Nonnull String withSubnetId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Subnet association is not implemented in " + getProvider().getCloudName());
    }

    @Override
    public @Nonnull String authorize(@Nonnull String firewallId, @Nonnull Direction direction, @Nonnull Permission permission, @Nonnull RuleTarget sourceEndpoint, @Nonnull Protocol protocol, @Nonnull RuleTarget destinationEndpoint, int beginPort, int endPort, int precedence) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Authorization of " + direction + "/" + permission + " in " + getProvider().getCloudName() + " is not currently implemented");
    }

    @Override
    public @Nonnull String createFirewall(@Nonnull FirewallCreateOptions options) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Network firewall creation is not currently implemented in " + getProvider().getCloudName());
    }

    protected @Nonnull ProviderContext getContext() throws CloudException {
        ProviderContext ctx = getProvider().getContext();

        if( ctx == null ) {
            throw new CloudException("No context was provided for this request");
        }
        return ctx;
    }

    @Override
    public Firewall getFirewall(@Nonnull String firewallId) throws InternalException, CloudException {
        for( Firewall firewall : listFirewalls() ) {
            if( firewallId.equals(firewall.getProviderFirewallId()) ) {
                return firewall;
            }
        }
        return null;
    }

    protected @Nonnull CloudProvider getProvider() {
        return provider;
    }

    @Override
    public @Nonnull Requirement identifyPrecedenceRequirement() throws InternalException, CloudException {
        return Requirement.REQUIRED;
    }

    @Override
    public boolean isZeroPrecedenceHighest() throws InternalException, CloudException {
        return true;
    }

    @Override
    public @Nonnull Iterable<ResourceStatus> listFirewallStatus() throws InternalException, CloudException {
        ArrayList<ResourceStatus> status = new ArrayList<ResourceStatus>();

        for( Firewall firewall : listFirewalls() ) {
            String id = firewall.getProviderFirewallId();

            if( id != null ) {
                status.add(new ResourceStatus(id, firewall.isActive()));
            }
        }
        return status;
    }

    @Override
    public @Nonnull Iterable<RuleTargetType> listSupportedDestinationTypes() throws InternalException, CloudException {
        ArrayList<RuleTargetType> types = new ArrayList<RuleTargetType>();

        Collections.addAll(types, RuleTargetType.values());
        return types;
    }

    @Override
    public @Nonnull Iterable<Direction> listSupportedDirections() throws InternalException, CloudException {
        ArrayList<Direction> directions = new ArrayList<Direction>();

        Collections.addAll(directions, Direction.values());
        return directions;
    }

    @Override
    public @Nonnull Iterable<Permission> listSupportedPermissions() throws InternalException, CloudException {
        ArrayList<Permission> permissions = new ArrayList<Permission>();

        Collections.addAll(permissions, Permission.values());
        return permissions;
    }

    @Override
    public @Nonnull Iterable<RuleTargetType> listSupportedSourceTypes() throws InternalException, CloudException {
        ArrayList<RuleTargetType> types = new ArrayList<RuleTargetType>();

        Collections.addAll(types, RuleTargetType.values());
        return types;
    }

    @Override
    public @Nonnull String[] mapServiceAction(@Nonnull ServiceAction action) {
        return new String[0];
    }

    @Override
    public void removeTags(@Nonnull String firewallId, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void removeTags(@Nonnull String[] vmIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        for( String id : vmIds ) {
            removeTags(id, tags);
        }
    }

    @Override
    public void revoke(@Nonnull String providerFirewallRuleId) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Revoking rules is not currently implemented in " + getProvider().getCloudName());
    }

    @Override
    public boolean supportsNetworkFirewallCreation() throws CloudException, InternalException {
        return true;
    }

    @Override
    public void updateTags(@Nonnull String firewallId, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void updateTags(@Nonnull String[] vmIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        for( String id : vmIds ) {
            updateTags(id, tags);
        }
    }
}
