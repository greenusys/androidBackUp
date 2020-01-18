package com.icosom.social.recycler_adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.icosom.social.R;
import com.icosom.social.activity.ProfileActivity;
import com.icosom.social.activity.SearchActivity;
import com.icosom.social.model.SearchUserModel;
import com.icosom.social.utility.CommonFunctions;
import com.icosom.social.utility.DBHelper;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.MyViewHolder>
{
    private LayoutInflater inflater;
    Context context;
    ArrayList<SearchUserModel> searchUserModels;
    DBHelper db;
    SearchActivity searchActivity;

    public SearchRecyclerAdapter(Context context, ArrayList<SearchUserModel> searchUserModels, DBHelper db,
                                 SearchActivity searchActivity)
    {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.db = db;
        this.searchUserModels = searchUserModels;
        this.searchActivity = searchActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.search_layout_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        holder.lay_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor c = db.getDataFromUser();
                boolean isExist = true;
                if (c.getCount()>0)
                {
                    c.moveToFirst();
                    do {
                        if (!c.getString(0).equalsIgnoreCase(searchUserModels.get(position).getUserId()))
                        {
                            isExist = false;
                        }
                    }
                    while (c.moveToNext());

                    if (!isExist)
                    {
                        db.insertDataToUser(
                                searchUserModels.get(position).getUserId(),
                                searchUserModels.get(position).getFirstName(),
                                searchUserModels.get(position).getLastName(),
                                searchUserModels.get(position).getProfile(),
                                searchUserModels.get(position).getCover()
                        );
                    }

                    goToProfile(searchUserModels.get(position).getUserId());
                }
                else
                {
                    if (db.insertDataToUser(
                            searchUserModels.get(position).getUserId(),
                            searchUserModels.get(position).getFirstName(),
                            searchUserModels.get(position).getLastName(),
                            searchUserModels.get(position).getProfile(),
                            searchUserModels.get(position).getCover()
                    ))
                    {
                        goToProfile(searchUserModels.get(position).getUserId());
                    }
                    else
                    {
                        goToProfile(searchUserModels.get(position).getUserId());
                    }
                }

                c.close();
            }
        });

        Glide.
                with(context).
                load(CommonFunctions.FETCH_IMAGES+searchUserModels.get(position).getProfile()).
                apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                thumbnail(0.01f).
                into(holder.iv_profile);

        holder.txt_name.setText(searchUserModels.get(position).getFirstName()+" "+searchUserModels.get(position).getLastName());
    }

    private void goToProfile(String userId)
    {
        context.startActivity(new Intent(context, ProfileActivity.class).
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                putExtra("userId", userId));
        searchActivity.finish();
    }

    @Override
    public int getItemCount() {
        return searchUserModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView iv_profile;
        TextView txt_name;
        LinearLayout lay_search;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            iv_profile = itemView.findViewById(R.id.iv_profile);
            txt_name = itemView.findViewById(R.id.txt_name);
            lay_search = itemView.findViewById(R.id.lay_search);
        }
    }
}