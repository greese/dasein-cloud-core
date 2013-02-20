package org.dasein.cloud.platform;

import java.util.Map;

/**
 * Represents an alarm associated with a monitoring service.
 *
 * @author Cameron Stokes (http://github.com/clstokes)
 * @since 2013-02-19
 */
public class Alarm {

  private String name;
  private String description;
  private String metricNamespace;
  private String metricName;
  private Map<String, String> dimensions;

  private boolean actionsEnabled;

  private String providerAlarmId;
  private String[] providerAlarmActionIds;
  private String[] providerInsufficentDataActionIds;
  private String[] providerOKActionIds;

  private String period;
  private String evaluationPeriods;

  private String statistic;
  private String comparisonOperator;
  private String threshold;
  private String unit;

  private String stateReason;
  private String stateReasonData;
  private String stateUpdatedTimestamp;
  private String stateValue;

  public String getName() {
    return name;
  }

  public void setName( String name ) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription( String description ) {
    this.description = description;
  }

  public String getMetricNamespace() {
    return metricNamespace;
  }

  public void setMetricNamespace( String metricNamespace ) {
    this.metricNamespace = metricNamespace;
  }

  public String getMetricName() {
    return metricName;
  }

  public void setMetricName( String metricName ) {
    this.metricName = metricName;
  }

  public Map<String, String> getDimensions() {
    return dimensions;
  }

  public void setDimensions( Map<String, String> dimensions ) {
    this.dimensions = dimensions;
  }

  public boolean isActionsEnabled() {
    return actionsEnabled;
  }

  public void setActionsEnabled( boolean actionsEnabled ) {
    this.actionsEnabled = actionsEnabled;
  }

  public String getProviderAlarmId() {
    return providerAlarmId;
  }

  public void setProviderAlarmId( String providerAlarmId ) {
    this.providerAlarmId = providerAlarmId;
  }

  public String[] getProviderAlarmActionIds() {
    return providerAlarmActionIds;
  }

  public void setProviderAlarmActionIds( String[] providerAlarmActionIds ) {
    this.providerAlarmActionIds = providerAlarmActionIds;
  }

  public String[] getProviderInsufficentDataActionIds() {
    return providerInsufficentDataActionIds;
  }

  public void setProviderInsufficentDataActionIds( String[] providerInsufficentDataActionIds ) {
    this.providerInsufficentDataActionIds = providerInsufficentDataActionIds;
  }

  public String[] getProviderOKActionIds() {
    return providerOKActionIds;
  }

  public void setProviderOKActionIds( String[] providerOKActionIds ) {
    this.providerOKActionIds = providerOKActionIds;
  }

  public String getPeriod() {
    return period;
  }

  public void setPeriod( String period ) {
    this.period = period;
  }

  public String getEvaluationPeriods() {
    return evaluationPeriods;
  }

  public void setEvaluationPeriods( String evaluationPeriods ) {
    this.evaluationPeriods = evaluationPeriods;
  }

  public String getStatistic() {
    return statistic;
  }

  public void setStatistic( String statistic ) {
    this.statistic = statistic;
  }

  public String getComparisonOperator() {
    return comparisonOperator;
  }

  public void setComparisonOperator( String comparisonOperator ) {
    this.comparisonOperator = comparisonOperator;
  }

  public String getThreshold() {
    return threshold;
  }

  public void setThreshold( String threshold ) {
    this.threshold = threshold;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit( String unit ) {
    this.unit = unit;
  }

  public String getStateReason() {
    return stateReason;
  }

  public void setStateReason( String stateReason ) {
    this.stateReason = stateReason;
  }

  public String getStateReasonData() {
    return stateReasonData;
  }

  public void setStateReasonData( String stateReasonData ) {
    this.stateReasonData = stateReasonData;
  }

  public String getStateUpdatedTimestamp() {
    return stateUpdatedTimestamp;
  }

  public void setStateUpdatedTimestamp( String stateUpdatedTimestamp ) {
    this.stateUpdatedTimestamp = stateUpdatedTimestamp;
  }

  public String getStateValue() {
    return stateValue;
  }

  public void setStateValue( String stateValue ) {
    this.stateValue = stateValue;
  }

}
