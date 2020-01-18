package com.example.g116.vvn_social.User_Profile_Dashboard_Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.g116.vvn_social.Network_Package.AppController;
import com.example.g116.vvn_social.R;
import com.example.g116.vvn_social.Session_Package.AlertDialogManager;
import com.example.g116.vvn_social.Session_Package.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Update_Password extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText old_password, new_password, comfirm_new_password;
    Button update_password;
    private SessionManager session;
    String user_id = "";
    String user_type = "";
    private AppController appController;
    AlertDialogManager alert = new AlertDialogManager();
    public static int backfrom = 0;
    Boolean showPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__password);

        this.getSupportActionBar().setTitle("Update Password");
        this.getSupportActionBar().setHomeButtonEnabled(true);

        old_password = (TextInputEditText) findViewById(R.id.old_password);
        new_password = (TextInputEditText) findViewById(R.id.new_password);
        comfirm_new_password = (TextInputEditText) findViewById(R.id.comfirm_new_password);
        update_password = (Button) findViewById(R.id.update_password);
        update_password.setOnClickListener(this);

        appController = (AppController) getApplicationContext().getApplicationContext();
        session = new SessionManager(getApplicationContext());
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        user_id = user.get(SessionManager.KEY_ID);
        user_type = user.get(SessionManager.KEY_USER_TYPE);

        old_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                showPassword(motionEvent, old_password);
                return false;
            }
        });
        new_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                showPassword(motionEvent, new_password);
                return false;
            }
        });
        comfirm_new_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                showPassword(motionEvent, comfirm_new_password);
                return false;
            }
        });


    }

    @Override
    public void onClick(View view) {
        if (view == update_password) {

            String old_pwd = old_password.getText().toString();
            String new_pwd = new_password.getText().toString();
            String comfirm_new_pwd = comfirm_new_password.getText().toString();

            if (TextUtils.isEmpty(old_pwd)) {
                old_password.setError("Please Enter Old Password");
                return;
            } else if (TextUtils.isEmpty(new_pwd)) {
                new_password.setError("Please Enter New Password");
                return;
            } else if (TextUtils.isEmpty(comfirm_new_pwd)) {
                comfirm_new_password.setError("Please Enter Comfirm New Password");
                return;
            } else if (!new_pwd.equalsIgnoreCase(comfirm_new_pwd)) {
                comfirm_new_password.setError("Comfirm Password mismatched");
                return;
            } else {
                if (!isNetworkAvailable(getApplicationContext()))
                    Toast.makeText(getApplicationContext(), "Please Check You Internet Connection", Toast.LENGTH_SHORT).show();

                else
                    update_password(user_id, user_type, old_pwd, new_pwd);
            }


        }



    }

    private void update_password(String user_id, String user_type, String old_pwd, String new_pwd) {

        final ProgressDialog progress = new ProgressDialog(this);
        progress.show();
        progress.setMessage("Loading");
        progress.setCancelable(false);
        Log.e("user_type", "" + user_type);
        Log.e("user_id", "" + user_id);
        Log.e("old_pwd", "" + old_pwd);
        Log.e("new_pwd", "" + new_pwd);


        RequestBody body = new FormBody.Builder().
                add("type", user_type).
                add("user_id", user_id).
                add("current_pass", old_pwd).
                add("new_pass", new_pwd).
                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/change_password.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());


                Update_Password.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.hide();
                        alert.showAlertDialog(Update_Password.this, "VVN CITY.", "Something is Went Wrong Please Try Again Later.", false);

                    }
                });
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    JSONObject jo = new JSONObject(myResponse);
                    Log.e("update_password_status", "" + jo.getString("status"));

                    if (jo.getString("status").equals("1")) {


                        final String message = jo.getString("msg");
                        Log.e("message_update_password", "" + message);


                        if (Update_Password.this != null) {
                            Update_Password.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progress.hide();
                                    alert.showAlertDialog(Update_Password.this, "VVN CITY.", message, false);
                                    old_password.setText("");
                                    new_password.setText("");
                                    comfirm_new_password.setText("");


                                }
                            });
                        }

                    } else {
                        final String message = jo.getString("msg");
                        Log.e("message_update_password", "" + message);
                        if (Update_Password.this != null) {
                            Update_Password.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progress.hide();
                                    alert.showAlertDialog(Update_Password.this, "VVN CITY.", message, false);
                                }
                            });
                        }
                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backfrom = 1;
    }

    public boolean isNetworkAvailable(Context context)//check internet of device
    {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    private void showPassword(MotionEvent event, EditText password) {

        final int DRAWABLE_RIGHT = 2;

        if(event.getAction() == MotionEvent.ACTION_UP) {
            if(event.getRawX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                if (showPassword)
                {
                    password.setTransformationMethod(new PasswordTransformationMethod());
                    password.setSelected(false);
                }
                else
                {
                    password.setTransformationMethod(new HideReturnsTransformationMethod());
                    password.setSelected(true);
                }
                showPassword = !showPassword;
                //return true;
            }
        }

    }

}
