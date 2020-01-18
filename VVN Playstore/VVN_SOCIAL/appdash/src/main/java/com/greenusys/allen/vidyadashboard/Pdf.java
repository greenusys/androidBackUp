package com.greenusys.allen.vidyadashboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Pdf extends AppCompatActivity {
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        url = (String) extras.get("link");
        WebView view = (WebView) findViewById(R.id.webView1);

        view.setWebViewClient(new WebViewClient());
        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setPluginState(WebSettings.PluginState.ON);
        view.loadUrl("https://docs.google.com/gview?embedded=true&url="
                +"vvn.city/teacher/"+url);
    }
}
