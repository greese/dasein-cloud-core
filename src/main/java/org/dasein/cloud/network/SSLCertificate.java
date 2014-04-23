package org.dasein.cloud.network;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents a server certificate associated with current account and region.
 *
 * @author Bulat Badretdinov
 */
public class SSLCertificate implements Networkable {

    /**
     * Create new instance of SSL certificate details.
     * @param certificateBody the certificate body
     * @param certificateChain certificate chain
     * @param metadata additional metadata of the certificate
     * @return the SSL certificate object
     */
    public static @Nonnull SSLCertificate getInstance(@Nonnull String certificateBody,
                                          @Nullable String certificateChain,
                                          @Nonnull SSLCertificateMetadata metadata ) {
        SSLCertificate c = new SSLCertificate();
        c.certificateBody = certificateBody;
        c.certificateChain = certificateChain;
        c.metadata = metadata;
        return c;
    }

    private SSLCertificate() {}

    private String                 certificateBody;
    private String                 certificateChain;
    private SSLCertificateMetadata metadata;

    public @Nonnull String getCertificateBody() {
        return certificateBody;
    }

    public @Nullable String getCertificateChain() {
        return certificateChain;
    }

    public @Nonnull SSLCertificateMetadata getMetadata() {
        return metadata;
    }

    @Override
    public @Nonnull String toString() {
        return metadata == null ? "[no SSL certificate metadata]" : metadata.toString();
    }

}
