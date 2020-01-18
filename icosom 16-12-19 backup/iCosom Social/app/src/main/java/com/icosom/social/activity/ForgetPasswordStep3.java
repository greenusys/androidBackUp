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

public class ForgetPasswordStep3 extends AppCompatActivity {

    private EditText edt_password_verify_otp_register3;
    private EditText edt_confirm_password_verify_otp_register3;
    private LinearLayout btn_finish_verify_otp_register3;
    ProgressBar pb_register3;

    private String email_number;
    private String email_number_type;
    private String email_OTP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_step3);

        email_number = getIntent().getStringExtra("EMAIL_NUMBER");
        email_number_type = getIntent().getStringExtra("EMAIL_NUMBER_TYPE");
        email_OTP = getIntent().getStringExtra("EMAIL_OTP");

        edt_password_verify_otp_register3 = findViewById(R.id.edt_password_verify_otp_register3);
        edt_confirm_password_verify_otp_register3 = findViewById(R.id.edt_confirm_password_verify_otp_register3);
        btn_finish_verify_otp_register3 = findViewById(R.id.btn_finish_verify_otp_register3);
        pb_register3 = findViewById(R.id.pb_register3);


        btn_finish_verify_otp_register3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb_register3.setVisibility(View.VISIBLE);

                if (edt_password_verify_otp_register3.getText().toString().equalsIgnoreCase("")){
                    edt_password_verify_otp_register3.setError("Field cannot be empty");
                    pb_register3.setVisibility(View.GONE);
                    return;
                }
                if (edt_confirm_password_verify_otp_register3.getText().toString().equalsIgnoreCase("")){
                    pb_register3.setVisibility(View.GONE);
                    edt_confirm_password_verify_otp_register3.setError("Field cannot be empty");
                    return;
                }
                if (edt_password_verify_otp_register3.getText().toString().length()<6){
                    pb_register3.setVisibility(View.GONE);
                    edt_password_verify_otp_register3.setError("Password length must be 6 characters");
                    return;
                }

                if (edt_confirm_password_verify_otp_register3.getText().toString().length()<6){
                    pb_register3.setVisibility(View.GONE);
                    edt_confirm_password_verify_otp_register3.setError("Password length must be 6 characters");
                    return;
                }

                if (!edt_password_verify_otp_register3.getText().toString().trim().equalsIgnoreCase(edt_confirm_password_verify_otp_register3.getText().toString().trim())){
                    pb_register3.setVisibility(View.GONE);
                    edt_confirm_password_verify_otp_register3.setError("Password and confirm password doesn't match");
                    return;
                }


                final String url = "https://icosom.com/social/main/loginProcess.php?action=recoverPassword";

                String key_a = "data-source";
                String key_b = "email_number";
                String key_c = "email_number_type";
                String key_d = "password";

                RequestBody body = RequestBuilder.fourParameter(key_a , "android", key_b, email_number, key_c, email_number_type, key_d, edt_confirm_password_verify_otp_register3.getText().toString().trim() );

                try {
                    AppController.getInstance().PostTest(url, body, new GetLastIdCallback() {
                        @Override
                        public void lastId(String id) {
                            try {
                                JSONObject jsonObject = new JSONObject(id);
                                String status = jsonObject.getString("status");
                                if (status.equalsIgnoreCase("1")){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            pb_register3.setVisibility(View.GONE);
                                            Toast.makeText(ForgetPasswordStep3.this, "Credentials changed... Login again", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(ForgetPasswordStep3.this, LoginActivity.class));
                                        }
                                    });

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        pb_register3.setVisibility(View.GONE);
                                        Toast.makeText(ForgetPasswordStep3.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                });
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
