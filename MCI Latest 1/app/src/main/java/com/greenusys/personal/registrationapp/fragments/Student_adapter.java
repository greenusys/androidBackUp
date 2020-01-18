package com.greenusys.personal.registrationapp.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greenusys.personal.registrationapp.R;
import com.greenusys.personal.registrationapp.pojos.Newz;
import com.greenusys.personal.registrationapp.pojos.Quizzz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 07-Mar-18.
 */

public class Student_adapter extends RecyclerView.Adapter<Student_adapter.Student_adapterViewHolder> {

    private final Context context;

    private List<Quizzz> quizzzList;

    private Student_adapter.NewItemOnClickHandler clickHandler;
    public interface NewItemOnClickHandler{

        public void onClick(Quizzz quizzz);
    }

    public Student_adapter(Context context, Student_adapter.NewItemOnClickHandler clickHandler)
    {
        this.context = context;
        quizzzList = new ArrayList<>();
        this.clickHandler = clickHandler;
    }


    @Override
    public Student_adapter.Student_adapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.video_activity_list_item,parent,false);

        return new Student_adapter.Student_adapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Student_adapter.Student_adapterViewHolder holder, int position) {

       // holder.heading.setText(quizzzList.get(position).getTitle());
       // holder.date.setText(quizzzList.get(position).getTime().split(" ")[0]);
       // holder.description.setText(quizzzList.get(position).getNews());
        Log.e("ssssss", "onBindViewHolder: "+quizzzList.get(position).getTest_title() );
        holder.videoTitle.setText(quizzzList.get(position).getTest_title());
        //holder.videoTitle.setText(videoList.get(position));

    }

    @Override
    public int getItemCount() {
        return quizzzList.size();
    }

    public class Student_adapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView videoTitle;

        public Student_adapterViewHolder(View itemView)
        {
            super(itemView);

            videoTitle = (TextView)itemView.findViewById(R.id.video_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            clickHandler.onClick(quizzzList.get(getAdapterPosition()));

        }
    }

    public void updateData(List<Quizzz> quizzzList)
    {
        this.quizzzList = quizzzList;
        notifyDataSetChanged();
    }
}
