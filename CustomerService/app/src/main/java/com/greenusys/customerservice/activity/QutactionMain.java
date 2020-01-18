package com.greenusys.customerservice.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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

public class QutactionMain extends AppCompatActivity {
    RecyclerView rv;
    QutactionModel qutactionModel;
    List<QutactionModel> data = new ArrayList <>();
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    AppController appController;
    UrlHelper urlHelper = new UrlHelper();
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qutaction_main);
        appController = (AppController) getApplicationContext();
        rv = findViewById(R.id.rv);
        volleyJsonObjectRequest(UrlHelper.qutation);
        /*data = new ArrayList <>();
        for(int i=0; i<numberarray.length; i++) {
            data.add(
                    new QutactionModel(numberarray[i], Hnamearray[i], Ratearray[i], Quantyarrya[i], Amountarrya[i], Decarray[i])
            );
        }
            layoutManager = new LinearLayoutManager(this);
            rv.setLayoutManager(layoutManager);
            adapter = new QuationAdapter(data);
            rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }*/
    }

    public void volleyJsonObjectRequest(String url) {

        Log.e("12","sdf");
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("12","sdf");
                try {
                    Log.e("12","sdf");
                    final JSONArray ja = new JSONArray(response);
                    for (int i = 0; i < ja.length(); i++) {
                        Log.e("12","sdf");
                        JSONObject jsonObject = ja.getJSONObject(i);
                        Log.e("12","sdf");
                        qutactionModel = new QutactionModel(jsonObject.getString("id").trim(),jsonObject.getString("model").trim(),jsonObject.getString("rate").trim(),jsonObject.getString("qty"),jsonObject.getString("amount"),jsonObject.getString("description"),"http://eyematic.in/newdashboard5/uploads/downloads/"+jsonObject.getString("image"));
                        Log.e("12","sdf");
                        data.add(qutactionModel);
                        Log.e("12","sdf");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                rv = findViewById(R.id.rv);
                adapter = new QuationAdapter(QutactionMain.this,data);
                LinearLayoutManager layoutManager = new LinearLayoutManager(QutactionMain.this, LinearLayoutManager.VERTICAL, false);
                rv.setLayoutManager(layoutManager);
                rv.setAdapter(adapter);

                adapter.notifyDataSetChanged();



            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(QutactionMain.this, "Check your Internet Connection...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                //params.put("CustomerId",id);
                return params;
            }
        };


        AppSingleton.getInstance(QutactionMain.this).addToRequestque(stringRequest);


    }
}

