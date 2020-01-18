package com.avinashdavid.trivialtrivia;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.avinashdavid.trivialtrivia.Adapter.Check_Your_Eligibility_Adapter;
import com.avinashdavid.trivialtrivia.Adapter.Eligibility_Adapter;
import com.avinashdavid.trivialtrivia.Login_Register_Package.Registration;
import com.avinashdavid.trivialtrivia.Model.Check_Your_Eligibility_Model;
import com.avinashdavid.trivialtrivia.Model.Eligibility_Model;
import com.avinashdavid.trivialtrivia.Notification_package.Check_Rally;
import com.avinashdavid.trivialtrivia.Soldier_GD_Package.Soldier_Eligibility_Data;
import com.avinashdavid.trivialtrivia.Soldier_Tech_Package.Tech_Eligibility_Data;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

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

public class Check_Your_Eligibility extends AppCompatActivity {

    LinearLayout past_paper1, past_paper2_buy, past_paper3_buy;
    EditText seller_email, seller_password;
    Button bt_seller_login;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    AppController appController;
    RecyclerView rv_gd;
    Button submit;
    EditText age, qul;
    Check_Your_Eligibility_Adapter eligibility_adapter;
    List<Check_Your_Eligibility_Model> eligibility_models = new ArrayList<>();
    Check_Your_Eligibility_Model check_your_eligibility_model;

    ArrayAdapter<String> arrayAdapter,arrayAdapter2;
    Spinner materialDesignSpinner,materialDesignSpinner2;
    List<String> qualification_list = new ArrayList<String>();
    List<String> age_list = new ArrayList<String>();
     String qualification_value="",age_value="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check__your__eligibility);




        getSupportActionBar().setTitle("Check Your Eligibility");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        rv_gd = (RecyclerView) findViewById(R.id.rv_soldier_gd_pastpaper);
        appController = (AppController) getApplicationContext();






        materialDesignSpinner =  findViewById(R.id.qualification);
        materialDesignSpinner2 =  findViewById(R.id.age);
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, Dashboard.qualification_list);
        arrayAdapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, Dashboard.age_list);

        materialDesignSpinner.setAdapter(arrayAdapter);
       materialDesignSpinner2.setAdapter(arrayAdapter2);










      materialDesignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

              qualification_value= parent.getItemAtPosition(position).toString();//get subject name from list

              // Toast.makeText(appController, qualification_value, Toast.LENGTH_SHORT).show();
              if (!qualification_value.equals("") && !age_value.equals("")  )
                  submit.setVisibility(View.VISIBLE);

          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });



        materialDesignSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                age_value= parent.getItemAtPosition(position).toString();//get subject name from list

                // Toast.makeText(appController, qualification_value, Toast.LENGTH_SHORT).show();
                if (!qualification_value.equals("") && !age_value.equals("")  )
                    submit.setVisibility(View.VISIBLE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        submit = findViewById(R.id.submit);
        submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (qualification_value.equals("") && age_value.equals("")  )
                        {
                            Toast.makeText(appController, "Please Select Value Fist", Toast.LENGTH_SHORT).show();
                            //materialDesignSpinner.setError("please select qaualification");
                           // materialDesignSpinner2.setError("please select age");
                        }
                        else if (qualification_value.equals("") )
                        {
                            Toast.makeText(appController, "Please Select Qualification", Toast.LENGTH_SHORT).show();
                        }
                        else if (age_value.equals("") )
                        {
                            //materialDesignSpinner2.setError("please select age");
                            Toast.makeText(appController, "Please Select Age", Toast.LENGTH_SHORT).show();
                        }
                        else {
                             eligibility_models.clear();
                            Check_eligblilty(qualification_value,age_value);
                          //  Toast.makeText(appController, "else", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    public void Check_eligblilty(String qual,String age)
    {
        final ProgressDialog pdLoading = new ProgressDialog(Check_Your_Eligibility.this);
        RequestBody requestBody = new FormBody.Builder()
                .add("qualification",qual)
                .add("age",age)

                .build();
        Request request = new Request.Builder().
                url("http://4army.in/indianarmy/androidapi/apifetch_checkeligibility.php")
                .post(requestBody)
                .build();
        pdLoading.setMessage("Loading...");
        pdLoading.show();
        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Error" + e.getMessage());
                Check_Your_Eligibility.this.runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {

                            }
                        }
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();

                Check_Your_Eligibility.this.runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    //  Toast.makeText(Registration.this, "1", Toast.LENGTH_SHORT).show();

                                    final JSONObject ja = new JSONObject(myResponse);

                                    //Toast.makeText(Registration.this, "2", Toast.LENGTH_SHORT).show();

                                    String stauts = ja.getString("status");

                                    if (stauts.equalsIgnoreCase("0")) {


                                        String message = ja.getString("message");
                                        Log.e("h1mes1","me"+message);
                                        JSONArray jsonArray1 = ja.getJSONArray("message");
                                        for (int i = 0; i < jsonArray1.length(); i++) {
                                            JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                                            Log.e("helo", "hell" + jsonObject1.getString("id"));
                                            Log.e("helo", "hell" + jsonObject1.getString("category"));
                                            Log.e("date", "date" + jsonObject1.getString("date"));



                                            String date=jsonObject1.getString("date");

                                            String date3="";
                                            String month3="";
                                            String year3="";
                                            String finaldate="";

                                            date3=date3+date.charAt(8)+date.charAt(9);
                                            month3=month3+date.charAt(5)+date.charAt(6);
                                            year3=year3+date.charAt(0)+date.charAt(1)+date.charAt(2)+date.charAt(3);
                                            finaldate=finaldate+date3+"-"+month3+"-"+year3;

                                            Log.e("finaldate","finaldate"+finaldate);

                                            Check_Your_Eligibility_Model check_your_eligibility_model1 = new Check_Your_Eligibility_Model(jsonObject1.getString("category"),
                                                    jsonObject1.getString("title"),
                                                    jsonObject1.getString("age"),
                                                    finaldate

                                            );



                                            eligibility_models.add(check_your_eligibility_model1);

                                        }
                                        Check_Your_Eligibility_Adapter eligibility_adapter = new Check_Your_Eligibility_Adapter(eligibility_models);
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                        rv_gd.setLayoutManager(mLayoutManager);
                                        rv_gd.setItemAnimator(new DefaultItemAnimator());
                                        rv_gd.setAdapter(eligibility_adapter);
                                        eligibility_adapter.notifyDataSetChanged();

                                        pdLoading.dismiss();
                                    } else {

                                        pdLoading.dismiss();
                                        Toast.makeText(appController, "No Data Found!", Toast.LENGTH_SHORT).show();

                                        if(eligibility_models!=null)
                                            eligibility_models.clear();

                                        Check_Your_Eligibility_Adapter eligibility_adapter = new Check_Your_Eligibility_Adapter(eligibility_models);
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                        rv_gd.setLayoutManager(mLayoutManager);
                                        rv_gd.setItemAnimator(new DefaultItemAnimator());
                                        rv_gd.setAdapter(eligibility_adapter);
                                        eligibility_adapter.notifyDataSetChanged();

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
            }
        });
    }




}

