package com.example.salonproduct.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.salonproduct.Adapter.Categreat_adapter;
import com.example.salonproduct.Adapter.Prodect_Adapter;
import com.example.salonproduct.Adapter.SliderAdapter;
import com.example.salonproduct.Model.Categre_model;
import com.example.salonproduct.Model.HomePresenterImpl;
import com.example.salonproduct.Model.Prodect_Model;
import com.example.salonproduct.R;
import com.example.salonproduct.View.HomeView;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeView {

    HomePresenterImpl homeActivity2;
    int[] img;
    ArrayList<Prodect_Model> prodect_models = new ArrayList<>();
    ArrayList<Categre_model> categre_models = new ArrayList<>();
    Categreat_adapter categreat_adapter;

    boolean doubleBackToExitPressedOnce = false;
    Prodect_Adapter prodect_adapter;

    RecyclerView category_rv, prodect_rv;
    private ViewPager viewPager;
    private SliderAdapter sliderAdapter;

    ScrollView content_main;
    LottieAnimationView lottieAnimationView;
    SwipeRefreshLayout swipe_ref;
    LinearLayout no_internet_layout;
    TextView retry_internet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();

        homeActivity2 = new HomePresenterImpl(this);

        retry_internet_Connection();
        content_main.setVisibility(View.GONE);

        //net is running
        if(isNetworkAvailable(getApplicationContext()))
        {
            lottieAnimationView.setVisibility(View.VISIBLE);
            lottieAnimationView.playAnimation();

            homeActivity2.fetchCategory();
            homeActivity2.fetchProducts();
            homeActivity2.fetchSliderImages();
        }
        else
        {
            content_main.setVisibility(View.GONE);
            no_internet_layout.setVisibility(View.VISIBLE);


        }


    }




    void retry_internet_Connection()
    {

        retry_internet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lottieAnimationView.setVisibility(View.VISIBLE);
                lottieAnimationView.playAnimation();
                no_internet_layout.setVisibility(View.GONE);


                if(isNetworkAvailable(getApplicationContext()))
                {
                    no_internet_layout.setVisibility(View.GONE);

                    lottieAnimationView.setVisibility(View.VISIBLE);
                    lottieAnimationView.playAnimation();

                    homeActivity2.fetchCategory();
                    homeActivity2.fetchProducts();
                    homeActivity2.fetchSliderImages();
                }
                else
                {
                    lottieAnimationView.setVisibility(View.GONE);
                    no_internet_layout.setVisibility(View.VISIBLE);
                    content_main.setVisibility(View.GONE);

                }



            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();





    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setIcon(R.drawable.salon);

        viewPager = (ViewPager) findViewById(R.id.Viewpager);
        category_rv = findViewById(R.id.rv_cat_item);
        prodect_rv = findViewById(R.id.rv_prodect);

        lottieAnimationView = findViewById(R.id.animation_view);
        content_main = findViewById(R.id.content_main);
        swipe_ref = findViewById(R.id.swipe_ref);
        no_internet_layout = findViewById(R.id.no_internet_layout);
        retry_internet = findViewById(R.id.retry_internet);


    }


    public boolean isNetworkAvailable(Context context)//check internet of device
    {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mi_search) {
            startActivity(new Intent(getApplicationContext(),Search_Products_Activity.class));
           // return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_body) {

        } else if (id == R.id.nav_face) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_makeup) {

        } else if (id == R.id.nav_org) {

        } else if (id == R.id.nav_cart) {

        } else if (id == R.id.navigation_locate) {

        } else if (id == R.id.navigation_care) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_login) {
            Intent intent = new Intent(this, logIn.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {
        lottieAnimationView.setVisibility(View.GONE);
        content_main.setVisibility(View.VISIBLE);
    }

    @Override
    public void setDataToCategory_RecylerView(ArrayList<Categre_model> categre_modelsss) {

        if(categre_models!=null)
            categre_models.clear();

        this.categre_models = categre_modelsss;

        System.out.println("categre_models.size"+categre_models.size());
        System.out.println("categre_modelsss.size"+categre_modelsss.size());


        categreat_adapter = new Categreat_adapter(this, categre_models);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        category_rv.setLayoutManager(layoutManager);
        category_rv.setAdapter(categreat_adapter);
        category_rv.setNestedScrollingEnabled(false);

        categreat_adapter.notifyDataSetChanged();

    }

    @Override
    public void setDataToProduct_RecylerView(ArrayList<Prodect_Model> product_modelssss) {

        if(prodect_models!=null)
            prodect_models.clear();

        this.prodect_models = product_modelssss;
        prodect_adapter = new Prodect_Adapter(this,prodect_models);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        prodect_rv.setLayoutManager(gridLayoutManager);
        prodect_rv.setAdapter(prodect_adapter);
        prodect_rv.setNestedScrollingEnabled(false);
        prodect_adapter.notifyDataSetChanged();
    }

    @Override
    public void setDataToSlider_Layout(int[] img) {


        this.img = img;
        sliderAdapter = new SliderAdapter(this, img);
        viewPager.setAdapter(sliderAdapter);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new HomeActivity.SliderTimer(), 250, 2500);




    }

    public void view_all_products(View view) {
      startActivity(new Intent(getApplication(), View_All_Producs_Activity.class));


    }

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            HomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() < img.length - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });


            //swipe_ref.setRefreshing(false);
        }
    }


    @Override
    public void onBackPressed() {

        int i=0;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }  else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                //return;
                finishAffinity();
                i=1;

            }


            this.doubleBackToExitPressedOnce = true;

            if(i!=1)
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }


}

