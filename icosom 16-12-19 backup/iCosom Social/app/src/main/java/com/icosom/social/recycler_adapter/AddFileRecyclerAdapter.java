package com.icosom.social.recycler_adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.icosom.social.R;
import com.icosom.social.activity.AddFeedActivity;

import java.util.ArrayList;

public class AddFileRecyclerAdapter extends RecyclerView.Adapter<AddFileRecyclerAdapter.ViewHolder>
{
    Context context;
    private static final int TYPE_ITEM_NORMAL = 0;
    ArrayList<String> uris;

    public AddFileRecyclerAdapter(Context context, ArrayList<String> uris)
    {
        this.context = context;
        this.uris = uris;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == TYPE_ITEM_NORMAL)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_files_layout_item, parent, false);
            ViewHolder holder = new ViewHolder(v);
            return holder;
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        System.out.println("kaif_vi"+uris.get(position));

        Glide.with(context).
                load(uris.get(position)).
                into(holder.iv_img);

        holder.iv_close.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ((AddFeedActivity) context).removeImage(position);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return uris.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView iv_img;
        ImageView iv_close;

        public ViewHolder(View itemView)
        {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            iv_close = itemView.findViewById(R.id.iv_close);
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        return TYPE_ITEM_NORMAL;
    }
}