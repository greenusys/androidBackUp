package com.greenusys.allen.vidyadashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ProgressBar;


public class Splash_Dash extends AppCompatActivity {

    private ProgressBar firstBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_dash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        firstBar = (ProgressBar) findViewById(R.id.firstBar);
        new Thread(new Runnable() {
            public void run() {
                doWork();
                startApp();
                try {
                    ActivityCompat.finishAfterTransition(Splash_Dash.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void doWork() {
        for (int progress = 0; progress < 100; progress++) {
            try {
                Thread.sleep(20);
                firstBar.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void startApp() {
        Intent intent = new Intent(this, MainActivity_Dash.class);
        startActivity(intent);
    }
}




