package com.example.g116.vvn_social.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.g116.vvn_social.Modal.FriendRequestModel;

import com.example.g116.vvn_social.R;
import com.example.g116.vvn_social.Tab_Layout_Fragments.ShowFriendRequestFragment;
import com.example.g116.vvn_social.User_Profile_Dashboard_Activity.Friend_Profile_Dashboard;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowFriendRequestRecyclerAdapter extends RecyclerView.Adapter<ShowFriendRequestRecyclerAdapter.ViewHolder>
{
    Context context;
    String user_id;
    String user_type;
    ArrayList<FriendRequestModel> friendRequestModels;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM_NORMAL = 1;
    ShowFriendRequestFragment fragment;

    public ShowFriendRequestRecyclerAdapter(String user_id,String user_type,Context context, ArrayList<FriendRequestModel> friendRequestModels,
                                            ShowFriendRequestFragment fragment)
    {
        this.user_id = user_id;
        this.user_type = user_type;
        this.context = context;
        this.fragment = fragment;
        this.friendRequestModels = friendRequestModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == TYPE_HEADER)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_friends_recycler_header_layout, parent, false);
            ViewHolder holder = new ViewHolder(v);
            return holder;
        }
        if (viewType == TYPE_ITEM_NORMAL)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_friend_requests_layout_item, parent, false);
            ViewHolder holder = new ViewHolder(v);
            return holder;
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        if (position != 0)
        {
            holder.txt_name.setText(friendRequestModels.get(position-1).getFull_name());

         Glide.
                    with(context).
                    load (friendRequestModels.get(position-1).getPicture()).
                    thumbnail(0.01f).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(holder.iv_profile);

            holder.lay_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    holder.post__linear_layout.setVisibility(View.GONE);
                    holder.request_response_linear.setVisibility(View.VISIBLE);
                    holder.request_response_linear.setBackgroundColor(Color.parseColor("#18a702"));
                    //fragment.count_request.setText("Friend Request  "+String.valueOf(friendRequestModels.size()));
                   // holder.txt_confirm.setVisibility(View.GONE);
                   // holder.pb_confirm.setVisibility(View.VISIBLE);
                    (fragment).accept_or_reject_friend_request(friendRequestModels.get(position-1),user_id,user_type,"accept",friendRequestModels.get(position-1).getFriend_id());
                }
            });

            holder.lay_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    holder.post__linear_layout.setVisibility(View.GONE);
                    holder.request_response_linear.setVisibility(View.VISIBLE);
                    holder.request_response.setText("Friend Request Removed");
                    holder.request_response_linear.setBackgroundColor(Color.parseColor("#ff0900"));
                  /*  holder.txt_delete.setVisibility(View.GONE);
                    holder.pb_delete.setVisibility(View.VISIBLE);
                    (fragment).deleteFriendRequests(friendRequestModels.get(position-1).getFriendsId(), position-1);*/

                    (fragment).accept_or_reject_friend_request(friendRequestModels.get(position-1),user_id,user_type,"reject",friendRequestModels.get(position-1).getFriend_id());

                }
            });

            //open friend's profile
            holder.iv_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, Friend_Profile_Dashboard.class).
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                            putExtra("user_type","student").
                            putExtra("userId", friendRequestModels.get(position-1).getFriend_id()));


                }
            });




        }
    }

    @Override
    public int getItemCount()
    {
        return friendRequestModels.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView iv_profile;
        TextView txt_name;
        TextView txt_confirm;
        TextView txt_delete;
        TextView request_response;
        LinearLayout lay_confirm,request_response_linear,post__linear_layout;
        LinearLayout lay_delete;
        ProgressBar pb_confirm;
        ProgressBar pb_delete;


        public ViewHolder(View itemView)
        {
            super(itemView);
            iv_profile = itemView.findViewById(R.id.iv_profile);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_confirm = itemView.findViewById(R.id.txt_confirm);
            txt_delete = itemView.findViewById(R.id.txt_delete);
            lay_confirm = itemView.findViewById(R.id.lay_confirm);
            request_response = itemView.findViewById(R.id.request_response);
            lay_delete = itemView.findViewById(R.id.lay_delete);
            pb_confirm = itemView.findViewById(R.id.pb_confirm);
            request_response_linear = itemView.findViewById(R.id.request_response_linear);
            post__linear_layout = itemView.findViewById(R.id.post__linear_layout);

            pb_delete = itemView.findViewById(R.id.pb_delete);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;

        return TYPE_ITEM_NORMAL;
    }
}