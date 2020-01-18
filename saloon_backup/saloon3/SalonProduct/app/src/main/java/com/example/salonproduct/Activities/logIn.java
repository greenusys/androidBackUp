package com.example.salonproduct.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.salonproduct.R;
import com.example.salonproduct.Network.AppController;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class logIn extends AppCompatActivity {


    Button create,forget,bt_sal_login;
    TextInputLayout email_layout,password_layout;
    TextInputEditText email_text,password_text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        initViews();
        setListener();


    }


    private void initViews()
    {
        create = (Button) findViewById(R.id.create);
        forget = (Button) findViewById(R.id.forget);
        bt_sal_login = findViewById(R.id.bt_sal_login);

        email_layout=findViewById(R.id.email_layout);
        password_layout=findViewById(R.id.password_layout);

        email_text=findViewById(R.id.email_text);
        password_text=findViewById(R.id.password_text);

    }
    private void setListener()
    {
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), signup.class);
                startActivity(intent);
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), forgetpass.class);
                startActivity(intent);
            }
        });

        bt_sal_login.setOnClickListener(new View.OnClickListener() {
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

        if(!validatePassword())
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

    private boolean validatePassword()
    {
        if(password_text.getText().toString().equals(""))
        {
            password_layout.setError("Please Enter Password");
            requestFocusCursor(password_text);
            return false;
        }

        else
            password_layout.setErrorEnabled(false);

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
