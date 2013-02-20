package org.dasein.cloud.platform;

/**
 * Options for filtering alarms.
 *
 * @author Cameron Stokes (http://github.com/clstokes)
 * @since 2013-02-19
 */
public class AlarmFilterOptions {

  public static AlarmFilterOptions getInstance() {
    return new AlarmFilterOptions();
  }

  private String[] alarmNames;
  private String stateValue;

  private AlarmFilterOptions() {
  }

  public String[] getAlarmNames() {
    return alarmNames;
  }

  public String getStateValue() {
    return stateValue;
  }

  public AlarmFilterOptions withAlarmNames( String[] alarmNames ) {
    this.alarmNames = alarmNames;
    return this;
  }

  public AlarmFilterOptions withStateValue( String stateValue ) {
    this.stateValue = stateValue;
    return this;
  }

}
