package com.greenusys.allen.vidyadashboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

public class Chemistry extends AppCompatActivity {
 GridView grid1;
    String[] web = {"Chem Tutorial", "Formula", "Question", "Dictonary", "Quiz"};
    int[] imageId = {R.drawable.book1, R.drawable.formula, R.drawable.ques, R.drawable.dicii, R.drawable.quizz};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemistry);
getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      Chemistrygrid adapter = new Chemistrygrid(Chemistry.this, web, imageId);
        grid1 = (GridView) findViewById(R.id.grid1);
        grid1.setAdapter(adapter);

        grid1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {


                    case 0:
                        Intent clist1 = new Intent(Chemistry.this,Chemistrytut.class);
                        startActivity(clist1);


                        break;
                    case 1:
                        Intent clist3 = new Intent(Chemistry.this,Chemistryformula.class);
                        startActivity(clist3);


                        break;
                    case 2:
                        Intent clist2 = new Intent(Chemistry.this,ChemistryQues.class);
                        startActivity(clist2);


                        break;


                    case 4:
                        Intent clist5 = new Intent(Chemistry.this, ServerList.class);
                        startActivity(clist5);

                        break;
                    case 3:
                    Intent clist45=new Intent(Chemistry.this,Chemdici.class);
                        startActivity(clist45);

       break;

                }
        }


        });

    }
}

