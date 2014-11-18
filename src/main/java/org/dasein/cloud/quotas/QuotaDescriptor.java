package org.dasein.cloud.quotas;

import java.util.Map;

/**
 * User: mgulimonov
 * Date: 23.07.2014
 */
public class QuotaDescriptor {

    private CloudResourceType resourceType;
    private CloudResourceScope scope;
    private int quota;
    private int usage;
    private Map<String, Object> rawData;

    public QuotaDescriptor() {
    }

    public QuotaDescriptor(CloudResourceType resourceType, CloudResourceScope scope, int quota, int usage) {
        this.resourceType = resourceType;
        this.scope = scope;
        this.quota = quota;
        this.usage = usage;
    }

    public QuotaDescriptor(CloudResourceType resourceType, CloudResourceScope scope, int quota, int usage, Map<String, Object> rawData) {
        this.resourceType = resourceType;
        this.scope = scope;
        this.quota = quota;
        this.usage = usage;
        this.rawData = rawData;
    }

    public CloudResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(CloudResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public CloudResourceScope getScope() {
        return scope;
    }

    public void setScope(CloudResourceScope scope) {
        this.scope = scope;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public int getUsage() {
        return usage;
    }

    public void setUsage(int usage) {
        this.usage = usage;
    }

    public Map<String, Object> getRawData() {
        return rawData;
    }

    public void setRawData(Map<String, Object> rawData) {
        this.rawData = rawData;
    }

    public float percentageUsed() {
        return usage / quota;
    }
}
