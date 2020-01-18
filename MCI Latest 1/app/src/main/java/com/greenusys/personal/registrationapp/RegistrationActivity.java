package com.greenusys.personal.registrationapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.greenusys.personal.registrationapp.Utility.AppController;
import com.greenusys.personal.registrationapp.Utility.RequestBuilder;
import com.greenusys.personal.registrationapp.Utility.UrlHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;

public class RegistrationActivity extends AppCompatActivity {
    EditText name, email, number, password, confirmPassword;
    private AppController appController;
    UrlHelper urlhelper;
    Button register;
    RadioButton male, female;
    RadioGroup rg;
    Spinner spinner;
    String selectedSuperStar = "male";
    private AwesomeValidation awesomeValidation;
    List<String> spinnerArray;
    List<String> idArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        //set title and back button
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Student Registration");

        appController = (AppController) getApplicationContext();
        spinnerArray = new ArrayList<>();
        idArray = new ArrayList<>();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        new RegistrationActivity.SignIn1().execute();
        name = findViewById(R.id.name_text_view);
        email = findViewById(R.id.email_text_view);
        number = findViewById(R.id.number_text_view);
        password = findViewById(R.id.edit_text_password);
        confirmPassword = findViewById(R.id.edit_text_confirm_password);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        register = findViewById(R.id.register);
        rg = findViewById(R.id.radiogroup);
        spinner = findViewById(R.id.class_spinner);
        awesomeValidation.addValidation(this, R.id.name_text_view, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.email_text_view, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String classs= null;
                if(spinner != null && spinner.getSelectedItem() !=null ) {

                  int pos =  spinner.getSelectedItemPosition();
                  classs = idArray.get(pos);
                }
                String names = name.getText().toString();
                String emails = email.getText().toString();
                String numbers = number.getText().toString();
                String passwords = password.getText().toString();
                String confirmPasswords = confirmPassword.getText().toString();
                if (male.isChecked()) {
                    selectedSuperStar = male.getText().toString();
                } else if (female.isChecked()) {
                    selectedSuperStar = female.getText().toString();
                }

                if (names.length() == 0) {
                    name.setError("Name is not entered");
                    name.requestFocus();
                } else if (emails.length() == 0) {
                    email.setError("Email is not entered");
                    email.requestFocus();
                } else if (numbers.length() == 0) {
                    number.setError("Number is not entered");
                    number.requestFocus();
                }
                else if (classs.length() == 0) {
                    Toast.makeText(RegistrationActivity.this, "class must not empty", Toast.LENGTH_SHORT).show();
                }else {
                    if(awesomeValidation.validate()) {
                        if (confirmPasswords.equals(passwords)) {
                            new RegistrationActivity.SignIn().execute(names, emails, numbers, selectedSuperStar, classs, passwords);
                        } else {
                            password.setText("");
                            confirmPassword.setText("");
                            Toast.makeText(RegistrationActivity.this, "Password and Confirm password donot match", Toast.LENGTH_SHORT).show();
                        }
                /*Intent in = new Intent(LoginScreen.this,MainActivity.class);
                startActivity(in);
                finish();*/
                    } }
            }
        });



    }
    private class SignIn1 extends AsyncTask<String, Void, String> {

        private String key_a = "data-source";
        private String value_a = "android";
        private String response;
        private RequestBody body;
        private String url = urlhelper.classs;

        @Override
        protected String doInBackground(String... strings) {

            Log.e("back", "doInBackground: " + value_a);
            body = RequestBuilder.Parameter(
                    key_a, value_a);

            try {
                response = appController.POST(url, body);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("RESSS", "onPostExecute: " +  s);
            if (s != null) {
                try {

                    JSONObject rootObject = new JSONObject(response);
                    JSONArray jsonArray = rootObject.getJSONArray("classdata");

                    for(int i=0; i<jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        spinnerArray.add(jsonObject.getString("classes"));
                        idArray.add(jsonObject.getString("id"));
                    }

                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<String>(RegistrationActivity.this,
                                    android.R.layout.simple_spinner_item, spinnerArray);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void bui() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(RegistrationActivity.this);
        builder1.setMessage("Please wait for admin approval");
        builder1.setCancelable(false);

        builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Intent ins = new Intent(RegistrationActivity.this, LoginScreen.class);
                startActivity(ins);
                finish();
            }
        });


        AlertDialog alert11 = builder1.create();
        alert11.show();


    }
    private class SignIn extends AsyncTask<String, Void, String> {

        private String key_a = "name";
        private String value_a = "";
        private String key_b = "email";
        private String value_b = "";
        private String key_c = "number";
        private String value_c = "";
        private String key_d = "gender";
        private String value_d = "";
        private String key_e = "class";
        private String value_e = "";
        private String key_f = "password";
        private String value_f = "";

        private String response;
        private RequestBody body;
        private String url = urlhelper.registration;

        @Override
        protected String doInBackground(String... strings) {

            value_a = strings[0];
            value_b = strings[1];
            value_c = strings[2];
            value_d = strings[3];
            value_e = strings[4];
            value_f = strings[5];


            Log.e("back", "doInBackground: " + value_a + value_b+value_c+value_d+value_e+value_f);
            body = RequestBuilder.sixParameter(
                    key_a, value_a, key_b, value_b,key_c, value_c, key_d, value_d,key_e, value_e, key_f, value_f);

            try {
                response = appController.POST(url, body);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("RES", "onPostExecute: " +  s);
            if (s != null) {
                try {

                    JSONObject jsonObject = new JSONObject(response);


                    String CODE = jsonObject.getString("status");


                    if (CODE.equalsIgnoreCase("1")) {
                        bui();
                    } else {
                        Toast.makeText(RegistrationActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
