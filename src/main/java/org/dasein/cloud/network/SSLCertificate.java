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
 * Represents a server certificate associated with current account and region.
 *
 * @author Bulat Badretdinov
 */
public class SSLCertificate implements Networkable {

    /**
     * Create new instance of SSL certificate details.
     * @param certificateName SSL certificate ID
     * @param providerCertificateId provider-specific SSL certificate ID
     * @param createdTimestamp the timestamp when certificate was uploaded
     * @param certificateBody the certificate body
     * @param certificateChain certificate chain
     * @param path certificate path
     * @return the SSL certificate object
     */
    public static @Nonnull SSLCertificate getInstance(@Nonnull String certificateName,
                                          @Nonnull String providerCertificateId, @Nullable Long createdTimestamp,
                                          @Nullable String certificateBody, @Nullable String certificateChain,
                                          @Nonnull String path) {
        SSLCertificate c = new SSLCertificate();
        c.certificateBody = certificateBody;
        c.certificateChain = certificateChain;
        c.providerCertificateId = providerCertificateId;
        c.path = path;
        c.certificateName = certificateName;
        c.createdTimestamp = createdTimestamp;
        return c;
    }

    private SSLCertificate() {}

    private String certificateName;
    private String providerCertificateId;
    private Long   createdTimestamp;
    private String certificateBody;
    private String certificateChain;
    private String path;

    public @Nonnull String getCertificateName() {
        return certificateName;
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
        return certificateName + " (" + createdTimestamp + ") [#" + providerCertificateId + "]";
    }

}
