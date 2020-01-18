package com.example.salonproduct.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.salonproduct.R;
import com.example.salonproduct.Network.AppController;

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

public class logIn extends AppCompatActivity {
    AppController appController;
    Button bt_login;
    EditText user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Button button = (Button) findViewById(R.id.create);
        Button button1 = (Button) findViewById(R.id.forget);
        bt_login = findViewById(R.id.bt_sal_login);
        user = findViewById(R.id.name3);
        pass = findViewById(R.id.pass2);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setIcon(R.drawable.salon);
        getSupportActionBar().hide();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), signup.class);
                startActivity(intent);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), forgetpass.class);
                startActivity(intent);
            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPlease(user.getText().toString(), pass.getText().toString());
            }
        });
        appController = (AppController) getApplicationContext();
    }


    private void loginPlease(final String email, final String password) {
        final ProgressDialog pdLoading = new ProgressDialog(logIn.this);
        RequestBody body = new FormBody.Builder().
                add("email", email).
                add("password", password).
                build();
        Request request = new Request.Builder().
                url("https://greenusys.com/salon/User-Login-Validation").
                post(body).
                build();
        pdLoading.setMessage("Loading...");
        pdLoading.show();
        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                logIn.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                Log.e("hhh", "onResponse: " + myResponse);

                try {
                    final JSONObject ja1 = new JSONObject(myResponse);
                    if (ja1.getString("status").equals("1")) {

                        JSONArray jsonArray = ja1.getJSONArray("data");
                        JSONObject ja = null;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            ja = jsonArray.getJSONObject(i);





                        }
                        startActivity(new Intent(logIn.this, HomeActivity.class));
                        System.out.println(ja);
                        pdLoading.dismiss();
                    } else {

                        if (logIn.this != null) {
                            logIn.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    pdLoading.dismiss();
                                    Toast.makeText(appController, "Please Enter Valid Email or Password", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }


                    }

                    return;


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
