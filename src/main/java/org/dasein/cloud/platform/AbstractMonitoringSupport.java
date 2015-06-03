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

import org.dasein.cloud.AbstractProviderService;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.identity.ServiceAction;

import javax.annotation.Nonnull;
import java.util.Collections;

/**
 * Default no-op implementation of MonitoringSupport, also any common helper methods.
 *
 * @author Cameron Stokes (http://github.com/clstokes)
 * @since 2013-02-18
 */
public abstract class AbstractMonitoringSupport<T extends CloudProvider> extends AbstractProviderService<T> implements MonitoringSupport {

    protected AbstractMonitoringSupport(T provider) {
        super(provider);
    }

    @Override
    public @Nonnull Iterable<Metric> listMetrics(MetricFilterOptions options) throws InternalException, CloudException {
        return Collections.emptyList();
    }

    @Override
    public @Nonnull Iterable<Alarm> listAlarms(AlarmFilterOptions options) throws InternalException, CloudException {
        return Collections.emptyList();
    }

    @Override
    public void updateAlarm( @Nonnull AlarmUpdateOptions options ) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Creating alarms is not currently implemented");
    }

    @Override
    public void removeAlarms( @Nonnull String[] alarmNames ) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Removing alarms is not currently implemented");
    }

    @Override
    public void enableAlarmActions( @Nonnull String[] alarmNames ) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Enabling alarm actions is not currently implemented");
    }

    @Override
    public void disableAlarmActions( @Nonnull String[] alarmNames ) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Disabling alarm actions is not currently implemented");
    }

    @Override
    public @Nonnull String[] mapServiceAction( @Nonnull ServiceAction action ) {
        return new String[0];
    }

}
