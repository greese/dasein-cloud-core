package org.dasein.cloud.ci;

import javax.annotation.Nullable;

/**
 * [Class Documentation]
 * <p>Created by George Reese: 6/3/13 3:48 PM</p>
 *
 * @author George Reese
 */
public abstract class AbstractCIServices implements CIServices {
    @Override
    public @Nullable ConvergedInfrastructureSupport getConvergedInfrastructureSupport() {
        return null;
    }

    @Override
    public @Nullable TopologySupport getTopologySupport() {
        return null;
    }

    @Override
    public boolean hasConvergedInfrastructureSupport() {
        return (getConvergedInfrastructureSupport() == null);
    }

    @Override
    public boolean hasTopologySupport() {
        return (getTopologySupport() == null);
    }
}
