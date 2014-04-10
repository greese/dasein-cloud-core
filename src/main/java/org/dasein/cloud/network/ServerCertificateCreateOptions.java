package org.dasein.cloud.network;

import javax.annotation.Nonnull;

/**
 * Parameters used to create a new server certificate.
 *
 * @author Bulat Badretdinov
 */
public class ServerCertificateCreateOptions {

    /**
     * Creates new parameters for a request to create a server certificate.
     * @param certificateBody certificate body as string
     * @param certificateChain certificate chain
     * @param path certificate path to use
     * @param privateKey private key as string
     * @param serverCertificateName the name to assign to created certificate
     * @return new instance of {@link ServerCertificateCreateOptions}
     */
    public static @Nonnull ServerCertificateCreateOptions getInstance(
            @Nonnull String certificateBody, @Nonnull String certificateChain, @Nonnull String path,
            @Nonnull String privateKey, @Nonnull String serverCertificateName) {
        ServerCertificateCreateOptions options = new ServerCertificateCreateOptions();
        options.certificateBody = certificateBody;
        options.certificateChain = certificateChain;
        options.path = path;
        options.privateKey = privateKey;
        options.serverCertificateName = serverCertificateName;
        return options;
    }

    private ServerCertificateCreateOptions() {}

    private String certificateBody;
    private String certificateChain;
    private String path;
    private String privateKey;
    private String serverCertificateName;

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
    public @Nonnull String getServerCertificateName() {
        return serverCertificateName;
    }
}