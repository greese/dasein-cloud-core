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

import javax.annotation.Nonnull;

/**
 * The Spot Instance request as it is held with the cloud provider. As long as this request exists
 * in an active state within the cloud then the provider will spin up instances as the price history allows.
 */
public class SpotInstanceRequest{
    private String        providerSIRequestId;
    private double        spotPrice;
    private SIRequestType type;
    private long          fulfilmentDateTime;
    private String        providerMachineImageId;
    private String        productId;
    private long          createdDateTime;
    private String        fulfiledDataCenterId;
    private String        launchGroup;

    /**
     *
     * @param providerSIRequestId
     * @param spotPrice
     * @param type
     * @param fulfilmentDateTime
     * @param providerMachineImageId
     * @param productId
     * @param createdDateTime
     * @param fulfiledDataCenterId
     * @param launchGroup
     * @return
     */
    public static @Nonnull SpotInstanceRequest getInstance(@Nonnull String providerSIRequestId, double spotPrice, @Nonnull SIRequestType type, long fulfilmentDateTime, @Nonnull String providerMachineImageId, @Nonnull String productId, long createdDateTime, @Nonnull String fulfiledDataCenterId, @Nonnull String launchGroup){
        SpotInstanceRequest sir = new SpotInstanceRequest();
        sir.providerSIRequestId = providerSIRequestId;
        sir.spotPrice = spotPrice;
        sir.type = type;
        sir.fulfilmentDateTime = fulfilmentDateTime;
        sir.providerMachineImageId = providerMachineImageId;
        sir.productId = productId;
        sir.createdDateTime = createdDateTime;
        sir.fulfiledDataCenterId = fulfiledDataCenterId;
        sir.launchGroup = launchGroup;
        return sir;
    }

    public String getProviderSIRequestId(){
        return providerSIRequestId;
    }

    public double getSpotPrice(){
        return spotPrice;
    }

    public SIRequestType getType(){
        return type;
    }

    public long getFulfilmentDateTime(){
        return fulfilmentDateTime;
    }

    public String getProviderMachineImageId(){
        return providerMachineImageId;
    }

    public String getProductId(){
        return productId;
    }

    public long getCreatedDateTime(){
        return createdDateTime;
    }

    public String getFulfiledDataCenterId(){
        return fulfiledDataCenterId;
    }

    public String getLaunchGroup(){
        return launchGroup;
    }
}