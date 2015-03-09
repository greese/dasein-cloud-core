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

package org.dasein.cloud.platform.bigdata;

import org.dasein.cloud.Taggable;

import javax.annotation.Nonnull;

/**
 * A product offering for the provisioning and billing of data clusters in a cloud.
 * <p>Created by George Reese: 2/8/14 11:18 AM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 initial version (issue #100)
 */
public class DataClusterProduct {
    /**
     * Constructs a data cluster product with the specified values.
     * @param providerProductId the unique ID for the product
     * @param name a user-friendly name for the product
     * @param description a description of the product
     * @return a new data cluster product
     */
    static public @Nonnull DataClusterProduct getInstance(@Nonnull String providerProductId, @Nonnull String name, @Nonnull String description) {
        DataClusterProduct product = new DataClusterProduct();

        product.providerProductId = providerProductId;
        product.name = name;
        product.description = description;
        return product;
    }

    private String description;
    private String name;
    private String providerProductId;

    private DataClusterProduct() {}

    /**
     * @return a user-friendly description of the product
     */
    public @Nonnull String getDescription() {
        return description;
    }

    /**
     * @return a user-friendly name for the product
     */
    public @Nonnull String getName() {
        return name;
    }

    /**
     * @return the identifier the cloud uses to identify the product
     */
    public @Nonnull String getProviderProductId() {
        return providerProductId;
    }

    @Override
    public @Nonnull String toString() {
        return providerProductId;
    }
}
