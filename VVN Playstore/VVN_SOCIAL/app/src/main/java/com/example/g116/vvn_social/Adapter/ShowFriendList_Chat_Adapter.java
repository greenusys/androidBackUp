package com.example.g116.vvn_social.Adapter;

import android.content.Context;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.example.g116.vvn_social.Home_Activities.ChatActivity;
import com.example.g116.vvn_social.Modal.Friend_List_Model;
import com.example.g116.vvn_social.R;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowFriendList_Chat_Adapter extends RecyclerView.Adapter<ShowFriendList_Chat_Adapter.ViewHolder>
{
    Context context;
    ArrayList<Friend_List_Model> friendRequestModels;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM_NORMAL = 1;


    public ShowFriendList_Chat_Adapter(Context context, ArrayList<Friend_List_Model> friendRequestModels)
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
    public void onBindViewHolder(final ViewHolder holder,  int position)
    {

        final Friend_List_Model friend_list_model = friendRequestModels.get(position);


            holder.txt_name.setText(friend_list_model.getFirstName() + " "
                    + friend_list_model.getLastName());

            holder.course_name.setText(friend_list_model.getEducationDetails());


         Glide.
                    with(context).
                    load(friend_list_model.getPicture()).
                    thumbnail(0.01f).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(holder.iv_profile);




              holder.main_layout.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      context.startActivity(new Intent(context,ChatActivity.class)
                              .putExtra("friend_id",friend_list_model.getFriend_id())
                              .putExtra("friend_name",friend_list_model.getFirstName()+" "+friend_list_model.getLastName())

                      );
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
        LinearLayout main_layout;




        public ViewHolder(View itemView)
        {
            super(itemView);
            iv_profile = itemView.findViewById(R.id.iv_profile);
            main_layout = itemView.findViewById(R.id.main_layout);
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