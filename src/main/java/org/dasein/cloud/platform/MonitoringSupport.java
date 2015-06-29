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

package org.dasein.cloud.platform;

import org.dasein.cloud.AccessControlledService;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.identity.ServiceAction;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * Provides interaction with cloud monitoring services - allows you to list existing metrics and manage alarms based on those metrics.
 *
 * @author Cameron Stokes (http://github.com/clstokes)
 * @since 2013-02-18
 */
public interface MonitoringSupport extends AccessControlledService {

  static public final ServiceAction ANY = new ServiceAction( "MONITORING:ANY" );

  static public final ServiceAction LIST_METRICS = new ServiceAction( "MONITORING:LIST_METRICS" );
  static public final ServiceAction DESCRIBE_ALARMS = new ServiceAction( "MONITORING:DESCRIBE_ALARMS" );
  static public final ServiceAction UPDATE_ALARM = new ServiceAction( "MONITORING:UPDATE_ALARM" );
  static public final ServiceAction REMOVE_ALARMS = new ServiceAction( "MONITORING:REMOVE_ALARMS" );
  static public final ServiceAction ENABLE_ALARM_ACTIONS = new ServiceAction( "MONITORING:ENABLE_ALARM_ACTIONS" );
  static public final ServiceAction DISABLE_ALARM_ACTIONS = new ServiceAction( "MONITORING:DISABLE_ALARM_ACTIONS" );

  /**
   * Lists all valid metrics for the account owner.
   *
   * @param options filter options
   * @return all metrics or filtered metrics
   * @throws InternalException
   *             an error occurred within the Dasein Cloud API implementation
   * @throws CloudException
   *             an error occurred within the cloud provider
   */
  public @Nonnull Iterable<Metric> listMetrics(MetricFilterOptions options) throws InternalException, CloudException;

  /**
   * List all alarms.
   *
   * @param options filter options
   * @return all metrics or filtered alarms
   * @throws InternalException
   *             an error occurred within the Dasein Cloud API implementation
   * @throws CloudException
   *             an error occurred within the cloud provider
   */
  public @Nonnull Iterable<Alarm> listAlarms(AlarmFilterOptions options) throws InternalException, CloudException;

  /**
   * Adds or updates an existing alarm.
   *
   * @param options options for the alarm
   * @throws InternalException
   *             an error occurred within the Dasein Cloud API implementation
   * @throws CloudException
   *             an error occurred within the cloud provider
   */
  public void updateAlarm( @Nonnull AlarmUpdateOptions options ) throws InternalException, CloudException;

  /**
   * Removes the provided alarms.
   *
   * @param alarmNames the alarm names to remove
   * @throws InternalException
   *             an error occurred within the Dasein Cloud API implementation
   * @throws CloudException
   *             an error occurred within the cloud provider
   */
  public void removeAlarms( @Nonnull String[] alarmNames ) throws InternalException, CloudException;

  /**
   * Enables alarm actions for specified alarms.
   *
   * @param alarmNames the names of the alarms to enable actions for
   * @throws InternalException
   *             an error occurred within the Dasein Cloud API implementation
   * @throws CloudException
   *             an error occurred within the cloud provider
   */
  public void enableAlarmActions( @Nonnull String[] alarmNames ) throws InternalException, CloudException;

  /**
   * Disable alarm actions for specified alarms.
   *
   * @param alarmNames the names of the alarms to disable actions for
   * @throws InternalException
   *             an error occurred within the Dasein Cloud API implementation
   * @throws CloudException
   *             an error occurred within the cloud provider
   */
  public void disableAlarmActions( @Nonnull String[] alarmNames ) throws InternalException, CloudException;

  /**
   * Validates that the current user is subscribed to monitoring services in the target cloud/region.
   *
   * @return true if the account is subscribed to monitoring services
   * @throws InternalException an error occurred within the Dasein Cloud implementation
   * @throws CloudException    an error occurred with the cloud provide
   */
  public boolean isSubscribed() throws CloudException, InternalException;

}
