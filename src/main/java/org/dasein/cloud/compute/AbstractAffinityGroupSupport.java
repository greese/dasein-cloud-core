package org.dasein.cloud.compute;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.identity.ServiceAction;

import javax.annotation.Nonnull;

/**
 * Basic default implemetations of all AffinityGroupSupport methods
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
