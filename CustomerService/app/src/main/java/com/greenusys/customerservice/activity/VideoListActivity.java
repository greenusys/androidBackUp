package com.greenusys.customerservice.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.greenusys.customerservice.R;
import com.greenusys.customerservice.adapter.VideoListAdapter;
import com.greenusys.customerservice.model.Video;
import com.greenusys.customerservice.utility.AppController;
import com.greenusys.customerservice.utility.UrlHelper;


public class VideoListActivity extends AppCompatActivity implements VideoListAdapter.NewItemOnClickHandler {
    private AppController appController;
    UrlHelper urlhelper;
    RecyclerView recyclerView;
    VideoListAdapter adapter;
    LinearLayoutManager layoutManager;
    private static final String VIDEO_URI = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";
    Video video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        appController = (AppController) getApplicationContext();
        recyclerView = (RecyclerView) findViewById(R.id.video_list_rv);
      // new VideoListActivity.SignIn1().execute();

        adapter = new VideoListAdapter(VideoListActivity.this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(VideoListActivity.this));

        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);


    }


    @Override
    public void onClick(Video video) {
     /*   String urlss = urlhelper.uploads_video;
         urlss = urlss+video.getVideo();
        Log.e("zzzz", "onClick: "+urlss );
        startActivity(PlayerActivity.getVideoPlayerIntent(VideoListActivity.this,urlss,video.getDescription()));*/

    }


   /* private class SignIn1 extends AsyncTask<String, Void, String> {

        private String key_a = "data-source";
        private String value_a = "android";
        private String key_b = "className";
        private String value_b = urlhelper.classId;
        private String response;
        private RequestBody body;
        private String url = urlhelper.video;

        @Override
        protected String doInBackground(String... strings) {

            Log.e("back", "doInBackground: " + value_a + url);
            body = RequestBuilder.twoParameter(
                    key_a, value_a, key_b, value_b);

            try {
                response = appController.POST(url, body);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("RESSS", "onPostExecute: " + s);
            if (s != null) {


                ///  JSONArray jsonArray = new JSONArray(response);

                final List<Video> videoList = JsonParser.getVideoList(response);
                *//*com.greenusys.customerservice.activity.VideoListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {*//*
                        adapter.updateData(videoList);
                        Log.e("RESSS", "onPostExecute: " + response);
               *//*     }
                });*//*

*//*
                    for(int i=0; i<jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        video = new Video(jsonObject.getString("classes"),jsonObject.getString("video"),jsonObject.getString("description"));

                    }*//*


            }



        }
    }*/
}
