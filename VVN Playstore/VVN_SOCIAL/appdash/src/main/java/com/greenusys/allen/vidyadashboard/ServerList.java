package com.greenusys.allen.vidyadashboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerList extends AppCompatActivity {
    String url = "https://vvn.city/apps/jain/quizlist.php";
    ListView lv;
    String str;
    List<String> a ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_list);
        a = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0 ; i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        str = jsonObject.getString("subject");

                        a.add(str);
                        Log.e("DA", "onResponse: "+str );

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                lv=(ListView)findViewById(R.id.quiz_list);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(ServerList.this, android.R.layout.simple_list_item_checked, a);

                lv.setAdapter(itemsAdapter);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        switch (position)
                        {
                            case 0:
//            view.setBackgroundColor(Color.parseColor("#66F44336"));
                                next("Maths");

                                break;

                            case 1:
//            view.setBackgroundColor(Color.parseColor("#66F44336"));
                                next("Chemistry");

                                break;
                            case 2:
//            view.setBackgroundColor(Color.parseColor("#66F44336"));
                                next("Science2");

                                break;


                        }
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ServerList.this, "Check your Internet Connection...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                //  params.put("type", type);
                return params;
            }
        };


        MySingleton.getInstance(ServerList.this).addToRequestque(stringRequest);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent yy3=new Intent(ServerList.this,MainActivity_Dash.class);
        startActivity(yy3);
    }
    public void next(String str)
    {
        Intent inn1 = new Intent(ServerList.this,Quizfinal.class);
        inn1.putExtra("type", str);
        startActivity(inn1);

    }
}
