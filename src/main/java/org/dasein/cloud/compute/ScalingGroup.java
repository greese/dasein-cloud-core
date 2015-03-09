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

import java.io.Serializable;
import java.util.*;

public class ScalingGroup implements Serializable {
    private static final long serialVersionUID = -5317003700769693511L;

    private int                   defaultCooldown;
    private long                  creationTimestamp;
    private String                description;
    private int                   maxServers;
    private int                   minServers;
    private String                name;
    private String[]              providerDataCenterIds;
    private String                providerLaunchConfigurationId;
    private String                providerOwnerId;
    private String                providerRegionId;
    private String                providerScalingGroupId;
    private String[]              providerServerIds;
    private int                   targetCapacity;
    private String                id;
    private String[]              enabledMetrics;
    private int                   healthCheckGracePeriod;
    private String                healthCheckType;
    private String[]              providerLoadBalancerNames;
    private String                status;
    private Collection<String[]>  suspendedProcesses;
    private String[]              terminationPolicies;
    private AutoScalingTag[]      tags;
    // comma separated list
    private String[]              providerSubnetIds;

    public ScalingGroup() { }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String[] getEnabledMetrics() {
        return enabledMetrics;
    }

    public void setEnabledMetrics( String[] metrics ) {
        this.enabledMetrics = metrics;
    }

    public int getHealthCheckGracePeriod() {
        return healthCheckGracePeriod;
    }

    public void setHealthCheckGracePeriod( int healthCheckGracePeriod ) {
        this.healthCheckGracePeriod = healthCheckGracePeriod;
    }

    public String getHealthCheckType() {
        return healthCheckType;
    }

    public void setHealthCheckType( String healthCheckType ) {
        this.healthCheckType = healthCheckType;
    }

    public String[] getProviderLoadBalancerNames() {
        return providerLoadBalancerNames;
    }

    public void setProviderLoadBalancerNames( String[] providerLoadBalancerNames ) {
        this.providerLoadBalancerNames = providerLoadBalancerNames;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus( String status ) {
        this.status = status;
    }

    public Collection<String[]> getSuspendedProcesses() {
        return suspendedProcesses;
    }

    public void setSuspendedProcesses( Collection<String[]> suspendedProcesses ) {
        this.suspendedProcesses = suspendedProcesses;
    }

    public String[] getTerminationPolicies() {
        return terminationPolicies;
    }

    public void setTerminationPolicies( String[] terminationPolicies ) {
        this.terminationPolicies = terminationPolicies;
    }

    public int getDefaultCooldown() {
        return defaultCooldown;
    }

    public void setDefaultCooldown( int cooldown ) {
        this.defaultCooldown = cooldown;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public int getMaxServers() {
        return maxServers;
    }

    public void setMaxServers( int maxServers ) {
        this.maxServers = maxServers;
    }

    public int getMinServers() {
        return minServers;
    }

    public void setMinServers( int minServers ) {
        this.minServers = minServers;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String[] getProviderDataCenterIds() {
        return providerDataCenterIds;
    }

    public void setProviderDataCenterIds( String[] providerDataCenterIds ) {
        this.providerDataCenterIds = providerDataCenterIds;
    }

    public String getProviderLaunchConfigurationId() {
        return providerLaunchConfigurationId;
    }

    public void setProviderLaunchConfigurationId( String providerLaunchConfigurationId ) {
        this.providerLaunchConfigurationId = providerLaunchConfigurationId;
    }

    public String getProviderOwnerId() {
        return providerOwnerId;
    }

    public void setProviderOwnerId( String providerOwnerId ) {
        this.providerOwnerId = providerOwnerId;
    }

    public String getProviderRegionId() {
        return providerRegionId;
    }

    public void setProviderRegionId( String providerRegionId ) {
        this.providerRegionId = providerRegionId;
    }

    public String getProviderScalingGroupId() {
        return providerScalingGroupId;
    }

    public void setProviderScalingGroupId( String providerScalingGroupId ) {
        this.providerScalingGroupId = providerScalingGroupId;
    }

    public void setProviderServerIds( String[] providerServerIds ) {
        this.providerServerIds = providerServerIds;
    }

    public String[] getProviderServerIds() {
        return providerServerIds;
    }

    public void setCreationTimestamp( long creationTimestamp ) {
        this.creationTimestamp = creationTimestamp;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setTargetCapacity( int targetCapacity ) {
        this.targetCapacity = targetCapacity;
    }

    public int getTargetCapacity() {
        return targetCapacity;
    }

    public AutoScalingTag[] getTags() {
        return tags;
    }

    public Map<String, String> getTagsAsMap() {
        Map<String, String> tagsAsMap = new HashMap<String, String>();
        if( tags != null ) {
            for( AutoScalingTag tag : tags ) {
                tagsAsMap.put(tag.getKey(), tag.getValue());
            }
        }
        return tagsAsMap;
    }

    public void setTags( AutoScalingTag[] tags ) {
        this.tags = tags;
    }

    @Deprecated
    public String getSubnetIds() {
        if( this.providerSubnetIds == null || this.providerSubnetIds.length == 0 ) {
            return new String();
        }
        StringBuilder sb = new StringBuilder();
        for( String sn : this.providerSubnetIds ) {
            sb.append(sn).append(",");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    @Deprecated
    public void setSubnetIds( String subnetIds ) {
        this.providerSubnetIds = ( subnetIds == null ) ? new String[0] : subnetIds.split("\\s*,\\s*");
    }

    public String[] getProviderSubnetIds() {
        return ( this.providerSubnetIds == null ) ? new String[0] : this.providerSubnetIds;
    }

    public void setProviderSubnetIds( String[] providerSubnetIds ) {
        this.providerSubnetIds = providerSubnetIds;
    }

}
