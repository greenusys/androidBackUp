package com.example.salonproduct.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salonproduct.Activities.Product_Description;
import com.example.salonproduct.Activities.Show_Category_Products;
import com.example.salonproduct.Model.Prodect_Model;
import com.example.salonproduct.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class Prodect_Adapter extends RecyclerView.Adapter<Prodect_Adapter.ViewHolder> {
    ArrayList<Prodect_Model> prodect_models;
    Context context;
    int product_count;
    TextView dec_quantity,inc_quantity,total_product,add_to_cart;
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

        holder.quick_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



              Dialog  dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.quick_view_item);

                 dec_quantity=dialog.findViewById(R.id.dec_quantity);
                 inc_quantity=dialog.findViewById(R.id.inc_quantity);
                 total_product=dialog.findViewById(R.id.total_product);
                 add_to_cart=dialog.findViewById(R.id.add_to_cart);

                inc_quantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        product_count = product_count + 1;
                        total_product.setText(String.valueOf(product_count));
                    }
                });
                dec_quantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (product_count >= 2) {
                            product_count = product_count - 1;
                            total_product.setText(String.valueOf(product_count));
                        }
                    }
                });

                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

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
         LinearLayout quick_view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.rv_pro_img_id);
            this.textView = itemView.findViewById(R.id.rv_pro_txt_id);
            this.quick_view = itemView.findViewById(R.id.quick_view);
        }
    }
}
