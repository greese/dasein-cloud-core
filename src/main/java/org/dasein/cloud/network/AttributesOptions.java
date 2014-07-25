package org.dasein.cloud.network;

import javax.annotation.Nullable;

/**
 * User: mgulimonov
 * Date: 20.06.2014
 */
public class AttributesOptions {

    Boolean crossZone;
    Boolean connectionDraining;
    Integer connectionDrainingTimeout;
    Integer idleConnectionTimeout;

    public static AttributesOptions getInstance(Boolean crossZone, Boolean connectionDraining, @Nullable Integer connectionDrainingTimeout,
                                                @Nullable Integer idleConnectionTimeout) {
        AttributesOptions options = new AttributesOptions();
        options.crossZone = crossZone;
        options.connectionDraining = connectionDraining;
        options.connectionDrainingTimeout = connectionDrainingTimeout;
        options.idleConnectionTimeout = idleConnectionTimeout;
        return options;
    }

    public Boolean getCrossZone() {
        return crossZone;
    }

    public void setCrossZone(Boolean crossZone) {
        this.crossZone = crossZone;
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
