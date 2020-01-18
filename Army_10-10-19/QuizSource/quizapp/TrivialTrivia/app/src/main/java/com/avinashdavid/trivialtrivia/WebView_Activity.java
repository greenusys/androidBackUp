package com.avinashdavid.trivialtrivia;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebView_Activity extends AppCompatActivity {

    private WebView wb;
    String link="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_);

        final ProgressDialog pdLoading = new ProgressDialog(WebView_Activity.this);

        pdLoading.setMessage("Loading...");
         pdLoading.show();

        wb = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = wb.getSettings();
        webSettings.setJavaScriptEnabled(true);

        if(getIntent().getStringExtra("link")!=null)
            link=getIntent().getStringExtra("link");

        wb.loadUrl(link);
        pdLoading.dismiss();
    }
}
