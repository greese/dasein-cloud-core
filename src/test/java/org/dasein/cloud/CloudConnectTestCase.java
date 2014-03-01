/**
 * Copyright (C) 2009-2014 Dell, Inc.
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
package org.dasein.cloud;

import org.dasein.cloud.test.TestCloudProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nullable;

import static org.junit.Assert.*;

/**
 * Tests for the support classes of the data warehouse support in Dasein Cloud
 * <p>Created by George Reese: 2/28/14 11:20 AM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 (issue #123)
 */
public class CloudConnectTestCase {
    static public final String ACCOUNT = "account";
    static public final String REGION  = "region";

    static public final ProviderContext.Value KEYS = new ProviderContext.Value("apiKeys", new byte[][] { "public".getBytes(), "private".getBytes() });

    private Cloud  sharedCloud;
    private String cloudName;
    private String providerName;
    private String endpoint;

    private int testNumber = 0;

    @Before
    public void setUp() {
        testNumber++;
        cloudName = "Cloud " + testNumber;
        providerName = "Provider " + testNumber;
        endpoint = "https://example.com/" + testNumber;
    }

    @After
    public void tearDown() {

    }

    private void checkCloud(@Nullable Cloud cloud) {
        assertNotNull("The registered cloud may not be null", cloud);
        assertEquals("The cloud name does not match what was registered", cloudName, cloud.getCloudName());
        assertEquals("The provider name does not match what was registered", providerName, cloud.getProviderName());
        assertEquals("The endpoint does not match what was registered", endpoint, cloud.getEndpoint());
        assertEquals("Thew provider class does not match what was registered", TestCloudProvider.class, cloud.getProviderClass());
    }

    @Test
    public void verifyRegister() {
        sharedCloud = Cloud.register(providerName, cloudName, endpoint, TestCloudProvider.class);
        checkCloud(sharedCloud);
    }

    @Test
    public void verifyGetInstance() {
        if( sharedCloud == null ) {
            sharedCloud = Cloud.register(providerName, cloudName, endpoint, TestCloudProvider.class);
        }
        Cloud cloud = Cloud.getInstance(sharedCloud.getEndpoint());

        assertNotNull("Unable to locate the registred cloud", cloud);
        assertEquals("The cloud name does not match what was registered", sharedCloud.getCloudName(), cloud.getCloudName());
        assertEquals("The provider name does not match what was registered", sharedCloud.getProviderName(), cloud.getProviderName());
        assertEquals("The endpoint does not match what was registered", sharedCloud.getEndpoint(), cloud.getEndpoint());
        assertEquals("The provider class does not match what was registered", sharedCloud.getProviderClass(), cloud.getProviderClass());
    }

    @Test
    public void verifyNoCloud() {
        Cloud cloud = Cloud.getInstance("https://example.com/bogus");

        assertNull("A bogus cloud was surprisingly found", cloud);
    }

    @Test
    public void verifyProvider() throws InstantiationException, IllegalAccessException {
        Cloud cloud = Cloud.register(providerName, cloudName, endpoint, TestCloudProvider.class);
        CloudProvider p = cloud.buildProvider();

        assertNotNull("No provider was built", p);
        assertFalse("Cloud provider is connected without any connect operation", p.isConnected());
    }

    @Test
    public void verifyContext() {
        Cloud cloud = Cloud.register(providerName, cloudName, endpoint, TestCloudProvider.class);
        ProviderContext ctx = cloud.createContext(ACCOUNT, REGION, KEYS);

        assertNotNull("No context was created", ctx);
        assertEquals("The cloud associated with the context does not match the cloud that created it", cloud, ctx.getCloud());
        assertEquals("Context region does not match", REGION, ctx.getRegionId());
        assertEquals("Context account number does not match", ACCOUNT, ctx.getAccountNumber());

        Object keys = ctx.getConfigurationValue("apiKeys");

        assertNotNull("No API keys were found in the provider context", keys);
        assertTrue("The API keys are not an array of byte arrays", keys instanceof byte[][]);
        byte[][] kb = (byte[][])keys;
        assertEquals("API key size is not two parts", 2, kb.length);
        String pub = new String(kb[0]);
        String priv = new String(kb[1]);

        assertEquals("Public key is not public", "public", pub);
        assertEquals("Private key is not private", "private", priv);
    }

    @Test
    public void connect() throws CloudException, InternalException {
        Cloud cloud = Cloud.register(providerName, cloudName, endpoint, TestCloudProvider.class);
        ProviderContext ctx = cloud.createContext(ACCOUNT, REGION, KEYS);
        CloudProvider p = ctx.connect();

        assertNotNull("Cloud provider resulting from a connect may not be null");
        assertTrue("Cloud provider was not connected", p.isConnected());
        assertNotNull("The cloud name should not be null", p.getCloudName());
        assertNotNull("The provider name should not be null", p.getProviderName());

        ContextRequirements requirements = p.getContextRequirements();

        assertNotNull("The context requirements may not be null", requirements);
        assertEquals("There should be exactly one field required for the test provider", 1, requirements.getConfigurableValues().size());
    }

    @Test
    public void close() throws CloudException, InternalException {
        Cloud cloud = Cloud.register(providerName, cloudName, endpoint, TestCloudProvider.class);
        ProviderContext ctx = cloud.createContext(ACCOUNT, REGION, KEYS);
        CloudProvider p = ctx.connect();

        p.close();
        assertFalse("Cloud provider was not closed", p.isConnected());
    }

    @Test
    public void hold() throws CloudException, InternalException {
        Cloud cloud = Cloud.register(providerName, cloudName, endpoint, TestCloudProvider.class);
        ProviderContext ctx = cloud.createContext(ACCOUNT, REGION, KEYS);
        CloudProvider p = ctx.connect();

        p.hold();
        p.close();
        try {
            assertTrue("Cloud provider was closed prior to hold being released", p.isConnected());
        }
        finally {
            p.release();
        }
    }

    @Test
    public void release() throws CloudException, InternalException {
        Cloud cloud = Cloud.register(providerName, cloudName, endpoint, TestCloudProvider.class);
        ProviderContext ctx = cloud.createContext(ACCOUNT, REGION, KEYS);
        CloudProvider p = ctx.connect();

        p.hold();
        p.close();
        try {
            try { Thread.sleep(1000L); }
            catch( InterruptedException e ) { }
        }
        finally {
            p.release();
        }
        try { Thread.sleep(2000L); }
        catch( InterruptedException e ) { }
        assertFalse("Cloud provider was not properly closed after release", p.isConnected());
    }

    @Test
    public void verifyServices() throws CloudException, InternalException {
        Cloud cloud = Cloud.register(providerName, cloudName, endpoint, TestCloudProvider.class);
        ProviderContext ctx = cloud.createContext(ACCOUNT, REGION, KEYS);
        CloudProvider p = ctx.connect();

        assertFalse("Test provider should have no admin services", p.hasAdminServices());
        assertFalse("Test provider should have no compute services", p.hasComputeServices());
        assertFalse("Test provider should have no converged infrastructure services", p.hasCIServices());
        assertFalse("Test provider should have no identity services", p.hasIdentityServices());
        assertFalse("Test provider should have no network services", p.hasNetworkServices());
        assertFalse("Test provider should have no platform services", p.hasPlatformServices());
        assertFalse("Test provider should have no storage services", p.hasStorageServices());
    }

    @Test
    public void testContext() throws CloudException, InternalException {
        Cloud cloud = Cloud.register(providerName, cloudName, endpoint, TestCloudProvider.class);
        ProviderContext ctx = cloud.createContext(ACCOUNT, REGION, KEYS);
        CloudProvider p = ctx.connect();

        assertNull("The default context test in the test provider should always return null", p.testContext());
    }
}