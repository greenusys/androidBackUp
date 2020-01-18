package com.example.salonproduct.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salonproduct.Activities.Product_Description;
import com.example.salonproduct.Activities.Show_Category_Products;
import com.example.salonproduct.Model.Prodect_Model;
import com.example.salonproduct.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class Prodect_Adapter extends RecyclerView.Adapter<Prodect_Adapter.ViewHolder> {
    ArrayList<Prodect_Model> prodect_models;
    Context context;
    public Prodect_Adapter(Context context, ArrayList<Prodect_Model> prodect_models) {
        this.prodect_models = prodect_models;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prodect,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

     holder.textView.setText(prodect_models.get(position).getName());
     holder.img.setImageResource(prodect_models.get(position).getImage());

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Product_Description.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return prodect_models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
          TextView textView;
         ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.rv_pro_img_id);
            this.textView = itemView.findViewById(R.id.rv_pro_txt_id);
        }
    }
}
