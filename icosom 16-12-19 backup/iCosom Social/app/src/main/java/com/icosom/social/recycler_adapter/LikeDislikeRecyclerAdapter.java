package com.icosom.social.recycler_adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.icosom.social.R;
import com.icosom.social.model.LikeDislikeModel;
import com.icosom.social.utility.CommonFunctions;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class LikeDislikeRecyclerAdapter extends RecyclerView.Adapter<LikeDislikeRecyclerAdapter.MyViewHolder>
{
    private LayoutInflater inflater;
    Context context;
    ArrayList<LikeDislikeModel> likeDislikeModels;

    public LikeDislikeRecyclerAdapter(Context context, ArrayList<LikeDislikeModel> likeDislikeModels)
    {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.likeDislikeModels = likeDislikeModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.item_like_dislike, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
       final LikeDislikeModel current = likeDislikeModels.get(position);
       holder.txt_name_like_dislike.setText(current.getFirstName() + " " + current.getLasttName());
        Glide.
                with(context).
                load(CommonFunctions.FETCH_IMAGES + current.getProfilePicture()).
                thumbnail(0.01f).
                apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                into(holder.iv_profile_like_dislike);
    }

    @Override
    public int getItemCount() {
        return likeDislikeModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView iv_profile_like_dislike;
        TextView txt_name_like_dislike;
        LinearLayout lay_frag_like_dislike;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            iv_profile_like_dislike = itemView.findViewById(R.id.iv_profile_like_dislike);
            txt_name_like_dislike = itemView.findViewById(R.id.txt_name_like_dislike);
            lay_frag_like_dislike = itemView.findViewById(R.id.lay_frag_like_dislike);
        }
    }
}