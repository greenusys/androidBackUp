package com.greenusys.allen.vidyadashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Physicstutorial extends AppCompatActivity {
    String[] physlist = {"Physics as Science", "Kinematics", "Fluid and Weight",
            "Force", "Energy", "Momentum","Scalar and Vector", "Heat and Thermodynamics","Magnetism"
            , "Electricity"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physicstutorial);
        ListView lv = (ListView) findViewById(R.id.phys_list);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(Physicstutorial.this, android.R.layout.simple_list_item_1,physlist);


        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position)
                {
                    case 0:

                        setContentView(R.layout.intro);
                        break;

                    case 1:

                        setContentView(R.layout.kinematics);
                        break;
                    case 2:
                        setContentView(R.layout.fluid);
                        break;

                    case 3:

                        setContentView(R.layout.force);
                        break;
                    case 4:

                        setContentView(R.layout.energy);
                        break;
                    case 5:

                    setContentView(R.layout.momentum);
                    break;
                    case 6:

                        setContentView(R.layout.scalar);
                        break;
                    case 7:

                        setContentView(R.layout.heat);
                        break;
                    case 8:

                        setContentView(R.layout.magnet);
                        break;
                    case 9:

                        setContentView(R.layout.elec);
                        break;
                }
            }
        });
    }
}
