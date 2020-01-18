package com.greenusys.personal.registrationapp;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.greenusys.personal.registrationapp.Utility.AppController;
import com.greenusys.personal.registrationapp.Utility.SharedPreference;
import com.greenusys.personal.registrationapp.Utility.UrlHelper;

import java.util.concurrent.Executor;

import okhttp3.MediaType;

public class Student_Attentec extends AppCompatActivity {
    private final String TAG = "Order Placed";
    private static final String LOG_TAG = LoginScreen.class.getSimpleName();
    private SharedPreference sharedPreference;
    private AppController appController;
    UrlHelper urlhelper;
    TextView forgotPassword;
    private EditText emailEditText;
    private TextInputEditText passwordText;
    private Button registrationButton, loginButton;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String POST_URL = " ";
    private static Executor networkIo;
    private UrlHelper urlHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__attentec);
    }
}
