package com.icosom.social.recycler_adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.icosom.social.R;
import com.icosom.social.activity.ShowSubMediaActivity;
import com.icosom.social.model.Videostore_model;
import com.icosom.social.utility.CommonFunctions;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Greenusys on 25-07-2019.
 */

public class Videostore_adapter_rev extends RecyclerView.Adapter<Videostore_adapter_rev.Myviweholder>{
    List<Videostore_model> videostore_model ;
    Context context;

    public Videostore_adapter_rev(Context context, List<Videostore_model>videostore_models) {
        this.videostore_model = videostore_models;
        this.context = context;
    }

    @NonNull
    @Override
    public Myviweholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.videostore_item,parent,false);
        Myviweholder myviweholder = new Myviweholder(view);
        return myviweholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviweholder holder, final int position) {
       final Videostore_model videostore = videostore_model.get(position);

       System.out.println("sayed_vide"+CommonFunctions.FETCH_VIDEOS +videostore_model.get(position ).getPostFileLists().get(0));
       // holder.txt.setText(videostore.getName());

        Glide.
                with(context).
                load(CommonFunctions.FETCH_VIDEOS + videostore_model.get(position).getPostFileLists().get(0)).
                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progress.setVisibility(View.VISIBLE);
                        holder.videos.setImageResource(R.drawable.placeholder);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progress.setVisibility(View.GONE);
                        return false;
                    }
                }).
                thumbnail(0.01f).
                into(holder.videos);

        System.out.println("test"+"https://icosom.com/social/postFiles/images/"+videostore_model.get(position).getProfilepic());

        Glide.
                with(context).
                load("https://icosom.com/social/postFiles/images/"+videostore_model.get(position).getProfilepic()).
                thumbnail(0.01f).
                apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                into(holder.profilepic);

        holder.videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                        putExtra("imgs", videostore_model.get(position).getPostFileLists()).
                        putExtra("isVideos", true).
                        putExtra("position", 0));
            }
        });



      holder.user_profile_name.setText(videostore_model.get(position).getUser_profile_name());
      holder.date.setText(videostore_model.get(position).getUser_post_date());
        System.out.println("name"+videostore_model.get(position).getUser_profile_name()+"time"+videostore_model.get(position).getUser_post_date());


    }

    @Override
    public int getItemCount() {
        return videostore_model.size();
    }

    public class Myviweholder extends RecyclerView.ViewHolder
    {
        TextView txt;
        TextView user_profile_name;
        TextView date;
        ImageView videos;
        CircleImageView profilepic;
        ProgressBar progress;
        public Myviweholder(View itemView) {
            super(itemView);

            txt = itemView.findViewById(R.id.txt);
            user_profile_name = itemView.findViewById(R.id.usr_profile_name);
            date = itemView.findViewById(R.id.use_post_time);
            videos = itemView.findViewById(R.id.videos);
            profilepic =itemView.findViewById(R.id.profile_img);
            progress = itemView.findViewById(R.id.progress);
        }
    }


}
