package com.avinashdavid.trivialtrivia.Education_Havildar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.avinashdavid.trivialtrivia.AppController;
import com.avinashdavid.trivialtrivia.R;
import com.avinashdavid.trivialtrivia.URL;
import com.avinashdavid.trivialtrivia.WebView_Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;


public class Education_Havildar extends AppCompatActivity {
    LinearLayout sample_paper, past_paper, eligibility, syllabus, exam_pattern;

    int flag = 0;
    private AppController appController;
    String syllabus_link_data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.education_havildar);


        sample_paper = (LinearLayout) findViewById(R.id.sample_paper);
        past_paper = (LinearLayout) findViewById(R.id.past_paper);
        eligibility = (LinearLayout) findViewById(R.id.eligibility);
        syllabus = (LinearLayout) findViewById(R.id.syllabus);
        exam_pattern = (LinearLayout) findViewById(R.id.exam_pattern);

        //  syllabus_link=(TextView)findViewById(R.id.syllabus_link);
        // exam_link=(TextView)findViewById(R.id.exam_pattern_link2);


        getSupportActionBar().setTitle("Education Havildar");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sample_paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Education_Havildar.this, Havildar_Tech_Sample_Paper.class);
                startActivity(i);
            }
        });


        past_paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Education_Havildar.this, Havildar_Tech_Past_Paper.class);
                startActivity(i);
            }
        });

        eligibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Education_Havildar.this, Havildar_Eligibility_Data.class);
                startActivity(i);
            }
        });

        syllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_link("syllabus");


            }
        });

        exam_pattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                open_link("exam");


            }
        });


        appController = (AppController) getApplicationContext();

    }

    private void open_link(final String value) {

        Request request = null;


        if (value.equals("syllabus"))
            request = new Request.Builder().url(URL.havildar_syllabus_link).get().build();

        if (value.equals("exam"))
            request = new Request.Builder().url(URL.havildar_exam_pattern).get().build();


        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Education_Havildar.this.runOnUiThread(new Runnable() {
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
                    if (ja.has("status")) {
                        Education_Havildar.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("status");
                                    if (ja.getString("status").equalsIgnoreCase("0")) {
                                        // startActivity(new Intent(Soldier_Sample_Paper.this, soldier_category.class));

                                        JSONArray aaa = ja.getJSONArray("message");
                                        for (int i = 0; i < a.length(); i++) {

                                            if (value.equals("syllabus")) {
                                                JSONObject item = aaa.getJSONObject(i);
                                                Log.d("syllabus_name", item.getString("syllabus_name"));
                                                Log.d("syllabus_link", item.getString("syllabus_link"));
                                                syllabus_link_data = item.getString("syllabus_link");
                                                System.out.println("Data" + syllabus_link_data);
//                                            syllabus_link.setText(syllabus_link_data);

                                                //String url = "https://logicalpaper.co/indian-army-written-exam-pattern/";
                                               /* Intent k = new Intent(Intent.ACTION_VIEW);
                                                k.setData(Uri.parse(syllabus_link_data));
                                                startActivity(k);*/
                                                startActivity(new Intent(getBaseContext(), WebView_Activity.class)
                                                        .putExtra("link",syllabus_link_data));
                                            }
                                            if (value.equals("exam")) {
                                                JSONObject item = aaa.getJSONObject(i);
                                                Log.d("syllabus_name", item.getString("exam_name"));
                                                Log.d("syllabus_link", item.getString("exam_link"));
                                                syllabus_link_data = item.getString("exam_link");
                                                System.out.println("Data" + syllabus_link_data);
//                                            syllabus_link.setText(syllabus_link_data);

                                                //String url = "https://logicalpaper.co/indian-army-written-exam-pattern/";
                                               /* Intent k = new Intent(Intent.ACTION_VIEW);
                                                k.setData(Uri.parse(syllabus_link_data));
                                                startActivity(k);*/
                                                startActivity(new Intent(getBaseContext(), WebView_Activity.class)
                                                        .putExtra("link",syllabus_link_data));
                                            }


                                        }


                                        //finish();
                                        Log.e("aaaa2", "onResponse: " + a);
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
    }


}
