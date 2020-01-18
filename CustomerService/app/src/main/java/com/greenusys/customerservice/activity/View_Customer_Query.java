package com.greenusys.customerservice.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.greenusys.customerservice.R;

import com.greenusys.customerservice.utility.UrlHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class View_Customer_Query extends AppCompatActivity {
    private List<Customer_Model> customerModelLists = new ArrayList<>();
    Customer_Model customer_models;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    String userId;
    RecyclerView rv;
    Customer_Adapter customer_adapter;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__customer__query);

        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edit = sp.edit();
        userId = sp.getString("userId", "0");


        volleyJsonObjectRequest(UrlHelper.custQueryList);

    }

    public void volleyJsonObjectRequest(String url) {


        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int i = 0;
                try {

                    final JSONArray ja = new JSONArray(response);
                    for (i = 0; i < ja.length(); i++) {

                        JSONObject jsonObject = ja.getJSONObject(i);
                        Log.e("error", "onResponse: " + jsonObject);
                        customer_models = new Customer_Model(jsonObject.getString("UniqueId"), jsonObject.getString("CustomerQuery"), jsonObject.getString("CustomerName"), jsonObject.getString("PostedOn"), jsonObject.getString("EnggName"), jsonObject.getString("EnggPhone"), jsonObject.getString("workstatus"));
                        customerModelLists.add(customer_models);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                rv = findViewById(R.id.recycler_view_customer);
                customer_adapter = new Customer_Adapter(customerModelLists, View_Customer_Query.this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                rv.setLayoutManager(mLayoutManager);
                rv.setAdapter(customer_adapter);
                customer_adapter.notifyItemInserted(i);
                customer_adapter.notifyDataSetChanged();


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(View_Customer_Query.this, "Check your Internet Connection...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("custId", userId);
                return params;
            }
        };


        AppSingleton.getInstance(View_Customer_Query.this).addToRequestque(stringRequest);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customer_dashboard_2, menu);
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        //.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        customer_adapter.getFilter().filter(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String query) {
                        customer_adapter.getFilter().filter(query);
                        return false;
                    }
                }
        );


        return true;
    }


}
