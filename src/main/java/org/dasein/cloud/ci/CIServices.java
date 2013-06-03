package org.dasein.cloud.ci;

import javax.annotation.Nullable;

/**
 * [Class Documentation]
 * <p>Created by George Reese: 6/3/13 3:27 PM</p>
 *
 * @author George Reese
 */
public interface CIServices {
    public @Nullable ConvergedInfrastructureSupport getConvergedInfrastructureSupport();

    public @Nullable TopologySupport getTopologySupport();

    public boolean hasConvergedInfrastructureSupport();


    /**
     * @return indicates whether or not the cloud provider supports complex resource topologies
     */
    public abstract boolean hasTopologySupport();
}
