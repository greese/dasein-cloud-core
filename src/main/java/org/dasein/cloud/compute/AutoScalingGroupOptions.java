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
