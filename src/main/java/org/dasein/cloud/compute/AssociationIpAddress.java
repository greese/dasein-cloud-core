package org.dasein.cloud.compute;

import org.dasein.cloud.network.RawAddress;

/**
 * @author Eugene Yaroslavtsev
 * @since 22.09.2014
 */
public class AssociationIpAddress {
    private RawAddress privateIpAddress;
    private RawAddress publicIpAddress;
    private String publicDnsName;
    private boolean primary;

    public AssociationIpAddress() {
    }

    public RawAddress getPrivateIpAddress() {
        return privateIpAddress;
    }

    public void setPrivateIpAddress( RawAddress privateIpAddress ) {
        this.privateIpAddress = privateIpAddress;
    }

    public RawAddress getPublicIpAddress() {
        return publicIpAddress;
    }

    public void setPublicIpAddress( RawAddress publicIpAddress ) {
        this.publicIpAddress = publicIpAddress;
    }

    public String getPublicDnsName() {
        return publicDnsName;
    }

    public void setPublicDnsName( String publicDnsName ) {
        this.publicDnsName = publicDnsName;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary( boolean primary ) {
        this.primary = primary;
    }
}
