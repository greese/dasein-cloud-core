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
 * @author Eugene Yaroslavtsev
 * @since 09.07.2014
 */
public class AutoScalingGroupNotificationConfig implements Serializable {
    private String autoScalingGroupName;
    private String notificationType;
    private String topic;

    public AutoScalingGroupNotificationConfig() {
    }

    public String getAutoScalingGroupName() {
        return autoScalingGroupName;
    }

    public void setAutoScalingGroupName( String autoScalingGroupName ) {
        this.autoScalingGroupName = autoScalingGroupName;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType( String notificationType ) {
        this.notificationType = notificationType;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic( String topic ) {
        this.topic = topic;
    }
}
