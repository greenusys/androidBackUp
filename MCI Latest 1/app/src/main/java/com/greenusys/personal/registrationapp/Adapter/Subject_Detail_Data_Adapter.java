package com.greenusys.personal.registrationapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.greenusys.personal.registrationapp.LoginScreen;
import com.greenusys.personal.registrationapp.R;
import com.greenusys.personal.registrationapp.pojos.Subject_Detail_Data_Modal;
import com.greenusys.personal.registrationapp.pojos.Subject_Detail_Modal;

import java.util.List;

/*
import com.greenusys.army_project.Model.soldire_gd_model;
import com.greenusys.army_project.R;*/

/**
 * Created by Greenusys on 15-03-2019.
 */

public class Subject_Detail_Data_Adapter extends RecyclerView.Adapter<Subject_Detail_Data_Adapter.MyViewHolder> {

    private final Context context;
    private List<Subject_Detail_Modal> soldire_gd_models;
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


    public Subject_Detail_Data_Adapter(Context context,List<Subject_Detail_Modal> soldire_gd_models) {
        this.soldire_gd_models = soldire_gd_models;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_pdf_rv_grid_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int pos) {
       final Subject_Detail_Modal gd_model = soldire_gd_models.get(pos);



       holder.cv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String url = "http://greenusys.website/mci/uploads/study_material/"+soldire_gd_models.get(pos).getUpload_file();
               Intent i = new Intent(Intent.ACTION_VIEW);
               i.setData(Uri.parse(url));
               v.getContext().startActivity(i);
           }
       });


       // holder.iconImage.setImageResource(soldire_gd_models.get(pos).getImageSource());
        holder.descriptionText.setText(soldire_gd_models.get(pos).getDescription());



    }

    @Override
    public int getItemCount() {
        return soldire_gd_models.size();
    }
}

