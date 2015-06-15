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

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Options for adding a Health Check to a LoadBalancer. Different clouds have very different
 * requirements when specifying what is required for a Health Check with many requiring them to be
 * created at the same time and dependant on the life cycle of the LB they're attached to
 */
public class HealthCheckOptions {
    private String                             name;
    private String                             description;
    private String                             providerLoadBalancerId;
    private LbListener                         listener;
    private String                             host;
    private LoadBalancerHealthCheck.HCProtocol protocol;
    private int                                port;
    private String                             path;
    private int                             interval;
    private int                             timeout;
    //If left as 0 assume to use the default values for the underlying cloud
    private int                                unhealthyCount = 0;
    private int                                healthyCount = 0;

    /**
     * Create HealthCheckOptions instance
     *
     * @param name
     *          Name/ID of the health check.
     * @param description
     *          Description of the health check.
     * @param providerLoadBalancerId
     *          Name/ID of the LoadBalancer this HealthCheck is for.
     * @param host
     *          Hostname of the VM instance being checked.
     * @param protocol
     *          Network protocol being used for the check.
     * @param port
     *          Network port on the VM instance used for the check.
     * @param path
     *          For HTTP/HTTPS protocols a path on the host that must return 200 OK.
     * @param interval
     *          Approximate interval, in seconds, between the checks.
     * @param timeout
     *          Timeout, in seconds, that is allowed before the check fails.
     * @param healthyCount
     *          Number of consecutive successful checks required to mark VM instance as healthy.
     * @param unhealthyCount
     *          Number of consecutive unsuccessful checks required to mark VM instance as unhealthy.
     * @return HealthCheckOptions instance
     */
    public static HealthCheckOptions getInstance(@Nullable String name, @Nullable String description, @Nullable String providerLoadBalancerId, @Nullable String host, @Nullable LoadBalancerHealthCheck.HCProtocol protocol, int port, @Nullable String path, int interval, int timeout, int healthyCount, int unhealthyCount){
        HealthCheckOptions options = new HealthCheckOptions();
        options.name = name;
        options.description = description;
        options.providerLoadBalancerId = providerLoadBalancerId;
        options.host = host;
        options.protocol = protocol;
        options.port = port;
        options.path = path;
        options.interval = interval;
        options.timeout = timeout;
        options.unhealthyCount = unhealthyCount;
        options.healthyCount = healthyCount;

        return options;
    }

    public @Nonnull LoadBalancerHealthCheck build(@Nonnull CloudProvider provider) throws CloudException, InternalException {
        NetworkServices services = provider.getNetworkServices();

        if( services == null ) {
            throw new OperationNotSupportedException("Network services are not supported in " + provider.getCloudName());
        }
        LoadBalancerSupport support = services.getLoadBalancerSupport();

        if( support == null ) {
            throw new OperationNotSupportedException("Load balancers are not supported in " + provider.getCloudName());
        }

        return support.createLoadBalancerHealthCheck(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public @Nullable String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProviderLoadBalancerId() {
        return providerLoadBalancerId;
    }

    public void setLoadBalancerId(String providerLoadBalancerId){
        this.providerLoadBalancerId = providerLoadBalancerId;
    }

    public @Nullable LbListener getListener() {
        return listener;
    }

    public void setListener(@Nullable LbListener lbListener) {
        this.listener = lbListener;
    }

    public @Nullable String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public LoadBalancerHealthCheck.HCProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(LoadBalancerHealthCheck.HCProtocol protocol) {
        this.protocol = protocol;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public @Nullable String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getUnhealthyCount() {
        return unhealthyCount;
    }

    public void setUnhealthyCount(int unhealthyCount) {
        this.unhealthyCount = unhealthyCount;
    }

    public int getHealthyCount() {
        return healthyCount;
    }

    public void setHealthyCount(int healthyCount) {
        this.healthyCount = healthyCount;
    }

    /**
     * The providerLoadBalancerId might not be known at the original creation point of the options
     * so this method allows it to be added after creation
     * @param providerLoadBalancerId the ID of the Load Balancer to which the Health Check is attached
     * @return this
     */
    public @Nonnull HealthCheckOptions withProviderLoadBalancerId(@Nonnull String providerLoadBalancerId){
        this.providerLoadBalancerId = providerLoadBalancerId;
        return this;
    }

    /**
     * For listener level health check, sets the listener which the health check is attached
     * @param lbListener the LbListener to which the Health Check is attached
     * @return this
     */
    public @Nonnull HealthCheckOptions withListener(@Nullable LbListener lbListener) {
        this.listener = lbListener;
        return this;
    }

}
