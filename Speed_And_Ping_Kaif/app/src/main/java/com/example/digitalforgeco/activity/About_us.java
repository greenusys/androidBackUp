package com.example.digitalforgeco.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.digitalforgeco.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


public class About_us extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String app_description = "Use Speed And Ping app  to test your internet speed and check network performance!\n" +
                "With just one tap, it will test your internet connection through thousands of servers worldwide and show accurate results within 30 seconds.\n" +
                "SpeedTest Master is a free internet speed meter. It can test speed for 2G, 3G, 4G, 5G, DSL, and ADSL. It's also a wifi analyzer that could help you test wifi connection.";


        //   simulateDayNight(/* DAY */ 0);
        Element adsElement = new Element();
        adsElement.setTitle("Advertise with us");

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setDescription(app_description)
                .setImage(R.drawable.app_icon_2)
                .addItem(new Element().setTitle("Version 1.1"))
                /*      .addItem(adsElement)*/
                /*  .addGroup("Connect with us")*/
                .addEmail("digitalforgejax@gmail.com")
                .addWebsite("http://speedandping.com/")
                /*   .addFacebook("the.medy")
                   .addTwitter("medyo80")*/
                /*.addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")*/
/*
                .addPlayStore("https://play.google.com/store/apps/details?id=com.digitalforgeco.packchase")
*/
                /*  .addInstagram("medyo80")*/
                /* .addGitHub("medyo")*/
                .create();

        setContentView(aboutPage);

    }


    public void back(View view) {
        System.out.println("called");
    onBackPressed();
    }
}
