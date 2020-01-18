package com.avinashdavid.trivialtrivia.Soldier_Tech_Package;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
/*
import com.greenusys.army_project.AppController;
import com.greenusys.army_project.R;
import com.greenusys.army_project.URL;*/

import com.avinashdavid.trivialtrivia.AppController;
import com.avinashdavid.trivialtrivia.R;

public class Soldier_Tech_Eligibility extends AppCompatActivity {
    LinearLayout qualification,written,physical;
    EditText seller_email,seller_password;
    Button bt_seller_login;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    AppController appController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_eligibility);

        getSupportActionBar().setTitle("Soldier Tech Eligibility");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        qualification=(LinearLayout)findViewById(R.id.qualification);
        written=(LinearLayout)findViewById(R.id.written);
        physical=(LinearLayout)findViewById(R.id.physical);


        qualification.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(Soldier_Tech_Eligibility.this,Tech_Eligibility_Data.class);
                        startActivity(intent);


                    }

                }
        );


        written.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Intent intent=new Intent(Soldier_Eligibility.this,Soldier_Eligibility_Data.class);
                        // startActivity(intent);


                    }

                }
        );
        physical.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Intent intent=new Intent(Soldier_Eligibility.this,Soldier_Eligibility_Data.class);
                        //startActivity(intent);


                    }

                }
        );
    }
}
