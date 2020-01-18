package com.avinashdavid.trivialtrivia.Notification_package;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;
/*
import com.greenusys.army_project.Adapter.Notication_rally_adapter;
import com.greenusys.army_project.AppController;
import com.greenusys.army_project.Model.Rally_model;
import com.greenusys.army_project.Model.soldire_gd_model;
import com.greenusys.army_project.R;*/


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

public class All_Rally extends AppCompatActivity {
    AppController appController;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__rally);

        final ProgressDialog pdLoading = new ProgressDialog(All_Rally.this);

        pdLoading.setMessage("Loading...");
        pdLoading.show();

        webView = (WebView) findViewById(R.id.all_rally_webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        appController = (AppController) getApplicationContext();


        getSupportActionBar().setTitle("All Rally");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        appController = (AppController) getApplicationContext();
    //}



        Request request = new Request.Builder()
                .url(URL.check_rally)
                .build();
        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                All_Rally.this.runOnUiThread(new Runnable() {
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
                        All_Rally.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    final String a = ja.getString("message");
                                    Log.e("sdf", "sdf" + a);

                                    if (All_Rally.this != null)
                                    {
                                        All_Rally.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                webView.loadUrl(a);
                                                pdLoading.dismiss();


                                            }
                                        });
                                    }


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
    //}
}
}


