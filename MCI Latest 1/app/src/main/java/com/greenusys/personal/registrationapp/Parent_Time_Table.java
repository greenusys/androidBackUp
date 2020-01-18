package com.greenusys.personal.registrationapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.greenusys.personal.registrationapp.Adapter.Subject_Adapter;
import com.greenusys.personal.registrationapp.Utility.AppController;
import com.greenusys.personal.registrationapp.pojos.Subject_Modal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Parent_Time_Table extends AppCompatActivity {

    private AppController appController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent__time__table);





        API();

    }



    public void API() {

        final ProgressDialog pdLoading = new ProgressDialog(Parent_Time_Table.this);
        pdLoading.setMessage("Loading...");
        pdLoading.show();

        ImageView image = findViewById(R.id.imageView2);
        TextView offline = findViewById(R.id.textView2);
        TextView retry = findViewById(R.id.retry);

        appController = (AppController) getApplicationContext();
        //  mRecyclerView=findViewById(R.id.subject_rv);
        RequestBody body= new FormBody.Builder().add("class", "1").build();


        Request request = new Request.Builder().url("http://greenusys.website/mci/api/timeTable.php").post(body).build();
        appController.getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Parent_Time_Table.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        pdLoading.dismiss();

                        image.setVisibility(View.VISIBLE);
                        offline.setVisibility(View.VISIBLE);
                        retry.setVisibility(View.VISIBLE);


                        retry.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                // final ProgressDialog pdLoading = new ProgressDialog(Subjects.this);
                                //pdLoading.setMessage("Loading...");
                                pdLoading.show();
                                API();
                                pdLoading.dismiss();

                            }
                        });


                        //Toast.makeText(getBaseContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();

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
                        Parent_Time_Table.this.runOnUiThread(new Runnable() {
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



                                            Log.d("timetable_id", item.getString("timetable_id"));
                                            Log.d("class_id", item.getString("class_id"));
                                            Log.d("title", item.getString("title"));

                                            /*if(!item.getString("subject_name").equalsIgnoreCase("other")) {
                                                subject_modal = new Subject_Modal(item.getString("id"), item.getString("subject_name"));
                                                subjects_list.add(subject_modal);
                                            }
                                            else
                                            {
                                                other_id=(item.getString("id"));
                                                other_subject=(item.getString("subject_name"));

                                            }*/


                                        }

                                       /* image.setVisibility(View.INVISIBLE);
                                        offline.setVisibility(View.INVISIBLE);
                                        retry.setVisibility(View.INVISIBLE);
*/

                                        /*subject_modal = new Subject_Modal(other_id,other_subject);
                                        subjects_list.add(subject_modal);

                                        subject_adapter = new Subject_Adapter(subjects_list);



                                        mRecyclerView.setNestedScrollingEnabled(false);
                                        mRecyclerView.setHasFixedSize(true);
                                        mRecyclerView.setFocusable(false);

                                        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

                                        mRecyclerView.setAdapter(subject_adapter);
*/


                                        // Toast.makeText(appController, "Welcome "+email, Toast.LENGTH_SHORT).show();

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
