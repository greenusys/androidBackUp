package com.greenusys.personal.registrationapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.greenusys.personal.registrationapp.Utility.AppController;

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

/**
 * Created by ali on 01-04-2019.
 */

public class Fetch_Rewards extends AppCompatActivity {


    private AppController appController;
    public String total_rewards="";

  public   Fetch_Rewards()
    {
        API();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);






    }



    public String API() {

//    //    final ProgressDialog pdLoading = new ProgressDialog(Fetch_Rewards.this);
       // pdLoading.setMessage("Loading...");
       // pdLoading.show();
       // final String[] total_rewards = {""};

       /* ImageView image = findViewById(R.id.imageView2);
        TextView offline = findViewById(R.id.textView2);
        TextView retry = findViewById(R.id.retry);*/

        AppController   appController = (AppController) getApplicationContext();
        //  mRecyclerView=findViewById(R.id.subject_rv);
        RequestBody body= new FormBody.Builder().add("user_id", "2").build();


        Request request = new Request.Builder().url("http://greenusys.website/mci/api/myRewards.php").post(body).build();
        appController.getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Fetch_Rewards.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                      //  pdLoading.dismiss();

                        /*image.setVisibility(View.VISIBLE);
                        offline.setVisibility(View.VISIBLE);
                        retry.setVisibility(View.VISIBLE);


                        retry.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                // final ProgressDialog pdLoading = new ProgressDialog(Subjects.this);
                                //pdLoading.setMessage("Loading...");
                              //  pdLoading.show();
                                //API();
                               // pdLoading.dismiss();
//
                            }
                        });
*/

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
                        Fetch_Rewards.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("code");
                                    if (ja.getString("code").equalsIgnoreCase("1")) {
                                        // startActivity(new Intent(Soldier_Sample_Paper.this, soldier_category.class));

                                        JSONObject aaa = ja.getJSONObject("data");
                                        String other_id = "";
                                        String other_subject = "";

                                        Log.e("dsf", "sd" + aaa);

                                        total_rewards =aaa.getString("rewards");




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
                                  //  pdLoading.dismiss();
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


        return total_rewards;
    }

}