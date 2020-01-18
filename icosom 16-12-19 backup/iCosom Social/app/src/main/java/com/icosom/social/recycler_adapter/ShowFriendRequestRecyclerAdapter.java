package com.icosom.social.recycler_adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.icosom.social.R;
import com.icosom.social.fragment.ShowFriendRequestFragment;
import com.icosom.social.model.FriendRequestModel;
import com.icosom.social.utility.CommonFunctions;

import java.util.ArrayList;

public class ShowFriendRequestRecyclerAdapter extends RecyclerView.Adapter<ShowFriendRequestRecyclerAdapter.ViewHolder>
{
    Context context;
    ArrayList<FriendRequestModel> friendRequestModels;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM_NORMAL = 1;
    ShowFriendRequestFragment fragment;

    public ShowFriendRequestRecyclerAdapter(Context context, ArrayList<FriendRequestModel> friendRequestModels,
                                            ShowFriendRequestFragment fragment)
    {
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
            holder.txt_name.setText(friendRequestModels.get(position-1).getFriendsFirstName() + " "
                    + friendRequestModels.get(position-1).getFriendsLastName());

            Glide.
                    with(context).
                    load(CommonFunctions.FETCH_IMAGES+friendRequestModels.get(position-1).getFriendsProfile()).
                    thumbnail(0.01f).
                    apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                    into(holder.iv_profile);

            holder.lay_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.txt_confirm.setVisibility(View.GONE);
                    holder.pb_confirm.setVisibility(View.VISIBLE);
                    (fragment).acceptFriendRequests(friendRequestModels.get(position-1).getFriendsId(), position-1);
                }
            });

            holder.lay_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.txt_delete.setVisibility(View.GONE);
                    holder.pb_delete.setVisibility(View.VISIBLE);
                    (fragment).deleteFriendRequests(friendRequestModels.get(position-1).getFriendsId(), position-1);
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
        ImageView iv_profile;
        TextView txt_name;
        TextView txt_confirm;
        TextView txt_delete;
        LinearLayout lay_confirm;
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
            lay_delete = itemView.findViewById(R.id.lay_delete);
            pb_confirm = itemView.findViewById(R.id.pb_confirm);
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