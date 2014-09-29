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

package org.dasein.cloud.util;

import org.apache.log4j.Logger;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.ProviderContext;
import org.dasein.cloud.RequestTrackingStrategy;
import org.json.JSONObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * A tool for tracing the load your Dasein Cloud usage is placing on a cloud provider. This class is used by
 * {@link API} to provide JMX integration. In order for any API tracing to be functional, you must set the
 * log level for org.dasein.cloud.util.APITrace to TRACE, DEBUG, or INFO depending on the information you are seeking.
 * To turn it off, set the level to WARN or higher.
 * <p>Created by George Reese: 11/16/12 7:20 PM</p>
 * @author George Reese
 * @version 2013.01 initial version (Issue #1)
 * @since 2013.01
 */
public class  APITrace {
    static private final Logger logger = Logger.getLogger(APITrace.class);
    static public final String DELIMITER       = ".";
    static public final String DELIMITER_REGEX = "\\.";

    static private class CloudOperation {
        public String name;
        public long startTimestamp = System.currentTimeMillis();
        public long endTimestamp = 0L;
        public int calls = 0;
        public CloudOperation currentChild;
        public ArrayList<CloudOperation> priorChildren;
        public ArrayList<String> apiCalls;

        public CloudOperation(@Nonnull String name) { this.name = name; }
    }

    static private final HashMap<String,Long>            apiCount       = new HashMap<String, Long>();
    static private final HashMap<String,Long>            operationApis  = new HashMap<String, Long>();
    static private final HashMap<String,Long>            operationCount = new HashMap<String, Long>();
    static private final HashMap<String,CloudOperation>  operationTrace = new HashMap<String, CloudOperation>();

    static private HashMap<Long,CloudOperation> operations = new HashMap<Long, CloudOperation>();


    static {
        try {
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("org.dasein:type=API");
            API api = new API();

            server.registerMBean(api, name);
        }
        catch( Throwable t ) {
            logger.error("Unable to set up API MBean: " + t.getMessage());
            t.printStackTrace();
        }
    }

    static public void begin(@Nonnull CloudProvider provider, @Nonnull String operationName) {
        if( logger.isDebugEnabled() ) {
            try {
                ProviderContext ctx = provider.getContext();
                String accountNumber = getAccountNumber( ctx );
                RequestTrackingStrategy strategy = ctx.getRequestTrackingStrategy();
                String requestTracking = "";
                if(strategy != null && strategy.getInAPITrace()){
                    requestTracking = strategy.getRequestID() + DELIMITER;
                }

                operationName = provider.getProviderName().replaceAll(DELIMITER_REGEX, "_") + DELIMITER + provider.getCloudName().replaceAll(DELIMITER_REGEX, "_") + DELIMITER + accountNumber.replaceAll(DELIMITER_REGEX, "_") + DELIMITER + requestTracking + operationName;
                long thread = Thread.currentThread().getId();
                CloudOperation operation = new CloudOperation(operationName);
                CloudOperation current = operations.get(thread);

                if( current == null ) {
                    operations.put(thread, operation);
                }
                else {
                    while( current.currentChild != null ) {
                        current = current.currentChild;
                    }
                    current.currentChild = operation;
                }
                synchronized( operationCount ) {
                    if( operationCount.containsKey(operationName) ) {
                        operationCount.put(operationName,  operationCount.get(operationName) + 1);
                    }
                    else {
                        operationCount.put(operationName, 1L);
                    }
                }
            }
            catch( Throwable t ) {
                logger.warn("Error with API trace begin: " + t.getMessage());
            }
        }
    }

    static private long count(CloudOperation operation) {
        long count = operation.calls;

        if( operation.priorChildren != null ) {
            for( CloudOperation o : operation.priorChildren ) {
                count += count(o);
            }
        }
        return count;
    }

    static public void end() {
        if( logger.isDebugEnabled() ) {
            try {
                long thread = Thread.currentThread().getId();
                CloudOperation current = operations.get(thread);

                if( current == null ) {
                    return;
                }
                CloudOperation parent = null;

                while( current.currentChild != null ) {
                    parent = current;
                    current = current.currentChild;
                }
                current.endTimestamp = System.currentTimeMillis();
                if( parent != null ) {
                    if( parent.priorChildren == null ) {
                        parent.priorChildren = new ArrayList<CloudOperation>();
                    }
                    parent.priorChildren.add(current);
                    parent.currentChild = null;
                }
                else {
                    operations.remove(thread);
                }
                log(current);
            }
            catch( Throwable t ) {
                logger.warn("Error with API trace end: " + t.getMessage());
            }
        }
    }

    static public long getAPICount() {
        long count = 0L;

        synchronized( apiCount ) {
            for( Map.Entry<String,Long> api : apiCount.entrySet() ) {
                count += api.getValue();
            }
        }
        return count;
    }

    static public long getAPICount(@Nonnull String providerName) {
        return getAPICountForPrefix(providerName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER);
    }

    static public long getAPICount(@Nonnull String providerName, @Nonnull String cloudName) {
        return getAPICountForPrefix(providerName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER + cloudName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER);
    }

    static public long getAPICount(@Nonnull String providerName, @Nonnull String cloudName, @Nonnull String accountNumber) {
        return getAPICountForPrefix(providerName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER + cloudName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER + accountNumber.replaceAll(DELIMITER_REGEX, "_") + DELIMITER);
    }

    static public long getAPICount(@Nonnull String providerName, @Nonnull String cloudName, @Nonnull String accountNumber, @Nonnull String apiCall) {
        return getAPICountForPrefix(providerName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER + cloudName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER + accountNumber.replaceAll(DELIMITER_REGEX, "_") + DELIMITER + apiCall);
    }

    static public long getAPICountAcrossAccounts(@Nonnull String providerName, @Nonnull String cloudName, @Nonnull String apiCall) {
        String prefix = providerName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER + cloudName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER;
        long count = 0L;

        synchronized( apiCount ) {
            for( Map.Entry<String,Long> api : apiCount.entrySet() ) {
                if( api.getKey().startsWith(prefix) && api.getKey().endsWith(apiCall)) {
                    count += api.getValue();
                }
            }
        }
        return count;
    }

    static private long getAPICountForPrefix(@Nonnull String prefix) {
        long count = 0L;

        synchronized( apiCount ) {
            for( Map.Entry<String,Long> api : apiCount.entrySet() ) {
                if( api.getKey().startsWith(prefix) ) {
                    count += api.getValue();
                }
            }
        }
        return count;
    }

    static public long getAPICountForOperation() {
        long count = 0L;

        synchronized( operationApis ) {
            for( Map.Entry<String,Long> api : operationApis.entrySet() ) {
                count += api.getValue();
            }
        }
        return count;
    }

    static public long getAPICountForOperation(@Nonnull String providerName) {
        return getAPICountForPrefixForOperation(providerName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER);
    }

    static public long getAPICountForOperation(@Nonnull String providerName, @Nonnull String cloudName) {
        return getAPICountForPrefixForOperation(providerName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER + cloudName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER);
    }

    static public long getAPICountForOperation(@Nonnull String providerName, @Nonnull String cloudName, @Nonnull String accountNumber) {
        return getAPICountForPrefixForOperation(providerName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER + cloudName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER + accountNumber.replaceAll(DELIMITER_REGEX, "_") + DELIMITER);
    }

    static public long getAPICountForOperation(@Nonnull String providerName, @Nonnull String cloudName, @Nonnull String accountNumber, @Nonnull String operation) {
        return getAPICountForPrefixForOperation(providerName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER + cloudName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER + accountNumber.replaceAll(DELIMITER_REGEX, "_") + DELIMITER + operation);
    }

    static public long getAPICountForOperationAcrossAccounts(@Nonnull String providerName, @Nonnull String cloudName, @Nonnull String operation) {
        String prefix = providerName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER + cloudName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER;
        long count = 0L;

        synchronized( operationApis ) {
            for( Map.Entry<String,Long> api : operationApis.entrySet() ) {
                if( api.getKey().startsWith(prefix) && api.getKey().endsWith(operation)) {
                    count += api.getValue();
                }
            }
        }
        return count;
    }

    static private long getAPICountForPrefixForOperation(@Nonnull String prefix) {
        long count = 0L;

        synchronized( operationApis ) {
            for( Map.Entry<String,Long> api : operationApis.entrySet() ) {
                if( api.getKey().startsWith(prefix) ) {
                    count += api.getValue();
                }
            }
        }
        return count;
    }

    static public long getOperationCount() {
        long count = 0L;

        synchronized( operationCount ) {
            for( Map.Entry<String,Long> api : operationCount.entrySet() ) {
                count += api.getValue();
            }
        }
        return count;
    }

    static public long getOperationCount(@Nonnull String providerName) {
        return getOperationCountForPrefix(providerName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER);
    }

    static public long getOperationCount(@Nonnull String providerName, @Nonnull String cloudName) {
        return getOperationCountForPrefix(providerName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER + cloudName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER);
    }

    static public long getOperationCount(@Nonnull String providerName, @Nonnull String cloudName, @Nonnull String accountNumber) {
        return getOperationCountForPrefix(providerName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER + cloudName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER + accountNumber.replaceAll(DELIMITER_REGEX, "_") + DELIMITER);
    }

    static public long getOperationCount(@Nonnull String providerName, @Nonnull String cloudName, @Nonnull String accountNumber, @Nonnull String operation) {
        return getOperationCountForPrefix(providerName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER + cloudName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER + accountNumber.replaceAll(DELIMITER_REGEX, "_") + DELIMITER + operation);
    }

    static public long getOperationCountAcrossAccounts(@Nonnull String providerName, @Nonnull String cloudName, @Nonnull String operation) {
        String prefix = providerName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER + cloudName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER;
        long count = 0L;

        synchronized( operationCount ) {
            for( Map.Entry<String,Long> api : operationCount.entrySet() ) {
                if( api.getKey().startsWith(prefix) && api.getKey().endsWith(operation)) {
                    count += api.getValue();
                }
            }
        }
        return count;
    }

    static private long getOperationCountForPrefix(@Nonnull String prefix) {
        long count = 0L;

        synchronized( operationCount ) {
            for( Map.Entry<String,Long> api : operationCount.entrySet() ) {
                if( api.getKey().startsWith(prefix) ) {
                    count += api.getValue();
                }
            }
        }
        return count;
    }

    static public @Nullable String getStackTrace(@Nonnull String providerName, @Nonnull String cloudName, @Nonnull String operationName) {

        CloudOperation operation = null;

        synchronized( operationTrace ) {
            for( Map.Entry<String,CloudOperation> entry : operationTrace.entrySet() ) {
                if( entry.getKey().startsWith(providerName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER + cloudName.replaceAll(DELIMITER_REGEX, "_") + DELIMITER) && entry.getKey().endsWith(DELIMITER + operationName) ) {
                    operation = entry.getValue();
                    break;
                }
            }
        }
        if( operation == null ) {
            return null;
        }
        return (new JSONObject(toJSON(operation))).toString();
    }

    static private @Nonnull Map<String,Object> toJSON(@Nonnull CloudOperation operation) {
        HashMap<String,Object> map = new HashMap<String, Object>();
        String[] parts = operation.name.split(DELIMITER_REGEX);
        String provider, cloud;
        String name;

        if( parts.length == 4 ) {
            provider = parts[0];
            cloud = parts[1];
            name = parts[3];
        }
        else if( parts.length > 4 ) {
            StringBuilder tmp = new StringBuilder();

            provider = parts[0];
            cloud = parts[1];
            for( int i=3; i<parts.length; i++ ) {
                tmp.append(parts[i]);
                if( i < parts.length-1 ) {
                    tmp.append(DELIMITER);
                }
            }
            name = tmp.toString();
        }
        else {
            provider = (parts.length > 0 ? parts[0] : "");
            cloud = (parts.length > 1 ? parts[1] : "");
            name = operation.name;
        }
        map.put("operation", name);
        map.put("provider", provider);
        map.put("cloud", cloud);
        map.put("apiCalls", operation.apiCalls == null ? new String[0] : operation.apiCalls);
        if( operation.endTimestamp > 0L ) {
            map.put("duration", operation.endTimestamp - operation.startTimestamp);
        }
        if( operation.priorChildren != null ) {
            ArrayList<Map<String,Object>> children = new ArrayList<Map<String, Object>>();

            for( CloudOperation child : operation.priorChildren ) {
                children.add(toJSON(child));
            }
            map.put("operationCalls", children);
        }
        else {
            map.put("operationCalls", new String[0]);
        }
        return map;
    }

    static public String[] listAccounts(@Nonnull String provider, @Nonnull String cloud) {
        provider = provider.replaceAll(DELIMITER_REGEX, "_");
        cloud = cloud.replaceAll(DELIMITER_REGEX, "_");
        TreeSet<String> list = new TreeSet<String>();

        synchronized( apiCount ) {
            for( String call : apiCount.keySet() ) {
                String[] parts = call.split(DELIMITER_REGEX);

                if( parts.length > 2 && parts[0].equals(provider) && parts[1].equals(cloud) ) {
                    list.add(parts[2]);
                }
            }
        }
        return list.toArray(new String[list.size()]);
    }

    static public String[] listApis(@Nonnull String provider, @Nonnull String cloud) {
        provider = provider.replaceAll(DELIMITER_REGEX, "_");
        cloud = cloud.replaceAll(DELIMITER_REGEX, "_");
        TreeSet<String> list = new TreeSet<String>();

        synchronized( apiCount ) {
            for( String call : apiCount.keySet() ) {
                String[] parts = call.split(DELIMITER_REGEX);

                if( parts.length > 3 && parts[0].equals(provider) && parts[1].equals(cloud) ) {
                    if( parts.length == 4 ) {
                        list.add(parts[3]);
                    }
                    else {
                        StringBuilder tmp = new StringBuilder();

                        for( int i=3; i<parts.length; i++ ) {
                            tmp.append(parts[i]);
                            if( i< parts.length-1 ) {
                                tmp.append(DELIMITER);
                            }
                        }
                        list.add(tmp.toString());
                    }
                }
            }
        }
        return list.toArray(new String[list.size()]);
    }

    static public String[] listClouds(@Nonnull String provider) {
        provider = provider.replaceAll(DELIMITER_REGEX, "_");
        TreeSet<String> list = new TreeSet<String>();

        synchronized( apiCount ) {
            for( String call : apiCount.keySet() ) {
                String[] parts = call.split(DELIMITER_REGEX);

                if( parts.length > 1 && parts[0].equals(provider) ) {
                    list.add(parts[1]);
                }
            }
        }
        return list.toArray(new String[list.size()]);
    }

    static public String[] listOperations(@Nonnull String provider, @Nonnull String cloud) {
        provider = provider.replaceAll(DELIMITER_REGEX, "_");
        cloud = cloud.replaceAll(DELIMITER_REGEX, "_");
        TreeSet<String> list = new TreeSet<String>();

        synchronized( operationCount ) {
            for( String call : operationCount.keySet() ) {
                String[] parts = call.split(DELIMITER_REGEX);

                if( parts.length > 3 && parts[0].equals(provider) && parts[1].equals(cloud) ) {
                    if( parts.length == 4 ) {
                        list.add(parts[3]);
                    }
                    else {
                        StringBuilder tmp = new StringBuilder();

                        for( int i=3; i<parts.length; i++ ) {
                            tmp.append(parts[i]);
                            if( i< parts.length-1 ) {
                                tmp.append(DELIMITER);
                            }
                        }
                        list.add(tmp.toString());
                    }
                }
            }
        }
        return list.toArray(new String[list.size()]);
    }

    static public String[] listProviders() {
        TreeSet<String> providers = new TreeSet<String>();

        synchronized( apiCount ) {
            for( String call : apiCount.keySet() ) {
                String[] parts = call.split(DELIMITER_REGEX);

                if( parts.length > 0 ) {
                    providers.add(parts[0]);
                }
            }
        }
        return providers.toArray(new String[providers.size()]);
    }

    static private void log(CloudOperation operation) {
        long count = count(operation);

        synchronized( operationApis ) {
            if( operationApis.containsKey(operation.name) ) {
                operationApis.put(operation.name, operationApis.get(operation.name) + count);
            }
            else {
                operationApis.put(operation.name, count);
            }
        }
        if( logger.isTraceEnabled() ) {
            operationTrace.put(operation.name, operation);
        }
    }

    static public void report(@Nonnull String prefix) {
        logger.info("");
        if( logger.isInfoEnabled() ) {
            synchronized( apiCount ) {
                TreeSet<String> keys = new TreeSet<String>();

                keys.addAll(apiCount.keySet());
                logger.debug(prefix + "-> API calls: ");
                for( String key : keys ) {
                    logger.debug(prefix + "->\t" + key + " = " + apiCount.get(key));
                }
            }
        }
        if( logger.isDebugEnabled() ) {
            synchronized( operationCount ) {
                TreeSet<String> keys = new TreeSet<String>();

                keys.addAll(operationCount.keySet());
                logger.debug(prefix + "-> Operation calls:");
                for( String key : keys ) {
                    logger.debug(prefix + "->\t" + key + " = " + operationCount.get(key));
                }
            }
            synchronized( operationApis ) {
                TreeSet<String> keys = new TreeSet<String>();

                keys.addAll(operationApis.keySet());
                logger.debug(prefix + "-> API calls by operation:");
                for( String key : keys ) {
                    logger.debug(prefix + "->\t" + key + " = " + operationApis.get(key));
                }
            }
        }
        if( logger.isTraceEnabled() ) {
            synchronized( operationTrace ) {
                TreeSet<String> keys = new TreeSet<String>();

                keys.addAll(operationTrace.keySet());
                logger.trace(prefix + "-> Stack trace:");
                for( String key : keys ) {
                    Map<String,Object> map = toJSON(operationTrace.get(key));

                    logger.trace((new JSONObject(map)).toString());
                    logger.trace("");
                }
            }
        }
        logger.info("");
    }

    static public void reset() {
        synchronized( apiCount ) {
            apiCount.clear();
        }
        synchronized( operationApis ) {
            operationApis.clear();
        }
        synchronized( operationCount ) {
            operationCount.clear();
        }
        synchronized( operationTrace ) {
            operationTrace.clear();
        }
        operations.clear();
    }

    static public void trace(@Nonnull CloudProvider provider,  @Nonnull String apiCall) {
        if( logger.isInfoEnabled() ) {
            ProviderContext ctx = provider.getContext();
            String accountNumber = getAccountNumber( ctx );
            String callName = provider.getProviderName().replaceAll(DELIMITER_REGEX, "_") + DELIMITER + provider.getCloudName().replaceAll(DELIMITER_REGEX, "_") + DELIMITER + accountNumber.replaceAll(DELIMITER_REGEX, "_") + DELIMITER + apiCall;

            try {
                CloudOperation current = null;

                if( logger.isDebugEnabled() ) {
                    long thread = Thread.currentThread().getId();

                    current = operations.get(thread);
                    if( current != null ) {
                        while( current.currentChild != null ) {
                            current = current.currentChild;
                        }
                        current.calls++;
                    }
                }
                synchronized( apiCount ) {
                    if( apiCount.containsKey(callName) ) {
                        apiCount.put(callName, apiCount.get(callName) + 1);
                    }
                    else {
                        apiCount.put(callName, 1L);
                    }
                }
                if( logger.isTraceEnabled() ) {
                    if( current != null ) {
                        if( current.apiCalls == null ) {
                            current.apiCalls = new ArrayList<String>();
                        }
                        current.apiCalls.add(apiCall);
                    }
                }
            }
            catch( Throwable t ) {
                logger.warn("Error with API trace trace: " + t.getMessage());
            }
        }
    }

    static public String getAccountNumber(@Nullable ProviderContext ctx) {
      return ((ctx == null || ctx.getAccountNumber() == null) ? "---" : ctx.getAccountNumber());
    }
}
