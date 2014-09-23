package org.dasein.cloud.compute;

/**
 * @author Eugene Yaroslavtsev
 * @since 22.09.2014
 */
public class ProviderNetworkInterface {
    private String networkInterfaceId;
    private AssociationIpAddress[] associationIpAddresses;

    public ProviderNetworkInterface() {
    }

    public String getNetworkInterfaceId() {
        return networkInterfaceId;
    }

    public void setNetworkInterfaceId( String networkInterfaceId ) {
        this.networkInterfaceId = networkInterfaceId;
    }

    public AssociationIpAddress[] getAssociationIpAddresses() {
        return associationIpAddresses;
    }

    public void setAssociationIpAddresses( AssociationIpAddress[] associationIpAddresses ) {
        this.associationIpAddresses = associationIpAddresses;
    }
}
