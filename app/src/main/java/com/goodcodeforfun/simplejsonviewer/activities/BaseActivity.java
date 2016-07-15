package com.goodcodeforfun.simplejsonviewer.activities;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.goodcodeforfun.simplejsonviewer.R;
import com.goodcodeforfun.simplejsonviewer.SimpleJSONViewerApplication;
import com.goodcodeforfun.simplejsonviewer.models.Transaction;
import com.goodcodeforfun.simplejsonviewer.utils.JSONUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static com.goodcodeforfun.simplejsonviewer.Configuration.AMOUNT_JSON_OBJECT_NAME;
import static com.goodcodeforfun.simplejsonviewer.Configuration.CURRENCY_JSON_OBJECT_NAME;
import static com.goodcodeforfun.simplejsonviewer.Configuration.SKU_JSON_OBJECT_NAME;
import static com.goodcodeforfun.simplejsonviewer.Configuration.TRANSACTIONS_JSON_FILE_NAME;

public abstract class BaseActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONArray> {

    private static final String LOG_TAG = "BaseActivity";
    private static final int TRANSACTIONS_LOADER_ID = 0;
    private static final int CURRENCY_CODE_LENGTH = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimpleJSONViewerApplication.getInstance().initCurrencyConverter(this);
        getSupportLoaderManager().initLoader(TRANSACTIONS_LOADER_ID, null, this);
    }

    @Override
    protected void onResume() {
        if (!getSupportLoaderManager().hasRunningLoaders())
            getSupportLoaderManager().restartLoader(TRANSACTIONS_LOADER_ID, null, this);
        super.onResume();
    }

    @Override
    public Loader<JSONArray> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<JSONArray>(this) {

            private JSONArray jsonArray;

            @Override
            protected void onStartLoading() {
                onProgress();
                if (jsonArray == null) {
                    forceLoad();
                } else {
                    deliverResult(jsonArray);
                }
            }

            @Override
            public void deliverResult(JSONArray jsonArray) {
                if (jsonArray != null)
                    this.jsonArray = jsonArray;
                super.deliverResult(jsonArray);
            }

            @Override
            public JSONArray loadInBackground() {
                try {
                    return JSONUtils.getJSONFromFile(TRANSACTIONS_JSON_FILE_NAME);
                } catch (JSONUtils.EmptyJSONFileException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(
                                    SimpleJSONViewerApplication.getInstance(),
                                    getString(R.string.empty_file_error),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                    Log.e(LOG_TAG, e.getLocalizedMessage());
                }
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<JSONArray> loader, JSONArray data) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        if (data != null) {
            try {
                for (int i = 0; i < data.length(); i++) {
                    String sku = String.valueOf(data.getJSONObject(i).get(SKU_JSON_OBJECT_NAME));
                    Double amount = JSONUtils.parseDouble(String.valueOf(data.getJSONObject(i).get(AMOUNT_JSON_OBJECT_NAME)));
                    String currency = String.valueOf(data.getJSONObject(i).get(CURRENCY_JSON_OBJECT_NAME));
                    if (null != sku && null != currency && currency.length() == CURRENCY_CODE_LENGTH){
                        Transaction transaction = new Transaction();
                        transaction.setSku(sku);
                        transaction.setAmount(amount);
                        transaction.setCurrency(currency);
                        transactions.add(transaction);
                    }
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getLocalizedMessage());
            }
        }
        processTransactions(transactions);
        onProgressStop();
    }

    protected abstract void onProgress();

    protected abstract void onProgressStop();

    protected abstract void processTransactions(ArrayList<Transaction> data);

    protected abstract void resetAdapter();

    @Override
    public void onLoaderReset(Loader<JSONArray> loader) {
        resetAdapter();
    }
}
