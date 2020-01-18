package com.avinashdavid.trivialtrivia.Soldier_Nursing_Package;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
/*
import com.greenusys.army_project.AppController;
import com.greenusys.army_project.R;
import com.greenusys.army_project.URL;*/

import com.avinashdavid.trivialtrivia.Adapter.Eligibility_Adapter;
import com.avinashdavid.trivialtrivia.AppController;
import com.avinashdavid.trivialtrivia.Model.Eligibility_Model;
import com.avinashdavid.trivialtrivia.R;

import java.util.ArrayList;
import java.util.List;

public class Soldier_Nursing_Eligibility extends AppCompatActivity {
    LinearLayout qualification,written,physical;
    EditText seller_email,seller_password;
    Button bt_seller_login;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    AppController appController;
    RecyclerView rv_gd;
    Eligibility_Adapter eligibility_adapter;
    //Eligibility_Model eligibility_model;
    List<Eligibility_Model> eligibility_models = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nursing_eligibility);

        getSupportActionBar().setTitle("Soldier Nursing Eligibility");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        qualification=(LinearLayout)findViewById(R.id.qualification);
        written=(LinearLayout)findViewById(R.id.written);
        physical=(LinearLayout)findViewById(R.id.physical);


        qualification.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(Soldier_Nursing_Eligibility.this,Nursing_Eligibility_Data.class);
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
