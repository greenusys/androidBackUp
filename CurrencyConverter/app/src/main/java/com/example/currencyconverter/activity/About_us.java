package com.example.currencyconverter.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import com.example.currencyconverter.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


public class About_us extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String app_description = "Our online Currency Converter is a quick and easy way to see live market exchange rates at the click of a button.";

        //   simulateDayNight(/* DAY */ 0);
        Element adsElement = new Element();
        adsElement.setTitle("Advertise with us");

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setDescription(app_description)
                .setImage(R.drawable.cur_logo)
                .addItem(new Element().setTitle("Version 1.1"))
                /*      .addItem(adsElement)*/
                /*  .addGroup("Connect with us")*/
                .addEmail("digitalforgejax@gmail.com")
                .addWebsite("https://convertedcurrency.com/")
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



}
