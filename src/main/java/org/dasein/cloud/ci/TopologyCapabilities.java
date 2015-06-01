package org.dasein.cloud.ci;

import javax.annotation.Nonnull;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.util.NamingConstraints;

public interface TopologyCapabilities {

    public NamingConstraints getTopologyNamingConstraints() throws CloudException, InternalException;
    public @Nonnull TopologyCapabilities getCapabilities() throws CloudException, InternalException;
}
