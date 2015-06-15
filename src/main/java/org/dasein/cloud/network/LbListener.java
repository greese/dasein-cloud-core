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
 * A listener represents a strategy for routing traffic of a specific network protocol coming over one of the load balancer's
 * public ports to a private port on the endpoints.
 * @author George Reese
 * @version 2013.04 added javadoc and support for data integrity and sticky sessions
 * @since unknown
 */
public class LbListener {
    /**
     * Constructs a listener that routes raw TCP traffic in a round-robin format from the specified public port to the
     * specified private port.
     * @param publicPort the public port on which the load balancer is listening
     * @param privatePort the private port on which endpoints are listening
     * @return a newly constructed listener
     */
    static public LbListener getInstance(int publicPort, int privatePort) {
        return new LbListener(LbAlgorithm.ROUND_ROBIN, LbPersistence.NONE, LbProtocol.RAW_TCP, publicPort, privatePort);
    }

    /**
     * Constructs a listener that routes traffic of the specified protocol in a round-robin format from the specified
     * public port to the specified private port.
     * @param protocol the network protocol being load balanced
     * @param publicPort the public port on which the load balancer is listening
     * @param privatePort the private port on which endpoints are listening
     * @return a newly constructed listener
     */
    static public LbListener getInstance(@Nonnull LbProtocol protocol, int publicPort, int privatePort) {
        return new LbListener(LbAlgorithm.ROUND_ROBIN, LbPersistence.NONE, protocol, publicPort, privatePort);
    }

    /**
     * Constructs a listener that routes traffic of the specified protocol in a round-robin format from the specified
     * public port to the specified private port. SSL certificate can be attached if protocol is HTTPS.
     * @param protocol the network protocol being load balanced
     * @param publicPort the public port on which the load balancer is listening
     * @param privatePort the private port on which endpoints are listening
     * @param sslCertificateName SSL certificate name to use for this listener in case protocol is HTTPS.
     *                         See also {@link LoadBalancerSupport#createSSLCertificate(SSLCertificateCreateOptions)}.
     * @return a newly constructed listener
     */
    static public LbListener getInstance(@Nonnull LbProtocol protocol, int publicPort, int privatePort,
            String sslCertificateName) {
        return new LbListener(LbAlgorithm.ROUND_ROBIN, LbPersistence.NONE, protocol, publicPort, privatePort,
                sslCertificateName);
    }

    /**
     * Constructs a listener that routes traffic of the specified protocol in accordance with the specified
     * load balancing algorithm based on server cookies for persistence from the specified
     * public port to the specified private port.
     * @param algorithm the load balancing algorithm in place
     * @param persistenceCookie the name of the cookie to use for server cookie persistence
     * @param protocol the network protocol being load balanced
     * @param publicPort the public port on which the load balancer is listening
     * @param privatePort the private port on which endpoints are listening
     * @return a newly constructed listener
     */
    static public LbListener getInstance(@Nonnull LbAlgorithm algorithm, @Nonnull String persistenceCookie, @Nonnull LbProtocol protocol, int publicPort, int privatePort) {
        LbListener listener = new LbListener(algorithm, LbPersistence.COOKIE, protocol, publicPort, privatePort);

        listener.cookie = persistenceCookie;
        return listener;
    }

    /**
     * Constructs a listener that routes traffic of the specified protocol in accordance with the specified
     * load balancing algorithm based on a specific stickiness strategy from the specified
     * public port to the specified private port.
     * @param algorithm the load balancing algorithm in place
     * @param persistence the persistence strategy for session stickiness
     * @param protocol the network protocol being load balanced
     * @param publicPort the public port on which the load balancer is listening
     * @param privatePort the private port on which endpoints are listening
     * @return a newly constructed listener
     */
    static public LbListener getInstance(@Nonnull LbAlgorithm algorithm, @Nonnull LbPersistence persistence, @Nonnull LbProtocol protocol, int publicPort, int privatePort) {
        return new LbListener(algorithm, persistence, protocol, publicPort, privatePort);
    }

    private LbAlgorithm   algorithm;
    private String        cookie;
    private LbProtocol    networkProtocol;
    private LbPersistence persistence;
    private int           privatePort;
    private int           publicPort;
    private String        sslCertificateName;
    private String        providerLBHealthCheckId;

    /**
     * Constructs an empty listener object.
     * @deprecated Use {@link #getInstance(LbAlgorithm, LbPersistence, LbProtocol, int, int)}
     */
    public LbListener() { }

    public LbListener(@Nonnull LbAlgorithm algorithm, @Nonnull LbPersistence persistence, @Nonnull LbProtocol protocol, int publicPort, int privatePort) {
        this(algorithm, persistence, protocol, publicPort, privatePort, null);
    }

    public LbListener(@Nonnull LbAlgorithm algorithm, @Nonnull LbPersistence persistence, @Nonnull LbProtocol protocol,
            int publicPort, int privatePort, @Nullable String sslCertificateName) {
        this.algorithm = algorithm;
        this.persistence = persistence;
        this.networkProtocol = protocol;
        this.publicPort = publicPort;
        this.privatePort = privatePort;
        this.sslCertificateName = sslCertificateName;
    }

    /**
     * @return the load balancing algorithm for the traffic associated with this listener
     */
    public @Nonnull LbAlgorithm getAlgorithm() {
        return (algorithm == null ? LbAlgorithm.ROUND_ROBIN : algorithm);
    }

    /**
     * @return the name of the cookie to be used when the persistence mechanism is a server cookie
     */
    public @Nullable String getCookie() {
        return cookie;
    }

    /**
     * Indicates the network protocol being load balanced (or {@link LbProtocol#RAW_TCP} if no protocol awareness exists).
     * @return the load balanced network protocol for the traffic associated with this listener
     */
    public @Nonnull LbProtocol getNetworkProtocol() {
        return (networkProtocol == null ? LbProtocol.RAW_TCP : networkProtocol);
    }

    /**
     * Indicates the stickiness of any client sessions using this listener to communicate to endpoints behind
     * the load balancer.
     * @return the load balancer persistence strategy
     */
    public @Nonnull LbPersistence getPersistence() {
        return (persistence == null ? LbPersistence.NONE : persistence);
    }

    /**
     * The private port is some port that all endpoints have open to receive traffic from the load balancer that the
     * load balancer, in turn, is receiving over the {@link #getPublicPort()}.
     * @return the private port endpoints should have open for this listener
     */
    public int getPrivatePort() {
        return privatePort;
    }

    /**
     * The public port is a port matching one of the load balancer's public ports {@link LoadBalancer#getPublicPorts()}
     * on which the load balancer is listening for traffic to be routed to one of its endpoints listening on the
     * associated {@link #getPrivatePort()}.
     * @return the public port through which traffic is going to be routed on the load balancer
     */
    public int getPublicPort() {
        return publicPort;
    }

    /**
     * The ID of the server certificate. To create a server certificate, call the
     * {@link LoadBalancerSupport#createSSLCertificate(SSLCertificateCreateOptions)}.
     * @return the SSL certificate ID.
     */
    public String getSslCertificateName() {
        return sslCertificateName;
    }

    /**
     * @return the ID of a Health Check if one is attached.
     */
    public @Nullable String getProviderLBHealthCheckId(){
        return providerLBHealthCheckId;
    }

    /**
     * Sets the SSL Certificate Name assoicated with this listener
     * @param sslCertificateName the SSL Certificate name
     * @return this
     */
    public @Nonnull LbListener withSslCertificateName(@Nullable String sslCertificateName) {
        this.sslCertificateName = sslCertificateName;
        return this;
    }

    /**
     * Sets the Health Check ID associated with this listener
     * @param providerLBHealthCheckId the Health Check ID
     * @return this
     */
    public @Nonnull LbListener withProviderLBHealthCheckId(@Nullable String providerLBHealthCheckId) {
        this.providerLBHealthCheckId = providerLBHealthCheckId;
        return this;
    }


    @Override
    public @Nonnull String toString() {
        return (networkProtocol + "/" + algorithm + "/" + persistence + ": " + publicPort + " -> " + privatePort);
    }

    /******************************* DEPRECATED METHODS ***********************************/

    /**
     * Sets the load balancing algorithm associated with this listener.
     * @param algorithm the load balancing algorithm for the listener
     * @deprecated Use the new factory methods for constructing a listener
     */
    public void setAlgorithm(@Nonnull LbAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Sets the network protocol for the traffic managed by this load balancer
     * @param networkProtocol the load balancer's network protocol
     * @deprecated Use the new factory methods for constructing a listener
     */
    public void setNetworkProtocol(@Nonnull LbProtocol networkProtocol) {
        this.networkProtocol = networkProtocol;
    }

    /**
     * Sets the private port associated with the listener.
     * @param privatePort the private port on which the endpoints are listening
     * @deprecated Use the new factory methods for constructing a listener
     */
    public void setPrivatePort(int privatePort) {
        this.privatePort = privatePort;
    }

    /**
     * Sets the public port associated with the listener.
     * @param publicPort the public port on which the load balancer is listening
     * @deprecated Use the new factory methods for constructing a listener
     */
    public void setPublicPort(int publicPort) {
        this.publicPort = publicPort;
    }
}
