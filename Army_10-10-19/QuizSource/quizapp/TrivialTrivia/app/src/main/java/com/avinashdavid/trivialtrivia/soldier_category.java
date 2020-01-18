package com.avinashdavid.trivialtrivia;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.avinashdavid.trivialtrivia.Notification_package.Admit_Card;
import com.avinashdavid.trivialtrivia.Notification_package.All_Rally;
import com.avinashdavid.trivialtrivia.Notification_package.Check_Rally;
import com.avinashdavid.trivialtrivia.Notification_package.Latest_Notification;
import com.avinashdavid.trivialtrivia.Notification_package.More_Notification;
import com.avinashdavid.trivialtrivia.Soldier_Clerk.Clerk;
import com.avinashdavid.trivialtrivia.Soldier_GD_Package.Soldier_GD;
import com.avinashdavid.trivialtrivia.Soldier_Nursing_Package.Soldier_Nursing;
import com.avinashdavid.trivialtrivia.Soldier_Tech_Package.Soldier_Tech;
import com.avinashdavid.trivialtrivia.UI.ActivityWelcomePage;


public class soldier_category extends AppCompatActivity {

    public soldier_category() {
    }

    //indian army preparation
    LinearLayout soldier_gd, soldier_cleark, soldier_tech, soldier_nursing, soldier_post;

    //Indian Army Notification
    LinearLayout check_rally, all_rally, latest_notification, admit_card, more_notification;

    //Daily Army QUiz
    LinearLayout maths, gk, english, ca, all_quiz;

    //Current Affair lang
    LinearLayout hindi_lang,english_lang,check_your_eligibility,check_latest_rally;


    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soldier_category);
        //Current Affair lang
        hindi_lang = (LinearLayout) findViewById(R.id.hindi_lang);
        english_lang = (LinearLayout) findViewById(R.id.english_lang);



        LayoutInflater inflater = getLayoutInflater();
        View toastLayout = inflater.inflate(R.layout.content_custom_toast, (ViewGroup) findViewById(R.id.llCustom));
        toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastLayout);

        //toast.show();




        check_your_eligibility = (LinearLayout) findViewById(R.id.check_your_eligibility);
        check_your_eligibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(soldier_category.this,Check_Your_Eligibility.class);
                startActivity(intent);
            }
        });
        check_latest_rally = (LinearLayout) findViewById(R.id.check_latest_rally);
        check_latest_rally.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(soldier_category.this, Check_Rally.class);
                startActivity(i);
            }
        });



        //indian army preparation
        soldier_gd = (LinearLayout) findViewById(R.id.soldier_gd);
        soldier_cleark = (LinearLayout) findViewById(R.id.soldier_clerk);
        soldier_tech = (LinearLayout) findViewById(R.id.soldier_tech);
        soldier_nursing = (LinearLayout) findViewById(R.id.soldier_nursing);
        //soldier_post = (LinearLayout) findViewById(R.id.soldier_post);



//        check_rally.setBackgroundColor(Color.parseColor("#000000"));

        //Indian army notification
        check_rally = (LinearLayout) findViewById(R.id.check_rally);
        all_rally = (LinearLayout) findViewById(R.id.all_rally);
        latest_notification = (LinearLayout) findViewById(R.id.latest_notification);
        admit_card = (LinearLayout) findViewById(R.id.admit_card);
        more_notification = (LinearLayout) findViewById(R.id.more);

        //Daily Army QUiz
        maths = (LinearLayout) findViewById(R.id.maths);
        gk = (LinearLayout) findViewById(R.id.gk);
        english = (LinearLayout) findViewById(R.id.english);
        ca = (LinearLayout) findViewById(R.id.ca);
        all_quiz = (LinearLayout) findViewById(R.id.allquiz);




        getSupportActionBar().setTitle("Home");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Current Affair Language Intents
        hindi_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent i = new Intent(soldier_category.this, Current_Affair_Hindi_English.class);
                 i.putExtra("language","hindi");
                 startActivity(i);
            }
        });

        english_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(soldier_category.this, Current_Affair_Hindi_English.class);
                i.putExtra("language","english");
                 startActivity(i);
            }
        });



        //Daily Army Quiz Intents
        maths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(soldier_category.this, ActivityWelcomePage.class);
                startActivity(i);
            }
        });

        gk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(soldier_category.this, ActivityWelcomePage.class);
                startActivity(i);
            }
        });


        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(soldier_category.this, ActivityWelcomePage.class);
                startActivity(i);
            }
        });

        ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(soldier_category.this, ActivityWelcomePage.class);
                startActivity(i);
            }
        });

        all_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(soldier_category.this, all_quiz_category.class);
                startActivity(i);
            }
        });










        //Indian Army Preparation Intents
        check_rally.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(soldier_category.this, Check_Rally.class);
                startActivity(i);
            }
        });

        all_rally.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(soldier_category.this, All_Rally.class);
                startActivity(i);
            }
        });


        latest_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(soldier_category.this, Latest_Notification.class);
                startActivity(i);
            }
        });

        admit_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(soldier_category.this, Admit_Card.class);
                startActivity(i);
            }
        });

        more_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(soldier_category.this, More_Notification.class);
                startActivity(i);
            }
        });








        //Soldier GD Intents
        soldier_gd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(soldier_category.this, Soldier_GD.class);
                startActivity(i);
            }
        });

        soldier_cleark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(soldier_category.this, Clerk.class);
                startActivity(i);
            }
        });


        soldier_tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(soldier_category.this, Soldier_Tech.class);
                startActivity(i);
            }
        });

        soldier_nursing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(soldier_category.this, Soldier_Nursing.class);
                startActivity(i);
            }
        });




    }



    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        //Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        toast.show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
