package com.greenusys.personal.registrationapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.greenusys.personal.registrationapp.Adapter.Subject_Adapter;
import com.greenusys.personal.registrationapp.Utility.AppController;
import com.greenusys.personal.registrationapp.Utility.RequestBuilder;
import com.greenusys.personal.registrationapp.Utility.SharedPreference;
import com.greenusys.personal.registrationapp.Utility.UrlHelper;
import com.greenusys.personal.registrationapp.data.MciContract;
import com.greenusys.personal.registrationapp.fragments.CategoryAdapter;
import com.greenusys.personal.registrationapp.fragments.HomeAdapter;
import com.greenusys.personal.registrationapp.fragments.NewsAdapter;
import com.greenusys.personal.registrationapp.pojos.News;
import com.greenusys.personal.registrationapp.pojos.Subject_Modal;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class
MainActivity extends AppCompatActivity {
    private AppController appController;
    UrlHelper urlhelper;
    private static int currentPge = 0;
    private static int NUM_PAGES = 0;
    private static Executor databaseIO;
    private static final Integer[] IMAGES = {R.drawable.place_holder,R.drawable.place_holder,R.drawable.place_holder};

    private ArrayList<Integer> imagesList = new ArrayList<>();

    ViewPager mViewPager;
    CategoryAdapter mCategoryAdapter;
    TabLayout mTabLayout;

    RecyclerView mRecyclerView;
    HomeAdapter mAdapter;

    ViewPager carouselVp;

    CirclePageIndicator circleIndicator;

    Toolbar toolbar;

   public static ArrayList<News>newslist= new ArrayList<>();
    News news;
    NewsAdapter newsAdapter;
    public static String rewards;
  //  private Toast toast;


    @Override
    protected void onResume() {
        super.onResume();
        news_Api_Student();
        Reward_API();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        news_Api_Student();
        Reward_API();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appController = (AppController) getApplicationContext();
        mViewPager = (ViewPager)findViewById(R.id.vpPager);

       // toolbar = (Toolbar)findViewById(R.id.toolbar_layout);

        //Toast.makeText(appController, "kaif student main", Toast.LENGTH_SHORT).show();

        databaseIO = Executors.newFixedThreadPool(1);

       // setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        news_Api_Student();
        Reward_API();

        mCategoryAdapter = new CategoryAdapter(this, getSupportFragmentManager());

        mViewPager.setAdapter(mCategoryAdapter);

        mTabLayout = (TabLayout)findViewById(R.id.tabs);

        mTabLayout.setupWithViewPager(mViewPager);








        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 3)
                {
                   new MainActivity.SignIn1().execute();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        getUserDetailsFromDb();

    }





    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        //Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        LayoutInflater inflater = getLayoutInflater();
        View toastLayout = inflater.inflate(R.layout.content_custom_toast, (ViewGroup) findViewById(R.id.llCustom));

        Toast  toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastLayout);
        toast.show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.logut_menu_item)
        {
            SharedPreference.removeLoggedInState(MainActivity.this);

            Toast.makeText(this,"Logged out", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, FirstActivity.class);

            startActivity(intent);
        }
        if(item.getItemId()==R.id.main_menu_profile){
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);

    }
    private void bui() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setMessage("PLEASE CONTACT TO ADMIN FOR STUDENT PANEL ");
        builder1.setCancelable(false);

        builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Intent ins = new Intent(MainActivity.this, MainActivity.class);
                startActivity(ins);

            }
        });


        AlertDialog alert11 = builder1.create();
        alert11.show();


    }
    private class SignIn1 extends AsyncTask<String, Void, String> {

        private String key_a = "data-source";
        private String value_a = "android";
        private String key_b = "userId";
        private String value_b = urlhelper.userId ;
        private String response;
        private RequestBody body;
        private String url = urlhelper.checking;

        @Override
        protected String doInBackground(String... strings) {

            Log.e("back", "doInBackground: " + value_a+url);
            body = RequestBuilder.twoParameter(
                    key_a, value_a,key_b, value_b);

            try {
                response = appController.POST(url, body);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("RESSS", "onPostExecute: " + s);
            if (s != null) {
                try {

                    JSONObject rootObject = new JSONObject(response);
                  String sta = rootObject.getString("user_type");
                  if(sta.equalsIgnoreCase("normal")) {
                      bui();
                  }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void getUserDetailsFromDb()
    {
        databaseIO.execute(() -> {

           try {
               Cursor cursor = getContentResolver().query(MciContract.MciEntry.CONTENT_URI,
                       new String[]{MciContract.MciEntry.COLUMN_ID, MciContract.MciEntry.COLUMNN_CLASS},
                       null,
                       null,
                       null);

               setUserDataInUrlHelper(cursor);
           }
           catch (Exception e)
           {
               e.printStackTrace();
           }
        });



    }

    private void setUserDataInUrlHelper(Cursor cursor) {

        int classIndex = cursor.getColumnIndex(MciContract.MciEntry.COLUMNN_CLASS);
        int userId = cursor.getColumnIndex(MciContract.MciEntry.COLUMN_ID);

        while(cursor.moveToNext())
        {
            UrlHelper.userId = cursor.getString(userId);
            UrlHelper.classId = cursor.getString(classIndex);
        }
    }




    public ArrayList<News> news_Api_Student()
    {



        appController = (AppController) getApplicationContext();



        RequestBody body= new FormBody.Builder().add("class", "3").build();

        Request request = new Request.Builder().url("http://greenusys.website/mci/api/fetchNews.php").post(body).build();
        appController.getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                    }
                });}



            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                Log.e("hhh", "onResponse: " + myResponse);
                try {
                    final JSONObject ja = new JSONObject(myResponse);
                    Log.e("aaaa", "onResponse: " + ja);
                    if (ja.has("code")) {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("code");
                                    if (ja.getString("code").equalsIgnoreCase("1")) {

                                        JSONArray aaa = ja.getJSONArray("data");

                                        if(newslist!=null)
                                            newslist.clear();

                                        Log.e("dsf","sd"+aaa);
                                        for (int i = 0; i < aaa.length(); i++)
                                        {
                                            JSONObject item = aaa.getJSONObject(i);

                                            Log.d("id", item.getString("id"));
                                            Log.d("title", item.getString("title"));
                                            Log.d("news", item.getString("news"));
                                            Log.d("time", item.getString("time"));




                                               news = new News(item.getString("id"), item.getString("title"), item.getString("news"), item.getString("time"));
                                                newslist.add(news);



                                        }




                                        System.out.println("champ2"+newslist);









                                    }
                                    else
                                    {
                                        //Toast.makeText(appController, "Please Enter Correct Email id and Password", Toast.LENGTH_SHORT).show();
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


        return newslist;

    }

    public void  kaif_test()
    {



        appController = (AppController) getApplicationContext();



       // RequestBody body= new FormBody.Builder().add("class", "3").build();

        Request request = new Request.Builder().url("https://api.rechapi.com/moneyTransfer/cusDetails.php?format=json&token=xjW1bCqhDUOcotCzHVrMhyQ2AsXCUE&customerMobile=9890236559").build();
        appController.getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Errorrrrr_APIIII " + e.getMessage());

                    }
                });}



            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                Log.e("hhh", "onResponse: " + myResponse);
                try {
                    final JSONObject ja = new JSONObject(myResponse);
                    Log.e("aaaa", "onResponse: " + ja);
                    if (ja.has("code")) {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("code");
                                    if (ja.getString("code").equalsIgnoreCase("1")) {

                                        JSONArray aaa = ja.getJSONArray("data");

                                        if(newslist!=null)
                                            newslist.clear();

                                        Log.e("dsf","sd"+aaa);
                                        for (int i = 0; i < aaa.length(); i++)
                                        {
                                            JSONObject item = aaa.getJSONObject(i);

                                            Log.d("id", item.getString("id"));
                                            Log.d("title", item.getString("title"));
                                            Log.d("news", item.getString("news"));
                                            Log.d("time", item.getString("time"));




                                            news = new News(item.getString("id"), item.getString("title"), item.getString("news"), item.getString("time"));
                                            newslist.add(news);



                                        }




                                        System.out.println("champ2"+newslist);









                                    }
                                    else
                                    {
                                        //Toast.makeText(appController, "Please Enter Correct Email id and Password", Toast.LENGTH_SHORT).show();
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



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    public void Reward_API()
    {

        final ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        pdLoading.setMessage("Loading...");
        pdLoading.show();




        //appController = (AppController) getActivity();
        appController = (AppController) getApplicationContext();
        //  mRecyclerView=findViewById(R.id.subject_rv);


        RequestBody body = new FormBody.Builder().add("user_id", "3").build();

        Request request = new Request.Builder().url("http://greenusys.website/mci/api/myRewards.php").post(body).build();
        appController.getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdLoading.dismiss();
                        setContentView(R.layout.internet_layout_faied);

                        TextView retry=findViewById(R.id.retry);
                        retry.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(isNetworkAvailable()) {
                                    setContentView(R.layout.activity_subjects);
                                    Reward_API();
                                }
                            }
                        });


                       // Toast.makeText(appController, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                    }
                });}



            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                Log.e("hhh", "onResponse: " + myResponse);
                try {
                    final JSONObject ja = new JSONObject(myResponse);
                    Log.e("aaaa", "onResponse: " + ja);
                    if (ja.has("code")) {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("code");
                                    if (ja.getString("code").equalsIgnoreCase("1")) {
                                        // startActivity(new Intent(Soldier_Sample_Paper.this, soldier_category.class));

                                       // JSONArray aaa = myResponse.getJSONArray("data");
                                        String other_id="";
                                        String other_subject="";

                                       // Log.e("dsf","sd"+aaa);

                                        JSONObject jsonObject=ja.getJSONObject("data");

                                            Log.d("rwd_id", jsonObject.getString("rwd_id"));
                                            Log.d("rewards", jsonObject.getString("rewards"));

                                            rewards= jsonObject.getString("rewards");





                                    }
                                    else
                                    {
                                        //Toast.makeText(appController, "Please Enter Correct Email id and Password", Toast.LENGTH_SHORT).show();
                                    }
                                    pdLoading.dismiss();
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















}
