package com.icosom.social;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.icosom.social.activity.EditProfileActivity;
import com.icosom.social.activity.MainActivity;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PrivacySetting extends AppCompatActivity {
    CollapsingToolbarLayout toolbar_layout;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    AppController appController;
    Switch phoneSwitch,emailSwitch;
    TextView pro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_setting);
        appController = (AppController) getApplicationContext();
        toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layouts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profiles);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();
        toolbar_layout.setTitle(sp.getString("firstName","")+" "+sp.getString("lastName",""));
        pro = findViewById(R.id.pro);
        checkPrivacy(sp.getString("userId", ""));
        emailSwitch = (Switch) findViewById(R.id.emailSwitch);
        phoneSwitch = (Switch) findViewById(R.id.phoneSwitchs);
        phoneSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneSwitch.isChecked()) {
                    phoneStatus("1");
                    edt.putString("phonehide", "1");
                    edt.commit();
                }
                else {
                    phoneStatus("0");
                    edt.putString("phonehide", "0");
                    edt.commit();
                }
            }
        });
        emailSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailSwitch.isChecked()) {
                    emailStatus("1");
                    edt.putString("emailhide", "1");
                    edt.commit();
                }
                else {
                    emailStatus("0");
                    edt.putString("emailhide", "0");
                    edt.commit();
                }
            }
        });



        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrivacySetting.this, EditProfileActivity.class));
            }
        });
    }

    public void emailStatus(String sta) {
        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", sp.getString("userId", "")).
                add("emailStatus",sta).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.email).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    JSONObject jo = new JSONObject(myResponse);
                    if (jo.getString("status").equals("1")) {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void phoneStatus(String sta) {
        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", sp.getString("userId", "")).
                add("phoneStatus",sta).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.phone).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    JSONObject jo = new JSONObject(myResponse);
                    if (jo.getString("status").equals("1")) {



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PrivacySetting.this, MainActivity.class));
    }
     @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(PrivacySetting.this, MainActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void checkPrivacy(String id) {

        RequestBody body = new FormBody.Builder().
                add("userId", id).
                add("data-source", "android").
                build();

        Request request = new Request.Builder().
                url("https://icosom.com/social/main/profileProcess.php?action=privacyCheck").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                PrivacySetting.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                try {

                    final JSONObject ja = new JSONObject(myResponse);

                    Log.e("jaa", "run: " + ja);
                    PrivacySetting.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            try {


                                JSONArray jsonArray = ja.getJSONArray("data");
                                JSONObject jaa = jsonArray.getJSONObject(0);
                                String emailcheck = jaa.getString("email_status");
                                String phonecheck = jaa.getString("phone_status");
                                if (emailcheck.equalsIgnoreCase("1")) {
                                    emailSwitch.setChecked(true);
                                } else {
                                    emailSwitch.setChecked(false);
                                }
                                if (phonecheck.equalsIgnoreCase("1")) {

                                    phoneSwitch.setChecked(true);
                                } else {
                                    phoneSwitch.setChecked(false);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
