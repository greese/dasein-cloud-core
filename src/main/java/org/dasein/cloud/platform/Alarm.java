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

  private boolean function;
  private String metric;
  private Map<String, String> metadata;

  private boolean enabled;

  private String providerAlarmId;
  private String[] providerAlarmActionIds;
  private String[] providerInsufficentDataActionIds;
  private String[] providerOKActionIds;

  private int period;
  private int evaluationPeriods;

  private String statistic;
  private String comparisonOperator;
  private String threshold;

  private String stateReason;
  private String stateReasonData;
  private long stateUpdatedTimestamp;
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

  public boolean isFunction() {
    return function;
  }

  public void setFunction( boolean function ) {
    this.function = function;
  }

  public String getMetric() {
    return metric;
  }

  public void setMetric( String metric ) {
    this.metric = metric;
  }

  public Map<String, String> getMetadata() {
    return metadata;
  }

  public void setMetadata( Map<String, String> newMetadata ) {
    this.metadata = newMetadata;
  }

  public void addMetadata( String name, String value ) {
    if ( this.metadata == null ) {
      this.metadata = new HashMap<String, String>();
    }
    this.metadata.put( name, value );
  }

  public void addMetadata( Map<String, String> newMetadata ) {
    if ( this.metadata == null ) {
      this.metadata = new HashMap<String, String>();
    }
    this.metadata.putAll( newMetadata );
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled( boolean enabled ) {
    this.enabled = enabled;
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

  public int getPeriod() {
    return period;
  }

  public void setPeriod( int period ) {
    this.period = period;
  }

  public int getEvaluationPeriods() {
    return evaluationPeriods;
  }

  public void setEvaluationPeriods( int evaluationPeriods ) {
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

  public long getStateUpdatedTimestamp() {
    return stateUpdatedTimestamp;
  }

  public void setStateUpdatedTimestamp( long stateUpdatedTimestamp ) {
    this.stateUpdatedTimestamp = stateUpdatedTimestamp;
  }

  public String getStateValue() {
    return stateValue;
  }

  public void setStateValue( String stateValue ) {
    this.stateValue = stateValue;
  }

}
