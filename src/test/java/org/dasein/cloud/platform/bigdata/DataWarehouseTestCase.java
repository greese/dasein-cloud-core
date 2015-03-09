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

package org.dasein.cloud.platform.bigdata;

import org.dasein.cloud.network.FirewallReference;
import org.dasein.util.uom.time.Day;
import org.dasein.util.uom.time.TimePeriod;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the support classes of the data warehouse support in Dasein Cloud
 * <p>Created by George Reese: 2/7/14 1:27 PM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 (issue #100)
 */
public class DataWarehouseTestCase {
    static private final String           CLUSTER_ID    = "clusterId";
    static private final DataClusterState CLUSTER_STATE = DataClusterState.AVAILABLE;
    static private final String           DB_NAME       = "dbName";
    static private final String           DESCRIPTION   = "description";
    static private final String           FIREWALL_ID   = "firewallId";
    static private final String           NAME          = "name";
    static private final int              PORT          = 17;
    static private final String           OWNER_ID      = "me";
    static private final String           PARAM_FAMILY  = "flintstones";
    static private final String           PRODUCT_ID    = "productId";
    static private final String           REGION_ID     = "regionId";
    static private final String           SNAPSHOT_ID   = "snapshotId";
    static private final DataClusterSnapshotState SNAPSHOT_STATE = DataClusterSnapshotState.AVAILABLE;

    private String adminPassword;
    private String adminUser;
    private boolean automated;
    private FirewallReference[] computeFirewalls;
    private boolean createEncrypted;
    private int    createPort;
    private String createVersion;
    private long   creationTimestamp;
    private String dataCenterId;
    private boolean encrypted;
    private String[] firewalls;
    private String[] ipCidrs;
    private int    nodeCount;
    private Map<String,Object> parameters;
    private String parameterGroup;
    private ClusterQueryProtocol[] protocols;
    private TimePeriod<Day> retention;
    private String[] shares;
    private String version;
    private String vlanId;

    @Before
    public void setUp() {
        automated = true;
        dataCenterId = null;
        vlanId = null;
        adminUser = null;
        adminPassword = null;
        nodeCount = 1;
        version = "0";
        encrypted = false;
        createEncrypted = true;
        creationTimestamp = 0L;
        createPort = 0;
        createVersion = null;
        protocols = new ClusterQueryProtocol[0];
        parameterGroup = null;
        parameters = new HashMap<String,Object>();
        firewalls = new String[0];
        computeFirewalls = new FirewallReference[0];
        ipCidrs = new String[0];
        retention = null;
        shares = new String[0];
    }

    @After
    public void tearDown() {

    }

    private void checkDataClusterContent(DataCluster cluster) {
        assertNotNull("The cluster returned from the constructor was illegally a null value", cluster);
        assertEquals("The cluster owner ID does not match the test value", OWNER_ID, cluster.getProviderOwnerId());
        assertEquals("The cluster region ID does not match the test value", REGION_ID, cluster.getProviderRegionId());
        assertEquals("The cluster data center ID does not match the test value", dataCenterId, cluster.getProviderDataCenterId());
        assertEquals("The cluster ID does not match the test value", CLUSTER_ID, cluster.getProviderDataClusterId());
        assertEquals("The name does not match the test value", NAME, cluster.getName());
        assertEquals("The description does not match the test value", DESCRIPTION, cluster.getDescription());
        assertEquals("The product ID does not match the test value", PRODUCT_ID, cluster.getProviderProductId());
        assertEquals("The database name does not match the test value", DB_NAME, cluster.getDatabaseName());
        assertEquals("The port does not match the test value", PORT, cluster.getDatabasePort());
        assertEquals("The VLAN ID does not match the test value", vlanId, cluster.getProviderVlanId());
        assertEquals("The admin user does not match the test value", adminUser, cluster.getAdminUserName());
        assertEquals("The admin password does not match the test value", adminPassword, cluster.getAdminPassword());
        assertEquals("The node count does not match the test value", nodeCount, cluster.getNodeCount());
        assertEquals("The version does not match the test value", version, cluster.getClusterVersion());
        assertEquals("The parameter group does not match the test value", parameterGroup, cluster.getProviderParameterGroupId());
        assertEquals("The creation timestamp does not match the test value", creationTimestamp, cluster.getCreationTimestamp());
        assertEquals("The cluster state does not match the test value", CLUSTER_STATE, cluster.getCurrentState());
        assertEquals("The snapshot retention period does not match the test value", retention, cluster.getSnapshotRetentionPeriod());
        ClusterQueryProtocol[] p = cluster.getProtocols();

        assertNotNull("The protocols supported by a data cluster cannot be null", p);
        assertEquals("The number of protocols must match", protocols.length, p.length);
        assertArrayEquals("The protocols must match", protocols, p);
        assertNotNull("toString() may not be null", cluster.toString());
    }

    private void checkDataClusterSnapshotContent(@Nullable DataClusterSnapshot snapshot) {
        assertNotNull("The snapshot returned from the constructor was illegally a null value", snapshot);
        assertEquals("The owner ID does not match the test value", OWNER_ID, snapshot.getProviderOwnerId());
        assertEquals("The region ID does not match the test value", REGION_ID, snapshot.getProviderRegionId());
        assertEquals("The data center ID does not match the test value", dataCenterId, snapshot.getProviderDataCenterId());
        assertEquals("The cluster ID does not match the test value", CLUSTER_ID, snapshot.getProviderClusterId());
        assertEquals("The snapshot ID does not match the test value", SNAPSHOT_ID, snapshot.getProviderSnapshotId());
        assertEquals("The name does not match the test value", NAME, snapshot.getName());
        assertEquals("The description does not match the test value", DESCRIPTION, snapshot.getDescription());
        assertEquals("The product ID does not match the test value", PRODUCT_ID, snapshot.getProviderProductId());
        assertEquals("The database name does not match the test value", DB_NAME, snapshot.getDatabaseName());
        assertEquals("The port does not match the test value", createPort, snapshot.getDatabasePort());
        assertEquals("The admin user does not match the test value", adminUser, snapshot.getAdminUserName());
        assertEquals("The node count does not match the test value", nodeCount, snapshot.getNodeCount());
        assertEquals("The version does not match the test value", version, snapshot.getClusterVersion());
        assertEquals("The automated value does not match the test value", automated, snapshot.isAutomated());
        assertEquals("The creation timestamp does not match the test value", creationTimestamp, snapshot.getCreationTimestamp());
        assertEquals("The snapshot state does not match the test value", SNAPSHOT_STATE, snapshot.getCurrentState());
        assertArrayEquals("The snapshot shares do not match the test value", shares, snapshot.getShares());
        assertNotNull("toString() may not be null", snapshot.toString());
    }

    private void checkDataClusterCreateOptionsContent(DataClusterCreateOptions options) {
        assertNotNull("The data cluster create options may not be null", options);
        assertEquals("The data center ID does not match the test value", dataCenterId, options.getProviderDataCenterId());
        assertEquals("The product ID does not match the test value", PRODUCT_ID, options.getProviderProductId());
        assertEquals("The name does not match the test value", NAME, options.getName());
        assertEquals("The description does not match the test value", DESCRIPTION, options.getDescription());
        assertEquals("The node count does not match the test value", nodeCount, options.getNodeCount());
        if( adminUser == null ) {
            assertNotNull("The default admin user may not be null", options.getAdminUserName());
        }
        else {
            assertEquals("The admin user does not match the test value", adminUser, options.getAdminUserName());
        }
        if( adminPassword == null ) {
            assertNotNull("The default admin password may not be null", options.getAdminPassword());
        }
        else {
            assertEquals("The admin password does not match the test value", adminPassword, options.getAdminPassword());
        }
        assertEquals("The cluster version does not match the test value", createVersion, options.getClusterVersion());
        assertEquals("The database name does not match the test value", DB_NAME, options.getDatabaseName());
        assertEquals("The database port does not match the test or expected default value", createPort, options.getDatabasePort());
        assertEquals("The encrypted value does not match the test value", createEncrypted,  options.isEncrypted());
        assertEquals("The parameter group does not match the test value", parameterGroup, options.getProviderParameterGroupId());
        assertArrayEquals("The firewall IDs do not match the test value", firewalls, options.getProviderFirewallIds());
        assertNotNull("toString() may not be null", options);
    }

    private void checkDataClusterProductContent(DataClusterProduct product) {
        assertNotNull("The data cluster product may not be null", product);
        assertEquals("The product ID does not match the test value", PRODUCT_ID, product.getProviderProductId());
        assertEquals("The name does not match the test value", NAME, product.getName());
        assertEquals("The description does not match the test value", DESCRIPTION, product.getDescription());
        assertNotNull("toString() may not be null", product.toString());
    }

    private void checkDataClusterVersionContent(DataClusterVersion version) {
        assertNotNull("The data cluster version may not be null", version);
        assertEquals("The version number does not match the test value", createVersion, version.getVersionNumber());
        assertEquals("The parameter family does not match the test value", PARAM_FAMILY, version.getParameterFamily());
        assertEquals("The name does not match the test value", NAME, version.getName());
        assertEquals("The description does not match the test value", DESCRIPTION, version.getDescription());
        assertNotNull("toString() may not be null", version.toString());
    }

    private void checkDataClusterParameterGroupContent(DataClusterParameterGroup group) {
        assertNotNull("The parameter group may not be null", group);
        assertEquals("The parameter group ID does not match", parameterGroup, group.getProviderGroupId());
        assertEquals("The family does not match the test value", PARAM_FAMILY, group.getFamily());
        assertEquals("The name does not match the test value", NAME, group.getName());
        assertEquals("The description does not match the test value", DESCRIPTION, group.getDescription());

        Set<String> keys = group.getParameters().keySet();
        String[] actualKeys = keys.toArray(new String[keys.size()]);

        keys = parameters.keySet();
        String[] expectedKeys = keys.toArray(new String[keys.size()]);

        assertArrayEquals("The available parameters do not match", expectedKeys, actualKeys);

        for( String key : expectedKeys ) {
            assertEquals("The parameter value for " + key + " does not match the test value", parameters.get(key), group.getParameters().get(key));
        }
        assertNotNull("toString() may not be null", group.toString());
    }

    private void checkDataClusterFirewallContent(@Nullable DataClusterFirewall firewall) {
        assertNotNull("The constructed firewall may not be null", firewall);
        assertEquals("The name did not match the test value", NAME, firewall.getName());
        assertEquals("The description did not match the test value", DESCRIPTION, firewall.getDescription());
        assertEquals("The firewall ID did not match the test value", FIREWALL_ID, firewall.getProviderFirewallId());
        assertEquals("The owner ID did not match the test value", OWNER_ID, firewall.getProviderOwnerId());
        assertEquals("The region ID did not match the test value", REGION_ID, firewall.getProviderRegionId());
        assertArrayEquals("The compute firewalls did not match the test value", computeFirewalls, firewall.getAuthorizedComputeFirewalls());
        assertArrayEquals("The CIDR IPs did not match the test value", ipCidrs, firewall.getAuthorizedIps());
        assertNotNull("toString() may not be null", firewall.toString());
    }

    @Test
    public void verifyDataClusterSimpleConstructor() {
        checkDataClusterContent(DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT));
    }

    @Test
    public void verifyDataClusterSimpleConstructorWithProtocols() {
        protocols = new ClusterQueryProtocol[] { ClusterQueryProtocol.JDBC };
        checkDataClusterContent(DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT, protocols));
    }

    @Test
    public void verifyDataClusterSimpleConstructorWithValidDC() {
        dataCenterId = "dataCenterId";
        checkDataClusterContent(DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT));
    }

    @Test
    public void verifyDataClusterSimpleVLANConstructor() {
        dataCenterId = "dataCenterId";
        vlanId = "vlanId";
        checkDataClusterContent(DataCluster.getInstance(vlanId, OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT));
    }

    @Test
    public void verifyDataClusterUserConstructor() {
        dataCenterId = "dataCenterId";
        adminUser = "admin";
        adminPassword = "password";
        nodeCount = 2;
        checkDataClusterContent(DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT, adminUser, adminPassword, nodeCount));
    }

    @Test
    public void verifyDataClusterFullConstructor() {
        dataCenterId = "dataCenterId";
        adminUser = "admin";
        adminPassword = "password";
        vlanId = "vlanId";
        nodeCount = 2;
        version = "1.0";
        encrypted = true;
        checkDataClusterContent(DataCluster.getInstance(vlanId, OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, version, DB_NAME, PORT, adminUser, adminPassword, nodeCount, encrypted, creationTimestamp));
    }

    @Test
    public void verifyDataClusterFullConstructorWithOneProtocol() {
        dataCenterId = "dataCenterId";
        adminUser = "admin";
        adminPassword = "password";
        vlanId = "vlanId";
        nodeCount = 2;
        version = "1.0";
        encrypted = true;
        protocols = new ClusterQueryProtocol[] { ClusterQueryProtocol.JDBC };
        checkDataClusterContent(DataCluster.getInstance(vlanId, OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, version, DB_NAME, PORT, adminUser, adminPassword, nodeCount, encrypted, creationTimestamp, protocols));
    }

    @Test
    public void verifyDataClusterFullConstructorWithTwoProtocols() {
        dataCenterId = "dataCenterId";
        adminUser = "admin";
        adminPassword = "password";
        vlanId = "vlanId";
        nodeCount = 2;
        version = "1.0";
        encrypted = true;
        protocols = new ClusterQueryProtocol[] { ClusterQueryProtocol.JDBC, ClusterQueryProtocol.ODBC };
        checkDataClusterContent(DataCluster.getInstance(vlanId, OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, version, DB_NAME, PORT, adminUser, adminPassword, nodeCount, encrypted, creationTimestamp, protocols));
    }

    @Test
    public void verifyDataClusterFullConstructorWithCreationTimestamp() {
        dataCenterId = "dataCenterId";
        adminUser = "admin";
        adminPassword = "password";
        vlanId = "vlanId";
        nodeCount = 2;
        version = "1.0";
        encrypted = true;
        creationTimestamp = System.currentTimeMillis();
        checkDataClusterContent(DataCluster.getInstance(vlanId, OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, version, DB_NAME, PORT, adminUser, adminPassword, nodeCount, encrypted, creationTimestamp, protocols));
    }

    @Test
    public void verifyDataClusterAlterCredentials() {
        DataCluster c = DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT);

        adminUser = "admin";
        adminPassword = "password";
        c.havingAdminCredentials(adminUser, adminPassword);
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterAlterNodeCount() {
        DataCluster c = DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT);

        nodeCount = 3;
        c.havingNodeCount(nodeCount);
        checkDataClusterContent(c);
    }

    /*  implement once comparing time periods is fixed in dasein-util
    public void verifyDataClusterAlterSnapshotRetention() {
        DataCluster c = DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT);

        retention = new TimePeriod<Day>(3, TimePeriod.DAY);
        c.withSnapshotsRetainedFor(retention);
        checkDataClusterContent(c);
    }
    */

    @Test
    public void verifyDataClusterAlterParameterGroup() {
        DataCluster c = DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT);

        parameterGroup = "soda";
        c.withParameterGroup(parameterGroup);
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterAlterEncryptionOn() {
        DataCluster c = DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT);

        encrypted = true;
        c.withEncryption();
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterAlterEncryptionOff() {
        dataCenterId = "dataCenterId";
        adminUser = "admin";
        adminPassword = "password";
        vlanId = "vlanId";
        nodeCount = 2;
        version = "1.0";
        encrypted = true;

        DataCluster c = DataCluster.getInstance(vlanId, OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, version, DB_NAME, PORT, adminUser, adminPassword, nodeCount, encrypted, creationTimestamp);

        encrypted = false;
        c.withoutEncryption();
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterAlterVersion() {
        DataCluster c = DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT);

        version = "2.0";
        c.usingVersion(version);
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterAlterVLAN() {
        DataCluster c = DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT);

        vlanId = "vlanId";
        c.inVlan(vlanId);
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterAlterProtocolsWithNone() {
        DataCluster c = DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT);

        protocols = new ClusterQueryProtocol[] { ClusterQueryProtocol.ODBC };
        c.supportingProtocols(protocols);
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterAlterProtocolsWithOne() {
        DataCluster c = DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT, ClusterQueryProtocol.JDBC);

        c.supportingProtocols(ClusterQueryProtocol.ODBC);
        protocols = new ClusterQueryProtocol[] { ClusterQueryProtocol.JDBC, ClusterQueryProtocol.ODBC };
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterAlterCreationTimestamp() {
        DataCluster c = DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT);

        creationTimestamp = System.currentTimeMillis();
        c.createdAt(creationTimestamp);
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterCreateOptionsSimpleConstructor() {
        DataClusterCreateOptions options = DataClusterCreateOptions.getInstance(PRODUCT_ID, NAME, DESCRIPTION, DB_NAME);

        checkDataClusterCreateOptionsContent(options);
    }

    @Test
    public void verifyDataClusterCreateOptionsSimpleConstructorWithDC() {
        dataCenterId = "moon";

        DataClusterCreateOptions options = DataClusterCreateOptions.getInstance(PRODUCT_ID, dataCenterId, NAME, DESCRIPTION, DB_NAME);

        checkDataClusterCreateOptionsContent(options);
    }

    @Test
    public void verifyDataClusterCreateOptionsFullConstructor() {
        dataCenterId = "moon";
        createEncrypted = false;
        nodeCount = 7;
        createVersion = "9.34";
        createPort = 1701;
        adminUser = "spiderman";
        adminPassword = "olympics";

        DataClusterCreateOptions options = DataClusterCreateOptions.getInstance(PRODUCT_ID, dataCenterId, NAME, DESCRIPTION, createVersion, DB_NAME, createPort, adminUser, adminPassword, nodeCount, createEncrypted);

        checkDataClusterCreateOptionsContent(options);
    }

    @Test
    public void verifyCreateAlterEncryption() {
        DataClusterCreateOptions options = DataClusterCreateOptions.getInstance(PRODUCT_ID, NAME, DESCRIPTION, DB_NAME);

        createEncrypted = false;
        options.withoutEncryption();
        checkDataClusterCreateOptionsContent(options);
    }

    @Test
    public void verifyCreateAlterFirewalls() {
        DataClusterCreateOptions options = DataClusterCreateOptions.getInstance(PRODUCT_ID, NAME, DESCRIPTION, DB_NAME);

        firewalls = new String[] { "a", "b" };
        options.behindFirewalls(firewalls);
        checkDataClusterCreateOptionsContent(options);
    }

    @Test
    public void verifyCreateAlterParameterGroup() {
        DataClusterCreateOptions options = DataClusterCreateOptions.getInstance(PRODUCT_ID, NAME, DESCRIPTION, DB_NAME);

        parameterGroup = "vikings";
        options.withParameterGroup(parameterGroup);
        checkDataClusterCreateOptionsContent(options);
    }

    @Test
    public void verifyCreateAlterCredentials() {
        DataClusterCreateOptions options = DataClusterCreateOptions.getInstance(PRODUCT_ID, NAME, DESCRIPTION, DB_NAME);

        adminUser = "wonderwoman";
        adminPassword = "airplane";
        options.havingAdminCredentials(adminUser, adminPassword);
        checkDataClusterCreateOptionsContent(options);
    }

    @Test
    public void verifyCreateAlterNodeCount() {
        DataClusterCreateOptions options = DataClusterCreateOptions.getInstance(PRODUCT_ID, NAME, DESCRIPTION, DB_NAME);

        nodeCount = 12;
        options.havingNodeCount(nodeCount);
        checkDataClusterCreateOptionsContent(options);
    }

    @Test
    public void verifyCreateAlterDC() {
        DataClusterCreateOptions options = DataClusterCreateOptions.getInstance(PRODUCT_ID, NAME, DESCRIPTION, DB_NAME);

        dataCenterId = "mars";
        options.inDataCenter(dataCenterId);
        checkDataClusterCreateOptionsContent(options);
    }

    @Test
    public void verifyProductConstructor() {
        DataClusterProduct product = DataClusterProduct.getInstance(PRODUCT_ID, NAME, DESCRIPTION);

        checkDataClusterProductContent(product);
    }

    @Test
    public void verifyVersionConstructor() {
        createVersion = "91.5fm";
        DataClusterVersion version = DataClusterVersion.getInstance(createVersion, PARAM_FAMILY, NAME, DESCRIPTION);

        checkDataClusterVersionContent(version);
    }

    @Test
    public void verifyParameterGroupConstructorNoParams() {
        parameterGroup = "alpha";
        DataClusterParameterGroup group = DataClusterParameterGroup.getInstance(parameterGroup, PARAM_FAMILY, NAME, DESCRIPTION, parameters);

        checkDataClusterParameterGroupContent(group);
    }

    @Test
    public void verifyParameterGroupConstructorWithParams() {
        parameterGroup = "alpha";
        parameters.put("hello", "goodbye");
        DataClusterParameterGroup group = DataClusterParameterGroup.getInstance(parameterGroup, PARAM_FAMILY, NAME, DESCRIPTION, parameters);

        checkDataClusterParameterGroupContent(group);
    }

    @Test
    public void verifyDataClusterSnapshotSimpleConstructor() {
        creationTimestamp = System.currentTimeMillis();
        DataClusterSnapshot snapshot = DataClusterSnapshot.getInstance(OWNER_ID, REGION_ID, SNAPSHOT_ID, CLUSTER_ID, NAME, DESCRIPTION, PRODUCT_ID, creationTimestamp, SNAPSHOT_STATE, DB_NAME);
        checkDataClusterSnapshotContent(snapshot);
    }

    @Test
    public void verifyDataClusterSnapshotComplexConstructor() {
        creationTimestamp = System.currentTimeMillis();
        dataCenterId = "pluto";
        automated = false;
        version = "crm114";
        nodeCount = 3;
        createPort = PORT;
        DataClusterSnapshot snapshot = DataClusterSnapshot.getInstance(OWNER_ID, REGION_ID, SNAPSHOT_ID, CLUSTER_ID, dataCenterId, NAME, DESCRIPTION, PRODUCT_ID, creationTimestamp, automated, SNAPSHOT_STATE, version, nodeCount, DB_NAME, createPort);
        checkDataClusterSnapshotContent(snapshot);
    }

    @Test
    public void verifyDataClusterSnapshotAlterUser() {
        creationTimestamp = System.currentTimeMillis();
        DataClusterSnapshot snapshot = DataClusterSnapshot.getInstance(OWNER_ID, REGION_ID, SNAPSHOT_ID, CLUSTER_ID, NAME, DESCRIPTION, PRODUCT_ID, creationTimestamp, SNAPSHOT_STATE, DB_NAME);
        adminUser = "voltaire";
        snapshot = snapshot.havingAdminCredentials(adminUser);
        checkDataClusterSnapshotContent(snapshot);
    }

    @Test
    public void verifyDataClusterSnapshotAlterShares() {
        creationTimestamp = System.currentTimeMillis();
        DataClusterSnapshot snapshot = DataClusterSnapshot.getInstance(OWNER_ID, REGION_ID, SNAPSHOT_ID, CLUSTER_ID, NAME, DESCRIPTION, PRODUCT_ID, creationTimestamp, SNAPSHOT_STATE, DB_NAME);
        shares = new String[] { "friend" };

        snapshot = snapshot.sharedWith(shares);
        checkDataClusterSnapshotContent(snapshot);
    }

    @Test
    public void verifyDataClusterFirewallConstructor() {
        DataClusterFirewall fw = DataClusterFirewall.getInstance(OWNER_ID, REGION_ID, FIREWALL_ID, NAME, DESCRIPTION);

        checkDataClusterFirewallContent(fw);
    }

    @Test
    public void verifyDataClusterFirewallAlterComputeFirewalls() {
        DataClusterFirewall fw = DataClusterFirewall.getInstance(OWNER_ID, REGION_ID, FIREWALL_ID, NAME, DESCRIPTION);

        computeFirewalls = new FirewallReference[] { FirewallReference.getInstance("1", "2") };

        fw.authorizingComputeFirewalls(computeFirewalls);
        checkDataClusterFirewallContent(fw);
    }

    @Test
    public void verifyDataClusterFirewallAlterIPs() {
        DataClusterFirewall fw = DataClusterFirewall.getInstance(OWNER_ID, REGION_ID, FIREWALL_ID, NAME, DESCRIPTION);

        ipCidrs = new String[] { "192.168.1.0/0" };

        fw.authorizingIps(ipCidrs);
        checkDataClusterFirewallContent(fw);
    }

    @Test
    public void verifyDataClusterFirewallAlterBoth() {
        DataClusterFirewall fw = DataClusterFirewall.getInstance(OWNER_ID, REGION_ID, FIREWALL_ID, NAME, DESCRIPTION);

        ipCidrs = new String[] { "12.0.0.0/0", "192.168.1.0/0" };
        fw.authorizingIps(ipCidrs);
        computeFirewalls = new FirewallReference[] { FirewallReference.getInstance("1", "2"), FirewallReference.getInstance("3", "4") };
        fw.authorizingComputeFirewalls(computeFirewalls);

        checkDataClusterFirewallContent(fw);
    }

    @Test
    public void verifyFirewallEquals() {
        DataClusterFirewall fw = DataClusterFirewall.getInstance(OWNER_ID, REGION_ID, FIREWALL_ID, NAME, DESCRIPTION);

        assertTrue("A firewall must equal itself", fw.equals(fw));

        DataClusterFirewall fw2 = DataClusterFirewall.getInstance(OWNER_ID, REGION_ID, FIREWALL_ID, NAME, DESCRIPTION);

        assertTrue("The firewall should equal one with the same values", fw.equals(fw2));
    }

    @Test
    public void verifyFirewallNotEquals() {
        DataClusterFirewall fw = DataClusterFirewall.getInstance(OWNER_ID, REGION_ID, FIREWALL_ID, NAME, DESCRIPTION);

        //noinspection ObjectEqualsNull
        assertFalse("A firewall must not equal null", fw.equals(null));

        DataClusterFirewall fw2 = DataClusterFirewall.getInstance(OWNER_ID, REGION_ID, "someotherid", NAME, DESCRIPTION);

        assertFalse("The firewall should NOT equal one with a different id", fw.equals(fw2));

        fw2 = DataClusterFirewall.getInstance(OWNER_ID, "someotherregion", FIREWALL_ID, NAME, DESCRIPTION);
        assertFalse("The firewall should NOT equal one with a different region", fw.equals(fw2));

        fw2 = DataClusterFirewall.getInstance("someoneelse", REGION_ID, FIREWALL_ID, NAME, DESCRIPTION);
        assertFalse("The firewall should NOT equal one with a different owner", fw.equals(fw2));
    }
}
