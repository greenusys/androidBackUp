package com.greenusys.personal.registrationapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.greenusys.personal.registrationapp.Adapter.Subject_Detail_Data_Adapter;
import com.greenusys.personal.registrationapp.Utility.AppController;
import com.greenusys.personal.registrationapp.pojos.Subject_Detail_Data_Modal;
import com.greenusys.personal.registrationapp.pojos.Subject_Detail_Modal;

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

public class Subject_Details_Data extends AppCompatActivity {

    private AppController appController;
    Subject_Detail_Data_Modal subject_modal;

    ArrayList<Subject_Detail_Modal> subjects_list= new ArrayList<>();
    Subject_Detail_Data_Adapter subject_adapter;
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject__details__data);


       // Toast.makeText(this, "sub_id"+getIntent().getStringExtra("sub_id"), Toast.LENGTH_SHORT).show();
String sub_id=getIntent().getStringExtra("sub_id");
        appController = (AppController) getApplicationContext();
        mRecyclerView=findViewById(R.id.subject_rv);

        RequestBody body= new FormBody.Builder().add("sub_id", sub_id).build();
        Request request = new Request.Builder().url("http://greenusys.website/mci/api/subjectDetails.php").post(body).build();
        appController.getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Subject_Details_Data.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();
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
                        Subject_Details_Data.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("code");
                                    if (ja.getString("code").equalsIgnoreCase("1")) {
                                        // startActivity(new Intent(Soldier_Sample_Paper.this, soldier_category.class));

                                        JSONArray aaa = ja.getJSONArray("study_material");

                                        Log.e("dsf","sd"+aaa);
                                        for (int i = 0; i < aaa.length(); i++)
                                        {
                                            JSONObject item = aaa.getJSONObject(i);


                                            Log.d("upload_file", item.getString("upload_file"));
                                            Log.d("description", item.getString("description"));

                                            subject_modal = new Subject_Detail_Data_Modal(item.getString("upload_file"),item.getString("description"));
                                            //subjects_list.add(subject_modal);



                                        }


                                      //  subject_adapter = new Subject_Detail_Data_Adapter(subjects_list);

                                      /*  solider_gd_adapter = new Solider_GD_adapter(soldire_gd_models);
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                        rv_gd.setLayoutManager(mLayoutManager);
                                        rv_gd.setItemAnimator(new DefaultItemAnimator());
                                        rv_gd.setAdapter(solider_gd_adapter);
                                        solider_gd_adapter.notifyDataSetChanged();*/

                                        mRecyclerView.setNestedScrollingEnabled(false);
                                        mRecyclerView.setHasFixedSize(true);
                                        mRecyclerView.setFocusable(false);

                                        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

                                        mRecyclerView.setAdapter(subject_adapter);




                                        // Toast.makeText(appController, "Welcome "+email, Toast.LENGTH_SHORT).show();

                                    }
                                    else
                                    {
                                        //Toast.makeText(appController, "Please Enter Correct Email id and Password", Toast.LENGTH_SHORT).show();
                                    }
                                    //pdLoading.dismiss();
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
