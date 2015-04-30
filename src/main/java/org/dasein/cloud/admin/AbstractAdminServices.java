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

package org.dasein.cloud.admin;

import org.dasein.cloud.AbstractProviderService;
import org.dasein.cloud.CloudProvider;

public abstract class AbstractAdminServices<T extends CloudProvider> extends AbstractProviderService<T> implements AdminServices {
    protected AbstractAdminServices(T provider) {
        super(provider);
    }

    @Override
    public PrepaymentSupport getPrepaymentSupport() {
        return null;
    }
    
    @Override
    public boolean hasPrepaymentSupport() {
        return (getPrepaymentSupport() != null);
    }
    
    @Override
    public AccountSupport getAccountSupport() {
        return null;
    }
    
    @Override
    public boolean hasAccountSupport() {
        return (getAccountSupport() != null);
    }
    
    @Override
    public BillingSupport getBillingSupport() {
        return null;
    }
    
    @Override
    public boolean hasBillingSupport() {
        return (getBillingSupport() != null);
    }
}
