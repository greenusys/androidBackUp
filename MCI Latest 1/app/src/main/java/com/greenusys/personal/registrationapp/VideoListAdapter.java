package com.greenusys.personal.registrationapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greenusys.personal.registrationapp.fragments.NewsAdapter;
import com.greenusys.personal.registrationapp.pojos.News;
import com.greenusys.personal.registrationapp.pojos.Newz;
import com.greenusys.personal.registrationapp.pojos.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by G-112 on 28-02-2018.
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoListViewHolder> {

    List<Video> videoList;
    Context context;

    private VideoListAdapter.NewItemOnClickHandler clickHandler;
    public interface NewItemOnClickHandler{

        public void onClick(Video video);
    }

    public VideoListAdapter(Context context, NewItemOnClickHandler clickHandler)
    {
        this.context = context;
        videoList = new ArrayList<>();
        this.clickHandler = clickHandler;
    }
    @Override
    public VideoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(context).inflate(R.layout.video_activity_list_item,parent,false);

        return new VideoListViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(VideoListViewHolder holder, int position) {
        Log.e("ssssss", "onBindViewHolder: "+videoList.get(position).getDescription() );
        holder.videoTitle.setText(videoList.get(position).getDescription());
        //holder.videoTitle.setText(videoList.get(position));

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class VideoListViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        TextView videoTitle;
        public VideoListViewHolder(View itemView) {
            super(itemView);

            videoTitle = (TextView)itemView.findViewById(R.id.video_title);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            clickHandler.onClick(videoList.get(getAdapterPosition()));

        }
    }
    public void updateData(List<Video> videoList)
    {
        this.videoList = videoList;
        notifyDataSetChanged();
    }
}
