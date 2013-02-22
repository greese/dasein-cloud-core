package org.dasein.cloud.platform;

import java.util.Map;

/**
 * Options for creating an alarm.
 *
 * @author Cameron Stokes (http://github.com/clstokes)
 * @since 2013-02-20
 */
public class AlarmCreateOptions {

  /**
   * TODO: talk about default functional and enabled values
   *
   * @param alarmName
   * @param metricNamespace
   * @param metric
   * @param statistic
   * @param comparisonOperator
   * @param threshold
   * @param period
   * @param evaluationPeriods
   * @return
   */
  public static AlarmCreateOptions getInstance( String alarmName,
                                                String metricNamespace, String metric,
                                                String statistic, String comparisonOperator, double threshold,
                                                int period, int evaluationPeriods ) {
    return new AlarmCreateOptions( alarmName, metricNamespace, metric, statistic, comparisonOperator, threshold, period, evaluationPeriods );
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

  private AlarmCreateOptions() {
  }

  private AlarmCreateOptions( String alarmName, String metricNamespace, String metric, String statistic, String comparisonOperator, double threshold, int period, int evaluationPeriods ) {
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

  public String getAlarmName() {
    return alarmName;
  }

  public String getAlarmDescription() {
    return alarmDescription;
  }

  public AlarmCreateOptions withDescription( String description ) {
    this.alarmDescription = description;
    return this;
  }

  public boolean isFunction() {
    return function;
  }

  public AlarmCreateOptions asFunction( boolean function ) {
    this.function = function;
    return this;
  }

  public String getMetric() {
    return metric;
  }

  public AlarmCreateOptions withMetric( String metric ) {
    this.metric = metric;
    return this;
  }

  public String getMetricNamespace() {
    return metricNamespace;
  }

  public Map<String, String> getMetadata() {
    return metadata;
  }

  public AlarmCreateOptions withMetadata( Map<String, String> metadata ) {
    this.metadata = metadata;
    return this;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public AlarmCreateOptions asEnabled( boolean enabled ) {
    this.enabled = enabled;
    return this;
  }

  public String[] getProviderAlarmActionIds() {
    return providerAlarmActionIds;
  }

  public AlarmCreateOptions withProviderAlarmActionIds( String[] providerAlarmActionIds ) {
    this.providerAlarmActionIds = providerAlarmActionIds;
    return this;
  }

  public String[] getProviderInsufficentDataActionIds() {
    return providerInsufficentDataActionIds;
  }

  public AlarmCreateOptions withProviderInsufficentDataActionIds( String[] providerInsufficentDataActionIds ) {
    this.providerInsufficentDataActionIds = providerInsufficentDataActionIds;
    return this;
  }

  public String[] getProviderOKActionIds() {
    return providerOKActionIds;
  }

  public AlarmCreateOptions withProviderOKActionIds( String[] providerOKActionIds ) {
    this.providerOKActionIds = providerOKActionIds;
    return this;
  }

  public int getPeriod() {
    return period;
  }

  public int getEvaluationPeriods() {
    return evaluationPeriods;
  }

  public String getStatistic() {
    return statistic;
  }

  public String getComparisonOperator() {
    return comparisonOperator;
  }

  public double getThreshold() {
    return threshold;
  }

}
