package com.greenusys.personal.registrationapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.greenusys.personal.registrationapp.Utility.AppController;
import com.greenusys.personal.registrationapp.Utility.RequestBuilder;
import com.greenusys.personal.registrationapp.Utility.UrlHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;

public class ImagesGridActivity extends AppCompatActivity implements ImagesGridAdapter.ImagesGridAdapterOnClickHandler {
    private AppController appController;
    UrlHelper urlhelper;
    RecyclerView mRecyclerView;
    ImagesGridAdapter adapter;
     List<String> idArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_grid);

        getSupportActionBar().setTitle("Gallery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appController = (AppController) getApplicationContext();
        idArray = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.images_grid_rv);
        new ImagesGridActivity.SignIn1().execute();


    }


    @Override
    public void onClick(String imageSource) {
        Log.e("ssssss", "onClick: "+imageSource );

        Intent fullImageActivityIntent = new Intent(ImagesGridActivity.this, FullImageActivity.class);


       fullImageActivityIntent.putExtra("source",imageSource);

        startActivity(fullImageActivityIntent);

    }

    private class SignIn1 extends AsyncTask<String, Void, String> {

        private String key_a = "data-source";
        private String value_a = "android";
        private String response;
        private RequestBody body;
        private String url = urlhelper.gallery;
        private String urls = urlhelper.images;

        @Override
        protected String doInBackground(String... strings) {


            Log.e("back", "doInBackground: " + value_a);
            body = RequestBuilder.Parameter(
                    key_a, value_a);
            Log.e("back11111", "doInBackground: " + body);
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
                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.e("qqqq", "onPostExecute: " + urls + jsonObject.getString("upload_file"));
                        idArray.add(urls + jsonObject.getString("upload_file"));
                        Log.e("xxxxx", "onPostExecute: " + idArray.size()+idArray.get(i));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            mRecyclerView.setLayoutManager(new GridLayoutManager(ImagesGridActivity.this, 2));

            adapter = new ImagesGridAdapter(ImagesGridActivity.this, idArray, ImagesGridActivity.this);

            mRecyclerView.setAdapter(adapter);
        }
    }

    /*private ArrayList<String> getImagesList() {
        ArrayList<String> imageList = new ArrayList<>();
        for (int i = 0; i < 1; i++)

        {
            Toast.makeText(ImagesGridActivity.this,"xx"+idArray.size(), Toast.LENGTH_SHORT).show();
            Log.e("mmmmmmm", "onPostExecute: " + idArray.size());
           // imageList.add("http://greenusys.website/mci/uploads/gallery/20180306160344.jpg");
        }


        return imageList;
    }*/
}
