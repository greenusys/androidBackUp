package com.example.g116.vvn_social.Login_Registration_API;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.g116.vvn_social.Home_Activities.MainActivity;
import com.example.g116.vvn_social.Network_Package.AppController;
import com.example.g116.vvn_social.R;
import com.example.g116.vvn_social.Session_Package.AlertDialogManager;
import com.example.g116.vvn_social.Session_Package.SessionManager;
import com.greenusys.allen.vidyadashboard.DatabaseHelper_Dash;


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

    AppController appController;
    TextView txt_login;
    ProgressBar pb_login;
    EditText edt_mail;
    EditText edt_password;
    CheckBox cb_rememberMe;
    TextView txt_forgotPassword;
    Button buttonlogin;
    public static LoginActivity loginActivity;
    Boolean showPassword = false;
    String user_type = "";
    private SessionManager session;
    private final AppCompatActivity activity = LoginActivity.this;
    AlertDialogManager alert = new AlertDialogManager();
    private DatabaseHelper_Dash databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        appController = (AppController) getApplicationContext();
        loginActivity = LoginActivity.this;


        databaseHelper = new DatabaseHelper_Dash(activity);
        session = new SessionManager(getApplicationContext());


        if (getIntent().getStringExtra("user_type") != null)
            user_type = getIntent().getStringExtra("user_type");


        Log.e("user_type_value", "skdfj  " + user_type);

        edt_mail = findViewById(R.id.edt_mail);
        edt_password = findViewById(R.id.edt_password);
        // cb_rememberMe = findViewById(R.id.cb_rememberMe);
        txt_forgotPassword = findViewById(R.id.txt_forgotPassword);
        // txt_login = findViewById(R.id.txt_login);
        // pb_login = findViewById(R.id.pb_login);
        buttonlogin = findViewById(R.id.buttonlogin);
        /*txt_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivitydasb.this,ForgotPassword.class));
            }
        });*/
        //pb_login.setVisibility(View.GONE);

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (edt_mail.getText().toString().isEmpty()) {
                    edt_mail.setError("Please Enter Email Id.");
                    return;
                } else if (edt_password.getText().toString().isEmpty()) {
                    edt_password.setError("Please Enter Password.");
                    return;
                } else {
                    //startActivity(new Intent(getBaseContext(), MainActivity_ERP.class));
                    Log.e("login1", "ksdf");
                    if (!isNetworkAvailable(getApplicationContext()))
                        Toast.makeText(getApplicationContext(), "Please Check You Internet Connection", Toast.LENGTH_SHORT).show();
                    else
                        login(edt_mail.getText().toString(), edt_password.getText().toString());


                }
            }
        });

        (findViewById(R.id.createNewAccount)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), Registration.class).putExtra("user_type", user_type));

            }
        });


        edt_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                showPassword(motionEvent, edt_password);
                return false;
            }
        });

    }

    public boolean isNetworkAvailable(Context context)//check internet of device
    {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private void showPassword(MotionEvent event, EditText password) {

        final int DRAWABLE_RIGHT = 2;

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getRawX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                if (showPassword) {
                    password.setTransformationMethod(new PasswordTransformationMethod());
                    password.setSelected(false);
                } else {
                    password.setTransformationMethod(new HideReturnsTransformationMethod());
                    password.setSelected(true);
                }
                showPassword = !showPassword;
                //return true;
            }
        }

    }


    private void login(final String email, String password) {

        final ProgressDialog progress = new ProgressDialog(this);
        progress.show();
        progress.setMessage("Loading");
        progress.setCancelable(false);
        Log.e("login2", "ksdf");

        RequestBody body = new FormBody.Builder().
                add("type", user_type).
                add("email", email).
                add("password", password).
                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/login.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("login3", "ksdf");
                        progress.dismiss();
                        alert.showAlertDialog(LoginActivity.this, "VVN CITY.", "Something is Went Wrong Please Try Again Later.", false);
                        // Toast.makeText(getBaseContext(), "Something is Went Wrong Please Try Again Later. ", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    final JSONObject mainjson = new JSONObject(myResponse);
                    if (mainjson.has("msg")) {
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Log.e("login4", "ksdf");
                                    String message = mainjson.getString("msg");
                                    String path = "https://vvn.city/";
                                    if (message.equalsIgnoreCase("Login Successfull")) {


                                        if (user_type.equals("student")) {

                                            boolean updateData = databaseHelper.addUser(activity, email);
                                            System.out.println("kaif_update_data" + updateData);


                                            JSONObject jsonObject = mainjson.getJSONObject("data");

                                            String full_name = jsonObject.getString("firstName") + " " +
                                                    jsonObject.getString("lastName");
                                            String educationDetails = jsonObject.getString("educationDetails") != null ? jsonObject.getString("educationDetails") : " ";
                                            String passYear = jsonObject.getString("passYear") != null ? jsonObject.getString("passYear") : " ";
                                            String maritalStatus = jsonObject.getString("maritalStatus") != null ? jsonObject.getString("maritalStatus") : " ";
                                            String birthDate = !jsonObject.getString("birthDate").equals("0000-00-00") ? jsonObject.getString("birthDate") : " ";
                                            String gender = jsonObject.getString("gender") != null ? jsonObject.getString("gender") : " ";
                                            ;
                                            String address = jsonObject.getString("address") != null ? jsonObject.getString("address") : " ";
                                            ;
                                            String password = jsonObject.getString("pass");
                                            String id = jsonObject.getString("sno");
                                            String mobile = !jsonObject.getString("phone").equals("0") ? jsonObject.getString("phone") : " ";
                                            ;
                                            String email = jsonObject.getString("email");
                                            String picture = jsonObject.getString("picture");
                                            picture = picture.replace("../", "");
                                            String class_course = jsonObject.getString("class") != null ? jsonObject.getString("class") : " ";
                                            ;
                                            String state = jsonObject.getString("state");
                                            String city = jsonObject.getString("city");

                                            session.createLoginSession(id, full_name, email, mobile, path + picture, user_type, class_course, state, city,
                                                    educationDetails, passYear, maritalStatus, birthDate, gender, address, password);

                                            startActivity(new Intent(getBaseContext(), MainActivity.class));

                                        }
                                        if (user_type.equals("teacher")) {

                                            JSONObject jsonObject = mainjson.getJSONObject("data");


                                            String id = jsonObject.getString("sno");
                                            String full_name = jsonObject.getString("firstName") + " " +
                                                    jsonObject.getString("lastName");

                                            String educationDetails = jsonObject.getString("educationDetails") != null ? jsonObject.getString("educationDetails") : " ";
                                            String maritalStatus = jsonObject.getString("t_marital_status") != null ? jsonObject.getString("t_marital_status") : " ";
                                            String birthDate = !jsonObject.getString("dob").equals("0000-00-00") ? jsonObject.getString("dob") : " ";
                                            String gender = jsonObject.getString("gender") != null ? jsonObject.getString("gender") : " ";
                                            ;
                                            String address = jsonObject.getString("address") != null ? jsonObject.getString("address") : " ";
                                            ;
                                            String password = jsonObject.getString("pwd");
                                            String mobile = !jsonObject.getString("phone").equals("0") ? jsonObject.getString("phone") : " ";
                                            String email = jsonObject.getString("email");
                                            String picture = jsonObject.getString("picture");


                                            String state = jsonObject.getString("state");
                                            String city = jsonObject.getString("city");

                                            Log.e("User_path+picture", "" + path + picture);


                                            //creating user's session
                                            session.createLoginSession(id, full_name, email, mobile, path + picture, user_type, "", state, city,
                                                    educationDetails, "", maritalStatus, birthDate, gender, address, password);

                                            startActivity(new Intent(getBaseContext(), MainActivity.class));

                                        }


                                    } else {
                                        Log.e("login5", "ksdf");
                                        message = mainjson.getString("msg");
                                        //showAlert(message);
                                        alert.showAlertDialog(LoginActivity.this, "Login Failed..", message, false);

                                        //Toast.makeText(appController, status, Toast.LENGTH_SHORT).show();


                                    }
                                    progress.dismiss();


                                } catch (JSONException e) {
                                    Log.e("login6", "ksdf");
                                    progress.dismiss();

                                    e.printStackTrace();
                                }
                            }
                        });
                        return;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    progress.dismiss();

                    Log.e("login8", "ksdf");
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(getBaseContext(), Login_Type_Activity.class));


    }


}