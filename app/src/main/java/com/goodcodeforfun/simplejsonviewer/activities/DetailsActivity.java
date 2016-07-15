package com.goodcodeforfun.simplejsonviewer.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.goodcodeforfun.simplejsonviewer.R;
import com.goodcodeforfun.simplejsonviewer.SimpleJSONViewerApplication;
import com.goodcodeforfun.simplejsonviewer.adapters.ProductsListAdapter;
import com.goodcodeforfun.simplejsonviewer.adapters.TransactionListAdapter;
import com.goodcodeforfun.simplejsonviewer.models.Transaction;

import java.util.ArrayList;

import static com.goodcodeforfun.simplejsonviewer.Configuration.GLOBAL_NUMBER_FORMATTER;

public class DetailsActivity extends BaseActivity {

    private TransactionListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private TextView mTotalTextView;
    private ProgressBar mProgressBar;
    private String mSkuName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_details);
        mSkuName = getIntent().getStringExtra(ProductsListAdapter.SELECTED_SKU_KEY);
        setTitle(String.format(getString(R.string.details_activity_name), mSkuName));
        mTotalTextView = (TextView) findViewById(R.id.tv_total);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mRecyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onProgressStop() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void processTransactions(ArrayList<Transaction> data) {
        ArrayList<Transaction> filteredData = new ArrayList<>();
        String transactionSku = mSkuName;
        Double amountTotal = 0.0d;
        for (Transaction transaction : data) {
            if (transaction.getSku() != null && transaction.getSku().contains(transactionSku)) {
                transaction.setAmountGlobal(SimpleJSONViewerApplication
                        .getInstance()
                        .getCurrencyConverter()
                        .convertToGlobal(transaction.getCurrency(), transaction.getAmount()));
                filteredData.add(transaction);
                amountTotal += transaction.getAmountGlobal();
            }
        }
        mTotalTextView.setText(String.format(
                getString(R.string.details_activity_total),
                GLOBAL_NUMBER_FORMATTER.format(amountTotal))
        );
        mAdapter = new TransactionListAdapter(filteredData);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void resetAdapter() {
        mAdapter.resetTransactions();
    }
}
