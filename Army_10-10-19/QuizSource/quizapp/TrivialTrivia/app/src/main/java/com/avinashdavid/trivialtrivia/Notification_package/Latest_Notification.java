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

public class Latest_Notification extends AppCompatActivity {
    AppController appController;
    WebView wb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest__notification);

        final ProgressDialog pdLoading = new ProgressDialog(Latest_Notification.this);

       pdLoading.setMessage("Loading...");
        pdLoading.show();

        getSupportActionBar().setTitle("Latest Notification");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        wb = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = wb.getSettings();
        webSettings.setJavaScriptEnabled(true);

        appController = (AppController) getApplicationContext();


        Request request = new Request.Builder()
                .url(URL.latest_notification)
                .build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Latest_Notification.this.runOnUiThread(new Runnable() {
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

                    final String link = ja.getString("message");
                    Log.e("link", ": " + link);


                    if(Latest_Notification.this!=null) {
                        Latest_Notification.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                wb.loadUrl(link);
                                pdLoading.dismiss();
                            }
                        });
                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        });
    }
}


