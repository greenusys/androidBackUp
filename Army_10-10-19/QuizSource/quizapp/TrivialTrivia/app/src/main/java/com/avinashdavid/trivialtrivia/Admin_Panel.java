package com.avinashdavid.trivialtrivia;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class Admin_Panel extends AppCompatActivity {

    WebView simpleWebView;
     ProgressDialog pdLoading;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__panel);

        getSupportActionBar().setTitle("Admin Panel");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(isNetworkAvailable()){
            // do network operation here
            pdLoading = new ProgressDialog(Admin_Panel.this);
            simpleWebView = (WebView) findViewById(R.id.simpleWebView);
            pdLoading.setMessage("Loading...");
            pdLoading.show();
            kaif();


        }else{
            Toast.makeText(this, "No Available Network. Please try again later", Toast.LENGTH_LONG).show();
            pdLoading.dismiss();
            return;
        }

    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




    public void kaif()
    {


        simpleWebView.setWebViewClient(new MyWebViewClient());
        String url = "http://4army.in/indianarmy/pages/index.php";
        simpleWebView.getSettings().setJavaScriptEnabled(true);
        simpleWebView.loadUrl(url); // load a web page in a web view
        pdLoading.dismiss();
    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


}