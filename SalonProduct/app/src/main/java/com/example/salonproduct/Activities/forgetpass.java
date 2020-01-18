package com.example.salonproduct.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salonproduct.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class forgetpass extends AppCompatActivity {

    TextInputLayout email_layout;
    TextInputEditText email_text;
    TextView submit_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);
        initViews();
        setListener();
    }


    private void initViews()
    {

        submit_email = findViewById(R.id.submit_email);
        email_layout=findViewById(R.id.email_Layout);
        email_text=findViewById(R.id.email_text);


    }

    private void setListener()
    {

        submit_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkValidations();
            }
        });

    }

    private void checkValidations()
    {
        if(!validateEmail())
            return;


        Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();


    }

    private boolean validateEmail()
    {
        if(email_text.getText().toString().equals(""))
        {
            email_layout.setError("Please Enter Email Address");
            requestFocusCursor(email_text);
            return false;
        }
        else if(!isValidEmail(email_text.getText().toString()))
        {
            email_layout.setError("Please Enter Valid Email Address");
            requestFocusCursor(email_text);
            return false;
        }
        else
            email_layout.setErrorEnabled(false);



        return true;
    }

    boolean isValidEmail(String email)
    {
        return  Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocusCursor(View view)
    {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
