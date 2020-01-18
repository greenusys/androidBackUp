package com.greenusys.customerservice.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.greenusys.customerservice.R;

public class Pdf extends AppCompatActivity {
    String url;
    String urlTest;
    WebView webView;
    ImageView imageView;
    private static final String LOG_TAG = Pdf.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        webView = (WebView)findViewById(R.id.webView1);
        imageView = (ImageView)findViewById(R.id.pdf_image);

      /*  String pdfName =  getIntent().getStringExtra(getString(R.string.pdf_intent_extra));*/
        String link =  getIntent().getStringExtra("link");
       /* url = UrlHelper.coursePdfStaticUrl + pdfName;*/

        // urlTest = "http://greenusys.website/mci/uploads/course/" + "2018030733001.pdf";

        //      Log.d(LOG_TAG, pdfName.split("\\.")[0] + pdfName.split("\\.")[0]);


            Log.e("url", "onCreate: " + url);

            webView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);


            /**view.setWebViewClient(new WebViewClient());
             view.getSettings().setJavaScriptEnabled(true);
             view.getSettings().setPluginState(WebSettings.PluginState.ON);
             view.loadUrl("http://docs.google.com/gview?embedded=true&url="
             +url);*/

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
