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

package org.dasein.cloud.compute;

import org.dasein.cloud.AbstractProviderService;
import org.dasein.cloud.CloudProvider;

import javax.annotation.Nullable;

/**
 * Skeleton implementation of compute services with a default behavior of supporting no services. Override those services
 * you wish to provide in support of the cloud you are implementing.
 * @author George Reese
 * @version 2013.07 added topology support
 * @since unknown
 */
public abstract class AbstractComputeServices<T extends CloudProvider> extends AbstractProviderService<T> implements ComputeServices {
    protected AbstractComputeServices(T provider) {
        super(provider);
    }

    @Override
    public @Nullable AffinityGroupSupport getAffinityGroupSupport(){
        return null;
    }

    @Override
    public @Nullable  AutoScalingSupport getAutoScalingSupport() {
        return null;
    }

    @Override
    public @Nullable MachineImageSupport getImageSupport() {
        return null;
    }

    @Override
    public @Nullable SnapshotSupport getSnapshotSupport() {
        return null;
    }

    @Override
    public @Nullable VirtualMachineSupport getVirtualMachineSupport() {
        return null;
    }

    @Override
    public @Nullable VolumeSupport getVolumeSupport() {
        return null;
    }

    @Override
    public boolean hasAffinityGroupSupport(){
        return (getAffinityGroupSupport() != null);
    }

    @Override
    public boolean hasAutoScalingSupport() {
        return (getAutoScalingSupport() != null);
    }

    @Override
    public boolean hasImageSupport() {
        return (getImageSupport() != null);
    }

    @Override
    public boolean hasSnapshotSupport() {
        return (getSnapshotSupport() != null);
    }

    @Override
    public boolean hasVirtualMachineSupport() {
        return (getVirtualMachineSupport() != null);
    }

    @Override
    public boolean hasVolumeSupport() {
        return (getVolumeSupport() != null);
    }
}
