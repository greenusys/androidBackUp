package com.example.digitalforgeco.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import com.example.digitalforgeco.R
import com.example.menu_library.animation.GuillotineAnimation
import com.example.menu_library.interfaces.GuillotineListener
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd

class MainActivity : AppCompatActivity() {
    internal var root: FrameLayout? = null
    internal var contentHamburger: View? = null
    internal var toolbar: Toolbar? = null

    //for menu layout
    internal lateinit var home: LinearLayout
    internal lateinit var distance_1: LinearLayout
    internal lateinit var percentage_2: LinearLayout
    internal lateinit var temperature_3: LinearLayout
    internal lateinit var weight_4: LinearLayout
    internal lateinit var length_5: LinearLayout
    internal lateinit var simple_6: LinearLayout
    internal lateinit var scientific_7: LinearLayout
    internal lateinit var volume_8: LinearLayout
    internal lateinit var abouts: LinearLayout
    private var isGuillotineOpened: Boolean = false
    internal lateinit var aniMenu: GuillotineAnimation


    private var mInterstitialAd: InterstitialAd? = null
    private var adView: AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adView = findViewById(R.id.adView)

        setMenus()

        //loadInterStitialsAd();
        //loadBannerAdAd();


    }

    /*private fun initViews() {




        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val headerView = navigationView.getHeaderView(0)
        //val header_image=headerView.findViewById<ImageView>(R.id.header_image)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)





    }
*/


    //1
    fun goto_distance_calc(view: View) {
        startActivity(Intent(this, Distance_Calculator_1::class.java))
        //  overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);

    }

    //2
    fun goto_percentaqe_calc(view: View) {
        startActivity(Intent(this, Percentage_Calculator_2::class.java))
        //overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
    }

    //3
    fun goto_temperature_cacl(view: View) {
        startActivity(Intent(this, Temperature_Calculator_3::class.java))
        // overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
    }


    //4
    fun goto_weight_calc(view: View) {
        startActivity(Intent(this, Weight_Calculator_4::class.java))
        //overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
    }

    //5
    fun goto_length_calc(view: View) {
        startActivity(Intent(this, Length_Calculator_5::class.java))
        //overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
    }

    //6
    fun goto_simple_calc(view: View) {
        startActivity(Intent(this, Simple_Calculator_6::class.java))
        //overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
    }

    //7
    fun goto_bmi_calc(view: View) {
        startActivity(Intent(this, BMI_Calculator_7::class.java))
        //overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
    }

    //8
    fun goto_scintific_calc(view: View) {
        startActivity(Intent(this, Scientific_Calculator_8::class.java))
        //overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
    }


    //9
    fun goto_volume_calc(view: View) {
        startActivity(Intent(this, Volume_Calculator_9::class.java))
        //overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
    }

    fun abouts(view: View) {
        startActivity(Intent(this@MainActivity, Aboutsus::class.java))
    }


    private fun setMenus() {

        val root: FrameLayout
        val contentHamburger: View
        val toolbar: Toolbar? = null


        if (toolbar != null) {
            setSupportActionBar(toolbar)
            supportActionBar!!.title = null
        }

        root = findViewById(R.id.root)
        contentHamburger = findViewById(R.id.content_hamburger)

        val guillotineMenu = LayoutInflater.from(this).inflate(R.layout.menu_layout, null)
        root.addView(guillotineMenu)


        val guillotineBuilder = GuillotineAnimation.GuillotineBuilder(guillotineMenu,
                guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
        guillotineBuilder.setStartDelay(RIPPLE_DURATION)
        guillotineBuilder.setActionBarViewForAnimation(toolbar)
        guillotineBuilder.setGuillotineListener(object : GuillotineListener {
            override fun onGuillotineOpened() {
                isGuillotineOpened = true

            }

            override fun onGuillotineClosed() {
                isGuillotineOpened = false

            }
        })

        guillotineBuilder.setClosedOnStart(true)
        aniMenu = guillotineBuilder.build()

        //find menus ids
        home = guillotineMenu.findViewById(R.id.home)
        distance_1 = guillotineMenu.findViewById(R.id.distance)
        percentage_2 = guillotineMenu.findViewById(R.id.percentage)
        temperature_3 = guillotineMenu.findViewById(R.id.temperature)
        weight_4 = guillotineMenu.findViewById(R.id.weight)
        length_5 = guillotineMenu.findViewById(R.id.length)
        simple_6 = guillotineMenu.findViewById(R.id.simple)
        scientific_7 = guillotineMenu.findViewById(R.id.scientific)
        volume_8 = guillotineMenu.findViewById(R.id.volume)
        abouts = guillotineMenu.findViewById(R.id.abouts)
        //


        set_Menus_Click_Listener()

    }

    private fun set_Menus_Click_Listener() {


        home.setBackgroundResource(R.color.box_7)
        home.setPadding(0, 0, 0, 5)


        distance_1.setOnClickListener {
            close_Menu()

            startActivity(Intent(applicationContext, Distance_Calculator_1::class.java).setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME))
            //overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
        }

        percentage_2.setOnClickListener {
            startActivity(Intent(applicationContext, Percentage_Calculator_2::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            //overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
        }

        temperature_3.setOnClickListener {
            close_Menu()

            startActivity(Intent(applicationContext, Temperature_Calculator_3::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            //overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
        }

        weight_4.setOnClickListener {
            close_Menu()

            startActivity(Intent(applicationContext, Weight_Calculator_4::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            //overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
        }

        length_5.setOnClickListener {
            close_Menu()

            startActivity(Intent(applicationContext, Length_Calculator_5::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            //overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
        }

        simple_6.setOnClickListener {
            close_Menu()
            startActivity(Intent(applicationContext, Simple_Calculator_6::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            //overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
        }

        scientific_7.setOnClickListener {
            close_Menu()
            startActivity(Intent(applicationContext, Scientific_Calculator_8::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            //overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
        }
        volume_8.setOnClickListener {
            close_Menu()
            startActivity(Intent(applicationContext, Volume_Calculator_9::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            //overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
        }


    }


    override fun onBackPressed() {
        if (!isGuillotineOpened) {
            super.onBackPressed()
        } else
            close_Menu()

    }

    private fun close_Menu() {

        this@MainActivity?.runOnUiThread { aniMenu.close() }
    }


    override fun onPause() {

        if (adView != null)
            adView!!.pause()

        super.onPause()

    }

    override fun onResume() {
        if (adView != null)
            adView!!.resume()
        super.onResume()
    }

    override fun onDestroy() {
        if (adView != null)
            adView!!.destroy()
        super.onDestroy()
    }

    //adds

    private fun loadInterStitialsAd() {
        mInterstitialAd = InterstitialAd(this)
        //mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712"); //test adid
        mInterstitialAd!!.adUnitId = "ca-app-pub-3701953680756708/7797728612" //live adid

        mInterstitialAd!!.loadAd(AdRequest.Builder().build())

        mInterstitialAd!!.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.

                if (mInterstitialAd != null)
                    mInterstitialAd!!.show()
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.

                Toast.makeText(this@MainActivity, "$errorCode code", Toast.LENGTH_SHORT).show()
            }

            override fun onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
            }
        }
    }

    private fun loadBannerAdAd() {
        /* AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");*/
        val adRequest = AdRequest.Builder().build()
        adView!!.loadAd(adRequest)


        adView!!.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.
                Toast.makeText(this@MainActivity, "$errorCode Banner code", Toast.LENGTH_SHORT).show()

            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }
    }

    companion object {

        private val RIPPLE_DURATION: Long = 100
    }

}


