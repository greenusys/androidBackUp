package com.greenusys.customerservice.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import com.greenusys.customerservice.R;

public class Descripation extends AppCompatActivity {
     TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripation);
        textView = findViewById(R.id.txt);
        textView.setMovementMethod(new ScrollingMovementMethod());
        Intent intent = getIntent();
        String  message1 = intent.getStringExtra("message").toString();
        textView.setText(message1);
        Toast.makeText(this, "sd"+message1, Toast.LENGTH_SHORT).show();

    }
}
