package com.example.digitalforgeco.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.digitalforgeco.R
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element

class Aboutsus : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aboutsus)
        val app_description = "Calculator is a free online site for conversion and calculator tool that is hand-crafted to be used by users.  Our calculator is designed with the intension to make the users to calculate and convert any of the math or logical or scientific or arithmetic problems and get the desired and appropriate solutions.  The calculator tool helps the users to learn and create math problems without difficulty."


        //   simulateDayNight(/* DAY */ 0);
        val adsElement = Element()
        adsElement.title = "Advertise with us"

        val aboutPage = AboutPage(this)
                .isRTL(false)
                .setDescription(app_description)
                .setImage(R.drawable.av)

                .addItem(Element().setTitle("Version 1.6"))
                /*      .addItem(adsElement)*/
                /*  .addGroup("Connect with us")*/
                .addEmail("digitalforgejax@gmail.com")
                .addWebsite("https://numberscalculator.com/")
                /*   .addFacebook("the.medy")
                   .addTwitter("medyo80")*/
                /*.addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")*/
                .addPlayStore("https://play.google.com/store/apps/details?id=com.digitalforgeco.calculator")
                /*  .addInstagram("medyo80")*/
                /* .addGitHub("medyo")*/
                .create()

        setContentView(aboutPage)

    }

    override fun onBackPressed() {

        super.onBackPressed()
    }
}
