package com.icosom.social.recycler_adapter;

import android.content.Context;
import android.content.Intent;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.icosom.social.R;
import com.icosom.social.activity.Detail_Comp;
import com.icosom.social.model.TalentModel;

import java.util.List;

/**
 * Created by admin on 30-Mar-18.
 */

public class TalentAdapter extends RecyclerView.Adapter<TalentAdapter.MyViewHolder>
{ public Context context;
    private List<TalentModel> TalentList;
    @Override
    public TalentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_talent, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TalentAdapter.MyViewHolder holder, int position) {

        final TalentModel talent = TalentList.get(position);
        holder.comp_name.setText(talent.getComp_name());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent i = new Intent(context, Detail_Comp.class);
                i.putExtra("id",talent.getId());
                i.putExtra("comp_name",talent.getComp_name());
                context.startActivity(i);*/

                Intent i = new Intent(context, Detail_Comp.class);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return TalentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView comp_name;
        public CardView cv ;


        public MyViewHolder(View itemView) {
            super(itemView);
            cv= itemView.findViewById(R.id.card_talent);
         comp_name = itemView.findViewById(R.id.txt_talent_comp);
        }
    }
    public TalentAdapter(List<TalentModel> TalentList) {
        this.TalentList = TalentList;
    }
}
