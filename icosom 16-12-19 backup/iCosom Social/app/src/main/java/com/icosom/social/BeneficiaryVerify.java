package com.icosom.social;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
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

public class BeneficiaryVerify extends AppCompatActivity {
    CommonFunctions urlHelper;
    AppController appController;
    EditText NUMBER, otp;
    String phone, botp, id, bid;
    TextView sub;
    private String url;
    ProgressBar progressBar;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    int i =0;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(BeneficiaryVerify.this, DashboardRecharge.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiary_verify);
        id = getIntent().getStringExtra("beneficiaryId");
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();
        //user_id = sp.getString("userId", "");
        phone = sp.getString("phone", "");
        progressBar = findViewById(R.id.transfer3);
        appController = (AppController) getApplicationContext();
        urlHelper = new CommonFunctions();
        NUMBER = findViewById(R.id.customerMobile_verify);
        otp = findViewById(R.id.bene_otp_verify);
        sub = findViewById(R.id.verifyBeneficiary);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // phone = NUMBER.getText().toString();
                botp = otp.getText().toString();
                url = urlHelper.beneficiaryVerifiy;
                url = url + "&customerMobile=" + phone + "&otp=" + botp + "&beneficiaryId=" + id;

                setData();
            }
        });


        //setData();

    }


    private void setData() {
        if(i==0) {
            i=1;
            new BeneficiaryVerify.SignIn1().execute();
            progressBar.setVisibility(View.VISIBLE);

        }
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
                    if (error_code.equalsIgnoreCase("200")) {
                        Toast.makeText(BeneficiaryVerify.this, "Suceess", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BeneficiaryVerify.this, MainActivityW.class));

                    }
                    else  {
                        i=0;
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(BeneficiaryVerify.this, "Failure : "+jsonObject1.getString("resText"), Toast.LENGTH_SHORT).show();

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
        startActivity(new Intent(BeneficiaryVerify.this, MainActivity.class));
    }

}



