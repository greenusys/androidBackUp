package com.greenusys.allen.vidyadashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Bioformula extends AppCompatActivity {
    String[] physlist2 = {"Metrices", "Water  Potenial", "Dilution Equation", "Gibbs Free Energy", "Wave Equation", "Growth and Decay"
            , "pH Measurement"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bioformula);
        ListView lv = (ListView) findViewById(R.id.phys_list);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(Bioformula.this, android.R.layout.simple_list_item_1,physlist2);


        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position)
                {
                    case 0:

                        setContentView(R.layout.bfor1);
                        break;

                    case 1:
                        setContentView(R.layout.bfor2);

                        break;
                    case 2:

                        setContentView(R.layout.bfor3);

                        break;

                    case 3:

                        setContentView(R.layout.bfor4);
                        break;
                    case 4:

                        setContentView(R.layout.bfor5);
                        break;
                    case 5:

                        setContentView(R.layout.bfor6);
                        break;
                    case 6:

                        setContentView(R.layout.bfor7);
                        break;

                }
            }
        });
    }
    }

