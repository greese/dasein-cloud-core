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
import org.dasein.cloud.ProviderContext;
import org.dasein.cloud.Requirement;
import org.dasein.cloud.Tag;
import org.dasein.cloud.*;
import org.dasein.cloud.identity.ServiceAction;
import org.dasein.cloud.util.TagUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by stas on 19/02/2015.
 */
public abstract class AbstractIpAddressSupport<T extends CloudProvider> extends AbstractProviderService<T> implements IpAddressSupport {

    protected AbstractIpAddressSupport(T provider) {
        super(provider);
    }

    @Override
    @Deprecated
    public @Nonnull String getProviderTermForIpAddress(@Nonnull Locale locale) {
        try {
            return getCapabilities().getProviderTermForIpAddress(locale);
        }
        catch( CloudException e ) {
            throw new RuntimeException("Unexpected problem with capabilities", e);
        }
        catch( InternalException e ) {
            throw new RuntimeException("Unexpected problem with capabilities", e);
        }
    }

    @Override
    @Deprecated
    public @Nonnull Requirement identifyVlanForVlanIPRequirement() throws CloudException, InternalException {
        return getCapabilities().identifyVlanForVlanIPRequirement();
    }

    @Override
    @Deprecated
    public boolean isAssigned(@Nonnull AddressType type) {
        return AddressType.PUBLIC.equals(type);
    }

    @Override
    @Deprecated
    public boolean isAssigned(@Nonnull IPVersion version) throws CloudException, InternalException {
        return getCapabilities().isAssigned(version);
    }

    @Override
    @Deprecated
    public boolean isAssignablePostLaunch(@Nonnull IPVersion version) throws CloudException, InternalException {
        return getCapabilities().isAssignablePostLaunch(version);
    }

    @Override
    @Deprecated
    public boolean isForwarding() {
        throw new RuntimeException("This operation is not supported and is deprecated");
    }

    @Override
    @Deprecated
    public boolean isForwarding(IPVersion version) throws CloudException, InternalException {
        return getCapabilities().isForwarding(version);
    }

    @Override
    @Deprecated
    public boolean isRequestable(@Nonnull AddressType type) {
        return AddressType.PUBLIC.equals(type);
    }

    @Override
    @Deprecated
    public boolean isRequestable(@Nonnull IPVersion version) throws CloudException, InternalException {
        return getCapabilities().isRequestable(version);
    }

    @Override
    @Deprecated
    public @Nonnull Iterable<IpAddress> listPrivateIpPool(boolean unassignedOnly) throws InternalException, CloudException {
        return Collections.emptyList();
    }

    @Override
    @Deprecated
    public Iterable<IpAddress> listPublicIpPool(boolean unassignedOnly) throws InternalException, CloudException {
        return listIpPool(IPVersion.IPV4, unassignedOnly);
    }

    private static final ExecutorService taskPool = Executors.newCachedThreadPool();

    @Nonnull
    @Override
    public Future<Iterable<IpAddress>> listIpPoolConcurrently(final @Nonnull IPVersion version, final boolean unassignedOnly) throws InternalException, CloudException {
        return taskPool.submit(new Callable<Iterable<IpAddress>>() {
            @Override
            public Iterable<IpAddress> call() throws Exception {
                return listIpPool(version, unassignedOnly);
            }
        });
    }

    @Override
    @Deprecated
    public Iterable<IPVersion> listSupportedIPVersions() throws CloudException, InternalException {
        return getCapabilities().listSupportedIPVersions();
    }

    @Override
    @Deprecated
    public @Nonnull String request(@Nonnull AddressType typeOfAddress) throws InternalException, CloudException {
        return request(IPVersion.IPV4);
    }

    @Override
    @Deprecated
    public boolean supportsVLANAddresses(@Nonnull IPVersion ofVersion) throws InternalException, CloudException {
        return getCapabilities().supportsVLANAddresses(ofVersion);
    }

    @Override
    public @Nonnull String[] mapServiceAction(@Nonnull ServiceAction action) {
        return new String[0];
    }
    
    @Override
    public void removeTags(@Nonnull String addressId, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void removeTags(@Nonnull String[] addressIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        for( String id : addressIds ) {
            removeTags(id, tags);
        }
    }
    
    @Override
    public void updateTags(@Nonnull String addressId, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void updateTags(@Nonnull String[] addressIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        for( String id : addressIds ) {
            updateTags(id, tags);
        }
    }

    @Override
    public void setTags( @Nonnull String addressId, @Nonnull Tag... tags ) throws CloudException, InternalException {
        setTags(new String[]{addressId}, tags);
    }

    @Override
    public void setTags( @Nonnull String[] addressIds, @Nonnull Tag... tags ) throws CloudException, InternalException {
        for( String id : addressIds ) {
            Tag[] collectionForDelete = TagUtils.getTagsForDelete(getIpAddress(id).getTags(), tags);

            if( collectionForDelete.length != 0) {
                removeTags(id, collectionForDelete);
            }

            updateTags(id, tags);
        }
    }

    @Override
    public @Nonnull Iterable<IpForwardingRule> listRules(@Nonnull String addressId) throws InternalException, CloudException{
        return Collections.emptyList();
    }

    @Override
    public @Nonnull Iterable<IpForwardingRule> listRulesForServer(@Nonnull String serverId) throws InternalException, CloudException{
        return Collections.emptyList();
    }

    @Override
    public void stopForward(@Nonnull String ruleId) throws InternalException, CloudException{
        throw new OperationNotSupportedException("Removing forwarding rules is not currently implemented for " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

    @Override
    public void stopForwardToServer(@Nonnull String ruleId, @Nonnull String serverId) throws InternalException, CloudException{
        throw new OperationNotSupportedException("Removing forwarding rules is not currently implemented for " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

}
