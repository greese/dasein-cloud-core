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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("deprecation")
public class CloudConnectOldTestCase {
    private CloudProvider provider = null;
    
    @Before
    public void setUp() {
        provider = new TestCloudProvider();
    }
    
    @After
    public void tearDown() {
        provider.close();
        while( provider.isConnected() ) {
            provider.release();
        }
    }
    
    @Test
    public void close() {
        provider.connect(new ProviderContext());
        provider.close();
        assertTrue("Did not close provider", !provider.isConnected());
    }

    @Test
    public void connect() {
        provider.connect(new ProviderContext());
        assertTrue("Did not connect to provider", provider.isConnected());
    }

    @Test
    public void testPrivateKey() {
        ProviderContext ctx = new ProviderContext();

        ctx.setAccessKeys(new byte[] { 1, 2, 3 }, new byte[] { 1, 2, 3 });
        provider.connect(ctx);
        provider.close();
        boolean cleared = false;
        
        byte[] privateKey = ctx.getAccessPrivate();
        
        assertNotNull("Private key was null", privateKey);
        for( int i=0; i<privateKey.length; i++ ) {
            if( privateKey[i] != i ) {
                cleared = true;
                break;
            }
        }
        assertTrue("Data was not cleared from private key", cleared);
    }
    
    @Test
    public void hold() {
        provider.connect(new ProviderContext());
        provider.hold();
        provider.close();
        assertTrue("Hold is not held", provider.isConnected());
    }
    
    @Test
    public void release() {
        provider.connect(new ProviderContext());
        provider.hold();
        provider.close();
        provider.release();
        try { Thread.sleep(3000L); }
        catch( InterruptedException e ) { }
        assertTrue("Provider did not release", !provider.isConnected());        
    }
}
