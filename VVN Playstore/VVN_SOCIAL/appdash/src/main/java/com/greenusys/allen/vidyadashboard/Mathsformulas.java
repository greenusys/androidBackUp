package com.greenusys.allen.vidyadashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Mathsformulas extends AppCompatActivity {
    String[] physlist2 = {"Constants", "Basic", "Indices", "Logarthims", "Surds", "Quadrilaterals"
          };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mathsformulas);
        ListView lv = (ListView) findViewById(R.id.phys_list);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(Mathsformulas.this, android.R.layout.simple_list_item_1,physlist2);


        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position)
                {
                    case 0:

                        setContentView(R.layout.mfor1);
                        break;

                    case 1:
                        setContentView(R.layout.mfor2);

                        break;
                    case 2:

                        setContentView(R.layout.mfor3);

                        break;

                    case 3:

                        setContentView(R.layout.mfor4);

                        break;
                    case 4:

                        setContentView(R.layout.mfor5);

                        break;
                    case 5:

                        setContentView(R.layout.mfor6);


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