package com.example.currencyconverter.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.currencyconverter.Network_Package.AppController;
import com.example.currencyconverter.R;
import com.example.currencyconverter.adapter.History_Adapter;
import com.example.currencyconverter.modal.Country_Modal;
import com.example.currencyconverter.modal.Data;
import com.example.currencyconverter.modal.History;
import com.example.currencyconverter.room_db_package.repository.NoteRepository;
import com.example.menu_library.FButton;
import com.example.menu_library.animation.GuillotineAnimation;
import com.example.menu_library.interfaces.GuillotineListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private static final long RIPPLE_DURATION = 100;
    Toolbar toolbar;
    FrameLayout root;
    View contentHamburger;
    LinearLayout first_selection;
    LinearLayout clear_history, home,Abouts;
    History_Adapter adapter;
    ArrayList<History> history_list = new ArrayList<>();
    ImageView image1, image2;
    TextView name1, name2, hitory_title;
    List<Country_Modal> country_list = new ArrayList<>();
    String value = "";
    TextView result_box;
    FButton convert_button;
    GuillotineAnimation aniMenu;
    private RecyclerView history_rv;
    private EditText input_box;
    private AppController appController;
    private NoteRepository noteRepository;
    private String country_code_2lett_first;
    private String country_code_2lett_second;
    private boolean isGuillotineOpened;

    private InterstitialAd mInterstitialAd;
    private AdView adView;

    public static byte[] getPictureByteOfArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        System.out.println("called");

        initViews();
        fetch_History_Record();
        setMenus();
        called_API(false);
        setconvertButton_Listener();

        loadInterStitialsAd();
        loadBannerAdAd();




    }



    private void setconvertButton_Listener() {

        convert_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!isNetworkAvailable(getApplicationContext())) {
                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.root), "Please check your internet connection!", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else
                    called_API(true);


            }
        });
    }

    public boolean isNetworkAvailable(Context context)//check internet of device
    {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private void called_API(boolean value) {

        input_box.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {
                    System.out.println("text_lenght" + s.length());
                    convert_Currency_API_Called(name1.getText().toString(), name2.getText().toString());


                }
            }
        });


        //System.out.println("data_else_called");


        if (value)
            convert_Currency_API_Called(name1.getText().toString(), name2.getText().toString());
        // }
    }

    @Override
    protected void onResume() {
        if(adView!=null)
            adView.resume();
        super.onResume();



        //set value from Show Country Class
        if (getIntent().getStringExtra("data") != null) {

            System.out.println("name1" + Data.name1);
            System.out.println("name2" + Data.name2);
            System.out.println("country_code_2lett_first" + Data.country_code_2lett_first);
            System.out.println("country_code_2lett_second" + Data.country_code_2lett_first);


            if (getIntent().getStringExtra("value").equals("first")) {


                if (Data.name1 != null) {
                    name1.setText(Data.name1);

                    Glide.
                            with(getApplicationContext()).
                            load(Data.image1).
                            thumbnail(0.01f).
                            into(image1);

                }

                if (Data.name2 != null) {
                    name2.setText(Data.name2);

                    Glide.
                            with(getApplicationContext()).
                            load(Data.image2).
                            thumbnail(0.01f).
                            into(image2);
                }


            }
            if (getIntent().getStringExtra("value").equals("second")) {


                if (Data.name1 != null) {
                    name1.setText(Data.name1);

                    Glide.
                            with(getApplicationContext()).
                            load(Data.image1).
                            thumbnail(0.01f).
                            into(image1);

                }

                if (Data.name2 != null) {
                    name2.setText(Data.name2);
                    Glide.
                            with(getApplicationContext()).
                            load(Data.image2).
                            thumbnail(0.01f).
                            into(image2);
                }
            }

        }
        //end

        System.out.println("on_resume_" + Data.country_code_2lett_first);
    }

    private void setMenus() {
        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.menu_layout, null);
        root.addView(guillotineMenu);

        clear_history = guillotineMenu.findViewById(R.id.clear_history);
        Abouts = guillotineMenu.findViewById(R.id.about_us);

        home = guillotineMenu.findViewById(R.id.home);
        home.setBackgroundResource(R.color.selected);
        clear_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear_history();
            }
        });

        Abouts.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this,AboutUs.class));
                    }
                }
        );

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


        guillotineMenu.findViewById(R.id.home).setBackgroundResource(R.color.selected);

    }

    private void initViews() {


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        noteRepository = new NoteRepository(getApplicationContext());

        toolbar = findViewById(R.id.toolbar);
        root = findViewById(R.id.root);
        contentHamburger = findViewById(R.id.content_hamburger);
        appController = (AppController) getApplicationContext();


        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);

        input_box = findViewById(R.id.input_box);
        result_box = findViewById(R.id.result_box);
        convert_button = findViewById(R.id.convert_button);
        hitory_title = findViewById(R.id.hitory_title);

        history_rv = findViewById(R.id.history_rv);
        adapter = new History_Adapter(this, history_list);
        history_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        history_rv.setAdapter(adapter);

        adView = findViewById(R.id.adView);


    }

    //adds

    private void loadInterStitialsAd() {
        mInterstitialAd = new InterstitialAd(this);
        //mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712"); //test adid
        mInterstitialAd.setAdUnitId("ca-app-pub-3701953680756708/3487427448"); //live adid

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

                Toast.makeText(MainActivity.this, errorCode+" code", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onPause() {

        if(adView != null)
            adView.pause();

        super.onPause();

    }



    @Override
    protected void onDestroy() {
        if(adView!=null)
            adView.destroy();
        super.onDestroy();
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
                Toast.makeText(MainActivity.this, errorCode+" Banner code", Toast.LENGTH_SHORT).show();

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



    public void select_country_first(View view) {
        startActivity(new Intent(getApplicationContext(), Show_Country_List.class).putExtra("value", "first"));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
    }

    public void select_country_second(View view) {
        startActivity(new Intent(getApplicationContext(), Show_Country_List.class).putExtra("value", "second"));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);

    }

    public void convert_Currency_API_Called(final String name1, final String name2) {

        System.out.println("calledddd" + name1 + "  " + name2);

        Request request = new Request.Builder().
                url("https://free.currconv.com/api/v7/convert?q=" + name1 + "_" + name2 + "&compact=ultra&apiKey=2464ad7b9dbb248f2661").
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
                    System.out.println("response" + mainjson);


                    if (MainActivity.this != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    System.out.println("result_Kaif" + mainjson.getString(name1 + "_" + name2));

                                    setResult(mainjson.getString(name1 + "_" + name2));


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setResult(String value) {

        DecimalFormat df = new DecimalFormat("#.####");

        if (input_box.getText().toString().isEmpty()) {
            Double result = Double.parseDouble(value);
            result_box.setText(String.valueOf(df.format(1 * result)));
        } else {
            float input = Float.parseFloat(input_box.getText().toString());
            Double result = Double.parseDouble(value);
            result_box.setText(String.valueOf(df.format(input * result)));

        }

        insert_Record_into_History();

    }

    private void insert_Record_into_History() {

        int position_from = -1;
        int position_to = -1;
        String from_country_name = "";
        String to_country_name = "";

        System.out.println("first" + Data.country_code_2lett_first);
        System.out.println("second" + Data.country_code_2lett_second);


        //get "from" country position
        for (int i = 0; i < Data.country_code1.length; i++) {
            if (Data.country_code_2lett_first.equalsIgnoreCase(Data.country_code1[i])) {
                position_from = i;
                break;
            }

        }

        //get "to" country position
        for (int i = 0; i < Data.country_code1.length; i++) {


            if (Data.country_code_2lett_second.equalsIgnoreCase(Data.country_code1[i])) {
                position_to = i;
                break;
            }

        }


        if (position_from != -1)
            from_country_name = Data.country_name[position_from];
        else
            from_country_name = Data.country_name[3];


        if (position_to != -1)
            to_country_name = Data.country_name[position_to];
        else
            to_country_name = Data.country_name[3];

        System.out.println("from_country_name" + from_country_name + " " + position_from);
        System.out.println("to_country_name" + to_country_name + " " + position_to);

        Bitmap bitmap1 = ((BitmapDrawable) image1.getDrawable()).getBitmap();
        Bitmap bitmap2 = ((BitmapDrawable) image2.getDrawable()).getBitmap();
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, baos1);
        bitmap2.compress(Bitmap.CompressFormat.PNG, 100, baos2);

        final byte[] first_imageInByte = baos1.toByteArray();
        final byte[] second_imageInByte = baos2.toByteArray();


        if (MainActivity.this != null) {
            final String finalFrom_country_name = from_country_name;
            final String finalTo_country_name = to_country_name;
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    check_Duplicate_Hisotory_Record("from", finalFrom_country_name, Data.country_code_2lett_first, first_imageInByte);


                    check_Duplicate_Hisotory_Record("to", finalTo_country_name, Data.country_code_2lett_second, second_imageInByte);


                    fetch_History_Record();


                }
            });
        }


    }

    public void setHistory_Title(final List<History> notes) {
        if (MainActivity.this != null) {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (notes.size() > 0)
                        hitory_title.setVisibility(View.VISIBLE);
                    else
                        hitory_title.setVisibility(View.GONE);
                }
            });
        }

    }


    private void fetch_History_Record() {
        noteRepository.getALL_History_Record().observe(this, new Observer<List<History>>() {
            @Override
            public void onChanged(@Nullable final List<History> notes) {

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {

                        setHistory_Title(notes);

                        if (history_list.size() != 0)
                            history_list.clear();


                        for (int i = 0; i < notes.size(); i++) {
                            history_list.add(new History(notes.get(i).getCountry_name(), notes.get(i).getCountry_code2_letter(), notes.get(i).getCountry_code3_letter(), notes.get(i).getCountry_image()));

                        }

                        if (MainActivity.this != null) {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    System.out.println("sallu" + history_list.size());
                                    history_rv.getRecycledViewPool().clear();
                                    adapter.notifyDataSetChanged();

                                }
                            });
                        }


                        return null;
                    }
                }.execute();


            }
        });
    }


    public void check_Duplicate_Hisotory_Record(final String value, final String Country_name, final String country_code, final byte[] imageInByte) {
        noteRepository.getTask(country_code).observe(this, new Observer<History>() {
            @Override
            public void onChanged(@Nullable final History notes) {


                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {


                        if (notes == null) {

                            if (value.equals("from"))
                                noteRepository.insert_History_Record(Country_name, country_code, name1.getText().toString(), imageInByte);

                            if (value.equals("to"))
                                noteRepository.insert_History_Record(Country_name, country_code, name2.getText().toString(), imageInByte);


                            System.out.println("record_inserted");
                        } else
                            System.out.println("record_not_inserted");


                        return null;
                    }
                }.execute();

            }
        });
    }

    public void set_Data_From_History_Adapter(String value, String name, byte[] image) {

        if (value.equals("first")) {
            name1.setText(name);

            Glide.
                    with(getApplicationContext()).
                    load(image).
                    thumbnail(0.01f).
                    into(image1);


        }


        if (value.equals("second")) {
            name2.setText(name);
            Glide.
                    with(getApplicationContext()).
                    load(image).
                    thumbnail(0.01f).
                    into(image2);
        }


    }


    public void swap_data(View view) {


        String temp = "", a = name1.getText().toString(), b = name2.getText().toString();

        temp = a;
        a = b;
        b = a;

        name1.setText(b);
        name2.setText(temp);


        String temp2 = "", a2 = Data.country_code_2lett_first, b2 = Data.country_code_2lett_second;

        // Data.country_code_2lett_first = Data.country_code_2lett_second;
        // Data.country_code_2lett_second = Data.country_code_2lett_first;

        temp2 = a2;
        a2 = b2;
        b2 = a2;
        Data.country_code_2lett_first = b2;
        Data.country_code_2lett_second = temp2;


        Bitmap bitmap1 = ((BitmapDrawable) image2.getDrawable()).getBitmap();
        Bitmap bitmap2 = ((BitmapDrawable) image1.getDrawable()).getBitmap();

        image1.setImageBitmap(bitmap1);
        image2.setImageBitmap(bitmap2);

    }


    public void clear_history() {


        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteRepository.Delete_ALL_History_Record();
                startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
                return null;
            }
        }.execute();


    }

    @Override
    public void onBackPressed() {

        System.out.println("check" + !isGuillotineOpened);
        if (!isGuillotineOpened) {
            super.onBackPressed();
        }
        aniMenu.close();
    }
}






