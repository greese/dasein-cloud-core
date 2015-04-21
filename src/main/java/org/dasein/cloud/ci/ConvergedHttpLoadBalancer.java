package org.dasein.cloud.ci;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.dasein.cloud.Taggable;


public class ConvergedHttpLoadBalancer implements Taggable {

    // urlMap
    private String name;
    private String description;
    private String selfLink;
    private String creationTimestamp;
    private String defaultBackendService;
    private List<UrlSet> urlSets = new ArrayList<UrlSet>();

    private List<TargetHttpProxy> targetHttpProxies = new ArrayList<TargetHttpProxy>();

    private List<ForwardingRule> forwardingRules = new ArrayList<ForwardingRule>();

    private List<BackendService> backendServices = new ArrayList<BackendService>();

    private List<HealthCheck> healthChecks = new ArrayList<HealthCheck>();

    private List<BackendServiceBackend> backendServiceBackends = new ArrayList<BackendServiceBackend>();

    public List<BackendServiceBackend> getBackendServiceBackends() {
        return backendServiceBackends;
    }

    public List<HealthCheck> getHealthChecks() {
        return healthChecks;
    }

    public List<BackendService> getBackendServices() {
        return backendServices;
    }

    public List<ForwardingRule> getForwardingRules() {
        return forwardingRules;
    }

    public List<TargetHttpProxy> getTargetHttpProxies() {
        return targetHttpProxies;
    }

    public List<UrlSet> getUrlSets() {
        return urlSets;
    }

    private ConvergedHttpLoadBalancer(String name, String description, String selfLink, String creationTimestamp, String defaultBackendService) { 
        this.name = name;
        this.description = description;
        this.selfLink = selfLink;
        this.creationTimestamp = creationTimestamp;
        this.defaultBackendService = defaultBackendService;
    }

    // For cataloging existing 
    static public @Nonnull ConvergedHttpLoadBalancer getInstance(String name, String description, String selfLink, String creationTimestamp, String defaultBackendService) {
        return new ConvergedHttpLoadBalancer(name, description, selfLink, creationTimestamp, defaultBackendService);
    }

    // For creating new
    static public @Nonnull ConvergedHttpLoadBalancer getInstance(String name, String description, String defaultBackendService) {
        return new ConvergedHttpLoadBalancer(name, description, null, null, defaultBackendService);
    }

    public class UrlSet {
        private String name;
        private String description;
        private String hostMatchPatterns;
        private Map<String, String> pathMap;

        UrlSet(String name, String description, String hostMatchPatterns, Map<String, String> pathMap) {
            this.name = name;
            this.description = description;
            this.hostMatchPatterns = hostMatchPatterns;
            this.pathMap = pathMap;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getHostMatchPatterns() {
            return hostMatchPatterns;
        }


        public Map<String, String> getPathMap() {
            return pathMap;
        }
    }

    public ConvergedHttpLoadBalancer withUrlSet(String name, String description, String hostMatchPatterns, Map<String, String> pathMap) {
        urlSets.add(new UrlSet(name, description, hostMatchPatterns, pathMap));
        return this;
    }

    public class TargetHttpProxy {
        private String name;
        private String description;
        private String creationTimestamp;
        private String selfLink;

        TargetHttpProxy(String name, String description, String creationTimestamp, String selfLink) {
            this.name = name;
            this.description = description;
            this.creationTimestamp = creationTimestamp;
            this.selfLink = selfLink;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getCreationTimestamp() {
            return creationTimestamp;
        }

        public String getSelfLink() {
            return selfLink;
        }

        public void setTargetProxySelfUrl(String selfLink) {
            this.selfLink = selfLink;
        }
    }

    // For cataloging existing
    public ConvergedHttpLoadBalancer withTargetHttpProxy(String name, String description, String creationTimestamp, String selfLink) {
        targetHttpProxies.add(new TargetHttpProxy(name, description, creationTimestamp, selfLink));
        return this;
    }

    // For creating new
    public ConvergedHttpLoadBalancer withTargetHttpProxy(String name, String description) {
        targetHttpProxies.add(new TargetHttpProxy(name, description, null, null));
        return this;
    }

    
    public class ForwardingRule {
        private String name;
        private String description;
        private String creationTimestamp;
        private String selfLink;
        private String ipAddress;
        private String ipProtocol;
        private String portRange;
        private String target;

        ForwardingRule(String name, String description, String creationTimestamp, String ipAddress, String ipProtocol, String portRange, String selfLink, String target) {
            this.name = name;
            this.description = description;
            this.creationTimestamp = creationTimestamp;
            this.ipAddress = ipAddress;
            this.ipProtocol = ipProtocol;
            this.portRange = portRange;
            this.selfLink = selfLink;
            this.target = target;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getCreationTimestamp() {
            return creationTimestamp;
        }

        public String getSelfLink() {
            return selfLink;
        }

        public void setGlobalForwardingRuleSelfUrl(String selfLink) {
            this.selfLink = selfLink;
        }

        public String getIpAddress() {
            return ipAddress;
        }

        public String getIpProtocol() {
            return ipProtocol;
        }

        public String getPortRange() {
            return portRange;
        }

        public String getTarget() {
            return target;
        }

    }

    // For cataloging existing
    public ConvergedHttpLoadBalancer withForwardingRule(String name, String description, String creationTimestamp, String ipAddress, String ipProtocol, String portRange, String selfLink, String target) {
        forwardingRules.add(new ForwardingRule(name, description, creationTimestamp, ipAddress, ipProtocol, portRange, selfLink, target));
        return this;
    }

    // For creating new
    public ConvergedHttpLoadBalancer withForwardingRule(String name, String description, String ipAddress, String ipProtocol, String portRange, String target) {
        forwardingRules.add(new ForwardingRule(name, description, null, ipAddress, ipProtocol, portRange, null, target));
        return this;
    }

    public class BackendService {
        private String name;
        private String description;
        private String creationTimestamp;
        private Integer port;
        private String portName;
        private String protocol;
        private String[] healthChecks;
        private String selfLink;
        private Integer timeoutSec;

        BackendService(String name, String description, String creationTimestamp, Integer port, String portName, String protocol, String[] healthChecks, String selfLink, Integer timeoutSec) {
            this.name = name;
            this.description = description;
            this.creationTimestamp = creationTimestamp;
            this.port = port;
            this.portName = portName;
            this.protocol = protocol;
            this.healthChecks = healthChecks;
            this.selfLink = selfLink;
            this.timeoutSec = timeoutSec;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getCreationTimestamp() {
            return creationTimestamp;
        }

        public Integer getPort() {
            return port;
        }

        public String getPortName() {
            return portName;
        }

        public String getProtocol() {
            return protocol;
        }

        public String[] getHealthChecks() {
            return healthChecks;
        }

        public void setServiceUrl(String serviceUrl) {
            selfLink = serviceUrl;
        }

        public String getSelfLink() {
            return selfLink;
        }

        public Integer getTimeoutSec() {
            return timeoutSec;
        }

    }

    // For cataloging existing
    public ConvergedHttpLoadBalancer withBackendService(String name, String description, String creationTimestamp, Integer port, String portName, String protocol, String[] healthChecks, String selfLink, Integer timeoutSec) {
        backendServices.add(new BackendService(name, description, creationTimestamp, port, portName, protocol, healthChecks, selfLink, timeoutSec));
        return this;
    }

    // For cataloging existing
    public ConvergedHttpLoadBalancer withBackendService(String name, String description, Integer port, String portName, String protocol, String[] healthChecks, Integer timeoutSec) {
        backendServices.add(new BackendService(name, description, null, port, portName, protocol, healthChecks, null, timeoutSec));
        return this;
    }

    public class BackendServiceBackend {
        private String name;
        private String description;
        private String balancingMode;
        private Float capacityScaler;
        private String group;
        private Integer maxRate;
        private Float maxRatePerInstance;
        private Float maxUtilization;

        BackendServiceBackend(String name, String description, String balancingMode, Float capacityScaler, String group, Integer maxRate, Float maxRatePerInstance, Float maxUtilization) {
            this.name = name;
            this.description = description;
            this.balancingMode = balancingMode;
            this.capacityScaler = capacityScaler;
            this.group = group;
            this.maxRate = maxRate;
            this.maxRatePerInstance = maxRatePerInstance;
            this.maxUtilization = maxUtilization;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getCreationTimestamp() {
            return creationTimestamp;
        }

        public String getBalancingMode() {
            return balancingMode;
        }

        public Float getCapacityScaler() {
            return capacityScaler;
        }

        public String getGroup() {
            return group;
        }

        public Integer getMaxRate() {
            return maxRate;
        }

        public Float getMaxRatePerInstance() {
            return maxRatePerInstance;
        }

        public Float getMaxUtilization() {
            return maxUtilization;
        }

        public String getSelfLink() {
            System.out.println("getSelfLink not implemented for BackendServiceBackend");
            return null;
        }
    }

    public ConvergedHttpLoadBalancer withBackendServiceBackend(String name, String description, String balancingMode, Float capacityScaler, String group, Integer maxRate, Float maxRatePerInstance, Float maxUtilization) {
        backendServiceBackends.add(new BackendServiceBackend(name, description, balancingMode, capacityScaler, group, maxRate, maxRatePerInstance, maxUtilization));
        return this;
    }

    public class HealthCheck {
        private String name;
        private String description;
        private String creationTimestamp;
        private String host;
        private Integer port;
        private String requestPath;
        private Integer checkIntervalSec;
        private Integer timeoutSec;
        private Integer healthyThreshold;
        private Integer unhealthyThreshold;
        private String selfLink;

        HealthCheck(String name, String description, String creationTimestamp, String host, Integer port, String requestPath, Integer checkIntervalSec, Integer timeoutSec, Integer healthyThreshold, Integer unhealthyThreshold, String selfLink) {
            this.name = name;
            this.description = description;
            this.creationTimestamp = creationTimestamp;
            this.host = host;
            this.port = port;
            this.requestPath = requestPath;
            this.checkIntervalSec = checkIntervalSec;
            this.timeoutSec = timeoutSec;
            this.healthyThreshold = healthyThreshold;
            this.unhealthyThreshold = unhealthyThreshold;
            this.selfLink = selfLink;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getCreationTimestamp() {
            return creationTimestamp;
        }

        public String getHost() {
            return host;
        }

        public Integer getPort() {
            return port;
        }

        public String getRequestPath() {
            return requestPath;
        }

        public Integer getCheckIntervalSec() {
            return checkIntervalSec;
        }

        public Integer getTimeoutSec() {
            return timeoutSec;
        }

        public Integer getHealthyThreshold() {
            return healthyThreshold;
        }

        public Integer getUnHealthyThreshold() {
            return unhealthyThreshold;
        }

        public String getSelfLink() {
            return selfLink;
        }

        public void setSelfLink(String selfLink) {
            this.selfLink = selfLink;
        }
    }

    // For cataloging existing 
    public ConvergedHttpLoadBalancer withHealthCheck(String name, String description, String creationTimestamp, String host, Integer port, String requestPath, Integer checkIntervalSec, Integer timeoutSec, Integer healthyThreshold, Integer unhealthyThreshold, String selfLink) {
        healthChecks.add(new HealthCheck(name, description, creationTimestamp, host, port, requestPath, checkIntervalSec, timeoutSec, healthyThreshold, unhealthyThreshold, selfLink));
        return this;
    }

    // For creating new 
    public ConvergedHttpLoadBalancer withHealthCheck(String name, String description, String host, Integer port, String requestPath, Integer checkIntervalSec, Integer timeoutSec, Integer healthyThreshold, Integer unhealthyThreshold) {
        healthChecks.add(new HealthCheck(name, description, null, host, port, requestPath, checkIntervalSec, timeoutSec, healthyThreshold, unhealthyThreshold, null));
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setUrlMapSelfUrl(String selfLink) {
        this.selfLink = selfLink;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public String getCreationTimestamp() {
        return creationTimestamp;
    }

    public String getDefaultBackendService() {
        return defaultBackendService;
    }

    @Override
    public Map<String, String> getTags() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setTag(String key, String value) {
        // TODO Auto-generated method stub
    }

    public Object getCurrentState() {
        System.out.println("getCurrentState not implemented");
        return "getCurrentState not implemented";
    }

    public String getName() {
        return name;
    }


    
    public String getbackendServiceBackendSelfUrl(@Nonnull String name) {
        for (BackendServiceBackend backendServiceBackend : backendServiceBackends) {
            if (backendServiceBackend.getName().equals(name)) {
                return backendServiceBackend.getSelfLink();
            }
        }
        return null;
    }

    public String getBackendServiceSelfUrl(@Nonnull String name) {
        for (BackendService backendService : backendServices) {
            if (backendService.getName().equals(name)) {
                return backendService.getSelfLink();
            }
        }
        return null;
    }

    public String getTargetProxySelfUrl(@Nonnull String name) {
        for (TargetHttpProxy targetHttpProxy : targetHttpProxies) {
            if (targetHttpProxy.getName().equals(name)) {
                return targetHttpProxy.getSelfLink();
            }
        }
        return null;
    }

    public String getHealthCheckSelfUrl(String healthCheckName) {
        for (HealthCheck healthCheck : healthChecks) {
            if (healthCheck.getName().equals(healthCheckName)) {
                return healthCheck.getSelfLink();
            }
        }
        return null;
    }
}
