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
