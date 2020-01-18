package com.example.salonproduct.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salonproduct.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class signup extends AppCompatActivity {

    TextInputLayout email_layout,phone_Layout,password_layout,comfirm_password_layout;
    TextInputEditText email_text,phone_text,password_text,comfirm_password_text;
    TextView sign_up;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initviews();
        setListener();

        Button button=(Button) findViewById(R.id.haveAcc);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), logIn.class);
                startActivity(intent);
            }
        });
    }

    private void setListener() {
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkValidations();
            }
        });


    }

    void checkValidations()
    {
        if(!validateEmail())
            return;
        if(!validatePhone())
            return;
        if(!validatePassword())
            return;
        if(!validateComfirmPassword())
            return;

        Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
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

    private boolean validatePhone()
    {
        if(phone_text.getText().toString().equals(""))
        {
            phone_Layout.setError("Please Enter Mobile Number");
            requestFocusCursor(phone_text);
            return false;
        }
        else if(phone_text.getText().toString().length()<10)
        {
            phone_Layout.setError("Please Enter 10 Digit Mobile Number");
            requestFocusCursor(phone_text);
            return false;
        }
        else
            phone_Layout.setErrorEnabled(false);

        return true;
    }

    private boolean validatePassword()
    {
        if(password_text.getText().toString().equals(""))
        {
            password_layout.setError("Please Enter Password");
            requestFocusCursor(password_text);
            return false;
        }
        else if(password_text.getText().toString().length()<5)
        {
            password_layout.setError("Please Enter atleast 5 digit Password");
            requestFocusCursor(password_text);
            return false;
        }

        else
            password_layout.setErrorEnabled(false);

        return true;
    }

    private boolean validateComfirmPassword()
    {
        if(comfirm_password_text.getText().toString().equals(""))
        {
            comfirm_password_layout.setError("Please Enter Comfirm Password");
            requestFocusCursor(comfirm_password_layout);
            return false;
        }
        else if(!isSamePassword(password_text.getText().toString(),comfirm_password_text.getText().toString()))
        {
            comfirm_password_layout.setError("Comfirm Password Do Not Matched");
            requestFocusCursor(comfirm_password_layout);
            return false;
        }
        else
            comfirm_password_layout.setErrorEnabled(false);

        return true;
    }

    boolean isSamePassword(String password,String comfirm_pass)
    {
        return password.equals(comfirm_pass);

    }



    boolean isValidEmail(String email)
    {
        return  Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }




    private void initviews() {




        //TextInput Layouts
        email_layout=findViewById(R.id.email_layout);
        phone_Layout=findViewById(R.id.phone_layout);
        password_layout=findViewById(R.id.password_layout);
        comfirm_password_layout=findViewById(R.id.comfirm_password_layout);


        //TextInputEditText
        email_text=findViewById(R.id.email_text);
        phone_text=findViewById(R.id.phone_text);
        password_text=findViewById(R.id.password_text);
        comfirm_password_text=findViewById(R.id.comfirm_password_text);


        //login field
        sign_up=findViewById(R.id.sign_up);

    }

    private void requestFocusCursor(View view)
    {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
