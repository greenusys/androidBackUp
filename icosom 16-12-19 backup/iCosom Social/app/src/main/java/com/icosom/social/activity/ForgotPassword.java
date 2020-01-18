package com.icosom.social.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.icosom.social.Interface.GetLastIdCallback;
import com.icosom.social.R;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.RequestBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.RequestBody;

public class ForgotPassword extends AppCompatActivity
{
    EditText edt_emailId;
    LinearLayout txt_submit_forgot_password;
    AppController appController;
    ProgressBar pb_register1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        appController = (AppController) getApplicationContext();

        edt_emailId = (EditText) findViewById(R.id.edt_emailId);
        txt_submit_forgot_password =  findViewById(R.id.txt_submit_forgot_password);
        pb_register1 =  findViewById(R.id.pb_register1);
        pb_register1.setVisibility(View.GONE);
        txt_submit_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb_register1.setVisibility(View.VISIBLE);

                if (edt_emailId.getText().toString().equalsIgnoreCase("")){
                    pb_register1.setVisibility(View.GONE);
                    edt_emailId.setError("Enter email or phone number");
                    return;
                }

                final String url = "https://icosom.com/social/main/loginProcess.php?action=forgetPassword";
                RequestBody body = RequestBuilder.twoParameter("email_number",edt_emailId.getText().toString().trim(), "data-source", "android" );

                try {
                    AppController.getInstance().PostTest(url, body, new GetLastIdCallback() {
                        @Override
                        public void lastId(String id) {
                            try {
                                System.out.println("Forget password: " + id);
                                JSONObject jsonObject = new JSONObject(id);
                                String status = jsonObject.getString("status");
                                JSONObject joData = jsonObject.getJSONObject("data");
                                final String email_number = joData.getString("email_number");
                                final String email_number_type = joData.getString("email_number_type");
                                final String email_OTP = joData.getString("email_OTP");

                                if (status.equalsIgnoreCase("1")){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            pb_register1.setVisibility(View.GONE);
                                            Toast.makeText(getBaseContext(), "Check your email", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(ForgotPassword.this, ForgetPasswordStep2.class)
                                                    .putExtra("EMAIL_NUMBER", email_number)
                                                    .putExtra("EMAIL_NUMBER_TYPE",  email_number_type)
                                                    .putExtra("EMAIL_OTP", email_OTP));
                                        }
                                    });
                                }else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            pb_register1.setVisibility(View.GONE);
                                            Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }


}
