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

/**
 * Options for filtering spot vm requests when querying the cloud provider. You may optionally filter on
 * any or all set criteria. If a value has not been set for a specific criterion, it is not included in the filtering
 * process.
 * <p>Created by Stas Maksimov: 21/06/2014 18:37</p>
 *
 * @author Stas Maksimov
 * @version 2014.08 initial version
 * @since 2014.08
 */
public class SpotVirtualMachineRequestFilterOptions {
    private boolean                       matchesAny;
    private String[]                      spotRequestIds;
    private String                        machineImageId;
    private String                        standardProductId;
    private float                         maximumPrice;
    private String                        launchGroup;
    private long                          validFromTimestamp;
    private long                          validUntilTimestamp;
    private SpotVirtualMachineRequestType type;

    // TODO: add filtering by request state and status message

    public static SpotVirtualMachineRequestFilterOptions getInstance() {
        return new SpotVirtualMachineRequestFilterOptions();
    }

    public SpotVirtualMachineRequestFilterOptions matchingAll() {
        this.matchesAny = false;
        return this;
    }

    public SpotVirtualMachineRequestFilterOptions matchingAny() {
        this.matchesAny = true;
        return this;
    }

    /**
     * Indicates whether there are any criteria associated with these options.
     *
     * @return <code>true</code> if this filter options object has any criteria associated with it
     */
    public boolean hasCriteria() {
        return ( ( spotRequestIds != null && spotRequestIds.length > 0 ) || machineImageId != null || standardProductId != null || maximumPrice > 0.0f || launchGroup != null || validFromTimestamp > 0 || validUntilTimestamp > 0 || type != null );
    }

    public boolean matches( SpotVirtualMachineRequest request ) {
        if( spotRequestIds != null ) {
            boolean matches = false;
            for( String requestId : spotRequestIds ) {
                if( requestId.equals(request.getProviderSpotVmRequestId()) ) {
                    matches = true;
                    break;
                }
            }
            if( !matches && !matchesAny ) {
                return false;
            }
            else if( matches && matchesAny ) {
                return true;
            }
        }
        if( machineImageId != null ) {
            boolean matches = machineImageId.equals(request.getProviderMachineImageId());
            if( !matches && !matchesAny ) {
                return false;
            }
            else if( matches && matchesAny ) {
                return true;
            }
        }
        if( standardProductId != null ) {
            boolean matches = standardProductId.equals(request.getProductId());
            if( !matches && !matchesAny ) {
                return false;
            }
            else if( matches && matchesAny ) {
                return true;
            }
        }
        if( maximumPrice > 0.0f ) {
            boolean matches = Float.compare(maximumPrice, request.getSpotPrice()) == 0;
            if( !matches && !matchesAny ) {
                return false;
            }
            else if( matches && matchesAny ) {
                return true;
            }
        }
        if( launchGroup != null ) {
            boolean matches = launchGroup.equals(request.getLaunchGroup());
            if( !matches && !matchesAny ) {
                return false;
            }
            else if( matches && matchesAny ) {
                return true;
            }
        }
        if( validFromTimestamp > 0l ) {
            boolean matches = validFromTimestamp == request.getValidFromTimestamp();
            if( !matches && !matchesAny ) {
                return false;
            }
            else if( matches && matchesAny ) {
                return true;
            }
        }
        if( validUntilTimestamp > 0l ) {
            boolean matches = validUntilTimestamp == request.getValidUntilTimestamp();
            if( !matches && !matchesAny ) {
                return false;
            }
            else if( matches && matchesAny ) {
                return true;
            }
        }
        if( type != null ) {
            boolean matches = type.equals(request.getType());
            if( !matches && !matchesAny ) {
                return false;
            }
            else if( matches && matchesAny ) {
                return true;
            }
        }
        return !matchesAny;
    }

    public SpotVirtualMachineRequestFilterOptions withSpotRequestIds( String... ids ) {
        this.spotRequestIds = ids;
        return this;
    }

    public SpotVirtualMachineRequestFilterOptions withMachineImageId( String machineImageId ) {
        this.machineImageId = machineImageId;
        return this;
    }

    public SpotVirtualMachineRequestFilterOptions withStandardProductId( String productId ) {
        this.standardProductId = productId;
        return this;
    }

    public SpotVirtualMachineRequestFilterOptions withSpotPrice( float price ) {
        this.maximumPrice = price;
        return this;
    }

    public SpotVirtualMachineRequestFilterOptions inLaunchGroup( String launchGroup ) {
        this.launchGroup = launchGroup;
        return this;
    }

    public SpotVirtualMachineRequestFilterOptions validFrom( long ts ) {
        this.validFromTimestamp = ts;
        return this;
    }

    public SpotVirtualMachineRequestFilterOptions validUntil( long ts ) {
        this.validUntilTimestamp = ts;
        return this;
    }

    public SpotVirtualMachineRequestFilterOptions ofType( SpotVirtualMachineRequestType type ) {
        this.type = type;
        return this;
    }

    public String[] getSpotRequestIds() {
        return spotRequestIds;
    }

    public String getMachineImageId() {
        return machineImageId;
    }

    public String getStandardProductId() {
        return standardProductId;
    }

    public float getMaximumPrice() {
        return maximumPrice;
    }

    public String getLaunchGroup() {
        return launchGroup;
    }

    public long getValidFromTimestamp() {
        return validFromTimestamp;
    }

    public long getValidUntilTimestamp() {
        return validUntilTimestamp;
    }

    public SpotVirtualMachineRequestType getType() {
        return type;
    }
}
