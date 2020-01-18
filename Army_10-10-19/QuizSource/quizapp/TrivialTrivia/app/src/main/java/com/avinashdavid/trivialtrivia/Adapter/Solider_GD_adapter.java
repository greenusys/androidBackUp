package com.avinashdavid.trivialtrivia.Adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
/*
import com.greenusys.army_project.Model.soldire_gd_model;
import com.greenusys.army_project.R;*/

import com.avinashdavid.trivialtrivia.Model.soldire_gd_model;
import com.avinashdavid.trivialtrivia.R;

import java.util.List;

/**
 * Created by Greenusys on 15-03-2019.
 */

public class Solider_GD_adapter extends RecyclerView.Adapter<Solider_GD_adapter.MyViewHolder> {

    private List<soldire_gd_model> soldire_gd_models;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,date,link;
        public TextView button_link;


        public MyViewHolder(View view) {
            super(view);
           //name = (TextView) view.findViewById(R.id.gd_name);
           // date = (TextView) view.findViewById(R.id.gd_date);
           // link = (TextView) view.findViewById(R.id.gd_link);
            button_link = (TextView) view.findViewById(R.id.gd_link);

        }
    }


    public Solider_GD_adapter(List<soldire_gd_model> soldire_gd_models) {
        this.soldire_gd_models = soldire_gd_models;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gd, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       final soldire_gd_model gd_model = soldire_gd_models.get(position);
        holder.button_link.setText(gd_model.getName());
        //holder.date.setText(gd_model.getDate());
      //  holder.link.setText(Html.fromHtml(gd_model.getLink()));


            //holder.button_link.setText("Army GD Sample Paper #"+ ++position);



        holder.button_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(gd_model.getLink()));
                view.getContext().startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return soldire_gd_models.size();
    }
}

