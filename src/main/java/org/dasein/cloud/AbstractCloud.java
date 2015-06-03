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
import javax.annotation.Nullable;

import org.dasein.cloud.admin.AdminServices;
import org.dasein.cloud.ci.CIServices;
import org.dasein.cloud.compute.ComputeServices;
import org.dasein.cloud.identity.IdentityServices;
import org.dasein.cloud.network.NetworkServices;
import org.dasein.cloud.platform.PlatformServices;
/**
 * Simple base implementation of a cloud provider bootstrap object that defaults all services to <code>null</code>.
 * @author George Reese
 * @version 2013.07 added javadoc, fixed annotations on data center services, made it return an NPE
 * @since unknown
 */
public abstract class AbstractCloud extends CloudProvider {
    /**
     * Constructs a cloud provider instance.
     */
    public AbstractCloud() { }

    @Override
    public @Nullable AdminServices getAdminServices() {
        return null;
    }
    
    @Override
    public @Nullable ComputeServices getComputeServices() {
        CloudProvider compute = getComputeCloud();
        
        return (compute == null ? null : compute.getComputeServices());
    }

    @Override
    public @Nonnull ContextRequirements getContextRequirements() {
        return new ContextRequirements(
                new ContextRequirements.Field("apiKeys", ContextRequirements.FieldType.KEYPAIR),
                new ContextRequirements.Field("x509", ContextRequirements.FieldType.KEYPAIR, false)
        );
    }

    @Override
    public @Nullable CIServices getCIServices() {
        CloudProvider compute = getComputeCloud();

        return (compute == null ? null : compute.getCIServices());
    }
    
    @Override
    public @Nullable IdentityServices getIdentityServices() {
        CloudProvider compute = getComputeCloud();
        
        return (compute == null ? null : compute.getIdentityServices());
    }
    
    @Override
    public @Nullable NetworkServices getNetworkServices() {
        CloudProvider compute = getComputeCloud();
        
        return (compute == null ? null : compute.getNetworkServices());
    }
    
    @Override
    public @Nullable PlatformServices getPlatformServices() {
        CloudProvider compute = getComputeCloud();
        
        return (compute == null ? null : compute.getPlatformServices());
    }
}
