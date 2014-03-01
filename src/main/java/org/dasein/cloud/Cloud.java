/*
 * Copyright (C) 2014 Dell, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dasein.cloud;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;

/**
 * [Class Documentation]
 * <p>Created by George Reese: 2/27/14 8:40 PM</p>
 *
 * @author George Reese
 */
public class Cloud {
    static private HashMap<String,Cloud> clouds = new HashMap<String, Cloud>();

    static public @Nullable Cloud getInstance(@Nonnull String endpoint) {
        return clouds.get(endpoint);
    }

    static public @Nonnull Cloud register(@Nonnull String providerName, @Nonnull String cloudName, @Nonnull String endpoint, @Nonnull Class<? extends CloudProvider> providerClass) {
        if( clouds.containsKey(endpoint) ) {
            return clouds.get(endpoint);
        }
        Cloud cloud = new Cloud();

        cloud.endpoint = endpoint;
        cloud.cloudName = cloudName;
        cloud.providerClass = providerClass;
        cloud.providerName = providerName;
        clouds.put(endpoint, cloud);
        return cloud;
    }

    private String cloudName;
    private String endpoint;
    private Class<? extends CloudProvider> providerClass;
    private String providerName;

    private Cloud() { }

    public @Nonnull CloudProvider buildProvider() throws IllegalAccessException, InstantiationException {
        return providerClass.newInstance();
    }

    public @Nonnull ProviderContext createContext(@Nonnull String forAccountNumber, @Nonnull String inRegionId, ProviderContext.Value... values) {
        return ProviderContext.getContext(this, forAccountNumber, inRegionId, values);
    }

    public boolean equals(Object other) {
        return other != null && (other == this || getClass().getName().equals(other.getClass().getName()) && endpoint.equals(((Cloud) other).endpoint));
    }

    public @Nonnull String getCloudName() {
        return cloudName;
    }

    public @Nonnull String getEndpoint() {
        return endpoint;
    }

    public @Nonnull Class<? extends CloudProvider> getProviderClass() {
        return providerClass;
    }

    public @Nonnull String getProviderName() {
        return providerName;
    }
}
