package com.avinashdavid.trivialtrivia.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.avinashdavid.trivialtrivia.AppController;
import com.avinashdavid.trivialtrivia.Model.Name_Wise_Quiz;

import com.avinashdavid.trivialtrivia.R;
import com.avinashdavid.trivialtrivia.UI.ActivityInstructions;
import com.avinashdavid.trivialtrivia.all_quiz_category;

import java.util.List;

public class Dashboard_Quiz_adapter_Name extends RecyclerView.Adapter<Dashboard_Quiz_adapter_Name.MyViewHolder> {



    private List<Name_Wise_Quiz> soldire_gd_models;
    public   int i;

    AppController appController;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,date,link;
        public Button button_link;
        public CardView cardView;



        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.text);
            cardView = (CardView) view.findViewById(R.id.cardview);




        }
    }


    public Dashboard_Quiz_adapter_Name(List<Name_Wise_Quiz> soldire_gd_models) {
        this.soldire_gd_models = soldire_gd_models;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quiz_name_items, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
       final Name_Wise_Quiz gd_model = soldire_gd_models.get(position);


       //for(int i=0;i<3;i++)
        holder.name.setText(gd_model.getName());

holder.cardView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      //  Toast.makeText(v.getContext(), "clicked"+gd_model.getName(), Toast.LENGTH_SHORT).show();

        if(gd_model.getName().equalsIgnoreCase("more")) {
            Intent intent = new Intent(v.getContext(), all_quiz_category.class);
            //intent.putExtra("quiz_name", gd_model.getName());
           // intent.putExtra("category", "name");
            v.getContext().startActivity(intent);

        }
        else
        {

            Intent intent=new Intent(v.getContext(), ActivityInstructions.class);
            intent.putExtra("quiz_name",soldire_gd_models.get(position).getName());
            intent.putExtra("category","name");



            v.getContext().startActivity(intent);
        }





    }
});


    }

    @Override
    public int getItemCount() {

        if(soldire_gd_models.size()>4) {

            for (int i = 5; i < soldire_gd_models.size(); )
                soldire_gd_models.remove(i);

            soldire_gd_models.add(new Name_Wise_Quiz("More"));
        }

        return soldire_gd_models.size();

    }





}

