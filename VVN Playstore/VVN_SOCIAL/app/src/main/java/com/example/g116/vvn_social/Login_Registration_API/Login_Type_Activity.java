package com.example.g116.vvn_social.Login_Registration_API;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.g116.vvn_social.R;
import com.example.g116.vvn_social.Session_Package.SessionManager;

public class Login_Type_Activity extends AppCompatActivity {

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__type_);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();//check user is logged in or not if not it will navigate to Login Page
    }

    public void student_login(View view) {

        startActivity(new Intent(getBaseContext(),LoginActivity.class).putExtra("user_type","student"));
        //startActivity(new Intent(getApplicationContext(), MainActivity_ERP.class));
    }

    public void teacher_login(View view) {

        startActivity(new Intent(getBaseContext(),LoginActivity.class).putExtra("user_type","teacher"));

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }


}
