package org.dasein.cloud.util;

public interface NamingRules {

    public NamingRules forInstanceNameRules();
    public NamingRules forVolumeNameRules();
    public NamingRules forSnapshotNameRules();
    public NamingRules forFirewallNameRules();
    public NamingRules forVLANNameRules();
    public NamingRules forSubnetNameRules();
    public NamingRules forLoadBalancerNameRules();
    public NamingRules forHttpLoadBalancerNameRules();
    public NamingRules forImageNameRules();
    public NamingRules forIpAddressNameRules();
    public NamingRules forRelationalDatabaseNameRules();
    public NamingRules forReplicaPoolTemplateNameRules();
    public NamingRules forTopologyNameRules();

    public NamingConstraints getInstanceNameRules();
    public NamingConstraints getVolumeNameRules();
    public NamingConstraints getSnapshotNameRules();
    public NamingConstraints getFirewallNameRules();
    public NamingConstraints getVLANNameRules();
    public NamingConstraints getSubnetNameRules();
    public NamingConstraints getLoadBalancerNameRules();
    public NamingConstraints getHttpLoadBalancerNameRules();
    public NamingConstraints getImageNameRules();
    public NamingConstraints getIpAddressNameRules();
    public NamingConstraints getRelationalDatabaseNameRules();
    public NamingConstraints getReplicaPoolTemplateNameRules();
    public NamingConstraints getTopologyNameRules();

    public NamingRules setNamingConstraints(NamingConstraints namingConstraints);
}
