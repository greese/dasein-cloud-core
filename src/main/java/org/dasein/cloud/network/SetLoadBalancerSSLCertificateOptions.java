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
            @Nonnull String loadBalancerName, @Nonnull int sslCertificateAssignToPort,
            @Nonnull String providerSslCertificateId ) {
        SetLoadBalancerSSLCertificateOptions options = new SetLoadBalancerSSLCertificateOptions();
        options.loadBalancerName = loadBalancerName;
        options.sslCertificateAssignToPort = sslCertificateAssignToPort;
        options.providerSslCertificateId = providerSslCertificateId ;
        return options;
    }

    private SetLoadBalancerSSLCertificateOptions() {}

    private String loadBalancerName;
    private int    sslCertificateAssignToPort;
    private String providerSslCertificateId;

    public @Nonnull String getLoadBalancerName() {
        return loadBalancerName;
    }

    public @Nonnull int getSslCertificateAssignToPort() {
        return sslCertificateAssignToPort;
    }

    public @Nonnull String getProviderSslCertificateId() {
        return providerSslCertificateId;
    }
}
