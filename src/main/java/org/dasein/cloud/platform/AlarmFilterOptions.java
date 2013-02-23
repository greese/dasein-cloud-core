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
  private AlarmState stateValue;

  private AlarmFilterOptions() {
  }

  /**
   * @return the alarm names to filter by
   */
  public String[] getAlarmNames() {
    return alarmNames;
  }

  /**
   * @return the alarm state to filter by
   */
  public AlarmState getStateValue() {
    return stateValue;
  }

  /**
   * Sets the alarm names to filter by.
   *
   * @param alarmNames the alarm names to filter by
   * @return this
   */
  public AlarmFilterOptions withAlarmNames( String[] alarmNames ) {
    this.alarmNames = alarmNames;
    return this;
  }

  /**
   * The alarm state to filter by.
   *
   * @param stateValue alarm state to filter by
   * @return this
   */
  public AlarmFilterOptions withStateValue( AlarmState stateValue ) {
    this.stateValue = stateValue;
    return this;
  }

}
