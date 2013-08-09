package org.dasein.cloud.compute;

import java.io.Serializable;

/**
 * <p>
 * Terse alarm class for use inside other resources.
 * Specific scenario currently documented for AWS where calling 'DescribePolicies'
 * for autoscaling: http://docs.aws.amazon.com/AutoScaling/latest/APIReference/API_DescribePolicies.html
 * This call returns an object (http://docs.aws.amazon.com/AutoScaling/latest/APIReference/API_ScalingPolicy.html)
 * with an alarm list that consist only of name and ARN: http://docs.aws.amazon.com/AutoScaling/latest/APIReference/API_Alarm.html
 * </p>
 *
 * @author Chris Kelner (chris.kelner@weather.com) - [http://github.com/ckelner]
 *
 */

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
