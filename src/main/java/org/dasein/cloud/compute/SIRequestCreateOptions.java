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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Options for creating Spot Instance Requests.
 * @author Drew Lyall
 * @version 2014.05 initial version
 * @since 2014.05
 */
public class SIRequestCreateOptions{
    private String        providerMachineImageId;
    private String        providerProductId;
    private int           instanceCount;
    private double        maximumPrice;
    private String        launchGroup;
    private long          startDateTime;
    private long          expiryDateTime;
    private SIRequestType type;
    private String        providerVlanId;
    private String        providerSubnetId;
    private boolean       isAutoAssignIP;
    private String        providerIAMRoleId;
    private boolean       isMonitoring;
    private String        userData;

    /**
     * Provides options for creating a Spot Instance Request
     * @return an object representing the options for creating a Spot Instance
     */
    static public SIRequestCreateOptions getInstance(@Nonnull String providerMachineImageId, @Nonnull String providerProductId, int instanceCount, double maximumPrice, @Nullable String launchGroup, long startDateTime, long expiryDateTime, @Nonnull SIRequestType type, @Nonnull String providerVlanId, @Nullable String providerSubnetId, boolean autoAssignIP, @Nullable String providerIAMRoleId, boolean monitoring, @Nullable String userData){
        SIRequestCreateOptions opts = new SIRequestCreateOptions();
        opts.providerMachineImageId = providerMachineImageId;
        opts.providerProductId = providerProductId;
        opts.instanceCount = instanceCount;
        opts.maximumPrice = maximumPrice;
        opts.launchGroup = launchGroup;
        opts.startDateTime = startDateTime;
        opts.expiryDateTime = expiryDateTime;
        opts.type = type;
        opts.providerVlanId = providerVlanId;
        opts.providerSubnetId = providerSubnetId;
        opts.isAutoAssignIP = autoAssignIP;
        opts.providerIAMRoleId = providerIAMRoleId;
        opts.isMonitoring = monitoring;
        opts.userData = userData;

        return opts;
    }

    public String getProviderMachineImageId(){
        return providerMachineImageId;
    }

    public String getProviderProductId(){
        return providerProductId;
    }

    public int getInstanceCount(){
        return instanceCount;
    }

    public double getMaximumPrice(){
        return maximumPrice;
    }

    public String getLaunchGroup(){
        return launchGroup;
    }

    public long getStartDateTime(){
        return startDateTime;
    }

    public long getExpiryDateTime(){
        return expiryDateTime;
    }

    public SIRequestType getType(){
        return type;
    }

    public String getProviderVlanId(){
        return providerVlanId;
    }

    public String getProviderSubnetId(){
        return providerSubnetId;
    }

    public boolean isAutoAssignIP(){
        return isAutoAssignIP;
    }

    public String getProviderIAMRoleId(){
        return providerIAMRoleId;
    }

    public @Nonnull SpotInstanceRequest build(CloudProvider provider)throws CloudException, InternalException{
        ComputeServices services = provider.getComputeServices();
        if(services == null){
            throw new OperationNotSupportedException(provider.getCloudName() + " does not have support for compute services");
        }

        VirtualMachineSupport support = services.getVirtualMachineSupport();
        if(support == null){
            throw new OperationNotSupportedException(provider.getCloudName() + " does not have VM support");
        }

        return support.createSpotInstanceRequest(this);
    }
}
