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

/**
 * Parameters used to assign an SSL certificate to
 * a port of load balancer.
 *
 * @author Bulat Badretdinov
 */
public class SetLoadBalancerSSLCertificateOptions {

    public static @Nonnull SetLoadBalancerSSLCertificateOptions getInstance(
            @Nonnull String loadBalancerName, int sslCertificateAssignToPort,
            @Nonnull String sslCertificateName ) {
        SetLoadBalancerSSLCertificateOptions options = new SetLoadBalancerSSLCertificateOptions();
        options.loadBalancerName = loadBalancerName;
        options.sslCertificateAssignToPort = sslCertificateAssignToPort;
        options.sslCertificateName = sslCertificateName ;
        return options;
    }

    private SetLoadBalancerSSLCertificateOptions() {}

    private String loadBalancerName;
    private int    sslCertificateAssignToPort;
    private String sslCertificateName;

    public @Nonnull String getLoadBalancerName() {
        return loadBalancerName;
    }

    public int getSslCertificateAssignToPort() {
        return sslCertificateAssignToPort;
    }

    public @Nonnull String getSslCertificateName() {
        return sslCertificateName;
    }
}
