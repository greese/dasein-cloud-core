/*
  A simple alarm to support AutoScaling...
 */

package org.dasein.cloud.compute;

import java.io.Serializable;

public class Alarm implements Serializable {
    private static final long serialVersionUID = 4416279768844752736L;

    private String                name;
    private String                id;

    public Alarm() { }

    public void setId(String alarmARN) {
      this.id = alarmARN;
    }

    public String getId() {
      return this.id;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getName() {
      return this.name;
    }
}
