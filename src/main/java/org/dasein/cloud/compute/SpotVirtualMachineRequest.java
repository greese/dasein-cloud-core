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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * The Spot VM request as it is held with the cloud provider. As long as this request exists
 * in an active state within the cloud then the provider will spin up VMs as the price history allows.
 */
public class SpotVirtualMachineRequest {
    private String                  providerSpotVmRequestId;
    private String                  spotPrice;
    private SpotVirtualMachineRequestType type;
    private long                    fulfillmentTimestamp;
    private String                  providerMachineImageId;
    private String                  productId;
    private long                    createdTimestamp;
    private String                  fulfillmentDataCenterId;
    private String                  launchGroup;

    /**
     *
     * @param providerSpotVmRequestId
     * @param spotPrice
     * @param type
     * @param fulfillmentTimestamp
     * @param providerMachineImageId
     * @param productId
     * @param createdTimestamp
     * @param fulfillmentDataCenterId
     * @param launchGroup
     * @return
     */
    public static @Nonnull SpotVirtualMachineRequest getInstance(@Nonnull String providerSpotVmRequestId, @Nonnull String spotPrice, @Nonnull SpotVirtualMachineRequestType type, @Nonnegative long fulfillmentTimestamp, @Nonnull String providerMachineImageId, @Nonnull String productId, @Nonnegative long createdTimestamp, @Nonnull String fulfillmentDataCenterId, @Nonnull String launchGroup){
        SpotVirtualMachineRequest sir = new SpotVirtualMachineRequest();
        sir.providerSpotVmRequestId = providerSpotVmRequestId;
        sir.spotPrice = spotPrice;
        sir.type = type;
        sir.fulfillmentTimestamp = fulfillmentTimestamp;
        sir.providerMachineImageId = providerMachineImageId;
        sir.productId = productId;
        sir.createdTimestamp = createdTimestamp;
        sir.fulfillmentDataCenterId = fulfillmentDataCenterId;
        sir.launchGroup = launchGroup;
        return sir;
    }

    public String getProviderSpotVmRequestId(){
        return providerSpotVmRequestId;
    }

    public String getSpotPrice(){
        return spotPrice;
    }

    public SpotVirtualMachineRequestType getType(){
        return type;
    }

    public long getFulfillmentDateTime(){
        return fulfillmentTimestamp;
    }

    public String getProviderMachineImageId(){
        return providerMachineImageId;
    }

    public String getProductId(){
        return productId;
    }

    public long getCreatedTimestamp(){
        return createdTimestamp;
    }

    public String getFulfilledDataCenterId(){
        return fulfillmentDataCenterId;
    }

    public String getLaunchGroup(){
        return launchGroup;
    }
}
