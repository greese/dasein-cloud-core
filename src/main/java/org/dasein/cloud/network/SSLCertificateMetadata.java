package org.dasein.cloud.network;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents metadata of a server certificate: all its information
 * except for body and chain.
 *
 * @author Bulat Badretdinov
 */
public class SSLCertificateMetadata implements Networkable {

    public static @Nonnull SSLCertificateMetadata getInstance(@Nonnull String providerCertificateId,
                                          @Nonnull String path, @Nonnull String certificateId,
                                          @Nullable Long uploadDate) {
        SSLCertificateMetadata m = new SSLCertificateMetadata();
        m.providerCertificateId = providerCertificateId;
        m.path = path;
        m.certificateId = certificateId;
        m.uploadDate = uploadDate;
        return m;
    }

    private SSLCertificateMetadata() {}

    private String providerCertificateId;
    private String path;
    private String certificateId;
    private Long   uploadDate;

    public @Nonnull String getProviderCertificateId() {
        return providerCertificateId;
    }

    public @Nonnull String getPath() {
        return path;
    }

    public @Nonnull String getCertificateId() {
        return certificateId;
    }

    public @Nullable Long getUploadDate() {
        return uploadDate;
    }

    @Override
    public @Nonnull String toString() {
        return certificateId + " (" + uploadDate + ") [#" + providerCertificateId + "]";
    }

}
