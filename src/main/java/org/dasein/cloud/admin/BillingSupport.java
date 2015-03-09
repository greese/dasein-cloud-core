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

package org.dasein.cloud.admin;

import java.util.Collection;
import java.util.Date;

import javax.annotation.Nonnull;

import org.dasein.cloud.AccessControlledService;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.identity.ServiceAction;

/**
 * Provides interaction with the account hierarchy functions for the cloud.
 * 
 * @author David R Young (david.young@centurylink.com)
 */
public interface BillingSupport extends AccessControlledService {
    static public final ServiceAction ANY = new ServiceAction("BILL:ANY");

    static public final ServiceAction GET_ACCT_ESTIMATE = new ServiceAction("BILL:GET_ACCT_ESTIMATE");
    static public final ServiceAction GET_BALANCE = new ServiceAction("BILL:GET_BALANCE");
    static public final ServiceAction GET_BILL_CYCLE_DAY = new ServiceAction("BILL:GET_BILL_CYCLE_DAY");
    static public final ServiceAction GET_INVOICE = new ServiceAction("BILL:GET_INVOICE");
    static public final ServiceAction LIST_BILLING_HIST = new ServiceAction("BILL:LIST_BILLING_HIST");

    /**
     * Provides access to meta-data about billing capabilities in the current
     * region of this cloud.
     * 
     * @return a description of the features supported for accounts within this
     *         cloud
     * @throws InternalException
     *             an error occurred within the Dasein Cloud API implementation
     * @throws CloudException
     *             an error occurred within the cloud provider
     */
    public @Nonnull
    BillingCapabilities getCapabilities() throws InternalException, CloudException;

    /**
     * Provides a list of invoices for the specified account. If retrieving
     * invoices are not supported in this cloud, the list will be empty.
     * 
     * @param accountId
     *            the account id to list invoices for
     * @return all invoices for the given account
     * @throws InternalException
     *             an error occurred within the Dasein Cloud API implementation
     * @throws CloudException
     *             an error occurred within the cloud provider
     */
    public Iterable<InvoiceHeader> listBillingHistory(String accountId) throws InternalException, CloudException;

    /**
     * Provides a list of invoices for the specified account since the supplied
     * date. If retreiving invoices is not supported in this cloud, the list
     * will be empty.
     * 
     * @param accountId
     *            the account id to list invoices for
     * @param date
     *            return all invoices since this date
     * @return all invoices for the account since the given date
     * @throws InternalException
     *             an error occurred within the Dasein Cloud API implementation
     * @throws CloudException
     *             an error occurred within the cloud provider
     */
    public Iterable<InvoiceHeader> listBillingHistory(String accountId, Date date) throws InternalException,
            CloudException;

    /**
     * Provides the data for a specific invoice.
     * 
     * @param invoiceId
     *            the invoice id to get details for
     * @return line item data for the given invoice
     * @throws InternalException
     *             an error occurred within the Dasein Cloud API implementation
     * @throws CloudException
     *             an error occurred within the cloud provider
     */
    public Invoice getInvoice(String invoiceId) throws InternalException, CloudException;

    /**
     * Provides current month charge estimates for the specific account. For
     * clouds that do not provide estimates, this method should return an empty
     * {@link BillingEstimates} object and NOT <code>null</code> .
     * 
     * @param accountId
     *            the account id retrieve estimates for
     * @return current month estimates for the given account
     * @throws InternalException
     *             an error occurred within the Dasein Cloud API implementation
     * @throws CloudException
     *             an error occurred within the cloud provider
     */
    public BillingEstimates getAccountEstimates(String accountId) throws InternalException, CloudException;

    /**
     * Provides the day of the month in which the account's billing cycle
     * starts. It can then be assumed the billing cycle ends on the previous
     * day.
     * 
     * @param accountId
     *            the account id to retrieve the billing cycle details
     * @return the day of the month the billing starts
     * @throws InternalException
     *             an error occurred within the Dasein Cloud API implementation
     * @throws CloudException
     *             an error occurred within the cloud provider
     */
    public int getBillingCycleDay(String accountId) throws InternalException, CloudException;

    /**
     * Provides the current outstanding balance for the specific account.
     * 
     * @param accountId
     *            the account id to retrieve the balance for
     * @return the current balance in the account's local currency
     * @throws InternalException
     *             an error occurred within the Dasein Cloud API implementation
     * @throws CloudException
     *             an error occurred within the cloud provider
     */
    public float getBalance(String accountId) throws InternalException, CloudException;
}
