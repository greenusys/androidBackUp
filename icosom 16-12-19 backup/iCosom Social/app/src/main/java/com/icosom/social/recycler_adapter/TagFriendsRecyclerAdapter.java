package com.icosom.social.recycler_adapter;

import android.content.Context;
import android.graphics.Color;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icosom.social.R;
import com.icosom.social.activity.TagFriendsActivity;
import com.icosom.social.model.TagUserModel;

import java.util.ArrayList;

public class TagFriendsRecyclerAdapter extends RecyclerView.Adapter<TagFriendsRecyclerAdapter.ViewHolder>
{
    Context context;
    ArrayList<TagUserModel> friendsName;
    ArrayList<Boolean> selected;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM_NORMAL = 1;

    public TagFriendsRecyclerAdapter(Context context, ArrayList<TagUserModel> friendsName, ArrayList<Boolean> selected)
    {
        this.context = context;
        this.friendsName = friendsName;
        this.selected = selected;
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
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_friends_recycler_layout, parent, false);
            ViewHolder holder = new ViewHolder(v);
            return holder;
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (position != 0)
        {
            holder.lay_background.setBackgroundColor(selected.get(position-1)? Color.parseColor("#ededed"): Color.parseColor("#ffffff"));
            holder.txt_initials.setText(friendsName.get(position-1).getName().substring(0, 1)+"");
            holder.txt_name.setText(friendsName.get(position-1).getName());
        }
    }

    @Override
    public int getItemCount()
    {
        return friendsName.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout lay_background;
        TextView txt_initials;
        TextView txt_name;

        public ViewHolder(View itemView)
        {
            super(itemView);
            lay_background = itemView.findViewById(R.id.lay_background);
            txt_initials = itemView.findViewById(R.id.txt_initials);
            txt_name = itemView.findViewById(R.id.txt_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getAdapterPosition() != 0)
                    {
                        if (selected.get(getAdapterPosition()-1))
                        {
                            ((TagFriendsActivity)context).removeSelectedFriends(friendsName.get(getAdapterPosition()-1));
                        }
                        else
                        {
                            ((TagFriendsActivity)context).addSelectedFriends(friendsName.get(getAdapterPosition()-1));
                        }

                        selected.set(getAdapterPosition()-1, !selected.get(getAdapterPosition()-1));
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0)
            return TYPE_HEADER;

        return TYPE_ITEM_NORMAL;
    }
}