/**
 * Copyright (C) 2009-2015 Dell, Inc.
 * See annotations for authorship information
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */

package org.dasein.cloud;

import javax.annotation.Nonnull;

/**
 * Abstract implementation of the {@link Capabilities} interface with useful helper methods.
 * <p>Created by George Reese: 2/27/14 4:37 PM</p>
 * @author George Reese
 * @version 2014.03 initial version
 * @since 2014.03
 */
public abstract class AbstractCapabilities<T extends CloudProvider> implements Capabilities {
    private T provider;

    public AbstractCapabilities(@Nonnull T provider) {
        this.provider = provider;
    }

    @Override
    public @Nonnull String getAccountNumber() {
        return getContext().getAccountNumber();
    }

    protected final @Nonnull ProviderContext getContext() {
        return provider.getContext();
    }

    protected final @Nonnull T getProvider() {
        return provider;
    }

    public @Nonnull String getRegionId() {
        return getContext().getRegionId();
    }
}
