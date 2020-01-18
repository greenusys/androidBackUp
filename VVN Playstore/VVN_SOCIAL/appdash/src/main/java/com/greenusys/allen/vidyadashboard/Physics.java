package com.greenusys.allen.vidyadashboard;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import android.widget.AdapterView;
import android.widget.GridView;

public class Physics extends AppCompatActivity {
    GridView grid1;
    String[] web = {"Physics Tutorial", "Formula", "Physics Question", "Dictonary", "Quiz"};
    int[] imageId = {R.drawable.book1, R.drawable.formula, R.drawable.ques, R.drawable.dicii, R.drawable.quizz};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physics);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Physicsgrid adapter = new Physicsgrid(Physics.this, web, imageId);
        grid1 = (GridView) findViewById(R.id.grid1);
        grid1.setAdapter(adapter);

        grid1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        Intent plist1 = new Intent(Physics.this, Physicstutorial.class);
                        startActivity(plist1);


                        break;

                    case 1:
                        Intent plist2 = new Intent(Physics.this, Physicsformula.class);
                        startActivity(plist2);

                        break;

                    case 2:
                        Intent plist3 = new Intent(Physics.this, PhysicsQues.class);
                        startActivity(plist3);

                        break;


                    case 3:
                        Intent plist4 = new Intent(Physics.this, PhysDici.class);
                        startActivity(plist4);

                        break;


                    case 4:
                        Intent plist5 = new Intent(Physics.this, ServerList.class);
                        startActivity(plist5);

                        break;

                }
        }


        });

    }
}




