package com.greenusys.personal.registrationapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greenusys.personal.registrationapp.pojos.Course;

import java.util.ArrayList;

/**
 * Created by admin on 15-Mar-18.
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseAdapterViewHolder> {

    ArrayList<Course> Courses;
    Context context;
    private CourseItemClickHandler clickHandler;

    public interface CourseItemClickHandler{

        public void onClick(Course course);
    }

    public CourseAdapter(Context context, ArrayList<Course> Courses, CourseItemClickHandler clickHandler)
    {
        this.Courses= Courses;
        this.context = context;
        this.clickHandler = clickHandler;
    }

    @Override
    public CourseAdapter.CourseAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(context).inflate(R.layout.video_activity_list_item,parent,false);

        return new CourseAdapter.CourseAdapterViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(CourseAdapter.CourseAdapterViewHolder holder, int position) {

      holder.courseName.setText(Courses.get(position).getCourseName());



    }

    @Override
    public int getItemCount() {
        return Courses.size();
    }

    class CourseAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView courseName;



        public CourseAdapterViewHolder(View itemView) {
            super(itemView);

            courseName = (TextView)itemView.findViewById(R.id.video_title);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            clickHandler.onClick(Courses.get(getAdapterPosition()));

        }
    }

    public void updateDataset(ArrayList<Course> results)
    {
        this.Courses= results;
        notifyDataSetChanged();
    }
}
