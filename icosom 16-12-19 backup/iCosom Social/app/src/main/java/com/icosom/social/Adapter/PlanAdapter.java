package com.icosom.social.Adapter;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.icosom.social.R;
import com.icosom.social.model.PlanModel;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 07-06-2018.
 */

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.MyViewHolder>
{ public Context context;
    private List<PlanModel> planModels = new ArrayList<>();


    public PlanAdapter(Context context ,List<PlanModel> planModels) {
        this.planModels = planModels;
        this.context=context;
    }

    @Override
    public PlanAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plan_item, parent, false);

        return new PlanAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlanAdapter.MyViewHolder holder, int position) {
      final PlanModel talent = planModels.get(position);
        holder.pamount.setText(talent.getAmount());
        holder.pdetail.setText(talent.getDetail());
        holder.pvalidity.setText(talent.getValidity());
        holder.ptalktime.setText(talent.getTalktime());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+talent.getDetail(), Toast.LENGTH_SHORT).show();

               /* Intent i = new Intent(context, TransferMoney.class);
               *//* i.putExtra("beneficiaryId",talent.g());
                i.putExtra("customerMobile",talent.getCustomerMobile());
                i.putExtra("balance",talent.getBalance());*//*
                context.startActivity(i);*/
            }
        });

    }

    @Override
    public int getItemCount() {
     //   Log.e("size", "getItemCount: +,"+planModels.size() );
        return planModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView pamount,pdetail,pvalidity,ptalktime;
        public CardView cv ;


        public MyViewHolder(View itemView) {
            super(itemView);
            cv= itemView.findViewById(R.id.card_plan);
            pamount = itemView.findViewById(R.id.p_amount);
            pdetail = itemView.findViewById(R.id.p_detail);
            pvalidity = itemView.findViewById(R.id.p_validity);
            ptalktime = itemView.findViewById(R.id.p_talktime);
        }
    }

}
