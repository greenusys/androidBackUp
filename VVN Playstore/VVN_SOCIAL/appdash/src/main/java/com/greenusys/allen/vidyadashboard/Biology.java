package com.greenusys.allen.vidyadashboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

public class Biology extends AppCompatActivity {
    GridView grid1;
    String[] web = { "Formula", "Question", "Dictonary", "Quiz"};
    int[] imageId = { R.drawable.formula, R.drawable.ques, R.drawable.dicii, R.drawable.quizz};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biology);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Mathsgrid adapter = new Mathsgrid(Biology.this, web, imageId);
        grid1 = (GridView) findViewById(R.id.grid1);
        grid1.setAdapter(adapter);

        grid1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {

                    case 0:
                        Intent blist1 = new Intent(Biology.this,Bioformula.class);
                        startActivity(blist1);

                        break;
                    case 1:
                        Intent blist3 = new Intent(Biology.this,Bioques.class);
                        startActivity(blist3);


                        break;
                    case 2:
                        Intent blist2 = new Intent(Biology.this,Biodici.class);
                        startActivity(blist2);


                        break;
                    case 3:
                        Intent blist4 = new Intent(Biology.this,ServerList.class);
                        startActivity(blist4);


                        break;


                }
            }


        });

    }
}


