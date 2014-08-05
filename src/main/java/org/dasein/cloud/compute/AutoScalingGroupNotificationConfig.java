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

    public void setAutoScalingGroupName(String autoScalingGroupName) {
        this.autoScalingGroupName = autoScalingGroupName;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
