package com.greenusys.allen.vidyadashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Chemistryformula extends AppCompatActivity {
    String[] physlist2 = {"Chemical Constants", "Atomic structure", "Gas laws", "Equilibrium", "Thermodynamics", "Electrochemistry"
            , "Polyatomic ions","Nuclear Symbols","Alkane Series","Functional Group"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemistryformula);
        ListView lv = (ListView) findViewById(R.id.phys_list);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(Chemistryformula.this, android.R.layout.simple_list_item_1,physlist2);


        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position)
                {
                    case 0:

                        setContentView(R.layout.cfor1);
                        break;

                    case 1:
                        setContentView(R.layout.cfor2);

                        break;
                    case 2:

                        setContentView(R.layout.cfor5);

                        break;

                    case 3:

                        setContentView(R.layout.cfor4);
                        break;
                    case 4:

                        setContentView(R.layout.cfor5);
                        break;
                    case 5:

                        setContentView(R.layout.cfor6);
                        break;
                    case 6:

                        setContentView(R.layout.cfor7);
                        break;
                    case 7:

                        setContentView(R.layout.cfor8);
                        break;
                    case 8:

                        setContentView(R.layout.cfor9);
                        break;
                    case 9:

                        setContentView(R.layout.cfor10);
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