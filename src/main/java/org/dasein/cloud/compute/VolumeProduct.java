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
     * @param id the provider ID for the product
     * @param name the name of the product
     * @param description a description of the produce
     * @param type the type of volume provisioned under the produce
     * @return a product instantiation with the specified data
     */
    static public @Nonnull VolumeProduct getInstance(@Nonnull String id, @Nonnull String name, @Nonnull String description, @Nonnull VolumeType type) {
        return new VolumeProduct(id, name, description, type);
    }

    /**
     * Instantiates a volume product with minimal data plus IOPS guarantees and costs.
     * @param id the provider ID for the product
     * @param name the name of the provider product
     * @param description a description of the product offering
     * @param type what kind of technology is behind the volume
     * @param minIops the minimum number of provisionable guaranteed iops under this product offering
     * @param maxIops the maximum number of provisionable guaranteed IOPS under this product offering or 0 if no guarantees
     * @param monthlyCost the monthly cost per gigabyte
     * @param iopsCost the monthly cost per IOPS
     * @return a product instantiation with the specified daa
     */
    static public @Nonnull VolumeProduct getInstance(@Nonnull String id, @Nonnull String name, @Nonnull String description, @Nonnull VolumeType type, @Nonnegative int minIops, @Nonnegative int maxIops, @Nullable Float monthlyCost, @Nullable Float iopsCost) {
        return new VolumeProduct(id, name, description, type, minIops, maxIops, monthlyCost, iopsCost);
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
     * @param minIops the minimum number of guaranteed IOPS for this product definition
     * @param maxIops the maximum number of guaranteed IOPS for this product definition
     * @param monthlyCost the cost per gigabyte per month of volumes billed under this product
     * @param iopsCost the cost per IOPS per month of the volumes billed under this product
     * @return a product instantiation with the specified data
     */
    static public @Nonnull VolumeProduct getInstance(@Nonnull String id, @Nonnull String name, @Nonnull String description, @Nonnull VolumeType type, @Nonnull Storage<?> volumeSize, @Nonnull String currency, @Nonnegative int minIops, @Nonnegative int maxIops, @Nullable Float monthlyCost, @Nullable Float iopsCost) {
        return new VolumeProduct(id, name, description, type, (Storage<Gigabyte>)volumeSize.convertTo(Storage.GIGABYTE), currency, minIops, maxIops, monthlyCost, iopsCost);
    }
    
    private String                  currency;
    private String                  description;
    private Float                   iopsCost;
    private int                     maxIops;
    private int                     minIops;
    private Float                   monthlyGigabyteCost;
    private String                  name;
    private String                  providerProductId;
    private Storage<Gigabyte>       volumeSize;
    private Storage<Gigabyte>       minVolumeSize;
    private Storage<Gigabyte>       maxVolumeSize;
    private VolumeType              type;
    private Float                   maxIopsRatio;
    
    private VolumeProduct() { }

    private VolumeProduct(@Nonnull String id, @Nonnull String name, @Nonnull String description, @Nonnull VolumeType type) {
        this.providerProductId = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.minIops = 0;
        this.maxIops = 0;
    }

    private VolumeProduct(@Nonnull String id, @Nonnull String name, @Nonnull String description, @Nonnull VolumeType type, @Nonnegative int minIops, @Nonnegative int maxIops, @Nullable Float monthlyCost, @Nullable Float iopsCost) {
        this.providerProductId = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.minIops = minIops;
        this.maxIops = maxIops;
        monthlyGigabyteCost = monthlyCost;
        this.iopsCost = iopsCost;
    }

    private VolumeProduct(@Nonnull String id, @Nonnull String name, @Nonnull String description, @Nonnull VolumeType type, @Nonnull Storage<Gigabyte> volumeSize) {
        this.providerProductId = id;
        this.name = name;
        this.description = description;
        this.volumeSize = volumeSize;
        this.type = type;
        this.minIops = 0;
        this.maxIops = 0;
    }

    private VolumeProduct(@Nonnull String id, @Nonnull String name, @Nonnull String description, @Nonnull VolumeType type, @Nonnull Storage<Gigabyte> volumeSize, @Nonnull String currency, @Nonnegative float monthlyCost) {
        this.providerProductId = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.volumeSize = volumeSize;
        this.currency = currency;
        this.monthlyGigabyteCost = monthlyCost;
        this.minIops = 0;
        this.maxIops = 0;
    }

    private VolumeProduct(@Nonnull String id, @Nonnull String name, @Nonnull String description, @Nonnull VolumeType type, @Nonnull Storage<Gigabyte> volumeSize, @Nonnull String currency, @Nonnegative int minIops, @Nonnegative int maxIops, @Nullable Float monthlyCost, @Nullable Float iopsCost) {
        this.providerProductId = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.volumeSize = volumeSize;
        this.currency = currency;
        this.monthlyGigabyteCost = monthlyCost;
        this.minIops = minIops;
        this.maxIops = maxIops;
        this.iopsCost = iopsCost;
    }

    @Override
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

    /**
     * Set the volume product with the maximum IOPS to storage size ratio,
     * e.g. 30:1 will result in a ratio value of 30.0.
     * @param ratio maximum IOPS to storage size ratio
     * @return this
     */
    public @Nonnull VolumeProduct withMaxIopsRatio(@Nonnegative float ratio) {
        maxIopsRatio = ratio;
        return this;
    }

    public @Nonnull VolumeProduct withMinVolumeSize(@Nonnull Storage<Gigabyte> size) {
        minVolumeSize = size;
        return this;
    }

    public @Nonnull VolumeProduct withMaxVolumeSize(@Nonnull Storage<Gigabyte> size) {
        maxVolumeSize = size;
        return this;
    }

    public @Nullable String getCurrency() {
        return currency;
    }
    
    public @Nonnull String getDescription() {
        return description;
    }

    public @Nonnegative int getMaxIops() {
        return maxIops;
    }

    public @Nonnegative int getMinIops() {
        return minIops;
    }

    public @Nullable Float getIopsCost() {
        return iopsCost;
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

    /**
     * @return minimum size of volume
     * @deprecated use {@link VolumeProduct#getMinVolumeSize()} and {@link VolumeProduct#getMinVolumeSize()} instead.
     */
    @Deprecated
    public @Nullable Storage<Gigabyte> getVolumeSize() {
        return volumeSize;
    }

    public @Nullable Storage<Gigabyte> getMinVolumeSize() {
        return minVolumeSize;
    }

    public @Nullable Storage<Gigabyte> getMaxVolumeSize() {
        return maxVolumeSize;
    }

    public @Nullable Float getMaxIopsRatio() {
        return maxIopsRatio;
    }

    public String toString() {
        return (name + " [#" + providerProductId + "]");
    }
}
