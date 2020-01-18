package com.avinashdavid.trivialtrivia.Login_Register_Package;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
/*
import com.greenusys.army_project.AppController;
import com.greenusys.army_project.R;
import com.greenusys.army_project.URL;
import com.greenusys.army_project.fragments.IconTextTabsActivity;
import com.greenusys.army_project.soldier_category;*/

import com.avinashdavid.trivialtrivia.AppController;
import com.avinashdavid.trivialtrivia.R;
import com.avinashdavid.trivialtrivia.URL;
import com.avinashdavid.trivialtrivia.soldier_category;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends AppCompatActivity {
    LinearLayout seller_newuser,seller_forgetpassword;
    EditText seller_email,seller_password;
    Button bt_seller_login;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    AppController appController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        seller_email = findViewById(R.id.et_seller_email);
        seller_password = findViewById(R.id.et_seller_password);
        bt_seller_login = findViewById(R.id.seller_signin);

        seller_newuser = findViewById(R.id.seller_newuser);

        seller_forgetpassword = findViewById(R.id.seller_forgetpassword);



        seller_newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Registration.class));
            }
        });

        seller_forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
startActivity(new Intent(Login.this,Forget_Password.class));
            }
        });


        bt_seller_login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (seller_email.getText().toString().isEmpty()) {
                            seller_email.setError("Please Enter Email Id.");
                            return;
                        }

                        if (seller_password.getText().toString().isEmpty()) {
                            seller_password.setError("Please Enter Password.");
                            return;
                        } else {
                            sellerLogin(seller_email.getText().toString(), seller_password.getText().toString());
                            //startActivity(new Intent(Buyer_Login.this,Buyer_Reg.class));

                        }
                        //startActivity(new Intent(Seller_Login.this,Seller_Reg.class));
                    }
                }
        );

        appController = (AppController) getApplicationContext();

    }

    private void sellerLogin(final String email, String password) {
      //  final ProgressDialog pdLoading = new ProgressDialog(Seller_Login.this);
//       pb_login.setVisibility(View.VISIBLE);

        RequestBody body = new FormBody.Builder().add("email", email).add("password", password).build();

        Request request = new Request.Builder().url(URL.seller_login).post(body).build();

       // pdLoading.setMessage("Loading...");
       // pdLoading.show();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Login.this.runOnUiThread(new Runnable() {
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
                        Login.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("status");
                                    if (ja.getString("status").equalsIgnoreCase("0")) {
                                        startActivity(new Intent(Login.this, soldier_category.class));
                                        finish();
                                        Log.e("aaaa2", "onResponse: " +a);
                                        Toast.makeText(appController, "Welcome "+email, Toast.LENGTH_SHORT).show();

                                        Intent intent=new Intent(Login.this, soldier_category.class);
                                        startActivity(intent);

                                    }
                                    else
                                    {
                                        Toast.makeText(appController, "Please Enter Correct Email id and Password", Toast.LENGTH_SHORT).show();
                                       // Intent intent=new Intent(Login.this, Login.class);
                                        //startActivity(intent);
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
        });
    }
}
