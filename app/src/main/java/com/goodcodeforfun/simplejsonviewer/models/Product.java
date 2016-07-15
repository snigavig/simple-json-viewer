package com.goodcodeforfun.simplejsonviewer.models;

/**
 * Created by snigavig on 14.07.16.
 */

public class Product {
    private String sku;
    private int count;

    public Product() {
        this.count = 0;
        incrementCount();
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getCount() {
        return count;
    }

    public void incrementCount() {
        this.count++;
    }
}
