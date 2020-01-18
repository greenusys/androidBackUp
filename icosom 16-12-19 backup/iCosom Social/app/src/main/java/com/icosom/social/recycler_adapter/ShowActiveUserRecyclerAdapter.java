package com.icosom.social.recycler_adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.icosom.social.R;
import com.icosom.social.model.ActiveUserModel;
import com.icosom.social.utility.CommonFunctions;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowActiveUserRecyclerAdapter extends RecyclerView.Adapter<ShowActiveUserRecyclerAdapter.ViewHolder>
{
    Context context;
    ArrayList<ActiveUserModel> activeUserModels;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM_NORMAL = 1;

    public ShowActiveUserRecyclerAdapter(Context context, ArrayList<ActiveUserModel> activeUserModels)
    {
        this.context = context;
        this.activeUserModels = activeUserModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == TYPE_HEADER)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_user_recycler_header_layout, parent, false);
            ViewHolder holder = new ViewHolder(v);
            return holder;
        }
        if (viewType == TYPE_ITEM_NORMAL)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_active_user_layout, parent, false);
            ViewHolder holder = new ViewHolder(v);
            return holder;
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position != 0)
        {
            holder.txt_name.setText(activeUserModels.get(position-1).getFriendsFirstName() + " "
                    + activeUserModels.get(position-1).getFriendsLastName());
            Glide.
                    with(context).
                    load(CommonFunctions.FETCH_IMAGES+activeUserModels.get(position-1).getFriendsProfilePic()).
                    thumbnail(0.01f).
                    apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                    into(holder.iv_profile);
        }
    }

    @Override
    public int getItemCount()
    {
        return activeUserModels.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView iv_profile;
        TextView txt_name;

        public ViewHolder(View itemView)
        {
            super(itemView);
            iv_profile = itemView.findViewById(R.id.iv_profile);
            txt_name = itemView.findViewById(R.id.txt_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.greenusys.icosom.messenger");
                    if (intent == null) {
                        // Bring user to the market or let them choose an app?
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("market://details?id=com.greenusys.icosom.messenger"));
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;

        return TYPE_ITEM_NORMAL;
    }
}