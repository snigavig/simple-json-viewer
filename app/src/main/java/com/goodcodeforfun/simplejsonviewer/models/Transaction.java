package com.goodcodeforfun.simplejsonviewer.models;

/**
 * Created by snigavig on 14.07.16.
 */

public class Transaction {
    private String sku;
    private Double amount;
    private Double amountGlobal;
    private String currency;

    public Transaction() {
    }

    public String getSku() {
        return sku;
    }

    public Double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmountGlobal() {
        return amountGlobal;
    }

    public void setAmountGlobal(Double amountGlobal) {
        this.amountGlobal = amountGlobal;
    }
}
