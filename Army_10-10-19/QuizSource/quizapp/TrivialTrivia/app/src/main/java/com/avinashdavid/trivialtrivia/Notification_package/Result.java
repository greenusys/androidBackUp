package com.avinashdavid.trivialtrivia.Notification_package;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;
/*

import com.greenusys.army_project.Adapter.Solider_GD_adapter;
import com.greenusys.army_project.AppController;
import com.greenusys.army_project.Model.soldire_gd_model;
import com.greenusys.army_project.R;
*/

import com.avinashdavid.trivialtrivia.AppController;
import com.avinashdavid.trivialtrivia.R;
import com.avinashdavid.trivialtrivia.URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class Result extends AppCompatActivity {
    AppController appController;
    WebView result_wb;
    /*Solider_GD_adapter solider_gd_adapter;
    soldire_gd_model soldire_gd_model1;
    List<soldire_gd_model> soldire_gd_models = new ArrayList<>();*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getSupportActionBar().setTitle("Result");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ProgressDialog pdLoading = new ProgressDialog(Result.this);

        pdLoading.setMessage("Loading...");
        pdLoading.show();

        result_wb = (WebView) findViewById(R.id.result_webView);
        WebSettings webSettings = result_wb.getSettings();
        webSettings.setJavaScriptEnabled(true);

        appController = (AppController) getApplicationContext();


        Request request = new Request.Builder()
                .url(URL.result)
                .build();
        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Result.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();
                    }
                });}
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                Log.e("hhh","onResponse: " + myResponse);
                try {
                    final JSONObject ja = new JSONObject(myResponse);
                    Log.e("aaaa","onResponse" + ja);
                    final String link = ja.getString("message");
                    Log.e("link",": " + link);

                    if(Result.this != null) {
                        Result.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                result_wb.loadUrl(link);
                                pdLoading.dismiss();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            /*    String myResponse = response.body().string();
                Log.e("hhh", "onResponse: " + myResponse);
                try {
                    final JSONObject ja = new JSONObject(myResponse);
                    Log.e("aaaa", "onResponse: " + ja);
                    if (ja.has("status")) {
                        Result.this.runOnUiThread(new Runnable() {
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

                                           *//* soldire_gd_model soldire_gd_model1 = new soldire_gd_model(item.getString("result_name"),item.getString("date"),item.getString("link"));
                                            soldire_gd_models.add(soldire_gd_model1);*//*
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
                }*/
            }
        });
    }
}

