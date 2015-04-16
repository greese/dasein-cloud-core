package org.dasein.cloud.ci;

import java.util.ArrayList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.ProviderContext;
import org.dasein.cloud.ResourceStatus;

public abstract class AbstractConvergedHttpLoadBalancer<T extends CloudProvider> implements ConvergedHttpLoadBalancerSupport {
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
    public @Nullable ConvergedHttpLoadBalancer getConvergedHttpLoadBalancer(@Nonnull String convergedHttpLoadBalancerName) throws CloudException, InternalException {
        throw new InternalException("Operation not supported for this cloud");
    }

    @Override
    public @Nonnull Iterable<ResourceStatus> listConvergedHttpLoadBalancerStatus() throws InternalException, CloudException {
        throw new InternalException("Operation not supported for this cloud");
    }

    @Override
    public String createConvergedHttpLoadBalancer(@Nonnull ConvergedHttpLoadbalancerOptions withConvergedHttpLoadBalancerOptions) throws CloudException, InternalException {
        throw new InternalException("Operation not supported for this cloud");
    }

    @Override
    public void removeConvergedHttpLoadBalancers(@Nonnull String globalForwardingRuleName) throws CloudException, InternalException {
        throw new InternalException("Operation not supported for this cloud");
    }

}
