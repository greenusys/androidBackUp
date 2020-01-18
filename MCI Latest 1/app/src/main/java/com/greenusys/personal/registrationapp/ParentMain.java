package com.greenusys.personal.registrationapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import com.greenusys.personal.registrationapp.fragments.Category_Parent_Adapter;
import com.greenusys.personal.registrationapp.fragments.HomeAdapter;
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

public class ParentMain extends AppCompatActivity {
    private AppController appController;
    UrlHelper urlhelper;
    private static int currentPge = 0;
    private static int NUM_PAGES = 0;
    private static Executor databaseIO;
    private static final Integer[] IMAGES = {R.drawable.place_holder,R.drawable.place_holder,R.drawable.place_holder};

    private ArrayList<Integer> imagesList = new ArrayList<>();

    ViewPager mViewPager;
    Category_Parent_Adapter mCategoryAdapter;
    TabLayout mTabLayout;

    RecyclerView mRecyclerView;
    HomeAdapter mAdapter;

    ViewPager carouselVp;

    CirclePageIndicator circleIndicator;

    Toolbar toolbar;

    String parent_Id="";
    String pName="";
    String pEmail="";
    String sEmail="";
    String pMobile="";
    String stud_Id="";
    String passw="";

    public static String stu_class;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_main);
        appController = (AppController) getApplicationContext();
        mViewPager = (ViewPager)findViewById(R.id.vpPager);

        toolbar = (Toolbar)findViewById(R.id.toolbar_layout);

        //Toast.makeText(appController, "kaif student main", Toast.LENGTH_SHORT).show();

        databaseIO = Executors.newFixedThreadPool(1);
       // setSupportActionBar(toolbar);

      //  getSupportActionBar().setDisplayShowTitleEnabled(false);

        mCategoryAdapter = new Category_Parent_Adapter(this, getSupportFragmentManager());

        mViewPager.setAdapter(mCategoryAdapter);

        mTabLayout = (TabLayout)findViewById(R.id.tabs);

        mTabLayout.setupWithViewPager(mViewPager);









        parent_Id=getIntent().getStringExtra("parent_Id");
        pName=getIntent().getStringExtra("pName");
        pEmail=getIntent().getStringExtra("pEmail");
        sEmail=getIntent().getStringExtra("sEmail");
        pMobile=getIntent().getStringExtra("pMobile");
        stud_Id=getIntent().getStringExtra("stud_Id");
        passw=getIntent().getStringExtra("passw");



API();




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







    public void API()
    {

        final ProgressDialog pdLoading = new ProgressDialog(ParentMain.this);
        pdLoading.setMessage("Loading...");
        pdLoading.show();

        ImageView image=findViewById(R.id.imageView2);
        TextView offline=findViewById(R.id.textView2);
        TextView retry=findViewById(R.id.retry);

        appController = (AppController) getApplicationContext();
        mRecyclerView=findViewById(R.id.subject_rv);

        RequestBody body= new FormBody.Builder().add("parentId", "2").build();

        Request request = new Request.Builder().url("http://greenusys.website/mci/api/studentDetails.php").post(body).build();
        appController.getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                ParentMain.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        pdLoading.dismiss();

                        image.setVisibility(View.VISIBLE);
                        offline.setVisibility(View.VISIBLE);
                        retry.setVisibility(View.VISIBLE);


                        retry.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                // final ProgressDialog pdLoading = new ProgressDialog(Subjects.this);
                                //pdLoading.setMessage("Loading...");
                                pdLoading.show();
                                API();
                                pdLoading.dismiss();

                            }
                        });


                        //Toast.makeText(getBaseContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();

                    }
                });}



            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                Log.e("hhh", "onResponse: " + myResponse);
                try {
                    final JSONObject rootObject = new JSONObject(myResponse);
                    Log.e("aaaa", "onResponse: " + rootObject);
                    if (rootObject.has("code")) {
                        ParentMain.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + rootObject);
                                try {


                                    String status = rootObject.getString("code");


                                    JSONObject json2 = rootObject.getJSONObject("student_data");

                                Log.e("sss","ss"+json2);



                                    Log.e("name", "name" + json2.getString("name"));
                                    Log.e("parent_email", "parent_email" + json2.getString("email"));
                                    Log.e("phone_number", "phone_number" + json2.getString("phone_number"));
                                    Log.e("gender", "gender" + json2.getString("gender"));
                                    Log.e("password", "password" + json2.getString("password"));
                                    Log.e("class", "class" + json2.getString("class"));
                                    Log.e("user_type", "user_type" + json2.getString("user_type"));
                                    Log.e("status", "status" + json2.getString("status"));

                                    stu_class=json2.getString("class");



                                    Log.e("sdf", "sdf" + status);



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
            SharedPreference.removeLoggedInState(ParentMain.this);

            Toast.makeText(this,"Logged out", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(ParentMain.this, FirstActivity.class);

            startActivity(intent);
        }
        if(item.getItemId()==R.id.main_menu_profile){
            Intent intent = new Intent(ParentMain.this, ProfileActivity.class);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);

    }
    private void bui() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(ParentMain.this);
        builder1.setMessage("PLEASE CONTACT TO ADMIN FOR STUDENT PANEL ");
        builder1.setCancelable(false);

        builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Intent ins = new Intent(ParentMain.this, MainActivity.class);
                startActivity(ins);

            }
        });


        AlertDialog alert11 = builder1.create();
        alert11.show();


    }
    /*private class SignIn1 extends AsyncTask<String, Void, String> {

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
*/
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
