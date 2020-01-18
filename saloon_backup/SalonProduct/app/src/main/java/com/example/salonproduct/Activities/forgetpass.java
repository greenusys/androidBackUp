package com.example.salonproduct.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.salonproduct.R;

public class forgetpass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setIcon(R.drawable.salon);
    }
}
