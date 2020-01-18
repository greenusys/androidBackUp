package com.example.g116.vvn_social.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.g116.vvn_social.Home_Activities.AddFeedActivity;
import com.example.g116.vvn_social.Modal.Theme;
import com.example.g116.vvn_social.R;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 08-08-2018.
 */

public class ThemeRecyclerAdapter extends RecyclerView.Adapter<ThemeRecyclerAdapter.ViewHolder> {
    Context context;
    private static final int TYPE_ITEM_NORMAL = 0;
    List<Theme> themeList = new ArrayList<>();
    AddFeedActivity.CustomItemClickListener listener;
  /*  public ThemeRecyclerAdapter(Context context,List<Theme> themeList)
    {

    }*/

    public ThemeRecyclerAdapter(Context context, List<Theme> themeList, AddFeedActivity.CustomItemClickListener listener) {
        this.context = context;
        this.themeList = themeList;
        this.listener = listener;
    }

    @Override
    public ThemeRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theme, parent, false);
        // ThemeRecyclerAdapter.ViewHolder holder = new ThemeRecyclerAdapter.ViewHolder(v);
        final ViewHolder mViewHolder = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getPosition());
            }
        });
        return mViewHolder;


    }

    @Override
    public void onBindViewHolder(ThemeRecyclerAdapter.ViewHolder holder, final int position) {
        Theme theme = themeList.get(position);

        Glide.
                with(context).
                load("https://icosom.com/social/postFiles/images/" + theme.getImg()).
                into(holder.iv_img);

        Log.e("kaif_image", "sdkj" + "https://icosom.com/social/postFiles/images/" + theme.getImg());


    }

    @Override
    public int getItemCount() {
        return themeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_img;


        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.theme_rv);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM_NORMAL;
    }
}