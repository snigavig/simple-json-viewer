package com.goodcodeforfun.simplejsonviewer.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.goodcodeforfun.simplejsonviewer.R;

/**
 * Created by snigavig on 14.07.16.
 */


public class TransactionViewHolder extends RecyclerView.ViewHolder {
    public final TextView tv_currency_global, tv_currency_local;

    public TransactionViewHolder(View view) {
        super(view);
        tv_currency_global = (TextView) view.findViewById(R.id.tv_currency_global);
        tv_currency_local = (TextView) view.findViewById(R.id.tv_currency_local);
    }
}