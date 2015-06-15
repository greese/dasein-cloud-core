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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The Spot VM request as it is held with the cloud provider. As long as this request exists
 * in an active state within the cloud then the provider will spin up VMs as the price history allows.
 */
public class SpotVirtualMachineRequest {
    private String                        providerSpotVmRequestId;
    private float                         spotPrice;
    private SpotVirtualMachineRequestType type;
    private String                        providerMachineImageId;
    private String                        productId;
    private long                          validUntilTimestamp;
    private long                          validFromTimestamp;
    private long                          createdTimestamp;
    private long                          fulfillmentTimestamp;
    private String                        fulfillmentDataCenterId;
    private String                        launchGroup;

    /**
     * @param providerSpotVmRequestId the id of the request
     * @param spotPrice maximum spot vm price when the request should be fulfilled
     * @param type type of the request
     * @param providerMachineImageId machine image which needs to be used for fulfillment
     * @param productId product which needs to be used for fulfillment
     * @param createdTimestamp when the request has been created
     * @param validFromTimestamp when the request is valid from
     * @param validUntilTimestamp when the request expires
     * @param fulfillmentTimestamp when the request has been fulfilled
     * @param fulfillmentDataCenterId which data center the request has been fulfilled in
     * @param launchGroup launch group used to launch instances together
     * @return a constructed request instance
     */
    public static @Nonnull SpotVirtualMachineRequest getInstance( @Nonnull String providerSpotVmRequestId, @Nonnegative float spotPrice, @Nonnull SpotVirtualMachineRequestType type, @Nonnull String providerMachineImageId, @Nonnull String productId, @Nonnegative long createdTimestamp, @Nonnegative long validFromTimestamp, @Nonnegative long validUntilTimestamp, long fulfillmentTimestamp, @Nullable String fulfillmentDataCenterId, @Nullable String launchGroup ) {
        SpotVirtualMachineRequest sir = new SpotVirtualMachineRequest();
        sir.providerSpotVmRequestId = providerSpotVmRequestId;
        sir.spotPrice = spotPrice;
        sir.type = type;
        sir.providerMachineImageId = providerMachineImageId;
        sir.productId = productId;
        sir.createdTimestamp = createdTimestamp;
        sir.validFromTimestamp = validFromTimestamp;
        sir.validUntilTimestamp = validUntilTimestamp;
        sir.fulfillmentTimestamp = fulfillmentTimestamp;
        sir.fulfillmentDataCenterId = fulfillmentDataCenterId;
        sir.launchGroup = launchGroup;
        return sir;
    }

    public @Nonnull String getProviderSpotVmRequestId() {
        return providerSpotVmRequestId;
    }

    public @Nonnegative float getSpotPrice() {
        return spotPrice;
    }

    public @Nonnull SpotVirtualMachineRequestType getType() {
        return type;
    }

    public long getFulfillmentTimestamp() {
        return fulfillmentTimestamp;
    }

    public @Nonnull String getProviderMachineImageId() {
        return providerMachineImageId;
    }

    public @Nonnull String getProductId() {
        return productId;
    }

    public @Nonnegative long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public @Nullable String getFulfillmentDataCenterId() {
        return fulfillmentDataCenterId;
    }

    public @Nullable String getLaunchGroup() {
        return launchGroup;
    }

    public @Nonnegative long getValidUntilTimestamp() {
        return validUntilTimestamp;
    }

    public @Nonnegative long getValidFromTimestamp() {
        return validFromTimestamp;
    }
}
