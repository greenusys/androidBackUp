package com.avinashdavid.trivialtrivia;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class Check_Eligibility extends AppCompatActivity {
    WebView webView;
    AppController appController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check__eligibility);

        final ProgressDialog pdLoading = new ProgressDialog(Check_Eligibility.this);
        pdLoading.setMessage("Loading...");
        pdLoading.show();

        webView = findViewById(R.id.check_eligibility_webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        appController = (AppController) getApplicationContext();

        getSupportActionBar().setTitle("Check Your Eligibility");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Request request = new Request.Builder().url(URL.check_your_eligiblity).build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Error..."+ e.getMessage());

                Check_Eligibility.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(),"Sorry! Not Connected to the Internet", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                    String myResponse = response.body().string();
                Log.e("hhh","My Response:" + myResponse);

                Check_Eligibility.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl(URL.check_your_eligiblity);
                        pdLoading.dismiss();
                    }
                });
            }
        });
    }
}
