package com.example.rx_java_kaif.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rx_java_kaif.R;


/**
 * @author ashok.kumar
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sinpleRxJavaOnlyBtn = (Button) findViewById(R.id.rxjava_only);
        Button retrofitUsingByJavaBtn = (Button) findViewById(R.id.rxjava_using_retrofit);
        sinpleRxJavaOnlyBtn.setOnClickListener(this);
        retrofitUsingByJavaBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rxjava_only) {
            startActivity(new Intent(this, SimpleRxJavaActivity.class));
        } else if (v.getId() == R.id.rxjava_using_retrofit) {
            startActivity(new Intent(this, RetroRxJavaActivity.class));
        }
    }
}
