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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.greenusys.customerservice.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 01-06-2018.
 */
class Customer_Adapter extends RecyclerView.Adapter<Customer_Adapter.MyviewHolder> implements Filterable {

    private List<Customer_Model> customerModelList;
    List<Customer_Model> customerModelFilter;
    Context context;
    ContactsAdapterListener listener;



    public Customer_Adapter(List<Customer_Model> customerModelList, Context context) {
        this.customerModelList = customerModelList;
        this.customerModelFilter = customerModelList;
        this.context = context;


    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_list, parent, false);
        MyviewHolder myviewHolder = new MyviewHolder(view);

        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyviewHolder holder, final int position) {
              //Customer_Model customer_model = customerModelFilter.get(position);
       final Customer_Model Customer_Model = customerModelFilter.get(position);

        if (Customer_Model.getStatus().equalsIgnoreCase("1")) {
            holder.btt.setBackgroundColor(Color.parseColor("#F9F636"));

        } else if (Customer_Model.getStatus().equalsIgnoreCase("2")) {
            holder.btt.setBackgroundColor(Color.parseColor("#21FA05"));

        } else {
            holder.btt.setBackgroundColor(Color.parseColor("#f70707"));

        }



        holder.query.setText("Query :  : "+Customer_Model.getCustomerQuery());
        holder.date.setText("Date : "+Customer_Model.getPostedOn());
        holder.id.setText("ENQIURY ID : "+Customer_Model.getQueryId());

        holder.cv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        context.startActivity(new Intent(context, Customer_Single_call.class)
                                .putExtra("query",Customer_Model.getCustomerQuery())
                                .putExtra("date",Customer_Model.getPostedOn())
                                .putExtra("id",Customer_Model.getQueryId())
                                .putExtra("engName",Customer_Model.getEnggName())
                                .putExtra("EnggPhone",Customer_Model.getEnggPhone())
                                .putExtra("status",Customer_Model.getStatus()));

                    }
                }
        );

    }

    @Override
    public int getItemCount() {
        return customerModelFilter.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
               String charString = charSequence.toString();
               System.out.println("champ_query"+charString);
               if(charString.isEmpty())
               {
                   customerModelFilter = customerModelList;
               }
               else {
                   List<Customer_Model> filteredList = new ArrayList <>();
                   for (Customer_Model row : customerModelList)
                   {
                       System.out.println("k1 "+row.getCustomerQuery().toLowerCase());
                       System.out.println("k2 "+charString.toString().toLowerCase());

                       if(row.getCustomerQuery().toLowerCase().contains(charString.toString().toLowerCase()))
                       {
                           System.out.println("champ_contain_query");

                           filteredList.add(row);
                       }
                   }

                   customerModelFilter = filteredList;
               }
                  FilterResults filterResults = new FilterResults();
                  filterResults.values = customerModelFilter;
                   return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
             customerModelFilter = (ArrayList<Customer_Model>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(Customer_Model contact);
    }


    public class MyviewHolder extends RecyclerView.ViewHolder {
        public TextView query, date, address,id,bal;
        CardView cv;
        Button btt;

        public MyviewHolder(View itemView) {
            super(itemView);
            query = itemView.findViewById(R.id.query_cust);
            date = itemView.findViewById(R.id.date_query);
            id = itemView.findViewById(R.id.cid);
            //    address = itemView.findViewById(R.id.address);
            cv = itemView.findViewById(R.id.customer_card);
            btt = itemView.findViewById(R.id.col);

            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onContactSelected(customerModelFilter.get(getAdapterPosition()));
                        }
                    }
            );
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
