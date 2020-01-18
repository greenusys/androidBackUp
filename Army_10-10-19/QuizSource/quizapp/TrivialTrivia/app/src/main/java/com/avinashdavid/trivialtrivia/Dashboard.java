package com.avinashdavid.trivialtrivia;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.avinashdavid.trivialtrivia.Adapter.All_Quiz_adapter_Subjects;
import com.avinashdavid.trivialtrivia.Adapter.All_Quiz_adapter_Topic;
import com.avinashdavid.trivialtrivia.Adapter.Dashboard_Quiz_adapter_Name;
import com.avinashdavid.trivialtrivia.Education_Havildar.Education_Havildar;
import com.avinashdavid.trivialtrivia.Model.Name_Wise_Quiz;
import com.avinashdavid.trivialtrivia.Model.Subject_Category_Model;
import com.avinashdavid.trivialtrivia.Model.Topic_Category_Model;
import com.avinashdavid.trivialtrivia.Notification_package.Admit_Card;
import com.avinashdavid.trivialtrivia.Notification_package.All_Rally;
import com.avinashdavid.trivialtrivia.Notification_package.AnswerKey;
import com.avinashdavid.trivialtrivia.Notification_package.Check_Rally;
import com.avinashdavid.trivialtrivia.Notification_package.Latest_Notification;
import com.avinashdavid.trivialtrivia.Notification_package.Result;
import com.avinashdavid.trivialtrivia.Soldier_Clerk.Clerk;
import com.avinashdavid.trivialtrivia.Soldier_GD_Package.Soldier_GD;
import com.avinashdavid.trivialtrivia.Soldier_GD_Package.Soldier_Sample_Paper;
import com.avinashdavid.trivialtrivia.Soldier_Nursing_Package.Soldier_Nursing;
import com.avinashdavid.trivialtrivia.Soldier_Tech_Package.Soldier_Tech;
import com.avinashdavid.trivialtrivia.Soldier_Tradesman.Tradesman;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //indian army preparation
    LinearLayout soldier_gd, soldier_cleark, soldier_tech, soldier_nursing, soldier_tradesman, soldier_havildar;

    //Indian Army Notification
    LinearLayout check_rally, all_rally, latest_notification, admit_card, result,merit;

    //Daily Army QUiz
    LinearLayout maths, gk, english, ca, quiz_category, all_quiz;

    //Current Affair lang
    LinearLayout hindi_lang, english_lang, check_your_eligibility, check_latest_rally;

    Dashboard_Quiz_adapter_Name all_quiz_adapter_name;
    Name_Wise_Quiz name_wise_quiz;
    List<Name_Wise_Quiz> name_wise_quizs_array = new ArrayList<>();

    Toast toast;
    All_Quiz_adapter_Subjects solider_gd_adapter;
    All_Quiz_adapter_Topic all_quiz_adapter_topic;

    Subject_Category_Model all_quiz_model;
    Topic_Category_Model topic_category_model;
    List<Subject_Category_Model> soldire_gd_models = new ArrayList<>();
    List<Topic_Category_Model> topicCategoryModels = new ArrayList<>();
    AppController appController;
    RecyclerView rv;
    ArrayList<String> subject = new ArrayList<String>();
    ArrayList<String> subject_id = new ArrayList<String>();

    Request request;

    static List<String> qualification_list = new ArrayList<String>();
    static List<String> age_list = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dashboard);
        appController = (AppController) getApplicationContext();



        if(!isNetworkAvailable())
        {
            setContentView(R.layout.internet_layout_faied);
            TextView retry=findViewById(R.id.retry);
          //  Toast.makeText(appController, "ellllllll", Toast.LENGTH_SHORT).show();

            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isNetworkAvailable()) {
                        setContentView(R.layout.activity_dashboard);
                        fetch_Quiz_Latest_Name();
                        API();
                    }



                }
            });


        }

        else {
            setContentView(R.layout.activity_dashboard);
//            Toast.makeText(appController, "iffff", Toast.LENGTH_SHORT).show();


            fetch_Quiz_Latest_Name();



            API();


            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);


            //for content activity

            hindi_lang = (LinearLayout) findViewById(R.id.hindi_lang);
            english_lang = (LinearLayout) findViewById(R.id.english_lang);




            check_your_eligibility = (LinearLayout) findViewById(R.id.check_your_eligibility);
            check_your_eligibility.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Dashboard.this, Check_Eligibility.class);
                    startActivity(intent);
                }
            });
            check_latest_rally = (LinearLayout) findViewById(R.id.check_latest_rally);
            check_latest_rally.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Dashboard.this, Check_Rally.class);
                    startActivity(i);
                }
            });






            //indian army preparation
            soldier_gd = (LinearLayout) findViewById(R.id.soldier_gd);
            soldier_cleark = (LinearLayout) findViewById(R.id.soldier_clerk);
            soldier_tech = (LinearLayout) findViewById(R.id.soldier_tech);
            soldier_nursing = (LinearLayout) findViewById(R.id.soldier_nursing);
            soldier_tradesman = (LinearLayout) findViewById(R.id.soldier_tradesmen);
            soldier_havildar = (LinearLayout) findViewById(R.id.education_havildar);


            //soldier_post = (LinearLayout) findViewById(R.id.soldier_post);

            rv = (RecyclerView) findViewById(R.id.rv);

            // appController = (AppController) getApplicationContext();

//        check_rally.setBackgroundColor(Color.parseColor("#000000"));

            //Indian army notification
            check_rally = (LinearLayout) findViewById(R.id.check_rally);
            all_rally = (LinearLayout) findViewById(R.id.all_rally);
            latest_notification = (LinearLayout) findViewById(R.id.latest_notification);
            admit_card = (LinearLayout) findViewById(R.id.admit_card);
            result = (LinearLayout) findViewById(R.id.result);
            merit = (LinearLayout) findViewById(R.id.merit);

            //Daily Army QUiz
       /* maths = (LinearLayout) findViewById(R.id.maths);
        gk = (LinearLayout) findViewById(R.id.gk);
        english = (LinearLayout) findViewById(R.id.english);
        ca = (LinearLayout) findViewById(R.id.ca);
       // quiz_category = (LinearLayout) findViewById(R.id.quiz_category);
        all_quiz = (LinearLayout) findViewById(R.id.allquiz);*/


            getSupportActionBar().setTitle("Home");
            //this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            //Current Affair Language Intents
            hindi_lang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Dashboard.this, Current_Affair_Hindi_English.class);
                    i.putExtra("language", "hindi");

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Dashboard.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("language", "hindi");
                    editor.apply();

                    startActivity(i);
                }
            });

            english_lang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Dashboard.this, Current_Affair_Hindi_English.class);
                    i.putExtra("language", "English");
                    startActivity(i);

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Dashboard.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("language", "English");
                    editor.apply();
                }
            });



            rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);




                }
            });


            all_rally.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    all_rally();
                /*Intent i = new Intent(Dashboard.this, All_Rally.class);
                startActivity(i);*/
                }
            });


            latest_notification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    latest_notifcation();

                /*Intent i = new Intent(Dashboard.this, Latest_Notification.class);
                startActivity(i);*/
                }
            });

            admit_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(Dashboard.this, Admit_Card.class);
                startActivity(i);
                }
            });

            result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    result();
                    /*String url = " https://logicalpaper.co/indian-army-results-declared-check-online/";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);*/

                }
            });

            merit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Dashboard.this, AnswerKey.class);
                    startActivity(i);
                    /*String url = "https://logicalpaper.co/indian-army-gd-answer-key/";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);*/

                }
            });


            //Soldier GD Intents
            soldier_gd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Dashboard.this, Soldier_GD.class);
                    startActivity(i);
                }
            });

            soldier_cleark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Dashboard.this, Clerk.class);
                    startActivity(i);
                }
            });


            soldier_tech.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Dashboard.this, Soldier_Tech.class);
                    startActivity(i);
                }
            });

            soldier_nursing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Dashboard.this, Soldier_Nursing.class);
                    startActivity(i);
                }
            });


            soldier_tradesman.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Dashboard.this, Tradesman.class);
                    startActivity(i);
                }
            });

            soldier_havildar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Dashboard.this, Education_Havildar.class);
                    startActivity(i);
                }
            });


        }//else for internet
    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    private void API() {
        final ProgressDialog pdLoading = new ProgressDialog(Dashboard.this);

        pdLoading.setMessage("Loading...");
       // pdLoading.show();



        Request request = new Request.Builder().url("http://4army.in/indianarmy/androidapi/apifetch_allcheckeligibility.php").build();



        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Dashboard.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();
                        pdLoading.dismiss();
                    }
                });}
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                Log.e("hhh", "onResponse: " + myResponse);
                try {
                    final JSONObject ja = new JSONObject(myResponse);
                    Log.e("aaaa", "onResponse: " + ja);
                    if (ja.has("status")) {
                        Dashboard.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("status");
                                    if (ja.getString("status").equalsIgnoreCase("1")) {

                                        JSONArray message = ja.getJSONArray("message");


                                        Log.e("message","message"+message);

                                        if(qualification_list!=null)
                                            qualification_list.clear();

                                        if(age_list!=null)
                                            age_list.clear();


                                        for (int i = 0; i < message.length(); i++)
                                        {
                                            JSONObject item = message.getJSONObject(i);

                                            Log.d("qualification", item.getString("qualification"));
                                            Log.d("age", item.getString("age"));


                                            qualification_list.add(item.getString("qualification"));
                                            age_list.add(item.getString("age"));



                                        }


                                        System.out.println("kaifasdkf"+qualification_list);
                                        System.out.println("kaifasdkf"+age_list);










                                    }

                                    pdLoading.dismiss();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        return;
                    }
                    else
                    {
                        //if(list!=null)
                        // list.clear();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }














    public void fetch_Quiz_Latest_Name()

    {




        //RequestBody body = new FormBody.Builder().add("id", id).build();

        final Request   request = new Request.Builder().url("http://4army.in/indianarmy/androidapi/apifetch_allquiz.php").build();



        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Dashboard.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();

                    }
                });}
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                Log.e("hhh", "onResponse: " + myResponse);
                try {
                    final JSONObject ja = new JSONObject(myResponse);
                    Log.e("aaaa", "onResponse: " + ja);
                    if (ja.has("status")) {
                        Dashboard.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("status");
                                    if (ja.getString("status").equalsIgnoreCase("0")) {

                                        JSONArray aaa = ja.getJSONArray("message");

                                        Log.e("dsf","sd"+aaa);
                                       for (int i = 0; i < aaa.length(); i++)

                                        {
                                            JSONObject item = aaa.getJSONObject(i);
                                            Log.d("name", item.getString("name"));

                                            name_wise_quiz = new Name_Wise_Quiz(item.getString("name"));
                                            //name_wise_quiz = new Name_Wise_Quiz("More");

                                            name_wise_quizs_array.add(name_wise_quiz);
                                        }



                                        Log.e("original_kaif_1",""+name_wise_quizs_array.size());


                                        List<Name_Wise_Quiz> result = new ArrayList<Name_Wise_Quiz>();
                                        Set<String> titles = new HashSet<String>();


                                        for( Name_Wise_Quiz item : name_wise_quizs_array ) {
                                            if( titles.add( item.getName() )) {
                                                result.add( item );
                                            }
                                        }



                                        name_wise_quiz = new Name_Wise_Quiz("More");
                                        result.add(name_wise_quiz);
                                          // rv.addItemDecoration(new DividerItemDecoration(Dashboard.this, LinearLayoutManager.HORIZONTAL));
                                            all_quiz_adapter_name = new Dashboard_Quiz_adapter_Name(result);
                                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(Dashboard.this, LinearLayoutManager.HORIZONTAL, false);
                                            rv.setLayoutManager(horizontalLayoutManager);
                                            rv.setAdapter(all_quiz_adapter_name);








                                    }
                                    else
                                    {
                                        /*Toast.makeText(appController, "No List Found", Toast.LENGTH_SHORT).show();
                                        topicCategoryModels.clear();
                                        rv_gd.setAdapter(all_quiz_adapter_topic);
                                        all_quiz_adapter_topic.notifyDataSetChanged();
                                        // Intent intent=new Intent(Login.this, Login.class);
                                        //startActivity(intent);*/
                                    }
                                    //pdLoading.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        return;
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



    }
















    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        int i = 0;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                //return;
                finishAffinity();
                i = 1;

            }

            this.doubleBackToExitPressedOnce = true;

            if (i != 1)
            Toast.makeText(appController, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.share_app) {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareBodyText = "Your shearing message goes here";
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject/Title");
            intent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(intent, "Choose sharing method"));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();






        if(id==R.id.rally){

            Intent intent = new Intent(this,Check_Rally.class);
            startActivity(intent);
        }

        if(id==R.id.check_eligibility){

            Intent intent = new Intent(this,Check_Eligibility.class);
            startActivity(intent);
        }
        if(id==R.id.all_quiz){

            Intent intent = new Intent(this,all_quiz_category.class);
            startActivity(intent);
        }

        if(id==R.id.modal_papers){

            Intent i = new Intent(Dashboard.this,Soldier_Sample_Paper.class);
            startActivity(i );
        }
        if(id==R.id.current_affair){

            Intent i = new Intent(Dashboard.this, Current_Affair_Hindi_English.class);
            i.putExtra("language", "hindi");
            startActivity(i);
        }

        if(id==R.id.latest_rally){

            Intent intent = new Intent(this,Check_Rally.class);
           startActivity(intent);
        }









        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void all_rally()
    {
        Intent i = new Intent(Dashboard.this, All_Rally.class);
        startActivity(i);

    }

    public void latest_notifcation()
    {
        Intent i = new Intent(Dashboard.this, Latest_Notification.class);
        startActivity(i);

    }


    public void result() {
        Intent i = new Intent(Dashboard.this, Result.class);
        startActivity(i);

    }

}



