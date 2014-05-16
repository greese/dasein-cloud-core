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
 * The history of the price of Spot Instances for a specified datacenter and VM Product type.
 */
public class SpotPriceHistory{
    private long startDateTime;
    private long stopDateTime;
    private String providerDataCenterId;
    private String productId;
    private SpotPrice[] priceHistory;

    /**
     * Creates a new instance of a Spot Price History Object.
     * @param startDateTime the start time for the earliest price entry
     * @param stopDateTime the end time for the most latest price entry
     * @param providerDataCenterId the datacenter being queried
     * @param productId the product type being queried
     * @param priceHistory an array showing price history records of timestamp against price
     * @return
     */
    public static SpotPriceHistory getInstance(long startDateTime, long stopDateTime, @Nonnull String providerDataCenterId, @Nonnull String productId, @Nonnull SpotPrice[] priceHistory){
        SpotPriceHistory sph = new SpotPriceHistory();
        sph.startDateTime = startDateTime;
        sph.stopDateTime = stopDateTime;
        sph.providerDataCenterId = providerDataCenterId;
        sph.productId = productId;
        sph.priceHistory = priceHistory;
        return sph;
    }

    public long getStartDateTime(){
        return startDateTime;
    }

    public long getStopDateTime(){
        return stopDateTime;
    }

    public String getProviderDataCenterId(){
        return providerDataCenterId;
    }

    public String getProductId(){
        return productId;
    }

    public SpotPrice[] getPriceHistory(){
        return priceHistory;
    }
}
