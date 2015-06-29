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

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an alarm associated with a monitoring service. Depending on the underlying provider, the current state may not be available.
 *
 * @author Cameron Stokes (http://github.com/clstokes)
 * @since 2013-02-19
 */
public class Alarm {

  private String name;
  private String description;

  /**
   * If function is set to true, expect the metric field to contain the alarm function or DSL.
   * If set to false, expect statistic, comparisonOperator, and threshold to be populated and metric is set to the the associated metric.
   */
  private boolean function;
  private String metric;
  private String metricNamespace;
  private Map<String, String> metricMetadata;

  private boolean enabled;

  private String providerAlarmId;
  private String[] providerAlarmActionIds;
  private String[] providerInsufficientDataActionIds;
  private String[] providerOKActionIds;

  private int period;
  private int evaluationPeriods;

  private String statistic;
  private String comparisonOperator;
  private double threshold;

  private String stateReason;
  private String stateReasonData;
  private long stateUpdatedTimestamp;
  private AlarmState stateValue;

  /**
   * @return the alarm name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the alarm name.
   *
   * @param name the alarm name
   */
  public void setName( String name ) {
    this.name = name;
  }

  /**
   * @return the alarm description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the alarm description.
   *
   * @param description the alarm description
   */
  public void setDescription( String description ) {
    this.description = description;
  }

  /**
   * Some providers, such as Rackspace, use a custom DSL for evaluating metrics to determine alarm state. For these providers,
   * {@link #function} should be true with and {@link #metric} should contain the DSL or function value.
   * <p>
   * Other providers, such as Amazon Web Services, use distinct values to specify how metrics are evaluated to determine alarm state.
   * For these providers, {@link #function} should be false and {@link #metric} should contain the metric name that's evaluated, with
   * {@link #statistic}, {@link #comparisonOperator}, {@link #threshold}, {@link #period}, and {@link #evaluationPeriods} should be set.
   * </p>
   *
   * @return if the alarm is actually a function that is executed
   */
  public boolean isFunction() {
    return function;
  }

  /**
   * Indicates if this alarm is evaluated with a function.
   *
   * @param function indicates if this alarm is evaluated with a function
   */
  public void setFunction( boolean function ) {
    this.function = function;
  }

  /**
   * The metric name or function applied to the metric used for the alarm. See {@link #isFunction()} for more details.
   *
   * @return the metric name or function
   */
  public String getMetric() {
    return metric;
  }

  /**
   * Sets the metric name or function. See {@link #getMetric()} for more details.
   *
   * @param metric the metric name or function
   */
  public void setMetric( String metric ) {
    this.metric = metric;
  }

  /**
   * @return the metric metadata (or dimensions)
   */
  public Map<String, String> getMetricMetadata() {
    return metricMetadata;
  }

  /**
   * Sets the metric metadata (or dimensions). Wipes all existing metadata.
   *
   * @param newMetadata the metric metadata (or dimensions)
   */
  public void setMetricMetadata( Map<String, String> newMetadata ) {
    this.metricMetadata = newMetadata;
  }

  /**
   * Adds additional metric metadata (or dimensions). Replaces an existing value if present.
   *
   * @param name  the metadata name or key
   * @param value the metadata value
   */
  public void addMetricMetadata( String name, String value ) {
    if ( this.metricMetadata == null ) {
      this.metricMetadata = new HashMap<String, String>();
    }
    this.metricMetadata.put( name, value );
  }

  /**
   * Adds additional metric metadata (or dimensions). Replaces an existing value if present.
   *
   * @param newMetadata the metric metadata
   */
  public void addMetricMetadata( Map<String, String> newMetadata ) {
    if ( this.metricMetadata == null ) {
      this.metricMetadata = new HashMap<String, String>();
    }
    this.metricMetadata.putAll( newMetadata );
  }

  /**
   * @return the metric namespace
   */
  public String getMetricNamespace() {
    return metricNamespace;
  }

  /**
   * Sets the metric namespace.
   *
   * @param metricNamespace the metric namespace
   */
  public void setMetricNamespace( String metricNamespace ) {
    this.metricNamespace = metricNamespace;
  }

  /**
   * If the alarm and its actions are enabled.
   *
   * @return enabled value
   */
  public boolean isEnabled() {
    return enabled;
  }

  /**
   * Sets if the alarm and its actions are enabled.
   *
   * @param enabled enabled value
   */
  public void setEnabled( boolean enabled ) {
    this.enabled = enabled;
  }

  /**
   * @return the unique ID for this alarm as it is identified with the cloud provider
   */
  public String getProviderAlarmId() {
    return providerAlarmId;
  }

  /**
   * Sets the unique ID for this alarm as it is identified with the cloud provider.
   *
   * @param providerAlarmId the unique ID for this alarm as it is identified with the cloud provider
   */
  public void setProviderAlarmId( String providerAlarmId ) {
    this.providerAlarmId = providerAlarmId;
  }

  /**
   * @return the list of unique IDs of actions (as identified by the cloud provider) when the alarm is triggered as in "alarm" state
   */
  public String[] getProviderAlarmActionIds() {
    return providerAlarmActionIds;
  }

  /**
   * Sets the list of unique IDs of actions (as identified by the cloud provider) when the alarm reaches "alarm" state.
   *
   * @param providerAlarmActionIds the list of unique IDs of actions (as identified by the cloud provider) when the alarm reaches "alarm" state
   */
  public void setProviderAlarmActionIds( String[] providerAlarmActionIds ) {
    this.providerAlarmActionIds = providerAlarmActionIds;
  }

  /**
   * @return the list of unique IDs of actions (as identified by the cloud provider) when the alarm reaches "insufficient data" state
   */
  public String[] getProviderInsufficientDataActionIds() {
    return providerInsufficientDataActionIds;
  }

  /**
   * Sets the list of unique IDs of actions (as identified by the cloud provider) when the alarm reaches "insufficient data" state.
   *
   * @param providerInsufficientDataActionIds
   *         the list of unique IDs of actions (as identified by the cloud provider) when the alarm reaches "insufficient data" state
   */
  public void setProviderInsufficientDataActionIds( String[] providerInsufficientDataActionIds ) {
    this.providerInsufficientDataActionIds = providerInsufficientDataActionIds;
  }

  /**
   * @return the list of unique IDs of actions (as identified by the cloud provider) when the alarm reaches "ok" state
   */
  public String[] getProviderOKActionIds() {
    return providerOKActionIds;
  }

  /**
   * Sets the list of unique IDs of actions (as identified by the cloud provider) when the alarm reaches "ok" state.
   *
   * @param providerOKActionIds the list of unique IDs of actions (as identified by the cloud provider) when the alarm reaches "ok" state
   */
  public void setProviderOKActionIds( String[] providerOKActionIds ) {
    this.providerOKActionIds = providerOKActionIds;
  }

  /**
   * @return the period over which the metric is measured to determine alarm state
   */
  public int getPeriod() {
    return period;
  }

  /**
   * Sets the period of time in seconds over which the metric is measured to determine alarm state.
   *
   * @param period the period of time in seconds over which the metric is measured to determine alarm state
   */
  public void setPeriod( int period ) {
    this.period = period;
  }

  /**
   * @return the number of periods evaluated before triggering a state change of an alarm
   */
  public int getEvaluationPeriods() {
    return evaluationPeriods;
  }

  /**
   * Sets the number of periods evaluated before triggering a state change of an alarm.
   *
   * @param evaluationPeriods the number of periods evaluated before triggering a state change of an alarm
   */
  public void setEvaluationPeriods( int evaluationPeriods ) {
    this.evaluationPeriods = evaluationPeriods;
  }

  /**
   * @return the statistic of the metric value to measure
   */
  public String getStatistic() {
    return statistic;
  }

  /**
   * Sets the statistic of the metric value to measure. Example values are SampleCount | Average | Sum | Minimum | Maximum.
   * The values here are different for each provider. No validation is done on these values currently.
   *
   * @param statistic the statistic of the metric value to measure
   */
  public void setStatistic( String statistic ) {
    this.statistic = statistic;
  }

  /**
   * @return the comparison operator to use when evaluating the metric value against the {@link #threshold}
   */
  public String getComparisonOperator() {
    return comparisonOperator;
  }

  /**
   * Sets the comparison operator to use when evaluating the metric value against the {@link #threshold}. Example values are
   * GreaterThanOrEqualToThreshold | GreaterThanThreshold | LessThanThreshold | LessThanOrEqualToThreshold. The values here are
   * different for each provider. No validation is done on these values currently.
   *
   * @param comparisonOperator the comparison operator to use when evaluating the metric value against the {@link #threshold}
   */
  public void setComparisonOperator( String comparisonOperator ) {
    this.comparisonOperator = comparisonOperator;
  }

  /**
   * @return the threshold the metric (and statistic if applicable) is evaluated against
   */
  public double getThreshold() {
    return threshold;
  }

  /**
   * Sets the threshold the metric (and statistic if applicable) is evaluated against.
   *
   * @param threshold the threshold the metric (and statistic if applicable) is evaluated against
   */
  public void setThreshold( double threshold ) {
    this.threshold = threshold;
  }

  /**
   * @return the reason for the current state value
   */
  public String getStateReason() {
    return stateReason;
  }

  /**
   * Sets the reason for the current state value.
   *
   * @param stateReason the reason for the current state value
   */
  public void setStateReason( String stateReason ) {
    this.stateReason = stateReason;
  }

  /**
   * @return data associated with the alarm state change, in whatever format from the provider
   */
  public String getStateReasonData() {
    return stateReasonData;
  }

  /**
   * Sets data associated with the alarm state change.
   *
   * @param stateReasonData data associated with the alarm state change
   */
  public void setStateReasonData( String stateReasonData ) {
    this.stateReasonData = stateReasonData;
  }

  /**
   * @return timestamp when the alarm state changed
   */
  public long getStateUpdatedTimestamp() {
    return stateUpdatedTimestamp;
  }

  /**
   * Sets the timestamp when the alarm state changed.
   *
   * @param stateUpdatedTimestamp timestamp
   */
  public void setStateUpdatedTimestamp( long stateUpdatedTimestamp ) {
    this.stateUpdatedTimestamp = stateUpdatedTimestamp;
  }

  /**
   * @return the current state of the alarm
   */
  public AlarmState getStateValue() {
    return stateValue;
  }

  /**
   * Sets current state of the alarm.
   *
   * @param stateValue current state of the alarm
   */
  public void setStateValue( AlarmState stateValue ) {
    this.stateValue = stateValue;
  }

}
