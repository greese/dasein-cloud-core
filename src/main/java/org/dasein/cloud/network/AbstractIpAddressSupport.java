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

import org.dasein.cloud.*;
import org.dasein.cloud.identity.ServiceAction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by stas on 19/02/2015.
 */
public abstract class AbstractIpAddressSupport<T extends CloudProvider> implements IpAddressSupport {
    private T provider;

    public AbstractIpAddressSupport(@Nonnull T provider) {
        this.provider = provider;
    }

    /**
     * @return the current authentication context for any calls through this support object
     * @throws org.dasein.cloud.CloudException no context was set
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
    protected final @Nonnull T getProvider() {
        return provider;
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
    @Deprecated
    public @Nonnull Iterable<IpForwardingRule> listRules(@Nonnull String addressId) throws InternalException, CloudException{
        return listRules(addressId, null);
    }

    @Override
    public @Nonnull Iterable<IpForwardingRule> listRules(@Nullable String addressId, @Nullable String serverId) throws InternalException, CloudException{
        return Collections.emptyList();
    }

    @Override
    @Deprecated
    public void stopForward(@Nonnull String ruleId) throws InternalException, CloudException{
        stopForward(ruleId, null);
    }

    @Override
    public void stopForward(@Nullable String ruleId, @Nullable String serverId) throws InternalException, CloudException{
        throw new OperationNotSupportedException("Removing forwarding rules is not currently implemented for " + getContext().getRegionId() + " of " + getProvider().getCloudName());
    }

}
