package com.greenusys.personal.registrationapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.greenusys.personal.registrationapp.Utility.AppController;
import com.greenusys.personal.registrationapp.Utility.RequestBuilder;
import com.greenusys.personal.registrationapp.Utility.UrlHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.RequestBody;

public class Forgot extends AppCompatActivity {
    EditText email;
    private AppController appController;
    UrlHelper urlhelper;
    Button forgot;
    String emails;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        appController = (AppController) getApplicationContext();
        email = findViewById(R.id.forgotEmailNew);
      //  awesomeValidation.addValidation(this, R.id.forgotEmailNew, Patterns.EMAIL_ADDRESS, R.string.emailerror);

        forgot = findViewById(R.id.forgotButton);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emails = email.getText().toString();
                new Forgot.SignIn1().execute(emails);
            }
        });


    }

    private void bui(String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Forgot.this);
        builder1.setMessage(msg);
        builder1.setCancelable(false);

        builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Intent ins = new Intent(Forgot.this, LoginScreen.class);
                startActivity(ins);
                finish();
            }
        });


        AlertDialog alert11 = builder1.create();
        alert11.show();


    }

    private class SignIn1 extends AsyncTask<String, Void, String> {

        private String key_a = "data-source";
        private String value_a = "android";
        private String key_b = "email";
        private String value_b ;
        private String response;
        private RequestBody body;
        private String url = urlhelper.forgot;

        @Override
        protected String doInBackground(String... strings) {

            value_b = strings[0];

            Log.e("back", "doInBackground: " + value_a + value_b);
            body = RequestBuilder.twoParameter(
                    key_a, value_a, key_b, value_b);
            Log.e("back11111", "doInBackground: " + body);
            try {
                response = appController.POST(url, body);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;

        }


        @Override
        protected void onPostExecute(String s) {
            Log.e("RESSS", "onPostExecute: " + s);
            if (s != null) {
                try {

                    JSONObject rootObject = new JSONObject(response);
                   String status = rootObject.getString("status");
                    String msgs = rootObject.getString("message");
                    Log.e("RESSS", "onPostExecute: " + status);
                    if(status.equalsIgnoreCase("1")) {
                        bui(msgs);
                    }
                    else {
                        bui(msgs);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
