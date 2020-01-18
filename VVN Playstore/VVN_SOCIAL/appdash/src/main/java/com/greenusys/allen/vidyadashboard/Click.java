package com.greenusys.allen.vidyadashboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Click extends AppCompatActivity {
    String[] list ;
    List<String> a = new ArrayList<String>();
    String str;
    ListView lv;
    ArrayList<String> stringArrayList = new ArrayList<String>();
    Context context;
    String url = "https://vvn.city/apps/jain/course.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0 ; i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        str = jsonObject.getString("course");

                        a.add( jsonObject.getString("course"));



                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                lv = (ListView) findViewById(R.id.list_click);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(Click.this, android.R.layout.simple_list_item_checked, a);

                lv.setAdapter(itemsAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Click.this, "some error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) ;


        MySingleton.getInstance(getApplication()).addToRequestque(stringRequest);
        context = this;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent yy5=new Intent(Click.this,MainActivity_Dash.class);
        startActivity(yy5);
    }
}

