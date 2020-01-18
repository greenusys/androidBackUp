package com.greenusys.personal.registrationapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.greenusys.personal.registrationapp.Adapter.Subject_Adapter;
import com.greenusys.personal.registrationapp.Utility.AppController;
import com.greenusys.personal.registrationapp.pojos.Subject_Modal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;


public class Subjects extends AppCompatActivity {

    private AppController appController;
    Subject_Modal subject_modal;

    ArrayList<Subject_Modal>subjects_list= new ArrayList<>();
    Subject_Adapter subject_adapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isNetworkAvailable()) {
            setContentView(R.layout.activity_subjects);
            API();
        }
        else {
            setContentView(R.layout.internet_layout_faied);
            TextView retry=findViewById(R.id.retry);

           retry.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if(isNetworkAvailable()) {
                       setContentView(R.layout.activity_subjects);
                       API();
                   }
               }
           });


        }





    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void API()
    {

        final ProgressDialog pdLoading = new ProgressDialog(Subjects.this);
        pdLoading.setMessage("Loading...");
        pdLoading.show();




        appController = (AppController) getApplicationContext();
        mRecyclerView=findViewById(R.id.subject_rv);


        Request request = new Request.Builder().url("http://greenusys.website/mci/api/fetchAllSubjects.php").build();
        appController.getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Subjects.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdLoading.dismiss();
                        setContentView(R.layout.internet_layout_faied);

                        TextView retry=findViewById(R.id.retry);

                        retry.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(isNetworkAvailable()) {
                                    setContentView(R.layout.activity_subjects);
                                    API();
                                }
                            }
                        });


                        //Toast.makeText(appController, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                    }
                });}



            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                Log.e("hhh", "onResponse: " + myResponse);
                try {
                    final JSONObject ja = new JSONObject(myResponse);
                    Log.e("aaaa", "onResponse: " + ja);
                    if (ja.has("code")) {
                        Subjects.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("code");
                                    if (ja.getString("code").equalsIgnoreCase("1")) {
                                        // startActivity(new Intent(Soldier_Sample_Paper.this, soldier_category.class));

                                        JSONArray aaa = ja.getJSONArray("data");
                                        String other_id="";
                                        String other_subject="";

                                        Log.e("dsf","sd"+aaa);
                                        for (int i = 0; i < aaa.length(); i++)
                                        {
                                            JSONObject item = aaa.getJSONObject(i);


                                            Log.d("id", item.getString("id"));
                                            Log.d("subject_name", item.getString("subject_name"));

                                            if(!item.getString("subject_name").equalsIgnoreCase("other")) {
                                                subject_modal = new Subject_Modal(item.getString("id"), item.getString("subject_name"));
                                                subjects_list.add(subject_modal);
                                            }
                                            else
                                            {
                                                other_id=(item.getString("id"));
                                                other_subject=(item.getString("subject_name"));

                                            }



                                        }



                                        subject_modal = new Subject_Modal(other_id,other_subject);
                                        subjects_list.add(subject_modal);

                                        subject_adapter = new Subject_Adapter(subjects_list);



                                        mRecyclerView.setNestedScrollingEnabled(false);
                                        mRecyclerView.setHasFixedSize(true);
                                        mRecyclerView.setFocusable(false);

                                        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

                                        mRecyclerView.setAdapter(subject_adapter);





                                    }
                                    else
                                    {
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
