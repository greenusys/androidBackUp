package com.greenusys.personal.registrationapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.greenusys.personal.registrationapp.Utility.SharedPreference;

public class Splash extends AppCompatActivity 
{
   // private SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
      //  session = new SessionManagement(getApplicationContext());
        int animationDuration = 3500; // 2500ms = 2,5s
    }

    private void goNext()
    {

            new Thread(new Runnable() {
                public void run() {
                    doWork();
                    startApp();
                    try {
                        ActivityCompat.finishAfterTransition(Splash.this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

    }

    @Override
    protected void onResume() {
        super.onResume();
     //   AppController.getInstance().setConnectivityListener(this);
        goNext();
    }
/*
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
    }*/

    private void doWork() {
        for (int progress = 0; progress < 100; progress++) {
            try {
                Thread.sleep(30);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void startApp() {
       if(SharedPreference.isLoggedIn(Splash.this)){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, FirstActivity.class);
            startActivity(intent);
        }
    }

   /* private void showSnack(boolean isConnected) {
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
                .make(findViewById(R.id.coorLayout), message, Snackbar.LENGTH_INDEFINITE);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }*/

    /*@Override
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
    }*/
}