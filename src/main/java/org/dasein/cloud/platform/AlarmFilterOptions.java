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
