package org.dasein.cloud.network;

/**
 * Represents a server certificate associated with current account and region.
 *
 * @author Bulat Badretdinov
 */
public class SSLCertificate implements Networkable {

    private String                 certificateBody;
    private String                 certificateChain;
    private SSLCertificateMetadata metadata;

}
