package com.icosom.social.Test;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.icosom.social.R;

/**
 * Created by Allen on 10/30/2017.
 */

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    Context context;
   // private List<SearchResult> listModelSearchResult;

    public GenreAdapter(Context context){//, List<SearchResult> listModelSearchResult) {
        inflater = LayoutInflater.from(context);
       // this.listModelSearchResult = listModelSearchResult;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_genre, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
      TextView item_genre;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_genre = itemView.findViewById(R.id.item_genre);
        }
    }
}