package com.icosom.social;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.icosom.social.model.PlanModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 19-06-2018.
 */

public class Padapter extends RecyclerView.Adapter<Padapter.MyView> {
    Context context;
    List<PlanModel> planModels = new ArrayList<>();
    String mob;
    String opid;

    public Padapter(Context context, List<PlanModel> planModels,String mob,String opid) {

        this.context = context;
        this.planModels = planModels;
        this.mob=mob;
        this.opid=opid;
    }

    @NonNull
    @Override
    public Padapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plan_item, parent, false);
        MyView holder = new MyView(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Padapter.MyView holder, int position) {

        final PlanModel talent = planModels.get(position);
        holder.pamount.setText("Rs. "+talent.getAmount());
        holder.pdetail.setText("Detail : "+talent.getDetail());
        holder.pvalidity.setText("Validity : "+talent.getValidity());
        holder.ptalktime.setText("Talktime : "+talent.getTalktime());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Toast.makeText(context, ""+talent.getDetail(), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(context, Confirm.class);
                i.putExtra("amount",talent.getAmount());
                i.putExtra("mob",mob);
                i.putExtra("opid",opid);
                context.startActivity(i);*/

                Log.e("list", "onCreate: "+opid+mob );

                Confirm cdds=new Confirm(context,talent.getAmount(),mob,opid);
                cdds.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return planModels.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        public TextView pamount,pdetail,pvalidity,ptalktime;
        public CardView cv ;
        public MyView(View itemView) {
            super(itemView);
            cv= itemView.findViewById(R.id.card_plan);
            pamount = itemView.findViewById(R.id.p_amount);
            pdetail = itemView.findViewById(R.id.p_detail);
            pvalidity = itemView.findViewById(R.id.p_validity);
            ptalktime = itemView.findViewById(R.id.p_talktime);
        }
    }
}
