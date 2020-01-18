package com.greenusys.personal.registrationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class FullNewzActivity extends AppCompatActivity {

    TextView titleTextView;
    TextView descriptionTextView,dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_newz);

        titleTextView = (TextView)findViewById(R.id.news_title);
        descriptionTextView = (TextView)findViewById(R.id.newz_description);
        dates = (TextView)findViewById(R.id.newz_date);

        //String title = getIntent().getStringExtra(getString(R.string.newz_title));
        //String time = getIntent().getStringExtra("time");

        String title = getIntent().getStringExtra("title");
        String date = getIntent().getStringExtra("date");
        String description = getIntent().getStringExtra("description");


       // Log.e("llll", "onCreate:" +time);
       // String description = getIntent().getStringExtra(getString(R.string.newz_descrition));

        titleTextView.setText(title);
        dates.setText(date);
       descriptionTextView.setText(description);

    }
}
