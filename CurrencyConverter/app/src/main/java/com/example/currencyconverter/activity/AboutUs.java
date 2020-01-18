package com.example.currencyconverter.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.currencyconverter.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        String app_description = "The world’s most valued currency, the Bahraini Dinar, has been added to the list of currencies we exchange for cash. Did you travel to Bahrain recently and did you come back with leftover travel money?";


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
                .addPlayStore("https://play.google.com/store/apps/details?id=com.safderali.currency")
                /*  .addInstagram("medyo80")*/
                /* .addGitHub("medyo")*/
                .create();

        setContentView(aboutPage);

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}
