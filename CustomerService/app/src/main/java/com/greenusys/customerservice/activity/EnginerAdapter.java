package com.greenusys.customerservice.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.greenusys.customerservice.R;

import java.util.List;

/**
 * Created by admin on 01-06-2018.
 */

public class EnginerAdapter extends RecyclerView.Adapter<EnginerAdapter.MyviewHolder> {

    private List<EnginerModel> customerModelList;
    Context context;


    public EnginerAdapter(List<EnginerModel> customerModelList, Context context) {
        this.customerModelList = customerModelList;
        this.context = context;


    }

    @NonNull
    @Override
    public EnginerAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_list, parent, false);
        EnginerAdapter.MyviewHolder myviewHolder = new EnginerAdapter.MyviewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final EnginerAdapter.MyviewHolder holder, final int position) {

        final EnginerModel Customer_Model = customerModelList.get(position);

        if (Customer_Model.getStatus().equalsIgnoreCase("0")) {
            holder.bt.setBackgroundColor(Color.parseColor("#F9F636"));

        } else if (Customer_Model.getStatus().equalsIgnoreCase("1")) {
            holder.bt.setBackgroundColor(Color.parseColor("#21FA05"));

        } else {
            holder.bt.setBackgroundColor(Color.parseColor("#f70707"));

        }


        holder.query.setText("Query :  : " + Customer_Model.getQuery());
        holder.date.setText("Date : " + Customer_Model.getDate());
        holder.id.setText("ENQIURY ID : " + Customer_Model.getQueryId());

        holder.cv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        context.startActivity(new Intent(context, Eng_single_view.class)
                                .putExtra("cname", Customer_Model.getName())
                                .putExtra("cmobile", Customer_Model.getPhone())
                                .putExtra("caddress", Customer_Model.getAddress())
                                .putExtra("cquery", Customer_Model.getQuery())
                                .putExtra("cqid", Customer_Model.getQueryId())
                                .putExtra("pid", Customer_Model.getId())
                                .putExtra("status", Customer_Model.getStatus())
                                .putExtra("cdate", Customer_Model.getDate()));


                    }
                }
        );

    }

    @Override
    public int getItemCount() {


        return customerModelList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        public TextView query, date, address, id, bal;
        Button bt;
        CardView cv;

        public MyviewHolder(View itemView) {
            super(itemView);
            query = itemView.findViewById(R.id.query_cust);
            date = itemView.findViewById(R.id.date_query);
            id = itemView.findViewById(R.id.cid);
            //    address = itemView.findViewById(R.id.address);
            cv = itemView.findViewById(R.id.customer_card);
            bt = itemView.findViewById(R.id.col);
      /*      if(i==1){
                cv.setCardBackgroundColor(Color.parseColor("#92D4E9"));
            }
            if(i==2){
                cv.setCardBackgroundColor(Color.parseColor("#FDC448"));
            }
            if(i==3){
                cv.setCardBackgroundColor(Color.parseColor("#F1D9D9"));
            }*/


        }
    }

}
