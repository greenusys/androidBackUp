package com.icosom.social.activity;

import androidx.core.widget.ContentLoadingProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.icosom.social.Adapter.Show_Like_Dislike_Adapter;
import com.icosom.social.R;
import com.icosom.social.model.Like_Dislike_User;
import com.icosom.social.utility.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Show_Liked_Disliked_User extends AppCompatActivity {


    String type="";
    String postid="";
    RecyclerView rv;
    Show_Like_Dislike_Adapter adapter;
    ArrayList<Like_Dislike_User> list=new ArrayList<>();
    private AppController appController;
    ContentLoadingProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__liked__disliked__user);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getIntent().getStringExtra("type")!=null) {
            type = getIntent().getStringExtra("type");
            postid = getIntent().getStringExtra("postid");






            System.out.println("type_show_liked" + type);
            System.out.println("postid_show_liked" + postid);
        }

        initViews();
        fetch_List(postid);



    }

    private void initViews() {
        appController = (AppController) getApplicationContext();
        rv=findViewById(R.id.rv);
        loadingProgressBar=findViewById(R.id.progress_bar);
        adapter=new Show_Like_Dislike_Adapter(getApplicationContext(),list);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        rv.setAdapter(adapter);
    }

    private void fetch_List(String postid)
    {
        loadingProgressBar.show();



        RequestBody body = new FormBody.Builder().
                add("postid", postid).
                build();

        Request request = new Request.Builder().
                url("https://icosom.com/social/main/new_api/like_dislike_api.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                if(Show_Liked_Disliked_User.this!=null) {
                    Show_Liked_Disliked_User.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadingProgressBar.hide();
                            //Toast.makeText(getContext(), "Try Again Later", Toast.LENGTH_SHORT).show();
                        }
                    });
                }



            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();
                Show_Liked_Disliked_User.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            String path = "https://icosom.com/social/postFiles/images/";
                            final JSONObject mainjson = new JSONObject(myResponse);
                            Log.e("mainjson_frnd_list",""+mainjson);
                            Log.e("status",""+mainjson.getString("status"));
                            JSONArray jsonArray =null;

                            if (mainjson.getString("status").equals("1")) {

                                if(list!=null)
                                    list.clear();

                                if(type.equalsIgnoreCase("view_like"))
                                    getSupportActionBar().setTitle("People who liked");

                                    if(type.equalsIgnoreCase("view_dislike"))
                                        getSupportActionBar().setTitle("People who Disliked");



                            if(type.equals("view_like"))
                                jsonArray = mainjson.getJSONArray("likes");

                            if(type.equals("view_dislike"))
                                jsonArray = mainjson.getJSONArray("dislikes");


                                for (int i = 0; i < jsonArray.length(); i++)//data loop
                                {

                                    JSONObject jsonObject=jsonArray.getJSONObject(i);

                                    String userid=jsonObject.getString("userId");
                                    String name=jsonObject.getString("firstName")+" "+jsonObject.getString("lastName");
                                    String image=jsonObject.getString("profilePicture");

                                    Like_Dislike_User user=new Like_Dislike_User();
                                    user.setUserid(userid);
                                    user.setName(name);
                                    user.setImage(path+image);


                                    list.add(user);


                                }//data loop closed

                                if (getApplicationContext() != null) {
                                    Show_Liked_Disliked_User.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            loadingProgressBar.hide();
                                           adapter.notifyDataSetChanged();
                                        }
                                    });
                                }





                            } else {

                                if (Show_Liked_Disliked_User.this != null) {
                                    Show_Liked_Disliked_User.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            loadingProgressBar.hide();

                                        }
                                    });
                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }



}
