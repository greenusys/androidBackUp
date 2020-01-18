package com.greenusys.personal.registrationapp.Adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.greenusys.personal.registrationapp.R;
import com.greenusys.personal.registrationapp.Subject_Details;
import com.greenusys.personal.registrationapp.Video_Play;
import com.greenusys.personal.registrationapp.pojos.Subject_Modal;
import com.greenusys.personal.registrationapp.pojos.Video_Modal;

import java.util.List;

/*
import com.greenusys.army_project.Model.soldire_gd_model;
import com.greenusys.army_project.R;*/

/**
 * Created by Greenusys on 15-03-2019.
 */

public class Video_Adapter extends RecyclerView.Adapter<Video_Adapter.MyViewHolder> {

    private List<Video_Modal> soldire_gd_models;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,date,link;
        public Button button_link;
        ImageView iconImage;
        TextView descriptionText;
        CardView cv;

        public MyViewHolder(View view) {
            super(view);
            iconImage = (ImageView) itemView.findViewById(R.id.item_iv);
            descriptionText = (TextView) itemView.findViewById(R.id.item_description);
            cv = itemView.findViewById(R.id.cv_home);

        }
    }


    public Video_Adapter(List<Video_Modal> soldire_gd_models) {
        this.soldire_gd_models = soldire_gd_models;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_rv_grid_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int pos) {
       final Video_Modal gd_model = soldire_gd_models.get(pos);



       holder.cv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent= new Intent(v.getContext(), Video_Play.class);
               intent.putExtra("video_name",gd_model.getVideo());
               v.getContext().startActivity(intent);
           }
       });


        holder.descriptionText.setText(gd_model.getDescription());



    }

    @Override
    public int getItemCount() {
        return soldire_gd_models.size();
    }
}

