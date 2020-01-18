package com.icosom.social.Adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.icosom.social.R;
import com.icosom.social.activity.AddFeedActivity;
import com.icosom.social.model.Theme;
import com.icosom.social.utility.CommonFunctions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 08-08-2018.
 */

public class ThemeRecyclerAdapter extends RecyclerView.Adapter<ThemeRecyclerAdapter.ViewHolder>
{
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
    public ThemeRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

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
    public void onBindViewHolder(ThemeRecyclerAdapter.ViewHolder holder, final int position)
    {
        Theme theme = themeList.get(position);

        Glide.
                with(context).
                load(CommonFunctions.FETCH_IMAGES+theme.getImg()).
                into(holder.iv_img);


    }

    @Override
    public int getItemCount()
    {
        return themeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView iv_img;


        public ViewHolder(View itemView)
        {
            super(itemView);
            iv_img = itemView.findViewById(R.id.theme_rv);

        }
    }

    @Override
    public int getItemViewType(int position)
    {
        return TYPE_ITEM_NORMAL;
    }
}