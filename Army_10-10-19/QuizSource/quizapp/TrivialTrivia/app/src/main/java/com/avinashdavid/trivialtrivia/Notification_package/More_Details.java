package com.avinashdavid.trivialtrivia.Notification_package;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.avinashdavid.trivialtrivia.AppController;
import com.avinashdavid.trivialtrivia.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class More_Details extends AppCompatActivity {
    LinearLayout sample_paper,past_paper,eligibility,syllabus,merit_result,syllabus_link,exam_link;

    private AppController appController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more__details);

        getSupportActionBar().setTitle("More");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sample_paper = (LinearLayout) findViewById(R.id.sample_paper);
        past_paper = (LinearLayout) findViewById(R.id.past_paper);
        eligibility = (LinearLayout) findViewById(R.id.eligibility);
        syllabus = (LinearLayout) findViewById(R.id.syllabus);
        merit_result = (LinearLayout) findViewById(R.id.merit_result);

        sample_paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all_rally();
                /*Intent i = new Intent(More_Details.this, All_Rally.class);
                startActivity(i);*/
            }
        });


        past_paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                latest_notifcation();

                /* Intent i = new Intent(More_Details.this, Latest_Notification.class);
                startActivity(i);*/
            }
        });
        eligibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all_admit();

                /* Intent i = new Intent(More_Details.this, Admit_Card.class);
                startActivity(i);*/
            }
        });

        syllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                all_merti();
                //startActivity(new Intent(More_Details.this, Result.class));
            }
        });

        merit_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://www.google.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });


        appController = (AppController) getApplicationContext();
    }

    public void all_rally() {

        Request request = new Request.Builder()
                .url("http://4army.in/indianarmy/androidapi/apifetch_checkrally.php")
                .build();
        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                More_Details.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();
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
                    if (ja.has("message")) {
                        More_Details.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("message");
                                    Log.e("sdf", "sdf" + a);
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(a));
                                    startActivity(i);


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

    public void latest_notifcation()
    {

        Request request = new Request.Builder()
                .url("http://4army.in/indianarmy/androidapi/apifetch_alllatestnotification.php")
                .build();
        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                More_Details.this.runOnUiThread(new Runnable() {
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
                    if (ja.has("message")) {
                        More_Details.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("message");
                                    Log.e("sdf","sdf"+a);
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(a));
                                    startActivity(i);


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

    public void all_admit() {

        Request request = new Request.Builder()
                .url("http://4army.in/indianarmy/androidapi/apifetch_allresult.php")
                .build();
        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                More_Details.this.runOnUiThread(new Runnable() {
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
                    if (ja.has("message")) {
                        More_Details.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("message");
                                    Log.e("sdf","sdf"+a);
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(a));
                                    startActivity(i);


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

    public void all_merti() {

        Request request = new Request.Builder()
                .url("http://4army.in/indianarmy/androidapi/apifetch_allresult.php")
                .build();
        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                More_Details.this.runOnUiThread(new Runnable() {
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
                    if (ja.has("message")) {
                        More_Details.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("message");
                                    Log.e("sdf","sdf"+a);
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(a));
                                    startActivity(i);


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

