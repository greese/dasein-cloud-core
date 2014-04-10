package org.dasein.cloud.network;

/**
 * Represents a server certificate associated with current account and region.
 *
 * @author Bulat Badretdinov
 */
public class ServerCertificate implements Networkable {

    private String certificateBody;
    private String certificateChain;
    private ServerCertificateMetadata metadata;

}
