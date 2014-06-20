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
 * @version 2014.07 replaced take-all getInstance method with a single parameter method taking only mandatory spot price
 * @since 2014.05
 */
public class SpotVirtualMachineRequestCreateOptions {
    private String                        providerMachineImageId;
    private String                        providerProductId;
    private int                           vmCount;
    private float                         maximumPrice;
    private String                        launchGroup;
    private long                          startTimestamp;
    private long                          expiryTimestamp;
    private SpotVirtualMachineRequestType type;
    private String                        providerVlanId;
    private String                        providerSubnetId;
    private boolean                       autoAssignIp;
    private String                        providerIAMRoleId;
    private boolean                       monitoring;
    private String                        userData;

    /**
     * Provides options for creating a Spot VM Request
     *
     * @return an object representing the options for creating a Spot VM
     */
    public static @Nonnull SpotVirtualMachineRequestCreateOptions getInstance( @Nonnegative float maximumPrice ) {
        SpotVirtualMachineRequestCreateOptions opts = new SpotVirtualMachineRequestCreateOptions();
        opts.maximumPrice = maximumPrice;
        return opts;
    }

    public @Nonnull SpotVirtualMachineRequestCreateOptions withProviderMachineImageId( @Nonnull String providerMachineImageId ) {
        this.providerMachineImageId = providerMachineImageId;
        return this;
    }

    public @Nonnull SpotVirtualMachineRequestCreateOptions withProviderProductId( @Nonnull String providerProductId ) {
        this.providerProductId = providerProductId;
        return this;
    }

    public @Nonnull SpotVirtualMachineRequestCreateOptions withVirtualMachineCount( int vmCount ) {
        this.vmCount = vmCount;
        return this;
    }

    public @Nonnull SpotVirtualMachineRequestCreateOptions withLaunchGroup( @Nonnull String launchGroup ) {
        this.launchGroup = launchGroup;
        return this;
    }

    public @Nonnull SpotVirtualMachineRequestCreateOptions withStartTimestamp( @Nonnegative long startTimestamp ) {
        this.startTimestamp = startTimestamp;
        return this;
    }

    public @Nonnull SpotVirtualMachineRequestCreateOptions withExpiryTimestamp( @Nonnegative long expiryTimestamp ) {
        this.expiryTimestamp = expiryTimestamp;
        return this;
    }

    public @Nonnull SpotVirtualMachineRequestCreateOptions withRequestType( @Nonnull SpotVirtualMachineRequestType type ) {
        this.type = type;
        return this;
    }

    public @Nonnull SpotVirtualMachineRequestCreateOptions withProviderVlanId( @Nonnull String providerVlanId ) {
        this.providerVlanId = providerVlanId;
        return this;
    }

    public @Nonnull SpotVirtualMachineRequestCreateOptions withProviderSubnetId( @Nonnull String providerSubnetId ) {
        this.providerSubnetId = providerSubnetId;
        return this;
    }

    public @Nonnull SpotVirtualMachineRequestCreateOptions withAutoAssignIp() {
        this.autoAssignIp = true;
        return this;
    }

    public @Nonnull SpotVirtualMachineRequestCreateOptions withProviderIAMRoleId( @Nonnull String providerIAMRoleId ) {
        this.providerIAMRoleId = providerIAMRoleId;
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

    public @Nullable String getProviderMachineImageId() {
        return providerMachineImageId;
    }

    public @Nullable String getProviderProductId() {
        return providerProductId;
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

    public @Nonnegative long getStartTimestamp() {
        return startTimestamp;
    }

    public @Nonnegative long getExpiryTimestamp() {
        return expiryTimestamp;
    }

    public @Nullable SpotVirtualMachineRequestType getType() {
        return type;
    }

    public @Nullable String getProviderVlanId() {
        return providerVlanId;
    }

    public @Nullable String getProviderSubnetId() {
        return providerSubnetId;
    }

    public boolean isAutoAssignIp() {
        return autoAssignIp;
    }

    public @Nullable String getProviderIAMRoleId() {
        return providerIAMRoleId;
    }

    public boolean isMonitoring() {
        return monitoring;
    }

    public @Nullable String getUserData() {
        return userData;
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
