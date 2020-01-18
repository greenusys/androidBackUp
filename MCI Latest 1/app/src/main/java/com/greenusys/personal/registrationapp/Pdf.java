package com.greenusys.personal.registrationapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.github.barteksc.pdfviewer.PDFView;
import com.greenusys.personal.registrationapp.Utility.UrlHelper;
import com.squareup.picasso.Picasso;

public class Pdf extends AppCompatActivity {
    String url;
    String urlTest;
  WebView webView;
    //PDFView pdfView;
    Uri uri;

    ImageView imageView;
    private static final String LOG_TAG = Pdf.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        String link =  getIntent().getStringExtra("link");



        webView = (WebView)findViewById(R.id.webView1);
      //  imageView = (ImageView)findViewById(R.id.pdf_image);

      //  String link =  getIntent().getStringExtra("link");



            webView.setVisibility(View.VISIBLE);



            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setPluginState(WebSettings.PluginState.ON);
            webView.setWebViewClient(new Callback());

            webView.loadUrl("http://docs.google.com/gview?embedded=true&url="
                    + link);




    }

 private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            return(false);
        }
    }
}
