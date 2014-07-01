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
