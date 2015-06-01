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

package org.dasein.cloud.ci;

import org.dasein.cloud.AbstractProviderService;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.ResourceStatus;
import org.dasein.cloud.Tag;
import org.dasein.cloud.identity.ServiceAction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Implements the most basic operations for a cloud with converged infrastructure support with no public topology library. Concrete
 * classes for specific clouds should override the public searching methods if those cloud support a public topology library
 * as well as the methods left unimplemented by this class. In addition, this class provides no-OP implementations of
 * access control and tagging.
 * <p>Created by George Reese: 5/31/13 9:40 AM</p>
 * @author George Reese
 * @version 2013.07 initial version
 * @since 2013.07
 */
public abstract class AbstractTopologySupport<T extends CloudProvider> extends AbstractProviderService<T> implements TopologySupport {

    protected AbstractTopologySupport(T provider) {
        super(provider);
    }

    @Override
    public @Nullable Topology getTopology(@Nonnull String topologyId) throws CloudException, InternalException {
        for( Topology t : listTopologies(null) ) {
            if( t.getProviderTopologyId().equals(topologyId) ) {
                return t;
            }
        }
        return null;
    }

    @Override
    public @Nonnull Iterable<ResourceStatus> listTopologyStatus() throws InternalException, CloudException {
        ArrayList<ResourceStatus> status = new ArrayList<ResourceStatus>();

        for( Topology topology : listTopologies(null) ) {
            status.add(new ResourceStatus(topology.getProviderTopologyId(), topology.getCurrentState()));
        }
        return status;
    }

    @Override
    public @Nonnull String[] mapServiceAction(@Nonnull ServiceAction action) {
        return new String[0];
    }

    @Override
    public @Nonnull Iterable<Topology> searchPublicTopologies(@Nullable TopologyFilterOptions options) throws CloudException, InternalException {
        return Collections.emptyList();
    }

    @Override
    public boolean supportsPublicLibrary() throws CloudException, InternalException {
        return false;
    }

    @Override
    public void updateTags(@Nonnull String topologyId, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void updateTags(@Nonnull String[] topologyIds, @Nonnull Tag ... tags) throws CloudException, InternalException {
        for( String id : topologyIds ) {
            updateTags(id, tags);
        }
    }

    @Override
    public void removeTags(@Nonnull String topologyId, @Nonnull Tag ... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void removeTags(@Nonnull String[] topologyIds, @Nonnull Tag ... tags) throws CloudException, InternalException {
        for( String id : topologyIds ) {
            removeTags(id, tags);
        }
    }

    @Override
    public boolean createTopology(@Nonnull TopologyProvisionOptions withTopologyOptions) throws CloudException, InternalException {
        throw new InternalException("Operation not supported for this cloud");
    }

    @Override
    public boolean removeTopologies(@Nonnull String[] topologyIds) throws CloudException, InternalException {
        throw new InternalException("Operation not supported for this cloud");
    }

    public @Nonnull TopologyCapabilities getCapabilities() throws CloudException, InternalException{
        return null;
    }

}
