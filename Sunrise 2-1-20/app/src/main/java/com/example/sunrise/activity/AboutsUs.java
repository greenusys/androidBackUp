package com.example.sunrise.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.example.sunrise.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutsUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abouts_us);

        String app_description = "Sunrise-Sunset is a free online tool that provides users information about day length, twilight, sunrise and sunset times for any location of the world. Our purpose is to make it easy to everybody to access Sun related information through simple tools that offers accurate information.";


        //   simulateDayNight(/* DAY */ 0);
        Element adsElement = new Element();
        adsElement.setTitle("Advertise with us");

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setDescription(app_description)
                .setImage(R.drawable.sunlogo)

                .addItem(new Element().setTitle("Version 1.1"))
                /*      .addItem(adsElement)*/
                /*  .addGroup("Connect with us")*/
                .addEmail("digitalforgejax@gmail.com")
                .addWebsite("https://mysunsetandsunrise.com/")
                /*   .addFacebook("the.medy")
                   .addTwitter("medyo80")*/
                /*.addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")*/
                .addPlayStore("https://play.google.com/store/apps/details?id=com.digitalforgeco.suntime")
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



