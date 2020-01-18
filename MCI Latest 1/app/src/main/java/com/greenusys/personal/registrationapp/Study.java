package com.greenusys.personal.registrationapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.appunite.appunitevideoplayer.PlayerActivity;
import com.greenusys.personal.registrationapp.Utility.AppController;
import com.greenusys.personal.registrationapp.Utility.RequestBuilder;
import com.greenusys.personal.registrationapp.Utility.UrlHelper;
import com.greenusys.personal.registrationapp.pojos.Studyy;
import com.greenusys.personal.registrationapp.pojos.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.RequestBody;

public class Study extends AppCompatActivity implements StudyAdapter.NewItemOnClickHandler {
    private AppController appController;
    UrlHelper urlhelper;
    RecyclerView recyclerViews;
    StudyAdapter adapter;
    LinearLayoutManager layoutManager;
    private static final String VIDEO_URI = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";
    private String act;
    Studyy studyy;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        appController = (AppController) getApplicationContext();
        recyclerViews = (RecyclerView) findViewById(R.id.study_list_rv);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        act = (String) extras.get("act");
        if (act.equalsIgnoreCase("1"))
        {
            url = urlhelper.test_ofline;
            new Study.SignIn1().execute();
        }
        if (act.equalsIgnoreCase("2"))
        {
            url = urlhelper.study_material;
            new Study.SignIn1().execute();
        }




        adapter = new StudyAdapter(Study.this, this);
        recyclerViews.setLayoutManager(new LinearLayoutManager(Study.this));

        recyclerViews.setHasFixedSize(true);

        recyclerViews.setAdapter(adapter);


    }


    @Override
    public void onClick(Studyy study) {
        if (act.equalsIgnoreCase("1"))
        {String urlss = urlhelper.testlink;
            urlss = urlss+study.getUpload_file();
            Log.e("zzzz", "onClick: "+urlss );
            Intent ii = new Intent(Study.this, Pdf.class);
            ii.putExtra("link",urlss);
            startActivity(ii);
        }
        if (act.equalsIgnoreCase("2"))
        {
            String urlss = urlhelper.uploads_study;
            urlss = urlss+study.getUpload_file();
            Log.e("zzzz", "onClick: "+urlss );
            Intent ii = new Intent(Study.this, Pdf.class);
            ii.putExtra("link",urlss);
            startActivity(ii);
        }


    }


    private class SignIn1 extends AsyncTask<String, Void, String> {

        private String key_a = "data-source";
        private String value_a = "android";
        private String key_b = "className";
        private String value_b = urlhelper.classId;
        private String response;
        private RequestBody body;



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

                final List<Studyy> studyList = JsonParser.getStudyList(response);
                /*VideoListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {*/
                adapter.updateData(studyList);
                Log.e("RESSS", "onPostExecute: " + response);
               /*     }
                });*/

/*
                    for(int i=0; i<jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        video = new Video(jsonObject.getString("classes"),jsonObject.getString("video"),jsonObject.getString("description"));

                    }*/


            }

            //


        }
    }

}
