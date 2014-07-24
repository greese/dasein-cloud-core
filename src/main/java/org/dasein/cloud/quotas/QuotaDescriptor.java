package org.dasein.cloud.quotas;

import java.util.Map;

/**
 * User: mgulimonov
 * Date: 23.07.2014
 */
public class QuotaDescriptor {

    Class resourceType;
    String scope;
    int quota;
    int usage;
    Map<String, Object> rawData;

    public QuotaDescriptor(Class resourceType, String scope, int quota, int usage) {
        this.resourceType = resourceType;
        this.scope = scope;
        this.quota = quota;
        this.usage = usage;
    }

    public QuotaDescriptor(Class resourceType, String scope, int quota, int usage, Map<String, Object> rawData) {
        this.resourceType = resourceType;
        this.scope = scope;
        this.quota = quota;
        this.usage = usage;
        this.rawData = rawData;
    }

    public Class getResourceType() {
        return resourceType;
    }

    public String getScope() {
        return scope;
    }

    public int getQuota() {
        return quota;
    }

    public int getUsage() {
        return usage;
    }

    public Map<String, Object> getRawData() {
        return rawData;
    }

    public float percentageUsed() {
        return usage / quota;
    }
}
