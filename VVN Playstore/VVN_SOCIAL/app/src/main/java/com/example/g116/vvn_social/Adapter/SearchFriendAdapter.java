package com.example.g116.vvn_social.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.g116.vvn_social.Home_Activities.ChatActivity;
import com.example.g116.vvn_social.Modal.SearchResult;
import com.example.g116.vvn_social.R;


import java.util.List;

/**
 * Created by Allen on 10/30/2017.
 */

public class SearchFriendAdapter extends RecyclerView.Adapter<SearchFriendAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    Context context;
    private List<SearchResult> listModelSearchResult;

    public SearchFriendAdapter(Context context, List<SearchResult> listModelSearchResult) {
        inflater = LayoutInflater.from(context);
        this.listModelSearchResult = listModelSearchResult;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_search_result, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final SearchResult current = listModelSearchResult.get(position);
        Glide.with(context).load(current.getProfilePicture()).into(holder.ivProfile);

        holder.tvName.setText(current.getFirstName().concat(" ").concat(current.getLastName()));
        holder.tvPlace.setText("Id : "+current.getId());
        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ChatActivity.class)
                        .putExtra("friend_id", current.getId())
                        .putExtra("friend_name", current.getFirstName() + " " + current.getLastName())

                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return listModelSearchResult.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfile;
        TextView tvName;
        TextView tvPlace;
        CardView cvSearch;
        LinearLayout main_layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            main_layout = itemView.findViewById(R.id.main_layout);
            ivProfile = itemView.findViewById(R.id.iv_profile_search);
            tvName = (TextView)itemView.findViewById(R.id.tv_profile_name_search);
            tvPlace = (TextView)itemView.findViewById(R.id.tv_profile_place_search);
            cvSearch = (CardView)itemView.findViewById(R.id.cv_search);
        }
    }
}