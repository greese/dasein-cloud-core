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

/**
 * Options for filtering spot price history records when querying the cloud provider. You may optionally filter on
 * any or all set criteria. If a value has not been set for a specific criterion, it is not included in the filtering
 * process.
 *
 * @author Drew Lyall
 * @version 2014.05 initial version
 * @since 2014.05
 */
public class SpotPriceHistoryFilterOptions {
    /**
     * Constructs an empty set of filtering options that will force match against any History Record by default.
     *
     * @return an empty filtering options objects
     */
    static public @Nonnull SpotPriceHistoryFilterOptions getInstance() {
        return new SpotPriceHistoryFilterOptions(false);
    }

    /**
     * Constructs filter options that will match either any criteria or all criteria, but has no actual criteria
     * associated with it.
     *
     * @param matchesAny <code>true</code> if it is sufficient that just one of the criteria are matched, false if all are needed to be matched
     * @return a newly constructed set of Spot History filtering options
     */
    static public @Nonnull SpotPriceHistoryFilterOptions getInstance( boolean matchesAny ) {
        return new SpotPriceHistoryFilterOptions(matchesAny);
    }

    private String[] dataCenterIds;
    private String[] productIds;
    private boolean  matchesAny;
    private long startTimestamp = -1;
    private long endTimestamp   = -1;

    private SpotPriceHistoryFilterOptions( boolean matchesAny ) {
        this.matchesAny = matchesAny;
    }

    public String[] getDataCenterIds() {
        return dataCenterIds;
    }

    public String[] getProductIds() {
        return productIds;
    }

    public boolean isMatchesAny() {
        return matchesAny;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public long getEndTimestamp() {
        return endTimestamp;
    }

    /**
     * Indicates whether there are any criteria associated with these options.
     *
     * @return <code>true</code> if this filter options object has any criteria associated with it
     */
    public boolean hasCriteria() {
        return ( ( productIds != null && productIds.length > 0 ) ||
                ( dataCenterIds != null && dataCenterIds.length > 0 ) ||
                ( startTimestamp > 0 && endTimestamp > 0 ) );
    }

    /**
     * Indicates that the criteria associated with this filter must match all set criteria.
     *
     * @return this
     */
    public @Nonnull SpotPriceHistoryFilterOptions matchingAll() {
        this.matchesAny = false;
        return this;
    }

    /**
     * Indicates that the criteria associated with this filter must match just one single criterion.
     *
     * @return this
     */
    public @Nonnull SpotPriceHistoryFilterOptions matchingAny() {
        this.matchesAny = true;
        return this;
    }

    public @Nonnull SpotPriceHistoryFilterOptions matchingDataCenters( @Nonnull String[] dataCenterIds ) {
        this.dataCenterIds = dataCenterIds;
        return this;
    }

    public @Nonnull SpotPriceHistoryFilterOptions matchingProducts( @Nonnull String[] productIds ) {
        this.productIds = productIds;
        return this;
    }

    public @Nonnull SpotPriceHistoryFilterOptions matchingInterval( @Nonnegative long startTimestamp, @Nonnegative long endTimestamp ) {
        if( endTimestamp <= startTimestamp ) {
            throw new RuntimeException("Start timestamp should be greater than end timestamp");
        }
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        return this;
    }

    public boolean matches( @Nonnull SpotPriceHistory sph ) {
        boolean matches = false;

        if( dataCenterIds != null && dataCenterIds.length > 0 ) {
            for( String currentDC : dataCenterIds ) {
                if( currentDC.equals(sph.getProviderDataCenterId()) ) {
                    matches = true;
                    break;
                }
            }
        }
        if( productIds != null && productIds.length > 0 ) {
            for( String currentProd : productIds ) {
                if( currentProd.equals(sph.getProductId()) ) {
                    matches = true;
                    break;
                }
            }
        }
        if( getStartTimestamp() > 0 ) {
            for( SpotPrice price : sph.getPriceHistory() ) {
                if( price.getTimestamp() > getStartTimestamp() && price.getTimestamp() < getEndTimestamp() ) {
                    matches = true;
                    break;
                }
            }
        }
        if( !matches && !matchesAny ) return false;
        else if( matches && matchesAny ) return true;
        return !matchesAny;
    }
}
