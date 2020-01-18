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

public class CustomerRegistration extends AppCompatActivity {
    CommonFunctions urlHelper;
    AppController appController;
    EditText NUMBER, ename, epincode;
    String phone, name, pincode;
    TextView sub;
    private String url;
    ProgressBar progressBar;
    int i = 0;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CustomerRegistration.this, DashboardRecharge.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);
        progressBar = findViewById(R.id.transfer4);
        appController = (AppController) getApplicationContext();
        urlHelper = new CommonFunctions();
        NUMBER = findViewById(R.id.customerNumber);
        ename = findViewById(R.id.customerName);
        epincode = findViewById(R.id.customerPincode);
        sub = findViewById(R.id.customerRegistration);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = NUMBER.getText().toString();
                name = ename.getText().toString();
                pincode = epincode.getText().toString();
                url = urlHelper.customerRegistration;
                url = url + name + "&customerPincode=" + pincode + "&customerMobile=" + phone;
                setData();
            }
        });


        //setData();

    }


    private void setData() {
        if (i == 0) {
            i = 1;

            new CustomerRegistration.SignIn1().execute();
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
                        Toast.makeText(CustomerRegistration.this, "Suceess", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CustomerRegistration.this, CustomerRegistration.class));
                    } else {
                        i = 0;
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(CustomerRegistration.this, "Failure : " + jsonObject1.getString("resText"), Toast.LENGTH_SHORT).show();

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
        startActivity(new Intent(CustomerRegistration.this, MainActivity.class));
    }

}


