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
            @Nonnull String loadBalancerName, @Nonnull String sslCertificateAssignToPort,
            @Nonnull String sslCertificateResourceId) {
        SetLoadBalancerSSLCertificateOptions options = new SetLoadBalancerSSLCertificateOptions();
        options.loadBalancerName = loadBalancerName;
        options.sslCertificateAssignToPort = sslCertificateAssignToPort;
        options.sslCertificateResourceId = sslCertificateResourceId;
        return options;
    }

    private SetLoadBalancerSSLCertificateOptions() {}

    private String loadBalancerName;
    private String sslCertificateAssignToPort;
    private String sslCertificateResourceId;

    public @Nonnull String getLoadBalancerName() {
        return loadBalancerName;
    }

    public @Nonnull String getSslCertificateAssignToPort() {
        return sslCertificateAssignToPort;
    }

    public @Nonnull String getSslCertificateResourceId() {
        return sslCertificateResourceId;
    }
}
