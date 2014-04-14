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

    public static @Nonnull SSLCertificateMetadata getInstance(@Nonnull String arn, @Nonnull String path,
                                          @Nonnull String certificateId, @Nonnull String certificateName,
                                          @Nullable Long uploadDate) {
        SSLCertificateMetadata m = new SSLCertificateMetadata();
        m.arn = arn;
        m.path = path;
        m.certificateId = certificateId;
        m.certificateName = certificateName;
        m.uploadDate = uploadDate;
        return m;
    }

    private SSLCertificateMetadata() {}

    private String arn;
    private String path;
    private String certificateId;
    private String certificateName;
    private Long   uploadDate;

    public @Nonnull String getArn() {
        return arn;
    }

    public @Nonnull String getPath() {
        return path;
    }

    public @Nonnull String getCertificateId() {
        return certificateId;
    }

    public @Nonnull String getCertificateName() {
        return certificateName;
    }

    public @Nullable Long getUploadDate() {
        return uploadDate;
    }
}
