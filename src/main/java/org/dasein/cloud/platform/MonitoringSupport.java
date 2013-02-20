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
  static public final ServiceAction ENABLE_ALARM_ACTIONS = new ServiceAction( "MONITORING:ENABLE_ALARM_ACTIONS" );
  static public final ServiceAction DISABLE_ALARM_ACTIONS = new ServiceAction( "MONITORING:DISABLE_ALARM_ACTIONS" );

  /**
   * Lists all valid metrics for the account owner.
   *
   * @param options filter options
   * @return all metrics or filtered metrics
   * @throws InternalException
   * @throws CloudException
   */
  public @Nonnull Collection<Metric> listMetrics( MetricFilterOptions options ) throws InternalException, CloudException;

  /**
   * List all alarms for the account owner.
   *
   * @param options filter options
   * @return all metrics or filtered alarms
   * @throws InternalException
   * @throws CloudException
   */
  public @Nonnull Collection<Alarm> listAlarms( AlarmFilterOptions options ) throws InternalException, CloudException;

  /**
   * Enables alarm actions for specified alarms.
   *
   * @param alarmNames the names of the alarms to enable actions for
   * @throws InternalException
   * @throws CloudException
   */
  public void enableAlarmActions( String[] alarmNames ) throws InternalException, CloudException;

  /**
   * Disable alarm actions for specified alarms.
   *
   * @param alarmNames the names of the alarms to disable actions for
   * @throws InternalException
   * @throws CloudException
   */
  public void disableAlarmActions( String[] alarmNames ) throws InternalException, CloudException;

}
