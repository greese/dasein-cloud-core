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

public class SpotPrice {
    private long   timestamp;
    private float price;

    public static @Nonnull SpotPrice getInstance(@Nonnegative long timestamp, @Nonnegative float price){
        SpotPrice sp = new SpotPrice();
        sp.timestamp = timestamp;
        sp.price = price;
        return sp;
    }

    public @Nonnegative long getTimestamp(){
        return timestamp;
    }

    public @Nonnegative float getPrice(){
        return price;
    }
}
