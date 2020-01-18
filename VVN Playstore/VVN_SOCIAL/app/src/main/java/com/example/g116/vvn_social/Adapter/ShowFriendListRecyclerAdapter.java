package com.example.g116.vvn_social.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.g116.vvn_social.Modal.Friend_List_Model;
import com.example.g116.vvn_social.Modal.Image;
import com.example.g116.vvn_social.R;
import com.example.g116.vvn_social.User_Profile_Dashboard_Activity.Friend_Profile_Dashboard;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowFriendListRecyclerAdapter extends RecyclerView.Adapter<ShowFriendListRecyclerAdapter.ViewHolder>
{
    Context context;
    ArrayList<Friend_List_Model> friendRequestModels;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM_NORMAL = 1;


    public ShowFriendListRecyclerAdapter(Context context, ArrayList<Friend_List_Model> friendRequestModels)
    {
        this.context = context;
        this.friendRequestModels = friendRequestModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_friend_list_layout_item, parent, false);
            ViewHolder holder = new ViewHolder(v);
            return holder;


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {

        final Friend_List_Model friend_list_model = friendRequestModels.get(position);


            holder.txt_name.setText(friend_list_model.getFirstName() + " "
                    + friend_list_model.getLastName());

            holder.course_name.setText(friend_list_model.getEducationDetails());


         Glide.
                    with(context).
                    load(friend_list_model.getPicture()).
                    thumbnail(0.01f).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(holder.iv_profile);



        //open friend's profile
        holder.iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, Friend_Profile_Dashboard.class).
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                        putExtra("user_type",friend_list_model.getUser_type()).
                        putExtra("userId", friend_list_model.getUser_id()));


            }
        });



    }

    @Override
    public int getItemCount()
    {
        return friendRequestModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView iv_profile;
        TextView txt_name;
        TextView course_name;




        public ViewHolder(View itemView)
        {
            super(itemView);
            iv_profile = itemView.findViewById(R.id.iv_profile);
            txt_name = itemView.findViewById(R.id.txt_name);
            course_name = itemView.findViewById(R.id.course_name);


        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;

        return TYPE_ITEM_NORMAL;
    }
}