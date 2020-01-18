package com.greenusys.personal.registrationapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.greenusys.personal.registrationapp.Utility.AppController;
import com.greenusys.personal.registrationapp.Utility.RequestBuilder;
import com.greenusys.personal.registrationapp.Utility.SharedPreference;
import com.greenusys.personal.registrationapp.Utility.UrlHelper;
import com.greenusys.personal.registrationapp.data.MciContract;
import com.greenusys.personal.registrationapp.fragments.Category_Parent_Adapter;
import com.greenusys.personal.registrationapp.fragments.Category_staff_Adapter;
import com.greenusys.personal.registrationapp.fragments.HomeAdapter;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.RequestBody;

public class StaffMain extends AppCompatActivity {
    private AppController appController;
    UrlHelper urlhelper;
    private static int currentPge = 0;
    private static int NUM_PAGES = 0;
    private static Executor databaseIO;
    private static final Integer[] IMAGES = {R.drawable.place_holder,R.drawable.place_holder,R.drawable.place_holder};

    private ArrayList<Integer> imagesList = new ArrayList<>();

    ViewPager mViewPager;
    Category_staff_Adapter mCategoryAdapter;
    TabLayout mTabLayout;

    RecyclerView mRecyclerView;
    HomeAdapter mAdapter;

    ViewPager carouselVp;

    CirclePageIndicator circleIndicator;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_main);
        appController = (AppController) getApplicationContext();
        mViewPager = (ViewPager)findViewById(R.id.vpPager);

        toolbar = (Toolbar)findViewById(R.id.toolbar_layout);

        //Toast.makeText(appController, "kaif student main", Toast.LENGTH_SHORT).show();

        databaseIO = Executors.newFixedThreadPool(1);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mCategoryAdapter = new Category_staff_Adapter(this, getSupportFragmentManager());

        mViewPager.setAdapter(mCategoryAdapter);

        mTabLayout = (TabLayout)findViewById(R.id.tabs);

        mTabLayout.setupWithViewPager(mViewPager);





        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 3)
                {
                    //new MainActivity.SignIn1().execute();
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
            SharedPreference.removeLoggedInState(StaffMain.this);

            Toast.makeText(this,"Logged out", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(StaffMain.this, FirstActivity.class);

            startActivity(intent);
        }
        if(item.getItemId()==R.id.main_menu_profile){
            Intent intent = new Intent(StaffMain.this, ProfileActivity.class);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);

    }
    private void bui() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(StaffMain.this);
        builder1.setMessage("PLEASE CONTACT TO ADMIN FOR STUDENT PANEL ");
        builder1.setCancelable(false);

        builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Intent ins = new Intent(StaffMain.this, MainActivity.class);
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
}
