package com.icosom.social;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.icosom.social.activity.DashboardRecharge;
import com.icosom.social.activity.MainActivity;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.RequestBody;

public class MainActivityW extends AppCompatActivity {

    CommonFunctions urlHelper;
    AppController appController;
    EditText NUMBER;
    String phone;
    TextView sub;
    private String url;
    ProgressBar progressBar;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MainActivityW.this, DashboardRecharge.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_w);
        progressBar = findViewById(R.id.transfer5);
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();
        //user_id = sp.getString("userId", "");
        phone = sp.getString("phone", "");
        appController = (AppController) getApplicationContext();
        urlHelper = new CommonFunctions();
        NUMBER = findViewById(R.id.number);
        sub = findViewById(R.id.SUBMIT);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   phone = NUMBER.getText().toString();
                url = urlHelper.FETCH_FEEDSs;
                url=url+phone;
                setData();
            }
        });


        //setData();

    }



    private void setData() {
        new MainActivityW.SignIn1().execute();
        progressBar.setVisibility(View.VISIBLE);
    }

    private class SignIn1 extends AsyncTask<String, Void, String> {
        private String response;
        private RequestBody body;


        @Override
        protected String doInBackground(String... strings) {

            body = RequestBuilder.NoParameter();

            try {
                response = appController.POST(url, body);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("talent", "onPostExecute: " + s);
            if (s != null) {
                try {
                    Log.e("talent", "onPostExecute: " + response);

                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                    String error_code = jsonObject1.getString("error_code");
                    if(error_code.equalsIgnoreCase("200")){
                        startActivity(new Intent(MainActivityW.this,Talent_List.class).putExtra("response",response).putExtra("customerMobile",phone));
                    }
                    else if(error_code.equalsIgnoreCase("123")){
                        startActivity(new Intent(MainActivityW.this,CustomerRegistration.class));

                    }
                    else  {

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivityW.this, "Failure : "+jsonObject1.getString("resText"), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(new Intent(MainActivityW.this, MainActivity.class));
    }
}


