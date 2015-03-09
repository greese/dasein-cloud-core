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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.dasein.cloud.Tag;

/**
 * <p>
 * Generic header information for an invoice, only relating to charges
 * accrued within the billing period.
 * </p>
 * 
 * @author David R Young (david.young@centurylink.com)
 */
public class InvoiceHeader {
    private String accountId;
    private String invoiceId;
    private Date invoiceDate;
    private String summary;
    private float totalDebit;
    private float totalCredit;
    private Map<String, String> tags;

    public InvoiceHeader() {
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public synchronized Map<String, String> getTags() {
        if (tags == null) {
            tags = new HashMap<String, String>();
        }
        return tags;
    }

    public void setTag(@Nonnull String key, @Nonnull String value) {
        if (tags == null) {
            tags = new HashMap<String, String>();
        }
        tags.put(key, value);
    }

    public synchronized void setTags(Map<String, String> properties) {
        getTags().clear();
        getTags().putAll(properties);
    }

    public void addTag(Tag t) {
        addTag(t.getKey(), t.getValue());
    }

    public void addTag(String key, String value) {
        getTags().put(key, value);
    }

    public float getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(float totalCredit) {
        this.totalCredit = totalCredit;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public float getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(float totalDebit) {
        this.totalDebit = totalDebit;
    }
}
