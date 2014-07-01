/**
 * Copyright (C) 2009-2014 Dell, Inc.
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

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Options for creating Spot VM Requests.
 *
 * @author Drew Lyall
 * @author Stas Maksimov
 * @version 2014.07 reduced number of parameters in getInstance to mandatory only
 * @since 2014.05
 */
public class SpotVirtualMachineRequestCreateOptions {
    private String                        machineImageId;
    private String                        standardProductId;
    private int                           vmCount;
    private float                         maximumPrice;
    private String                        launchGroup;
    private long                          validFromTimestamp;
    private long                          validUntilTimestamp;
    private SpotVirtualMachineRequestType type;
    private String                        providerSubnetId;
    private boolean                       autoAssignIp;
    private String                        roleId;
    private boolean                       monitoring;
    private String                        userData;
    private String                        bootstrapKey;

    /**
     * Provides options for creating a Spot VM Request
     *
     * @return an object representing the options for creating a Spot VM
     */
    public static @Nonnull SpotVirtualMachineRequestCreateOptions getInstance( @Nonnull String standardProductId, @Nonnull String machineImageId, @Nonnegative float maximumPrice ) {
        SpotVirtualMachineRequestCreateOptions opts = new SpotVirtualMachineRequestCreateOptions();
        opts.maximumPrice = maximumPrice;
        opts.machineImageId = machineImageId;
        opts.standardProductId = standardProductId;
        return opts;
    }

    public @Nonnull SpotVirtualMachineRequestCreateOptions withVirtualMachineCount( int vmCount ) {
        this.vmCount = vmCount;
        return this;
    }

    public @Nonnull SpotVirtualMachineRequestCreateOptions inLaunchGroup( @Nonnull String launchGroup ) {
        this.launchGroup = launchGroup;
        return this;
    }

    public @Nonnull SpotVirtualMachineRequestCreateOptions validFrom( @Nonnegative long startTimestamp ) {
        this.validFromTimestamp = startTimestamp;
        return this;
    }

    public @Nonnull SpotVirtualMachineRequestCreateOptions validUntil( @Nonnegative long expiryTimestamp ) {
        this.validUntilTimestamp = expiryTimestamp;
        return this;
    }

    public @Nonnull SpotVirtualMachineRequestCreateOptions ofType( @Nonnull SpotVirtualMachineRequestType type ) {
        this.type = type;
        return this;
    }

    public @Nonnull SpotVirtualMachineRequestCreateOptions inSubnet( @Nonnull String providerSubnetId ) {
        this.providerSubnetId = providerSubnetId;
        return this;
    }

    public @Nonnull SpotVirtualMachineRequestCreateOptions withAutoAssignIp() {
        this.autoAssignIp = true;
        return this;
    }

    public @Nonnull SpotVirtualMachineRequestCreateOptions withRoleId( @Nonnull String roleId ) {
        this.roleId = roleId;
        return this;
    }

    public @Nonnull SpotVirtualMachineRequestCreateOptions withMonitoring() {
        this.monitoring = true;
        return this;
    }

    public @Nonnull SpotVirtualMachineRequestCreateOptions withUserData( @Nonnull String userData ) {
        this.userData = userData;
        return this;
    }

    public @Nonnull SpotVirtualMachineRequestCreateOptions withBootstrapKey( @Nonnull String bootstrapKey ) {
        this.bootstrapKey = bootstrapKey;
        return this;
    }

    public @Nullable String getMachineImageId() {
        return machineImageId;
    }

    public @Nullable String getStandardProductId() {
        return standardProductId;
    }

    public @Nonnegative int getVmCount() {
        return vmCount;
    }

    public @Nonnegative float getMaximumPrice() {
        return maximumPrice;
    }

    public @Nullable String getLaunchGroup() {
        return launchGroup;
    }

    public @Nonnegative long getValidFromTimestamp() {
        return validFromTimestamp;
    }

    public @Nonnegative long getValidUntilTimestamp() {
        return validUntilTimestamp;
    }

    public @Nullable SpotVirtualMachineRequestType getType() {
        return type;
    }

    public @Nullable String getProviderSubnetId() {
        return providerSubnetId;
    }

    public boolean isAutoAssignIp() {
        return autoAssignIp;
    }

    public @Nullable String getRoleId() {
        return roleId;
    }

    public boolean isMonitoring() {
        return monitoring;
    }

    public @Nullable String getUserData() {
        return userData;
    }

    public @Nullable String getBootstrapKey() {
        return bootstrapKey;
    }

    public @Nonnull SpotVirtualMachineRequest build( CloudProvider provider ) throws CloudException, InternalException {
        ComputeServices services = provider.getComputeServices();
        if( services == null ) {
            throw new OperationNotSupportedException(provider.getCloudName() + " does not have support for compute services");
        }

        VirtualMachineSupport support = services.getVirtualMachineSupport();
        if( support == null ) {
            throw new OperationNotSupportedException(provider.getCloudName() + " does not have VM support");
        }

        return support.createSpotVirtualMachineRequest(this);
    }
}
