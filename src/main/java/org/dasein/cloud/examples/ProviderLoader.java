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
package org.dasein.cloud.examples;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.ProviderContext;

import javax.annotation.Nonnull;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Loads a properly configured Dasein Cloud {@link org.dasein.cloud.CloudProvider}. This class looks for the following
 * system properties:
 * <ul>
 *     <li>DSN_PROVIDER_CLASS</li>
 *     <li>DSN_ENDPOINT</li>
 *     <li>DSN_REGION</li>
 *     <li>DSN_ACCOUNT</li>
 *     <li>DSN_API_SHARED</li>
 *     <li>DSN_API_SECRET</li>
 *     <li>DSN_API_VERSION</li>
 *     <li>DSN_CLOUD_NAME</li>
 *     <li>DSN_CLOUD_PROVIDER</li>
 * </ul>
 * The only required values are DSN_PROVIDER_CLASS, DSN_ENDPOINT, DSN_PROVIDER_REGION, DSN_API_SHARED,
 * and DSN_API_SECRET. The DSN_API_SHARED will be used for DSN_ACCOUNT if DSN_ACCOUNT is omitted.
 * In addition, you may specify implementation-specific properties using the system property DSN_CUSTOM_prop_name. For
 * example, the custom property "domain" would be specified through DSN_CUSTOM_domain.
 * <p>Created by George Reese: 10/3/12 12:18 PM</p>
 * @author George Reese
 * @version 2012.09 initial version
 * @since 2012.09
 */
public class ProviderLoader {
    private CloudProvider configuredProvider;

    public ProviderLoader() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedEncodingException {
        configure();
    }

    public @Nonnull CloudProvider getConfiguredProvider() {
        return configuredProvider;
    }

    private void configure() throws ClassNotFoundException, IllegalAccessException, InstantiationException, UnsupportedEncodingException {
        String cname = System.getProperty("DSN_PROVIDER_CLASS");

        configuredProvider = (CloudProvider)Class.forName(cname).newInstance();

        String endpoint = System.getProperty("DSN_ENDPOINT");
        String regionId = System.getProperty("DSN_REGION");
        String shared = System.getProperty("DSN_API_SHARED");
        String account = System.getProperty("DSN_ACCOUNT", shared);
        String secret = System.getProperty("DSN_API_SECRET");
        String version = System.getProperty("DSN_API_VERSION");
        String cloudName = System.getProperty("DSN_CLOUD_NAME");
        String providerName = System.getProperty("DSN_CLOUD_PROVIDER");

        ProviderContext ctx = new ProviderContext();

        ctx.setEndpoint(endpoint);
        if( providerName != null ) { ctx.setProviderName(providerName); }
        if( cloudName != null ) { ctx.setCloudName(cloudName); }
        if( regionId != null ) { ctx.setRegionId(regionId); }
        ctx.setAccountNumber(account);
        ctx.setAccessKeys(shared.getBytes("utf-8"), secret.getBytes("utf-8"));

        Properties properties = new Properties();

        if( version != null ) {
            properties.setProperty("apiVersion", version);
        }
        for( String prop : System.getProperties().stringPropertyNames() ) {
            if( prop.startsWith("DSN_CUSTOM_") ) {
                properties.put(prop.substring("DSN_CUSTOM_".length()), System.getProperty(prop));
            }
        }
        ctx.setCustomProperties(properties);
        configuredProvider.connect(ctx);
    }
}
