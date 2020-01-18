package com.icosom.social.Adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.icosom.social.R;
import com.icosom.social.model.PassbookModel;

import java.util.List;


public class PassbookAdapter extends RecyclerView.Adapter<PassbookAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    Context context;
    List<PassbookModel> listPassbookModel;
    String userId;

    public PassbookAdapter(Context context, List<PassbookModel> listPassbookModel,String userId){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.listPassbookModel = listPassbookModel;
        this.userId = userId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_passbook_recharge, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final PassbookModel current = listPassbookModel.get(position);
        if(current.getUser_id().equalsIgnoreCase(userId)){
            holder.tDetail.setTextColor(Color.RED);
        }
        else {
            holder.tDetail.setTextColor(Color.GREEN);
       }
        if (current.getTransaction_type().equalsIgnoreCase("transfer_money")){
            if(current.getUser_id().equalsIgnoreCase(userId)){
                holder.txt_paid_receive.setText("Paid");
            }
            else {
                holder.txt_paid_receive.setText("Received");
            }

        }else if (current.getTransaction_type().equalsIgnoreCase("receive_money")){
            holder.txt_paid_receive.setText("Received");
        }else if (current.getTransaction_type().equalsIgnoreCase("recharge")){
            holder.txt_paid_receive.setText("Recharge");

        }else if (current.getTransaction_type().equalsIgnoreCase("add_money")){
            holder.txt_paid_receive.setText("Added");
        }
        else{
            holder.txt_paid_receive.setText("Other");
        }

        holder.txt_transaction_id.setText("Txn. id: " + current.getTransaction_id());
        holder.txt_transaction_date.setText("Date: " + current.getCurrentdate());
        holder.txt_transaction_price.setText("₹ " + current.getAmount());


    }

    @Override
    public int getItemCount() {
        return listPassbookModel.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txt_paid_receive;
        TextView txt_transaction_id;
        TextView txt_transaction_date;
        TextView txt_transaction_price;
        TextView tDetail;

        public MyViewHolder(View itemView) {
            super(itemView);
            tDetail = itemView.findViewById(R.id.td);
            txt_paid_receive = itemView.findViewById(R.id.txt_paid_receive);
            txt_transaction_id = itemView.findViewById(R.id.txt_transaction_id);
            txt_transaction_date = itemView.findViewById(R.id.txt_transaction_date);
            txt_transaction_price = itemView.findViewById(R.id.txt_transaction_price);
        }
    }
}
