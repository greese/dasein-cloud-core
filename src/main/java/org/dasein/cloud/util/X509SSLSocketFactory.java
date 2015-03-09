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

package org.dasein.cloud.util;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.dasein.cloud.InternalException;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

/**
 * SSL socket factory for supporting dynamic X509 over SSL authentication.
 * <p>Created by George Reese: 11/19/12 8:49 AM</p>
 * @author George Reese
 * @version 2013.01 initial version borrowed from the work done in dasein-cloud-azure
 * @since 2013.01
 */
public class X509SSLSocketFactory extends SSLSocketFactory {

    public X509SSLSocketFactory(X509Store creds) throws InternalException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        super("TLS", creds.getKeystore(), X509Store.PASSWORD, null, null, null, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
    }
}