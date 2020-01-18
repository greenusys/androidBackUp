package com.greenusys.personal.registrationapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.greenusys.personal.registrationapp.Adapter.Subject_Adapter;
import com.greenusys.personal.registrationapp.Adapter.Video_Adapter;
import com.greenusys.personal.registrationapp.Utility.AppController;
import com.greenusys.personal.registrationapp.pojos.Subject_Modal;
import com.greenusys.personal.registrationapp.pojos.Video_Modal;

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

public class Video_Subject_Wise extends AppCompatActivity {

    private AppController appController;
    ArrayList<Video_Modal> video_list= new ArrayList<>();
    Video_Modal video_modal;
    Video_Adapter video_adapter;
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video__subject__wise);


        API();
    }

    public void API() {

        final ProgressDialog pdLoading = new ProgressDialog(Video_Subject_Wise.this);
        pdLoading.setMessage("Loading...");
        pdLoading.show();



        appController = (AppController) getApplicationContext();
         mRecyclerView=findViewById(R.id.subject_rv);
        RequestBody body= new FormBody.Builder().add("subject_Id", "2").add("class_Id", "4").build();


        Request request = new Request.Builder().url("http://greenusys.website/mci/api/videoSubjectWise.php").post(body).build();
        appController.getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Video_Subject_Wise.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        pdLoading.dismiss();

                        setContentView(R.layout.internet_layout_faied);





                        Toast.makeText(getBaseContext(), "Something went wrong! ", Toast.LENGTH_SHORT).show();

                    }
                });
            }


            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                Log.e("hhh", "onResponse: " + myResponse);
                try {
                    final JSONObject ja = new JSONObject(myResponse);
                    Log.e("aaaa", "onResponse: " + ja);
                    if (ja.has("code")) {
                        Video_Subject_Wise.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("code");
                                    if (ja.getString("code").equalsIgnoreCase("1")) {
                                        // startActivity(new Intent(Soldier_Sample_Paper.this, soldier_category.class));

                                        JSONArray aaa = ja.getJSONArray("data");
                                        String other_id = "";
                                        String other_subject = "";

                                        Log.e("dsf", "sd" + aaa);
                                        for (int i = 0; i < aaa.length(); i++) {
                                            JSONObject item = aaa.getJSONObject(i);






                                            Log.d("class_id", item.getString("class_id"));
                                            Log.d("subject_id", item.getString("subject_id"));
                                            Log.d("video", item.getString("video"));
                                            Log.d("description", item.getString("description"));

                                               video_modal = new Video_Modal(item.getString("video"), item.getString("description"));
                                                video_list.add(video_modal);



                                        }



                                        video_adapter = new Video_Adapter(video_list);



                                        mRecyclerView.setNestedScrollingEnabled(false);
                                        mRecyclerView.setHasFixedSize(true);
                                        mRecyclerView.setFocusable(false);

                                        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

                                        mRecyclerView.setAdapter(video_adapter);




                                    } else {
                                        //Toast.makeText(appController, "Please Enter Correct Email id and Password", Toast.LENGTH_SHORT).show();
                                    }
                                    pdLoading.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        return;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}