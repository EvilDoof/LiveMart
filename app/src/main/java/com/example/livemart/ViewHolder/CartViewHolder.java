package com.example.livemart.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livemart.Interface.ItemClickListner;
import com.example.livemart.R;

public class CartViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtProductName, txtProductPrice, txtProductQuantity,txtProductStock;
    private ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtProductPrice = itemView.findViewById(R.id.cart_product_price);
        txtProductQuantity = itemView.findViewById(R.id.cart_product_quantity);
        txtProductStock = itemView.findViewById(R.id.cart_product_stock);
    }

    @Override
    public void onClick(View view) {
        itemClickListner.onClick(view, getAdapterPosition(), false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner)
    {
        this.itemClickListner = itemClickListner;
    }

}
