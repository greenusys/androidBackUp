package com.greenusys.allen.vidyadashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Physicsformula extends AppCompatActivity {
    String[] physlist2 = {"Physics Constants", "Vectors", "Mechanism", "Force", "Momentum", "Heat and temperature","waves"
            , "Electricity"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physicsformula);

    ListView lv = (ListView) findViewById(R.id.phys_list);
    ArrayAdapter<String> adapter =
            new ArrayAdapter<String>(Physicsformula.this, android.R.layout.simple_list_item_1,physlist2);


        lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

@Override
public void onItemClick(AdapterView<?> parent, View view,
        int position, long id) {
        switch (position)
        {
        case 0:

        setContentView(R.layout.formula1);
        break;

        case 1:
        setContentView(R.layout.formula2);

        break;
        case 2:

        setContentView(R.layout.formula3);

        break;

        case 3:

        setContentView(R.layout.formula4);
        break;
        case 4:

        setContentView(R.layout.formula5);
        break;
        case 5:

        setContentView(R.layout.formula6);
        break;
        case 6:

        setContentView(R.layout.formula7);
        break;
        case 7:

        setContentView(R.layout.formula8);
        break;
        }
        }
        });
        }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent nnnn=new Intent(Physicsformula.this,Physicsformula.class);
//        startActivity(nnnn);
        //}
        }