/**
 * Copyright (C) 2009-2013 Dell, Inc.
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

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.compute.ComputeServices;

import javax.annotation.Nonnull;

/**
 * Options for provisioning infrastructure from topologies.
 * <p>Created by George Reese: 5/31/13 9:30 AM</p>
 * @author George Reese
 * @version 2013.07 initial version
 * @since 2013.07
 */
public class TopologyProvisionOptions {
    static public @Nonnull TopologyProvisionOptions getInstance(@Nonnull String topologyId) {
        TopologyProvisionOptions options = new TopologyProvisionOptions();

        options.topologyId = topologyId;
        return options;
    }

    private String topologyId;

    private TopologyProvisionOptions() { }

    /**
     * Triggers a call to provision from the topology based on the current state of the topology provisioning options.
     * @param provider the cloud provider in which to provision
     * @return the result of the attempt to provision from the topology
     * @throws CloudException an error occurred in the cloud during the provisioning operation
     * @throws InternalException an error occurred within Dasein Cloud attempting to execute the request
     */
    public @Nonnull
    ConvergedInfrastructure build(@Nonnull CloudProvider provider) throws CloudException, InternalException {
        ComputeServices compute = provider.getComputeServices();

        if( compute == null ) {
            throw new CloudException("Compute services are not supported in " + provider.getCloudName());
        }
        ConvergedInfrastructureSupport support = compute.getTopologySupport();

        if( support == null ) {
            throw new CloudException("Topologies are not supported in " + provider.getCloudName());
        }
        return support.provision(this);
    }

    /**
     * @return the topology from which an infrastructure is being provisioned
     */
    public @Nonnull String getTopologyId() {
        return topologyId;
    }

    @Override
    public @Nonnull String toString() {
        return topologyId;
    }
}
