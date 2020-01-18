package com.greenusys.allen.vidyadashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Chemistrytut extends AppCompatActivity {
    String[] chemlist = {"Atomic Structure", "Periodicity Chemistry", "Chemical Bonding",
            "State of Matter", "Acid Base and salts", "Redox reactions"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemistrytut);
        ListView lv = (ListView) findViewById(R.id.chem_list);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(Chemistrytut.this, android.R.layout.simple_list_item_1,chemlist);


        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position)
                {
                    case 0:

                        setContentView(R.layout.cintro);
                        break;

                    case 1:

                        setContentView(R.layout.cpp);
                        break;
                    case 2:

                        setContentView(R.layout.cb);
                        break;
                    case 3:

                        setContentView(R.layout.state);
                        break;
                    case 4:

                        setContentView(R.layout.acid);
                        break;
                    case 5:

                        setContentView(R.layout.te);
                        break;
                    case 6:

                        setContentView(R.layout.scalar);
                        break;

                }
            }
        });
    }
}
