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
     * @param certificateId SSL certificate ID
     * @param providerCertificateId provider-specific SSL certificate ID
     * @param createdTimestamp the timestamp when certificate was uploaded
     * @param certificateBody the certificate body
     * @param certificateChain certificate chain
     * @param path certificate path
     * @return the SSL certificate object
     */
    public static @Nonnull SSLCertificate getInstance(@Nonnull String certificateId,
                                          @Nonnull String providerCertificateId, @Nullable Long createdTimestamp,
                                          @Nullable String certificateBody, @Nullable String certificateChain,
                                          @Nonnull String path) {
        SSLCertificate c = new SSLCertificate();
        c.certificateBody = certificateBody;
        c.certificateChain = certificateChain;
        c.providerCertificateId = providerCertificateId;
        c.path = path;
        c.certificateId = certificateId;
        c.createdTimestamp = createdTimestamp;
        return c;
    }

    private SSLCertificate() {}

    private String certificateId;
    private String providerCertificateId;
    private Long   createdTimestamp;
    private String certificateBody;
    private String certificateChain;
    private String path;

    public @Nonnull String getCertificateId() {
        return certificateId;
    }

    public @Nonnull String getProviderCertificateId() {
        return providerCertificateId;
    }

    public @Nullable Long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public @Nullable String getCertificateBody() {
        return certificateBody;
    }

    public @Nullable String getCertificateChain() {
        return certificateChain;
    }

    public @Nullable String getPath() {
        return path;
    }

    @Override
    public @Nonnull String toString() {
        return certificateId + " (" + createdTimestamp + ") [#" + providerCertificateId + "]";
    }

}
