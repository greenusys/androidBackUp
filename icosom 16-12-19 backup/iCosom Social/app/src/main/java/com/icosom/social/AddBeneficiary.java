package com.icosom.social;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class AddBeneficiary extends AppCompatActivity {
    CommonFunctions urlHelper;
    AppController appController;
    EditText NUMBER,ename,ecode,eaccount,enumber;
    String phone,bname,bcode,baccount,bnumber;
    TextView sub;
    private String url;
    ProgressBar progressBar;
int i =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beneficiary);
        phone = getIntent().getStringExtra("customerMobile");

        appController = (AppController) getApplicationContext();
        urlHelper = new CommonFunctions();
        progressBar = findViewById(R.id.transfer2);
        NUMBER = findViewById(R.id.customerMobileBen);
        ename = findViewById(R.id.beneficiaryName);
        ecode = findViewById(R.id.ifscCode);
        eaccount = findViewById(R.id.beneficiaryAccountNumber);
        enumber = findViewById(R.id.beneficiaryMobileNumber);
        sub = findViewById(R.id.addBeneficiary);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // phone = NUMBER.getText().toString();
                bname = ename.getText().toString();
                bcode = ecode.getText().toString();
                baccount = eaccount.getText().toString();
                bnumber = enumber.getText().toString();
                Log.e("talent", "onPostExecute: " + baccount+bcode+bnumber);
                url = urlHelper.AddBeneficiary;
                url=url+"&customerMobile="+phone+"&beneficiaryName="+bname+"&beneficiaryMobileNumber="+bnumber+"&beneficiaryAccountNumber="+baccount+"&ifscCode="+bcode;
                Log.e("talent", "onPostExecute: " + url);
                setData();
            }
        });


        //setData();

    }



    private void setData() {
        if(i==0) {
            new AddBeneficiary.SignIn1().execute();
            progressBar.setVisibility(View.VISIBLE);
            i=1;
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
                    String beneficiaryId = jsonObject1.getString("beneficiaryId");
                    if(error_code.equalsIgnoreCase("200")){
                        Toast.makeText(AddBeneficiary.this, "Suceess", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddBeneficiary.this,BeneficiaryVerify.class).putExtra("beneficiaryId",beneficiaryId));

                    }

                    else {
                        i=0;
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(AddBeneficiary.this, "Failure : "+jsonObject1.getString("resText"), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddBeneficiary.this, DashboardRecharge.class));

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(new Intent(AddBeneficiary.this, MainActivity.class));
    }
}


