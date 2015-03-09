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
import org.dasein.cloud.test.TestNewStorageCloudProvider;
import org.dasein.cloud.test.TestOldCloudProvider;
import org.dasein.cloud.test.TestOldStorageCloudProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Tests many of the same operations as {@link org.dasein.cloud.CloudConnectTestCase}, except it focuses on the Dasein
 * hybrid cloud scenario in which distinct compute and storage clouds are joined into a single virtual cloud.
 * <p>Created by George Reese: 3/1/14 2:04 PM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 (issue #123)
 */
public class HybridCloudConnectTestCase {
    static public final String COMPUTE_ACCOUNT = "account";
    static public final String STORAGE_ACCOUNT = "storageAccount";
    static public final String REGION          = "region";
    static public final String SREGION         = "sregion";

    static public final ProviderContext.Value<byte[][]> KEYS    = new ProviderContext.Value<byte[][]>("apiKeys", new byte[][] { "public".getBytes(), "private".getBytes() });
    static public final ProviderContext.Value<byte[][]> SKEYS   = new ProviderContext.Value<byte[][]>("apiKeys", new byte[][] { "spub".getBytes(), "spriv".getBytes() });
    static public final ProviderContext.Value<String>   VERSION = new ProviderContext.Value<String>("version", "1");
    static public final ProviderContext.Value<byte[][]> X509    = new ProviderContext.Value<byte[][]>("x509", new byte[][] { "x509c".getBytes(), "x509k".getBytes() });

    static private class Config {
        private String cloudName;
        private String effectiveAccountNumber;
        private String providerName;
        private String endpoint;
    }

    static private int testNumber = 0;

    private Config compute;
    private Config storage;

    @Before
    public void setUp() {
        testNumber++;
        compute = new Config();
        compute.cloudName = "Compute Cloud " + testNumber;
        compute.providerName = "Compute Provider " + testNumber;
        compute.endpoint = "https://compute.example.com/" + testNumber;
        compute.effectiveAccountNumber = COMPUTE_ACCOUNT;
        storage = new Config();
        storage.cloudName = "Storage Cloud " + testNumber;
        storage.providerName = "Storage Provider " + testNumber;
        storage.endpoint = "https://storage.example.com/" + testNumber;
        storage.effectiveAccountNumber = COMPUTE_ACCOUNT;
    }

    @After
    public void tearDown() {

    }

    /********************************** NEW IMPLEMENTATION, OLD CLIENT **********************************/

    @SuppressWarnings("deprecation")
    private void checkConnectionIntegrityForOldClientMethods(@Nullable ProviderContext ctx, @Nonnull Config cfg, boolean cTest) {
        assertNotNull("The context resulting from context creation may not be null", ctx);
        assertNotNull("No context was created", ctx);

        byte[] b = ctx.getAccessPublic();
        byte[] v = ctx.getAccessPrivate();

        assertNotNull("No public key was found in the provider context", b);
        assertNotNull("No private key was found in the provider context", v);
        String pub = new String(b);
        String priv = new String(v);

        if( cTest ) {
            assertEquals("Public key is not public", "public", pub);
            assertEquals("Private key is not private", "private", priv);
        }
        else {
            assertEquals("Public key is not public", "spub", pub);
            assertEquals("Private key is not private", "spriv", priv);
        }

        b = ctx.getX509Cert();
        v = ctx.getX509Key();
        if( cTest ) {
            assertNotNull("No X509 cert was found in the provider context", b);
            assertNotNull("No X509 key was found in the provider context", v);
            pub = new String(b);
            priv = new String(v);

            assertEquals("X509 certificate is not public", "x509c", pub);
            assertEquals("X509 key is not private", "x509k", priv);
        }
        if( cTest ) {
            Properties props = ctx.getCustomProperties();

            assertNotNull("Custom properties may not be null", props);
            assertFalse("Custom properties for test cloud should contain values", props.isEmpty());

            String version = props.getProperty("version");

            assertEquals("The set version does not match the test value", VERSION.value, version);
        }
        else {
            Properties props = ctx.getCustomProperties();

            assertNotNull("Custom properties may not be null", props);
            assertTrue("Custom properties for test storage cloud should NOT contain values: " + props, props.isEmpty());
        }
        assertEquals("The cloud name does not match the set value", cfg.cloudName, ctx.getCloudName());
        assertEquals("The provider name does not match the set value", cfg.providerName, ctx.getProviderName());
        assertEquals("The endpoint does not match the set value", cfg.endpoint, ctx.getEndpoint());
    }

    private void checkConnectionIntegrityForNewClientMethods(@Nonnull Cloud cloud, @Nullable ProviderContext ctx, @Nullable CloudProvider provider, @Nonnull Config cfg, String account, String region) {
        assertNotNull("The context resulting from context creation may not be null", ctx);
        assertNotNull("Cloud provider resulting from a connect may not be null", provider);

        assertTrue("Cloud provider was not connected", provider.isConnected());
        assertNotNull("The cloud name should not be null", provider.getCloudName());
        assertNotNull("The provider name should not be null", provider.getProviderName());

        ContextRequirements requirements = provider.getContextRequirements();

        assertNotNull("The context requirements may not be null", requirements);
        if( provider.getClass().getName().contains("New") ) {
            if( provider.getClass().getName().contains("Storage") ) {
                assertEquals("There should be exactly one field required for the test provider", 1, requirements.getConfigurableValues().size());
            }
            else {
                assertEquals("There should be exactly three fields required for the test provider", 3, requirements.getConfigurableValues().size());
            }
        }
        else {
            assertEquals("There should be exactly two fields required for the test provider", 2, requirements.getConfigurableValues().size());
        }
        assertEquals("The cloud does not match", cloud, ctx.getCloud());
        assertEquals("The account numbers do not match", account, ctx.getAccountNumber());
        assertEquals("The region values do not match", region, ctx.getRegionId());
        assertEquals("The effective account number does not match the required value", cfg.effectiveAccountNumber, ctx.getEffectiveAccountNumber());
        for( ContextRequirements.Field f : requirements.getConfigurableValues() ) {
            if( f.required ) {
                Object value = ctx.getConfigurationValue(f.name);

                assertNotNull("The value for " + f.name + " should have been set for these tests", value);

                Object full = ctx.getConfigurationValue(f);

                assertEquals("The two configurable value methods for " + f.name + " should return the same value", full, value);
            }
        }
    }

    @Test
    public void verifyServicesUsingNewConnectWithNewCloud() throws CloudException, InternalException {
        Cloud cCloud = Cloud.register(compute.providerName, compute.cloudName, compute.endpoint, TestNewCloudProvider.class);
        ProviderContext cCtx = cCloud.createContext(COMPUTE_ACCOUNT, REGION, KEYS, X509, VERSION);
        CloudProvider cp = cCtx.connect();

        assertFalse("Test compute provider should have no admin services", cp.hasAdminServices());
        assertFalse("Test compute provider should have no compute services", cp.hasComputeServices());
        assertFalse("Test compute provider should have no converged infrastructure services", cp.hasCIServices());
        assertFalse("Test compute provider should have no identity services", cp.hasIdentityServices());
        assertFalse("Test compute provider should have no network services", cp.hasNetworkServices());
        assertFalse("Test compute provider should have no platform services", cp.hasPlatformServices());
        assertFalse("Test compute provider should have no storage services", cp.hasStorageServices());

        Cloud sCloud = Cloud.register(storage.providerName, storage.cloudName, storage.endpoint, TestNewStorageCloudProvider.class);
        ProviderContext sCtx = sCloud.createContext(STORAGE_ACCOUNT, SREGION, SKEYS);
        CloudProvider sp = sCtx.connect(cp);

        assertFalse("Test storage provider should have no admin services", sp.hasAdminServices());
        assertFalse("Test storage provider should have no compute services", sp.hasComputeServices());
        assertFalse("Test storage provider should have no converged infrastructure services", sp.hasCIServices());
        assertFalse("Test storage provider should have no identity services", sp.hasIdentityServices());
        assertFalse("Test storage provider should have no network services", sp.hasNetworkServices());
        assertFalse("Test storage provider should have no platform services", sp.hasPlatformServices());
        assertTrue("Test storage provider has no storage services", sp.hasStorageServices());

        assertFalse("Test compute provider should have no admin services after configuration", cp.hasAdminServices());
        assertFalse("Test compute provider should have no compute services after configuration", cp.hasComputeServices());
        assertFalse("Test compute provider should have no converged infrastructure services after configuration", cp.hasCIServices());
        assertFalse("Test compute provider should have no identity services after configuration", cp.hasIdentityServices());
        assertFalse("Test compute provider should have no network services after configuration", cp.hasNetworkServices());
        assertFalse("Test compute provider should have no platform services after configuration", cp.hasPlatformServices());
        assertTrue("Test compute provider has no storage services after configuration", cp.hasStorageServices());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void verifyServicesUsingOldConnectWithNewCloud() throws CloudException, InternalException, IllegalAccessException, InstantiationException {
        CloudProvider cp = TestNewCloudProvider.class.newInstance();
        ProviderContext cCtx = new ProviderContext(COMPUTE_ACCOUNT, REGION);

        cCtx.setEndpoint(compute.endpoint);
        cCtx.setAccessKeys(KEYS.value[0], KEYS.value[1]);
        cCtx.setX509Cert( X509.value[0]);
        cCtx.setX509Key( X509.value[1]);
        Properties props = new Properties();

        props.setProperty("version", VERSION.value);
        cCtx.setCustomProperties(props);

        cCtx.setStorage(TestNewStorageCloudProvider.class.getName());
        cCtx.setStorageAccountNumber(STORAGE_ACCOUNT);
        cCtx.setStorageEndpoint(storage.endpoint);
        cCtx.setStorageKeys( SKEYS.value[0],  SKEYS.value[1]);

        cp.connect(cCtx);

        assertFalse("Test compute provider should have no admin services after configuration", cp.hasAdminServices());
        assertFalse("Test compute provider should have no compute services after configuration", cp.hasComputeServices());
        assertFalse("Test compute provider should have no converged infrastructure services after configuration", cp.hasCIServices());
        assertFalse("Test compute provider should have no identity services after configuration", cp.hasIdentityServices());
        assertFalse("Test compute provider should have no network services after configuration", cp.hasNetworkServices());
        assertFalse("Test compute provider should have no platform services after configuration", cp.hasPlatformServices());
        assertTrue("Test compute provider has no storage services after configuration", cp.hasStorageServices());
    }

    @Test
    public void newConnectWithNewImpl() throws CloudException, InternalException {
        Cloud cCloud = Cloud.register(compute.providerName, compute.cloudName, compute.endpoint, TestNewCloudProvider.class);
        ProviderContext cCtx = cCloud.createContext(COMPUTE_ACCOUNT, REGION, KEYS, X509, VERSION);
        CloudProvider cp = cCtx.connect();

        Cloud sCloud = Cloud.register(storage.providerName, storage.cloudName, storage.endpoint, TestNewStorageCloudProvider.class);
        ProviderContext sCtx = sCloud.createContext(STORAGE_ACCOUNT, SREGION, SKEYS);
        CloudProvider sp = sCtx.connect(cp);

        checkConnectionIntegrityForOldClientMethods(cCtx, compute, true);
        checkConnectionIntegrityForNewClientMethods(cCloud, cCtx, cp, compute, COMPUTE_ACCOUNT, REGION);

        checkConnectionIntegrityForOldClientMethods(sCtx, storage, false);
        checkConnectionIntegrityForNewClientMethods(sCloud, sCtx, sp, storage, STORAGE_ACCOUNT, SREGION);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void oldConnectWithNewImpl() throws CloudException, InternalException, IllegalAccessException, InstantiationException {
        CloudProvider cp = TestNewCloudProvider.class.newInstance();
        ProviderContext cCtx = new ProviderContext(COMPUTE_ACCOUNT, REGION);

        cCtx.setEndpoint(compute.endpoint);
        cCtx.setAccessKeys(KEYS.value[0], KEYS.value[1]);
        cCtx.setX509Cert( X509.value[0]);
        cCtx.setX509Key( X509.value[1]);
        cCtx.setCloudName(compute.cloudName);
        cCtx.setProviderName(compute.providerName);
        Properties props = new Properties();

        props.setProperty("version", VERSION.value);
        cCtx.setCustomProperties(props);

        cCtx.setStorage(TestNewStorageCloudProvider.class.getName());
        cCtx.setStorageAccountNumber(STORAGE_ACCOUNT);
        cCtx.setStorageEndpoint(storage.endpoint);
        cCtx.setStorageKeys( SKEYS.value[0],  SKEYS.value[1]);
        cp.connect(cCtx);

        checkConnectionIntegrityForOldClientMethods(cCtx, compute, true);
        checkConnectionIntegrityForNewClientMethods(Cloud.register(compute.providerName, compute.cloudName, compute.endpoint, TestNewCloudProvider.class), cCtx, cp, compute, COMPUTE_ACCOUNT, REGION);
    }

    @Test
    public void newConnectWithOldImpl() throws CloudException, InternalException {
        Cloud cCloud = Cloud.register(compute.providerName, compute.cloudName, compute.endpoint, TestOldCloudProvider.class);
        ProviderContext cCtx = cCloud.createContext(COMPUTE_ACCOUNT, REGION, KEYS, X509, VERSION);
        CloudProvider cp = cCtx.connect();

        Cloud sCloud = Cloud.register(storage.providerName, storage.cloudName, storage.endpoint, TestOldStorageCloudProvider.class);
        ProviderContext sCtx = sCloud.createContext(STORAGE_ACCOUNT, SREGION, SKEYS);
        CloudProvider sp = sCtx.connect(cp);

        checkConnectionIntegrityForOldClientMethods(cCtx, compute, true);
        checkConnectionIntegrityForNewClientMethods(cCloud, cCtx, cp, compute, COMPUTE_ACCOUNT, REGION);

        checkConnectionIntegrityForOldClientMethods(sCtx, storage, false);
        checkConnectionIntegrityForNewClientMethods(sCloud, sCtx, sp, storage, STORAGE_ACCOUNT, SREGION);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void oldConnectWithOldImpl() throws CloudException, InternalException, IllegalAccessException, InstantiationException {
        CloudProvider cp = TestOldCloudProvider.class.newInstance();
        ProviderContext cCtx = new ProviderContext(COMPUTE_ACCOUNT, REGION);

        cCtx.setEndpoint(compute.endpoint);
        cCtx.setAccessKeys(KEYS.value[0], KEYS.value[1]);
        cCtx.setX509Cert( X509.value[0]);
        cCtx.setX509Key( X509.value[1]);
        cCtx.setCloudName(compute.cloudName);
        cCtx.setProviderName(compute.providerName);
        Properties props = new Properties();

        props.setProperty("version", VERSION.value);
        cCtx.setCustomProperties(props);

        cCtx.setStorage(TestOldStorageCloudProvider.class.getName());
        cCtx.setStorageAccountNumber(STORAGE_ACCOUNT);
        cCtx.setStorageEndpoint(storage.endpoint);
        cCtx.setStorageKeys(SKEYS.value[0], SKEYS.value[1]);
        cp.connect(cCtx);

        checkConnectionIntegrityForOldClientMethods(cCtx, compute, true);
        checkConnectionIntegrityForNewClientMethods(Cloud.register(compute.providerName, compute.cloudName, compute.endpoint, TestOldCloudProvider.class), cCtx, cp, compute, COMPUTE_ACCOUNT, REGION);
    }

    @Test
    public void closeWithNewConnectAndNewImpl() throws CloudException, InternalException {
        Cloud cCloud = Cloud.register(compute.providerName, compute.cloudName, compute.endpoint, TestNewCloudProvider.class);
        ProviderContext cCtx = cCloud.createContext(COMPUTE_ACCOUNT, REGION, KEYS, X509, VERSION);
        CloudProvider cp = cCtx.connect();

        Cloud sCloud = Cloud.register(storage.providerName, storage.cloudName, storage.endpoint, TestNewStorageCloudProvider.class);
        ProviderContext sCtx = sCloud.createContext(STORAGE_ACCOUNT, SREGION, SKEYS);
        CloudProvider sp = sCtx.connect(cp);

        cp.close();
        assertFalse("The compute cloud provider was not closed", cp.isConnected());
        assertFalse("The storage cloud provider was not closed", sp.isConnected());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void closeWithOldConnectAndNewImpl() throws CloudException, InternalException, IllegalAccessException, InstantiationException {
        CloudProvider cp = TestNewCloudProvider.class.newInstance();
        ProviderContext cCtx = new ProviderContext(COMPUTE_ACCOUNT, REGION);

        cCtx.setEndpoint(compute.endpoint);
        cCtx.setAccessKeys(KEYS.value[0], KEYS.value[1]);
        cCtx.setX509Cert( X509.value[0]);
        cCtx.setX509Key( X509.value[1]);
        Properties props = new Properties();

        props.setProperty("version", VERSION.value);
        cCtx.setCustomProperties(props);

        cCtx.setStorage(TestNewStorageCloudProvider.class.getName());
        cCtx.setStorageAccountNumber(STORAGE_ACCOUNT);
        cCtx.setStorageEndpoint(storage.endpoint);
        cCtx.setStorageKeys( SKEYS.value[0],  SKEYS.value[1]);
        cp.connect(cCtx);

        cp.close();
        assertFalse("The compute cloud provider was not closed", cp.isConnected());
    }

    @Test
    public void closeWithNewConnectAndOldImpl() throws CloudException, InternalException {
        Cloud cCloud = Cloud.register(compute.providerName, compute.cloudName, compute.endpoint, TestOldCloudProvider.class);
        ProviderContext cCtx = cCloud.createContext(COMPUTE_ACCOUNT, REGION, KEYS, X509, VERSION);
        CloudProvider cp = cCtx.connect();

        Cloud sCloud = Cloud.register(storage.providerName, storage.cloudName, storage.endpoint, TestOldStorageCloudProvider.class);
        ProviderContext sCtx = sCloud.createContext(STORAGE_ACCOUNT, SREGION, SKEYS);
        CloudProvider sp = sCtx.connect(cp);

        cp.close();
        assertFalse("The compute cloud provider was not closed", cp.isConnected());
        assertFalse("The storage cloud provider was not closed", sp.isConnected());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void closeWithOldConnectAndOldImpl() throws CloudException, InternalException, IllegalAccessException, InstantiationException {
        CloudProvider cp = TestOldCloudProvider.class.newInstance();
        ProviderContext cCtx = new ProviderContext(COMPUTE_ACCOUNT, REGION);

        cCtx.setEndpoint(compute.endpoint);
        cCtx.setAccessKeys(KEYS.value[0], KEYS.value[1]);
        cCtx.setX509Cert( X509.value[0]);
        cCtx.setX509Key( X509.value[1]);
        Properties props = new Properties();

        props.setProperty("version", VERSION.value);
        cCtx.setCustomProperties(props);

        cCtx.setStorage(TestOldStorageCloudProvider.class.getName());
        cCtx.setStorageAccountNumber(STORAGE_ACCOUNT);
        cCtx.setStorageEndpoint(storage.endpoint);
        cCtx.setStorageKeys( SKEYS.value[0],  SKEYS.value[1]);
        cp.connect(cCtx);

        cp.close();
        assertFalse("The compute cloud provider was not closed", cp.isConnected());
    }

    @Test
    public void holdWithNewConnectAndNewImpl() throws CloudException, InternalException {
        Cloud cCloud = Cloud.register(compute.providerName, compute.cloudName, compute.endpoint, TestNewCloudProvider.class);
        ProviderContext cCtx = cCloud.createContext(COMPUTE_ACCOUNT, REGION, KEYS, X509, VERSION);
        CloudProvider cp = cCtx.connect();

        Cloud sCloud = Cloud.register(storage.providerName, storage.cloudName, storage.endpoint, TestNewStorageCloudProvider.class);
        ProviderContext sCtx = sCloud.createContext(STORAGE_ACCOUNT, SREGION, SKEYS);
        CloudProvider sp = sCtx.connect(cp);

        cp.hold();
        cp.close();
        try {
            assertTrue("Compute cloud provider was closed prior to hold being released", cp.isConnected());
            assertTrue("Storage cloud provider was closed prior to hold being released", sp.isConnected());
        }
        finally {
            cp.release();
        }
    }

    @SuppressWarnings("deprecation")
    @Test
    public void holdWithOldConnectAndNewImpl() throws CloudException, InternalException, IllegalAccessException, InstantiationException {
        CloudProvider cp = TestNewCloudProvider.class.newInstance();
        ProviderContext cCtx = new ProviderContext(COMPUTE_ACCOUNT, REGION);

        cCtx.setEndpoint(compute.endpoint);
        cCtx.setAccessKeys(KEYS.value[0], KEYS.value[1]);
        cCtx.setX509Cert( X509.value[0]);
        cCtx.setX509Key( X509.value[1]);
        Properties props = new Properties();

        props.setProperty("version", VERSION.value);
        cCtx.setCustomProperties(props);

        cCtx.setStorage(TestNewStorageCloudProvider.class.getName());
        cCtx.setStorageAccountNumber(STORAGE_ACCOUNT);
        cCtx.setStorageEndpoint(storage.endpoint);
        cCtx.setStorageKeys( SKEYS.value[0],  SKEYS.value[1]);
        cp.connect(cCtx);

        cp.hold();
        cp.close();
        try {
            assertTrue("Compute cloud provider was closed prior to hold being released", cp.isConnected());
        }
        finally {
            cp.release();
        }
    }

    @Test
    public void holdWithNewConnectAndOldImpl() throws CloudException, InternalException {
        Cloud cCloud = Cloud.register(compute.providerName, compute.cloudName, compute.endpoint, TestOldCloudProvider.class);
        ProviderContext cCtx = cCloud.createContext(COMPUTE_ACCOUNT, REGION, KEYS, X509, VERSION);
        CloudProvider cp = cCtx.connect();

        Cloud sCloud = Cloud.register(storage.providerName, storage.cloudName, storage.endpoint, TestOldStorageCloudProvider.class);
        ProviderContext sCtx = sCloud.createContext(STORAGE_ACCOUNT, SREGION, SKEYS);
        CloudProvider sp = sCtx.connect(cp);

        cp.hold();
        cp.close();
        try {
            assertTrue("Compute cloud provider was closed prior to hold being released", cp.isConnected());
            assertTrue("Storage cloud provider was closed prior to hold being released", sp.isConnected());
        }
        finally {
            cp.release();
        }
    }

    @SuppressWarnings("deprecation")
    @Test
    public void holdWithOldConnectAndOldImpl() throws CloudException, InternalException, IllegalAccessException, InstantiationException {
        CloudProvider cp = TestOldCloudProvider.class.newInstance();
        ProviderContext cCtx = new ProviderContext(COMPUTE_ACCOUNT, REGION);

        cCtx.setEndpoint(compute.endpoint);
        cCtx.setAccessKeys(KEYS.value[0], KEYS.value[1]);
        cCtx.setX509Cert( X509.value[0]);
        cCtx.setX509Key( X509.value[1]);
        Properties props = new Properties();

        props.setProperty("version", VERSION.value);
        cCtx.setCustomProperties(props);

        cCtx.setStorage(TestOldStorageCloudProvider.class.getName());
        cCtx.setStorageAccountNumber(STORAGE_ACCOUNT);
        cCtx.setStorageEndpoint(storage.endpoint);
        cCtx.setStorageKeys( SKEYS.value[0],  SKEYS.value[1]);
        cp.connect(cCtx);

        cp.hold();
        cp.close();
        try {
            assertTrue("Compute cloud provider was closed prior to hold being released", cp.isConnected());
        }
        finally {
            cp.release();
        }
    }

    @Test
    public void releaseWithNewConnectAndNewImpl() throws CloudException, InternalException {
        Cloud cCloud = Cloud.register(compute.providerName, compute.cloudName, compute.endpoint, TestNewCloudProvider.class);
        ProviderContext cCtx = cCloud.createContext(COMPUTE_ACCOUNT, REGION, KEYS, X509, VERSION);
        CloudProvider cp = cCtx.connect();

        Cloud sCloud = Cloud.register(storage.providerName, storage.cloudName, storage.endpoint, TestNewStorageCloudProvider.class);
        ProviderContext sCtx = sCloud.createContext(STORAGE_ACCOUNT, SREGION, SKEYS);
        CloudProvider sp = sCtx.connect(cp);

        cp.hold();
        cp.close();
        try {
            try { Thread.sleep(1000L); }
            catch( InterruptedException e ) { }
        }
        finally {
            cp.release();
        }
        try { Thread.sleep(2000L); }
        catch( InterruptedException e ) { }
        assertFalse("Compute cloud provider was not properly closed after release", cp.isConnected());
        assertFalse("Storage cloud provider was not properly closed after release", sp.isConnected());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void releaseWithOldConnectAndNewImpl() throws CloudException, InternalException, IllegalAccessException, InstantiationException {
        CloudProvider cp = TestNewCloudProvider.class.newInstance();
        ProviderContext cCtx = new ProviderContext(COMPUTE_ACCOUNT, REGION);

        cCtx.setEndpoint(compute.endpoint);
        cCtx.setAccessKeys(KEYS.value[0], KEYS.value[1]);
        cCtx.setX509Cert( X509.value[0]);
        cCtx.setX509Key( X509.value[1]);
        Properties props = new Properties();

        props.setProperty("version", VERSION.value);
        cCtx.setCustomProperties(props);

        cCtx.setStorage(TestNewStorageCloudProvider.class.getName());
        cCtx.setStorageAccountNumber(STORAGE_ACCOUNT);
        cCtx.setStorageEndpoint(storage.endpoint);
        cCtx.setStorageKeys( SKEYS.value[0],  SKEYS.value[1]);
        cp.connect(cCtx);

        cp.hold();
        cp.close();
        try {
            try { Thread.sleep(1000L); }
            catch( InterruptedException e ) { }
        }
        finally {
            cp.release();
        }
        try { Thread.sleep(2000L); }
        catch( InterruptedException e ) { }
        assertFalse("Compute cloud provider was not properly closed after release", cp.isConnected());
    }

    @Test
    public void releaseWithNewConnectAndOldImpl() throws CloudException, InternalException {
        Cloud cCloud = Cloud.register(compute.providerName, compute.cloudName, compute.endpoint, TestOldCloudProvider.class);
        ProviderContext cCtx = cCloud.createContext(COMPUTE_ACCOUNT, REGION, KEYS, X509, VERSION);
        CloudProvider cp = cCtx.connect();

        Cloud sCloud = Cloud.register(storage.providerName, storage.cloudName, storage.endpoint, TestOldStorageCloudProvider.class);
        ProviderContext sCtx = sCloud.createContext(STORAGE_ACCOUNT, SREGION, SKEYS);
        CloudProvider sp = sCtx.connect(cp);

        cp.hold();
        cp.close();
        try {
            try { Thread.sleep(1000L); }
            catch( InterruptedException e ) { }
        }
        finally {
            cp.release();
        }
        try { Thread.sleep(2000L); }
        catch( InterruptedException e ) { }
        assertFalse("Compute cloud provider was not properly closed after release", cp.isConnected());
        assertFalse("Storage cloud provider was not properly closed after release", sp.isConnected());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void releaseWithOldConnectAndOldImpl() throws CloudException, InternalException, IllegalAccessException, InstantiationException {
        CloudProvider cp = TestOldCloudProvider.class.newInstance();
        ProviderContext cCtx = new ProviderContext(COMPUTE_ACCOUNT, REGION);

        cCtx.setEndpoint(compute.endpoint);
        cCtx.setAccessKeys(KEYS.value[0], KEYS.value[1]);
        cCtx.setX509Cert( X509.value[0]);
        cCtx.setX509Key( X509.value[1]);
        Properties props = new Properties();

        props.setProperty("version", VERSION.value);
        cCtx.setCustomProperties(props);

        cCtx.setStorage(TestOldStorageCloudProvider.class.getName());
        cCtx.setStorageAccountNumber(STORAGE_ACCOUNT);
        cCtx.setStorageEndpoint(storage.endpoint);
        cCtx.setStorageKeys( SKEYS.value[0],  SKEYS.value[1]);
        cp.connect(cCtx);

        cp.hold();
        cp.close();
        try {
            try { Thread.sleep(1000L); }
            catch( InterruptedException e ) { }
        }
        finally {
            cp.release();
        }
        try { Thread.sleep(2000L); }
        catch( InterruptedException e ) { }
        assertFalse("Compute cloud provider was not properly closed after release", cp.isConnected());
    }
}
