/*
 * Copyright (C) 2014 enStratus Networks Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dasein.cloud.platform.bigdata;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
    static private final String           NAME          = "name";
    static private final int              PORT          = 17;
    static private final String           PRODUCT_ID    = "productId";
    static private final String           REGION_ID     = "regionId";

    private String adminPassword;
    private String adminUser;
    private String dataCenterId;
    private boolean encrypted;
    private int    nodeCount;
    private ClusterQueryProtocol[] protocols;
    private String version;
    private String vlanId;

    @Before
    public void setUp() {
        dataCenterId = null;
        vlanId = null;
        adminUser = null;
        adminPassword = null;
        nodeCount = 1;
        version = "0";
        encrypted = false;
        protocols = new ClusterQueryProtocol[0];
    }

    @After
    public void tearDown() {

    }

    private void checkDataClusterContent(DataCluster cluster) {
        assertNotNull("The cluster returned from the constructor was illegally a null value", cluster);
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

        ClusterQueryProtocol[] p = cluster.getProtocols();

        assertNotNull("The protocols supported by a data cluster cannot be null", p);
        assertEquals("The number of protocols must match", protocols.length, p.length);
        assertArrayEquals("The protocols must match", protocols, p);
    }

    @Test
    public void verifyDataClusterSimpleConstructor() {
        checkDataClusterContent(DataCluster.getInstance(REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT));
    }

    @Test
    public void verifyDataClusterSimpleConstructorWithProtocols() {
        protocols = new ClusterQueryProtocol[] { ClusterQueryProtocol.JDBC };
        checkDataClusterContent(DataCluster.getInstance(REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT, protocols));
    }

    @Test
    public void verifyDataClusterSimpleConstructorWithValidDC() {
        dataCenterId = "dataCenterId";
        checkDataClusterContent(DataCluster.getInstance(REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT));
    }

    @Test
    public void verifyDataClusterSimpleVLANConstructor() {
        dataCenterId = "dataCenterId";
        vlanId = "vlanId";
        checkDataClusterContent(DataCluster.getInstance(vlanId, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT));
    }

    @Test
    public void verifyDataClusterUserConstructor() {
        dataCenterId = "dataCenterId";
        adminUser = "admin";
        adminPassword = "password";
        nodeCount = 2;
        checkDataClusterContent(DataCluster.getInstance(REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT, adminUser, adminPassword, nodeCount));
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
        checkDataClusterContent(DataCluster.getInstance(vlanId, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, version, DB_NAME, PORT, adminUser, adminPassword, nodeCount, encrypted));
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
        checkDataClusterContent(DataCluster.getInstance(vlanId, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, version, DB_NAME, PORT, adminUser, adminPassword, nodeCount, encrypted, protocols));
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
        checkDataClusterContent(DataCluster.getInstance(vlanId, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, version, DB_NAME, PORT, adminUser, adminPassword, nodeCount, encrypted, protocols));
    }

    @Test
    public void verifyDataClusterAlterCredentials() {
        DataCluster c = DataCluster.getInstance(REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT);

        adminUser = "admin";
        adminPassword = "password";
        c.havingAdminCredentials(adminUser, adminPassword);
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterAlterNodeCount() {
        DataCluster c = DataCluster.getInstance(REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT);

        nodeCount = 3;
        c.havingNodeCount(nodeCount);
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterAlterEncryptionOn() {
        DataCluster c = DataCluster.getInstance(REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT);

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

        DataCluster c = DataCluster.getInstance(vlanId, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, version, DB_NAME, PORT, adminUser, adminPassword, nodeCount, encrypted);

        encrypted = false;
        c.withoutEncryption();
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterAlterVersion() {
        DataCluster c = DataCluster.getInstance(REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT);

        version = "2.0";
        c.havingVersion(version);
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterAlterVLAN() {
        DataCluster c = DataCluster.getInstance(REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT);

        vlanId = "vlanId";
        c.inVlan(vlanId);
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterAlterProtocolsWithNone() {
        DataCluster c = DataCluster.getInstance(REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT);

        protocols = new ClusterQueryProtocol[] { ClusterQueryProtocol.ODBC };
        c.supportingProtocols(protocols);
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterAlterProtocolsWithOne() {
        DataCluster c = DataCluster.getInstance(REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT, ClusterQueryProtocol.JDBC);

        c.supportingProtocols(ClusterQueryProtocol.ODBC);
        protocols = new ClusterQueryProtocol[] { ClusterQueryProtocol.JDBC, ClusterQueryProtocol.ODBC };
        checkDataClusterContent(c);
    }

}
