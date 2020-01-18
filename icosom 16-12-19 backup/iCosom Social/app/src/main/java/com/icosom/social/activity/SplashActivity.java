package com.icosom.social.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icosom.social.R;
import com.icosom.social.broadcast.ConnectivityReceiver;
import com.icosom.social.utility.AppController;

public class SplashActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener
{
    Boolean inBackground = false;

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        int animationDuration = 3500; // 2500ms = 2,5s

        linearLayout = findViewById(R.id.tab_layout);
        linearLayout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    }
                }
        );
    }

    private void goNext()
    {
        if (checkConnection())
        {
            new Thread(new Runnable() {
                public void run() {
                    doWork();
                    startApp();
                    try {
                        ActivityCompat.finishAfterTransition(SplashActivity.this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        else
        {
            showSnack(checkConnection());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppController.getInstance().setConnectivityListener(this);
        goNext();
    }

    private boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected)
        {
            inBackground = true;
            showSnack(isConnected);
            return false;
        }
        else
        {
            inBackground = false;
            showSnack(isConnected);
            return true;
        }
    }

    private void doWork() {
        for (int progress = 0; progress < 100; progress++) {
            try {
                Thread.sleep(30000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void startApp() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void showSnack(boolean isConnected) {
        String message = null;
        int color = 0;
        if (!isConnected) {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }
        else
        {
            message = "Great You are connected";
            color = Color.GREEN;
        }

       Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coorLayout), message, Snackbar.LENGTH_SHORT);


        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected)
        {
            inBackground = false;
            goNext();
        }
        else
        {
            inBackground = true;
            showSnack(isConnected);
        }
    }
}