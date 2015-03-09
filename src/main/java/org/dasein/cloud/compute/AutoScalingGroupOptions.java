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
 * @author Cameron Stokes (http://github.com/clstokes)
 * @since 2013-11-18
 */
public class AutoScalingGroupOptions {

  private String name;
  private String launchConfigurationId;
  private Integer minServers;
  private Integer maxServers;
  private Integer cooldown;
  private Integer desiredCapacity;
  private Integer healthCheckGracePeriod;
  private String healthCheckType;
  private AutoScalingTag[] tags;
  private String[] providerLoadBalancerIds;
  private String[] providerSubnetIds;
  private String[] providerDataCenterIds;

  public AutoScalingGroupOptions(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getLaunchConfigurationId() {
    return launchConfigurationId;
  }

  public Integer getMinServers() {
    return minServers;
  }

  public Integer getMaxServers() {
    return maxServers;
  }

  public Integer getCooldown() {
    return cooldown;
  }

  public Integer getDesiredCapacity() {
    return desiredCapacity;
  }

  public Integer getHealthCheckGracePeriod() {
    return healthCheckGracePeriod;
  }

  public String getHealthCheckType() {
    return healthCheckType;
  }

  public AutoScalingTag[] getTags() {
    return tags;
  }

  public String[] getProviderLoadBalancerIds() {
    return providerLoadBalancerIds;
  }

  public String[] getProviderSubnetIds() {
    return providerSubnetIds;
  }

  public String[] getProviderDataCenterIds() {
    return providerDataCenterIds;
  }

  public AutoScalingGroupOptions withLaunchConfigurationId(final String launchConfigurationId) {
    this.launchConfigurationId = launchConfigurationId;
    return this;
  }

  public AutoScalingGroupOptions withMinServers(final Integer minServers) {
    this.minServers = minServers;
    return this;
  }

  public AutoScalingGroupOptions withMaxServers(final Integer maxServers) {
    this.maxServers = maxServers;
    return this;
  }

  public AutoScalingGroupOptions withCooldown(final Integer cooldown) {
    this.cooldown = cooldown;
    return this;
  }

  public AutoScalingGroupOptions withDesiredCapacity(final Integer desiredCapacity) {
    this.desiredCapacity = desiredCapacity;
    return this;
  }

  public AutoScalingGroupOptions withHealthCheckGracePeriod(final Integer healthCheckGracePeriod) {
    this.healthCheckGracePeriod = healthCheckGracePeriod;
    return this;
  }

  public AutoScalingGroupOptions withHealthCheckType(final String healthCheckType) {
    this.healthCheckType = healthCheckType;
    return this;
  }

  public AutoScalingGroupOptions withTags(final AutoScalingTag[] tags) {
    this.tags = tags;
    return this;
  }

  public AutoScalingGroupOptions withProviderLoadBalancerIds(final String[] providerLoadBalancerIds) {
    this.providerLoadBalancerIds = providerLoadBalancerIds;
    return this;
  }

  public AutoScalingGroupOptions withProviderSubnetIds(final String[] providerSubnetIds) {
    this.providerSubnetIds = providerSubnetIds;
    return this;
  }

  public AutoScalingGroupOptions withProviderDataCenterIds(final String[] providerDataCenterIds) {
    this.providerDataCenterIds = providerDataCenterIds;
    return this;
  }


}
