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

import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.ProviderContext;
import org.dasein.cloud.compute.VirtualMachineSupport;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Interface defining the mechanisms for interacting with any Dasein Cloud deployment using JMX
 * conventions. To properly leverage this interface, it's important to know several terms:
 * <ul>
 *     <li>provider - the name of the provider as specified in {@link CloudProvider#getProviderName()}</li>
 *     <li>cloud - the name of the cloud as specified in {@link CloudProvider#getCloudName()}</li>
 *     <li>account - the account number as specified in {@link ProviderContext#getAccountNumber()}</li>
 *     <li>api/call - a specific SOAP or REST API call to the cloud provider</li>
 *     <li>operation - a Dasein Cloud function like {@link VirtualMachineSupport#listVirtualMachines()}
 *     that may be realized by 0 or more API calls</li>
 * </ul>
 * <p>
 *     For all of this to work, the log4j logging level for {@link APITrace} must be set to TRACE, DEBUG, or INFO
 *     depending on the level of information you want. Note that TRACE is very expensive, DEBUG is somewhat expensive,
 *     and INFO is largely innocuous. Unless otherwise specified, these methods require DEBUG.
 * </p>
 * <p>Created by George Reese: 11/17/12 9:55 AM</p>
 * @author George Reese
 * @version 2013.01 initial version (Issue #1)
 * @since 2013.01
 */
public interface APIMBean {
    /**
     * Lists all accounts in the specified cloud that have API calls associated with them. This method works when
     * log4j is set to INFO.
     * @param provider the provider for the cloud
     * @param cloud the name of the cloud
     * @return a list of accounts in the target cloud for which at least one API call has been made
     */
    public @Nonnull String[] getAccounts(@Nonnull String provider, @Nonnull String cloud);

    /**
     * Lists all API calls that have had at least one call made against them. This method works when log4j is set to
     * INFO.
     * @param provider the provider of the cloud
     * @param cloud the name of the cloud
     * @return a list of API calls with at least one call made to it
     */
    public @Nonnull String[] getApis(@Nonnull String provider, @Nonnull String cloud);

    /**
     * Provides the total number of API calls that have been made on behalf of the specified account. This method is
     * available with log4j set to INFO.
     * @param provider the provider of the cloud
     * @param cloud the name of the cloud
     * @param account the account number of the account to count
     * @return the total number of API calls that have been made on behalf of the specified account
     */
    public @Nonnegative long getCallCountByAccount(@Nonnull String provider, @Nonnull String cloud, @Nonnull String account);

    /**
     * The total number of calls from the specified account to the target API. This method works when log4j is set to INFO.
     * @param provider the provider of the cloud
     * @param cloud the name of the cloud
     * @param account the account number under which the target API call has been made
     * @param api the API call to count
     * @return the total number of calls against this API for this account
     */
    public @Nonnegative long getCallCountByAccountApi(@Nonnull String provider, @Nonnull String cloud, @Nonnull String account, @Nonnull String api);

    /**
     * The total number of API calls made in support of a specific Dasein operation for the specified account. For example,
     * if {@link VirtualMachineSupport#listVirtualMachines()} requires API calls and 10 calls have been made to
     * {@link VirtualMachineSupport#listVirtualMachines()}, this method will return 20.
     * @param provider the provider of the cloud
     * @param cloud the name of the cloud
     * @param account the account number under which the target operation has been made
     * @param operation the operation whose API calls you are counting
     * @return the total number of API calls made in support of the specified operation in the specified account context
     */
    public @Nonnegative long getCallCountByAccountOperation(@Nonnull String provider, @Nonnull String cloud, @Nonnull String account, @Nonnull String operation);

    /**
     * The total number of calls made against a specific API across all accounts in a cloud. This method works when
     * log4j is set to INFO.
     * @param provider the cloud provider
     * @param cloud the name of the cloud
     * @param api the name of the API call to be counted
     * @return the total number of calls against the API call across all accounts in a cloud
     */
    public @Nonnegative long getCallCountByApi(@Nonnull String provider, @Nonnull String cloud, @Nonnull String api);

    /**
     * The total number of API calls made in support of a specific Dasein Cloud operation across all accounts in a cloud.
     * @param provider the cloud provider
     * @param cloud the name of the cloud
     * @param operation the operation whose API calls are being counted
     * @return the total number of API calls made in support of a specific operation across all accounts in a cloud
     */
    public @Nonnegative long getCallCountByOperation(@Nonnull String provider, @Nonnull String cloud, @Nonnull String operation);

    /**
     * A list of clouds associated with a specific provider. This method works when log4j is set to INFO.
     * @param provider the name of the provider whose clouds you are listing
     * @return the list of clouds associated with the provider
     */
    public @Nonnull String[] getClouds(@Nonnull String provider);

    /**
     * The total number of times a specific Dasein Cloud operation has been invoked.
     * @param provider the cloud provider
     * @param cloud the name of the cloud
     * @param operation the name of the operation to be counted
     * @return the total number of times this operation has been invoked
     */
    public @Nonnegative long getOperationInvocationCount(@Nonnull String provider, @Nonnull String cloud, @Nonnull String operation);

    /**
     * The total number of times a specific Dasein Cloud operation has been invoked in support of a specific account.
     * @param provider the cloud provider
     * @param cloud the name of the cloud
     * @param account the account number to which the count should be restricted
     * @param operation the name of the operation to be counted
     * @return the total number of times a specific operation has been invoked in the specified account context
     */
    public @Nonnegative long getOperationInvocationCountInAccount(@Nonnull String provider, @Nonnull String cloud, @Nonnull String account, @Nonnull String operation);

    /**
     * A list of operations with at least one invocation in the target cloud.
     * @param provider the cloud provider
     * @param cloud the name of the cloud
     * @return the list of operations with at least one invocation in the target cloud
     */
    public @Nonnull String[] getOperations(@Nonnull String provider, @Nonnull String cloud);

    /**
     * A list of all providers that have had calls made against them. This method works when log4j is set to INFO.
     * @return the list of providers with at least one call made against them
     */
    public @Nonnull String[] getProviders();

    /**
     * Provides a full API stack trace for the last invocation of the specified operation along with all child operations
     * it triggered as a JSON object. Not the same thing as a Java stack trace. This method provides useful data
     * only when the log4j level is set to TRACE. WARNING: Setting to TRACE is very expensive.
     * @param provider the cloud provider
     * @param cloud the name of the cloud
     * @param operation the name of the operation for which a stack trace is being fetched
     * @return JSON outlining the stack trace associated with the specified operation
     */
    public @Nullable String getStackTrace(@Nonnull String provider, @Nonnull String cloud, @Nonnull String operation);

    /**
     * Sends a report via log4j to the log class associated with {@link APITrace}. This method works when log4j is
     * set to INFO.
     * @param prefix a prefix to prepend to report entries
     */
    public void report(@Nonnull String prefix);

    /**
     * Resets all counters to zero. This method works when log4j is set to INFO.
     */
    public void reset();
}
