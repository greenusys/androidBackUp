package com.avinashdavid.trivialtrivia.Soldier_GD_Package;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/*import com.greenusys.army_project.AppController;
import com.greenusys.army_project.R;
import com.greenusys.army_project.URL;*/

import com.avinashdavid.trivialtrivia.Adapter.Eligibility_Adapter;
import com.avinashdavid.trivialtrivia.AppController;
import com.avinashdavid.trivialtrivia.Model.Eligibility_Model;
import com.avinashdavid.trivialtrivia.R;

import java.util.ArrayList;
import java.util.List;

public class Soldier_Eligibility extends AppCompatActivity {
    LinearLayout qualification,written,physical;
    EditText seller_email,seller_password;
    Button bt_seller_login;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    AppController appController;

    RecyclerView rv_gd;
    Eligibility_Adapter eligibility_adapter;
    //Eligibility_Model eligibility_model;
    List<Eligibility_Model> eligibility_models = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soldier_eligibility);

        getSupportActionBar().setTitle("Soldier Eligibility");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        qualification=(LinearLayout)findViewById(R.id.qualification);
        written=(LinearLayout)findViewById(R.id.written);
        physical=(LinearLayout)findViewById(R.id.physical);



        qualification.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       Intent intent=new Intent(Soldier_Eligibility.this,Soldier_Eligibility_Data.class);
                       startActivity(intent);


                    }

                }
        );


        written.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(Soldier_Eligibility.this,Soldier_Eligibility_Data.class);
                        startActivity(intent);


                    }

                }
        );
        physical.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(Soldier_Eligibility.this,Soldier_Eligibility_Data.class);
                        startActivity(intent);


                    }

                }
        );

/*

        appController = (AppController) getApplicationContext();

    }

    private void qualification() {
        //  final ProgressDialog pdLoading = new ProgressDialog(Seller_Login.this);
//        pb_login.setVisibility(View.VISIBLE);

        RequestBody body = new FormBody.Builder().add("id", id).build();

        Request request = new Request.Builder().url("http://greenusys.website/indianarmy/indianarmy/androidapi/apifetch_allsoldiereligibility.php").post(body).build();



        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Soldier_Eligibility.this.runOnUiThread(new Runnable() {
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
                        Soldier_Eligibility.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("status");
                                    if (ja.getString("status").equalsIgnoreCase("1")) {
                                        // startActivity(new Intent(Soldier_Sample_Paper.this, soldier_category.class));

                                        JSONArray aaa = ja.getJSONArray("data");
                                        for (int i = 0; i < a.length(); i++)
                                        {
                                            JSONObject item = aaa.getJSONObject(i);
                                            Log.d("exam_name", item.getString("exam_name"));
                                            Log.d("qualification", item.getString("qualification"));
                                            Log.d("written", item.getString("written"));
                                            Log.d("physical", item.getString("physical"));
                                            Log.d("date", item.getString("date"));

                                            Eligibility_Model eligibility_model = new Eligibility_Model(item.getString("exam_name"),item.getString("date"),item.getString("qualification"),item.getString("physical"),item.getString("written"));
                                            eligibility_models.add(eligibility_model);

                                        }

                                        Eligibility_Adapter eligibility_adapter = new Eligibility_Adapter(eligibility_models);
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                        rv_gd.setLayoutManager(mLayoutManager);
                                        rv_gd.setItemAnimator(new DefaultItemAnimator());
                                        rv_gd.setAdapter(eligibility_adapter);
                                        eligibility_adapter.notifyDataSetChanged();

                                        //finish();
                                        Log.e("aaaa2", "onResponse: " +a);
                                        // Toast.makeText(appController, "Welcome "+email, Toast.LENGTH_SHORT).show();

                                    }
                                    else
                                    {
                                        //Toast.makeText(appController, "Please Enter Correct Email id and Password", Toast.LENGTH_SHORT).show();
                                    }
                                    //  pdLoading.dismiss();
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
        });*/
    }
}
