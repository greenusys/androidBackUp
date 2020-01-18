package com.avinashdavid.trivialtrivia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class Current_Affair extends AppCompatActivity {
    String[] SPINNERLIST = {"Hindi", "English"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current__affair);

        getSupportActionBar().setTitle("Current Affair(GK)");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.android_material_design_spinner);
        materialDesignSpinner.setAdapter(arrayAdapter);



        materialDesignSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String spinner_value= adapterView.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),spinner_value,Toast.LENGTH_LONG).show();
            }
        });





    }


}
