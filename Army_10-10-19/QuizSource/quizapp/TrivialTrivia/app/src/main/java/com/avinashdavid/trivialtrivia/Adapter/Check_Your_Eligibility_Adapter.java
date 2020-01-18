package com.avinashdavid.trivialtrivia.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.avinashdavid.trivialtrivia.Model.Check_Your_Eligibility_Model;
import com.avinashdavid.trivialtrivia.Model.Eligibility_Model;
import com.avinashdavid.trivialtrivia.R;

import java.util.List;

/*
import com.greenusys.army_project.Model.soldire_gd_model;
import com.greenusys.army_project.R;*/

/**
 * Created by Greenusys on 15-03-2019.
 */

public class Check_Your_Eligibility_Adapter extends RecyclerView.Adapter<Check_Your_Eligibility_Adapter.MyViewHolder> {

    private List<Check_Your_Eligibility_Model> eligibility_models;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, category;
        public Button seemore;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            category = (TextView) view.findViewById(R.id.category);
        }
    }

    public Check_Your_Eligibility_Adapter(List<Check_Your_Eligibility_Model> eligibility_models) {
        this.eligibility_models = eligibility_models;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.check_eligibility_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Check_Your_Eligibility_Model eligibility_model = eligibility_models.get(position);
        holder.title.setText(eligibility_model.getTitle());
        holder.category.setText(eligibility_model.getCategory());


    }

    @Override
    public int getItemCount() {
        return eligibility_models.size();
    }
}

