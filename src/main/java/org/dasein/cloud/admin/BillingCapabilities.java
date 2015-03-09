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

import java.util.Locale;

import javax.annotation.Nonnull;

import org.dasein.cloud.Capabilities;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;

/**
 * Describes the billing capabilities for accounts within a cloud.
 * 
 * @author David R Young (david.young@centurylink.com)
 */
public interface BillingCapabilities extends Capabilities {

    /**
     * Indicates whether or not billing functions are supported by this cloud.
     * 
     * @return true if billing functions are supported, false if otherwise
     * @throws CloudException
     *             the cloud provider encountered an error while processing the
     *             request
     * @throws InternalException
     *             a Dasein Cloud error occurred while processing the request
     */
    public boolean supportsBilling() throws InternalException, CloudException;

    /**
     * Indicates whether or not this cloud can provide account-level estimates
     * for the current month.
     * 
     * @return true if estimates can be retrieved, false if otherwise
     * @throws CloudException
     *             the cloud provider encountered an error while processing the
     *             request
     * @throws InternalException
     *             a Dasein Cloud error occurred while processing the request
     */
    public boolean supportEstimation() throws InternalException, CloudException;

    /**
     * Assists UIs by providing the cloud-specific term for an invoice in the
     * cloud.
     * 
     * @param locale
     *            the locale for which the term should be translated
     * @return the provider-specific term for an invoice
     */
    public @Nonnull
    String getProviderTermForInvoice(Locale locale);
}
