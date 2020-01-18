package com.example.g116.vvn_social.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.g116.vvn_social.Home_Activities.AddFeedActivity;
import com.example.g116.vvn_social.Home_Activities.CommentActivity;
import com.example.g116.vvn_social.Home_Activities.ShowSubMediaActivity;
import com.example.g116.vvn_social.Modal.Home_Post_Model;
import com.example.g116.vvn_social.Modal.Image_String;
import com.example.g116.vvn_social.Network_Package.AppController;
import com.example.g116.vvn_social.R;
import com.example.g116.vvn_social.Tab_Layout_Fragments.HomeFragment;
import com.example.g116.vvn_social.User_Profile_Dashboard_Activity.Friend_Profile_Dashboard;
import com.example.g116.vvn_social.User_Profile_Dashboard_Activity.User_Profile_Dashboard;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by karsk on 25/04/2018.
 */

public class Home_Post_Adapter extends RecyclerView.Adapter<Home_Post_Adapter.MyViewHolder> {

    Context context;
    ArrayList<Home_Post_Model> modelFeedArrayList = new ArrayList<>();
    ArrayList<Image_String> image_list = new ArrayList<>();
    RequestManager glide;
    AppController appController;
    HomeFragment homeFragment;
    Home_Post_Adapter homePostAdapter;
    BottomSheetDialog bottomSheetDialog;
    String user_id = "", user_type = "";

    public Home_Post_Adapter(Home_Post_Adapter homePostAdapter, BottomSheetDialog bottomSheetDialog, AppController appController, String user_id, String user_type, Context context, ArrayList<Home_Post_Model> modelFeedArrayList, ArrayList<Image_String> image_list, HomeFragment homeFragment) {

        this.appController = appController;
        this.homePostAdapter = homePostAdapter;
        this.bottomSheetDialog = bottomSheetDialog;
        this.context = context;
        this.user_id = user_id;
        this.user_type = user_type;
        this.homeFragment = homeFragment;
        this.modelFeedArrayList = modelFeedArrayList;
        this.image_list = image_list;
        glide = Glide.with(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_page_test_2, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Home_Post_Model modelFeed = modelFeedArrayList.get(position);


        String path = "https://vvn.city/social/";


        //no images is coming
        if (modelFeed.getImage_list().size() == 0) {


            //  Log.e("No_Image", "" + position);
            holder.frame_postA.setVisibility(View.GONE);
            holder.three_frames_container.setVisibility(View.GONE);

           /* holder.progressA.setVisibility(View.GONE);
            holder.progressB.setVisibility(View.GONE);
            holder.progressC.setVisibility(View.GONE);
            holder.progressD.setVisibility(View.GONE);*/
        }

        //only 1 image is coming & set only 1 image
        else if (modelFeed.getImage_list().size() == 1) {
            holder.frame_postA.setVisibility(View.VISIBLE);
            holder.three_frames_container.setVisibility(View.GONE);

            holder.progressA.setVisibility(View.VISIBLE);
         /*   holder.progressB.setVisibility(View.GONE);
            holder.progressC.setVisibility(View.GONE);
            holder.progressD.setVisibility(View.GONE);*/
            //  glide.load(path+modelFeed.getImage_list().get(0)).into(holder.iv_postImgA);
            //  System.out.println("kaif_path"+path+modelFeed.getImage_list().get(0));

            //set only First Image
            Glide.with(context).
                    load(path + modelFeed.getImage_list().get(0)).
                    apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                    listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            // holder.progressA.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressA.setVisibility(View.GONE);
                            return false;
                        }
                    }).
                    thumbnail(0.01f).
                    into(holder.iv_postImgA);

        }


        //only 2 images is coming and set 2 images
        else if (modelFeed.getImage_list().size() == 2) {
            holder.three_frames_container.setVisibility(View.VISIBLE);
            holder.frame_postA.setVisibility(View.VISIBLE);
            holder.frame_postB.setVisibility(View.VISIBLE);
            holder.frame_postC.setVisibility(View.GONE);
            holder.frame_postD.setVisibility(View.GONE);

            holder.progressA.setVisibility(View.VISIBLE);
            holder.progressB.setVisibility(View.VISIBLE);
       /*     holder.progressC.setVisibility(View.GONE);
            holder.progressD.setVisibility(View.GONE);*/

            //set two Images
            Glide.with(context).
                    load(path + modelFeed.getImage_list().get(0)).
                    apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                    listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            // holder.progressA.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressA.setVisibility(View.GONE);
                            return false;
                        }
                    }).
                    thumbnail(0.01f).
                    into(holder.iv_postImgA);


            Glide.with(context).
                    load(path + modelFeed.getImage_list().get(1)).
                    apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                    listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressB.setVisibility(View.GONE);
                            return false;

                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressB.setVisibility(View.GONE);
                            return false;
                        }
                    }).
                    thumbnail(0.01f).
                    into(holder.iv_postImgB);

        }


        //only 3 images is coming and set 3 images
        else if (modelFeed.getImage_list().size() == 3) {
            holder.three_frames_container.setVisibility(View.VISIBLE);
            holder.frame_postA.setVisibility(View.VISIBLE);
            holder.frame_postB.setVisibility(View.VISIBLE);
            holder.frame_postC.setVisibility(View.VISIBLE);
            holder.frame_postD.setVisibility(View.GONE);

            holder.progressA.setVisibility(View.VISIBLE);
            holder.progressB.setVisibility(View.VISIBLE);
            holder.progressC.setVisibility(View.VISIBLE);
            //  holder.progressD.setVisibility(View.GONE);

            //set 3 Images
            Glide.with(context).
                    load(path + modelFeed.getImage_list().get(0)).
                    apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                    listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressA.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressA.setVisibility(View.GONE);
                            return false;
                        }
                    }).
                    thumbnail(0.01f).
                    into(holder.iv_postImgA);


            Glide.with(context).
                    load(path + modelFeed.getImage_list().get(1)).
                    apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                    listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressB.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressB.setVisibility(View.GONE);
                            return false;
                        }
                    }).
                    thumbnail(0.01f).
                    into(holder.iv_postImgB);

            Glide.with(context).
                    load(path + modelFeed.getImage_list().get(2)).
                    apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                    listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressC.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressC.setVisibility(View.GONE);
                            return false;
                        }
                    }).
                    thumbnail(0.01f).
                    into(holder.iv_postImgC);
        }


        //only 4 images is coming and set 4 images
        else if (modelFeed.getImage_list().size() == 4) {
            holder.three_frames_container.setVisibility(View.VISIBLE);
            holder.frame_postA.setVisibility(View.VISIBLE);
            holder.frame_postB.setVisibility(View.VISIBLE);
            holder.frame_postC.setVisibility(View.VISIBLE);
            holder.frame_postD.setVisibility(View.VISIBLE);
            holder.txt_postImgCount.setVisibility(View.GONE);

            holder.progressA.setVisibility(View.VISIBLE);
            holder.progressB.setVisibility(View.VISIBLE);
            holder.progressC.setVisibility(View.VISIBLE);
            holder.progressD.setVisibility(View.VISIBLE);


            //set 4 Images
            Glide.with(context).
                    load(path + modelFeed.getImage_list().get(0)).
                    apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                    listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressA.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressA.setVisibility(View.GONE);
                            return false;
                        }
                    }).
                    thumbnail(0.01f).
                    into(holder.iv_postImgA);


            Glide.with(context).
                    load(path + modelFeed.getImage_list().get(1)).
                    apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                    listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressB.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressB.setVisibility(View.GONE);
                            return false;
                        }
                    }).
                    thumbnail(0.01f).
                    into(holder.iv_postImgB);

            Glide.with(context).
                    load(path + modelFeed.getImage_list().get(2)).
                    apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                    listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressC.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressC.setVisibility(View.GONE);
                            return false;
                        }
                    }).
                    thumbnail(0.01f).
                    into(holder.iv_postImgC);

            Glide.with(context).
                    load(path + modelFeed.getImage_list().get(3)).
                    apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                    listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressD.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressD.setVisibility(View.GONE);
                            return false;
                        }
                    }).
                    thumbnail(0.01f).
                    into(holder.iv_postImgD);
        }

        //more than 4 images is coming and set 4 images
        else if (modelFeed.getImage_list().size() > 4) {
            holder.three_frames_container.setVisibility(View.VISIBLE);
            holder.frame_postA.setVisibility(View.VISIBLE);
            holder.frame_postB.setVisibility(View.VISIBLE);
            holder.frame_postC.setVisibility(View.VISIBLE);
            holder.frame_postD.setVisibility(View.VISIBLE);
            holder.txt_postImgCount.setVisibility(View.VISIBLE);
            holder.txt_postImgCount.setText((modelFeed.getImage_list().size()) - 4 + " More");

            //set 4 Images
            Glide.with(context).
                    load(path + modelFeed.getImage_list().get(0)).
                    apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                    listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressA.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressA.setVisibility(View.GONE);
                            return false;
                        }
                    }).
                    thumbnail(0.01f).
                    into(holder.iv_postImgA);


            Glide.with(context).
                    load(path + modelFeed.getImage_list().get(1)).
                    apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                    listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressB.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressB.setVisibility(View.GONE);
                            return false;
                        }
                    }).
                    thumbnail(0.01f).
                    into(holder.iv_postImgB);

            Glide.with(context).
                    load(path + modelFeed.getImage_list().get(2)).
                    apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                    listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressC.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressC.setVisibility(View.GONE);
                            return false;
                        }
                    }).
                    thumbnail(0.01f).
                    into(holder.iv_postImgC);

            Glide.with(context).
                    load(path + modelFeed.getImage_list().get(3)).
                    apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                    listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressD.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressD.setVisibility(View.GONE);
                            return false;
                        }
                    }).
                    thumbnail(0.01f).
                    into(holder.iv_postImgD);

        }
        //closing display post images


        //view post images
        holder.frame_postA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!modelFeed.getView()) {
                    modelFeed.setView(true);
                    modelFeed.setTotal_views(String.valueOf(Integer.parseInt(modelFeed.getTotal_views()) + 1));
                    notifyDataSetChanged();
                }


                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                        putExtra("images_list", modelFeed.getImage_list()).
                        putExtra("isVideos", false).
                        putExtra("post_id", modelFeed.getPost_id()).
                        putExtra("position", 0));
            }
        });

        holder.frame_postB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!modelFeed.getView()) {
                    modelFeed.setView(true);
                    modelFeed.setTotal_views(String.valueOf(Integer.parseInt(modelFeed.getTotal_views()) + 1));
                    notifyDataSetChanged();
                }
                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                        putExtra("images_list", modelFeed.getImage_list()).
                        putExtra("post_id", modelFeed.getPost_id()).
                        putExtra("isVideos", false).
                        putExtra("position", 1));
            }
        });
        holder.frame_postC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!modelFeed.getView()) {
                    modelFeed.setView(true);
                    modelFeed.setTotal_views(String.valueOf(Integer.parseInt(modelFeed.getTotal_views()) + 1));
                    notifyDataSetChanged();
                }
                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                        putExtra("images_list", modelFeed.getImage_list()).
                        putExtra("post_id", modelFeed.getPost_id()).
                        putExtra("isVideos", false).
                        putExtra("position", 2));
            }
        });
        holder.frame_postD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!modelFeed.getView()) {
                    modelFeed.setView(true);
                    modelFeed.setTotal_views(String.valueOf(Integer.parseInt(modelFeed.getTotal_views()) + 1));
                    notifyDataSetChanged();
                }
                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                        putExtra("images_list", modelFeed.getImage_list()).
                        putExtra("post_id", modelFeed.getPost_id()).
                        putExtra("isVideos", false).
                        putExtra("position", 3));
            }
        });

        //closing view post images


        //set post user profile pic
        Glide.with(context)
                .load("https://vvn.city/" + modelFeed.getUser_profile_pic()).
                apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                into(holder.imgView_proPic);
        ;

        System.out.println("adapter_user_pic" + modelFeed.getUser_profile_pic());


        //set user's name,course and post time
        holder.tv_name.setText(modelFeed.getFull_name());
        holder.course_name.setText(modelFeed.getEducation_detail());
        holder.tv_time.setText(modelFeed.getPostTime());


        //it is for to prevent the set 0 likes,dislikes,views and comments to the view
        if (modelFeed.getTotalLikes().equals("0")) {
            holder.total_likes.setVisibility(View.INVISIBLE);
        } else {
            holder.total_likes.setVisibility(View.VISIBLE);
            holder.total_likes.setText(String.valueOf(modelFeed.getTotalLikes()));
        }


        if (modelFeed.getTotalDislikes().equals("0"))
            holder.total_dislikes.setVisibility(View.INVISIBLE);
        else {
            holder.total_dislikes.setVisibility(View.VISIBLE);
            holder.total_dislikes.setText(String.valueOf(modelFeed.getTotalDislikes()));
        }

        if (modelFeed.getTotal_views().equals("0"))
            holder.total_view.setVisibility(View.INVISIBLE);
        else {
            holder.total_view.setVisibility(View.VISIBLE);
            holder.total_view.setText(String.valueOf(modelFeed.getTotal_views()));
        }

        if (modelFeed.getTotalComments().equals("0"))
            holder.total_comments.setVisibility(View.INVISIBLE);
        else {
            holder.total_comments.setVisibility(View.VISIBLE);
            holder.total_comments.setText(String.valueOf(modelFeed.getTotalComments()));
        }
//close

        //set post status
        holder.tv_status.setText(modelFeed.getPost_status());

        //set like and dislike images
        holder.iv_postLike.setImageResource(
                modelFeedArrayList.get(position).getMyLike() ? R.drawable.ic_like_fill : R.drawable.ic_like);

        holder.iv_postDislike.setImageResource(
                modelFeedArrayList.get(position).getMyDislike() ? R.drawable.ic_dislike_fill : R.drawable.ic_dislike
        );


        //like post
        holder.like_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.total_likes.setVisibility(View.VISIBLE);
                //if post has like (true)
                if (modelFeedArrayList.get(position).getMyLike()) {
                    modelFeedArrayList.get(position).
                            setTotalLikes((Integer.parseInt(modelFeedArrayList.get(position).getTotalLikes()) - 1) + "");
                }

                //first it will execute
                else {
                    modelFeedArrayList.get(position).
                            setTotalLikes((Integer.parseInt(modelFeedArrayList.get(position).getTotalLikes()) + 1) + "");
                }

                //update setMyLike value of this post position
                modelFeedArrayList.get(position).setMyLike(!modelFeedArrayList.get(position).getMyLike());


                if (modelFeedArrayList.get(position).getMyDislike()) {
                    modelFeedArrayList.get(position).setMyDislike(false);
                    modelFeedArrayList.get(position).
                            setTotalDislikes((Integer.parseInt(modelFeedArrayList.get(position).getTotalDislikes()) - 1) + "");
                }

                notifyDataSetChanged();
                homeFragment.put_like(appController, modelFeed.getPost_id(), "like", user_id, user_type);

                if (!modelFeed.getView()) {
                    modelFeed.setView(true);
                    modelFeed.setTotal_views(String.valueOf(Integer.parseInt(modelFeed.getTotal_views()) + 1));
                    notifyDataSetChanged();
                    homeFragment.view_post(appController, user_id, modelFeed.getPost_id());
                    holder.total_view.setVisibility(View.VISIBLE);
                }


            }
        });


        //dislike post
        holder.dislike_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                holder.total_dislikes.setVisibility(View.VISIBLE);
                if (modelFeedArrayList.get(position).getMyDislike()) {
                    modelFeedArrayList.get(position).
                            setTotalDislikes((Integer.parseInt(modelFeedArrayList.get(position).getTotalDislikes()) - 1) + "");
                }

                //first it will execute
                else {
                    modelFeedArrayList.get(position).
                            setTotalDislikes((Integer.parseInt(modelFeedArrayList.get(position).getTotalDislikes()) + 1) + "");
                }

                modelFeedArrayList.get(position).setMyDislike(!modelFeedArrayList.get(position).getMyDislike());
                if (modelFeedArrayList.get(position).getMyLike()) {
                    modelFeedArrayList.get(position).setMyLike(false);
                    modelFeedArrayList.get(position).
                            setTotalLikes((Integer.parseInt(modelFeedArrayList.get(position).getTotalLikes()) - 1) + "");
                }


                notifyDataSetChanged();
                homeFragment.put_like(appController, modelFeed.getPost_id(), "dislike", user_id, user_type);

                if (!modelFeed.getView()) {
                    modelFeed.setView(true);
                    modelFeed.setTotal_views(String.valueOf(Integer.parseInt(modelFeed.getTotal_views()) + 1));
                    notifyDataSetChanged();
                    homeFragment.view_post(appController, user_id, modelFeed.getPost_id());
                    holder.total_view.setVisibility(View.VISIBLE);
                }


            }
        });


        //it will shows all post comment
        holder.comment_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!modelFeed.getView()) {
                    modelFeed.setView(true);
                    modelFeed.setTotal_views(String.valueOf(Integer.parseInt(modelFeed.getTotal_views()) + 1));
                    notifyDataSetChanged();
                    homeFragment.view_post(appController, user_id, modelFeed.getPost_id());
                }

                context.startActivity(new Intent(context, CommentActivity.class)
                        .putExtra("post_id", modelFeed.getPost_id())
                        .putExtra("user_id", user_id)
                        .putExtra("user_pic", "https://vvn.city/" + modelFeed.getUser_profile_pic())
                        .putExtra("type", user_type));

            }
        });


        //user can edit only their posts
        if (user_id.equals(modelFeedArrayList.get(position).getPost_user_id()))
            holder.iv_postMore.setVisibility(View.VISIBLE);
        else
            holder.iv_postMore.setVisibility(View.GONE);


        //it will show option for edit and delete posts
        holder.iv_postMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.iv_postMore);
                //inflating menu from xml resource
                popup.inflate(R.menu.comment_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:

                                ((Activity) context).startActivityForResult(new Intent(context, AddFeedActivity.class).
                                        putExtra("share", true).
                                        putExtra("edit", true).
                                        putExtra("position", position).
                                        putExtra("postId", modelFeedArrayList.get(position).getPost_id()).
                                        putExtra("name", modelFeedArrayList.get(position).getPost_User_Name()).
                                        putExtra("desc", modelFeedArrayList.get(position).getPost_status()), 30);


                                break;
                            case R.id.delete:
                                //  Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();

                                homeFragment.deletePost(appController, modelFeedArrayList, user_id, user_type, position);
                                modelFeedArrayList.remove(modelFeedArrayList.get(position));
                                notifyDataSetChanged();


                                break;

                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();


            }
        });


        //open friend's profile
        holder.imgView_proPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!user_id.equals(modelFeedArrayList.get(position).getPost_user_id()))
                    context.startActivity(new Intent(context, Friend_Profile_Dashboard.class).
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                            putExtra("user_type", modelFeedArrayList.get(position).getUser_type()).
                            putExtra("userId", modelFeedArrayList.get(position).getPost_user_id()));

                else
                    context.startActivity(new Intent(context, User_Profile_Dashboard.class));


            }
        });


    }


    @Override
    public int getItemCount() {
        return modelFeedArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_postImgCount, tv_name, tv_time, total_view, total_likes, total_dislikes, total_comments, tv_status, course_name;
        ImageView imgView_postPic;
        CircleImageView imgView_proPic;
        LinearLayout like_layout, dislike_layout, comment_layout, three_frames_container;
        FrameLayout frame_postA, frame_postB, frame_postC, frame_postD;
        ImageView iv_postImgA, iv_postImgB, iv_postImgC, iv_postImgD;
        ImageView iv_postLike, iv_postDislike;
        ImageView iv_postMore;
        ProgressBar progressA, progressB, progressC, progressD;

        public MyViewHolder(View itemView) {
            super(itemView);

            progressA = (ProgressBar) itemView.findViewById(R.id.progressA);
            progressB = (ProgressBar) itemView.findViewById(R.id.progressB);
            progressC = (ProgressBar) itemView.findViewById(R.id.progressC);
            progressD = (ProgressBar) itemView.findViewById(R.id.progressD);


            iv_postMore = (ImageView) itemView.findViewById(R.id.iv_postMore);


            iv_postImgA = (ImageView) itemView.findViewById(R.id.iv_postImgA);
            iv_postImgB = (ImageView) itemView.findViewById(R.id.iv_postImgB);
            iv_postImgC = (ImageView) itemView.findViewById(R.id.iv_postImgC);
            iv_postImgD = (ImageView) itemView.findViewById(R.id.iv_postImgD);

            frame_postA = (FrameLayout) itemView.findViewById(R.id.frame_postA);
            frame_postB = (FrameLayout) itemView.findViewById(R.id.frame_postB);
            frame_postC = (FrameLayout) itemView.findViewById(R.id.frame_postC);
            frame_postD = (FrameLayout) itemView.findViewById(R.id.frame_postD);

            three_frames_container = (LinearLayout) itemView.findViewById(R.id.three_frames_container);


            imgView_proPic = (CircleImageView) itemView.findViewById(R.id.imgView_proPic);
            imgView_postPic = (ImageView) itemView.findViewById(R.id.imgView_postPic);

            like_layout = (LinearLayout) itemView.findViewById(R.id.like_layout);
            dislike_layout = (LinearLayout) itemView.findViewById(R.id.dislike_layout);

            iv_postLike = (ImageView) itemView.findViewById(R.id.iv_postLike);
            iv_postDislike = (ImageView) itemView.findViewById(R.id.iv_postDislike);

            comment_layout = (LinearLayout) itemView.findViewById(R.id.comment_layout);


            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            course_name = (TextView) itemView.findViewById(R.id.course_name);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            total_likes = (TextView) itemView.findViewById(R.id.total_likes);
            total_dislikes = (TextView) itemView.findViewById(R.id.total_dislikes);
            total_comments = (TextView) itemView.findViewById(R.id.total_comments);
            total_view = (TextView) itemView.findViewById(R.id.total_view);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
            txt_postImgCount = (TextView) itemView.findViewById(R.id.txt_postImgCount);


        }
    }
}
