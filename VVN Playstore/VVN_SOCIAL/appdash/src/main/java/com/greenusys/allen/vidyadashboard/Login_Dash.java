package com.greenusys.allen.vidyadashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login_Dash extends AppCompatActivity {
    private DatabaseHelper_Dash databaseHelper;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    String message, code;
    EditText UserName, Password;
    String username, password;
    TextView forgot, account;
    Button sign;
    SessionManagement_Dash session;
    String url = "https://vvn.city/apps/jain/login.php";
    private final AppCompatActivity activity = Login_Dash.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_dash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        session = new SessionManagement_Dash(getApplicationContext());
    }
        // Toast.makeText(getApplicationContext(), "User Login_Dash Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

    /*    if(session.isLoggedIn()==true)
        {

            Intent mm = new Intent(getApplicationContext(), MainActivity_Dash.class);
            startActivity(mm);
        }
        databaseHelper = new DatabaseHelper_Dash(activity);


        UserName = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.pass);
        forgot = (TextView) findViewById(R.id.forgot);
        account = (TextView) findViewById(R.id.account);
        sign = (Button) findViewById(R.id.sign);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = UserName.getText().toString();
                password = Password.getText().toString();
                if(username.length()==0) {
                    UserName.setError("UserName is must not be empty");
                    UserName.requestFocus();
                }
                if(password.length()==0) {
                    Password.setError("Password is must not be empty");
                    Password.requestFocus();
                }
                else {


                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                boolean updateData = databaseHelper.addUser(activity,username);
                                code = jsonObject.getString("code");
                                message = jsonObject.getString("message");
                                session.createLoginSession("somya", "s@gmail.com");

                                if(code.equalsIgnoreCase("1"))
                                {
                                    if(updateData) {
                                        final ProgressDialog pd = new ProgressDialog(Login_Dash.this);
                                        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                                        pd.setTitle("Please Wait");
                                        pd.setMessage("Loading...");

                                        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));

                                        pd.setIndeterminate(false);

                                        pd.show();

                                        // Set the progress status zero on each button click
                                        progressStatus = 0;

                                        // Start the lengthy operation in a background thread
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                while(progressStatus < 100){
                                                    // Update the progress status
                                                    progressStatus +=1;

                                                    // Try to sleep the thread for 20 milliseconds
                                                    try{
                                                        Thread.sleep(20);
                                                    }catch(InterruptedException e){
                                                        e.printStackTrace();
                                                    }

                                                    // Update the progress bar
                                                    handler.post(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            // Update the progress status
                                                            pd.setProgress(progressStatus);
                                                            // If task execution completed
                                                            if(progressStatus == 100){
                                                                Intent ikk = new Intent(getApplicationContext(), MainActivity_Dash.class);
                                                                startActivity(ikk);
                                                                finish();
                                                                // Dismiss/hide the progress dialog
                                                                pd.dismiss();
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        }).start(); // Start the operation
                                    }}
                                else
                                {
                                    Toast.makeText(Login_Dash.this, "invalid username and password", Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Login_Dash.this, "Check your Internet Connection...", Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("username", username);
                            params.put("password", password);
                            return params;
                        }
                    };


                    MySingleton.getInstance(getApplication()).addToRequestque(stringRequest);

                }


            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(Login_Dash.this, Forgot.class);
                startActivity(ii);
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(Login_Dash.this, Registration_Dash.class);
                startActivity(ii);
            }
        });
    }*/
}

