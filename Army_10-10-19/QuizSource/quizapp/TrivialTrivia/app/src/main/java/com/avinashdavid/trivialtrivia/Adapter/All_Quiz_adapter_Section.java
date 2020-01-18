package com.avinashdavid.trivialtrivia.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.avinashdavid.trivialtrivia.AppController;
import com.avinashdavid.trivialtrivia.Model.Topic_Category_Model;
import com.avinashdavid.trivialtrivia.Quiz_Subject_List;
import com.avinashdavid.trivialtrivia.R;

import java.util.List;

public class All_Quiz_adapter_Section extends RecyclerView.Adapter<All_Quiz_adapter_Section.MyViewHolder> {



    private List<Topic_Category_Model> soldire_gd_models;
    public   int i;
    AppController appController;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,date,link;
        public Button button_link;




        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.gd_name);
            date = (TextView) view.findViewById(R.id.gd_date);
           // link = (TextView) view.findViewById(R.id.gd_link);
            button_link = (Button) view.findViewById(R.id.gd_link);



        }
    }


    public All_Quiz_adapter_Section(List<Topic_Category_Model> soldire_gd_models) {
        this.soldire_gd_models = soldire_gd_models;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_quiz_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       final Topic_Category_Model gd_model = soldire_gd_models.get(position);
        holder.button_link.setText(gd_model.getTopic_name());



        holder.button_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String topic_id= gd_model.getTopic_id();


                Intent intent=new Intent(view.getContext(), Quiz_Subject_List.class);
                intent.putExtra("id",gd_model.getTopic_id());
                intent.putExtra("name",gd_model.getTopic_name());
                intent.putExtra("category","section");
                view.getContext().startActivity(intent);







            }
        });





      //  holder.date.setText(gd_model.getDate());
      //  holder.link.setText(Html.fromHtml(gd_model.getLink()));

       // System.out.println("i"+position);
          //  for(i=1;i<=3;i++) {
                //System.out.println("i"+position);
               // holder.button_link.setText("Army GD Sample Paper #" + ++position);
            //}


       /* holder.button_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               Intent intent=new Intent(view.getContext(), ActivityWelcomePage.class);
               view.getContext().startActivity(intent);
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return soldire_gd_models.size();
    }





}

