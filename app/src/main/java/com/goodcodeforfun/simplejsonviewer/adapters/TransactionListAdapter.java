package com.goodcodeforfun.simplejsonviewer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodcodeforfun.simplejsonviewer.R;
import com.goodcodeforfun.simplejsonviewer.holders.TransactionViewHolder;
import com.goodcodeforfun.simplejsonviewer.models.Transaction;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

import static com.goodcodeforfun.simplejsonviewer.Configuration.CURRENCY_PATTERN;
import static com.goodcodeforfun.simplejsonviewer.Configuration.GLOBAL_NUMBER_FORMATTER;

/**
 * Created by snigavig on 14.07.16.
 */

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionViewHolder> {
    private ArrayList<Transaction> transactionArrayList;

    public void resetTransactions() {
        this.transactionArrayList = null;
    }


    public TransactionListAdapter(ArrayList<Transaction> transactionArrayList) {
        this.transactionArrayList = transactionArrayList;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transaction_item_view, viewGroup, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder viewHolder, int i) {
        double amount = transactionArrayList.get(i).getAmount();
        double amountGlobal = transactionArrayList.get(i).getAmountGlobal();
        String currency = transactionArrayList.get(i).getCurrency();
        viewHolder.tv_currency_local.setText(
                String.format(
                        Locale.getDefault(),
                        CURRENCY_PATTERN,
                        Currency.getInstance(currency).getSymbol(),
                        amount
                ));

        viewHolder.tv_currency_global.setText(GLOBAL_NUMBER_FORMATTER.format(amountGlobal));
    }

    @Override
    public int getItemCount() {
        return transactionArrayList.size();
    }
}
