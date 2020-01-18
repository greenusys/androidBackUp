package com.greenusys.personal.registrationapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greenusys.personal.registrationapp.pojos.Studyy;
import com.greenusys.personal.registrationapp.pojos.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 07-Mar-18.
 */

public class StudyAdapter extends RecyclerView.Adapter<StudyAdapter.StudyAdapterViewHolder>{

    List<Studyy> studyList;
    Context context;

    private StudyAdapter.NewItemOnClickHandler clickHandler;
    public interface NewItemOnClickHandler{

        public void onClick(Studyy study);
    }

    public StudyAdapter(Context context, StudyAdapter.NewItemOnClickHandler clickHandler)
    {
        this.context = context;
        studyList = new ArrayList<>();
        this.clickHandler = clickHandler;
    }


    @Override
    public StudyAdapter.StudyAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(context).inflate(R.layout.video_activity_list_item,parent,false);

        return new StudyAdapter.StudyAdapterViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(StudyAdapter.StudyAdapterViewHolder holder, int position) {
        Log.e("ssssss", "onBindViewHolder: "+studyList.get(position).getDescription() );
        holder.videoTitle.setText(studyList.get(position).getDescription());
        //holder.videoTitle.setText(videoList.get(position));

    }

    @Override
    public int getItemCount() {
        return studyList.size();

    }

    public class StudyAdapterViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        TextView videoTitle;
        public StudyAdapterViewHolder(View itemView) {
            super(itemView);

            videoTitle = (TextView)itemView.findViewById(R.id.video_title);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            clickHandler.onClick(studyList.get(getAdapterPosition()));

        }
    }
    public void updateData(List<Studyy> studyList)
    {
        this.studyList = studyList;
        notifyDataSetChanged();
    }
}
