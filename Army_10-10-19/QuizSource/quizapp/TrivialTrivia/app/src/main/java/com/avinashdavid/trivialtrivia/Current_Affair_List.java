package com.avinashdavid.trivialtrivia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Current_Affair_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current__affair__list);

        getSupportActionBar().setTitle("Current Affair");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
