package com.example.g116.vvn_social.User_Profile_Dashboard_Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.g116.vvn_social.Home_Activities.ShowSubMediaActivity;
import com.example.g116.vvn_social.Network_Package.AppController;
import com.example.g116.vvn_social.R;
import com.example.g116.vvn_social.Tab_Layout_Fragments.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SinglePostActivity extends AppCompatActivity {


    String post_id="";
    String user_name="";
    String user_pic="";
    String education="";
    String time="";
    FrameLayout frame_postA,frame_postB,frame_postC,frame_postD;
    ImageView iv_postImgA,iv_postImgB,iv_postImgC,iv_postImgD;
    LinearLayout like_layout,dislike_layout,comment_layout,three_frames_container;
    ProgressBar progressA,progressB,progressC,progressD;
    private AppController appController;
    private TextView txt_postImgCount;
    private TextView tv_status;
    private TextView tv_time;
    private TextView tv_name;
    private TextView course_name;
    private CircleImageView imgView_proPic;
    TextView total_view,total_likes,total_dislikes, total_comments;
    public static int backfrom;
    ImageView iv_postLike,iv_postDislike;

      boolean getMyLike=false;
      boolean getMyDislike=false;
    int count_like=0;
    int count_dislike=0;

    Boolean like = false;
    Boolean dislike = false;
    int likeCount = 0;
    int dislikeCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);

        appController = (AppController) getApplicationContext();

        iv_postImgA = (ImageView) findViewById(R.id.iv_postImgA);
        iv_postImgB = (ImageView) findViewById(R.id.iv_postImgB);
        iv_postImgC = (ImageView) findViewById(R.id.iv_postImgC);
        iv_postImgD = (ImageView) findViewById(R.id.iv_postImgD);

        txt_postImgCount = (TextView) findViewById(R.id.txt_postImgCount);

        iv_postLike = (ImageView) findViewById(R.id.iv_postLike);
        iv_postDislike = (ImageView) findViewById(R.id.iv_postDislike);

        frame_postA = (FrameLayout) findViewById(R.id.frame_postA);
        frame_postB = (FrameLayout) findViewById(R.id.frame_postB);
        frame_postC = (FrameLayout) findViewById(R.id.frame_postC);
        frame_postD = (FrameLayout) findViewById(R.id.frame_postD);

        progressA = (ProgressBar) findViewById(R.id.progressA);
        progressB = (ProgressBar) findViewById(R.id.progressB);
        progressC = (ProgressBar) findViewById(R.id.progressC);
        progressD = (ProgressBar) findViewById(R.id.progressD);


        three_frames_container = (LinearLayout) findViewById(R.id.three_frames_container);

        tv_status = (TextView) findViewById(R.id.tv_status);
        tv_time = (TextView) findViewById(R.id.tv_time);

        tv_name = (TextView)findViewById(R.id.tv_name);
        course_name = (TextView) findViewById(R.id.course_name);

        imgView_proPic = (CircleImageView) findViewById(R.id.imgView_proPic);
        
        total_likes = (TextView) findViewById(R.id.total_likes);
        total_dislikes= (TextView) findViewById(R.id.total_dislikes);
        total_comments = (TextView) findViewById(R.id.total_comments);
        total_view = (TextView) findViewById(R.id.total_view);

        iv_postLike = (ImageView)findViewById(R.id.iv_postLike);
        iv_postDislike = (ImageView)findViewById(R.id.iv_postDislike);

        like_layout = (LinearLayout) findViewById(R.id.like_layout);
        dislike_layout = (LinearLayout)findViewById(R.id.dislike_layout);



        if(getIntent().getStringExtra("post_id")!=null) {
            post_id = getIntent().getStringExtra("post_id");
            getSingle_Post_Details(post_id);
        }
        if(getIntent().getStringExtra("user_name")!=null) {
            user_name = getIntent().getStringExtra("user_name");
            tv_name.setText(user_name);

        }

        if(getIntent().getStringExtra("user_pic")!=null) {
            user_pic = getIntent().getStringExtra("user_pic");

        }
        if(getIntent().getStringExtra("education")!=null) {
            education = getIntent().getStringExtra("education");
            course_name.setText(education);

        }

        if(getIntent().getStringExtra("time")!=null) {
            time = getIntent().getStringExtra("time");
            tv_time.setText(time);

        }


        Glide.with(getApplicationContext())
                .load(user_pic).
                apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                into(imgView_proPic);

        System.out.println("single_post_post_id"+post_id);
        System.out.println("single_post_user_name"+user_name);
        System.out.println("single_post_user_pic"+user_pic);

    }



    public void getSingle_Post_Details(final String postId) {

        System.out.println("getSingle_Post_Details_api_called " + postId);

        RequestBody body = new FormBody.Builder().
                add("post_id", postId).
                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/single_post.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                SinglePostActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();

                    }
                });
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    JSONObject main = new JSONObject(myResponse);
                    System.out.println("getSingle_Post_Details_status " + main.getString("status"));
                    final String path = "https://vvn.city/social/";
                    if(main.getString("status").equals("1"))
                    {

                        JSONArray jsonArray = main.getJSONArray("data");
                        String server_time=main.getString("servertime");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject item = jsonArray.getJSONObject(i);

                            String id=item.getString("id");
                            final String userId=item.getString("userId");
                            String user_type=item.getString("user_type");
                            final String totalLikes=item.getString("totalLikes");
                            final String totalDislikes=item.getString("totalDislikes");
                            final String totalComments=item.getString("totalComments");
                            final String description=item.getString("description");
                            String postTime=item.getString("postTime");

                            likeCount = Integer.parseInt(totalLikes);
                            dislikeCount = Integer.parseInt(totalDislikes);

                            like = !item.getString("totalLikes").equals("0");
                            dislike = !item.getString("totalDislikes").equals("0");

                            String value= item.getString("user_type");
                            if(value.equals("1"))
                                user_type="teacher";
                            if(value.equals("2"))
                                user_type="student";


                            iv_postLike.setImageResource(!totalLikes.equals("0") ? R.drawable.ic_like_fill : R.drawable.ic_like);
                            iv_postDislike.setImageResource(!totalDislikes.equals("0") ? R.drawable.ic_like_fill : R.drawable.ic_like);



                            if(!totalLikes.equals("0"))
                            {
                                getMyLike = true;
                                count_like=Integer.parseInt(totalLikes);

                            }


                            if(!totalDislikes.equals("0")) {
                                count_dislike=Integer.parseInt(totalDislikes);
                                getMyDislike = true;
                            }



                            String post="";
                            final ArrayList<String> postFileLinks = new ArrayList<>();
                            if(!item.getString("fileLink").equalsIgnoreCase("no")) {
                                post = item.getString("fileLink");//post image


                                for (String str : post.split(",")) {
                                    String image = str.replace("../", "");
                                    postFileLinks.add(image);
                                }
                            }

                            System.out.println("single_post_images_size"+postFileLinks.size());



                            String total_views = item.getString("views");//get total views

                            final String total_view_array[]=total_views.split(",");





                            //set images
                            if(SinglePostActivity.this!=null) {

                                final String finalUser_type = user_type;
                                SinglePostActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {



                                        like_layout.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                                    /*lay_like.animate().setInterpolator(sDecelerater).
                                                            scaleX(.7f).scaleY(.7f);*/
                                                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                                                    /*lay_like.animate().setInterpolator(sOvershooter).
                                                            scaleX(1f).scaleY(1f);*/

                                                    if (dislike) {
                                                        dislike = false;
                                                        dislikeCount--;
                                                        iv_postDislike.setImageResource(R.drawable.ic_dislike);
                                                       // txt_dislike.setTextColor(Color.parseColor("#000000"));
                                                        //txt_likeDislikeCount.setText(likeCount + " likes, " + dislikeCount + " dislikes");
                                                    }

                                                  else  if (like) {
                                                        like = false;
                                                        likeCount--;
                                                        iv_postLike.setImageResource(R.drawable.ic_like);
                                                       // txt_like.setTextColor(Color.parseColor("#000000"));
                                                       // txt_likeDislikeCount.setText(likeCount + " likes, " + dislikeCount + " dislikes");

                                                        total_likes.setText(String.valueOf(likeCount));
                                                        total_dislikes.setText(String.valueOf(dislikeCount));
                                                    } else {
                                                        like = true;
                                                        likeCount++;
                                                        iv_postLike.setImageResource(R.drawable.ic_like_fill);
                                                        //txt_like.setTextColor(Color.parseColor("#e53935"));
                                                        //txt_likeDislikeCount.setText(likeCount + " likes, " + dislikeCount + " dislikes");
                                                        total_likes.setText(String.valueOf(likeCount));
                                                        total_dislikes.setText(String.valueOf(dislikeCount));


                                                    }

                                                    new HomeFragment().put_like(appController,postId,"like",userId, finalUser_type);
                                                }
                                                return false;
                                            }
                                        });

                                        dislike_layout.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                                   /* lay_dislike.animate().setInterpolator(sDecelerater).
                                                            scaleX(.7f).scaleY(.7f);*/
                                                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                                                    /*lay_dislike.animate().setInterpolator(sOvershooter).
                                                            scaleX(1f).scaleY(1f);*/

                                                    if (like) {
                                                        like = false;
                                                        likeCount--;
                                                        iv_postLike.setImageResource(R.drawable.ic_like);
                                                        //txt_like.setTextColor(Color.parseColor("#000000"));
                                                        //txt_likeDislikeCount.setText(likeCount + " likes, " + dislikeCount + " dislikes");

                                                        total_likes.setText(String.valueOf(likeCount));
                                                        total_dislikes.setText(String.valueOf(dislikeCount));
                                                    }

                                                  else  if (dislike) {
                                                        dislike = false;
                                                        dislikeCount--;
                                                        iv_postDislike.setImageResource(R.drawable.ic_dislike);
                                                        //txt_dislike.setTextColor(Color.parseColor("#000000"));
                                                        //txt_likeDislikeCount.setText(likeCount + " likes, " + dislikeCount + " dislikes");
                                                    } else {
                                                        dislike = true;
                                                        dislikeCount++;
                                                        iv_postDislike.setImageResource(R.drawable.ic_dislike_fill);
                                                        //txt_dislike.setTextColor(Color.parseColor("#e53935"));
                                                        //txt_likeDislikeCount.setText(likeCount + " likes, " + dislikeCount + " dislikes");

                                                        total_likes.setText(String.valueOf(likeCount));
                                                        total_dislikes.setText(String.valueOf(dislikeCount));
                                                    }

                                                    new HomeFragment().put_like(appController,postId,"dislike",userId, finalUser_type);;
                                                }
                                                return false;
                                            }
                                        });










                                        frame_postA.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                                        putExtra("images_list", postFileLinks).
                                                        putExtra("isVideos", false).
                                                        putExtra("position", 0));
                                            }
                                        });


                                        frame_postB.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                                        putExtra("images_list", postFileLinks).
                                                        putExtra("isVideos", false).
                                                        putExtra("position", 1));
                                            }
                                        });

                                        frame_postC.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                                        putExtra("images_list", postFileLinks).
                                                        putExtra("isVideos", false).
                                                        putExtra("position", 2));
                                            }
                                        });

                                        frame_postD.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                                        putExtra("images_list", postFileLinks).
                                                        putExtra("isVideos", false).
                                                        putExtra("position", 3));
                                            }
                                        });




                                        //set status
                                        tv_status.setText(description);



                                        total_likes.setText(String.valueOf(totalLikes));
                                        total_dislikes.setText(String.valueOf(totalDislikes));
                                        total_comments.setText(String.valueOf(totalComments));


                                        if(total_view_array.length==1) {
                                            total_view.setText("0");
                                        }
                                        else
                                            total_view.setText(Integer.toString(total_view_array.length));


                            if (postFileLinks.size() == 0)
                            {
                                frame_postA.setVisibility(View.GONE);
                                three_frames_container.setVisibility(View.GONE);
                                
                            }
                            else if (postFileLinks.size() == 1)
                            {
                                frame_postA.setVisibility(View.VISIBLE);
                                three_frames_container.setVisibility(View.GONE);

                              progressA.setVisibility(View.VISIBLE);


                                Glide.with(getApplicationContext()).
                                        load(path+postFileLinks.get(0)).
                                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                        listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }
                                        }).
                                        thumbnail(0.01f).
                                        into(iv_postImgA);

                            }



                            //only 2 images is coming and set 2 images
                            else if (postFileLinks.size() == 2)
                            {
                              three_frames_container.setVisibility(View.VISIBLE);
                              frame_postA.setVisibility(View.VISIBLE);
                              frame_postB.setVisibility(View.VISIBLE);
                              frame_postC.setVisibility(View.GONE);
                              frame_postD.setVisibility(View.GONE);

                              progressA.setVisibility(View.VISIBLE);
                              progressB.setVisibility(View.VISIBLE);

                                //set two Images

                                Glide.with(getApplicationContext()).
                                        load(path+postFileLinks.get(0)).
                                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                        listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }
                                        }).
                                        thumbnail(0.01f).
                                        into(iv_postImgA);

                                Glide.with(getApplicationContext()).
                                        load(path+postFileLinks.get(1)).
                                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                        listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }
                                        }).
                                        thumbnail(0.01f).
                                        into(iv_postImgB);

                            }


                            //only 3 images is coming and set 3 images
                            else if (postFileLinks.size() == 3)
                            {
                              three_frames_container.setVisibility(View.VISIBLE);
                              frame_postA.setVisibility(View.VISIBLE);
                              frame_postB.setVisibility(View.VISIBLE);
                              frame_postC.setVisibility(View.VISIBLE);
                              frame_postD.setVisibility(View.GONE);

                              progressA.setVisibility(View.VISIBLE);
                              progressB.setVisibility(View.VISIBLE);
                              progressC.setVisibility(View.VISIBLE);
                              progressD.setVisibility(View.GONE);

                                //set 3 Images

                                Glide.with(getApplicationContext()).
                                        load(path+postFileLinks.get(0)).
                                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                        listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }
                                        }).
                                        thumbnail(0.01f).
                                        into(iv_postImgA);

                                Glide.with(getApplicationContext()).
                                        load(path+postFileLinks.get(1)).
                                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                        listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }
                                        }).
                                        thumbnail(0.01f).
                                        into(iv_postImgB);

                                Glide.with(getApplicationContext()).
                                        load(path+postFileLinks.get(2)).
                                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                        listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }
                                        }).
                                        thumbnail(0.01f).
                                        into(iv_postImgC);
                            }


                            //only 4 images is coming and set 4 images
                            else if (postFileLinks.size() == 4)
                            {
                              three_frames_container.setVisibility(View.VISIBLE);
                              frame_postA.setVisibility(View.VISIBLE);
                              frame_postB.setVisibility(View.VISIBLE);
                              frame_postC.setVisibility(View.VISIBLE);
                              frame_postD.setVisibility(View.VISIBLE);
                              txt_postImgCount.setVisibility(View.GONE);

                              progressA.setVisibility(View.VISIBLE);
                              progressB.setVisibility(View.VISIBLE);
                              progressC.setVisibility(View.VISIBLE);
                              progressD.setVisibility(View.VISIBLE);


                                //set 4 Images

                                Glide.with(getApplicationContext()).
                                        load(path+postFileLinks.get(0)).
                                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                        listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }
                                        }).
                                        thumbnail(0.01f).
                                        into(iv_postImgA);

                                Glide.with(getApplicationContext()).
                                        load(path+postFileLinks.get(1)).
                                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                        listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }
                                        }).
                                        thumbnail(0.01f).
                                        into(iv_postImgB);

                                Glide.with(getApplicationContext()).
                                        load(path+postFileLinks.get(2)).
                                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                        listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }
                                        }).
                                        thumbnail(0.01f).
                                        into(iv_postImgC);

                                Glide.with(getApplicationContext()).
                                        load(path+postFileLinks.get(3)).
                                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                        listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }
                                        }).
                                        thumbnail(0.01f).
                                        into(iv_postImgD);
                            }

                            //more than 4 images is coming and set 4 images
                            else if (postFileLinks.size()> 4)
                            {
                              three_frames_container.setVisibility(View.VISIBLE);
                              frame_postA.setVisibility(View.VISIBLE);
                              frame_postB.setVisibility(View.VISIBLE);
                              frame_postC.setVisibility(View.VISIBLE);
                              frame_postD.setVisibility(View.VISIBLE);
                              txt_postImgCount.setVisibility(View.VISIBLE);
                              txt_postImgCount.setText((postFileLinks.size())-4+" More");

                                //set 4 Images

                                Glide.with(getApplicationContext()).
                                        load(path+postFileLinks.get(0)).
                                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                        listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }
                                        }).
                                        thumbnail(0.01f).
                                        into(iv_postImgA);

                                Glide.with(getApplicationContext()).
                                        load(path+postFileLinks.get(1)).
                                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                        listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }
                                        }).
                                        thumbnail(0.01f).
                                        into(iv_postImgB);

                                Glide.with(getApplicationContext()).
                                        load(path+postFileLinks.get(2)).
                                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                        listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }
                                        }).
                                        thumbnail(0.01f).
                                        into(iv_postImgC);

                                Glide.with(getApplicationContext()).
                                        load(path+postFileLinks.get(3)).
                                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                        listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                progressA.setVisibility(View.GONE);
                                                return false;
                                            }
                                        }).
                                        thumbnail(0.01f).
                                        into(iv_postImgD);

                            }
                            //closing display post images



                                    }
                                });



                            }//closing thread method


                        }





                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        });
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backfrom=1;
    }




}
