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

import org.dasein.cloud.*;
import org.dasein.cloud.identity.ServiceAction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

public interface AutoScalingSupport extends AccessControlledService {
    static public final ServiceAction ANY = new ServiceAction("SCALING:ANY");

    static public final ServiceAction CREATE_LAUNCH_CONFIGURATION = new ServiceAction("SCALING:CREATE_LAUNCH_CONFIGURATION");
    static public final ServiceAction CREATE_SCALING_GROUP        = new ServiceAction("SCALING:CREATE_SCALING_GROUP");
    static public final ServiceAction GET_LAUNCH_CONFIGURATION    = new ServiceAction("SCALING:GET_LAUNCH_CONFIGURATION");
    static public final ServiceAction GET_SCALING_GROUP           = new ServiceAction("SCALING:GET_SCALING_GROUP");
    static public final ServiceAction LIST_LAUNCH_CONFIGURATION   = new ServiceAction("SCALING:LIST_LAUNCH_CONFIGURATION");
    static public final ServiceAction LIST_SCALING_GROUP          = new ServiceAction("SCALING:LIST_SCALING_GROUP");
    static public final ServiceAction REMOVE_LAUNCH_CONFIGURATION = new ServiceAction("SCALING:REMOVE_LAUNCH_CONFIGURATION");
    static public final ServiceAction REMOVE_SCALING_GROUP        = new ServiceAction("SCALING:REMOVE_SCALING_GROUP");
    static public final ServiceAction SET_CAPACITY                = new ServiceAction("SCALING:SET_CAPACITY");
    static public final ServiceAction SET_SCALING_TRIGGER         = new ServiceAction("SCALING:SET_SCALING_TRIGGER");
    static public final ServiceAction UPDATE_SCALING_GROUP        = new ServiceAction("SCALING:UPDATE_SCALING_GROUP");
    static public final ServiceAction SUSPEND_AUTO_SCALING_GROUP  = new ServiceAction("SCALING:SUSPEND_AUTO_SCALING_GROUP");
    static public final ServiceAction RESUME_AUTO_SCALING_GROUP   = new ServiceAction("SCALING:RESUME_AUTO_SCALING_GROUP");
    static public final ServiceAction PUT_SCALING_POLICY          = new ServiceAction("SCALING:PUT_SCALING_POLICY");
    static public final ServiceAction DELETE_SCALING_POLICY       = new ServiceAction("SCALING:DELETE_SCALING_POLICY");
    static public final ServiceAction LIST_SCALING_POLICIES       = new ServiceAction("SCALING:LIST_SCALING_POLICIES");

    /**
     * Creates an auto scaling group with the provided options.
     *
     * @param options the auto scaling group options
     * @return the provider's auto scaling group id
     * @throws CloudException an error occurred in the cloud identifying this capability
     * @throws InternalException an error occurred within the Dasein Cloud implementation identifying this capability
     */
    public String createAutoScalingGroup( @Nonnull AutoScalingGroupOptions options ) throws InternalException, CloudException;

    /**
     * @param name name of the group
     * @param launchConfigurationId launch configuration identifier
     * @param minServers minimum number of virtual machines in the group
     * @param maxServers maximum number of virtual machines in the group
     * @param cooldown the amount of time, in seconds, after a scaling activity completes before another scaling activity can start
     * @param loadBalancerIds load balancers to be associated with the group
     * @param desiredCapacity the number of the virtual machines that should be running in this group
     * @param healthCheckGracePeriod delay, in seconds, after the VM start when its health will be monitored
     * @param healthCheckType type of healthcheck service - FIXME: too AWS specific, needs to be generalised
     * @param vpcZones list of VPC subnets - FIXME: too AWS specific
     * @param dataCenterIds list of datacenter identifiers this group should be created in
     * @return the provider's auto scaling group id
     * @throws CloudException an error occurred in the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     * @deprecated use {@link #createAutoScalingGroup(AutoScalingGroupOptions)}
     */
    @Deprecated
    public String createAutoScalingGroup( @Nonnull String name, @Nonnull String launchConfigurationId, @Nonnull Integer minServers, @Nonnull Integer maxServers, @Nullable Integer cooldown, @Nullable String[] loadBalancerIds, @Nullable Integer desiredCapacity, @Nullable Integer healthCheckGracePeriod, @Nullable String healthCheckType, @Nullable String vpcZones, @Nullable String... dataCenterIds ) throws InternalException, CloudException;

    public void updateAutoScalingGroup( String scalingGroupId, @Nullable String launchConfigurationId, @Nullable Integer minServers, @Nullable Integer maxServers, @Nullable Integer cooldown, @Nullable Integer desiredCapacity, @Nullable Integer healthCheckGracePeriod, @Nullable String healthCheckType, @Nullable String vpcZones, @Nullable String... zoneIds ) throws InternalException, CloudException;

    public String createLaunchConfiguration( String name, String imageId, VirtualMachineProduct size, String keyPairName, String userData, String providerRoleId, Boolean detailedMonitoring, String... firewalls ) throws InternalException, CloudException;

    /**
     * Created an Auto Scaling Launch Configuration based on passed options
     *
     * @param options the auto scaling launch configuration options
     * @return the provider's launch configuration id
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public String createLaunchConfiguration( @Nonnull LaunchConfigurationCreateOptions options ) throws InternalException, CloudException;

    public void deleteAutoScalingGroup( String providerAutoScalingGroupId ) throws CloudException, InternalException;

    /**
     * Deletes an Auto Scaling group based on passed options
     *
     * @param options the auto scaling group delete options
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void deleteAutoScalingGroup( @Nonnull AutoScalingGroupDeleteOptions options ) throws CloudException, InternalException;

    public void deleteLaunchConfiguration( String providerLaunchConfigurationId ) throws CloudException, InternalException;

    public LaunchConfiguration getLaunchConfiguration( String providerLaunchConfigurationId ) throws CloudException, InternalException;

    public ScalingGroup getScalingGroup( String providerScalingGroupId ) throws CloudException, InternalException;

    public boolean isSubscribed() throws CloudException, InternalException;

    public void suspendAutoScaling( String providerScalingGroupId, String[] processesToSuspend ) throws CloudException, InternalException;

    public void resumeAutoScaling( String providerScalingGroupId, String[] processesToResume ) throws CloudException, InternalException;

    public String updateScalingPolicy( String policyName, String adjustmentType, String autoScalingGroupName, Integer cooldown, Integer minAdjustmentStep, Integer scalingAdjustment ) throws CloudException, InternalException;

    public void deleteScalingPolicy( String policyName, String autoScalingGroupName ) throws CloudException, InternalException;

    public Iterable<ScalingPolicy> listScalingPolicies( @Nullable String autoScalingGroupName ) throws CloudException, InternalException;

    public ScalingPolicy getScalingPolicy( @Nonnull String policyName ) throws CloudException, InternalException;

    public Iterable<ResourceStatus> listScalingGroupStatus() throws CloudException, InternalException;

    public Iterable<ScalingGroup> listScalingGroups( AutoScalingGroupFilterOptions options ) throws CloudException, InternalException;

    public Iterable<ScalingGroup> listScalingGroups() throws CloudException, InternalException;

    public Iterable<ResourceStatus> listLaunchConfigurationStatus() throws CloudException, InternalException;

    public Iterable<LaunchConfiguration> listLaunchConfigurations() throws CloudException, InternalException;

    public void setDesiredCapacity( String scalingGroupId, int capacity ) throws CloudException, InternalException;

    public String setTrigger( String name, String scalingGroupId, String statistic, String unitOfMeasure, String metric, int periodInSeconds, double lowerThreshold, double upperThreshold, int lowerIncrement, boolean lowerIncrementAbsolute, int upperIncrement, boolean upperIncrementAbsolute, int breachDuration ) throws InternalException, CloudException;

    /**
     * Set notification configurations for scaling group.
     *
     * @param scalingGroupId    the auto scaling group id
     * @param topic             the notification service topic
     * @param notificationTypes types to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void setNotificationConfig( @Nonnull String scalingGroupId, @Nonnull String topic, @Nonnull String[] notificationTypes ) throws CloudException, InternalException;

    /**
     * Get list of notification configs for multiple auto scaling groups.
     *
     * @param scalingGroupIds the auto scaling group ids
     * @return list of notification configs
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public Iterable<AutoScalingGroupNotificationConfig> listNotificationConfigs( final String[] scalingGroupIds ) throws CloudException, InternalException;

    /**
     * Updates meta-data for multiple auto scaling groups with the new values. It will not overwrite any value that currently
     * exists unless it appears in the tags you submit.
     *
     * @param providerScalingGroupIds the auto scaling groups to update
     * @param tags                    the meta-data tags to set
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void updateTags( @Nonnull String[] providerScalingGroupIds, @Nonnull AutoScalingTag... tags ) throws CloudException, InternalException;

    /**
     * Removes meta-data from multiple auto scaling groups. If tag values are set, their removal is dependent on underlying cloud
     * provider behavior. They may be removed only if the tag value matches or they may be removed regardless of the
     * value.
     *
     * @param providerScalingGroupIds the auto scaling groups to update
     * @param tags                    the meta-data tags to remove
     * @throws CloudException    an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public void removeTags( @Nonnull String[] providerScalingGroupIds, @Nonnull AutoScalingTag... tags ) throws CloudException, InternalException;

    /**
     * Set meta-data for auto scaling group. Remove any tags that were not provided by the incoming tags, and add or
     * overwrite any new or pre-existing tags.
     *
     * @param providerScalingGroupId the auto scaling group to set
     * @param tags                   the meta-data tags to set
     * @throws CloudException an error occurred in the cloud identifying this capability
     * @throws InternalException an error occurred within the Dasein Cloud implementation identifying this capability
     */
    public void setTags( @Nonnull String providerScalingGroupId, @Nonnull AutoScalingTag... tags ) throws CloudException, InternalException;

    /**
     * Set meta-data for multiple auto scaling groups. Remove any tags that were not provided by the incoming tags, and add or
     * overwrite any new or pre-existing tags.
     *
     * @param providerScalingGroupIds the auto scaling groups to set
     * @param tags                    the meta-data tags to set
     * @throws CloudException an error occurred in the cloud identifying this capability
     * @throws InternalException an error occurred within the Dasein Cloud implementation identifying this capability
     */
    public void setTags( @Nonnull String[] providerScalingGroupIds, @Nonnull AutoScalingTag... tags ) throws CloudException, InternalException;

}
