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

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.dasein.cloud.Tag;
import org.dasein.cloud.dc.DataCenter;

/**
 * <p>
 * An account container within a cloud. For clouds that support account
 * hierarchies, accounts can be stacked using parentId.
 * </p>
 * 
 * @author David R Young (david.young@centurylink.com)
 */
public class Account {
    private String accountId;
    private AccountState state;
    private String parentId;
    private DataCenter primaryDataCenter;
    private String name;
    private String address1;
    private String address2;
    private String city;
    private String stateProvince;
    private String postalCode;
    private String country;
    private String telephoneNumber;
    private String faxNumber;
    private String timezone;
    private Map<String, String> tags;
    private String currency;

    public Account() {
    }

    @Override
    public boolean equals(Object ob) {
        if (ob == null) {
            return false;
        }
        if (ob == this) {
            return true;
        }
        if (!getClass().getName().equals(ob.getClass().getName())) {
            return false;
        }
        Account other = (Account) ob;

        return getAccountId().equals(other.getAccountId());
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public DataCenter getPrimaryDataCenter() {
        return primaryDataCenter;
    }

    public void setPrimaryDataCenter(DataCenter primaryDataCenter) {
        this.primaryDataCenter = primaryDataCenter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
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

    public AccountState getState() {
        return state;
    }

    public void setState(AccountState state) {
        this.state = state;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
