package com.greenusys.personal.registrationapp;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.greenusys.personal.registrationapp.Utility.AppController;
import com.greenusys.personal.registrationapp.Utility.RequestBuilder;
import com.greenusys.personal.registrationapp.Utility.SharedPreference;
import com.greenusys.personal.registrationapp.Utility.UrlHelper;
import com.greenusys.personal.registrationapp.data.MciContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginScreen extends AppCompatActivity {
    private final String TAG = "Order Placed";
    private static final String LOG_TAG = LoginScreen.class.getSimpleName();
    private SharedPreference sharedPreference;
    private AppController appController;
    UrlHelper urlhelper;
    TextView forgotPassword;
    private EditText emailEditText;
    private TextInputEditText passwordText;
    private Button registrationButton, loginButton;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String POST_URL = " ";
    private static Executor networkIo;
    private UrlHelper urlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        sharedPreference = new SharedPreference(getBaseContext());


        //set title and back button
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Student Login");

        networkIo = Executors.newFixedThreadPool(1);

        appController = (AppController) getApplicationContext();
        urlhelper = new UrlHelper();
        emailEditText = (EditText) findViewById(R.id.editTextEmail);
        passwordText = (TextInputEditText) findViewById(R.id.etPassword);
        forgotPassword  = findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inn = new Intent(LoginScreen.this, Forgot.class);
                startActivity(inn);
            }
        });
        urlhelper = new UrlHelper();
        registrationButton = (Button) findViewById(R.id.registerButton);

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(LoginScreen.this, RegistrationActivity.class);
                startActivity(in);
                finish();

            }
        });
        loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordText.getText().toString();
                if (email.length() == 0) {
                    emailEditText.setError("Email is not entered");
                    emailEditText.requestFocus();
                } else if (password.length() == 0) {
                    passwordText.setError("Password is not entered");
                    passwordText.requestFocus();
                } else {
                    new SignIn().execute(email, password);
                /*Intent in = new Intent(LoginScreen.this,MainActivity.class);
                startActivity(in);
                finish();*/
                }
            }
        });

    }


    private class SignIn extends AsyncTask<String, Void, String> {

        private String key_a = "email";
        private String value_a = "";
        private String key_b = "password";
        private String value_b = "";
        private String key_c = "data-source";
        private String value_c = "android";
        private String response;
        private RequestBody body;
        private String url = urlhelper.login;

        @Override
        protected String doInBackground(String... strings) {

            value_a = strings[0];
            value_b = strings[1];
            Log.e("back", "doInBackground: " + value_a + value_b);
            body = RequestBuilder.threeParameter(
                    key_a, value_a, key_b, value_b, key_c, value_c);
            Log.e("back11111", "doInBackground: " + body);
            try {
                response = appController.POST(url, body);

                Log.d(LOG_TAG, response);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;

        }

        @Override
        protected void onPostExecute(String s) {

            if (s != null) {
                try {

                    JSONObject rootObject = new JSONObject(response);

                    String id = rootObject.getString("id");
                    String name = rootObject.getString("name");
                    String email = rootObject.getString("email");
                    String phnNumber = rootObject.getString("phone_number");
                    String gender = rootObject.getString("gender");
                    String password = rootObject.getString("password");
                    String clss = rootObject.getString("class");
                    String userType = rootObject.getString("user_type");
                    String clsses = rootObject.getString("classes");

                    String status = rootObject.getString("status");

                    //save user details to shared preference once we get the response from the server
                    SharedPreference.saveLoginState(LoginScreen.this, name);

                    JSONObject jsonObject = new JSONObject(response);

                    Log.d(LOG_TAG,"CLASS ID IS " + clss);





                    Log.d(LOG_TAG,"USER ID IS " +  id);

                    UrlHelper.classId = clss;

                    UrlHelper.userId = id;


                    ContentValues cv = new ContentValues();

                    cv.put(MciContract.MciEntry.COLUMN_NAME, name);
                    cv.put(MciContract.MciEntry.COLUMN_EMAIL, email);
                    cv.put(MciContract.MciEntry.COLUMN_ID, id);
                    cv.put(MciContract.MciEntry.COLUMNN_NUMBER, phnNumber);
                    cv.put(MciContract.MciEntry.COLUMNN_CLASS, clss);
                    cv.put(MciContract.MciEntry.COLUMN_USER_TYPE, userType);
                    cv.put(MciContract.MciEntry.COLUMN_GENDER, gender);
                    cv.put(MciContract.MciEntry.COLUMN_CLASSES, clsses);

                    networkIo.execute(() -> {

                        Uri row = getContentResolver().insert(MciContract.MciEntry.CONTENT_URI, cv);

                        Log.d(LOG_TAG, row.toString());

                    });


                    if (status.equalsIgnoreCase("1")) {
                        Intent in = new Intent(LoginScreen.this, MainActivity.class);
                        in.putExtra("class_id",clss);
                        startActivity(in);
                        finish();
                    } else {
                        Toast.makeText(LoginScreen.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(LoginScreen.this, "" + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(LoginScreen.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

       // finishAffinity();
    }
}