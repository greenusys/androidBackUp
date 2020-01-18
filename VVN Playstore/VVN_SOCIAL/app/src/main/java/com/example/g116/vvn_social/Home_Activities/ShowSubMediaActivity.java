package com.example.g116.vvn_social.Home_Activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import com.example.g116.vvn_social.Adapter.ShowSubMediaPagerAdapter;
import com.example.g116.vvn_social.Network_Package.AppController;
import com.example.g116.vvn_social.R;
import com.example.g116.vvn_social.Session_Package.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ShowSubMediaActivity extends AppCompatActivity
{
    ViewPager vp_showSubMedia;
    ArrayList<String> images_list;
    Boolean isVideo;
    int pos;
   public static int backfrom=0;
    private AppController appController;
    SessionManager session;
    HashMap<String, String> user;
    private String post_id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sub_media);
        appController = (AppController) getApplicationContext();


        session = new SessionManager(getApplicationContext());
        user = session.getUserDetails();

        String user_id = user.get(SessionManager.KEY_ID);

        images_list = new ArrayList<String>(getIntent().getStringArrayListExtra("images_list"));

        isVideo = getIntent().getBooleanExtra("isVideos", false);
        pos = getIntent().getIntExtra("position", 0);

        if(getIntent().getStringExtra("post_id")!=null) {
            post_id = getIntent().getStringExtra("post_id");
            view_post(user_id, post_id);
        }


        vp_showSubMedia = findViewById(R.id.vp_showSubMedia);
        vp_showSubMedia.setAdapter(new ShowSubMediaPagerAdapter(getBaseContext(), images_list, isVideo));

        vp_showSubMedia.setCurrentItem(pos);






    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
         backfrom=1;
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

                ShowSubMediaActivity.this.runOnUiThread(new Runnable() {
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

}