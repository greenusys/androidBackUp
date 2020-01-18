package com.icosom.social.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.icosom.social.R;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;
import com.icosom.social.utility.RequestBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.RequestBody;

public class Wallet_otp extends AppCompatActivity {
    AppController appController;
    CommonFunctions urlHelper;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    String user_id, phone;
    ProgressBar progressBar;
    LinearLayout lin;
    EditText pin;
    String spin, wallet_pin_temp, wallet_pin_OTP, wallet_pin_otp_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_otp);

        wallet_pin_otp_time = getIntent().getStringExtra("wallet_pin_otp_time");
        wallet_pin_OTP = getIntent().getStringExtra("wallet_pin_OTP");
        wallet_pin_temp = getIntent().getStringExtra("wallet_pin_temp");


        appController = (AppController) getApplicationContext();
        urlHelper = new CommonFunctions();
        progressBar = findViewById(R.id.pb_otp);
        lin = findViewById(R.id.button_wallet_otp);
        pin = findViewById(R.id.edt_otp_create);
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();
        user_id = sp.getString("userId", "");


        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spin = pin.getText().toString();


                if (pin.getText().toString().isEmpty()) {
                    pin.setError("Please Enter Otp");
                    return;
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    new Wallet_otp.SignIn1().execute(spin, user_id);

                }
            }
        });


    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Wallet_otp.this, WalletPin.class));
    }*/

    private class SignIn1 extends AsyncTask<String, Void, String> {

        private String key_a = "data-source";
        private String value_a = "android";
        private String key_b = "otp";
        private String value_b = "";
        private String key_c = "userId";
        private String value_c = "";
        private String key_d = "wallet_pin_temp";
        private String value_d = wallet_pin_temp;
        private String key_e = "wallet_pin_OTP";
        private String value_e = wallet_pin_OTP;
        private String key_f = "wallet_pin_otp_time";
        private String value_f = wallet_pin_otp_time;

        private String response;
        private RequestBody body;
        private String url = urlHelper.RESETPIN2;

        @Override
        protected String doInBackground(String... strings) {
            value_b = strings[0];
            value_c = strings[1];

            Log.e("back", "doInBackground: " + value_a + value_d);
            body = RequestBuilder.sixParameter(
                    key_a, value_a, key_b, value_b, key_c, value_c, key_d, value_d, key_e, value_e, key_f, value_f);

            try {
                response = appController.POST(url, body);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("RESSS", "onPostExecute: " + s);
            if (s != null) {
                try {
                    Log.e("Wallet_otp", "onPostExecute: " + response);
                    JSONObject rootObject = new JSONObject(response);
                    String statuss = rootObject.getString("status");

                    if (statuss.equalsIgnoreCase("1")) {

                        Toast.makeText(Wallet_otp.this, "" + rootObject.getString("message"), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Wallet_otp.this, WalletPin.class));

                    }
                    if (statuss.equalsIgnoreCase("0")) {
                        Toast.makeText(Wallet_otp.this, "" + rootObject.getString("message"), Toast.LENGTH_SHORT).show();

                    }

                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}

