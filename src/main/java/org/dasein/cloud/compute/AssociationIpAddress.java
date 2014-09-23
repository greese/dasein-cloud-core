package org.dasein.cloud.compute;

import org.dasein.cloud.network.RawAddress;

/**
 * @author Eugene Yaroslavtsev
 * @since 22.09.2014
 */
public class AssociationIpAddress {
    private RawAddress privateIpAddress;
    private RawAddress publicIp;
    private boolean primary;

    public AssociationIpAddress() {
    }

    public RawAddress getPrivateIpAddress() {
        return privateIpAddress;
    }

    public void setPrivateIpAddress( RawAddress privateIpAddress ) {
        this.privateIpAddress = privateIpAddress;
    }

    public RawAddress getPublicIp() {
        return publicIp;
    }

    public void setPublicIp( RawAddress publicIp ) {
        this.publicIp = publicIp;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary( boolean primary ) {
        this.primary = primary;
    }
}
