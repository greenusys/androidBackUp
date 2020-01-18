package com.icosom.social.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.icosom.social.R;
import com.icosom.social.model.CommentModel;
import com.icosom.social.recycler_adapter.CommentRecyclerAdapter;
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

public class CommentActivity extends AppCompatActivity {
    RecyclerView rvShowComments;
    RecyclerView.LayoutManager layoutManager;
    AppController appController;
    ArrayList<CommentModel> commentModels;
    CommentRecyclerAdapter commentAdapter;
    EditText edt_comment;
    int del = 0;
    ImageView iv_sendComment;
    DecelerateInterpolator sDecelerater = new DecelerateInterpolator();
    OvershootInterpolator sOvershooter = new OvershootInterpolator(5f);
    public static String postId;
    String device_token="";
   public static String post_user_id="";
    boolean isComment = false;
    int position;
    CircleImageView ivProfileImageComment;
    SharedPreferences sp;
    TextView tvCommentCountComment;
    BottomSheetDialog sheetDialog;
    String commentId;
    int commentPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        appController = (AppController) getBaseContext().getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ivProfileImageComment = findViewById(R.id.ivProfileImageComment);
        edt_comment = findViewById(R.id.edt_comment);
        tvCommentCountComment = findViewById(R.id.tvCommentCountComment);
        iv_sendComment = findViewById(R.id.iv_sendComment);

        rvShowComments = findViewById(R.id.rvShowComments);
        layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, true);

        commentModels = new ArrayList<>();

        commentAdapter = new CommentRecyclerAdapter(CommentActivity.this, commentModels, sp.getString("userId", ""));
        rvShowComments.setLayoutManager(layoutManager);
        rvShowComments.setAdapter(commentAdapter);

        sheetDialog = new BottomSheetDialog(CommentActivity.this);

        View sheetView = LayoutInflater.from(CommentActivity.this).inflate(R.layout.more_options_sheet_layout_comment, null);
        sheetDialog.setContentView(sheetView);

        TextView txt_edtComment = sheetView.findViewById(R.id.txt_edtComment);
        TextView txt_deleteComment = sheetView.findViewById(R.id.txt_deleteComment);


        txt_deleteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteComment();
            }
        });



        if(getIntent().getStringExtra("device_token")!=null)
        device_token = getIntent().getStringExtra("device_token");

        if(getIntent().getStringExtra("postId")!=null)
        postId = getIntent().getStringExtra("postId");

        if(getIntent().getStringExtra("post_user_id")!=null)
        post_user_id = getIntent().getStringExtra("post_user_id");

        if(getIntent().getStringExtra("position")!=null)
        position = Integer.parseInt(getIntent().getStringExtra("position"));


        Log.e("comment_token",""+device_token);//post user device token
        Log.e("comment_post_user_id",""+post_user_id);//user post id
        Log.e("comment_postId",""+postId);//user post id



        txt_edtComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();


            }
        });
        fetchAllComments(postId);

        iv_sendComment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_sendComment.animate().setInterpolator(sDecelerater).
                            scaleX(.7f).scaleY(.7f);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_sendComment.animate().setInterpolator(sOvershooter).
                            scaleX(1f).scaleY(1f);

                    if (!edt_comment.getText().equals("")) {
                        if (del == 1) {
                            putCommentss(postId, edt_comment.getText().toString());
                        } else {
                            putComments(postId, edt_comment.getText().toString());
                        }
                    }
                }
                return false;
            }
        });

        Glide.
                with(getBaseContext()).
                load(CommonFunctions.FETCH_IMAGES + sp.getString("profile", "")).
                apply(new RequestOptions().placeholder(R.drawable.placeholder).
                        diskCacheStrategy(DiskCacheStrategy.ALL)).
                thumbnail(0.01f).
                into(ivProfileImageComment);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (tvCommentCountComment != null) {
                    tvCommentCountComment.setVisibility(View.GONE);
                }
            }
        }, 5000);
    }

    public void showBottomSheet(String commentId, int position) {
        this.commentId = commentId;
        this.commentPosition = position;
        sheetDialog.show();
    }

    public void edit() {
        del = 1;
        sheetDialog.hide();
        edt_comment.setText(commentModels.get(commentPosition).getComment());

    }

    public void deleteComment() {
        commentModels.remove(commentPosition);
        commentAdapter.notifyDataSetChanged();
        sheetDialog.hide();

        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("commentId", commentId).
                add("postId", postId).
                add("userId", sp.getString("userId", "")).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.DELETE_COMMENT).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                CommentActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                CommentActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                // no response needed
            }
        });
    }
    public void deleteComments() {
        commentModels.remove(commentPosition);
        commentAdapter.notifyDataSetChanged();
        sheetDialog.hide();

        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("commentId", commentId).
                add("postId", postId).
                add("userId", sp.getString("userId", "")).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.DELETE_COMMENT).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                CommentActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                CommentActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Updated", Toast.LENGTH_SHORT).show();
                        del=0;
                    }
                });
                // no response needed
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public void fetchAllComments(String postId)
    {
        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("postId", postId).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.FETCH_COMMENTS).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                CommentActivity.this.runOnUiThread(new Runnable() {
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
                    JSONArray ja = new JSONArray(myResponse);
                    if (ja.length() != 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            commentModels.add(new CommentModel(
                                    jo.getString("device_token"),
                                    jo.getString("id"),
                                    jo.getString("userId"),
                                    jo.getString("comment"),
                                    jo.getString("activityTime"),
                                    jo.getString("profilePicture"),
                                    jo.getString("firstName"),
                                    jo.getString("lastName")
                            ));
                        }

                        CommentActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvCommentCountComment.setText(commentModels.size() + " comments");
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

    public void putComments(String postId, String comment)
    {

        final String userid=sp.getString("userId", "");

        System.out.println("putComments_method-"+device_token);

        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("postId", postId).
                add("comment", comment).
                add("device_token", device_token).
                add("userId", sp.getString("userId", "")).

                build();

        Request request = new Request.Builder().
                url(CommonFunctions.ADD_COMMENTS).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                CommentActivity.this.runOnUiThread(new Runnable() {
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
                    JSONArray ja = new JSONArray(myResponse);
                    if (ja.length() != 0) {
                        MediaPlayer.create(CommentActivity.this, R.raw.beep3);
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            commentModels.add(new CommentModel(
                                    jo.getString("device_token"),
                                    jo.getString("id"),
                                    jo.getString("userId"),
                                    jo.getString("comment"),
                                    jo.getString("activityTime"),
                                    jo.getString("profilePicture"),
                                    jo.getString("firstName"),
                                    jo.getString("lastName")
                            ));



                            System.out.println("check_user_and_post_id");
                            System.out.println("comment_token"+device_token);
                            System.out.println("comment_post_user_Id"+post_user_id);
                            //for send notification
                            if(device_token!=null && !device_token.equals(""))
                            {
                                if (!userid.equalsIgnoreCase(post_user_id))
                                {
                                    System.out.println("like+userid"+userid);


                                    sendNotification_To_User(post_user_id,device_token,"comment");
                                }
                                else
                                    System.out.println("same post's user id");
                            }
                            else
                                System.out.println("Device_Token_Is_Null");




                        }

                        CommentActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                commentAdapter.notifyDataSetChanged();
                                isComment = true;
                                edt_comment.setText("");

                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, new Intent().
                putExtra("isComment", isComment).
                putExtra("position", position));
        super.onBackPressed();
    }

    public void putCommentss(String postId, String comment)
    {

        final String userid=sp.getString("userId", "");


        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("postId", postId).
                add("comment", comment).
                add("device_token", device_token).
                add("userId",userid).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.ADD_COMMENTS).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                CommentActivity.this.runOnUiThread(new Runnable() {
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
                    JSONArray ja = new JSONArray(myResponse);
                    if (ja.length() != 0) {
                        MediaPlayer.create(CommentActivity.this, R.raw.beep3);
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            commentModels.add(new CommentModel(
                                    jo.getString("device_token"),
                                    jo.getString("id"),
                                    jo.getString("userId"),
                                    jo.getString("comment"),
                                    jo.getString("activityTime"),
                                    jo.getString("profilePicture"),
                                    jo.getString("firstName"),
                                    jo.getString("lastName")
                            ));



                            System.out.println("check_user_and_post_id");
                            System.out.println("comment_token"+device_token);
                            System.out.println("comment_post_user_Id"+post_user_id);
                            //for send notification
                           if(device_token!=null && !device_token.equals(""))
                            {
                                if (!userid.equalsIgnoreCase(post_user_id))
                                {
                                    System.out.println("like+userid"+userid);


                                    sendNotification_To_User(post_user_id,device_token,"comment");
                                }
                                else
                                    System.out.println("same post's user id");
                            }
                            else
                                System.out.println("Device_Token_Is_Null");



                        }

                        CommentActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                commentAdapter.notifyDataSetChanged();
                                isComment = true;
                                edt_comment.setText("");
                                deleteComments();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }




    public void sendNotification_To_User(String post_user_id,String device_token,String type)

    {

        String user_name=sp.getString("firstName", "")
                + " " + sp.getString("lastName", "");

        //String device_token = sp.getString("device_token", "");//firebase token id


        String message="";
        String title="Icosom";


            message =user_name+" has "+" comment on your post";

        System.out.println("kaif_SendNotificationMethod");
        System.out.println("message"+message);
        System.out.println("title"+title);
        System.out.println("device_token"+device_token);


        RequestBody body = new FormBody.Builder().
                add("send_to", "single").
                add("firebase_token", device_token).
                add("message", message).
                add("title", "icosom").
                add("image_url", "").
                add("action", "").
                add("user_id", post_user_id).

                build();

        Request request = new Request.Builder().
                url("https://icosom.com/kaif_notification/newindex.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                CommentActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();

                System.out.println("notification_send_Success");
                /*try {
                    JSONObject jo = new JSONObject(myResponse);
                    if (jo.getString("success").equals("1")) {
                        Log.e("notification_success","");
                        System.out.println("notification_success"+jo);
                    }
                    else
                    {
                        Log.e("notification__failed","");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        });
    }


}