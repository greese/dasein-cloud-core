/**
 * Copyright (C) 2009-2013 enstratius, Inc.
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

package org.dasein.cloud.identity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents a policy tied to a user or group in the cloud.
 * @author George Reese (george.reese@imaginary.com)
 * @since 2012.02
 * @version 2012.02
 */
public class CloudPolicy {
    static public CloudPolicy getInstance(@Nonnull String policyId, @Nonnull String name, @Nonnull CloudPermission permission, @Nullable ServiceAction action, @Nullable String resourceId) {
        CloudPolicy policy = new CloudPolicy();

        policy.providerPolicyId = policyId;
        policy.name = name;
        policy.permission = permission;
        policy.action = action;
        policy.resourceId = resourceId;
        return policy;
    }

    private ServiceAction   action;
    private String          name;
    private CloudPermission permission;
    private String          providerPolicyId;
    private String          resourceId;

    private CloudPolicy() { }

    /**
     * Constructs a new policy object
     * @param name the name and ID of the policy
     * @param permission the permission
     * @param action the action the policy governs
     * @param resourceId the resource being governed (or null for any)
     * @deprecated Use {@link #getInstance(String, String, CloudPermission, ServiceAction, String)}
     */
    public CloudPolicy(@Nonnull String name, @Nonnull CloudPermission permission, @Nullable ServiceAction action, @Nullable String resourceId) {
        this.permission = permission;
        this.action = action;
        this.name = name;
        this.providerPolicyId = name;
        this.resourceId = resourceId;
    }

    public @Nullable ServiceAction getAction() {
        return action;
    }

    public @Nonnull String getName() {
        return name;
    }

    public @Nonnull CloudPermission getPermission() {
        return permission;
    }

    public @Nonnull String getProviderPolicyId() {
        return providerPolicyId;
    }

    public @Nullable String getResourceId() {
        return resourceId;
    }
    
    @Override
    public @Nonnull String toString() {
        return (permission + "/" + action + "/" + resourceId + " [#" + providerPolicyId + "]");
    }
}
