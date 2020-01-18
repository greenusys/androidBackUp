package com.greenusys.customerservice.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.greenusys.customerservice.A_ImageUpload;
import com.greenusys.customerservice.R;
import com.greenusys.customerservice.utility.AppController;
import com.greenusys.customerservice.utility.UrlHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnginerDashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private List<EnginerModel> customerModelLists = new ArrayList<>();

    String agent;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    String userId;
    String email;
    RecyclerView rv;
    AppController appController;
    EnginerAdapter customer_adapters;
    UrlHelper urlHelper = new UrlHelper();
    EnginerModel enginerModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enginer_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        appController = (AppController) getApplicationContext();
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edit = sp.edit();
        userId = sp.getString("userId", "0");
        email = sp.getString("email","0");
        String c = userId;
        Log.e("t1","hjksd----------->"+email);
        volleyJsonObjectRequest(UrlHelper.enggQueryList);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finishAffinity();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.enginer_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.e_logout) {
           // edit.putBoolean("savePassword", false);
           // edit.commit();
            startActivity(new Intent(EnginerDashboard.this,Loginactivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(EnginerDashboard.this, A_ImageUpload.class).putExtra("h11",email));


        } else  {

            //startActivity(new Intent(EnginerDashboard.this,Pdf.class));

        /*} else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {*/

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        drawer.setVisibility(View.GONE);
        return true;
    }

    public void volleyJsonObjectRequest(String url) {


        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    //final JSONArray ja = new JSONArray(response);
                     JSONArray ja = new JSONArray(response);
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jsonObject = ja.getJSONObject(i);
                        enginerModel = new EnginerModel(jsonObject.getString("CustomerName"), jsonObject.getString("CustomerMobile"), jsonObject.getString("CustomerAddress"), jsonObject.getString("Id"),jsonObject.getString("QueryId"),jsonObject.getString("CustomerQuery"),jsonObject.getString("PostedOn"),jsonObject.getString("workstatus"));
                        customerModelLists.add(enginerModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                rv = findViewById(R.id.recycler_view_enginer);
                customer_adapters = new EnginerAdapter(customerModelLists, EnginerDashboard.this);
                LinearLayoutManager layoutManager = new LinearLayoutManager(EnginerDashboard.this, LinearLayoutManager.VERTICAL, false);
                rv.setLayoutManager(layoutManager);
                rv.setAdapter(customer_adapters);
                customer_adapters.notifyDataSetChanged();



            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EnginerDashboard.this, "Check your Internet Connection...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("enggId", userId);
                return params;
            }
        };


        AppSingleton.getInstance(EnginerDashboard.this).addToRequestque(stringRequest);


    }
}






