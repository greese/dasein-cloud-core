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

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemReader;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.ProviderContext;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * An X509 in-memory key store to handle this nonsense for bozo clouds using SSL certificates for authentication.
 * <p>Created by George Reese: 11/19/12 8:48 AM</p>
 * @author George Reese
 * @version 2013.01 initial version ported from dasein-cloud-azure
 * @since 2013.01
 */
public class X509Store {
    static public final String ENTRY_ALIAS = "";
    static public final String PASSWORD    = "memory";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private KeyStore keystore;

    public X509Store(ProviderContext ctx) throws InternalException {
        try {
            X509Certificate certificate = certFromString(new String(ctx.getX509Cert(), "utf-8"));
            PrivateKey privateKey = keyFromString(new String(ctx.getX509Key(), "utf-8"));

            keystore = createJavaKeystore(certificate, privateKey);
        }
        catch( Exception e ) {
            throw new InternalException(e);
        }
    }

    private X509Certificate certFromString(String pem) throws IOException {
        return (X509Certificate)readPemObject(pem);
    }

    private KeyStore createJavaKeystore(X509Certificate cert, PrivateKey key) throws NoSuchProviderException, KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        KeyStore store = KeyStore.getInstance("JKS", "SUN");
        char[] pw = PASSWORD.toCharArray();

        store.load(null, pw);
        store.setKeyEntry(ENTRY_ALIAS, key, pw, new Certificate[] {cert});
        return store;
    }

    public KeyStore getKeystore() {
        return keystore;
    }

    private PrivateKey keyFromString(String pem) throws IOException {
        KeyPair keypair = (KeyPair)readPemObject(pem);

        if( keypair == null ) {
            throw new IOException("Could not parse key from string");
        }
        return keypair.getPrivate();
    }

    private Object readPemObject(String pemString) throws IOException {
        StringReader strReader = new StringReader(pemString);
        PemReader pemReader = new PemReader(strReader);

        try {
            return pemReader.readPemObject();
        }
        finally {
            strReader.close();
            pemReader.close();
        }
    }
}
