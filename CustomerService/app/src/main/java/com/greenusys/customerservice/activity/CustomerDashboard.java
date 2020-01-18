package com.greenusys.customerservice.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.greenusys.customerservice.R;
import com.greenusys.customerservice.utility.AppController;
import com.greenusys.customerservice.utility.UrlHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,  BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    private List<Customer_Model> customerModelLists = new ArrayList<>();
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    String userId;
    RecyclerView rv;
    AppController appController;


    Customer_Model customer_models;
    SearchView searchView;
    int i=0;

    SliderLayout sliderLayout;
    HashMap<String,String> Hash_file_maps ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle(R.string.toolbar_title);
        appController = (AppController) getApplicationContext();
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edit = sp.edit();
        userId = sp.getString("userId", "0");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerDashboard.this, AddCallLock.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        //for slider images
        Hash_file_maps = new HashMap<String, String>();

        sliderLayout = (SliderLayout)findViewById(R.id.slider);

        Hash_file_maps.put("Android CupCake", "https://image.freepik.com/free-photo/graphic-designers-meeting_1170-2002.jpg");
        Hash_file_maps.put("Android Donut", "https://image.freepik.com/free-photo/happy-middle-eastern-call-center-operator-smiling-showing-thumbs-up-office_97712-396.jpg");
        Hash_file_maps.put("Android Eclair", "https://image.freepik.com/free-photo/woman-student-posing-with-computer-while-studying-it-room_73503-1264.jpg");
        Hash_file_maps.put("Android Froyo", "https://image.freepik.com/free-photo/teens-holding-text-boxes_53876-90853.jpg");
        Hash_file_maps.put("Android GingerBread", "https://image.freepik.com/free-photo/colleagues-giving-fist-bump_53876-64857.jpg");

        for(String name : Hash_file_maps.keySet()){


           /* TextSliderView textSliderView = new TextSliderView(School_Home_Activity.this);
            textSliderView
                    .description("")
                    .image(Hash_file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);
            sliderLayout.addSlider(textSliderView);*/

            DefaultSliderView defaultSliderView = new DefaultSliderView(CustomerDashboard.this);
            defaultSliderView .image(Hash_file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);



            sliderLayout.addSlider(defaultSliderView);

        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(this);
        //end slider images

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finishAffinity();
        }
    }


     @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customer_dashboard, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            edit.putBoolean("savePassword", false);
            edit.commit();
            startActivity(new Intent(CustomerDashboard.this,Loginactivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.calllock_nav) {

            startActivity(new Intent(CustomerDashboard.this,Customer_Single_call.class).putExtra("id",userId));
            // Handle the camera action
        } else if (id == R.id.pay_nav) {
            startActivity(new Intent(CustomerDashboard.this,PayNext.class));

        } else if (id == R.id.reminder_nav) {

        } else if (id == R.id.video_nav) {
            startActivity(new Intent(CustomerDashboard.this, VideoListActivity.class));

        } else if (id == R.id.broucher_nav) {
            startActivity(new Intent(CustomerDashboard.this, Scanner.class));

        } else if (id == R.id.nav_share) {
            Intent i = new Intent(android.content.Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Customer service");
            i.putExtra(android.content.Intent.EXTRA_TEXT, "extra text that you want to put");
            startActivity(Intent.createChooser(i, "Share via"));

        } else if (id == R.id.nav_about) {

            startActivity(new Intent(CustomerDashboard.this,ServiceList.class));
            //Toast.makeText(appController, "hello", Toast.LENGTH_SHORT).show();

        }else  if(id==R.id.qutaction)
        {
            startActivity(new Intent(CustomerDashboard.this,QutactionMain.class));
        }
        else  if(id==R.id.pp)
        {
            startActivity(new Intent(CustomerDashboard.this,PP.class));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //for slider images
    @Override
    protected void onStop() {

        sliderLayout.stopAutoCycle();

        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

        // Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    //end slider images



    public void add_query(View view)
    {
        startActivity(new Intent(CustomerDashboard.this, AddCallLock.class));
    }


    public void view_query(View view)
    {

        startActivity(new Intent(getApplicationContext(),View_Customer_Query.class));
    }

    public void call_locks(View view) {

        startActivity(new Intent(CustomerDashboard.this,Customer_Single_call.class).putExtra("id",userId));


    }

    public void pay(View view) {
        startActivity(new Intent(CustomerDashboard.this,PayNext.class));
    }

    public void service_package(View view) {
        startActivity(new Intent(CustomerDashboard.this,ServiceList.class));
    }

    public void reminder(View view) {
    }

    public void video(View view) {
        startActivity(new Intent(CustomerDashboard.this, VideoListActivity.class));
    }

    public void brouncher(View view) {
        startActivity(new Intent(CustomerDashboard.this, Scanner.class));
    }

    public void company_quotation(View view) {
        startActivity(new Intent(CustomerDashboard.this,QutactionMain.class));
    }


}



