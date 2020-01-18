package com.example.digitalforgeco.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.digitalforgeco.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


public class About_us extends AppCompatActivity {


    //https://areuloved.com:2083/
    //kvtaf1utdxk1
    //Port@9090

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String app_description = "Having bought in the online store always want to know where the package is now? To save time checking the sites of postal companies manually, I created \"Packchase\" website and mobile apps which will automatically check every relevant postal, courier and logistics company website on your behalf. To find out where your parcel is, you need to know only tracking number of your package";


        //   simulateDayNight(/* DAY */ 0);
        Element adsElement = new Element();
        adsElement.setTitle("Advertise with us");

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setDescription(app_description)
                .setImage(R.drawable.pack)
                .addItem(new Element().setTitle("Version 1.1"))
                /*      .addItem(adsElement)*/
                /*  .addGroup("Connect with us")*/
                .addEmail("digitalforgejax@gmail.com")
                .addWebsite("https://packchase.com/")
                /*   .addFacebook("the.medy")
                   .addTwitter("medyo80")*/
                /*.addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")*/
                .addPlayStore("https://play.google.com/store/apps/details?id=com.digitalforgeco.packchase")
                /*  .addInstagram("medyo80")*/
                /* .addGitHub("medyo")*/
                .create();

        setContentView(aboutPage);

    }


}
