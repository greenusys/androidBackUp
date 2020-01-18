package com.icosom.social.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.icosom.social.R;
import com.icosom.social.model.Comment_Reply_Model;
import com.icosom.social.recycler_adapter.Comment_Reply_Adapter;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Comment_Reply_Activity extends AppCompatActivity {

    private AppController appController;
    private Comment_Reply_Adapter commentAdapter;
    RecyclerView rvShowComments;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Comment_Reply_Model> commentModels=new ArrayList<>();
    private SharedPreferences sp;
    private TextView tvCommentCountComment;
    String commnent_id="",post_id="",comment="",profile="";
    private EditText edt_comment;
    private ImageView iv_sendComment;
    private BottomSheetDialog sheetDialog;
    String delete_activity_id="";
    int delete_activity_position;
    private String today_date="";
    private CircleImageView ivProfileImageComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment__reply_);


        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        today_date = dateFormat.format(date);

        System.out.println("today_date_"+today_date);


        initViews();
        setListener();
        commnent_id=getIntent().getStringExtra("comment_id");
        post_id=getIntent().getStringExtra("post_id");
        comment=getIntent().getStringExtra("comment");
        profile=getIntent().getStringExtra("profile");

        System.out.println("comment_id"+commnent_id);
        System.out.println("post_id"+post_id);
        System.out.println("comment"+comment);
        System.out.println("profile"+profile);

        fetchAllComments(commnent_id);



    }

    private void setListener() {

        iv_sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edt_comment.getText().toString().equals(""))
                    putComments(commnent_id, post_id,edt_comment.getText().toString(),sp.getString("userId", ""),profile);
                else
                    System.out.println("api_not_called");
            }
        });





    }

    private void initViews() {
        appController = (AppController) getBaseContext().getApplicationContext();
        tvCommentCountComment = findViewById(R.id.tvCommentCountComment);
        edt_comment = findViewById(R.id.edt_comment);
        iv_sendComment = findViewById(R.id.iv_sendComment);
        ivProfileImageComment = findViewById(R.id.ivProfileImageComment);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());


        //recyclerview
        rvShowComments = findViewById(R.id.rvShowComments);
        layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        commentAdapter = new Comment_Reply_Adapter(Comment_Reply_Activity.this, commentModels, sp.getString("userId", ""));
        rvShowComments.setLayoutManager(layoutManager);
        rvShowComments.setAdapter(commentAdapter);

        //bottom sheet
        sheetDialog = new BottomSheetDialog(Comment_Reply_Activity.this);
        View sheetView = LayoutInflater.from(Comment_Reply_Activity.this).inflate(R.layout.more_options_sheet_layout_reply_comment, null);
        sheetDialog.setContentView(sheetView);
        TextView txt_deleteComment = sheetView.findViewById(R.id.txt_deleteComment);
        txt_deleteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteComments(delete_activity_id);
            }
        });


        Glide.
                with(getBaseContext()).
                load(CommonFunctions.FETCH_IMAGES + sp.getString("profile", "")).
                apply(new RequestOptions().placeholder(R.drawable.placeholder).
                        diskCacheStrategy(DiskCacheStrategy.ALL)).
                thumbnail(0.01f).
                into(ivProfileImageComment);

    }

    public void showBottomSheet(String delete_activity_id, int delete_activity_position) {



        this.delete_activity_id = delete_activity_id;
        this.delete_activity_position = delete_activity_position;
        sheetDialog.show();

    }

    public void fetchAllComments(String activity_id)
    {
        RequestBody body = new FormBody.Builder().
                add("activity_id", activity_id).
                build();

        Request request = new Request.Builder().
                url("https://icosom.com/social/main/new_api/fetch_replies.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Comment_Reply_Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    JSONObject ja = new JSONObject(myResponse);

                    if (ja.getString("status").equals("1"))
                    {

                        JSONArray main=ja.getJSONArray("data");

                        for (int i = 0; i < main.length(); i++)
                        {
                            JSONObject item = main.getJSONObject(i);

                            Comment_Reply_Model model=new Comment_Reply_Model();
                            model.setAct_id(item.getString("act_id"));
                            model.setUserId(item.getString("userId"));
                            model.setPostId(item.getString("postId"));
                            model.setComment(item.getString("comment"));
                            model.setProfilePicture(item.getString("profilePicture"));
                            model.setTime(item.getString("activityTime"));
                            model.setName(item.getString("firstName")+" "+item.getString("lastName"));

                            commentModels.add(model);

                        }



                        Comment_Reply_Activity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvCommentCountComment.setText(commentModels.size() + " Replies");
                                commentAdapter.notifyDataSetChanged();
                            }
                        });




                    }

                    else
                    {
                        Comment_Reply_Activity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvCommentCountComment.setText(commentModels.size() + " Replies");
                                commentAdapter.notifyDataSetChanged();
                            }
                        });


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void putComments(final String activity_id, final String post_id, final String comment, final String user_id,final String profile)
    {
        System.out.println("put_commnet_called");

        RequestBody body = new FormBody.Builder().
                add("activity_id", activity_id).
                add("post_id", post_id).
                add("comment", comment).
                add("user_id",user_id ).

                build();

        Request request = new Request.Builder().
                url("https://icosom.com/social/main/new_api/comments_reply.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Comment_Reply_Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                System.out.println("response"+myResponse);

                try {
                    JSONObject object=new JSONObject(myResponse);

                    if(object.getString("status").equals("1"))
                    {
                        Comment_Reply_Activity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                edt_comment.setText("");
                                Comment_Reply_Model model=new Comment_Reply_Model();
                                model.setAct_id(activity_id);
                                model.setUserId(user_id);
                                model.setPostId(post_id);
                                model.setComment(comment);
                                model.setProfilePicture(profile);
                                model.setTime(today_date);
                                model.setName(sp.getString("firstName","")+" "+sp.getString("lastName",""));

                                commentModels.add(model);

                                commentAdapter.notifyDataSetChanged();


                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    public void deleteComments(String activity_id) {
        System.out.println("delete_comment_called"+activity_id);
        System.out.println("delete_comment_position"+delete_activity_position);




        sheetDialog.hide();

        RequestBody body = new FormBody.Builder().
                add("activity_id", activity_id).
                build();

        Request request = new Request.Builder().
                url("https://icosom.com/social/main/new_api/delete_reply.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();

                Comment_Reply_Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            JSONObject object = new JSONObject(myResponse);

                            if (object.getString("status").equals("1")) {
                                System.out.println("message_deleted");
                                commentModels.remove(delete_activity_position);
                                commentAdapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
                // no response needed
            }
        });
    }




}
