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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Basic implementation of firewall support methods to minimize the work in implementing support in a new cloud.
 * <p>Created by George Reese: 2/1/13 8:40 AM</p>
 * @author George Reese
 * @since 2013.04
 * @version 2013.04
 */
public abstract class AbstractFirewallSupport implements FirewallSupport {
    private CloudProvider provider;

    public AbstractFirewallSupport(@Nonnull CloudProvider provider) {
        this.provider = provider;
    }

    @Override
    @Deprecated
    public final @Nonnull String authorize(@Nonnull String firewallId, @Nonnull String source, @Nonnull Protocol protocol, int beginPort, int endPort) throws CloudException, InternalException {
        return authorize(firewallId, Direction.INGRESS, Permission.ALLOW, RuleTarget.getCIDR(source), protocol, RuleTarget.getGlobal(firewallId), beginPort, endPort, 0);

    }

    @Override
    @Deprecated
    public final @Nonnull String authorize(@Nonnull String firewallId, @Nonnull Direction direction, @Nonnull String source, @Nonnull Protocol protocol, int beginPort, int endPort) throws CloudException, InternalException {
        if( direction.equals(Direction.INGRESS) ) {
            return authorize(firewallId, direction, Permission.ALLOW, RuleTarget.getCIDR(source), protocol, RuleTarget.getGlobal(firewallId), beginPort, endPort, 0);
        }
        else {
            return authorize(firewallId, direction, Permission.ALLOW, RuleTarget.getGlobal(firewallId), protocol, RuleTarget.getCIDR(source), beginPort, endPort, 0);
        }
    }

    @Override
    @Deprecated
    public final @Nonnull String authorize(@Nonnull String firewallId, @Nonnull Direction direction, @Nonnull Permission permission, @Nonnull String source, @Nonnull Protocol protocol, int beginPort, int endPort) throws CloudException, InternalException {
        if( direction.equals(Direction.INGRESS) ) {
            return authorize(firewallId, direction, permission, RuleTarget.getCIDR(source), protocol, RuleTarget.getGlobal(firewallId), beginPort, endPort, 0);
        }
        else {
            return authorize(firewallId, direction, permission, RuleTarget.getGlobal(firewallId), protocol, RuleTarget.getCIDR(source), beginPort, endPort, 0);
        }
    }

    @Override
    @Deprecated
    public final @Nonnull String authorize(@Nonnull String firewallId, @Nonnull Direction direction, @Nonnull Permission permission, @Nonnull String source, @Nonnull Protocol protocol, @Nonnull RuleTarget target, int beginPort, int endPort) throws CloudException, InternalException {
        if( direction.equals(Direction.INGRESS) ) {
            return authorize(firewallId, direction, permission, RuleTarget.getCIDR(source), protocol, target, beginPort, endPort, 0);
        }
        else {
            return authorize(firewallId, direction, permission, target, protocol, RuleTarget.getCIDR(source), beginPort, endPort, 0);
        }
    }

    @Override
    public @Nonnull String authorize(@Nonnull String firewallId, @Nonnull Direction direction, @Nonnull Permission permission, @Nonnull RuleTarget sourceEndpoint, @Nonnull Protocol protocol, @Nonnull RuleTarget destinationEndpoint, int beginPort, int endPort, @Nonnegative int precedence) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Authorization is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public @Nonnull String create(@Nonnull String name, @Nonnull String description) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Firewall creation is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public @Nonnull String createInVLAN(@Nonnull String name, @Nonnull String description, @Nonnull String providerVlanId) throws InternalException, CloudException {
        throw new OperationNotSupportedException("VLAN firewall creation is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public @Nullable
    Firewall getFirewall(@Nonnull String firewallId) throws InternalException, CloudException {
        for( Firewall fw : list() ) {
            if( firewallId.equals(fw.getProviderFirewallId()) ) {
                return fw;
            }
        }
        return null;
    }

    /**
     * @return the current authentication context for any calls through this support object
     * @throws CloudException no context was set
     */
    protected @Nonnull ProviderContext getContext() throws CloudException {
        ProviderContext ctx = getProvider().getContext();

        if( ctx == null ) {
            throw new CloudException("No context was specified for this request");
        }
        return ctx;
    }

    /**
     * @return the provider object associated with any calls through this support object
     */
    protected final @Nonnull CloudProvider getProvider() {
        return provider;
    }

    @Override
    public @Nonnull Collection<FirewallRule> getRules(@Nonnull String firewallId) throws InternalException, CloudException {
        return Collections.emptyList();
    }

    @Override
    public @Nonnull Requirement identifyPrecedenceRequirement(boolean inVlan) throws InternalException, CloudException {
        return Requirement.NONE;
    }

    @Override
    public boolean isZeroPrecedenceHighest() throws InternalException, CloudException {
        return true;
    }

    @Override
    public @Nonnull Iterable<ResourceStatus> listFirewallStatus() throws InternalException, CloudException {
        ArrayList<ResourceStatus> status = new ArrayList<ResourceStatus>();

        for( Firewall fw : list() ) {
            //noinspection ConstantConditions
            status.add(new ResourceStatus(fw.getProviderFirewallId(), true));
        }
        return status;
    }

    @Override
    public void removeTags(@Nonnull String volumeId, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void removeTags(@Nonnull String[] volumeIds, @Nonnull Tag ... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void revoke(@Nonnull String providerFirewallRuleId) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Revoke is not currently implemented in " + getProvider().getCloudName());
    }

    @Override
    public void revoke(@Nonnull String firewallId, @Nonnull String source, @Nonnull Protocol protocol, int beginPort, int endPort) throws CloudException, InternalException {
        revoke(firewallId, Direction.INGRESS, Permission.ALLOW, source, protocol,  RuleTarget.getGlobal(firewallId), beginPort, endPort);
    }

    @Override
    public void revoke(@Nonnull String firewallId, @Nonnull Direction direction, @Nonnull String source, @Nonnull Protocol protocol, int beginPort, int endPort) throws CloudException, InternalException {
        revoke(firewallId, direction, Permission.ALLOW, source, protocol,  RuleTarget.getGlobal(firewallId), beginPort, endPort);
    }

    @Override
    public void revoke(@Nonnull String firewallId, @Nonnull Direction direction, @Nonnull Permission permission, @Nonnull String source, @Nonnull Protocol protocol, int beginPort, int endPort) throws CloudException, InternalException {
        revoke(firewallId, direction, permission, source, protocol,  RuleTarget.getGlobal(firewallId), beginPort, endPort);
    }

    @Override
    public void revoke(@Nonnull String firewallId, @Nonnull Direction direction, @Nonnull Permission permission, @Nonnull String source, @Nonnull Protocol protocol, @Nonnull RuleTarget target, int beginPort, int endPort) throws CloudException, InternalException {
        for( FirewallRule rule : getRules(firewallId) ) {
            if( !rule.getDirection().equals(direction) ) {
                continue;
            }
            if( !rule.getPermission().equals(permission) ) {
                continue;
            }
            if( !rule.getProtocol().equals(protocol) ) {
                continue;
            }
            if( rule.getStartPort() != beginPort || rule.getEndPort() != endPort ) {
                continue;
            }

            if( direction.equals(Direction.INGRESS) ) {
                RuleTarget se = rule.getSourceEndpoint();

                if( !se.getRuleTargetType().equals(RuleTargetType.CIDR) ) {
                    continue;
                }
                if( !source.equals(se.getCidr()) ) {
                    continue;
                }
            }
            else {
                RuleTarget de = rule.getDestinationEndpoint();

                if( !de.getRuleTargetType().equals(RuleTargetType.CIDR) ) {
                    continue;
                }
                if( !source.equals(de.getCidr()) ) {
                    continue;
                }
            }
            RuleTarget ruleEndpoint;
            RuleTargetType type;

            if( direction.equals(Direction.INGRESS) ) {
                ruleEndpoint = rule.getDestinationEndpoint();
            }
            else {
                ruleEndpoint = rule.getSourceEndpoint();
            }
            type = ruleEndpoint.getRuleTargetType();

            if( !type.equals(target.getRuleTargetType()) ) {
                continue;
            }
            switch( type ) {
                case CIDR:
                    //noinspection ConstantConditions
                    if( !target.getCidr().equals(ruleEndpoint.getCidr()) ) { continue; }
                    break;
                case VLAN:
                    //noinspection ConstantConditions
                    if( !target.getProviderVlanId().equals(ruleEndpoint.getProviderVlanId()) ) { continue; }
                    break;
                case VM:
                    //noinspection ConstantConditions
                    if( !target.getProviderVirtualMachineId().equals(ruleEndpoint.getProviderVirtualMachineId()) ) { continue; }
                    break;
                case GLOBAL:
                    //noinspection ConstantConditions
                    if( !target.getProviderFirewallId().equals(ruleEndpoint.getProviderFirewallId()) ) { continue; }
                    break;
                default:
                    throw new CloudException("Unknown rule target type: " + type);

            }
            revoke(rule.getProviderRuleId());
            return;
        }
    }

    @Override
    public boolean supportsRules(@Nonnull Direction direction, @Nonnull Permission permission, boolean inVlan) throws CloudException, InternalException {
        return (direction.equals(Direction.INGRESS) && permission.equals(Permission.ALLOW) && !inVlan);
    }

    @Override
    public boolean supportsFirewallCreation(boolean inVlan) throws CloudException, InternalException {
        return !inVlan;
    }

    @Override
    public boolean supportsFirewallSources() throws CloudException, InternalException {
        return false;
    }

    @Override
    public @Nonnull String[] mapServiceAction(@Nonnull ServiceAction action) {
        return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public void updateTags(@Nonnull String volumeId, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void updateTags(@Nonnull String[] volumeIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }
}
