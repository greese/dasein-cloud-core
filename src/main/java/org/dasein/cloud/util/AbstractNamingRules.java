package org.dasein.cloud.util;

import org.dasein.cloud.AbstractProviderService;
import org.dasein.cloud.CloudProvider;

public abstract class AbstractNamingRules<T extends CloudProvider> extends AbstractProviderService<T> implements NamingRules {
    private NamingConstraints instanceNameRules = null;
    private NamingConstraints volumeNameRules = null;
    private NamingConstraints snapshotNameRules = null;
    private NamingConstraints firewallNameRules = null;
    private NamingConstraints vlanNameRules = null;
    private NamingConstraints subnetNameRules = null;
    private NamingConstraints loadBalancerNameRules = null;
    private NamingConstraints namingConstraints = null;
    private NamingConstraints imageNameRules = null;
    private NamingConstraints ipAddressNameRules = null;
    private NamingConstraints httpLoadBalancerNameRules = null;
    private NamingConstraints relationalDatabaseNameRules = null;
    private NamingConstraints replicaPoolTemplateNameRules = null;
    private NamingConstraints topologyNameRules = null;

    protected AbstractNamingRules(T provider) {
        super(provider);
    }

    public AbstractNamingRules<T> forInstanceNameRules() {
        instanceNameRules = namingConstraints;
        return this; 
    }

    public AbstractNamingRules<T> forVolumeNameRules() {
        volumeNameRules = namingConstraints;
        return this; 
    }

    public AbstractNamingRules<T> forSnapshotNameRules() {
        snapshotNameRules = namingConstraints;
        return this; 
    }

    public AbstractNamingRules<T> forFirewallNameRules() {
        firewallNameRules = namingConstraints;
        return this; 
    }

    public AbstractNamingRules<T> forVLANNameRules() {
        vlanNameRules = namingConstraints;
        return this; 
    }

    public AbstractNamingRules<T> forSubnetNameRules() {
        subnetNameRules = namingConstraints;
        return this; 
    }

    public AbstractNamingRules<T> forLoadBalancerNameRules() {
        loadBalancerNameRules = namingConstraints;
        return this; 
    }

    public AbstractNamingRules<T> forHttpLoadBalancerNameRules() {
        httpLoadBalancerNameRules = namingConstraints;
        return this; 
    }

    public AbstractNamingRules<T> forImageNameRules() {
        imageNameRules = namingConstraints;
        return this; 
    }

    public AbstractNamingRules<T> forIpAddressNameRules() {
        ipAddressNameRules = namingConstraints;
        return this; 
    }

    public AbstractNamingRules<T> forRelationalDatabaseNameRules() {
        relationalDatabaseNameRules = namingConstraints;
        return this; 
    }

    public AbstractNamingRules<T> forReplicaPoolTemplateNameRules() {
        replicaPoolTemplateNameRules = namingConstraints;
        return this; 
    }

    public AbstractNamingRules<T> forTopologyNameRules() {
        topologyNameRules = namingConstraints;
        return this; 
    }

    public NamingConstraints getInstanceNameRules() {
        return instanceNameRules;
    }

    public NamingConstraints getVolumeNameRules() {
        return volumeNameRules;
    }

    public NamingConstraints getSnapshotNameRules() {
        return snapshotNameRules;
    }

    public NamingConstraints getFirewallNameRules() {
        return firewallNameRules;
    }

    public NamingConstraints getVLANNameRules() {
        return vlanNameRules;
    }

    public NamingConstraints getSubnetNameRules() {
        return subnetNameRules;
    }

    public NamingConstraints getLoadBalancerNameRules() {
        return loadBalancerNameRules;
    }

    public NamingConstraints getHttpLoadBalancerNameRules() {
        return httpLoadBalancerNameRules;
    }

    public NamingConstraints getImageNameRules() {
        return imageNameRules;
    }

    public NamingConstraints getIpAddressNameRules() {
        return ipAddressNameRules;
    }

    public NamingConstraints getRelationalDatabaseNameRules() {
        return relationalDatabaseNameRules;
    }

    public NamingConstraints getReplicaPoolTemplateNameRules() {
        return replicaPoolTemplateNameRules;
    }

    public NamingConstraints getTopologyNameRules() {
        return topologyNameRules;
    }

    public AbstractNamingRules<T> setNamingConstraints(NamingConstraints namingConstraints) {
        this.namingConstraints  = namingConstraints;
        return this;
    }
}
