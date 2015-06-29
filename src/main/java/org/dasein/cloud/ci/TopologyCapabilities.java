package org.dasein.cloud.ci;

import javax.annotation.Nonnull;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.util.NamingConstraints;

public interface TopologyCapabilities {

    /**
     * Identifies the naming conventions that constrain how topologies(replica pool templates) may be named (friendly name) in this cloud.
     * @return naming conventions that constrain topologies(replica pool templates) naming
     * @throws CloudException an error occurred in the cloud identifying this capability
     * @throws InternalException an error occurred within the Dasein Cloud implementation identifying this capability
     */
    public @Nonnull NamingConstraints getTopologyNamingConstraints() throws CloudException, InternalException;
}
