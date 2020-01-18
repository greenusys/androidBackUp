package com.greenusys.customerservice.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.greenusys.customerservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 06-Sep-18.
 */

public class QuationAdapter extends RecyclerView.Adapter<QuationAdapter.MyViewHolder> {
     ArrayList<QutactionModel> dataset;
     Context context;

    public QuationAdapter(Context context,List <QutactionModel> data) {
        this.dataset= (ArrayList <QutactionModel>) data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qutaction,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);



        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



    holder.txtnumber.setText(dataset.get(position).getNumber());
    holder.txthname.setText(dataset.get(position).getHname());
    holder.txtrate.setText(dataset.get(position).getRate());
    holder.txtquanty.setText(dataset.get(position).getQuanty());
    holder.txtamount.setText(dataset.get(position).getAmount());
    holder.txtdecc.setText(dataset.get(position).getDec());
        Glide.with(context).load(dataset.get(position).getImg()).into(holder.img);

    holder.cv.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "hello", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(v.getContext(),Descripation.class);
                    intent.putExtra("message",dataset.get(position).getDec());
                    v.getContext().startActivity(intent);

                }
            }
    );
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtnumber;
        TextView txthname;
        TextView txtrate;
        TextView txtquanty;
        TextView txtamount;
        CardView cv;
        TextView txtdecc;
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtnumber = itemView.findViewById(R.id.txtno);
            txthname = itemView.findViewById(R.id.txthname);
            txtrate = itemView.findViewById(R.id.txtrate1);
            txtquanty = itemView.findViewById(R.id.txtquty1);
            txtamount = itemView.findViewById(R.id.txtamout1);
            txtdecc = itemView.findViewById(R.id.txtdec);
            cv = itemView.findViewById(R.id.cv);
            img = itemView.findViewById(R.id.img_qot);
        }
    }
}
