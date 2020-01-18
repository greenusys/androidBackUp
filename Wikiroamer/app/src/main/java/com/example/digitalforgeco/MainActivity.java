package com.example.digitalforgeco;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.menu_library.animation.GuillotineAnimation;
import com.example.menu_library.interfaces.GuillotineListener;
import com.example.digitalforgeco.data.SessionManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnCameraIdleListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private static final long RIPPLE_DURATION = 100;
    GuillotineAnimation aniMenu;
    private GoogleMap mMap;
    private String country_name = "";
    private String state_name = "";
    private GoogleApiClient mGoogleApiClient;
    private BottomSheetDialog sheetDialog;
    private boolean isGuillotineOpened;
    private SessionManager session;
    View guillotineMenu;

    private HashMap<String, String> user;
    String map_value="";

    LinearLayout std_map,silver_map,retro_map,night_map,aubergine_map,dark_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        setMenus();
        initViews();
        get_Map_Value();


        check_Connectivity();

        load_map();


    }

    private void check_Connectivity() {
        //no internet
        if (!isNetworkAvailable(getApplicationContext()))
            Snackbar.make(findViewById(R.id.coor), "Please Check Your Internet", Snackbar.LENGTH_SHORT).show();

    }

    private void get_Map_Value() {
        session = new SessionManager(getApplicationContext());
        user = session.getMap_Value();
     map_value=user.get(SessionManager.KEY_ID);

    }

    private void initViews() {
        session = new SessionManager(getApplicationContext());

        std_map=guillotineMenu.findViewById(R.id.std_map);
        silver_map=guillotineMenu.findViewById(R.id.silver_map);
        retro_map=guillotineMenu.findViewById(R.id.retro_map);
        night_map=guillotineMenu.findViewById(R.id.night_map);
        dark_map=guillotineMenu.findViewById(R.id.dark_map);
        aubergine_map=guillotineMenu.findViewById(R.id.aubergine_map);



    }

    private void load_map()
    {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        connectClient();
    }


    public void connectClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    public boolean isNetworkAvailable(Context context)//check internet of device
    {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void onMapReady(GoogleMap map) {

        mMap = map;

        set_Map_Style(mMap);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(23.104055045871974, 79.42836485803126), 3.5f));
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnCameraIdleListener(this);

    }

    private void set_Map_Style(GoogleMap mMap) {
        System.out.println("map_value"+map_value);

        if(map_value.equalsIgnoreCase("standard")) {

            std_map.setBackgroundResource(R.color.colorAccentSplash);
        }

        else if(map_value.equalsIgnoreCase("silver")) {

            silver_map.setBackgroundResource(R.color.colorAccentSplash);

            try {

                boolean success = mMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                this, R.raw.silver));

                if (!success) {
                    Log.e("MapsActivityRaw", "Style parsing failed.");
                }
            } catch (Resources.NotFoundException e) {
                Log.e("MapsActivityRaw", "Can't find style.", e);
            }
        }

        else if (map_value.equalsIgnoreCase("retro"))
        {
            retro_map.setBackgroundResource(R.color.colorAccentSplash);

            try {

                boolean success = mMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                this, R.raw.retro));

                if (!success) {
                    Log.e("MapsActivityRaw", "Style parsing failed.");
                }
            } catch (Resources.NotFoundException e) {
                Log.e("MapsActivityRaw", "Can't find style.", e);
            }
        }

        else if (map_value.equalsIgnoreCase("dark"))
        {
            dark_map.setBackgroundResource(R.color.colorAccentSplash);

            try {

                boolean success = mMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                this, R.raw.dark));

                if (!success) {
                    Log.e("MapsActivityRaw", "Style parsing failed.");
                }
            } catch (Resources.NotFoundException e) {
                Log.e("MapsActivityRaw", "Can't find style.", e);
            }
        }

        else if (map_value.equalsIgnoreCase("night"))
        {
            night_map.setBackgroundResource(R.color.colorAccentSplash);

            try {

                boolean success = mMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                this, R.raw.night));

                if (!success) {
                    Log.e("MapsActivityRaw", "Style parsing failed.");
                }
            } catch (Resources.NotFoundException e) {
                Log.e("MapsActivityRaw", "Can't find style.", e);
            }
        }

        else if (map_value.equalsIgnoreCase("aubergine"))
        {
            aubergine_map.setBackgroundResource(R.color.colorAccentSplash);

            try {

                boolean success = mMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                this, R.raw.aubergine));

                if (!success) {
                    Log.e("MapsActivityRaw", "Style parsing failed.");
                }
            } catch (Resources.NotFoundException e) {
                Log.e("MapsActivityRaw", "Can't find style.", e);
            }
        }
    }


    @Override

    public void onMapClick(LatLng point) {

        if (!isNetworkAvailable(getApplicationContext())) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.coor), "Please Check Your Internet Connection", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        System.out.println("lat" + point.latitude);
        System.out.println("long" + point.longitude);
        mMap.clear();

        //fetch address
        try {
            Geocoder geo = new Geocoder(MainActivity.this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(point.latitude, point.longitude, 1);
            if (addresses.isEmpty()) {
                System.out.println("Waiting");

            } else {
                if (addresses.size() > 0) {
                    country_name = addresses.get(0).getCountryName();
                    state_name = addresses.get(0).getAdminArea() + ",";

                    if (country_name == null)
                        country_name = "";

                    if (state_name == null)
                        state_name = "";


                    System.out.println(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());

                    mMap.addMarker(new MarkerOptions().position(point).title(state_name + " " + country_name));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(point));


                    sendURL(country_name);

                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }


    }

    private void sendURL(String country_name) {


        sheetDialog = new BottomSheetDialog(MainActivity.this);
        View sheetView = LayoutInflater.from(MainActivity.this).inflate(R.layout.bottom_sheet, null);
        WebView webView = sheetView.findViewById(R.id.webview);
        webView.setVerticalScrollBarEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://en.wikipedia.org/wiki/" + country_name);
        sheetDialog.setContentView(sheetView);
        sheetDialog.show();


    }


    @Override

    public void onMapLongClick(LatLng point) {

        //   mTapTextView.setText("long pressed, point=" + point);

    }


    @Override

    public void onCameraIdle() {

        //mCameraTextView.setText(mMap.getCameraPosition().toString());

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void setMenus() {

        FrameLayout root;
        View contentHamburger;
        Toolbar toolbar = null;


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        root = findViewById(R.id.coor);
        contentHamburger = findViewById(R.id.content_hamburger);

         guillotineMenu = LayoutInflater.from(this).inflate(R.layout.menu_layout, null);
        root.addView(guillotineMenu);


        GuillotineAnimation.GuillotineBuilder guillotineBuilder = new GuillotineAnimation.GuillotineBuilder(guillotineMenu,
                guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger);
        guillotineBuilder.setStartDelay(RIPPLE_DURATION);
        guillotineBuilder.setActionBarViewForAnimation(toolbar);
        guillotineBuilder.setGuillotineListener(new GuillotineListener() {
            @Override
            public void onGuillotineOpened() {
                isGuillotineOpened = true;

            }

            @Override
            public void onGuillotineClosed() {
                isGuillotineOpened = false;

            }
        });

        guillotineBuilder.setClosedOnStart(true);
        aniMenu = guillotineBuilder.build();
        aniMenu.close();


    }






    public void std_map(View view) {
        session.store_Map_Value("standard");
        startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }

    public void retro_map(View view) {
        session.store_Map_Value("retro");
        startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }

    public void dark_map(View view) {
        session.store_Map_Value("dark");
        startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }

    public void night_map(View view) {
        session.store_Map_Value("night");
        startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }

    public void aubergine_map(View view) {
        session.store_Map_Value("aubergine");
        startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }

    public void silver_map(View view) {
        session.store_Map_Value("silver");
        startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }

    @Override
    public void onBackPressed() {
        if (!isGuillotineOpened) {
            super.onBackPressed();
        }

        aniMenu.close();
    }
}