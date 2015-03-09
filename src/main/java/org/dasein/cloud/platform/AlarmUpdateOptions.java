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

import java.util.Map;

/**
 * Options for adding or updating an alarm.
 *
 * @author Cameron Stokes (http://github.com/clstokes)
 * @since 2013-02-20
 */
public class AlarmUpdateOptions {

  /**
   * Factory method for creating an instance of AlarmUpdateOptions that does *not* use a metric function, but uses
   * a metric, statist, comparisonOperator, etc. See {@link org.dasein.cloud.platform.Alarm#isFunction()} for further explanation.
   *
   * @param alarmName          the alarm name
   * @param metricNamespace    the metric namespace
   * @param metric             the metric
   * @param statistic          the statistic function to use
   * @param comparisonOperator the comparison operator
   * @param threshold          the threshold to measure against
   * @param period             the time period in seconds to evaluate the metric value
   * @param evaluationPeriods  the number of periods to evaluate
   * @return AlarmUpdateOptions
   */
  public static AlarmUpdateOptions getInstance( String alarmName,
                                                String metricNamespace, String metric,
                                                String statistic, String comparisonOperator, double threshold,
                                                int period, int evaluationPeriods ) {
    return new AlarmUpdateOptions( alarmName, metricNamespace, metric, statistic, comparisonOperator, threshold, period, evaluationPeriods );
  }

  private String alarmName;
  private String alarmDescription;

  /**
   * If function is set to true, expect the metric field to contain the alarm function or DSL.
   * If set to false, expect statistic, comparisonOperator, and threshold to be populated and metric is set to the the associated metric.
   */
  private boolean function;
  private String metric;
  private String metricNamespace;
  private Map<String, String> metadata;

  private boolean enabled = true;

  private String[] providerAlarmActionIds;
  private String[] providerInsufficentDataActionIds;
  private String[] providerOKActionIds;

  private int period;
  private int evaluationPeriods;

  private String statistic;
  private String comparisonOperator;
  private double threshold;

  private AlarmUpdateOptions() {
  }

  private AlarmUpdateOptions( String alarmName, String metricNamespace, String metric, String statistic, String comparisonOperator, double threshold, int period, int evaluationPeriods ) {
    this.alarmName = alarmName;
    this.metricNamespace = metricNamespace;
    this.metric = metric;
    this.statistic = statistic;
    this.comparisonOperator = comparisonOperator;
    this.threshold = threshold;
    this.period = period;
    this.evaluationPeriods = evaluationPeriods;
    // if this constructor is used (with comparisonOperator, metric, statistic, etc.) then the metric isn't a function.
    this.function = false;
  }

  /**
   * @return the alarm name
   */
  public String getAlarmName() {
    return alarmName;
  }

  /**
   * @return the alarm description
   */
  public String getAlarmDescription() {
    return alarmDescription;
  }

  /**
   * Sets the alarm description
   *
   * @param description the alarm description
   * @return this
   */
  public AlarmUpdateOptions withDescription( String description ) {
    this.alarmDescription = description;
    return this;
  }

  /**
   * See {@link org.dasein.cloud.platform.Alarm#isFunction()} for further details.
   *
   * @return if the alarm is actually a function that is executed
   */
  public boolean isFunction() {
    return function;
  }

  /**
   * Indicates if this alarm is evaluated with a function.
   *
   * @param function if this alarm is evaluated with a function
   * @return this
   */
  public AlarmUpdateOptions asFunction( boolean function ) {
    this.function = function;
    return this;
  }

  /**
   * See {@link org.dasein.cloud.platform.Alarm#isFunction()} for further details.
   *
   * @return either the metric name or function
   */
  public String getMetric() {
    return metric;
  }

  /**
   * @return the metric namespace, if the provider supports namespaces
   */
  public String getMetricNamespace() {
    return metricNamespace;
  }

  /**
   * @return the metric metadata (or dimensions)
   */
  public Map<String, String> getMetadata() {
    return metadata;
  }

  /**
   * Sets the metric metadata (or dimensions).
   *
   * @param metadata the metric metadata (or dimensions)
   * @return this
   */
  public AlarmUpdateOptions withMetadata( Map<String, String> metadata ) {
    this.metadata = metadata;
    return this;
  }

  /**
   * @return if the alarm action(s) is enabled
   */
  public boolean isEnabled() {
    return enabled;
  }

  /**
   * Sets if the alarm action(s) is enabled.
   *
   * @param enabled if the alarm action(s) is enabled
   * @return this
   */
  public AlarmUpdateOptions asEnabled( boolean enabled ) {
    this.enabled = enabled;
    return this;
  }

  /**
   * @return the list of unique IDs of actions (as identified by the cloud provider) when the alarm is triggered as in "alarm" state
   */
  public String[] getProviderAlarmActionIds() {
    return providerAlarmActionIds;
  }

  /**
   * Sets the list of unique IDs of actions (as identified by the cloud provider) when the alarm is triggered as in "alarm" state.
   *
   * @param providerAlarmActionIds the list of unique IDs of actions (as identified by the cloud provider) when the alarm is triggered as in "alarm" state
   * @return this
   */
  public AlarmUpdateOptions withProviderAlarmActionIds( String[] providerAlarmActionIds ) {
    this.providerAlarmActionIds = providerAlarmActionIds;
    return this;
  }

  /**
   * @return the list of unique IDs of actions (as identified by the cloud provider) when the alarm reaches "insufficient data" state
   */
  public String[] getProviderInsufficentDataActionIds() {
    return providerInsufficentDataActionIds;
  }

  /**
   * Sets the list of unique IDs of actions (as identified by the cloud provider) when the alarm reaches "insufficient data" state.
   *
   * @param providerInsufficentDataActionIds
   *         the list of unique IDs of actions (as identified by the cloud provider) when the alarm reaches "insufficient data" state
   * @return this
   */
  public AlarmUpdateOptions withProviderInsufficentDataActionIds( String[] providerInsufficentDataActionIds ) {
    this.providerInsufficentDataActionIds = providerInsufficentDataActionIds;
    return this;
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
   * @return this
   */
  public AlarmUpdateOptions withProviderOKActionIds( String[] providerOKActionIds ) {
    this.providerOKActionIds = providerOKActionIds;
    return this;
  }

  /**
   * @return the period over which the metric is measured to determine alarm state
   */
  public int getPeriod() {
    return period;
  }

  /**
   * @return the number of periods evaluated before triggering a state change of an alarm
   */
  public int getEvaluationPeriods() {
    return evaluationPeriods;
  }

  /**
   * @return the statistic of the metric value to measure
   */
  public String getStatistic() {
    return statistic;
  }

  /**
   * @return the comparison operator to use when evaluating the metric value against the {@link #threshold}
   */
  public String getComparisonOperator() {
    return comparisonOperator;
  }

  /**
   * @return the threshold the metric (and statistic if applicable) is evaluated against
   */
  public double getThreshold() {
    return threshold;
  }


}
