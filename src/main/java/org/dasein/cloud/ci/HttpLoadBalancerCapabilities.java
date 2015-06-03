package org.dasein.cloud.ci;

import java.util.Locale;

import javax.annotation.Nonnull;

import org.dasein.cloud.Capabilities;
import org.dasein.cloud.util.NamingConstraints;
/**
 * Describes the capabilities of a HttpLoadBalancer within a cloud for a specific account.
 * <p>Created by Roger Unwin: 4/27/15</p>
 * @author Roger Unwin
 * @version 2015.03 initial version
 * @since 2015.03
 */
public interface HttpLoadBalancerCapabilities extends Capabilities {
    /**
     * Provides the HttpLoadBalancer terminology for the concept of a HttpLoadBalancer. For example, GCE calls a
     * HttpLoadBalancer a "HttpLoadBalancer".
     * @param locale the locale for which you should translate the HttpLoadBalancer term
     * @return the translated term for HttpLoadBalancer with the target cloud provider
     */
    public String getProviderTermForHttpLoadBalancer(@Nonnull Locale locale);

    /**
     * Indicates whether or not the HttpLoadBalancer supports HTTP traffic
     * @return <code>true</code> if HTTP traffic is supported
     */
    public boolean supportsHttpTraffic();

    /**
     * Returns the supported ports that HTTP traffic may travel over
     * @return <code>Iterable<String></code> of available ports.
     */
    public @Nonnull Iterable<String> listSupportedHttpPorts();

    /**
     * Indicates whether or not the HttpLoadBalancer supports HTTPS traffic
     * @return <code>true</code> if HTTPS traffic is supported
     */
    public boolean supportsHttpsTraffic();

    /**
     * Indicates whether or not you can create new HealthChecks 
     * @return <code>true</code> if creating HealthChecks is supported
     */
    public boolean supportsHealthChecks();

    /**
     * Indicates whether or not you can create more than one HealthChecks for a HttpLoadBalancer
     * @return <code>true</code> if creating more than one HealthCheck per HttpLoadBalancer is supported
     */
    public boolean supportsMoreThanOneHealthCheck();

    /**
     * Indicates whether or not you can use an existing HealthCheck
     * @return <code>true</code> if using existing HealthChecks is supported
     */
    public boolean supportsUsingExistingHealthCheck();

    /**
     * Indicates whether or not you can create new BackendServices 
     * @return <code>true</code> if creating BackendServices is supported
     */
    public boolean supportsBackendServices();

    /**
     * Indicates whether or not you can create more than one BackendService for a HttpLoadBalancer
     * @return <code>true</code> if creating more than one BackendService per HttpLoadBalancer is supported
     */
    public boolean supportsMoreThanOneBackendService();

    /**
     * Indicates whether or not you can use an existing BackendService
     * @return <code>true</code> if using existing BackendService is supported
     */
    public boolean supportsUsingExistingBackendService();

    /**
     * Indicates whether or not you can create new UrlSets 
     * @return <code>true</code> if creating UrlSets is supported
     */
    public boolean supportsUrlSets();

    /**
     * Indicates whether or not you can create more than one UrlSet for a HttpLoadBalancer
     * @return <code>true</code> if creating more than one UrlSet per HttpLoadBalancer is supported
     */
    public boolean supportsMoreThanOneUrlSet();

    /**
     * Indicates whether or not you can create new TargetHttpProxies 
     * @return <code>true</code> if creating TargetHttpProxies is supported
     */
    public boolean supportsTargetHttpProxies();

    /**
     * Indicates whether or not you can create more than one TargetHttpProxy for a HttpLoadBalancer
     * @return <code>true</code> if creating more than one TargetHttpProxy per HttpLoadBalancer is supported
     */
    public boolean supportsMoreThanOneTargetHttpProxy();

    /**
     * Indicates whether or not you can create new ForwardingRules 
     * @return <code>true</code> if creating ForwardingRules is supported
     */
    public boolean supportsForwardingRules();

    /**
     * Indicates whether or not you can create more than one ForwardingRule for a HttpLoadBalancer
     * @return <code>true</code> if creating more than one ForwardingRule per HttpLoadBalancer is supported
     */
    public boolean supportsMoreThanOneForwardingRule();

    /**
     * Identifies the naming conventions that constrain how http load balancers may be named (friendly name) in this cloud.
     * @return naming conventions that constrain http load balancers naming
     */
    public @Nonnull NamingConstraints getConvergedHttpLoadBalancerNamingConstraints();
}
