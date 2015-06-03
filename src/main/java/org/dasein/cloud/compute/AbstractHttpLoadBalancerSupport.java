package org.dasein.cloud.compute;

import javax.annotation.Nonnull;

import org.dasein.cloud.CloudProvider;

public abstract class AbstractHttpLoadBalancerSupport<T extends CloudProvider> implements HttpLoadBalancerSupport {
    private T provider;

    public AbstractHttpLoadBalancerSupport( @Nonnull T provider ) {
        this.provider = provider;
    }
}