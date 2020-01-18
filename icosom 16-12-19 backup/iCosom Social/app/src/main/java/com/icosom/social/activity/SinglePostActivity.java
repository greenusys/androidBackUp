package com.icosom.social.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.icosom.social.R;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;

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
    TextView txt_userName;
    TextView txt_time;
    CircleImageView iv_userProfile;
    ImageView iv_more;
    TextView txt_description;
    LinearLayout lay_media;
    FrameLayout frameA;
    ProgressBar shareProgresshide;
    ImageView iv_imgA;
    ImageView iv_isVideoA;
    ProgressBar progressA;
    FrameLayout frameB;
    ImageView iv_imgB;
    ImageView iv_isVideoB;
    ProgressBar progressB;
    LinearLayout lay_forBelowImgs;
    FrameLayout frameC;
    ImageView iv_imgC;
    ImageView iv_isVideoC;
    ProgressBar progressC;
    FrameLayout frameD;
    ImageView iv_imgD;
    ImageView iv_isVideoD;
    ProgressBar progressD;
    FrameLayout frameE;
    ImageView iv_imgE;
    ImageView iv_isVideoE;
    ProgressBar progressE;
    TextView txt_imgCount;
    LinearLayout lay_shareLayout;
    TextView txt_shareUserName;
    TextView txt_shareTime;
    CircleImageView iv_shareUserProfile;
    TextView txt_shareDescription;
    LinearLayout lay_sharedMedia;
    FrameLayout frame_shareA;
    ImageView iv_shareImgA;
    ImageView iv_shareIsVideoA;
    ProgressBar shareProgressA;
    FrameLayout frame_shareB;
    ImageView iv_shareImgB;
    ImageView iv_shareIsVideoB;
    ProgressBar shareProgressB;
    LinearLayout lay_shareForBelowImgs;
    FrameLayout frame_shareC;
    ImageView iv_shareImgC;
    ImageView iv_shareIsVideoC;
    ProgressBar shareProgressC;
    FrameLayout frame_shareD;
    ImageView iv_shareImgD;
    ImageView iv_shareIsVideoD;
    ProgressBar shareProgressD;
    FrameLayout frame_shareE;
    ImageView iv_shareImgE;
    ImageView iv_shareIsVideoE;
    ProgressBar shareProgressE;
    TextView txt_shareImgCount;
    TextView txt_likeDislikeCount;
    TextView txt_commentShareCount;
    LinearLayout lay_like;
    ImageView iv_like;
    TextView txt_like;
    LinearLayout lay_dislike;
    ImageView iv_dislike;
    TextView txt_dislike;
    LinearLayout lay_comment;
    ImageView iv_comment;
    TextView txt_comment;
    LinearLayout lay_share;
    ImageView iv_share;
    TextView txt_share;
    TextView txt_toolbarFirstTitle;
    TextView txt_toolbarSecondTitle;
    NestedScrollView hi;
    AppController appController;
    SharedPreferences sp;
    LinearLayout linhide;
    Boolean like = false;
    Boolean dislike = false;
    Boolean comment = false;
    Boolean share = false;
    int likeCount = 0;
    int dislikeCount = 0;
    int commentCount = 0;
    int shareCount = 0;

    DecelerateInterpolator sDecelerater = new DecelerateInterpolator();
    OvershootInterpolator sOvershooter = new OvershootInterpolator(5f);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);
        hi = findViewById(R.id.hide);
        linhide = findViewById(R.id.linhide);
        shareProgresshide = findViewById(R.id.shareProgresshide);
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        appController = (AppController) getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialize();

        getPostDetails(getIntent().getStringExtra("postId"));
    }

    private void initialize() {
        txt_userName = findViewById(R.id.txt_userName);
        txt_time = findViewById(R.id.txt_time);
        iv_userProfile = findViewById(R.id.iv_userProfile);
        iv_more = findViewById(R.id.iv_more);
        txt_description = findViewById(R.id.txt_description);
        lay_media = findViewById(R.id.lay_media);
        frameA = findViewById(R.id.frameA);
        iv_imgA = findViewById(R.id.iv_imgA);
        iv_isVideoA = findViewById(R.id.iv_isVideoA);
        progressA = findViewById(R.id.progressA);
        frameB = findViewById(R.id.frameB);
        iv_imgB = findViewById(R.id.iv_imgB);
        iv_isVideoB = findViewById(R.id.iv_isVideoB);
        progressB = findViewById(R.id.progressB);
        lay_forBelowImgs = findViewById(R.id.lay_forBelowImgs);
        frameC = findViewById(R.id.frameC);
        iv_imgC = findViewById(R.id.iv_imgC);
        iv_isVideoC = findViewById(R.id.iv_isVideoC);
        progressC = findViewById(R.id.progressC);
        frameD = findViewById(R.id.frameD);
        iv_imgD = findViewById(R.id.iv_imgD);
        iv_isVideoD = findViewById(R.id.iv_isVideoD);
        progressD = findViewById(R.id.progressD);
        frameE = findViewById(R.id.frameE);
        iv_imgE = findViewById(R.id.iv_imgE);
        iv_isVideoE = findViewById(R.id.iv_isVideoE);
        progressE = findViewById(R.id.progressE);
        txt_imgCount = findViewById(R.id.txt_imgCount);
        lay_shareLayout = findViewById(R.id.lay_shareLayout);
        txt_shareUserName = findViewById(R.id.txt_shareUserName);
        txt_shareTime = findViewById(R.id.txt_shareTime);
        iv_shareUserProfile = findViewById(R.id.iv_shareUserProfile);
        txt_shareDescription = findViewById(R.id.txt_shareDescription);
        lay_sharedMedia = findViewById(R.id.lay_sharedMedia);
        frame_shareA = findViewById(R.id.frame_shareA);
        iv_shareImgA = findViewById(R.id.iv_shareImgA);
        iv_shareIsVideoA = findViewById(R.id.iv_shareIsVideoA);
        shareProgressA = findViewById(R.id.shareProgressA);
        frame_shareB = findViewById(R.id.frame_shareB);
        iv_shareImgB = findViewById(R.id.iv_shareImgB);
        iv_shareIsVideoB = findViewById(R.id.iv_shareIsVideoB);
        shareProgressB = findViewById(R.id.shareProgressB);
        lay_shareForBelowImgs = findViewById(R.id.lay_shareForBelowImgs);
        frame_shareC = findViewById(R.id.frame_shareC);
        iv_shareImgC = findViewById(R.id.iv_shareImgC);
        iv_shareIsVideoC = findViewById(R.id.iv_shareIsVideoC);
        shareProgressC = findViewById(R.id.shareProgressC);
        frame_shareD = findViewById(R.id.frame_shareD);
        iv_shareImgD = findViewById(R.id.iv_shareImgD);
        iv_shareIsVideoD = findViewById(R.id.iv_shareIsVideoD);
        shareProgressD = findViewById(R.id.shareProgressD);
        frame_shareE = findViewById(R.id.frame_shareE);
        iv_shareImgE = findViewById(R.id.iv_shareImgE);
        iv_shareIsVideoE = findViewById(R.id.iv_shareIsVideoE);
        shareProgressE = findViewById(R.id.shareProgressE);
        txt_shareImgCount = findViewById(R.id.txt_shareImgCount);
        txt_likeDislikeCount = findViewById(R.id.txt_likeDislikeCount);
        txt_commentShareCount = findViewById(R.id.txt_commentShareCount);
        lay_like = findViewById(R.id.lay_like);
        iv_like = findViewById(R.id.iv_like);
        txt_like = findViewById(R.id.txt_like);
        lay_dislike = findViewById(R.id.lay_dislike);
        iv_dislike = findViewById(R.id.iv_dislike);
        txt_dislike = findViewById(R.id.txt_dislike);
        lay_comment = findViewById(R.id.lay_comment);
        iv_comment = findViewById(R.id.iv_comment);
        txt_comment = findViewById(R.id.txt_comment);
        lay_share = findViewById(R.id.lay_share);
        iv_share = findViewById(R.id.iv_share);
        txt_share = findViewById(R.id.txt_share);
        txt_toolbarFirstTitle = findViewById(R.id.txt_toolbarFirstTitle);
        txt_toolbarSecondTitle = findViewById(R.id.txt_toolbarSecondTitle);
    }

    private void getPostDetails(String postId) {
        System.out.println("wwwwwwwwwwwwwee postId " + postId);
        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("showPostId", postId).
                add("userId", sp.getString("userId", "")).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.SHOW_SINGLE_POST).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                SinglePostActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                SinglePostActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            setThings(myResponse);
                            hi.setVisibility(View.VISIBLE);
                            linhide.setVisibility(View.VISIBLE);
                            shareProgresshide.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));



    }

    private String getGender(String gen) {
        return gen.equalsIgnoreCase("male") ? " his " : " her ";
    }

    private void setThings(String myResponse) throws JSONException {
        JSONObject jo = new JSONObject(myResponse);
        final JSONObject post = jo.getJSONObject("post");

        txt_toolbarFirstTitle.setText(post.getString("firstName"));
        txt_toolbarSecondTitle.setText(post.getString("lastName"));

        if (post.getString("flag").equalsIgnoreCase("1")) {
            txt_userName.setText(
                    post.getString("firstName") + " " + post.getString("lastName") +
                            " updated " + getGender(post.getString("gender")) + "profile picture."
            );
        } else if (post.getString("flag").equalsIgnoreCase("2")) {
            txt_userName.setText(
                    post.getString("firstName") + " " + post.getString("lastName") +
                            " updated " + getGender(post.getString("gender")) + "cover picture."
            );
        } else {
            // normal
            if (post.getString("shareFlag").equalsIgnoreCase("1")) {
                // is shared
                JSONObject share = jo.getJSONObject("share");
                if (post.getString("tagFlag").equalsIgnoreCase("0")) {
                    // no tag
                    txt_userName.setText(
                            post.getString("firstName") + " " + post.getString("lastName") +
                                    " shared " + share.getString("firstName") + " " + share.getString("lastName") +
                                    " post."
                    );
                } else {
                    // is Tagged
                    JSONObject tag = jo.getJSONObject("tag");
                    JSONArray tagUser = tag.getJSONArray("tagData");

                    if (tag.getString("tagCount").equalsIgnoreCase("1")) {
                        txt_userName.setText(
                                post.getString("firstName") + " " + post.getString("lastName") +
                                        " shared " + share.getString("firstName") + " " + share.getString("lastName") +
                                        " post with " + tagUser.getJSONObject(0).getString("firstName") + " " +
                                        tagUser.getJSONObject(0).getString("lastName")
                        );
                    } else {
                        txt_userName.setText(
                                post.getString("firstName") + " " + post.getString("lastName") +
                                        " shared " + share.getString("firstName") + " " + share.getString("lastName") +
                                        " post with " + tagUser.getJSONObject(0).getString("firstName") + " " +
                                        tagUser.getJSONObject(0).getString("lastName") +
                                        " and " + (Integer.parseInt(tag.getString("tagCount")) - 1) + " other."
                        );
                    }
                }
            } else {
                // is not shared
                if (post.getString("fileLink").trim().equalsIgnoreCase("")) {
                    // no media
                    if (post.getString("tagFlag").equalsIgnoreCase("0")) {
                        // no tag
                        txt_userName.setText(
                                post.getString("firstName") + " " + post.getString("lastName")
                        );
                    } else {
                        // is Tagged
                        JSONObject tag = jo.getJSONObject("tag");
                        JSONArray tagUser = tag.getJSONArray("tagData");

                        if (tag.getString("tagCount").equalsIgnoreCase("1")) {
                            txt_userName.setText(
                                    post.getString("firstName") + " " + post.getString("lastName") +
                                            " is with " + tagUser.getJSONObject(0).getString("firstName") + " " +
                                            tagUser.getJSONObject(0).getString("lastName")
                            );
                        } else {
                            txt_userName.setText(
                                    post.getString("firstName") + " " + post.getString("lastName") +
                                            " is with " + tagUser.getJSONObject(0).getString("firstName") + " " +
                                            tagUser.getJSONObject(0).getString("lastName") +
                                            " and " + (Integer.parseInt(tag.getString("tagCount")) - 1) + " other."
                            );
                        }
                    }
                } else {
                    // media
                    String[] files = post.getString("fileLink").split(",");

                    if (post.getString("tagFlag").equalsIgnoreCase("0")) {
                        // no tag
                        txt_userName.setText(
                                post.getString("firstName") + " " + post.getString("lastName") +
                                        " added " + files.length + " media."
                        );
                    } else {
                        // is Tagged
                        JSONObject tag = jo.getJSONObject("tag");
                        JSONArray tagUser = tag.getJSONArray("tagData");

                        if (tag.getString("tagCount").equalsIgnoreCase("1")) {
                            txt_userName.setText(
                                    post.getString("firstName") + " " + post.getString("lastName") +
                                            " added " + files.length + " media with " +
                                            tagUser.getJSONObject(0).getString("firstName") + " " +
                                            tagUser.getJSONObject(0).getString("lastName")
                            );
                        } else {
                            txt_userName.setText(
                                    post.getString("firstName") + " " + post.getString("lastName") +
                                            " added " + files.length + " media with " +
                                            tagUser.getJSONObject(0).getString("firstName") + " " +
                                            tagUser.getJSONObject(0).getString("lastName") +
                                            " and " + (Integer.parseInt(tag.getString("tagCount")) - 1) + " other."
                            );
                        }
                    }
                }
            }
        }

        txt_time.setText(post.getString("postTime"));

        Glide.
                with(getBaseContext()).
                load(CommonFunctions.FETCH_IMAGES + post.get("profilePicture")).
                apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                thumbnail(0.01f).
                into(iv_userProfile);

        txt_description.setText(post.getString("description").trim());

        if (post.getString("type").equalsIgnoreCase("0")) {
            lay_media.setVisibility(View.GONE);
        } else if (post.getString("type").equalsIgnoreCase("1")) {
            lay_media.setVisibility(View.VISIBLE);
            //image post
            final String[] files = post.getString("fileLink").split(",");

            final ArrayList listFiles = new ArrayList();
            for (int i = 0; i < files.length; i++) {
                listFiles.add(files[i]);
            }

            if (files.length == 1) {
                frameA.setVisibility(View.VISIBLE);
                frameB.setVisibility(View.GONE);
                frameC.setVisibility(View.GONE);
                frameD.setVisibility(View.GONE);
                frameE.setVisibility(View.GONE);

                iv_isVideoA.setVisibility(View.GONE);

                lay_forBelowImgs.setVisibility(View.GONE);

                progressA.setVisibility(View.VISIBLE);

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_IMAGES + files[0]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressA.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgA);

                iv_imgA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", false).
                                putExtra("position", 0));
                    }
                });

            } else if (files.length == 2) {
                frameA.setVisibility(View.VISIBLE);
                frameB.setVisibility(View.VISIBLE);
                frameC.setVisibility(View.GONE);
                frameD.setVisibility(View.GONE);
                frameE.setVisibility(View.GONE);

                iv_isVideoA.setVisibility(View.GONE);
                iv_isVideoB.setVisibility(View.GONE);

                lay_forBelowImgs.setVisibility(View.GONE);

                progressA.setVisibility(View.VISIBLE);
                progressB.setVisibility(View.VISIBLE);


                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_IMAGES + files[0]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressA.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgA);

                iv_imgA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", false).
                                putExtra("position", 0));
                    }
                });


                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_IMAGES + files[1]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressB.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgB);

                iv_imgB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", false).
                                putExtra("position", 1));
                    }
                });


            } else if (files.length == 3) {
                frameA.setVisibility(View.VISIBLE);
                frameB.setVisibility(View.GONE);
                frameC.setVisibility(View.VISIBLE);
                frameD.setVisibility(View.VISIBLE);
                frameE.setVisibility(View.GONE);

                iv_isVideoA.setVisibility(View.GONE);
                iv_isVideoC.setVisibility(View.GONE);
                iv_isVideoD.setVisibility(View.GONE);

                lay_forBelowImgs.setVisibility(View.VISIBLE);

                progressA.setVisibility(View.VISIBLE);
                progressC.setVisibility(View.VISIBLE);
                progressD.setVisibility(View.VISIBLE);


                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_IMAGES + files[0]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressA.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgA);

                iv_imgA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", false).
                                putExtra("position", 0));
                    }
                });


                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_IMAGES + files[1]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressC.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgC);

                iv_imgC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", false).
                                putExtra("position", 1));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_IMAGES + files[2]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressD.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgD);

                iv_imgD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", false).
                                putExtra("position", 2));
                    }
                });


            } else if (files.length == 4) {
                frameA.setVisibility(View.VISIBLE);
                frameB.setVisibility(View.GONE);
                frameC.setVisibility(View.VISIBLE);
                frameD.setVisibility(View.VISIBLE);
                frameE.setVisibility(View.VISIBLE);

                txt_imgCount.setVisibility(View.GONE);

                lay_forBelowImgs.setVisibility(View.VISIBLE);

                iv_isVideoA.setVisibility(View.GONE);
                iv_isVideoC.setVisibility(View.GONE);
                iv_isVideoD.setVisibility(View.GONE);
                iv_isVideoE.setVisibility(View.GONE);

                progressA.setVisibility(View.VISIBLE);
                progressC.setVisibility(View.VISIBLE);
                progressD.setVisibility(View.VISIBLE);
                progressE.setVisibility(View.VISIBLE);

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_IMAGES + files[0]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressA.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgA);

                iv_imgA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", false).
                                putExtra("position", 0));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_IMAGES + files[1]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressC.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgC);

                iv_imgC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", false).
                                putExtra("position", 1));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_IMAGES + files[2]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressD.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgD);

                iv_imgD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", false).
                                putExtra("position", 2));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_IMAGES + files[3]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressE.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgE);

                iv_imgE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", false).
                                putExtra("position", 3));
                    }
                });


            } else if (files.length == 5) {
                frameA.setVisibility(View.VISIBLE);
                frameB.setVisibility(View.VISIBLE);
                frameC.setVisibility(View.VISIBLE);
                frameD.setVisibility(View.VISIBLE);
                frameE.setVisibility(View.VISIBLE);

                txt_imgCount.setVisibility(View.GONE);

                lay_forBelowImgs.setVisibility(View.VISIBLE);

                iv_isVideoA.setVisibility(View.GONE);
                iv_isVideoB.setVisibility(View.GONE);
                iv_isVideoC.setVisibility(View.GONE);
                iv_isVideoD.setVisibility(View.GONE);
                iv_isVideoE.setVisibility(View.GONE);

                progressA.setVisibility(View.VISIBLE);
                progressB.setVisibility(View.VISIBLE);
                progressC.setVisibility(View.VISIBLE);
                progressD.setVisibility(View.VISIBLE);
                progressE.setVisibility(View.VISIBLE);

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_IMAGES + files[0]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressA.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgA);

                iv_imgA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", false).
                                putExtra("position", 0));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_IMAGES + files[1]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressB.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgB);

                iv_imgB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", false).
                                putExtra("position", 1));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_IMAGES + files[2]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressC.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgC);

                iv_imgC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", false).
                                putExtra("position", 2));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_IMAGES + files[3]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressD.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgD);

                iv_imgD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", false).
                                putExtra("position", 3));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_IMAGES + files[4]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressE.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgE);

                iv_imgE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", false).
                                putExtra("position", 4));
                    }
                });
            } else {
                frameA.setVisibility(View.VISIBLE);
                frameB.setVisibility(View.VISIBLE);
                frameC.setVisibility(View.VISIBLE);
                frameD.setVisibility(View.VISIBLE);
                frameE.setVisibility(View.VISIBLE);

                txt_imgCount.setVisibility(View.VISIBLE);

                lay_forBelowImgs.setVisibility(View.VISIBLE);

                iv_isVideoA.setVisibility(View.GONE);
                iv_isVideoB.setVisibility(View.GONE);
                iv_isVideoC.setVisibility(View.GONE);
                iv_isVideoD.setVisibility(View.GONE);
                iv_isVideoE.setVisibility(View.GONE);

                progressA.setVisibility(View.VISIBLE);
                progressB.setVisibility(View.VISIBLE);
                progressC.setVisibility(View.VISIBLE);
                progressD.setVisibility(View.VISIBLE);
                progressE.setVisibility(View.VISIBLE);

                txt_imgCount.setText("+" + (files.length - 5) + " More");

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_IMAGES + files[0]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressA.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgA);

                iv_imgA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", false).
                                putExtra("position", 0));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_IMAGES + files[1]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressB.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgB);

                iv_imgB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", false).
                                putExtra("position", 1));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_IMAGES + files[2]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressC.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgC);

                iv_imgC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", false).
                                putExtra("position", 2));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_IMAGES + files[3]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressD.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgD);

                iv_imgD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", false).
                                putExtra("position", 3));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_IMAGES + files[4]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressE.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgE);

                iv_imgE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", false).
                                putExtra("position", 4));
                    }
                });
            }
        } else {
            // video
            String[] files = post.getString("fileLink").split(",");

            final ArrayList listFiles = new ArrayList();
            for (int i = 0; i < files.length; i++) {
                listFiles.add(files[i]);
            }

            if (files.length == 1) {
                frameA.setVisibility(View.VISIBLE);
                frameB.setVisibility(View.GONE);
                frameC.setVisibility(View.GONE);
                frameD.setVisibility(View.GONE);
                frameE.setVisibility(View.GONE);

                iv_isVideoA.setVisibility(View.VISIBLE);

                lay_forBelowImgs.setVisibility(View.GONE);

                progressA.setVisibility(View.VISIBLE);

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_VIDEOS + files[0]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressA.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgA);

                iv_imgA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", true).
                                putExtra("position", 0));
                    }
                });
            } else if (files.length == 2) {
                frameA.setVisibility(View.VISIBLE);
                frameB.setVisibility(View.VISIBLE);
                frameC.setVisibility(View.GONE);
                frameD.setVisibility(View.GONE);
                frameE.setVisibility(View.GONE);

                iv_isVideoA.setVisibility(View.VISIBLE);
                iv_isVideoB.setVisibility(View.VISIBLE);

                lay_forBelowImgs.setVisibility(View.GONE);

                progressA.setVisibility(View.VISIBLE);
                progressB.setVisibility(View.VISIBLE);

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_VIDEOS + files[0]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressA.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgA);

                iv_imgA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", true).
                                putExtra("position", 0));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_VIDEOS + files[1]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressB.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgB);

                iv_imgB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", true).
                                putExtra("position", 1));
                    }
                });
            } else if (files.length == 3) {
                frameA.setVisibility(View.VISIBLE);
                frameB.setVisibility(View.GONE);
                frameC.setVisibility(View.VISIBLE);
                frameD.setVisibility(View.VISIBLE);
                frameE.setVisibility(View.GONE);

                iv_isVideoA.setVisibility(View.VISIBLE);
                iv_isVideoC.setVisibility(View.VISIBLE);
                iv_isVideoD.setVisibility(View.VISIBLE);

                lay_forBelowImgs.setVisibility(View.VISIBLE);

                progressA.setVisibility(View.VISIBLE);
                progressC.setVisibility(View.VISIBLE);
                progressD.setVisibility(View.VISIBLE);

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_VIDEOS + files[0]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressA.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgA);

                iv_imgA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", true).
                                putExtra("position", 0));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_VIDEOS + files[1]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressC.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgC);

                iv_imgC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", true).
                                putExtra("position", 1));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_VIDEOS + files[2]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressD.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgD);

                iv_imgD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", true).
                                putExtra("position", 2));
                    }
                });
            } else if (files.length == 4) {
                frameA.setVisibility(View.VISIBLE);
                frameB.setVisibility(View.GONE);
                frameC.setVisibility(View.VISIBLE);
                frameD.setVisibility(View.VISIBLE);
                frameE.setVisibility(View.VISIBLE);

                txt_imgCount.setVisibility(View.GONE);

                lay_forBelowImgs.setVisibility(View.VISIBLE);

                iv_isVideoA.setVisibility(View.VISIBLE);
                iv_isVideoC.setVisibility(View.VISIBLE);
                iv_isVideoD.setVisibility(View.VISIBLE);
                iv_isVideoE.setVisibility(View.VISIBLE);

                progressA.setVisibility(View.VISIBLE);
                progressC.setVisibility(View.VISIBLE);
                progressD.setVisibility(View.VISIBLE);
                progressE.setVisibility(View.VISIBLE);

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_VIDEOS + files[0]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressA.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgA);

                iv_imgA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", true).
                                putExtra("position", 0));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_VIDEOS + files[1]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressC.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgC);

                iv_imgC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", true).
                                putExtra("position", 1));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_VIDEOS + files[2]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressD.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgD);

                iv_imgD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", true).
                                putExtra("position", 2));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_VIDEOS + files[3]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressE.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgE);

                iv_imgE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", true).
                                putExtra("position", 3));
                    }
                });
            } else if (files.length == 5) {
                frameA.setVisibility(View.VISIBLE);
                frameB.setVisibility(View.VISIBLE);
                frameC.setVisibility(View.VISIBLE);
                frameD.setVisibility(View.VISIBLE);
                frameE.setVisibility(View.VISIBLE);

                txt_imgCount.setVisibility(View.GONE);

                lay_forBelowImgs.setVisibility(View.VISIBLE);

                iv_isVideoA.setVisibility(View.VISIBLE);
                iv_isVideoB.setVisibility(View.VISIBLE);
                iv_isVideoC.setVisibility(View.VISIBLE);
                iv_isVideoD.setVisibility(View.VISIBLE);
                iv_isVideoE.setVisibility(View.VISIBLE);

                progressA.setVisibility(View.VISIBLE);
                progressB.setVisibility(View.VISIBLE);
                progressC.setVisibility(View.VISIBLE);
                progressD.setVisibility(View.VISIBLE);
                progressE.setVisibility(View.VISIBLE);

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_VIDEOS + files[0]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressA.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgA);

                iv_imgA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", true).
                                putExtra("position", 0));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_VIDEOS + files[1]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressB.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgB);

                iv_imgB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", true).
                                putExtra("position", 1));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_VIDEOS + files[2]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressC.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgC);

                iv_imgC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", true).
                                putExtra("position", 2));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_VIDEOS + files[3]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressD.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgD);

                iv_imgD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", true).
                                putExtra("position", 3));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_VIDEOS + files[4]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressE.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgE);

                iv_imgE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", true).
                                putExtra("position", 4));
                    }
                });
            } else {
                frameA.setVisibility(View.VISIBLE);
                frameB.setVisibility(View.VISIBLE);
                frameC.setVisibility(View.VISIBLE);
                frameD.setVisibility(View.VISIBLE);
                frameE.setVisibility(View.VISIBLE);

                txt_imgCount.setVisibility(View.VISIBLE);

                lay_forBelowImgs.setVisibility(View.VISIBLE);

                iv_isVideoA.setVisibility(View.VISIBLE);
                iv_isVideoB.setVisibility(View.VISIBLE);
                iv_isVideoC.setVisibility(View.VISIBLE);
                iv_isVideoD.setVisibility(View.VISIBLE);
                iv_isVideoE.setVisibility(View.VISIBLE);

                progressA.setVisibility(View.VISIBLE);
                progressB.setVisibility(View.VISIBLE);
                progressC.setVisibility(View.VISIBLE);
                progressD.setVisibility(View.VISIBLE);
                progressE.setVisibility(View.VISIBLE);

                txt_imgCount.setText("+" + (files.length - 5) + " More");

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_VIDEOS + files[0]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressA.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgA);

                iv_imgA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", true).
                                putExtra("position", 0));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_VIDEOS + files[1]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressB.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgB);

                iv_imgB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", true).
                                putExtra("position", 1));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_VIDEOS + files[2]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressC.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgC);

                iv_imgC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", true).
                                putExtra("position", 2));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_VIDEOS + files[3]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressD.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgD);

                iv_imgD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", true).
                                putExtra("position", 3));
                    }
                });

                Glide.
                        with(getApplicationContext()).
                        load(CommonFunctions.FETCH_VIDEOS + files[4]).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressE.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.01f).
                        into(iv_imgE);

                iv_imgE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                putExtra("imgs", listFiles).
                                putExtra("isVideos", true).
                                putExtra("position", 4));
                    }
                });
            }
        }

        if (post.getString("shareFlag").equalsIgnoreCase("1")) {
            lay_shareLayout.setVisibility(View.VISIBLE);
            JSONObject share = jo.getJSONObject("share");

            if (share.getString("flag").equalsIgnoreCase("1")) {
                txt_shareUserName.setText(
                        share.getString("firstName") + " " + share.getString("lastName") +
                                " updated" + getGender(share.getString("gender")) + "profile picture"
                );
            } else if (share.getString("flag").equalsIgnoreCase("2")) {
                txt_shareUserName.setText(
                        share.getString("firstName") + " " + share.getString("lastName") +
                                " updated" + getGender(share.getString("gender")) + "cover picture"
                );
            } else {
                //normal
                if (share.getString("fileLink").trim().equalsIgnoreCase("")) {
                    // no media
                    txt_shareUserName.setText(
                            share.getString("firstName") + " " + share.getString("lastName")
                    );
                } else {
                    // media
                    String[] files = share.getString("fileLink").split(",");
                    txt_shareUserName.setText(
                            share.getString("firstName") + " " + share.getString("lastName") +
                                    " added " + files.length + " media."
                    );
                }
            }

            txt_shareTime.setText(share.getString("postTime"));

            Glide.
                    with(getBaseContext()).
                    load(CommonFunctions.FETCH_IMAGES + share.getString("profilePicture")).
                    apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                    into(iv_shareUserProfile);

            txt_shareDescription.setText(share.getString("description").trim());

            if (share.getString("type").equalsIgnoreCase("0")) {
                lay_sharedMedia.setVisibility(View.GONE);
            } else if (share.getString("type").equalsIgnoreCase("1")) {
                // images
                lay_sharedMedia.setVisibility(View.VISIBLE);

                String[] files = share.getString("fileLink").split(",");
                if (files.length == 1) {
                    frame_shareA.setVisibility(View.VISIBLE);
                    frame_shareB.setVisibility(View.GONE);
                    frame_shareC.setVisibility(View.GONE);
                    frame_shareD.setVisibility(View.GONE);
                    frame_shareE.setVisibility(View.GONE);

                    iv_shareIsVideoA.setVisibility(View.GONE);

                    lay_shareForBelowImgs.setVisibility(View.GONE);

                    shareProgressA.setVisibility(View.VISIBLE);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[0]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressA.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgA);
                } else if (files.length == 2) {
                    frame_shareA.setVisibility(View.VISIBLE);
                    frame_shareB.setVisibility(View.VISIBLE);
                    frame_shareC.setVisibility(View.GONE);
                    frame_shareD.setVisibility(View.GONE);
                    frame_shareE.setVisibility(View.GONE);

                    iv_shareIsVideoA.setVisibility(View.GONE);
                    iv_shareIsVideoB.setVisibility(View.GONE);

                    lay_shareForBelowImgs.setVisibility(View.GONE);

                    shareProgressA.setVisibility(View.VISIBLE);
                    shareProgressB.setVisibility(View.VISIBLE);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[0]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressA.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgA);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[1]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressB.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgB);
                } else if (files.length == 3) {
                    frame_shareA.setVisibility(View.VISIBLE);
                    frame_shareB.setVisibility(View.GONE);
                    frame_shareC.setVisibility(View.VISIBLE);
                    frame_shareD.setVisibility(View.VISIBLE);
                    frame_shareE.setVisibility(View.GONE);

                    iv_shareIsVideoA.setVisibility(View.GONE);
                    iv_shareIsVideoC.setVisibility(View.GONE);
                    iv_shareIsVideoD.setVisibility(View.GONE);

                    lay_shareForBelowImgs.setVisibility(View.VISIBLE);

                    shareProgressA.setVisibility(View.VISIBLE);
                    shareProgressC.setVisibility(View.VISIBLE);
                    shareProgressD.setVisibility(View.VISIBLE);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[0]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressA.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgA);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[1]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressC.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgC);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[2]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressD.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgD);
                } else if (files.length == 4) {
                    frame_shareA.setVisibility(View.VISIBLE);
                    frame_shareB.setVisibility(View.GONE);
                    frame_shareC.setVisibility(View.VISIBLE);
                    frame_shareD.setVisibility(View.VISIBLE);
                    frame_shareE.setVisibility(View.VISIBLE);

                    txt_shareImgCount.setVisibility(View.GONE);

                    lay_shareForBelowImgs.setVisibility(View.VISIBLE);

                    iv_shareIsVideoA.setVisibility(View.GONE);
                    iv_shareIsVideoC.setVisibility(View.GONE);
                    iv_shareIsVideoD.setVisibility(View.GONE);
                    iv_shareIsVideoE.setVisibility(View.GONE);

                    shareProgressA.setVisibility(View.VISIBLE);
                    shareProgressC.setVisibility(View.VISIBLE);
                    shareProgressD.setVisibility(View.VISIBLE);
                    shareProgressE.setVisibility(View.VISIBLE);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[0]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressA.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgA);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[1]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressC.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgC);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[2]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressD.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgD);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[3]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressE.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgE);
                } else if (files.length == 5) {
                    frame_shareA.setVisibility(View.VISIBLE);
                    frame_shareB.setVisibility(View.VISIBLE);
                    frame_shareC.setVisibility(View.VISIBLE);
                    frame_shareD.setVisibility(View.VISIBLE);
                    frame_shareE.setVisibility(View.VISIBLE);

                    txt_shareImgCount.setVisibility(View.GONE);

                    lay_shareForBelowImgs.setVisibility(View.VISIBLE);

                    iv_shareIsVideoA.setVisibility(View.GONE);
                    iv_shareIsVideoB.setVisibility(View.GONE);
                    iv_shareIsVideoC.setVisibility(View.GONE);
                    iv_shareIsVideoD.setVisibility(View.GONE);
                    iv_shareIsVideoE.setVisibility(View.GONE);

                    shareProgressA.setVisibility(View.VISIBLE);
                    shareProgressB.setVisibility(View.VISIBLE);
                    shareProgressC.setVisibility(View.VISIBLE);
                    shareProgressD.setVisibility(View.VISIBLE);
                    shareProgressE.setVisibility(View.VISIBLE);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[0]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressA.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgA);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[1]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressB.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgB);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[2]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressC.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgC);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[3]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressD.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgD);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[4]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressE.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgE);
                } else {
                    frame_shareA.setVisibility(View.VISIBLE);
                    frame_shareB.setVisibility(View.VISIBLE);
                    frame_shareC.setVisibility(View.VISIBLE);
                    frame_shareD.setVisibility(View.VISIBLE);
                    frame_shareE.setVisibility(View.VISIBLE);

                    txt_shareImgCount.setVisibility(View.VISIBLE);

                    lay_shareForBelowImgs.setVisibility(View.VISIBLE);

                    iv_shareIsVideoA.setVisibility(View.GONE);
                    iv_shareIsVideoB.setVisibility(View.GONE);
                    iv_shareIsVideoC.setVisibility(View.GONE);
                    iv_shareIsVideoD.setVisibility(View.GONE);
                    iv_shareIsVideoE.setVisibility(View.GONE);

                    shareProgressA.setVisibility(View.VISIBLE);
                    shareProgressB.setVisibility(View.VISIBLE);
                    shareProgressC.setVisibility(View.VISIBLE);
                    shareProgressD.setVisibility(View.VISIBLE);
                    shareProgressE.setVisibility(View.VISIBLE);

                    txt_shareImgCount.setText("+" + (files.length - 5) + " More");

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[0]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressA.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgA);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[1]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressB.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgB);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[2]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressC.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgC);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[3]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressD.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgD);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[4]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressE.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgE);
                }
            } else if (share.getString("type").equalsIgnoreCase("2")) {
                // videos
                lay_sharedMedia.setVisibility(View.VISIBLE);

                String[] files = share.getString("fileLink").split(",");
                if (files.length == 1) {
                    frame_shareA.setVisibility(View.VISIBLE);
                    frame_shareB.setVisibility(View.GONE);
                    frame_shareC.setVisibility(View.GONE);
                    frame_shareD.setVisibility(View.GONE);
                    frame_shareE.setVisibility(View.GONE);

                    iv_shareIsVideoA.setVisibility(View.VISIBLE);

                    lay_shareForBelowImgs.setVisibility(View.GONE);

                    shareProgressA.setVisibility(View.VISIBLE);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[0]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressA.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgA);
                } else if (files.length == 2) {
                    frame_shareA.setVisibility(View.VISIBLE);
                    frame_shareB.setVisibility(View.VISIBLE);
                    frame_shareC.setVisibility(View.GONE);
                    frame_shareD.setVisibility(View.GONE);
                    frame_shareE.setVisibility(View.GONE);

                    iv_shareIsVideoA.setVisibility(View.VISIBLE);
                    iv_shareIsVideoB.setVisibility(View.VISIBLE);

                    lay_shareForBelowImgs.setVisibility(View.GONE);

                    shareProgressA.setVisibility(View.VISIBLE);
                    shareProgressB.setVisibility(View.VISIBLE);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[0]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressA.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgA);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[1]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressB.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgB);
                } else if (files.length == 3) {
                    frame_shareA.setVisibility(View.VISIBLE);
                    frame_shareB.setVisibility(View.GONE);
                    frame_shareC.setVisibility(View.VISIBLE);
                    frame_shareD.setVisibility(View.VISIBLE);
                    frame_shareE.setVisibility(View.GONE);

                    iv_shareIsVideoA.setVisibility(View.VISIBLE);
                    iv_shareIsVideoC.setVisibility(View.VISIBLE);
                    iv_shareIsVideoD.setVisibility(View.VISIBLE);

                    lay_shareForBelowImgs.setVisibility(View.VISIBLE);

                    shareProgressA.setVisibility(View.VISIBLE);
                    shareProgressC.setVisibility(View.VISIBLE);
                    shareProgressD.setVisibility(View.VISIBLE);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[0]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressA.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgA);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[1]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressC.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgC);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[2]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressD.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgD);
                } else if (files.length == 4) {
                    frame_shareA.setVisibility(View.VISIBLE);
                    frame_shareB.setVisibility(View.GONE);
                    frame_shareC.setVisibility(View.VISIBLE);
                    frame_shareD.setVisibility(View.VISIBLE);
                    frame_shareE.setVisibility(View.VISIBLE);

                    txt_shareImgCount.setVisibility(View.GONE);

                    lay_shareForBelowImgs.setVisibility(View.VISIBLE);

                    iv_shareIsVideoA.setVisibility(View.VISIBLE);
                    iv_shareIsVideoC.setVisibility(View.VISIBLE);
                    iv_shareIsVideoD.setVisibility(View.VISIBLE);
                    iv_shareIsVideoE.setVisibility(View.VISIBLE);

                    shareProgressA.setVisibility(View.VISIBLE);
                    shareProgressC.setVisibility(View.VISIBLE);
                    shareProgressD.setVisibility(View.VISIBLE);
                    shareProgressE.setVisibility(View.VISIBLE);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[0]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressA.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgA);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[1]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressC.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgC);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[2]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressD.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgD);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[3]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressE.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgE);
                } else if (files.length == 5) {
                    frame_shareA.setVisibility(View.VISIBLE);
                    frame_shareB.setVisibility(View.VISIBLE);
                    frame_shareC.setVisibility(View.VISIBLE);
                    frame_shareD.setVisibility(View.VISIBLE);
                    frame_shareE.setVisibility(View.VISIBLE);

                    txt_shareImgCount.setVisibility(View.GONE);

                    lay_shareForBelowImgs.setVisibility(View.VISIBLE);

                    iv_shareIsVideoA.setVisibility(View.VISIBLE);
                    iv_shareIsVideoB.setVisibility(View.VISIBLE);
                    iv_shareIsVideoC.setVisibility(View.VISIBLE);
                    iv_shareIsVideoD.setVisibility(View.VISIBLE);
                    iv_shareIsVideoE.setVisibility(View.VISIBLE);

                    shareProgressA.setVisibility(View.VISIBLE);
                    shareProgressB.setVisibility(View.VISIBLE);
                    shareProgressC.setVisibility(View.VISIBLE);
                    shareProgressD.setVisibility(View.VISIBLE);
                    shareProgressE.setVisibility(View.VISIBLE);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[0]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressA.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgA);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[1]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressB.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgB);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[2]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressC.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgC);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[3]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressD.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgD);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[4]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressE.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgE);
                } else {
                    frame_shareA.setVisibility(View.VISIBLE);
                    frame_shareB.setVisibility(View.VISIBLE);
                    frame_shareC.setVisibility(View.VISIBLE);
                    frame_shareD.setVisibility(View.VISIBLE);
                    frame_shareE.setVisibility(View.VISIBLE);

                    txt_shareImgCount.setVisibility(View.VISIBLE);

                    lay_shareForBelowImgs.setVisibility(View.VISIBLE);

                    iv_shareIsVideoA.setVisibility(View.VISIBLE);
                    iv_shareIsVideoB.setVisibility(View.VISIBLE);
                    iv_shareIsVideoC.setVisibility(View.VISIBLE);
                    iv_shareIsVideoD.setVisibility(View.VISIBLE);
                    iv_shareIsVideoE.setVisibility(View.VISIBLE);

                    shareProgressA.setVisibility(View.VISIBLE);
                    shareProgressB.setVisibility(View.VISIBLE);
                    shareProgressC.setVisibility(View.VISIBLE);
                    shareProgressD.setVisibility(View.VISIBLE);
                    shareProgressE.setVisibility(View.VISIBLE);

                    txt_shareImgCount.setText("+" + (files.length - 5) + " More");

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[0]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressA.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgA);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[1]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressB.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgB);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[2]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressC.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgC);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[3]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressD.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgD);

                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + files[4]).
                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                            listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    shareProgressE.setVisibility(View.GONE);
                                    return true;
                                }
                            }).
                            into(iv_shareImgE);
                }
            }
        } else {
            lay_shareLayout.setVisibility(View.GONE);
        }

        likeCount = Integer.parseInt(post.getString("totalLikes"));
        dislikeCount = Integer.parseInt(post.getString("totalDislikes"));
        commentCount = Integer.parseInt(post.getString("totalComments"));
        shareCount = Integer.parseInt(post.getString("totalShares"));

        txt_likeDislikeCount.setText(likeCount + " likes, " + dislikeCount + " dislikes");

        txt_commentShareCount.setText(commentCount + " comments, " + shareCount + " shares");

        final JSONObject action = jo.getJSONObject("action");

        like = !action.getString("like").equals("0");
        dislike = !action.getString("dislike").equals("0");
        comment = !action.getString("comment").equals("0");
        share = !action.getString("share").equals("0");

        iv_like.setImageResource(like ? R.drawable.ic_like_fill : R.drawable.ic_like);
        iv_dislike.setImageResource(dislike ? R.drawable.ic_dislike_fill : R.drawable.ic_dislike);
        iv_comment.setImageResource(comment ? R.drawable.ic_comment_fill : R.drawable.ic_comment);
        iv_share.setImageResource(share ? R.drawable.ic_share_fill : R.drawable.ic_share);

        txt_like.setTextColor(like ? Color.parseColor("#e53935") : Color.parseColor("#000000"));
        txt_dislike.setTextColor(dislike ? Color.parseColor("#e53935") : Color.parseColor("#000000"));
        txt_comment.setTextColor(comment ? Color.parseColor("#e53935") : Color.parseColor("#000000"));
        txt_share.setTextColor(share ? Color.parseColor("#e53935") : Color.parseColor("#000000"));

        lay_like.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    lay_like.animate().setInterpolator(sDecelerater).
                            scaleX(.7f).scaleY(.7f);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    lay_like.animate().setInterpolator(sOvershooter).
                            scaleX(1f).scaleY(1f);

                    if (dislike) {
                        dislike = false;
                        dislikeCount--;
                        iv_dislike.setImageResource(R.drawable.ic_dislike);
                        txt_dislike.setTextColor(Color.parseColor("#000000"));
                        txt_likeDislikeCount.setText(likeCount + " likes, " + dislikeCount + " dislikes");
                    }

                    if (like) {
                        like = false;
                        likeCount--;
                        iv_like.setImageResource(R.drawable.ic_like);
                        txt_like.setTextColor(Color.parseColor("#000000"));
                        txt_likeDislikeCount.setText(likeCount + " likes, " + dislikeCount + " dislikes");
                    } else {
                        like = true;
                        likeCount++;
                        iv_like.setImageResource(R.drawable.ic_like_fill);
                        txt_like.setTextColor(Color.parseColor("#e53935"));
                        txt_likeDislikeCount.setText(likeCount + " likes, " + dislikeCount + " dislikes");
                    }

                    try {
                        likeDislikeFeeds(post.getString("id").toString(), "like");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

        lay_dislike.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    lay_dislike.animate().setInterpolator(sDecelerater).
                            scaleX(.7f).scaleY(.7f);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    lay_dislike.animate().setInterpolator(sOvershooter).
                            scaleX(1f).scaleY(1f);

                    if (like) {
                        like = false;
                        likeCount--;
                        iv_like.setImageResource(R.drawable.ic_like);
                        txt_like.setTextColor(Color.parseColor("#000000"));
                        txt_likeDislikeCount.setText(likeCount + " likes, " + dislikeCount + " dislikes");
                    }

                    if (dislike) {
                        dislike = false;
                        dislikeCount--;
                        iv_dislike.setImageResource(R.drawable.ic_dislike);
                        txt_dislike.setTextColor(Color.parseColor("#000000"));
                        txt_likeDislikeCount.setText(likeCount + " likes, " + dislikeCount + " dislikes");
                    } else {
                        dislike = true;
                        dislikeCount++;
                        iv_dislike.setImageResource(R.drawable.ic_dislike_fill);
                        txt_dislike.setTextColor(Color.parseColor("#e53935"));
                        txt_likeDislikeCount.setText(likeCount + " likes, " + dislikeCount + " dislikes");
                    }

                    try {
                        likeDislikeFeeds(post.getString("id").toString(), "dislike");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

        lay_comment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    lay_comment.animate().setInterpolator(sDecelerater).
                            scaleX(.7f).scaleY(.7f);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    lay_comment.animate().setInterpolator(sOvershooter).
                            scaleX(1f).scaleY(1f);

                    try {
                        startActivityForResult(new Intent(getBaseContext(), CommentActivity.class).
                                        putExtra("postId", post.getString("id")).
                                        putExtra("position", 0),
                                10);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

        lay_share.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    lay_share.animate().setInterpolator(sDecelerater).
                            scaleX(.7f).scaleY(.7f);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    lay_share.animate().setInterpolator(sOvershooter).
                            scaleX(1f).scaleY(1f);

                    try {
                        startActivityForResult(new Intent(getBaseContext(), AddFeedActivity.class).
                                        putExtra("share", true).
                                        putExtra("name", post.getString("firstName") + " " + post.getString("lastName")).
                                        putExtra("postId", post.getString("id")).
                                        putExtra("notifyToUser", post.getString("userId")).
                                        putExtra("desc", post.getString("description").trim()),
                                40);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
    }

    public void likeDislikeFeeds(String postId, String type) {
        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", sp.getString("userId", "")).
                add("id", postId).
                add("type", type).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.LIKE_DISLIKE_FEED).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();

                System.out.println("rrrrrrrrrrrttttttttttttt " + myResponse);
                try {
                    JSONObject jo = new JSONObject(myResponse);
                    if (jo.getString("status").equals("1")) {
                        // no need to get response
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}