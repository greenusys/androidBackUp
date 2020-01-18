package com.avinashdavid.trivialtrivia.Adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.avinashdavid.trivialtrivia.Model.Eligibility_Model;
import com.avinashdavid.trivialtrivia.R;

import java.util.List;


public class Eligibility_Adapter extends RecyclerView.Adapter<Eligibility_Adapter.MyViewHolder> {

    private List<Eligibility_Model> eligibility_models;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView details;
        public Button seemore;

        public MyViewHolder(View view) {
            super(view);
            details = (TextView) view.findViewById(R.id.details);


        }
    }


    public Eligibility_Adapter(List<Eligibility_Model> eligibility_models) {
        this.eligibility_models = eligibility_models;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gd_eligibility, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Eligibility_Model eligibility_model = eligibility_models.get(position);


            holder.details.setText(eligibility_model.getDetails());

      System.out.println("kaif_eligibi"+eligibility_model.getDetails());


       // holder.details.loadDataWithBaseURL(null, eligibility_model.getDetails(), "text/html", "utf-8", null);


    }

    @Override
    public int getItemCount() {
        return eligibility_models.size();
    }
}

