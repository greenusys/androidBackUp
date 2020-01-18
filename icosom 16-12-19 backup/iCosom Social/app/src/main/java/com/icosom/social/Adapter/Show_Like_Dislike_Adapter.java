package com.icosom.social.Adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.icosom.social.R;
import com.icosom.social.model.Like_Dislike_User;


import java.util.List;

public class Show_Like_Dislike_Adapter extends RecyclerView.Adapter<Show_Like_Dislike_Adapter.MyViewHolder> {


    Context context;
    private List<Like_Dislike_User> list;

    public Show_Like_Dislike_Adapter(Context context, List<Like_Dislike_User> listModelSearchResult) {
        this.list = listModelSearchResult;
        this.context = context;
    }


    @Override
    public Show_Like_Dislike_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_search_result,parent,false);
        Show_Like_Dislike_Adapter.MyViewHolder holder = new Show_Like_Dislike_Adapter.MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final Show_Like_Dislike_Adapter.MyViewHolder holder, int position) {
        final Like_Dislike_User current = list.get(position);
        Glide.with(context).load(""+ current.getImage()).into(holder.ivProfile);
        holder.tvName.setText(list.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfile;
        TextView tvName;
        LinearLayout main_layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            main_layout = itemView.findViewById(R.id.main_layout);
            ivProfile = itemView.findViewById(R.id.iv_profile_search);
            tvName = (TextView)itemView.findViewById(R.id.tv_profile_name_search);

        }
    }
}
