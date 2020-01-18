package com.example.salonproduct.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salonproduct.Activities.Show_Category_Products;
import com.example.salonproduct.Activities.View_Cart;
import com.example.salonproduct.Model.Cart_Model;
import com.example.salonproduct.Model.Categre_model;
import com.example.salonproduct.R;

import java.util.ArrayList;

import static com.example.salonproduct.R.layout.item_categree;
import static com.example.salonproduct.R.layout.view_cart_item;

public class View_Cart_Adapter extends RecyclerView.Adapter<View_Cart_Adapter.MyviewHolder> {
    ArrayList<Cart_Model> cart_models;
    //Context context;
    View_Cart view_cart;


    public View_Cart_Adapter(View_Cart view_cart, ArrayList<Cart_Model> cart_models) {
        this.cart_models = cart_models;

        this.view_cart = view_cart;

    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(view_cart_item, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyviewHolder holder, final int position) {


        holder.product_name.setText(cart_models.get(position).getProduct_name());
        holder.product_price.setText("Price :" + cart_models.get(position).getProduct_price());
        holder.product_total_price.setText("Total Price :" + cart_models.get(position).getProduct_total_price());
        holder.product_quanity.setText(String.valueOf(cart_models.get(position).getProduct_quantity()));




        holder.inc_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart_models.get(position).setProduct_quantity(cart_models.get(position).getProduct_quantity() + 1);

                cart_models.get(position).setProduct_total_price(cart_models.get(position).getProduct_price()  * cart_models.get(position).getProduct_quantity());
                view_cart.setDataToCart_RecylerView(cart_models);
                view_cart.setPayment_Detail();
                notifyDataSetChanged();
            }
        });
        holder.dec_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart_models.get(position).getProduct_quantity() >= 2) {
                    cart_models.get(position).setProduct_quantity(cart_models.get(position).getProduct_quantity() - 1);
                    cart_models.get(position).setProduct_total_price(cart_models.get(position).getProduct_price()  * cart_models.get(position).getProduct_quantity());

                    view_cart.setDataToCart_RecylerView(cart_models);
                    view_cart.setPayment_Detail();
                    notifyDataSetChanged();
                }
            }
        });


        holder.delete_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cart_models.remove(position);
                view_cart.setPayment_Detail();
                view_cart.setDataToCart_RecylerView(cart_models);
                notifyDataSetChanged();
            }
        });





    }

    @Override
    public int getItemCount() {
        return cart_models.size();
    }


    public static class MyviewHolder extends RecyclerView.ViewHolder {
        TextView product_name, product_price, product_total_price, product_quanity;
        TextView delete_product, inc_quantity, dec_quantity;
        ImageView product_image;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            this.product_name = itemView.findViewById(R.id.product_name);
            this.product_price = itemView.findViewById(R.id.product_price);
            this.product_total_price = itemView.findViewById(R.id.product_total_price);
            this.product_quanity = itemView.findViewById(R.id.product_quantity);
            this.product_image = itemView.findViewById(R.id.product_image);

            this.delete_product = itemView.findViewById(R.id.delete_product);
            this.inc_quantity = itemView.findViewById(R.id.inc_quantity);
            this.dec_quantity = itemView.findViewById(R.id.dec_quantity);





        }
    }



}
