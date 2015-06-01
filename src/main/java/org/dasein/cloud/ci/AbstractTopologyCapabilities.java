package org.dasein.cloud.ci;

import org.dasein.cloud.AbstractProviderService;
import org.dasein.cloud.CloudProvider;


public abstract class AbstractTopologyCapabilities<T extends CloudProvider> extends AbstractProviderService<T> implements TopologyCapabilities {

    protected AbstractTopologyCapabilities(T provider) {
        super(provider);

    }

    
}
