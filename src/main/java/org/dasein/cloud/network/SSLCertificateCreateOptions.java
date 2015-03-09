/**
 * Copyright (C) 2009-2015 Dell, Inc.
 * See annotations for authorship information
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */

package org.dasein.cloud.network;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
     * @param privateKey private key as string
     * @param certificateName the ID to assign to created certificate
     * @param path certificate path to use
     * @return new instance of {@link SSLCertificateCreateOptions}
     */
    public static @Nonnull SSLCertificateCreateOptions getInstance(
            @Nonnull String certificateBody, @Nullable String certificateChain,
            @Nonnull String privateKey, @Nonnull String certificateName, @Nullable String path) {
        SSLCertificateCreateOptions options = new SSLCertificateCreateOptions();
        options.certificateBody = certificateBody;
        options.certificateChain = certificateChain;
        options.path = path;
        options.privateKey = privateKey;
        options.certificateName = certificateName;
        return options;
    }

    private SSLCertificateCreateOptions() {}

    private String certificateBody;
    private String certificateChain;
    private String path;
    private String privateKey;
    private String certificateName;

    /**
     * @return the body of certificate to be created
     */
    public @Nonnull String getCertificateBody() {
        return certificateBody;
    }

    /**
     * @return the chain of certificate to be created
     */
    public @Nullable String getCertificateChain() {
        return certificateChain;
    }

    /**
     * @return the path of certificate to be created
     */
    public @Nullable String getPath() {
        return path;
    }

    /**
     * @return the private key of certificate to be created
     */
    public @Nonnull String getPrivateKey() {
        return privateKey;
    }

    /**
     * @return the ID of certificate to be created
     */
    public @Nonnull String getCertificateName() {
        return certificateName;
    }
}