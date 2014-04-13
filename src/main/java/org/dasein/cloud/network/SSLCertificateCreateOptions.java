package org.dasein.cloud.network;

import javax.annotation.Nonnull;

/**
 * Parameters used to create a new server certificate.
 *
 * @author Bulat Badretdinov
 */
public class SSLCertificateCreateOptions {

    /**
     * Creates new parameters for a request to create a server certificate.
     * @param certificateBody certificate body as string
     * @param certificateChain certificate chain
     * @param path certificate path to use
     * @param privateKey private key as string
     * @param sslCertificateName the name to assign to created certificate
     * @return new instance of {@link SSLCertificateCreateOptions}
     */
    public static @Nonnull
    SSLCertificateCreateOptions getInstance(
            @Nonnull String certificateBody, @Nonnull String certificateChain, @Nonnull String path,
            @Nonnull String privateKey, @Nonnull String sslCertificateName) {
        SSLCertificateCreateOptions options = new SSLCertificateCreateOptions();
        options.certificateBody = certificateBody;
        options.certificateChain = certificateChain;
        options.path = path;
        options.privateKey = privateKey;
        options.sslCertificateName = sslCertificateName;
        return options;
    }

    private SSLCertificateCreateOptions() {}

    private String certificateBody;
    private String certificateChain;
    private String path;
    private String privateKey;
    private String sslCertificateName;

    /**
     * @return the body of certificate to be created
     */
    public @Nonnull String getCertificateBody() {
        return certificateBody;
    }

    /**
     * @return the chain of certificate to be created
     */
    public @Nonnull String getCertificateChain() {
        return certificateChain;
    }

    /**
     * @return the path of certificate to be created
     */
    public @Nonnull String getPath() {
        return path;
    }

    /**
     * @return the private key of certificate to be created
     */
    public @Nonnull String getPrivateKey() {
        return privateKey;
    }

    /**
     * @return the name of certificate to be created
     */
    public @Nonnull String getSSLCertificateName() {
        return sslCertificateName;
    }
}