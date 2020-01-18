package com.greenusys.allen.vidyadashboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Terms extends AppCompatActivity {
    String url="https://vvn.city/Terms/TERM.pdf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        WebView view = (WebView) findViewById(R.id.webView1);

        view.setWebViewClient(new WebViewClient());
        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setPluginState(WebSettings.PluginState.ON);
        view.loadUrl("http://docs.google.com/gview?embedded=true&url="
                +url);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backa=new Intent(Terms.this,MainActivity_Dash.class);
        startActivity(backa);
    }
}
