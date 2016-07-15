package com.goodcodeforfun.simplejsonviewer.models;

/**
 * Created by snigavig on 15.07.16.
 */

public class ExchangeRate {

    private String from;
    private Double rate;
    private String to;


    public ExchangeRate() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
