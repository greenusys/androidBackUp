package com.avinashdavid.trivialtrivia.Notification_package;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
/*
import com.greenusys.army_project.Adapter.Notication_rally_adapter;
import com.greenusys.army_project.AppController;
import com.greenusys.army_project.Model.Rally_model;
import com.greenusys.army_project.Model.soldire_gd_model;
import com.greenusys.army_project.R;*/

import com.avinashdavid.trivialtrivia.Adapter.Check_Rally_Adapter;
import com.avinashdavid.trivialtrivia.AppController;
import com.avinashdavid.trivialtrivia.Model.Check_Rally_Model;
import com.avinashdavid.trivialtrivia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Check_Rally extends AppCompatActivity {
    AppController appController;
    RecyclerView rv_gd;
    //Check_Rally_Adapter check_rally_adapter;
    //Check_Rally_Model check_rally_model;
    List<Check_Rally_Model> check_rally_models = new ArrayList<>();
    Spinner spinner_state;
    Spinner spinner_dis;
    String s_state="", s_dis="";

    List<String> list = new ArrayList<String>();
    Button go;

    Check_Rally_Model check_rally_model;
    Check_Rally_Adapter check_rally_adapter;
    List<Check_Rally_Model> check_list = new ArrayList<Check_Rally_Model>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check__rally);

        getSupportActionBar().setTitle("Check Statewise Rally");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rv_gd = (RecyclerView) findViewById(R.id.rv_all_rally);
        appController = (AppController) getApplicationContext();

        go = (Button) findViewById(R.id.go);




        spinner_state = findViewById(R.id.state);
        //spinner_dis = findViewById(R.id.region);

        ArrayAdapter<CharSequence> countryAdapter = ArrayAdapter.createFromResource
                (this, R.array.state_type, R.layout.spinner_items);

        countryAdapter.setDropDownViewResource(R.layout.spinner_items);


        spinner_state.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                         s_state = spinner_state.getSelectedItem().toString();
                       // Toast.makeText(appController, s_state, Toast.LENGTH_SHORT).show();


                        if (check_list != null)
                            check_list.clear();
                       // API_DATA(s_state);




                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });



        go.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        System.out.println("kkk"+s_state);
                        System.out.println("kkk2"+s_dis);

                        if(s_state.equalsIgnoreCase("Please Select Your State"))

                        {
                            if(check_list!=null)
                                check_list.clear();

                            check_rally_adapter = new Check_Rally_Adapter(check_list);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            rv_gd.setLayoutManager(mLayoutManager);
                            rv_gd.setItemAnimator(new DefaultItemAnimator());
                            rv_gd.setAdapter(check_rally_adapter);
                            check_rally_adapter.notifyDataSetChanged();

                            Toast.makeText(appController, "Please Select Your  State", Toast.LENGTH_SHORT).show();

                        }


                        else
                        {
                            if(check_list!=null)
                            check_list.clear();
                            API_DATA(s_state);
                        }


                    }
                }
        );



    }




    private void API_DATA(String state) {
        final ProgressDialog pdLoading = new ProgressDialog(Check_Rally.this);

        Log.e("kk","vv"+state);
      //  Log.e("kjjk","vv"+city);

            pdLoading.setMessage("Loading...");
        pdLoading.show();

        RequestBody body = new FormBody.Builder().add("state", state).build();


        Request request = new Request.Builder().url("http://4army.in/indianarmy/androidapi/apifetch_dsrally.php").post(body).build();

       // list.add("Please Select Your City");


        if(check_list!=null)
            check_list.clear();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Check_Rally.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();
                    }
                });}
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();
                Log.e("hhh", "onResponse: " + myResponse);
                try {
                    final JSONObject ja = new JSONObject(myResponse);
                    Log.e("aaaa", "onResponse: " + ja);
                    if (ja.has("status")) {
                        Check_Rally.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("status");
                                    if (a.equals("0")) {

                                        JSONArray message = ja.getJSONArray("message");

                                        //JSONObject jsonObject1=ja.getJSONObject("result");






                                        Log.e("jsonObject1","jsonObject1"+message);



                                        for (int i = 0; i < message.length(); i++) {
                                            JSONObject item = message.getJSONObject(i);


                                            Log.d("id", item.getString("id"));
                                            Log.d("state", item.getString("state"));
                                            Log.d("district", item.getString("district"));
                                            Log.d("cat_code", item.getString("cat_code"));
                                            Log.d("venue_of_rally", item.getString("venue_of_rally"));
                                            Log.d("app_start_date", item.getString("app_start_date"));
                                            Log.d("app_end_date", item.getString("app_end_date"));
                                            Log.d("rally_start_date", item.getString("rally_start_date"));
                                            Log.d("rally_end_date", item.getString("rally_end_date"));

                                            check_rally_model = new Check_Rally_Model(item.getString("id"),item.getString("state"),item.getString("district"),
                                                    item.getString("cat_code"),item.getString("venue_of_rally"),item.getString("app_start_date"),
                                                    item.getString("app_end_date"),item.getString("rally_start_date"),item.getString("rally_end_date"));
                                            check_list.add(check_rally_model);



                                        }



                                        check_rally_adapter = new Check_Rally_Adapter(check_list);
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                        rv_gd.setLayoutManager(mLayoutManager);
                                        rv_gd.setItemAnimator(new DefaultItemAnimator());
                                        rv_gd.setAdapter(check_rally_adapter);
                                        check_rally_adapter.notifyDataSetChanged();





                                        pdLoading.dismiss();

                                    }

                                    else
                                    {
                                        pdLoading.dismiss();
                                        Toast.makeText(appController, "No Data Found!", Toast.LENGTH_SHORT).show();
                                        if(check_list!=null)
                                            check_list.clear();


                                        check_rally_adapter = new Check_Rally_Adapter(check_list);
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                        rv_gd.setLayoutManager(mLayoutManager);
                                        rv_gd.setItemAnimator(new DefaultItemAnimator());
                                        rv_gd.setAdapter(check_rally_adapter);
                                        check_rally_adapter.notifyDataSetChanged();

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                       // return;
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }






    private void API(String state_id) {
        final ProgressDialog pdLoading = new ProgressDialog(Check_Rally.this);

        pdLoading.setMessage("Loading...");
        pdLoading.show();

       RequestBody body = new FormBody.Builder().add("type", "getCities").add("stateId",state_id).build();


        Request request = new Request.Builder().url("http://4army.in/indianarmy/androidapi/state.php").post(body).build();

        list.add("Please Select Your City");

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Check_Rally.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();
                    }
                });}
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();
                Log.e("hhh", "onResponse: " + myResponse);
                try {
                    final JSONObject ja = new JSONObject(myResponse);
                    Log.e("aaaa", "onResponse: " + ja);
                    if (ja.has("status")) {
                        Check_Rally.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("status");
                                    if (ja.getString("status").equalsIgnoreCase("success")) {

                                        JSONArray message = ja.getJSONArray("result");

                                        //JSONObject jsonObject1=ja.getJSONObject("result");


                                        Log.e("jsonObject1","jsonObject1"+message);



                                        for (int i = 0; i < message.length(); i++) {
                                            JSONObject item = message.getJSONObject(i);

                                            Log.d("name", item.getString("name"));


                                            list.add(item.getString("name"));


                                        }

                                        System.out.println("city"+list);








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
                        Toast.makeText(appController, "No Data Found!", Toast.LENGTH_SHORT).show();
                        if(list!=null)
                            list.clear();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }






}

































