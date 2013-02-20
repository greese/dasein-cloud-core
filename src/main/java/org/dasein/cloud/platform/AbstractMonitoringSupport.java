package org.dasein.cloud.platform;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.identity.ServiceAction;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * Default no-op implementation of MonitoringSupport, also any common helper methods.
 *
 * @author Cameron Stokes (http://github.com/clstokes)
 * @since 2013-02-18
 */
public class AbstractMonitoringSupport implements MonitoringSupport {

  private CloudProvider provider;

  public AbstractMonitoringSupport( @Nonnull CloudProvider provider ) {
    this.provider = provider;
  }

  @Override
  public @Nonnull Collection<Metric> listMetrics( MetricFilterOptions options ) throws InternalException, CloudException {
    throw new OperationNotSupportedException( "Listing metrics is not currently implemented" );
  }

  @Nonnull @Override public Collection<Alarm> listAlarms( AlarmFilterOptions options ) throws InternalException, CloudException {
    throw new OperationNotSupportedException( "Listing alarms is not currently implemented" );
  }

  @Override public void enableAlarmActions( String[] alarmNames ) throws InternalException, CloudException {
    throw new OperationNotSupportedException( "Enabling alarm actions is not currently implemented" );
  }

  @Override public void disableAlarmActions( String[] alarmNames ) throws InternalException, CloudException {
    throw new OperationNotSupportedException( "Disabling alarm actions is not currently implemented" );
  }

  @Override
  public @Nonnull String[] mapServiceAction( @Nonnull ServiceAction action ) {
    return new String[0];
  }

}
