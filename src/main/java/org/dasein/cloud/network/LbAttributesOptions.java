package org.dasein.cloud.network;

import javax.annotation.Nonnull;

/**
 * User: mgulimonov
 * Date: 20.06.2014
 */
public class LbAttributesOptions {

    boolean modifyCrossDataCenter = false;
    boolean modifyConnectionDraining = false;
    boolean modifyConnectionTimeout = false;

    private boolean crossDataCenter;
    private boolean connectionDraining;
    private int connectionDrainingTimeout;
    private int idleConnectionTimeout;

    private LbAttributesOptions() {
    }

    public static LbAttributesOptions getInstance( boolean crossDataCenter, boolean connectionDraining, int connectionDrainingTimeout, int idleConnectionTimeout ) {
        return new LbAttributesOptions()
                .withCrossDataCenter(crossDataCenter)
                .withConnectionDraining(connectionDraining)
                .withConnectionDrainingTimeout(connectionDrainingTimeout)
                .withIdleConnectionTimeout(idleConnectionTimeout);
    }

    public static LbAttributesOptions getInstance() {
        return new LbAttributesOptions();
    }

    public boolean isModifyCrossDataCenter() {
        return modifyCrossDataCenter;
    }

    public boolean isModifyConnectionDraining() {
        return modifyConnectionDraining;
    }

    public boolean isModifyConnectionTimeout() {
        return modifyConnectionTimeout;
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

    public @Nonnull LbAttributesOptions withCrossDataCenter( boolean crossDataCenter ) {
        this.crossDataCenter = crossDataCenter;
        this.modifyCrossDataCenter = true;
        return this;
    }

    public @Nonnull LbAttributesOptions withConnectionDraining( boolean connectionDraining ) {
        this.connectionDraining = connectionDraining;
        this.modifyConnectionDraining = true;
        return this;
    }

    public @Nonnull LbAttributesOptions withConnectionDrainingTimeout( int connectionDrainingTimeout ) {
        this.connectionDrainingTimeout = connectionDrainingTimeout;
        this.modifyConnectionDraining = true;
        return this;
    }

    public @Nonnull LbAttributesOptions withIdleConnectionTimeout( int idleConnectionTimeout ) {
        this.idleConnectionTimeout = idleConnectionTimeout;
        this.modifyConnectionTimeout = true;
        return this;
    }

    public boolean isEmptyOptions() {
        return !modifyCrossDataCenter && !modifyConnectionDraining && !modifyConnectionTimeout;
    }
}
