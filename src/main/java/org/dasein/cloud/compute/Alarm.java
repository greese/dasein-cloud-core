/*
  A simple alarm to support AutoScaling...
 */

package org.dasein.cloud.compute;

import java.io.Serializable;

public class Alarm implements Serializable {
    private static final long serialVersionUID = 4416279768844752736L;

    private String                name;
    private String                alarmARN;

    public Alarm() { }

    public void setAlarmARN(String alarmARN) {
      this.alarmARN = alarmARN;
    }

    public String getAlarmARN() {
      return this.alarmARN;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getName() {
      return this.name;
    }
}
