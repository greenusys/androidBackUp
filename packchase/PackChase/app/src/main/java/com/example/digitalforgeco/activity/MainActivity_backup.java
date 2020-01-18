package com.example.digitalforgeco.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.digitalforgeco.Network_Package.AppController;
import com.example.digitalforgeco.R;
import com.example.digitalforgeco.adapter.Tracking_Result_Adapter;
import com.example.digitalforgeco.modal.Courier_Modal;
import com.example.digitalforgeco.modal.Tracking_Modal;
import com.example.menu_library.FButton;
import com.example.menu_library.animation.GuillotineAnimation;
import com.example.menu_library.interfaces.GuillotineListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity_backup extends AppCompatActivity {

    private static final long RIPPLE_DURATION = 100;
    Toolbar toolbar;
    TextView order_id_title;
    FrameLayout root;
    View contentHamburger;
    GuillotineAnimation aniMenu;
    FButton track;
    EditText tracking_input;
    LinearLayout four_box_layout;
    Tracking_Result_Adapter tracking_result_adapter;
    ArrayList<Tracking_Modal> tracking_list = new ArrayList<>();
    ArrayList<Tracking_Modal> final_tracking_list = new ArrayList<>();
    ArrayList<String> json_res_list = new ArrayList<>();
    ArrayList<String> code_list = new ArrayList<>();
    ArrayList<String> output_code_list = new ArrayList<>();
    ArrayList<Courier_Modal> courier_list = new ArrayList<>();
    TextView shipped_via, status, weight, expected_date;
    LottieAnimationView loading_anim;
    LinearLayout tracking_data_layout, no_shipment_layout, no_internet_layout;
    private boolean isGuillotineOpened;
    private RecyclerView data_rv, courier_list_rv;
    private AppController appController;
    private boolean data_exist;
    private boolean data_find = false;

    private InterstitialAd mInterstitialAd;
    private AdView adView;

    public static void hideKeyboardFrom(Context context, View view) {

        try {

            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            initViews();
            setMenus();
            set_Button_Listener();
            share_Links();
            //loadInterStitialsAd();
            //loadBannerAdAd();

            Intent intent = getIntent();
            if (intent != null && intent.getData() != null) {

                Uri uri = intent.getData();


                String track_no = uri.getQueryParameter("track_no");

                System.out.println("track_number" + track_no);

                View rootView = getWindow().getDecorView().getRootView();
                hideKeyboardFrom(getApplicationContext(), rootView);
                call_Api(track_no);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void share_Links() {
        order_id_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set up URI data
                Uri builtUri = Uri.parse("https://areuloved.com/")
                        .buildUpon()
                        .path("redirect.php")
                        .appendQueryParameter("track_no", tracking_input.getText().toString())
                        .build();
                try {
                    URL url = new URL(builtUri.toString());


                    System.out.println("me_link" + url.toString());
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    //String shareBodyText = "Your shearing message goes here";
                    intent.putExtra(Intent.EXTRA_SUBJECT, "PackChase ");
                    intent.putExtra(Intent.EXTRA_TEXT, url.toString());
                    startActivity(Intent.createChooser(intent, "Choose sharing method"));


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private void loadInterStitialsAd() {
        mInterstitialAd = new InterstitialAd(this);
        //mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712"); //test adid
        mInterstitialAd.setAdUnitId("ca-app-pub-3701953680756708/2892052145"); //live adid

        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.

                if (mInterstitialAd != null)
                    mInterstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.

                Toast.makeText(MainActivity_backup.this, errorCode + " code", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MainActivity_backup.this, errorCode + " Banner code", Toast.LENGTH_SHORT).show();

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

    @Override
    protected void onPause() {
        if (adView != null)
            adView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (adView != null)
            adView.resume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (adView != null)
            adView.destroy();
        super.onDestroy();
    }

    public boolean isNetworkAvailable(Context context)//check internet of device
    {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private void set_Button_Listener() {
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tracking_no = tracking_input.getText().toString();

                if (!isNetworkAvailable(getApplicationContext())) {
                    visible_no_internet_layout();
                } else {
                    if (tracking_no.length() > 0) {

                        View rootView = getWindow().getDecorView().getRootView();
                        hideKeyboardFrom(getApplicationContext(), rootView);

                        call_Api(tracking_no);
                    } else {
                        Snackbar snackbar = Snackbar.make(findViewById(R.id.root), "Please Enter Tracking Number", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                }


            }
        });
    }

    private void call_Api(String tracking_no) {
        loading_anim.playAnimation();
        loading_anim.setVisibility(View.VISIBLE);
        fetch_courier_code(tracking_no);
    }


    private void initViews() {

        code_list.add("fedex");
        code_list.add("ups");
        code_list.add("usps");
        code_list.add("tnt");
        code_list.add("dhl");


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }


        toolbar = findViewById(R.id.toolbar);
        four_box_layout = findViewById(R.id.four_box_layout);


        tracking_data_layout = findViewById(R.id.tracking_data_layout);
        no_shipment_layout = findViewById(R.id.no_shipment_layout);
        no_internet_layout = findViewById(R.id.no_internet_layout);

        adView = findViewById(R.id.adView);
        loading_anim = findViewById(R.id.loading_anim);
        shipped_via = findViewById(R.id.shipped_via);
        status = findViewById(R.id.status);
        weight = findViewById(R.id.weight);
        expected_date = findViewById(R.id.expected_date);

        order_id_title = findViewById(R.id.order_id_title);
        appController = (AppController) getApplicationContext();
        root = findViewById(R.id.root);
        contentHamburger = findViewById(R.id.title_image);


       ImageView title_image= findViewById(R.id.title_image);
        title_image.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_menu_90));


        track = findViewById(R.id.track);
        tracking_input = findViewById(R.id.tracking_input);


        //courier_list_rv = findViewById(R.id.courier_list_rv);
        data_rv = findViewById(R.id.tracking_rv);

        tracking_result_adapter = new Tracking_Result_Adapter(tracking_list);

        data_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
       // courier_list_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));


        data_rv.setAdapter(tracking_result_adapter);
       // courier_list_rv.setAdapter(courier_list_adapter);


    }


    //fetch courier list
    public void fetch_courier_code(final String tracking_number) {

        System.out.println("1---fetch_courier_code");

        //String requestData="{\"tracking_number\":\"6203517891\"}";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tracking_number", tracking_number);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //String requestData="{\"tracking_number\":"\+tracking_number+"\}";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, String.valueOf(jsonObject));


        Request request = new Request.Builder().
                url("https://api.trackingmore.com/v2/carriers/detect").
                addHeader("Content-Type", "application/json").
                addHeader("Trackingmore-Api-Key", "de1ba7aa-a0e7-49d4-8f60-8a9010002907").
                post(body).
                build();


        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                try {

                    if (courier_list != null)
                        courier_list.clear();

                    final JSONObject mainjson = new JSONObject(myResponse);
                    System.out.println("response" + mainjson);
                    JSONObject meta = mainjson.getJSONObject("meta");


                    //for success
                    if (meta.getString("code").equals("200")) {

                        JSONArray data = mainjson.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            courier_list.add(new Courier_Modal(item.getString("name"), item.getString("code")));

                        }

                        if (MainActivity_backup.this != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    create_tracking_api();


                                }
                            });
                        }


                    } else {


                        if (MainActivity_backup.this != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    //clear RV data,
                                    // gone 4 box layout
                                    // ,gone animation,
                                    // gone tracking title
                                    clear_data_and_RV_Data("from_courier_code");


                                    //gone tracking_layout,
                                    // gone no_internet_layout,
                                    // visible no_shipment_found layout
                                    gone_tracking_data_layout("from_courier_code");


                                }
                            });
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //only for create tracking api
    public synchronized void create_tracking_api() {


        if (courier_list.size() > 0) {

            for (int i = 0; i < courier_list.size(); i++) {


                System.out.println("2----create_tracking_api_called_" + courier_list.get(i).getCode());
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("tracking_number", tracking_input.getText().toString());
                    jsonObject.put("carrier_code", courier_list.get(i).getCode());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, String.valueOf(jsonObject));


                Request request = new Request.Builder().
                        url("https://api.trackingmore.com/v2/trackings/post").
                        addHeader("Content-Type", "application/json").
                        addHeader("Trackingmore-Api-Key", "de1ba7aa-a0e7-49d4-8f60-8a9010002907").
                        post(body).
                        build();


                appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println("Errorrrrr " + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String myResponse = response.body().string();
                        try {


                            final JSONObject mainjson = new JSONObject(myResponse);
                            System.out.println("response_second" + mainjson);

                            //fetch_Tracking_Data(courier_list.get(finalI).getName(), courier_list.get(finalI).getCode(), tracking_input.getText().toString());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


            }
        }

        fetch_Tracking_Data();
    }


    //only for fetch tracking details
    public synchronized void fetch_Tracking_Data() {

        System.out.println("couries_size" + courier_list.size());

        for (int k = 0; k < courier_list.size(); k++)
            System.out.println("couries" + courier_list.get(k).getCode());

        if (courier_list.size() > 0) {

            for (int i = 0; i < courier_list.size(); i++) {

                // System.out.println("final_loop_run");


                System.out.println("3----fetch_Tracking_Data_api_called_" + courier_list.get(i).getCode());


                Request request = new Request.Builder().
                        url("https://api.trackingmore.com/v2/trackings/" + courier_list.get(i).getCode() + "/" + tracking_input.getText().toString()).
                        addHeader("Content-Type", "application/json").
                        addHeader("Trackingmore-Api-Key", "de1ba7aa-a0e7-49d4-8f60-8a9010002907").
                        build();


                final int finalI = i;
                appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println("Errorrrrr " + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String myResponse = response.body().string();

                        //System.out.println("kaif"+myResponse);
                        json_res_list.add(myResponse);

                        // System.out.println("katrina_"+finalI+json_res_list);


                        if (finalI == courier_list.size() - 1) {
                            System.out.println("json_list" + json_res_list.size() + " " + finalI);

                            if (tracking_list.size() > 0)
                                tracking_list.clear();

                            set_up_Data(json_res_list);
                        }

                    }
                });


            }
        }


    }

    private void set_up_Data(ArrayList<String> json_res_list2) {

        System.out.println("result_size" + json_res_list2.size());
        System.out.println("result" + json_res_list2);

        ArrayList<String> output_code_list = get_code_name_from_Json_List(courier_list);
        System.out.println("output_code_lis" + output_code_list);//print tracking codes


        int json_list_position = get_json_list_position(code_list, output_code_list);

        System.out.println("kaif_position" + json_list_position);


        for (int i = 0; i < json_res_list2.size(); i++) {

            System.out.println("loop_run");
            try {
                JSONObject mainjson = null;

                if (json_list_position != -1)
                    mainjson = new JSONObject(json_res_list2.get(i));
                else {
                    mainjson = new JSONObject(json_res_list2.get(json_list_position));
                    i = json_res_list2.size();
                }


                JSONObject meta = mainjson.getJSONObject("meta");

                System.out.println("mainJson" + i + mainjson);


                if (meta.getString("code").equals("200")) {


                    JSONObject data = mainjson.getJSONObject("data");
                    JSONObject origin_info = data.getJSONObject("origin_info");

                    JSONArray trackinfo = null;

                    if (origin_info.getString("trackinfo") != "null") {
                        trackinfo = origin_info.getJSONArray("trackinfo");

                        for (int k = trackinfo.length() - 1; k >= 0; k--) {
                            JSONObject item = trackinfo.getJSONObject(k);
                            tracking_list.add(new Tracking_Modal(item.getString("Date"), item.getString("checkpoint_status"), item.getString("StatusDescription")));

                        }//track info loop end

                        if (tracking_list.size() > 0) {


                            final String status2 = data.getString("status");
                            final String weight2 = data.getString("weight");
                            final String expected_date2 = data.getString("created_at");
                            final String date[] = expected_date2.split("T");
                            final String shipped_via2 = courier_list.get(i).getName();


                            if (MainActivity_backup.this != null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        order_id_title.setText("ORDER TRACKING:\n" + tracking_input.getText().toString());
                                        order_id_title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.share_icon, 0);
                                        status.setText(status2);
                                        weight.setText(weight2);
                                        shipped_via.setText(shipped_via2);
                                        expected_date.setText(getDate(date[0]));

                                        //insert into history

                                    }
                                });
                            }


                            visible_tracking_data_layout();


                            json_res_list.clear();//cause new data will be stored
                            break;
                        }


                    }//for track info
                    else {

                        json_res_list.clear();//cause new data will be stored
                        System.out.println("no_tracking_record_found_and_size_finallist" + tracking_list.size());

                        //clear RV data,
                        // gone 4 box layout
                        // ,gone animation,
                        // gone tracking title
                        clear_data_and_RV_Data("from_fetch_tracking_no_tracking_found");


                        //gone tracking_layout,
                        // gone no_internet_layout,
                        // visible no_shipment_found layout
                        gone_tracking_data_layout("from_fetch_tracking_no_tracking_found");
                    }


                }//if check code stauts
                else {
                    json_res_list.clear();//cause new data will be stored
                    clear_data_and_RV_Data("from_fetch_tracking_status_unsuccess");
                    gone_tracking_data_layout("from_fetch_tracking_status_unsuccess");


                }


            } catch (JSONException e) {
                json_res_list.clear();//cause new data will be stored
                clear_data_and_RV_Data("from_exception");
                gone_tracking_data_layout("from_exception");

                e.printStackTrace();
            }


        }


    }

    private int get_json_list_position(ArrayList<String> code_list, ArrayList<String> output_code_list) {

        boolean temp = false;
        int value = -1;


        try {

            for (int i = 0; i < code_list.size(); i++) {
                for (int j = 0; j < output_code_list.size(); j++) {

                    if (code_list.get(i).equalsIgnoreCase(output_code_list.get(j))) {
                        temp = true;

                        System.out.println("check" + i);
                        System.out.println("first" + code_list.get(j));
                        System.out.println("second" + output_code_list.get(j));

                        value = j;

                    }
                    if (temp)
                        break;
                }
                if (temp)
                    break;
            }

        } catch (Exception e) {

        }

        return value;
    }

    private ArrayList<String> get_code_name_from_Json_List(ArrayList<Courier_Modal> json_res_list2) {

        if (output_code_list != null)
            output_code_list.clear();

        for (int i = 0; i < json_res_list2.size(); i++) {

            output_code_list.add(json_res_list2.get(i).getCode());


        }


        return output_code_list;

    }


    private void visible_tracking_data_layout() {
        if (MainActivity_backup.this != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    tracking_result_adapter.notifyDataSetChanged();

                    data_rv.smoothScrollToPosition(1);

                    tracking_data_layout.setVisibility(View.VISIBLE);
                    no_shipment_layout.setVisibility(View.GONE);
                    no_internet_layout.setVisibility(View.GONE);

                    four_box_layout.setVisibility(View.VISIBLE);
                    order_id_title.setVisibility(View.VISIBLE);

                    loading_anim.setVisibility(View.GONE);
                    loading_anim.cancelAnimation();


                    data_exist = true;


                }
            });
        }


    }

    private void visible_no_internet_layout() {
        if (MainActivity_backup.this != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    tracking_data_layout.setVisibility(View.VISIBLE);
                    no_shipment_layout.setVisibility(View.GONE);
                    no_internet_layout.setVisibility(View.VISIBLE);


                }
            });
        }


    }

    private void gone_tracking_data_layout(String from_courier_code) {

        System.out.println("call_gone_tracking" + from_courier_code);
        if (MainActivity_backup.this != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    tracking_data_layout.setVisibility(View.GONE);
                    no_shipment_layout.setVisibility(View.VISIBLE);
                    no_internet_layout.setVisibility(View.GONE);


                }
            });
        }


    }

    private void clear_data_and_RV_Data(String from_courier_code) {

        System.out.println("clear_data_rv_" + from_courier_code);

        if (MainActivity_backup.this != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    if (final_tracking_list != null)
                        final_tracking_list.clear();

                    tracking_result_adapter.notifyDataSetChanged();

                    four_box_layout.setVisibility(View.GONE);
                    order_id_title.setVisibility(View.GONE);


                    loading_anim.setVisibility(View.GONE);
                    loading_anim.cancelAnimation();


                }
            });
        }
    }

    public String getDate(String date) {
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
        Date date3 = null;
        try {
            date3 = inputFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final String outputDateStr = outputFormat.format(date3);

        return outputDateStr;
    }

    private void setMenus() {
        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.menu_layout, null);
        root.addView(guillotineMenu);

        GuillotineAnimation.GuillotineBuilder guillotineBuilder = new GuillotineAnimation.GuillotineBuilder(guillotineMenu,
                guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger);

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
        guillotineBuilder.setStartDelay(RIPPLE_DURATION);
        guillotineBuilder.setActionBarViewForAnimation(toolbar);
        guillotineBuilder.setClosedOnStart(true);

        aniMenu = guillotineBuilder.build();
    }

    @Override
    public void onBackPressed() {

        if (!isGuillotineOpened) {
            super.onBackPressed();
        }
        else
       close_Menu();
    }

    private void close_Menu() {

        if(MainActivity_backup.this!=null)
        {
            MainActivity_backup.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    aniMenu.close();
                }
            });
        }
    }


    public void about_us(View view) {
        close_Menu();
        startActivity(new Intent(getApplicationContext(), About_us.class));
    }

    public void goto_Tracking_List(View view) {
        close_Menu();
        startActivity(new Intent(getApplicationContext(),Tracking_History_List.class));
    }


}
