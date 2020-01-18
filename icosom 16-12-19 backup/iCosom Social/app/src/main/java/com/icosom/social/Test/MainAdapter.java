package com.icosom.social.Test;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.icosom.social.R;

/**
 * Created by Allen on 10/30/2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    Context context;
    RecyclerView.LayoutManager layoutManagerTrack;
    RecyclerView.LayoutManager layoutManagerGenre;

    TrackAdapter adapterTrack;
    GenreAdapter adapterGenre;
   // private List<SearchResult> listModelSearchResult;

    public MainAdapter(Context context){//, List<SearchResult> listModelSearchResult) {
        inflater = LayoutInflater.from(context);
       // this.listModelSearchResult = listModelSearchResult;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_main, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.itemView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("Child Touch");

                // Disallow the touch request for parent scroll on touch of  child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        layoutManagerGenre = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.rv_genre.setLayoutManager(layoutManagerGenre);
        adapterGenre = new GenreAdapter(context);
        holder.rv_genre.setAdapter(adapterGenre);

        layoutManagerTrack = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        holder.rv_track.setLayoutManager(layoutManagerTrack);
        adapterTrack = new TrackAdapter(context);
        holder.rv_track.setAdapter(adapterTrack);

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rv_genre;
        RecyclerView rv_track;

        public MyViewHolder(View itemView) {
            super(itemView);
            rv_genre = itemView.findViewById(R.id.rv_genre);
            rv_track = itemView.findViewById(R.id.rv_track);
        }
    }
}