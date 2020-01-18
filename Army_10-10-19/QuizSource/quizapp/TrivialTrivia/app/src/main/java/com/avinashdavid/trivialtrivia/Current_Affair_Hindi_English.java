package com.avinashdavid.trivialtrivia;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
/*

import com.greenusys.army_project.Adapter.Current_Affair_adapter;
import com.greenusys.army_project.Model.Current_Affair_model;
*/

import com.avinashdavid.trivialtrivia.Adapter.Current_Affair_adapter;
import com.avinashdavid.trivialtrivia.Model.Current_Affair_model;
import com.avinashdavid.trivialtrivia.Soldier_GD_Package.Soldier_Eligibility_Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Current_Affair_Hindi_English extends AppCompatActivity {

    private AppController appController;
    Current_Affair_adapter current_affair_adapter;
    Current_Affair_model current_affair_model;
    List<Current_Affair_model> soldire_gd_models = new ArrayList<>();
    RecyclerView rv_gd;
    String language ="";
   // TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current__affair__hindi__english);

       // textView = findViewById(R.id.current_affair_id);

        if(getIntent().getStringExtra("language")!=null)
        language= getIntent().getStringExtra("language");


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String language= preferences.getString("language", "");
        System.out.println("Language"+language);

        //textView = findViewById(R.id.current_affair_id);
       // textView.setText(preferences.getString("langauge", ""));

        Request request = null;
        RequestBody requestBody = null;

        if (language.equals("English")) {
            request = new Request.Builder().url("http://4army.in/indianarmy/androidapi/apifetch_dateforengcurrentafair.php").build();
            getSupportActionBar().setTitle("English Current Affair");
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (language.equals("hindi")) {
          //  textView.setText("Hindi");
            request = new Request.Builder().url("http://4army.in/indianarmy/androidapi/apifetch_dateforhindicurrentafair.php").build();
            getSupportActionBar().setTitle("Hindi Current Affair");
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        final ProgressDialog pdLoading = new ProgressDialog(Current_Affair_Hindi_English.this);

        pdLoading.setMessage("Loading...");
        pdLoading.show();

        rv_gd = (RecyclerView) findViewById(R.id.rv_soldier_gd);
        appController = (AppController) getApplicationContext();


        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Current_Affair_Hindi_English.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                Log.e("hhh", "onResponse: " + myResponse);
                try {
                    final JSONObject ja = new JSONObject(myResponse);
                    Log.e("aaaa", "onResponse: " + ja);
                    if (ja.has("status")) {
                        Current_Affair_Hindi_English.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("status");
                                    if (ja.getString("status").equalsIgnoreCase("0")) {
                                        // startActivity(new Intent(Soldier_Sample_Paper.this, soldier_category.class));

                                        JSONArray aaa = ja.getJSONArray("message");

                                        Log.e("dsf", "sd" + aaa);
                                        for (int i = aaa.length()-1; i >= 0; i--) {

                                            JSONObject item = aaa.getJSONObject(i);

                                            Log.d("date_kaif",item.getString("date"));
                                            String date = item.getString("date");
                                            Current_Affair_model current_affair_model = new Current_Affair_model();
                                            current_affair_model.setDate(date);

                                           // String detail_result = Html.fromHtml(s).toString();
                                          // Current_Affair_model current_affair_model = new Current_Affair_model(detail_result);
                                           // Current_Affair_model current_affair_model = new Current_Affair_model(detail_result);
                                            soldire_gd_models.add(current_affair_model);


                                        }

                                        current_affair_adapter = new Current_Affair_adapter(soldire_gd_models,language);


                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                        rv_gd.setLayoutManager(mLayoutManager);


                                        rv_gd.setAdapter(current_affair_adapter);
                                        current_affair_adapter.notifyDataSetChanged();


                                        // Toast.makeText(appController, "Welcome "+email, Toast.LENGTH_SHORT).show();

                                    } else {
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


    }//language


}
