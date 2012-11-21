/**
 * ========= CONFIDENTIAL =========
 *
 * Copyright (C) 2012 enStratus Networks Inc - ALL RIGHTS RESERVED
 *
 * ====================================================================
 *  NOTICE: All information contained herein is, and remains the
 *  property of enStratus Networks Inc. The intellectual and technical
 *  concepts contained herein are proprietary to enStratus Networks Inc
 *  and may be covered by U.S. and Foreign Patents, patents in process,
 *  and are protected by trade secret or copyright law. Dissemination
 *  of this information or reproduction of this material is strictly
 *  forbidden unless prior written permission is obtained from
 *  enStratus Networks Inc.
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