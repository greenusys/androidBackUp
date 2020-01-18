package com.avinashdavid.trivialtrivia.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avinashdavid.trivialtrivia.Model.Check_Rally_Model;
import com.avinashdavid.trivialtrivia.R;

import java.util.List;

/*
import com.greenusys.army_project.Model.Current_Affair_model;
import com.greenusys.army_project.R;*/

/**
 * Created by Greenusys on 15-03-2019.
 */

public class Check_Rally_Adapter extends RecyclerView.Adapter<Check_Rally_Adapter.MyViewHolder> {

    private List<Check_Rally_Model> check_rally_models;

Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView sn,state_name,district_name,category_name,venue,app_start_date,app_end_date,rally_start_date,rally_end_date;



        public MyViewHolder(View view) {
            super(view);
            sn = (TextView) view.findViewById(R.id.sn_value);
            state_name = (TextView) view.findViewById(R.id.state_value);
            district_name = (TextView) view.findViewById(R.id.distrct_value);
            category_name = (TextView) view.findViewById(R.id.category_value);
            venue = (TextView) view.findViewById(R.id.venue_value);
            app_start_date = (TextView) view.findViewById(R.id.app_start_date_value);
            app_end_date = (TextView) view.findViewById(R.id.app_end_date_value);
            rally_start_date = (TextView) view.findViewById(R.id.rally_start_date_value);
            rally_end_date = (TextView) view.findViewById(R.id.rally_end_date_value);



        }
    }



    public Check_Rally_Adapter(List<Check_Rally_Model> check_rally_models) {
        this.check_rally_models = check_rally_models;
    }





    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_rally_layout, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        final Check_Rally_Model check_rally_model = check_rally_models.get(position);
        holder.sn.setText(check_rally_model.getSn());
        holder.state_name.setText(check_rally_model.getState_name());
        holder.district_name.setText(check_rally_model.getDistrict_name());
        holder.category_name.setText(check_rally_model.getCategory_name());
        holder.venue.setText(check_rally_model.getVenue());
        holder.app_start_date.setText(check_rally_model.getApp_start_date());
        holder.app_end_date.setText(check_rally_model.getApp_end_date());
        holder.rally_start_date.setText(check_rally_model.getRally_start_date());
        holder.rally_end_date.setText(check_rally_model.getRally_end_date());



    }

    @Override
    public int getItemCount() {
        return check_rally_models.size();
    }



}

