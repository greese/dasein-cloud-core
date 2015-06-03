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

import javax.annotation.Nullable;

import org.dasein.cloud.compute.HttpLoadBalancerSupport;

/**
 * Compute services define how a consuming application interacts with cloud compute as a service resources like
 * virtual machines, volumes, and images/templates. To use this (or any other services class), you call one of
 * the <code>getXXXSupport()</code> methods to find the support class for a specific resource. If the method
 * returns null, the cloud does not have support for that kind of resource.
 * @version 2013.02 added javadoc
 * @since unknown
 */
public interface ComputeServices {
    /**
     * @return access to support for affinity groups in the cloud provider
     */
    public @Nullable AffinityGroupSupport getAffinityGroupSupport();

    /**
     * @return access to support for auto-scaling capabilities native to the cloud provider
     */
    public @Nullable AutoScalingSupport getAutoScalingSupport();

    /**
     * @return access to support for images/templates in the cloud provider
     */
    public @Nullable MachineImageSupport getImageSupport();

    /**
     * @return access to support for volume snapshots in the cloud provider
     */
    public @Nullable SnapshotSupport getSnapshotSupport();

    /**
     * @return access to support for virtual machines in the cloud provider
     */
    public @Nullable VirtualMachineSupport getVirtualMachineSupport();

    /**
     * @return access to support for volumes in the cloud provider
     */
    public @Nullable VolumeSupport getVolumeSupport();

    /**
     * @return indicates whether or not the cloud provider supports affinity groups
     */
    public boolean hasAffinityGroupSupport();

    /**
     * @return indicates whether or not the cloud provider supports native auto-scaling capabilities
     */
    public boolean hasAutoScalingSupport();

    /**
     * @return indicates whether or not the cloud provider supports images/templates
     */
    public boolean hasImageSupport();

    /**
     * @return indicates whether or not the cloud provider supports snapshotting volumes
     */
    public boolean hasSnapshotSupport();

    /**
     * @return indicates whether or not the cloud provider supports virtual machines
     */
    public boolean hasVirtualMachineSupport();

    /**
     * @return indicates whether or not the cloud provider supports block or network volumes
     */
    public boolean hasVolumeSupport();
}
