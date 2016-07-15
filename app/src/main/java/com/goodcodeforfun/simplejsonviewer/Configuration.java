package com.goodcodeforfun.simplejsonviewer;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by snigavig on 15.07.16.
 */

public class Configuration {
    public static final String GLOBAL_CURRENCY = "GBP";
    public static final String CURRENCY_PATTERN = "%1$s%2$,.2f";
    public static final String SKU_JSON_OBJECT_NAME = "sku";
    public static final String AMOUNT_JSON_OBJECT_NAME = "amount";
    public static final String CURRENCY_JSON_OBJECT_NAME = "currency";
    public static final String FROM_JSON_OBJECT_NAME = "from";
    public static final String RATE_JSON_OBJECT_NAME = "rate";
    public static final String TO_JSON_OBJECT_NAME = "to";
    public static final String RATES_JSON_FILE_NAME = "rates.json";
    public static final String TRANSACTIONS_JSON_FILE_NAME = "transactions.json";
    public static final NumberFormat GLOBAL_NUMBER_FORMATTER = NumberFormat.getCurrencyInstance(Locale.UK);
}
