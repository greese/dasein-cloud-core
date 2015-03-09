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
}
