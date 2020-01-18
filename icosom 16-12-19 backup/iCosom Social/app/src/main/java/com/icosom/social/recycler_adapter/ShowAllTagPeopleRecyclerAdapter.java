package com.icosom.social.recycler_adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icosom.social.R;
import com.icosom.social.model.TagModel;

import java.util.ArrayList;

public class ShowAllTagPeopleRecyclerAdapter extends RecyclerView.Adapter<ShowAllTagPeopleRecyclerAdapter.ViewHolder>
{
    Context context;
    ArrayList<TagModel> models;

    public ShowAllTagPeopleRecyclerAdapter(Context context, ArrayList<TagModel> models)
    {
        this.context = context;
        this.models = models;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_all_tag_person_layout_item, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount()
    {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View itemView)
        {
            super(itemView);
        }
    }
}