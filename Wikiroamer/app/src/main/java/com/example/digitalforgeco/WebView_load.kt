package com.example.digitalforgeco

import androidx.appcompat.app.AppCompatActivity


import android.os.Bundle
import android.view.LayoutInflater
import android.webkit.WebView

import com.google.android.material.bottomsheet.BottomSheetDialog


class WebView_load : AppCompatActivity() {


    // WebView webView;
    private var sheetDialog: BottomSheetDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_view)

        supportActionBar!!.title = intent.getStringExtra("country_name")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //  webView=findViewById(R.id.webview);

        // webView.getSettings().setJavaScriptEnabled(true);
        //webView.loadUrl(getIntent().getStringExtra("url"));
        //webView.setWebViewClient(new WebViewClient());

        sheetDialog = BottomSheetDialog(this@WebView_load)
        val sheetView = LayoutInflater.from(this@WebView_load).inflate(R.layout.bottom_sheet, null)
        val webView = sheetView.findViewById<WebView>(R.id.webview)
        sheetDialog!!.setContentView(sheetView)
        webView.loadUrl("https://en.wikipedia.org/wiki/India")
        sheetDialog!!.show()


    }


}