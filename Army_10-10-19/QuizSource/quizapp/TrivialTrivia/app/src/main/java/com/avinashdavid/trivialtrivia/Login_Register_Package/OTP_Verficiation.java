package com.avinashdavid.trivialtrivia.Login_Register_Package;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
/*
import com.greenusys.army_project.AppController;
import com.greenusys.army_project.R;
import com.greenusys.army_project.URL;*/

import com.avinashdavid.trivialtrivia.AppController;
import com.avinashdavid.trivialtrivia.R;
import com.avinashdavid.trivialtrivia.URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OTP_Verficiation extends AppCompatActivity {
   EditText ed_buyer_otp;
   Button bt_buy_otp;
   String BuyerRegemail;
    AppController appController;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer__otp__verficiation);
        ed_buyer_otp = findViewById(R.id.et_buyer_otp);
        bt_buy_otp = findViewById(R.id.bt_buy_otp);
        BuyerRegemail = getIntent().getExtras().getString("Buyer_Reg_email");
        Log.e("hotp","hotp"+BuyerRegemail);
        bt_buy_otp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Buyer_otp(ed_buyer_otp.getText().toString());
                    }
                }
        );
        appController = (AppController) getApplicationContext();

    }
    private void Buyer_otp(String otp)
    {
        final ProgressDialog pdLoading = new ProgressDialog(OTP_Verficiation.this);
        RequestBody requestBody = new FormBody.Builder()
                .add("otp",otp)
                .build();
        Request request = new Request.Builder().
                url(URL.otp_key)
                .post(requestBody)
                .build();
        pdLoading.setMessage("Loading...");
        pdLoading.show();
        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Error" + e.getMessage());
                OTP_Verficiation.this.runOnUiThread(
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
                Log.e("hotp","hotp"+myResponse);
                OTP_Verficiation.this.runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject ja= new JSONObject(myResponse);
                                    String stauts = ja.getString("status");
                                    String message = ja.getString("message");
                                    if (stauts.equalsIgnoreCase("1"))
                                    {
                                        System.out.println("Successkaif");

                                       message(message);
                                    }
                                    else
                                    {
                                        System.out.println("errorkaif");

                                        message(message);
                                    }

                                    pdLoading.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
            }
        });
    }
    private void message(String msg) {
        builder = new AlertDialog.Builder(this);
        builder.setMessage(msg) .setTitle("Registration");

        //Setting message manually and performing action on button click
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        startActivity(new Intent(OTP_Verficiation.this,Login.class));
                        finish();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();

        alert.show();
    }
}
