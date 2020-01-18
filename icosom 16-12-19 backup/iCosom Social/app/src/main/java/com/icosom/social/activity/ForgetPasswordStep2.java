package com.icosom.social.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.icosom.social.R;

public class ForgetPasswordStep2 extends AppCompatActivity {

    private EditText edt_code_verify_otp_register;
    private LinearLayout btn_finish_verify_otp_register;
    ProgressBar pb_register2;
    SharedPreferences sp;

    private String email_number;
    private String email_number_type;
    private String email_OTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_step2);
        edt_code_verify_otp_register = findViewById(R.id.edt_code_verify_otp_register);
        btn_finish_verify_otp_register = findViewById(R.id.btn_finish_verify_otp_register);
        pb_register2 = findViewById(R.id.pb_register2);
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        email_number = getIntent().getStringExtra("EMAIL_NUMBER");
        email_number_type = getIntent().getStringExtra("EMAIL_NUMBER_TYPE");
        email_OTP = getIntent().getStringExtra("EMAIL_OTP");


        btn_finish_verify_otp_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb_register2.setVisibility(View.VISIBLE);
                if (edt_code_verify_otp_register.getText().toString().trim().equalsIgnoreCase("")){
                    pb_register2.setVisibility(View.GONE);
                    edt_code_verify_otp_register.setError("Field cannot be empty");
                    return;
                }
                if (edt_code_verify_otp_register.getText().toString().trim().equalsIgnoreCase(email_OTP)){
                    pb_register2.setVisibility(View.GONE);
                    startActivity(new Intent(ForgetPasswordStep2.this, ForgetPasswordStep3.class)
                            .putExtra("EMAIL_NUMBER", email_number)
                            .putExtra("EMAIL_NUMBER_TYPE",  email_number_type)
                            .putExtra("EMAIL_OTP", email_OTP));
                }else {
                    pb_register2.setVisibility(View.GONE);
                    Toast.makeText(ForgetPasswordStep2.this, "OTP doesn't match", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
