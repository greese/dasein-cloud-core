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

package org.dasein.cloud;

import org.dasein.cloud.test.TestNewCloudProvider;
import org.dasein.cloud.test.TestOldCloudProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Properties;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Tests all possible mechanisms of connecting providers, contexts, and clouds as well as the possible permutations of
 * using them. The following combinations must be addressed:
 * <ol>
 *     <li>A client using the old connection methods to connect to a legacy Dasein Cloud implementation (old client + old implementation)</li>
 *     <li>A newer client using the 2014.03+ connection methods to a legacy Dasein Cloud implementation (new client + old implementation)</li>
 *     <li>A client using the old connection methods to connect to a refactored Dasein Cloud implementation (old client + new implementation)</li>
 *     <li>A newer client using the 2014.03+ connection methods to a refactored Dasein Cloud implementation (new client + new implementation)</li>
 * </ol>
 * <p>
 *     The tests should specifically validate that all forms of fetching data (new + old) work once a connection has been established. It is OK
 *     that only new methods or only old methods work prior to connect since, in general, only the client doing the connection is
 *     interacting with the context at that point (and hopefully won't be mixing old and new calls during that process).
 * </p>
 * <p>Created by George Reese: 3/1/14 9:12 AM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 (issue #123)
 */
public class CloudConnectTestCase {
    static public final String ACCOUNT = "account";
    static public final String REGION  = "region";

    static public final ProviderContext.Value<byte[][]> KEYS    = new ProviderContext.Value<byte[][]>("apiKeys", new byte[][] { "public".getBytes(), "private".getBytes() });
    static public final ProviderContext.Value<String>   VERSION = new ProviderContext.Value<String>("version", "1");
    static public final ProviderContext.Value<byte[][]> X509    = new ProviderContext.Value<byte[][]>("x509", new byte[][] { "x509c".getBytes(), "x509k".getBytes() });

    private Cloud  newCloud;
    private String cloudName;
    private String effectiveAccountNumber;
    private String providerName;
    private String endpoint;

    static private int testNumber = 0;

    @Before
    public void setUp() {
        testNumber++;
        cloudName = "Cloud " + testNumber;
        providerName = "Provider " + testNumber;
        endpoint = "https://example.com/" + testNumber;
        effectiveAccountNumber = ACCOUNT;
        if( newCloud == null ) {
            newCloud = Cloud.register(providerName, cloudName, endpoint, TestNewCloudProvider.class);
        }
    }

    @After
    public void tearDown() {

    }

    /********************************** NEW IMPLEMENTATION, OLD CLIENT **********************************/

    @SuppressWarnings("deprecation")
    private void checkConnectionIntegrityForOldClientMethods(@Nullable ProviderContext ctx) {
        assertNotNull("The context resulting from context creation may not be null", ctx);
        assertNotNull("No context was created", ctx);

        byte[] b = ctx.getAccessPublic();
        byte[] v = ctx.getAccessPrivate();

        assertNotNull("No public key was found in the provider context", b);
        assertNotNull("No private key was found in the provider context", v);
        String pub = new String(b);
        String priv = new String(v);

        assertEquals("Public key is not public", "public", pub);
        assertEquals("Private key is not private", "private", priv);

        b = ctx.getX509Cert();
        v = ctx.getX509Key();
        assertNotNull("No X509 cert was found in the provider context", b);
        assertNotNull("No X509 key was found in the provider context", v);
        pub = new String(b);
        priv = new String(v);

        assertEquals("X509 certificate is not public", "x509c", pub);
        assertEquals("X509 key is not private", "x509k", priv);

        Properties props = ctx.getCustomProperties();

        assertNotNull("Custom properties may not be null", props);
        assertFalse("Custom properties for test cloud should contain values", props.isEmpty());

        String version = props.getProperty("version");

        assertEquals("The set version does not match the test value", "1", version);

        assertEquals("The cloud name does not match the set value", cloudName, ctx.getCloudName());
        assertEquals("The provider name does not match the set value", providerName, ctx.getProviderName());
        assertEquals("The endpoint does not match the set value", endpoint, ctx.getEndpoint());
    }

    private void checkConnectionIntegrityForNewClientMethods(@Nonnull Cloud cloud, @Nullable ProviderContext ctx, @Nullable CloudProvider provider) {
        assertNotNull("The context resulting from context creation may not be null", ctx);
        assertNotNull("Cloud provider resulting from a connect may not be null", provider);

        assertTrue("Cloud provider was not connected", provider.isConnected());
        assertNotNull("The cloud name should not be null", provider.getCloudName());
        assertNotNull("The provider name should not be null", provider.getProviderName());

        ContextRequirements requirements = provider.getContextRequirements();

        assertNotNull("The context requirements may not be null", requirements);
        if( provider.getClass().getName().equals(TestNewCloudProvider.class.getName()) ) {
            assertEquals("There should be exactly three fields required for the test provider", 3, requirements.getConfigurableValues().size());
        }
        else {
            assertEquals("There should be exactly two fields required for the test provider", 2, requirements.getConfigurableValues().size());
        }
        assertEquals("The cloud does not match", cloud, ctx.getCloud());
        assertEquals("The account numbers do not match", ACCOUNT, ctx.getAccountNumber());
        assertEquals("The region values do not match", REGION, ctx.getRegionId());
        assertEquals("The effective account number does not match the required value", effectiveAccountNumber, ctx.getEffectiveAccountNumber());
        for( ContextRequirements.Field f : requirements.getConfigurableValues() ) {
            Object value = ctx.getConfigurationValue(f.name);

            assertNotNull("The value for " + f.name + " should have been set for these tests", value);

            Object full = ctx.getConfigurationValue(f);

            assertEquals("The two configurable value methods for " + f.name + " should return the same value", full, value);
        }
    }

    @Test
    public void registerCloud() {
        Cloud cloud = Cloud.register(providerName, cloudName, endpoint, TestNewCloudProvider.class);

        assertNotNull("The registered cloud may not be null", cloud);
        assertEquals("The cloud name does not match what was registered", cloudName, cloud.getCloudName());
        assertEquals("The provider name does not match what was registered", providerName, cloud.getProviderName());
        assertEquals("The endpoint does not match what was registered", endpoint, cloud.getEndpoint());
        assertEquals("Thew provider class does not match what was registered", TestNewCloudProvider.class, cloud.getProviderClass());
    }

    @Test
    public void getCloudInstanceAfterFormalRegister() {
        Cloud sharedCloud = Cloud.register(providerName, cloudName, endpoint, TestNewCloudProvider.class);
        Cloud cloud = Cloud.getInstance(sharedCloud.getEndpoint());

        assertNotNull("Unable to locate the registred cloud", cloud);
        assertEquals("The cloud name does not match what was registered", sharedCloud.getCloudName(), cloud.getCloudName());
        assertEquals("The provider name does not match what was registered", sharedCloud.getProviderName(), cloud.getProviderName());
        assertEquals("The endpoint does not match what was registered", sharedCloud.getEndpoint(), cloud.getEndpoint());
        assertEquals("The provider class does not match what was registered", sharedCloud.getProviderClass(), cloud.getProviderClass());
    }

    @Test
    public void getCloudFromBogusInstance() {
        Cloud cloud = Cloud.getInstance("https://example.com/bogus");

        assertNull("A bogus cloud was surprisingly found", cloud);
    }

    @Test
    public void buildProvider() throws InstantiationException, IllegalAccessException {
        Cloud cloud = Cloud.register(providerName, cloudName, endpoint, TestNewCloudProvider.class);
        CloudProvider p = cloud.buildProvider();

        assertNotNull("No provider was built", p);
        assertFalse("Cloud provider is connected without any connect operation", p.isConnected());
    }

    @Test
    public void verifyServices() throws CloudException, InternalException {
        Cloud cloud = Cloud.register(providerName, cloudName, endpoint, TestNewCloudProvider.class);
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
    public void newConnectWithNewImpl() throws CloudException, InternalException {
        Cloud cloud = Cloud.register(providerName, cloudName, endpoint, TestNewCloudProvider.class);
        ProviderContext ctx = cloud.createContext(ACCOUNT, REGION, KEYS, X509, VERSION);
        CloudProvider p = ctx.connect();

        checkConnectionIntegrityForOldClientMethods(ctx);
        checkConnectionIntegrityForNewClientMethods(cloud, ctx, p);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void oldConnectWithNewImpl() throws CloudException, InternalException, IllegalAccessException, InstantiationException {
        CloudProvider p = TestNewCloudProvider.class.newInstance();
        ProviderContext ctx = new ProviderContext(ACCOUNT, REGION);

        ctx.setEndpoint(endpoint);
        ctx.setAccessKeys(KEYS.value[0], KEYS.value[1]);
        ctx.setX509Cert(X509.value[0]);
        ctx.setX509Key(X509.value[1]);
        Properties props = new Properties();

        props.setProperty("version", VERSION.value);
        ctx.setCustomProperties(props);
        p.connect(ctx);

        checkConnectionIntegrityForOldClientMethods(ctx);
        checkConnectionIntegrityForNewClientMethods(Cloud.register(providerName, cloudName, endpoint, TestNewCloudProvider.class), ctx, p);
    }

    @Test
    public void newConnectWithOldImpl() throws CloudException, InternalException {
        Cloud cloud = Cloud.register(providerName, cloudName, endpoint, TestOldCloudProvider.class);
        ProviderContext ctx = cloud.createContext(ACCOUNT, REGION, KEYS, X509, VERSION);
        CloudProvider p = ctx.connect();

        checkConnectionIntegrityForOldClientMethods(ctx);
        checkConnectionIntegrityForNewClientMethods(cloud, ctx, p);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void oldConnectWithOldImpl() throws CloudException, InternalException, IllegalAccessException, InstantiationException {
        CloudProvider p = TestOldCloudProvider.class.newInstance();
        ProviderContext ctx = new ProviderContext(ACCOUNT, REGION);

        ctx.setEndpoint(endpoint);
        ctx.setAccessKeys(KEYS.value[0], KEYS.value[1]);
        ctx.setX509Cert(X509.value[0]);
        ctx.setX509Key(X509.value[1]);
        Properties props = new Properties();

        props.setProperty("version", VERSION.value);
        ctx.setCustomProperties(props);
        p.connect(ctx);

        checkConnectionIntegrityForOldClientMethods(ctx);
        checkConnectionIntegrityForNewClientMethods(Cloud.register(providerName, cloudName, endpoint, TestOldCloudProvider.class), ctx, p);
    }

    @Test
    public void closeWithNewConnectAndNewImpl() throws CloudException, InternalException {
        Cloud cloud = Cloud.register(providerName, cloudName, endpoint, TestNewCloudProvider.class);
        ProviderContext ctx = cloud.createContext(ACCOUNT, REGION, KEYS);
        CloudProvider p = ctx.connect();

        p.close();
        assertFalse("The cloud provider was not closed", p.isConnected());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void closeWithOldConnectAndNewImpl() throws CloudException, InternalException, IllegalAccessException, InstantiationException {
        CloudProvider p = TestNewCloudProvider.class.newInstance();
        ProviderContext ctx = new ProviderContext(ACCOUNT, REGION);

        ctx.setEndpoint(endpoint);
        ctx.setAccessKeys(KEYS.value[0], KEYS.value[1]);
        ctx.setX509Cert(X509.value[0]);
        ctx.setX509Key(X509.value[1]);
        Properties props = new Properties();

        props.setProperty("version", VERSION.value);
        ctx.setCustomProperties(props);
        p.connect(ctx);

        p.close();
        assertFalse("The cloud provider was not closed", p.isConnected());
    }

    @Test
    public void closeWithNewConnectAndOldImpl() throws CloudException, InternalException {
        Cloud cloud = Cloud.register(providerName, cloudName, endpoint, TestOldCloudProvider.class);
        ProviderContext ctx = cloud.createContext(ACCOUNT, REGION, KEYS);
        CloudProvider p = ctx.connect();

        p.close();
        assertFalse("The cloud provider was not closed", p.isConnected());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void closeWithOldConnectAndOldImpl() throws CloudException, InternalException, IllegalAccessException, InstantiationException {
        CloudProvider p = TestOldCloudProvider.class.newInstance();
        ProviderContext ctx = new ProviderContext(ACCOUNT, REGION);

        ctx.setEndpoint(endpoint);
        ctx.setAccessKeys(KEYS.value[0], KEYS.value[1]);
        ctx.setX509Cert(X509.value[0]);
        ctx.setX509Key(X509.value[1]);
        Properties props = new Properties();

        props.setProperty("version", VERSION.value);
        ctx.setCustomProperties(props);
        p.connect(ctx);

        p.close();
        assertFalse("The cloud provider was not closed", p.isConnected());
    }

    @Test
    public void holdWithNewConnectAndNewImpl() throws CloudException, InternalException {
        Cloud cloud = Cloud.register(providerName, cloudName, endpoint, TestNewCloudProvider.class);
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

    @SuppressWarnings("deprecation")
    @Test
    public void holdWithOldConnectAndNewImpl() throws CloudException, InternalException, IllegalAccessException, InstantiationException {
        CloudProvider p = TestNewCloudProvider.class.newInstance();
        ProviderContext ctx = new ProviderContext(ACCOUNT, REGION);

        ctx.setEndpoint(endpoint);
        ctx.setAccessKeys(KEYS.value[0], KEYS.value[1]);
        ctx.setX509Cert(X509.value[0]);
        ctx.setX509Key(X509.value[1]);
        Properties props = new Properties();

        props.setProperty("version", VERSION.value);
        ctx.setCustomProperties(props);
        p.connect(ctx);

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
    public void holdWithNewConnectAndOldImpl() throws CloudException, InternalException {
        Cloud cloud = Cloud.register(providerName, cloudName, endpoint, TestOldCloudProvider.class);
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

    @SuppressWarnings("deprecation")
    @Test
    public void holdWithOldConnectAndOldImpl() throws CloudException, InternalException, IllegalAccessException, InstantiationException {
        CloudProvider p = TestOldCloudProvider.class.newInstance();
        ProviderContext ctx = new ProviderContext(ACCOUNT, REGION);

        ctx.setEndpoint(endpoint);
        ctx.setAccessKeys(KEYS.value[0], KEYS.value[1]);
        ctx.setX509Cert(X509.value[0]);
        ctx.setX509Key(X509.value[1]);
        Properties props = new Properties();

        props.setProperty("version", VERSION.value);
        ctx.setCustomProperties(props);
        p.connect(ctx);

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
    public void releaseWithNewConnectAndNewImpl() throws CloudException, InternalException {
        Cloud cloud = Cloud.register(providerName, cloudName, endpoint, TestNewCloudProvider.class);
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

    @SuppressWarnings("deprecation")
    @Test
    public void releaseWithOldConnectAndNewImpl() throws CloudException, InternalException, IllegalAccessException, InstantiationException {
        CloudProvider p = TestNewCloudProvider.class.newInstance();
        ProviderContext ctx = new ProviderContext(ACCOUNT, REGION);

        ctx.setEndpoint(endpoint);
        ctx.setAccessKeys(KEYS.value[0], KEYS.value[1]);
        ctx.setX509Cert(X509.value[0]);
        ctx.setX509Key(X509.value[1]);
        Properties props = new Properties();

        props.setProperty("version", VERSION.value);
        ctx.setCustomProperties(props);
        p.connect(ctx);

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
    public void releaseWithNewConnectAndOldImpl() throws CloudException, InternalException {
        Cloud cloud = Cloud.register(providerName, cloudName, endpoint, TestOldCloudProvider.class);
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

    @SuppressWarnings("deprecation")
    @Test
    public void releaseWithOldConnectAndOldImpl() throws CloudException, InternalException, IllegalAccessException, InstantiationException {
        CloudProvider p = TestOldCloudProvider.class.newInstance();
        ProviderContext ctx = new ProviderContext(ACCOUNT, REGION);

        ctx.setEndpoint(endpoint);
        ctx.setAccessKeys(KEYS.value[0], KEYS.value[1]);
        ctx.setX509Cert(X509.value[0]);
        ctx.setX509Key(X509.value[1]);
        Properties props = new Properties();

        props.setProperty("version", VERSION.value);
        ctx.setCustomProperties(props);
        p.connect(ctx);

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
    public void testContext() throws CloudException, InternalException {
        Cloud cloud = Cloud.register(providerName, cloudName, endpoint, TestNewCloudProvider.class);
        ProviderContext ctx = cloud.createContext(ACCOUNT, REGION, KEYS);
        CloudProvider p = ctx.connect();

        assertNull("The default context test in the test provider should always return null", p.testContext());
    }
}
