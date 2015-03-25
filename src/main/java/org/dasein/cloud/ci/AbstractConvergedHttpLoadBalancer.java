package org.dasein.cloud.ci;

import java.util.ArrayList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.ProviderContext;
import org.dasein.cloud.ResourceStatus;

public abstract class AbstractConvergedHttpLoadBalancer<T extends CloudProvider> implements ConvergedHttpLoadBalancer {
    private T provider;

    public AbstractConvergedHttpLoadBalancer(@Nonnull T provider) {
        this.provider = provider;
    }

    protected final @Nonnull T getProvider() {
        return provider;
    }

    protected final @Nonnull ProviderContext getContext() throws CloudException {
        ProviderContext ctx = getProvider().getContext();

        if( ctx == null ) {
            throw new CloudException("No context was specified for this request");
        }
        return ctx;
    }

    @Override
    public @Nullable ConvergedHttpLoadBalancer getConvergedHttpLoadBalancer(@Nonnull String convergedHttpLoadBalancerId) throws CloudException, InternalException {
        for( ConvergedHttpLoadBalancer c : listConvergedHttpLoadBalancers(null) ) {
            if( c.getConvergedHttpLoadBalancerId().equals(convergedHttpLoadBalancerId) ) {
                return c;
            }
        }
        return null;
    }

    @Override
    public @Nonnull Iterable<ResourceStatus> listConvergedHttpLoadBalancerStatus() throws InternalException, CloudException {
        ArrayList<ResourceStatus> status = new ArrayList<ResourceStatus>();

        for( ConvergedHttpLoadBalancer c : listConvergedHttpLoadBalancers(null) ) {
            status.add(new ResourceStatus(c.getConvergedHttpLoadBalancerId(), c.getCurrentState()));
        }
        return status;
    }

    @Override
    public boolean createConvergedHttpLoadBalancer(@Nonnull ConvergedHttpLoadbalancerOptions withConvergedHttpLoadBalancerOptions) throws CloudException, InternalException {
        throw new InternalException("Operation not supported for this cloud");
    }

    @Override
    public boolean removeConvergedHttpLoadBalancers(@Nonnull String[] convergedHttpLoadBalancerIds) throws CloudException, InternalException {
        throw new InternalException("Operation not supported for this cloud");
    }

}
