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

/**
 * <p>
 * Billing estimates for a given account for the current month only. Any charges
 * or balance impacting events prior to the current month should be represented
 * in an invoice.
 * </p>
 * 
 * @author David R Young (david.young@centurylink.com)
 */
public class BillingEstimates {
    private String accountId;
    private double oneTimeCharges;
    private double monthToDateCharges;
    private double monthToDateTotal;
    private double fullMonthEstimate;
    private double currentHour;
    private double previousHour;

    public BillingEstimates() {
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getOneTimeCharges() {
        return oneTimeCharges;
    }

    public void setOneTimeCharges(double oneTimeCharges) {
        this.oneTimeCharges = oneTimeCharges;
    }

    public double getMonthToDateCharges() {
        return monthToDateCharges;
    }

    public void setMonthToDateCharges(double monthToDateCharges) {
        this.monthToDateCharges = monthToDateCharges;
    }

    public double getMonthToDateTotal() {
        return monthToDateTotal;
    }

    public void setMonthToDateTotal(double monthToDateTotal) {
        this.monthToDateTotal = monthToDateTotal;
    }

    public double getFullMonthEstimate() {
        return fullMonthEstimate;
    }

    public void setFullMonthEstimate(double fullMonthEstimate) {
        this.fullMonthEstimate = fullMonthEstimate;
    }

    public double getCurrentHour() {
        return currentHour;
    }

    public void setCurrentHour(double currentHour) {
        this.currentHour = currentHour;
    }

    public double getPreviousHour() {
        return previousHour;
    }

    public void setPreviousHour(double previousHour) {
        this.previousHour = previousHour;
    }
}
