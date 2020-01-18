package com.avinashdavid.trivialtrivia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Quiz_Instruction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz__instruction);

        String value=getIntent().getStringExtra("");
        String id=getIntent().getStringExtra("");



    }
}
