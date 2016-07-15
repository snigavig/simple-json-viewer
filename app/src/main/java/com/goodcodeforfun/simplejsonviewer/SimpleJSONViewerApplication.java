package com.goodcodeforfun.simplejsonviewer;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.goodcodeforfun.simplejsonviewer.utils.CurrencyConverter;

/**
 * Created by snigavig on 15.07.16.
 */

public class SimpleJSONViewerApplication extends Application {
    private static CurrencyConverter mCurrencyConverter;
    private static SimpleJSONViewerApplication mInstance;

    public SimpleJSONViewerApplication() {
        super();
    }

    public static SimpleJSONViewerApplication getInstance() {
        return mInstance;
    }

    public CurrencyConverter getCurrencyConverter() {
        return mCurrencyConverter;
    }

    public void initCurrencyConverter(AppCompatActivity activity) {
        mCurrencyConverter = new CurrencyConverter(activity);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
