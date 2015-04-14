package org.dasein.cloud.ci;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;


//import com.google.api.services.compute.model.HostRule;

public class ConvergedHttpLoadbalancerOptions {
    private String name;
    private String description;
    private ConvergedHttpLoadbalancerOptions() { }
    private List<UrlMapPathRule> urlMapRules = new ArrayList<UrlMapPathRule>();
    private List<HttpHealthCheck> httpHealthChecks = new ArrayList<HttpHealthCheck>();
    private String testUrlMappingHostPathUrl;
    private String testUrlMappingBackendService;
    private String urlMapDescription;
    private String urlMapName;
    private String defaultForwardingRuleName;
    private String defaultForwardingRuleDescription;
    private String defaultForwardingRuleDefaultBackendServiceUrl;
    private String defaultForwardingRuleHostPattern;
    private HttpPort defaultForwardingRulePort;
    private String targetProxyName;
    private String targetProxyDescription;
    private String globalForwardingRuleName;
    private String globalForwardingRuleDescription;
    private HttpPort globalForwardingRulePort;
    private String globalForwardingRuleIpAddress;
    private String backendServiceName;
    private String backendServiceDescription;
    private int backendServicePortNumber;
    private String backendServicePortName;
    private String backendServiceUrl;
    private String urlMapSelfUrl;
    private String targetProxySelfUrl;
    private String globalForwardingRuleSelfUrl;


    static public @Nonnull ConvergedHttpLoadbalancerOptions getInstance(@Nonnull String name, @Nonnull String description) {
        ConvergedHttpLoadbalancerOptions options = new ConvergedHttpLoadbalancerOptions();
        options.name = name;
        options.description = description;

        return options;
    }


    public class UrlMapPathRule {
        private String backendService;
        private String[] paths;

        UrlMapPathRule(@Nonnull String[] paths, @Nonnull String backendService) {
            this.paths = paths;
            this.backendService = backendService;
        }

        public String[] getPaths() {
            return paths;
        }

        public String getBackendService() {
            return backendService;
        }
    }

    public class HttpHealthCheck {
        private String name;
        private String description;
        private int checkIntervalSeconds;
        private int timeoutSeconds;
        private int healthyThreshold;
        private int unhealthyTreshold;
        private int port;
        private String requestPath;
        private String host;
        private String httpHealthCheckSelfUrl;

        HttpHealthCheck(@Nonnull String name, String description, int checkIntervalSeconds, int timeoutSeconds, int healthyThreshold, int unhealthyTreshold, int port, @Nonnull String requestPath, String host) {
            this.name = name;
            this.description = description;
            this.checkIntervalSeconds = checkIntervalSeconds;
            this.timeoutSeconds = timeoutSeconds;
            this.healthyThreshold = healthyThreshold;
            this.unhealthyTreshold = unhealthyTreshold;
            this.port = port;
            this.requestPath = requestPath;
            this.host = host;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public int getCheckIntervalSeconds() {
            return checkIntervalSeconds;
        }

        public int getTimeoutSeconds() {
            return timeoutSeconds;
        }

        public int getHealthyThreshold() {
            return healthyThreshold;
        }

        public int getUnhealthyTreshold() {
            return unhealthyTreshold;
        }

        public int getPort() {
            return port;
        }

        public String getRequestPath() {
            return requestPath;
        }

        public String getHost() {
            return host;
        }

        public void setHttpHealthCheckSelfUrl(String httpHealthCheckSelfUrl) {
            this.httpHealthCheckSelfUrl = httpHealthCheckSelfUrl;
        }

        public String getHttpHealthCheckSelfUrl() {
            return httpHealthCheckSelfUrl;
        }
    }

    public ConvergedHttpLoadbalancerOptions withHttpHealthCheck(@Nonnull String name, String description, int checkIntervalSeconds, int timeoutSeconds, int healthyThreshold, int unhealthyTreshold, int port, @Nonnull String requestPath, String host) {
        httpHealthChecks.add(new HttpHealthCheck(name, description, checkIntervalSeconds, timeoutSeconds, healthyThreshold, unhealthyTreshold, port, requestPath, host));
        return this;
    }

    public String[] getConvergedHttpLoadbalancerSelfUrls() {
        List<String> httpHealthCheckSelfUrl = new ArrayList<String>();
        Iterator<ConvergedHttpLoadbalancerOptions.HttpHealthCheck> healthCheckIterator = httpHealthChecks.iterator();
        while (healthCheckIterator.hasNext()) {
            HttpHealthCheck httpHealthCheck = healthCheckIterator.next();
            httpHealthCheckSelfUrl.add(httpHealthCheck.getHttpHealthCheckSelfUrl());
        }
        return httpHealthCheckSelfUrl.toArray(new String[httpHealthCheckSelfUrl.size()]);
    }

    public ConvergedHttpLoadbalancerOptions withBackendService(@Nonnull String name, String description, int portNumber, String portName) {
        this.backendServiceName = name;
        this.backendServiceDescription = description;
        this.backendServicePortNumber = portNumber;
        this.backendServicePortName = portName;
        return this;
    }

    public ConvergedHttpLoadbalancerOptions withUrlMapPathRule(@Nonnull String[] paths, @Nonnull String backendService) {
        urlMapRules.add(new UrlMapPathRule(paths, backendService));
        return this;
    }

    public ConvergedHttpLoadbalancerOptions withUrlMap(@Nonnull String name, String description) {
        this.urlMapName = name;
        this.urlMapDescription = description;
        return this;
    }

    public ConvergedHttpLoadbalancerOptions withTestUrlMapping(@Nonnull String hostPathUrl, @Nonnull String backendService) {
        this.testUrlMappingHostPathUrl = hostPathUrl;
        this.testUrlMappingBackendService = backendService;
        return this;
    }

    public ConvergedHttpLoadbalancerOptions withDefaultForwardingRule(@Nonnull String name, String description, @Nonnull String defaultBackendServiceUrl, String hostPattern, @Nonnull HttpPort port) {
        this.defaultForwardingRuleName = name;
        this.defaultForwardingRuleDescription = description;
        this.defaultForwardingRuleDefaultBackendServiceUrl = defaultBackendServiceUrl;
        this.defaultForwardingRuleHostPattern = hostPattern;
        this.defaultForwardingRulePort = port;
        return this;
    }

    public ConvergedHttpLoadbalancerOptions withTargetProxy(@Nonnull String name, String description) {
        this.targetProxyName = name;
        this.targetProxyDescription = description;
        return this;
    }

    public ConvergedHttpLoadbalancerOptions withGlobalForwardingRule(@Nonnull String name, String description, @Nonnull HttpPort port, String ipAddress) {
        this.globalForwardingRuleName = name;
        this.globalForwardingRuleDescription = description;
        this.globalForwardingRulePort = port;
        this.globalForwardingRuleIpAddress = ipAddress;

        return this;
    }

    /*-------------------------------------*/

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTestUrlMappingHostPathUrl() {
        return testUrlMappingHostPathUrl;
    }

    public String getTestUrlMappingBackendService() {
        return testUrlMappingBackendService;
    }

    public String getUrlMapDescription() {
        return urlMapDescription;
    }

    public String getUrlMapName() {
        return urlMapName;
    }

    public String getDefaultForwardingRuleName() {
        return defaultForwardingRuleName;
    }

    public String getDefaultForwardingRuleDescription() {
        return defaultForwardingRuleDescription;
    }

    public String getDefaultForwardingRuleDefaultBackendServiceUrl() {
        return defaultForwardingRuleDefaultBackendServiceUrl;
    }

    public String getDefaultForwardingRuleHostPattern() {
        return defaultForwardingRuleHostPattern;
    }

    public HttpPort getDefaultForwardingRulePort() {
        return defaultForwardingRulePort;
    }

    public String getTargetProxyName() {
        return targetProxyName;
    }

    public String getTargetProxyDescription() {
        return targetProxyDescription;
    }

    public String getGlobalForwardingRuleName() {
        return globalForwardingRuleName;
    }

    public String getGlobalForwardingRuleDescription() {
        return globalForwardingRuleDescription;
    }

    public HttpPort getGlobalForwardingRulePort() {
        return globalForwardingRulePort;
    }

    public String getGlobalForwardingRuleIpAddress() {
        return globalForwardingRuleIpAddress;
    }

    public String getBackendServiceName() {
        return backendServiceName;
    }

    public String getBackendServiceDescription() {
        return backendServiceDescription;
    }

    public int getBackendServicePortNumber() {
        return backendServicePortNumber;
    }

    public String getBackendServicePortName() {
        return backendServicePortName;
    }
    
    public Iterator<UrlMapPathRule> getUrlMapPathRules() {
        return urlMapRules.iterator();
    }
    
    public Iterator<HttpHealthCheck> getHttpHealthChecks() {
        return httpHealthChecks.iterator();
    }

    public void setBackendServiceUrl(String backendServiceUrl) {
        this.backendServiceUrl = backendServiceUrl;
    }

    public String getBackendServiceUrl() {
        return backendServiceUrl;
    }

    public void setUrlMapSelfUrl(String urlMapSelfUrl) {
        this.urlMapSelfUrl = urlMapSelfUrl;
    }

    public String getUrlMapSelfUrl() {
        return urlMapSelfUrl;
    }

    public void setTargetProxySelfUrl(String targetProxySelfUrl) {
        this.targetProxySelfUrl = targetProxySelfUrl;
    }

    public String getTargetProxySelfUrl() {
        return(targetProxySelfUrl);
    }

    public void setGlobalForwardingRuleSelfUrl(String globalForwardingRuleSelfUrl) {
        this.globalForwardingRuleSelfUrl = globalForwardingRuleSelfUrl;
    }

    public String getGlobalForwardingRuleSelfUrl() {
        return globalForwardingRuleSelfUrl;
    }
}
