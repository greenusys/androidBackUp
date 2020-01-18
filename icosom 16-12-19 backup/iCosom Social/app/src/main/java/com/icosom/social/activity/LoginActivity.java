package com.icosom.social.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.text.format.Formatter;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.icosom.social.R;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    public static LoginActivity loginActivity;
    AppController appController;
    TextView txt_login;
    ProgressBar pb_login;
    EditText edt_mail;
    EditText edt_password;
    CheckBox cb_rememberMe;
    TextView txt_forgotPassword;
    LinearLayout buttonlogin;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    Boolean showPassword = false;
    String user;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());



        appController = (AppController) getApplicationContext();

        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();

        if (sp.getBoolean("savePassword", false)) {
            startActivity(new Intent(getBaseContext(), MainActivity.class));
            finish();
            return;
        }


        loginActivity = LoginActivity.this;

        edt_mail = findViewById(R.id.edt_mail);
        edt_password = findViewById(R.id.edt_password);
        cb_rememberMe = findViewById(R.id.cb_rememberMe);
        txt_forgotPassword = findViewById(R.id.txt_forgotPassword);
        txt_login = findViewById(R.id.txt_login);
        pb_login = findViewById(R.id.pb_login);
        buttonlogin = findViewById(R.id.buttonlogin);
        txt_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
            }
        });
        pb_login.setVisibility(View.GONE);

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               /* if (cb_rememberMe.isChecked())
                {
                    edt.putBoolean("savePassword", true);
                    edt.commit();
                }*/

                if (edt_mail.getText().toString().isEmpty()) {
                    edt_mail.setError("Please Enter Email Id.");
                    return;
                }

                if (edt_password.getText().toString().isEmpty()) {
                    edt_password.setError("Please Enter Password.");
                    return;
                }
// mail verification
                /*if (!Patterns.EMAIL_ADDRESS.matcher(edt_mail.getText().toString()).matches())
                {
                    edt_mail.setError("Please Enter Valid Email.");
                    return;
                }*/

                /*if (edt_password.getText().toString().length() < 6)
                {
                    edt_password.setError("Password must be 8 characters long.");
                    return;
                }*/
                else {
                    loginPlease(edt_mail.getText().toString(), edt_password.getText().toString());
                }
            }
        });

        (findViewById(R.id.createNewAccount)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), SetUpProfileActivity.class));
            }
        });


        edt_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edt_password.getRight() - edt_password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (showPassword) {
                            edt_password.setTransformationMethod(new PasswordTransformationMethod());
                            edt_password.setSelected(false);
                        } else {
                            edt_password.setTransformationMethod(new HideReturnsTransformationMethod());
                            edt_password.setSelected(true);
                        }
                        showPassword = !showPassword;
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void loginPlease(String email, final String password) {


        final String firebase_token = FirebaseInstanceId.getInstance().getToken();
        FirebaseMessaging.getInstance().subscribeToTopic("kaif");
        Log.e("Login_token", "" + FirebaseInstanceId.getInstance().getToken());


        buttonlogin.setClickable(false);
        txt_login.setVisibility(View.GONE);
        pb_login.setVisibility(View.VISIBLE);

        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("email", email).
                add("password", password).
                add("device_token", firebase_token).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.LOGIN).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        buttonlogin.setClickable(true);
                        txt_login.setVisibility(View.VISIBLE);
                        pb_login.setVisibility(View.GONE);
                        Toast.makeText(getBaseContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    final JSONObject ja = new JSONObject(myResponse);
                    if (ja.has("status")) {
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                buttonlogin.setClickable(true);
                                txt_login.setVisibility(View.VISIBLE);
                                pb_login.setVisibility(View.GONE);
                                try {
                                    Log.e("message", "" + ja.getString("message"));
                                    Toast.makeText(getBaseContext(), "" + ja.getString("message"), Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        return;
                    }

                    LoginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            buttonlogin.setClickable(true);
                            txt_login.setVisibility(View.VISIBLE);
                            pb_login.setVisibility(View.GONE);

                            Log.e("login", "run: " + ja);
                            try {


                                user = ja.getString("id");
                                edt.putBoolean("savePassword", true);
                                edt.commit();
                                edt.putString("userId", ja.getString("id"));
                                edt.putString("firstName", ja.getString("firstName"));
                                edt.putString("lastName", ja.getString("lastName"));
                                edt.putString("email", ja.getString("email"));
                                edt.putString("phone", ja.getString("phone"));
                                edt.putString("profile", ja.getString("profilePicture"));
                                edt.putString("cover", ja.getString("coverPhoto"));
                                edt.putString("birthDate", ja.getString("birthDate"));
                                edt.putString("country", ja.getString("country"));
                                edt.putString("state", ja.getString("state"));
                                edt.putString("city", ja.getString("city"));
                                edt.putString("gender", ja.getString("gender"));
                                edt.putString("address1", ja.getString("address1"));
                                edt.putString("device_token", firebase_token);
                                edt.putString("password", password);


                                edt.commit();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            edt.putBoolean("isLogin", true);
                            edt.commit();

                            notifys(user);
                            // startActivity(new Intent(getBaseContext(), MainActivity.class));
                            // finish();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void notifys(String user) {

        RequestBody body = new FormBody.Builder().
                add("user_id", user).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.notify).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                LoginActivity.this.runOnUiThread(new Runnable() {
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
                    if (ja.has("code")) {
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    Log.e("loginss", "run: " + ja);
                                    edt.putString("request", ja.getString("request"));
                                    edt.putString("notification", ja.getString("notification"));
                                    edt.putString("kyc", ja.getString("kyc"));
                                    edt.commit();
                                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                                    finish();
                                   // Toast.makeText(getBaseContext(), "" + ja.getString("notification"), Toast.LENGTH_SHORT).show();
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