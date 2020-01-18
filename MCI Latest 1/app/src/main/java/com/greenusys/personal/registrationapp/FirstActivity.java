package com.greenusys.personal.registrationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {
    Button btstudent,btpar,btstaff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        btstudent = findViewById(R.id.studlogin);
        btpar = findViewById(R.id.plogin);
        btstaff = findViewById(R.id.slogin);



        btstudent.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(FirstActivity.this,LoginScreen.class));
                    }
                }
        );

        btpar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(FirstActivity.this,ParentLogin.class));
                    }
                }
        );
        btstaff.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(FirstActivity.this,StaffLogin.class));
                    }
                }
        );



    }
}
