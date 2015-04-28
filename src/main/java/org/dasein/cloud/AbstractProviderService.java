package org.dasein.cloud;

import javax.annotation.Nonnull;

/**
 * Created by stas on 28/04/2015.
 */
public abstract class AbstractProviderService<T extends CloudProvider> {
    private T provider;

    protected AbstractProviderService(T provider) {
        this.provider = provider;
    }

    protected final @Nonnull T getProvider() {
        return provider;
    }

    /**
     * @return the current authentication context for any calls through this support object
     * @throws InternalException no context was set
     */
    protected final @Nonnull ProviderContext getContext() throws InternalException {
        ProviderContext ctx = getProvider().getContext();
        if( ctx == null ) {
            throw new InternalException("No context was specified for this request");
        }
        return ctx;
    }
}
