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

package org.dasein.cloud.compute;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.identity.ServiceAction;

import javax.annotation.Nonnull;

/**
 * Basic default implementations of all AffinityGroupSupport methods
 * Created by Drew Lyall: 16/07/14 15:43
 * @author Drew Lyall
 * @version 2014.08
 * @since 2014.08
 */
public class AbstractAffinityGroupSupport<T extends CloudProvider> implements AffinityGroupSupport{
    private T provider;

    protected final @Nonnull T getProvider() {
        return provider;
    }

    public AbstractAffinityGroupSupport(@Nonnull T provider) {
        this.provider = provider;
    }

    @Override
    public @Nonnull AffinityGroup create(@Nonnull AffinityGroupCreateOptions options) throws InternalException, CloudException{
        throw new OperationNotSupportedException("Affinity Groups cannot be created in " + provider.getCloudName());
    }

    @Override
    public void delete(@Nonnull String affinityGroupId) throws InternalException, CloudException{
        throw new OperationNotSupportedException("Affinity Groups cannot be deleted in " + provider.getCloudName());
    }

    @Override
    public @Nonnull AffinityGroup get(@Nonnull String affinityGroupId) throws InternalException, CloudException{
        throw new OperationNotSupportedException("Affinity Groups are not supported in " + provider.getCloudName());
    }

    @Override
    public @Nonnull Iterable<AffinityGroup> list(@Nonnull AffinityGroupFilterOptions options) throws InternalException, CloudException{
        throw new OperationNotSupportedException("Affinity Groups are not supported in " + provider.getCloudName());
    }

    @Override
    public AffinityGroup modify(@Nonnull String affinityGroupId, @Nonnull AffinityGroupCreateOptions options) throws InternalException, CloudException{
        throw new OperationNotSupportedException("Affinity Groups cannot be modified in " + provider.getCloudName());
    }

    @Override
    public @Nonnull String[] mapServiceAction(@Nonnull ServiceAction action) {
        return new String[0];
    }
}
