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

import org.dasein.cloud.AbstractProviderService;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.ProviderContext;
import org.dasein.cloud.Requirement;
import org.dasein.cloud.ResourceStatus;
import org.dasein.cloud.Tag;
import org.dasein.cloud.identity.ServiceAction;
import org.dasein.cloud.util.TagUtils;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Basic implementation of firewall support methods to minimize the work in implementing support in a new cloud.
 * <p>Created by George Reese: 2/1/13 8:40 AM</p>
 * @author George Reese
 * @since 2013.04
 * @version 2013.04
 * @version 2014.03 added support for authorizing with rule create options
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class AbstractFirewallSupport<T extends CloudProvider> extends AbstractProviderService<T> implements FirewallSupport {
    protected AbstractFirewallSupport(T provider) {
        super(provider);
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
    public @Nonnull String authorize(@Nonnull String firewallId, @Nonnull FirewallRuleCreateOptions options) throws CloudException, InternalException {
        RuleTarget source = options.getSourceEndpoint();
        RuleTarget dest = options.getDestinationEndpoint();

        if( source == null ) {
            source = RuleTarget.getGlobal(firewallId);
        }
        if( dest == null ) {
            dest = RuleTarget.getGlobal(firewallId);
        }
        return authorize(firewallId, options.getDirection(), options.getPermission(), source, options.getProtocol(), dest, options.getPortRangeStart(), options.getPortRangeEnd(), options.getPrecedence());
    }

    @Override
    @Deprecated
    public @Nonnull String create(@Nonnull String name, @Nonnull String description) throws InternalException, CloudException {
        return create(FirewallCreateOptions.getInstance(name, description));

    }

    @Override
    public @Nonnull String create(@Nonnull FirewallCreateOptions options) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Firewall creation is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    @Deprecated
    public @Nonnull String createInVLAN(@Nonnull String name, @Nonnull String description, @Nonnull String providerVlanId) throws InternalException, CloudException {
        return create(FirewallCreateOptions.getInstance(providerVlanId, name, description));
    }

    @Override
    public @Nullable Map<FirewallConstraints.Constraint, Object> getActiveConstraintsForFirewall(@Nonnull String firewallId) throws CloudException, InternalException {
        HashMap<FirewallConstraints.Constraint, Object> active = new HashMap<FirewallConstraints.Constraint, Object>();
        FirewallConstraints fields = getCapabilities().getFirewallConstraintsForCloud();

        if( fields.isOpen() ) {
            return active;
        }
        Firewall firewall = getFirewall(firewallId);

        if( firewall == null ) {
            return null;
        }
        for( FirewallConstraints.Constraint c : fields.getConstraints() ) {
            FirewallConstraints.Level l = fields.getConstraintLevel(c);

            if( !l.equals(FirewallConstraints.Level.NOT_CONSTRAINED) ) {

                 active.put(c, c.getValue(getProvider(), firewallId));
            }
        }
        return active;
    }

    @Override
    public @Nullable Firewall getFirewall(@Nonnull String firewallId) throws InternalException, CloudException {
        for( Firewall fw : list() ) {
            if( firewallId.equals(fw.getProviderFirewallId()) ) {
                return fw;
            }
        }
        return null;
    }

    @Override
    @Deprecated
    public @Nonnull FirewallConstraints getFirewallConstraintsForCloud() throws InternalException, CloudException {
        return getCapabilities().getFirewallConstraintsForCloud();
    }

    @Override
    public @Nonnull String getProviderTermForFirewall(@Nonnull Locale locale) {
        try {
            return getCapabilities().getProviderTermForFirewall(locale);
        }
        catch( CloudException e ) {
            throw new RuntimeException("Unexpected problem with capabilities", e);
        }
        catch( InternalException e ) {
            throw new RuntimeException("Unexpected problem with capabilities", e);
        }
    }

    @Override
    public @Nonnull Iterable<FirewallRule> getRules(@Nonnull String firewallId) throws InternalException, CloudException {
        return Collections.emptyList();
    }

    @Override
    @Deprecated
    public @Nonnull Requirement identifyPrecedenceRequirement(boolean inVlan) throws InternalException, CloudException {
        return getCapabilities().identifyPrecedenceRequirement(inVlan);
    }

    @Override
    @Deprecated
    public boolean isZeroPrecedenceHighest() throws InternalException, CloudException {
        return getCapabilities().isZeroPrecedenceHighest();
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

    @Deprecated
    public @Nonnull Iterable<RuleTargetType> listSupportedDestinationTypes(boolean inVlan) throws InternalException, CloudException {
        return getCapabilities().listSupportedDestinationTypes(inVlan);
    }

    @Deprecated
    public @Nonnull Iterable<Direction> listSupportedDirections(boolean inVlan) throws InternalException, CloudException {
        return getCapabilities().listSupportedDirections(inVlan);
    }

    @Override
    @Deprecated
    public @Nonnull Iterable<Permission> listSupportedPermissions(boolean inVlan) throws InternalException, CloudException {
        return getCapabilities().listSupportedPermissions(inVlan);
    }

    @Override
    @Deprecated
    public @Nonnull Iterable<RuleTargetType> listSupportedSourceTypes(boolean inVlan) throws InternalException, CloudException {
        return getCapabilities().listSupportedSourceTypes(inVlan);
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
    public void removeTags(@Nonnull String[] firewallIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        for( String id : firewallIds ) {
            removeTags(id, tags);
        }
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
    @Deprecated
    public boolean supportsRules(@Nonnull Direction direction, @Nonnull Permission permission, boolean inVlan) throws CloudException, InternalException {
        return getCapabilities().supportsRules(direction, permission, inVlan);
    }

    @Override
    @Deprecated
    public boolean supportsFirewallCreation(boolean inVlan) throws CloudException, InternalException {
        return getCapabilities().supportsFirewallCreation(inVlan);
    }

    @Override
    @Deprecated
    public boolean requiresRulesOnCreation() throws CloudException, InternalException{
        return getCapabilities().requiresRulesOnCreation();
    }

    @Override
    @Deprecated
    public boolean supportsFirewallDeletion() throws CloudException, InternalException {
        return getCapabilities().supportsFirewallDeletion();
    }

    @Override
    @Deprecated
    public boolean supportsFirewallSources() throws CloudException, InternalException {
        return false;
    }

    @Override
    public void updateTags(@Nonnull String firewallId, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void updateTags(@Nonnull String[] firewallIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        for( String id : firewallIds ) {
            updateTags(id, tags);
        }
    }

    @Override
    public void setTags( @Nonnull String firewallId, @Nonnull Tag... tags ) throws CloudException, InternalException {
        setTags(new String[]{firewallId}, tags);
    }

    @Override
    public void setTags( @Nonnull String[] firewallIds, @Nonnull Tag... tags ) throws CloudException, InternalException {
        for( String id : firewallIds ) {
            Tag[] collectionForDelete = TagUtils.getTagsForDelete(getFirewall(id).getTags(), tags);

            if( collectionForDelete.length != 0) {
                removeTags(id, collectionForDelete);
            }

            updateTags(id, tags);
        }
    }

}
