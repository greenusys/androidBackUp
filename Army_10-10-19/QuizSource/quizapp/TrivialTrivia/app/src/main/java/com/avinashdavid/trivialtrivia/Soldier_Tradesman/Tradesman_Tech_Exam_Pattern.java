package com.avinashdavid.trivialtrivia.Soldier_Tradesman;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.avinashdavid.trivialtrivia.Adapter.Solider_GD_adapter;
import com.avinashdavid.trivialtrivia.AppController;
import com.avinashdavid.trivialtrivia.Model.soldire_gd_model;
import com.avinashdavid.trivialtrivia.R;
import com.avinashdavid.trivialtrivia.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/*
import com.greenusys.army_project.Adapter.Solider_GD_adapter;
import com.greenusys.army_project.AppController;
import com.greenusys.army_project.Model.soldire_gd_model;
import com.greenusys.army_project.R;*/

public class Tradesman_Tech_Exam_Pattern extends AppCompatActivity {
    LinearLayout past_paper1,past_paper2_buy,past_paper3_buy;
    EditText seller_email,seller_password;
    Button bt_seller_login;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    AppController appController;
    RecyclerView rv_gd;
    Solider_GD_adapter solider_gd_adapter;
    soldire_gd_model soldire_gd_model1;
    List<soldire_gd_model> soldire_gd_models = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tradesman_tech_exam_pattern);

        getSupportActionBar().setTitle("Soldier Tradesman Exam Pattern");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ProgressDialog pdLoading = new ProgressDialog(Tradesman_Tech_Exam_Pattern.this);

        pdLoading.setMessage("Loading...");
        pdLoading.show();

        rv_gd = (RecyclerView) findViewById(R.id.rv_soldier_tech_exam_pattarn);
        appController = (AppController) getApplicationContext();


        Request request = new Request.Builder()
                .url(URL.tradesman_exam_pattern)
                .build();
        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Tradesman_Tech_Exam_Pattern.this.runOnUiThread(new Runnable() {
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
                    if (ja.has("status")) {
                        Tradesman_Tech_Exam_Pattern.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("status");
                                    if (ja.getString("status").equalsIgnoreCase("0")) {
                                        // startActivity(new Intent(Soldier_Sample_Paper.this, soldier_category.class));

                                        JSONArray aaa = ja.getJSONArray("message");

                                        Log.e("dsf","sd"+aaa);
                                        for (int i = 0; i < aaa.length(); i++)
                                        {
                                            JSONObject item = aaa.getJSONObject(i);

                                            soldire_gd_model soldire_gd_model1 = new soldire_gd_model(item.getString("exam_name"),item.getString("date"),item.getString("exam_link"));
                                            soldire_gd_models.add(soldire_gd_model1);



                                        }

                                        solider_gd_adapter = new Solider_GD_adapter(soldire_gd_models);
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                        rv_gd.setLayoutManager(mLayoutManager);
                                        rv_gd.setItemAnimator(new DefaultItemAnimator());
                                        rv_gd.setAdapter(solider_gd_adapter);
                                        solider_gd_adapter.notifyDataSetChanged();



                                        // Toast.makeText(appController, "Welcome "+email, Toast.LENGTH_SHORT).show();

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
