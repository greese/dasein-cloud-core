package org.dasein.cloud.compute;

import java.io.Serializable;

public class ScalingPolicy implements Serializable {
    private static final long serialVersionUID = 4090371193033312095L;

    private String                adjustmentType;
    private String                autoScalingGroupName;
    private int                   coolDown;
    private int                   minAdjustmentStep;
    private String                id;
    private String                name;
    private int                   scalingAdjustment;
    private Alarm[]               alarms;

    public ScalingPolicy() { }

    public void setAdjustmentType(String adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    public String getAdjustmentType() {
        return this.adjustmentType;
    }

    public void setAutoScalingGroupName(String autoScalingGroupName) {
      this.autoScalingGroupName = autoScalingGroupName;
    }

    public String getAutoScalingGroupName() {
      return this.autoScalingGroupName;
    }

    public void setCoolDown(int coolDown) {
      this.coolDown = coolDown;
    }

    public int getCoolDown() {
      return this.coolDown;
    }

    public void setMinAdjustmentStep(int minAdjustmentStep) {
      this.minAdjustmentStep = minAdjustmentStep;
    }

    public int getMinAdjustmentStep() {
      return this.minAdjustmentStep;
    }

    public void setId(String id) {
      this.id = id;
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

    public void setScalingAdjustment(int scalingAdjustment) {
      this.scalingAdjustment = scalingAdjustment;
    }

    public int getScalingAdjustment() {
      return this.scalingAdjustment;
    }

    public void setAlarms(Alarm[] alarms) {
      this.alarms = alarms;
    }

    public Alarm[] getAlarms() {
      return this.alarms;
    }
}
