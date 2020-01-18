package com.greenusys.personal.registrationapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greenusys.personal.registrationapp.pojos.TestResult;

import java.util.ArrayList;

/**
 * Created by personal on 3/10/2018.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultAdapterViewHolder> {

    ArrayList<TestResult> testResults;
    Context context;

    public ResultAdapter(Context context, ArrayList<TestResult> testResults)
    {
        this.testResults = testResults;
        this.context = context;

    }

    @Override
    public ResultAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(context).inflate(R.layout.student_result_item,parent,false);

        return new ResultAdapterViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ResultAdapterViewHolder holder, int position) {

        holder.maxMarks.setText(testResults.get(position).getMaxMarks());
        holder.testName.setText(testResults.get(position).getTestName());
        holder.score.setText(testResults.get(position).getScore());

    }

    @Override
    public int getItemCount() {
        return testResults.size();
    }

    class ResultAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView testName;
        TextView maxMarks;
        TextView score;


       public ResultAdapterViewHolder(View itemView) {
           super(itemView);

           testName = (TextView)itemView.findViewById(R.id.test_name);
           maxMarks = (TextView)itemView.findViewById(R.id.max_marks);
           score = (TextView)itemView.findViewById(R.id.score);
       }
   }

   public void updateDataset(ArrayList<TestResult> results)
   {
       this.testResults = results;
       notifyDataSetChanged();
   }
}
