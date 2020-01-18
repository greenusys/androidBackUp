package com.avinashdavid.trivialtrivia.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
/*
import com.greenusys.army_project.Model.Current_Affair_model;
import com.greenusys.army_project.R;*/

import com.avinashdavid.trivialtrivia.Current_Affair_Description;
import com.avinashdavid.trivialtrivia.Model.Current_Affair_model;
import com.avinashdavid.trivialtrivia.R;

import java.util.List;

/**
 * Created by Greenusys on 15-03-2019.
 */

public class Current_Affair_adapter extends RecyclerView.Adapter<Current_Affair_adapter.MyViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private final String language;
    private List<Current_Affair_model> soldire_gd_models;

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView heading, date, current_affair_id;

        public LinearLayout layout;


        public MyViewHolder(View view) {
            super(view);

            heading = (TextView) view.findViewById(R.id.heading);
            layout = (LinearLayout) view.findViewById(R.id.layout);
            current_affair_id = view.findViewById(R.id.current_affair_id);


        }
    }


    public Current_Affair_adapter(List<Current_Affair_model> soldire_gd_models, String language) {
        this.soldire_gd_models = soldire_gd_models;
        this.language = language;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gd_current_affair, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Current_Affair_model gd_model = soldire_gd_models.get(position);
        System.out.println("Piyush" + gd_model.getDetails());

        holder.heading.setText(gd_model.getDate());

        if(language.equalsIgnoreCase("hindi"))
        holder.current_affair_id.setText("Hindi");

        if(language.equalsIgnoreCase("english"))
        holder.current_affair_id.setText("English");




        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), Current_Affair_Description.class);
                // intent.putExtra("details", soldire_gd_models.get(position).getCurrent_affair());
                //intent.putExtra("news_title",soldire_gd_models.get(position).getName());
                intent.putExtra("news_date", soldire_gd_models.get(position).getDate());
                intent.putExtra("language", language.toLowerCase());

                //intent.putExtra("news_link",soldire_gd_models.get(position).getLink());
                //intent.putExtra("news_description",soldire_gd_models.get(position).getCurrent_affair());
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return soldire_gd_models.size();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }


}

