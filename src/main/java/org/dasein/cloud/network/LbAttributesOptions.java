package org.dasein.cloud.network;

import javax.annotation.Nullable;

/**
 * User: mgulimonov
 * Date: 20.06.2014
 */
public class LbAttributesOptions {

    Boolean crossDataCenter;
    Boolean connectionDraining;
    Integer connectionDrainingTimeout;
    Integer idleConnectionTimeout;

    public static LbAttributesOptions getInstance(Boolean crossDataCenter, Boolean connectionDraining, @Nullable Integer connectionDrainingTimeout,
                                                @Nullable Integer idleConnectionTimeout) {
        LbAttributesOptions options = new LbAttributesOptions();
        options.crossDataCenter = crossDataCenter;
        options.connectionDraining = connectionDraining;
        options.connectionDrainingTimeout = connectionDrainingTimeout;
        options.idleConnectionTimeout = idleConnectionTimeout;
        return options;
    }

    public Boolean getCrossDataCenter() {
        return crossDataCenter;
    }

    public void setCrossDataCenter( Boolean crossDataCenter ) {
        this.crossDataCenter = crossDataCenter;
    }

    public Boolean getConnectionDraining() {
        return connectionDraining;
    }

    public void setConnectionDraining(Boolean connectionDraining) {
        this.connectionDraining = connectionDraining;
    }

    public Integer getConnectionDrainingTimeout() {
        return connectionDrainingTimeout;
    }

    public void setConnectionDrainingTimeout(Integer connectionDrainingTimeout) {
        this.connectionDrainingTimeout = connectionDrainingTimeout;
    }

    public Integer getIdleConnectionTimeout() {
        return idleConnectionTimeout;
    }

    public void setIdleConnectionTimeout(Integer idleConnectionTimeout) {
        this.idleConnectionTimeout = idleConnectionTimeout;
    }
}
