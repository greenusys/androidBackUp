package com.greenusys.allen.vidyadashboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class ChooseVideo extends AppCompatActivity {
    GridView grid1;
    String[] web = {"Science","Maths","Chem","Bio","geography","Physics"};
    int[] imageId ={R.drawable.sci,R.drawable.math,R.drawable.chem,R.drawable.bio,R.drawable.comp,R.drawable.phys};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_video);
        Videogrid adapter = new Videogrid(ChooseVideo.this, web, imageId);
        grid1 = (GridView) findViewById(R.id.grid1);
        grid1.setAdapter(adapter);
        grid1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position)
                {
                    case 0:
                        next("Science2",1);
                        break;

                    case 1:
                        next("Maths",2);
                        break;
                    case 2:
                        next("Chemistry",3);
                        break;
                    case 3:
                        next("Biology",4);
                        break;
                    case 4:
                        next("geography",5);
                        break;
                    case 5:
                        next("Physics",6);
                        break;

                }

            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent yy1=new Intent(ChooseVideo.this,MainActivity_Dash.class);
        startActivity(yy1);
    }

    private void next(String str,int im) {

        Intent nnn=new Intent(ChooseVideo.this,Main2Activity.class);
        nnn.putExtra("type", str);
        nnn.putExtra("image", im);
        startActivity(nnn);
    }
}