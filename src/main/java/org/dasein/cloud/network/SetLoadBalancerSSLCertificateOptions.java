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
