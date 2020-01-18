package com.avinashdavid.trivialtrivia.Soldier_Nursing_Package;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.avinashdavid.trivialtrivia.Adapter.Eligibility_Adapter;
import com.avinashdavid.trivialtrivia.AppController;
import com.avinashdavid.trivialtrivia.Model.Eligibility_Model;
import com.avinashdavid.trivialtrivia.R;
import com.avinashdavid.trivialtrivia.Soldier_Clerk.Clerk_Eligibility_Data;
import com.avinashdavid.trivialtrivia.Soldier_GD_Package.Soldier_Eligibility_Data;
import com.avinashdavid.trivialtrivia.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class Nursing_Eligibility_Data extends AppCompatActivity {
    LinearLayout past_paper1,past_paper2_buy,past_paper3_buy;
    EditText seller_email,seller_password;
    Button bt_seller_login;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    AppController appController;
    RecyclerView rv_gd;
    Eligibility_Adapter eligibility_adapter;

    List<Eligibility_Model> eligibility_models = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soldier__eligibility__data);

        getSupportActionBar().setTitle("Nursing Eligibility");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ProgressDialog pdLoading = new ProgressDialog(Nursing_Eligibility_Data.this);

        pdLoading.setMessage("Loading...");
        pdLoading.show();

        rv_gd = (RecyclerView) findViewById(R.id.rv_soldier_gd_pastpaper);
        appController = (AppController) getApplicationContext();


        Request request = new Request.Builder()
                .url(URL.nursing_eligibility_data)
                .build();
        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Nursing_Eligibility_Data.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();
                    }
                });}
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                Log.e("hhh", "onResponse: " + myResponse);
                try {
                    final JSONObject ja = new JSONObject(myResponse);
                    Log.e("aaaa", "onResponse: " + ja);
                    if (ja.has("status")) {
                        Nursing_Eligibility_Data.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("status");
                                    if (ja.getString("status").equalsIgnoreCase("0")) {
                                        // startActivity(new Intent(Soldier_Sample_Paper.this, soldier_category.class));

                                        JSONArray aaa = ja.getJSONArray("message");

                                        Log.e("dsf","sd"+aaa);
                                        for (int i = 0; i < aaa.length(); i++)
                                        {
                                            JSONObject item = aaa.getJSONObject(i);
                                            Log.d("details", item.getString("details"));
                                           /* Log.d("qualification", item.getString("qualification"));
                                            Log.d("written", item.getString("written"));
                                            Log.d("physical", item.getString("physical"));
                                            Log.d("date", item.getString("date"));*/
                                            String s = item.getString("details");
                                            String detail_result = Html.fromHtml(s).toString();
                                            /*Eligibility_Model eligibility_model = new Eligibility_Model(item.getString("exam_name"),item.getString("date"),item.getString("qualification"),item.getString("physical"),item.getString("written"));*/
                                            Eligibility_Model eligibility_model = new Eligibility_Model(detail_result);
                                            eligibility_models.add(eligibility_model);

                                            Log.d("modelsize", ""+eligibility_models.size());
                                        }

                                        // System.out.print("modelsize"+eligibility_models.size());

                                        Eligibility_Adapter eligibility_adapter = new Eligibility_Adapter(eligibility_models);
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                        rv_gd.setLayoutManager(mLayoutManager);
                                        rv_gd.setItemAnimator(new DefaultItemAnimator());
                                        rv_gd.setAdapter(eligibility_adapter);
                                        eligibility_adapter.notifyDataSetChanged();
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
