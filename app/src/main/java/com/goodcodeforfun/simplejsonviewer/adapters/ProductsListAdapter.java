package com.goodcodeforfun.simplejsonviewer.adapters;

import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodcodeforfun.simplejsonviewer.R;
import com.goodcodeforfun.simplejsonviewer.SimpleJSONViewerApplication;
import com.goodcodeforfun.simplejsonviewer.activities.DetailsActivity;
import com.goodcodeforfun.simplejsonviewer.holders.ProductViewHolder;
import com.goodcodeforfun.simplejsonviewer.models.Product;

import java.util.ArrayList;

/**
 * Created by snigavig on 14.07.16.
 */

public class ProductsListAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    public static final String SELECTED_SKU_KEY = "SELECTED_SKU";
    private ArrayList<Product> products;

    public void resetProducts() {
        this.products = null;
    }

    public ProductsListAdapter(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_item_view, viewGroup, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder viewHolder, int i) {
        final String sku = products.get(i).getSku();
        viewHolder.cv_wrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent(view.getContext(), DetailsActivity.class);
                resultIntent.putExtra(SELECTED_SKU_KEY, sku);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(view.getContext());
                stackBuilder.addParentStack(DetailsActivity.class);
                stackBuilder.addNextIntent(resultIntent);
                stackBuilder.startActivities();
            }
        });
        viewHolder.tv_sku.setText(sku);
        viewHolder.tv_count.setText(
                String.format(
                        SimpleJSONViewerApplication.getInstance().getString(R.string.transaction_count),
                        String.valueOf(products.get(i).getCount())
                )
        );
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
