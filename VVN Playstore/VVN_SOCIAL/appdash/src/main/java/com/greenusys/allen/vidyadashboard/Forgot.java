package com.greenusys.allen.vidyadashboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

public class Forgot extends AppCompatActivity {
    String message, code;
    EditText UserName;
    Button repass;
    String url = "http://vvn.city/apps/jain/forget.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_dash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        UserName = (EditText) findViewById(R.id.femail);

        repass = (Button) findViewById(R.id.repass);

        repass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user ;
                user = UserName.getText().toString();
                if(user.length()==0) {
                    UserName.setError("UserName is must not be empty");
                    UserName.requestFocus();
                }
                else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                code = jsonObject.getString("code");
                                message = jsonObject.getString("message");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // Toast.makeText(Forget.this, message , Toast.LENGTH_SHORT).show();
                            Intent forg = new Intent(Forgot.this, Login_Dash.class);
                            startActivity(forg);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Forgot.this, "Check your Internet Connection..", Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("user", user);

                            return params;
                        }
                    };


                    MySingleton.getInstance(getApplication()).addToRequestque(stringRequest);
                }
            }


        });

    }
}

