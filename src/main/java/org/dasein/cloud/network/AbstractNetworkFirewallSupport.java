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
import java.util.*;

/**
 * Bare-bones implementation of network firewall support with nothing enabled.
 * <p>Created by George Reese: 2/4/13 9:27 AM</p>
 * @author George Reese
 * @since 2013.04
 * @version 2013.04 (issue greese/dasein-cloud-aws/#8)
 * @version 2014.03 (issue #99)
 */
public abstract class AbstractNetworkFirewallSupport<T extends CloudProvider> extends AbstractProviderService<T> implements NetworkFirewallSupport {
    protected AbstractNetworkFirewallSupport(T provider) {
        super(provider);
    }

    @Override
    public void associateWithSubnet(@Nonnull String firewallId, @Nonnull String withSubnetId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Subnet association is not implemented in " + getProvider().getCloudName());
    }

    @Override
    public @Nonnull String authorize(@Nonnull String firewallId, @Nonnull Direction direction, @Nonnull Permission permission, @Nonnull RuleTarget sourceEndpoint, @Nonnull Protocol protocol, @Nonnull RuleTarget destinationEndpoint, int beginPort, int endPort, @Nonnegative int precedence) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Authorization of " + direction + "/" + permission + " in " + getProvider().getCloudName() + " is not currently implemented");
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
    public @Nonnull String createFirewall(@Nonnull FirewallCreateOptions options) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Network firewall creation is not currently implemented in " + getProvider().getCloudName());
    }


    @Override
    public @Nullable Map<FirewallConstraints.Constraint, Object> getActiveConstraintsForFirewall(@Nonnull String firewallId) throws CloudException, InternalException {
        HashMap<FirewallConstraints.Constraint, Object> active = new HashMap<FirewallConstraints.Constraint, Object>();
        FirewallConstraints fields = getFirewallConstraintsForCloud();

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
    public Firewall getFirewall(@Nonnull String firewallId) throws InternalException, CloudException {
        for( Firewall firewall : listFirewalls() ) {
            if( firewallId.equals(firewall.getProviderFirewallId()) ) {
                return firewall;
            }
        }
        return null;
    }

    @Override
    public @Nonnull FirewallConstraints getFirewallConstraintsForCloud() throws InternalException, CloudException {
        return FirewallConstraints.getInstance();
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

    @Override
    public void setTags( @Nonnull String[] firewallIds, @Nonnull Tag... tags ) throws CloudException, InternalException {
        for( String id : firewallIds ) {

            Tag[] collectionForDelete = TagUtils.getTagsForDelete(getFirewall(id).getTags(), tags);

            if( collectionForDelete.length != 0 ) {
                removeTags(id, collectionForDelete);
            }

            updateTags(id, tags);
        }
    }

    @Override
    public void setTags( @Nonnull String firewallId, @Nonnull Tag... tags ) throws CloudException, InternalException {
        setTags(new String[]{firewallId}, tags);
    }

}
