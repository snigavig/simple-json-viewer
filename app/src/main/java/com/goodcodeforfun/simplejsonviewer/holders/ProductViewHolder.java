package com.goodcodeforfun.simplejsonviewer.holders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.goodcodeforfun.simplejsonviewer.R;

/**
 * Created by snigavig on 14.07.16.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder {
    public final TextView tv_sku, tv_count;
    public final CardView cv_wrap;

    public ProductViewHolder(View view) {
        super(view);
        cv_wrap = (CardView) view.findViewById(R.id.cv_wrap);
        tv_sku = (TextView) view.findViewById(R.id.tv_sku);
        tv_count = (TextView) view.findViewById(R.id.tv_count);
    }
}