/**
 * ========= CONFIDENTIAL =========
 *
 * Copyright (C) 2012 enStratus Networks Inc - ALL RIGHTS RESERVED
 *
 * ====================================================================
 *  NOTICE: All information contained herein is, and remains the
 *  property of enStratus Networks Inc. The intellectual and technical
 *  concepts contained herein are proprietary to enStratus Networks Inc
 *  and may be covered by U.S. and Foreign Patents, patents in process,
 *  and are protected by trade secret or copyright law. Dissemination
 *  of this information or reproduction of this material is strictly
 *  forbidden unless prior written permission is obtained from
 *  enStratus Networks Inc.
 * ====================================================================
 */
package org.dasein.cloud.compute;

import org.dasein.util.uom.storage.Gigabyte;
import org.dasein.util.uom.storage.Storage;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents a standard product in a cloud provider catalog for allocating volumes. A cloud will typically have
 * volume products either when it allows only discreet volume sizes or it charges for things other than raw storage
 * such as SSDs or service levels.
 * <p>Created by George Reese: 6/24/12 10:51 AM</p>
 * @author George Reese (george.reese@imaginary.com)
 * @since 2012-07
 * @version 2012-07 initial version
 */
@SuppressWarnings("UnusedDeclaration")
public class VolumeProduct {
    /**
     * Instantiates a volume product with the minimal acceptable data under the Dasein Cloud model.
     * @param id the provider ID for the produce
     * @param name the name of the product
     * @param description a description of the produce
     * @param type the type of volume provisioned under the produce
     * @return a product instantiation with the specified data
     */
    static public @Nonnull VolumeProduct getInstance(@Nonnull String id, @Nonnull String name, @Nonnull String description, @Nonnull VolumeType type) {
        return new VolumeProduct(id, name, description, type);
    }

    /**
     * Instantiates a volume product with size information.
     * @param id the provider ID for the produce
     * @param name the name of the product
     * @param description a description of the produce
     * @param type the type of volume provisioned under the produce
     * @param volumeSize the size of the volume that will be provisioned under this produce
     * @return a product instantiation with the specified data
     */
    static public @Nonnull VolumeProduct getInstance(@Nonnull String id, @Nonnull String name, @Nonnull String description, @Nonnull VolumeType type, @Nonnull Storage<?> volumeSize) {
        return new VolumeProduct(id, name, description, type, (Storage<Gigabyte>)volumeSize.convertTo(Storage.GIGABYTE));
    }

    /**
     * Instantiates a volume product with size and pricing information.
     * @param id the provider ID for the produce
     * @param name the name of the product
     * @param description a description of the produce
     * @param type the type of volume provisioned under the produce
     * @param volumeSize the size of the volume that will be provisioned under this produce
     * @param currency the currency in which volumes are billed under this account
     * @param cost the cost per gigabyte per month of volumes billed under this product
     * @return a product instantiation with the specified data
     */
    static public @Nonnull VolumeProduct getInstance(@Nonnull String id, @Nonnull String name, @Nonnull String description, @Nonnull VolumeType type, @Nonnull Storage<?> volumeSize, @Nonnull String currency, @Nonnegative float cost) {
        return new VolumeProduct(id, name, description, type, (Storage<Gigabyte>)volumeSize.convertTo(Storage.GIGABYTE), currency, cost);
    }
    
    private String                  currency;
    private String                  description;
    private String                  name;
    private Float                   monthlyGigabyteCost;
    private String                  providerProductId;
    private Storage<Gigabyte>       volumeSize;
    private VolumeType              type;
    
    private VolumeProduct() { }

    private VolumeProduct(@Nonnull String id, @Nonnull String name, @Nonnull String description, @Nonnull VolumeType type) {
        this.providerProductId = id;
        this.name = name;
        this.description = description;
        this.type = type;
    }
    
    private VolumeProduct(@Nonnull String id, @Nonnull String name, @Nonnull String description, @Nonnull VolumeType type, @Nonnull Storage<Gigabyte> volumeSize) {
        this.providerProductId = id;
        this.name = name;
        this.description = description;
        this.volumeSize = volumeSize;
        this.type = type;
    }

    private VolumeProduct(@Nonnull String id, @Nonnull String name, @Nonnull String description, @Nonnull VolumeType type, @Nonnull Storage<Gigabyte> volumeSize, @Nonnull String currency, @Nonnegative float cost) {
        this.providerProductId = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.volumeSize = volumeSize;
        this.currency = currency;
        this.monthlyGigabyteCost = cost;
    }
    
    public boolean equals(Object other) {
        if( other == null ) {
            return false;
        }
        if( other == this ) {
            return true;
        }
        if( !getClass().getName().equals(other.getClass().getName()) ) {
            return false;
        }
        VolumeProduct prd = (VolumeProduct)other;
        
        return (getProviderProductId().equals(prd.getProviderProductId()));
    }

    public @Nullable String getCurrency() {
        return currency;
    }
    
    public @Nonnull String getDescription() {
        return description;
    }
    
    public @Nonnull String getName() {
        return name;
    }
    
    public @Nullable Float getMonthlyGigabyteCost() {
        return monthlyGigabyteCost;
    }
    
    public @Nonnull String getProviderProductId() {
        return providerProductId;
    }
    
    public @Nonnull VolumeType getType() {
        return type;
    }
    
    public @Nullable Storage<Gigabyte> getVolumeSize() {
        return volumeSize;
    }
    
    public String toString() {
        return (name + " [#" + providerProductId + "]");
    }
}
