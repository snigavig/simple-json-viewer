package com.goodcodeforfun.simplejsonviewer.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.goodcodeforfun.simplejsonviewer.R;
import com.goodcodeforfun.simplejsonviewer.adapters.ProductsListAdapter;
import com.goodcodeforfun.simplejsonviewer.models.Product;
import com.goodcodeforfun.simplejsonviewer.models.Transaction;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private ProductsListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
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
        ArrayList<Product> products = new ArrayList<>();
        String transactionSku;
        for (Transaction transaction : data) {
            transactionSku = transaction.getSku();
            boolean isProductInitialized = false;
            Product currentProduct = null;
            for(Product product : products){
                if(product.getSku() != null && product.getSku().contains(transactionSku)){
                    isProductInitialized = true;
                    currentProduct = product;
                }
            }

            if (isProductInitialized) {
                currentProduct.incrementCount();
            } else {
                Product product = new Product();
                product.setSku(transactionSku);
                products.add(product);
            }
        }
        mAdapter = new ProductsListAdapter(products);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void resetAdapter() {
        mAdapter.resetProducts();
    }
}
