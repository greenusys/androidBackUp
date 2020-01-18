package com.icosom.social.recycler_adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.icosom.social.R;
import com.icosom.social.activity.TagFriendsActivity;
import com.icosom.social.model.TagUserModel;

import java.util.ArrayList;

public class SelectedTagFriendsRecyclerAdapter extends RecyclerView.Adapter<SelectedTagFriendsRecyclerAdapter.ViewHolder>
{
    Context context;
    ArrayList<TagUserModel> friendsName;

    public SelectedTagFriendsRecyclerAdapter(Context context, ArrayList<TagUserModel> friendsName)
    {
        this.context = context;
        this.friendsName = friendsName;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_tag_friends_recycler_layout, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.txt_selectedFriendsName.setText(friendsName.get(position).getName());
    }

    @Override
    public int getItemCount()
    {
        return friendsName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txt_selectedFriendsName;

        public ViewHolder(View itemView)
        {
            super(itemView);
            txt_selectedFriendsName = itemView.findViewById(R.id.txt_selectedFriendsName);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    ((TagFriendsActivity)context).removeSelectedTags(getAdapterPosition(), friendsName.get(getAdapterPosition()));
                }
            });
        }
    }
}