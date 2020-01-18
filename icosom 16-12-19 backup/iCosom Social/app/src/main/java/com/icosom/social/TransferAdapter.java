package com.icosom.social;

import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by admin on 21-04-2018.
 */

public class TransferAdapter extends RecyclerView.Adapter<TransferAdapter.MyViewHolder>
{ public Context context;
    private List<Transfer_Model> transferMonies;


    public TransferAdapter(Context context ,List<Transfer_Model> transferMonies) {
        this.transferMonies = transferMonies;
        this.context=context;
    }



    @Override
    public TransferAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transfer, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TransferAdapter.MyViewHolder holder, int position) {
        final Transfer_Model talent = transferMonies.get(position);
        holder.bname.setText(talent.getBeneficiaryName());
        holder.bacount.setText(talent.getBeneficiaryAccountNumber());
        holder.bphone.setText(talent.getBeneficiaryMobileNumber());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, TransferMoney.class);
                i.putExtra("beneficiaryId",talent.getBeneficiaryId());
                i.putExtra("customerMobile",talent.getCustomerMobile());
                i.putExtra("balance",talent.getBalance());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return transferMonies.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView bname,bacount,bphone,bnext;
        public CardView cv ;


        public MyViewHolder(View itemView) {
            super(itemView);
            cv= itemView.findViewById(R.id.card_transfer);
            bname = itemView.findViewById(R.id.bbname);
            bacount = itemView.findViewById(R.id.bbaccount);
            bphone = itemView.findViewById(R.id.bbphone);
            bnext = itemView.findViewById(R.id.tranfer_next);
        }
    }

}
