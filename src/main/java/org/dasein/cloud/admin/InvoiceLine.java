package org.dasein.cloud.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.dasein.cloud.dc.DataCenter;

/**
 * <p>
 * Generic information for an invoice line. It contains details about
 * quantities, unit costs, service locations, and line break-outs in the case of
 * summarized charges.
 * </p>
 * 
 * @author David R Young (david.young@centurylink.com)
 */
public class InvoiceLine {
    private int quantity;
    private String description;
    private double unitCost;
    private double tax;
    private double itemTotal;
    private DataCenter serviceLocation;
    private Map<String, Double> breakouts;
    
    public InvoiceLine() {
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(double itemTotal) {
        this.itemTotal = itemTotal;
    }

    public DataCenter getServiceLocation() {
        return serviceLocation;
    }

    public void setServiceLocation(DataCenter serviceLocation) {
        this.serviceLocation = serviceLocation;
    }

    public synchronized Map<String, Double> getBreakouts() {
        if (breakouts == null) {
            breakouts = new HashMap<String, Double>();
        }
        return breakouts;
    }

    public void setBreakout(@Nonnull String description, @Nonnull Double charge) {
        if (breakouts == null) {
            breakouts = new HashMap<String, Double>();
        }
        breakouts.put(description, charge);
    }

    public synchronized void setTags(Map<String, Double> breakouts) {
        getBreakouts().clear();
        getBreakouts().putAll(breakouts);
    }

    public void addBreakout(@Nonnull String description, @Nonnull Double charge) {
        getBreakouts().put(description, charge);
    }
}
