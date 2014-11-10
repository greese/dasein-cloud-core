package org.dasein.cloud.network;

/**
 * User: mgulimonov
 * Date: 20.06.2014
 */
public class LbAttributesOptions {

    boolean crossDataCenter;
    boolean connectionDraining;
    int connectionDrainingTimeout;
    int idleConnectionTimeout;

    public static LbAttributesOptions getInstance( boolean crossDataCenter, boolean connectionDraining, int connectionDrainingTimeout, int idleConnectionTimeout ) {
        LbAttributesOptions options = new LbAttributesOptions();
        options.crossDataCenter = crossDataCenter;
        options.connectionDraining = connectionDraining;
        options.connectionDrainingTimeout = connectionDrainingTimeout;
        options.idleConnectionTimeout = idleConnectionTimeout;
        return options;
    }

    public boolean isCrossDataCenter() {
        return crossDataCenter;
    }

    public boolean isConnectionDraining() {
        return connectionDraining;
    }

    public int getConnectionDrainingTimeout() {
        return connectionDrainingTimeout;
    }

    public int getIdleConnectionTimeout() {
        return idleConnectionTimeout;
    }
}
