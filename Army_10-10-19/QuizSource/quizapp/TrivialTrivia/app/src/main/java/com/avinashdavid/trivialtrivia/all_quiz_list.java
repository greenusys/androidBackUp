package com.avinashdavid.trivialtrivia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class all_quiz_list extends AppCompatActivity {

    String[] SPINNERLIST = {"Topic", "Subject", "Section" ,"CA"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_quiz_list);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
       final MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner) findViewById(R.id.android_material_design_spinner);
        materialDesignSpinner.setAdapter(arrayAdapter);


        getSupportActionBar().setTitle("All Quiz");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        materialDesignSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String spinner_value= adapterView.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),spinner_value,Toast.LENGTH_LONG).show();
            }
        });









    }
}