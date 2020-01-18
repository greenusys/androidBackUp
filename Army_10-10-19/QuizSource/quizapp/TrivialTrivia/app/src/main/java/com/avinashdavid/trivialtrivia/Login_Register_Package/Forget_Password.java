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

/*import com.greenusys.army_project.AppController;
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

public class Forget_Password extends AppCompatActivity {
    String buyerget_email;
    EditText email;
    Button send;
    AppController appController;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget__password);

        email = findViewById(R.id.email);

        send = findViewById(R.id.send);

        send.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (email.getText().toString().isEmpty()) {
                            email.setError("Please Enter Email Id.");
                            return;
                        }
                        else {

                            Buyer_otp(email.getText().toString());
                        }
                    }
                }
        );
        appController = (AppController) getApplicationContext();

    }

    private void Buyer_otp(String BuyerRegemail)
    {
        final ProgressDialog pdLoading = new ProgressDialog(Forget_Password.this);
        RequestBody requestBody = new FormBody.Builder()
                .add("email",BuyerRegemail)
                .build();
        Request request = new Request.Builder().
                url(URL.buyerforget)
                .post(requestBody)
                .build();
        pdLoading.setMessage("Loading...");
        pdLoading.show();
        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Error" + e.getMessage());
                Forget_Password.this.runOnUiThread(
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
                Forget_Password.this.runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject ja= new JSONObject(myResponse);
                                    String stauts = ja.getString("status");
                                    String message = ja.getString("message");
                                    if (stauts.equalsIgnoreCase("1"))
                                    {
                                        message(message);
                                    }
                                    else
                                    {
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

                        startActivity(new Intent(Forget_Password.this,Login.class));
                        finish();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();

        alert.show();
    }
}
