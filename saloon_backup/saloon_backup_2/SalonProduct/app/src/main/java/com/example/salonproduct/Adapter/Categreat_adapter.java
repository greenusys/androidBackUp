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
import com.example.salonproduct.Model.Categre_model;
import com.example.salonproduct.R;

import java.util.ArrayList;

import static com.example.salonproduct.R.layout.item_categree;

public class Categreat_adapter extends RecyclerView.Adapter<Categreat_adapter.MyviewHolder> {
       ArrayList<Categre_model> categre_models;
         Context context;

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(item_categree,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, final int position) {
        TextView textViewName = holder.txt_name;
        ImageView imageView = holder.img;

        textViewName.setText(categre_models.get(position).getName());

        imageView.setImageResource(categre_models.get(position).getImage());

        holder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Show_Category_Products.class).putExtra("cat_name",categre_models.get(position).getName()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categre_models.size();
    }


    public static class MyviewHolder extends RecyclerView.ViewHolder
    {
       TextView txt_name;
       ImageView img;
       LinearLayout item_layout;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            this.txt_name = itemView.findViewById(R.id.rv_cat_txt_id);
            this.img = itemView.findViewById(R.id.rv_cat_img_id);
            this.item_layout = itemView.findViewById(R.id.item_layout);
        }
    }

    public Categreat_adapter(Context context, ArrayList<Categre_model> categre_models) {
        this.categre_models = categre_models;
        this.context = context;

    }
}
