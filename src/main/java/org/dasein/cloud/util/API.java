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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Implements the API JMX interface to provide access to data about Dasein Cloud interaction with underlying cloud
 * APIs.
 * <p>Created by George Reese: 11/17/12 9:55 AM</p>
 * @author George Reese
 * @version 2013.01 initial version (Issue #1)
 * @since 2013.01
 */
public class API implements APIMBean {
    @Override
    public @Nonnull String[] getAccounts(@Nonnull String provider, @Nonnull String cloud) {
        return APITrace.listAccounts(provider, cloud);
    }

    @Override
    public @Nonnull String[] getApis(@Nonnull String provider, @Nonnull String cloud) {
        return APITrace.listApis(provider, cloud);
    }

    @Override
    public long getCallCountByAccount(@Nonnull String provider, @Nonnull String cloud, @Nonnull String account) {
        return APITrace.getAPICount(provider, cloud, account);
    }

    @Override
    public long getCallCountByAccountApi(@Nonnull String provider, @Nonnull String cloud, @Nonnull String account, @Nonnull String api) {
        return APITrace.getAPICount(provider, cloud, account, api);
    }

    @Override
    public long getCallCountByAccountOperation(@Nonnull String provider, @Nonnull String cloud, @Nonnull String account, @Nonnull String operation) {
        return APITrace.getAPICountForOperation(provider, cloud, account, operation);
    }

    @Override
    public long getCallCountByApi(@Nonnull String provider, @Nonnull String cloud, @Nonnull String api) {
        return APITrace.getAPICountAcrossAccounts(provider, cloud, api);
    }

    @Override
    public long getCallCountByOperation(@Nonnull String provider, @Nonnull String cloud, @Nonnull String operation) {
        return APITrace.getAPICountForOperationAcrossAccounts(provider, cloud, operation);
    }

    @Override
    public @Nonnull String[] getClouds(@Nonnull String provider) {
        return APITrace.listClouds(provider);
    }

    @Override
    public long getOperationInvocationCount(@Nonnull String provider, @Nonnull String cloud, @Nonnull String operation) {
        return APITrace.getOperationCountAcrossAccounts(provider, cloud, operation);
    }

    @Override
    public long getOperationInvocationCountInAccount(@Nonnull String provider, @Nonnull String cloud, @Nonnull String account, @Nonnull String operation) {
        return APITrace.getOperationCount(provider, cloud, account, operation);
    }

    @Override
    public @Nonnull String[] getOperations(@Nonnull String provider, @Nonnull String cloud) {
        return APITrace.listOperations(provider, cloud);
    }

    @Override
    public @Nonnull String[] getProviders() {
        return APITrace.listProviders();
    }

    @Override
    public @Nullable String getStackTrace(@Nonnull String provider, @Nonnull String cloud, @Nonnull String operation) {
        return APITrace.getStackTrace(provider, cloud, operation);
    }

    @Override
    public void report(@Nonnull String prefix) {
        APITrace.report(prefix);
    }

    @Override
    public void reset() {
        APITrace.reset();
    }
}
