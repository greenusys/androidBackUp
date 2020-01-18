package com.example.sunrise;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;
import com.example.menu_library.animation.GuillotineAnimation;
import com.example.menu_library.interfaces.GuillotineListener;
import com.example.sunrise.Modal.City_Modal;
import com.example.sunrise.Modal.Country_Modal;
import com.example.sunrise.Modal.State_Modal;
import com.example.sunrise.Modal.SunModel;
import com.example.sunrise.Network_Package.AppController;
import com.example.sunrise.activity.AboutsUs;
import com.example.sunrise.adapter.CityAdapter;
import com.example.sunrise.adapter.CountryAdapter;
import com.example.sunrise.adapter.StateAdapter;
import com.example.sunrise.data.SessionManager;
import com.example.sunrise.widget.CanaroTextView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
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
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity_backup extends AppCompatActivity implements GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnCameraIdleListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
        , AdapterView.OnItemSelectedListener {


    private static final long RIPPLE_DURATION = 100;
    LottieAnimationView anim;
    List<String> list = new ArrayList<String>();
    GuillotineAnimation aniMenu;
    CanaroTextView title, time;
    String lat_long_url = "https://africamedec.com/suntime/latlong_api.php";
    String city_url = "https://mysunsetandsunrise.com/api.php";
    String map_value = "";
    LinearLayout std_map, silver_map, retro_map, night_map, aubergine_map, dark_map;
    List<Country_Modal> country_list = new ArrayList<Country_Modal>();
    List<State_Modal> state_list = new ArrayList<State_Modal>();
    List<City_Modal> city__list = new ArrayList<City_Modal>();
    SunModel sunModel = new SunModel();
    private GoogleMap mMap;
    private AppController appController;
    private String country_name = "";
    private String state_name = "";
    private FusedLocationProviderClient mLocation;
    private GoogleApiClient mGoogleApiClient;
    private MaterialBetterSpinner country, state, city;
    private ArrayAdapter<String> arrayAdapter;
    private View guillotineMenu;
    private boolean isGuillotineOpened;
    private boolean by_spinner;
    private String api_latitude = "", api_longitude = "", api_seach_value = "", api_url = "";
    private SessionManager session;
    private HashMap<String, String> user;
    private Spinner country_sp, state_sp, city_sp;
    private CountryAdapter countryAdapter;
    private StateAdapter stateAdapter;
    private CityAdapter cityAdapter;
    private String country_name_sp;


    private AdView adView;
    private InterstitialAd mInterstitialAd;

    public static LatLng getCityLatitude(Context context, String city) {

        System.out.println("getCityLatitude" + city);
        Geocoder geocoder = new Geocoder(context, context.getResources().getConfiguration().locale);
        List<Address> addresses = null;
        LatLng latLng = null;
        try {
            addresses = geocoder.getFromLocationName(city, 1);
            Address address = addresses.get(0);
            latLng = new LatLng(address.getLatitude(), address.getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return latLng;
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setMenus();
        initViews();
        initSpinners();
        set_Spinner_Listener();
        get_Map_Value();

        loadBannerAdAd();

        loadInterStitialsAd();


    }

    private void loadInterStitialsAd() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3701953680756708/6987998847");

        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.

                if(mInterstitialAd!=null)
                    mInterstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.

                Toast.makeText(appController, errorCode+" code", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
            }
        });
    }

    private void loadBannerAdAd() {
       /* AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");*/
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Toast.makeText(appController, errorCode+" Banner code", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }

    private void set_Spinner_Listener() {


        country_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

                country_name_sp = parent.getItemAtPosition(position).toString();


//                System.out.println("country_id" + country_list.get(position).getCountry_id());

                if (country_list != null && position > 0 && !country_list.get(position).getCountry_id().equals("00"))
                    fetch_States(country_list.get(position).getCountry_id());


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        state_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

                String name = parent.getItemAtPosition(position).toString();

                System.out.println("state_sp_name" + name);

//                System.out.println("country_id" + country_list.get(position).getCountry_id());


                if (state_list != null && !name.equalsIgnoreCase("Please Select State"))
                    fetch_Cities(state_list.get(position).getStates_id());


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        city_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

                String name = parent.getItemAtPosition(position).toString();

                System.out.println("city_sp_name" + name);


                if (city__list != null && !name.equalsIgnoreCase("Please Select City"))
                    by_spinner = true;

                if (position > 0)
                    fetch_Sun_Time(city__list.get(position).getName());
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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


        //set default location
        //USA location
        //    LatLng sydney = new LatLng(39.70059405352709, -101.71103518456222);

        //india location
        // LatLng sydney = new LatLng(23.371260289351373, -79.67974651604891);
        LatLng sydneyt = new LatLng(18.965621185096186, -74.58708219230175);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydneyt));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.70059405352709, -101.71103518456222), 3.5f));

        mMap.setOnMapClickListener(this);

        mMap.setOnMapLongClickListener(this);

        mMap.setOnCameraIdleListener(this);


        System.out.println("test" + city);

    }

    @Override

    public void onMapClick(LatLng point) {


        System.out.println("lat" + point.latitude);
        System.out.println("long" + point.longitude);
        mMap.clear();


        //fetch address
        try {
            Geocoder geo = new Geocoder(MainActivity_backup.this.getApplicationContext(), Locale.getDefault());

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


                    System.out.println("test_map" + addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());

                    mMap.addMarker(new MarkerOptions().position(point).title(state_name + " " + country_name));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(point));


                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }


        api_latitude = String.valueOf(point.latitude);
        api_longitude = String.valueOf(point.longitude);

        System.out.println("test" + api_latitude + api_longitude);

        by_spinner = false;
        fetch_Sun_Time(null);


    }

    private void show_Bottom(final SunModel sunModel) {

        if (MainActivity_backup.this != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (by_spinner == true) {

                        System.out.println("if_runonthi_byspinner_" + by_spinner);

                        mMap.clear();
                        LatLng latlong_city = getCityLatitude(getApplicationContext(), sunModel.getCity());

                        if(latlong_city!=null) {
                            mMap.addMarker(new MarkerOptions().position(latlong_city));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latlong_city));
                        }
                        else
                            System.out.println("latlong_is_null_from_getcity_method");


                    } else
                        System.out.println("else_runonthi_byspinner_" + by_spinner);


                    anim.setVisibility(View.GONE);

                    BottomSheetDialog sheetDialog = new BottomSheetDialog(MainActivity_backup.this);
                    View sheetView = LayoutInflater.from(MainActivity_backup.this).inflate(R.layout.bottom_sheet, null);
                    CanaroTextView sunrise_time = sheetView.findViewById(R.id.sunrise_time);
                    CanaroTextView sunset_time = sheetView.findViewById(R.id.sunset_time);
                    CanaroTextView address = sheetView.findViewById(R.id.address);

                    title.setText(sunModel.getCity());
                    time.setText(sunModel.getUtcTime());


                    sunrise_time.setText(sunModel.getSunrise());
                    sunset_time.setText(sunModel.getSunset());


                    if (by_spinner == true) {

                        address.setText(sunModel.getCity() + "(" + sunModel.getCountry() + ")");
                    } else {

                        if (sunModel.getCountry().equals(""))
                            address.setText(sunModel.getCity());

                        else
                            address.setText(sunModel.getCity() + "(" + sunModel.getCountry() + ")");

                    }

                    // by_spinner = false;


                    sheetDialog.setContentView(sheetView);
                    sheetDialog.show();
                }
            });
        }


    }

    private String format_date(String date) {

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        try {
            dt1 = format1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat format2 = new SimpleDateFormat("EEE,MM,yyyy");
        String finalDay = format2.format(dt1);
        System.out.println("sayed" + finalDay);

        return finalDay;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    protected void onResume() {
        check_Connectivity();
        super.onResume();
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

    @Override
    public void onBackPressed() {
        if (!isGuillotineOpened) {
            super.onBackPressed();
        }

        aniMenu.close();
    }

    public void fetch_Sun_Time(String city_name) {
        anim.setVisibility(View.VISIBLE);
        RequestBody body = null;
        Request request = null;

        System.out.println("fetch_Sun_Time_called");
        System.out.println("by_spinner_value" + by_spinner);


        if (by_spinner == false) {
            body = new FormBody.Builder().
                    add("lat", api_latitude).
                    add("long", api_longitude).
                    build();

            request = new Request.Builder().
                    url(lat_long_url).
                    post(body).
                    build();
        }
        if (by_spinner == true) {

            System.out.println("by_spinner_value" + city_name);
            body = new FormBody.Builder().
                    add("get_city", city_name).
                    build();

            request = new Request.Builder().
                    url(city_url).
                    post(body).
                    build();

        }


        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());
                if (MainActivity_backup.this != null) {
                    MainActivity_backup.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            anim.setVisibility(View.GONE);
                        }
                    });
                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                System.out.println("myResponse" + myResponse);
                try {

                    if (!myResponse.equals("")) {


                        JSONObject mainjson = new JSONObject(myResponse);
                        JSONObject object = mainjson.getJSONObject("astronomy");

                        final String citytest = object.getString("city");
                        final String country = object.getString("country");
                        System.out.println("teset" + citytest);
                        JSONArray data = object.getJSONArray("astronomy");
                        for (int i = 0; i < 1; i++) {
                            JSONObject item = data.getJSONObject(i);
                            SunModel sunModel = new SunModel();
                            sunModel.setCity(citytest);
                            sunModel.setCountry(country);
                            sunModel.setSunrise(item.getString("sunrise"));
                            sunModel.setSunset(item.getString("sunset"));
                            String lat = item.getString("latitude");
                            String lon = item.getString("longitude");
                            sunModel.setLat(lat);
                            sunModel.setLon(lon);


                            String date = "";
                            String date_input[] = item.getString("utcTime").split(":");
                            String date2 = date_input[0];
                            for (int k = 0; k < 10; k++)
                                date = date.concat(String.valueOf(date2.charAt(k)));

                            String result_date = format_date(date);


                            System.out.println("kaif_date" + result_date);
                            sunModel.setUtcTime(result_date);


                            show_Bottom(sunModel);

                        }


                    } else {

                        if (MainActivity_backup.this != null) {
                            MainActivity_backup.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    anim.setVisibility(View.GONE);

                                    Snackbar snackbar = Snackbar.make(findViewById(R.id.coor), "Please enter a valid country!", Snackbar.LENGTH_SHORT);
                                    View snackBarView = snackbar.getView();
                                    snackBarView.setBackgroundColor(Color.RED);
                                    TextView textView = (TextView) snackBarView.findViewById(R.id.snackbar_text);
                                    textView.setTextColor(Color.WHITE);
                                    textView.setTextSize(18);
                                    snackbar.show();

                                }
                            });
                        }


                    }


                } catch (JSONException e) {

                    if (MainActivity_backup.this != null) {
                        MainActivity_backup.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                anim.setVisibility(View.GONE);
                            }
                        });
                    }


                    e.printStackTrace();
                }
            }
        });

    }

    public void std_map(View view) {
        session.store_Map_Value("standard");
        startActivity(new Intent(getApplicationContext(), MainActivity_backup.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }

    public void retro_map(View view) {
        session.store_Map_Value("retro");
        startActivity(new Intent(getApplicationContext(), MainActivity_backup.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }

    public void dark_map(View view) {
        session.store_Map_Value("dark");
        startActivity(new Intent(getApplicationContext(), MainActivity_backup.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }

    public void night_map(View view) {
        session.store_Map_Value("night");
        startActivity(new Intent(getApplicationContext(), MainActivity_backup.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }

    public void aubergine_map(View view) {
        session.store_Map_Value("aubergine");
        startActivity(new Intent(getApplicationContext(), MainActivity_backup.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }

    public void silver_map(View view) {
        session.store_Map_Value("silver");
        startActivity(new Intent(getApplicationContext(), MainActivity_backup.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }
    public void abouts_us(View view) {
        session.store_Map_Value("silver");
        startActivity(new Intent(getApplicationContext(), AboutsUs.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }

    public void fetch_Countries() {


        Request request
                = new Request.Builder().
                url("https://mysunsetandsunrise.com/API/fetchCountry.php").
                build();


        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());
                if (MainActivity_backup.this != null) {
                    MainActivity_backup.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                System.out.println("myResponse" + myResponse);
                try {

                    JSONObject mainjson = new JSONObject(myResponse);

                    if (country_list != null)
                        country_list.clear();

                    if (mainjson.getString("code").equals("1")) {


                        JSONArray data = mainjson.getJSONArray("data");


                        for (int i = 0; i < data.length(); i++) {

                            JSONObject item = data.getJSONObject(i);

                            Country_Modal model = new Country_Modal();
                            model.setCountry_id(item.getString("country_id"));
                            model.setName(item.getString("name"));
                            country_list.add(model);


                        }

                        if (MainActivity_backup.this != null) {
                            MainActivity_backup.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Country_Modal model = new Country_Modal();
                                    model.setName("Please Select Country");
                                    model.setCountry_id("00");
                                    country_list.add(0, model);

                                    country_sp.setAdapter(countryAdapter);
                                    countryAdapter.notifyDataSetChanged();


                                }
                            });
                        }

                    } else {

                        if (MainActivity_backup.this != null) {
                            MainActivity_backup.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                }
                            });
                        }


                    }


                } catch (JSONException e) {

                    if (MainActivity_backup.this != null) {
                        MainActivity_backup.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                anim.setVisibility(View.GONE);
                            }
                        });
                    }


                    e.printStackTrace();
                }
            }
        });

    }

    public void fetch_States(String country_id) {

        System.out.println("state_country_id" + country_id);

        RequestBody body = new FormBody.Builder().
                add("country_id", country_id).
                build();


        Request request
                = new Request.Builder().
                post(body).
                url("https://mysunsetandsunrise.com/API/fetchStates.php").
                build();


        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());
                if (MainActivity_backup.this != null) {
                    MainActivity_backup.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                System.out.println("myResponse" + myResponse);
                try {

                    JSONObject mainjson = new JSONObject(myResponse);

                    if (state_list != null)
                        state_list.clear();

                    if (mainjson.getString("code").equals("1")) {


                        JSONArray data = mainjson.getJSONArray("data");


                        for (int i = 0; i < data.length(); i++) {

                            JSONObject item = data.getJSONObject(i);

                            State_Modal model = new State_Modal();
                            model.setStates_id(item.getString("states_id"));
                            model.setName(item.getString("name"));

                            state_list.add(model);


                        }

                        if (MainActivity_backup.this != null) {
                            MainActivity_backup.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    State_Modal model = new State_Modal();
                                    model.setName("Please Select State");
                                    model.setStates_id("00");
                                    state_list.add(0, model);

                                    state_sp.setAdapter(stateAdapter);
                                    stateAdapter.notifyDataSetChanged();


                                }
                            });
                        }

                    } else {

                        if (MainActivity_backup.this != null) {
                            MainActivity_backup.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                }
                            });
                        }


                    }


                } catch (JSONException e) {

                    if (MainActivity_backup.this != null) {
                        MainActivity_backup.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                anim.setVisibility(View.GONE);
                            }
                        });
                    }


                    e.printStackTrace();
                }
            }
        });

    }

    public void fetch_Cities(String state_id) {

        System.out.println("Citie_state_id" + state_id);

        RequestBody body = new FormBody.Builder().
                add("state_id", state_id).
                build();


        Request request
                = new Request.Builder().
                post(body).
                url("https://mysunsetandsunrise.com/API/fetchCities.php").
                build();


        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());
                if (MainActivity_backup.this != null) {
                    MainActivity_backup.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                System.out.println("myResponse" + myResponse);
                try {

                    JSONObject mainjson = new JSONObject(myResponse);

                    if (city__list != null)
                        city__list.clear();

                    if (mainjson.getString("code").equals("1")) {


                        JSONArray data = mainjson.getJSONArray("data");


                        for (int i = 0; i < data.length(); i++) {

                            JSONObject item = data.getJSONObject(i);

                            City_Modal model = new City_Modal();
                            model.setCities_id(item.getString("cities_id"));
                            model.setName(item.getString("name"));

                            city__list.add(model);


                        }

                        if (MainActivity_backup.this != null) {
                            MainActivity_backup.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    City_Modal model = new City_Modal();
                                    model.setName("Please Select City");
                                    model.setCities_id("00");
                                    city__list.add(0, model);

                                    city_sp.setAdapter(cityAdapter);
                                    cityAdapter.notifyDataSetChanged();


                                }
                            });
                        }

                    } else {

                        if (MainActivity_backup.this != null) {
                            MainActivity_backup.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                }
                            });
                        }


                    }


                } catch (JSONException e) {

                    if (MainActivity_backup.this != null) {
                        MainActivity_backup.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                anim.setVisibility(View.GONE);
                            }
                        });
                    }


                    e.printStackTrace();
                }
            }
        });

    }

    private void get_Map_Value() {
        session = new SessionManager(getApplicationContext());
        user = session.getMap_Value();
        map_value = user.get(SessionManager.KEY_ID);

    }

    private void set_Map_Style(GoogleMap mMap) {
        System.out.println("map_value" + map_value);

        if (map_value.equalsIgnoreCase("standard")) {

            std_map.setBackgroundResource(R.color.colorAccent);
        } else if (map_value.equalsIgnoreCase("silver")) {

            silver_map.setBackgroundResource(R.color.colorAccent);

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
        } else if (map_value.equalsIgnoreCase("retro")) {
            retro_map.setBackgroundResource(R.color.colorAccent);

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
        } else if (map_value.equalsIgnoreCase("dark")) {
            dark_map.setBackgroundResource(R.color.colorAccent);

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
        } else if (map_value.equalsIgnoreCase("night")) {
            night_map.setBackgroundResource(R.color.colorAccent);

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
        } else if (map_value.equalsIgnoreCase("aubergine")) {
            aubergine_map.setBackgroundResource(R.color.colorAccent);

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

    private void check_Connectivity() {
        //no internet
        if (!isNetworkAvailable(getApplicationContext())) {

            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.coor), "Please Check Your Internet Connection", Snackbar.LENGTH_SHORT);

            snackbar.show();
        } else {

            fetch_Countries();

        }
    }

    private void initSpinners() {
        country_sp = findViewById(R.id.country_sp);
        state_sp = findViewById(R.id.state_sp);
        city_sp = findViewById(R.id.city_sp);

        countryAdapter = new CountryAdapter(country_list, getApplicationContext());
        stateAdapter = new StateAdapter(state_list, getApplicationContext());
        cityAdapter = new CityAdapter(city__list, getApplicationContext());


    }

    private void initViews() {
        appController = (AppController) getApplicationContext();
        anim = findViewById(R.id.loading_anim);
        title = findViewById(R.id.title);
        time = findViewById(R.id.time);

        adView = findViewById(R.id.adView);


        std_map = guillotineMenu.findViewById(R.id.std_map);
        silver_map = guillotineMenu.findViewById(R.id.silver_map);
        retro_map = guillotineMenu.findViewById(R.id.retro_map);
        night_map = guillotineMenu.findViewById(R.id.night_map);
        dark_map = guillotineMenu.findViewById(R.id.dark_map);
        aubergine_map = guillotineMenu.findViewById(R.id.aubergine_map);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        connectClient();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int j, long l) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override

    public void onMapLongClick(LatLng point) {

        //   mTapTextView.setText("long pressed, point=" + point);

    }

    @Override

    public void onCameraIdle() {

        //mCameraTextView.setText(mMap.getCameraPosition().toString());

    }

}