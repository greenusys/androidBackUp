package com.example.g116.vvn_social.Home_Activities;


import android.os.Build;
import android.os.Bundle;


import android.support.annotation.Nullable;

import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

 import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.g116.vvn_social.Adapter.CommentRecyclerAdapter;
import com.example.g116.vvn_social.Adapter.Home_Post_Adapter;
import com.example.g116.vvn_social.Modal.CommentModel;
import com.example.g116.vvn_social.Network_Package.AppController;
import com.example.g116.vvn_social.R;
import com.example.g116.vvn_social.Tab_Layout_Fragments.HomeFragment;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;


import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.lang.Integer.parseInt;

public class CommentActivity extends AppCompatActivity {

    private RecyclerView rvShowComments;


    AppController appController;
  public  static   ArrayList<CommentModel> commentModels;
    public  static CommentRecyclerAdapter commentAdapter;
    String post_id="",type="",user_id="",user_pic="";
    RecyclerView.LayoutManager layoutManager;
    TextView count_comments;
    public static EditText edt_comment;
    ImageView iv_sendComment;


    public static int position;
    public static String  edit_comment_id;
    public static String edit="";
    public static String edit_text="";
    public static int backfrom=0;

    CircleImageView ivProfileImageComment;

    HomeFragment homeFragment;
    Home_Post_Adapter home_post_adapter;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        rvShowComments = findViewById(R.id.rvShowComments);
        count_comments = findViewById(R.id.tvCommentCountComment);
        edt_comment = findViewById(R.id.edt_comment);
        iv_sendComment = findViewById(R.id.iv_sendComment);
        ivProfileImageComment = findViewById(R.id.ivProfileImageComment);

        appController = (AppController) getBaseContext().getApplicationContext();
        commentModels = new ArrayList<>();


        getSupportActionBar().setTitle("Comments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, true);
        rvShowComments.setLayoutManager(layoutManager);

        post_id = getIntent().getStringExtra("post_id");
        type = getIntent().getStringExtra("type");
        user_id = getIntent().getStringExtra("user_id");
        user_pic = getIntent().getStringExtra("user_pic");

        System.out.println("CommentActivity_user_pic"+user_pic);

        Glide.with(getApplicationContext())
                .load(user_pic).
                apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                into(ivProfileImageComment);;

        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final LocalDateTime now = LocalDateTime.now();
        System.out.println("date_kaif"+dtf.format(now)); //2016-11/-16 12:08:43






        commentAdapter = new CommentRecyclerAdapter(edt_comment,CommentActivity.this, commentModels, user_id,appController,post_id,type,count_comments);
        rvShowComments.setAdapter(commentAdapter);

        fetchAllComments(commentAdapter,count_comments,commentModels,appController,post_id,type,user_id);






               iv_sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("edit_text"+edit);
                System.out.println("edit_position"+position);
                System.out.println("edit_text"+edit_text);
                System.out.println("edit_edit_comment_id"+edit_comment_id);


                if(edit.equalsIgnoreCase("edit"))
                {


                   // String final_time= calculate_time(postTime,server_time);


                    commentModels.get(position).setComment(edt_comment.getText().toString());//update modals text



                  //  commentModels.get(position).setActivityTime(dtf.format(now));//update modals time



                    commentAdapter = new CommentRecyclerAdapter(edt_comment,CommentActivity.this, commentModels, user_id,appController,post_id,type,count_comments);
                    rvShowComments.setAdapter(commentAdapter);
                    commentAdapter.notifyDataSetChanged();
                    editComments(edit_comment_id,edt_comment.getText().toString());
                    edt_comment.setText("");
                    edit="";

                }
                else
                putComments(commentAdapter,count_comments,commentModels,user_id,post_id,edt_comment.getText().toString(),type);
            }
        });






    }



    public void edit(ArrayList<CommentModel> commentModels, TextView edt_comment, int position,String edit) {
        this.position=position;
        this.edit=edit;

        //edt_comment.setText(commentModels.get(position).getComment());

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public void fetchAllComments(CommentRecyclerAdapter commentAdapter, final TextView count_comments, final ArrayList<CommentModel> commentModels, AppController appController, final String post_id, String type, String user_id) {



        Log .e("fetchAllComments_method","ksdj");
        Log .e("post_id","ksdj"+post_id);
        Log .e("type","ksdj"+type);



        if(commentModels!=null)
            commentModels.clear();


        RequestBody body = new FormBody.Builder().
                add("post_id", post_id).
                add("type", type).
                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/fetch_comment.php").
                post(body).
                build();

        final CommentRecyclerAdapter finalCommentAdapter = commentAdapter;
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

                    JSONObject mainjson = new JSONObject(myResponse);
                    String status=mainjson.getString("status");
                    String user_type="";
                    String path = "https://vvn.city/";


                    if (status.equals("1"))
                    {
                        Log .e("fetch_comment_status","ksdj"+status);


                  //  System.out.println("sayed_champ " + homeFragment.modelFeedArrayList.size());


                   /*    homeFragment.modelFeedArrayList.get(0).setTotalComments("10");
                        homeFragment.homePostAdapter.notifyDataSetChanged();*/




                        JSONArray data=mainjson.getJSONArray("data");
                        String server_time=mainjson.getString("servertime");
                        for (int i=0;i<data.length();i++) {

                            JSONObject jo = data.getJSONObject(i);


                        String postTime= jo.getString("posted_on");

                            String final_time= calculate_time(postTime,server_time);



                            String value= jo.getString("cmt_user_type");
                            if(value.equals("1"))
                                user_type="teacher";
                            if(value.equals("2"))
                                user_type="student";

                            String picture=jo.getString("picture");

                            picture=picture.replace("../","");

                            Log.e("Comment__path+picture",""+path+picture);
                            Log.e("Comment__user_type",""+user_type);
                            Log.e("Comment__user_type-2",""+jo.getString("cmt_user_type"));


                            commentModels.add(new CommentModel(
                                    jo.getString("id"),
                                    jo.getString("user_id"),
                                    jo.getString("comment"),
                                    final_time,
                                    path+picture,
                                    jo.getString("firstName"),
                                    jo.getString("lastName"),
                                    user_type
                            ));


                        }

                        Log .e("fetch_comment_list_size","ksdj"+commentModels.size());

                        CommentActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               // count_comments.setVisibility(View.VISIBLE);
                                //count_comments.setText(commentModels.size() + " comments");
                                count_comments.setVisibility(View.GONE);
                                finalCommentAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                    else
                    {
                        CommentActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            count_comments.setVisibility(View.VISIBLE);
                        }
                    });


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    public void putComments(final CommentRecyclerAdapter commentRecyclerAdapter, final TextView count_comments, final ArrayList<CommentModel>commentModels, final String user_id, final String postId, String comment, String type2) {

        Log .e("Put_Comments_method","ksdj");


        Log .e("user_id","ksdj"+user_id);
        Log .e("postId","ksdj"+postId);
        Log .e("comment","ksdj"+comment);
        Log .e("type","ksdj"+type2);

        RequestBody body = new FormBody.Builder().
                add("user_id", user_id).
                add("post_id", postId).
                add("comment", comment).
                add("type", type).
                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/add_comments.php").
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
                    JSONObject jo = new JSONObject(myResponse);
                    if (jo.getString("status").equals("1")) {
                        edt_comment.setText("");

                        String message=jo.getString("msg");
                        Log.e("message_like",""+message);

                        fetchAllComments(commentRecyclerAdapter,count_comments,commentModels,appController,post_id,type,user_id);

                        view_post(user_id,postId);



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        });
    }

    public void view_post(String user_id, String post_id) {


        System.out.println("view_post_called");
        System.out.println("user_id"+user_id);
        System.out.println("post_id"+post_id);


        RequestBody body = new FormBody.Builder().
                add("user_id", user_id).
                add("post_id", post_id).

                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/countview.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                CommentActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                    }
                });
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    JSONArray jo = new JSONArray(myResponse);
                    JSONObject jsonObject=jo.getJSONObject(0);
                    String total_view=jsonObject.getString("count");




                    System.out.println("total_view"+total_view);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        });
    }


    public void editComments( String cmt_id, String cmt) {

        Log .e("editComments_method","ksdj"+commentAdapter);


        Log .e("cmt_id",""+cmt_id);
        Log .e("cmt",""+cmt);


        RequestBody body = new FormBody.Builder().
                add("cmt_id", cmt_id).
                add("cmt", cmt).

                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/edit_comment.php").
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
                    JSONObject jo = new JSONObject(myResponse);
                    if (jo.getString("status").equals("1")) {



                        String message=jo.getString("msg");
                        Log.e("message_like",""+message);





                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        });
    }







    public void deleteComments(final TextView count_comments,final ArrayList<CommentModel>commentModels,final AppController appController, String cmt_id,final String post_id,final String type,final String user_id)
    {


        Log .e("delete_Comments_method","ksdj");



        Log .e("cmt_id",""+cmt_id);




        RequestBody body = new FormBody.Builder().
                add("cmt_id", cmt_id).
                add("type", type).
                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/delete_comment.php").
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
                    JSONObject jo = new JSONObject(myResponse);
                    if (jo.getString("status").equals("1")) {

                        String message=jo.getString("msg");
                        Log.e("delete_comments",""+message);
                    //   fetchAllComments(commentRecyclerAdapter,count_comments,commentModels,appController,post_id,type,user_id);



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

    private String calculate_time(String postTime2, String server_timee) {

        System.out.println("calculate_time");

        String post_time= arrange_post_time(postTime2);
        String server_time= arrange_server_time(server_timee);
        // System.out.println("calc_time_post_time "+post_time);
        //System.out.println("calc_time_server_time"+server_time);

        String post_time_date[]=post_time.split(" ");
        String server_time_date[]=server_time.split(" ");

        String post_time2[];
        String server_time2[];
        int post_time2_hr;
        int server_time2_hr;
        int min_diff=0;
        int post_time2_min=0;
        int server_time2_min=0;

        //if post and server date is same
        if(post_time_date[0].equalsIgnoreCase(server_time_date[0]))
        {

            post_time2=post_time_date[1].split(":");
            server_time2=server_time_date[1].split(":");

            post_time2_hr= parseInt(post_time2[0]);
            server_time2_hr= parseInt(server_time2[0]);
            post_time2_min= parseInt(post_time2[1]);
            server_time2_min= parseInt(server_time2[1]);
            min_diff=Math.abs(post_time2_min-server_time2_min);

            //if post and server hour is same then calculate min difference
            if(post_time2_hr==server_time2_hr)
            {
                System.out.println("post and server hour is same");


                if(min_diff!=0)
                    return String.valueOf(min_diff)+" minute ago ";
                else
                    return "Just Now";

            }

            //if post and server hour diff is 1

            else if(Math.abs(post_time2_hr-server_time2_hr)==1 )
            {
                System.out.println("post and server hour diff is 1");

                int result1=post_time2_hr*60+post_time2_min;
                int result2=server_time2_hr*60+server_time2_min;
                int res=Math.abs(result1-result2);


                if(res>59)
                    return "About an Hour Ago";
                else
                    return String.valueOf(res)+" minute ago ";


            }


           /* else if(Math.abs(post_time2_hr-server_time2_hr)==1 && post_time2_min==server_time2_min )
            {
                System.out.println("post and server hour diff is 1 and post_time2_min==server_time2_min");

                return "About an Hour Ago";

            }


            //if post and server hour is different  but date is same
            //if post and server hour diff is only 1
            // then caluclate difference

            else if(Math.abs(post_time2_hr-server_time2_hr)==1 && post_time2_min>0 && server_time2_min>0 )
            {
                System.out.println("post and server hour diff is 1 and post_time2_min > 0 ");

                post_time2_min= parseInt(post_time2[1]);
                server_time2_min= parseInt(server_time2[1]);


                if(min_diff!=0)
                    return String.valueOf(min_diff)+" minute ago ";
                else
                    return "Just Now";

            }
            else if(Math.abs(post_time2_hr-server_time2_hr)==1 && post_time2_min>0 *//*&& server_time2_min>0*//**//* *//* )
            {
                System.out.println("post and server hour diff is 1 and server_time2_min > 0 ");

                post_time2_min= parseInt(post_time2[1]);
                server_time2_min= parseInt(server_time2[1]);


                if(min_diff!=0)
                    return String.valueOf(min_diff)+" minute ago ";
                else
                    return "Just Now";

            }else if(Math.abs(post_time2_hr-server_time2_hr)==1 && server_time2_min>0 *//*&& server_time2_min>0*//**//* *//* )
            {
                System.out.println("post and server hour diff is 1 and server_time2_min > 0 ");

                post_time2_min= parseInt(post_time2[1]);
                server_time2_min= parseInt(server_time2[1]);


                if(min_diff!=0)
                    return String.valueOf(min_diff)+" minute ago ";
                else
                    return "Just Now";

            }

            //if post and server hour is different  but date is same
            //if post and server hour diff is only 1 and  post_time2_min is 0 and
            // then caluclate difference
            else if(Math.abs(post_time2_hr-server_time2_hr)==1 && post_time2_min==0 )
            {
                System.out.println("post and server hour diff is 1 and post_time2_min==0");

                    return "About an Hour Ago";
            }

            //if post and server hour is different  but date is same
            //if post and server hour diff is only 1 and  server_time2_min is 0 and
            // then caluclate difference
            else if(Math.abs(post_time2_hr-server_time2_hr)==1 && server_time2_min==0 )
            {
                System.out.println("post and server hour diff is 1 and server_time2_min==0");

                    return "About an Hour Ago";
            }
*/
            /*else
            {
                return "About an Hour Ago";
            }*/
            else
                return post_time;

        }
        else
        {
            System.out.println("calc_post_date "+post_time_date[0]+" "+post_time_date[1]);
            //return post_time_date[0]+" "+post_time_date[1];
            return post_time;

        }



    }



    public String arrange_post_time(String postTime)
    {

        String date2[]=postTime.split(" ");
        String date=date2[0];//only date
        String time=date2[1];//only time
        String date_result="";
        String time_result="";
        int hours=0;
        String date3[]=date.split("-");
        String time3[]=time.split(":");

        date_result=date3[2]+"-"+date3[1]+"-"+date3[0];


        if(parseInt(time3[0])>12)
        {
            hours= parseInt(time3[0])-12;

        }
        else
            hours= parseInt(time3[0]);



        //  System.out.println("time_champ"+String.valueOf(parseInt(time3[0])));

        if(parseInt(time3[0])>=12 ) {
            // System.out.println("if_kaif");
            time_result = Integer.toString(hours) + ":" + time3[1] + ":" + time3[2] + " PM";
        }
        else {
            // System.out.println("else_kaif");
            time_result = Integer.toString(hours) + ":" + time3[1] + ":" + time3[2] + " AM";
        }


        return date_result+" "+time_result;
    }

    public String arrange_server_time(String postTime)
    {

        String date2[]=postTime.split(" ");
        String date=date2[0];//only date
        String time=date2[1];//only time
        String date_result="";
        String time_result="";
        int hours=0;
        String date3[]=date.split("-");
        String time3[]=time.split(":");

        date_result=date3[2]+"-"+date3[1]+"-"+date3[0];


        if(parseInt(time3[0])>12)
        {
            hours= parseInt(time3[0])-12;

        }
        else
            hours= parseInt(time3[0]);

        if(parseInt(time3[0])>12 && parseInt(time3[0])==12)
            time_result=Integer.toString(hours)+":"+time3[1]+":"+time3[2]+" PM";
        else
            time_result=Integer.toString(hours)+":"+time3[1]+":"+time3[2]+" AM";


        return date_result+" "+time_result;
    }



}