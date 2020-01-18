package com.example.currencyconverter.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.currencyconverter.R;
import com.example.currencyconverter.Viewmodal.Convert_Currency_VM;
import com.example.currencyconverter.adapter.History_Adapter;
import com.example.currencyconverter.adapter.Multiple_Currency_Adapter;
import com.example.currencyconverter.modal.Country_Modal;
import com.example.currencyconverter.modal.Data;
import com.example.currencyconverter.modal.History;
import com.example.currencyconverter.modal.Mul_Currency_Modal;
import com.example.currencyconverter.room_db_package.repository.NoteRepository;
import com.example.menu_library.FButton;
import com.example.menu_library.animation.GuillotineAnimation;
import com.example.menu_library.interfaces.GuillotineListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.hadiidbouk.charts.ChartProgressBar;
import com.hadiidbouk.charts.OnBarClickedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements OnBarClickedListener {

    private static final long RIPPLE_DURATION = 100;

    DecimalFormat df = new DecimalFormat("#.####");

    Toolbar toolbar;
    FrameLayout root;
    LottieAnimationView loading_anim;
    View contentHamburger;
    LinearLayout first_selection;
    LinearLayout clear_history, home;
    History_Adapter adapter;
    Multiple_Currency_Adapter mul_adapter;
    ArrayList<History> history_list = new ArrayList<>();
    ArrayList<Mul_Currency_Modal> mul_cur_list = new ArrayList<>();
    ArrayList<JSONObject> response_list = new ArrayList<JSONObject>();
    ImageView image1, image2, graph_icon;
    TextView name1, name2, hitory_title;
    List<Country_Modal> country_list = new ArrayList<>();
    String value = "";
    TextView result_box;
    FButton convert_button;
    GuillotineAnimation aniMenu;

    //flag data is used if two country data is converting again and again
    //to set data again and again and prevent
    boolean flag;
    SharedPreferences shared = null;
    SharedPreferences.Editor sp = null;
    ArrayList<String> fvrt_list = new ArrayList<>();
    View activity_view;
    private RecyclerView history_rv, multiple_cur_rv;
    private EditText input_box;
    private NoteRepository noteRepository;
    private boolean isGuillotineOpened;
    private ChartProgressBar mChart;
    private Convert_Currency_VM Viewmodel;

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
        setContentView(R.layout.activity);

        activity_view = getWindow().getDecorView().getRootView();


        try {


            initViews();
            prepare_All_Favourite_LIst();
            fetch_History_Record();
            setMenus();
            called_API(false);
            setconvertButton_Listener();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void prepare_All_Favourite_LIst() {

        if (fvrt_list != null)
            fvrt_list.clear();

        Map<String, ?> prefsMap = shared.getAll();
        for (Map.Entry<String, ?> entry : prefsMap.entrySet()) {
            Log.v("SharedPreferences_key", entry.getKey() + ":" +
                    entry.getValue().toString());


            fvrt_list.add(entry.getValue().toString());
        }

        System.out.println("fvrt_list" + fvrt_list.size());

    }

    private void setconvertButton_Listener() {

        convert_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboardFrom(getApplicationContext(), activity_view);


                if (!isNetworkAvailable(getApplicationContext())) {
                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.root), "Please check your internet connection!", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else {


                    called_API(true);
                }


            }
        });
    }

    private void visible_Loading_Animation(boolean value) {
        if (value) {
            loading_anim.setVisibility(View.VISIBLE);
            loading_anim.playAnimation();
        } else {
            loading_anim.setVisibility(View.GONE);
            loading_anim.pauseAnimation();
        }
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


                if (s.length() != 0) {
                    System.out.println("text_lenght" + s.length());

                    Viewmodel.convert_Currency("https://free.currconv.com/api/v7/convert?q=" + name1.getText().toString() + "_" + name2.getText().toString() + "&compact=ultra&apiKey=2464ad7b9dbb248f2661").observe(MainActivity.this, new Observer<JSONObject>() {
                        @Override
                        public void onChanged(JSONObject mainjson) {


                            if (response_list != null)
                                response_list.clear();

                            if (flag == false) {

                                // set_UP_Currency_Graph();

                                convert_Multiple_Currency(name1.getText().toString(), "INR");
                                convert_Multiple_Currency(name1.getText().toString(), "JPY");
                                convert_Multiple_Currency(name1.getText().toString(), "KWD");
                                convert_Multiple_Currency(name1.getText().toString(), "CNY");
                                convert_Multiple_Currency(name1.getText().toString(), "LKR");

                            }

                            try {
                                setResult(mainjson.getString(name1.getText().toString() + "_" + name2.getText().toString()));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }


                    });


                }
            }
        });


        //called after button click
        if (value) {
            Viewmodel.convert_Currency("https://free.currconv.com/api/v7/convert?q=" + name1.getText().toString() + "_" + name2.getText().toString() + "&compact=ultra&apiKey=2464ad7b9dbb248f2661").observe(MainActivity.this, new Observer<JSONObject>() {
                @Override
                public void onChanged(JSONObject mainjson) {


                    if (response_list != null)
                        response_list.clear();


                    if (flag == false) {

                        //set_UP_Currency_Graph();

                        convert_Multiple_Currency(name1.getText().toString(), "INR");
                        convert_Multiple_Currency(name1.getText().toString(), "JPY");
                        convert_Multiple_Currency(name1.getText().toString(), "KWD");
                        convert_Multiple_Currency(name1.getText().toString(), "CNY");
                        convert_Multiple_Currency(name1.getText().toString(), "LKR");

                    }

                    try {
                        System.out.println("first_res" + mainjson);

                        setResult(mainjson.getString(name1.getText().toString() + "_" + name2.getText().toString()));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            });


        }

    }

    @Override
    protected void onResume() {
        super.onResume();


        //set value from Show Country Class
        if (getIntent().getStringExtra("data") != null) {

            System.out.println("name1" + Data.name1);
            System.out.println("name2" + Data.name2);
            System.out.println("country_code_2lett_first" + Data.country_code_2lett_first);
            System.out.println("country_code_2lett_second" + Data.country_code_2lett_first);


            //data is coming from Show_Country_list class
            if (getIntent().getStringExtra("value").equals("first")) {

                flag = false;

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

            //data is coming from Show_Country_list class
            if (getIntent().getStringExtra("value").equals("second")) {

                flag = false;

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
        clear_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear_history();
            }
        });

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


    public void rate_me(View view) {

        close_Menu();
        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this, R.style.RoundShapeTheme);

        final AlertDialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        final View dialogLayout = inflater.inflate(R.layout.rate_me_layout, null);
        TextView rate_me = dialogLayout.findViewById(R.id.rate_me_text);
        rate_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                goto_Playstore_for_rateMe();
            }
        });
        dialog.setView(dialogLayout);
        dialog.show();
    }

    public void goto_Playstore_for_rateMe() {

        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    public void share(View view) {

        close_Menu();

        String post_link = "http://play.google.com/store/apps/details?id=" + getPackageName();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBodyText = "Share the app to your friends";
        intent.putExtra(Intent.EXTRA_SUBJECT, "Currency Converter");
        intent.putExtra(Intent.EXTRA_TEXT, post_link);
        startActivity(Intent.createChooser(intent, "Choose sharing method"));
    }

    public void about_us(View view) {

        close_Menu();
        startActivity(new Intent(getApplicationContext(), About_us.class));


    }

    private void initViews() {


        //hide keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Viewmodel = ViewModelProviders.of(this).get(Convert_Currency_VM.class);
        shared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sp = shared.edit();


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        noteRepository = new NoteRepository(getApplicationContext());

        toolbar = findViewById(R.id.toolbar);
        graph_icon = findViewById(R.id.graph_icon);
        loading_anim = findViewById(R.id.loading_anim);
        root = findViewById(R.id.root);
        contentHamburger = findViewById(R.id.content_hamburger);


        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);

        input_box = findViewById(R.id.input_box);
        result_box = findViewById(R.id.result_box);
        convert_button = findViewById(R.id.convert_button);
        hitory_title = findViewById(R.id.hitory_title);

        //set convert multiple currency adapter
        multiple_cur_rv = findViewById(R.id.multiple_cur_rv);
        mul_adapter = new Multiple_Currency_Adapter(mul_cur_list, this);
        multiple_cur_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        multiple_cur_rv.setAdapter(mul_adapter);


        //set history adapter data
        history_rv = findViewById(R.id.history_rv);
        adapter = new History_Adapter(this, history_list, fvrt_list);
        history_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        history_rv.setAdapter(adapter);


        graph_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation rotation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
                graph_icon.startAnimation(rotation);


                gotoGraphActivity();
            }
        });


    }

    private void convert_Multiple_Currency(final String name11, final String name22) {

        visible_Loading_Animation(true);
        hideKeyboardFrom(getApplicationContext(), activity_view);


        Viewmodel.convert_mul_Currency("https://free.currconv.com/api/v7/convert?q=" + name11 + "_" + name22 + "&compact=ultra&apiKey=2464ad7b9dbb248f2661").observe(MainActivity.this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject mainjson) {


                response_list.add(mainjson);
                System.out.println("response_size" + response_list.size());

                visible_Loading_Animation(false);


                if (response_list.size() == 5 && flag == false)
                    set_multiple_currency_Data();


            }


        });

    }

    private void set_multiple_currency_Data() {

        if (mul_cur_list != null)
            mul_cur_list.clear();

        int inr_position = -1;
        int jps_position = -1;
        int kwd_position = -1;
        int cny_position = -1;
        int lkr_position = -1;


        int same_cur_pos = -1;
        boolean same_country = false;

        for (int i = 0; i < response_list.size(); i++) {

            if (response_list.get(i).toString().contains("INR"))
                inr_position = i;
            if (response_list.get(i).toString().contains("JPY"))
                jps_position = i;
            if (response_list.get(i).toString().contains("KWD"))
                kwd_position = i;
            if (response_list.get(i).toString().contains("CNY"))
                cny_position = i;
            if (response_list.get(i).toString().contains("LKR"))
                lkr_position = i;


            if (i == 4) {
                try {

                    Double input = 0.0;
                    Double result = 0.0;
                    String value = "";

                    if (input_box.getText().toString().isEmpty())
                        input = 1.0;
                    else
                        input = Double.parseDouble(input_box.getText().toString());

                    if (!name1.getText().toString().equals("INR"))
                        result = Double.parseDouble(response_list.get(inr_position).getString(name1.getText().toString() + "_" + "INR"));
                    else
                        result = 1.0;
                    value = String.valueOf(df.format(result * input));


                    mul_cur_list.add(new Mul_Currency_Modal(
                            "https://www.countryflags.io/IN/flat/32.png",
                            "India",
                            "INR",
                            value.equals("1") ? "1       " : value
                    ));

                    if (!name1.getText().toString().equals("JPY"))
                        result = Double.parseDouble(response_list.get(jps_position).getString(name1.getText().toString() + "_" + "JPY"));
                    else
                        result = 1.0;

                    value = String.valueOf(df.format(result * input));

                    mul_cur_list.add(new Mul_Currency_Modal(
                            "https://www.countryflags.io/JP/flat/32.png",
                            "Japan",
                            "JPY",
                            value.equals("1") ? "1       " : value
                    ));


                    if (!name1.getText().toString().equals("KWD"))
                        result = Double.parseDouble(response_list.get(kwd_position).getString(name1.getText().toString() + "_" + "KWD"));
                    else
                        result = 1.0;

                    value = String.valueOf(df.format(result * input));
                    mul_cur_list.add(new Mul_Currency_Modal("https://www.countryflags.io/KW/flat/32.png",
                            "Kuwait", "KWD",
                            value.equals("1") ? "1       " : value
                    ));


                    if (!name1.getText().toString().equals("CNY"))
                        result = Double.parseDouble(response_list.get(cny_position).getString(name1.getText().toString() + "_" + "CNY"));
                    else
                        result = 1.0;

                    value = String.valueOf(df.format(result * input));

                    mul_cur_list.add(new Mul_Currency_Modal("https://www.countryflags.io/CN/flat/32.png",
                            "China", "CNY",
                            value.equals("1") ? "1       " : value
                    ));

                    if (!name1.getText().toString().equals("LKR"))
                        result = Double.parseDouble(response_list.get(lkr_position).getString(name1.getText().toString() + "_" + "LKR"));
                    else
                        result = 1.0;

                    value = String.valueOf(df.format(result * input));

                    mul_cur_list.add(new Mul_Currency_Modal("https://www.countryflags.io/LK/flat/32.png",
                            "Sri Lanka", "LKR",
                            value.equals("1") ? "1       " : value
                    ));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }


        if (MainActivity.this != null) {

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    mul_adapter.notifyDataSetChanged();

                    flag = true;


                }
            });
        }

    }

    public void select_country_first(View view) {
        startActivity(new Intent(getApplicationContext(), Show_Country_List.class).putExtra("value", "first"));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
    }

    public void select_country_second(View view) {
        startActivity(new Intent(getApplicationContext(), Show_Country_List.class).putExtra("value", "second"));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);

    }

    private void setResult(String value) {


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

        flag = false;


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


        close_Menu();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                Data.country_code_2lett_first = "EU";
                Data.country_code_2lett_second = "US";

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
        } else
            close_Menu();
    }

    @Override
    public void onBarClicked(int index) {
        // Toast.makeText(this, String.valueOf(index), Toast.LENGTH_SHORT).show();
    }

    public void setting(View view) {
        close_Menu();
        startActivity(new Intent(getApplicationContext(), Setting.class));
    }

    public void close_Menu() {
        if (MainActivity.this != null)
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    aniMenu.close();
                }
            });
    }

    public void news(View view) {
        close_Menu();

        startActivity(new Intent(getApplicationContext(), News_List.class));
    }

    public void add_Favourite_Country(String country_name) {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.root), country_name + " has been added to the favourite list.", Snackbar.LENGTH_SHORT);

        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.blue));
        TextView textView = (TextView) snackBarView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(16);
        snackbar.show();


        sp.putString(country_name.substring(0, 4), country_name);
        sp.commit();

        prepare_All_Favourite_LIst();


    }

    public void remove_Fvrt_From_SP(String key) {

        System.out.println("remove_fvrt_" + key);
        sp.remove(key);
        sp.commit();

        prepare_All_Favourite_LIst();
    }

    public void global_currency(View view) {
        startActivity(new Intent(getApplicationContext(), Global_Currency.class));
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);

    }

    public void gotoGraphActivity() {
        startActivity(new Intent(getApplicationContext(), GraphActivity.class));
    }
}






