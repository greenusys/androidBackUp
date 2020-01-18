package com.avinashdavid.trivialtrivia.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.avinashdavid.trivialtrivia.Model.Name_Wise_Quiz;
import com.avinashdavid.trivialtrivia.Quiz_Subject_List;
import com.avinashdavid.trivialtrivia.R;
import com.avinashdavid.trivialtrivia.UI.ActivityInstructions;

import java.util.List;

public class Quiz_Subject_List_Adapter extends RecyclerView.Adapter<Quiz_Subject_List_Adapter.MyViewHolder> {

    private List<Name_Wise_Quiz> soldire_gd_models;
    public int i;

    public Quiz_Subject_List_Adapter(Quiz_Subject_List quiz_subject_list, List<Name_Wise_Quiz> name_wise_quizs_array) {
        this.soldire_gd_models = name_wise_quizs_array;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date, link;
        public Button button_link;


        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.gd_name);
            date = (TextView) view.findViewById(R.id.gd_date);
            button_link = (Button) view.findViewById(R.id.gd_link);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_quiz_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Name_Wise_Quiz gd_model = soldire_gd_models.get(position);
        holder.button_link.setText(gd_model.getName());

        holder.button_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), ActivityInstructions.class);
                intent.putExtra("quiz_name", gd_model.getName());
                intent.putExtra("category", "name");
                view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return soldire_gd_models.size();
    }

}

