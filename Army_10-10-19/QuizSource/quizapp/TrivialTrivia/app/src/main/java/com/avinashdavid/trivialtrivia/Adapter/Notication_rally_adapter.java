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

/*import com.greenusys.army_project.Model.Rally_model;
import com.greenusys.army_project.R;*/

import com.avinashdavid.trivialtrivia.Model.Rally_model;
import com.avinashdavid.trivialtrivia.R;

import java.util.List;

/**
 * Created by Greenusys on 15-03-2019.
 */

public class Notication_rally_adapter extends RecyclerView.Adapter<Notication_rally_adapter.MyViewHolder> {

    private List<Rally_model> rally_models;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,date,state,regsion,link;
        public Button button_link;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.gd_name);
            date = (TextView) view.findViewById(R.id.gd_date);
            state = (TextView) view.findViewById(R.id.gd_name_state);
            regsion = (TextView) view.findViewById(R.id.gd_rallyRegin);
           // link = (TextView) view.findViewById(R.id.gd_link);
            button_link = (Button) view.findViewById(R.id.gd_link);
        }
    }


    public Notication_rally_adapter(List<Rally_model> rally_models) {
        this.rally_models = rally_models;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_allrally, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
      final  Rally_model r_model = rally_models.get(position);
        holder.name.setText(r_model.getName());
        holder.date.setText(r_model.getDate());
        holder.state.setText(r_model.getState());
        holder.regsion.setText(r_model.getRegsion());
       // holder.link.setText(Html.fromHtml(r_model.getLink()));


        holder.button_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(r_model.getLink()));
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rally_models.size();
    }
}


