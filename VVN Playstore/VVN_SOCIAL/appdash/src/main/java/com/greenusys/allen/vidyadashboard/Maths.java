package com.greenusys.allen.vidyadashboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

public class Maths extends AppCompatActivity {
    GridView grid1;
    String[] web = { "Formula", "Question", "Dictonary", "Quiz"};
    int[] imageId = { R.drawable.formula, R.drawable.ques, R.drawable.dicii, R.drawable.quizz};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
   Mathsgrid adapter = new Mathsgrid(Maths.this, web, imageId);
        grid1 = (GridView) findViewById(R.id.grid1);
        grid1.setAdapter(adapter);

        grid1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {


                    case 0:
                        Intent mlist3 = new Intent(Maths.this,Mathsformulas.class);
                        startActivity(mlist3);



                        break;

                    case 1:
                        Intent mlist1 = new Intent(Maths.this,Mathsques.class);
                        startActivity(mlist1);



                        break;
                    case 2:
                        Intent mlist2 = new Intent(Maths.this,Mathsdicii.class);
                        startActivity(mlist2);



                        break;
                    case 3:
                        Intent mlist4 = new Intent(Maths.this,ServerList.class);
                        startActivity(mlist4);



                        break;


                }
            }


        });

    }
}

